<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.BufferedOutputStream" %>
<%@ page import="com.sds.acube.app.login.vo.UserProfileVO,
				 com.sds.acube.app.common.vo.OrganizationVO,
				 com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 java.util.List"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : SelectLogoSymbol.jsp 
 *  Description : 로고/심볼 선택
 *  Modification Information 
 * 
 *   수 정 일 : 2012.05.16 
 *   수 정 자 : jth8172 
 *   수정내용 :  
 * 
 *  @author  신경훈
 *  @since 2012. 5. 16 
 *  @version 1.0 
 */ 
%>
<%
	String imagePath = webUri + "/app/ref/image";
	String buttonConfirm = messageSource.getMessage("approval.button.confirm" , null, langType);
	String buttonClose = messageSource.getMessage("approval.button.close" , null, langType);
	
	String logo = messageSource.getMessage("env.option.subtitle.opt328" , null, langType);
	String symbol = messageSource.getMessage("env.option.subtitle.opt329" , null, langType);
	String deptlist = messageSource.getMessage("approval.form.readrange.drs002", null, langType);  // jth8172 2012 신결재 TF
	String list = messageSource.getMessage("approval.enforce.title.list", null, langType);
	String nodata =  messageSource.getMessage("common.msg.nodata" , null, langType);
	
	// 소속부서 상위 기관목록을 가져옴  // jth8172 2012 신결재 TF
    List<OrganizationVO> upperOrgs = (List<OrganizationVO>) request.getAttribute("upperOrgs");
	if(upperOrgs == null) {
		upperOrgs = (List) new OrganizationVO();
	}
	int nOrgSize = upperOrgs.size();
	int nSize = 1;
	String registerDeptId =  CommonUtil.nullTrim(request.getParameter("registerDeptId"));	

	// 심볼,로고 등은 기관별로 관리한다.   // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	if ("".equals(institutionId)) {
		institutionId = userProfileVO.getCompId();
	}	
	if("".equals(registerDeptId)) {
	 	registerDeptId = institutionId;
	}	 	
 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.title.logoSymbol"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<style type="text/css">
	.range {display:inline-block;width:125px;height:25px; !important;}
</style>
<script type="text/javascript">


$(document).ready(function(){
	init();
});


//초기화
function init() {
	// 파일 ActiveX 초기화
	$("#selectLogo:eq(0)").click();  
	$("#selectSymbol:eq(0)").click();  
	$("#selDept").closest("td").css("background-color","#FFFFFF");
	$("a[deptId=<%=registerDeptId%>]").closest("td").css("background-color","#F2F2F4");  //CSS 변경시 일괄적용해야하는 부분.
	
}
//부서선택 
function selectDept(deptId) {
	var url = document.location.pathname + "?registerDeptId=" + deptId;
	top.location.href = url;
}


//목록클릭
function clickLogo(fileId, fileName, rowIndex) {

	if(fileId=="" || fileId ==null) {
		return;
	}	
	var url ="<%=webUri%>/app/env/selectOptionComImg.do?formEnvId=" + fileId; 
 
	$("#selectLogo").closest("td").css("background-color","#FFFFFF");
	$("#selectLogo:eq(" + rowIndex + ")").closest("td").css("background-color","#F2F2F4");  //CSS 변경시 일괄적용해야하는 부분.

	document.all.logoimg.src= url;
	$("#logoId").val(fileName);
	
}  


//목록클릭
function clickSymbol(fileId, fileName, rowIndex) {

	if(fileId=="" || fileId ==null) {
		return;
	}	
	var url ="<%=webUri%>/app/env/selectOptionComImg.do?formEnvId=" + fileId; 
 
	$("#selectSymbol").closest("td").css("background-color","#FFFFFF");
	$("#selectSymbol:eq(" + rowIndex + ")").closest("td").css("background-color","#F2F2F4");  //CSS 변경시 일괄적용해야하는 부분.

	document.all.symbolimg.src= url;
	$("#symbolId").val(fileName);
	  
}  


//이미지 선택 
function selectImage(url) {

	document.all.imageUrl.border="3";
}


//확인
function confirm() {
	if (opener != null && opener.setLogoSymbol) {		
		opener.setLogoSymbol($("#logoId").val(),$("#symbolId").val());
		window.close();
	}
 }

//닫기
function closeWin() {
	window.close();
}

function codeToName(cd) {
	if (cd=="FET001") {
		return document.write('<spring:message code="env.option.subtitle.opt328"/>');
	} else if (cd=="FET002") {
		return document.write('<spring:message code="env.option.subtitle.opt329"/>');
	} else if (cd=="FET003") {
		return document.write('<spring:message code="env.option.subtitle.opt323"/>');
	} else if (cd=="FET004") {
		return document.write('<spring:message code="env.option.subtitle.opt324"/>');
	}
}	
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame popup="true">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td>
		<span class="pop_title77"><spring:message code="env.option.title.logoSymbol"/></span><td>


		<tr>
			<td>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="tree_table">
          <tr><td colspan="5" height="10" class="communi_text"></td></tr><!-- 여백 -->
          <tr>
       	 	<td width="2%" class="communi_text"></td><!-- 여백 -->
	 		  <td width="44%"  valign="top" bgcolor="#ffffff">   
	 		    <table width="100%" height="100%"  class="tree_table"  border="0" bgcolor="#ffffff"><tr>
	 		  	<td width="1%" ></td>
	 		  	<td style="position:relative;">
					<%
						AcubeList acubeListDept = null;
						acubeListDept = new AcubeList(nSize, 1);
						acubeListDept.setColumnWidth("*");
						acubeListDept.setColumnAlign("center");   
						acubeListDept.setListWithScroll(271);
						AcubeListRow titleRowDept = acubeListDept.createTitleRow();
						
						titleRowDept.setData(0,deptlist + " " + list);
						titleRowDept.setAttribute(0,"style","border-top:none; border-left:none;");
						
						AcubeListRow rowDept = null;
					
						OrganizationVO upperOrg = null;
						for(int i = 0; i < nOrgSize; i++) {
							upperOrg = (OrganizationVO) upperOrgs.get(i);
							rowDept = acubeListDept.createRow();
							rowDept.setData(0, "<a href='#' id='selDept' deptId='"+ upperOrg.getOrgID() + "' onclick=\"selectDept('"+ upperOrg.getOrgID() +"'); \">"+upperOrg.getOrgName()+"</a>");  
							
						}
						if (nOrgSize==0){
							rowDept = acubeListDept.createDataNotFoundRow();
							rowDept.setData(0, nodata);
			    
			            }
						acubeListDept.setNavigationType("normal");
						acubeListDept.generatePageNavigator(false); 
						acubeListDept.setTotalCount(nSize);
						acubeListDept.setCurrentPage(1);
						acubeListDept.generate(out);
					%>
				</td>
				 <td width="1%"></td><!-- 여백 -->
				</tr>
				</table>
			</td>
       	 	<td width="1%" class="communi_text"></td><!-- 여백 -->
			<td width="44%"  bgcolor="#ffffff" valign="top" align="center">
	 		    <table width="100%" height="100%"  class="tree_table"  border="0" bgcolor="#ffffff"><tr>
	 		  	<td width="1%" ></td>
				<td>
				<!-- 로고 -->
				<table class="tree_table"  width="100"><tr><td valign="top" bgcolor="#ffffff" style="height:150px" >
				<acube:tableFrame class="table_grow">
					<tr><td class="ltb_head"><nobr><spring:message code="appcom.title.filetype.aft008"/></nobr></td></tr>
					<c:forEach var="vo3" items="${VOLogoList}" varStatus="vo">
					<tr bgcolor='#ffffff' onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#F2F2F4'">
						<td class="ltb_center"><nobr><a  href="#" id='selectLogo'  onclick="clickLogo('${vo3.formEnvId}','${vo3.remark}','${vo.index}');"> ${vo3.envName}</a></nobr></td>
					</tr>
					</c:forEach>
				</acube:tableFrame>
				</td></tr><tr><td>
				<acube:tableFrame class="tree_table">
					<tr>
						<td class="ltb_center">
						<img id="logoimg" name="logoimg" src="<%=webUri%>/app/env/selectOptionComImg.do?formEnvId=${vo3.formEnvId}" border="1" width="100" height="100" />
						<input type="hidden" name="logoId" id="logoId" value="" />
						</td>
					</tr>
				</acube:tableFrame>
				</td></tr></table>
				</td>
				<td>
				<!-- 심볼 -->
				<table class="tree_table"  width="100"><tr><td valign="top" bgcolor="#ffffff" style="height:150px" >
				<acube:tableFrame class="table_grow">
					<tr><td class="ltb_head"><nobr><spring:message code="appcom.title.filetype.aft009"/></nobr></td></tr>
					<c:forEach var="vo4" items="${VOSymbolList}" varStatus="vo">
					<tr bgcolor='#ffffff' onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#F2F2F4'">
						<td class="ltb_center"><nobr><a  href="#" id='selectSymbol'  onclick="clickSymbol('${vo4.formEnvId}','${vo4.remark}','${vo.index}');"> ${vo4.envName}</a></nobr></td>
					</tr>
					</c:forEach>
				</acube:tableFrame>
				</td></tr><tr><td>
				<acube:tableFrame class="tree_table">
					<tr>
						<td class="ltb_center">
						<img id="symbolimg" name="symbolimg" src="<%=webUri%>/app/env/selectOptionComImg.do?formEnvId=${vo4.formEnvId}" border="1" width="100" height="100" />
						<input type="hidden" name="symbolId" id="symbolId" value="" />
						</td>
					</tr>
				</acube:tableFrame>
				</td></tr></table>
				</td>
				</tr>
				</table>
			
			</td>
       	 	<td width="2%" class="communi_text"></td><!-- 여백 -->
			</tr>	
          	<tr><td colspan="5" height="10" class="communi_text"></td></tr><!-- 여백 -->
			</table>
 			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="confirm();" value="<%=buttonConfirm%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="closeWin();" value="<%=buttonClose%>" type="2" class="gr" />
				</acube:buttonGroup>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>

	</table>
</acube:outerFrame>

</body>
</html>