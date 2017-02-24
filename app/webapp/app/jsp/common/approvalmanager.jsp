<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.common.util.DateUtil" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
/** 
 *  Class Name  : approvalmanager.jsp 
 *  Description : 결재객체 처리 
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
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String opt305 = appCode.getProperty("OPT305", "OPT305", "OPT"); // 반려문서결재 - 1 : 다시결재, 2 : 기안자 결정	
	opt305 = envOptionAPIService.selectOptionValue(compId, opt305);
	String opt303 = appCode.getProperty("OPT303", "OPT303", "OPT"); // 부서협조 - 1 : 최종협조자, 2 : 모든협조자
	opt303 = envOptionAPIService.selectOptionValue(compId, opt303);
	String opt304 = appCode.getProperty("OPT304", "OPT304", "OPT"); // 감사표시 - 1 : 결재라인, 2 : 협조라인, 3 : 감사라인	
	opt304 = envOptionAPIService.selectOptionValue(compId, opt304);
	
	String format = AppConfig.getProperty("format", "yyyy-MM-dd HH:mm:ss", "date");
	String roleId29 = AppConfig.getProperty("role_dailyinpecttarget", "", "role"); // 일상감사대상자
	String roleId31 = AppConfig.getProperty("role_ceo", "", "role"); // CEO

	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)
	String art030 = appCode.getProperty("ART030", "ART030", "ART"); // 협조
	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art032 = appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
	String art033 = appCode.getProperty("ART033", "ART033", "ART"); // 협조(기안)
	String art034 = appCode.getProperty("ART034", "ART034", "ART"); // 협조(검토)
	String art035 = appCode.getProperty("ART035", "ART035", "ART"); // 협조(결재)

	String art130 = appCode.getProperty("ART130", "ART130", "ART"); // 합의 // jth8172 2012 신결재 TF
	String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의 // jth8172 2012 신결재 TF
	String art132 = appCode.getProperty("ART132", "ART132", "ART"); // 부서합의 // jth8172 2012 신결재 TF
	String art133 = appCode.getProperty("ART133", "ART133", "ART"); // 합의(기안) // jth8172 2012 신결재 TF
	String art134 = appCode.getProperty("ART134", "ART134", "ART"); // 합의(검토) // jth8172 2012 신결재 TF
	String art135 = appCode.getProperty("ART135", "ART135", "ART"); // 합의(결재) // jth8172 2012 신결재 TF
	
	String art040 = appCode.getProperty("ART040", "ART040", "ART"); // 감사
	String art041 = appCode.getProperty("ART041", "ART041", "ART"); // 부서감사
	String art042 = appCode.getProperty("ART042", "ART042", "ART"); // 감사(기안)
	String art043 = appCode.getProperty("ART043", "ART043", "ART"); // 감사(검토)
	String art044 = appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재)
	String art045 = appCode.getProperty("ART045", "ART045", "ART"); // 일상감사
	String art046 = appCode.getProperty("ART046", "ART046", "ART"); // 준법감시
	String art047 = appCode.getProperty("ART047", "ART047", "ART"); // 감사위원
	String art050 = appCode.getProperty("ART050", "ART050", "ART"); // 결재 
	String art051 = appCode.getProperty("ART051", "ART051", "ART"); // 전결
	String art052 = appCode.getProperty("ART052", "ART052", "ART"); // 대결
	String art053 = appCode.getProperty("ART053", "ART053", "ART"); // 1인전결
	String art054 = appCode.getProperty("ART054", "ART054", "ART"); // 후열
	String art055 = appCode.getProperty("ART055", "ART055", "ART"); // 통보 // jth8172 2012 신결재 TF
	String art060 = appCode.getProperty("ART060", "ART060", "ART"); // 선람
	String art070 = appCode.getProperty("ART070", "ART070", "ART"); // 담당	
	
	// variable naming rule: {tgw_app_code.code_value(ART010~ART070)}_{tgw_app_option.option_id(OPT001~OPT023)}. 2012.03.23. edited by bonggon.choi.
	String art010_opt001 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT001", "OPT001", "OPT"));	
	String art020_opt002 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT002", "OPT002", "OPT"));	
	String art030_opt003 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT003", "OPT003", "OPT"));	
	String art031_opt004 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT004", "OPT004", "OPT"));	
	String art032_opt005 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT005", "OPT005", "OPT"));	
	String art033_opt006 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT006", "OPT006", "OPT"));	
	String art034_opt007 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT007", "OPT007", "OPT"));	
	String art035_opt008 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT008", "OPT008", "OPT"));	

	String art130_opt053 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT053", "OPT053", "OPT"));	 // jth8172 2012 신결재 TF
	String art131_opt054 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT054", "OPT054", "OPT"));	 // jth8172 2012 신결재 TF
	String art132_opt055 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT055", "OPT055", "OPT"));	 // jth8172 2012 신결재 TF
	String art133_opt056 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT056", "OPT056", "OPT"));	 // jth8172 2012 신결재 TF
	String art134_opt057 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT057", "OPT057", "OPT"));	 // jth8172 2012 신결재 TF
	String art135_opt058 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT058", "OPT058", "OPT"));	 // jth8172 2012 신결재 TF
	
	String art040_opt009 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT009", "OPT009", "OPT"));	
	String art041_opt010 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT010", "OPT010", "OPT"));	
	String art042_opt011 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT011", "OPT011", "OPT"));	
	String art043_opt012 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT012", "OPT012", "OPT"));	
	String art044_opt013 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT013", "OPT013", "OPT"));	
	String art045_opt021 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT021", "OPT021", "OPT"));	
	String art046_opt022 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT022", "OPT022", "OPT"));	
	String art047_opt023 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT023", "OPT023", "OPT"));	
	String art050_opt014 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT014", "OPT014", "OPT"));	
	String art051_opt015 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT015", "OPT015", "OPT"));	
	String art052_opt016 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT016", "OPT016", "OPT"));	
	String art053_opt017 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT017", "OPT017", "OPT"));	
	String art054_opt018 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT018", "OPT018", "OPT"));	
	String art055_opt059 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT059", "OPT059", "OPT")); // jth8172 2012 신결재 TF	 
	String art060_opt019 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT019", "OPT019", "OPT"));	
	String art070_opt020 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT020", "OPT020", "OPT"));
	
	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	
	String det001 = appCode.getProperty("DET001", "DET001", "DET"); // 내부
	String det002 = appCode.getProperty("DET002", "DET002", "DET"); // 대내
	String det003 = appCode.getProperty("DET003", "DET003", "DET"); // 대외
	String det004 = appCode.getProperty("DET004", "DET004", "DET"); // 대내외
	String det005 = appCode.getProperty("DET005", "DET005", "DET"); // 외부 행정기관
	String det006 = appCode.getProperty("DET006", "DET006", "DET"); // 외부 민간기관
	String det007 = appCode.getProperty("DET007", "DET007", "DET"); // 민원인
	String det011 = appCode.getProperty("DET011", "DET011", "DET"); // 행정기관
	
	String drs001 = appCode.getProperty("DRS001", "DRS001", "DRS"); // 보고경로
	String drs002 = appCode.getProperty("DRS002", "DRS002", "DRS"); // 부서
	String drs003 = appCode.getProperty("DRS003", "DRS003", "DRS"); // 본부
	String drs004 = appCode.getProperty("DRS004", "DRS004", "DRS"); // 기관 // jth8172 2012 신결재 TF
	String drs005 = appCode.getProperty("DRS005", "DRS005", "DRS"); // 회사
	
	String dry001 = appCode.getProperty("DRY001", "DRY001", "DRY"); // 1년
	String dry002 = appCode.getProperty("DRY002", "DRY002", "DRY"); // 3년
	String dry003 = appCode.getProperty("DRY003", "DRY003", "DRY"); // 5년
	String dry004 = appCode.getProperty("DRY004", "DRY004", "DRY"); // 10년	
	String dry005 = appCode.getProperty("DRY005", "DRY005", "DRY"); // 20년
	String dry006 = appCode.getProperty("DRY006", "DRY006", "DRY"); // 30년
	String dry007 = appCode.getProperty("DRY007", "DRY007", "DRY"); // 준영구
	String dry008 = appCode.getProperty("DRY008", "DRY008", "DRY"); // 영구
	
	String wkt001 = appCode.getProperty("WKT001", "WKT001", "WKT"); // 여신
	String wkt002 = appCode.getProperty("WKT002", "WKT002", "WKT"); // 일반

	String oac001 = appCode.getProperty("OAC001", "OAC001", "OAC"); // 일반
	String oac002 = appCode.getProperty("OAC002", "OAC002", "OAC"); // 도서신청서
	
	String obt001 = appCode.getProperty("OBT001", "OBT001", "OBT"); // 그룹웨어
	String obt002 = appCode.getProperty("OBT002", "OBT002", "OBT"); // 코어시스템
	String obt003 = appCode.getProperty("OBT003", "OBT003", "OBT"); // 카드시스템
	String obt004 = appCode.getProperty("OBT004", "OBT004", "OBT"); // 기타

	String ort001 = appCode.getProperty("ORT001", "ORT001", "ORT"); // 데이타수정-입력오류
	String ort002 = appCode.getProperty("ORT002", "ORT002", "ORT"); // 데이터수정-프로그램미비
	String ort003 = appCode.getProperty("ORT003", "ORT003", "ORT"); // 데이터수정-기타
	String ort004 = appCode.getProperty("ORT004", "ORT004", "ORT"); // 프로그램요청
	String ort005 = appCode.getProperty("ORT005", "ORT005", "ORT"); // 자료요청
	String ort006 = appCode.getProperty("ORT006", "ORT006", "ORT"); // 개인정보
	String ort007 = appCode.getProperty("ORT007", "ORT007", "ORT"); // 기타
	
	String rtt001 = appCode.getProperty("RTT001", "RTT001", "RTT"); // 워터마크 인쇄제외
	String rtt002 = appCode.getProperty("RTT002", "RTT002", "RTT"); // CDRW 사용 신청
	String rtt003 = appCode.getProperty("RTT003", "RTT003", "RTT"); // 이동저장장치 파일반출
	
	String srt030 = appCode.getProperty("SRT030", "SRT030", "SRT"); // 30분
	String srt100 = appCode.getProperty("SRT100", "SRT100", "SRT"); // 1시간
	String srt200 = appCode.getProperty("SRT200", "SRT200", "SRT"); // 2시간
	String srt300 = appCode.getProperty("SRT300", "SRT300", "SRT"); // 3시간
	String srt400 = appCode.getProperty("SRT400", "SRT400", "SRT"); // 4시간
	String srt500 = appCode.getProperty("SRT500", "SRT500", "SRT"); // 5시간
	String srt600 = appCode.getProperty("SRT600", "SRT600", "SRT"); // 6시간
	String srt700 = appCode.getProperty("SRT700", "SRT700", "SRT"); // 7시간
	String srt800 = appCode.getProperty("SRT800", "SRT800", "SRT"); // 8시간
	
	String dct498 = AppConfig.getProperty("form498", "DCT498", "formcode");
%>
<script type="text/javascript">
function getApproverList(lineinfos) {
	var line = new Array();
	var lineinfo = lineinfos.split(String.fromCharCode(4));
	var linelength = lineinfo.length;
	
	
	
	for (var loop = 0; loop < linelength; loop++) {
		//alert('getApproverList : ' + lineinfo[loop]);
		
		
		if (lineinfo[loop].indexOf(String.fromCharCode(2)) != -1) {
			var info = lineinfo[loop].split(String.fromCharCode(2));
			var approver = new Object();
			approver.lineOrder = info[0];
			approver.lineSubOrder = info[1];
			approver.approverId = info[2];
			approver.approverName = info[3];
			approver.approverPos = info[4];
			approver.approverDeptId = info[5];
			approver.approverDeptName = info[6];
			approver.representativeId = info[7];
			approver.representativeName = info[8];
			approver.representativePos = info[9];
			approver.representativeDeptId = info[10];
			approver.representativeDeptName = info[11];
			approver.askType = info[12];
			approver.procType = info[13];
			approver.absentReason = info[14];
			approver.editBodyYn = info[15];
			approver.editAttachYn = info[16];
			approver.editLineYn = info[17];
			approver.mobileYn = info[18];
			approver.procOpinion = info[19];
			approver.signFileName = info[20];
			approver.readDate = info[21];
			approver.processDate = info[22];
			approver.lineHisId = info[23];
			approver.fileHisId = info[24];
			approver.bodyHisId = info[25];
			approver.approverRole = info[26];
			approver.lineNum = info[27];
	
			line[loop] = approver;
			
			//alert('getApproverList : ' + line[loop].lineOrder);
		}
	}

	return line;
}

function getApproverInfo(line) {
	var lineinfo = "";

	var linelength = line.length;
	for (var loop = 0; loop < linelength; loop++) {
		var approver = line[loop];
		lineinfo += approver.lineOrder + String.fromCharCode(2) + approver.lineSubOrder + String.fromCharCode(2) + approver.approverId + String.fromCharCode(2) + 
			approver.approverName + String.fromCharCode(2) + approver.approverPos + String.fromCharCode(2) + approver.approverDeptId + String.fromCharCode(2) + 
			approver.approverDeptName + String.fromCharCode(2) + approver.representativeId + String.fromCharCode(2) + approver.representativeName + String.fromCharCode(2) + 
			approver.representativePos + String.fromCharCode(2) + approver.representativeDeptId + String.fromCharCode(2) + approver.representativeDeptName + String.fromCharCode(2) + 
			approver.askType + String.fromCharCode(2) + approver.procType + String.fromCharCode(2) + approver.absentReason + String.fromCharCode(2) + 
			approver.editBodyYn + String.fromCharCode(2) + approver.editAttachYn + String.fromCharCode(2) + approver.editLineYn + String.fromCharCode(2) + 
			approver.mobileYn + String.fromCharCode(2) + approver.procOpinion + String.fromCharCode(2) + approver.signFileName + String.fromCharCode(2) + 
			approver.readDate + String.fromCharCode(2) + approver.processDate + String.fromCharCode(2) + approver.lineHisId + String.fromCharCode(2) + 
			approver.fileHisId + String.fromCharCode(2) + approver.bodyHisId + String.fromCharCode(2) + approver.approverRole + String.fromCharCode(2) + 
			approver.lineNum + String.fromCharCode(4);
	}

	return lineinfo;
}

// 현재결재자
function getCurrentApprover(lineinfo, userid) {
	var line = new Array();
	if (lineinfo instanceof Array) {
		line = lineinfo;
	} else {
		line = getApproverList(lineinfo);
	}

	var linesize = line.length;
	for (var loop = 0; loop < linesize; loop++) {
		var approver = line[loop];
		if ((userid == approver.approverId || userid == approver.representativeId)
				&& (approver.procType == "<%=apt003%>" || approver.procType == "<%=apt004%>" || approver.procType == "")) {
			return approver;
		}
	}

	return null;
}

function getCurrentDept(lineinfo, deptid) {
	var line = new Array();
	if (lineinfo instanceof Array) {
		line = lineinfo;
	} else {
		line = getApproverList(lineinfo);
	}
	var linesize = line.length;
	for (var loop = 0; loop < linesize; loop++) {
		var approver = line[loop];
		if ((deptid == approver.approverDeptId)
				&& ((approver.askType == "<%=art032%>" ||  approver.askType == "<%=art041%>") || (approver.lineNum == "2" && approver.approverId == ""))
				&& (approver.procType == "<%=apt003%>" || approver.procType == "<%=apt004%>" || approver.procType == "")) {
			return approver;
		}
	}

	return null;
}

// 현재 사용자
function getCurrentUser(lineinfo, userid, proctype) {
	var line = new Array();
	if (lineinfo instanceof Array) {
		line = lineinfo;
	} else {
		line = getApproverList(lineinfo);
	}

	var linesize = line.length;
	for (var loop = linesize - 1; loop >= 0; loop--) {
		var approver = line[loop];
		if ((userid == approver.approverId || userid == approver.representativeId)
				&& (approver.procType == proctype)) {
			return approver;
		}
	}

	return null;
}

function getPreviousUser(lineinfo, currentUser) {
	var line = new Array();
	if (lineinfo instanceof Array) {
		line = lineinfo;
	} else {
		line = getApproverList(lineinfo);
	}

	var pos = 0;
	var previousUsers = new Array();
	var linesize = line.length;
	for (var loop = 0; loop < linesize; loop++) {
		var approver = line[loop];		
		if (currentUser.lineOrder - 1 == approver.lineOrder) {
			previousUsers[pos++] = approver;
		}
	}

	return previousUsers;
}

function getFirstApprover(line) {
	if (line.length > 0) {
		return line[0];
	}

	return null;
}

function getLastApprover(line) {
	var linesize = line.length;
	for (var loop = linesize - 1; loop >= 0; loop--) {
		var approver = line[loop];
		if (approver.type != "<%=art054%>" && approver.type != "<%=art055%>") {   // jth8172 2012 신결재 TF
			return approver;
		}
	}

	return null;
}


// 검토자(결재자) 수
function getApproverCount(lineinfo, auditlinetype) {
	var line = new Array();
	if (lineinfo instanceof Array) {
		line = lineinfo;
	} else {
		line = getApproverList(lineinfo);
	}

	var keyword = "<%=art010%>|<%=art020%>|<%=art021%>|<%=art050%>|<%=art051%>|<%=art052%>|<%=art053%>";
	if (auditlinetype == "1") {
		keyword += "|<%=art040%>|<%=art041%>|<%=art044%>|<%=art045%>|<%=art046%>|<%=art047%>";
	}
	return searchApprover(line, keyword);
}

// 협조자 수
function getAssistantCount(lineinfo, assistantlinetype, auditlinetype) {
	var line = new Array();
	if (lineinfo instanceof Array) {
		line = lineinfo;
	} else {
		line = getApproverList(lineinfo);
	}

	var keyword = "";
	if (assistantlinetype == "1") { 
		keyword += "<%=art030%>|<%=art031%>|<%=art032%>|<%=art035%>|<%=art130%>|<%=art131%>|<%=art132%>|<%=art135%>";
	} else {
		keyword += "<%=art030%>|<%=art031%>|<%=art032%>|<%=art033%>|<%=art034%>|<%=art035%>|<%=art130%>|<%=art131%>|<%=art132%>|<%=art133%>|<%=art134%>|<%=art135%>";
	}
	if (auditlinetype == "2") {
		keyword += "|<%=art040%>|<%=art041%>|<%=art044%>|<%=art045%>|<%=art046%>|<%=art047%>";
	}
	return searchApprover(line, keyword);
}
 

// 감사자 수
function getAuditCount(lineinfo, auditlinetype) {
	var line = new Array();
	if (lineinfo instanceof Array) {
		line = lineinfo;
	} else {
		line = getApproverList(lineinfo);
	}
	// 결재경로에서 감사자 수만 체크할 경우 auditlinetype : undefined
	if (	typeof(auditlinetype) == "undefined") {
		auditlinetype = "3";
	}

	var keyword = "";
	if (auditlinetype == "3") {
		keyword = "<%=art040%>|<%=art041%>|<%=art044%>|<%=art045%>|<%=art046%>|<%=art047%>";
	}
	return searchApprover(line, keyword);
}

function getSpecialRoleUser(lineinfo) {
	var line = new Array();
	if (lineinfo instanceof Array) {
		line = lineinfo;
	} else {
		line = getApproverList(lineinfo);
	}

	var count = 0;
	var linelength = line.length;
	for (var loop = 0; loop < linelength; loop++) {
		var approver = line[loop];
		if ((approver.approverRole).indexOf("<%=roleId31%>") != -1 || (approver.approverRole).indexOf("<%=roleId29%>") != -1) {
			return approver;
		}
	}
	
	return null;
}


// 결재자 - 결재/전결/대결/1인전결
function getCurrentLineApproverCount(lineinfo, linenum) {
	var line = new Array();
	if (lineinfo instanceof Array) {
		line = lineinfo;
	} else {
		line = getApproverList(lineinfo);
	}

	var keyword = "<%=art050%>|<%=art051%>|<%=art052%>|<%=art053%>";
	return searchCurrentLineApprover(line, keyword, linenum);	
}

// 결재라인 전체 수
function getAppLineCount(lineinfo) {
	var line = new Array();
	if (lineinfo instanceof Array) {
		line = lineinfo;
	} else {
		line = getApproverList(lineinfo);
	}
	
	return line.length;
}

// 라인별 검토자(결재자) 수
function getApproverCountByLine(lineinfo, lineNum) {
	var line = new Array();
	if (lineinfo instanceof Array) {
		line = lineinfo;
	} else {
		line = getApproverList(lineinfo);
	}

	var linecount = 0;
	var linesize = line.length;
	for (var loop = 0; loop < linesize; loop++) {
		var approver = line[loop];
		if (approver.lineNum == lineNum) {
			linecount++;
		}
	}
	
	return linecount
}

// 결재자 의견여부
function existApproverOpinion(lineinfo) {
	var line = new Array();
	if (lineinfo instanceof Array) {
		line = lineinfo;
	} else {
		line = getApproverList(lineinfo);
	}

	var existOpinion = false;
	var linelength = line.length;
	for (var loop = 0; loop < linelength; loop++) {
		var approver = line[loop];
		if (approver.procOpinion != "") {
			existOpinion = true;
			break;
		}
	}

	return existOpinion;
}

function changeApproveType(lineinfo) {
	var line = new Array();
	if (lineinfo instanceof Array) {
		line = lineinfo;
	} else {
		line = getApproverList(lineinfo);
	}
	line[0].askType = "<%=art053%>";

	return getApproverInfo(line);
}

function searchApprover(line, keyword) {
	var count = 0;
	var linelength = line.length;
	for (var loop = 0; loop < linelength; loop++) {
		var approver = line[loop];
		if (keyword.indexOf(approver.askType) != -1) {
			count++;
		}
	}
			 
	return count;
}

function searchCurrentLineApprover(line, keyword, linenum) {
	var count = 0;
	var linelength = line.length;
	for (var loop = 0; loop < linelength; loop++) {
		var approver = line[loop];
		if (keyword.indexOf(approver.askType) != -1 && approver.lineNum == linenum) {
			count++;
		}
	}
			 
	return count;
}

function getApproverUser(lineinfo, compId) {
	var line = new Array();
	if (lineinfo instanceof Array) {
		line = lineinfo;
	} else {
		line = getApproverList(lineinfo);
	}
	
	if (typeof(compId) == "undefined") {
		compId = "<%=compId%>";
	}
	var userList = new Array();
	var linelength = line.length;
	for (var loop = 0; loop < linelength; loop++) {
		var approver = line[loop];
		var user = new Object();
		user.repname = approver.representativeName;
		user.reppos = approver.representativePos;
		if (approver.askType == "<%=art021%>" || approver.askType == "<%=art032%>" || approver.askType == "<%=art041%>" || approver.askType == "<%=art132%>") {
			user.position = approver.approverDeptName;
			user.name = approver.approverDeptName;
		} else if ((approver.askType == "<%=art040%>") || (approver.askType == "<%=art044%>")) {
			user.position = "<%=art040_opt009%>";
			user.name = approver.approverName;
		} else if (approver.askType == "<%=art045%>") {
			user.position = "<%=art045_opt021%>";
			user.name = approver.approverName;
		} else if (approver.askType == "<%=art046%>") {
			user.position = "<%=art046_opt022%>";
			user.name = approver.approverName;
		} else if (approver.askType == "<%=art047%>") {
			user.position = "<%=art047_opt023%>";
			user.name = approver.approverName;
		} else {
			user.position = approver.approverPos;
			user.name = approver.approverName;
		}
		user.type = approver.askType;
		if (approver.procType != "<%=apt002%>" && approver.procType != "<%=apt004%>") {
			user.date = approver.processDate;
		} else {
			user.date = "";
		}
		user.absreason = approver.absentReason;
		user.linenum = approver.lineNum;
		user.lineorder = approver.lineOrder;
		user.linesuborder = approver.lineSubOrder;
		user.signfile = approver.signFileName;
		user.dept = approver.approverDeptName;
		user.repdept = approver.representativeDeptName;
		user.approverrole = approver.approverRole;
		userList[loop] = user;
	}

	return userList;
}

//transfer appr-code to appr-codename
function getApprovalCode (apprcode, linenum, doctype, assistantlinetype, auditlinetype) {
	if (typeof(assistantlinetype) == "undefined" || typeof(auditlinetype) == "undefined") {
		assistantlinetype = "<%=opt303%>";
		auditlinetype = "<%=opt304%>";
	}

    if (apprcode == "<%=art010%>") {
        return HwpConst.Appr.Submit;
    } else if (apprcode == "<%=art020%>" || apprcode == "<%=art021%>") {
        return HwpConst.Appr.Consider;
    } else if (apprcode == "<%=art040%>" || apprcode == "<%=art041%>" || apprcode == "<%=art044%>" 
        || apprcode == "<%=art045%>" || apprcode == "<%=art046%>" || apprcode == "<%=art047%>") {
        if (auditlinetype == "1") {
        	return HwpConst.Appr.Consider;
        } else if (auditlinetype == "2") {
        	return HwpConst.Appr.Assistance;
        } else if (auditlinetype == "3") {
        	return HwpConst.Appr.Audit;
        } else {
            return "";
        }
    } else if (apprcode == "<%=art030%>" || apprcode == "<%=art032%>" || apprcode == "<%=art034%>" || apprcode == "<%=art035%>") {
        if (assistantlinetype == "2" || apprcode != "<%=art034%>") {
        	return HwpConst.Appr.Assistance;
        } else {
            return "";
        }
    } else if (apprcode == "<%=art031%>") {
        return HwpConst.Appr.ParellelAssistance;
    } else if (apprcode == "<%=art130%>" || apprcode == "<%=art132%>" || apprcode == "<%=art134%>" || apprcode == "<%=art135%>") {
        if (assistantlinetype == "2" || apprcode != "<%=art134%>") {
        	return HwpConst.Appr.Agree;
        } else {
            return "";
        }
    } else if (apprcode == "<%=art131%>") {
        return HwpConst.Appr.ParellelAgree;
    } else if (apprcode == "<%=art050%>") {
        return HwpConst.Appr.Approver;
    } else if (apprcode == "<%=art051%>" || apprcode == "<%=art053%>") {
        return HwpConst.Appr.Arbitrary;
    } else if (apprcode == "<%=art052%>") {
        return HwpConst.Appr.DecideFor;
    } else if (apprcode == "<%=art054%>") {
        return HwpConst.Appr.ReadAfter;
    } else if (apprcode == "<%=art055%>") {  // jth8172 2012 신결재 TF
        return HwpConst.Appr.Inform;
    } else if (apprcode == "DEA71") {
        return HwpConst.Appr.DecideForArbitrary;
    } else {
        return "";
    }
}

// 협조와 감사 순서 정렬
function arrangeAssistant(lineinfo, auditlinetype) {
	var line = new Array();
	if (lineinfo instanceof Array) {
		line = lineinfo;
	} else {
		line = getApproverList(lineinfo);
	}
	
	if (auditlinetype == "2") {
		var assistantcount = 0;
		var assistantlist = new Array();
		var agreecount = 0;
		var agreelist = new Array();
		var auditcount = 0;
		var auditlist = new Array();
		var approvercount = 0;
		var approverlist = new Array();
	
		var linelength = line.length;
		for (var loop = 0; loop < linelength; loop++) {
			var approver = line[loop];
			if (approver.askType == "<%=art030%>" || approver.askType == "<%=art031%>" || approver.askType == "<%=art032%>" || approver.askType == "<%=art033%>" 
				|| approver.askType == "<%=art034%>" || approver.askType == "<%=art035%>") {
				assistantlist[assistantcount++] = approver;
			} else if (approver.askType == "<%=art130%>" || approver.askType == "<%=art131%>" || approver.askType == "<%=art132%>" || approver.askType == "<%=art133%>" 
				|| approver.askType == "<%=art134%>" || approver.askType == "<%=art135%>") {
				agreelist[agreecount++] = approver;
			} else if (approver.askType == "<%=art040%>" || approver.askType == "<%=art041%>" || approver.askType == "<%=art042%>" || approver.askType == "<%=art043%>" 
				|| approver.askType == "<%=art044%>" || approver.askType == "<%=art045%>" || approver.askType == "<%=art046%>" || approver.askType == "<%=art047%>") {
				auditlist[auditcount++] = approver;
			} else {
				approverlist[approvercount++] = approver;
			}
		}
		if (auditcount > 0) {
			for (var loop = 0; loop < approvercount; loop++) {
				line[loop] = approverlist[loop];
			}
			for (var loop = 0; loop < assistantcount; loop++) {
				line[approvercount + loop] = assistantlist[loop];
			}
			for (var loop = 0; loop < agreecount; loop++) {
				line[approvercount + loop] = agreelist[loop];
			}
			for (var loop = 0; loop < auditcount; loop++) {
				line[approvercount + assistantcount + loop] = auditlist[loop];
			}
		}
	}
	
	/* for(var loop = 0; loop < line.length; loop++){
		alert('arrangeAssistant : ' + line[loop].lineOrder);
	} */
	
	return line;
}

// 결재선 초기화
function clearApproverList(lineinfos) {
	var line = new Array();
	var lineinfo = lineinfos.split(String.fromCharCode(4));
		
	var linelength = lineinfo.length;
	for (var loop = 0; loop < linelength; loop++) {
		if (lineinfo[loop].indexOf(String.fromCharCode(2)) != -1) {
			var info = lineinfo[loop].split(String.fromCharCode(2));
			var approver = new Object();
			approver.lineOrder = info[0];
			approver.lineSubOrder = info[1];
			approver.approverId = info[2];
			approver.approverName = info[3];
			approver.approverPos = info[4];
			approver.approverDeptId = info[5];
			approver.approverDeptName = info[6];
			approver.representativeId = "";
			approver.representativeName = "";
			approver.representativePos = "";
			approver.representativeDeptId = "";
			approver.representativeDeptName = "";
			approver.askType = info[12];
			approver.procType = "";
			approver.absentReason = "";
			approver.editBodyYn = "";
			approver.editAttachYn = "";
			approver.editLineYn = "";
			approver.mobileYn = "";
			approver.procOpinion = "";
			if (loop == 0) {
				approver.signFileName = info[20];
			} else {
				approver.signFileName = "";
			}
			approver.readDate = "";
			approver.processDate = "";
			approver.lineHisId = "";
			approver.fileHisId = "";
			approver.bodyHisId = "";
			approver.approverRole = info[26];
			approver.lineNum = info[27];
	
			line[loop] = approver;
		}
	}

	return getApproverInfo(line);
}

// 결재선 회수처리
function withdrawApprover(lineinfos) {
	var line = new Array();
	var lineinfo = lineinfos.split(String.fromCharCode(4));
		
	var linelength = lineinfo.length;
	for (var loop = 0; loop < linelength; loop++) {
		if (lineinfo[loop].indexOf(String.fromCharCode(2)) != -1) {
			var info = lineinfo[loop].split(String.fromCharCode(2));
			var approver = new Object();
			approver.lineOrder = info[0];
			approver.lineSubOrder = info[1];
			approver.approverId = info[2];
			approver.approverName = info[3];
			approver.approverPos = info[4];
			approver.approverDeptId = info[5];
			approver.approverDeptName = info[6];
			approver.representativeId = "";
			approver.representativeName = "";
			approver.representativePos = "";
			approver.representativeDeptId = "";
			approver.representativeDeptName = "";
			approver.askType = info[12];
			approver.procType = "";
			approver.absentReason = "";
			approver.editBodyYn = "";
			approver.editAttachYn = "";
			approver.editLineYn = "";
			approver.mobileYn = "";
			approver.procOpinion = "";
			approver.signFileName = info[20];
			approver.readDate = "";
			approver.processDate = "";
			approver.lineHisId = "";
			approver.fileHisId = "";
			approver.bodyHisId = "";
			approver.approverRole = info[26];
			approver.lineNum = info[27];
	
			line[loop] = approver;
		}
	}

	return getApproverInfo(line);
}

// 반려문서결재선 처리
function getProcessTypeofAppLine(draftType) {
	if (draftType.indexOf("redraft") == -1) {
		return draftType;
	} else {
<% if ("1".equals(opt305)) { %>
		return "redraft-all";
<% } else { %>
		var sourceInfo = $("#sourceAppLine").val();
		var targetInfo = $("#appLine", "#approvalitem1").val();
		if (sourceInfo == null || sourceInfo == "" || targetInfo == null || targetInfo == "") {
			return "redraft-all";
		}
		if (compareAppLine(sourceInfo, targetInfo) == true) {
			/* if (confirm("<spring:message code='approval.msg.skip.approved.approver'/>")) { 결재경로의 모든 결재자에게 새로 결재를 받으시겠습니까?
				return "redraft-all";
			} else {
				return "redraft-skip";
			} */
			return "redraft-all";
		} else {
			return "redraft-all";
		}
<% } %>
	}
}

function compareAppLine(sourceInfo, targetInfo) {
	var result = true;
	var sourceLine = getApproverList(sourceInfo);
	var targetLine = getApproverList(targetInfo);
	var sourceCount = sourceLine.length;
	var targetCount = targetLine.length;
	if (sourceCount == targetCount) {
		for (var loop = 0; loop < sourceCount; loop++) {
			if (sourceLine[loop].lineOrder != targetLine[loop].lineOrder || sourceLine[loop].lineSubOrder != targetLine[loop].lineSubOrder
				|| sourceLine[loop].approverId != targetLine[loop].approverId || sourceLine[loop].representativeId != targetLine[loop].representativeId
				|| sourceLine[loop].askType != targetLine[loop].askType || sourceLine[loop].lineNum != targetLine[loop].lineNum) {
				result = false;
				break;
			}
		}
	} else {
		result = false;
	}

	return result;
}

// 수신자
function getReceiverList(apprecv) {
	var recvlist = new Array();
	var recvinfo = apprecv.split(String.fromCharCode(4));
	var recvlength = recvinfo.length;
	for (var loop = 0; loop < recvlength; loop++) {
		if (recvinfo[loop].indexOf(String.fromCharCode(2)) != -1) {
			var info = recvinfo[loop].split(String.fromCharCode(2));
			var receiver = new Object();
			receiver.receiverType = info[0];
			receiver.enfType = info[1];
			receiver.recvCompId = info[2];
			receiver.recvDeptId = info[3];
			receiver.recvDeptName = info[4];
			receiver.refDeptId = info[5];
			receiver.refDeptName = info[6];
			receiver.recvUserId = info[7];
			receiver.recvUserName = info[8];
			receiver.postNumber = info[9];
			receiver.address = info[10];
			receiver.telephone = info[11];
			receiver.fax = info[12];
			receiver.recvSymbol = info[13];
			receiver.newFlg = info[14];
			receiver.refSymbol = info[15];
			receiver.recvChiefName = info[16];
			receiver.refChiefName = info[17];
			receiver.receiverOrder = info[18];
				
			recvlist[loop] = receiver;
		}
	}

	return recvlist;
}

function getReceiverInfo(recvlist) {
	var recvinfo = "";

	var recvlength = recvlist.length;
	for (var loop = 0; loop < recvlength; loop++) {
		var receiver = recvlist[loop];
		recvinfo += receiver.receiverType + String.fromCharCode(2) + receiver.enfType + String.fromCharCode(2) + receiver.recvCompId + String.fromCharCode(2) + 
			receiver.recvDeptId + String.fromCharCode(2) + receiver.recvDeptName + String.fromCharCode(2) + receiver.refDeptId + String.fromCharCode(2) + 
			receiver.refDeptName + String.fromCharCode(2) + receiver.recvUserId + String.fromCharCode(2) + receiver.recvUserName + String.fromCharCode(2) + 
			receiver.postNumber + String.fromCharCode(2) + receiver.address + String.fromCharCode(2) + receiver.telephone + String.fromCharCode(2) + 
			receiver.fax + String.fromCharCode(2) + receiver.recvSymbol + String.fromCharCode(2) + receiver.newFlg + String.fromCharCode(2) + 
			receiver.refSymbol + String.fromCharCode(2) + receiver.recvChiefName + String.fromCharCode(2) + 
			receiver.refChiefName + String.fromCharCode(2) + receiver.receiverOrder + String.fromCharCode(4);
	}

	return lineinfo;
}

// 관련문서
function getRelatedDocList(relateddoc) {
	var relatedlist = new Array();
	var relatedinfo = relateddoc.split(String.fromCharCode(4));
	var relatedlength = relatedinfo.length;
	for (var loop = 0; loop < relatedlength; loop++) {
		if (relatedinfo[loop].indexOf(String.fromCharCode(2)) != -1) {
			var info = relatedinfo[loop].split(String.fromCharCode(2));
			var relatedDoc = new Object();
			relatedDoc.docId = info[0];
			relatedDoc.title = info[1];
			relatedDoc.usingType = info[2];
			relatedDoc.electronDocYn = info[3];
			relatedlist[loop] = relatedDoc;
		}
	}

	return relatedlist;
}

function getRelatedDocInfo(relatedlist) {
	var relatedinfo = "";

	if (relatedlist instanceof Array) {
		var relatedlength = relatedlist.length;
		for (var loop = 0; loop < relatedlength; loop++) {
			var relatedDoc = relatedlist[loop];
			relatedinfo += relatedDoc.docId + String.fromCharCode(2) + relatedDoc.title + String.fromCharCode(2) 
				+ relatedDoc.usingType + String.fromCharCode(2) + relatedDoc.electronDocYn + String.fromCharCode(4);
		}
	} else {
		var relatedDoc = relatedlist;
		relatedinfo += relatedDoc.docId + String.fromCharCode(2) + relatedDoc.title + String.fromCharCode(2) 
			+ relatedDoc.usingType + String.fromCharCode(2) + relatedDoc.electronDocYn + String.fromCharCode(4);
	}

	return relatedinfo;	
}

function getDisplayRelatedDoc(relateddoc) {
	var relatedlist = "";
	var relatedinfo = relateddoc.split(String.fromCharCode(4));
	var relatedlength = relatedinfo.length;
	for (var loop = 0; loop < relatedlength; loop++) {
		if (relatedinfo[loop].indexOf(String.fromCharCode(2)) != -1) {
			var info = relatedinfo[loop].split(String.fromCharCode(2));
			if (relatedlist != "") {
				relatedlist += "\r\n";
			}
			relatedlist += info[1];
		}
	}

	return relatedlist;
}

// 관련규정
function getRelatedRuleList(relatedrule) {
	var relatedlist = new Array();
	var relatedinfo = relatedrule.split(String.fromCharCode(4));
	var relatedlength = relatedinfo.length;
	for (var loop = 0; loop < relatedlength; loop++) {
		if (relatedinfo[loop].indexOf(String.fromCharCode(2)) != -1) {		
			var info = relatedinfo[loop].split(String.fromCharCode(2));
			var relatedRule = new Object();
			relatedRule.ruleId = info[0];
			relatedRule.ruleLink = info[1];
			relatedRule.ruleName = info[2];
			relatedlist[loop] = relatedRule;
		}
	}

	return relatedlist;
}

function getRelatedRuleInfo(relatedlist) {
	var relatedinfo = "";

	if (relatedlist instanceof Array) {
		var relatedlength = relatedlist.length;
		for (var loop = 0; loop < relatedlength; loop++) {
			var relatedRule = relatedlist[loop];
			relatedinfo += relatedRule.ruleId + String.fromCharCode(2) + relatedRule.ruleLink + String.fromCharCode(2) + relatedRule.ruleName + String.fromCharCode(4);
		}
	} else {
		var relatedRule = relatedlist;
		relatedinfo += relatedRule.ruleId + String.fromCharCode(2) + relatedRule.ruleLink + String.fromCharCode(2) + relatedRule.ruleName + String.fromCharCode(4);
	}

	return relatedinfo;	
}

function getDisplayRelatedRule(relatedrule) {
	var relatedlist = "";
	var relatedinfo = relatedrule.split(String.fromCharCode(4));
	var relatedlength = relatedinfo.length;
	for (var loop = 0; loop < relatedlength; loop++) {
		if (relatedinfo[loop].indexOf(String.fromCharCode(2)) != -1) {		
			var info = relatedinfo[loop].split(String.fromCharCode(2));
			if (relatedlist != "") {
				relatedlist += "\r\n";
			}
			relatedlist += info[2];
		}
	}

	return relatedlist;
}

// 거래처
function getCustomerList(customers) {
	var customerlist = new Array();
	var customerinfo = customers.split(String.fromCharCode(4));
	var customerlength = customerinfo.length;
	for (var loop = 0; loop < customerlength; loop++) {
		if (customerinfo[loop].indexOf(String.fromCharCode(2)) != -1) {
			var info = customerinfo[loop].split(String.fromCharCode(2));
			var customer = new Object();
			customer.customerId = info[0];
			customer.customerName = info[1];
			customerlist[loop] = customer;
		}
	}

	return customerlist;
}

function getCustomerInfo(customerlist) {
	var customerinfo = "";

	if (customerlist instanceof Array) {
		var customerlength = customerlist.length;
		for (var loop = 0; loop < customerlength; loop++) {
			var customer = customerlist[loop];
			customerinfo += customer.customerId + String.fromCharCode(2) + customer.customerName + String.fromCharCode(4);
		}
	} else {
		var customer = customerlist;
		customerinfo += customer.customerId + String.fromCharCode(2) + customer.customerName + String.fromCharCode(4);
	}

	return customerinfo;	
}

function getDisplayCustomer(customers) {
	var customerlist = "";
	var customerinfo = customers.split(String.fromCharCode(4));
	var customerlength = customerinfo.length;
	for (var loop = 0; loop < customerlength; loop++) {
		if (customerinfo[loop].indexOf(String.fromCharCode(2)) != -1) {
			var info = customerinfo[loop].split(String.fromCharCode(2));
			if (customerlist != "") {
				customerlist += ", ";
			}
			customerlist += info[1];
		}
	}

	return customerlist;
}


function getEnfList(appenf) {
	var enflist = new Array();
	var enfinfo = appenf.split(String.fromCharCode(4));
	var enflength = enfinfo.length;
	for (var loop = 0; loop < enflength; loop++) {
		if (enfinfo[loop].indexOf(String.fromCharCode(2)) != -1) {
			var info = enfinfo[loop].split(String.fromCharCode(2));
			var enf = new Object();
			
			enf.processorId             = info[0];
			enf.processorName           = info[1];
			enf.processorPos            = info[2];
			enf.processorDeptId         = info[3];
			enf.processorDeptName       = info[4];
			enf.representativeId       	= info[5];
			enf.representativeName     	= info[6];
			enf.representativePos      	= info[7];
			enf.representativeDeptId   	= info[8];
			enf.representativeDeptName 	= info[9];
			enf.askType                 = info[10];
			enf.procType                = info[11];
			enf.processDate             = info[12];
			enf.readDate                = info[13];
			enf.editLineYn              = info[14];
			enf.mobileYn                = info[15];
			enf.procOpinion             = info[16];
			enf.signFileName            = info[17];
			enf.lineHisId            	= info[18];
			enf.fileHisId            	= info[19];
			enf.absentReason            = info[20];
			enf.lineOrder               = info[21];
	
			enflist[loop] = enf;
		}
	}

	return enflist;
}

function getEnfInfo(enflist) {
	var enfinfo = "";

	var enflength = enflist.length;
	for (var loop = 0; loop < enflength; loop++) {
		var enf = enflist[loop];
		enfinfo += enf.processorId + String.fromCharCode(2)         
			+ enf.processorName + String.fromCharCode(2) + enf.processorPos + String.fromCharCode(2)      	      
			+ enf.processorDeptId + String.fromCharCode(2) + enf.processorDeptName + String.fromCharCode(2)       
			+ enf.originprocessorId + String.fromCharCode(2) + enf.originprocessorName + String.fromCharCode(2)     
			+ enf.originprocessorPos + String.fromCharCode(2) + enf.originprocessorDeptId + String.fromCharCode(2)   
			+ enf.originprocessorDeptName + String.fromCharCode(2) + enf.askType + String.fromCharCode(2)                 
			+ enf.procType + String.fromCharCode(2) + enf.processDate + String.fromCharCode(2)             
			+ enf.readDate + String.fromCharCode(2) + enf.editLineYn + String.fromCharCode(2)              
			+ enf.mobileYn + String.fromCharCode(2) + enf.procOpinion + String.fromCharCode(2)             
			+ enf.signFileName + String.fromCharCode(2) + enf.lineHisId  + String.fromCharCode(2)
			+ enf.fileHisId + String.fromCharCode(2) + enf.absentReason  + String.fromCharCode(2)
			+ enf.lineOrder  + String.fromCharCode(4);
	}

	return enfinfo;
}

function getPubReaderList(readersinfo) {
	var readers = new Array();
	var readerinfo = readersinfo.split(String.fromCharCode(4));
		
	var readerlength = readerinfo.length;
	for (var loop = 0; loop < readerlength; loop++) {
		if (readerinfo[loop].indexOf(String.fromCharCode(2)) != -1) {
			var info = readerinfo[loop].split(String.fromCharCode(2));
			var reader = new Object();
			reader.pubReaderId = info[0];
			reader.pubReaderName = info[1];
			reader.pubReaderPos = info[2];
			reader.pubReaderDeptId = info[3];
			reader.pubReaderDeptName = info[4];
			reader.pubReaderRole = info[5];
			reader.pubReaderOrder = info[6];
			reader.readDate = info[7];
			reader.pubReadDate = info[8];
			reader.registerId = info[9];
			reader.usingType = info[10];
	
			readers[loop] = reader;
		}
	}

	return readers;
}

function getPubReaderInfo(readers) {
	var readersinfo = "";

	var readerlength = readers.length;
	for (var loop = 0; loop < readerlength; loop++) {
		var reader = readers[loop];
		readersinfo += reader.pubReaderId + String.fromCharCode(2) + reader.pubReaderName + String.fromCharCode(2) + reader.pubReaderPos + String.fromCharCode(2) + 
			reader.pubReaderDeptId + String.fromCharCode(2) + reader.pubReaderDeptName + String.fromCharCode(2) + reader.pubReaderRole + String.fromCharCode(2) + 
			reader.pubReaderOrder + String.fromCharCode(2) + reader.readDate + String.fromCharCode(2) + reader.pubReadDate + String.fromCharCode(2) + reader.registerId + String.fromCharCode(2) + 
			reader.usingType + String.fromCharCode(4);
	}

	return readersinfo;
}

function getRecvEnfType(recvinfo,newrecvcnt){
	var recvlist = new Array();
	var recvEnfType ="";
	if(recvinfo instanceof Array){
		recvlist = recvinfo;
	}else{
		recvlist = getReceiverList(recvinfo);
	}
	var recvlength = recvlist.length;

	for(var loop = recvlength - newrecvcnt ; loop < recvlength ; loop++){
		var receiver = recvlist[loop];
		if(loop == recvlength -1){
			recvEnfType += receiver.enfType;
		}else{
			recvEnfType += receiver.enfType + ",";
		}
	}
	return recvEnfType;
}

function getEnfType(recvinfo) {
	var recvlist = new Array();
	if (recvinfo instanceof Array) {
		recvlist = recvinfo;
	} else {
		recvlist = getReceiverList(recvinfo);
	}
	var enfType = "<%=det001%>";
	var recvlength = recvlist.length;
	for (var loop = 0; loop < recvlength; loop++) {
		var receiver = recvlist[loop];
		var recvEnfType = receiver.enfType;
		if("<%=compId%>" != receiver.recvCompId) { 
		// 다른 회사 수신자 지정시 참조인 경우 
		// 수신자 유형이 대내(DET002:원래 이유는 배부함에 들어가지 않고 직접 접수하기 위해서였는데)로 지정되었으나
		// 문서의 시행범위를 대외(DET003)로 변경.. emptyColor. 2012.05.09.
			recvEnfType = "<%=det003%>";
		}
		 	
		if (recvEnfType == "<%=det002%>") {
			if (enfType == "<%=det003%>" || enfType == "<%=det005%>" || enfType == "<%=det006%>" || enfType == "<%=det007%>" || enfType == "<%=det011%>") {
				enfType = "<%=det004%>";
				break;
			}
			enfType = "<%=det002%>";
		} else if (recvEnfType == "<%=det003%>" || receiver.enfType == "<%=det005%>" || receiver.enfType == "<%=det006%>" || receiver.enfType == "<%=det007%>"|| receiver.enfType == "<%=det011%>") {
			if (enfType == "<%=det002%>") {
				enfType = "<%=det004%>";
				break;
			}
			enfType = "<%=det003%>";
		}
	}
	
	return enfType;
}

function getEnfBound(recvinfo) {
	var recvlist = new Array();
	if (recvinfo instanceof Array) {
		recvlist = recvinfo;
	} else {
		recvlist = getReceiverList(recvinfo);
	}
	
	var enfBound = "IN";
	var recvlength = recvlist.length;
	for (var loop = 0; loop < recvlength; loop++) {
		var receiver = recvlist[loop];
		if (receiver.enfType == "<%=det005%>" || receiver.enfType == "<%=det006%>" || receiver.enfType == "<%=det007%>" || receiver.enfType == "<%=det011%>") {
			enfBound = "OUT";
			break;
		}
	}

	return enfBound;
}

function getGroupEnfBound(recvinfo) {
	var recvlist = new Array();
	if (recvinfo instanceof Array) {
		recvlist = recvinfo;
	} else {
		recvlist = getReceiverList(recvinfo);
	}
	
	var enfBound = "IN";
	var recvlength = recvlist.length;
	for (var loop = 0; loop < recvlength; loop++) {
		var receiver = recvlist[loop];
		if (receiver.enfType != "<%=det002%>") {
			enfBound = "OUT";
			break;
		}
	}

	return enfBound;
}

function getCurrentDate() {
	return "<%=DateUtil.getCurrentDate()%>";
}

function typeOfAppDate(str, format) {
	if (str == "9999-12-31 23:59:59") {
		return "&nbsp;";
	} else {
		str = replaceAll(replaceAll(str, "-", "<%=format.substring(4,5)%>"), ":", "<%=format.substring(13,14)%>");
		if (format.length < str.length) {
			return str.substring(0, format.length);
		} else {
			return str;
		}
	}
}

function typeOfStr(str) {
	if (str == "") {
		return "&nbsp;";
	} else {
		return str;
	}
}

function typeOfApp(str) {
	if (str == "<%=art010%>") {
		return "<%=art010_opt001%>";
	} else if (str == "<%=art020%>" || str == "<%=art021%>") {
		return "<%=art020_opt002%>"
	} else if (str == "<%=art030%>") {
		return "<%=art030_opt003%>"
	} else if (str == "<%=art031%>") {
		return "<%=art031_opt004%>"
	} else if (str == "<%=art032%>") {
		return "<%=art032_opt005%>"
	} else if (str == "<%=art033%>") {
		return "<%=art033_opt006%>"
	} else if (str == "<%=art034%>") {
		return "<%=art034_opt007%>"
	} else if (str == "<%=art035%>") {
		return "<%=art035_opt008%>"

// jth8172 2012 신결재 TF
	} else if (str == "<%=art130%>") {
		return "<%=art130_opt053%>"
	} else if (str == "<%=art131%>") {
		return "<%=art131_opt054%>"
	} else if (str == "<%=art132%>") {
		return "<%=art132_opt055%>"
	} else if (str == "<%=art133%>") {
		return "<%=art133_opt056%>"
	} else if (str == "<%=art134%>") {
		return "<%=art134_opt057%>"
	} else if (str == "<%=art135%>") {
		return "<%=art135_opt058%>"
		
	} else if (str == "<%=art040%>") {
		return "<%=art040_opt009%>"
	} else if (str == "<%=art041%>") {
		return "<%=art041_opt010%>"
	} else if (str == "<%=art042%>") {
		return "<%=art042_opt011%>"
	} else if (str == "<%=art043%>") {
		return "<%=art043_opt012%>"
	} else if (str == "<%=art044%>") {
		return "<%=art044_opt013%>"
	} else if (str == "<%=art045%>") {
		return "<%=art045_opt021%>"
	} else if (str == "<%=art046%>") {
		return "<%=art046_opt022%>"
	} else if (str == "<%=art047%>") {
		return "<%=art047_opt023%>"
	} else if (str == "<%=art050%>") {
		return "<%=art050_opt014%>"
	} else if (str == "<%=art051%>") {
		return "<%=art051_opt015%>"
	} else if (str == "<%=art052%>") {
		return "<%=art052_opt016%>"
	} else if (str == "<%=art053%>") {
		return "<%=art053_opt017%>"
	} else if (str == "<%=art054%>") {
		return "<%=art054_opt018%>"
	} else if (str == "<%=art055%>") {  // jth8172 2012 신결재 TF
		return "<%=art055_opt059%>"
	} else if (str == "<%=art060%>") {
		return "<%=art060_opt019%>"
	} else if (str == "<%=art070%>") {
		return "<%=art070_opt020%>"
	} else {
		return str;
	}
}

function typeOfAppDeprecated(str) {
	if (str == "<%=art010%>") {
		return "<spring:message code='approval.apptype.art010'/>";
	} else if (str == "<%=art020%>" || str == "<%=art021%>") {
		return "<spring:message code='approval.apptype.art020'/>";
	} else if (str == "<%=art030%>") {
		return "<spring:message code='approval.apptype.art030'/>";
	} else if (str == "<%=art031%>") {
		return "<spring:message code='approval.apptype.art031'/>";
	} else if (str == "<%=art032%>") {
		return "<spring:message code='approval.apptype.art032'/>";
	} else if (str == "<%=art033%>") {
		return "<spring:message code='approval.apptype.art033'/>";
	} else if (str == "<%=art034%>") {
		return "<spring:message code='approval.apptype.art034'/>";
	} else if (str == "<%=art035%>") {
		return "<spring:message code='approval.apptype.art035'/>";

// jth8172 2012 신결재 TF
	} else if (str == "<%=art130%>") {
		return "<spring:message code='approval.apptype.art130'/>";
	} else if (str == "<%=art131%>") {
		return "<spring:message code='approval.apptype.art131'/>";
	} else if (str == "<%=art132%>") {
		return "<spring:message code='approval.apptype.art132'/>";
	} else if (str == "<%=art133%>") {
		return "<spring:message code='approval.apptype.art133'/>";
	} else if (str == "<%=art134%>") {
		return "<spring:message code='approval.apptype.art134'/>";
	} else if (str == "<%=art135%>") {
		return "<spring:message code='approval.apptype.art135'/>";
		
	} else if (str == "<%=art040%>") {
		return "<spring:message code='approval.apptype.art040'/>";
	} else if (str == "<%=art041%>") {
		return "<spring:message code='approval.apptype.art041'/>";
	} else if (str == "<%=art042%>") {
		return "<spring:message code='approval.apptype.art042'/>";
	} else if (str == "<%=art043%>") {
		return "<spring:message code='approval.apptype.art043'/>";
	} else if (str == "<%=art044%>") {
		return "<spring:message code='approval.apptype.art044'/>";
	} else if (str == "<%=art045%>") {
		return "<spring:message code='approval.apptype.art045'/>";
	} else if (str == "<%=art046%>") {
		return "<spring:message code='approval.apptype.art046'/>";
	} else if (str == "<%=art047%>") {
		return "<spring:message code='approval.apptype.art047'/>";
	} else if (str == "<%=art050%>") {
		return "<spring:message code='approval.apptype.art050'/>";
	} else if (str == "<%=art051%>") {
		return "<spring:message code='approval.apptype.art051'/>";
	} else if (str == "<%=art052%>") {
		return "<spring:message code='approval.apptype.art052'/>";
	} else if (str == "<%=art053%>") {
		return "<spring:message code='approval.apptype.art053'/>";
	} else if (str == "<%=art054%>") {
		return "<spring:message code='approval.apptype.art054'/>";
	} else if (str == "<%=art055%>") { 
		return "<spring:message code='approval.apptype.art055'/>"; // jth8172 2012 신결재 TF
	} else if (str == "<%=art060%>") {
		return "<spring:message code='approval.apptype.art060'/>";
	} else if (str == "<%=art070%>") {
		return "<spring:message code='approval.apptype.art070'/>";
	} else {
		return str;
	}
}

function typeOfReadRange(str) {
	if (str == "<%=drs001%>") {
		return "<spring:message code='approval.form.readrange.drs001'/>";
	} else if (str == "<%=drs002%>") {
		return "<spring:message code='approval.form.readrange.drs002'/>";
	} else if (str == "<%=drs003%>") {
		return "<spring:message code='approval.form.readrange.drs003'/>";
	} else if (str == "<%=drs004%>") {
		return "<spring:message code='approval.form.readrange.drs004'/>";
	} else if (str == "<%=drs005%>") { // jth8172 2012 신결재 TF
		return "<spring:message code='approval.form.readrange.drs005'/>";
	} else {
		return str;
	}
}

function typeOfConserveType(str) {
	if (str == "<%=dry001%>") {
		return "<spring:message code='approval.form.conservetype.dry001'/>";
	} else if (str == "<%=dry002%>") {
		return "<spring:message code='approval.form.conservetype.dry002'/>";
	} else if (str == "<%=dry003%>") {
		return "<spring:message code='approval.form.conservetype.dry003'/>";
	} else if (str == "<%=dry004%>") {
		return "<spring:message code='approval.form.conservetype.dry004'/>";
	} else if (str == "<%=dry005%>") {
		return "<spring:message code='approval.form.conservetype.dry005'/>";
	} else if (str == "<%=dry006%>") {
		return "<spring:message code='approval.form.conservetype.dry006'/>";
	} else if (str == "<%=dry007%>") {
		return "<spring:message code='approval.form.conservetype.dry007'/>";
	} else if (str == "<%=dry008%>") {
		return "<spring:message code='approval.form.conservetype.dry008'/>";
	} else {
		return str;
	}
}

function typeOfWork(str) {
	if (str == "<%=wkt001%>") {
		return "<spring:message code='approval.form.worktype.wkt001'/>";
	} else if (str == "<%=wkt002%>") {
		return "<spring:message code='approval.form.worktype.wkt002'/>";
	} else {
		return str;
	}
}

function typeOfAppForm(str) {
	if (str == "<%=oac001%>") {
		return "<spring:message code='approval.form.oactype.oac001'/>";
	} else if (str == "<%=oac002%>") {
		return "<spring:message code='approval.form.oactype.oac002'/>";
	} else {
		return str;
	}
}

function typeOfBizCode(str) {
	if (str == "<%=obt001%>") {
		return "<spring:message code='approval.form.obttype.obt001'/>";
	} else if (str == "<%=obt002%>") {
		return "<spring:message code='approval.form.obttype.obt002'/>";
	} else if (str == "<%=obt003%>") {
		return "<spring:message code='approval.form.obttype.obt003'/>";
	} else if (str == "<%=obt004%>") {
		return "<spring:message code='approval.form.obttype.obt004'/>";
	} else {
		return str;
	}
}

function typeOfReqCode(str) {
	if (str == "<%=ort001%>") {
		return "<spring:message code='approval.form.orttype.ort001'/>";
	} else if (str == "<%=ort002%>") {
		return "<spring:message code='approval.form.orttype.ort002'/>";
	} else if (str == "<%=ort003%>") {
		return "<spring:message code='approval.form.orttype.ort003'/>";
	} else if (str == "<%=ort004%>") {
		return "<spring:message code='approval.form.orttype.ort004'/>";
	} else if (str == "<%=ort005%>") {
		return "<spring:message code='approval.form.orttype.ort005'/>";
	} else if (str == "<%=ort006%>") {
		return "<spring:message code='approval.form.orttype.ort006'/>";
	} else if (str == "<%=ort007%>") {
		return "<spring:message code='approval.form.orttype.ort007'/>";
	} else {
		return str;
	}
}

function typeOfRequestType(str) {
	if (str == "<%=rtt001%>") {
		return "<spring:message code='approval.form.rtttype.rtt001'/>";
	} else if (str == "<%=rtt002%>") {
		return "<spring:message code='approval.form.rtttype.rtt002'/>";
	} else if (str == "<%=rtt003%>") {
		return "<spring:message code='approval.form.rtttype.rtt003'/>";
	} else {
		return str;
	}
}

function typeOfRequestTime(str) {
	if (str == "<%=srt030%>") {
		return "<spring:message code='approval.form.srttype.srt030'/>";
	} else if (str == "<%=srt100%>") {
		return "<spring:message code='approval.form.srttype.srt100'/>";
	} else if (str == "<%=srt200%>") {
		return "<spring:message code='approval.form.srttype.srt200'/>";
	} else if (str == "<%=srt300%>") {
		return "<spring:message code='approval.form.srttype.srt300'/>";
	} else if (str == "<%=srt400%>") {
		return "<spring:message code='approval.form.srttype.srt400'/>";
	} else if (str == "<%=srt500%>") {
		return "<spring:message code='approval.form.srttype.srt500'/>";
	} else if (str == "<%=srt600%>") {
		return "<spring:message code='approval.form.srttype.srt600'/>";
	} else if (str == "<%=srt700%>") {
		return "<spring:message code='approval.form.srttype.srt700'/>";
	} else if (str == "<%=srt800%>") {
		return "<spring:message code='approval.form.srttype.srt800'/>";
	} else {
		return str;
	}
}

//20120511 본문내 의견표시 리스트 추출 kj.yang
function setOpinionList(appline) {
	var approvers = appline.split(String.fromCharCode(4));
	var approverslength = approvers.length;
	var totalOpinion = "";
	
	for (var pos = 0; pos < approverslength; pos++) {
		if (approvers[pos].indexOf(String.fromCharCode(2)) != -1) {
			var approver = approvers[pos].split(String.fromCharCode(2));
			var opinionChk = approver[19].indexOf(String.fromCharCode(15));
			
			if(opinionChk != -1) {	//본문내 의견표시
				var opinion = approver[19].substr(1);;
				
				if (approver[22] != "" && approver[22] != "9999-12-31 23:59:59") {
					if (totalOpinion != "") {
						totalOpinion += "\u0003";
					}

					if (approver[7] == "") {
						totalOpinion += approver[22] + " [" + ((approver[4] == "") ? "" : approver[4] + " ") + approver[3] + "]";
						if (approver[19] != "") {
							totalOpinion += "\u0003" + opinion.replace(/\r\n/g, "\u0003").replace(/\r/g, "\u0003").replace(/\n/g, "\u0003");							
							
						}
					} else {
						totalOpinion += approver[22] + " [" + HwpConst.Form.DecideFor + ((approver[9] == "") ? "" : approver[9] + " ") + approver[8] + "]";
						if (approver[19] != "") {
							totalOpinion += "\u0003" + opinion.replace(/\r\n/g, "\u0003").replace(/\r/g, "\u0003").replace(/\n/g, "\u0003");
						}
					}
				}
			}	
		}
	}

	return totalOpinion;

}
</script>