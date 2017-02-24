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
	String dateFormat = AppConfig.getProperty("date_format", "yyyy/MM/dd", "date");
	String dateFormat2 = AppConfig.getProperty("basic_format", "yyyyMMddHHmmss", "date");
	String currentDate = DateUtil.getCurrentDate(dateFormat2);
	String startDate = DateUtil.getCurrentDate(dateFormat);
	String startDateId = DateUtil.getCurrentDate("yyyyMMdd");
	String endDate = DateUtil.getCurrentDate(dateFormat);
	String endDateId = DateUtil.getCurrentDate("yyyyMMdd");
	String emptyReason = "";
	String startHour = "00";
	String endHour = "23";
	String strEmptyYn = "";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<%-- <link rel="stylesheet" href="<%=webUri%>/app/ref/css/common.css" type="text/css"> --%>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" flush="true" />
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<script type="text/javascript"> 
var selectBindId = 'ROOT';
var selectUnitType = '';
var boardId = 'BOARD_COMMON_SHARE';
$(document).ready(function(){
	init();
	bindTreeReload();
	//bullListReload();
});

function init() {
	var tempBindId = '${selectBindId}';
	if(tempBindId!=null&&tempBindId!=""&&tempBindId!='null'){
		selectBindId = tempBindId;
	}
	if('${boardId}'!=null&&'${boardId}'!=""&&'${boardId}'!='null'){
		boardId = '${boardId}';
	}
}

bindTreeReload = function() {
	var param = 'bindType=DOC_BOARD';
	
    var url = '<%=webUri%>/app/bind/bindListShare.do?'+param;
    $("#ms_sec_left").load(url, "");
}

bullListReload = function() {
	var param = 'boardId=' + boardId + '&selectBindId=' + selectBindId + '&docDisplayType=expirationDoc'
		+ '&isNotNormal=Y';
	var url = '<%=webUri%>/app/board/BullListShare.do?' + param;
	$("#ms_sec_right").load(url, "");
}

bullListPageReload = function(cPage) {
	var param = 'boardId=' + boardId + '&selectBindId=' + selectBindId + '&cPage=' + cPage
		+ '&docDisplayType=expirationDoc' + '&isNotNormal=Y';
	var url = '<%=webUri%>/app/board/BullListShare.do?' + param;
	$("#ms_sec_right").load(url, "");
}

// 콜백함수 정의
var selectBindCallback = function(bindId, bindOrder, bindDepth, parentId, isChildBind, unitType) {
	selectBindId = bindId;
	selectUnitType = unitType;
	bullListReload();
};

//게시등록
function reg() {
	var param = 'boardId=' + boardId + '&selectBindId=' + selectBindId + '&selectUnitType=' + selectUnitType;
	location.href = '<%=webUri%>/app/board/BullWrite.do?' + param;
} 

//게시삭제
function del() {
	bullDel();
} 


//게시물조회
function view(bullId , docVersion) {
	var param = 'bullId=' + bullId + '&docVersion=' + docVersion + '&selectBindId=' + selectBindId  + '&isNotNormal=Y' + '&docDisplayType=expirationDoc';
	location.href = '<%=webUri%>/app/board/BullViewShare.do?' + param;
}

//검색하기
function goBullSearch() {
	var searchWord = $('#searchWord').val();
	var searchType = $("#searchoption option:selected").val();
	/* var startDate = $('#startDate').val();
	var endDate = $('#endDate').val();
	var searchYn = $(':input:checkbox[name="searchYN"]:checked').val(); 
	if (searchYn == 'Y') {
		if (startDate > endDate) {
			alert("시작일자보다 종료일자가 커서 검색을 하실 수 없습니다.");
			return;
		}
	} */
	//alert($('#retentionPeriod').val());
	var param = 'boardId=' + boardId + '&selectBindId=' + selectBindId
		+ '&selectRetentionPeriod=' + $('#retentionPeriod').val() + '&docDisplayType=expirationDoc' + '&isNotNormal=Y'
		+ 'searchWord=' +  encodeURIComponent(searchWord)+'&searchType=' +  encodeURIComponent(searchType);
		//+ '&startDate=' + startDate+'&endDate=' + endDate+'&searchYn=' + searchYn ;
	var url = '<%=webUri%>/app/board/BullListShare.do?'+param;
	
	$("#ms_sec_right").load(url, "");
}

function searchSetValue(searchWord, display){
	if((searchWord!=null)&&(searchWord!=""))
		$('#searchWord').val(searchWord);
	if((display!=null)&&(display!=""))
		$('#searchWord').css("display",display);
}

</script>
</head>
<body "topmargin="0" marginwidth="0" marginheight="0">
	<div class="luxor-inner-body">
		<!-- 컨텐츠 단 -->
				<div class="title_warp">
					<div class="title_sub">
						<h3>보존년한 경과문서 </h3>
					</div>
					<div class="title_right btn"></div>
				</div>

				<!-- 검색 -->
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tablesearch">
					<tr>
						<%-- <th width="100">보존년한</th>
						<td width="460"><form:select id="retentionPeriod" name="retentionPeriod" path="retentionPeriod" items="${retentionPeriod}" /></td> --%>
						<%-- <th width="100">검색기간</th>
						<td align="left" width="355"><input type="checkbox" name="searchYN" value="Y"/>
							<nobr>
									<input type="text" name="startDate" id="startDate" value="<%=startDate%>" class="searchinput" readonly/>
									<img id="calendarBTN1" name="calendarBTN1"
								        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif"
								        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
								        onclick="cal.select(event, document.getElementById('startDateId'), document.getElementById('startDate'), 'calendarBTN1',
								        '<%= dateFormat %>');">
								    <input type="hidden" name="startDateId" id="startDateId" value="<%=startDateId%>" />
								    <input type="hidden" name="paramStartDate" id="paramStartDate" value="" />
							    
								<input type="text" name="endDate" id="endDate" value="<%=endDate%>" class="searchinput" readonly/>
									<img id="calendarBTN2" name="calendarBTN2"
								        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif"
								        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
								        onclick="cal.select(event, document.getElementById('endDateId'), document.getElementById('endDate'), 'calendarBTN1',
								        '<%= dateFormat %>');">
								    <input type="hidden" name="endDateId" id="endDateId" value="<%=endDateId%>" />
								    <input type="hidden" name="paramEndDate" id="paramEndDate" value="" />
							</nobr>
						</td> --%>
						<td width="460">
							<select name="searchoption" id="searchoption" class="searchinput" onchange="option()">
								<c:forEach items="${docSearchList}" var="docSearch">
									<option value='${docSearch.codeValue}'>${docSearch.codeName}</option>
								</c:forEach>
							</select><input type="text" class="input" name="searchWord" id="searchWord" maxlength="25" style="width: 130px;" onkeydown="if(event.keyCode==13){goBullSearch();}" ></td>
						<td width="70" rowspan="1" align="center"><a href="#" onclick="javascript:goBullSearch();"><img src="<%=webUri%>/app/ref/img/bt_search.gif" alt="검색하기" title="검색하기"/></a></td> 
					</tr>
				</table>
				<!-- 검색 //-->

			<div style="width: 100%">
				<div class="ms_sec_left" id="ms_sec_left">
				</div>
				<div class="ms_sec_right" id="ms_sec_right">
				</div>
			</div>
		<!-- 컨텐츠 단 //-->
	</div>
</body>
</html>