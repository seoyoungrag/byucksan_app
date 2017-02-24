<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.sds.acube.app.common.util.DateUtil"%>
<%@page import="com.sds.acube.app.enforce.vo.EnfDocVO"%>
<%@page import="com.sds.acube.app.enforce.vo.EnfLineVO"%>
<%@page import="com.sds.acube.app.appcom.vo.FileVO"%>
<%@page import="com.sds.acube.app.common.util.AppTransUtil"%>
<%@page import="com.sds.acube.app.appcom.vo.PubReaderVO"%>
<%@page import="com.sds.acube.app.approval.vo.RelatedDocVO"%>
<%@page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
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

String opt420 = appCode.getProperty("OPT420", "OPT420", "OPT"); //결재라인 사용 유무
opt420 = envOptionAPIService.selectOptionValue(compId, opt420);

//button
String msgOpinion =  messageSource.getMessage("approval.enforce.opinion", null, langType); //의견
String reDistOpinion = (String) request.getAttribute("reDistOpinion");  //재배부요청의견
String moveOpinion = (String) request.getAttribute("moveOpinion");  //이송의견
String sendOpinion = (String) request.getAttribute("sendOpinion"); //발송의견

String docinfoBtn = messageSource.getMessage("approval.button.docinfo", null, langType);
String acceptBtn = messageSource.getMessage("approval.enforce.button.accept", null, langType); // 접수
String updateBtn = messageSource.getMessage("appcom.button.update", null, langType); //수정
String processBtn = messageSource.getMessage("approval.enforce.button.process", null, langType);//처리
String prerespBtn = messageSource.getMessage("enforce.button.processorfix", null, langType);//선람/담당 지정
String processorRefix = messageSource.getMessage("enforce.button.processorRefix", null, langType);//담당자재지정
String respaccBtn = messageSource.getMessage("approval.enforce.button.respacc", null, langType);//담당접수
String insertBtn = messageSource.getMessage("appcom.button.insert", null, langType); //등록
String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); // 확인
String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기
String reDistributeBtn = messageSource.getMessage("approval.enforce.button.redistribute", null, langType); //재배부
String reDistRequestBtn = messageSource.getMessage("approval.enforce.button.redistrequest", null, langType); //재배부요청
String moveBtn = messageSource.getMessage("approval.enforce.button.move", null, langType);  //이송
String art60Btn = messageSource.getMessage("enforce.button.preopen", null, langType); //선람
String art70Btn = messageSource.getMessage("enforce.button.approaldoc", null, langType); //담당
String retrievedocBtn = messageSource.getMessage("enforce.button.retrievedoc", null, langType); //회수
String rejectDocBtn = messageSource.getMessage("enforce.button.rejectdoc", null, langType); //반려(담당자재지정요청)
String approalprocessdocBtn = messageSource.getMessage("enforce.button.approalprocessdoc", null, langType); //담당처리

String apppublicBtn = messageSource.getMessage("approval.button.pubreader", null, langType);//공람자
String sendToDocBtn = messageSource.getMessage("approval.button.sendtodoc", null, langType); // 문서관리로 보내기
String docHisBtn = messageSource.getMessage("approval.title.dochis", null, langType); // 문서 이력
String deleteBtn = messageSource.getMessage("approval.button.delete", null, langType); //삭제
String pubread = messageSource.getMessage("approval.button.pubread", null, langType); //공람

String saveBtn = messageSource.getMessage("approval.button.save", null, langType); // 첨부저장

//이전다음
String previousBtn = messageSource.getMessage("approval.button.previous.doc", null, langType); 
String nextBtn = messageSource.getMessage("approval.button.next.doc", null, langType); 

String bindBtn = messageSource.getMessage("approval.form.bind", null, langType); 

String docKindBtn = messageSource.getMessage("approval.form.docKind", null, langType); // 문서분류
String docKindInitBtn = messageSource.getMessage("approval.button.initialize", null, langType); // 문서분류 초기화
String applineBtn = messageSource.getMessage("approval.button.appline", null, langType); // 결재경로
String adminEditEnflineBtn = messageSource.getMessage("approval.button.modifyappline", null, langType); // 결재경로수정(관리자)

String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
String userName = (String) session.getAttribute("USER_NAME"); // 사용자명
String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
String deptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서명
String compName = (String) session.getAttribute("COMP_NAME");
pageContext.setAttribute("userId", userId);

String ROLE_CODES = (String) session.getAttribute("ROLE_CODES"); //role code

String role_doccharger = AppConfig.getProperty("role_doccharger","","role"); //처리과 문서 담당자
String role_cordoccharger = AppConfig.getProperty("role_cordoccharger","","role"); //문서과 문서 담당자
String role_appadmin = AppConfig.getProperty("role_appadmin","","role"); //시스템관리자


String apt006 = appCode.getProperty("APT006", "APT006", "APT"); // 이송
String apt013 = appCode.getProperty("APT013", "APT013", "APT"); // 재배부요청
String apt012 = appCode.getProperty("APT012", "APT012", "APT"); // 배부
String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기

String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
String startDate = DateUtil.getCurrentDate(dateFormat); // 2011-04-01
String startDateId = DateUtil.getCurrentDate("yyyyMMdd"); // 20110401
String currentYY = DateUtil.getCurrentDate("yyyy");

pageContext.setAttribute("currentYY", currentYY);
pageContext.setAttribute("compId", compId);

String lobCode = CommonUtil.nullTrim((String) request.getParameter("lobCode"));

String LOL001 = appCode.getProperty("LOL001","LOL001","LOL"); //등록대장
String LOL002 = appCode.getProperty("LOL002","LOL002","LOL"); //배부대장
String LOL003 = appCode.getProperty("LOL003","LOL003","LOL"); //미등록문서대장
String LOB007 = appCode.getProperty("LOB007","LOB007","LOB"); //배부대기함
String LOB008 = appCode.getProperty("LOB008","LOB008","LOB"); //접수대기함
String LOB003 = appCode.getProperty("LOB003","LOB003","LOB"); //결재대기함
String LOB004 = appCode.getProperty("LOB004","LOB004","LOB"); //결재진행함
String LOB019 = appCode.getProperty("LOB019","LOB019","LOB"); //재배부요청함
String LOB099 = appCode.getProperty("LOB099","LOB099","LOB"); //관리자함
String LOB011 = appCode.getProperty("LOB011", "LOB011", "LOB"); //접수완료함
String LOB012 = appCode.getProperty("LOB012", "LOB012", "LOB"); //공람문서함

pageContext.setAttribute("LOL001", LOL001);
pageContext.setAttribute("LOL002", LOL002);
pageContext.setAttribute("LOL003", LOL003);
pageContext.setAttribute("LOB003", LOB003);
pageContext.setAttribute("LOB004", LOB004);
pageContext.setAttribute("LOB007", LOB007);
pageContext.setAttribute("LOB008", LOB008);
pageContext.setAttribute("LOB019", LOB019);
pageContext.setAttribute("LOB099", LOB099);
pageContext.setAttribute("LOB011", LOB011);
pageContext.setAttribute("LOB012", LOB012);

String ENF600 = appCode.getProperty("ENF600","ENF600","ENF");
String ENF110 = appCode.getProperty("ENF110","ENF110","ENF"); //재배부요청
String ENF200 = appCode.getProperty("ENF200","ENF200","ENF"); //접수대기(부서)
String ENF250 = appCode.getProperty("ENF250","ENF250","ENF"); //접수대기(사람)
String ENF300 = appCode.getProperty("ENF300","ENF300","ENF"); //접수대기(사람)
String ENF310 = appCode.getProperty("ENF310","ENF310","ENF"); //선람 및 담당 지정 대기(반송)
String ENF400 = appCode.getProperty("ENF400","ENF400","ENF");
String ENF500 = appCode.getProperty("ENF500","ENF500","ENF");
pageContext.setAttribute("ENF600", ENF600);
pageContext.setAttribute("ENF110", ENF110);
pageContext.setAttribute("ENF200", ENF200);
pageContext.setAttribute("ENF250", ENF250);
pageContext.setAttribute("ENF250", ENF250);
pageContext.setAttribute("ENF300", ENF300);
pageContext.setAttribute("ENF310", ENF310);
pageContext.setAttribute("ENF400", ENF400);
pageContext.setAttribute("ENF500", ENF500);

String ART060 = appCode.getProperty("ART060","ART060","ART"); //선람
String ART070 = appCode.getProperty("ART070","ART070","ART"); //선람
pageContext.setAttribute("ART060", ART060);
pageContext.setAttribute("ART070", ART070);

String DRS001 = appCode.getProperty("DRS001", "DRS001", "DRS");
String DRS002 = appCode.getProperty("DRS002", "DRS002", "DRS");
String DRS003 = appCode.getProperty("DRS003", "DRS003", "DRS");
String DRS004 = appCode.getProperty("DRS004", "DRS004", "DRS"); // jth8172 2012 신결재 TF
String DRS005 = appCode.getProperty("DRS005", "DRS005", "DRS");  // jth8172 2012 신결재 TF
pageContext.setAttribute("DRS001", DRS001);
pageContext.setAttribute("DRS002", DRS002);
pageContext.setAttribute("DRS003", DRS003);
pageContext.setAttribute("DRS004", DRS004);  // jth8172 2012 신결재 TF 
pageContext.setAttribute("DRS005", DRS005);   // jth8172 2012 신결재 TF

String docId = request.getParameter("docId");
docId = (docId == null?"":docId);
pageContext.setAttribute("docId", docId);

// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 start --- // jth8172 2012 신결재 TF
UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
String institutionId = (String) userProfileVO.getInstitution();
String headOfficeId = (String) userProfileVO.getHeadOffice();
// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 end   --- // jth8172 2012 신결재 TF


String sAttach = "";
StringBuffer sEnfLines = new StringBuffer("");
StringBuffer sEnfLines310 = new StringBuffer("");
int lineSize = 0;

String procYn = "N"; //로그인 사용자 처리 여부
String retvYn = "N"; //회수가능여부
String reEnfLinYn = "Y"; //담당자재지정 가능여부

EnfDocVO result = (EnfDocVO)request.getAttribute("docInfo");

String bindingResourceName = "";
if (result != null) {
	bindingResourceName = EscapeUtil.escapeJavaScript(result.getBindingResourceId());
}

StringBuffer pubReader = new StringBuffer();
String pubReaderYn = "N";
String pubReadYn = "N"; //공람처리 여부

String docState = ""; // 문서상태
int serialNumber = 0;

if(request.getAttribute("docInfo") != null){
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부	
	List<EnfLineVO> enflines = result.getEnfLines();

	
	lineSize = enflines.size();
	
	if((lineSize == 0 && ENF300.equals(result.getDocState())) || ENF310.equals(result.getDocState()) ){
	    procYn = "Y";
	}
	
	if(lineSize == 0){
		reEnfLinYn = "N";
	}
	
	int myCnt = -1;	//내결재선의 현 위치
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
	    sEnfLines.append(item.getMobileYn().replace("\n","<br />"));
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
	    
	    sEnfLines310.append(item.getProcessorId());
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getProcessorName()); 
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getProcessorPos());  
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getProcessorDeptId());
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getProcessorDeptName());
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getRepresentativeId()); 
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getRepresentativeName());
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getRepresentativePos());
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getRepresentativeDeptId());
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getRepresentativeDeptName());
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getAskType());
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append("");
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append("");
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append("");
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getEditLineYn());
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getMobileYn());
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append("");
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getSignFileName());
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getLineHisId());
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getFileHisId());
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getAbsentReason());  
	    sEnfLines310.append(String.valueOf((char) 2));
	    sEnfLines310.append(item.getLineOrder());
	    sEnfLines310.append(String.valueOf((char) 4));
	    
	    if(apt003.equals(item.getProcType())){
			if(userId.equals(item.getProcessorId()) || userId.equals(item.getRepresentativeId())){
			    procYn = "Y";
			}
	    }
	    
	    if(apt001.equals(item.getProcType())){
			if(userId.equals(item.getProcessorId()) || userId.equals(item.getRepresentativeId())){
			    myCnt = i+1;
			}
    	}
	    
	    String rdDate = item.getReadDate();
	    rdDate =(rdDate.indexOf("9999") != -1 ? "" :rdDate);
	    //회수 가능 여부
	    if(myCnt == i && apt003.equals(item.getProcType())&& "".equals(rdDate)){//readdate 여부도 채크할 것
			retvYn = "Y";
	    }
	    
	  //담당자 재지정 여부체크 읽은 표시가 없으면 다시 지정가능, 한번이라도 읽으면 재지정 못함
	    if("".equals(rdDate) || rdDate.indexOf("9999") != -1){ 
	    	if(!"N".equals(reEnfLinYn)){
	    		reEnfLinYn = "Y";
	    	}
	    }else{	    	
	    	reEnfLinYn = "N";
	    }
	  
	  // 결재여부를 채크한다.
	  	String procDate = item.getProcessDate();
	    if("".equals(procDate) || procDate.indexOf("9999") != -1){ 
	    	if(!"N".equals(reEnfLinYn)){
	    		reEnfLinYn = "Y";
	    	}
	    }else{	    	
	    	reEnfLinYn = "N";
	    }
	}
	//reEnfLinYn = "Y";//테스트용 후에 지 울것
	
	List<FileVO> fileVOs = result.getFileInfos();
	sAttach = EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft004));
	
	if(ROLE_CODES.indexOf(role_doccharger) != -1){//처리과 문서관리자이면
	    pubReaderYn = "Y";
	}

	//공람자
	List<PubReaderVO> pubReaders = result.getPubReader();
	
	for(int i = 0; i < pubReaders.size(); i++){
	    PubReaderVO pubReaderVO = pubReaders.get(i);    
	    pubReader.append(pubReaderVO.getPubReaderId());
	    pubReader.append(String.valueOf((char) 2));
	    pubReader.append(pubReaderVO.getPubReaderName());  
	    pubReader.append(String.valueOf((char) 2));
		pubReader.append(pubReaderVO.getPubReaderPos()); 
		pubReader.append(String.valueOf((char) 2));
		pubReader.append(pubReaderVO.getPubReaderDeptId());
		pubReader.append(String.valueOf((char) 2));
		pubReader.append(pubReaderVO.getPubReaderDeptName());
		pubReader.append(String.valueOf((char) 2));
		pubReader.append(pubReaderVO.getPubReaderRole());  
		pubReader.append(String.valueOf((char) 2));
		pubReader.append(pubReaderVO.getPubReaderOrder());
		pubReader.append(String.valueOf((char) 2));
		pubReader.append(pubReaderVO.getReadDate());
		pubReader.append(String.valueOf((char) 2));
		pubReader.append(pubReaderVO.getPubReadDate());
		pubReader.append(String.valueOf((char) 2));
		pubReader.append(pubReaderVO.getRegisterId());
		pubReader.append(String.valueOf((char) 2));
		pubReader.append(pubReaderVO.getUsingType());
		pubReader.append(String.valueOf((char) 2));
		pubReader.append(pubReaderVO.getDeleteYn());
		pubReader.append(String.valueOf((char) 4));
		
		if(userId.equals(pubReaderVO.getPubReaderId())){
		    pubReaderYn = "Y";
		    
		   if(! "".equals(pubReaderVO.getPubReadDate()) && pubReaderVO.getPubReadDate().indexOf("9999") == -1){
			   pubReadYn = "Y";
		   }
		}
	}
	docState = CommonUtil.nullTrim(result.getDocState()); // 문서상태
	serialNumber = result.getSerialNumber(); // 문서번호
}

pageContext.setAttribute("enfLines", sEnfLines.toString());
pageContext.setAttribute("enfLines310", sEnfLines310.toString());

pageContext.setAttribute("procYn", procYn);
pageContext.setAttribute("retvYn", retvYn);//회수가능여부
pageContext.setAttribute("reEnfLinYn", reEnfLinYn); //담당자 재지정 가능여부

pageContext.setAttribute("pubReader", pubReader.toString());
pageContext.setAttribute("pubReaderYn", pubReaderYn);
pageContext.setAttribute("pubReadYn", pubReadYn);

pageContext.setAttribute("lineSize",lineSize);
pageContext.setAttribute("isExtWeb", isExtWeb);

String closeYn = CommonUtil.nullTrim(request.getParameter("closeYn"));
String title = CommonUtil.nullTrim(request.getParameter("title"));
String sendOrgName = CommonUtil.nullTrim(request.getParameter("sendOrgName"));
String docNumber = CommonUtil.nullTrim(request.getParameter("docNumber"));

String bindingYn = CommonUtil.nullTrim((String) request.getAttribute("binding"));
bindingYn = ("".equals(bindingYn) == true)? "N" : bindingYn;

String authority = CommonUtil.nullTrim((String) request.getAttribute("authority"));
//authority = "success";
if(!"".equals(docId)){
	if(!"success".equals(authority) && userId.equals(result.getRegisterId())){//등록자인경우 권한 있음
		authority = "success";
	}
}
String msg = CommonUtil.nullTrim((String)request.getAttribute("message"));

String opt357 = appCode.getProperty("OPT357", "OPT357", "OPT"); // 결재 처리 후 문서 자동닫기
opt357 = envOptionAPIService.selectOptionValue(compId, opt357);
String opt379 = appCode.getProperty("OPT379", "OPT379", "OPT"); // 공람문서 다음 문서 자동 열기(Y:사용, N:미사용)
opt379 = envOptionAPIService.selectOptionValue(compId, opt379);
String autoNextDocYn  = CommonUtil.nullTrim(request.getParameter("autoNextDocYn")); // 다음문서자동오픈
%>

<%
//페이지 권한 체크
if("".equals(docId) || "success".equals(authority)){

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
<% if(!"".equals(docId)){ %>
	<spring:message code="approval.title.enforce.noeledoc.select" />
<% }else{ %>
	<spring:message code="approval.title.enforce.noeledoc.insert" />
<% } %>
</title>
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

<jsp:include page="/app/jsp/enforce/InsertEnfNonEleDocIn.jsp" />

<script type="text/javascript">
$(document).ready(function() { init_page(); });
$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });
$.ajaxSetup({async:false});

function init_page(){
	//편철 미사용시 보조기한 설정(defalut 값 설정),jd.park, 20120509
	<c:if test='${(OPT423 == "N" && docId == "" && lobCode != LOL002) || (OPT423 == "N" && lobCode == LOB008)}'>
	$("#retentionPeriod").val("${default}");
	</c:if>
	
	// 화면블럭지정
	screenBlock();

	initializeFileManager();

	<c:if test='${OrgAbbrName != ""}'>
	bOrgAbbrName = true;
	</c:if>
	
	setValiables();

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

	//채번안함noSerialYn
	$('#noSerialYn').bind('click', function(){
		if($(this).attr("checked") === true){
			$('#divSerialNum').show();
			$("#divProSerial").show();
			
			<c:if test='${OPT355.useYn == "Y" || OPT356.useYn == "Y"}'>
			$('#divAcess1').show();
			$('#divAcess2').hide();
			</c:if>
			
			<c:if test='${OPT355.useYn == "Y" || OPT356.useYn == "N"}'>
			$('#divAcess1').show();
			$('#divAcess3').hide();
			</c:if>

			<c:if test='${OPT355.useYn == "N" || OPT356.useYn == "Y"}'>
			$('#divAcess3').show();
			$('#divAcess2').hide();
			</c:if>
			
		}else{
			$('#divSerialNum').hide();
			$("#divProSerial").hide();

			<c:if test='${OPT355.useYn == "Y" || OPT356.useYn == "Y"}'>
			$('#divAcess1').hide();
			$('#divAcess2').show();
			</c:if>

			<c:if test='${OPT355.useYn == "Y" || OPT356.useYn == "N"}'>
			$('#divAcess1').hide();
			$('#divAcess3').show();
			</c:if>

			<c:if test='${OPT355.useYn == "N" || OPT356.useYn == "Y"}'>
			$('#divAcess3').hide();
			$('#divAcess2').show();
			</c:if>
		}
		initBind();
		
	});

	<c:if test='${docId != ""}'>
		setPageValues(); //페이지를 세팅한다.
		drawEnfLines();
		
		<c:if test='${lobCode == LOB003 && docInfo.docState == ENF310}'>
		$('#enfLines').val("${enfLines310}");
		</c:if>
	</c:if>

	<c:if test='${lobCode == LOB008}'>	
	// 이송의견(발송의견이 있으면 함께 보여준다.) 
	if ($("#moveOpinion").val() != "") {
		viewMoveOpinion();
	}
	</c:if>

	<c:if test='${(lobCode == LOB007 && docInfo.docState == ENF110) || lobCode == LOB019}'>
	if ($("#reDistOpinion").val() != "") {
		viewReDistOpinion();
	}
	</c:if>

	<c:if test='${lobCode == LOB003}'>
	setReadDate();
	</c:if>

	$('#publicPostYn').bind("click",function(){
		if($(this).attr('checked')){
			$('#divPublicPost').show();
		}else{
			$('#divPublicPost').hide();
		}
	});

	<c:if test='${docId != ""}'>
	//첨부파일 체크파일 없음
	//$('input[name=attach_cname]:checkbox').hide();
	</c:if>
	
	// 화면블럭해제
	screenUnblock();
}

//편철 및 생산등록번호 초기화
function initBind(){
	$('#bindId').val("");
	$('#bindNm').val("");
	$('#conserveType').val("");
	$('#divBind').show();
	
	$('#DeptCategory').val("${OrgAbbrName}");
	$('#divSerialValue').html("${OrgAbbrName}");

}

<c:if test='${docId != ""}'>
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
	var recvEnfType		= $('#recvEnfType');	//시행범위
	var readRange       = $('#readRange');		//열람범위
	var readRangeNm		= $('#readRangeNm');	//열람범위
	var apDty           = $('#apDty');			//등록구분
	var apDtyNm         = $('#apDtyNm');		//등록구분
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
	
	<c:if test='${docInfo.serialNumber != "0"}'>
	DeptCategory.val('${docInfo.deptCategory}');
	SerialNum.val('${docInfo.serialNumber}');
	SubserialNumber.val('${docInfo.subserialNumber}');
	</c:if>

<c:if test='${docId != ""}'>

var publicPost		= "<c:out value="${docInfo.publicPost}" />";

if(publicPost.trim() !== ""){	
	$('#publicPostYn').attr('checked',true);
	$('#divPublicPost').show();
	$('#publicPost').val(publicPost);
}

<c:if test='${lobCode != LOB008}'>	
	$('#divSerial').hide();	
</c:if>	
	$('#divRefDept').hide();	
</c:if>

	$('#imgAppend').hide();
	$('#imgRemove').hide();
	$('#imgUp').hide();
	$('#imgDown').hide();
	$('#imgRelDoc').hide();
	//$('#imgBinding').hide();
	$('#calProcess').hide();
	
	
	title          .attr('readonly',true); 
	sendOrgName    .attr('readonly',true); 
	enforceDate    .attr('readonly',true); 
	docNumber      .attr('readonly',true); 
	recSummary     .attr('readonly',true); 
	bindId         .attr('readonly',true); 
	 

	title          .attr('class','input_read'); 
	sendOrgName    .attr('class','input_read'); 
	enforceDate    .attr('class','input_read'); 
	docNumber      .attr('class','input_read'); 
	recSummary     .attr('class','input_read'); 
	

	//제목
	title.val("<%=(result.getTitle() == null)?"":EscapeUtil.escapeJavaScript(result.getTitle())%>");
	//title.val("<%=result.getTitle().replace("\"","\\\"")%>");
	
	//편철
	bindId.val("${docInfo.bindingId}");
	bindNm.val("<%=(result.getBindingName() == null)? "" : EscapeUtil.escapeJavaScript(result.getBindingName())%>");
	conserveType.val("${docInfo.conserveType}");
	
	//편철 미사용시 보존기한 설정(defalut 값 설정),jd.park, 20120509
	<c:if test='${lobCode != LOL002 && lobCode != LOB007 && lobCode != LOB019 && (OPT423 == "N" || (OPT423 == "Y" && docInfo.bindingId == ""))}'>
	$("#retentionPeriod").val(conserveType.val());
	</c:if>
	
	//파일첨부
	loadAttach($("#attachFile").val(), false);
	<%-- 접수 문서 시 관련문서 사용안하도록 변경, jd.park, 20120612
	//관련문서
	var docInfo = "";
	<%
	StringBuffer relatedDoc = new StringBuffer();
	List<RelatedDocVO> relDocs = result.getRelatedDoc();
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
	%>
	
	var infolist = getRelatedDocList("<%=relatedDoc%>");
	setRe1DocList(infolist);
	relDoc.val(docInfo);
	--%>
	//시행정보
	//발신기관명
	sendOrgName.val("<%=(result.getSenderDeptName() == null)? "" : EscapeUtil.escapeJavaScript(result.getSenderDeptName())%>");
	//시행일자
	var tmpDate = '${docInfo.receiveDate}'.substring(0,10).replace(/-/g, "/");
	tmpDate = (tmpDate.indexOf('9999') != -1 ? "" : tmpDate);
	enforceDate.val(tmpDate);
	$('#divEnforceDate').html(enforceDate.val());

	tmpDate = '${docInfo.receiveDate}'.substring(0,10).replace(/-/g, '');
	tmpDate = (tmpDate.indexOf('9999') != -1 ? "" : tmpDate);
	enforceDateId.val(tmpDate);

	//접수부서
	recvDeptNm.val('${docInfo.receiverInfos[0].refDeptName}');
	recvDeptId.val('${docInfo.receiverInfos[0].refDeptId}');
	
	//생산등록번호
	docNumber.val("<%=(result.getDocNumber() == null)? "": EscapeUtil.escapeJavaScript(result.getDocNumber())%>");
	//시행범위
	enfType.val('${docInfo.enfType}');
	recvEnfType.val(enfType.val());
	if(enfType.val() === DET003){
		$('#enfTypeNm').val('<spring:message code="approval.form.det003" />')
	}else if(enfType.val() === DET004){
		$('#enfTypeNm').val('<spring:message code="approval.form.det004" />')
	}else{
		$('#enfTypeNm').val('<spring:message code="approval.form.det002" />')
	}
	$('#divEnfTypeNm').html($('#enfTypeNm').val());
	//$('#divEnforce').show();

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
	if(readRange.val() === DRS001){
		readRangeNm.val('<spring:message code="approval.form.readrange.drs001" />');
	}
	if(readRange.val() === DRS002){
		readRangeNm.val('<spring:message code="approval.form.readrange.drs002" />');
	}
	if(readRange.val() === DRS003){
		readRangeNm.val('<spring:message code="approval.form.readrange.drs003" />');
	}
	if(readRange.val() === DRS004){
		readRangeNm.val('<spring:message code="approval.form.readrange.drs004" />');
	}
	if(readRange.val() === DRS005){   // jth8172 2012 신결재 TF
		readRangeNm.val('<spring:message code="approval.form.readrange.drs005" />');
	}

	$('#divReadRangeNm').html(readRangeNm.val());
	
	apDty.val('${docInfo.nonElectron.apDty}');
	rectype.val('${docInfo.nonElectron.recType}');

	if(apDty.val() === DTY001 ){
		apDtyNm.val('<spring:message code="approval.form.dty001" />');
	}
	if(apDty.val() === DTY002 ){
		apDtyNm.val('<spring:message code="approval.form.dty002" />');
	}
	if(apDty.val() === DTY003 ){
		apDtyNm.val('<spring:message code="approval.form.dty003" />');
		$('#div_rectype').show();
		$('#div_rectype_c').show();
	}
	if(apDty.val() === DTY004 ){
		apDtyNm.val('<spring:message code="approval.form.dty004" />');
		$('#div_rectype').show();
		$('#div_rectype_d').show();
	}
	if(apDty.val() === DTY005 ){
		apDtyNm.val('<spring:message code="approval.form.dty005" />');
	}

	$('#divApDtyNm').html(apDtyNm.val());
	
	var recTypes = rectype.val().split(',');
	var strRecType = "";
	var recCnt = 0;
	for(var i = 0; i < recTypes.length; i++){
		for(var j = 0; j < RTS.length; j++){
			if(recTypes[i] === RTS[j].code){
				if(recCnt > 0 ){
					strRecType += ",&nbsp;";
				}
				//strRecType += "[";
				strRecType += RTS[j].name; 
				//strRecType += "]";
				recCnt++;
				break;
			}
		}
	}

	$('div#divRectype').html(strRecType);
	
	openLevel.val('${docInfo.openLevel}');
	openReason.val('${docInfo.openReason}');
	specialRec.val('${docInfo.nonElectron.specialRec}');
	
	var strTmp = "";
	if(specialRec.val().length === 5){
		var strTmp = "";
		if("Y" === specialRec.val().substring(0,1)){
			strTmp += '<spring:message code="approval.from.spcreca" />';
		}

		if("Y" === specialRec.val().substring(1,2)){
			if(strTmp !== ""){
				strTmp += ",&nbsp;";
			}
			strTmp += '<spring:message code="approval.from.spcrecb" />';
		}

		if("Y" === specialRec.val().substring(2,3)){
			if(strTmp !== ""){
				strTmp += ",&nbsp;";
			}
			strTmp += '<spring:message code="approval.from.spcrecc" />';
		}

		if("Y" === specialRec.val().substring(3,4)){
			if(strTmp !== ""){
				strTmp += ",&nbsp;";
			}
			strTmp += '<spring:message code="approval.from.spcrecd" />';

		}


		if("Y" === specialRec.val().substring(4,5)){
			if(strTmp !== ""){
				strTmp += ",&nbsp;";
			}
			strTmp += '<spring:message code="approval.from.spcrece" />';
		}
		

		$('div#divSpecialRec').html(strTmp);
	}

	Categoris.val('${docInfo.docType}');
}
</c:if>

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
//공람자
function selectPubReader() {
	var width = 650;
	var height = 570;
	var appDoc = null;
	var url = "<%=webUri%>/app/approval/ApprovalPubReader.do?usingType="+DPI002;
	appDoc = openWindow("pubreaderWin", url, width, height); 
}

function getPubReader() {
	return $("#pubReader").val();
}

function setPubReader(pubreader) {	
	$("#pubReader").val(pubreader);
}

function listPubReader(){
	var width = 700;
	var height = 450;
	var appDoc = null;
	
	var url = "<%=webUri%>/app/appcom/listPubReader.do?docId=${docInfo.docId}&lobCode=${lobCode}&electronicYn=N";
	appDoc = openWindow("listPubReaderWin", url, width, height); 
}

function goAjax(url){
	// 편철 다국어 추가
	var saveBindName =$('#bindNm').val();
	$('#bindNm').val(escapeJavaScript($("#bindingResourceId").val()));
	
	closeNo = false;
	var result = "", Status = "fail";
	$.ajaxSetup({async:false});
	$.post(url,$('form').serialize(),function(data, textStatus){
		result = data
		Status = textStatus;

		$('#bindNm').val(saveBindName);	
	if (Status !== "success" || result.resultCode === "fail") {
		if(bReEnfLine ){
			//bReEnfLine = false;
			$('#refrash').val("Y");
			setTimeout(function(){ afterSubmit(true, '<spring:message code="enforce.msg.approvalRedefine" />'); }, 100);
		}else{
			setTimeout(function(){ afterSubmit(false, '<spring:message code="approval.msg.fail.savenonele" />'); }, 100);
		}
	} else {
		if(bReqAcc){//담당접수
			if($('#docId').val() === ""){
				$('#docId').val(result.resultMessageKey);
				$('#noSerialYn').hide();
				$('#imgProSerial').hide();
				$('#imgClose').hide();
				$('#noSerialYnNm').hide();
				$('#divSerialValue').html(result.errorMessage);

				//$('#refrash').val("Y");
			}
			$('#refrash').val("Y");
			setTimeout(function(){ afterReqAcc(true, '<spring:message code="approval.result.msg.acceptapprovalok" />'); }, 100);
		}else if(bApprovalok){//담당
			$('#refrash').val("Y");
			setTimeout(function(){ afterReqAcc(true, '<spring:message code="approval.result.msg.approvalok" />'); }, 100);
		}else if(bPrereadok){//선람
			$('#refrash').val("Y");
			setTimeout(function(){ afterReqAcc(true, '<spring:message code="approval.result.msg.prereadok" />'); }, 100);
		}else{
			if($('#docId').val() === ""){//문서대장에서 접수처리
				$('#docId').val(result.resultMessageKey);
				$('#noSerialYn').hide();
				$('#imgProSerial').hide();
				$('#imgClose').hide();
				$('#noSerialYnNm').hide();
				$('#divSerialValue').html(result.errorMessage);
				
				$('#refrash').val("Y");
				//alert('접수');
				if(insertType ===2){
					closeNo = true;
				}
			}else{
				$('#refrash').val("N");
			}

			var saveMsg ='<spring:message code="approval.msg.success.savenonele" />'; 

			if(messageType === 2){ //접수(접수대장에서 접수처리)
				$('#refrash').val("N");
				$('#divBind').hide();
				$('#imgProSerial').hide();
				$('#imgClose').hide();
				$('#noSerialYn').hide();
				$('#noSerialYnNm').hide();
				$('#divSerialValue').html(result.errorMessage);
				saveMsg  ='<spring:message code="approval.result.msg.acceptok" />';

				if(insertType ===2){
					closeNo = true;
				}
			}else if(messageType === 3){
				$('#refrash').val("Y");
				
				saveMsg  ='<spring:message code="approval.result.msg.approverfixok" />';				
			}else{
				saveMsg ='<spring:message code="approval.msg.success.savenonele" />'; 
			}
			
			setTimeout(function(){ afterSubmit(true, saveMsg ); }, 100);
		}
	}

	}, 'json').error(function(data) {

		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='enforce.preread.error.processerror'/>");
		}
	});
}

//담당접수 후에 종료 처리
function afterReqAcc(result, message){
	if(result){		
		try {
			if(typeof(opener) !== "undefined" && typeof(opener.listRefreshCompulsion) !== "undefined"){
				if(opener.curLobCode != null && opener.curLobCode == '${lobCode}') {
				    opener.listRefreshCompulsion();
				}
			}	
		} catch(e) {}
		
		//필요시사용, 포탈 홈화면에서 문서 닫을때 결재대기함, 진행문서함 refresh  jd.park, 20120427 S
		//InsertEnfNonEleDocIn.jsp의 함수
		//각 사이트에 맞도록 아래 함수에 적용
		callRefreshPortlet();
		//필요시사용, 포탈 홈화면에서 문서 닫을때 결재대기함, 진행문서함 refresh  jd.park, 20120427 E
		
<% if("Y".equals(opt357)){ %>
		<%if(!"N".equals(closeYn)){%>		
		window.close();		
		<%}%>
<% }else{ %>
		if($('#refrash').val() === "N"){
			$('#divAcess').hide();
			$('#divAcess1').hide();
			$('#divAcess2').hide();
			$('#divAcess3').hide();
			$('#divCloseBtn').hide();
			$('#divCloseBtn2').show();
			
			
		}else{
			setCancel();
		}
<% } %>
		if($('#refrash').val() === "Y"){
			proRefresh();
		}
		
	}
}

function afterSubmit(result, message) {
	alert(message);

	if (result) {
		if(svrType === "1"){
			$('#divAcess').hide();
			$('#divAcess1').hide();
			$('#divAcess2').hide();
			$('#divAcess3').hide();
			$('#divCloseBtn').show();
			setProcess(gBtnNm);
		}else{
			try {
				if(typeof(opener) !== "undefined" && typeof(opener.listRefreshCompulsion) !== "undefined"){
					if(opener.curLobCode != null && opener.curLobCode == '${lobCode}') {
					    opener.listRefreshCompulsion();
					}
				}
			} catch(e) {}

<% if("Y".equals(opt357)){ %>
			<%if(!"N".equals(closeYn)){%>
			if(closeNo){
				proRefresh();
			}else{	
				window.close();
			}		
			<%}%>
<% }else {%>
			proRefresh();
<% } %>
		}
	}
}

function proRefresh(){
	if($('#refrash').val() === "N"){
		$('#divAcess').hide();
		$('#divAcess1').hide();
		$('#divAcess2').hide();
		$('#divAcess3').hide();
		$('#divCloseBtn').show();
	}else{
		setCancel();
	}
}

//등록
function setEnf(method){ 
	messageType = method;
	$('#method').val(method);

	if(method == 2){
		insertType = 2
	}

	<c:if test='${(lobCode == LOL001 && docId == "") || lobCode == LOB008}' >
	//${OPT358.useYn}
	<c:if test='${OPT358.useYn == "1"}' >
		if(!bOrgAbbrName){
			alert('<spring:message code="approval.msg.notexist.deptcategory" />');
			return;
		}
	</c:if>
	<c:if test='${OPT358.useYn == "2"}' >
	if($('#noSerialYn').attr('checked')){
		if(!bOrgAbbrName){
			alert('<spring:message code="approval.msg.notexist.deptcategory" />');
			return;
		}
	}
	</c:if>
	</c:if>	

	// 편철 사용시, jd.park, 20120509
	<c:if test='${lobCode != LOL002 && lobCode != LOB007 && lobCode != LOB019}'>
		<c:choose>
			<c:when test='${OPT423 == "Y"}'>				
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
	</c:if>

	//문서분류 사용시, jd.park, 20120509
	<c:if test='${lobCode != LOL002 && lobCode != LOB007 && lobCode != LOB019}'>
		<c:if test='${OPT422 == "Y"}'>			
			if("" === $.trim($('#classNumber').val())){	
				alert("<spring:message code='approval.msg.nomanage.number'/>");
				goDocKind();
				return;
			}

		</c:if>
	</c:if>
	

	
	// 기본사항 체크
	if(!chkDefaultValues()){
		return;
	}

	<c:if test='${OPT359.useYn == "Y"}' >
	if("" === $('#attachFile').val()){ 
		alert('<spring:message code="approval.file.selectonlyoneattachfile" />');
		return;
	}
	</c:if>
	
	<c:if test='${lobCode == LOL002}'>
	if("" === $.trim($('#refDeptName').val())){//접수부서
		alert('<spring:message code="approval.msg.noaccdept" />');
		return;
	}
	</c:if>
		
	arrangeAttach();
	
	<c:if test='${lobCode != LOL001}'>
	</c:if>
	if(method === 2){
		svrType = "1";
	}


	gBtnNm = 40;
	var btnNm = "<%=insertBtn%>";
	if(method === 2){
		gBtnNm = 50;
		btnNm = "<%=acceptBtn%>";
	}

	//popOpinion("goEnf",btnNm, "N" );

	goCertificateProcess("goEnf", btnNm, "N");
}

function goEnf(){
	var url = "<%=webUri%>/app/enforce/insertEnfNonElecDoc.do";
	goAjax(url);
}

//담당접수
function setResAcc(){
	<c:if test='${(lobCode == LOL001 && docId == "") || lobCode == LOB008}' >
	//${OPT358.useYn}
	<c:if test='${OPT358.useYn == "1"}' >
		if(!bOrgAbbrName){
			alert('<spring:message code="approval.msg.notexist.deptcategory" />');
			return;
		}
	</c:if>
	<c:if test='${OPT358.useYn == "2"}' >
	if($('#noSerialYn').attr('checked')){
		if(!bOrgAbbrName){
			alert('<spring:message code="approval.msg.notexist.deptcategory" />');
			return;
		}
	}
	</c:if>
	</c:if>
	// 편철 사용시, jd.park, 20120509
	<c:if test='${lobCode != LOL002 && lobCode != LOB007 && lobCode != LOB019}'>
		<c:choose>
			<c:when test='${OPT423 == "Y"}'>
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
	</c:if>
	
	//문서분류 사용시, jd.park, 20120509
	<c:if test='${lobCode != LOL002 && lobCode != LOB007 && lobCode != LOB019}'>
		<c:if test='${OPT422 == "Y"}'>
			if("" === $.trim($('#classNumber').val())){	
				alert("<spring:message code='approval.msg.nomanage.number'/>");
				goDocKind();
				return;
			}
		</c:if>
	</c:if>
	
	// 기본사항 체크
	if(!chkDefaultValues()){
		return;
	}
		

	<c:if test='${OPT359.useYn == "Y"}' >
	if("" === $('#attachFile').val()){ 
		alert('<spring:message code="approval.file.selectonlyoneattachfile" />');
		return;
	}
	</c:if>
	
	arrangeAttach();

	bReqAcc = true;

	openOpinion(ART070);
	
}

//처리---------------------------->결재등을 처리한다.
function setProcess(btn){
	gBtnNm = btn;

	if(btn === 10){
		bPrereadok = true;
	}

	if(btn === 20){
		bApprovalok = true;
	}

	if(btn === 30 || btn === 50){
		messageType = 3; //선람담당지정
	}
	
	<c:if test='${docInfo.enfLine == null || docInfo.docState == ENF310}'>
	svrType = "2";
	</c:if>
	
	var enfs = getEnfList($('#enfLines').val());
	
	if (enfs.length === 0 || ENF310 === '${docInfo.docState}') {
		
		setTimeout(function(){openEnfLine();}, 100);
		return;
	}

	if(btn === 30 || btn === 50){
		setTimeout(function(){openEnfLine();}, 100);
		return;
	}else{
		setProcessAfter();
	}
}

function setProcessAfter(){
	<c:if test='${docInfo.enfLine == null || docInfo.docState == ENF310}'>

	//담당자 지정 및  반려(담당자 재지정 요청) 후 담당자 지정
	$('#method').val(1);
	//setOpinion('','','','');
	goOpinion();
	return;
	</c:if>

	if(bReEnfLine){//담당자 재지정(접수후 담당자 재지정 2011.09.01 추가
		$('#method').val(1);
		//setOpinion('','','','');
		rejectDoc();
		return;
	}
	
	<c:if test='${docInfo.enfLine != null}'>
	$('#method').val(2);
	/* openOpinion('${docInfo.enfLine.askType}'); */			//담당자 담당 확인 시에도 결재의견 삭제함.
	//if(confirm("결재를 진행 하시겠습니까?")){
		goOpinion();	
	//}
	
	return;
	</c:if>
}

function openOpinion(askType){
		
	var width = 500;
	var height = 300;
	var appDoc = null;
	var url = "<%=webUri%>/app/approval/createOpinion.do?askType=" + askType  + "&actType="+APT001;
	appDoc = openWindow("opinionWin", url, width, height); 
}

function setOpinion(opinion, askType, actType){
	
	$('#opinion').val(opinion);
	$('#askType').val(askType);
	
	
	goOpinion();
	
}

function goOpinion(){
	var url = "<%=webUri%>/app/enforce/processEnfNonElecDoc.do";

	if(bReqAcc){//접수
		url = "<%=webUri%>/app/enforce/processEnfNonEleResAcc.do";
	}

	goAjax(url);
}

function closePopup(){	
	<c:if test='${lobCode == LOB003 || lobCode == LOB012}' >
		try {
			if(typeof(opener) !== "undefined" && typeof(opener.listRefreshCompulsion) !== "undefined"){
				if(opener.curLobCode != null && opener.curLobCode == '${lobCode}') {
				    opener.listRefreshCompulsion();
				}
			}
		} catch(e) {}
	</c:if>
	window.close();
}

//--------검색이벤트-------------------
//편철업무
var bindDoc = null
function goBind(){	
	var width = 420;
	var height = 440;
	var appDoc = null;
	var serialNumber = "Y";
	if (!$("#noSerialYn").attr("checked")) {
		serialNumber = "N";
	}
	var url = "<%=webUri%>/app/bind/select.do?serialNumber=" + serialNumber;
	
	//관리번호 사용일 경우
	//width = 420;
	//height = 300;
	//var url = "<%=webUri%>/app/common/selectClassification.do";
		
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
			var procDate = enf.processDate.replace(/-/g, "/");		
			if(procDate.indexOf("9999") != -1) procDate = "&nbsp;"
			rows += "<td class='ltb_center'>"+procDate+"</td>";
//		}
		
		var lineopinion = $('#lineopinion');
		lineopinion.val(enf.procOpinion);
		var procOpinion = lineopinion.val();
		procOpinion = escapeJavaScript(procOpinion);
		var pattern = /\\n/g;
		procOpinion = procOpinion.replace(pattern,"<br />");
		
		//procOpinion = unescapeCarriageReturn(escapeHtml(procOpinion));		
		rows += "<td class='ltb_left'>"+procOpinion+"</td>";
		tbEnfLines.append(rows);
	}
}

//담당자 지정 오픈
function openEnfLine(){	

	if (typeof(type) == "undefined") {
		type = "";
	}
	var appDoc = null;
	var groupYn = "Y";

<% if(ENF400.equals(docState) || ENF500.equals(docState)){ %>
	groupYn = "N"; 
<% } %>
	
	if(type == "U"){
		appDoc = openWindow("enfLine", "<%=webUri%>/app/enforce/enfLine/selectEnfLine.do?groupYn="+groupYn+"&opentype="+type+"&docId="+$('#docId').val(), 650, 550);
    }else{
    	appDoc = openWindow("enfLine", "<%=webUri%>/app/approval/ApprovalPreReader.do?groupYn="+groupYn, 650, 550);
    } 

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
	$.post("<%=webUri%>/app/enforce/reDistRequest.do", $('form').serialize(), function(data){
		//결과 페이지의 값을 받아 메세지 처리한다.
			if("1" == data.result ) {				
				alert("<spring:message code='approval.result.msg.redistrequestok'/>");
				afterButton();
			} else {
				alert("<spring:message code='approval.result.msg.redistrequestfail'/>");
			}	
	},'json');		
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

	goCertificateProcess("reDistributeProcess","<%=reDistributeBtn%>","N"); 

<% } %>
}

// 재배부
function reDistributeProcess() {

	//1. DB 작업(배부수신자 지정, 문서발송)
	$("#procType").val("<%=apt012%>"); //배부	
	$.post("<%=webUri%>/app/enforce/ProcessReDistDoc.do", $('form').serialize(), function(data){
		//결과 페이지의 값을 받아 메세지 처리한다.
		if("1" == data.result) {    
			alert("<spring:message code='approval.result.msg.distributeok'/>");
			afterButton();
		} else {
			alert("<spring:message code='approval.result.msg.distributefail'/>");
		}	
	},'json');	

}


//이송여부확인 의견 및 암호입력
function moveOk() {
	setTimeout(function(){popOpinion("moveProcess","<%=moveBtn%>","Y");}, 100);
	 
}
//이송처리
function moveProcess(popComment) {
	$("#comment").val(popComment);
	$("#procType").val("<%= apt006 %>");  // 이송
	// 1. DB 저장
	$.post("<%=webUri%>/app/enforce/moveSendDoc.do", $('form').serialize(), function(data){
		//결과 페이지의 값을 받아 메세지 처리한다.
			if("1" == data.result ) {  
				alert("<spring:message code='approval.result.msg.moveok'/>");
				afterButton();
			} else {
				alert("<spring:message code='approval.result.msg.movefail'/>");
			}	
	},'json');		
	
}

//반려 
function rejectDoc() {
	popOpinion("procRejectDoc", "<%=rejectDocBtn%>", "Y"); 
}


//문서회수 확인
function retrieveDoc() {
	<% if ("1".equals(opt301)) { %>		
	opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createPassword.do", 500, 160);
	<% } else if ("2".equals(opt301)) { %>
	$.post("<%=webUri%>/app/appcom/validateSignDate.do", "", function(data) {
		if (data.validation == "Y") {
			procRetrieveDoc();
			$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
		} else {
			if (certificate()) {
				procRetrieveDoc();
				$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
			}
		}
	}, 'json').error(function(data) {
		if (certificate()) {
			procRetrieveDoc();
			$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
		}
	});
	<% } else { %>
	procRetrieveDoc();
	<% } %>
}

<% if ("1".equals(opt301)) { %>		
function setPassword(password, roundkey) {
	if (typeof(password) != "undefined" && typeof(roundkey) != "undefined") {
		if (password != "") {
			$("#password", "#frmDocInfo").val(password);
			$("#roundkey", "#frmDocInfo").val(roundkey);
		}
	}
	procRetrieveDoc();
}
<% } %>

function afterButton(){	
	try {
		if(typeof(opener) !== "undefined" && typeof(opener.listRefreshCompulsion) !== "undefined"){
			if(opener.curLobCode != null && opener.curLobCode == '${lobCode}') {
			    opener.listRefreshCompulsion();
			}
		}
	} catch(e) {}
	<%if(!"N".equals(closeYn)){%>
	window.close();
	<%}%>
}

//의견 및 결재암호 팝업
function popOpinion(returnFunction, btnName, opinionYn) {

	var top = (screen.availHeight - 250) / 2;
	var left = (screen.availWidth - 500) / 2;
	var height = "height=240,";
	<% if (!"1".equals(opt301)) { %> // 암호입력아니면
		height = "height=200,";
	<% } %>  
    
	if(opinionYn=="N") {
		height = "height=140,";
		<% if (!"1".equals(opt301)) { %> // 암호입력아니면
		height = "height=170,";
		<% } %>  
	}	

	var width = ",width=500,";
	
    if(opinionYn=="N") {
    	width = ",width=400,";
    }

	popupWin = window.open("", "popupWin",
            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+ width + height +
	        "scrollbars=no,resizable=no"); 
    
	$("#returnFunction").val(returnFunction);
	$("#btnName").val(btnName);
	$("#opinionYn").val(opinionYn);
	$("#frm").attr("target", "popupWin");
	$("#frm").attr("action", "<%=webUri%>/app/enforce/popupOpinion.do");
	$("#frm").submit();
	popupWin.focus();
} 

//의견조회
//	이송 의견조회
function viewMoveOpinion() {
	 viewOpinion("<%=moveBtn%><%=msgOpinion%>", $("#moveOpinion").val());
}

//	재배부요청 의견조회
function viewReDistOpinion() {
	 viewOpinion("<%=reDistRequestBtn%><%=msgOpinion%>", $("#reDistOpinion").val());
}

//공람처리 수행
function pubreadAppDocProc(){
	if (confirm("<spring:message code='enforce.msg.pubreader'/>")) {
		$.post("<%=webUri%>/app/appcom/processPubReader.do", $("#frm").serialize(), function(data) {
			alert(data.message);
			
			if(opener !== null && typeof(opener.listRefresh) !== "undefined"){
				if(opener.curLobCode != null && opener.curLobCode == '${lobCode}') {	
				    opener.listRefresh();	
				}
			}
			
	<% if("Y".equals(opt357)){ %>
			window.close();		
	<% }else {%>
			setCancel();
	<% } %>
					
		}, 'json').error(function(data) {
			alert("<spring:message code='appcom.msg.fail.pubread'/>");
		});
	}
}

//다음문서 open
function goNextDoc(){
	moveToNext("Y");
}


<% if (ROLE_CODES.indexOf(role_appadmin) != -1) { %>
//문서관리로 보내기
function sendToDoc() {
	$.post("<%=webUri%>/app/approval/admin/sendToDoc.do", $("#frm").serialize(), function(data) {
		alert("<spring:message code='approval.msg.success.sendtodoc'/>");
	}, 'json').error(function(data) {
		alert("<spring:message code='approval.msg.fail.sendtodoc'/>");
	});
}

var docinfoWin = null;
function selectDocInfo() {
	var docId = $('#docId').val();
	
	docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/appcom/docHisInfo.do?docId="+docId, 700, 450, "yes");
}
<% } %>

var docinfoWin = null;
function selectDocInfo() {
	var docId = $('#docId').val();
	
	docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/appcom/docHisInfo.do?docId="+docId, 700, 450, "yes");
}

/**
 * 삭제버튼처리
 */
function deleteDocInfo()
{
	if(confirm("<spring:message code='approval.msg.noneledeletedoc'/>")){
		$.post("<%=webUri%>/app/approval/deleteNonEleEnfDoc.do", $("#frm").serialize(), function(data){
			alert("<spring:message code='approval.msg.deleted.nonele.doc'/>");
			window.close();
			//opener.listRefreshFromList();
			try {
				if(opener !== null && typeof(opener.listRefresh) !== "undefined"){
					if(opener.curLobCode != null && opener.curLobCode == '${lobCode}') {	
						opener.listRefreshFromList();
					}
				}
			} catch(e) {}
		}, 'json').error(function(data) {
			alert("<spring:message code='approval.msg.fail.delete.doc'/>");
		});
		
	}
}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
	
	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="pop_table05">
	<form id="frm" name="frm" method="post" onsubmit="return false;">
		<tr>
			<td colspan="2">
				<span class="pop_title77">
<% if(!"".equals(docId)){ %>
			<spring:message code="approval.title.enforce.noeledoc.select" />
<% }else{ %>			
			<spring:message code="approval.title.enforce.noeledoc.insert" />
<% } %>
			</span>
		</td>
<% if(LOB012.equals(lobCode) && !"N".equals(autoNextDocYn)){ %>						
		<td width="50%" align="right">
			<acube:buttonGroup align="right">
				<acube:button onclick="moveToPrevious();return(false);" value="<%=previousBtn%>" type="2" />
				<acube:space between="button" />
				<acube:button onclick="moveToNext();return(false);" value="<%=nextBtn%>" type="2" />
			</acube:buttonGroup>
		</td>
<% } %>
		</tr>
		</table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
		<td align="right">
			<c:if test='${lobCode == LOL002 && docInfo.docState != ENF600 && docId == ""}'>
		<div id="divAcess">
			<acube:buttonGroup align="right"><!-- 배부대장에서 호출 최초 입력 -->
		<c:if test="${!isExtWeb}">
			<acube:button id="sendOk" disabledid="" onclick="setEnf(1);" value="<%=insertBtn %>" type="2" />
			<acube:space between="button" />
		</c:if>
			<acube:button id="closeBtn" disabledid="" onclick="closePopup();" value="<%=closeBtn %>" type="2" />
			</acube:buttonGroup>
		</div>
			</c:if>	
			<c:if test='${(lobCode == LOB007 && docInfo.docState == ENF110) || lobCode == LOB019}'>
			<div id="divAcess">
			<acube:buttonGroup align="right"><!-- 재배부요청함에서 호출 -->
		<c:if test="${!isExtWeb}">
			<acube:button id="reDistributeBtn" disabledid="" onclick="reDistribute();" value="<%=reDistributeBtn %>" type="2" />
			<acube:space between="button" />
		</c:if>
			<acube:button id="closeBtn" disabledid="" onclick="closePopup();" value="<%=closeBtn %>" type="2" />
			</acube:buttonGroup>
			</div>
			</c:if>	
			
<!-- 담당접수 옵션처리 -->
			<c:if test='${(lobCode == LOL001 && docId == "")||((lobCode == LOB008)&& docInfo.docState != ENF600)}'>
			
			<c:if test='${OPT355.useYn == "N" && OPT356.useYn == "N"}' >
			<div id="divAcess" <c:if test='${docInfo.docState == ENF300}'>style="display:none;"</c:if>>
			<acube:buttonGroup align="right"><!-- 접수함 또는 등록 대장에서 호출 -->
			<c:if test="${!isExtWeb}">
			<c:if test='${docId != ""}'>
			<acube:button id="reDistRequestBtn" onclick="reDistRequest();" value="<%=reDistRequestBtn%>" type="2" /><!-- 재배부요청 -->
			<acube:space between="button" />
			</c:if>
			<%if("Y".equals(opt420)){%>
				<acube:button id="acceptBtn" onclick="setEnf(2);" value="<%=acceptBtn %>" type="2" /><!-- 접수 -->
				<acube:space between="button" />
			<%}%>
			<%-- <acube:button id="respaccBtn" disabledid="" onclick="setResAcc();" value="<%=respaccBtn %>" type="2" /><!-- 담당접수 -->
			<acube:space between="button" />			 --%>
			<c:if test='${docId != ""}'>
			<acube:button id="moveBtn" disabledid="" onclick="move();" value="<%=moveBtn %>" type="2" /><!-- 이송 -->
			<acube:space between="button" />
			</c:if>
			<c:if test='${lobCode == LOL001}'>
			<!--<acube:button id="prerespBtn" disabledid="" onclick="openEnfLine();" value="<%=prerespBtn %>" type="2" />--><!-- 결재 -->
			<!--<acube:space between="button" />-->
			</c:if>
			<c:if test='${OPT335.useYn == "Y"}'><!-- 공람자 -->
		<!-- <acube:button id="apppublicBtn" disabledid="" onclick="selectPubReader();" value="<%=apppublicBtn %>"  type="2" />
			<acube:space between="button" /> -->
			</c:if>
			</c:if>
			<acube:button id="closeBtn" disabledid="" onclick="closePopup();" value="<%=closeBtn %>" type="2" /><!-- 닫기 -->
			</acube:buttonGroup>
			</div>
			</c:if>
			
			<c:if test='${(OPT355.useYn == "Y" && OPT356.useYn == "N") || (OPT355.useYn == "Y" && OPT356.useYn == "Y")}' >
			<c:if test='${docInfo.docState == ENF200 || docInfo.docState == ENF250 || docId == ""}'>
			<div id="divAcess1" style="display:inline;">
			<acube:buttonGroup align="right"><!-- 접수함 또는 등록 대장에서 호출 -->
		<c:if test="${!isExtWeb}">
			<c:if test='${docId != ""}'>
			<acube:button id="reDistRequestBtn" onclick="reDistRequest();" value="<%=reDistRequestBtn%>" type="2" /><!-- 재배부요청 -->
			<acube:space between="button" />
			</c:if>
			<%if("Y".equals(opt420)){%>
				<acube:button id="acceptBtn" onclick="setEnf(2);" value="<%=acceptBtn %>" type="2" /><!-- 접수 -->
				<acube:space between="button" />
			<%}%>
			<c:if test='${docId != ""}'>
			<acube:button id="moveBtn" disabledid="" onclick="move();" value="<%=moveBtn %>" type="2" /><!-- 이송 -->
			<acube:space between="button" />
			</c:if>
			<c:if test='${lobCode == LOL001}'><!-- 결재 -->
			<!--<acube:button id="prerespBtn" disabledid="" onclick="openEnfLine();" value="<%=prerespBtn %>" type="2" />
			<acube:space between="button" />-->
			</c:if>
			<c:if test='${OPT335.useYn == "Y"}'><!-- 공람자 -->
		<!-- <acube:button id="apppublicBtn" disabledid="" onclick="selectPubReader();" value="<%=apppublicBtn %>"  type="2" />
			<acube:space between="button" /> -->
			</c:if>
		</c:if>
			<acube:button id="closeBtn" disabledid="" onclick="closePopup();" value="<%=closeBtn %>" type="2" /><!-- 닫기 -->
			</acube:buttonGroup>
			</div>	
			</c:if>		
			</c:if>
			
			<c:if test='${(OPT355.useYn == "N" && OPT356.useYn == "Y") || (OPT355.useYn == "Y" && OPT356.useYn == "Y")}' >
			<div id="divAcess2" style="display:none;">
			<acube:buttonGroup align="right"><!-- 접수함 또는 등록 대장에서 호출 -->
		<c:if test="${!isExtWeb}">
			<c:if test='${docId != ""}'>
			<acube:button id="reDistRequestBtn" onclick="reDistRequest();" value="<%=reDistRequestBtn%>" type="2" /><!-- 재배부요청 -->
			<acube:space between="button" />
			</c:if>
			<acube:button id="respaccBtn" disabledid="" onclick="setResAcc();" value="<%=respaccBtn %>" type="2" /><!-- 담당접수 -->
			<acube:space between="button" />			
			<c:if test='${docId != ""}'>
			<acube:button id="moveBtn" disabledid="" onclick="move();" value="<%=moveBtn %>" type="2" /><!-- 이송 -->
			<acube:space between="button" />
			</c:if>
			<c:if test='${OPT335.useYn == "Y"}'><!-- 공람자 -->
		<!-- <acube:button id="apppublicBtn" disabledid="" onclick="selectPubReader();" value="<%=apppublicBtn %>"  type="2" />
			<acube:space between="button" /> -->
			</c:if>
		</c:if>
			<acube:button id="closeBtn" disabledid="" onclick="closePopup();" value="<%=closeBtn %>" type="2" /><!-- 닫기 -->
			</acube:buttonGroup>
			</div>
			</c:if>

			<c:if test='${(OPT355.useYn == "N" && OPT356.useYn == "Y") || (OPT355.useYn == "Y" && OPT356.useYn == "N")}' >
			<div id="divAcess3" style="display:<c:if test='${(OPT355.useYn == "N" && OPT356.useYn == "Y")}'>inline;</c:if><c:if test='${(OPT355.useYn == "Y" && OPT356.useYn == "N")}'>none;</c:if>">
			<acube:buttonGroup align="right"><!-- 접수함 또는 등록 대장에서 호출 -->
		<c:if test="${!isExtWeb}">
			<c:if test='${docId != ""}'>
			<acube:button id="reDistRequestBtn" onclick="reDistRequest();" value="<%=reDistRequestBtn%>" type="2" /><!-- 재배부요청 -->
			<acube:space between="button" />
			</c:if>		
			<c:if test='${docId != ""}'>
			<acube:button id="moveBtn" disabledid="" onclick="move();" value="<%=moveBtn %>" type="2" /><!-- 이송 -->
			<acube:space between="button" />
			</c:if>
			<c:if test='${lobCode == LOL001}'>
			<acube:button id="prerespBtn" disabledid="" onclick="messageType = 3; openEnfLine();" value="<%=prerespBtn %>" type="2" /><!-- 결재 -->
			<acube:space between="button" />
			</c:if>
			<c:if test='${OPT335.useYn == "Y"}'><!-- 공람자 -->
		<!-- <acube:button id="apppublicBtn" disabledid="" onclick="selectPubReader();" value="<%=apppublicBtn %>"  type="2" />
			<acube:space between="button" /> -->
			</c:if>
		</c:if>
			<acube:button id="closeBtn" disabledid="" onclick="closePopup();" value="<%=closeBtn %>" type="2" /><!-- 닫기 --> 
			</acube:buttonGroup>
			</div>
			</c:if>
				
			</c:if>
<!-- 담당접수 옵션처리 -->
			
			<c:if test="${lobCode == LOB003&& docInfo.docState != ENF600}">
			<div id="divAcess">
			<acube:buttonGroup align="right"><!-- 결재 대기함(선람담당지정) -->
		<c:if test="${!isExtWeb}">
			<c:choose> 
			<c:when test='${docInfo.enfLine.askType==ART060}'>
			<acube:button id="art60Btn" disabledid="" onclick="setProcess(10);" value="<%=art60Btn %>" type="2" />
			<acube:space between="button" />
			
			<acube:button id="prerespBtn" disabledid="" onclick="messageType = 3; openEnfLine();" value="<%=prerespBtn %>" type="2" />
			<acube:space between="button" />
					
			</c:when>
			<c:when test='${docInfo.enfLine.askType==ART070}'>
			<acube:button id="art70Btn" disabledid="" onclick="setProcess(20);" value="<%=art70Btn %>" type="2" />
			<acube:space between="button" />
			
			<acube:button id="prerespBtn" disabledid="" onclick="messageType = 3; openEnfLine();" value="<%=prerespBtn %>" type="2" />
			<acube:space between="button" />
			
			<acube:button id="btnReturnBtn" onclick="javascript:rejectDoc();return(false);" value="<%=rejectDocBtn%>" type="2" />
			<acube:space between="button" />
			</c:when>
			<c:otherwise>
			<c:if test='${procYn == "Y"}'><!-- 결재대기함에서 선람담당지정 -->
			<acube:button id="processBtn" disabledid="" onclick="messageType = 3; setProcess(30);" value="<%=prerespBtn %>" type="2" />
			<acube:space between="button" />
				<c:if test='${docInfo.docState == ENF300 || docInfo.docState == ENF310}'>
					<c:if test='${OPT355.useYn == "N" && docInfo.serialNumber != "0" }'>
						<acube:button id="respaccBtn" disabledid="" onclick="setResAcc();" value="<%=approalprocessdocBtn %>" type="2" /><!-- 담당처리 -->
						<acube:space between="button" />
					</c:if>						
				</c:if>
			</c:if>
			</c:otherwise>
			</c:choose>
			<%if(!isExtWeb){ %>
			<c:choose>
			<c:when test='${OPT335.useYn == "Y" && procYn == "Y"}'><!-- 공람자 -->
			<acube:button id="apppublicBtn" disabledid="" onclick="selectPubReader();" value="<%=apppublicBtn %>"  type="2" />
			<acube:space between="button" />
			</c:when>
			<c:otherwise>
			<acube:button id="apppublicBtn" disabledid="" onclick="listPubReader();" value="<%=apppublicBtn %>"  type="2" />
			<acube:space between="button" />
			</c:otherwise>
			</c:choose>
			<% } %>
		</c:if>
			<!-- 결재라인일때도 문서수정 가능함 -->
<%if(  ((LOL001.equals(lobCode) || LOL003.equals(lobCode) )&& userId.equals(result.getRegisterId()) && "N".equals(bindingYn)) //작성자수정권한 
				|| ((LOL001.equals(lobCode) || LOL003.equals(lobCode) ) && ROLE_CODES.indexOf(role_doccharger) != -1  && "N".equals(bindingYn)) //문서과 문서담당자 수정권한
				|| (LOB099.equals(lobCode) && ROLE_CODES.indexOf(role_appadmin) != -1) //관리자는 관리자 목록에서 만 수정 가능
			){  %>
			<c:if test='${(docInfo.transferYn != "Y" && docInfo.docState == ENF600) || lobCode == LOB099}' ><!-- 이관문서 수정금지, 완료문서만 수정 -->
			<acube:button id="selectDocInfo" disabledid="" onclick="selectDocInfo();" value="<%=docHisBtn %>"  type="2" />
			<acube:space between="button" />
			<acube:button id="sendModify" disabledid="" onclick="modifyPopup();" value="<%=updateBtn %>" type="2" />
			<acube:space between="button" />
			<acube:button id="deleteDocInfo" disabledid="" onclick="deleteDocInfo();" value="<%=deleteBtn%>"  type="2" />
			<acube:space between="button" />
			</c:if>
<%} %>
			<!-- 결재라인일때도 문서수정 가능함 -->
			<acube:button id="closeBtn" disabledid="" onclick="closePopup();" value="<%=closeBtn %>" type="2" />
			</acube:buttonGroup>
			</div>
			</c:if>	<!-- 아래 문서 수정 및 닫기 정보 -->
			<c:if test='${docInfo.docState == ENF600||(lobCode == LOL002 && docId != "")|| (lobCode == LOL003 && docId != "") ||(lobCode == LOL001 && docId != "")
							||(lobCode == LOB004 && docId != "")|| (lobCode == LOB012 && docId != "") || (lobCode == LOB011 && docId != "") || (lobCode == LOB099)  }'>
			<acube:buttonGroup align="right">
			<%if(LOB099.equals(lobCode) && ROLE_CODES.indexOf(role_appadmin) != -1) //관리자는 관리자 목록에서 만 수정 가능
			{ %>
			<c:if test='${docInfo.docState == ENF600}'>
			<!-- added by jkkim 문서관리로 보내기 주석처리함 차후 변경예정 2012.03.29 -->
			<!--<acube:button id="sendToDoc" disabledid="" onclick="sendToDoc();" value="<%=sendToDocBtn %>"  type="2" />
			<acube:space between="button" />-->
			</c:if>
			<%} %>
			<%if(  ((LOL001.equals(lobCode) || LOL003.equals(lobCode) )&& userId.equals(result.getRegisterId()) && "N".equals(bindingYn)) //작성자수정권한 
				|| ((LOL001.equals(lobCode) || LOL003.equals(lobCode) ) && ROLE_CODES.indexOf(role_doccharger) != -1 && "N".equals(bindingYn) ) //문서과 문서담당자 수정권한
				|| (LOB099.equals(lobCode) && ROLE_CODES.indexOf(role_appadmin) != -1) //관리자는 관리자 목록에서 만 수정 가능
			){  %>
			<c:if test='${(docInfo.transferYn != "Y" && docInfo.docState == ENF600) || lobCode == LOB099}' ><!-- 이관문서 수정금지, 완료문서만 수정 -->
			<acube:button id="selectDocInfo" disabledid="" onclick="selectDocInfo();" value="<%=docHisBtn %>"  type="2" />
			<acube:space between="button" />
			<acube:button id="sendModify" disabledid="" onclick="modifyPopup();" value="<%=updateBtn %>" type="2" />
			<acube:space between="button" />
			<acube:button id="deleteDocInfo" disabledid="" onclick="deleteDocInfo();" value="<%=deleteBtn%>"  type="2" />
			<acube:space between="button" />
			</c:if>
			<%} %>
			<c:if test='${lobCode == LOB004 && docInfo.docState != ENF600 && retvYn == "Y"}'><!-- 회수 -->
           	<acube:button id="btnRetrieveDoc" onclick="javascript:retrieveDoc();return(false);" value="<%=retrievedocBtn%>" type="2" />
            <acube:space between="button" />
			</c:if>
			<%if(!isExtWeb){ %>
			<c:if test='${lobCode == LOB012 && pubReadYn == "N"}'>			
			<acube:button id="pubread" disabledid="" onclick="pubreadAppDoc();" value="<%=pubread%>"  type="2" />
			<acube:space between="button" />
			</c:if>
			<% } %>
			<c:if test='${lobCode == LOB011 && reEnfLinYn == "Y"}'> <!-- 담당자 재지정 -->
			<acube:button id="processorRefix" disabledid="" onclick="processorRefix();" value="<%=processorRefix %>"  type="2" />
			<acube:space between="button" />
			</c:if>
			<%if(!isExtWeb){ %>
			<c:if test='${OPT335.useYn == "Y"}'><!-- 공람자 -->
			<acube:button id="apppublicBtn" disabledid="" onclick="listPubReader();" value="<%=apppublicBtn %>"  type="2" />
			<acube:space between="button" />
			</c:if>
			<% } %>
			<acube:button id="closeBtn" disabledid="" onclick="closePopup();" value="<%=closeBtn %>" type="2" />
			</acube:buttonGroup>
			</c:if>
			<div id="divCloseBtn" <c:choose><c:when test='${(docInfo.docState == ENF310 || docInfo.docState == ENF300) && lobCode == LOB008}'>style="display:inline;"</c:when><c:otherwise>style="display:none;"</c:otherwise></c:choose>>
			<acube:buttonGroup align="right">
			<c:if test='${((docInfo.docState == ENF200 || docInfo.docState == ENF250 ||docInfo.docState == ENF310 || docInfo.docState == ENF300) && lobCode == LOB008) || (docId == "" && lobCode == LOL001)}' >
			<acube:button id="processBtn" disabledid="" onclick="messageType = 3; setProcess(30);" value="<%=prerespBtn %>" type="2" />
			<acube:space between="button" />
			<%if(!isExtWeb){ %>
			<c:if test='${OPT335.useYn == "Y" && ((docId == "" && lobCode == LOL001)||lobCode == LOB008)}'><!-- 공람자 -->
			<acube:button id="apppublicBtn" disabledid="" onclick="selectPubReader();" value="<%=apppublicBtn %>"  type="2" />
			<acube:space between="button" />
			</c:if>
			<% } %>
			</c:if>
			<acube:button id="closeBtn" disabledid="" onclick="closePopup();" value="<%=closeBtn %>" type="2" />
			</acube:buttonGroup>
			</div>
			<c:choose>
				<c:when test='${lobCode ==  LOB008 && (docInfo.docState == ENF400 || docInfo.docState == ENF500)}'>
				<div id="divCloseBtn2" style="display:inline;">
				</c:when>
				<c:otherwise>
				<div id="divCloseBtn2" style="display:none;">
				</c:otherwise>
			</c:choose>
			
			<acube:buttonGroup align="right">
			<acube:button id="closeBtn" disabledid="" onclick="closePopup();" value="<%=closeBtn %>" type="2" />
			</acube:buttonGroup>
			</div>
						
			
		</td>
		</tr>
		</table>
		
		<!-- 문서정보 -->
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td width=65%>
					<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.docinfo" /></acube:titleBar>
				</td>
				<c:choose>
				<c:when test='${(docInfo == null && (lobCode == LOL001 || lobCode == LOL002)) || (docInfo.docState == ENF110 && lobCode == LOB007) || lobCode == LOB019 || 
				((docInfo.docState == ENF200 || docInfo.docState == ENF250) && lobCode == LOB008) ||
				((docInfo.docState == ENF300 || docInfo.docState == ENF310) && lobCode == LOB003) ||
				 (procYn == "Y" &&  lobCode == LOB003) || (reEnfLinYn == "Y" &&  lobCode == LOB011) }'>
				
				<c:if test='${OPT316.useYn == "2" || OPT316.useYn == "3"}'>
				<td align="right"><!-- 공람게시 -->		
				<table border="0" cellpadding="0" cellspacing="0" >
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
							<div id="divPublicPost" style="display: none;font-size: 9pt;">
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
				</c:when>
				<c:otherwise>
				<c:if test='${docInfo.publicPost != null && docInfo.publicPost != ""}'>
				<c:if test='${OPT316.useYn == "1" || OPT316.useYn == "3"}'>
				<td align="right"><!-- 공람게시 -->				
				<table border="0" cellpadding="0" cellspacing="0">
					<tr bgcolor="#ffffff" >
						<td style="font-size: 9pt;" align="right" width="5">
						<input type="checkbox" id="publicPostYn" name="publicPostYn" value="Y" checked="checked" disabled="disabled"/>
						</td>
						<td style="font-size: 9pt;" width="50">
						<spring:message code='approval.form.publicpost'/>
						</td>
						<c:if test='${OPT361.useYn == "1"}'>
						<td style="width: 130;" align="right">
							<c:choose>
								<c:when test='${docInfo.publicPost == DRS002}'>
								<input class="input" type="text" id="publicPostNm" name="publicPost" value='<spring:message code="approval.form.readrange.drs002" />' style="width:115" disabled="disabled"/>
								</c:when>
								<c:when test='${docInfo.publicPost == DRS003}'>
								<input class="input" type="text" id="publicPostNm" name="publicPost" value='<spring:message code="approval.form.readrange.drs003" />' style="width:115" disabled="disabled"/>
								</c:when>
								<c:when test='${docInfo.publicPost == DRS004}'>
								<input class="input" type="text" id="publicPostNm" name="publicPost" value='<spring:message code="approval.form.readrange.drs004" />' style="width:115" disabled="disabled"/>
								</c:when>
								<c:otherwise>
								<input class="input" type="text" id="publicPostNm" name="publicPost" value='<spring:message code="approval.form.readrange.drs005" />' style="width:115" disabled="disabled"/>
								</c:otherwise>
							</c:choose>
							
						</td>
						</c:if>
					</tr>
				</table>
				</td>
				</c:if>
				</c:if>
				</c:otherwise>
			</c:choose>
			</tr>
		</table>
		
		<acube:tableFrame class="td_table borderBottom borderRight">
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.title" /><c:if test='${docId == ""}'><spring:message code='common.title.essentiality'/></c:if></td><!-- 제목 -->
				<td class="tb_left_bg" colspan="3" >
				<c:if test='${docId == ""}'>			
				<input id="title" name="title" type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" value="<%=title %>"/>
				</c:if>
				<c:if test='${docId != ""}'>
				<%=(result.getTitle() == null)? "": EscapeUtil.escapeHtmlDisp(result.getTitle()) %><input id="title" name="title" type="hidden" value="" />
				</c:if>
				</td>
			</tr>
			<c:if test='${lobCode != LOL002 && lobCode != LOB007 && lobCode != LOB019}'>		
				<c:choose>
					<c:when test='${(OPT423 == "Y" && lobCode == LOB008) ||(OPT423 == "Y" && docId == "") ||(OPT423 == "Y" && docId != "" && (docInfo.bindingName != null && docInfo.bindingName != "")) ||(OPT423 == "N" && docId != "" && (docInfo.bindingName != null && docInfo.bindingName != ""))}'>
						<tr bgcolor="#ffffff" >
							<td class="tb_tit" ><spring:message code="approval.form.records" /><c:if test='${docInfo.bindingName == null || docInfo.bindingName == ""}'><spring:message code='common.title.essentiality'/></c:if></td><!-- 편철 -->
							<td class="tb_left_bg" colspan="3" >
							<c:if test='${(docId == "" || lobCode == LOB008) && (docInfo.bindingName == null || docInfo.bindingName == "") }'>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>				
								<td width="96%">
									<input id="bindNm"  name="bindNm" type="text" class="input_read" readonly="readonly" style="width: 100%" />
								</td>					
								<td>&nbsp;</td><td><div id="divBind"><acube:button onclick="goBind();return(false);" value="<%=bindBtn%>" type="4" class="gr" /></div></td>
								
							</tr>
							</table>					
							</c:if>
							<c:if test='${(docId != "" && lobCode != LOB008 )|| (docInfo.bindingName != null && docInfo.bindingName != "")}'>
							${docInfo.bindingName}<input id="bindNm" name="bindNm" type="hidden" value=""/>
							</c:if>
							</td>
						</tr>					
					</c:when>
					<c:otherwise>					
						<tr bgcolor="#ffffff" >
							<td class="tb_tit" ><spring:message code="bind.obj.retention.period" /></td><!-- 보존기간 -->
							<td class="tb_left_bg" colspan="3" >
							<c:if test='${(docId == "" || lobCode == LOB008) && (docInfo.conserveType == null || docInfo.conserveType == "") }'>
								<table border="0" cellspacing="1" cellpadding="1">
									<tr>
										<td><form:select id="retentionPeriod" name="retentionPeriod" path="retentionPeriod"  items="${retentionPeriod}" /></td>
									</tr>
								</table>				
							</c:if>
							<c:if test='${(docId != "" && lobCode != LOB008 )|| (docInfo.conserveType != null && docInfo.conserveType != "")}'>
								<table border="0" cellspacing="1" cellpadding="1">
									<tr>
										<td><form:select id="retentionPeriod" name="retentionPeriod" path="retentionPeriod"  items="${retentionPeriod}" disabled="true"/></td>
									</tr>
								</table>
							</c:if>
							</td>
						</tr>					
					</c:otherwise>
				</c:choose>
			</c:if>
			
			<c:if test='${lobCode == LOB008 || (docInfo.docState != ENF200 && docInfo.docState != ENF250 && docInfo.docState != ENF110 && docInfo.serialNumber != "0") && (lobCode == LOL002 && docId == "") == false }'><!-- 접수등록번호 -->
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%" style="height: 28px;"><spring:message code="approval.form.enforce.serialnumber" /></td><!-- 접수 등록번호 -->
				<td  class="tb_left_bg" colspan="3" style="margin: 0px;padding: 0px">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
					<td class="tb_left tb_last tr_last">
					<div id="divSerialNum" style="float: left; width:100%;height:100%;margin: 0px;padding: 0px;font-size: 9pt;">
						<c:if test='${docId != "" && docInfo.serialNumber != "0" && docInfo.serialNumber != "" }'>
						<span id="divSerialValue" style="float: left; width:90%;height:100%;margin-top:4px;"><c:out value="${docInfo.deptCategory}" />-<c:out value="${docInfo.serialNumber}" /><c:if test='${OPT310.useYn == "Y" && docInfo.subserialNumber != "0"}'>-<c:out value="${docInfo.subserialNumber}" /></c:if></span>
						</c:if>
						<c:if test='${docId == "" || docInfo.serialNumber == "" || docInfo.docState == ENF200}'>
						<div id="divSerialValue" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:3pt; vertical-align:bottom;">
							${OrgAbbrName}
						</div>
						</c:if>
					</div>
					</td>
					<c:if test='${(docId == "" && lobCode==LOL001) || (docInfo.docState == ENF200 || docInfo.docState == ENF250)}'>					
					<td class="tb_left tb_last tr_last" width="60">
					<div id="divProSerial">
						<c:if test='${OPT310.useYn == "Y"}'><a href="javascript:goProSerial();"><img id="imgProSerial" src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></c:if>
						<a href="javascript:delProSerial();"><img id="imgClose" src='<%=webUri%>/app/ref/image/bu5_close.gif' border="0"></a>
					</div>
					</td>
					<c:choose>
					<c:when test='${OPT358.useYn == "1"}' >
					<input id="noSerialYn" name="noSerialYn" type="checkbox"  value="Y" style="display:none;" checked />
					</c:when>
					<c:otherwise>
					<td class="tb_left tb_last tr_last" width="70" style="font-size: 9pt;">						
					<input id="noSerialYn" name="noSerialYn" type="checkbox"  value="Y" checked /><span id="noSerialYnNm"><spring:message code="approval.form.nosericalnum" /></span>
					</td>
					</c:otherwise>
					</c:choose>
					</c:if>
					</tr>
					</table><input id="DeptCategory" name="DeptCategory" type="hidden" value="${OrgAbbrName}" /><input id="SerialNum" name="SerialNum" type="hidden" value="0" /><input id="SubserialNumber" name="SubserialNumber" type="hidden" value="0" />
				</td>
			</tr>
			</c:if>
			
			<c:if test='${lobCode != LOL002 && lobCode != LOB007 && lobCode != LOB019}'>
				<c:if test='${(lobCode == LOB008 && OPT422 == "Y") ||(OPT422 == "Y" && docId == "") || (OPT422 == "Y" && docId != "" && (docInfo.classNumber !=null && docInfo.classNumber !="")) || (OPT422 == "N" && docId != "" && (docInfo.classNumber !=null && docInfo.classNumber !=""))}'>
					<tr bgcolor="#ffffff" >
						<td class="tb_tit" width="18%" style="height: 28px;"><spring:message code="approval.form.docKind" /><c:if test='${(docId == "") || (lobCode == LOB008 && OPT422 == "Y")}'><spring:message code='common.title.essentiality'/></c:if></td><!-- 문서분류 -->
						<td class="tb_left_bg" colspan="3">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="96%">
										<div id="divDocKind" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:3pt; vertical-align:bottom;"></div>
									</td>
									<c:if test='${docId == "" || (lobCode == LOB008 && OPT422 == "Y")}'>
									<td align="left"><acube:button onclick="goDocKind();return(false);" value="<%=docKindBtn%>" type="4" class="gr" /></td>
									<td width="5">&nbsp;</td>
									<td align="left"><acube:button onclick="docKindInit();return(false);" value="<%=docKindInitBtn%>" type="4" class="gr" /></td>
									</c:if>
								</tr>
							</table>
						</td>
					</tr>
				</c:if>
			</c:if>
			<%-- 접수 문서 시 관련문서 사용안하도록 변경, jd.park, 20120612
			<c:if test='${OPT321.useYn == "Y"}'>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit" ><spring:message code="approval.form.reldoc" /></td><!-- 관련문서 -->
				<td class="tb_left_bg" colspan="3" width="100%" height="60">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
						<td width='97%'>
							<div style="width: 100%; height:50px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF; float: left;" >
								<table id="tbRelDoc" width="95%" border="0" cellpadding="0" cellspacing="0">
								<tbody />
								</table>
							</div>
						</td>
						<c:if test='${docId == ""}'>
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
				<c:if test='${docId == ""}'>
				<textarea id="summary" name="summary"  rows="3" cols="10" style="width: 100%;ime-mode:active;word-break:break-all;"></textarea>
				</c:if>
				<c:if test='${docId != ""}'>
				<div id="divSummary" style="width: 100%;height: 40px;overflow-y:auto;word-break:break-all;"><%= (result.getNonElectron() == null) ? "" : EscapeUtil.escapeHtmlDisp(result.getNonElectron().getSummary()) %></div>
				<textarea id="summary" name="summary"  rows="3" cols="10" style="display:none;position: absolute;word-break:break-all;"><c:out value='${docInfo.nonElectron.summary}' /></textarea>
				</c:if>
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit"><spring:message code="approval.form.attach" /><c:if test='${docId =="" && OPT359.useYn == "Y"}' ><spring:message code='common.title.essentiality'/></c:if></td><!-- 첨부파일 -->
				<td class="tb_left_bg" colspan="3" height="60">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td width="100%">
							<div id="divattach" style="background-color:#ffffff;border:0px solid;height:50px;width:100%;overflow:auto;">
							</div>
						</td>
						<td>
						<c:if test='${docId == ""}'>
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
						</c:if>
						<c:if test='${(docId != "") && (!isExtWeb)}'>
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
						</td>
				</tr>
				</table>
				</td>
			</tr>
		</acube:tableFrame>
	
		<c:if test='${lineSize > 0  || lobCode == LOB003 || lobCode == LOL001 || lobCode == LOB008}'>	
		<!-- 경로정보 -->
		<acube:space between="button_content" table="y"/>
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.lineinfo" /></acube:titleBar>
		<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td>
				<table id="tbEnfLines" width="100%" border="0" cellpadding="0" cellspacing="0" class="td_table borderBottom borderRight">
					<thead>
					<tr bgcolor="#ffffff" >
						<td  width="10%" class="tb_tit"  rowspan="2" style="vertical-align: middle;"><CENTER><spring:message code="approval.table.title.gubun" /></CENTER></td>
						<td  class="tb_tit" colspan="6" ><CENTER><spring:message code="approval.form.enforce.enforg" /></CENTER></td>
					</tr>
		
					<tr bgcolor="#ffffff" >
						<td class="tb_tit" width="15%"><center><spring:message code="approval.form.readrange.drs002" /></center></td>
						<td class="tb_tit" width="13%"><center><spring:message code="approval.form.position" /></center></td>
						<td class="tb_tit" width="10%"><center><spring:message code="approval.form.name" /></center></td>
						<td class="tb_tit" width="10%"><center><spring:message code="approval.form.enforce.proresult" /></center></td>
						<td class="tb_tit" width="12%"><center><spring:message code="approval.form.processdate" /></center></td>					
						<td class="tb_tit"><center><spring:message code="approval.title.opinion" /></center></td>
					</tr>
					</thead>
					<tbody></tbody>
				</table><input id="lineopinion" name="lineopinion" type="hidden" />
				</td>
			</tr>	
		</acube:tableFrame>
		</c:if>	
		<acube:space between="button_content" table="y"/>
		<!-- 시행정보 -->
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.procinfo" /></acube:titleBar>
		<acube:tableFrame class="td_table borderBottom borderRight">
			<tr><!-- 발신기관명 -->
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.enforce.sendorgname" /><c:if test='${docId == ""}'><spring:message code='common.title.essentiality'/></c:if></td>
				<td  class="tb_left_bg" width="32%">
					<c:if test='${docId == ""}'>
					<input id="sendOrgName" name="sendOrgName" type="text" class="input" maxlength="256" style="width:99%;ime-mode:active;" value="<%=sendOrgName %>" />
					</c:if>
					<c:if test='${docId != ""}'>
					<%=(result.getSenderDeptName() == null)?"":EscapeUtil.escapeHtmlDisp(result.getSenderDeptName()) %><input id="sendOrgName" name="sendOrgName" type="hidden" value=""/>
					</c:if>
				</td><!-- 생산등록번호 -->
				<td class="tb_tit" width="18%"><spring:message code="approval.form.proregnum" /><c:if test='${docId == ""}'><spring:message code='common.title.essentiality'/></c:if></td>
				<td class="tb_left_bg">
					<c:if test='${docId == ""}'>
					<input id="docNumber" name="docNumber" type="text" class="input" maxlength="256" style="width:99%;ime-mode:active;"  value="<%=docNumber %>" />
					</c:if>
					<c:if test='${docId != ""}'>
					<%=(result.getDocNumber() == null)?"":EscapeUtil.escapeHtmlDisp(result.getDocNumber()) %><input id="docNumber" name="docNumber" type="hidden" value="" />
					</c:if>
				</td>
			</tr>
			<c:if test='${lobCode != LOB008 && ((lobCode == LOL002 && docId == "") || docInfo.docState == ENF200 || docInfo.docState == ENF250 || docInfo.docState == ENF110)}'>
			<tr>
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.enforce.recvdept" /><c:if test='${docId == ""}'><spring:message code='common.title.essentiality'/></c:if></td>
				<td  class="tb_left_bg" width="32%"><!-- 접수부서 -->
					<c:if test='${docId == ""}'>
					<table width="99%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="90%">
							<input id="refDeptName" name="refDeptName" type="text" class="input_read" maxlength="256" style="width:99%" readonly="readonly" />
							</td>
							<td>&nbsp;</td>
							<td><span id="divRefDept" ><a href="javascript:goRecvDept();"><img src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></span></td>
						</tr>
					</table>						
					</c:if>
					<c:if test='${docId != ""}'>
					<div id="divRefDeptName"><c:out value='${docInfo.receiverInfos[0].refDeptName}' /></div><input id="refDeptName" name="refDeptName" type="hidden" value="${docInfo.receiverInfos[0].refDeptName}" />
					</c:if>
					<input id="refDeptId" name="refDeptId" type="hidden" value="${docInfo.receiverInfos[0].refDeptId}" />	
				</td>
				<td  class="tb_tit" >&nbsp;</td>
				<td  class="tb_left_bg" >&nbsp;</td>
			</tr>
			</c:if>
			<tr>
				<td  class="tb_tit" ><spring:message code="approval.form.enforceange" /></td><!-- 시행범위 -->
				<td class="tb_left_bg" style="vertical-align: middle;">
				<div style="float: left;">
				<c:if test='${docId == ""}'>
				<select id="enfType" name="enfType"  style="width: 200px;">
					<c:if test='${lobCode != LOL002}'>
					<option value="<%=appCode.getProperty("DET002", "DET002", "DET") %>"><spring:message code="approval.form.det002" /></option><!-- 대내 -->
					</c:if>
					<option value="<%=appCode.getProperty("DET003", "DET003", "DET") %>"><spring:message code="approval.form.det003" /></option><!-- 대외 -->
					<option value="<%=appCode.getProperty("DET004", "DET003", "DET") %>"><spring:message code="approval.form.det004" /></option><!-- 대내외 -->
				</select>
				</c:if>
				<c:if test='${docId != ""}'>
					<div id="divEnfTypeNm"></div><input id="enfTypeNm" name="enfTypeNm" type="hidden" value="" /><input id="enfType" name="enfType" type="hidden" value=""></input><input id="recvEnfType" name="recvEnfType" type="hidden" value=""></input>
				</c:if>
				</div>
				</td>
				<td  class="tb_tit" width="150"><spring:message code="approval.form.enforcedate" /></td><!-- 시행일자 -->
				<td class="tb_left_bg" >
				<c:if test='${docId == ""}'>
					<input id="enforceDate" name="enforceDate" type="text" class="input_read" maxlength="256" style="width: 90%"  value="<%=startDate %>"  readonly="readonly" />
					<img id="calProcess" name="calProcess"
				        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
				        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
				        onclick="javascript:cal.select(event, document.getElementById('enforceDateId'), document.getElementById('enforceDate'), 'calProcess','<%= dateFormat %>');">
				</c:if>
				<c:if test='${docId != ""}'>
				<div id="divEnforceDate"></div><input id="enforceDate" name="enforceDate" type="hidden" value="" />
				</c:if>
				</td>
			</tr>
		</acube:tableFrame>
			
		<!-- 관리정보 -->
		<acube:space between="button_content" table="y"/>
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.mgrinfo" /></acube:titleBar>
		<acube:tableFrame class="td_table borderBottom borderRight">
			<tr>
				<td class="tb_tit" width="18%"><spring:message code="approval.from.docclsdiv" /></td><!-- 문서분류구분 -->
				<td class="tb_left_bg" width="32%">
				<c:if test='${docId == ""}'>
				<select id="Categoris" name="Categoris" style="width:99%">
				<c:forEach items='${Categoris}' var='category'>
					<option value="${category.categoryId}">${category.categoryName}</option>
				</c:forEach>
				</select>
				</c:if>
				<c:if test='${docId != ""}'>
				${docInfo.categoryName}<input id="Categoris" name="Categoris" type="hidden" value="${docInfo.docType}" />
				</c:if>
				</td>
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.readrange" /></td><!-- 열람범위 -->
				<td  class="tb_left_bg" >
				<c:if test='${docId == ""}'>
				<c:choose>
					<c:when test='${OPT361.useYn == "1"}'>
					<select id="readRange" name="readRange" style="width:99%">
						<option value='<%=appCode.getProperty("DRS001", "DRS001", "DRS") %>'><spring:message code="approval.form.readrange.drs001" /></option>
						<option value='<%=appCode.getProperty("DRS002", "DRS002", "DRS") %>' selected="selected"><spring:message code="approval.form.readrange.drs002" /></option>
						<% if(!"".equals(headOfficeId)) {    // jth8172 2012 신결재 TF%>
						<option value='<%=appCode.getProperty("DRS003", "DRS003", "DRS") %>'><spring:message code="approval.form.readrange.drs003" /></option>
						<% } %>
						<% if(!"".equals(institutionId)) {   // jth8172 2012 신결재 TF %>
						<option value='<%=appCode.getProperty("DRS004", "DRS004", "DRS") %>'><spring:message code="approval.form.readrange.drs004" /></option>
						<% } %>
						<option value='<%=appCode.getProperty("DRS005", "DRS005", "DRS") %>'><spring:message code="approval.form.readrange.drs005" /></option>
					</select>
					</c:when>
					<c:otherwise>
					<spring:message code="approval.form.drs002" /><input id="readRange" name="readRange" type="hidden" value="<%=appCode.getProperty("DRS002", "DRS002", "DRS") %>" />
					</c:otherwise>
				</c:choose>
				</c:if>
				<c:if test='${docId != ""}'>
				<div id="divReadRangeNm"></div><input input id="readRangeNm" name="readRangeNm"  type="hidden" value="" /><input id="readRange" name="readRange" type="hidden" value="" />
				</c:if>
				</td>
			</tr>
			<tr>
				<td class="tb_tit" ><spring:message code="approval.from.regdiv" /></td><!-- 등록구분 -->
				<td class="tb_left_bg" style="vertical-align: middle;">
				<c:if test='${docId == ""}'>
					<select id="apDty" name="apDty" style="width:99%">
						<option value='<%=appCode.getProperty("DTY001", "DTY001", "DTY") %>' selected="selected"><spring:message code="approval.form.dty001" /></option>
						<option value='<%=appCode.getProperty("DTY002", "DTY002", "DTY") %>'><spring:message code="approval.form.dty002" /></option>
						<option value='<%=appCode.getProperty("DTY003", "DTY003", "DTY") %>'><spring:message code="approval.form.dty003" /></option>
						<option value='<%=appCode.getProperty("DTY004", "DTY004", "DTY") %>'><spring:message code="approval.form.dty004" /></option>
						<option value='<%=appCode.getProperty("DTY005", "DTY005", "DTY") %>'><spring:message code="approval.form.dty005" /></option>
					</select>
				</c:if>
				<c:if test='${docId != ""}'>
				<div id="divApDtyNm"></div><input input id="apDtyNm" name="apDtyNm" type="hidden" value="" /><input id="apDty" name="apDty" type="hidden" value="" />
				</c:if>
				</td>
				<td class="tb_tit" >&nbsp;</td>
				<td class="tb_left_bg">
				</td>
			</tr>
			<tr>
				<td class="tb_tit" ><spring:message code="approval.from.spcrec" /></td><!-- 특수기록물 -->
				<td class="tb_left_bg"  colspan="3">
				<c:if test='${docId == ""}'>
				<input id="specialReca" name="specialReca" group="specialRec" type="checkbox" value="1" /><spring:message code="approval.from.spcreca" />&nbsp;
				<input id="specialRecb" name="specialRecb" group="specialRec" type="checkbox" value="2" /><spring:message code="approval.from.spcrecb" />&nbsp;
				<input id="specialRecc" name="specialRecc" group="specialRec" type="checkbox" value="3" /><spring:message code="approval.from.spcrecc" />&nbsp;
				<input id="specialRecd" name="specialRecd" group="specialRec" type="checkbox" value="4" /><spring:message code="approval.from.spcrecd" />&nbsp;<br />
				<input id="specialRece" name="specialRece" group="specialRec" type="checkbox" value="5" /><spring:message code="approval.from.spcrece" />
				</c:if>
				<c:if test='${docId != ""}'>
				<div id="divSpecialRec"></div>
				</c:if>
				</td>
			</tr>
		</acube:tableFrame>
		<div id="div_rectype" style="display: none;">
		<acube:space between="button_content" table="y"/>
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.addregrec" /></acube:titleBar>
		<acube:tableFrame class="table_grow">
			<tr>
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.dty.summary" /></td><!-- 내용요약 -->
				<td  class="tb_left_bg"  colspan="3" height="60">
				<c:if test='${docId == ""}'>
				<textarea id="recSummary" name="recSummary"  rows="4" cols="10" style="width: 100%;word-break:break-all;"></textarea>
				</c:if>
				<c:if test='${docId != ""}'>
				<div id="divRecSummary" style="width: 100%;height: 50px;overflow-y:auto;word-break:break-all;"><%= (result.getNonElectron() == null) ? "" : EscapeUtil.escapeHtmlDisp(result.getNonElectron().getRecSummary()) %></div>
				<textarea id="recSummary" name="recSummary"  rows="4" cols="10" style="display:none;position: absolute;word-break:break-all;"><%= (result.getNonElectron() == null) ? "" : EscapeUtil.escapeHtmlDisp(result.getNonElectron().getRecSummary()) %></textarea>
				</c:if>
				</td>
			</tr>
			
			<tr>
				<td  width="150" class="tb_tit" ><spring:message code="approval.form.dty.rectype" /></td><!-- 기록물형태 -->
				<td  class="tb_left_bg" colspan="3">
				<c:if test='${docId == ""}'>
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
				</c:if>
				<c:if test='${docId != ""}'>
				<div id="divRectype">
				</div>	
				</c:if>
				</td>
			</tr>
		</acube:tableFrame>
		</div>
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		<div id="approvalitem1" style="position: absolute;display: none;">
		<!-- hidden -->
		<input id="refrash" name="refrash" type="hidden" value="N" />
		<input id="bindId" name="bindId" type="hidden"  /><!-- 편철 -->
		<input id="relDoc" name="relDoc" type="hidden"  /><!-- 관련문서 -->
		<input id="enfTarget" name="enfTarget" type="hidden" value="" /><!-- 시행범위 기타 -->
		<input id="enforceDateId" name="enforceDateId" type="hidden" value="<%=startDateId %>"  /><!-- 수신일자 -->
		<input id="openLevel" name="openLevel" type="hidden" value="1NNNNNNNN" /><!-- 정보공개 --> 
		<input id="openReason" name="openReason" type="hidden" value="" /><!-- 정보공개사유 --> 
		<input id="specialRec" name="specialRec" type="hidden" value="NNNNN" /><!-- 특수기록물 --> 
		<input id="rectype" name="rectype" type="hidden" value="" /><!-- 기록물형태 --> 
		<input id ="enfLines" name="enfLines" type="hidden" value="${enfLines}"/><!-- 담당/선람결제라인 -->
		<input id="pubReader" name="pubReader" type="hidden" value="${pubReader}" />
		<input id ="askType" name="askType" type="hidden" value=""/><!-- 요청타입 -->
		<input id ="opinion" name="opinion" type="hidden" value=""/><!-- 결재의견 -->
		<input id="attachFile" name="attachFile" type="hidden" value="<%=sAttach %>" />
		<input id="method" name="method" type="hidden" value="2" />
		<input id="lobCode" name="lobCode" type="hidden" value="${lobCode}" />
		<input id="docId" name="docId" type="hidden" value="${docId}" />
		<input id="procType" name="procType" type="hidden" value="" />	
		<input id="comment" name="comment" type="hidden" value="" />
		<input type="hidden" id="returnFunction" name="returnFunction" value="" />
    	<input type="hidden" id="btnName" name="btnName" value="" />
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
	    <input id="popupTitle" name="popupTitle" type="hidden" value="" />
	    <input id="popupOpinion" name="popupOpinion" type="hidden" value="" />
	   	<!-- 발송, 이송, 재배부요청 의견 -->
	    <input type="hidden" id="sendOpinion" name="sendOpinion" value="<%=sendOpinion%>"/><br/>
	    <input type="hidden" id="moveOpinion" name="moveOpinion" value="<%=moveOpinion%>"/><br/>
	    <input type="hidden" id="reDistOpinion" name="reDistOpinion" value="<%=reDistOpinion%>"/><br/>
	    <input id="conserveType" name="conserveType" type="hidden" value="" /> <!-- 보존연한 -->
	    <c:if test='${lobCode != LOL002 && lobCode != LOB007 && lobCode != LOB019}'><!-- 접수부서 이송시 사용 -->
	    <input id="refDeptName" name="refDeptName" type="hidden" value=""  />
	    <input id="refDeptId" name="refDeptId" type="hidden" value="" />
	    </c:if>
	    
	    <!-- 문서분류 -->
	    <input type="hidden" name="classNumber" id="classNumber" value=""/>
		<input type="hidden" name="docnumName" id="docnumName" value="" />
		
		<input id="autoNextDocYn" name="autoNextDocYn" type="hidden" value="<%=autoNextDocYn%>"/>
		
		<input id="reason" name="reason" type="hidden" value=""/>
		<input id="password" name="password" type="hidden" value=""/>
		<input id="roundkey" name="roundkey" type="hidden" value=""/>

		</div>
		<!-- 편철 다국어 추가 -->
		<input type="hidden" name="bindingResourceId" id="bindingResourceId" value="<%= bindingResourceName %>"/>				
	</form>
	</acube:outerFrame>
<jsp:include page="/app/jsp/common/adminform.jsp" />
	
<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>
<% }else{ %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.select.approval'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
<% if (!"".equals(msg)) { %>	
	alert("<%=messageSource.getMessage(msg, null, langType)%>");
<% } %>
	window.close();
}
</script>
</head>
<body></body>
</html>
<% } %>