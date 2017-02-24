<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="org.apache.bcel.generic.DALOAD"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : BullWrite.jsp 
 *  Description : 게시물 작성
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
<%@ page import="com.sds.acube.app.board.vo.AppBoardVO"%>
<%@ page import="com.sds.acube.app.common.vo.AppCodeVO"%>

<%
	String cPage = (String) request.getAttribute("cPage"); // 리스트의 페이지
	AppBoardVO boardVO = (AppBoardVO) request.getAttribute("boardVO");
	response.setHeader("pragma","no-cache");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 ID
	String deptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 소속 부서 이름
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 ID
	String userName = (String) session.getAttribute("USER_NAME"); // 사용자명
	String roleCodes = (String)session.getAttribute("ROLE_CODES"); //권한정보
	String boardId = boardVO.getBoardId(); //보드ID
	String mode = (String) request.getAttribute("mode"); //관리자
	
	List<AppBoardVO> titlelist = (List<AppBoardVO>)request.getAttribute("titleList");

	//List<AppCodeVO> docSecurityList = (List<AppCodeVO>)request.getAttribute("docSecurityList");
	
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
	
	
	Date date = new Date();
	SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
	String today = simpleDate.format(date);
	String search = messageSource.getMessage("env.empty.button.Search" , null, langType);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code="board.list.write" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css" />
<%-- <link rel="stylesheet" href="<%=webUri%>/app/ref/css/common.css" type="text/css"> --%>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
var index = 0;
var callType = 1;

var bOrgAbbrName = false;
var selectBindId = null;
$(document).ready(function() {
	init_page();
	selectBindId = '${selectBindId}';
});

function init_page(){
	initializeFileManager();
	if('${bindVO!=null}'){
		printRetentionPeriod('${bindVO.retentionPeriod}');
		printBind('${bindVO.bindId}','${bindVO.bindName}');
	}
}

function printBind(bindId, bindName) {
	$("#bindingId").val(bindId);
	$("#bindingName").val(bindName);
	$("#bindingDisp").val(bindName);
}

function trim(str){
	   //정규 표현식을 사용하여 화이트스페이스를 빈문자로 전환
	   str = str.replace(/^\s*/,'').replace(/\s*$/, ''); 
	   return str; //변환한 스트링을 리턴.
	 }

function regForm(){
	var title = $("#bullTitle").val();
	if((trim(title) == "") || (title == null)){
		alert("제목을 입력해주세요");
		document.myform.bullTitle.focus();
		return;
	}
	var contents = $("#contents").val();
		if((trim(contents) == "") || (contents == null)){
			alert("내용을 입력해주세요");
			document.myform.contents.focus();
		return;
	}
		
	var contents = $("#bindingDisp").val();
	if((trim(contents) == "") || (contents == null)){
			alert("캐비닛/폴더가 선택되지 않았습니다.");
			document.myform.contents.focus();
		return;
	}

	$("#securityType").val(document.myform.docSecurity.value); //보안 체계
	
	document.getElementById("myform").submit();
}

function listForm(){
	var param = 'boardId=<%=boardId%>'+ '&selectBindId=' + selectBindId + '&cPage=<%=cPage%>';

	location.href = "<%=webUri%>/app/board/BullShare.do?" + param;
}

function getCallType() {
	return callType;
}

function setUserInfo(obj) {
	if (getCallType() == "e") {	
		setEmptyInfo(obj);
	} else if (getCallType() == "p") {
		setProxyInfo(obj);
	}
}

function setEmptyInfo(obj) {
	$('#docOwnerName').val(obj.userName);
	$('#docOwnerId').val(obj.userId);
}

function setCallType(type) {
	callType = type;
}

function popOrgTree(type) {
	setCallType(type);
	if (type == "p" && $('#emptyUserName').val() == "") {
		alert("<spring:message code='env.option.msg.validate.noempty'/>");
		return;
	}
	var url = "<%=webUri%>/app/common/OrgTree.do?type=1&treetype=3";
	if (type == "p") {
		url = "<%=webUri%>/app/common/OrgTree.do?type=1&treetype=3&deptId="+$('#emptyDeptId').val();
	}
	if (type == "p" && opt313 == "2") {
		alert("<spring:message code='env.empty.msg.validate.setEmpty'/>");
	} else {
		var winRecvLine = openWindow("winRecvLine", url, 600, 310);
	}
}

function selectBind() {
	var bindWin = openWindow("bindWin", "<%=webUri%>/app/bind/selectShare.do?bindType=DOC_BOARD", 430, 450);
}

function setBind(bind) {
	
	if (typeof(bind) == "object") {
		$("#bindingId").val(bind.bindingId);
		$("#bindingName").val(bind.bindingName);
		$("#bindingDisp").val(bind.bindingName);
		printRetentionPeriod(bind.retentionPeriod);
	}
}

function printRetentionPeriod(retentionPeriod) {
	if(retentionPeriod!='null'&&retentionPeriod!=null&&retentionPeriod!="") {
		var url = "<%=webUri%>/app/board/getRetentionPeriodAjax.do?retentionPeriod="+retentionPeriod;
		$.ajaxSetup({async:false});
		$.getJSON(url,function(data){
			$("#retentionPeriod").val(retentionPeriod);
			$("#retentionPeriodValue").val(data);
		});
	}
}

</script>

</head>
<body leftmargin="20" topmargin="20" marginwidth="20" marginheight="20">
		<form action="<%=webUri%>/app/board/BullReg.do" name="myform" id="myform" method="post">
			<input type="hidden" name="cPage" value="<%=cPage%>" />
			<input type="hidden" id="boardId" name="boardId" value="<%=boardId%>" />
			 <div class="title_warp">
					<div class="title_sub">
						<h3><spring:message code='board.list.reg' text="문서작성"/></h3>
					</div>
					<!-- 버튼양식 -->
					<div class="title_right btn">
					   <span class="main_btn" onclick="listForm()">취소</span>
					   <span class="main_btn" onclick="regForm()">저장</span>
					</div>
					<!-- 버튼양식 //-->
				</div>
				<!-- 검색 -->
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tablegray">
					<tr>
						<th width="115">작성자</th>
						<td width="193"><%=userName%></td>
						<th width="117">작성부서</th>
						<td width="239"><%=deptName%></td>
						<th width="114">소유자</th>
						<td width="306">
						<input type="text" name="docOwnerName" id="docOwnerName" style="width: 150px" value="<%=userName%>"/><span><a onclick="popOrgTree('e')"><%=search%></a></span></td>
						<input name="docOwnerId" id="docOwnerId" type="hidden" value="<%=userId%>"></input>
					</tr>
					<tr>
						<th>지시일</th>
						<td><%=today %></td>
						<th>보존기간</th>
						<td>
							<input type="hidden" name="retentionPeriod" id="retentionPeriod" value=""/>
							<input type="text" name="retentionPeriodValue" id="retentionPeriodValue" style="width: 99%" disabled/>
						</td>
						<th>보안체계</th>
						<td>
							<input type="hidden" id="securityType" name="securityType" />
							<select name="docSecurity" id="docSecurity">
							<c:forEach items="${docSecurityList}" var="docSecurity">
								<option value='${docSecurity.codeValue}'>${docSecurity.codeName}</option>
							</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>케비닛/폴더</th>
						<td colspan="5">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">	
								<tr>
									<td width="75%">
										<input type="text" name="bindingDisp" id="bindingDisp" style="width: 99%" disabled/>
										<input type="hidden" name="bindingName" id="bindingName" value=""/>
										<input type="hidden" name="bindingId" id="bindingId" value=""/>
									</td>
									<td width="25%" align="right">
										<div id="bindDiv">
											<acube:button onclick="selectBind();return(false);" value="찾기" type="4" class="gr" />
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<th><spring:message code='board.title.title' text="제목"/></th>
						<td colspan="5"><input type="text" name="bullTitle" id="bullTitle" maxlength="256" style="width: 99%; ime-mode: active;" onkeyup="checkInputMaxLength(this,'',512)" /></td>
					</tr>
					<tr>
						<th>키워드</th>
						<td colspan="5"><input type="text" name="docKiword" id="docKiword" style="width: 99%"/></td>
					</tr>
					<tr>
						<th><spring:message code='board.title.contents' text="내용"/></th>
						<td colSpan="6"><textarea id="contents" name="contents" rows="25" cols="8" style="width: 100%; ime-mode: active;"></textarea></td>
						<!-- <td colspan="6">web Editor 영역</td> -->
					</tr>
					<tr>
						<th>현재버전</th>
						<td>1.0
							<input type="hidden" name="docVersion" id="docVersion" value="1.0" />
						</td>
						<th>저장옵션</th>
						<td colspan="3"><select name="select" id="select">
								<option>현재 버전으로 저장(문서의 현버전에 저장합니다.)</option>
						</select> (1.0)으로 저장됩니다.</td>
					</tr>
					<tr>
						<th><spring:message code="approval.form.attach" /></th>
						<!-- 첨부파일 -->
						<td class="tb_left_bg" colspan="10">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="100%">
										<div id="divattach" style="background-color: #ffffff; border: 0px solid; height: 90px; width: 100%; overflow: auto;"></div>
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" border="0" width="30%">
											<tr>
												<td align="center" valign="top"><img src="<%=webUri%>/app/ref/img/bu_up.gif" style="cursor: pointer" onclick="javascript:moveUpAttach();return(false);">
												</td>
												<td width="5">&nbsp;</td>
												<td align="center" valign="top"><img src="<%=webUri%>/app/ref/img/bu_pp.gif" style="cursor: pointer" onclick="javascript:appendAttach();return(false);">
												</td>
											</tr>
											<tr>
												<td colspan="3" style="height: 5px;"></td>
											</tr>
											<tr>
												<td align="center" valign="bottom"><img src="<%=webUri%>/app/ref/img/bu_down.gif" style="cursor: pointer" onclick="javascript:moveDownAttach();return(false);">
												</td>
												<td></td>
												<td align="center" valign="bottom"><img src="<%=webUri%>/app/ref/img/bu_mm.gif" style="cursor: pointer" onclick="javascript:removeAttach();return(false);"></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<!-- 검색 //-->
			<acube:tableFrame class="table_grow" width="84%">
			</acube:tableFrame>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr height="30">
				</tr>
			</table>
			<div id="approvalitem1" name="approvalitem">
				<input id="attachFile" name="attachFile" type="hidden" value=""></input>
			</div>
		</form>
</body>
</html>