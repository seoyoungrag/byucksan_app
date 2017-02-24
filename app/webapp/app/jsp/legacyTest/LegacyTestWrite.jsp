<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.sds.acube.app.legacyTest.vo.LegacyTestVO" %>
<%@ page import="com.sds.acube.app.common.util.UtilRequest,com.sds.acube.app.common.util.DateUtil,java.util.List,com.sds.acube.app.common.vo.UserVO"
%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
	/** 
	 *  Class Name  : LegacyTestWrite.jsp 
	 *  Description : 출장신청 등록
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
	response.setHeader("pragma", "no-cache");
	String userName = (String) session.getAttribute("USER_NAME");
	List<UserVO> userVOs = (List<UserVO>) request
			.getAttribute("userVOs");
	String userId = (String) session.getAttribute("USER_ID");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>출장신청입력</title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
 
//현재 안건번호

function regForm(){

	if(document.myform.title.value !="" && document.myform.fromDate.value != "" && document.myform.toDate.value != ""
		&&  document.myform.objective.value != "" &&  document.myform.amount.value != "" ){
		
		$.post("<%=webUri%>/app/LegacyTest/LegacyTestReg.do", $("#myform").serialize(), function(data) {
			if ("1" == data.result) {
				alert("결재를 연계하였습니다");
				listForm();
			} else {
				alert("결재 연계중 오류가 발생하였습니다");
			}		
		},'json').error(function(data) {
			alert("결재 연계중 오류가 발생하였습니다");
		});				
	}
	else{
		alert("데이타를 모두 입력하세요.");
	}
}

function listForm(){
	location.href = "<%=webUri%>/app/LegacyTest/LegacyTestList.do";
}

</script>

</head>
<body leftmargin="20" topmargin="20" marginwidth="20" marginheight="20">
<acube:outerFrame>
<acube:titleBar>출장신청등록</acube:titleBar>
<form name="myform" id="myform" method="post">
	<input type="hidden" name="deptId" value="<%=(String) session.getAttribute("DEPT_ID")%>" />
	<input type="hidden" name="userId" value="<%=(String) session.getAttribute("USER_ID")%>" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">
				<acube:buttonGroup>
					<acube:button value="결재연계" onclick="regForm();" />
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
				<td class="tb_left_bg" colspan="3" ><input id="title" name="title" type="text" class="input" value="출장 신청서(<%=userName%>)" maxlength="256" style="width: 99%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)"/>
				</td>
			</tr>
			
			<tr bgcolor="#ffffff" >
				<td class="tb_tit" width="18%">기간 From</td>
				<td class="tb_left_bg" ><input id="fromDate" name="fromDate" type="text" class="input" maxlength="10" value="2012-06-01"/></td>
				<td class="tb_tit" width="18%">기간 To</td>
				<td class="tb_left_bg" ><input id="toDate" name="toDate" type="text" class="input" maxlength="10" value="2012-06-04"/></td>
 			</tr>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%">목적</td>
				<td class="tb_left_bg" colspan="3" ><input id="objective" name="objective" type="text" class="input" maxlength="256" style="width: 99%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)"/>
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%">비용</td>
				<td class="tb_left_bg" colspan="3" ><input id="amount" name="amount" type="text" class="input" maxlength="12" style="width: 100;ime-mode:inactive; text-align:right" onkeyup="checkInputMaxLength(this,'',12)"/>원
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%">결재라인</td>
				<td class="tb_left_bg" colspan="3" >
<%
	for (int nLoop = 0; nLoop < userVOs.size(); ++nLoop) {
%>
					<select name="approver<%=(nLoop + 1)%>" class="select"">
						<option value="approver">선택안함</option>
					<%
						for (int nLoop2 = userVOs.size() - 1; nLoop2 >= 0; --nLoop2) {
							String selected = "";
							if ((nLoop == 0) && (userId.equals(userVOs.get(nLoop2).getUserUID()))) {
								selected = "selected";
							}
					%>
						<option value="<%=userVOs.get(nLoop2).getUserID()%>" <%=selected%>><%=userVOs.get(nLoop2).getUserName()%></option>
					<%
						}
					%>
					</select>&nbsp;&nbsp;&nbsp;
					<select name="art<%=(nLoop + 1)%>" class="select"">
						<option value="ART000">선택안함</option>
					<%
						String selected = "";
						if (nLoop == 0) {
							selected = "selected";
						}
					%>
						<option value="ART010" <%=selected%>>기안</option>
						<option value="ART020">검토</option>
						<option value="ART030">협조</option>
						<option value="ART130">합의</option>
						<option value="ART040">감사</option>
						<option value="ART050">결재</option>
						<option value="ART053">1인전결</option>						
					</select><br/><br/>
<%
	}
%>
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
				<acube:button value="결재연계" onclick="regForm();" />
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