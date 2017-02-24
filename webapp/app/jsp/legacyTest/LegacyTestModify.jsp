<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.sds.acube.app.legacyTest.vo.LegacyTestVO" %>
<%@ page import="com.sds.acube.app.common.util.UtilRequest,
				 com.sds.acube.app.common.util.DateUtil,
				 java.util.List"
%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : LegacyTestmodify.jsp 
 *  Description : 출장신청 수정
 *  Modification Information 
 * 
 *   수 정 일 : 
 *   수 정 자 :  
 *   수정내용 :  
 * 
 *  @author jth8172
 *  @since 2012. 5. 22 
 *  @version 1.0 
 */ 
%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	response.setHeader("pragma","no-cache");	
	LegacyTestVO results = (LegacyTestVO) request.getAttribute("LegacyTestVO");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>출장신청수정</title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
function regForm(){
	if("<%=results.getFlag()%>" != "0" && "<%=results.getFlag()%>" != "3") {  // 신청중이거나 반려된건만 진행
		alert("결재가 진행중인 건은 수정할 수 없습니다.");
		return;
	}		
	if(document.myform.title.value !="" && document.myform.fromDate.value != "" && document.myform.toDate.value != ""
		&&  document.myform.objective.value != "" &&  document.myform.amount.value != "" ){
		
		$.post("<%=webUri%>/app/LegacyTest/LegacyTestReg.do", $("#myform").serialize(), function(data) {
			if ("1" == data.result) {
				alert("결재가 상신되었습니다");
				listForm();
			} else {
				alert("결재상신중 오류가 발생하였습니다");
			}		
		},'json').error(function(data) {
			alert("결재상신중 오류가 발생하였습니다");
		});				
	}
	else{
		alert("데이타를 모두 입력하세요.");
	}
}

function delForm(){
	if(!confirm("삭제하시겠습니까?")) {
		return;
	}	
	if("<%=results.getFlag()%>" != "0" && "<%=results.getFlag()%>" != "3") {  // 신청중이거나 반려된건만 진행
		alert("결재가 진행중인 건은 삭제할 수 없습니다.");
		return;
	}		
		
	$.post("<%=webUri%>/app/LegacyTest/LegacyTestDel.do", $("#myform").serialize(), function(data) {
		if ("1" == data.result) {
			alert("삭제되었습니다");
			listForm();
		} else {
			alert("삭제중 오류가 발생하였습니다");
		}		
	},'json').error(function(data) {
		alert("삭제중 오류가 발생하였습니다");
	});				

}
 
function listForm(){
	location.href = "<%=webUri%>/app/LegacyTest/LegacyTestList.do";
}

</script>

</head>
<body leftmargin="20" topmargin="20" marginwidth="20" marginheight="20">
<acube:outerFrame>
<acube:titleBar>출장신청수정</acube:titleBar>
<form  name="myform" id="myform" method="post">
	<input type="hidden" name="deptId" value="<%=results.getDeptId() %>" />
	<input type="hidden" name="userId" value="<%=results.getUserId() %>" />
	<input type="hidden" name="tripCd" value="<%=results.getTripCd()%>" />
	<input type="hidden" name="flag" value="<%=results.getFlag()%>" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">
				<acube:buttonGroup>
					<acube:button value="결재상신" onclick="regForm();" />
					<acube:space between="button" />
					<acube:button value="삭제" onclick="delForm();" />
					<acube:space between="button" />
					<acube:button value="목록" onclick="listForm();"/>
				</acube:buttonGroup>
			</td>
			<tr>
			<td>
				<acube:space between="title_button" />
			<td>
			</tr>	
		</tr>	
	</table>
		<acube:tableFrame class="table_grow">
	
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%">제목</td>
				<td class="tb_left_bg" colspan="3" ><input id="title" name="title" type="text" class="input" value="<%=results.getTitle()%>" maxlength="256" style="width: 99%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)"/>
				</td>
			</tr>
			
			<tr bgcolor="#ffffff" >
				<td class="tb_tit" width="18%">기간 From</td>
				<td class="tb_left_bg" ><input id="fromDate" name="fromDate" type="text" class="input" value="<%=results.getFromDate().substring(0,10)%>" maxlength="10" value="2012-06-01"/></td>
				<td class="tb_tit" width="18%">기간 To</td>
				<td class="tb_left_bg" ><input id="toDate" name="toDate" type="text" class="input" value="<%=results.getToDate().substring(0,10)%>" maxlength="10" value="2012-06-04"/></td>
 			</tr>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%">목적</td>
				<td class="tb_left_bg" colspan="3" ><input id="objective" name="objective" type="text" class="input" value="<%=results.getObjective()%>" maxlength="256" style="width: 99%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)"/>
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%">비용</td>
				<td class="tb_left_bg" colspan="3" ><input id="amount" name="amount" type="text" class="input" value="<%=results.getAmount()%>" maxlength="12" style="width: 100;ime-mode:inactive; text-align:right" onkeyup="checkInputMaxLength(this,'',12)"/>원
				</td>
			</tr>

	</acube:tableFrame>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
				<acube:space between="title_button" />
		</td>
	</tr>	
	<tr>	
			<td align="right">
			<acube:buttonGroup>
				<acube:button value="결재상신" onclick="regForm();" />
				<acube:space between="button" />
				<acube:button value="삭제" onclick="delForm();" />
				<acube:space between="button" />
				<acube:button value="목록" onclick="listForm();"/>
			</acube:buttonGroup>
		</td>
	</tr>	
</table>
<div id="approvalitem1" name="approvalitem" style="dispaly:none">
	<input id="attachFile" name="attachFile" type="hidden" value=""></input>
</div>
</form>
</acube:outerFrame>
	
</body>
</html>