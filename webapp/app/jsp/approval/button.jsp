<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.approval.util.ApprovalUtil" %>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppLineVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.PubReaderVO" %>
<%@ page import="com.sds.acube.app.env.vo.FormVO" %>
<%@ page import="java.util.List" %>
<%
/** 
 *  Class Name  : button.jsp 
 *  Description : 버튼공통처리 
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
	String roleCode = (String) session.getAttribute("ROLE_CODES"); // 역할코드
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	boolean adminstratorFlag = (roleCode.indexOf(roleId10) == -1) ? false : true;

	// 함종류
	String lob000 = appCode.getProperty("LOB000", "LOB000", "LOB");	// 새기안 - InsertAppDoc
	String lob001 = appCode.getProperty("LOB001", "LOB001", "LOB");	// 임시저장함 - SelectTemporary
	String lob002 = appCode.getProperty("LOB002", "LOB002", "LOB");	// 연계기안함 - SelectTemporary
	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB");	// 결재대기함 - SelectAppDoc
	String lob004 = appCode.getProperty("LOB004", "LOB004", "LOB");	// 진행문서함 - SelectAppDoc
	String lob009 = appCode.getProperty("LOB009", "LOB009", "LOB");	// 기안문서함 - SelectAppDoc
	String lob010 = appCode.getProperty("LOB010", "LOB010", "LOB");	// 결재완료함 - SelectAppDoc
	String lob012 = appCode.getProperty("LOB012", "LOB012", "LOB");	// 공람문서함 - SelectAppDoc
	String lob013 = appCode.getProperty("LOB013", "LOB013", "LOB");	// 후열문서함 - SelectAppDoc
	String lob014 = appCode.getProperty("LOB014", "LOB014", "LOB");	// 검사부열람함 - SelectAppDoc
	String lob015 = appCode.getProperty("LOB015", "LOB015", "LOB");	// 임원문서함 - SelectAppDoc
	String lob024 = appCode.getProperty("LOB024", "LOB024", "LOB");	// 통보문서함 - SelectAppDoc
	String lob031 = appCode.getProperty("LOB031", "LOB031", "LOB");	// 공람게시 - DisplayAppDoc
	String lob099 = appCode.getProperty("LOB099", "LOB099", "LOB");	// 관리자전체목록 - DisplayAppDoc
	String lol001 = appCode.getProperty("LOL001", "LOL001", "LOL");	// 문서등록대장 - SelectAppDoc
	String lol003 = appCode.getProperty("LOL003", "LOL003", "LOL");	// 문서등록대장 - SelectAppDoc
	String lol005 = appCode.getProperty("LOL005", "LOL005", "LOL");	// 직인날인대장 - DisplayAppDoc

	String lobCode = CommonUtil.nullTrim((String) request.getAttribute("lobCode")); // 문서함코드
%>
<% if (lob000.equals(lobCode) || lob001.equals(lobCode) || lob002.equals(lobCode) || lol001.equals(lobCode)) { %>
<jsp:include page="/app/jsp/approval/button/button000.jsp" />
<%} else if (lob003.equals(lobCode)) { %>
<jsp:include page="/app/jsp/approval/button/button003.jsp" />
<%} else if (lob004.equals(lobCode)) { %>
<jsp:include page="/app/jsp/approval/button/button004.jsp" />
<%} else if (lob009.equals(lobCode) || lob010.equals(lobCode)) { %>
<jsp:include page="/app/jsp/approval/button/button009.jsp" />
<%} else if (lob012.equals(lobCode)) { %>
<jsp:include page="/app/jsp/approval/button/button012.jsp" />
<%} else if (lob013.equals(lobCode)) { %>
<jsp:include page="/app/jsp/approval/button/button013.jsp" />
<%} else if (lob024.equals(lobCode)) { %>
<jsp:include page="/app/jsp/approval/button/button024.jsp" />
<%} else if (lob014.equals(lobCode) || lob015.equals(lobCode)) { %>
<jsp:include page="/app/jsp/approval/button/button014.jsp" />
<%} else if (lob031.equals(lobCode)) { %>
<jsp:include page="/app/jsp/approval/button/button031.jsp" />
<%} else if (lob099.equals(lobCode) && adminstratorFlag) { %>
<jsp:include page="/app/jsp/approval/button/button099.jsp" />
<%} else if (lol005.equals(lobCode)) { %>
<jsp:include page="/app/jsp/approval/button/button005.jsp" />
<% } %>
