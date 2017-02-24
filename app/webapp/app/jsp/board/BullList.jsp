<%@ page import="com.sds.acube.app.common.util.CommonUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService"%>
<%@ page import="com.sds.acube.app.list.vo.SearchVO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
  /** 
 *  Class Name  : BullList.jsp 
 *  Description : 게시목록조회
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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sds.acube.app.board.vo.AppBoardVO"%>

<%@ page
	import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 com.sds.acube.app.design.AcubePageNavigator,
				 com.sds.acube.app.common.util.UtilRequest,
				 com.sds.acube.app.common.util.DateUtil,
				 org.anyframe.pagination.Page,
				 org.anyframe.util.StringUtil,
				 java.util.Locale,
				 java.util.List"
%>
<%
		String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
		String userId = (String) session.getAttribute("USER_ID"); // 사용자 ID
		
		String roleCodes 	= StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), ""); // 사용자 rolecodes
		List<AppBoardVO> results = (List<AppBoardVO>) request.getAttribute("boardVOpage");
		SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
		AppBoardVO bindAuthInfo = (AppBoardVO) request.getAttribute("bindAuthInfo");
		
		String resultSearchType = CommonUtil.nullTrim(resultSearchVO.getSearchType());
		String resultSearchWord = CommonUtil.nullTrim(resultSearchVO.getSearchWord());
		String resultLobCode	= CommonUtil.nullTrim(resultSearchVO.getLobCode());
		String roleId00 = AppConfig.getProperty("role_admin", "", "role");
	    String roleId01 = AppConfig.getProperty("role_iam", "", "role");
	    String roleId10 = AppConfig.getProperty("role_appadmin", "", "role");

	    
	    String searchWordStyle = "inline";
	    if("searchSaveDate".equals(resultSearchType) || "searchBizDate".equals(resultSearchType) || "searchDraftDate".equals(resultSearchType) || "searchCompleteDate".equals(resultSearchType)){
	    	searchWordStyle = "none";
	    }
	//==============================================================================
	
	//==============================================================================
	// Page Navigation variables
	String curPageStr = request.getAttribute("curPage").toString();
	
	String cPageStr = request.getParameter("cPage");
	if(cPageStr != null && !cPageStr.equals(curPageStr)){
	    cPageStr = curPageStr;
	}	
	String sLineStr = request.getParameter("sline");
	int CPAGE = 1;
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int trSu = 1;
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) sLine = Integer.parseInt(sLineStr);
	
	
	//==============================================================================
	
	int curPage=CPAGE;	//현재페이지

	int nSize = results.size();
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());

	String boardId =  CommonUtil.nullTrim(UtilRequest.getString(request, "boardId"));
	String boardName = "";
	if (nSize >0) {
		boardName =  CommonUtil.nullTrim(((AppBoardVO)results.get(0)).getBoardName());
	}
	String bullId = CommonUtil.nullTrim(UtilRequest.getString(request, "bullId"));
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<%-- <link rel="stylesheet" href="<%=webUri%>/app/ref/css/common.css" type="text/css"> --%>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" flush="true" />
<%-- <%@ include file="/app/jsp/common/calendarPopup.jsp"%> --%>
<c:set var="bindAuthInfo" value="<%=bindAuthInfo%>"></c:set>
<script type="text/javascript"> 
var UserInfoWin = null;

$(document).ready(function(){
	init();
});
//초기화 
function init() {
	searchSetValue('<%=resultSearchWord%>', '<%=searchWordStyle%>');
} 
//목록화면 페이지이동
function changePage(p) {
	bullListPageReload(p);
}

function bullReg(){
	var bindAuthInfo = '${bindAuthInfo}';
	var bindAuthority = '${bindAuthInfo.bindAuthority}';
	if (bindAuthority == 'CABAUTH001' || bindAuthority == 'CABAUTH002' || bindAuthority == '') {
		alert("문서작성 권한이 없습니다.");
	} else {
		var param = 'boardId=' + boardId + '&selectBindId=' + selectBindId + '&selectUnitType=' + selectUnitType;
		location.href = '<%=webUri%>/app/board/BullWrite.do?' + param;
	}
}

//관리자 삭제
function bullDel(){

	var bullId = "";
	var count = 0;
	for(var i = 0 ; i < document.getElementsByName("bullId").length ; i++){
		var object = document.getElementsByName("bullId")[i];
		var checked = object.checked;
		
		var isManager = object.getAttribute('isManager'); //관리자 인 경우
		var bindAuthority = object.getAttribute('bindAuthority'); //캐비닛 문서 권한
		var isOwner = object.getAttribute('isOwner'); //삭제 권한
		var bindSendType = object.getAttribute('bindSendType'); //캐비닛 전송 타입
		if(checked && /* (bindSendType!='DST004')&& */(isOwner=='Y' || isManager=='Y' || bindAuthority == 'CABAUTH005')){
			count++;
			bullId = object.value+",";
		}
	}
	
	if(count==0){
		alert("선택 한 문서는 삭제할 권한이 없습니다.");
		return;
	}
	if(bullId ==""){
		alert("<spring:message code='board.msg.checkbull'/>");
	}
	else {
		var deleteCheck = confirm("<spring:message code='board.msg.confirm'/>");
		if (deleteCheck == true) {
			var param = 'bullId=' + bullId + '&boardId=<%=boardId%>' + '&selectBindId=' + selectBindId;
			location.href = "<%=webUri%>/app/board/adminBullDelete.do?"+param;
		} else {
			return false;
		}
	}
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
		var option = "top=" + top + ",left=" + left + ",toolbar=no,resizable=no, status=no, width=600,height=470";

		if (UserInfoWin != null && UserInfoWin.closed == false) {
			UserInfoWin.close();
		}

		UserInfoWin = openWindow("UserInfoWin", strUrl, "500", height, "no", "post", "no");
	}

	//체크박스 전부체크
	function checkAll() {
		if ($(":checkbox").length <= 0)
			return;
		if (chkAll == 0) {
			chkAll = 1;
			$(":checkbox").attr("checked", true);

		} else {
			chkAll = 0;
			$(":checkbox").attr("checked", false);
		}
	}
</script>
</head>
<body "topmargin="0" marginwidth="0" marginheight="0">
	<!-- 테이블 -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablelist">
		<tr>
		
			<%-- <c:if test="${isNotNormal!='Y'}"> --%>
			<th scope="col" width="39"><input type="checkbox" name="checkbox" id="checkbox" onclick="checkAll(0)" /></th>
			<%-- </c:if> --%>
			<th width="515" scope="col">제목</th>
			<th width="160" scope="col">소유자</th>
			<th width="171" scope="col">작성자</th>
			<c:choose>
				<c:when test="${isNotNormal!='Y'}">
					<th width="171" scope="col">등록일</th>
					<th width="171" scope="col">조회</th>
				</c:when>
				<c:otherwise>
					<th width="171" scope="col">도래일</th>
					<th width="171" scope="col">보존년한</th>
				</c:otherwise>
			</c:choose>
		</tr>
		<c:forEach items="${boardVOpage}" begin="0" end="<%=nSize %>" var="boardId">
			<tr>
				<%-- <c:if test="${isNotNormal!='Y'}"> --%>
				<td width="39" class="tablelistleft">
					<input type='checkbox'  name='bullId' id='bullId' 
						isOwner='${boardId.isOwner}' isManager='${boardId.isManager}' bindAuthority='${boardId.bindAuthority}'
						bindSendType='${boardId.bindSendType}' value='${boardId.bullId}'/>
				</td>
				<%-- </c:if> --%>
				<c:set var="userUid" value="<%=userId%>"/>
				<c:choose>
					<c:when test="${boardId.regId == userUid || boardId.docOwnerId == userUid 
						||boardId.isManager=='Y'||boardId.bindAuthority!=''}">
						<td class="tablelistleft"><a href="javascript:view('${boardId.bullId}','${boardId.docVersion}');">${boardId.bullTitle}</a>
							<input type='hidden'  name='docVersion' id='docVersion' value='${boardId.docVersion}'/>
						</td>
					</c:when>
					<c:otherwise>
						<td class="tablelistleft">${boardId.bullTitle}</td>
					</c:otherwise>
				</c:choose>
				<td width="160" class="tablelistcenter"><a href="javascript:onFindUserInfo('${boardId.docOwnerId}');">${boardId.docOwnerName}</a></td>
				<td width="171" class="tablelistcenter"><a href="javascript:onFindUserInfo('${boardId.regId}');">${boardId.regName}</a></td>
				<c:choose>
					<c:when test="${isNotNormal!='Y'}"> <!-- 도래문서함, 경과함일 경우 -->
						<td width="171" class="tablelistcenter">${boardId.regDate}</td>
						<td width="171" class="tablelistcenter">${boardId.hitno}</td>
					</c:when>
					<c:otherwise>
						<td width="171" class="tablelistcenter">${boardId.expirationDate}</td>
						<td width="171" class="tablelistcenter">${boardId.retentionPeriodName}</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
	</table>
	<!-- 테이블 // -->
	<!-- 페이지 -->
	<div class="pagediv">
		<table height="30px" width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="100%" valign="top" class="communi_text">
				<% 
					AcubeList acubeList = null;
					acubeList = new AcubeList(sLine, 7);
					acubeList.setNavigationType("normal");
					acubeList.generatePageNavigator(true); 
					acubeList.setTotalCount(totalCount);
					acubeList.setCurrentPage(curPage);
					acubeList.generate(out);
				%>
				</td>
			</tr>
		</table>
	</div>	
</body>
</html>