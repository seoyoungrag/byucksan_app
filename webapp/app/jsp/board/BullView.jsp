<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : BullView.jsp 
 *  Description : 게시물보기
 *  Modification Information 
 * sds
 *   수 정 일 : 2012.05.02 
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
<%@ page import="com.sds.acube.app.board.vo.AppBoardReplyVO"%>

<%@ page import="java.util.List,
				 com.sds.acube.app.appcom.vo.FileVO,
				 com.sds.acube.app.common.util.AppTransUtil,
				 com.sds.acube.app.common.util.CommonUtil,
				 com.sds.acube.app.common.util.EscapeUtil"
%>

<%
	String cPage = (String) request.getAttribute("cPage");
	
	response.setHeader("pragma","no-cache");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 ID
	String userName = (String) session.getAttribute("USER_NAME"); // 사용자명
	String loginId = (String) session.getAttribute("LOGIN_ID"); // 사용자 UID
	String readPerm = (String) request.getAttribute("readPerm");
	
	if(readPerm.equals("0")){
%>
		<script>alert('문서 조회권한이 없습니다.'); history.back(-1);</script>
<%
		return;
	}
	
	AppBoardVO boardVO = (AppBoardVO) request.getAttribute("boardVO");
	AppBoardReplyVO boardReplyVO = (AppBoardReplyVO) request.getAttribute("boardReplyVO");
	List<AppBoardReplyVO> boardReplyVOs =(List<AppBoardReplyVO>)request.getAttribute("boardReplyVOs");
	List<AppBoardVO> results =(List<AppBoardVO>)request.getAttribute("boardList");
	List<FileVO> fileVOs = (List<FileVO>)request.getAttribute("attachList");
	//List<AppCodeVO> docSecurityList = (List<AppCodeVO>)request.getAttribute("docSecurityList");
	String convertedTitle = boardVO.getBullTitle();
	convertedTitle = convertedTitle.replaceAll("\"","&quot;");
	boardVO.setBullTitle(convertedTitle);
/*  	String convertedContents = boardVO.getContents();
	convertedContents = convertedContents.replaceAll("\"","&quot;");
	boardVO.setContents(convertedContents); */
	int nSize = results.size();
	
	//버튼명
	String saveBtn = messageSource.getMessage("board.btn.save", null, langType);
	String delBtn = messageSource.getMessage("board.btn.del", null, langType); 
	
	String sysRoleCode = AppConfig.getProperty("role_appadmin", "011", "role");
	String roleCode = (String) session.getAttribute("ROLE_CODES");
	
	String contents = boardVO.getContents();
	contents = contents.replaceAll("\r","<br/>");
	boardVO.setContents(contents);

%>

<%
	
	String isActive = boardVO.getIsActive()!=null?boardVO.getIsActive():"N";
	String isManager = boardVO.getIsManager()!=null?boardVO.getIsManager():"N";
	String bindAuthority = boardVO.getBindAuthority()!=null?boardVO.getBindAuthority():"";
	String isOwner = boardVO.getIsOwner()!=null?boardVO.getIsOwner():"N";
	String bindSendType = boardVO.getBindSendType()!=null?boardVO.getBindSendType():"N";
	String isModified = (isActive.equals("Y")&&(isManager.equals("Y") || isOwner.equals("Y")
			|| bindAuthority.equals("") || bindAuthority.equals("CABAUTH004") || bindAuthority.equals("CABAUTH005")))?"Y":"N";
	
	String isDownload = (isActive.equals("Y")&&(isManager.equals("Y") || isOwner.equals("Y")
				|| bindAuthority.equals("CABAUTH002") || bindAuthority.equals("CABAUTH003")
				|| bindAuthority.equals("CABAUTH004") || bindAuthority.equals("CABAUTH005")))?"Y":"N";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="board.list.view" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css" />
<%-- <link rel="stylesheet" href="<%=webUri%>/app/ref/css/common.css" type="text/css"> --%>
<%-- <c:if test="${isDownload == 'Y'}" > --%>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<%-- </c:if> --%>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
<c:set var="isModified" value="<%=isModified%>"></c:set>				
<c:set var="isDownload" value="<%=isDownload%>"></c:set>
var UserInfoWin = null;
var selectBindId = null;

$(document).ready(

	
	function() { 
		if('${isDownload}' == 'Y'||'${isDownload2}' == 'Y') loadAtt();
		printRetentionPeriod('<%=boardVO.getRetentionPeriod()%>');
		selectBindId  = '${selectBindId}';
				
 	    $('#checkall').change(function() {
 			var f = false;
 			if ($('#checkall').is(':checked')) {
 				f = true;
 			} else {
 				f = false;
 			}
 			$('input:checkbox[name="attach_cname"]').each(function(idx) {
 				$(this).attr('checked', f);
 			});				
 		});	
			
	}
);

//목록화면 이동
function listForm(){

	var param = 'boardId=<%=boardVO.getBoardId()%>' + '&selectBindId=' + selectBindId + '&cPage=<%=cPage%>';
	if('${docDisplayType}'=='expirationDoc')
		location.href = "<%=webUri%>/app/board/BullExpirationDoc.do?"+param;//목록화면으로 이동
	else if('${docDisplayType}'=='retentionDoc')
		location.href = "<%=webUri%>/app/board/BullRetentionDoc.do?"+param;//목록화면으로 이동
	else
		location.href = "<%=webUri%>/app/board/BullShare.do?"+param;//목록화면으로 이동
	<%-- location.href = "<%=webUri%>/app/board/BullList.do?boardId=<%=boardVO.getBoardId()%>"; --%> //목록화면으로 이동
}

//첨부 파일 불러오기
function loadAtt(){
	//alert("<%=boardVO.getRegId()%>");
	//alert("<%=userId%>");
	initializeFileManager();
	loadAttach($("#attachFile").val(),false);
}

//게시물 수정관련
function modi(){

	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("name", "selectBindId");
	input.setAttribute("value", selectBindId);
	document.getElementById("modiForm").appendChild(input);
	document.getElementById("modiForm").submit(); //form 에 있는 값들을 수정페이지로 넘겨줍니다.
}
//삭제 권한관련
function delBtn(){
	regUser = "<%=boardVO.getRegId()%>"; //작성자ID
	loginUser = "<%=userId%>"; //사용자ID
	if(loginUser == regUser){
		if(confirm("<spring:message code='board.msg.confirm'/>")){
			//document.getElementById("delForm").submit(); //삭제페이지로 넘어가게됩니다.
			var param = 'bullId=<%=boardVO.getBullId()%>&boardId=<%=boardVO.getBoardId()%>' + '&selectBindId=' + selectBindId;
			location.href = "<%=webUri%>/app/board/BullDelete.do?"+param;
		}
	} 
}

//댓글 입력
function reBtn(){
document.getElementById("reply").submit();
}

//댓글 삭제
function delBt(replyId){
	document.getElementById("replyId").value = replyId;
	document.getElementById("redelForm").submit();
}
//사용자 팝업
function onFindUserInfo(strUserID) {
	
	if (strUserID == "" || strUserID == null) {
		alert("<spring:message code='list.list.msg.noSearchUser'/>");
		return;
	}

	var strUrl = "<%=webUri%>/app/common/userInfo.do?userId="+strUserID+"&compid=<%=compId%>";
 
	var height = "450";

	var top = (screen.availHeight - 560) / 2;
	var left = (screen.availWidth - 800) / 2;
	var option = "top="+top+",left="+left+",toolbar=no,resizable=no, status=no, width=600,height=470";

	if(UserInfoWin != null && UserInfoWin.closed == false) {
		UserInfoWin.close();
	}
	
	UserInfoWin = openWindow("UserInfoWin", strUrl , "500", height, "no", "post", "no");
}

//버전 변경
function version(){
	var version = $('#docVersions').val();
	var isModified = "<%=isModified%>";
	var isDownload = "<%=isDownload%>";
	
	if(isModified=="N" || isDownload=="N"){
		isModified = '${isModified2}';
		isDownload = '${isDownload2}';
	}
	var param = 'bullId=<%=boardVO.getBullId()%>&docVersion=' + version + '&selectBindId=' + selectBindId + '&isModified=' + isModified + '&isDownload=' + isDownload;
	location.href = "<%=webUri%>/app/board/BullViewShare.do?" + param; //이전이력조회
}

function printRetentionPeriod(retentionPeriod) {
	if(retentionPeriod!='null'&&retentionPeriod!=null&&retentionPeriod!="") {
		var url = "<%=webUri%>/app/board/getRetentionPeriodAjax.do?retentionPeriod="+retentionPeriod;
		$.ajaxSetup({async:false});
		$.getJSON(url,function(data){
			$("#retentionPeriodValue").val(data);
		});
	}
}
</script>
</head>
<body leftmargin='' topmargin='' marginwidth='' marginheight=''>
	<form name="modiForm" id="modiForm" action="<%=webUri%>/app/board/BullModify.do" method="post">
			<!-- 수정페이지로 이동 -->
			<input type="hidden" name="cPage" value="<%=cPage%>" />
			<input type="hidden" name="regId" value="<%=boardVO.getRegId()%>" />
			<input type="hidden" name="regName" value="<%=boardVO.getRegName()%>" />
			<input type="hidden" name="bullTitle" value="<%=boardVO.getBullTitle()%>" /> 
			<input type="hidden" name="contents" id="contents" value="" /> 
			<input type="hidden" name="bullId" value="<%=boardVO.getBullId()%>" /> 
			<input type="hidden" name="compId" value="<%=boardVO.getCompId()%>" /> 
			<input type="hidden" name="boardId" value="<%=boardVO.getBoardId()%>" /> 
			<input type="hidden" name="attfiles" value="<%=boardVO.getAttfiles()%>" />
			<input type="hidden" name="check" value="<%=boardVO.getOfficialflag()%>" />
			<input type="hidden" name="regDeptId" value="<%=boardVO.getRegDeptId()%>" />
			<input type="hidden" name="regDeptName" value="<%=boardVO.getRegDeptName()%>" />
			<input type="hidden" name="regDate" value="<%=boardVO.getRegDate()%>" />
			<input type="hidden" name="docOwnerId" value="<%=boardVO.getDocOwnerId()%>" />
			<input type="hidden" name="docOwnerName" value="<%=boardVO.getDocOwnerName()%>" />
			<input type="hidden" name="docVersion" id="docVersion" value="<%=boardVO.getDocVersion()%>" />
			<input type="hidden" name="docKiword" value="<%=boardVO.getDocKeyword()%>" />
			<input type="hidden" name="regNo" value="<%=boardVO.getRegNo()%>" />
			<input type="hidden" name="securityType" value="<%=boardVO.getSecurityType()%>" />
			<%String Info = "javascript:onFindUserInfo('" + CommonUtil.nullTrim(boardVO.getRegId()) +"');"; %>
			<!-- 등록자  팝업 -->
			<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAttach(fileVOs))%>"></input>
			<div class="title_warp">
				<div class="title_sub">
					<h3>문서내용 조회</h3>
				</div>
				<!-- 버튼양식 -->
				<div class="title_right btn">
				<%if(userId.toUpperCase().equals(boardVO.getDocOwnerId().toUpperCase()) || roleCode.indexOf(sysRoleCode) > -1){ %>	<!-- 관리자와 소유자는 문서를 수정 할 수 있다. -->
					<c:if test="${isModified == 'Y' && isNotNormal != 'Y'}" >
					   <span class="main_btn" onclick="modi()">수정</span>
					</c:if>
				<%}%>	
					   <span class="main_btn" onclick="listForm()">목록</span>
				</div>
				<!-- 버튼양식 //-->
			</div>
			<!-- 검색 -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablegray">
				<tr>
					<th width="115">작성자</th>
					<td width="193"><%=boardVO.getRegName()%></td>
					<th width="117">작성부서</th>
					<td width="239"><%=boardVO.getRegDeptName()%></td>
					<th width="114">소유자</th>
					<td width="306"><%=boardVO.getDocOwnerName()%></td>
				</tr>
				<tr>
					<th>등록일</th>
					<td><%=boardVO.getRegDate()%></td>
					<th>보존기간</th>
					<td>
						<input type="hidden" name="retentionPeriod" id="retentionPeriod" value="<%=boardVO.getRetentionPeriod()%>"/>
						<input type="text" name="retentionPeriodValue" id="retentionPeriodValue" style="width: 99%" disabled/>
					</td>
					<th>보안체계</th>
					<td>
						<c:forEach items="${docSecurityList}" var="docSecurity">
							<c:if test="${docSecurity.codeValue == boardVO.securityType}">
								${docSecurity.codeName}
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
						<th>케비닛/폴더</th>
						<td colspan="5">
							<input type="text" name="bindingDisp" id="bindingDisp" 
								value="<%=boardVO.getBindingName()%>" style="width: 99%" disabled/>
							<input type="hidden" name="bindingName" id="bindingName" value="<%=boardVO.getBindingName()%>"/>
							<input type="hidden" name="bindingId" id="bindingId" value="<%=boardVO.getBindingId()%>"/>
						</td>
					</tr>
				<tr>
					<th>제목</th>
					<td colspan="5"><%=boardVO.getBullTitle()%> <%boardVO.getOfficialflag(); %></td>
				</tr>
				<tr>
					<th>본문</th>
					<td colspan="6" id='tdContent'>
					<%=boardVO.getContents().trim()%>
					</td>
				</tr>
				<tr>
					<th>문서버전</th>
					<!-- boardVOv -->
						<td colspan="5"><select name="docVersions" id="docVersions" onchange="version()">
							<option>현재 버전은 : <%=boardVO.getDocVersion()%></option>
							<c:forEach items="${boardList}" begin="0" end="<%=nSize %>" var="boardId">
								<option value="${boardId.docVersion}">${boardId.docVersion}</option>
							</c:forEach>
						</select></td>
					</td>
				</tr>
				<c:choose>
				<c:when test="${isDownload == 'Y'|| isDownload2=='Y'}">
				<tr bgcolor="#ffffff">
					<th class="tb_tit"><spring:message code="approval.form.attach" /></th>
					<!-- 첨부파일 -->
					<td class="tb_left_bg" colspan="10" height="60">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
                         <th width="50%" style="text-align:left;"><input style="margin-left:7px;" type="checkbox" name="checkbox" id="checkall" /></th>
                       </tr>
                       <tr>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="80%" class="bordernone">
									<div id="divattach"
										style="background-color: #ffffff; border: 0px solid; height: 90px; width: 100%; overflow: auto;"></div>
								</td>
								<td>
									<table cellpadding="0" cellspacing="0" border="0" align="center" width="4%">
										<tr height="">
											<td style="background: #ffffff" align="right"></td>
											<td nowrap class="btn_adddown"><a href="#"
												onclick="saveAttach();return(false);" style="width: 60px; background-size: cover;">PC 저장</a></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						</tr>
						</table>
					</td>
				</tr>
				</c:when>
				<c:otherwise>
				<tr bgcolor="#ffffff">
					<th class="tb_tit"><spring:message code="approval.form.attach" /></th>
					<!-- 첨부파일 -->
					<td class="tb_left_bg" colspan="10" height="60">
						파일 조회 권한이 없습니다.
					</td>
				</tr>
				</c:otherwise>
				</c:choose>
			</table>
			<!-- 검색 //-->
		</form>
</body>
</html>