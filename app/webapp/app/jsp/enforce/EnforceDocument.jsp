<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="
java.util.Locale,
java.util.List,
java.util.ArrayList,
com.sds.acube.app.enforce.vo.EnfDocVO,
com.sds.acube.app.approval.vo.AppDocVO,
com.sds.acube.app.approval.vo.AppOptionVO,
com.sds.acube.app.approval.vo.AppLineVO,
com.sds.acube.app.approval.vo.RelatedDocVO,
com.sds.acube.app.approval.vo.RelatedRuleVO,
com.sds.acube.app.approval.vo.CustomerVO,
com.sds.acube.app.appcom.vo.SendInfoVO,
com.sds.acube.app.appcom.vo.OwnDeptVO,
com.sds.acube.app.appcom.vo.EnfProcVO,
com.sds.acube.app.enforce.vo.EnfRecvVO,
com.sds.acube.app.appcom.vo.PubReaderVO,
com.sds.acube.app.appcom.vo.FileVO,
com.sds.acube.app.common.util.AppTransUtil,
com.sds.acube.app.common.util.DateUtil,
org.anyframe.util.StringUtil,
com.sds.acube.app.common.util.UtilRequest,
com.sds.acube.app.env.vo.FormVO,
com.sds.acube.app.env.vo.FormEnvVO,
com.sds.acube.app.relay.vo.PackInfoVO,
com.sds.acube.app.relay.vo.LineInfoVO
" %>
<%
/** 
 *  Class Name  : EnforceDocument.jsp 
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
	String ProcVOs = (String) request.getAttribute("recvOpinion");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String compName = (String) session.getAttribute("COMP_NAME"); // 사용자 소속 회사명
	
    String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // 본문(HWP)
    String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT");
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
	String aft005 = appCode.getProperty("AFT005", "AFT005", "AFT"); // 관인
	String aft006 = appCode.getProperty("AFT006", "AFT006", "AFT"); // 서명인
	String aft011 = appCode.getProperty("AFT011", "AFT011", "AFT"); // 기관유통문서 본문(XML)
    
    String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
    String apt005 = appCode.getProperty("APT005", "APT005", "APT"); // 접수
    String apt006 = appCode.getProperty("APT006", "APT006", "APT"); // 이송
    String apt007 = appCode.getProperty("APT007", "APT007", "APT"); // 반송
    String apt012 = appCode.getProperty("APT012", "APT012", "APT"); // 배부
    String apt013 = appCode.getProperty("APT013", "APT013", "APT"); // 재배부요청
    String apt018 = appCode.getProperty("APT018", "APT018", "APT"); // 배부안함
    String apt019 = appCode.getProperty("APT019", "APT019", "APT"); // 배부회수
    String apt020 = appCode.getProperty("APT020", "APT020", "APT"); // 재발송요청

    String dct497 = AppConfig.getProperty("form497", "DCT497", "formcode");
	String dct498 = AppConfig.getProperty("form498", "DCT498", "formcode");
	String dct499 = AppConfig.getProperty("form499", "DCT499", "formcode");
   
    String det002 = appCode.getProperty("DET002", "DET002", "DET"); // 대내
    String det003 = appCode.getProperty("DET003", "DET003", "DET"); // 대외
    String det007 = appCode.getProperty("DET007", "DET007", "DET"); // 민원인
    String det011 = appCode.getProperty("DET011", "DET011", "DET"); // 대외유통

    String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
    String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수
    
    String dru001 = appCode.getProperty("DRU001", "DRU001", "DRU"); // 부서
    String dru002 = appCode.getProperty("DRU002", "DRU002", "DRU"); // 사람

	String enf100 = appCode.getProperty("ENF100", "ENF100", "ENF"); // 배부 대기
	String enf110 = appCode.getProperty("ENF110", "ENF110", "ENF"); // 재배부요청
	String enf200 = appCode.getProperty("ENF200", "ENF200", "ENF"); // 접수 대기(부서)
	String enf250 = appCode.getProperty("ENF250", "ENF250", "ENF"); // 배부 대기(사람)
	String enf300 = appCode.getProperty("ENF300", "ENF300", "ENF"); // 선람 및 담당 지정 대기
    String enf310 = appCode.getProperty("ENF310", "ENF310", "ENF"); // 선람 및 담당 지정 대기(반려)
    String enf400 = appCode.getProperty("ENF400", "ENF400", "ENF"); // 선람대기
    String enf500 = appCode.getProperty("ENF500", "ENF500", "ENF"); // 담당대기
    String enf600 = appCode.getProperty("ENF600", "ENF600", "ENF"); // 완료문서
    
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
	String lob099 = appCode.getProperty("LOB099", "LOB099", "LOB");	// 관리자전체목록  
    String lob031 = appCode.getProperty("LOB031", "LOB031", "LOB"); // 공람게시 
    String lob091 = appCode.getProperty("LOB091", "LOB091", "LOB"); // 접수대기함(관리자)
	String lob092 = appCode.getProperty("LOB092", "LOB092", "LOB"); // 배부대기함(관리자)
	
	/*접수단계에서는 관련문서 사용안함, jd.park, 20120612
	String lob093 = appCode.getProperty("LOB093", "LOB093", "LOB"); // 관련문서목록
	*/

	String lol001 = appCode.getProperty("LOL001", "LOL001", "LOL");	// 등록대장
	String lol002 = appCode.getProperty("LOL002", "LOL002", "LOL");	// 문서배부대장
	String lol003 = appCode.getProperty("LOL003", "LOL003", "LOL");	// 미등록대장

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
    
    boolean isWithdraw = (Boolean)request.getAttribute("isWithdraw");
    boolean isEnfLineChange = (Boolean)request.getAttribute("isEnfLineChange");
    
    // 배부문서 회수 가능여부
    boolean isEnableDistributeWithdraw = (Boolean)request.getAttribute("isEnableDistributeWithdraw");

    // 배부대장에서 반송이나 재배부요청 가능여부
    boolean isEnableReturnOnDist = (Boolean)request.getAttribute("isEnableReturnOnDist");

    // 접수문서
	EnfDocVO enfDocVO = (EnfDocVO) request.getAttribute("enfDocInfo");
	if (enfDocVO == null ) {
	    enfDocVO = new EnfDocVO();  
	}
	// 소유부서정보를 가져와서 현재 소유부서를 가려낸다  jth8172 20110821
	String ownDeptId = "";
	List<OwnDeptVO> ownDeptVOs = enfDocVO.getOwnDepts();
	int deptSize =  ownDeptVOs.size();
	OwnDeptVO ownDeptVO = null;
	for (int deptCnt=0; deptCnt<deptSize; deptCnt++) {
	    ownDeptVO = ownDeptVOs.get(deptCnt);
	    if("Y".equals(ownDeptVO.getOwnYn()) ) {
			ownDeptId = ownDeptVO.getOwnDeptId(); // 실제소유부서
	    }
	}
	
	EnfRecvVO enfRecvVO = (EnfRecvVO) request.getAttribute("enfRecvInfo");
	if(enfRecvVO == null ) {
	    enfRecvVO = new EnfRecvVO();  
	}
	List<EnfRecvVO> enfRecvVOs = new ArrayList<EnfRecvVO>();
	if (!("").equals(enfRecvVO.getDocId())) {
		enfRecvVOs.add(enfRecvVO);
	}
	
	// 원생산문서정보
	AppDocVO appDocVO = (AppDocVO) request.getAttribute("appDocVO");
	if ( appDocVO == null ) {
		appDocVO = new AppDocVO();
	}
	String docType = appDocVO.getDocType();
	SendInfoVO sendInfoVO = appDocVO.getSendInfoVO();
	if ( sendInfoVO == null ) {
		sendInfoVO = new SendInfoVO();
	}	
	List<AppLineVO> appLineVOs = appDocVO.getAppLine();  // 결재라인

	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	String opt303 = appCode.getProperty("OPT303", "OPT303", "OPT"); // 부서협조 - 1 : 최종협조자, 2 : 모든협조자
	opt303 = envOptionAPIService.selectOptionValue(compId, opt303);
	String opt304 = appCode.getProperty("OPT304", "OPT304", "OPT"); // 감사표시 - 1 : 결재라인, 2 : 협조라인, 3 : 감사라인	
	opt304 = envOptionAPIService.selectOptionValue(compId, opt304);
	String opt322 = appCode.getProperty("OPT322", "OPT322", "OPT"); // PDF파일 저장권한 - 1 : 문서과 문서관리책임자, 2 : 모든사용자
	opt322 = envOptionAPIService.selectOptionValue(compId, opt322);
    String opt335 = appCode.getProperty("OPT335", "OPT335", "OPT"); // 접수문서 공람사용여부
	opt335 = envOptionAPIService.selectOptionValue(compId, opt335); 
	String opt343 = appCode.getProperty("OPT343", "OPT343", "OPT"); // html
	opt343 = envOptionAPIService.selectOptionValue(compId, opt343);
	String opt351 = appCode.getProperty("OPT351", "OPT351", "OPT"); // 수신자 재발송 사용여부
	opt351 = envOptionAPIService.selectOptionValue(compId, opt351);
	String opt355 = appCode.getProperty("OPT355", "OPT355", "OPT"); //  채번문서 담당접수 사용불가  20110808
	opt355 = envOptionAPIService.selectOptionValue(compId, opt355); 
	String opt356 = appCode.getProperty("OPT356", "OPT356", "OPT"); //  미채번문서 접수경로 사용불가  20110808
	opt356 = envOptionAPIService.selectOptionValue(compId, opt356); 
	String opt357 = appCode.getProperty("OPT357", "OPT357", "OPT"); //  결재 처리 후 문서 자동닫기  20110808
	opt357 = envOptionAPIService.selectOptionValue(compId, opt357); 
	String opt371 = appCode.getProperty("OPT371", "OPT371", "OPT"); //  접수절차 간소화 사용 여부
	opt371 = envOptionAPIService.selectOptionValue(compId, opt371); 
	String opt401 = appCode.getProperty("OPT401", "OPT401", "OPT"); // 접수전 이송기능 사용 여부 2012.03.20 by jkkim
	opt401 = envOptionAPIService.selectOptionValue(compId, opt401);
	String opt402 = appCode.getProperty("OPT402", "OPT402", "OPT"); // 접수후 이송기능 사용 여부 2012.03.20 by jkkim
	opt402 = envOptionAPIService.selectOptionValue(compId, opt402);
	String opt403 = appCode.getProperty("OPT403", "OPT403", "OPT"); // 접수후 경유기능 사용 여부 2012.03.20 by jkkim
	opt403 = envOptionAPIService.selectOptionValue(compId, opt403);
	String opt427 = appCode.getProperty("OPT427", "OPT427", "OPT"); // 다중배부 사용 여부 2012.06.25 by redcomet
	opt427 = envOptionAPIService.selectOptionValue(compId, opt427);
	
	/* 접수단계에서는 관련문서 사용안함, jd.park, 20120612
	String opt321 = appCode.getProperty("OPT321", "OPT321", "OPT"); // 관련문서 사용유무, jd.park, 20120504
	opt321 = envOptionAPIService.selectOptionValue(compId, opt321);
	*/
	
	String roleCode = (String) session.getAttribute("ROLE_CODES"); // 역할코드
	String roleId12 = AppConfig.getProperty("role_cordoccharger","","role"); //문서과 문서 담당자
    String roleId11 = AppConfig.getProperty("role_doccharger","","role"); //부서 문서책임자
    boolean  deptDocManager = (roleCode.indexOf(roleId11) == -1) ? false : true; 
    
	boolean docManagerFlag = (roleCode.indexOf(roleId12) == -1) ? false : true; 
   
	String sendOpinion = (String) request.getAttribute("sendOpinion"); //발송의견
	String reDistOpinion = (String) request.getAttribute("reDistOpinion");  //재배부요청의견
	String moveOpinion = (String) request.getAttribute("moveOpinion");  //이송의견
	 
	boolean isMainDistribute = (Boolean) request.getAttribute("isMainDistribute");  //주 배부문서 여부

	FileVO bodyVO = (FileVO) request.getAttribute("bodyfile");
	if(bodyVO == null ) {
	    bodyVO = new FileVO();  
	}	
	
	// 본문문서 타입 설정
	String strBodyType = "hwp";
	strBodyType = CommonUtil.getFileExtentsion(bodyVO.getFileName());
	
	List<FileVO> fileVOs = (List<FileVO>) request.getAttribute("enfFileInfo");
	if(fileVOs == null ) {
	    fileVOs = new ArrayList<FileVO>();
	}
	int idxNum = enfDocVO.getDocNumber().indexOf("-");  // -1 이면 발번대상문서
	
	String isNum = "Y";  // 발번대상여부
	if(!(idxNum >0)) {
	    isNum = "N";
	}
	
	String transferYn = enfDocVO.getTransferYn(); // 컨버젼후 이관된 문서여부
	String docState = enfDocVO.getDocState(); // 문서상태
	String title = enfDocVO.getTitle();
	String originCompId = StringUtil.null2str(enfDocVO.getOriginCompId());
	
	String recvState = enfRecvVO.getRecvState(); // 수신상태

	String deleteBtn = messageSource.getMessage("approval.button.delete", null, langType); // 삭제
	String msgOpinion =  messageSource.getMessage("approval.enforce.opinion", null, langType); //의견
	String docinfoBtn = messageSource.getMessage("approval.button.docinfo", null, langType); //문서정보
	String distributeBtn = messageSource.getMessage("approval.enforce.button.distribute", null, langType); //배부
	String reDistributeBtn = messageSource.getMessage("approval.enforce.button.redistribute", null, langType); //재배부
	String reDistRequestBtn = messageSource.getMessage("approval.enforce.button.redistrequest", null, langType); //재배부요청
	String noDistributeBtn = messageSource.getMessage("approval.enforce.button.nodistribute", null, langType); //배부안함
	String appendDistributeBtn = messageSource.getMessage("approval.enforce.button.appenddistribute", null, langType); //추가배부
	String distributeWithdrawBtn = messageSource.getMessage("approval.enforce.button.distributewithdraw", null, langType); //배부회수
	String acceptBtn = messageSource.getMessage("approval.enforce.button.accept", null, langType); 
	String accapprovalBtn = messageSource.getMessage("approval.enforce.button.acceptapproval", null, langType); 
	String sendOpinionBtn = messageSource.getMessage("approval.enforce.button.sendopinion", null, langType);
	String reDistOpinionBtn = messageSource.getMessage("approval.enforce.button.redistopinion", null, langType); 
	String moveBtn = messageSource.getMessage("approval.enforce.button.move", null, langType); 
	String viaBtn = messageSource.getMessage("approval.enforce.button.via", null, langType); 
	String returnBtn = messageSource.getMessage("approval.enforce.button.return", null, langType); 
	String reSendBtn = messageSource.getMessage("approval.enforce.button.resendrequest", null, langType); //재발송요청
	String processorfixBtn = messageSource.getMessage("enforce.button.processorfix", null, langType); 
    String processorRefixBtn = messageSource.getMessage("enforce.button.processorRefix", null, langType); 
	String preopenBtn = messageSource.getMessage("enforce.button.preopen", null, langType); //선람
	String pubreadBtn = messageSource.getMessage("enforce.button.pubreader", null, langType); //공람
	String retrievedocBtn = messageSource.getMessage("enforce.button.retrievedoc", null, langType); //회수
	String saveBtn = messageSource.getMessage("approval.button.save", null, langType); //저장
	String saveHwpBtn = messageSource.getMessage("approval.button.savehwp", null, langType); // 본문저장	
	String savePdfBtn = messageSource.getMessage("approval.button.savepdf", null, langType);//pdf저장
	String saveAllBtn = messageSource.getMessage("approval.button.saveall", null, langType); // 본문/첨부 저장
	String sendMailBtn = messageSource.getMessage("approval.button.sendmail", null, langType); // 사내우편
	String printBtn = messageSource.getMessage("approval.button.print", null, langType); //인쇄
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); //닫기
	String sendInfoBtn = messageSource.getMessage("approval.enforce.button.sendinfo", null, langType);
	String procInfoBtn = messageSource.getMessage("approval.enforce.button.procinfo", null, langType);
    String pubreaderBtn = messageSource.getMessage("approval.button.pubreader", null, langType);//공람자
	
	String appendBtn = messageSource.getMessage("approval.button.append", null, langType); 
	String removeBtn = messageSource.getMessage("approval.button.remove", null, langType); 
	String upBtn = messageSource.getMessage("approval.button.up", null, langType); 
	String downBtn = messageSource.getMessage("approval.button.down", null, langType); 
 //   String procBtn = messageSource.getMessage("approval.enforce.button.procOpinion", null, langType); 

	String transMsg = messageSource.getMessage("enforce.tranfer.error.process", null, langType); //미접수이관
	
	String approaldocBtn = messageSource.getMessage("enforce.button.approaldoc", null, langType); //담당확인
	String rejectDocBtn = messageSource.getMessage("enforce.button.rejectdoc", null, langType); //반려(담당자재지정요청)
	String approaldocMsg =  messageSource.getMessage("approval.result.msg.approvalok", null, langType); //담당확인 메세지
	String rejectDocMsg =  messageSource.getMessage("enforce.msg.rejectdocok", null, langType); //담당자재지정요청 메세지
	String approalprocessdocBtn = messageSource.getMessage("enforce.button.approalprocessdoc", null, langType); //담당처리
	String previousBtn = messageSource.getMessage("approval.button.previous.doc", null, langType); // 이전문서
	String nextBtn = messageSource.getMessage("approval.button.next.doc", null, langType); // 다음문서
	String applineBtn = messageSource.getMessage("approval.button.appline", null, langType); // 결재경로
	
	// 이송의견(발송의견이 있으면 함께 보여준다.)
	if (!"".equals(moveOpinion) && !"".equals(sendOpinion)) {
	    moveOpinion = moveOpinion + "\r\n\r\n-- " + sendOpinionBtn + " --\r\n" + sendOpinion; 
	}

    //공람자리스트
    
    List pubReaderVOs = (List)request.getAttribute("pubReaderVOs");
    if(pubReaderVOs ==null) {
/////		pubReaderVOs = new List();    요기
    }    
    
    String  publishId = StringUtil.null2str(request.getParameter("publishId")); // 게시ID
    
    
    String procOpinion = StringUtil.null2str((String)request.getAttribute("procOpinion"));  // 결재처리의견
    String procAskType = StringUtil.null2str((String)request.getAttribute("procAskType"));  // 결재처리유형
    String strProcAskType =  messageSource.getMessage("approval.title.enforce." + procAskType, null, langType); //선람,담당
    
    FormVO formVO = (FormVO) request.getAttribute("formVO");
    FileVO logoEnvVO = (FileVO) request.getAttribute("logo");
	FileVO symbolEnvVO = (FileVO) request.getAttribute("symbol");
	List<FileVO> signFileVOs = (List<FileVO>) request.getAttribute("sign");
	PackInfoVO packInfoVO = (PackInfoVO) request.getAttribute("packInfoVO");
	FileVO sealVO = (FileVO) request.getAttribute("seal");

	String autoNextDocYn  = CommonUtil.nullTrim(request.getParameter("autoNextDocYn")); // 다음문서 자동 오픈
	
	//대외기관유통문서인 경우 한글문서로 strBodyType 설정 (T:배부대기, N:접수쪽에서 직접 접수대기 처리, lob008 : 문서함-접수대기함, det011 : 시행유형-행정기관))
    if((("T".equals(enfDocVO.getDistributeYn()) ||("N".equals(enfDocVO.getDistributeYn()) &&
			 lob008.equals(lobCode))))&& det011.equals(enfRecvVO.getEnfType())) {
    	strBodyType = "hwp";
    }
    
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
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<%if(strBodyType.equals("hwp")){ %>
<jsp:include page="/app/jsp/common/hwpmanager.jsp" />
<%}else if(strBodyType.equals("doc")){ %>
<jsp:include page="/app/jsp/common/wordmanager.jsp" />
<%}else if(strBodyType.equals("html")){ %>
<jsp:include page="/app/jsp/common/htmlmanager.jsp" />
<%}%>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />

<jsp:include page="/app/jsp/common/adminmanager.jsp">
	<jsp:param name="formBodyType" value="<%= strBodyType %>" />
</jsp:include>

<script type="text/javascript">
	var saveBindingName = "";
	var popupWin;
    var linePopupWin;//결재라인
    var opinionWin; //사유입력창
    var docinfoWin; //문서정보창
    var attachWin; //첨부정보창
    var bindWin; //기록물철
    var chargerPopupWin;//공람자팝업
    
    <%-- 접수단계에서는 관련문서 사용안함, jd.park, 20120612
    var relatedDocWin = null;  //관련문서정보창     
    var docLinkedWin = null; //관련문서창
    --%>
    var mailWin = null;

    var retrycount = 0;  //파일 업로드시 재시도 카운트 (기본 5)
    var pubReaderSaveYn = "N"; //공람자팝업후 저장여부
        
    var directAccept ="N"; //담당접수여부
    var docInfoAccept ="N"; //접수시문서정보입력여부
    var enfDepts; //접수부서
    var enfLines; //접수경로
    var g_lineOpenType; //결재라인 오픈시 type(I:접수후  결재라인 생성시)
    var bodyfilepath; //분문파일위치
    var reDistMoveYn = "N";  //재배부(Y,N),이송(M),추가배부(A)
    var afterPubReaderYn = "N"; // 담당자 지정 후 공람자 창 팝업 여부
    //공문서 유통관련 양식추가
    var hwpFormFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/EnforceForm11.hwp";
    var logopath = "";
    var symbolpath = "";
    var signpath = new Array();
    var signlist = new Array();
    var sealpath = "";  
    var g_erroloc = "";
    var bodyType = "<%=strBodyType%>";//added by jkkim 2013.04.24

    $(document).ready(function(){ init(); });    
	$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });	
	$.ajaxSetup({async:false});

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
		document.getElementById("divhwp").style.height = (document.body.offsetHeight - 190+25);
		// 파일 ActiveX 초기화
		initializeFileManager();
		var bodyfile = new Object();
		bodyfile.fileid = "<%=bodyVO.getFileId()%>";
		bodyfile.filename = "<%=bodyVO.getFileName()%>";
		bodyfile.displayname = "<%=EscapeUtil.escapeJavaScript(bodyVO.getDisplayName())%>";
		bodyfile.size = "<%=bodyVO.getFileSize()%>";
		bodyfilepath = FileManager.savebodyfile(bodyfile);
		// 한글 ActiveX 초기화
		initEditorForRecp();// added by jkkim 편집기 초기화 작업 2013.04.24
		// Hwp 양식파일
		
		if (bodyType == "html") {
			showHtmlBodyContent();
		}
	<%
	    //T:배부대기, Y:배부처리, N:접수쪽에서 직접 접수대기 처리  || "Y".equals(enfDocVO.getDistributeYn())
		if((("T".equals(enfDocVO.getDistributeYn()) ||("N".equals(enfDocVO.getDistributeYn()) && lobCode.equals(lob008))))&& det011.equals(enfRecvVO.getEnfType()))
		{
	%>
			var fileCount = 0;
			var fileCount2 = 0;
			var filelist = new Array();
		<% if 	(formVO != null) { %>
			var formFile = new Object();
			formFile.filename = "<%=formVO.getFormFileName()%>";
			formFile.displayname = "<%=formVO.getFormFileName()%>";
	//		hwpFormFile = FileManager.savebodyfile(formFile);
			filelist[fileCount++] = formFile;
		<% }else{ %>
				alert("<spring:message code='approval.enforce.msg.setpubdocform'/>");
				window.close();
				return;
		<%}%>	
		<% if 	(logoEnvVO != null) { %>
			var logo = new Object();
			logo.filename = "<%=logoEnvVO.getFileName()%>";
			logo.displayname = "<%=logoEnvVO.getFileName()%>";
	//		logopath = FileManager.savebodyfile(logo);
			filelist[fileCount++] = logo;
		<% } %>	
		<% if 	(symbolEnvVO != null) { %>
			var symbol = new Object();
			symbol.filename = "<%=symbolEnvVO.getFileName()%>";
			symbol.displayname = "<%=symbolEnvVO.getFileName()%>";
	//		symbolpath = FileManager.savebodyfile(symbol);
			filelist[fileCount++] = symbol;
		<% } %>	
		<% if 	(signFileVOs != null) {
		 for(int cnt = 0 ; cnt < signFileVOs.size(); cnt++ ){  
		     FileVO signFileVO = signFileVOs.get(cnt);
		 %>
			var sign = new Object();
			sign.filename = "<%=CommonUtil.nullTrim(signFileVO.getFileName())%>";
			sign.displayname = "<%=CommonUtil.nullTrim(signFileVO.getDisplayName())%>";
	//		signpath = FileManager.savebodyfile(sign);
			filelist[fileCount++] = sign;
			signlist[fileCount2++] = sign;
		<%
		 		}
			} 
		%>
		<% if 	(sealVO != null) { %>
			var seal = new Object();
			seal.filename = "<%=sealVO.getFileName()%>";
			seal.displayname = "<%=sealVO.getFileName()%>";
	//		symbolpath = FileManager.savebodyfile(symbol);
			filelist[fileCount++] = seal;
		<% } %>		
			if (filelist.length > 0) {
				var resultlist = FileManager.savefilelist(filelist);
				var result = resultlist.split(String.fromCharCode(31));
				var resultcount = 1;
		<% if 	(formVO != null) { %>
				hwpFormFile = result[resultcount++];
		<% } %>	
		<% if 	(logoEnvVO != null) { %>
				logopath = result[resultcount++];
		<% } %>	
		<% if 	(symbolEnvVO != null) { %>
				symbolpath = result[resultcount++];
		<% } %>	
		<% if 	(signFileVOs != null) { 
				for(int sCnt = 0; sCnt < signFileVOs.size() ; sCnt++){
		%>
				signpath[<%=sCnt%>] = result[resultcount++];
		<% 
				    }
				} 
		
		%>	
		<% if 	(sealVO != null) { %>
				sealpath = result[resultcount++];
		<% } %>	
			}

    		openHwpDocument(Document_HwpCtrl, hwpFormFile);//대외유통문서 폼을 오픈.
    		initEditorAfter();//added by jkkim 2013.04.24 job after editor loading
    		var pubfilename =bodyfile.filename;
			var pubfile = FileManager.getlocaltempdir() + pubfilename;
			var isInsertedBody = insertPubDocument(Document_HwpCtrl,pubfile,HwpConst.Form.Content);//xml에서 한글 본문으로 변환처리
			if(!isInsertedBody){
				alert("<spring:message code='approval.enforce.error.bodytranserror'/>");
				return;
			}
			var isInsertEnvFile = initDocumentEnv(Document_HwpCtrl);//기관문서에 logo, symbole, 헤더, 풋, 직인 넣는 로직
			if(!isInsertEnvFile){
				alert(g_erroloc+"<spring:message code='approval.enforce.error.envfiletranserror'/>");
				return;
			}

			// Hwp 본문으로 파일 만들기 by jkkim
			var filename = "";
			if(bodyType == "hwp"){
				filename = "HwpBody_" + UUID.generate() + ".hwp";
			}else if(bodyType == "doc"){
				filename = "DocBody_" + UUID.generate() + ".doc";
			}
			bodyfilepath = FileManager.getlocaltempdir() + filename;
			saveHwpDocument(Document_HwpCtrl, bodyfilepath, false);
	<%
		}else{
	%>
		openHwpDocument(Document_HwpCtrl, bodyfilepath);	
		initEditorAfter();//added by jkkim 2013.04.24 job after editor loading
		// 본문복구
		// 본문복구 관련해서 생산문서 DOCID를 이용해서 본문을 복구하므로 기관문서는 생산에 DOID가 없으므로 
		// 본문복구 처리하지 않음 으로수정함. 이전에는 else 밖에 있었음..2012.06.15
		if (!isExistDocument(Document_HwpCtrl)) {

		   $.post("<%=webUri%>/app/approval/selectBodyHis.do", "docId=<%=enfDocVO.getOriginDocId()%>", function(data) {
				if (data.result == "success") {
					if (data.bodypath != "") {
						var bodyHis = new Object();
						bodyHis.filename = data.bodypath;
						bodyHis.displayname = data.bodypath;
						bodyfilepath = FileManager.savebodyfile(bodyHis);
						openHwpDocument(Document_HwpCtrl, bodyfilepath);
				
						// 결재경로
						var appline = $("#app_appLine", "#approvalitem").val();
						if (appline != "") {
							var assistantlinetype = $("#assistantLineType", "#approvalitem").val();
							var auditlinetype = $("#auditLineType", "#approvalitem").val();
						<% 	if ("Y".equals(appDocVO.getDoubleYn())) { %>
							resetApprover(Document_HwpCtrl, getApproverUser(appline, "<%=originCompId%>"), 2, "<%=docType%>", assistantlinetype, auditlinetype);
						<% 	} else { %>
							resetApprover(Document_HwpCtrl, getApproverUser(arrangeAssistant(appline, "<%=originCompId%>"), originCompId), 1, "<%=docType%>", assistantlinetype, auditlinetype);
						<% 	} %>	
						}
		
					    // 문서번호
						<%	if (enfDocVO.getSerialNumber() > 0) { %>	
							putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber, "<%=enfDocVO.getDeptCategory()%>-<%=enfDocVO.getSerialNumber()%>");
							// 접수번호가 삽입되었는지 2번 더 체크
							if (existField(Document_HwpCtrl, HwpConst.Form.ReceiveNumber)) {
								if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber)) == "") {
									putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber, "<%=enfDocVO.getDeptCategory()%>-<%=enfDocVO.getSerialNumber()%>");
									if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber)) == "") {
										putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber, "<%=enfDocVO.getDeptCategory()%>-<%=enfDocVO.getSerialNumber()%>");
									}
								}
							}
						<%	} %>
						<%	if (!"".equals(enfDocVO.getAcceptDate())) { %>	
							putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate, "<%=enfDocVO.getAcceptDate()%>");
							// 접수일자가 삽입되었는지 2번 더 체크
							if (existField(Document_HwpCtrl, HwpConst.Form.ReceiveDate)) {
								if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate)) == "") {
									putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate, "<%=enfDocVO.getAcceptDate()%>");
									if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate)) == "") {
										putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate, "<%=enfDocVO.getAcceptDate()%>");
									}
								}
							}
						<%	} %>
			
						putFieldText(Document_HwpCtrl, HwpConst.Form.Title, $("#title", "#approvalitem").val());
						putFieldText(Document_HwpCtrl, HwpConst.Form.ConserveType, typeOfConserveType($("#app_conserveType", "#approvalitem").val()));
						putFieldText(Document_HwpCtrl, HwpConst.Form.ReadRange, typeOfReadRange($("#app_readRange", "#approvalitem").val()));
						putFieldText(Document_HwpCtrl, HwpConst.Form.HeaderCampaign, $("#app_headerCamp", "#approvalitem").val());
						putFieldText(Document_HwpCtrl, HwpConst.Form.FooterCampaign, $("#app_footerCamp", "#approvalitem").val());	
						// 발신명의 - 내부문서는 발신명의 생략
						var recvList = getReceiverList($("#appRecv", "#approvalitem").val());
						var recvsize = recvList.length;
						if (recvsize == 0) {
							putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, "");
						} else {
							putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, $("#senderTitle", "#approvalitem").val());
						}
						
										
						// 시행일자, 관인 서명인
						putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate, data.processDate);
						// 시행일자가 삽입되었는지 2번 더 체크
						if (existField(Document_HwpCtrl, HwpConst.Form.EnforceDate)) {
							if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate)) == "") {
								putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate, data.processDate);
								if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate)) == "") {
									putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate, data.processDate);
								}
							}
						}
						if (data.stamppath != "") {
							var stamp = new Object();
							stamp.filename = data.stamppath;
							stamp.displayname = data.stamppath;
							stampfilepath = FileManager.savebodyfile(stamp);
							insertStamp(Document_HwpCtrl, stampfilepath, data.stampwidth, data.stampheight);
						}
						
						moveToPos(Document_HwpCtrl, 2);
						// 서버반영
						if (isExistDocument(Document_HwpCtrl)) {
							recoverBody("recover");
						}
						
					}
				}
			}, 'json').error(function(data) {
			});
		} else {
			//isNum ->발번대상여부 2016-01-27 by 서영락
			 if("Y" != "<%=isNum%>" ) { 
				// 결재경로
				var appline = $("#app_appLine", "#approvalitem").val();
				if (appline != "") {
					var assistantlinetype = $("#assistantLineType", "#approvalitem").val();
					var auditlinetype = $("#auditLineType", "#approvalitem").val();
			<% 	if ("Y".equals(appDocVO.getDoubleYn())) { %>
					resetApprover(Document_HwpCtrl, getApproverUser(appline, "<%=originCompId%>"), 2, "<%=docType%>", assistantlinetype, auditlinetype);
			<% 	} else { %>
					resetApprover(Document_HwpCtrl, getApproverUser(arrangeAssistant(appline, auditlinetype), "<%=originCompId%>"), 1, "<%=docType%>", assistantlinetype, auditlinetype);
			<% 	} %>
				  }
			   }
			//
		}
	<%}%>
		//20120514 시행문서의 경우 접수대기, 배부대기함, 배부등록대장에서는 본문내 의견표시 초기화 kj.yang S
		<%	if (lobCode.equals(lob007) || lobCode.equals(lob008) || lobCode.equals(lol002)) { %>
				deleteOpinionTbl(Document_HwpCtrl);
		<% 	} %>		
		//20120514 시행문서의 경우 접수대기, 배부대기함, 배부등록대장에서는 본문내 의견표시 초기화 kj.yang E
 		
 		
		// 문서조회모드
		changeEditMode(Document_HwpCtrl, 0, false);
		moveToPos(Document_HwpCtrl, 2);
		setSavePoint(Document_HwpCtrl);
		// 첨부파일
		loadAttach($("#attachFile").val());

		<%--접수단계에서는 관련문서 사용안함, jd.park, 20120612
		// 관련문서 (같은회사내에서만 조회가능)
		<% if (compId.equals(appDocVO.getCompId())) { %>
		var relateddoc = getRelatedDocList($("#relatedDoc", "#approvalitem").val());
		var relatedDocCount = relateddoc.length;
		var message = "";
		
		<% if ("Y".equals(opt321)) { %>
			if (relatedDocCount > 0) {
				loadRelatedDoc(relateddoc);
			}
		<% } else { %>	
			if (relatedDocCount > 0) {
				message += "<spring:message code='approval.msg.relateddoc'/> " + relatedDocCount + "<spring:message code='approval.msg.unit'/>";
			}
		<% } %>		
		if (message != "") {
			message += "<spring:message code='approval.msg.registered'/>";
			alert(message);
		}
		<%}%>
		--%>		
		// 배부대기,접수대기함에서는 접수문서오픈시 발송의견을 보여준다. (발송처리이력의 최종발송의견)
<%		if (lobCode.equals(lob007) || lobCode.equals(lob008)) { %>
			// 이송의견(발송의견이 있으면 함께 보여준다.)
			if ($("#moveOpinion").val() != "") {
				viewMoveOpinion();
			} else if ($("#sendOpinion").val() != "") {
				viewSendOpinion();
			}	
<%
		//재배부요청 의견
		//배부대기함과의 통합으로 재배부요청문서의 구분을 recvState로 변경
		//} else if (lobCode.equals(lob019)) {  
			if (enf110.equals(recvState)) {  
%>     
				if ($("#reDistOpinion").val() != "") {
					viewReDistOpinion();
				}	
<%
			} 
		}
%>	


<%      
    if (lobCode.equals(lob003)) {
        if (docState.equals(enf400) || docState.equals(enf500)) { %>
                // 결재처리의견
                    viewProcOpinion();
                // 20160422 접수의견
                    viewRecvOpinion();
    <%      
        }
    } %>

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

        //공람게시 set
        <%
            if(lobCode.equals(lob031)){
        %>
            procPubPost();
        <%           
            }
        %>
        
		<% if (isExtWeb) { %>	
			afterButton();
		<% } %>
        
        screenUnblock()
        
		// 문서위치 조정
		setTimeout(function(){moveStartPosition();}, 100);
	}

   function getSignImageName(displayName){
	   var filename = "";
		for(var i=0 ; i < signlist.length ; i++){
			if(signlist[i].displayname == displayName)
			{
				filename = signlist[i].filename;
				break;
			}
		}
		return filename;
   }
   /**
   * 발신기관 로고 심볼 넣기 
   **/
	function initDocumentEnv(hwpCtrl) {
		var bResult=false;
		// 로고, 심볼, 상하캠페인, 발신기관
		if (logopath != ""){
			bResult = insertImage(hwpCtrl, HwpConst.Form.Logo, logopath, 20, 20);
			if(!bResult){
				g_erroloc = "<spring:message code='hwpconst.form.logo' />";
				return bResult;
			}
		}
		if (symbolpath != ""){
			bResult = insertImage(hwpCtrl, HwpConst.Form.Symbol, symbolpath, 20, 20);	
			if(!bResult){
				g_erroloc = "<spring:message code='hwpconst.form.symbol' />";
				return bResult;
			}	
		}
		
	
	<%	
			if (packInfoVO != null) {
			   List<LineInfoVO> linInfoVOs = packInfoVO.getApproval();
			   List<LineInfoVO> asslinInfoVOs = packInfoVO.getAssist();
		       String LineOrder = "";
		       String signImage = "";
		       String signPosition = "";
		       String signDate = "";
		       String textName = ""; 
		       String apprvalType = "";
	 %>
	     //결재라인 정보에 따라 라인의 정보를 새로 그린다.
		 //if (isStandardForm(Document_HwpCtrl)) {
				var tobe = Math.ceil( <%=linInfoVOs.size()%> / 4 ) + "" + Math.ceil(<%=asslinInfoVOs.size()%> / 4) + "" + Math.ceil( 0 / 4 );
				if (existField(hwpCtrl, HwpConst.Field.SimpleForm)) {
					replaceApprTable(hwpCtrl, "<%=webUrl%><%=webUri%>/app/ref/rsc/ApproverSemiForm" + tobe + ".hwp");
				} else {
					replaceApprTable(hwpCtrl, "<%=webUrl%><%=webUri%>/app/ref/rsc/ApproverForm" + tobe + ".hwp");
				}
		//	}
		//end
	<%
				for(int lcnt = 0 ; lcnt < linInfoVOs.size(); lcnt++){
				   LineInfoVO lineInfoVO = linInfoVOs.get(lcnt);
				   signImage = lineInfoVO.getSignimage();
				   signPosition = lineInfoVO.getSignposition();
				   signDate = lineInfoVO.getPdate();
				   textName = lineInfoVO.getName();
				   apprvalType = lineInfoVO.getType();
				   LineOrder = ""+(lcnt +1);
				   
				   //서명이미지가 있으면 우선적으로 서명이미지를 넣어주고 없을때는 
				   // 텍스트 서명을 넣음
				   if(!("").equals(signImage)){
		%>
		   		   var packFileName = getSignImageName("<%=signImage%>");
		           var signUrl = FileManager.getlocaltempdir()+packFileName;
		           bResult = insertImage(hwpCtrl, HwpConst.Form.Consider+"<%=LineOrder%>",signUrl ,'','');
				   if(!bResult){
					   g_erroloc = HwpConst.Form.Consider +<%=LineOrder%>+" : <spring:message code='appcom.title.filetype.aft007'/> ";
						return bResult;
					}
	    <%
				   }else{
	    %>
	    			bResult = putFieldText(hwpCtrl, HwpConst.Form.Consider+"<%=LineOrder%>","<%=textName%>");
	    			if(!bResult){
	    				 g_erroloc = HwpConst.Form.Consider + <%=LineOrder%>+" : <spring:message code='appcom.title.filetype.aft007'/> ";
						 return bResult;
					 }
	    <%
	   			   }
	    %>
	    			bResult = putFieldText(hwpCtrl, "*"+HwpConst.Form.Consider+"<%=LineOrder%>","<%=signPosition%>");
	    			if(!bResult){
	    				 g_erroloc = HwpConst.Form.Consider + <%=LineOrder%>+ " : <spring:message code='approval.form.position' /> ";
						 return bResult;
					 }
		<%
					if(lcnt == linInfoVOs.size()-1 ){//최종결재자인 경우에만 날짜를 찍는다.
		%>
					//전결이나 대결인경우, 최종 결재에서 날짜 압에 결재역할을 넣어준다.
						if("<%=apprvalType%>" == "<spring:message code='approval.apptype.art052'/>" || "<%=apprvalType%>" == "<spring:message code='approval.apptype.art051'/>"){
							var roleDate = "<%=apprvalType%>" + " " + typeOfDate("approval", "<%=signDate%>");
							bResult = putFieldText(hwpCtrl, "@"+HwpConst.Form.Consider+"<%=LineOrder%>",roleDate);	
						}else{
							bResult = putFieldText(hwpCtrl, "@"+HwpConst.Form.Consider+"<%=LineOrder%>",typeOfDate("approval", "<%=signDate%>"));	
						}
	
					if(!bResult){
						g_erroloc = "<spring:message code='approval.form.approvaldate'/> ";
						 return bResult;
					 }	

		<%
					}
				}
			}

			if (packInfoVO != null) {
			   List<LineInfoVO> linInfoVOs = packInfoVO.getAssist();
		       String LineOrder = "";
		       String signImage = "";
		       String signPosition = "";
		       String signDate = "";
		       String textName = ""; 
		
	
				for(int acnt = 0 ; acnt < linInfoVOs.size(); acnt++) {
				   LineInfoVO lineInfoVO = linInfoVOs.get(acnt);
				   signImage = lineInfoVO.getSignimage();
				   signPosition = lineInfoVO.getSignposition();
				   signDate = lineInfoVO.getPdate();
				   textName = lineInfoVO.getName();
				   LineOrder = ""+(acnt +1); 

			      //서명이미지가 있으면 우선적으로 서명이미지를 넣어주고 없을때는 
				   // 텍스트 서명을 넣음
				   if(!("").equals(signImage)){
		%>
		           var signUrl = FileManager.getlocaltempdir()+"<%=signImage%>";
		           bResult = insertImage(hwpCtrl, HwpConst.Form.Assistance+"<%=LineOrder%>",signUrl ,'','');
				   if(!bResult){
					   g_erroloc = HwpConst.Form.Assistance + <%=LineOrder%> + " : <spring:message code='appcom.title.filetype.aft007'/> ";
						 return bResult;
					 }

	    <%
				   }else{		     
	    %>
	    			bResult = putFieldText(hwpCtrl, HwpConst.Form.Assistance+"<%=LineOrder%>","<%=textName%>");
	    			if(!bResult){
	    				 g_erroloc = HwpConst.Form.Assistance + <%=LineOrder%> + " : <spring:message code='appcom.title.filetype.aft007'/> ";
						 return bResult;
					 }
	    
	    <%
	   			   }
	    %>
	    			bResult = putFieldText(hwpCtrl, "*"+HwpConst.Form.Assistance+"<%=LineOrder%>","<%=signPosition%>");	
			  		if(!bResult){
			  			 g_erroloc = HwpConst.Form.Assistance + <%=LineOrder%> + " : <spring:message code='approval.form.position' />";
						 return bResult;
					 }	

		<%
				}        
			}

		//제목 넣기,발신명인,헤드,풋,발신기관명
		if(packInfoVO != null){
		    if(!packInfoVO.getTitle().equals("")){
		%>
			 if(existField(Document_HwpCtrl, HwpConst.Form.Title)){
				bResult = putFieldText(Document_HwpCtrl, HwpConst.Form.Title, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getTitle())%>");//제목
	
				if(!bResult){
					g_erroloc = HwpConst.Form.Title+" ";
					 return bResult;
				 }	
			 }
		<%
			}
			
		    if(!packInfoVO.getSenderTitle().equals("")){
		%>
			 if(existField(Document_HwpCtrl, HwpConst.Form.SenderName)){
				bResult = putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getSenderTitle())%>");//발신명의
				if(!bResult){
					g_erroloc = HwpConst.Form.SenderName+" ";
					 return bResult;
				 }	
			 }
		<%
			}
			
		    if(!packInfoVO.getHeadCampaign().equals("")){
		 %>
			 if(existField(Document_HwpCtrl, HwpConst.Form.HeaderCampaign)){
				bResult = putFieldText(hwpCtrl, HwpConst.Form.HeaderCampaign, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getHeadCampaign())%>");
				if(!bResult){
					g_erroloc = HwpConst.Form.HeaderCampaign+" ";
					 return bResult;
				 }	
			 }
		<%
			}
			
		    if(!packInfoVO.getFootCampaign().equals("")){
		 %>
			 if(existField(Document_HwpCtrl, HwpConst.Form.FooterCampaign)){
				bResult = putFieldText(hwpCtrl, HwpConst.Form.FooterCampaign, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getFootCampaign())%>");
				if(!bResult){
					g_erroloc = HwpConst.Form.FooterCampaign+" ";
					 return bResult;
				 }	
			 }
		<%
			}
			
		    if(!packInfoVO.getOrgan().equals("")){
		 %>
			 if(existField(Document_HwpCtrl, HwpConst.Form.OrganName)){
				bResult = putFieldText(hwpCtrl, HwpConst.Form.OrganName, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getOrgan())%>");//기관명
				if(!bResult){
					g_erroloc = HwpConst.Form.OrganName+" ";
					 return bResult;
				 }
			 }
		<%
			}
			
		    if(!packInfoVO.getRegNumber().equals("")){
		 %>	
			 if(existField(Document_HwpCtrl, HwpConst.Form.EnforceNumber)){
				bResult = putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getRegNumber())%>");//시행번호
				if(!bResult){
					g_erroloc = HwpConst.Form.EnforceNumber+" ";
						 return bResult;
				 }	
			 }
		<%
			}
			
		    if(!packInfoVO.getRec().equals("")){
				if(packInfoVO.getRefer().equals("false")){  
		 %>
				 if(existField(Document_HwpCtrl, HwpConst.Form.Receiver)){
					bResult = putFieldText(hwpCtrl, HwpConst.Form.Receiver, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getRec())%>");//수신
					if(!bResult){
						g_erroloc = HwpConst.Form.Receiver+" ";
						 return bResult;
					 }
				 }
		<%
				}else{
		%>
					if(existField(Document_HwpCtrl, HwpConst.Form.Receiver)){
						bResult = putFieldText(hwpCtrl, HwpConst.Form.Receiver, "<spring:message code='hwpconst.field.receiverref'/>");//수신자 참조
						if(!bResult){
							g_erroloc = HwpConst.Form.Receiver+" ";
							 return bResult;
						 }
						bResult = putFieldText(hwpCtrl, HwpConst.Form.ReceiverRefTitle,  "<spring:message code='hwpconst.field.receiver'/>");//수신 타이틀
						if(!bResult){
							g_erroloc = HwpConst.Form.ReceiverRefTitle+" ";
							 return bResult;
						 }
						bResult = putFieldText(hwpCtrl, HwpConst.Form.ReceiverRef, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getRec())%>");//수신
						if(!bResult){
							g_erroloc = HwpConst.Form.ReceiverRef+" ";
							 return bResult;
						 }
					 }
		<%
				}
			}
			
		    if(!packInfoVO.getEnforceDate().equals("")){
		 %>	
			 if(existField(Document_HwpCtrl, HwpConst.Form.EnforceDate)){
				bResult = putFieldText(hwpCtrl, HwpConst.Form.EnforceDate, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getEnforceDate())%>");//시행일
				if(!bResult){
					g_erroloc = HwpConst.Form.EnforceDate+" ";
					 return bResult;
				 }
			 }
		<%
			}
			
		    if(!packInfoVO.getReceiptNumber().equals("")){
		 %>
			 if(existField(Document_HwpCtrl, HwpConst.Form.ReceiveNumber)){
				bResult = putFieldText(hwpCtrl, HwpConst.Form.ReceiveNumber, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getReceiptNumber())%>");//접수번호
				if(!bResult){
					g_erroloc = HwpConst.Form.ReceiveNumber+" ";
					 return bResult;
				 }	
			 }
		<%
			}
			
		    if(!packInfoVO.getReceiptDate().equals("")){
		 %>	
			 if(existField(Document_HwpCtrl, HwpConst.Form.ReceiveDate)){
				bResult = putFieldText(hwpCtrl, HwpConst.Form.ReceiveDate, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getReceiptDate())%>");//접수일
				if(!bResult){
					g_erroloc = HwpConst.Form.ReceiveDate+" ";
					 return bResult;
				 }
			 }
		<%
			}
			
		    if(!packInfoVO.getVia().equals("")){
		 %>	
			 if(existField(Document_HwpCtrl, HwpConst.Form.Via)){
				bResult = putFieldText(hwpCtrl, HwpConst.Form.Via, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getVia())%>");//경유
				if(!bResult){
					g_erroloc = HwpConst.Form.Via+" ";
					 return bResult;
				 }
			 }
		<%
			}
			
		    if(!packInfoVO.getTelephone().equals("")){
		 %>
			 if(existField(Document_HwpCtrl, HwpConst.Form.Telephone)){
				bResult = putFieldText(hwpCtrl, HwpConst.Form.Telephone, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getTelephone())%>");//전화번호
				if(!bResult){
					g_erroloc = HwpConst.Form.Telephone+" ";
					 return bResult;
				 }	
			 }
			<%
			}
			
		    if(!packInfoVO.getFax().equals("")){
		 %>	
			 if(existField(Document_HwpCtrl, HwpConst.Form.Fax)){
				putFieldText(hwpCtrl, HwpConst.Form.Fax, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getFax())%>");//팩스
				if(!bResult){
					g_erroloc = HwpConst.Form.Fax+" ";
					 return bResult;
				 }	
			   }
		<%
			}
			
		    if(!packInfoVO.getEmail().equals("")){
		 %>	
			 if(existField(Document_HwpCtrl, HwpConst.Form.Email)){
				bResult = putFieldText(hwpCtrl, HwpConst.Form.Email, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getEmail())%>");//E-mail
				if(!bResult){
					g_erroloc = HwpConst.Form.Email+" ";
					 return bResult;
				 }	
			 }
		<%
			}
			
		    if(!packInfoVO.getHomeUrl().equals("")){
		 %>	
			 if(existField(Document_HwpCtrl, HwpConst.Form.Homepage)){
				putFieldText(hwpCtrl, HwpConst.Form.Homepage, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getHomeUrl())%>");//HomePage
				if(!bResult){
					g_erroloc = HwpConst.Form.Homepage+" ";
					 return bResult;
				 }	
			 }
		<%
			}
			
		    if(!packInfoVO.getAddress().equals("")){
		 %>	
			 if(existField(Document_HwpCtrl, HwpConst.Form.Address)){
				bResult = putFieldText(hwpCtrl, HwpConst.Form.Address, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getAddress())%>");//주소
				if(!bResult){
					g_erroloc = HwpConst.Form.Address+" ";
					 return bResult;
				 }	
			 }
		<%
			}
			
		    if(!packInfoVO.getPublicationText().equals("")){
		 %>
			if(existField(Document_HwpCtrl, HwpConst.Form.PublicBound)){
				var strOpenLevel = setOpenLevelValue("<%=EscapeUtil.escapeJavaScript(packInfoVO.getPublication())%>");
				bResult = putFieldText(hwpCtrl, HwpConst.Form.PublicBound, strOpenLevel);//공개여부
				if(!bResult){
					g_erroloc = HwpConst.Form.PublicBound+" ";
					 return bResult;
				 }	
			}
	<%
		    }
		    
		      if(!packInfoVO.getZipcode().equals("")){
		 %>	
		 //approval.form.zipcode
			 if(existField(Document_HwpCtrl, HwpConst.Form.Zipcode)){
				bResult = putFieldText(hwpCtrl, HwpConst.Form.Zipcode, "<%=EscapeUtil.escapeJavaScript(packInfoVO.getZipcode())%>");//우편번호
				if(!bResult){
					g_erroloc = HwpConst.Form.Zipcode+" ";
					 return bResult;
				 }	
			 }
		<%
			}
		    
		}
	%>
	
	if(sealpath != ""){
		bResult = insertStamp(hwpCtrl,sealpath, 20,20);
		if(!bResult){
			g_erroloc = "<spring:message code='approval.enforce.title.orgstamp'/> ";
			return bResult;
		}
	}else{
        <%
		//제목 넣기,발신명인,헤드,풋,발신기관명
		if(packInfoVO != null){
		    if(packInfoVO.getOmit().equals("true")){
		%>
			showOmitStamp(hwpCtrl);
		<%
			}
		}
		%>
			
    }
		
		 return bResult;
	} 

	//공개범위 설정
	function setOpenLevelValue(strValue)
	{
		var strPLOpen = strValue.charAt(0);
		var strOpenLevel = "";
		if (strPLOpen == "1"){
			strOpenLevel = "<spring:message code='approval.form.publiclevel.open'/>";
		} else {
			if (strPLOpen == "2") {
				strOpenLevel = "<spring:message code='approval.form.publiclevel.partialopen'/>";
			} else if (strPLOpen == "3") {
				strOpenLevel = "<spring:message code='approval.form.publiclevel.closed'/>";
			}
			var lstLevel ="";
			for (var i = 1 ; i < strValue.length; i++)
			{
				if (strValue.charAt(i) == "Y") {
					lstLevel +="," + i;
				}	
			}
			strOpenLevel += "(" + lstLevel.substring(1) + ")";
		}

		return strOpenLevel;
	}

	function moveStartPosition() {
		moveToPos(Document_HwpCtrl, 2);
	}

	<%
    String hwpFileId = "";
    String htmlFileId = "";
	if ("Y".equals(enfDocVO.getMobileYn())) { 
         if (aft001.equals(bodyVO.getFileType())) {
             hwpFileId = bodyVO.getFileId();
         } else if (aft002.equals(bodyVO.getFileType())) {
             htmlFileId = bodyVO.getFileId();
         }
	 } 
	 %>
	 
	 
	function recoverBody(reason) {
	    var bodyinfo = "";
	    var filename = "";
		var filepath = "";
		
		if(bodyType == "hwp" || bodyType == "doc") {
			if(bodyType == "doc"){ 
				filename = "DocBody_" + UUID.generate() + ".doc";
			}else {
				filename = "HwpBody_" + UUID.generate() + ".hwp";
			} 
			
			//본문생성
			filepath = FileManager.getlocaltempdir() + filename;
			saveHwpDocument(Document_HwpCtrl, filepath, false);
			
		    var hwpfile = new Object();
		    hwpfile.localpath = filepath;
			hwpfile.type = "body";
		    var result = FileManager.uploadfile(hwpfile);
		    var filelength = result.length;
		    for (var pos = 0; pos < filelength; pos++) {
		        var file = result[pos];
		        bodyinfo += "<%=hwpFileId%>" + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
		        "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
		        "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
		    }
		    
			<%  if ("Y".equals(opt343)) { %>   
				filename = "HtmlBody_" + UUID.generate() + ".html";
				filepath = FileManager.getlocaltempdir() + filename;
				
				// Html 모바일본문 생성
			    saveHtmlDocument(Document_HwpCtrl, filepath, false);
				
			    var htmlfile = new Object();
				htmlfile.type = "body";
			    htmlfile.localpath = filepath;
			    result = FileManager.uploadfile(htmlfile);
			    filelength = result.length;
			    
			    for (var pos = 0; pos < filelength; pos++) {
			        var file = result[pos];
			        bodyinfo += "<%=htmlFileId%>" + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
			        "<%=aft002%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
			        "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + htmlfile.localpath + String.fromCharCode(4);
			    }
		<%  } %>     
		
		    $("#bodyFile", "#approvalitem").val(bodyinfo);
		    $.post("<%=webUri%>/app/approval/recoverBody.do", "docId=<%=enfDocVO.getDocId()%>&reason=" + reason + "&docState=<%=docState%>&bodyFile=" + bodyinfo, function(result) {
//		         $("#mobileYn", "#approvalitem").val("N"); 
		    }, 'json').error(function(data) {
		    });
		}
	}
	
	
	//---------------------------
    //이전/다음처리 함수
    //---------------------------
	var prevnext = false;
    //이전
	function moveToPrevious(chkPrenext) {
		if (typeof(chkPrenext) == "undefined") {
			chkPrenext = "N";
		}
	    prevnext = true;
	    if (opener != null && opener.getPreNextDoc != null) {
	        var itemnum = getCurrentItem();
	        var docId = $("#docId").val();   
	        var message = opener.getPreNextDoc(docId, "pre");
	        if (message != null && message != "") {
	        	if(chkPrenext == "Y"){
					prevnext = false;
				}
	            alert(message);
	        }
	    }
	}
	 
    //다음
	function moveToNext(chkPrenext) {
		if (typeof(chkPrenext) == "undefined") {
			chkPrenext = "N";
		}
	    prevnext = true;
	    if (opener != null && opener.getPreNextDoc != null) {
	        var itemnum = getCurrentItem();
	        var docId = $("#docId").val();   
	        var message = opener.getPreNextDoc(docId, "next");
	        if (message != null && message != "") {
	        	if(chkPrenext == "Y"){
					prevnext = false;
				}
	            alert(message);
	        }
	    }
	}
	    
    
    //공람문서를 열때마다 공람일자를 set한다.
    function setPubReadDate(){
        
        $.post("<%=webUri%>/app/appcom/pubReadDate.do", $("#frmDocInfo").serialize(), function(data){
        }, 'json');
    }

    
    //공람게시
    function procPubPost(){
       // alert('aaaa');
        $.post("<%=webUri%>/app/enforce/procPubReader.do", $("#frmDocInfo").serialize(), function(data){
        }, 'json');
    }

    
    //문서를 열때마다 열람일자를 set한다.
    function setReadDate(){
        $.post("<%=webUri%>/app/enforce/updateReadDate.do", $("#frmDocInfo").serialize(), function(data){
        }, 'json');
    }

	// 상단의 모든 버튼을 disable 하는 함수로써, 버튼클릭시 항상 호출해주어야함.
	function disableButton(){

		$("#beforeprocess").hide(); 
	}

	// 상단의 필요한 버튼을 enable 하는 함수로써, 버튼클릭 처리완료후 항상 호출해주어야함.
	function enableButton(){

		$("#beforeprocess").show();
	}	
	
	// 상단의 버튼을 처리후 담당 지정 모드로 변경
	function chargerButton(){
		$("#chargerprocess").show();  
		$("#beforeprocess").hide();  
		$("#afterprocess").hide();  
	}
	
	// 상단의 버튼을 처리후 공람자 지정 모드로 변경
	function pubReaderButton(){
		$("#beforeprocess").hide();  
		$("#chargerprocess").hide();  
		$("#pubreaderprocess").show();  
		$("#beforeprocess").hide();  
		$("#afterprocess").hide();  
	}
	
	// 상단의 버튼을 처리후 조회모드로 변경
	function afterButton(){

		$("#chargerprocess").hide();  
		$("#beforeprocess").hide();  
		$("#afterprocess").show();  
	}
	 

	//문서정보조회
	function selectDocInfo(type) {
		//var height = "280";
		var height = "310";
		
        if('Read' == type){
           //조회
		   docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/enforce/selectDocInfo.do?lobCode=<%=lobCode%>&docState=<%=docState%>&docId=" + $("#docId", "#approvalitem").val() + "&receiverOrder=" + $("#receiverOrder", "#approvalitem").val(), 850, height);

        }else{
           //저장
           docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/enforce/popupDocInfo.do?docState=<%=docState%>&bindId=<%=enfDocVO.getBindingId()%>", 850, height);
        }
    }

	<%--접수단계에서는 관련문서 사용안함, jd.park, 20120612
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
		<% if ("Y".equals(opt321)) { %>
			loadRelatedDoc(relateddoc);
		<% } %>		
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
			row += "<td width='40' title='<spring:message code='list.list.msg.docTypeProduct'/>' class='tb_center'>[<spring:message code='env.code.name.DPI001'/>]</td>";
		} else {
			row += "<td width='40' title='<spring:message code='list.list.msg.docTypeReceive'/>' class='tb_center'>[<spring:message code='env.code.name.DPI002'/>]</td>";
		}
		row += "<td class='tb_left'><a href='#' onclick='selectRelatedAppDoc(\"" + docId + "\", \"" + usingType + "\", \"" + electronDocYn + "\");return(false);'>" + escapeJavaScript(title) + "</a></td>";
		row += "</tr>";

		return row;
	}



	function selectRelatedAppDoc(docId, usingType, electronDocYn) {
		var randomValue = Math.round(Math.random() * 100000);
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
					docLinkedWin = openWindow("docLinkedWinN"+randomValue, linkUrl, width, height);
				} else {
					linkUrl = "<%=webUri%>/app/approval/selectAppDoc.do?docId="+docId+"&lobCode=<%=lob093%>";
					docLinkedWin = openWindow("docLinkedWinY"+randomValue, linkUrl, width, height);
				}
			} else {
				if (electronDocYn == "N") {
					linkUrl = "<%=webUri%>/app/enforce/insertEnfNonElecDoc.do?docId="+docId+"&lobCode=<%=lob093%>";
					docLinkedWin = openWindow("docLinkedWinN"+randomValue, linkUrl, width, height);
				} else {
					linkUrl = "<%=webUri%>/app/enforce/EnforceDocument.do?docId="+docId+"&lobCode=<%=lob093%>";
					docLinkedWin = openWindow("docLinkedWinY"+randomValue, linkUrl, width, height);
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
	--%>

	
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
		docInfo.ownDeptId = "<%=ownDeptId%>";   //소유부서 추가 jth8172 20110821

		docInfo.classNumber = $("#classNumber", "#frmDocInfo").val();
	 	docInfo.docnumName = $("#docnumName", "#frmDocInfo").val();
	 		 	
		docInfo.docType = $("#docType", "#approvalitem").val();		

				
		docInfo.transferYn = $("#transferYn", "#approvalitem").val();

		return docInfo;
	}

    //문서정보 set
    function setDocInfo(docInfo) {

        //alert(docInfo.publicPost);
        $("#title", "#frmDocInfo").val(docInfo.title);
        $("#bindingId", "#frmDocInfo").val(docInfo.bindingId);
        $("#bindingName", "#frmDocInfo").val(docInfo.bindingName);
        $("#conserveType", "#frmDocInfo").val(docInfo.conserveType);
		$("#deptCategory", "#frmDocInfo" ).val(docInfo.deptCategory);
        $("#readRange", "#frmDocInfo").val(docInfo.readRange);
        $("#urgencyYn", "#frmDocInfo").val(docInfo.urgencyYn);
        $("#publicPost", "#frmDocInfo").val(docInfo.publicPost);

    	$("#classNumber", "#frmDocInfo").val(docInfo.classNumber);
        $("#docnumName", "#frmDocInfo").val(docInfo.docnumName);

        // 편철 다국어 추가
        $("#bindingResourceId", "#frmDocInfo").val(docInfo.bindingResourceId);
        saveBindingName = docInfo.bindingName;

        if (docInfoAccept =="Y") {
			if (directAccept == "N") {
				acceptOk(); //접수
			} else if (directAccept == "Y") {
				acceptApprovalOk(); //담당접수
			}	
        }
    }
    
	// 문서정보-결재라인
	function getAppLine() {
		return $("#enfLines", "#frmDocInfo").val();
	}
	

	// 문서정보-수신자정보
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

    // 결재 의견조회
    function viewProcOpinion() {
    	 if($("#procOpinion").val() !=""){
            viewOpinion("<%=strProcAskType%><%=msgOpinion%>", escapeJavaScript(escapeCarriageReturn($("#procOpinion").val())));
    	 }
    }

    // 접수 의견조회 20160422
    function viewRecvOpinion() {
    	 if($("#ProcVOs").val() !=""){
    		 viewOpinionRecv("접수의견", escapeJavaScript(escapeCarriageReturn($("#ProcVOs").val())));
    	 }
    }

	// 접수의견조회팝업 20160422
	function viewOpinionRecv(title, opinion) {
 		var top = (screen.availHeight - 260) / 2;
		var left = (screen.availWidth - 400) / 2;
		popupWin = window.open("", "popupWin2",
	            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=400,height=260," +
		        "scrollbars=no,resizable=no"); 
		
		$("#popupTitle").val(title);
		$("#popupOpinion").val(opinion);
		$("#frmDocInfo").attr("target","popupWin2");
		$("#frmDocInfo").attr("action","<%=webUri%>/app/enforce/viewOpinion.do");
		$("#frmDocInfo").submit();
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

    //접수후 이송기능 추가  by jkkim
	function moveAfterRecv()
	{
		// 이송대상부서 선택(대외문서는 ref, 대내문서는 recv로 지정됨)
		reDistMoveYn = "M";
		lfn_formPop("OPT402");//이송
	}

    //접수후 경유기능 추가  by jkkim
	function viaAfterRecv()
	{
		// 이송대상부서 선택(대외문서는 ref, 대내문서는 recv로 지정됨)
		reDistMoveYn = "M";
		lfn_formPop("OPT403");//경유
	}

	//접수후 이송기능을 위한 양식함  팝업 by jkkim
	function lfn_formPop(transtype){

	    var top = (screen.availHeight - 250) / 2;
	    var left = (screen.availWidth - 400) / 2;
	    popupWin = openWindow("lfn_formPopWin", "<%=webUri%>/app/env/ListEnvFormPop.do?docId="+$("#docId").val()+"&title="+$("#title").val()+"&transtype="+transtype, "700", "420" );
	}
	
	//이송여부확인 의견 및 암호입력
	function moveOk() {
		popOpinion("moveProcess","<%=moveBtn%>","Y"); 
	}
	//이송처리
	function moveProcess(popComment) {
		// 편철 다국어 추가
		$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());
		
		$("#comment").val(popComment);
		$("#procType").val("<%= apt006 %>");  // 이송
		// 1. DB 저장
		$.post("<%=webUri%>/app/enforce/moveSendDoc.do", $("#frmDocInfo").serialize(), function(data){
			$("#bindingName", "#frmDocInfo").val(saveBindingName);
			
			//결과 페이지의 값을 받아 메세지 처리한다.
				if("1" == data.result ) {  
					alert("<spring:message code='approval.result.msg.moveok'/>");
					afterButton();

					// 모든처리후 문서닫음 start 20110804 
					<% if ("Y".equals(opt357)) { %> 
					    exitAppDoc(); 
					 <% } %>   
					// 모든처리후 문서닫음 end 20110804 
					
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
		// 편철 다국어 추가
		$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());
		
		$("#comment").val(popComment);
		$("#procType").val("<%= apt007 %>");  // 반송
		// 1. DB 저장
		$.post("<%=webUri%>/app/enforce/returnSendDoc.do", $("#frmDocInfo").serialize(), function(data){
			$("#bindingName", "#frmDocInfo").val(saveBindingName);
			
			//결과 페이지의 값을 받아 메세지 처리한다.
				if("1" == data.result ) {  
					alert("<spring:message code='approval.result.msg.returnok'/>");
					afterButton();

					// 모든처리후 문서닫음 start 20110804 
					<% if ("Y".equals(opt357)) { %> 
					    exitAppDoc(); 
					 <% } %>   
					// 모든처리후 문서닫음 end 20110804 
					
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


	//접수부서 선택팝업(다중선택)
	function goRecvDeptMulti(){
		var width = 500;
		var height = 450;
		var top = (screen.availHeight - height) / 2;
		var left = (screen.availWidth - width) / 2;
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
		var appDoc = window.open("<%=webUri%>/app/common/OrgTree.do?type=5&treetype=2", "dept", option);
	}	

	//접수부서 선택팝업
	function goRecvDept(){
		var width = 500;
		var height = 300;
		var top = (screen.availHeight - height) / 2;
		var left = (screen.availWidth - width) / 2;
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
		var appDoc = window.open("<%=webUri%>/app/common/OrgTree.do?type=2&treetype=2", "dept", option);
	}	

	//부서선택창
	function setDeptInfo(deptInfo){
		var flag = $("#distributeFlag").val();
		var orgIdTemp = '';
		var orgNameTemp = '';
		if (deptInfo.length != null && deptInfo.length > 0) {
			for(var i = 0; i < deptInfo.length; i++){
				orgIdTemp += (String.fromCharCode(2) + deptInfo[i].orgId);
				orgNameTemp += (String.fromCharCode(2) + deptInfo[i].orgName);
			}
			if(orgIdTemp != '') orgIdTemp = orgIdTemp.substring(1);
			if(orgNameTemp != '') orgNameTemp = orgNameTemp.substring(1);
		} else {	
			orgIdTemp = deptInfo.orgId;
			orgNameTemp = deptInfo.orgName;
		}
		//if(deptInfo.isProcess){//처리과여부
		if(true){//처리과여부
			$('#distDeptId').val(orgIdTemp);
			$('#distDeptName').val(orgNameTemp);
			//의견 및 암호입력
			if(flag == "D" ) {
				distributeOpinion(flag);
			} else if(flag == "R" || flag == "A") {
				$('#refDeptId').val(orgIdTemp); 
				$('#refDeptName').val(orgNameTemp);
				distributeOpinion(flag);
			} else if(flag == "M" ) {
				if("N" =="<%=enfDocVO.getDistributeYn()%>") { // 접수문서(대외문서는 ref, 대내문서는 recv로 지정됨)
					$('#recvDeptId').val(orgIdTemp);
					$('#recvDeptName').val(orgNameTemp);
				} else {
					$('#refDeptId').val(orgIdTemp); 
					$('#refDeptName').val(orgNameTemp);
				}
				distributeOpinion(flag);
			}		 
		}else{//처리과가 아님
			alert('<spring:message code="approval.result.msg.noisprocess" />');		
			$('#distDeptId').val("");
			$('#distDeptName').val("");
		}
	}

	// 배부관련처리( flag : 배부(D),재배부(R),추가배부(A),이송(M),재배부요청(Q),배부안함(N),배부회수(W) )
	function distribute(flag) {
		$("#distributeFlag").val(flag); 

		if(flag == 'D' || flag == 'R' || flag == 'A') {
			// 접수부서선택창을 띄운다.(다중선택)
			<%
		    // 다중배부 사용여부에 따라 다중선택인지 단인선택인지 분기
			if("Y".equals(opt427)) {
			%>
				goRecvDeptMulti();
			<%
			} else {
			%>
				goRecvDept();
			<%
			}
			%>
		} else if(flag == 'M') {
			// 접수부서선택창을 띄운다.
			goRecvDept();
		} else {
			distributeOpinion(flag);
		}
	}

	// 배부관련의견 입력( flag : 배부(D),재배부(R),추가배부(A),이송(M),재배부요청(Q),배부안함(N) )
	function distributeOpinion(flag) {
		var btnName;
		if(flag == 'D') { // 배부
			btnName = "<%=distributeBtn%>";
		} else if(flag == 'R') { // 재배부
			btnName = "<%=reDistributeBtn%>";
		} else if(flag == 'A') { // 추가배부
			btnName = "<%=appendDistributeBtn%>";
		} else if(flag == 'M') { // 이송
			btnName = "<%=moveBtn%>";
		} else if(flag == 'Q') { // 재배부요청
			btnName = "<%=reDistRequestBtn%>";
		} else if(flag == 'N') { // 배부안함
			btnName = "<%=noDistributeBtn%>";
		} else if(flag == 'W') { // 배부회수
			btnName = "<%=distributeWithdrawBtn%>";
		}

		popOpinion("distributeProcess",btnName,"Y"); 
	}

	// 배부관련의견 처리(confirm 딜레이 처리)
	function distributeProcess(popComment) {
		if (typeof(popComment) == "undefined") {
			popComment = "";
		}
		$("#comment").val(popComment); 
		setTimeout(executeDistributeProcess,100);
	}

	function executeDistributeProcess() {
		// 편철 다국어 추가
		$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());
		
		var flag = $("#distributeFlag").val();
		var procType;
		var url;
		var okMessage;
		var failMessage;
		
		if(flag == 'D') { // 배부
			procType = "<%=apt012%>";
			url = "<%=webUri%>/app/enforce/ProcessDistDoc.do";
			okMessage = "<spring:message code='approval.result.msg.distributeok'/>";
			failMessage = "<spring:message code='approval.result.msg.distributefail'/>";
		} else if(flag == 'R') { // 재배부
			procType = "<%=apt012%>";
			url = "<%=webUri%>/app/enforce/ProcessReDistDoc.do";
			okMessage = "<spring:message code='approval.result.msg.distributeok'/>";
			failMessage = "<spring:message code='approval.result.msg.distributefail'/>";
		} else if(flag == 'A') { // 추가배부
			procType = "<%=apt012%>";
			url = "<%=webUri%>/app/enforce/ProcessAppendDistribute.do";
			okMessage = "<spring:message code='approval.result.msg.distributeok'/>";
			failMessage = "<spring:message code='approval.result.msg.distributefail'/>";
		} else if(flag == 'M') { // 이송
			procType = "<%=apt006%>";
			url = "<%=webUri%>/app/enforce/moveSendDoc.do";
			okMessage = "<spring:message code='approval.result.msg.moveok'/>";
			failMessage = "<spring:message code='approval.result.msg.movefail'/>";
		} else if(flag == 'Q') { // 재배부요청
			procType = "<%=apt013%>";
			url = "<%=webUri%>/app/enforce/reDistRequest.do";
			okMessage = "<spring:message code='approval.result.msg.redistrequestok'/>";
			failMessage = "<spring:message code='approval.result.msg.redistrequestfail'/>";
		} else if(flag == 'N') { // 배부안함
			procType = "<%=apt018%>";
			url = "<%=webUri%>/app/enforce/noDistribute.do";
			okMessage = "<spring:message code='approval.result.msg.nodistributeok'/>";
			failMessage = "<spring:message code='approval.result.msg.nodistributefail'/>";
		} else if(flag == 'W') { // 배부회수
			procType = "<%=apt019%>";
			url = "<%=webUri%>/app/enforce/distributeWithdraw.do";
			okMessage = "<spring:message code='approval.result.msg.distributewithdrawok'/>";
			failMessage = "<spring:message code='approval.result.msg.distributewithdrawfail'/>";
		}
		
		//1. DB 작업
		$("#procType").val(procType);
	<%
	    //T:배부대기, Y:배부처리, N:접수쪽에서 직접 접수대기 처리  || "Y".equals(enfDocVO.getDistributeYn())
		if(("T".equals(enfDocVO.getDistributeYn()))&& det011.equals(enfRecvVO.getEnfType()))
		{
	%>
		// Hwp 본문으로 파일 만들기 by jkkim
		var hwpfile = new Object();
		hwpfile.type = "body";
		hwpfile.localpath = bodyfilepath;
	    // HWP 본문을 만든후, 업로드한다. Upload 후 DB에 저장을 위해 정보를 묶어준다. by jkkim
		var result = FileManager.uploadfile(hwpfile,true);
		if (result == null) {
			return false;
		} 
		var filelength = result.length;
		var bodyinfo = "";
		for (var loop = 0; loop < filelength; loop++) {
			var file = result[loop];
			bodyinfo += file.fileid + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
		    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(bodyfilepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
		    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
		}
		//alert(bodyinfo);
		$("#bodyFile", "#approvalitem").val(bodyinfo);
		//end
		<%
		}
		%>

		$.post(url, $("#frmDocInfo").serialize(), function(data){
			$("#bindingName", "#frmDocInfo").val(saveBindingName);
			
			//결과 페이지의 값을 받아 메세지 처리한다.
			if("1" == data.result) {    
				alert(okMessage);
				afterButton();
				
				// 모든처리후 문서닫음 start 20110804 
				<% if ("Y".equals(opt357)) { %> 
				    exitAppDoc(); 
				<% } else { %>
					// 배부(D), 재배부(R), 이송(M), 재배부요청(Q), 배부안함(N)의 경우 옵션에 상관없이 문서 닫음. 
					if(flag == 'D' || flag == 'R' || flag == 'M' || flag == 'Q' || flag == 'N') {
					    exitAppDoc(); 
					}
				<% } %>
				// 모든처리후 문서닫음 end 20110804 

				
			} else {
				alert(failMessage);
			}	
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert(failMessage);
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
	
/* //문서정보입력창에서 기록물철 입력하므로 사용안함
	// 기록물선택창
	function selectBind() {
		var top = (screen.availHeight - 450) / 2;
		var left = (screen.availWidth - 430) / 2;
		var option = "width=430,height=450,top=" + top + ",left=" + left + ",menubar=no,scrollbars=no,status=yes";
		bindWin = window.open("<%=webUri%>/app/bind/select.do", "itemWin", option);
	}
	
	// 기록물선택창리턴함수
	function setBind(bind) {
		if (typeof(bind) == "object") {
			$("#bindingId").val(bind.bindingId);
			$("#bindingName").val(bind.bindingName);
			$("#conserveType").val(bind.retentionPeriod);
			//의견 및 암호입력
			if (directAccept == "N") {
				acceptOk(); //접수
			} else if (directAccept == "Y") {
				acceptApprovalOk(); //담당접수
			}	
		}
	}
*/

	function acceptOk() {
		popOpinion("acceptProcess","<%=acceptBtn%>","Y"); 
	}
		
	//접수
	function acceptProcess(popComment) {
		$("#comment").val(popComment);
		setTimeout(afterAcceptProcess,100);		
	}

	function afterAcceptProcess() {
		
		// 편철 다국어 추가
		var saveBingingName = $("#bindingName", "#frmDocInfo").val();
		$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());

		var newDocId ="";
		var DocNum ="";
		$("#procType").val("<%=apt005%>"); //접수

		<%
	    //T:배부대기, Y:배부처리, N:접수쪽에서 직접 접수대기 처리  || "Y".equals(enfDocVO.getDistributeYn())
		if(("N".equals(enfDocVO.getDistributeYn()) && lobCode.equals(lob008))&& det011.equals(enfRecvVO.getEnfType()))
		{
	%>
		var hwpfile = new Object();
		hwpfile.type = "body";
		hwpfile.localpath = bodyfilepath;
		$("#newDocId").val("");
	    // HWP 본문을 만든후, 업로드한다. Upload 후 DB에 저장을 위해 정보를 묶어준다. by jkkim
	    
		var result = FileManager.uploadfile(hwpfile,true);
		if (result == null) {
			return false;
		} 
		var filelength = result.length;
		var bodyinfo = "";

		for (var loop = 0; loop < filelength; loop++) {
			var file = result[loop];
		    $("#bodyFileId").val(file.fileid);
		    $("#bodyFileName").val(file.filename);
		    $("#bodyFileDisplayName").val(file.displayname);
		    $("#bodyFileSize").val(FileManager.getfilesize(bodyfilepath));    

		    bodyinfo += file.fileid + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
		    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(bodyfilepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
		    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
		}
		$("#bodyFile", "#approvalitem").val(bodyinfo);
	    //end
		<%
		}
		%>

		// DB 저장
		$.ajaxSetup({async:false});
		$.post("<%=webUri%>/app/enforce/ProcessEnfDoc.do", $("#frmDocInfo").serialize(), function(data){
			$("#bindingName", "#frmDocInfo").val(saveBingingName);
			
			if (data.dupAccept == "N") {
			//결과 페이지의 값을 받아 메세지 처리한다.
				
				if("" != data.count ) {
					newDocId = data.count;  
					DocNum = data.result;  //발번대상이 아니면 "" 이 넘어옴
					$("#newDocId").val(newDocId);
					$("#serialNumber", "#frmDocInfo").val(DocNum);

					// 1. hwp 컨트롤 (접수일자,문서번호)
					if(existField(Document_HwpCtrl, HwpConst.Form.ReceiveDate)) {
						putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate, "<%=DateUtil.getCurrentDate()%>");
						// 접수일자가 삽입되었는지 2번 더 체크
						if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate)) == "") {
							putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate, "<%=DateUtil.getCurrentDate()%>");
							if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate)) == "") {
								putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate, "<%=DateUtil.getCurrentDate()%>");
							}
						}
					}

					if (!(DocNum =="")) { //발번일때만
						if(existField(Document_HwpCtrl, HwpConst.Form.ReceiveNumber)) {
							putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber, $("#deptCategory", "#frmDocInfo").val() + "-" + DocNum);
							// 접수번호가 삽입되었는지 2번 더 체크
							if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber)) == "") {
								putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber, $("#deptCategory", "#frmDocInfo").val() + "-" + DocNum);
								if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber)) == "") {
									putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber, $("#deptCategory", "#frmDocInfo").val() + "-" + DocNum);
								}
							}
						}
					}

					// 2.결재파일 저장
					if (bodyType == "hwp") {
						Document_HwpCtrl.SaveAs(FileManager.getlocaltempdir() + $("#bodyFileName").val() , "HWP", "lock:FALSE");
					} else if (bodyType == "doc") {
						saveHwpDocument(Document_HwpCtrl,FileManager.getlocaltempdir() + $("#bodyFileName").val(),"FALSE");
					}

					// 문서조회모드
					changeEditMode(Document_HwpCtrl, 0, false);

					// 3.파일 업데이트 업로드(본문)
					uploadBodyFile();		

					chargerButton();  //접수시 담당지정으로버튼변경

				} else {
					alert("<spring:message code='approval.result.msg.acceptfail'/>");
				}	
			} else {	// 이미 접수된 문서
				alert("<spring:message code='approval.result.msg.already.accept'/>");
				closeAppDoc();
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
		retrycount = 0;  //파일 업로드 실패시 재시도 (기본 5번)
		
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
		filelist = FileManager.uploadbody(file, true ); //업로드 성공후 nextprocess 호출됨.
	 	
			
	}	
		
	// 파일 업로드 처리후 프로세스(접수시)
	function nextprocess(filelist){
		// 편철 다국어 추가
		$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());
		
		var newDocId = $("#newDocId").val();
	
		var file = new Array();
		if (!(filelist instanceof Array)) {
			file[0] = filelist;
		} else {
			file = filelist; 
		}		
		$("#bodyFileId").val(file[0].fileid);
		$("#bodyFileName").val(file[0].filename);

		// 1. DB 저장
		var result = false;
		if(newDocId != ""){
			$.post("<%=webUri%>/app/enforce/updateBodyFileInfo.do", $("#frmDocInfo").serialize(), function(data){
				$("#bindingName", "#frmDocInfo").val(saveBindingName);
				
				result = true;
				
			},'json').error(function(data) {
				var context = data.responseText;
				if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
					alert("<spring:message code='common.msg.include.badinformation'/>");
				} else {
					alert("<spring:message code='common.msg.include.badinformation'/>");
				}
			});
		}

		if(result){
			//접수완료시 담당자선택창 띄움
			if (docInfoAccept == "Y") {
				alert("<spring:message code='approval.result.msg.acceptok'/>");
				openEnfLine('I');
				docInfoAccept = "N";
				
			}	
		}
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
		//의견 및 암호입력
		popOpinion("acceptApprovalProcess","<%=accapprovalBtn%>","Y"); 
	}

	// 접수시 담당처리(결재대기함 호출) 
	function acceptApprovalCallAppWaitOk() {
		//의견 및 암호입력
		popOpinion("acceptApprovalProcessCallAppWait","<%=accapprovalBtn%>","Y"); 
	}
    	
	// 접수시 담당처리 
	function acceptApprovalProcess(popComment) {
		$("#comment").val(popComment);
		setTimeout(AfterAcceptApprovalProcess,100);
	}

	
	function AfterAcceptApprovalProcess() {
		// 편철 다국어 추가
		$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());
		
		var newDocId ="";
 		var DocNum ="";
		$("#procType").val("<%=apt005%>"); //접수	

		<%
	    //T:배부대기, Y:배부처리, N:접수쪽에서 직접 접수대기 처리  || "Y".equals(enfDocVO.getDistributeYn())
		if(("N".equals(enfDocVO.getDistributeYn()) && lobCode.equals(lob008))&& det011.equals(enfRecvVO.getEnfType()))
		{
	%>
		// Hwp 본문으로 파일 만들기 by jkkim
		var hwpfile = new Object();
		hwpfile.type = "body";
		hwpfile.localpath = bodyfilepath;
		$("#newDocId").val("");
	    // HWP 본문을 만든후, 업로드한다. Upload 후 DB에 저장을 위해 정보를 묶어준다. by jkkim
		var result = FileManager.uploadfile(hwpfile,true);
		if (result == null) {
			return false;
		} 
		var filelength = result.length;
		var bodyinfo = "";

		for (var loop = 0; loop < filelength; loop++) {
			var file = result[loop];
		    $("#bodyFileId").val(file.fileid);
		    $("#bodyFileName").val(file.filename);
		    $("#bodyFileDisplayName").val(file.displayname);
		    $("#bodyFileSize").val(FileManager.getfilesize(bodyfilepath));    

		    bodyinfo += file.fileid + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
		    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(bodyfilepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
		    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
		}
		$("#bodyFile", "#approvalitem").val(bodyinfo);
		//end
		<%
		}
		%>

		var result = false;	
		$.post("<%=webUri%>/app/enforce/ProcessEnfApproval.do", $("#frmDocInfo").serialize(), function(data){
			$("#bindingName", "#frmDocInfo").val(saveBindingName);
			
			//결과 페이지의 값을 받아 메세지 처리한다.
				if("" != data.count ) {  
					newDocId = data.count;  
					DocNum = data.result;   //발번대상이 아니면 "" 이 넘어옴
					$("#newDocId").val(newDocId);
					 
					$("#serialNumber", "#frmDocInfo").val(DocNum);
					// 1. hwp 컨트롤 (접수일자,문서번호)
					if(existField(Document_HwpCtrl, HwpConst.Form.ReceiveDate)) {
						putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate, "<%=DateUtil.getCurrentDate()%>");
						// 접수일자가 삽입되었는지 2번 더 체크
						if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate)) == "") {
							putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate, "<%=DateUtil.getCurrentDate()%>");
							if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate)) == "") {
								putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate, "<%=DateUtil.getCurrentDate()%>");
							}
						}
					}
					if (!(DocNum =="")) { //발번일때만
						if(existField(Document_HwpCtrl, HwpConst.Form.ReceiveNumber)) {
							putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber, $("#deptCategory", "#frmDocInfo").val() + "-" + DocNum);
							// 접수번호가 삽입되었는지 2번 더 체크
							if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber)) == "") {
								putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber, $("#deptCategory", "#frmDocInfo").val() + "-" + DocNum);
								if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber)) == "") {
									putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber, $("#deptCategory", "#frmDocInfo").val() + "-" + DocNum);
								}
							}
						}
					}

					// 2.결재파일 저장
					if (bodyType == "hwp") {
						Document_HwpCtrl.SaveAs(FileManager.getlocaltempdir() + $("#bodyFileName").val() , "HWP", "lock:FALSE");
					} else if (bodyType == "doc") {
						saveHwpDocument(Document_HwpCtrl,FileManager.getlocaltempdir() + $("#bodyFileName").val(),"FALSE");
					}
					
					// 문서조회모드
					changeEditMode(Document_HwpCtrl, 0, false);
					// 3.파일 업데이트 업로드(본문)
					docInfoAccept = "N";
					uploadBodyFile();		
					alert("<spring:message code='approval.result.msg.enfapprovalok'/>");
					pubReaderSaveYn = "Y";
					pubReaderButton();  //담당접수시 공람자지정모드로 변경
					
					result = true;
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

		if(result){
			<% if("Y".equals(opt371)){ %>
				if($("#publicPost", "#frmDocInfo").val() == "" && $("#pubReader", "#frmDocInfo").val() == "" 
					&& $("#lobCode").val() == "<%=lob008%>"){
	                
	            	afterPubReaderYn = "Y";
	            	getpubReader();
	            }
       	 	<% } %>
		}	

	}

	// 접수시 담당처리 (결재대기함 호출)
	function acceptApprovalProcessCallAppWait(popComment) {
		$("#comment").val(popComment);
		setTimeout(AfterAcceptApprovalProcessCallAppWait,100);
	}
	
	function AfterAcceptApprovalProcessCallAppWait() {
		// 편철 다국어 추가
		$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());
		
		var newDocId ="";
 		var DocNum ="";
		$("#procType").val("<%=apt005%>"); //접수		
		$.post("<%=webUri%>/app/enforce/ProcessEnfApprovalCallAppWait.do", $("#frmDocInfo").serialize(), function(data){
			$("#bindingName", "#frmDocInfo").val(saveBindingName);
			
			//결과 페이지의 값을 받아 메세지 처리한다.
				if("" != data.count ) {
					alert("<spring:message code='approval.result.msg.enfapprovalok'/>");
					pubReaderSaveYn = "Y";
					pubReaderButton();  //담당접수시 공람자지정모드로 변경
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
		// 편철 다국어 추가
		$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());
		
		$("#comment").val(popComment);
		$("#procType").val("<%=apt020%>"); //재발송요청
		// 1. DB 저장
		$.post("<%=webUri%>/app/enforce/reSendRequest.do", $("#frmDocInfo").serialize(), function(data){
			$("#bindingName", "#frmDocInfo").val(saveBindingName);
			
			//결과 페이지의 값을 받아 메세지 처리한다.
				if("1" == data.result ) {  
					alert("<spring:message code='approval.result.msg.resendrequestok'/>");
					afterButton();

					// 모든처리후 문서닫음 start 20110804 
					<% if ("Y".equals(opt357)) { %> 
					    exitAppDoc(); 
					 <% } %>   
					// 모든처리후 문서닫음 end 20110804 
					
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
    	// 편철 다국어 추가
    	$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());
        
        $("#opinion").val(procOpinion);
        $.post("<%=webUri%>/app/enforce/processPreRead.do", $("#frmDocInfo").serialize(), function(data){
        	$("#bindingName", "#frmDocInfo").val(saveBindingName);
        	
            if (data.result) {
            	alert("<spring:message code='approval.result.msg.prereadok'/>");
            	afterButton();

				// 모든처리후 문서닫음 start 20110804 
				<% if ("Y".equals(opt357)) { %> 
				    exitAppDoc(); 
				 <% } %>   
				// 모든처리후 문서닫음 end 20110804 
				
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
    	// 편철 다국어 추가
    	$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());
        
        if (confirm("<spring:message code='enforce.msg.pubreader'/>")) {
            $.post("<%=webUri%>/app/appcom/processPubReader.do", $("#frmDocInfo").serialize(), function(data){
            	$("#bindingName", "#frmDocInfo").val(saveBindingName);
            	
                if (data.result =='success') {

                	alert("<spring:message code='appcom.msg.success.pubread'/>");
                	afterButton();

    				// 모든처리후 문서닫음 start 20110804 
    				<% if ("Y".equals(opt357)) { %> 
    				    exitAppDoc(); 
    				 <% } %>   
    				// 모든처리후 문서닫음 end 20110804 
    				
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
    
  	//다음문서 open
    function goNextDoc(){
    	moveToNext("Y");
    }
    
    //  담당자 확인
    function approvalDoc() {
        //if (confirm("<%=messageSource.getMessage("enforce.msg.approvaldoc", null, langType)%>")) {
            popOpinion("procApprovalDoc", "<%=approaldocBtn%>", "Y");   
       // }     
    }

	function procApprovalDoc(procOpinion) {
		$("#opinion").val(procOpinion);
		setTimeout(afterProcApprovalDoc,100);
	}
    //  담당자 확인 처리
    function afterProcApprovalDoc() {
    	// 편철 다국어 추가
    	$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());

        var result = false;
    	$.post("<%=webUri%>/app/enforce/processFinalApproval.do", $("#frmDocInfo").serialize(), function(data){
    		$("#bindingName", "#frmDocInfo").val(saveBindingName);
        	
            if (data.result =='OK') {
            	alert("<%=approaldocMsg%>");
            	afterButton();
            	result = true;
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

		if(result){
			// 모든처리후 문서닫음 start 20110804 
			<% if ("Y".equals(opt357)) { %> 
			    exitAppDoc(); 
			 <% } %>   
			// 모든처리후 문서닫음 end 20110804 
		}		
    }


    
    
    //  문서회수 
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

	// 문서 회수 처리
	function procRetrieveDoc(){
		// 편철 다국어 추가
		$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());
		
		$.post("<%=webUri%>/app/enforce/processDocRetrieve.do", $("#frmDocInfo").serialize(), function(data){
			$("#bindingName", "#frmDocInfo").val(saveBindingName);
			
            if (data.result=="OK") {
            	alert("<spring:message code='enforce.msg.retrievedocok'/>");
            	afterButton();

				// 모든처리후 문서닫음 start 20110804 
				<% if ("Y".equals(opt357)) { %> 
				    exitAppDoc(); 
				 <% } %>   
				// 모든처리후 문서닫음 end 20110804 
				
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
    	// 편철 다국어 추가
    	$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());
        
    	$("#opinion").val(procOpinion);
        $.post("<%=webUri%>/app/enforce/processEnfDocReject.do", $("#frmDocInfo").serialize(), function(data){
        	$("#bindingName", "#frmDocInfo").val(saveBindingName);
        	
            if (data.result =="OK") {
            	alert("<%=rejectDocMsg%>");
            	afterButton();

				// 모든처리후 문서닫음 start 20110804 
				<% if ("Y".equals(opt357)) { %> 
				    exitAppDoc(); 
				 <% } %>   
				// 모든처리후 문서닫음 end 20110804 
				
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



    function reloadBody() {
   		openHwpDocument(Document_HwpCtrl, bodyfilepath);
    	changeEditMode(Document_HwpCtrl, 0, false);
    	moveToPos(Document_HwpCtrl, 2);

    	//20120604 본문저장 후 리로드시 본문내 의견표시 초기화 kj.yang
    	deleteOpinionTbl(Document_HwpCtrl);
    }

    
 	// 저장
    function saveAppDoc(filepath) {
    	if (bodyType == "hwp" || bodyType == "doc") {
	    	var filename = escapeFilename($("#title").val() + "."+bodyType);
	    	FileManager.setExtension(bodyType);
			// 본문저장 시 경로값이 없고 본문/첨부 저장 시 경로값이 셋팅되어 들어온다.
			if (typeof(filepath) == "undefined") {
				filepath = FileManager.selectdownloadpath(filename);
			} else if(filepath == "-1") {	//본문/첨부 저장 취소 시
				return false;
			} else {
				filepath += "\\" + filename;
			}
	
	    	if (filepath != "") {
				if(bodyType == "hwp"){
		    		var transferYn = $("#transferYn").val();
					if (transferYn == "N") {
						// 서명정보를 제거한다.
						//clearApprover(Document_HwpCtrl); //by 0325 제거 안하게 바꿔줌
						// 관인(서명인)/생략인정보를 제거한다. 
						//clearStamp(Document_HwpCtrl); //by 0325 제거 안하게 바꿔줌
					} else {//이관후 문서 확인필요? by 0325
						clearApproverSNT(Document_HwpCtrl);
						clearStampSNT(Document_HwpCtrl);
					}
				}
				
		    	// 문서 처음으로 이동하기
		    	moveToPos(Document_HwpCtrl, 2);
	
	    		if (saveHwpDocument(Document_HwpCtrl, filepath)){
	                var msg = "<spring:message code='approval.msg.success.savebody'/>";
	    			alert(msg.replace("%s", filepath));
					
	    		}else{
	    			alert("<spring:message code='approval.msg.fail.savebody'/>");
	    	    }
	    		if(bodyType == "hwp"){
					reloadBody();
	    		}
	    	}
    	} else if (bodyType == "html") {
    		filename = escapeFilename($("#title").val() + ".html");
    		downloadHtmlBodyContent(filename, "");
    	}	    
    }

    function clearApproverSNT(hwpCtrl) {
    	try {
    		if (hwpCtrl != null) {
    			// Consider
    			var considercount = getApbCountSNT(hwpCtrl);
    			for (var loop = 1; loop <= considercount; loop++) {
    				clearImage(hwpCtrl, "apb" + loop + "_sign");
    			}
    			// Assistance
    			var assistancecount = getAsCountSNT(hwpCtrl);
    			for (var loop = 1; loop <= assistancecount; loop++) {
    				clearImage(hwpCtrl, "as" + loop + "_sign");
    			}
    			// 구 기록물
    			var considercount = getApCountSNT(hwpCtrl);
    			for (var loop = 1; loop <= considercount; loop++) {
    				clearImage(hwpCtrl, "ap" + loop + "_sign");
    			}
    		} else {
    			errormessage();
    		}
    	} catch (error) {
    		errormessage("clearApproverSNT");
    	}
    }

    function clearStampSNT(hwpCtrl) {
    	try {
    		if (hwpCtrl != null) {
    			if (!existField(hwpCtrl, "seal"))
    				return;
    			clearImage(hwpCtrl, "seal");
    		} else {
    			errormessage();
    		}
    	} catch (error) {
    		errormessage("clearStampSNT");
    	}	
    }

    function getApbCountSNT(hwpCtrl) {
    	try {
    		if (hwpCtrl != null) {
    			var loop = 1;		// start with 1.
    			while(true) {		// clear Consider Cell
    				if (!existField(hwpCtrl, "apb" + loop + "_sign")) {
    					break;
    				}
    				loop++;
    			}

    			return loop - 1;
    		} else {
    			errormessage();
    		}
    	} catch (error) {
    		errormessage("getApbCountSNT");
    	}
    }

    // get Assistance Count
    // argument[0] : HwpCtrl Object
    function getAsCountSNT(hwpCtrl) {
    	try {
    		if (hwpCtrl != null) {
    			var loop = 1;		// start with 1.
    			while(true) {		// clear Consider Cell
    				if (!existField(hwpCtrl, "as" + loop + "_sign")) {
    					break;
    				}
    				loop++;
    			}

    			return loop - 1;
    		} else {
    			errormessage();
    		}
    	} catch (error) {
    		errormessage("getAsCountSNT");
    	}
    }

    function getApCountSNT(hwpCtrl) {
    	try {
    		if (hwpCtrl != null) {
    			var loop = 1;		// start with 1.
    			while(true) {		// clear Consider Cell
    				if (!existField(hwpCtrl, "ap" + loop + "_sign")) {
    					break;
    				}
    				loop++;
    			}

    			return loop - 1;
    		} else {
    			errormessage();
    		}
    	} catch (error) {
    		errormessage("getApCountSNT");
    	}
    }
 	
<%	if (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322)) { %>																
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

<%if (lobCode.equals(lob011) || lobCode.equals(lob012) || lobCode.equals(lol001) || lobCode.equals(lol003)){ %>

	// 사내우편
	function sendMail() {
		var title = escapeFilename($("#title", "#approvalitem").val());
	
		var transferYn = $("#transferYn", "#approvalitem").val();
		if (transferYn == "N") {
			// 서명정보를 제거한다.
			clearApprover(Document_HwpCtrl);
			// 관인(서명인)/생략인정보를 제거한다.
			clearStamp(Document_HwpCtrl);
		} else {
			clearApproverSNT(Document_HwpCtrl);
			clearStampSNT(Document_HwpCtrl);
		}
		// 문서 처음으로 이동하기
		moveToPos(Document_HwpCtrl, 2);
	
		var filepath = FileManager.getlocaltempdir() + "HwpBody_" + UUID.generate() + ".hwp";
		var bodyfile = "";
		if (saveHwpDocument(Document_HwpCtrl, filepath)) {
			var hwpfile = new Object();
			hwpfile.type = "body";
			hwpfile.localpath = filepath;
			result = FileManager.uploadfile(hwpfile);
			if (result.length > 0) {
				bodyfile = result[0].filename;
			}
	
			reloadBody();
		}
		
		var attachfile = replaceAll($("#attachFile", "#approvalitem").val(), "'", String.fromCharCode(3));
		mailWin = openWindow("mailWin", "<%=webUri%>/app/approval/writeInnerMail.do?title=" + title.replace("ㆍ", " ") + "&bodyfile=" + bodyfile + "&attachfile=" + attachfile, 800, 550, "yes");
	}
<% } %>

	//본문/첨부 저장
	var saveType = "e";
	function saveAllAppDoc(type) {
	
		saveType = type;
		var count = 0;
		var attachid = "";
		var checkname = "attach_cname";
		var checkboxes = document.getElementsByName(checkname);
		var filelist = new Array();
		if (checkboxes != null && checkboxes.length != 0) {
			for (var loop = checkboxes.length - 1; loop >= 0; loop--) {
				attachid = checkboxes[loop].id;
				attachid = attachid.replace("attach_cid_", "attach_");
				var attach = $("#" + attachid);
				var file = new Object();
				file.fileid = attach.attr("fileid");
				file.filename = attach.attr("filename");
				file.displayname = attach.attr("displayname");
				file.gubun = "";
				file.docid = "";
				file.type = "save";
				filelist[count++] = file;
			}
		}
	
		if (count == 0) {
			var filename = escapeFilename($("#title").val() + ".hwp");
			if (saveType == "d") { 
				saveDistributeBody("", filename, false);
			} else {
				saveAppDoc();
			}
		} else {
			FileConst.Variable.Distribute = "Y";
			FileManager.download(filelist);
		}
	}
	
	function saveunified(filepath) {
		var filename = escapeFilename($("#title").val() + ".hwp");
		if (saveType == "d") { 	
			saveDistributeBody(filepath, filename, true);
		} else {
			saveAppDoc(filepath);
		}
	}
	
	function saveDistributeBody(filepath, filename, isall) {
		var fullpath = "";
		if (filepath == "") {
			fullpath = FileManager.selectdownloadpath(filename);
		} else {
			fullpath = filepath + "\\" + filename;
		}
	
		saveHwpDocument(Document_HwpCtrl, fullpath, true);
		makeDistributeDocument(Document_HwpCtrl, false, false);
		openHwpDocument(Document_HwpCtrl, bodyfilepath);
		if (existField(Document_HwpCtrl, HwpConst.Form.Title)) {
			moveToPos(Document_HwpCtrl, HwpConst.Form.Title);
		} else {
			moveToPos(Document_HwpCtrl, 26);
			moveToPos(Document_HwpCtrl, 2);
		}

		//20120604 본문/첨부저장 후 리로드시 본문내 의견표시 초기화 kj.yang
    	deleteOpinionTbl(Document_HwpCtrl);
		
		if (isall) {
			alert("<spring:message code='approval.msg.success.saveall'/>".replace("%s", filepath));
		} else {
			alert("<spring:message code='approval.msg.success.savebody'/>".replace("%s", fullpath));
		}
	}

    // 인쇄
    function printAppDoc() {
    	if (bodyType == "hwp" || bodyType == "doc") {
    		printHwpDocument(Document_HwpCtrl);
    	} else if (bodyType == "html") {
    		printHtmlDocument("");
    	}
    }

     
    // 강제닫기
	function exitAppDoc() {
		clearPopup();
		
	    //새로고침(메인리스트)
	    try {
	        if (!prevnext) { 
	            if (opener != null && opener.listRefresh != null 
	                    && opener.curLobCode != null && opener.curLobCode == $("#lobCode").val()) {
	                opener.listRefresh();
	            }
	        }
	    } catch (error) {
	    }
		window.close();
	}
    // 닫기
	function closeAppDoc() {
		if (!confirm("<spring:message code='approval.enforce.msg.confirmclose'/>")) {
			return;
		}	
		clearPopup();

	    //새로고침(메인리스트)
	    try {
	        if (!prevnext) { 
	            if (opener != null && opener.listRefresh != null 
	                    && opener.curLobCode != null && opener.curLobCode == $("#lobCode").val()) {
	                opener.listRefresh();
	            }
	        }
	    } catch (error) {
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
		if (attachWin != null && !attachWin.closed  ) {
			attachWin.close();
		}
		if (bindWin != null && !bindWin.closed  ) {
			bindWin.close();
		}
		if (chargerPopupWin != null && !chargerPopupWin.closed  ) {//공람자
			chargerPopupWin.close();
		}
		if (mailWin != null && !mailWin.closed)
			mailWin.close();
	}

    //접수경로 값 set
    function setEnfLine(enfLine) {
        enfLines = enfLine;
        document.getElementById("enfLines").value = enfLine;
		var result = false;
        if(g_lineOpenType =='I'){
            //alert('접수후담당자지정');
            result = true;
        }
        //담당재지정
        if(g_lineOpenType =='C'){
        	setTimeout(reDefineOpinion,10);
        	//reDefineOpinion();
        }

        if(g_lineOpenType =='I' && result == true){
            
	        <% if("Y".equals(opt371)){ %>
		        if($("#publicPost", "#frmDocInfo").val() == "" && $("#pubReader", "#frmDocInfo").val() == "" 
		             && $("#lobCode").val() == "<%=lob008%>"){
		            
		        	afterPubReaderYn = "Y";
		        }
	    	<% } %>

	    	setTimeout(defineEnfLine,100);
        }
    }


    //담당자지정처리
    function defineEnfLine() {
    	// 편철 다국어 추가
    	$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());

    	if($("#newDocId").val()!="") { // 문서접수시 자동으로 담당자 지정하면 접수시생성된 새 docId 로 처리해야함.
			$("#docId").val($("#newDocId").val());
    	}
    	var result = false;    
        $.post("<%=webUri%>/app/enforce/enfline/insertEnfLine.do", $("#frmDocInfo").serialize(), function(data){
        	$("#bindingName", "#frmDocInfo").val(saveBindingName);
        	
            if (data.result =='OK') {
				
            	alert("<spring:message code='approval.result.msg.approverfixok'/>");

				pubReaderSaveYn = "Y";
				pubReaderButton();  //담당접수시 공람자지정모드로 변경					
				// 담당자 지정후 공람자지정을 위해 닫지 않음
				result = true;
				
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

		if(result){			

			if(afterPubReaderYn == "Y"){
				getpubReader();
			}
		}	
    }


    
    //담당재지정 의견 오픈
    function reDefineOpinion(){
        popOpinion("openReDefineLine", "<%=processorRefixBtn%>", "Y" );
    }

    
    //담당자재지정 오픈
    function openReDefineLine(redefinopinion){

        $("#opinion").val(redefinopinion);
        reDefineEnfLine();
    }

    
    //담당재지정처리
    function reDefineEnfLine() {
    	// 편철 다국어 추가
    	$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());

        if($("#newDocId").val()!="") { 
            $("#docId").val($("#newDocId").val());
        }
            
        $.post("<%=webUri%>/app/enforce/enfline/reDefineEnfLine.do", $("#frmDocInfo").serialize(), function(data){
        	$("#bindingName", "#frmDocInfo").val(saveBindingName);
        	
            if (data.result =='OK') {
                alert("<spring:message code='approval.result.msg.approverfixok'/>");

                pubReaderSaveYn = "Y";
                pubReaderButton();  //담당접수시 공람자지정모드로 변경

                // 담당자 지정후 공람자지정을 위해 닫지 않음
            }
            else{
                alert("<spring:message code='enforce.msg.approvalRedefine'/>");
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

		//enfLines = document.getElementById("enfLines").value;
    	enfLines =  $("#enfLines", "#frmDocInfo").val();
        return enfLines;
    }

   //담당자 지정 오픈
    function openEnfLine(type){
        
        g_lineOpenType = type;
        var groupYn="N";//접수결재경로그룹 설정 변수(변경불가)
        if(type =='I'){ 
            groupYn ='Y';
        }
        if(type =='C'){// 담당재지정
            groupYn ='Y';
            document.getElementById("enfLines").value = document.getElementById("enfLines").value.replace('<%=apt003%>','');
           // alert(document.getElementById("enfLines").value);
        }
        if(type == "U"){
        	linePopupWin = openWindow("linePopupWin", "<%=webUri%>/app/enforce/enfLine/selectEnfLine.do?groupYn="+groupYn+"&opentype="+type, 650, 550);
        }else{
        	linePopupWin = openWindow("linePopupWin", "<%=webUri%>/app/approval/ApprovalPreReader.do?groupYn="+groupYn, 650, 550);
        } 
 

    }


    //공람자 오픈
    function getpubReader(type){

        g_chargerOpenType = type;
        var url;
        var width;
        var height;
        var docId = $("#docId").val();
        if(type =='search'){  
        	width =700;
        	height=450;
            
            if($("#newDocId").val() !="") {  // 접수시에는 새로운 docId를 사용해야함
        	   docId = $("#newDocId").val();
            }   
            url = "<%=webUri%>/app/appcom/listPubReader.do?docId="+docId+"&lobCode=<%=lobCode%>&usingType=<%=usingType%>";
        }
        else{

            if($("#newDocId").val() !="") {  // 접수시에는 새로운 docId를 사용해야함
            	$("#docId").val($("#newDocId").val());
            }
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
        if (pubReaderSaveYn=="Y") {  //공람지정버튼 클릭시 공람자정보 DB저장여부(접수및 담당지정 완료시) jth8172 20110805
            //공람자목록을 저장
			setTimeout(insertPubReader, 100);  // 공람지정창에 리턴 메세지 전송 후 insertPubReader 호출
        }    
    }

    //공람자 get
    function getPubReader() {
        return $("#pubReader").val();
    }


  	//공람자 등록(접수후 담당지정시)  jth8172 20110805
    function insertPubReader(){
    	// 편철 다국어 추가
    	$("#bindingName", "#frmDocInfo").val($("#bindingResourceId", "#frmDocInfo").val());
        
    	if(afterPubReaderYn == "Y" && $("#pubReader").val() == ""){
    		alert("<spring:message code='list.list.msg.noPubReaderInfo'/>");
			getpubReader();
    	}else{
        	 
	  		$.post("<%=webUri%>/app/appcom/insertPubReader.do", $("#frmDocInfo").serialize() , function(data){
	  			$("#bindingName", "#frmDocInfo").val(saveBindingName);
	  			
	            if(data.result =="success"){
	                alert("<spring:message code='appcom.result.msg.pubreaderok'/>");
	                 
	                //공람자 저장후에도 화면은 그대로 둠. 
               		<% if ("Y".equals(opt371) && "Y".equals(opt357) && lobCode.equals(lob008)){ %>
						exitAppDoc(); 
					<% } %>  	
	                
	                
	            }else{
	                alert("<spring:message code='list.list.msg.fail.insertPubReader'/>");
	            }
	        }, 'json').error(function(data) {
	    		alert("<spring:message code='list.list.msg.fail.insertPubReader'/>");
		
	    	});  
	    	
    	}
    }
    
    //문서관리정보수정(접수문서)-- 사용안함
    function updateDocMngInfo(){

         //팝업
        popupWin = openWindow("popupWin", "<%=webUri%>/app/appcom/enf/selectDocMngInfo.do?docId="+frmDocInfo.docId.value, 500, 300);
    } 

    
    //의견 및 결재암호 팝업
    function popOpinion(returnFunction, btnName, opinionYn) {

    	var width = 500;
<% if ("1".equals(opt301)) { %> // 암호입력이면
    	var height = 240;
<% } else { %>  
		var height = 200;
<% } %>  
        
    	if(opinionYn=="N") {
<% if ("1".equals(opt301)) { %> // 암호입력이면
    		height = 140;
<% } else { %>  
    		height = 170;
<% } %>  
    	}
    	
    	var top = (screen.availHeight - height) / 2;
    	var left = (screen.availWidth - width) / 2;
    	
    	popupWin = window.open("", "popupWin", "width=" + width + ",height=" + height + ",top=" + top + ",left=" + left + ",scrollbars=no,status=yes,resizable=no"); 

    	$("#returnFunction").val(returnFunction);
    	$("#btnName").val(btnName);
    	$("#opinionYn").val(opinionYn);
    	$("#frmDocInfo").attr("target", "popupWin");
    	$("#frmDocInfo").attr("action", "<%=webUri%>/app/enforce/popupOpinion.do");
    	$("#frmDocInfo").submit();
    	setTimeout("popupfocus()",100);
    } 
    
	function popupfocus() {
	    popupWin.focus();
	}

	// 관리자용 스크립트-------
	// 현재 안건번호
	function getCurrentItem() {
		return "";
	}  

	//필요시사용, 포탈홈 포틀릿에서 호출후 닫을때 새로고침 기능 필요시 사용하는 함수, kj.yang, 20120425 S
	/*
	function callRefreshPortlet(){
		try{
			if(($("#B_O201111241614557282311", opener.parent.document).val() != null || $("#B_O201110111741096728791", opener.parent.document).val() != null)
					&& ( $("#B_O201107312148255571630", opener.parent.document).val() != null || $("#B_O201110122147391389421", opener.parent.document).val() != null)
					&& opener.parent != null && opener.parent.updatePortletBody != null){
				
				if($("#B_O201111241614557282311", opener.parent.document).val() != null){
					//개발 WAS
					opener.parent.updatePortletBody('B_O201111241614557282311','C201107311833056351603','O201111241614557282311' , 'win_O201111241614557282311','view');
					opener.parent.updatePortletBody('B_O201107312148255571630','C201107311936459642252','O201107312148255571630' , 'win_O201107312148255571630','view');
				}
				
				if($("#B_O201110111741096728791", opener.parent.document).val() != null){
					//운영 WAS
					opener.parent.updatePortletBody('B_O201110111741096728791','C201107311833056351603','O201110111741096728791' , 'win_O201110111741096728791','view');
					opener.parent.updatePortletBody('B_O201110122147391389421','C201110122146300349373','O201110122147391389421' , 'win_O201110122147391389421','view');
				}
				
			}
		} catch (error) {
		}
	}
	*/
	//필요시사용, 포탈홈 포틀릿에서 호출후 닫을때 새로고침 기능 필요시 사용하는 함수, kj.yang, 20120425 E
	
	
<% if(lobCode.equals(lob091) || lobCode.equals(lob092)){ %>
	//	발송정보조회
	function sendInfo() {

	 	$("#sendInfoCompId").val($("#compId").val());
		$("#sendInfoDocId").val("<%=appDocVO.getDocId()%>");
		$("#sendInfoComment").val($("#comment").val());
		
		var editFlag = "N";  // 발송상황조회 발송회수, 재발송 가능유무		
		$("#sendInfoEditFlag").val(editFlag);
		
		clearPopup();
		var top = (screen.availHeight - 460) / 2;
		var left = (screen.availWidth - 800) / 2;
		popupWin = window.open("", "popupWin",
	            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=800,height=460," +
		        "scrollbars=no,resizable=no"); 
		$("#frmSendInfo").attr("action","<%=webUri%>/app/approval/sendInfo.do");
		$("#frmSendInfo").submit();
		
	}
<% } %>

var openerIndex = 4;
function closewin()
{
    if(opener){
    	openerIndex = openerIndex == 4 ? opener.approvalTabVal != null ? opener.approvalTabVal : openerIndex : openerIndex;
   		if(opener.approvalTabClick!=null){
   			opener.approvalTabClick(openerIndex+1);
       		if(opener.getApprovalList!=null){
       			opener.getApprovalList(openerIndex);
       		}
    	}
   	}
}

</script>
</head>  
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="closewin();" onbeforeunload="closewin()" onunload="closewin()">
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>

<acube:outerFrame>


	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td><span class="pop_title77"><spring:message code='approval.title.select.approval'/></span></td>
<% if(lob012.equals(lobCode) && !"N".equals(autoNextDocYn)){ %>							
			<td width="50%" align="right">
				<acube:buttonGroup align="right">
					<acube:button onclick="moveToPrevious();return(false);" value="<%=previousBtn%>" type="2" />
					<acube:space between="button" />
					<acube:button onclick="moveToNext();return(false);" value="<%=nextBtn%>" type="2" />
				</acube:buttonGroup>
			</td>
<% } %>	  
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td align="left" width="20%">
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
				<% if (lobCode.equals(lob007)) { %>
					<!-- 배부대기함 -->
					<acube:buttonGroup align="right">
					<% if (!"".equals(sendOpinion)) { %>
					<acube:button id="btnOpinion" onclick="javascript:viewSendOpinion();return(false);"  value="<%=sendOpinionBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>
					
					<% if (enf110.equals(recvState) && !"".equals(reDistOpinion)) { %>
					<acube:button id="btnOpinion" onclick="javascript:viewReDistOpinion();return(false);"  value="<%=reDistOpinionBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>
					
					<% if (enf110.equals(recvState)) { %>
					<acube:button id="btnReDist" onclick="javascript:distribute('R');return(false);" value="<%=reDistributeBtn%>" type="2" class="gr" />
	                <acube:space between="button" />
					<acube:button id="btnNoDistribute" onclick="javascript:distribute('N');return(false);"  value="<%=noDistributeBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } else { %>
					<acube:button id="btnDist" onclick="javascript:distribute('D');return(false);" value="<%=distributeBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% if ("TT".equals(enfDocVO.getDistributeYn())) { %> 
					<acube:button id="btnReDistReq" onclick="javascript:distribute('Q');return(false);" value="<%=reDistRequestBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } else {
							if(!det011.equals(enfRecvVO.getEnfType())){//기관간 유통문서의 경우, 반송처리하지 않음.
					%>
					<acube:button id="btnReturnDoc" onclick="javascript:returnDoc();return(false);" value="<%=returnBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%     }else{
					    		 if ("Y".equals(opt351)) { 
					 %>
									<acube:button id="btnReSend" onclick="javascript:reSend();return(false);" value="<%=reSendBtn%>" type="2" class="gr" />
	                				<acube:space between="button" />
					 <%
					    		 }
					    	} 
						}
						}
					 %>
					<acube:button onclick="selectDocInfo('Read');return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% if (!isExtWeb) { %>	
					<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	} %>									
					<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>
					<acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
					</acube:buttonGroup>
				<%} else if (lobCode.equals(lob019)) { %>
					<!-- 재배부요청함 -->
					<acube:buttonGroup align="right">
					<% if (!"".equals(reDistOpinion)) { %>
					<acube:button id="btnOpinion" onclick="javascript:viewReDistOpinion();return(false);"  value="<%=reDistOpinionBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>
					<acube:button id="btnReDist" onclick="javascript:distribute('R');return(false);" value="<%=reDistributeBtn%>" type="2" class="gr" />
	                <acube:space between="button" />
					<acube:button id="btnNoDistribute" onclick="javascript:distribute('N');return(false);"  value="<%=noDistributeBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="selectDocInfo('Read');return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% if (!isExtWeb) { %>	
					<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	} %>									
					<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>
					<acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
					</acube:buttonGroup>
                <%} else if (lobCode.equals(lob012)) { %>
                    <!-- 공람함에서문서조회시 -->
                    <acube:buttonGroup align="right">
                    <% if(pubReaderVO.getPubReadDate() ==null || ("".equals(pubReaderVO.getPubReadDate())) || ("9999-12-31 23:59:59".equals(pubReaderVO.getPubReadDate())) ){ %>
                    <acube:button id="btnPubReader" onclick="javascript:pubReader();return(false);" value="<%=pubreadBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <%}%>
                    <acube:button id="btnPubReader" onclick="javascript:getpubReader('search');return(false);" value="<%=pubreaderBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <acube:button onclick="selectDocInfo('Read');return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <% if (!isExtWeb) { %>  
                    <acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
 					<%	} %>									
					<%	if (false) { %>																
					<acube:button onclick="sendMail();return(false);" value="<%=sendMailBtn%>" type="2" class="gr" />
					<acube:space between="button" />
 					<%	} %>									
                    <acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <% } %>
                    <acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
                    </acube:buttonGroup>                    
				<% } else if (lobCode.equals(lob008)) { %>
					<!-- 접수대기함 -->
					<acube:buttonGroup align="right">
					<% if (!"".equals(sendOpinion)) { %>
					<acube:button id="btnOpinion" onclick="javascript:viewSendOpinion();return(false);"  value="<%=sendOpinionBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>
                    
					<% if("Y".equals(isNum)) {	// 발번 대상 일 경우
					    if ("Y".equals(opt355) ) {  //  채번문서 담당접수 사용불가  20110808 발번대상은 접수버튼만 20110804
					    	%>	
					<acube:button id="btnAccept" onclick="javascript:accept();return(false);" value="<%=acceptBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%   } else { %>
					<acube:button id="btnAccept" onclick="javascript:accept();return(false);" value="<%=acceptBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button id="btnAccApproval" onclick="javascript:acceptApproval();return(false);" value="<%=accapprovalBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%  }
					   } else { // 미채번시 					
					    if ("Y".equals(opt356) ) {  //  비채번문서 접수 사용불가  20110808  발번미대상은 담당접수버튼만 20110804%>
					<acube:button id="btnAccApproval" onclick="javascript:acceptApproval();return(false);" value="<%=accapprovalBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%   } else { %>
					<acube:button id="btnAccept" onclick="javascript:accept();return(false);" value="<%=acceptBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button id="btnAccApproval" onclick="javascript:acceptApproval();return(false);" value="<%=accapprovalBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%   }
					 }       %>
					 <!-- 접수전 이송 버튼 optional 처리 by jkkim 20120321 -->
					 <%if(opt401.equals("Y")){ %>
					<acube:button id="moveBtn" onclick="javascript:distribute('M');return(false);" value="<%=moveBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%}%>
					<% if ("Y".equals(enfDocVO.getDistributeYn()) || "YY".equals(enfDocVO.getDistributeYn())) { %> 
					<acube:button id="btnReDistReq" onclick="javascript:distribute('Q');return(false);" value="<%=reDistRequestBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } else {
						if(!det011.equals(enfRecvVO.getEnfType())){//기관간 유통문서의 경우, 반송처리하지 않음.
					%>
					<acube:button id="btnReturnDoc" onclick="javascript:returnDoc();return(false);" value="<%=returnBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% 
						}
					if ("Y".equals(opt351)) { %>
					<acube:button id="btnReSend" onclick="javascript:reSend();return(false);" value="<%=reSendBtn%>" type="2" class="gr" />
	                <acube:space between="button" />
					<% } %>
					<% } %>
					<acube:button onclick="selectDocInfo('Read');return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% if (!isExtWeb) { %>	
					<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	} %>									
					<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>
					<acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
					</acube:buttonGroup>

				<% } else if (lobCode.equals(lob003)) { %>
					 
                     <%
                     /*
                       -------------------------------------------------------------------------------
                                               결재대기함 - 접수, 접수확인, 이송, 반송, 재발송,선람,담당  버튼
                       -------------------------------------------------------------------------------
                     */
                     %>                     
					<acube:buttonGroup align="right">               
                    <% if (enf100.equals(docState) || enf200.equals(docState) || enf250.equals(docState)) { %>
					<% if (!"".equals(sendOpinion)) { %>
					<acube:button id="btnOpinion" onclick="javascript:viewSendOpinion();return(false);"  value="<%=sendOpinionBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>					
					<% if("Y".equals(isNum)) {	// 발번 대상 일 경우
					    if (!("Y".equals(opt355)) ) {  //  채번문서 담당접수 사용불가  20110808 발번대상은 접수버튼만 20110804
					    	%>	
					<acube:button id="btnAccApproval" onclick="javascript:acceptApproval();return(false);" value="<%=accapprovalBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%   } else { %>
					<acube:button id="btnAccept" onclick="javascript:accept();return(false);" value="<%=acceptBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%  }
					   } else { // 미채번시 					
					    if (!("Y".equals(opt356)) ) {  //  비채번문서 접수 사용불가  20110808  발번미대상은 담당접수버튼만 20110804%>
					<acube:button id="btnAccept" onclick="javascript:accept();return(false);" value="<%=acceptBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%   } else { %>
					<acube:button id="btnAccApproval" onclick="javascript:acceptApproval();return(false);" value="<%=accapprovalBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%   }
					 }       %>
					
					
					<%if(opt401.equals("Y")){ %>
					<acube:button id="moveBtn" onclick="javascript:distribute('M');return(false);" value="<%=moveBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%}%>
					<acube:button id="btnReturnDoc" onclick="javascript:returnDoc();return(false);" value="<%=returnBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% if ("Y".equals(opt351)) { %>
					<acube:button id="btnReSend" onclick="javascript:reSend();return(false);" value="<%=reSendBtn%>" type="2" class="gr" />
	                <acube:space between="button" />
                    <% } %>
                    <% } %>                    
                     
                    
                    
                    <% 
                     /*
                       -------------------------------------------------------------------------------
                                               선람대기상태 - 선람 버튼,담당자지정
                       -------------------------------------------------------------------------------
                     */
                    %>
                    <% if (enf400.equals(docState)) { %>

                    <!--  접수의견버튼 20160422 -->
                    <acube:button id="btnPreDocInfo" onclick="javascript:viewRecvOpinion();return(false);" value="접수의견" type="2" class="gr" />
                    <acube:space between="button" />
                    
                                   
                    <acube:button id="btnPreOpen" onclick="javascript:preOpen();return(false);" value="<%=preopenBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <acube:button id="btnReturnBtn" onclick="javascript:rejectDoc();return(false);" value="<%=rejectDocBtn%>" type="2" class="gr" />
                             
    				<acube:space between="button" />
                    <acube:button id="btnProcessorfix" onclick="javascript:openEnfLine();return(false);" value="<%=processorfixBtn%>" type="2" class="gr" />
                    
                     <%}%>
                   <acube:space between="button" />
                    
					<% 
                     /*
                       -------------------------------------------------------------------------------
                                               선람, 담당대기 상태 - 경로 수정 버튼
                       -------------------------------------------------------------------------------
                     */
                    %>
                    <% if ( enf400.equals(docState) || enf500.equals(docState) ) { %>
					<acube:button id="updateEnfLineBtn" onclick="javascript:openEnfLine('U');return(false);" value="<%=applineBtn %>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>                    
                    <% 
                     /*
                       -------------------------------------------------------------------------------
                                               담당대기상태 - 담당자지정, 담당확인, 반려 버튼
                       -------------------------------------------------------------------------------
                     */
                    %>
                    <% if (enf500.equals(docState)) { %>
                    
                    <!--  접수의견버튼 20160422 -->
                    <acube:button id="btnPreDocInfo" onclick="javascript:viewRecvOpinion();return(false);" value="접수의견" type="2" class="gr" />
                    <acube:space between="button" />
                    
                    <acube:button id="btnApprovalDoc" onclick="javascript:approvalDoc();return(false);" value="<%=approaldocBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                   
                    <acube:button id="btnProcessorfix" onclick="javascript:openEnfLine();return(false);" value="<%=processorfixBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    
                    <acube:button id="btnReturnBtn" onclick="javascript:rejectDoc();return(false);" value="<%=rejectDocBtn%>" type="2" class="gr" />
                    <% } %> 
                    <acube:space between="button" />

                    <% 
                     /*
                       -------------------------------------------------------------------------------
                                               담당자지정대기(반려문서포함)상태 - 담당자지정 버튼
                       -------------------------------------------------------------------------------
                     */
                    %>
                     <% if (enf300.equals(docState)|| enf310.equals(docState)) { %>
                    
                    <acube:button id="btnSetLineBtn" onclick="javascript:openEnfLine('I');return(false);" value="<%=processorfixBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
		                <% if (lob003.equals(lobCode) && enfDocVO.getSerialNumber() > 0){  %>
							<acube:button id="btnAccApproval" onclick="javascript:acceptApprovalCallAppWaitOk();return(false);" value="<%=approalprocessdocBtn%>" type="2" class="gr" />
							<acube:space between="button" />
						<% } %>
                    <% } %>
                     
                    <acube:button onclick="selectDocInfo('');return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <% 
                    //if("Y".equals(opt335)){ 
                    %>
                    <acube:button id="btnpubReader" onclick="javascript:getpubReader();return(false);" value="<%=pubreaderBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <%
                    //} 
                    %>
					<% if (!isExtWeb) { %>  
                    <acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
 					<%	} %>									
                    <acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
					<% } %>
                    <acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
                    </acube:buttonGroup>
                    
                    <% 
                     /*
                       -------------------------------------------------------------------------------
                                              문서진행함  - 회수, 문서정보, 공람  버튼
                       -------------------------------------------------------------------------------
                     */
                    %>
                <%} else if (lobCode.equals(lob004) || lobCode.equals(lob031)){ %>
                    <acube:buttonGroup align="right">
                    <% if(lobCode.equals(lob004) && isWithdraw){ %>
                    <acube:button id="btnRetrieveDoc" onclick="javascript:retrieveDoc();return(false);" value="<%=retrievedocBtn %>" type="2" class="gr" />
                    <acube:space between="button" />
                    <%}%>
                    <acube:button onclick="selectDocInfo('Read');return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <acube:button id="btnpubReader" onclick="javascript:getpubReader('search');return(false);" value="<%=pubreaderBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                                         
					<% if (!isExtWeb) { %>	
                    <acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	} %>									
                    <acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
					<% } %>
                    <acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
                    </acube:buttonGroup>             
                    <% 
                     /*
                       -------------------------------------------------------------------------------
                                              접수완료함 - 담당자재지정
                       -------------------------------------------------------------------------------
                     */
                    %>
                <%} else if (lobCode.equals(lob011)){ %>
                    <acube:buttonGroup align="right">
                    <% if(deptDocManager && isEnfLineChange){ %>
                    <acube:button id="btnSetLineBtn" onclick="javascript:openEnfLine('C');return(false);" value="<%=processorRefixBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <%}%>
                        <!-- 접수완료함 이송버튼 추가함 by jkkim option과 완료문서인경우 이송가능함.-->
                     <%if(opt402.equals("Y")&&enf600.equals(docState)){ %>           
                    <acube:button id="moveBtn" onclick="javascript:moveAfterRecv();return(false);" value="<%=moveBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <%}%>
                      <!-- 접수완료함 경유버튼 추가함 by jkkim option과 완료문서인경우 경유가능함.-->
                    <%if(opt403.equals("Y")&&enf600.equals(docState)){ %>           
                    <acube:button id="viaBtn" onclick="javascript:viaAfterRecv();return(false);" value="<%=viaBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <%}%>
                    <acube:button onclick="selectDocInfo('Read');return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <acube:button id="btnpubReader" onclick="javascript:getpubReader('search');return(false);" value="<%=pubreaderBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                                         
                    <% if (!isExtWeb) { %>  
                    <acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
                    <%  if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>                                                              
                    <acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <%  } %>                                    
                    <%  if (false) { %>                                                              
					<acube:button onclick="sendMail();return(false);" value="<%=sendMailBtn%>" type="2" class="gr" />
					<acube:space between="button" />
                    <%  } %>                                    
                    <acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <% } %>
                    <acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
                    </acube:buttonGroup>             
			<% 	
	
			 } else if (lobCode.equals(lob099)) { // 관리자결재목록 
			   //관리자 기능 버튼
				String adminEdityBodyBtn = messageSource.getMessage("approval.button.modifybody", null, langType); // 본문수정
				String adminConfirmEdityBodyBtn = messageSource.getMessage("approval.button.confirm.modifybody", null, langType); // 본문수정확인
				String adminCancelEdityBodyBtn = messageSource.getMessage("approval.button.cancel.modifybody", null, langType); // 본문수정취소
				String adminChangeBodyBtn = messageSource.getMessage("approval.button.changebody", null, langType); // 본문수정(파일변경)
				String adminEdityAttachBtn = messageSource.getMessage("approval.button.modifyattach", null, langType); // 첨부수정
				String adminEditDocInfoBtn = messageSource.getMessage("approval.button.modifydocinfo", null, langType); // 문서정보수정
				String adminSendToDocBtn = messageSource.getMessage("approval.button.sendtodoc", null, langType); // 문서관리로 보내기
				String adminEditEnflineBtn = messageSource.getMessage("approval.button.modifyappline", null, langType); // 결재경로수정
 
			 %>
                <div id="editbody1" style="display:box;">
 				<acube:buttonGroup align="right">
					<% if ("N".equals(transferYn) && !isExtWeb) { %>
					<acube:button onclick="deleteAppDocByAdmin();return(false);" value="<%=deleteBtn%>" type="2" />
					<acube:space between="button" />
					<acube:button onclick="editBodyByAdmin();return(false);" value="<%=adminEdityBodyBtn%>" type="2" />
					<acube:space between="button" />
					<acube:button onclick="changeBodyByAdmin();return(false);" value="<%=adminChangeBodyBtn%>" type="2" />
					<acube:space between="button" />
					<acube:button onclick="editAttachByAdmin();return(false);" value="<%=adminEdityAttachBtn%>" type="2" />
					<acube:space between="button" />
					<% 	if(!lobCode.equals(lob091)){ %>					
					<acube:button onclick="editEnfDocInfoByAdmin();return(false);" value="<%=adminEditDocInfoBtn%>" type="2" />
					<acube:space between="button" />
					<% 	} %>
					<% if(lobCode.equals(lob099) && ( enf400.equals(docState) || enf500.equals(docState) ) ){ %>
                    <acube:button id="updateEnfLineBtn" onclick="javascript:selectEnfLineByAdmin();return(false);" value="<%=adminEditEnflineBtn %>" type="2" class="gr" />
					<acube:space between="button" />
                    <acube:button id="btnWithdrawEnfDocByAdmin" onclick="javascript:withdrawEnfDocByAdmin();return(false);" value="<%=retrievedocBtn %>" type="2" class="gr" />
                    <acube:space between="button" />
                    <%}%>
					<% } %>
					<% if (enf600.equals(docState) && !isExtWeb) { %>
					<acube:button onclick="sendToDocByAdmin();return(false);" value="<%=adminSendToDocBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>
					<acube:button onclick="selectDocInfo('Read');return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% if (!isExtWeb) { %>	
					<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	} %>									
					<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>
					<acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup>
				</div>	
                <div id="editbody2" style="display:none;">
 				<acube:buttonGroup align="right">
					<acube:button onclick="confirmEditBodyByAdmin();return(false);" value="<%=adminConfirmEdityBodyBtn%>" type="2" />
					<acube:space between="button" />
					<acube:button onclick="cancelEditBodyByAdmin();return(false);" value="<%=adminCancelEdityBodyBtn%>" type="2" />
					<acube:space between="button" />
				</acube:buttonGroup>
				</div>	

				<% } else { %>
 					<acube:buttonGroup align="right">
                    <% if(lobCode.equals(lol002)) { //문서배부대장에서 추가배부 %>
						<acube:button onclick="distribute('A');return(false);" value="<%=appendDistributeBtn%>" type="2" class="gr" />						
						<acube:space between="button" />
						
	                    <% if(isEnableDistributeWithdraw) { //문서배부대장에서 배부회수 %>
						<acube:button onclick="distribute('W');return(false);" value="<%=distributeWithdrawBtn%>" type="2" class="gr" />						
						<acube:space between="button" />
						<% } %>
						
	                    <% if(isEnableReturnOnDist && "Y".equals(enfDocVO.getDistributeYn())) { //문서배부대장에서 반송 %>
						<acube:button onclick="returnDoc();return(false);" value="<%=returnBtn%>" type="2" class="gr" />
						<acube:space between="button" />
						<% } %>

	                    <% if(isEnableReturnOnDist && "YY".equals(enfDocVO.getDistributeYn())) { //문서배부대장에서 재배부요청 %>
						<%if(opt401.equals("Y")){ %>
						<acube:button id="moveBtn" onclick="javascript:distribute('M');return(false);" value="<%=moveBtn%>" type="2" class="gr" />
						<acube:space between="button" />
						<%}%>
						<acube:button onclick="distribute('Q');return(false);" value="<%=reDistRequestBtn%>" type="2" class="gr" />
						<acube:space between="button" />
						<% } %>
						
					<% } %>
                    <% if((lobCode.equals(lob091) || lobCode.equals(lob092)) && enfDocVO.getOriginCompId().equals(compId) ){ %>
                    	<acube:button id="btnSendInfo" onclick="javascript:sendInfo();return(false);" value="<%=sendInfoBtn%>" type="2" class="gr" />
                    	<acube:space between="button" />
                    <% } %>
                    <% if(docManagerFlag){ //문서관리자%>
	                    <%if(lobCode.equals(lol002)) { %>
						<acube:button onclick="selectDocInfo('Read');return(false);" value="<%=procInfoBtn%>" type="2" class="gr" />						
						<%}else{ %>
						<acube:button onclick="selectDocInfo('');return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />	
						<%} %>
						<acube:space between="button" />
                    <%}else{ %>  
                    <!-- 등록대장 이송버튼 추가함 by jkkim--> 
                    <%if(opt402.equals("Y")&&enf600.equals(docState)){ %>              
                    <acube:button id="moveBtn" onclick="javascript:moveAfterRecv();return(false);" value="<%=moveBtn%>" type="2" class="gr" /> 
                    <acube:space between="button" />     
                    <%}%>
                      <!-- 등록대장 경유버튼 추가함 by jkkim option과 완료문서인경우 경유가능함.-->
                    <%if(opt403.equals("Y")&&enf600.equals(docState)){ %>           
                    <acube:button id="viaBtn" onclick="javascript:viaAfterRecv();return(false);" value="<%=viaBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <%}%>
                    <acube:button onclick="selectDocInfo('Read');return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <%} %>
                    <%//if(!"Y".equals(transferYn)){ %>
                    <acube:button id="btnpubReader" onclick="javascript:getpubReader('search');return(false);" value="<%=pubreaderBtn%>" type="2" class="gr" />
                    <acube:space between="button" />  
                    <% //} %>                  
					<% if (!isExtWeb) { %>	
					<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	} %>									
					<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%		if (false && (lol001.equals(lobCode) || lobCode.equals(lol003))) { %>					
					<acube:button onclick="sendMail();return(false);" value="<%=sendMailBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% 		} %>
					<% } %>
					<acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
					</acube:buttonGroup>
 				<% } %>
 				</div>
 				
                
                <div id="chargerprocess" style="display:none;">
 				<acube:buttonGroup align="right">
                    <acube:button id="btnSetLineBtn" onclick="javascript:openEnfLine('I');return(false);" value="<%=processorfixBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <acube:button onclick="selectDocInfo('');return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
					<% if (!isExtWeb) { %>  
                    <acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	} %>									
                    <acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
					<% } %>
                    <acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
                    </acube:buttonGroup>
 				</div>

                <div id="pubreaderprocess" style="display:none;">
 				<acube:buttonGroup align="right">
                    <%if(!"Y".equals(transferYn)){ %>
                    <acube:button id="btnpubReader" onclick="javascript:getpubReader();return(false);" value="<%=pubreaderBtn%>" type="2" class="gr" />
                    <acube:space between="button" />  
                    <% } %>                  
                    <acube:button onclick="selectDocInfo('');return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
					<% if (!isExtWeb) { %>  
                    <acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	} %>									
                    <acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
					<% } %>
                    <acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
                    </acube:buttonGroup>
 				</div>                
                <div id="afterprocess" style="display:none;">
 				<acube:buttonGroup align="right">
					<% if (!isExtWeb) { %>	
					<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%	} %>									
					<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>
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
				<div id="divhwp" width="100%" height="600">
<% if(strBodyType.equals("html")) { %>
					<iframe id="editHtmlFrame" name="editHtmlFrame" src="" width="100%" height="100%" scrolling="auto" frameborder="no" border="0"></iframe>
					<input type="hidden" name="bodyFileName" id="bodyFileName" value="<%= bodyVO.getFileName() %>" />
<%	} %>
				</div>
				<div id="hiddenhwp" width="100%" height="1">
				</div>
				<div id="mobilehwp" width="100%" height="1">
				</div>
			</td>
		</tr>
		<tr>
			<td height="6" colspan="2"></td>
		</tr>
 		<tr>
<%-- 접수단계에서는 관련문서 사용안함, jd.park, 20120612
<% if ("Y".equals(opt321)) { %>
		    <td colspan="2">
		    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      		<tr>
		      			<td width="50%" class="approval_box">
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
						<td>&nbsp;</td>
		      			<td width="50%" class="approval_box">
					    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					      		<tr>
								    <td width="15%;" height="60px" class="ltb_head"><spring:message code='approval.title.attachfile'/></td>
					        		<td width="80%;" height="60px">
										<div id="divattach" style="background-color:#ffffff;border:0px solid;height:60px;width=100%;overflow:auto;"></div>
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
--%>
		    <td class="approval_box" colspan="2">
		    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      		<tr>
					    <td width="15%;" height="70px" class="msinputbox_tit"><spring:message code='approval.title.attachfile'/></td>
		        		<td width="80%;" height="70px">
							<div id="divattach" style="background-color:#ffffff;border:0px solid;height:70px;width=100%;overflow:auto;"></div>
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
<%-- } --%>	
 		</tr> 
	</table>	
</acube:outerFrame>

<form id="frmDocInfo" name="frmDocInfo" method="POST"  target="popupWin" >
	<input id="autoNextDocYn" name="autoNextDocYn" type="hidden" value="<%=autoNextDocYn%>"/>
<div id="approvalitem" name="approvalitem" >
    <input type="hidden" id="compId" name="compId" value="<%=compId%>" />
    <input type="hidden" id="title" name="title" value="<%=EscapeUtil.escapeHtmlTag(title)%>"/><!-- 문서제목 --> 
	<input type="hidden" id="docType" name="docType" value="<%=appDocVO.getDocType()%>"></input><!-- 문서유형 --> 
    <input type="hidden" id="lobCode" name="lobCode" value="<%=lobCode%>" />
    <input type="hidden" id="docId" name="docId" value="<%=docId%>" />
    <input type="hidden" id="newDocId" name="newDocId" value="" />
    <input type="hidden" id="originCompId" name="originCompId" value="<%=enfDocVO.getOriginCompId()%>" />
    <input type="hidden" id="originDocId" name="originDocId" value="<%=enfDocVO.getOriginDocId()%>" />

    <input type="hidden" id="electronDocYn" name="electronDocYn" value="Y" />
    <input type="hidden" id="docState" name="docState" value="<%=docState%>" />
    <input type="hidden" id="recvState" name="recvState" value="<%=recvState%>" />
    <input type="hidden" id="procType" name="procType" value="APT005" size="50" />
    <input type="hidden" id="distributeYn" name="distributeYn" value="<%=enfDocVO.getDistributeYn()%>" /><!-- 배부여부 -->
    <input type="hidden" id="urgencyYn" name="urgencyYn" value="<%=enfDocVO.getUrgencyYn()%>" /><br/>
    <input type="hidden" id="senderDeptName" name="senderDeptName" value="<%=enfDocVO.getSenderDeptName()%>" /><!-- 발송부서 -->
    <input type="hidden" id="senderDeptId" name="senderDeptId" value="<%=enfDocVO.getSenderDeptId()%>" /><!-- 발송부서ID -->
    <input type="hidden" id="senderCompName" name="senderCompName" value="<%=enfDocVO.getSenderCompName()%>" /><!-- 발송회사 -->
    
	<input id="bindingId" name="bindingId" type="hidden" value="<%=enfDocVO.getBindingId()%>"/><!-- 편철ID --> 
	<input id="bindingName" name="bindingName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(enfDocVO.getBindingName())%>"/><!-- 편철명 --> 
	<input id="bindingResourceId" name="bindingResourceId" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(enfDocVO.getBindingResourceId())%>"></input><!-- 편철 다국어 추가 -->
	<input id="conserveType" name="conserveType" type="hidden" value="<%=enfDocVO.getConserveType()%>"/><!-- 보존년한 --> 
	<input id="readRange" name="readRange" type="hidden" value="<%=enfDocVO.getReadRange()%>"/><!-- 열람범위 --> 
	<input id="docNumber" name="docNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(enfDocVO.getDocNumber())%>"/><!-- 생산문서번호 --> 
	<input id="deptCategory" name="deptCategory" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(enfDocVO.getDeptCategory())%>"/><!-- 문서부서분류 --> 
	<input id="serialNumber" name="serialNumber" type="hidden" value="<%=enfDocVO.getSerialNumber()%>"/><!-- 문서일련번호 --> 
	<input id="subserialNumber" name="subserialNumber" type="hidden" value="<%=enfDocVO.getSubserialNumber()%>"/><!-- 문서하위번호 --> 
	<input id="enfType" name="enfType" type="hidden" value="<%=enfDocVO.getEnfType()%>"/><!-- 시행유형 --> 
	<input id="publicPost" name="publicPost" type="hidden" value="<%=enfDocVO.getPublicPost()%>"/><!-- 공람게시 --> 
    <input id="pubReader" name="pubReader" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferPubReader(pubReaderVOs))%>" /><!-- 공람자 --> 
    <input id="transferYn" name="transferYn" type="hidden" value="<%=transferYn%>" />
  	<input id="assistantLineType" name="assistantLineType" type="hidden" value="<%=StringUtil.null2str(enfDocVO.getAssistantLineType(), opt303)%>"></input><!-- 협조라인유형 --> 
	<input id="auditLineType" name="auditLineType" type="hidden" value="<%=StringUtil.null2str(enfDocVO.getAuditLineType(), opt304)%>"></input><!-- 감사라인유형 --> 
    	
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
    <input type="hidden" id="procOpinion" name="procOpinion" value="<%=EscapeUtil.escapeHtmlTag(procOpinion)%>"/><br/>
    <!-- 접수의견 20160422 -->
 	<input type="hidden" id="ProcVOs" name="ProcVOs" value="<%=EscapeUtil.escapeHtmlTag(ProcVOs)%>"/><br/>
 

    <input type="hidden" id="bodyFileId" name="bodyFileId" value="<%=bodyVO.getFileId()%>" />
    <input type="hidden" id="bodyFileName" name="bodyFileName" value="<%=bodyVO.getFileName()%>" />
    <input type="hidden" id="bodyFileDisplayName" name="bodyFileDisplayName" value="<%=bodyVO.getDisplayName()%>" />
    <input type="hidden" id="bodyFileSize" name="bodyFileSize" value="<%=bodyVO.getFileSize()%>" />
	<!-- 본문 --> 
	<input id="bodyFile" name="bodyFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft001))%>"></input>
	<!-- 첨부 --> 
	<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAttach(fileVOs))%>"></input>
   
    <input type="hidden" id="bindId" name="bindId" value="" />
    <input type="hidden" id="bindName" name="bindName" value="" />
    
    <input type="hidden" id="userId" name="userId" value="<%=userId%>" size="50" /> 
    <input type="hidden" id="userName" name="userName" value="<%=userName%>" size="50" />
    <input type="hidden" id="userPos" name="userPos" value="<%=userPos%>" size="50" />
    <input type="hidden" id="userDeptId" name="userDeptId" value="<%=deptId%>" size="50" />
    <input type="hidden" id="userDeptName" name="userDeptName" value="<%=deptName%>" size="50" /><br/>

    <input type="hidden" id ="enfLines" name="enfLines" value="<%=EscapeUtil.escapeHtmlTag(enfLines)%>"/><!-- 결재경로 -->
    <input type="hidden" id ="publishId" name="publishId" value="<%=publishId%>"/><!-- 공람게시id -->
    
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
	
	<input id="distributeFlag" name="distributeFlag" type="hidden" value=""/><!-- 배부(D),재배부(R),추가배부(A),이송(M),재배부요청(Q),배부안함(N) -->

	<input id="classNumber" name="classNumber" type="hidden" value="<%=CommonUtil.nullTrim(enfDocVO.getClassNumber()) %>"></input><!-- 분류번호 --> 
	<input id="docnumName" name="docnumName" type="hidden" value="<%=CommonUtil.nullTrim(enfDocVO.getDocnumName()) %>"></input><!-- 분류번호명 -->
<%
	AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
	if(appOptionVO == null ) {
	    appOptionVO = new AppOptionVO();  
	}
	//접수단계에서는 관련문서 사용안함, jd.park, 20120612
	//List<RelatedDocVO> relatedDocVOs = appDocVO.getRelatedDoc();
	List<RelatedRuleVO> relatedRuleVOs = appDocVO.getRelatedRule();
	List<CustomerVO> customerVOs = appDocVO.getCustomer();
%>	
<!-- 원기안문서정보  start-->
		<input id="app_appLine" name="app_appLine" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppLine(appLineVOs, compId))%>"/>
		<%-- 접수단계에서는 관련문서 사용안함, jd.park, 20120612
		<!-- 관련문서 --> 
		<input id="relatedDoc" name="relatedDoc" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedDoc(relatedDocVOs))%>"></input>
		--%>
		<!-- 관련규정 --> 
		<input id="app_relatedRule" name="app_relatedRule" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedRule(relatedRuleVOs))%>"></input>
		<!-- 거래처 --> 
		<input id="app_customer" name="app_customer" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferCustomer(customerVOs))%>"></input>
		<input id="app_conserveType" name="app_conserveType" type="hidden" value="<%=appDocVO.getConserveType()%>"/><!-- 보존년한 --> 
		<input id="app_readRange" name="app_readRange" type="hidden" value="<%=appDocVO.getReadRange()%>"/><!-- 열람범위 --> 
		<!-- 발송정보 -->
		<input id="app_sendOrgName" name="app_sendOrgName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getSendOrgName() == null) ? "" : sendInfoVO.getSendOrgName())%>"></input><!-- 발신기관명 -->
		<input id="app_senderTitle" name="app_senderTitle" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getSenderTitle() == null) ? "" : sendInfoVO.getSenderTitle())%>"></input><!-- 발신명의 -->
		<input id="app_headerCamp" name="app_headerCamp" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getHeaderCamp() == null) ? "" : sendInfoVO.getHeaderCamp())%>"></input><!-- 상부캠페인 -->
		<input id="app_footerCamp" name="app_footerCamp" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getFooterCamp() == null) ? "" : sendInfoVO.getFooterCamp())%>"></input><!-- 하부캠페인 -->
		<input id="app_postNumber" name="app_postNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getPostNumber() == null) ? "" : sendInfoVO.getPostNumber())%>"></input><!-- 우편번호 -->
		<input id="app_address" name="app_address" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getAddress() == null) ? "" : sendInfoVO.getAddress())%>"></input><!-- 주소 -->
		<input id="app_telephone" name="app_telephone" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getTelephone() == null) ? "" : sendInfoVO.getTelephone())%>"></input><!-- 전화 -->
		<input id="app_fax" name="app_fax" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getFax() == null) ? "" : sendInfoVO.getFax())%>"></input><!-- FAX -->
		<input id="app_via" name="app_via" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getVia() == null) ? "" : sendInfoVO.getVia())%>"></input><!-- 경유 -->
		<input id="app_sealType" name="app_sealType" type="hidden" value="<%=(sendInfoVO.getSealType() == null) ? "" : sendInfoVO.getSealType()%>"></input><!-- 날인유형 -->
		<input id="app_homepage" name="app_homepage" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getHomepage() == null) ? "" : sendInfoVO.getHomepage())%>"></input><!-- 홈페이지 -->
		<input id="app_email" name="app_email" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getEmail() == null) ? "" : sendInfoVO.getEmail())%>"></input><!-- 이메일 -->
		<input id="app_receivers" name="app_receivers" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getReceivers() == null) ? "" : sendInfoVO.getReceivers())%>"></input><!-- 수신 -->
		<input id="app_displayNameYn" name="app_displayNameYn" type="hidden" value="<%=(sendInfoVO.getDisplayNameYn() == null) ? "" : sendInfoVO.getDisplayNameYn()%>"></input><!-- 수신 -->

<!-- 원기안문서정보 end -->
</div>	
</form>

<% if(lobCode.equals(lob091) || lobCode.equals(lob092)){ %>
<!-- 발송상황조회 -->
<form id="frmSendInfo" name="frmSendInfo" method="POST" action="<%=webUri%>/app/approval/sendInfo.do" target="popupWin" style="display:none">
	<input type="hidden" id="sendInfoCompId" name="sendInfoCompId" value="" />
	<input type="hidden" id="sendInfoEditFlag" name="sendInfoEditFlag" value="" />
	<input type="hidden" id="sendInfoDocId" name="sendInfoDocId" value="" />
	<input type="hidden" id="sendInfoDocState" name="sendInfoDocState" value="<%=appDocVO.getDocState()%>" />
	<input type="hidden" id="sendInfoComment" name="sendInfoComment" value=""/>
	<input type="hidden" id="sendInfoLobCode" name="sendInfoLobCode" value="<%=lobCode %>"/>
</form> 
<% } %> 
    
<jsp:include page="/app/jsp/common/adminform.jsp" />
<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>