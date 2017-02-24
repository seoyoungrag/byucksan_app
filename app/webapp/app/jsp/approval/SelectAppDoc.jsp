<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil" %>
<%@ page import="com.sds.acube.app.approval.util.ApprovalUtil" %>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppOptionVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppLineVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppRecvVO" %>
<%@ page import="com.sds.acube.app.approval.vo.RelatedDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.RelatedRuleVO" %>
<%@ page import="com.sds.acube.app.approval.vo.CustomerVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.StorFileVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.SendInfoVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.PubReaderVO" %>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil" %>
<%@ page import="com.sds.acube.app.env.vo.FormEnvVO" %>
<%@ page import="com.sds.acube.app.env.vo.FormVO" %>
<%@ page import="org.anyframe.util.StringUtil"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%
/** 
 *  Class Name  : SelectAppDoc.jsp 
 *  Description : 생산문서조회/처리
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.11 
 *   수 정 자 : 허 주
 *   수정내용 : KDB 요건반영
 * 
 *  @author  허주
 *  @since 2011. 03. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String isMessenger = (String) request.getAttribute("isMessenger");

	String erpId = (String) request.getAttribute("erpId");
	// 본문문서 타입 설정
	String strBodyType = "hwp";

	String roleCode = (String) session.getAttribute("ROLE_CODES"); // 역할코드
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	boolean adminstratorFlag = (roleCode.indexOf(roleId10) == -1) ? false : true;

	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디

	String OPT404 = appCode.getProperty("OPT404", "OPT404", "OPT"); // 비공개사유입력 // jth8172 2012 신결재 TF
	String ReasonUseYN = envOptionAPIService.selectOptionValue(compId, OPT404);

	String OPT406 = appCode.getProperty("OPT406", "OPT406", "OPT"); // 본문에 수신자기호표시여부 // jth8172 2012 신결재 TF
	String RecvSymbolUseYN = envOptionAPIService.selectOptionValue(compId, OPT406);
	String OPT407 = appCode.getProperty("OPT407", "OPT407", "OPT"); // 본문에 수신자부서장직위표시여부 // jth8172 2012 신결재 TF
	String RecvChiefUseYN = envOptionAPIService.selectOptionValue(compId, OPT407);
	
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	String opt303 = appCode.getProperty("OPT303", "OPT303", "OPT"); // 부서협조 - 1 : 최종협조자, 2 : 모든협조자
	opt303 = envOptionAPIService.selectOptionValue(compId, opt303);
	String opt304 = appCode.getProperty("OPT304", "OPT304", "OPT"); // 감사표시 - 1 : 결재라인, 2 : 협조라인, 3 : 감사라인	
	opt304 = envOptionAPIService.selectOptionValue(compId, opt304);
	String opt314 = appCode.getProperty("OPT314", "OPT314", "OPT");
	opt314 = envOptionAPIService.selectOptionValue(compId, opt314);
	String opt320 = appCode.getProperty("OPT320", "OPT320", "OPT"); // CEO 결재문서 감사
	opt320 = envOptionAPIService.selectOptionValue(compId, opt320);
	String opt343 = appCode.getProperty("OPT343", "OPT343", "OPT"); // 모바일사용여부 - Y : 사용, N : 사용안함
	opt343 = envOptionAPIService.selectOptionValue(compId, opt343);
	String opt346 = appCode.getProperty("OPT346", "OPT346", "OPT"); // 감사사용여부 - Y : 사용, N : 사용안함
	opt346 = envOptionAPIService.selectOptionValue(compId, opt346);
	String opt357 = appCode.getProperty("OPT357", "OPT357", "OPT"); // 결재 처리 후 문서 자동닫기 - Y : 사용, N : 사용안함
	opt357 = envOptionAPIService.selectOptionValue(compId, opt357);

	String opt412 = appCode.getProperty("OPT412", "OPT412", "OPT"); // 반려문서등록대장등록  // jth8172 2012 신결재 TF
	opt412 = envOptionAPIService.selectOptionValue(compId, opt412);
	
	String opt411 = appCode.getProperty("OPT411", "OPT411", "OPT"); //보안 - 1 : 로그인패스워드, 2 : 비밀번호
	opt411 = envOptionAPIService.selectOptionValue(compId, opt411);
	
	String opt321 = appCode.getProperty("OPT321", "OPT321", "OPT"); // 관련문서 사용유무, jd.park, 20120504
	opt321 = envOptionAPIService.selectOptionValue(compId, opt321);
	
	String opt380 = appCode.getProperty("OPT380", "OPT380", "OPT"); // 감사대상문서,감사문서 별도 사용여부, jkkim, 20120718
	opt380 = envOptionAPIService.selectOptionValue(compId, opt380);
	
	//대내문서자동발송여부  // jth8172 2012 신결재 TF
	String opt413 = appCode.getProperty("OPT413", "OPT413", "OPT");	
	String autoInnerSendYn = envOptionAPIService.selectOptionValue(compId, opt413);
 
	//자동발송시날인방법 (1:부서서명인, 2:생략인 3:최종결재자 서명)	  // jth8172 2012 신결재 TF
	String opt414 = appCode.getProperty("OPT414", "OPT414", "OPT");	
	String autoSealType = envOptionAPIService.selectOptionValue(compId, opt414);
	
	/*옵션 추가, 문서분류체계 및 편철 사용 유무 , jd.park, 20120509 S*/
	String opt422 = appCode.getProperty("OPT422", "OPT422", "OPT"); //문서분류체계 사용유무 
	opt422 = envOptionAPIService.selectOptionValue(compId, opt422);
	
	String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT"); //편철 사용유무
	opt423 = envOptionAPIService.selectOptionValue(compId, opt423);
	/*옵션 추가, 문서분류체계 및 편철 사용 유무 , jd.park, 20120509 E*/

	String det002 = appCode.getProperty("DET002", "DET002", "DET"); // 대내
	String det003 = appCode.getProperty("DET003", "DET003", "DET"); // 대외
	String det004 = appCode.getProperty("DET004", "DET004", "DET"); // 대내외
    String det007 = appCode.getProperty("DET007", "DET007", "DET"); // 민원인
	String dru002 = appCode.getProperty("DRU002", "DRU002", "DRU");
    
    String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT");
    String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT");
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
	String aft014 = appCode.getProperty("AFT014", "AFT014", "AFT"); // 감사기안 본문 added by jkkim 

	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류

	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성 // jth8172 2012 신결재 TF
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대 // jth8172 2012 신결재 TF

	String app100 = appCode.getProperty("APP100", "APP100", "APP"); // 기안대기
	String app110 = appCode.getProperty("APP110", "APP110", "APP"); // 기안대기(반려문서)
	String app200 = appCode.getProperty("APP200", "APP200", "APP"); // 검토대기
	String app250 = appCode.getProperty("APP250", "APP250", "APP"); // 검토대기(TEXT본문)-연계
	String app300 = appCode.getProperty("APP300", "APP300", "APP"); // 협조대기
	String app301 = appCode.getProperty("APP301", "APP301", "APP"); // 부서협조대기
	String app350 = appCode.getProperty("APP350", "APP350", "APP"); // 협조대기(TEXT본문)-연계
	String app351 = appCode.getProperty("APP351", "APP351", "APP"); // 부서협조대기(TEXT본문)-연계
	String app401 = appCode.getProperty("APP401", "APP401", "APP"); // 부서감사대기 added by jkkim : 감사옵션추가
	String app402 = appCode.getProperty("APP402", "APP402", "APP"); // 부서감사(검토) added by jkkim : 감사옵션추가
	String app405 = appCode.getProperty("APP405", "APP405", "APP"); // 부서감사(결재) added by jkkim : 감사옵션추가
	String app500 = appCode.getProperty("APP500", "APP500", "APP"); // 결재대기
	String app550 = appCode.getProperty("APP550", "APP550", "APP"); // 결재대기(TEXT본문)-연계
	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	String app610 = appCode.getProperty("APP610", "APP610", "APP"); // 발송대기 
	
	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art053 = appCode.getProperty("ART053", "ART053", "ART"); // 1인전결
	

	String art130 = appCode.getProperty("ART130", "ART130", "ART"); // 1인전결
	String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 1인전결
	String art132 = appCode.getProperty("ART132", "ART132", "ART"); // 1인전결
	String art133 = appCode.getProperty("ART133", "ART133", "ART"); // 1인전결
	String art134 = appCode.getProperty("ART134", "ART134", "ART"); // 1인전결
	String art135 = appCode.getProperty("ART135", "ART135", "ART"); // 1인전결

	String art050 = appCode.getProperty("ART050", "ART050", "ART"); // 반려
	
	String dts002 = appCode.getProperty("DTS002", "DTS002", "DTS"); // 참조기안
	String dts003 = appCode.getProperty("DTS003", "DTS003", "DTS"); // 반려문서 재기안
	String dts004 = appCode.getProperty("DTS004", "DTS004", "DTS"); // 이송문서 기안 added by jkkim
	
	String dct497 = AppConfig.getProperty("form497", "DCT497", "formcode");
	String dct498 = AppConfig.getProperty("form498", "DCT498", "formcode");
	String dct499 = AppConfig.getProperty("form499", "DCT499", "formcode");

	String obt001 = appCode.getProperty("OBT001", "OBT001", "OBT"); // 그룹웨어
	String wkt001 = appCode.getProperty("WKT001", "WKT001", "WKT"); // 여신

	// 함종류
	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB");	// 결재대기함 - SelectAppDoc
	String lob004 = appCode.getProperty("LOB004", "LOB004", "LOB");	// 진행문서함 - SelectAppDoc
	String lol007 = appCode.getProperty("LOL007", "LOL007", "LOL");	// 일상감사대장 - SelectAppDoc
	String lob010 = appCode.getProperty("LOB010", "LOB010", "LOB");	// 일상감사대장 - SelectAppDoc
	
	String enf610 = appCode.getProperty("ENF610", "ENF610", "ENF"); // 이송중인 문서
	String enf620 = appCode.getProperty("ENF620", "ENF620", "ENF"); // 이송완료 문서
	String enf630 = appCode.getProperty("ENF630", "ENF630", "ENF"); // 이송중인 문서
	String enf640 = appCode.getProperty("ENF640", "ENF640", "ENF"); // 이송완료 문서

	//자동발송시 날인 유형  // jth8172 2012 신결재 TF
	String spt002 = appCode.getProperty("SPT002", "SPT002", "SPT"); // 서명인
	String spt004 = appCode.getProperty("SPT004", "SPT004", "SPT"); // 서명인생략

	String lobCode = CommonUtil.nullTrim((String) request.getAttribute("lobCode")); // 문서함코드
	String result = CommonUtil.nullTrim((String) request.getAttribute("result"));
	String enfDocState = CommonUtil.nullTrim((String)request.getAttribute("enfDocState"));//이송기안-임시저장-접수문서 상태체크 by jkkim
	String appDocState = CommonUtil.nullTrim((String)request.getAttribute("appDocState"));//부서감사기능 옵션 추가 by jkkim
	String call = CommonUtil.nullTrim((String)request.getAttribute("call"));//부서감사기능 옵션 추가 by jkkim

	//최종결재자플래그를 전표입력할 때 넘겨준다.
	boolean lastUserFlag = false;
	String firstUserId = "";
	
	if ("success".equals(result)) {

	    String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
		String userName = (String) session.getAttribute("USER_NAME"); // 사용자 이름
		String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
		String userDeptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
		String userDeptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서 이름
		String compName = (String) session.getAttribute("COMP_NAME"); // 사용자 소속 회사
		String ownDeptId = CommonUtil.nullTrim((String) request.getAttribute("ownDeptId")); // 소유부서
		String ownDeptName = CommonUtil.nullTrim((String) request.getAttribute("ownDeptName")); // 소유부서
		ownDeptId = "".equals(ownDeptId) ? userDeptId : ownDeptId;

		List<AppDocVO> appDocVOs = (List<AppDocVO>) request.getAttribute("appDocVOs");
		int docCount = appDocVOs.size();
	
		FormVO formVO = (FormVO) request.getAttribute("formVO");
		FormEnvVO logoEnvVO = (FormEnvVO) request.getAttribute("logo");
		FormEnvVO symbolEnvVO = (FormEnvVO) request.getAttribute("symbol");
		FileVO signFileVO = (FileVO) request.getAttribute("sign");
		ArrayList<FileVO> signList = (ArrayList<FileVO>) request.getAttribute("signList");
		//20161120
		ArrayList<FileVO> signListTempVO = (ArrayList<FileVO>) request.getAttribute("signListTempVO");
		//20161215
		ArrayList<AppLineVO> appLineVOsTemp = (ArrayList<AppLineVO>) request.getAttribute("appLineTempVO");
	
		FileVO bodyVO = (FileVO) request.getAttribute("bodyfile");
		String itemnum = (String) request.getAttribute("itemnum");
		String docType = appDocVOs.get(Integer.parseInt(itemnum) - 1).getDocType();
		int serialNumber = appDocVOs.get(Integer.parseInt(itemnum) - 1).getSerialNumber();
		String editbodyYn = appDocVOs.get(Integer.parseInt(itemnum) - 1).getEditbodyYn();
		String doubleYn = appDocVOs.get(Integer.parseInt(itemnum) - 1).getDoubleYn();
		String docState = appDocVOs.get(Integer.parseInt(itemnum) - 1).getDocState();
		String assistantLineType = StringUtil.null2str(appDocVOs.get(Integer.parseInt(itemnum) - 1).getAssistantLineType(), opt303);
		String auditLineType = StringUtil.null2str(appDocVOs.get(Integer.parseInt(itemnum) - 1).getAuditLineType(), opt304);
		
		//added by jkkim 기안일시 추가
		String draftDate = appDocVOs.get(Integer.parseInt(itemnum) - 1).getDraftDate();

		boolean currentUserFlag = false;
		String currentAskType = "";
		String drafterDeptId = userDeptId;
		String drafterDeptName = userDeptName;
		if (lob003.equals(lobCode)) {
			if (docCount > 0) {
			    List<AppLineVO> appLineVOs = appDocVOs.get(0).getAppLine();
			    drafterDeptId = ApprovalUtil.getDrafter(appLineVOs).getApproverDeptId();
			    drafterDeptName = ApprovalUtil.getDrafter(appLineVOs).getApproverDeptName();
			    AppLineVO currentUser = ApprovalUtil.getCurrentUser(appLineVOs, userId, apt003);
			    if (currentUser == null) {
				    currentUser = ApprovalUtil.getCurrentUser(appLineVOs, userId, apt004);
				    if (currentUser == null) {
						currentUser = ApprovalUtil.getCurrentApprover(appLineVOs);
						if (currentUser != null && userDeptId.equals(currentUser.getApproverDeptId()) && "".equals(CommonUtil.nullTrim(currentUser.getApproverId()))) {
							String pdocManager = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
							if (roleCode.indexOf(pdocManager) != -1) {
								currentUserFlag = true;				    
							}
							currentAskType = currentUser.getAskType();
						}
				    } else {
						currentUserFlag = true;
						currentAskType = currentUser.getAskType();
				    }
			    } else {
					currentUserFlag = true;
					currentAskType = currentUser.getAskType();
			    }
				//최종결재자플래그를 전표입력할 때 넘겨준다.
				if(currentUserFlag){
					for(int lcnt = 0 ; lcnt < appLineVOs.size(); lcnt++){
						if(appLineVOs.get(lcnt).getApproverName().equals(currentUser.getApproverName())){
							lastUserFlag = true;
							//메신저 알림 연동을 위한 기안자정보를 넘겨준다.
							firstUserId = appLineVOs.get(0).getApproverId();
						}
					}
				}
			}
		}
		    
		// 버튼명
		String previousBtn = messageSource.getMessage("approval.button.previous.doc", null, langType); 
		String nextBtn = messageSource.getMessage("approval.button.next.doc", null, langType); 

		String modifyBodyBtn = messageSource.getMessage("approval.button.modifybody", null, langType); 
		String saveBodyBtn = messageSource.getMessage("approval.button.savebody", null, langType); 
		String cancelBodyBtn = messageSource.getMessage("approval.button.cancelbody", null, langType); 
		String saveBtn = messageSource.getMessage("approval.button.save", null, langType);
	
		String appendBtn = messageSource.getMessage("approval.button.append", null, langType); 
		String removeBtn = messageSource.getMessage("approval.button.remove", null, langType); 
		String upBtn = messageSource.getMessage("approval.button.up", null, langType); 
		String downBtn = messageSource.getMessage("approval.button.down", null, langType); 
		String auditOriginDocBtn = messageSource.getMessage("approval.button.auditorigidocview", null, langType); // 감사문서 원문보기   // jkkim 20120720
		

		// 본문 문서 타입을 구한다. 		
		String bodyFileName = "";
		String bodyFileId   = "";
		if (bodyVO != null) {
			bodyFileName = bodyVO.getFileName();
			bodyFileId   = bodyVO.getFileId();
			
			strBodyType = CommonUtil.getFileExtentsion(bodyVO.getFileName());
		}	
		
		//문서편집기 옵션에 해당하는 연계양식 확장자 setting
		//formType이 1(한글), 2(워드)인 경우 해당 연계양식 사용
		//문서편집기 옵션에 해당하는 연계양식이 존재하지 않고 문서편집기 옵션에 html도 체크되어 있는 경우 html로 연계문서 보여줌
		//그외는 경고 메시지 띄우고 문서 보여주지 않음
		String formType = (String) request.getAttribute("formType");

		if(formType != null && !"".equals(formType)) {
			if(formVO == null) {	//문서편집기 옵션(opt428)에 해당하는 양식이 존재하지 않는 경우
				if(formType.indexOf("3") == -1) {	//문서편집기 옵션(opt428)에 html이 체크되지 않았을 경우
					strBodyType = "none";
				}
			}else {
				formType = formVO.getFormType();

				if("1".equals(formType)) {
					strBodyType = "hwp";
				} else if("2".equals(formType)) {
					strBodyType = "doc";
				}
			}
		}
		
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.select.approval'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/uuid.js"></script>
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

<jsp:include page="/app/jsp/approval/approval.jsp" />
<% if ("2".equals(opt301)) { %>		
<jsp:include page="/app/jsp/common/certification.jsp" />
<% } %>
<script type="text/javascript">
$(document).ready(function() { initialize(); });
$(window).unload(function() { uninitialize(); });
$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });
$.ajaxSetup({async:false});
function uninitialize() { 
	if(isMessenger!="Y"){
		closeChildWindow();
	}
}

var isMessenger = "<%=isMessenger%>";
var index = 0;
var retrycount = 0;
var docinfoWin = null;
var applineWin = null;
var receiverWin = null;
var pubreaderWin = null;
var readerWin = null;
var relatedDocWin = null;
var relatedRuleWin = null;
var customerWin = null;
var summaryWin = null;
var opinionWin = null;
var passwordWin = null;

var bodyfilepath = "";
var logopath = "";
var symbolpath = "";
var signpath = "";
var hwpFormFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/EnforceForm11.hwp";
var opinionFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/OpinionTbl.hwp";	//의견파일 경로
var nextYn = "";  // 파일업로드후 다른프로세스 실행유무 // jth8172 2012 신결재 TF
var stampFilePath =""; // 날인파일path
var sealFile =  new Object();  //날인파일 // jth8172 2012 신결재 TF
var docData =  new Object();  //처리용데이타 // jth8172 2012 신결재 TF
var sign = new Object();
sign.filename = "";
var bodyType = "<%= strBodyType %>"; //added by jkkim
//20160421
var registNewTrader = "";

<% if (lob003.equals(lobCode)) { %>
var opentype = "U";
<% } else { %>
var opentype = "V";
<% } %>
// added by jkkim word add work
var call = "<%=call%>";
var opt380 = "<%=opt380%>" ;
var appDocState = "<%=appDocState%>";
var itemnum =<%=itemnum%>;
var app401 = "<%= app401 %>";
var deptcategory = "";
var serialnumber = "";
var draftdate =  "<%=draftDate%>";

function resizeDoc() {
		var docId = $("#docId", "#approvalitem1").val();
		var lobCode = "<%=lobCode%>";
		var width = 830;
		if (screen.availWidth < width) {	
		    width = screen.availWidth;
		}
		
		var height = 768;
		
		if (screen.availHeight > 768) {	
		    height = screen.availHeight;	
		}
		height = height - 80;
		
		var top = (screen.availHeight - height) / 2;	
		var left = (screen.availWidth - width) / 2; 
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
		var linkUrl;
		if(lobCode == "LOB005" || lobCode == "LOB006"){
			linkUrl = "<%=webUri%>/app/approval/SenderAppDoc.do?docId="+docId+"&lobCode="+lobCode;
		}else if(lobCode == "LOB007" || lobCode == "LOB011" || lobCode == "LOB019" || lobCode == "LOL002"){
				linkUrl = "<%=webUri%>/app/enforce/EnforceDocument.do?docId="+docId+"&lobCode="+lobCode;
		}else{
			linkUrl = "<%=webUri%>/app/approval/selectAppDoc.do?docId="+docId+"&lobCode="+lobCode;
		}
		if(lobCode != "LOL099" ) {
			appDoc = openWindow("selectAppDocWinM", linkUrl , width, height);
		}else{
			relationDoc = openWindow("relationAppDocWinM", linkUrl , width, height);	//20140724 명칭변경 kj.yang
		}


}

function initialize() {
	$("#erpId").val('<%=erpId%>');
	if(isMessenger=="Y"){
		resizeDoc();
		window.open("about:blank","_self").close();
	}else{
	// 화면블럭지정
	screenBlock();
	
<%
	if (docCount > 1) {
%>	    
		document.getElementById("divhwp").style.height = (document.body.offsetHeight - 215+25);
<%
	} else {
%>	
		document.getElementById("divhwp").style.height = (document.body.offsetHeight - 190+25); 
	<%
	}
%>	
	// 파일 ActiveX 초기화
	initializeFileManager();
	
	var fileCount = 0;
	var filelist = new Array();
<% if 	(formVO != null) { %>
	var formFile = new Object();
	formFile.filename = "<%=formVO.getFormFileName()%>";
	formFile.displayname = "<%=formVO.getFormFileName()%>";
//	hwpFormFile = FileManager.savebodyfile(formFile);
	filelist[fileCount++] = formFile;
<% } %>	
<% if 	(logoEnvVO != null) { %>
	var logo = new Object();
	logo.filename = "<%=logoEnvVO.getRemark()%>";
	logo.displayname = "<%=logoEnvVO.getRemark()%>";
//	logopath = FileManager.savebodyfile(logo);
	filelist[fileCount++] = logo;
<% } %>	
<% if 	(symbolEnvVO != null) { %>
	var symbol = new Object();
	symbol.filename = "<%=symbolEnvVO.getRemark()%>";
	symbol.displayname = "<%=symbolEnvVO.getRemark()%>";
//	symbolpath = FileManager.savebodyfile(symbol);
	filelist[fileCount++] = symbol;
<% } %>	
<% if 	(signFileVO != null) { %>
	sign.filename = "<%=CommonUtil.nullTrim(signFileVO.getFileName())%>";
	sign.displayname = "<%=CommonUtil.nullTrim(signFileVO.getFileName())%>";
//	signpath = FileManager.savebodyfile(sign);
	filelist[fileCount++] = sign;
<% } %>	
<% if (bodyVO != null) { %> 
	var bodyfile = new Object();
	bodyfile.filename = "<%=bodyVO.getFileName()%>";
	bodyfile.displayname = "<%=EscapeUtil.escapeJavaScript(bodyVO.getDisplayName())%>";
//	bodyfilepath = FileManager.savebodyfile(bodyfile);
	filelist[fileCount++] = bodyfile;
<% } %>
<% if (signList != null) { 
		int signCount = signList.size();
		if (signCount > 0) { %>
			var signList = new Object();
<%		for (int loop = 0; loop < signCount; loop++) {
			    FileVO signVO = (FileVO) signList.get(loop);		%>
				var sign<%=loop%> = new Object();
				sign<%=loop%>.filename = "<%=CommonUtil.nullTrim(signVO.getFileName())%>";
				sign<%=loop%>.displayname = "<%=CommonUtil.nullTrim(signVO.getFileName())%>";
				filelist[fileCount++] = sign<%=loop%>;
<%		} %>
//			FileManager.savebodyfile(signList);
<%	}
	} %>
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
<% if 	(signFileVO != null) { %>
		signpath = result[resultcount++];
<% } %>	
<% if (bodyVO != null) { %>
		bodyfilepath = result[resultcount++];
<% } %>	
	}

	var pos = bodyfilepath.lastIndexOf(".");
	var bodyext = "hwp";
	if (pos != -1 && pos < bodyfilepath.length) {
		bodyext = bodyfilepath.substring(pos + 1).toLowerCase();
	} else if (bodyfilepath.length == 0) {
		bodyext = "";
	}
	
	// 문서로드
	if (bodyType == "hwp" || bodyType == "doc") {
		loadSelectDocument(bodyext, bodyfilepath);
	} else if (bodyType == "html") {
		loadSelectDocument(bodyext, bodyfilepath, "<%= docState %>"); 	
	} else if(bodyType == "none") {
		alert("<spring:message code='approval.form.legacy.notexist'/>");
		return;
	}

	// 본문복구
		if (!isExistDocument(Document_HwpCtrl)) {
			$.post("<%=webUri%>/app/approval/selectBodyHis.do", "docId=" + $("#docId", "#approvalitem1").val(), function(data) {
				if (data.result == "success") {
					if (data.bodypath != "") {
						var bodyHis = new Object();
						bodyHis.filename = data.bodypath;
						bodyHis.displayname = data.bodypath;
						bodyfilepath = FileManager.savebodyfile(bodyHis);
						openHwpDocument(Document_HwpCtrl, bodyfilepath);
						// 문서정보
						putFieldText(Document_HwpCtrl, HwpConst.Form.Title, $("#title", "#approvalitem1").val());
						putFieldText(Document_HwpCtrl, HwpConst.Form.ConserveType, typeOfConserveType($("#conserveType", "#approvalitem1").val()));
						putFieldText(Document_HwpCtrl, HwpConst.Form.ReadRange, typeOfReadRange($("#readRange", "#approvalitem1").val()));
						putFieldText(Document_HwpCtrl, HwpConst.Form.HeaderCampaign, $("#headerCamp", "#approvalitem1").val());
						putFieldText(Document_HwpCtrl, HwpConst.Form.FooterCampaign, $("#footerCamp", "#approvalitem1").val());

						//발신명의 - 내부문서는 발신명의 생략
						var recvList = getReceiverList($("#appRecv", "#approvalitem1").val());
						var recvsize = recvList.length;
						if (recvsize == 0) {
							putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, "");
						} else {
							putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, $("#senderTitle", "#approvalitem1").val());
						}

						moveToPos(Document_HwpCtrl, 2);
						setSavePoint(Document_HwpCtrl);

						// 서버반영
						if (isExistDocument(Document_HwpCtrl)) {
							recoverBody("recover", 1);
						}
					}
				}
			}, 'json').error(function(data) {
			});
		}

	<% if (docCount > 1) { %>	    
		for (var loop = 1; loop <= <%=docCount%>; loop++) {
			if (loop == <%=itemnum%>) {
				document.getElementById('id_tab_left_'+loop).src = '<%=webUri%>/app/ref/image/tab1.gif';
				document.getElementById('id_tab_bg_'+loop).style.background = 'url(<%=webUri%>/app/ref/image/tabbg.gif)';
				document.getElementById('id_tab_bg_'+loop).className = 'tab';
				document.getElementById('id_tab_right_'+loop).src = '<%=webUri%>/app/ref/image/tab2.gif';
			} else {
				document.getElementById('id_tab_left_'+loop).src = '<%=webUri%>/app/ref/image/tab1_off.gif';
				document.getElementById('id_tab_bg_'+loop).style.background = 'url(<%=webUri%>/app/ref/image/tabbg_off.gif)';
				document.getElementById('id_tab_bg_'+loop).className = 'tab_off';
				document.getElementById('id_tab_right_'+loop).src = '<%=webUri%>/app/ref/image/tab2_off.gif';
			}
		}

		if (bodyType == "hwp") {
			initializeHwp("hiddenhwp", "enforce");
			registerModule(Enforce_HwpCtrl);
		}

	<% } %>
		//20160421
		registNewTrader = $.trim(getFieldText(Document_HwpCtrl, "신규거래선등록"));
		if('<%=art130%>'=='<%=currentAskType%>' || '<%=art131%>'=='<%=currentAskType%>' || '<%=art132%>'=='<%=currentAskType%>' || '<%=art133%>'=='<%=currentAskType%>' || '<%=art134%>'=='<%=currentAskType%>' || '<%=art135%>'=='<%=currentAskType%>'){
			if(registNewTrader == "신규거래처 등록"){
				$("#modifybody").show();
			}
		}
		// 표준서식 - 안추가/안삭제
		if (isStandardForm(Document_HwpCtrl) && $("#standardform_beforeprocess") != null) {
			$("#standardform_beforeprocess").show();
			$("#standardform_waiting").show();
		}

		// 결재라인 초기화
		var appline = $("#appLine", "#approvalitem<%=itemnum%>").val();
		//added by jkkim 2012.07.27 "원문보기"의 경우, setAppLine을 태지우지 않음
		//결재라인에 서명이미지가 보이지 않는 문제.
		if("auditorigindoc" =="<%=call%>"&& "Y" == "<%=opt380%>"){
		}else{
				if (isStandardForm(Document_HwpCtrl)) {
					setAppLine(appline, true);
				}
		}//end

		//20120511 본문내 의견표시 초기화 kj.yang S
		var totalOpinion = setOpinionList(appline);
		if(totalOpinion == "") {
			deleteOpinionTbl(Document_HwpCtrl);
		}else {
			insertOpinionTbl(Document_HwpCtrl, opinionFile, totalOpinion);
		}
		//20120511 본문내 의견표시 초기화 kj.yang E
		
	<% if (app250.equals(docState) || app350.equals(docState) || app351.equals(docState) || app550.equals(docState)) { %>
		uploadBizBody();
	<% } %>

		// 첨부파일
		loadAttach($("#attachFile", "#approvalitem<%=itemnum%>").val(), <%=(lob003.equals(lobCode) && currentUserFlag && !isExtWeb)%>);

		// 화면블럭해제
		screenUnblock();

		// 문서조회모드
		if ("APP100" != "<%= docState %>" && ("auditorigindoc" =="<%=call%>" || ("Y" != "<%=opt380%>" && "<%=app401%>" != "<%=appDocState%>" ))){
			changeEditMode(Document_HwpCtrl, 0, false);
			moveToPos(Document_HwpCtrl, 2);
			setSavePoint(Document_HwpCtrl);
		}

		// 관련문서, 관련규정, 거래처 알림
		var relateddoc = getRelatedDocList($("#relatedDoc", "#approvalitem1").val());
		var relatedDocCount = relateddoc.length;
		var relatedRuleCount = (getRelatedRuleList($("#relatedRule", "#approvalitem1").val())).length;
		var customerCount = (getCustomerList($("#customer", "#approvalitem1").val())).length;
		var message = "";
	<% if("Y".equals(opt321)) { //관련문서사용시 관련문서 창호출%>
		if (relatedDocCount > 0) {
			loadRelatedDoc(relateddoc);
		}
	<% } else { %>	
		if (relatedDocCount > 0) {
			message += "<spring:message code='approval.msg.relateddoc'/> " + relatedDocCount + "<spring:message code='approval.msg.unit'/>";
		}
	<% } %>
		if (relatedRuleCount > 0) {
			if (message != "") {
				message += ", ";
			}
			message += "<spring:message code='approval.msg.relatedrule'/> " + relatedRuleCount + "<spring:message code='approval.msg.unit'/>";
		}

		if (message != "") {
			message += "<spring:message code='approval.msg.registered'/>";
			alert(message);
		}

	<% if (lob003.equals(lobCode) || lob004.equals(lobCode)) { %> 
		// 결재대기함, 결재진행함에서 문서 오픈시 의견이 있으면 의견팝업
		if (existApproverOpinion(appline)) {
			opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/selectOpinion.do", 560, 400);
		}
	<% } %>
	
		//임시 본문 제목 Setting (for 본문수정이력)
		var currentItem = getCurrentItem();
		$("#tempTitle", "#approvalitem" + currentItem).val(getFieldText(Document_HwpCtrl, HwpConst.Form.Title));
		
		// 문서위치 조정
		setTimeout(function(){moveStartPosition();}, 100);

	}
	 /* 20161215 */
	var signListTemp = new Array();
	var fileListTempCount = 0;
	<% if (signListTempVO != null) { 
			int signCountTemp = signListTempVO.size();
			if (signCountTemp > 0) { %>
	<%		for (int loop = 0; loop < signCountTemp; loop++) {
				    FileVO signVO = (FileVO) signListTempVO.get(loop);		%>
					var signTemp = new Object();
					signTemp.filename = "<%=CommonUtil.nullTrim(signVO.getFileName())%>";
					signTemp.displayname = "<%=CommonUtil.nullTrim(signVO.getFileName())%>";
					signListTemp[fileListTempCount++] = signTemp;
				<%		} %>
			<%		} %>
	 <% } %>
	 /* 20161215 */
if (signListTemp.length > 0) {
	var resultlistTemp = FileManager.savefilelist(signListTemp);
	<% if (signListTempVO != null) { %>
		var itemCount = getItemCount();
		 for (var loop = 0; loop < itemCount; loop++) {
			var itemnum = loop + 1;
			var applineTemp = $("#appLineTemp", "#approvalitem" + itemnum).val();
			var assistantlinetype = $("#assistantLineType", "#approvalitem" + itemnum).val();
			var auditlinetype = $("#auditLineType", "#approvalitem" + itemnum).val();
			<% if ("Y".equals(doubleYn)) { %>
						resetApprover(Document_HwpCtrl, getApproverUser(applineTemp), 2, "<%=docType%>", assistantlinetype, auditlinetype);
			<% } else { %>
						resetApprover(Document_HwpCtrl, getApproverUser(arrangeAssistant(applineTemp, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
			<% } %>
			$("#appLine", "#approvalitem" + itemnum).val(applineTemp);
		} 
		
		//나중에 이부분만 주석하면 문서 영향 없음
	<%}%>
	}
		//도장찍기 끝

}
<% if (app250.equals(docState) || app350.equals(docState) || app351.equals(docState) || app550.equals(docState) || true) { %>
function uploadBizBody() {
	var bodyinfo = "";
	var filename = "";
	var filepath = "";
	
	//본문생성
	if (bodyType == "hwp" || bodyType == "doc") {
		if(bodyType == "doc"){ 
			filename = "DocBody_" + UUID.generate() + ".doc";
		}else {
			filename = "HwpBody_" + UUID.generate() + ".hwp";
		} 
		
		filepath = FileManager.getlocaltempdir() + filename;
		saveHwpDocument(Document_HwpCtrl, filepath, false);
		
		var hwpfile = new Object();
		hwpfile.type = "body";
		hwpfile.localpath = filepath;
		var result = FileManager.uploadfile(hwpfile);
		var filelength = result.length;
		
		for (var pos = 0; pos < filelength; pos++) {
			var file = result[pos];
			bodyinfo += "" + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
		    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
		    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
		}

	<% 	if ("Y".equals(opt343)) { %>
			filename = "HtmlBody_" + UUID.generate() + ".html";
			filepath = FileManager.getlocaltempdir() + filename;
	
			saveHtmlDocument(Document_HwpCtrl, filepath, false);
			var htmlfile = new Object();
			htmlfile.type = "body";
			htmlfile.localpath = filepath;
			result = FileManager.uploadfile(htmlfile);
			filelength = result.length;
			for (var pos = 0; pos < filelength; pos++) {
				var file = result[pos];
				bodyinfo += "" + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
			    "<%=aft002%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
			    "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + htmlfile.localpath + String.fromCharCode(4);
			}
	<%	} %>		
	
		$("#bodyFile", "#approvalitem1").val(bodyinfo);
	
		$.post("<%=webUri%>/app/approval/uploadBizBody.do", "docId=" + $("#docId", "#approvalitem1").val() + "&docState=<%=docState%>&bodyFile=" + bodyinfo, function(data) {
			if (data.result == "success") {
				var attachlist = transferFileList($("#bodyFile", "#approvalitem1").val());
				var attachcount = attachlist.length;
				for (var loop = 0; loop < attachcount; loop++) {
					attachlist[loop].fileid = data.filelist[loop].fileid;
				}
				$("#bodyFile", "#approvalitem1").val(transferFileInfo(attachlist));
	<% 	if (app250.equals(docState)) { %>
				$("#docState", "#approvalitem1").val("<%=app200%>");
	<% 	} else if (app350.equals(docState)) { %>
				$("#docState", "#approvalitem1").val("<%=app300%>");
	<% 	} else if (app351.equals(docState)) { %>
				$("#docState", "#approvalitem1").val("<%=app301%>");
	<% 	} else if (app550.equals(docState)) { %>
				$("#docState", "#approvalitem1").val("<%=app500%>");
	<% 	} %>	
			} else if (data.result == "fail" && retrycount < 5) {
				retrycount++;
				uploadBizBody();
			}	
		}, 'json');
	  }
	}
<% } %>

function initDocumentEnv(hwpCtrl, itemnum) {
	// 로고, 심볼, 상하캠페인, 발신기관
	if (logopath != "")
		insertImage(hwpCtrl, HwpConst.Form.Logo, logopath, 20, 20);
	if (symbolpath != "")
		insertImage(hwpCtrl, HwpConst.Form.Symbol, symbolpath, 20, 20);
	putFieldText(hwpCtrl, HwpConst.Form.HeaderCampaign, $("#headerCampaign", "#approvalitem" + itemnum).val());
	putFieldText(hwpCtrl, HwpConst.Form.FooterCampaign, $("#footerCampaign", "#approvalitem" + itemnum).val());
	putFieldText(hwpCtrl, HwpConst.Form.OrganName, $("#sendOrgName", "#approvalitem" + itemnum).val());

	initAppLineEnv(hwpCtrl, itemnum);
}

function initAppLineEnv(hwpCtrl, itemnum) {
	// 문서번호 초기화
	clearRegiInfo(hwpCtrl);
	removeStamp(hwpCtrl);
	removeOmitStamp(hwpCtrl);
	// 발신명의
	putFieldText(hwpCtrl, HwpConst.Form.SenderName, $("#senderTitle", "#approvalitem" + itemnum).val());	
	// 수신자
	var apprecv = $("#appRecv", "#approvalitem" + itemnum).val();
	var isuse = $("#displayNameYn", "#approvalitem" + itemnum).val();
	var displayname = $("#receivers", "#approvalitem" + itemnum).val();
	setAppReceiver(hwpCtrl, itemnum, apprecv, isuse, displayname);
	<% if (serialNumber > 0) { %>
	putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, "");
	<% } %>
	//putFieldText(hwpCtrl, HwpConst.Form.PublicBound, HwpConst.Data.Open);  // 공개범위는 문서정보창에서 설정함
	putFieldText(hwpCtrl, HwpConst.Form.Zipcode, $("#postNumber", "#approvalitem" + itemnum).val());
	putFieldText(hwpCtrl, HwpConst.Form.Address, $("#address", "#approvalitem" + itemnum).val());
	putFieldText(hwpCtrl, HwpConst.Form.Telephone, $("#telephone", "#approvalitem" + itemnum).val());
	putFieldText(hwpCtrl, HwpConst.Form.Fax, $("#fax", "#approvalitem" + itemnum).val());
	putFieldText(hwpCtrl, HwpConst.Form.Homepage, $("#homepage", "#approvalitem" + itemnum).val());
	putFieldText(hwpCtrl, HwpConst.Form.Email, $("#email", "#approvalitem" + itemnum).val());	
}

function recoverBody(reason, itemnum) {
	var bodyinfo = "";
	var filename = "";
	var filepath = "";
	
	//본문생성
	if (bodyType == "hwp" || bodyType == "doc") {
		if(bodyType == "doc"){ 
			filename = "DocBody_" + UUID.generate() + ".doc";
		}else {
			filename = "HwpBody_" + UUID.generate() + ".hwp";
		} 
		
		filepath = FileManager.getlocaltempdir() + filename;
		saveHwpDocument(Document_HwpCtrl, filepath, false);
		
		var hwpfile = new Object();
		hwpfile.type = "body";
		hwpfile.localpath = filepath;
		var result = FileManager.uploadfile(hwpfile);
		var filelength = result.length;
		
		for (var pos = 0; pos < filelength; pos++) {
			var file = result[pos];
			bodyinfo += "" + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
		    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
		    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
		}
	<% 	if ("Y".equals(opt343)) { %>	
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
				bodyinfo += "" + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
			    "<%=aft002%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
			    "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + htmlfile.localpath + String.fromCharCode(4);
			}
	<%	} %>	

		$("#bodyFile", "#approvalitem" + itemnum).val(bodyinfo);

		$.post("<%=webUri%>/app/approval/recoverBody.do", "docId=" + $("#docId", "#approvalitem" + itemnum).val() + "&reason=" + reason + "&docState=<%=docState%>&bodyFile=" + bodyinfo, function(result) {
			 $("#mobileYn", "#approvalitem" + itemnum).val("N");	
		}, 'json').error(function(data) {
		});
	}
}

// 문서처리
function processAppDoc() {
	var currentItem = getCurrentItem();
	var user = getCurrentApprover($("#appLine", "#approvalitem" + currentItem).val(), "<%=userId%>");
	if (user == null) {
		alert("<spring:message code='approval.msg.check.user.appline'/>");
		selectAppLine();
	} else {
		if (arrangeAppline()) {
			var width = 800;
			<% if ("1".equals(opt301)) { %>		
			var height = 460;
			<% } else { %>
			var height = 420;
			<% } %>

			var isCooperation = false;		
			var isProcess = false;
			
			<%	if("APP300".equals(docState))	{	%>			/* 협조시에만 의견 기능을 활성화 한다. */
					isCooperation = true;
			<%	}%>		
		
			if (bodyType == "hwp" || bodyType == "doc") {
				isProcess = checkSubmitData("process");
			
			} else if (bodyType == "html") {
				isProcess = checkSubmitDataHTML("process", "edit");
			
			}	
					
			if(isProcess){	
				if(isCooperation){
					opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createOpinion.do?askType=" + user.askType + "&actType=<%=apt001%>&formBodyType=" + bodyType + "&alarmYn=" + $("#alarmYn", "#approvalitem" + currentItem).val(), width, height);
					
				}else{
					setOpinion("",user.askType, "<%=apt001%>");
				}
			}
			
		} else {
			selectAppLine();
		}
	}
}



//합의문서처리(찬성)  // jth8172 2012 신결재 TF
function agreeAppDoc() {
	var currentItem = getCurrentItem();
	var user = getCurrentApprover($("#appLine", "#approvalitem" + currentItem).val(), "<%=userId%>");
	if (user == null) {
		alert("<spring:message code='approval.msg.check.user.appline'/>");
		selectAppLine();
	} else {
		if (arrangeAppline()) {
			var width = 800;
			<% if ("1".equals(opt301)) { %>		
			var height = 460;
			<% } else { %>
			var height = 420;
			<% } %>

			if (bodyType == "hwp" || bodyType == "doc") {
				if (checkSubmitData("process")) {
					//opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createOpinion.do?askType=" + user.askType + "&actType=<%=apt051%>&formBodyType=" + bodyType + "&alarmYn=" + $("#alarmYn", "#approvalitem" + currentItem).val(), width, height);
					checkOpinion("<%=apt051%>");
				}
			} else if (bodyType == "html") {
				if (checkSubmitDataHTML("process", "edit")) {
					//opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createOpinion.do?askType=" + user.askType + "&actType=<%=apt051%>&formBodyType=" + bodyType + "&alarmYn=" + $("#alarmYn", "#approvalitem" + currentItem).val(), width, height);
					checkOpinion("<%=apt051%>");
				}
			}
		} else {
			selectAppLine();
		}
	}
}



//합의문서처리(반대)  // jth8172 2012 신결재 TF
function disagreeAppDoc() {
	var currentItem = getCurrentItem();
	var user = getCurrentApprover($("#appLine", "#approvalitem" + currentItem).val(), "<%=userId%>");
	if (user == null) {
		alert("<spring:message code='approval.msg.check.user.appline'/>");
		selectAppLine();
	} else {
		if (arrangeAppline()) {
			var width = 800;
			<% if ("1".equals(opt301)) { %>		
			var height = 460;
			<% } else { %>
			var height = 420;
			<% } %>

			if (bodyType == "hwp" || bodyType == "doc") {	
				if (checkSubmitData("process")) {
					//opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createOpinion.do?askType=" + user.askType + "&actType=<%=apt052%>&formBodyType=" + bodyType + "&alarmYn=" + $("#alarmYn", "#approvalitem" + currentItem).val(), width, height);
					checkOpinion("<%=apt052%>");
				}
			} else if (bodyType == "html") {
				if (checkSubmitDataHTML("process", "edit")) {
					//opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createOpinion.do?askType=" + user.askType + "&actType=<%=apt052%>&formBodyType=" + bodyType + "&alarmYn=" + $("#alarmYn", "#approvalitem" + currentItem).val(), width, height);
					checkOpinion("<%=apt052%>");
				}
			}
		} else {
			selectAppLine();
		}
	}
}

function setArrangeBody(upload) {
	var itemCount = getItemCount();
	var currentItem = getCurrentItem();
	for (var loop = 0; loop < itemCount; loop++) {
		var itemnum = loop + 1;
		var appline = $("#appLine", "#approvalitem" + itemnum).val();
		//console.log(appline);
		
		var assistantlinetype = $("#assistantLineType", "#approvalitem" + itemnum).val();
		var auditlinetype = $("#auditLineType", "#approvalitem" + itemnum).val();
		if (currentItem == itemnum) {
<% if ("Y".equals(doubleYn)) { %>
			resetApprover(Document_HwpCtrl, getApproverUser(appline), 2, "<%=docType%>", assistantlinetype, auditlinetype);
<% } else { %>
			resetApprover(Document_HwpCtrl, getApproverUser(arrangeAssistant(appline, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
<% } %>		
			arrangeBody(Document_HwpCtrl, itemnum, upload);
		} else {
			var result = reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());
			if (result == false) {
				alert("reloadHiddenBody is false!!!");
			}		
<% if ("Y".equals(doubleYn)) { %>
			resetApprover(Enforce_HwpCtrl, getApproverUser(appline), 2, "<%=docType%>", assistantlinetype, auditlinetype);
<% } else { %>
			resetApprover(Enforce_HwpCtrl, getApproverUser(arrangeAssistant(appline, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
<% } %>		
			arrangeBody(Enforce_HwpCtrl, itemnum, upload);
		}
	}
}

function arrangeBody(hwpCtrl, itemnum, upload, bodylist) {
	var editMode = getEditMode(hwpCtrl);
	var bodyinfo = "";
	var hwpfileid = "";
	var htmlfileid = "";
	
	if (typeof(bodylist) != "undefined") {
		if (bodylist.length > 0 && (bodyType == "hwp" || bodyType == "doc")) {
			hwpfileid = bodylist[0].fileid;
		}
		if (bodylist.length > 1) {
			htmlfileid = bodylist[1].fileid;
		}
	}

	// Hwp 본문 added by jkkim 워드 수정작업
	var filename = "";
	var filepath = "";
	
	//본문생성
	if(bodyType == "hwp" || bodyType == "doc") {
		if(bodyType == "doc"){ 
			filename = "DocBody_" + UUID.generate() + ".doc";
		}else {
			filename = "HwpBody_" + UUID.generate() + ".hwp";
		} 
		
		filepath = FileManager.getlocaltempdir() + filename;
		saveHwpDocument(hwpCtrl, filepath, false);
		
		var hwpfile = new Object();
		hwpfile.type = "body";
		hwpfile.localpath = filepath;
		var fileType;
		if( "Y" == "<%=opt380%>" && ("<%=app401%>" == "<%=appDocState%>" || "<%=app402%>" == "<%=appDocState%>" ||  "<%=app405%>" == "<%=appDocState%>"))
			fileType = "<%=aft014%>";
		else
			fileType = "<%=aft001%>";
		if (upload == true) {
			var result = FileManager.uploadfile(hwpfile);
			if (result == null) {
				return false;
			} 
			var filelength = result.length;
			for (var loop = 0; loop < filelength; loop++) {
				var file = result[loop];
				bodyinfo += hwpfileid + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
				fileType + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
			    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
			}
		} else {
			bodyinfo += hwpfileid + String.fromCharCode(2) + filename + String.fromCharCode(2) + filename + String.fromCharCode(2) + 
			fileType + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
		    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
		}
	}
<% if ("Y".equals(opt343)) { %>	
	filename = "HtmlBody_" + UUID.generate() + ".html";
	filepath = FileManager.getlocaltempdir() + filename;
	
	// Html 모바일본문 생성
	if(bodyType == "hwp" || bodyType == "doc") {	//문서편집기가 한글, MS-Word인 경우
		saveHtmlDocument(hwpCtrl, filepath, false);
		var htmlfile = new Object();
		htmlfile.type = "body";
		htmlfile.localpath = filepath;
		if (upload == true) {
			result = FileManager.uploadfile(htmlfile);
			if (result == null) {
				return false;
			} 
			filelength = result.length;
			for (var loop = 0; loop < filelength; loop++) {
				var file = result[loop];
				bodyinfo += htmlfileid + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
			    "<%=aft002%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
			    "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + htmlfile.localpath + String.fromCharCode(4);
			}
		} 
	}else {											//문서편집기가 HTML인 경우
		saveHtmlDocument(itemnum, filename);
	}

<% } %>

	//html의 경우 이전에 이미 값을 Setting하므로 한글, MS-Word의 경우에만 bodyinfo 값 Setting
	if(bodyType == "hwp" || bodyType == "doc") {
		$("#bodyFile", "#approvalitem" + itemnum).val(bodyinfo);
	}
	
	changeEditMode(hwpCtrl, editMode, (editMode == 2) ? true : false);
	
	return true;
}

function reloadFile(itemnum) {
	reloadBody(itemnum);
	reloadAttach(itemnum, <%=(lob003.equals(lobCode) && currentUserFlag && !isExtWeb)%>);
}

function reloadBody(itemnum) {
	if ($("#draftType").val() == "redraft-all") {
		showToolbar(Document_HwpCtrl, 1);
		changeEditMode(Document_HwpCtrl, 2, false);
	}
	var editMode = getEditMode(Document_HwpCtrl);
	var bodylist = transferFileList($("#bodyFile", "#approvalitem" + itemnum).val());
	if (bodylist.length > 0) {
		if (bodylist[0].localpath == "") {
			var bodyFile = new Object();
			bodyFile.filename = bodylist[0].filename;
			bodyFile.displayname = bodylist[0].displayname;
			bodylist[0].localpath = FileManager.savebodyfile(bodyFile);
		}
		openHwpDocument(Document_HwpCtrl, bodylist[0].localpath);
		$("#bodyFile", "#approvalitem" + itemnum).val(transferFileInfo(bodylist));
	} else if (bodyfilepath != "") {
		openHwpDocument(Document_HwpCtrl, bodyfilepath);
	} else {
		openHwpDocument(Document_HwpCtrl, hwpFormFile);
	}
	// 본문복구
	if (!isExistDocument(Document_HwpCtrl)) {
		$.post("<%=webUri%>/app/approval/selectBodyHis.do", "docId=" + $("#docId", "#approvalitem" + itemnum).val(), function(data) {
			if (data.result == "success") {
				if (data.bodypath != "") {
					var bodyHis = new Object();
					bodyHis.filename = data.bodypath;
					bodyHis.displayname = data.bodypath;
					bodyfilepath = FileManager.savebodyfile(bodyHis);
					openHwpDocument(Document_HwpCtrl, bodyfilepath);
					// 문서정보
					putFieldText(Document_HwpCtrl, HwpConst.Form.Title, $("#title", "#approvalitem" + itemnum).val());
					putFieldText(Document_HwpCtrl, HwpConst.Form.ConserveType, typeOfConserveType($("#conserveType", "#approvalitem" + itemnum).val()));
					putFieldText(Document_HwpCtrl, HwpConst.Form.ReadRange, typeOfReadRange($("#readRange", "#approvalitem" + itemnum).val()));
					putFieldText(Document_HwpCtrl, HwpConst.Form.HeaderCampaign, $("#headerCamp", "#approvalitem" + itemnum).val());
					putFieldText(Document_HwpCtrl, HwpConst.Form.FooterCampaign, $("#footerCamp", "#approvalitem" + itemnum).val());	

					moveToPos(Document_HwpCtrl, 2);
					setSavePoint(Document_HwpCtrl);

					// 서버반영
					if (isExistDocument(Document_HwpCtrl)) {
						recoverBody("recover", itemnum);
					}
				}
			}
		}, 'json').error(function(data) {
		});
	}

	var currentItem = getCurrentItem();
	var appline = $("#appLine", "#approvalitem" + currentItem).val();
	var assistantlinetype = $("#assistantLineType", "#approvalitem" + currentItem).val();
	var auditlinetype = $("#auditLineType", "#approvalitem" + currentItem).val();
<% if ("Y".equals(doubleYn)) { %>
	resetApprover(Document_HwpCtrl, getApproverUser(appline), 2, "<%=docType%>", assistantlinetype, auditlinetype);
<% } else { %>
	resetApprover(Document_HwpCtrl, getApproverUser(arrangeAssistant(appline, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
<% } %>		

<% if ("Y".equals(editbodyYn)) { %>
	if (editMode == 2) {
		changeEditMode(Document_HwpCtrl, 2, true);
	} else {
		changeEditMode(Document_HwpCtrl, 0, false);
	}
<% } else { %>	
	changeEditMode(Document_HwpCtrl, 0, false);
<% } %>		
	moveToPos(Document_HwpCtrl, 2);
}

function reloadHiddenBody(bodyinfo) {
	var result = false;
	var bodylist = transferFileList(bodyinfo);
	if (bodylist.length > 0) {
		if (bodylist[0].localpath == "") {
			var bodyFile = new Object();
			bodyFile.filename = bodylist[0].filename;
			bodyFile.displayname = bodylist[0].displayname;
			bodylist[0].localpath = FileManager.savebodyfile(bodyFile);
		}
		result = openHwpDocument(Enforce_HwpCtrl, bodylist[0].localpath);
	} else if (bodyfilepath != "") {
		result = openHwpDocument(Enforce_HwpCtrl, bodyfilepath);
	} else {
		result = openHwpDocument(Enforce_HwpCtrl, hwpFormFile);
	}
	changeEditMode(Enforce_HwpCtrl, 2, true);
	moveToPos(Enforce_HwpCtrl, 2);	
	return result;
}

function arrangeAppline() {
	var itemnum = getCurrentItem();
	var lineinfo = $("#appLine", "#approvalitem" + itemnum).val();
	if (lineinfo == "") {
		alert("<spring:message code='approval.msg.noappline'/>");
		selectAppLine();
		return false;
	}
	var user = getCurrentApprover(lineinfo, "<%=userId%>");
	if (user == null) {
		alert("<spring:message code='approval.msg.check.user.appline'/>");
		selectAppLine();
		return false;
	} else {
		var audittype = $("#auditYn", "#approvalitem" + itemnum).val();
		if (getCurrentLineApproverCount(lineinfo, user.lineNum) != 1) {
<% if ("Y".equals(doubleYn)) { %>
			alert("<spring:message code='approval.msg.check.user.appline'/>");
			selectAppLine();
			return false;
<% } else { %>
			if (audittype == "Y") {
				if (getAuditCount(lineinfo) == 0) {
					alert("<spring:message code='approval.msg.check.not.include.audit'/>");
					selectAppLine();
					return false;
				}
			} else if (audittype == "N") {
				if (getAuditCount(lineinfo) != 0) {
					alert("<spring:message code='approval.msg.check.include.audit'/>");
					selectAppLine();
					return false;
				}
			}
			if (getAppLineCount(lineinfo) == 1) {
				alert("<spring:message code='approval.msg.check.user.appline3'/>");
			//	if (confirm("<spring:message code='approval.msg.process.art053'/>")) {
			//		setAppLine(changeApproveType(lineinfo), false);
			//	} else {
					selectAppLine();
					return false;
			//	}
			} else {
				alert("<spring:message code='approval.msg.check.user.appline'/>");
				selectAppLine();
				return false;
			}
<% } %>
		} else {
<% if ("Y".equals(doubleYn)) { %>
			if (getApproverCountByLine(lineinfo, 2) == 0) {
				alert("<spring:message code='approval.msg.check.user.appline'/>");
				selectAppLine();
				return false;
			}
<% } %>
			if (audittype == "Y") {
				if (getAuditCount(lineinfo) == 0) {
					alert("<spring:message code='approval.msg.check.not.include.audit'/>");
					selectAppLine();
					return false;
				}
			} else if (audittype == "N") {
				if (getAuditCount(lineinfo) != 0) {
					alert("<spring:message code='approval.msg.check.include.audit'/>");
					selectAppLine();
					return false;
				}
			}
		}
<% if ("Y".equals(opt320)) { %>
<%
		String roleId25 = AppConfig.getProperty("role_dailyinpectreader", "", "role"); // 일상감사일지/대장 조회자
		String roleId29 = AppConfig.getProperty("role_dailyinpecttarget", "", "role"); // 일상감사대상자
		String roleId31 = AppConfig.getProperty("role_ceo", "", "role"); // CEO
%>		
		if ((user.approverRole).indexOf("<%=roleId25%>") == -1
				&& (user.approverRole).indexOf("<%=roleId31%>") == -1 && (user.approverRole).indexOf("<%=roleId29%>") == -1
				&& (user.askType == "<%=art010%>" || user.askType == "<%=art020%>")) {
			var specialUser = getSpecialRoleUser(lineinfo);
			if (specialUser != null) {
				if (getAuditCount(lineinfo) == 0) {
					if (!confirm("<spring:message code='approval.msg.applines.after.norole_auditor'/>")) {
						selectAppLine();
						return false;
					}
				}
			}
		}
<% } %>	
	}
	return true;
}

function checkSubmitData(option) {
	var itemCount = getItemCount();	
	// 현재 안건 제목 점검
	var itemnum = getCurrentItem();
	var title = $.trim($("#title", "#approvalitem" + itemnum).val());
	if (existField(Document_HwpCtrl, HwpConst.Form.Title)) {
		title = $.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.Title));
	}
	if (title == "") {
		alert("<spring:message code='approval.msg.notitle'/>");
		insertDocInfo();
		return false;
	} else if (!checkSubmitMaxLength(title, '', 512)) {
		return false;
	} else {
		$("#title", "#approvalitem" + itemnum).val(title);
	}
	// 경유정보 점검
	var via = $.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.Via));
	if (!checkSubmitMaxLength(via, '', 256)) {
		return false;
	} else {
		$("#via", "#approvalitem" + itemnum).val(via);
	}
	
	// 현재 안건 편철 점검(옵션에 따른 정보를 문서정보에서 확인)
	/*
	if ($("#bindingId", "#approvalitem" + itemnum).val() == "") {
		alert("<spring:message code='approval.msg.nobind'/>");
		insertDocInfo();
		return false;
	}
	*/
	<%if(docState.equals(app100) || docState.equals(app110)){%>
	//현재 안건 보안 번호 점검
	if($("#securityYn", "#approvalitem" + itemnum).val() =="Y" && $("#securityPass", "#approvalitem" + itemnum).val() == ""){
		alert("<spring:message code='approval.msg.input.securitypassword'/>");		
		insertDocInfo();
		return;
	}
	<%}%>

	// 본문변경여부 확인
	// jkkim added if 조건 2012.07.19
	// alert("<%=opt380%>,<%=appDocState%>");
	// alert(option);
	if( "Y" != "<%=opt380%>" && "<%=app401%>" != "<%=appDocState%>" ){
		if (isStandardForm(Document_HwpCtrl) && isChanged(Document_HwpCtrl)) {
			if (confirm("<spring:message code='approval.msg.modifybody.willyousave'/>")) {
				if (option == "submit") {
					saveNeditBody();
				} else {
					saveBody();
				}
			} else {
				if (option == "submit") {
					cancelNeditBody();
				} else {
					cancelBody();
				}
			}
		}
	}

	if (itemCount > 1) {
		for (var loop = 0; loop < itemCount; loop++) {
			// 모든 안건 제목 점검
			var title = $.trim($("#title", "#approvalitem" + (loop + 1)).val());
			if (title == "") {
				alert((loop + 1) + "<spring:message code='approval.form.item'/> <spring:message code='approval.msg.notitle'/>");
				selectTab(loop + 1);
				return false;
			} else if (!checkSubmitMaxLength(title, '', 512)) {
				selectTab(loop + 1);
				return false;
			}
			$("#title", "#approvalitem" + (loop + 1)).val(title);
			// 모든 안건 경유정보 점검
			via = $("#via", "#approvalitem" + (loop + 1)).val();
			if (!checkSubmitMaxLength(via, '', 256)) {
				selectTab(loop + 1, true);
				return false;
			}
			// 모든 안건 편철 점검
			/*
			if ($("#bindingId", "#approvalitem" + (loop + 1)).val() == "") {
				alert((loop + 1) + "<spring:message code='approval.form.item'/> <spring:message code='approval.msg.nobind'/>");
				selectTab(loop + 1, true);
				insertDocInfo();
				return false;
			}
			*/
			<%if(docState.equals(app100) || docState.equals(app110)){%>
				//모든 안건 보안점검
				if($("#securityYn", "#approvalitem" + (loop + 1)).val() =="Y" && $("#securityPass", "#approvalitem" + (loop + 1)).val() == ""){
					alert((loop + 1) + "<spring:message code='approval.msg.input.securitypassword'/>");		
					selectTab(loop + 1, true);
					insertDocInfo();
					return;
				}
			<%}%>
			// 일괄기안 셋팅
			$("#batchDraftYn", "#approvalitem" + (loop + 1)).val("Y");
			$("#batchDraftNumber", "#approvalitem" + (loop + 1)).val(loop + 1);

		}
	}

//필요시사용, 모든 양식에서 수신자여부 체크, kj.yang, 20120425 S
<%--
	var chkAppRecv = "";
	
	if (itemCount > 0) {
		for (var loop = 0; loop < itemCount; loop++) {
			chkAppRecv = $.trim($("#appRecv", "#approvalitem" + (loop + 1)).val());
	
			if(chkAppRecv == ""){
				alert("<spring:message code='approval.msg.notexist.receiverSetInfo'/>");
				return false;
			}
		}
	}
--%>
//필요시사용, 모든 양식에서 수신자여부 체크, kj.yang, 20120425 E

	// 하단표 중복확인
	if (!removeBottomTable(option, itemCount, itemnum)) {
		return false;
	}

	// 본문파일정리
	var bUpload;
	if( "Y" == "<%=opt380%>" && ("<%=app401%>" == "<%=appDocState%>" || "<%=app405%>" == "<%=appDocState%>") )
		bUpload = true;
	else
		bUpload = false;
		
	arrangeBody(Document_HwpCtrl, itemnum, bUpload);
	return true;
}

//결재의견이 필수가 아닌것으로 변경되고 결재의견 버튼이 생겨났음. 이전 setOpinion은 결재진행의 단계로써 존재했으므로 기능을 
//두개로 분리한다.
function setOpinion1(opinion, askType, actType, password, roundkey) {
	$("#opinion").val(opinion);
	
	// 아이 짜증나게 이딴걸 수정하라고 해서.. 결재 코어 부분이라 계속 다른 오류가 발생한다. 하지 말자니까..
	// 반려나 합의의 찬성, 반대 인경우는 의견작성창을 바로 띄우고 문서도 처리해버려야 한다.앞으로 케이스가 더 추가될 수 있다.
	if(actType=='APT002' || actType=='APT051' || actType=='APT052' || (actType=='APT001' && askType == 'ART030')){
		setOpinion(opinion, askType, actType, password, roundkey)
	}
}

function setOpinion(opinion, askType, actType, password, roundkey) {
	opinion = $("#opinion").val(); 
	var currentLineNum = 1;
	var itemnum = getCurrentItem();
	var itemCount = getItemCount();
	for (var loop = 0; loop < itemCount; loop++) {

		var appline = $("#appLine", "#approvalitem" + (loop + 1)).val();
		var approvers = appline.split(String.fromCharCode(4));
		var approverslength = approvers.length;
		for (var pos = 0; pos < approverslength; pos++) {
			if (approvers[pos].indexOf(String.fromCharCode(2)) != -1) {
				var approver = approvers[pos].split(String.fromCharCode(2));
				if ((approver[2] == "<%=userId%>" || approver[7] == "<%=userId%>")
						&& ((approver[13] == "<%=apt003%>" || approver[13] == "<%=apt004%>") || approver[13] == "" && (approver[12] == "<%=art010%>" || approver[12] == "<%=art053%>"))) {
					if (approver[15] == "N") {
						approver[15] = $("#bodyEdited", "#approvalitem" + (loop + 1)).val();
					}
					approver[19] = opinion;
					approver[22] = getCurrentDate();
//					if (!isStandardForm(Document_HwpCtrl)) {
						approver[20] = signpath.replace(FileManager.getlocaltempdir(), "");
//					}
					currentLineNum = approver[27];
		
					var appinfo = "";
					var applength = approver.length; 
					for (var subpos = 0; subpos < applength; subpos++) {
						if (subpos == applength - 1) {
							appinfo += approver[subpos];
						} else {
							appinfo += approver[subpos] + String.fromCharCode(2);
						}
					}
					approvers[pos] = appinfo;
					break;
				} else{
					approver[20] = "";
					approver[22] = "";
				}
			}
		}

		appline = "";
		for (var pos = 0; pos < approverslength; pos++) {
			if (approvers[pos].indexOf(String.fromCharCode(2)) != -1) {
				appline += approvers[pos] + String.fromCharCode(4);
			}
		}
		$("#appLine", "#approvalitem" + (loop + 1)).val(appline);

		//20120511 본문내 의견 표시 리스트 추출 kj.yang S
		var totalOpinion = setOpinionList(appline);
		if(totalOpinion != "") {
			insertOpinionTbl(Document_HwpCtrl, opinionFile, totalOpinion);
		}
		//20120511본문내 의견 표시 리스트 추출 kj.yang E
	}
<% if ("1".equals(opt301)) { %>		
	if (typeof(password) != "undefined" && typeof(roundkey) != "undefined") {
		if (password != "") {
			$("#password", "#appDocForm").val(password);
			$("#roundkey", "#appDocForm").val(roundkey);
		}
	}
<% } %>
	if (currentLineNum == "1" && opentype == "I" && (askType == "<%=art010%>" || askType == "<%=art053%>")) {
		setArrangeBody(true);
		setTimeout(function(){submitApproval();}, 100);
	} else {
		if (actType == "<%=apt001%>") {
			setArrangeBody(true);
			setTimeout(function(){processApproval();}, 100);
		} else if (actType == "<%=apt002%>") {

			var itemCount = getItemCount();
			var currentItem = getCurrentItem();
			for (var loop = 0; loop < itemCount; loop++) {
				var itemnum = loop + 1;
				if(currentItem == itemnum){
					arrangeBody(Document_HwpCtrl, itemnum, true);
				}
			}
			setTimeout(function(){returnApproval();}, 100);
		} else if (actType == "<%=apt004%>") {
			setArrangeBody(true);
			setTimeout(function(){holdoffApproval();}, 100);
		} else if (actType == "<%=apt051%>" || actType == "<%=apt052%>") {  // 합의  // jth8172 2012 신결재 TF
			setArrangeBody(true);
			setTimeout(function(){agreeApproval(actType);}, 100);
 		}
	}
}

function processApproval() {
	// 편철 다국어 추가
	var itemnum = getCurrentItem();
	$("#bindingName", "#approvalitem" + itemnum).val($("#bindingResourceId", "#approvalitem" + itemnum).val());

	moveToPos(Document_HwpCtrl, 2);
	$("#beforeprocess").hide();
	$("#waiting").show();
	$.post("<%=webUri%>/app/approval/processAppDoc.do", $("#appDocForm").serialize(), function(data) {
		if (data.result == "success") {
			var insertedDocId = data.docId;
			var erpId = $("#erpId").val();
			if(erpId!=""){
				//최종결재자일때만 실db에 입력되고, 최초기안자만 수정할 수 있다. 
				$.post("<%=webUri%>/app/approval/insertStatementToDB.do", "&erpId=" + erpId +"&docId="+insertedDocId+"&isLastUser=<%=lastUserFlag%>&firstUserId=<%=firstUserId%>", function(data) {
					if (data.result == "success") {
						//alert("전표입력이 완료되었습니다.");
					}else{
						//alert("전표입력이 실패하었습니다.");
					}
				}, 'json').error(function(data) {
					//alert("전표입력이 실패하였습니다. - ajax 에러");
				});	
			}
			if (afterProcess) {
				afterProcess(data);
			}
		} else {
			$("#waiting").hide();
			$("#beforeprocess").show();
			screenUnblock();
			alert(data.message);
		}
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='approval.msg.fail.processdocument'/>");
		}
	});
<% if ("1".equals(opt301)) { %>		
	$("#password", "#appDocForm").val("");
	$("#roundkey", "#appDocForm").val("");
<% } %>
}

function afterProcess(data) {
	if (data.state == "<%=app600%>" || data.state == "<%=app610%>") {
<% if (serialNumber != -1) { %>
		var docidinfo = "";
		var totalbodyinfo = "";
		var bodyfile = data.bodyfile;
		var bodycount = bodyfile.length;
		var currentItem = getCurrentItem();

		for (var loop = 0; loop < bodycount; loop++) {
			var hwpCtrl = Document_HwpCtrl;
			var bodyinfo = "";

			if (bodyfile[loop].itemnum != currentItem) {
				hwpCtrl = Enforce_HwpCtrl;
				reloadHiddenBody($("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val());
			}
		
			// 시행번호삽입
			var deptCategory = $("#deptCategory", "#approvalitem" + bodyfile[loop].itemnum).val();
			putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, deptCategory + "-" + bodyfile[loop].serial);
			// 시행번호가 삽입되었는지 2번 더 체크
			if (existField(hwpCtrl, HwpConst.Form.EnforceNumber)) {
				if ($.trim(getFieldText(hwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
					putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, deptCategory + "-" + bodyfile[loop].serial);
					if ($.trim(getFieldText(hwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
						putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, deptCategory + "-" + bodyfile[loop].serial);
					}
				}
			}

			var filename = "";
			var filepath = "";
			
			if (bodyfile[loop].hwpfile != "") {
				//본문생성
				if (bodyType == "hwp" || bodyType == "doc") {
					if(bodyType == "doc"){ 
						filename = "DocBody_" + UUID.generate() + ".doc";
					}else {
						filename = "HwpBody_" + UUID.generate() + ".hwp";
					} 
					
					filepath = FileManager.getlocaltempdir() + filename;
					saveHwpDocument(hwpCtrl, filepath, false);
					
					var hwpfile = new Object();
					hwpfile.type = "body";
					hwpfile.localpath = filepath;

					var result = FileManager.uploadfile(hwpfile);
					var filelength = result.length;
					for (var pos = 0; pos < filelength; pos++) {
						var file = result[pos];
						docidinfo += bodyfile[loop].docid + String.fromCharCode(4);
						bodyinfo += bodyfile[loop].hwpfile + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
					    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
					    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
					}
				} else if (bodyType == "html") {
					docidinfo += bodyfile[loop].docid + String.fromCharCode(4);
				}
			}
<% 	if ("Y".equals(opt343)) { %>	
			if (bodyfile[loop].htmlfile != "" && typeof bodyfile[loop].htmlfile != "undefined") {
				filename = "HtmlBody_" + UUID.generate() + ".html";
				filepath = FileManager.getlocaltempdir() + filename;
				
				// Html 모바일본문 생성
				if(bodyType == "hwp" || bodyType == "doc") {	//문서편집기가 한글, MS-Word인 경우
					saveHtmlDocument(hwpCtrl, filepath, false);
					var htmlfile = new Object();
					htmlfile.type = "body";
					htmlfile.localpath = filepath;
					result = FileManager.uploadfile(htmlfile);
					filelength = result.length;
					for (var pos = 0; pos < filelength; pos++) {
						var file = result[pos];
						docidinfo += bodyfile[loop].docid + String.fromCharCode(4);
						bodyinfo += bodyfile[loop].htmlfile + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
					    "<%=aft002%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
					    "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + htmlfile.localpath + String.fromCharCode(4);
					}
				}else {											//문서편집기가 HTML인 경우
					docidinfo += bodyfile[loop].docid + String.fromCharCode(4);
					saveHtmlDocument(currentItem, filename);
				}
			}
<%	} %>		
			if (bodyType == "hwp" || bodyType == "doc") {
				$("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val(bodyinfo);
				totalbodyinfo += bodyinfo;
			} else if (bodyType == "html") {
				totalbodyinfo = $("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val();
			}	
		}

		$.post("<%=webUri%>/app/approval/updateBody.do", "docid=" + docidinfo + "&file=" + totalbodyinfo, function(resultdata) {
			if (resultdata.result == "success") {

				var autoSendFlag = false;
				for (var loop = 0; loop < bodycount; loop++) { 	 		
					var enfType = $("#enfType", "#approvalitem" + bodyfile[loop].itemnum).val();
		 			var autoSendYn = $("#autoSendYn", "#approvalitem" + bodyfile[loop].itemnum).val();

		 			// alert("<%=det002%>"+"<%=autoInnerSendYn%>"+"<%=det003%>"+"<%=det004%>");
		 			
		 			// 대내문서자동발송체크추가  // jth8172 2012 신결재 TF
		 			if (enfType == "<%=det002%>" && "<%=autoInnerSendYn%>" == "Y" || ((enfType == "<%=det003%>" || enfType == "<%=det004%>") && autoSendYn == "Y")) {
		 				var filecount = resultdata.fileidlist.length;
		 				for (var loop = 0; loop < filecount; loop++) {
		 					for (var pos = 0; pos < bodycount; pos++) {
		 						if (data.bodyfile[pos].hwpfile == resultdata.fileidlist[loop]) {
		 							data.bodyfile[pos].hwpfile = resultdata.filelist[loop].fileid;
			 						break;
		 						} else if (data.bodyfile[pos].htmlfile == resultdata.fileidlist[loop]) {
		 							data.bodyfile[pos].htmlfile = resultdata.filelist[loop].fileid;
			 						break;
		 						}
		 					}
		 				}
		 				autoSendFlag = true;
		 				break;
		 			}
				}

				if (autoSendFlag) {
					setTimeout(function(){autoSend(data);}, 100);		
				}
				$("#waiting").hide();
				 $("#afterprocess").show();
				<% if (!"N".equals(editbodyYn)) { %>			
				$("#savebody").hide();
				$("#modifybody").hide();
				<% } %>		
				screenUnblock();
				alert(data.message);
				<% if ("Y".equals(opt357)) { %>
				// 정상처리되면 창을 닫는다.
				exitAppDoc();
				<% } %>
			} else if (resultdata.result == "fail" && retrycount < 5) {
				retrycount++;
				afterProcess(data);
			}
		}, 'json');
<% } else { %>
			var bodyfile = data.bodyfile;
			var bodycount = bodyfile.length;
			var autoSendFlag = false;
			for (var loop = 0; loop < bodycount; loop++) { 	 		
				var enfType = $("#enfType", "#approvalitem" + bodyfile[loop].itemnum).val();
	 			var autoSendYn = $("#autoSendYn", "#approvalitem" + bodyfile[loop].itemnum).val();
	 			// 대내문서자동발송체크추가  // jth8172 2012 신결재 TF
	 			if (enfType == "<%=det002%>" && "<%=autoInnerSendYn%>" == "Y" || ((enfType == "<%=det003%>" || enfType == "<%=det004%>") && autoSendYn == "Y")) {
	 				autoSendFlag = true;
	 				break;
	 			}
			}
			if (autoSendFlag) {
				setTimeout(function(){autoSend(data);}, 100);			
			}
			$("#waiting").hide();
			$("#afterprocess").show();
			<% if (!"N".equals(editbodyYn)) { %>			
			$("#savebody").hide();
			$("#modifybody").hide();
			<% } %>		
			screenUnblock();
			alert(data.message);
			<% if ("Y".equals(opt357)) { %>
			// 정상처리되면 창을 닫는다.
			exitAppDoc();
			<% } %>			
<% } %>
	} else {
		$("#waiting").hide();
		$("#afterprocess").show();
		<% if (!"N".equals(editbodyYn)) { %>			
		$("#savebody").hide();
		$("#modifybody").hide();
		<% } %>		
		screenUnblock();
		alert(data.message);
		<% if ("Y".equals(opt357)) { %>
		// 정상처리되면 창을 닫는다.
		exitAppDoc();
		<% } %>			
	}
}


//합의 찬성/반대
function agreeApproval(actType) {
	// 편철 다국어 추가
	var itemnum = getCurrentItem();
	$("#bindingName", "#approvalitem" + itemnum).val($("#bindingResourceId", "#approvalitem" + itemnum).val());
	
	moveToPos(Document_HwpCtrl, 2);
	$("#procType", "#appDocForm").val(actType);  //찬성 반대코드
	$("#beforeprocess").hide();
	$("#waiting").show();
 
	$.post("<%=webUri%>/app/approval/processAppDoc.do", $("#appDocForm").serialize(), function(data) {
		if (data.result == "success") {
			if (afterProcess) {
				afterProcess(data);
			}
		} else {
			$("#waiting").hide();
			$("#beforeprocess").show();
			screenUnblock();
			alert(data.message);
		}
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='approval.msg.fail.processdocument'/>");
		}
	});
<% if ("1".equals(opt301)) { %>		
	$("#password", "#appDocForm").val("");
	$("#roundkey", "#appDocForm").val("");
<% } %>
}



// 반려
function returnAppDoc() {
	var user = getCurrentApprover($("#appLine", "#approvalitem" + getCurrentItem()).val(), "<%=userId%>");
	if (user == null) {
		user = getCurrentDept($("#appLine", "#approvalitem" + getCurrentItem()).val(), "<%=userDeptId%>");
		if (user == null) {
			alert("<spring:message code='approval.msg.check.user.appline'/>");
			selectAppLine();
			return false;
		}
	}

	var width = 800;
	<% if ("1".equals(opt301)) { %>		
	var height = 450;
	<% } else { %>
	var height = 420;
	<% } %>
	
	opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createOpinion.do?askType=" + user.askType + "&actType=<%=apt002%>&formBodyType=" + bodyType, width, height);
}

function createOpinion(){
	var width = 800;
	
	<% if ("1".equals(opt301)) { %>		
	var height = 460;
	<% } else { %>
	var height = 420;
	<% } %>
	var user = getCurrentApprover($("#appLine", "#approvalitem" + getCurrentItem()).val(), "<%=userId%>");
	opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createOpinion.do?askType=" + user.askType + "&actType=<%=apt001%>&formBodyType=" + bodyType +"&opinion="+$("#opinion").val(), width, height);
}

function returnApproval() {
	// 편철 다국어 추가
	var itemnum = getCurrentItem();
	$("#bindingName", "#approvalitem" + itemnum).val($("#bindingResourceId", "#approvalitem" + itemnum).val());
	
	$("#beforeprocess").hide();
	$("#waiting").show();
	
	$.post("<%=webUri%>/app/approval/returnAppDoc.do", $("#appDocForm").serialize(), function(data){
		$("#modifybody").attr("style", "display:none;");
		$("#waiting").hide();
		$("#afterprocess").show();
		screenUnblock();
		alert(data.message);
		<% if ("Y".equals(opt357)) { %>
		// 정상처리되면 창을 닫는다.
		exitAppDoc();
		<% } %>			
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='approval.msg.fail.return'/>");
		}
	});
}


// 회수
function withdrawAppDoc() {
	<% if ("1".equals(opt301)) { %>		
	opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createPassword.do", 500, 160);
	<% } else if ("2".equals(opt301)) { %>
	$.post("<%=webUri%>/app/appcom/validateSignDate.do", "", function(data) {
		if (data.validation == "Y") {
			withdrawApproval();
			$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
		} else {
			if (certificate()) {
				withdrawApproval();
				$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
			}
		}
	}, 'json').error(function(data) {
		if (certificate()) {
			withdrawApproval();
			$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
		}
	});
	<% } else { %>
	withdrawApproval();
	<% } %>
}

<% if ("1".equals(opt301)) { %>		
function setPassword(password, roundkey) {
	if (typeof(password) != "undefined" && typeof(roundkey) != "undefined") {
		if (password != "") {
			$("#password", "#appDocForm").val(password);
			$("#roundkey", "#appDocForm").val(roundkey);
		}
	}
	setTimeout(function(){withdrawApproval();}, 10);
}
<% } %>

function withdrawApproval() {
	$("#beforeprocess").hide();
	$("#waiting").show();

	// 본문에 결재정보 제거
	var currentItem = getCurrentItem();
	var itemCount = getItemCount();

	var appline = $("#appLine", "#approvalitem1").val();
	var approvers = getApproverList(appline);
	var currentUser = getCurrentUser(approvers, "<%=userId%>", "<%=apt001%>");
	var approverslength = approvers.length;
	
	if (currentUser != null) {
		var currentFlag = false;
		for (var pos = 0; pos < approverslength; pos++) {
			var approver = approvers[pos];
			if (currentFlag) {
				approver.processDate = "9999-12-31 23:59:59";
				approver.procOpinion = "";
			} else {
				if (approver.approverId == currentUser.approverId && approver.representativeId == currentUser.representativeId
						&& approver.procType == currentUser.procType && approver.processDate == currentUser.processDate) {
					currentFlag =  true;
					approver.processDate = "9999-12-31 23:59:59";
					approver.procOpinion = "";
				}
			}
		}

		var appline = getApproverInfo(approvers);
		for (var loop = 0; loop < itemCount; loop++) {
			var itemnum = loop + 1;
			$("#appLine", "#approvalitem" + itemnum).val(appline);
			var hwpCtrl = Document_HwpCtrl;
			if (currentItem != itemnum) {
				hwpCtrl = Enforce_HwpCtrl;
				reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());
			}

			var assistantlinetype = $("#assistantLineType", "#approvalitem" + itemnum).val();
			var auditlinetype = $("#auditLineType", "#approvalitem" + itemnum).val();
<% if ("Y".equals(doubleYn)) { %>
			resetApprover(hwpCtrl, getApproverUser(approvers), 2, "<%=docType%>", assistantlinetype, auditlinetype);
<% } else { %>
			resetApprover(hwpCtrl, getApproverUser(arrangeAssistant(approvers, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
<% } %>

			//20120511 본문내 의견 표시 리스트 추출 kj.yang S
			var totalOpinion = setOpinionList(appline);
			
			if(totalOpinion == "") {
				deleteOpinionTbl(Document_HwpCtrl);
			}else {
				insertOpinionTbl(Document_HwpCtrl, opinionFile, totalOpinion);
			}
			//20120511본문내 의견 표시 리스트 추출 kj.yang E

			var bodyinfo = $("#bodyFile", "#approvalitem" + itemnum).val();
			
			if(bodyType == "html") {
				var mobileFile = $("#mobileFile").val();

				if(mobileFile != undefined) {
					bodyinfo += mobileFile;
				}
			}
			
			var bodylist = transferFileList(bodyinfo);
			arrangeBody(hwpCtrl, itemnum, true, bodylist);
		}
	}
	
	$.post("<%=webUri%>/app/approval/withdrawAppDoc.do", $("#appDocForm").serialize(), function(data){
		$("#waiting").hide();
		$("#afterprocess").show();
		screenUnblock();
		alert(data.message);
		<% if ("Y".equals(opt357)) { %>
		// 정상처리되면 창을 닫는다.
		exitAppDoc();
		<% } %>			
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();
		alert("<spring:message code='approval.msg.fail.withdraw'/>");
	});
<% if ("1".equals(opt301)) { %>		
	$("#password", "#appDocForm").val("");
	$("#roundkey", "#appDocForm").val("");
<% } %>
}


// 보류
function holdoffAppDoc() {
	$("#beforeprocess").hide();
	$("#waiting").show();

	var user = getCurrentApprover($("#appLine", "#approvalitem" + getCurrentItem()).val(), "<%=userId%>");
	if (user == null) {
		user = getCurrentDept($("#appLine", "#approvalitem" + getCurrentItem()).val(), "<%=userDeptId%>");
		if (user == null) {
			alert("<spring:message code='approval.msg.check.user.appline3'/>");
			$("#beforeprocess").show();
			$("#waiting").hide();
			selectAppLine();
			return false;
		}
	}

	if (bodyType == "hwp" || bodyType == "doc") {
		if (checkSubmitData("holdoff")) {
			setOpinion("", "", "<%=apt004%>");	
		} else {
			$("#beforeprocess").show();
			$("#waiting").hide();
		}
	} else if (bodyType == "html") {
		if (checkSubmitDataHTML("holdoff", "edit")) {
			setOpinion("", "", "<%=apt004%>");	
		} else {
			$("#beforeprocess").show();
			$("#waiting").hide();
		}
	}
}

function holdoffApproval() {
	// 편철 다국어 추가
	var itemnum = getCurrentItem();
	$("#bindingName", "#approvalitem" + itemnum).val($("#bindingResourceId", "#approvalitem" + itemnum).val());
	
	$.post("<%=webUri%>/app/approval/holdoffAppDoc.do", $("#appDocForm").serialize(), function(data){
		$("#waiting").hide();
		$("#beforeprocess").show();
<% if (!"N".equals(editbodyYn)) { %>			
		$("#savebody").hide();
		$("#modifybody").hide();
<% } %>
		screenUnblock();
		if (data.result == "success") {
			<% if ("Y".equals(opt357)) { %>
			if (confirm("<spring:message code='approval.msg.success.aftersave.doyouwant.closewindow'/>")) {
				// 정상처리되면 창을 닫는다.
				exitAppDoc();
			} else {
				document.location.href = "<%=webUri%>/app/approval/selectAppDoc.do?docId=" + $("#docId", "#approvalitem1").val() + "&lobCode=<%=lobCode%>";
			}
			<% } else { %>
			alert("<spring:message code='approval.msg.success.holdoff'/>");		
			document.location.href = "<%=webUri%>/app/approval/selectAppDoc.do?docId=" + $("#docId", "#approvalitem1").val() + "&lobCode=<%=lobCode%>";
			<% } %>			
		} else {
			alert(data.message);
		}
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();
		alert("<spring:message code='approval.msg.fail.holdoff'/>");
	});
}

function deleteAppDoc() {
	$("#beforeprocess").hide();
	$("#waiting").show();
	$.post("<%=webUri%>/app/approval/deleteAppDoc.do", $("#appDocForm").serialize(), function(data){
		screenUnblock();
		alert(data.message);
		exitAppDoc();
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();
		alert("<spring:message code='approval.msg.fail.delete.doc'/>");
	});
}

// 재기안
function redraftAppDoc() {
	
	$("#statement_modify").show();
	$("#statement_select").hide();
	//이송할문서의 원접수문서 문서상태체크
	var enfDocState = "<%=enfDocState%>";
	var enf610 = "<%=enf610%>";
	var enf620 = "<%=enf620%>";
	var enf630 = "<%=enf630%>";
	var enf640 = "<%=enf640%>";
	if(enfDocState == enf610)
	{
		alert("<spring:message code='approval.msg.fail.rejproctransdoc'/>");
		return;
	}
	else if(enfDocState == enf620)
	{
		alert("<spring:message code='approval.msg.fail.rejregitransdoc'/>");
		return;
	}else if(enfDocState == enf630)
	{
		alert("<spring:message code='approval.msg.fail.rejregiviadoc'/>");
		return;
	}else if(enfDocState == enf640)
	{
		alert("<spring:message code='approval.msg.fail.rejregiviadoc'/>");
		return;
	}
	//end
	// 버튼 변경
	$("#before_beforeredraft").hide();
	$("#before_afterredraft").show();
	$("#after_beforeredraft").hide();
	$("#after_afterredraft").show();
	$("#editattach").show();
		
	opentype = "I";
	$("#draftType").val("redraft-all");
	$("#sourceAppLine").val($("#appLine", "#approvalitem1").val());
	$("#modifybody").show();
	var oriAppline = getAppLine();
	var approver = getApproverList(oriAppline);
	var approversize = approver.length;
	
	// 기안자 의견 Setting
	if(approversize > 0) {
		$("#drafterOpinion").val(approver[0].procOpinion);
	}
	
	var appline = clearApproverList($("#appLine", "#approvalitem1").val());	
	var itemCount = getItemCount();
	for (var loop = 0; loop < itemCount; loop++) {
		var itemnum = loop + 1;
		$("#appLine", "#approvalitem" + itemnum).val(appline);
		//jkkim added 이송기안-반려시 docId를 setting하지 않고 접수문서 아이디(현재orginid)를 setting함
		var docSource = $("#docSource", "#approvalitem" + itemnum).val();
		var originDoc = $("#originDocId", "#approvalitem" + itemnum).val();
		if(docSource !="<%=dts004%>" && originDoc != "" )
		{
		 $("#originDocId", "#approvalitem" + itemnum).val($("#docId", "#approvalitem" + itemnum).val());
		 $("#docSource", "#approvalitem" + itemnum).val("<%=dts003%>");
		}
		
		//반려 재기안일 경우 originDocId 값 삭제, 문서출처  docSource에 반려재기안 값(DTS003) Setting
		if("APP110" == "<%= docState %>" && originDoc != "") {
			$("#originDocId", "#approvalitem" + itemnum).val("");
			$("#docSource", "#approvalitem" + itemnum).val("<%=dts003%>");
		}
		
		//end
		$("#assistantLineType", "#approvalitem" + itemnum).val("<%=opt303%>");
		$("#auditLineType", "#approvalitem" + itemnum).val("<%=opt304%>");
	}
	

	deleteOpinionTbl(Document_HwpCtrl);	//20120511 재기안시 본문내 의견 표시 제거 kj.yang
	
	setArrangeBody(false);
	reloadAttach(getCurrentItem(), true);
	$("#tableattach").show();

	showToolbar(Document_HwpCtrl, 1);
	changeEditMode(Document_HwpCtrl, 2, true);
	moveToPos(Document_HwpCtrl, 2);
	setSavePoint(Document_HwpCtrl);
}

//반려문서대장등록  개발중
function regReject(){
	//반려문서 등록대장 등록여부를 확인  
	<% 	if("Y".equals(opt412) ) { %>
		if(!confirm("<spring:message code='approval.msg.redraftregyn'/>")) {
			return;
		}
	<% } %>	
	$("#beforeprocess").hide();
	$("#waiting").show();

	$("#redraftRegYn").val("Y");
	//1. 채번후 등록대장 등록 
	$.post("<%=webUri%>/app/approval/regRejectAppDoc.do", $("#appDocForm").serialize(), function(data){
		if (data.result == "success") {
			afterRegReject(data);
		} else {
			alert(data.message);
		}
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();
		alert("<spring:message code='approval.msg.fail.savendoc'/>");
	});

}	


function afterRegReject(data) {
	var docidinfo = "";
	var totalbodyinfo = "";
	var bodyfile = data.bodyfile;
	var bodycount = bodyfile.length;
	var currentItem = getCurrentItem();
	
	if(bodyType == "hwp" || bodyType == "doc") {	//문서편집기가 한글, MS-Word인 경우
		for (var loop = 0; loop < bodycount; loop++) {
			var hwpCtrl = Document_HwpCtrl;
			$("#docId", "#approvalitem" + bodyfile[loop].itemnum).val(bodyfile[loop].docid);
<% if (serialNumber != -1) { %>
			// 현재 안건이 아닌 경우 문서오픈
			var bodyinfo = "";			
			if (bodyfile[loop].itemnum != currentItem) {
				hwpCtrl = Enforce_HwpCtrl;
				reloadHiddenBody($("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val());
			}
			// 시행번호삽입
			var deptCategory = $("#deptCategory", "#approvalitem" + bodyfile[loop].itemnum).val();
			putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, deptCategory + "-" + bodyfile[loop].serial);
			// 시행번호가 삽입되었는지 2번 더 체크
			if (existField(hwpCtrl, HwpConst.Form.EnforceNumber)) {
				if ($.trim(getFieldText(hwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
					putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, deptCategory + "-" + bodyfile[loop].serial);
					if ($.trim(getFieldText(hwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
						putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, deptCategory + "-" + bodyfile[loop].serial);
					}
				}
			}

			var filename = "";
			var filepath = "";
			
			if (bodyfile[loop].hwpfile != "") {
				//본문생성
				if (bodyType == "hwp" || bodyType == "doc") {
					if(bodyType == "doc"){ 
						filename = "DocBody_" + UUID.generate() + ".doc";
					}else {
						filename = "HwpBody_" + UUID.generate() + ".hwp";
					} 
					
					filepath = FileManager.getlocaltempdir() + filename;
					saveHwpDocument(hwpCtrl, filepath, false);
					
					var hwpfile = new Object();
					hwpfile.type = "body";
					hwpfile.localpath = filepath;
					var result = FileManager.uploadfile(hwpfile);
					var filelength = result.length;
					for (var pos = 0; pos < filelength; pos++) {
						var file = result[pos];
						docidinfo += bodyfile[loop].docid + String.fromCharCode(4);
						bodyinfo += bodyfile[loop].hwpfile + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
					    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
					    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
					}
				} 
			}
<% 	if ("Y".equals(opt343)) { %>	
			if (bodyfile[loop].htmlfile != "" && typeof bodyfile[loop].htmlfile != "undefined") {
				filename = "HtmlBody_" + UUID.generate() + ".html";
				filepath = FileManager.getlocaltempdir() + filename;
				
				// Html 모바일본문 생성
				saveHtmlDocument(hwpCtrl, filepath, false);
				var htmlfile = new Object();
				htmlfile.type = "body";
				htmlfile.localpath = filepath;
				result = FileManager.uploadfile(htmlfile);
				filelength = result.length;
				for (var pos = 0; pos < filelength; pos++) {
					var file = result[pos];
					docidinfo += bodyfile[loop].docid + String.fromCharCode(4);
					bodyinfo += bodyfile[loop].htmlfile + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
				    "<%=aft002%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
				    "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + htmlfile.localpath + String.fromCharCode(4);
				}
			}
<%	} %>			

			$("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val(bodyinfo);
			totalbodyinfo += bodyinfo;	
<% } %>
		}  // for

<% if (serialNumber != -1) { %>
		$.post("<%=webUri%>/app/approval/updateBody.do", "docid=" + docidinfo + "&file=" + totalbodyinfo, function(resultdata) {
			if (resultdata.result == "success") {
 				$("#waiting").hide();
				$("#afterprocess").show(); 
				<% if (!"N".equals(editbodyYn)) { %>			
				$("#savebody").hide();
				$("#modifybody").hide();
				<% } %>		
				screenUnblock();
				alert(data.message);
				<% if ("Y".equals(opt357)) { %>
				// 정상처리되면 창을 닫는다.
				exitAppDoc();
				<% } %>
			} else if (resultdata.result == "fail" && retrycount < 5) {
				retrycount++;
				afterRegReject(data);
			}	
		}, 'json');		
<% } else { %>
 		$("#waiting").hide();
		$("#afterprocess").show(); 
		<% if (!"N".equals(editbodyYn)) { %>			
		$("#savebody").hide();
		$("#modifybody").hide();
		<% } %>
		screenUnblock();
		alert(data.message);
		<% if ("Y".equals(opt357)) { %>
		// 정상처리되면 창을 닫는다.
		exitAppDoc();
		<% } %>			
<% } %>
}
}



// 재상신
function insertAppDoc() {	
	// 재기안 일 경우는 본문 편집모드 변경하지 않음(1안)
	var currentItem = getCurrentItem();
	$("#bodyEdited", "#approvalitem" + currentItem).val("Y");
	var bodylist = transferFileList($("#bodyFile", "#approvalitem" + currentItem).val());
	if (bodylist.length > 0) {
		saveHwpDocument(Document_HwpCtrl, bodylist[0].localpath);
	}
	setSavePoint(Document_HwpCtrl);
	
	var user = getCurrentApprover($("#appLine", "#approvalitem" + getCurrentItem()).val(), "<%=userId%>");
	if (user == null) {
		alert("<spring:message code='approval.msg.check.user.appline'/>");
		selectAppLine();
	} else {
		if (arrangeAppline()) {
			var width = 800;
			<% if ("1".equals(opt301)) { %>
			var height = 450;
			<% } else { %>
			var height = 420;
			<% } %>

			if (bodyType == "hwp" || bodyType == "doc") {
				if (checkSubmitData("submit")) {
					$("#draftType").val(getProcessTypeofAppLine($("#draftType").val()));
				
					//opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createOpinion.do?askType=" + user.askType + "&actType=<%=apt001%>&formBodyType=" + bodyType, width, height);
				}
			} else if (bodyType == "html") {
				if (checkSubmitDataHTML("submit", "edit")) {
					$("#draftType").val(getProcessTypeofAppLine($("#draftType").val()));
					
					//opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createOpinion.do?askType=" + user.askType + "&actType=<%=apt001%>&formBodyType=" + bodyType, width, height);
				}
			}
			
			setOpinion("", user.askType,"<%=apt001%>");		// 15.10.28 결재 의견 제거
		} else {
			selectAppLine();
		}
	}
}

function submitApproval() {
	// 편철 다국어 추가
	var itemnum = getCurrentItem();
	$("#bindingName", "#approvalitem" + itemnum).val($("#bindingResourceId", "#approvalitem" + itemnum).val());
		
	moveToPos(Document_HwpCtrl, 2);
	$("#beforeprocess").hide();
	$("#waiting").show();

	$.post("<%=webUri%>/app/approval/insertAppDoc.do", $("#appDocForm").serialize(), function(data){
		if (data.result == "success") {
			var insertedDocId = data.docId;			
			if (afterSubmit) {
				afterSubmit(data);
				var erpId = $("#erpId").val();
				//erpId를 결재테이블에 넣으면 문서를 불러올 때 erpId에 담기만 하면 되고, erp테이블에 넣으면 전표입력을 선택할 때 erp 테이블을 검색 하면 된다.
				// -- >erp테이블에 넣을것이다.
				if(erpId!=""){
					//최종결재자일때만 실db에 입력되고, 최초기안자만 수정할 수 있다. 
					$.post("<%=webUri%>/app/approval/insertStatementToDB.do", "&erpId=" + erpId +"&docId="+insertedDocId, function(data) {
						if (data.result == "success") {
							//alert("전표입력이 완료되었습니다.");
						}else{
							//alert("전표입력이 실패하었습니다.");
						}
					}, 'json').error(function(data) {
						//alert("전표입력이 실패하였습니다. - ajax 에러");
					});	
				}
			}
		} else {
			$("#waiting").hide();
			$("#beforeprocess").show();
			screenUnblock();
			alert(data.message);
		}
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='approval.msg.fail.insertdocument'/>");
		}
	});
<% if ("1".equals(opt301)) { %>		
	$("#password", "#appDocForm").val("");
	$("#roundkey", "#appDocForm").val("");
<% } %>
}


function afterSubmit(data) {
	if (data.state == "<%=app600%>" || data.state == "<%=app610%>") {
		var docidinfo = "";
		var totalbodyinfo = "";
		var bodyfile = data.bodyfile;
		var bodycount = bodyfile.length;
		var currentItem = getCurrentItem();

		for (var loop = 0; loop < bodycount; loop++) {
			var hwpCtrl = Document_HwpCtrl;
			$("#docId", "#approvalitem" + bodyfile[loop].itemnum).val(bodyfile[loop].docid);
<% if (serialNumber != -1) { %>
			// 현재 안건이 아닌 경우 문서오픈
			var bodyinfo = "";			
			if (bodyfile[loop].itemnum != currentItem) {
				hwpCtrl = Enforce_HwpCtrl;
				reloadHiddenBody($("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val());
			}
			// 시행번호삽입
			var deptCategory = $("#deptCategory", "#approvalitem" + bodyfile[loop].itemnum).val();
			putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, deptCategory + "-" + bodyfile[loop].serial);
			// 시행번호가 삽입되었는지 2번 더 체크
			if (existField(hwpCtrl, HwpConst.Form.EnforceNumber)) {
				if ($.trim(getFieldText(hwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
					putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, deptCategory + "-" + bodyfile[loop].serial);
					if ($.trim(getFieldText(hwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
						putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, deptCategory + "-" + bodyfile[loop].serial);
					}
				}
			}

			var filename = "";
			var filepath = "";
			
			if (bodyfile[loop].hwpfile != "") {
				//본문생성
				if (bodyType == "hwp" || bodyType == "doc") {
					if(bodyType == "doc"){ 
						filename = "DocBody_" + UUID.generate() + ".doc";
					}else {
						filename = "HwpBody_" + UUID.generate() + ".hwp";
					} 
					
					filepath = FileManager.getlocaltempdir() + filename;
					saveHwpDocument(hwpCtrl, filepath, false);
					
					var hwpfile = new Object();
					hwpfile.type = "body";
					hwpfile.localpath = filepath;
					var result = FileManager.uploadfile(hwpfile);
					var filelength = result.length;
					for (var pos = 0; pos < filelength; pos++) {
						var file = result[pos];
						docidinfo += bodyfile[loop].docid + String.fromCharCode(4);
						bodyinfo += bodyfile[loop].hwpfile + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
					    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
					    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
					}
				} else if (bodyType == "html") {
					docidinfo = bodyfile[loop].docid + String.fromCharCode(4);
				}
			}
<% 	if ("Y".equals(opt343)) { %>	
			if (bodyfile[loop].htmlfile != "" && typeof bodyfile[loop].htmlfile != "undefined") {
				filename = "HtmlBody_" + UUID.generate() + ".html";
				filepath = FileManager.getlocaltempdir() + filename;
				
				// Html 모바일본문 생성
				if(bodyType == "hwp" || bodyType == "doc") {	//문서편집기가 한글, MS-Word인 경우
					saveHtmlDocument(hwpCtrl, filepath, false);
					var htmlfile = new Object();
					htmlfile.type = "body";
					htmlfile.localpath = filepath;
					result = FileManager.uploadfile(htmlfile);
					filelength = result.length;
					for (var pos = 0; pos < filelength; pos++) {
						var file = result[pos];
						docidinfo += bodyfile[loop].docid + String.fromCharCode(4);
						bodyinfo += bodyfile[loop].htmlfile + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
					    "<%=aft002%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
					    "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + htmlfile.localpath + String.fromCharCode(4);
					}
				}else {											//문서편집기가 HTML인 경우
					docidinfo += bodyfile[loop].docid + String.fromCharCode(4);
					saveHtmlDocument(currentItem, filename);
				}
			}
<%	} %>	

			if (bodyType == "hwp" || bodyType == "doc") {
				$("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val(bodyinfo);
				totalbodyinfo += bodyinfo;
			} else if (bodyType == "html") {
				totalbodyinfo = $("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val();
			}	

<% } %>
		}

<% if (serialNumber != -1) { %>
		$.post("<%=webUri%>/app/approval/updateBody.do", "docid=" + docidinfo + "&file=" + totalbodyinfo, function(resultdata) {
			if (resultdata.result == "success") {
		 		var autoSendFlag = false;
				for (var loop = 0; loop < bodycount; loop++) { 	 		
					var enfType = $("#enfType", "#approvalitem" + bodyfile[loop].itemnum).val();
		 			var autoSendYn = $("#autoSendYn", "#approvalitem" + bodyfile[loop].itemnum).val();
		 			// 대내문서자동발송체크추가  // jth8172 2012 신결재 TF
		 			if (enfType == "<%=det002%>" && "<%=autoInnerSendYn%>" == "Y" || ((enfType == "<%=det003%>" || enfType == "<%=det004%>") && autoSendYn == "Y")) {
		 				var filecount = resultdata.fileidlist.length;
		 				for (var loop = 0; loop < filecount; loop++) {
		 					for (var pos = 0; pos < bodycount; pos++) {
		 						if (data.bodyfile[pos].hwpfile == resultdata.fileidlist[loop]) {
		 							data.bodyfile[pos].hwpfile = resultdata.filelist[loop].fileid;
			 						break;
		 						} else if (data.bodyfile[pos].htmlfile == resultdata.fileidlist[loop]) {
		 							data.bodyfile[pos].htmlfile = resultdata.filelist[loop].fileid;
			 						break;
		 						}
		 					}
		 				}
		 				autoSendFlag = true;
		 				break;
		 			}
				}
				if (autoSendFlag) {
					setTimeout(function(){autoSend(data);}, 100);			
				}
				$("#waiting").hide();
				$("#afterprocess").show();
				<% if (!"N".equals(editbodyYn)) { %>			
				$("#savebody").hide();
				$("#modifybody").hide();
				<% } %>		
				screenUnblock();
				alert(data.message);
				<% if ("Y".equals(opt357)) { %>
				// 정상처리되면 창을 닫는다.
				exitAppDoc();
				<% } %>
			} else if (resultdata.result == "fail" && retrycount < 5) {
				retrycount++;
				afterSubmit(data);
			}	
		}, 'json');		
<% } else { %>
		var bodyfile = data.bodyfile;
		var bodycount = bodyfile.length;
		var autoSendFlag = false;
		for (var loop = 0; loop < bodycount; loop++) { 	 		
			var enfType = $("#enfType", "#approvalitem" + bodyfile[loop].itemnum).val();
			var autoSendYn = $("#autoSendYn", "#approvalitem" + bodyfile[loop].itemnum).val();
 			// 대내문서자동발송체크추가  // jth8172 2012 신결재 TF
 			if (enfType == "<%=det002%>" && "<%=autoInnerSendYn%>" == "Y" || ((enfType == "<%=det003%>" || enfType == "<%=det004%>") && autoSendYn == "Y")) {
				autoSendFlag = true;
				break;
			}
		}

		if (autoSendFlag) {
			setTimeout(function(){autoSend(data);}, 100);			
		}

		$("#waiting").hide();
		$("#afterprocess").show();

		
		<% if (!"N".equals(editbodyYn)) { %>			
		$("#savebody").hide();
		$("#modifybody").hide();
		<% } %>
		screenUnblock();
		alert(data.message);
		<% if ("Y".equals(opt357)) { %>
		// 정상처리되면 창을 닫는다.
		exitAppDoc();
		<% } %>			
<% } %>
	} else {
		$("#waiting").hide();
		$("#afterprocess").show();
		<% if (!"N".equals(editbodyYn)) { %>			
		$("#savebody").hide();
		$("#modifybody").hide();
		<% } %>		
		screenUnblock();

		// 기안대기일경우 다른 메시지를 보여준다.
		if ("APP100" == "<%= docState %>") {
			// alert("문서 상신 처리가 완료되었습니다.");
			alert("<spring:message code='approval.msg.success.insertdocument'/>");
		} else {
			alert(data.message);
		}

		<% if ("Y".equals(opt357)) { %>
		// 정상처리되면 창을 닫는다.
		exitAppDoc();
		<% } %>			
	}
}



//자동발송시 프로세스 변경 start   // jth8172 2012 신결재 TF ----------------------------
//자동발송
function autoSend(data) {
	docData = data;  //별도 함수에서 사용하기위해 저장 
	
	if (bodyType == "hwp" || bodyType == "doc") {
		if( "<%=autoInnerSendYn%>" == "Y" && "1" == "<%=autoSealType%>") { 	
			$.post("<%=webUri%>/app/approval/selectOrgSealFirst.do?drafterId=<%=userId%>", $("#appDocForm").serialize(), function(resultdata) { 
				// alert("autoSend : "+resultdata.result);
			if (resultdata.result != "") { //서명인 파일정보 가져오기(한번만 가져오기 위함)
				var fileInfos = ""; 
				fileInfos = resultdata.result.split(",");
				sealFile.fileid = fileInfos[0];
				sealFile.filename = fileInfos[1]; 
				sealFile.title = fileInfos[2];
				sealFile.displayname =fileInfos[1] +fileInfos[4];
				sealFile.filetype = fileInfos[4];
				sealFile.filewidth = fileInfos[5];
				sealFile.fileheight = fileInfos[6];
				sealFile.type="savebody";
				stampFilePath = FileManager.savebodyfile(sealFile); //서명파일로컬저장
				sealFile.stampfilepath=stampFilePath;
				$("#stampId").val(sealFile.fileid);
				$("#stampName").val(sealFile.title);
				$("#stampExt").val(sealFile.filetype);
				$("#stampFileName").val(sealFile.filename);
				$("#stampFilePath").val(sealFile.stampfilepath);
				$("#stampImageWidth").val(sealFile.filewidth);
				$("#stampImageHeight").val(sealFile.fileheight);
				$("#stampDisplayName").val(sealFile.title +"." + sealFile.filetype);
				$("#stampFileType").val("<%=spt002%>");

				// 파일 업로드(처음한번만)
				var  stampfile = new Object();
				stampfile.filename = sealFile.filename;
				stampfile.localpath =  stampFilePath;
				stampfile.type = "upload";

				var stampfilelist = new Array();
				nextYn = "Y";
				stampfilelist = FileManager.uploadfile(stampfile, true ); //업로드 성공후 nextprocess 호출됨.
			} else {
				//발송용 정보 저장,발송 호출
				setTimeout(function(){buildSend();}, 100);
			}	
			},'json').error(function(data) {
				var context = data.responseText;
				if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
					alert("<spring:message code='common.msg.include.badinformation'/>");
				} else {
					alert("<spring:message code='approval.result.msg.stampfail'/>");
				}
			});	
			
		} else if("<%=autoInnerSendYn%>" == "Y" && "3" == "<%=autoSealType%>") { // 최종결재자 서명 자동으로 날인(서명인 날인대장 등재) 서명이미지가 없으면 날인안함(서명인 날인대장 미등재)
			// 최종결재자서명인 파일 업로드(처음한번만)
			if(signpath !="" ) {
				var  stampfile = new Object();
				stampfile.filename = sign.filename;
				stampfile.localpath =  signpath;
				stampfile.type = "upload";
				var stampfilelist = new Array();
				nextYn = "Y";
				var signId = UUID.generate();
				var signFileName = sign.filename;
				$("#stampId").val(signId);
				$("#stampExt").val(signFileName.substring(signFileName.lastIndexOf(".")));
				$("#stampFileName").val(signFileName);
				$("#stampFilePath").val(signpath);
				$("#stampImageWidth").val("30");
				$("#stampImageHeight").val("20");
				$("#stampDisplayName").val("<%=userName%>." + signFileName.substring(signFileName.lastIndexOf(".") ) );
				$("#stampFileType").val("<%=spt002%>");
				
				stampfilelist = FileManager.uploadfile(stampfile, true ); //업로드 성공후 nextprocess 호출됨.

			} else {
				setTimeout(function(){buildSend();}, 100);
			}
		}
	}
}	
//발송용 정보 저장,발송		
function buildSend() {
	var data =  docData;
	var bodyfile = data.bodyfile;
	var bodycount = bodyfile.length;
	var currentItem = getCurrentItem();

	for (var loop = 0; loop < bodycount; loop++) {
		var hwpCtrl = Document_HwpCtrl;
		// 현재 안건이 아닌 경우 문서오픈
		var bodyinfo = "";
		if (bodyfile[loop].itemnum != currentItem) {
			hwpCtrl = Enforce_HwpCtrl;
			reloadHiddenBody($("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val());
		}
		// 발송정보삽입
		var enfType = $("#enfType", "#approvalitem" + bodyfile[loop].itemnum).val();
		var autoSendYn = $("#autoSendYn", "#approvalitem" + bodyfile[loop].itemnum).val();
		// 대내문서자동발송체크추가  // jth8172 2012 신결재 TF
		if (enfType == "<%=det002%>" && "<%=autoInnerSendYn%>" == "Y" || ((enfType == "<%=det003%>" || enfType == "<%=det004%>") && autoSendYn == "Y")) {
			// 시행일자
			putFieldText(hwpCtrl, HwpConst.Form.EnforceDate, typeOfDate("", getCurrentDate()));
			// 시행일자가 삽입되었는지 2번 더 체크
			if (existField(hwpCtrl, HwpConst.Form.EnforceDate)) {
				if ($.trim(getFieldText(hwpCtrl, HwpConst.Form.EnforceDate)) == "") {
					putFieldText(hwpCtrl, HwpConst.Form.EnforceDate, typeOfDate("", getCurrentDate()));
					if ($.trim(getFieldText(hwpCtrl, HwpConst.Form.EnforceDate)) == "") {
						putFieldText(hwpCtrl, HwpConst.Form.EnforceDate, typeOfDate("", getCurrentDate()));
					}
				}
			}
			
			//자동발송시 날인방법에 따라 날인하고 서명인날인대장에 등록  start // jth8172 2012 신결재 TF -------------------------------
			if(enfType == "<%=det002%>" && "<%=autoInnerSendYn%>" == "Y" && "1" == "<%=autoSealType%>") { 
				// 기안자부서서명인 중 순서가 0인 서명을 가져와 자동으로 날인(서명인 날인대장 등재)  서명이미지가 없으면 날인안함(서명인 날인대장 미등재)
				// 0.기안자부서서명인 가져오기 
				if(stampFilePath != "") {  //서명인 있으면
					//1. 날인대장등록관련 자료 셋팅
					$("#sealType", "#approvalitem" + bodyfile[loop].itemnum).val("<%=spt002%>");

					// 날인
					insertSeal(hwpCtrl, sealFile.stampfilepath, sealFile.filewidth, sealFile.fileheight);
					if (existField(hwpCtrl, HwpConst.Form.Seal)) {
						insertSeal(hwpCtrl, sealFile.stampfilepath, sealFile.filewidth, sealFile.fileheight);
						if (existField(hwpCtrl, HwpConst.Form.Seal)) {
							insertSeal(hwpCtrl, sealFile.stampfilepath, sealFile.filewidth, sealFile.fileheight);
						}
					}	
				} else {
					// 없으면 미날인
					$("#sealType", "#approvalitem" + bodyfile[loop].itemnum).val("");
	 			}	
			} else if(enfType == "<%=det002%>" && "<%=autoInnerSendYn%>" == "Y" && "2" == "<%=autoSealType%>") { // 서명생략인 자동으로 날인(서명인 날인대장 미등재)
				$("#stampFileType", "#approvalitem" + bodyfile[loop].itemnum).val("<%=spt004%>");
				$("#sealType", "#approvalitem" + bodyfile[loop].itemnum).val("");
				showOmitSignature(hwpCtrl);
			} else if(enfType == "<%=det002%>" && "<%=autoInnerSendYn%>" == "Y" && "3" == "<%=autoSealType%>") { // 최종결재자 서명 자동으로 날인(서명인 날인대장 등재) 서명이미지가 없으면 날인안함(서명인 날인대장 미등재)
				// 0.최종결재자 서명가져오기
				if(signpath != "") {
					$("#sealType", "#approvalitem" + bodyfile[loop].itemnum).val("<%=spt002%>");
	
					// 날인
					insertSeal(hwpCtrl, signpath, "30", "20");
					if (existField(hwpCtrl, HwpConst.Form.Seal)) {
						insertSeal(hwpCtrl, signpath,"30", "20");
						if (existField(hwpCtrl, HwpConst.Form.Seal)) {
							insertSeal(hwpCtrl, signpath, "30", "20");
						}
					}	
				} else {
					// 없으면 미날인
					$("#sealType", "#approvalitem" + bodyfile[loop].itemnum).val("");
				}	
			 
			}	
			//자동발송시 날인방법에 따라 날인하고 서명인날인대장에 등록  end // jth8172 2012 신결재 TF -------------------------------
		//기존 발송로직을 날인 이후로 변경
		
	// 변경된 본문 파일을 저장 start	 
	var filename = "";
	var filepath = "";
		
	if (bodyfile[loop].hwpfile != "") {
		//본문생성
		if (bodyType == "hwp" || bodyType == "doc") {
			if(bodyType == "doc"){ 
				filename = "DocBody_" + UUID.generate() + ".doc";
			}else {
				filename = "HwpBody_" + UUID.generate() + ".hwp";
			} 
			
			filepath = FileManager.getlocaltempdir() + filename;
			saveHwpDocument(hwpCtrl, filepath, false);
			
			var hwpfile = new Object();
			hwpfile.type = "body";
			hwpfile.localpath = filepath;
			var result = FileManager.uploadfile(hwpfile);
			var filelength = result.length;
			for (var pos = 0; pos < filelength; pos++) {
				var file = result[pos];
				bodyinfo += bodyfile[loop].hwpfile + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
			    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
			    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
			}
		}
	}
	<% if ("Y".equals(opt343)) { %>	
		if (bodyfile[loop].htmlfile != "" && typeof bodyfile[loop].htmlfile != "undefined") {
			filename = "HtmlBody_" + UUID.generate() + ".html";
			filepath = FileManager.getlocaltempdir() + filename;
			
			// Html 모바일본문 생성
			if(bodyType == "hwp" || bodyType == "doc") {	//문서편집기가 한글, MS-Word인 경우
				saveHtmlDocument(hwpCtrl, filepath, false);
				var htmlfile = new Object();
				htmlfile.type = "body";
				htmlfile.localpath = filepath;
				result = FileManager.uploadfile(htmlfile);
				filelength = result.length;
				for (var pos = 0; pos < filelength; pos++) {
					var file = result[pos];
					bodyinfo += bodyfile[loop].htmlfile + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
				    "<%=aft002%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
					    "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + htmlfile.localpath + String.fromCharCode(4);
				}
			}else {											//문서편집기가 HTML인 경우
				saveHtmlDocument(currentItem, filename);
			}
		}
	<% } %>			

	//html의 경우 이전에 이미 값을 Setting하므로 한글, MS-Word의 경우에만 bodyinfo 값 Setting
	if (bodyType == "hwp" || bodyType == "doc") {
		$("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val(bodyinfo);
	}
// 변경된 본문 파일을 저장 end	 
}// if 발송문서
}	 // for	

	//자동발송 호출
	$.post("<%=webUri%>/app/approval/sendDocAuto.do", $("#appDocForm").serialize(), function(resultdata) { 
	if (resultdata.result == "fail" && resultdata.message == "<spring:message code='approval.msg.fail.modifybody.incorrect.size'/>" && retrycount < 5) {
		retrycount++;
		buildSend(data);
	}
	},'json').error(function(data) {
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='approval.result.msg.sendenforcefail'/>");
		}
	});		

	
}			


//서명파일 업로드 처리후 프로세스(날인의 경우)
function nextprocess(filelist){

	var file = new Array();
	if (filelist instanceof Array) {
		file = filelist;
	} else {
		file[0] = filelist;
	}	
	$("#stampFileId").val(file[0].fileid);
	$("#stampFileSize").val(file[0].size);
	//발송용 정보 저장,발송 호출
	setTimeout(function(){buildSend();}, 100);
	
}


//자동발송시 프로세스 변경 End   // jth8172 2012 신결재 TF ----------------------------


 

// 후열
function readafterAppDoc() {
	$("#beforeprocess").hide();
	$.post("<%=webUri%>/app/approval/readafterAppDoc.do", $("#appDocForm").serialize(), function(data) {
		screenUnblock();
		alert(data.message);
		<% if ("Y".equals(opt357)) { %>
		// 정상처리되면 창을 닫는다.
		exitAppDoc();
		<% } %>			
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		alert("<spring:message code='approval.msg.fail.readafter'/>");
	});
}

//통보  // jth8172 2012 신결재 TF
function informAppDoc() {
	$("#beforeprocess").hide();
	$.post("<%=webUri%>/app/approval/informAppDoc.do", $("#appDocForm").serialize(), function(data) {
		screenUnblock();
		alert(data.message);
		<% if ("Y".equals(opt357)) { %>
		// 정상처리되면 창을 닫는다.
		exitAppDoc();
		<% } %>			
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		alert("<spring:message code='approval.msg.fail.inform'/>");
	});
}

// 공람
function pubreadAppDoc() {
	$("#beforeprocess").hide();
	$.post("<%=webUri%>/app/appcom/processPubReader.do", $("#appDocForm").serialize(), function(data) {
		screenUnblock();
		alert(data.message);
		<% if ("Y".equals(opt357)) { %>
		// 정상처리되면 창을 닫는다.
		exitAppDoc();
		<% } %>			
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		alert("<spring:message code='appcom.msg.fail.pubread'/>");
	});
}

//문서정보조회
function selectDocInfo() {
	docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/approval/selectDocInfo.do?bodyType=<%= strBodyType %>&docId=" + $("#docId", "#approvalitem" + getCurrentItem()).val()+"&securityYn="+$("#securityYn","#approvalitem" + getCurrentItem()).val(), <%=(adminstratorFlag ? 850 : 850)%>, 450);
}


//문서정보
function insertDocInfo() {
	if (bodyType == "html") {
		if (getAppLineStatus() == "insert") { 
			var itemnum = getCurrentItem();
			$("#title", "#approvalitem" + itemnum).val(getHtmlTitleText());
		}
	}
	
	docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/approval/createDocInfo.do?owndept=<%=ownDeptId%>&docstate=<%=docState%>"+"&securityYn="+$("#securityYn","#approvalitem" + getCurrentItem()).val(), 580, 550);
}

function getDocInfo() {
	var itemnum = getCurrentItem();
	if (existField(Document_HwpCtrl, HwpConst.Form.Title)) {
		$("#title", "#approvalitem" + itemnum).val($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.Title)));
	}

	var docInfo = new Object();
	docInfo.docId = $("#docId", "#approvalitem" + itemnum).val();
	docInfo.title = $("#title", "#approvalitem" + itemnum).val();
	docInfo.bindingId = $("#bindingId", "#approvalitem" + itemnum).val();
	docInfo.bindingName = $("#bindingName", "#approvalitem" + itemnum).val();

	// 편철 다국어 추가
	docInfo.bindingResourceId = $("#bindingResourceId", "#approvalitem" + itemnum).val();
	
	docInfo.conserveType = $("#conserveType", "#approvalitem" + itemnum).val();
	docInfo.readRange = $("#readRange", "#approvalitem" + itemnum).val();
	docInfo.auditReadYn = $("#auditReadYn", "#approvalitem" + itemnum).val();
	docInfo.auditReadReason = $("#auditReadReason", "#approvalitem" + itemnum).val();
	docInfo.auditYn = $("#auditYn", "#approvalitem" + itemnum).val();
	docInfo.deptCategory = $("#deptCategory", "#approvalitem" + itemnum).val();
	docInfo.serialNumber = $("#serialNumber", "#approvalitem" + itemnum).val();
	docInfo.subserialNumber = $("#subserialNumber", "#approvalitem" + itemnum).val();
	docInfo.sendOrgName = $("#sendOrgName", "#approvalitem" + itemnum).val(); //기관명   // jth8172 2012 신결재 TF
	docInfo.logoPath = $("#logoPath", "#approvalitem" + itemnum).val(); 		//로고   // jth8172 2012 신결재 TF
	docInfo.symbolPath = $("#symbolPath", "#approvalitem" + itemnum).val(); 	//심볼   // jth8172 2012 신결재 TF
	docInfo.senderTitle = $("#senderTitle", "#approvalitem" + itemnum).val();
	docInfo.headerCamp = $("#headerCamp", "#approvalitem" + itemnum).val();
	docInfo.footerCamp = $("#footerCamp", "#approvalitem" + itemnum).val();
	docInfo.urgencyYn = $("#urgencyYn", "#approvalitem" + itemnum).val();
	docInfo.autoSendYn = $("#autoSendYn", "#approvalitem" + itemnum).val();
	docInfo.enfType = $("#enfType", "#approvalitem" + itemnum).val();
	docInfo.publicPost = $("#publicPost", "#approvalitem" + itemnum).val();
	docInfo.openLevel = $("#openLevel", "#approvalitem" + itemnum).val();  // jth8172 2012 신결재 TF
	docInfo.openReason= $("#openReason", "#approvalitem" + itemnum).val(); // jth8172 2012 신결재 TF
	
	docInfo.enfBound = getEnfBound($("#appRecv", "#approvalitem" + itemnum).val());
	docInfo.docType = $("#docType", "#approvalitem" + itemnum).val();

	docInfo.classNumber = $("#classNumber", "#approvalitem" + itemnum).val();
	docInfo.docnumName = $("#docnumName", "#approvalitem" + itemnum).val();

	docInfo.transferYn = $("#transferYn", "#approvalitem" + itemnum).val();
	docInfo.drafterDeptId = "<%=drafterDeptId%>";

	//보안지정관련 설정을 추가함.. by jkkim start
	docInfo.securityYn = $("#securityYn", "#approvalitem" + itemnum).val();
	docInfo.securityStartDate = $("#securityStartDate", "#approvalitem" + itemnum).val();
	docInfo.securityEndDate = $("#securityEndDate", "#approvalitem" + itemnum).val();
	if("<%=opt411%>" == "2")
		docInfo.securityPass = $("#securityPass", "#approvalitem" + itemnum).val();
	//end
	
	return docInfo;
}

function setDocInfo(docInfo) {
	var itemnum = getCurrentItem();
	var itemcount = getItemCount();

	$("#title", "#approvalitem" + itemnum).val(docInfo.title);
	if (bodyType == "hwp" || bodyType == "doc") {
		putFieldText(Document_HwpCtrl, HwpConst.Form.Title, docInfo.title);
	} else if (bodyType == "html") {
		putHtmlTitleText(docInfo.title);
	}		
	
	$("#bindingId", "#approvalitem" + itemnum).val(docInfo.bindingId);
	$("#bindingName", "#approvalitem" + itemnum).val(docInfo.bindingName);

	// 편철 다국어 추가
	$("#bindingResourceId", "#approvalitem" + itemnum).val(docInfo.bindingResourceId);
	
	$("#conserveType", "#approvalitem" + itemnum).val(docInfo.conserveType);
	$("#openLevel", "#approvalitem" + itemnum).val(docInfo.openLevel);  // jth8172 2012 신결재 TF
	$("#openReason", "#approvalitem" + itemnum).val(docInfo.openReason);  // jth8172 2012 신결재 TF
	$("#deptCategory", "#approvalitem" + itemnum).val(docInfo.deptCategory);
	$("#readRange", "#approvalitem" + itemnum).val(docInfo.readRange);
	$("#auditReadYn", "#approvalitem" + itemnum).val(docInfo.auditReadYn);
	$("#auditReadReason", "#approvalitem" + itemnum).val(docInfo.auditReadReason);
	for (var loop = 0; loop < itemcount; loop++) {
		$("#auditYn", "#approvalitem" + (loop + 1)).val(docInfo.auditYn);
	}
	$("#senderTitle", "#approvalitem" + itemnum).val(docInfo.senderTitle);

	$("#sendOrgName", "#approvalitem" + itemnum).val(docInfo.sendOrgName);  //기관명   // jth8172 2012 신결재 TF
	putFieldText(Document_HwpCtrl, HwpConst.Form.OrganName, docInfo.sendOrgName);
	$("#logoPath", "#approvalitem" + itemnum).val(docInfo.logoPath);  		//로고   // jth8172 2012 신결재 TF
	if (docInfo.logoPath != "") {
		clearImage(Document_HwpCtrl, HwpConst.Form.Logo);
		insertImage(Document_HwpCtrl, HwpConst.Form.Logo, docInfo.logoPath, 20, 20);
	}
	$("#symbolPath", "#approvalitem" + itemnum).val(docInfo.symbolPath);  		//심볼   // jth8172 2012 신결재 TF
	if (docInfo.symbolPath != "") {
		clearImage(Document_HwpCtrl, HwpConst.Form.Symbol);
		insertImage(Document_HwpCtrl, HwpConst.Form.Symbol, docInfo.symbolPath, 20, 20);
	}
	$("#headerCamp", "#approvalitem" + itemnum).val(docInfo.headerCamp);
	$("#footerCamp", "#approvalitem" + itemnum).val(docInfo.footerCamp);
	$("#urgencyYn", "#approvalitem" + itemnum).val(docInfo.urgencyYn);
	if (docInfo.autoSendYn == "Y" && getEnfBound($("#appRecv", "#approvalitem" + itemnum).val()) == "OUT") {
		docInfo.autoSendYn = "N";
	}
	$("#autoSendYn", "#approvalitem" + itemnum).val(docInfo.autoSendYn);
	$("#publicPost", "#approvalitem" + itemnum).val(docInfo.publicPost);

	//보안지정관련 설정을 추가함.. by jkkim start
	$("#securityYn", "#approvalitem" + itemnum).val(docInfo.securityYn);
	$("#securityStartDate", "#approvalitem" + itemnum).val(docInfo.securityStartDate);
	$("#securityEndDate", "#approvalitem" + itemnum).val(docInfo.securityEndDate);
	if("<%=opt411%>" == "2")
		$("#securityPass", "#approvalitem" + itemnum).val(docInfo.securityPass);
	//end

	setOpenLevel(Document_HwpCtrl); // 공개범위 설정
	
	putFieldText(Document_HwpCtrl, HwpConst.Form.ConserveType, typeOfConserveType(docInfo.conserveType));
	putFieldText(Document_HwpCtrl, HwpConst.Form.ReadRange, typeOfReadRange(docInfo.readRange));
	putFieldText(Document_HwpCtrl, HwpConst.Form.HeaderCampaign, docInfo.headerCamp);
	putFieldText(Document_HwpCtrl, HwpConst.Form.FooterCampaign, docInfo.footerCamp);

	//내부문서는 발신명의 생략
	var recvList = getReceiverList($("#appRecv", "#approvalitem" + itemnum).val());
	var recvsize = recvList.length;
	if (recvsize == 0) {
		putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, "");
	} else {
		putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, docInfo.senderTitle);
	}

	$("#classNumber", "#approvalitem" + itemnum).val(docInfo.classNumber);
	$("#docnumName", "#approvalitem" + itemnum).val(docInfo.docnumName);

	moveToPos(Document_HwpCtrl, 2);
}


function setOpenLevel(Document_HwpCtrl){
	var itemnum = getCurrentItem();
	var itemcount = getItemCount();

	//공개범위 시작
	var strValue = $("#openLevel", "#approvalitem" + itemnum).val();
	var strPLOpen = strValue.charAt(0);
	var strReason = $("#openReason", "#approvalitem" + itemnum).val();
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
	putFieldText(Document_HwpCtrl, HwpConst.Form.PublicBound, strOpenLevel);
	//공개범위 끝	
}


// 보고경로
function selectAppLine() {
	var itemnum = getCurrentItem();
	var audittype = $("#auditYn", "#approvalitem" + itemnum).val();

	if (opentype == "I") {
		applineWin = openWindow("applineWin", "<%=webUri%>/app/approval/ApprovalLine.do?groupYn=N&linetype=<%="Y".equals(doubleYn) ? 2 : 1%>&audittype=" + audittype + "&formBodyType=" + bodyType, 650, 650); // 1 : 일반결재, 2 : 이중결재
	} else {
		applineWin = openWindow("applineWin", "<%=webUri%>/app/approval/selectAppLine.do?opentype=" + opentype + "&linetype=<%="Y".equals(doubleYn) ? 2 : 1%>&audittype=" + audittype + "&formBodyType=" + bodyType, 650, 650); // 1 : 일반결재, 2 : 이중결재
	}
}

function getAppLine() {
	var itemnum = getCurrentItem();
	return $("#appLine", "#approvalitem" + itemnum).val();
}

function setAppLine(appline, isinit) {
	if (typeof(isinit) == "undefined") {
		isinit = false;
	}

	//added by jkkim 2013.04.23 about WORD
	if(bodyType == "doc"){
		clearApprovalField();
		setApprLineForWord(appline, isinit, "<%= doubleYn %>");
		return;
	}

	var itemCount = getItemCount();
	var currentItem = getCurrentItem();

	if (appline != $("#appLine", "#approvalitem" + currentItem).val() || isinit) {
<% if ("Y".equals(doubleYn)) { %> // 이중결재
		var baseDraftLine = 10;
		var baseExecLine = 10;
		var line = getApproverList(appline);
		var tobeDraftLine = getApproverCountByLine(line, 1);
		var tobeExecLine = getApproverCountByLine(line, 2);
		var asisDraftLine = getLineApproverCount(Document_HwpCtrl, 1);
		var asisExecLine = getLineApproverCount(Document_HwpCtrl, 2);
		if (!existField(Document_HwpCtrl, HwpConst.Field.DraftDeptLine)) {
			baseDraftLine = asisDraftLine;
		}
		if (!existField(Document_HwpCtrl, HwpConst.Field.ExecDeptLine)) {
			baseExecLine = asisExecLine;
		}
		if (baseDraftLine == 0 && baseExecLine == 0) {
			alert("<spring:message code='approval.msg.noapprovertable'/>");
			return;
		} else if (tobeDraftLine > baseDraftLine || tobeExecLine > baseExecLine) {
			if (!confirm("<spring:message code='approval.msg.exceed.double.appline'/>")) {
				selectAppLine();
				return;
			}
		}
		for (var loop = 0; loop < itemCount; loop++) {
			var itemnum = loop + 1;
			$("#appLine", "#approvalitem" + itemnum).val(appline);
			var hwpCtrl = Document_HwpCtrl;
			if (currentItem != itemnum) {
				hwpCtrl = Enforce_HwpCtrl;
				reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());
			}
			if (tobeDraftLine == asisDraftLine || tobeDraftLine == 0) {
				clearApprTable(hwpCtrl);
			} else {
				var draftSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormD" + tobeDraftLine + ".hwp";
				replaceApprTable(hwpCtrl, draftSignFile, HwpConst.Field.DraftDeptLine);
			}
			if (tobeExecLine == asisExecLine || tobeExecLine == 0) {
				clearApprTable(hwpCtrl);
			} else {
				var execSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormE" + tobeExecLine + ".hwp";
				replaceApprTable(hwpCtrl, execSignFile, HwpConst.Field.ExecDeptLine);
			}
			var assistantlinetype = $("#assistantLineType", "#approvalitem" + itemnum).val();
			var auditlinetype = $("#auditLineType", "#approvalitem" + itemnum).val();
			resetApprover(hwpCtrl, getApproverUser(line), 2, "<%=docType%>", assistantlinetype, auditlinetype);
			setOpenLevel(hwpCtrl);
			if (currentItem != itemnum) {
				arrangeBody(hwpCtrl, itemnum, false);
			}
		}
<% } else { %>  //일반결재
		var line = getApproverList(appline);
		var considercount = getApproverCount(line, "<%=auditLineType%>");
		var assistancecount = getAssistantCount(line, "<%=assistantLineType%>", "<%=auditLineType%>");
		var auditcount = getAuditCount(line, "<%=auditLineType%>");

		if (isStandardForm(Document_HwpCtrl)) {  //표준기안문
			if (considercount > 20 || assistancecount > 32 || auditcount > 8) {
				return "<spring:message code='approval.msg.exceed.standard.appline'/>";
			}
			var tobe = Math.ceil(considercount / 4) + "" + Math.ceil(assistancecount / 4) + "" + Math.ceil(auditcount / 4);
			var asis = (Math.ceil(getConsiderCount(Document_HwpCtrl)) / 4) + "" + (Math.ceil(getAssistanceCount(Document_HwpCtrl)) / 4) + "" + (Math.ceil(getAuditorCount(Document_HwpCtrl)) / 4);
		
			for (var loop = 0; loop < itemCount; loop++) {
				var itemnum = loop + 1;
				$("#appLine", "#approvalitem" + itemnum).val(appline);
				var hwpCtrl = Document_HwpCtrl;
				if (currentItem != itemnum) {
					hwpCtrl = Enforce_HwpCtrl;
					reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());
				}
		
				if (asis == tobe) {
					clearApprTable(hwpCtrl);
				} else {
					if (existField(hwpCtrl, HwpConst.Field.SimpleForm)) {
						replaceApprTable(hwpCtrl, "<%=webUrl%><%=webUri%>/app/ref/rsc/ApproverSemiForm" + tobe + ".hwp");
					} else {
						replaceApprTable(hwpCtrl, "<%=webUrl%><%=webUri%>/app/ref/rsc/ApproverForm" + tobe + ".hwp");
					}
					initAppLineEnv(hwpCtrl, itemnum);
				}
				var assistantlinetype = $("#assistantLineType", "#approvalitem" + itemnum).val();
				var auditlinetype = $("#auditLineType", "#approvalitem" + itemnum).val();
				resetApprover(hwpCtrl, getApproverUser(arrangeAssistant(line, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
				setOpenLevel(hwpCtrl);
				if (currentItem != itemnum) {
					arrangeBody(hwpCtrl, itemnum, false);
				}
			}
		} else {  //표준기안문이 아닌 일반양식
			var baseConsider = 10;
			var baseAssistance = 10;
			var asisConsider = getConsiderCount(Document_HwpCtrl);
			var asisAssistance = getAssistanceCount(Document_HwpCtrl);
			if (!existField(Document_HwpCtrl, HwpConst.Field.ConsiderLine)) {
				baseConsider = asisConsider;
			}
			if (!existField(Document_HwpCtrl, HwpConst.Field.AssistanceLine)) {
				baseAssistance = asisAssistance;
			}
		
			if (baseConsider == 0 && baseAssistance == 0) {
				alert("<spring:message code='approval.msg.noapprovertable'/>");
				return;
			} else if (considercount > baseConsider || assistancecount > baseAssistance) {
				if (!confirm("<spring:message code='approval.msg.exceed.standalone.appline'/>")) {
					selectAppLine();
					return;
				}
			}
			for (var loop = 0; loop < itemCount; loop++) {
				var itemnum = loop + 1;
				$("#appLine", "#approvalitem" + itemnum).val(appline);
				var hwpCtrl = Document_HwpCtrl;
				if (currentItem != itemnum) {
					hwpCtrl = Enforce_HwpCtrl;
					reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());
				}

				if (considercount == asisConsider) {
					clearApprTable(hwpCtrl);
				} else {
					var hwpSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormC" + considercount + ".hwp";
					replaceApprTable(hwpCtrl, hwpSignFile, HwpConst.Field.ConsiderLine);
				}
				if (assistancecount == asisAssistance) {
					clearApprTable(hwpCtrl);
				} else {
					var hwpSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormA" + assistancecount + ".hwp";
					replaceApprTable(hwpCtrl, hwpSignFile, HwpConst.Field.AssistanceLine);
				}
			
				var assistantlinetype = $("#assistantLineType", "#approvalitem" + itemnum).val();
				var auditlinetype = $("#auditLineType", "#approvalitem" + itemnum).val();
				resetApprover(hwpCtrl, getApproverUser(arrangeAssistant(line, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
				setOpenLevel(hwpCtrl);
				if (currentItem != itemnum) {
					arrangeBody(hwpCtrl, itemnum, false);
				}
			}
		}
<% } %>
	}	
}

// 수신자
function selectAppRecv() {
	var itemnum = getCurrentItem();
	if ($("#serialNumber", "#approvalitem" + itemnum).val() == "0") {
		receiverWin = openWindow("receiverWin", "<%=webUri%>/app/approval/ApprovalRecip.do?owndept=<%=ownDeptId%>", 650, 650);

	} else {
		receiverWin = openWindow("receiverWin", "<%=webUri%>/app/approval/ApprovalRecip.do", 650, 650);
	}
}

function getAppRecv() {
	var itemnum = getCurrentItem();

	var recv = new Object();
	recv.appRecv = $("#appRecv", "#approvalitem" + itemnum).val();
	recv.displayNameYn = $("#displayNameYn", "#approvalitem" + itemnum).val();
	recv.receivers = $("#receivers", "#approvalitem" + itemnum).val();

	return recv;
}

function setAppRecv(apprecv, isuse, displayname, isall) {
	var itemnum = getCurrentItem();
<% if ("1".equals(opt314)) { %>
	if (typeof(isall) != "undefined" && isall == "Y") {
		$("#readRange", "#approvalitem" + itemnum).val("<%=appCode.getProperty("DRS005", "DRS005", "DRS")%>"); // jth8172 2012 신결재 TF
	}
<% } %>	
	return setAppReceiver(Document_HwpCtrl, itemnum, apprecv, isuse, displayname);
}

function setAppReceiver(hwpCtrl, itemnum, apprecv, isuse, displayname) {

	var recvList = getReceiverList(apprecv);
	var enfType = getEnfType(recvList);
	var serialNumber = $("#serialNumber", "#approvalitem" + itemnum).val();
	if (serialNumber == -1 && (enfType == "<%=det003%>" || enfType == "<%=det004%>")) {
		return "<spring:message code='approval.msg.not.send.to.outofcompany'/>";
	}

	$("#appRecv", "#approvalitem" + itemnum).val(apprecv);
	$("#displayNameYn", "#approvalitem" + itemnum).val(isuse);
	
	var recvsize = recvList.length;
	if (recvsize == 0) {
		putFieldText(hwpCtrl, HwpConst.Form.Receiver, "<spring:message code='hwpconst.data.innerapproval'/>");
		putFieldText(hwpCtrl, HwpConst.Form.Receivers, "<spring:message code='hwpconst.data.innerapproval'/>");
		putFieldText(hwpCtrl, HwpConst.Form.ReceiverRefTitle, "");
		putFieldText(hwpCtrl, HwpConst.Form.ReceiverRef, "");
		$("#receivers", "#approvalitem" + itemnum).val("");
	} else if (recvsize == 1) {
		var receiver = "";
		// 본문에 수신자기호,부서장직위 표시기능(OPT406) 추가 20120330 // jth8172 2012 신결재 TF
		var receiverDisplayName = recvList[0].recvDeptName;
		var refDisplayName = recvList[0].refDeptName;
		
		if("Y" == "<%=RecvSymbolUseYN%>") {
			receiverDisplayName = recvList[0].recvSymbol;
			refDisplayName = recvList[0].refSymbol;
		} else if("Y" == "<%=RecvChiefUseYN%>") {
			receiverDisplayName = recvList[0].recvChiefName;
			refDisplayName = recvList[0].refChiefName;
		}		
		//수신자기호나부서장 직위가 없으면 부서명으로 지정한다.
		if(receiverDisplayName == "") receiverDisplayName = recvList[0].recvDeptName;
		if(refDisplayName == "") refDisplayName = recvList[0].refDeptName;

		if (recvList[0].enfType == "<%=det002%>") {
			if (recvList[0].receiverType == "<%=dru002%>") {
				receiver += receiverDisplayName + "(" + recvList[0].recvUserName + ")";
			} else {
				receiver += receiverDisplayName;
				if (refDisplayName != "") {
					receiver += "(" + refDisplayName + ")";
				}
			}
		} else if (recvList[0].enfType == "<%=det007%>") {
			receiver += receiverDisplayName + " <spring:message code='hwpconst.data.dear'/> (<spring:message code='hwpconst.data.post'/>" + 
			recvList[0].postNumber + " " + recvList[0].address + ")";
		} else {
			receiver = receiverDisplayName
			if (refDisplayName != "") {
				receiver += "(" + refDisplayName + ")";
			}
		}
		if (isuse == "Y") {
			putFieldText(hwpCtrl, HwpConst.Form.Receiver, displayname);
			putFieldText(hwpCtrl, HwpConst.Form.Receivers, displayname);
			$("#receivers", "#approvalitem" + itemnum).val(displayname);
		} else {
			putFieldText(hwpCtrl, HwpConst.Form.Receiver, receiver);
			putFieldText(hwpCtrl, HwpConst.Form.Receivers, receiver);
			$("#receivers", "#approvalitem" + itemnum).val(receiver);
		}
		putFieldText(hwpCtrl, HwpConst.Form.ReceiverRefTitle, "");
		putFieldText(hwpCtrl, HwpConst.Form.ReceiverRef, "");
	} else {
		var receiverref = "";
		// 본문에 수신자기호,부서장직위 표시기능(OPT406) 추가 20120330 // jth8172 2012 신결재 TF
		var receiverDisplayName = "";
		var refDisplayName = "";
		
		for (var loop = 0; loop < recvsize; loop++) {
			receiverDisplayName = recvList[loop].recvDeptName;
			refDisplayName = recvList[loop].refDeptName;
			if("Y" == "<%=RecvSymbolUseYN%>") {
				receiverDisplayName = recvList[loop].recvSymbol;
				refDisplayName = recvList[loop].refSymbol;
			} else if("Y" == "<%=RecvChiefUseYN%>") {
				receiverDisplayName = recvList[loop].recvChiefName;
				refDisplayName = recvList[loop].refChiefName;
			}	
			//수신자기호나부서장 직위가 없으면 부서명으로 지정한다.
			if(receiverDisplayName == "") receiverDisplayName = recvList[loop].recvDeptName;
			if(refDisplayName == "") refDisplayName = recvList[loop].refDeptName;

			if (recvList[loop].enfType == "<%=det002%>") {
				if (recvList[loop].receiverType == "<%=dru002%>") {
					receiverref += receiverDisplayName + "(" + recvList[loop].recvUserName + ")";
				} else {
					receiverref += receiverDisplayName;
					if (refDisplayName != "") {
						receiverref += "(" + refDisplayName + ")";
					}
				}
			} else if (recvList[loop].enfType == "<%=det007%>") {
				receiverref += receiverDisplayName + " <spring:message code='hwpconst.data.dear'/> (<spring:message code='hwpconst.data.post'/>" + 
				recvList[loop].postNumber + " " + recvList[loop].address + ")";
			} else {
				receiverref += receiverDisplayName;
				if (refDisplayName != "") {
					receiverref += "(" + refDisplayName + ")";
				}
			}
			if (loop < recvsize - 1) {
				receiverref += ", ";
			}
		}
		putFieldText(hwpCtrl, HwpConst.Form.Receiver, "<spring:message code='hwpconst.data.receiverref'/>");
		putFieldText(hwpCtrl, HwpConst.Form.ReceiverRefTitle, "<spring:message code='hwpconst.data.receiver'/>");
		if (isuse == "Y") {
			putFieldText(hwpCtrl, HwpConst.Form.ReceiverRef, displayname);
			putFieldText(hwpCtrl, HwpConst.Form.Receivers, displayname);
			$("#receivers", "#approvalitem" + itemnum).val(displayname);
		} else {
			putFieldText(hwpCtrl, HwpConst.Form.ReceiverRef, receiverref);
			putFieldText(hwpCtrl, HwpConst.Form.Receivers, receiverref);
			$("#receivers", "#approvalitem" + itemnum).val(receiverref);
		}
	}
	

	// 내부문서는 발신명의 생략
	if (recvsize == 0) {
		putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, "");
	} else {
		putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, $("#senderTitle", "#approvalitem" + itemnum).val());
	}		

	$("#enfType", "#approvalitem" + itemnum).val(enfType);
	if (getEnfBound(recvList) == "OUT") {
		$("#autoSendYn", "#approvalitem" + itemnum).val("N");
	}

	arrangeBody(hwpCtrl, itemnum, false);
}

//열람자
function listPostReader() {
	readerWin = openWindow("readerWin", "<%=webUri%>/app/appcom/listPostReader.do?docId=" + $("#docId", "#approvalitem" + getCurrentItem()).val(), 500, 400);
}

<% if (!"N".equals(editbodyYn)) { %>	
// 본문수정
function modifyBody() {
	//20160421
	$("#registNewTrader").attr("style", "display:none;");
	$("#modifybody").attr("style", "display:none;");
	$("#savebody").attr("style", "display:'';");
	$("#beforeprocess").hide();
	
	var currentItem = getCurrentItem();
	if ($("#bodyEdited", "#approvalitem" + currentItem).val() != "Y") {
		$("#bodyEdited", "#approvalitem" + currentItem).val("T");
	}

	setSavePoint(Document_HwpCtrl);
	showToolbar(Document_HwpCtrl, 1);
	changeEditMode(Document_HwpCtrl, 2, true);

	if (bodyType == "html") {
		startEditHtmlBodyContent();
	}		
}
// 본문수정 완료
function saveBody() {
	$("#modifybody").attr("style", "display:'';");
	$("#savebody").attr("style", "display:none;");
	$("#beforeprocess").show();

	var currentItem = getCurrentItem();
	var title = $("#title", "#approvalitem" + currentItem).val();
	
	$("#bodyEdited", "#approvalitem" + currentItem).val("Y");
	$("#tempTitle", "#approvalitem" + currentItem).val(getFieldText(Document_HwpCtrl, HwpConst.Form.Title));
	
	var bodylist = transferFileList($("#bodyFile", "#approvalitem" + currentItem).val());
	if (bodylist.length > 0) {
		if (bodylist[0].localpath != "") {
			saveHwpDocument(Document_HwpCtrl, bodylist[0].localpath);
		} else {
			saveHwpDocument(Document_HwpCtrl, bodyfilepath);
		}
	}
	showToolbar(Document_HwpCtrl, 0);
	changeEditMode(Document_HwpCtrl, 0, false);
	setSavePoint(Document_HwpCtrl);

	if (bodyType == "html") {
		saveEditHtmlBodyContent();
		showHtmlBodyContent();
	}
}

//본문수정 완료 후 수정
function saveNeditBody() {
	$("#modifybody").attr("style", "display:'';");
	$("#savebody").attr("style", "display:none;");
	$("#beforeprocess").show();

	var editMode = getEditMode(Document_HwpCtrl);
	var currentItem = getCurrentItem();
	$("#bodyEdited", "#approvalitem" + currentItem).val("Y");
	var bodylist = transferFileList($("#bodyFile", "#approvalitem" + currentItem).val());
	if (bodylist.length > 0) {
		if (bodylist[0].localpath != "") {
			saveHwpDocument(Document_HwpCtrl, bodylist[0].localpath);
		} else {
			saveHwpDocument(Document_HwpCtrl, bodyfilepath);
		}
	}
	changeEditMode(Document_HwpCtrl, editMode, (editMode == 2) ? true : false);
	setSavePoint(Document_HwpCtrl);
}

//본문수정 취소
function cancelBody() {
	$("#modifybody").attr("style", "display:'';");
	$("#savebody").attr("style", "display:none;");
	$("#beforeprocess").show();

	var currentItem = getCurrentItem();
	
	if ($("#bodyEdited", "#approvalitem" + currentItem).val() != "Y") {
		$("#bodyEdited", "#approvalitem" + currentItem).val("N");
	}

	var bodylist = transferFileList($("#bodyFile", "#approvalitem" + currentItem).val());
	initEditorForRecp();
	if (bodylist.length > 0) {
		if (bodylist[0].localpath != "") {
			openHwpDocument(Document_HwpCtrl, bodylist[0].localpath);
		} else {
			openHwpDocument(Document_HwpCtrl, bodyfilepath);
		}
	}

	if(bodyType != "doc"){

		// 결재라인 초기화
		var appline = $("#appLine", "#approvalitem" + currentItem).val();
		var assistantlinetype = $("#assistantLineType", "#approvalitem" + currentItem).val();
		var auditlinetype = $("#auditLineType", "#approvalitem" + currentItem).val();
	
	<% if ("Y".equals(doubleYn)) { %>
		resetApprover(Document_HwpCtrl, getApproverUser(appline), 2, "<%=docType%>", assistantlinetype, auditlinetype);
	<% } else { %>
		resetApprover(Document_HwpCtrl, getApproverUser(arrangeAssistant(appline, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
	<% } %>
		showToolbar(Document_HwpCtrl, 0);
		changeEditMode(Document_HwpCtrl, 0, false);
		setSavePoint(Document_HwpCtrl);
		 if (bodyType == "html") {
			showHtmlBodyContent();
		}	
	}else{
		//initEditorAfter();
		editeBody(Document_HwpCtrl, 0, false);
		setSavePoint(Document_HwpCtrl);
	}	
}

//본문수정 취소
function cancelNeditBody() {
	$("#modifybody").attr("style", "display:'';");
	$("#savebody").attr("style", "display:none;");
	$("#beforeprocess").show();

	var editMode = getEditMode(Document_HwpCtrl);
	var currentItem = getCurrentItem();
	if ($("#bodyEdited", "#approvalitem" + currentItem).val() != "Y") {
		$("#bodyEdited", "#approvalitem" + currentItem).val("N");
	}

	var bodylist = transferFileList($("#bodyFile", "#approvalitem" + currentItem).val());
	if (bodylist.length > 0) {
		if (bodylist[0].localpath != "") {
			openHwpDocument(Document_HwpCtrl, bodylist[0].localpath);
		} else {
			openHwpDocument(Document_HwpCtrl, bodyfilepath);
		}
	}
	// 결재라인 초기화
	var appline = $("#appLine", "#approvalitem" + currentItem).val();
	var assistantlinetype = $("#assistantLineType", "#approvalitem" + currentItem).val();
	var auditlinetype = $("#auditLineType", "#approvalitem" + currentItem).val();
<% if ("Y".equals(doubleYn)) { %>
	resetApprover(Document_HwpCtrl, getApproverUser(appline), 2, "<%=docType%>", assistantlinetype, auditlinetype);
<% } else { %>
	resetApprover(Document_HwpCtrl, getApproverUser(arrangeAssistant(appline, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
<% } %>
	changeEditMode(Document_HwpCtrl, editMode, (editMode == 2) ? true : false);
	setSavePoint(Document_HwpCtrl);
}
<%} %>	
//안건별 보안 패스워드 체크
function selectTab(itemnum) {
    var securityYn = $("#securityYn", "#approvalitem" + itemnum).val();
    var curDocId = $("#docId", "#approvalitem" + itemnum).val();
    if("Y" == securityYn){
    	insertDocPassword4Body(curDocId,itemnum);
    	return;
    }else{
    	selectTabDoc(itemnum);
    }
}

// 안건탭 선택
function selectTabDoc(itemnum) {
	var itemCount = getItemCount();
<% if (lob003.equals(lobCode)) { %>	
	var currentItem = getCurrentItem();
	// 본문변경여부 확인
	if (isStandardForm(Document_HwpCtrl) && isChanged(Document_HwpCtrl)) {
		if ($("#draftType").val() == "redraft-all") {
			saveBody();
		} else {
			if (confirm("<spring:message code='approval.msg.modifybody.willyousave'/>")) {
				saveBody();
			} else {
				cancelBody();
			}
		}
	} else {
		$("#modifybody").attr("style", "display:'';");
		$("#savebody").attr("style", "display:none;");
		if ($("#bodyEdited", "#approvalitem" + currentItem).val() != "Y") {
			$("#bodyEdited", "#approvalitem" + currentItem).val("N");
		}
	}
	// 제목, 본문 정리
	if (existField(Document_HwpCtrl, HwpConst.Form.Title)) {
		$("#title", "#approvalitem" + currentItem).val($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.Title)));
	}
	$("#via", "#approvalitem" + currentItem).val($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.Via)));
	arrangeBody(Document_HwpCtrl, currentItem, false);
<% } %>	

	reloadFile(itemnum);
	loadRelatedDoc($("#relatedDoc", "#approvalitem" + itemnum).val());
	setSavePoint(Document_HwpCtrl);
	
	for (var loop = 1; loop <= itemCount; loop++) {
		if (loop == itemnum) {
			document.getElementById('id_tab_left_'+loop).src = '<%=webUri%>/app/ref/image/tab1.gif';
			document.getElementById('id_tab_bg_'+loop).style.background = 'url(<%=webUri%>/app/ref/image/tabbg.gif)';
			document.getElementById('id_tab_bg_'+loop).className = 'tab';
			document.getElementById('id_tab_right_'+loop).src = '<%=webUri%>/app/ref/image/tab2.gif';
		} else {
			document.getElementById('id_tab_left_'+loop).src = '<%=webUri%>/app/ref/image/tab1_off.gif';
			document.getElementById('id_tab_bg_'+loop).style.background = 'url(<%=webUri%>/app/ref/image/tabbg_off.gif)';
			document.getElementById('id_tab_bg_'+loop).className = 'tab_off';
			document.getElementById('id_tab_right_'+loop).src = '<%=webUri%>/app/ref/image/tab2_off.gif';
		}
	}
	
}

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

//2016-01-27 찬성,반대 클릭시 팝업넘기지 않고 바로 실행하도록 변경 askType=" + user.askType + "&actType=<%=apt001%>
function checkOpinion(receivedAct) {
	var currentItem = getCurrentItem();
	var user = getCurrentApprover($("#appLine", "#approvalitem" + currentItem).val(), "<%=userId%>");

	var askType = user.askType;
	var actType = receivedAct;
	 
	var opinion = $.trim($("#opinion").val());
	if(actType=="<%=apt002%>"){
		/* if (opinion == $.trim("")) {
			alert("<spring:message code='approval.msg.need.returnopinion'/>");
			$("#opinion").focus();
			return false;
		}  */
		var message = "<spring:message code='approval.msg.returnapproval'/>";
	}else if(actType=="<%=apt051%>"){
		var message = "<spring:message code='approval.msg.agreeapproval'/>";
	}else if(actType=="<%=apt052%>"){
		if (opinion == $.trim("")) {
			alert("<spring:message code='approval.msg.need.disagreeopinion'/>");
			
			var width = 500;
			<% if ("1".equals(opt301)) { %>		
			var height = 260;
			<% } else { %>
			var height = 220;
			<% } %>
			
			opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createOpinion.do?askType=" + user.askType + "&actType=<%=apt052%>&formBodyType=" + bodyType + "&alarmYn=" + $("#alarmYn", "#approvalitem" + currentItem).val(), width, height);
			$("#opinion").focus();
			return false;
		} 
		var message = "<spring:message code='approval.msg.disagreeapproval'/>";
	}else{
		if("<%=art010%>"==askType){
		var message = "<spring:message code='approval.msg.submitapproval'/>";
		}else{
		var message = "<spring:message code='approval.msg.processapproval'/>";
		}
	}
	if("1"=="<%=opt301%>"){
		var password = $.trim($("#password").val());
		if (password == $.trim("")) {
			alert("<spring:message code='approval.msg.need.approvalpassword'/>");
			$("#password").focus();
			return false;
		} else {
			prepareSeed();
			$("#encryptedpassword").val(document.getElementById("seedOCX").SeedEncryptData($("#password").val()));
			
			$.post("<%=webUri%>/app/appcom/compareAppPwd.do", $("#opinionForm").serialize(), function(data){
				if (data.result == "success") {
					submitOpinion(opinion, message, askType, actType);
				} else {
					alert(data.message);
					$("#password").focus();
					return false;
				}
			}, 'json');
		}
	}else{
		submitOpinion(opinion, message, askType, actType);
	}
}

function submitOpinion(opinion, message, askType, actType ) {
	 window.moveBy(0,0 );   
	//의견 본문내 표시 체크 시 의견내용앞에 구분자 추가, kj.yang, 20120510 S
	if ($("#opinionYn").attr("checked")) {
		if(opinion != "") {
			opinion = String.fromCharCode(15) + opinion;
		}
	}
	//의견 본문내 표시 체크 시 의견내용앞에 구분자 추가, kj.yang, 20120510 E
<% if ("2".equals(opt301)) { %>
	$.post("<%=webUri%>/app/appcom/validateSignDate.do", "", function(data) {
		if (data.validation == "Y") {
				setOpinion1(opinion, askType, actType);
				$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
			} else {
			if (certificate()) {
					setOpinion1(opinion, askType, actType);
					$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
				}
			} else {
				return false;
			}
		}
	}, 'json').error(function(data) {
		if (certificate()) {
				setOpinion1(opinion, askType, actType);
				$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
			}
		} else {
			return false;
		}
	});
<% } else { %>
<% 	if ("0".equals(opt301)) { %>
		setOpinion1(opinion, askType, actType);
<% 	} else { %>
		<% if ("1".equals(opt301)) { %>	
		setOpinion1(opinion, askType, actType, $("#encryptedpassword").val(), $("#roundkey").val()); 
		<% } else { %>
		setOpinion1(opinion, askType, actType);
		<% } %>	
<% 	} %>
<% } %>
}
function prepareSeed() {

    var currRoundKey = document.getElementById("seedOCX").GetCurrentRoundKey();

    if (currRoundKey == "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0") {
        var userkey = document.getElementById("seedOCX").GetUserKey();
        currRoundKey = document.getElementById("seedOCX").GetRoundKey(userkey);
    }

    var roundkey_c = document.getElementById("seedOCX").SeedEncryptRoundKey(currRoundKey);

    $('#roundkey').val(roundkey_c);
}

function closeNewOpinion(){
	if( bReqAcc != null || bApprovalok != null || bPrereadok != null ){
		if(bReqAcc != null){
			bReqAcc = false;
		}
		if(bApprovalok != null){
			bApprovalok = false;
		}
		if(bPrereadok != null){
			bPrereadok = false;
		}
	}
}

//전표
function selectStatement() {
	var width = 1400;
	var height = 800;
	opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/insertStatement.do?erpId=<%=erpId%>&isSelect=Y", width, height);
}

//전표수정
function insertStatement() {
	//compType -> b(벽산), p(페인트)
	var width = 1400;
	var height = 800;
	var costCount = 4;
	var totalCost = 0;
	var title = "";
	var erpId = "";
	var cost = $.trim(getFieldText(Document_HwpCtrl, "현금계"));
	if(cost && cost !=''){
		if(cost.indexOf("\n")>-1){
			var costs = cost.split("\n");
			cost = 0;
			for(j=0;j<costs.length;j++){
				if(costs[j] && costs[j].trim() !=''){
					cost += parseInt(costs[j].replace(/[^0-9\.]/g, ''));
				}
			}
		}else{
			cost = cost.replace(/[^0-9\.]/g, '');
			totalCost += parseInt(cost);
		}
	}
	title = $.trim(getFieldText(Document_HwpCtrl, "제목"));
	erpId = $("#erpId").val();
	opinionWin = openWindow("statementWin", "<%=webUri%>/app/approval/insertStatement.do?totalCost="+totalCost+"&title="+title+"&erpId="+erpId, width, height);
}


//문서에 전표의 erpid입력
function markingErpId(erpId){
	if(erpId!=""){
		putFieldText(Document_HwpCtrl, "전산번호", "전표전산번호: "+erpId);
	}
}

</script>
</head>
<body style="margin: 0" onload="closewin();" onbeforeunload="closewin()" onunload="closewin()">

<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="50%">
							<span class="pop_title77"><spring:message code='approval.title.select.approval'/></span>
						</td>
<!-- 						
						<td width="50%" align="right">
							<acube:buttonGroup align="right">
								<acube:menuButton onclick="moveToPrevious();return(false);" value="<%=previousBtn%>" />
								<acube:space between="button" />
								<acube:menuButton onclick="moveToNext();return(false);" value="<%=nextBtn%>" />
							</acube:buttonGroup>
						</td>
-->
					</tr>
				</table>	
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
<%
// added by jkkim 감사기안의 경우, 본문수정버튼 보이지 않도록 처리함 2012.07.24
if (!"N".equals(editbodyYn) && lob003.equals(lobCode) && !(app401.equals(appDocState) && currentUserFlag && !art031.equals(currentAskType) && !isExtWeb)) { 
%>
<%
//20160321 합의자에게 본문수정 버튼 보이지 않게하기 
if (!art130.equals(currentAskType) && !art131.equals(currentAskType) && !art132.equals(currentAskType) && !art133.equals(currentAskType) && !art134.equals(currentAskType) && !art135.equals(currentAskType) ){

%>
							<div id="modifybody" style="display:<%=(app110.equals(docState) ? "none" : "")%>">
								<acube:button onclick="modifyBody();return(false);" value="<%=modifyBodyBtn%>" type="4" class="gr" />
							</div>
							<div id="savebody" style="display:none;">
								<acube:buttonGroup align="left">
									<acube:button onclick="saveBody();return(false);" value="<%=saveBodyBtn%>" type="4" class="gr" />
									<acube:space between="button" />
									<acube:button onclick="cancelBody();return(false);" value="<%=cancelBodyBtn%>" type="4" class="gr" />
								</acube:buttonGroup>
							</div>
<% }else{%>
							<div id="modifybody" style="display:none">
								<acube:button onclick="modifyBody();return(false);" value="<%=modifyBodyBtn%>" type="4" class="gr" />
							</div>
							<div id="savebody" style="display:none;">
								<acube:buttonGroup align="left">
									<acube:button onclick="saveBody();return(false);" value="<%=saveBodyBtn%>" type="4" class="gr" />
									<acube:space between="button" />
									<acube:button onclick="cancelBody();return(false);" value="<%=cancelBodyBtn%>" type="4" class="gr" />
								</acube:buttonGroup>
							</div>
<%} %>		
<%} %>
						</td>
						<td>	
<jsp:include page="/app/jsp/approval/button.jsp" />
					<!-- added by jkkim 원문보기 기능 추가 -->
					<% if("Y".equals(opt380)&&(lobCode.equals(lol007))){ %>
					<acube:buttonGroup align="right">
						<acube:button onclick="javascript:selectOriginAppDoc();" value="<%=auditOriginDocBtn%>" type="4" class="gr" />
						<acube:space between="button" />
					</acube:buttonGroup>
					<%}%>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
<%
	if (docCount > 1) {
%>
		<tr>
			<td>
				<table id="tabmaster" width='100%' border='0' cellspacing='0' cellpadding='0'>
					<tr>
						<td>
							<table border='0' align='left' cellpadding='0' cellspacing='0'>
								<tr>
<%
		for (int loop = 0; loop < docCount; loop++) {
		    if (loop > 0) {
%>								
									<td name="tab<%=(loop+1)%>" width='2'></td>
<%
			}
%>									
									<td name="tab<%=(loop+1)%>"><img id='id_tab_left_<%=(loop+1)%>' src='<%=webUri%>/app/ref/image/tab1_off.gif' width='16' height='24'></td>
									<td name="tab<%=(loop+1)%>" id='id_tab_bg_<%=(loop+1)%>' background='<%=webUri%>/app/ref/image/tabbg_off.gif' class='tab_off'>
										<a href="#none" onclick="selectTab(<%=(loop+1)%>);">
										<% 
											 AppDocVO appDocVOSec = appDocVOs.get(loop);
										     if(appDocVOSec.getSecurityYn().equals("Y")){
										%>
												<img src="/ep/app/ref/image/secret.gif" border='0'/>
										<%}%>
										<%=(loop+1)%><spring:message code='approval.title.item'/></a></td>
									<td name="tab<%=(loop+1)%>"><img id='id_tab_right_<%=(loop+1)%>' src='<%=webUri%>/app/ref/image/tab2_off.gif' width='16' height='24'></td>
<%
		}
%>									
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td height='2' style='background-color:#6891CB'></td>
					</tr>
				</table>
			</td>
		</tr>
<% 
	} 
%>
		<tr>
		<tr>
			<td class="message_box">
				<div id="divhwp" width="100%" height="600">
<%	if(strBodyType.equals("html")) { %>				
					<iframe id="editHtmlFrame" name="editHtmlFrame" src="" width="100%" height="100%" scrolling="auto" frameborder="no" border="0"></iframe>
					<input type="hidden" name="bodyFileName" id="bodyFileName" value="<%= bodyFileName %>" />
					<input type="hidden" name="bodyFileId"   id="bodyFileId"   value="<%= bodyFileId %>" />
					<input type="hidden" name="htmlOpt423"   id="htmlOpt423"   value="<%= opt423 %>" />	<!-- 편철 사용 여부 -->
					<input type="hidden" name="htmlOpt422"   id="htmlOpt422"   value="<%= opt422 %>" /> <!-- 문서분류체계 사용유무  -->
<%	} %>				
				</div>
				<div id="hiddenhwp" width="100%" height="1">
				</div>
				<div id="mobilehwp" width="100%" height="1">
				</div>
			</td>
		</tr>
		<tr>
			<td height="6"></td>
		</tr>
 		<tr>
<% if("Y".equals(opt321)) { //관련문서 사용할 경우 %>
		    <td>
		    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      		<tr>
		      			<%-- <td width="50%" class="approval_box">
					    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					      		<tr>
					        		<td width="15%;" height="60px" class="msinputbox_tit"><spring:message code='approval.title.relateddoc'/></td>
					        		<td width="80%;" height="60px" style="background-color:#ffffff;border:0px solid;height:60px;width=100%;overflow:auto;">
						        		<div style="height:60px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">
											<table id="tbRelatedDocs" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3">
												<tbody/>
											</table>
										</div>	
									</td>
					        		<td width="10">&nbsp;</td>
					        		<td width="45" align="right">
					        			<table width="45" border="0" cellspacing="0" cellpadding="0" style="display:<%=(lob003.equals(lobCode) && currentUserFlag) ? "block" : "none"%>">
					          				<tr>
									            <td width="25" height="25" valign="top"><img src="<%=webUri%>/app/ref/image/bu_up.gif" width="20" height="20" style="cursor:pointer;" onclick="moveUpRelateDoc();return(false);" alt="<%=upBtn%>"></td>
									            <td width="20" valign="top"><img src="<%=webUri%>/app/ref/image/bu_pp.gif" width="20" height="20" style="cursor:pointer;" onclick="selectRelatedDoc();return(false);" alt="<%=appendBtn%>"></td>
					          				</tr>
					          				<tr>
									            <td><img src="<%=webUri%>/app/ref/image/bu_down.gif" width="20" height="20" style="cursor:pointer;" onclick="moveDownrelateDoc();return(false);" alt="<%=downBtn%>"></td>
									            <td width="20"><img src="<%=webUri%>/app/ref/image/bu_mm.gif" width="20" height="20" style="cursor:pointer;" onclick="removeRelatedDoc();return(false);" alt="<%=removeBtn%>"></td>
					          				</tr>
					        			</table>
					        		</td>
					        		
					        		
								</tr>
							</table>
						</td>
						<td>&nbsp;</td> --%>
		      			<td width="100%" class="approval_box">
					    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					      		<tr>
								    <td width="15%;" height="70px" class="msinputbox_tit"><spring:message code='approval.title.attachfile'/></td>
					        		<td width="80%;" height="70px">
										<div id="divattach" style="background-color:#ffffff;border:0px solid;height:70px;width=100%;overflow:auto;"></div>
					        		</td>
					        		<td width="10">&nbsp;</td>
					        		<td width="45" align="right">
<%
	String editfileYn = "Y";
	if (docCount > 0) {
		editfileYn = ((AppDocVO)appDocVOs.get(0)).getEditfileYn();
	}
	if ("Y".equals(editfileYn)) {
%>					        		
					        			<table id="tableattach" width="45" border="0" cellspacing="0" cellpadding="0" style="display:<%=(lob003.equals(lobCode) && currentUserFlag) ? "block" : "none"%>">
					          				<tr>
									            <td width="20" height="20" valign="top"><img src="<%=webUri%>/app/ref/image/bu_up.gif" width="15" height="15" style="cursor:pointer;" onclick="moveUpAttach();return(false);" alt="<%=upBtn%>"></td>
									            <td width="20" valign="top"><img src="<%=webUri%>/app/ref/image/bu_pp.gif" width="15" height="15" style="cursor:pointer;" onclick="appendAttach();return(false);" alt="<%=appendBtn%>"></td>
					          				</tr>
					          				<tr>
									            <td><img src="<%=webUri%>/app/ref/image/bu_down.gif" width="15" height="15" style="cursor:pointer;" onclick="moveDownAttach();return(false);" alt="<%=downBtn%>"></td>
									            <td width="20"><img src="<%=webUri%>/app/ref/image/bu_mm.gif" width="15" height="15" style="cursor:pointer;" onclick="removeAttach();return(false);" alt="<%=removeBtn%>"></td>
					          				</tr>
					        			</table>
					        		</td>
<% 		if (!isExtWeb) { %>	
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
<% 		} %>
<%	} %>
					      		</tr>
					    	</table>
					    </td>
					</tr>
		    	</table>
		    </td>	
<% } else { %>
		    <td class="approval_box">
		    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      		<tr>
					    <td width="15%;" height="70px" class="ltb_head"><spring:message code='approval.title.attachfile'/></td>
		        		<td width="80%;" height="70px">
							<div id="divattach" style="background-color:#ffffff;border:0px solid;height:70px;width=100%;overflow:auto;"></div>
		        		</td>
		        		<td width="10">&nbsp;</td>
		        		<td width="45" align="right">
		        			<table id="tableattach" width="45" border="0" cellspacing="0" cellpadding="0" style="display:<%=(lob003.equals(lobCode) && currentUserFlag) ? "block" : "none"%>">
		          				<tr>
						            <td width="20" height="20" valign="top"><img src="<%=webUri%>/app/ref/image/bu_up.gif" width="15" height="15" style="cursor:pointer;" onclick="moveUpAttach();return(false);" alt="<%=upBtn%>"></td>
						            <td width="20" valign="top"><img src="<%=webUri%>/app/ref/image/bu_pp.gif" width="15" height="15" style="cursor:pointer;" onclick="appendAttach();return(false);" alt="<%=appendBtn%>"></td>
		          				</tr>
		          				<tr>
						            <td><img src="<%=webUri%>/app/ref/image/bu_down.gif" width="15" height="15" style="cursor:pointer;" onclick="moveDownAttach();return(false);" alt="<%=downBtn%>"></td>
						            <td width="20"><img src="<%=webUri%>/app/ref/image/bu_mm.gif" width="15" height="15" style="cursor:pointer;" onclick="removeAttach();return(false);" alt="<%=removeBtn%>"></td>
		          				</tr>
		        			</table>
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
<% } %>		    	
		</tr> 
	</table>	
</acube:outerFrame>
<form id="appDocForm" name="appDocForm" method="post">
	<input id="draftType" name="draftType" type="hidden" value=""/>
	<input id="sourceAppLine" name="sourceAppLine" type="hidden" value=""/>
	<input id="lobCode" name="lobCode" type="hidden" value="<%=lobCode%>"/>
<% if ("1".equals(opt301)) { %>		
	<input id="password" name="password" type="hidden" value=""/>
	<input id="roundkey" name="roundkey" type="hidden" value=""/>
<% } %>	
		
	<!-- 반려문서대장등록여부  // jth8172 2012 신결재 TF-->
	<input id="redraftRegYn" name="redraftRegYn" type="hidden" value="N"></input><!-- 반려문서대장등록여부 -->


	<!-- 생산문서 -->
<%
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
		List<AppLineVO> appLineVOs = appDocVO.getAppLine();
		List<FileVO> fileVOs = appDocVO.getFileInfo();
		List<AppRecvVO> appRecvVOs = appDocVO.getReceiverInfo();
		SendInfoVO sendInfoVO = appDocVO.getSendInfoVO();
		if (sendInfoVO == null)
		    sendInfoVO = new SendInfoVO();
		List<RelatedDocVO> relatedDocVOs = appDocVO.getRelatedDoc();
		List<RelatedRuleVO> relatedRuleVOs = appDocVO.getRelatedRule();
		List<CustomerVO> customerVOs = appDocVO.getCustomer();
		List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();

		String securityStartDate = (CommonUtil.nullTrim(appDocVO.getSecurityStartDate())).replaceAll("/", "");
		String securityEndDate = (CommonUtil.nullTrim(appDocVO.getSecurityEndDate())).replaceAll("/", "");
		boolean isDuration = false;		
		if(!"".equals(securityStartDate)&&!"".equals(securityEndDate))
		{
		    int nStartDate = Integer.parseInt(securityStartDate);
		    int nEndDate = Integer.parseInt(securityEndDate);
		    int nCurDate = Integer.parseInt(DateUtil.getCurrentDate("yyyyMMdd"));
			if((nCurDate >= nStartDate) && (nCurDate <= nEndDate))
			    isDuration = true;
		}
%>		    
	<div id="approvalitem<%=(loop+1)%>" name="approvalitem">
		<input id="docId" name="docId" type="hidden" value="<%=appDocVO.getDocId()%>"></input><!-- 문서ID --> 
		<input id="compId" name="compId" type="hidden" value="<%=appDocVO.getCompId()%>"></input><!-- 회사ID --> 
		<input id="title" name="title" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getTitle())%>"></input><!-- 문서제목 -->
		<input id="tempTitle" name="tempTitle" type="hidden" value=""></input><!-- 임시본문제목 (for 본문수정이력) -->
		<input id="docType" name="docType" type="hidden" value="<%=appDocVO.getDocType()%>"></input><!-- 문서유형 --> 
		<input id="securityYn" name="securityYn" type="hidden" value="<%=appDocVO.getSecurityYn()%>"></input><!--보안문서여부 -->
		<input id="securityPass" name="securityPass" type="hidden" value="<%=appDocVO.getSecurityPass()%>"></input><!-- 문서보안 비밀번호 -->
		<input id="securityStartDate" name="securityStartDate" type="hidden" value="<%=appDocVO.getSecurityStartDate()%>"></input><!-- 문서보안 시작일 -->
		<input id="securityEndDate" name="securityEndDate" type="hidden" value="<%=appDocVO.getSecurityEndDate()%>"></input><!-- 문서보안 종료일 -->
		<input id="isDuration" name="isDuration" type="hidden" value="<%=isDuration%>"></input><!-- 문서보안 유지여부 -->
		<input id="enfType" name="enfType" type="hidden" value=<%=appDocVO.getEnfType()%>></input><!-- 시행유형 --> 
		<input id="docState" name="docState" type="hidden" value="<%=appDocVO.getDocState()%>"></input><!-- 문서상태 --> 
		<input id="deptCategory" name="deptCategory" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getDeptCategory())%>"></input><!-- 문서부서분류 --> 
		<input id="serialNumber" name="serialNumber" type="hidden" value="<%=appDocVO.getSerialNumber()%>"></input><!-- 문서일련번호 --> 
		<input id="subserialNumber" name="subserialNumber" type="hidden" value="<%=appDocVO.getSubserialNumber()%>"></input><!-- 문서하위번호 --> 
		<input id="readRange" name="readRange" type="hidden" value="<%=appDocVO.getReadRange()%>"></input><!-- 열람범위 --> 
		<input id="openLevel" name="openLevel" type="hidden" value="<%=appDocVO.getOpenLevel()%>"></input><!-- 정보공개 --> 
		<input id="openReason" name="openReason" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getOpenReason())%>"></input><!-- 정보공개사유 --> 
		<input id="conserveType" name="conserveType" type="hidden" value="<%=appDocVO.getConserveType()%>"></input><!-- 보존년한 --> 
		<input id="deleteYn" name="deleteYn" type="hidden" value="<%=appDocVO.getDeleteYn()%>"></input><!-- 삭제여부 --> 
		<input id="tempYn" name="tempYn" type="hidden" value="<%=appDocVO.getTempYn()%>"></input><!-- 임시여부 --> 
		<input id="docSource" name="docSource" type="hidden" value="<%=appDocVO.getDocSource()%>"></input><!-- 문서출처 --> 
		<input id="originDocId" name="originDocId" type="hidden" value="<%=appDocVO.getOriginDocId()%>"></input><!-- 원문서ID --> 
		<input id="originDocNumber" name="originDocNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getOriginDocNumber())%>"></input><!-- 원문서번호 --> 
		<input id="batchDraftYn" name="batchDraftYn" type="hidden" value="<%=appDocVO.getBatchDraftYn()%>"></input><!-- 일괄기안여부 --> 
		<input id="batchDraftNumber" name="batchDraftNumber" type="hidden" value="<%=appDocVO.getBatchDraftNumber()%>"></input><!-- 일괄기안일련번호 -->
		<input id="electronDocYn" name="electronDocYn" type="hidden" value="<%=appDocVO.getElectronDocYn()%>"></input><!-- 전자문서여부 --> 
		<input id="attachCount" name="attachCount" type="hidden" value="<%=appDocVO.getAttachCount()%>"></input><!-- 첨부개수 --> 
		<input id="urgencyYn" name="urgencyYn" type="hidden" value="<%=appDocVO.getUrgencyYn()%>"></input><!-- 긴급여부 --> 
		<input id="publicPost" name="publicPost" type="hidden" value="<%=appDocVO.getPublicPost()%>"></input><!-- 공람게시 --> 
		<input id="auditReadYn" name="auditReadYn" type="hidden" value="<%=appDocVO.getAuditReadYn()%>"></input><!-- 감사열람여부 --> 
		<input id="auditReadReason" name="auditReadReason" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getAuditReadReason())%>"></input><!-- 감사열람사유 -->
		<input id="auditYn" name="auditYn" type="hidden" value="<%=("Y".equals(opt346)) ? ("U".equals(appDocVO.getAuditYn()) ? "N" : appDocVO.getAuditYn()) : "U"%>"></input><!-- 감사여부 --> 
		<input id="bindingId" name="bindingId" type="hidden" value="<%=appDocVO.getBindingId()%>"></input><!-- 편철ID --> 
		<input id="bindingName" name="bindingName" type="text" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getBindingName())%>"></input><!-- 편철명 --> 
		<input id="bindingResourceId" name="bindingResourceId" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getBindingResourceId())%>"></input><!-- 편철 다국어 추가 -->
		<input id="handoverYn" name="handoverYn" type="hidden" value="<%=appDocVO.getHandoverYn()%>"></input><!-- 인계여부 -->
		<input id="autoSendYn" name="autoSendYn" type="hidden" value="<%=appDocVO.getAutoSendYn()%>"></input><!-- 자동발송여부 --> 
		<input id="bizSystemCode" name="bizSystemCode" type="hidden" value="<%=appDocVO.getBizSystemCode()%>"></input><!-- 업무시스템코드 -->
		<input id="bizTypeCode" name="bizTypeCode" type="hidden" value="<%=appDocVO.getBizTypeCode()%>"></input><!-- 업무유형코드 --> 
		<input id="mobileYn" name="mobileYn" type="hidden" value="<%=appDocVO.getMobileYn()%>"></input><!-- 모바일결재여부 --> 
		<input id="transferYn" name="transferYn" type="hidden" value="<%=appDocVO.getTransferYn()%>"></input><!-- 문서이관여부 --> 
		<input id="doubleYn" name="doubleYn" type="hidden" value="<%=appDocVO.getDoubleYn()%>"></input><!-- 이중결재여부 --> 
		<input id="editbodyYn" name="editbodyYn" type="hidden" value="<%=appDocVO.getEditbodyYn()%>"></input><!-- 본문수정가능여부 --> 
		<input id="editfileYn" name="editfileYn" type="hidden" value="<%=appDocVO.getEditfileYn()%>"></input><!-- 첨부수정가능여부 --> 
		<input id="execDeptId" name="execDeptId" type="hidden" value="<%=appDocVO.getExecDeptId()%>"></input><!-- 주관부서ID --> 
		<input id="execDeptName" name="execDeptName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getExecDeptName())%>"></input><!-- 주관부서명 --> 
		<input id="summary" name="summary" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getSummary())%>"></input><!-- 요약 --> 
		<input id="classNumber" name="classNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getClassNumber())%>"></input><!-- 분류번호 --> 
		<input id="docnumName" name="docnumName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getDocnumName())%>"></input><!-- 분류번호명 --> 
		<input id="assistantLineType" name="assistantLineType" type="hidden" value="<%=StringUtil.null2str(appDocVO.getAssistantLineType(), opt303)%>"></input><!-- 협조라인유형 --> 
		<input id="auditLineType" name="auditLineType" type="hidden" value="<%=StringUtil.null2str(appDocVO.getAuditLineType(), opt304)%>"></input><!-- 감사라인유형 -->
		<input id="drafterOpinion" name="drafterOpinion" type="hidden" value=""></input><!-- 기안자 의견 -->  
		<!-- 보고경로 --> 
		<input id="appLine" name="appLine" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppLine(appLineVOs))%>"></input>
		<input id="appLineTemp" name="appLine" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppLine(appLineVOsTemp))%>"></input>
		<!-- 수신자 --> 
		<input id="appRecv" name="appRecv" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppRecv(appRecvVOs))%>"></input>
		<!-- 결재의견 -->
		<input type="hidden" id="opinion" />
		
		<!-- ERP연계 아이디 -->		
		<input id="erpId" name="erpId" type="hidden" value=""></input><!-- 문서ID --> 
		
		<!-- 본문 --> 
<% 	
		if ("Y".equals(opt343)) { 
			if (strBodyType.equals("html")) { 
%>	   
			<input id="bodyFile" name="bodyFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft001))%>"></input>
			<input id="mobileFile" name="mobileFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft002))%>"></input>
<%      
			} else { 
%> 			
			<input id="bodyFile" name="bodyFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs))%>"></input>
<%	
			}
		} else { 
%>		
			<input id="bodyFile" name="bodyFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft001))%>"></input>
<%		
		} 
%>
		<!-- 첨부 --> 
		<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAttach(fileVOs))%>"></input>
		<!-- 발송정보 -->
		<input id="sendOrgName" name="sendOrgName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getSendOrgName() == null) ? "" : sendInfoVO.getSendOrgName())%>"></input><!-- 발신기관명 -->
		<input id="logoPath" name="logoPath" type="hidden" value=""></input><!-- 로고   // jth8172 2012 신결재 TF -->
		<input id="symbolPath" name="symbolPath" type="hidden" value=""></input><!-- 심볼   // jth8172 2012 신결재 TF -->
		<input id="senderTitle" name="senderTitle" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getSenderTitle() == null) ? "" : sendInfoVO.getSenderTitle())%>"></input><!-- 발신명의 -->
		<input id="headerCamp" name="headerCamp" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getHeaderCamp() == null) ? "" : sendInfoVO.getHeaderCamp())%>"></input><!-- 상부캠페인 -->
		<input id="footerCamp" name="footerCamp" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getFooterCamp() == null) ? "" : sendInfoVO.getFooterCamp())%>"></input><!-- 하부캠페인 -->
		<input id="postNumber" name="postNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getPostNumber() == null) ? "" : sendInfoVO.getPostNumber())%>"></input><!-- 우편번호 -->
		<input id="address" name="address" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getAddress() == null) ? "" : sendInfoVO.getAddress())%>"></input><!-- 주소 -->
		<input id="telephone" name="telephone" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getTelephone() == null) ? "" : sendInfoVO.getTelephone())%>"></input><!-- 전화 -->
		<input id="fax" name="fax" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getFax() == null) ? "" : sendInfoVO.getFax())%>"></input><!-- FAX -->
		<input id="via" name="via" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getVia() == null) ? "" : sendInfoVO.getVia())%>"></input><!-- 경유 -->
		<input id="sealType" name="sealType" type="hidden" value="<%=(sendInfoVO.getSealType() == null) ? "" : sendInfoVO.getSealType()%>"></input><!-- 날인유형 -->
		<input id="homepage" name="homepage" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getHomepage() == null) ? "" : sendInfoVO.getHomepage())%>"></input><!-- 홈페이지 -->
		<input id="email" name="email" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getEmail() == null) ? "" : sendInfoVO.getEmail())%>"></input><!-- 이메일 -->
		<input id="receivers" name="receivers" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getReceivers() == null) ? "" : sendInfoVO.getReceivers())%>"></input><!-- 수신 -->
		<input id="displayNameYn" name="displayNameYn" type="hidden" value="<%=(sendInfoVO.getDisplayNameYn() == null) ? "" : sendInfoVO.getDisplayNameYn()%>"></input><!-- 수신 -->
		<input id="enforceDate" name="enforceDate" type="hidden" value=""/><!-- 자동발송시 시행일자 -->
		<!-- 관련문서 --> 
		<input id="relatedDoc" name="relatedDoc" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedDoc(relatedDocVOs))%>"></input>
		<!-- 관련규정 --> 
		<input id="relatedRule" name="relatedRule" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedRule(relatedRuleVOs))%>"></input>
		<!-- 거래처 --> 
		<input id="customer" name="customer" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferCustomer(customerVOs))%>"></input>
		<!-- 공람자 --> 
		<input id="pubReader" name="pubReader" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferPubReader(pubReaderVOs))%>"></input>

		<!-- 본문변경여부 -->
		<input id="bodyEdited" name="bodyEdited" type="hidden" value="N"></input><!-- 본문변경여부 -->

		<!-- 합의 찬성/반대 -->
		<input id="procType" name="procType" type="hidden" value=""></input><!-- 합의찬성 반대여부 -->


		
	</div>
		<!-- 관인 --> 
		<input type="hidden" id="stampId" name="stampId" value="" />
		<input type="hidden" id="stampName" name="stampName" value="" />
		<input type="hidden" id="stampExt" name="stampExt" value="" />
		<input type="hidden" id="stampFileId" name="stampFileId" value="" />
		<input type="hidden" id="stampFilePath" name="stampFilePath" value="" />
		<input type="hidden" id="stampFileName" name="stampFileName" value="" />
		<input type="hidden" id="stampDisplayName" name="stampDisplayName" value="" />
		<input type="hidden" id="stampFileSize" name="stampFileSize" value="" />
		<input type="hidden" id="stampFileType" name="stampFileType" value="" />
		<input type="hidden" id="stampFileOrder" name="stampFileOrder" value="5" />
		<input type="hidden" id="stampImageWidth" name="stampImageWidth" value="30" />
		<input type="hidden" id="stampImageHeight" name="stampImageHeight" value="30" />	
<%	}  %>		

</form>
<jsp:include page="/app/jsp/common/adminform.jsp" />

<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>
<% } else { %>
<%
	String message = CommonUtil.nullTrim((String)request.getAttribute("message"));
	message = messageSource.getMessage(message, null, langType);
%>	
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
	alert("<%=message%>");
	window.close();
}
</script>
</head>
<body></body>
</html>
<% } %>

