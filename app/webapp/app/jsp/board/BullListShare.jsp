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
		String sysRoleCode = AppConfig.getProperty("role_appadmin", "011", "role");
		boolean rolecheck = false;
		if(roleCodes.indexOf(sysRoleCode) > -1){
			rolecheck = true;
		}
			
		List<AppBoardVO> results = (List<AppBoardVO>) request.getAttribute("boardVOpage");
		SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
		AppBoardVO bindAuthInfo = (AppBoardVO) request.getAttribute("bindAuthInfo");
		String drySearch 	= StringUtil.null2str((String) request.getAttribute("drySearch"), ""); // drySearch by 2016-02-23
		
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
	String bullId = CommonUtil.nullTrim(UtilRequest.getString(request, "bullId"));
	String selectBindId 	= StringUtil.null2str((String) request.getAttribute("selectBindId"), ""); // drySearch by 2016-02-23
	String boardName = "";
	if (nSize >0) {
		boardName =  CommonUtil.nullTrim(((AppBoardVO)results.get(0)).getBoardName());
	}
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
		var param = 'boardId=' + boardId + '&selectBindId=' + selectBindId + '&selectUnitType=' + selectUnitType +'&cPage=<%=curPageStr%>';
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
		if(checked && /* (bindSendType!='DST004')&& */(isOwner=='Y' || isManager=='Y' || bindAuthority == 'CABAUTH005' || <%=rolecheck%>)){
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
			var param = 'bullId=' + bullId + '&boardId=<%=boardId%>' + '&selectBindId=' + selectBindId +'&cPage=<%=curPageStr%>';
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
	function bbsListSort(srt){
		$('#sortBy').val(srt);
		if ('${SearchVO.sortType}' == 'ASC') {
			$('#sortType').val('DESC');
		} else {
			$('#sortType').val('ASC');
		}
		url = "<%=webUri%>/app/board/BullListShare.do?"+$('#listForm').serialize();
		/* $('#listForm').
		.attr('action', url)
		.attr('method', 'post')
		.submit(); */
		$("#ms_sec_right").load(url, "");
	}

	//게시물조회
	function view(bullId , docVersion) {
		var param = 'bullId=' + bullId + '&docVersion=' + docVersion + '&selectBindId=' + selectBindId  + '&isNotNormal=N&cPage=<%=curPageStr%>' ;
		location.href = '<%=webUri%>/app/board/BullViewShare.do?' + param;
	}
</script>
</head>
<body "topmargin="0" marginwidth="0" marginheight="0">
<form id='listForm' name='listForm' method='post' action=''>
	<input type='hidden' id='boardId' name='boardId' value="<%=boardId%>"/>
	<input type='hidden' id='bullId' name='bullId' value="<%=bullId%>"/>
	<input type='hidden' id='selectBindId' name='selectBindId' value="<%=selectBindId%>"/>
	<input type='hidden' id='sortBy' name='sortBy' value=''/>
	<input type='hidden' id='sortType' name='sortType' value='${SearchVO.sortType}'/>
	<input type='hidden' id='startDate' name='startDate' value='${SearchVO.startDate}'/>
	<input type='hidden' id='endDate' name='endDate' value='${SearchVO.endDate}'/>
	<input type='hidden' id='searchYn' name='searchYn' value='${SearchVO.searchYn}'/>
	<input type='hidden' id='radioType' name='radioType' value='${SearchVO.radioType}'/>
	<input type='hidden' id='searchType' name='searchType' value='${SearchVO.searchType}'/>
	<input type='hidden' id='searchWord' name='searchWord' value='${SearchVO.searchWord}'/>
	<input type='hidden' id='docDisplayType' name='docDisplayType' value='${SearchVO.docDisplayType}'/>
	<input type='hidden' id='drySearch' name='drySearch' value='${drySearch}'/>
	<input type='hidden' id='cPage' name='cPage' value='${curPage}'/>
	<input type='hidden' id='isNotNormal' name='isNotNormal' value='${isNotNormal}'/>
</form>
	<!-- 테이블 -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablelist">
		<tr style="cursor:pointer">
		
			<%-- <c:if test="${isNotNormal!='Y'}"> --%>
			<th scope="col" width="39"><input type="checkbox" name="checkbox" id="checkbox" onclick="checkAll(0)" /></th>
			<%-- </c:if> --%>
			<th width="805" scope="col" onclick="javascript:bbsListSort('BULL_TITLE');">제목</th>
			<th width="100" scope="col" onclick="javascript:bbsListSort('DOC_OWNER_NAME');">소유자</th>
			<th width="100" scope="col" onclick="javascript:bbsListSort('REG_NAME');">작성자</th>
			<c:choose>
				<c:when test="${isNotNormal!='Y'}">
					<th width="100" scope="col" onclick="javascript:bbsListSort('REG_DATE');">등록일</th>
					<th width="80" scope="col" onclick="javascript:bbsListSort('HITNO');">조회</th>
				</c:when>
				<c:otherwise>
					<th width="100" scope="col" onclick="javascript:bbsListSort('EXPIRATION_DATE');">도래일</th>
					<th width="100" scope="col" onclick="javascript:bbsListSort('RETENTION_PERIOD');">보존년한</th>
				</c:otherwise>
			</c:choose>
		</tr>
		<c:forEach items="${boardVOpage}" begin="0" end="<%=nSize %>" var="boardId">
			<tr>
				<%-- <c:if test="${isNotNormal!='Y'}"> --%>
				<td width="50" class="tablelistleft" style="text-align: center;">
					<input type='checkbox'  name='bullId' id='bullId' 
						isOwner='${boardId.isOwner}' isManager='${boardId.isManager}' bindAuthority='${boardId.bindAuthority}'
						bindSendType='${boardId.bindSendType}' value='${boardId.bullId}'/>
				</td>
				<%-- </c:if> --%>
				<c:set var="userUid" value="<%=userId%>"/>
				<c:choose>
					<c:when test="${boardId.regId == userUid || boardId.docOwnerId == userUid 
						||boardId.isManager=='Y'||boardId.bindAuthority!=''}">
						<td class="tablelistleft"  style="text-overflow:ellipsis; -o-text-overflow:ellipsis; overflow:hidden; white-space:nowrap;width:805;"><a href="javascript:view('${boardId.bullId}','${boardId.docVersion}');">${boardId.bullTitle}</a>
							<input type='hidden'  name='docVersion' id='docVersion' value='${boardId.docVersion}'/>
						</td>
					</c:when>
					<c:otherwise>
						<td class="tablelistleft"   style="text-overflow:ellipsis; -o-text-overflow:ellipsis; overflow:hidden; white-space:nowrap;width:805;">${boardId.bullTitle}</td>
					</c:otherwise>
				</c:choose>
				<%-- <td width="100" class="tablelistcenter"><a href="javascript:onFindUserInfo('${boardId.docOwnerId}');">${boardId.docOwnerName}</a></td>
				<td width="100" class="tablelistcenter"><a href="javascript:onFindUserInfo('${boardId.regId}');">${boardId.regName}</a></td>
				 --%>
				<td width="100" class="tablelistcenter">${boardId.docOwnerName}</td>
				<td width="100" class="tablelistcenter">${boardId.regName}</td>
				<c:choose>
					<c:when test="${isNotNormal!='Y'}"> <!-- 도래문서함, 경과함일 경우 -->
						<td width="100" class="tablelistcenter">${boardId.regDate}</td>
						<td width="80" class="tablelistcenter">${boardId.hitno}</td>
					</c:when>
					<c:otherwise>
						<td width="100" class="tablelistcenter">${boardId.expirationDate}</td>
						<td width="100" class="tablelistcenter">${boardId.retentionPeriodName}</td>
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