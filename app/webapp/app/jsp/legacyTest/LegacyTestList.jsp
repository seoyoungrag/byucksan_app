<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : LegacyTestList.jsp 
 *  Description : 연계목록조회
 *  Modification Information 
 * 
 *   수 정 일 : 2012.04.13 
 *   수 정 자 : 곽경종 
 *   수정내용 :  
 * 
 *  @author  정태환
 *  @since 2012. 3. 23 
 *  @version 1.0 
 */ 
%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.sds.acube.app.legacyTest.vo.LegacyTestVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 com.sds.acube.app.design.AcubePageNavigator,
				 com.sds.acube.app.common.util.UtilRequest,
				 com.sds.acube.app.common.util.DateUtil,
				 org.anyframe.pagination.Page,
				 java.util.Locale,
				 java.util.List"
%>
<%
	response.setHeader("pragma","no-cache");	
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userName = (String) session.getAttribute("USER_NAME"); // 사용자명

	List<LegacyTestVO> results = (List<LegacyTestVO>) request.getAttribute("LegacyTestVOpage");
	
	String curPageStr = request.getAttribute("curPage").toString();
	
	String cPageStr = request.getParameter("cPage");
	if(cPageStr != null && !cPageStr.equals(curPageStr)){
	    cPageStr = curPageStr;
	}	

	int CPAGE = 1;
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);

	if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);

	
	
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지

	int nSize = results.size();
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());
 
 	String regBtn = messageSource.getMessage("board.btn.reg", null, langType);  //등록버튼
 
 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>출장 신청 목록</title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />

<script type="text/javascript"> 
var UserInfoWin = null;

$(document).ready(function(){
	init();
});

//초기화 
function init() {

} 
//목록화면 페이지이동
function changePage(p) {
	
	$("#cPage").val(p);
 	$("#filterList").submit();
}   

// 연계등록
function reg() {
	location.href = "<%=webUri%>/app/LegacyTest/LegacyTestWrite.do";
} 

//연계조회/수정
function view(tripCd) {
	location.href = "<%=webUri%>/app/LegacyTest/LegacyTestModify.do?tripCd=" + tripCd;
}

//연계결과 내용 업데이트
function showResult() {
	
	$.post("<%=webUri%>/app/LegacyTest/LegacyUpdateAckTest.do", "compId=<%=compId%>", function(data) {
		if ("1" == data.result) {
			alert(data.count + "건의 결재 연계 결과를 업데이트 하였습니다.");
			changePage(1);
		} else {
			alert("결재 연계 결과 업데이트중 오류가 발생하였습니다.");
		}		
	},'json').error(function(data) {
		alert("결재 연계 결과 업데이트중 오류가 발생하였습니다.");
	});				
} 

</script>
</head>
<body "topmargin="0" marginwidth="0" marginheight="0">
<acube:outerFrame>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar>출장신청목록</acube:titleBar></td>
		</tr>
		<tr>
			<td align="right">
				<acube:buttonGroup>
				<acube:button id="btnReg" disabledid="" onclick="reg();" value="<%=regBtn%>" />
				<acube:space between="button" />
				<acube:button id="btnShow" disabledid="" onclick="showResult();" value="결재처리결과업데이트" />
				</acube:buttonGroup>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		
		<tr>
			<td>
			<table height="100%" width="100%" style='' border='0' cellspacing='0' cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<form name="formList" style="margin:0px">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->
					
<%
				AcubeList acubeList = null;
				acubeList = new AcubeList(sLine, 8);
				acubeList.setColumnWidth("250,*,80,100,100,150,100,170");
				acubeList.setColumnAlign("center,left,center,center,center,center,center,center");	 
				//acubeList.setListWithScroll(278);
				
				AcubeListRow titleRow = acubeList.createTitleRow();
	
				StringBuffer buff = new StringBuffer();
				titleRow.setData(0, "ID");
				titleRow.setData(1, "제목");
				titleRow.setData(2, "신청자ID");
				titleRow.setData(3, "기간From");
				titleRow.setData(4, "기간To");
				titleRow.setData(5, "목적");
				titleRow.setData(6, "비용");
				titleRow.setData(7, "처리상태");


				AcubeListRow row = null;
 				for(int i = 0; i < nSize; i++) {

					row = acubeList.createRow();
					   
					LegacyTestVO result = (LegacyTestVO) results.get(i);


					row.setData(0, CommonUtil.nullTrim(result.getTripCd()));
					buff = new StringBuffer();
 				    buff.append("<a href=\"javascript:view('" + CommonUtil.nullTrim(result.getTripCd() ) + "');\">");	
				    buff.append( CommonUtil.nullTrim(result.getTitle()));
 				    row.setData(1, buff.toString() );
					row.setData(2,  CommonUtil.nullTrim(result.getUserId()) );
					row.setData(3,  CommonUtil.nullTrim(result.getFromDate()));
					row.setData(4,  CommonUtil.nullTrim(result.getToDate()));
					row.setData(5,  CommonUtil.nullTrim(result.getObjective()));
					row.setData(6,  CommonUtil.nullTrim(result.getAmount()+""));
					
	
					String flag =  CommonUtil.nullTrim(result.getFlag());
					String strFlag = "신청대기";
					if("1".equals(flag)) {
						strFlag = "결재상신";
					} else if("2".equals(flag)) {
						strFlag = "결재진행";
					} else if("3".equals(flag)) {
						strFlag = "결재반려";
					} else if("5".equals(flag)) {
						strFlag = "결재완료";
					}

					row.setData(7, strFlag + "(" + flag + ")" );
 
			    } // for(~)

		        if(totalCount == 0){
		
					row = acubeList.createDataNotFoundRow();
					row.setData(0, messageSource.getMessage("board.msg.nodata" , null, langType));
		
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
					</form>
					</td>
					</tr>
			</table>
		</td>
	</tr>
</table>

</acube:outerFrame>
<form name="filterList" id="filterList" action="<%=webUri%>/app/LegacyTest/LegacyTestList.do" action="post" style="display:none">
<input name="cPage" id="cPage" TYPE="hidden" value="<%=cPageStr%>"/>
</form> 
</body>
</html>

