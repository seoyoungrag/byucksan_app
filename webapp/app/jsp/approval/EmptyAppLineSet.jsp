<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="java.util.HashMap" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : InsertOpinion.jsp 
 *  Description : 결재의견
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
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	
	String opt305 = appCode.getProperty("OPT305", "OPT305", "OPT"); // 반려문서결재 - 1 : 모든결재자 결재, 2 : 기 결재자 결재여부 선택
	opt305 = envOptionAPIService.selectOptionValue(compId, opt305);

	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)
	String art030 = appCode.getProperty("ART030", "ART030", "ART"); // 협조
	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art032 = appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
	String art033 = appCode.getProperty("ART033", "ART033", "ART"); // 협조(기안)
	String art034 = appCode.getProperty("ART034", "ART034", "ART"); // 협조(검토)
	String art035 = appCode.getProperty("ART035", "ART035", "ART"); // 협조(결재)

	String art130 = appCode.getProperty("ART130", "ART130", "ART"); // 합의
	String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의
	String art132 = appCode.getProperty("ART132", "ART132", "ART"); // 부서합의
	String art133 = appCode.getProperty("ART133", "ART133", "ART"); // 합의(기안)
	String art134 = appCode.getProperty("ART134", "ART134", "ART"); // 합의(검토)
	String art135 = appCode.getProperty("ART135", "ART135", "ART"); // 합의(결재)

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
	String art060 = appCode.getProperty("ART060", "ART060", "ART"); //선람
	String art070 = appCode.getProperty("ART070", "ART070", "ART"); //담당
	
	//	사업통제
	String art081 = appCode.getProperty("ART081", "ART081", "ART"); // 사업통제
	String art082 = appCode.getProperty("ART082", "ART082", "ART"); // 사업통제(기안)
	String art083 = appCode.getProperty("ART083", "ART083", "ART"); // 사업통제(검토)
	String art084 = appCode.getProperty("ART084", "ART084", "ART"); // 사업통제(결재)
	
	//	기획통제
	String art091 = appCode.getProperty("ART091", "ART091", "ART"); // 기획통제
	String art092 = appCode.getProperty("ART092", "ART092", "ART"); // 기획통제(기안)
	String art093 = appCode.getProperty("ART093", "ART093", "ART"); // 기획통제(검토)
	String art094 = appCode.getProperty("ART094", "ART094", "ART"); // 기획통제(결재)

	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대
	
	String opt317 = appCode.getProperty("OPT317","OPT317", "OPT");	// 알림설정
	HashMap<String, String> mapNoticeMode = envOptionAPIService.selectOptionTextMap(compId, opt317);

	String askType = CommonUtil.nullTrim(request.getParameter("askType"));
	String actType = CommonUtil.nullTrim(request.getParameter("actType"));
	String alarmYn = CommonUtil.nullTrim(request.getParameter("alarmYn"));
	
	boolean smsFlag = false;	//sms사용시 조건에 따라 smsflag true로 설정(mapNoticeMode.get("I3") : 알림설정, actType : 결재처리유형)
	
	// 버튼명
	String processBtn = "";
	if (apt002.equals(actType)) {
	    processBtn = messageSource.getMessage("approval.button.return", null, langType);
	} else if (art010.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.submit", null, langType);
	} else if (art020.equals(askType) || art021.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.approval", null, langType);
	} else if (art030.equals(askType) || art031.equals(askType) || art033.equals(askType) || art034.equals(askType) || art035.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.assistant", null, langType);	    
	} else if (apt051.equals(actType)) {
	    processBtn = messageSource.getMessage("approval.button.agree", null, langType);	    
	} else if (apt052.equals(actType)) {
	    processBtn = messageSource.getMessage("approval.button.disagree", null, langType);	    
	} else if (art130.equals(askType) || art131.equals(askType) || art133.equals(askType) || art134.equals(askType) || art135.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.agreesubmit", null, langType);	    
	} else if (art040.equals(askType) || art042.equals(askType) || art043.equals(askType) || art044.equals(askType) || art045.equals(askType) || art046.equals(askType) || art047.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.audit", null, langType);
	} else if (art050.equals(askType) || art051.equals(askType) || art052.equals(askType) || art053.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.approval", null, langType);
	} else if (art060.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.art60", null, langType);
	} else if (art070.equals(askType)) {
	    processBtn = messageSource.getMessage("approval.button.art70", null, langType);
	} else if (art082.equals(askType) || art083.equals(askType) || art084.equals(askType)) {
	    processBtn = messageSource.getMessage("env.code.name.ART081", null, langType);
	} else if (art092.equals(askType) || art093.equals(askType) || art094.equals(askType)) {
	    processBtn = messageSource.getMessage("env.code.name.ART091", null, langType);
	}
	
	String cancelBtn = messageSource.getMessage("approval.button.cancel", null, langType); 
	
	boolean opinionFlag = true;
	if (art060.equals(askType) || art070.equals(askType)) {	//비전자문서 선람, 담당인경우 비활성화
	    opinionFlag = false;
	}
	
	// 본문문서 타입 
	String formBodyType = (String) request.getParameter("formBodyType");
	if (formBodyType == null || "".equals(formBodyType)) {
		opinionFlag = false;		
	} else if ("html".equals(formBodyType)) {
		opinionFlag = false;		
	}
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code='approval.title.opinion'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
	function checkEmpty(){
		var substituteInfo = new Object();
		substituteInfo.substituteId = $("#substituteId").val();
		substituteInfo.substituteName = $("#substituteName").val();
		substituteInfo.substituteDeptName = $("#substituteDeptName").val();
		substituteInfo.substituteDeptId = $("#substituteDeptId").val();
		substituteInfo.substituteDisplayPosition = $("#substituteDisplayPosition").val();
		// alert(substituteInfo.substituteId+","+substituteInfo.substituteName+","+substituteInfo.substituteDeptName+","+substituteInfo.substituteDeptId+","+substituteInfo.substituteDisplayPosition);
		// alert($("#chkLastUser").attr("checked"));
		if($("#chkLastUser").attr("checked") == true){
			opener.setSubstituteLineSet(substituteInfo);
		}else{
			
		}
		opener.onWindowOpened(true);
		window.close();
	}
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar>직무대리자 선택</acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="table_grow">
					<tr bgcolor="#ffffff">		
					   <td width="25%" class="tb_tit">
					   	부재유형
					   </td>
						<td width="75%" class="tb_left_bg">
						 ${emptyInfo.emptyReason}
						</td>
					</tr>
					<tr bgcolor="#ffffff">		
					   <td width="25%" class="tb_tit">
					    시작일자
					   </td>
						<td width="75%" class="tb_left_bg">
						 ${emptyInfo.emptyStartDate}
						</td>
					</tr>
					<tr bgcolor="#ffffff">		
					   <td width="25%" class="tb_tit">
					   	종료일자
					   </td>
						<td width="75%" class="tb_left_bg">
						${emptyInfo.emptyEndDate}
						</td>
					</tr>
					<tr bgcolor="#ffffff">		
					   <td width="25%" class="tb_tit">
					   	직무대리자
					   </td>
						<td width="75%" class="tb_left_bg">
						${emptyInfo.substituteName}
						</td>
					</tr>
			</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<td>				
				<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0><TBODY>
				<TR>
				<TD class=basic_text align="center" style="font-weight:bold;" height=50 noWrap><input type="checkbox" id="chkLastUser" name="chkLastUser" /> 부재자가 최종결재자이면 체크 바람</TD>
				</TD></TR></TBODY></TABLE>
			</td>
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="checkEmpty();return(false);" value="확인" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="cancelEmpty();return(false);" value="<%=cancelBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
<input type="hidden" id="substituteId" name="substituteId" value="${emptyInfo.substituteId}"/>
<input type="hidden" id="substituteName" name="substituteName" value="${emptyInfo.substituteName}"/>
<input type="hidden" id="substituteDeptName" name="substituteDeptName" value="${emptyInfo.substituteDeptName}"/>
<input type="hidden" id="substituteDeptId" name="substituteDeptId" value="${emptyInfo.substituteDeptId}"/>
<input type="hidden" id="substituteDisplayPosition" name="substituteDisplayPosition" value="${emptyInfo.substituteDisplayPosition}"/>
</body>
</html>