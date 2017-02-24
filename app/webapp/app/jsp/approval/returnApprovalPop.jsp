<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@page import="java.util.Enumeration"%>
<%@ page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.sds.acube.app.common.vo.DepartmentVO"%>
<%@page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.env.vo.EmptyInfoVO"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ page import="com.sds.acube.app.common.util.AppTransUtil" %>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppLineVO" %>

<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : returnApprovalPop.jsp
 *  Description : 반송승인자 지정 팝업창
 *  Modification Information 
 * 
 *   수 정 일 : 2011. 04. 21
 *   수 정 자 : 장진홍
 *   수정내용 : 2011.06.02 신경훈  - 결재경로그룹 탭 추가
 * 
 *  @author  장진홍
 *  @since 2011. 04. 21 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");

	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String compName		= (String) session.getAttribute("COMP_NAME");
	String deptId		= (String) session.getAttribute("DEPT_ID");
	String deptName		= (String) session.getAttribute("DEPT_NAME");
	
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);

	// variable naming rule: {tgw_app_code.code_value(ART010~ART070)}_{tgw_app_option.option_id(OPT001~OPT023)}. 2012.03.23. edited by bonggon.choi.
	String art010_opt001 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT001", "OPT001", "OPT"));	
	String art020_opt002 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT002", "OPT002", "OPT"));	
	String art030_opt003 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT003", "OPT003", "OPT"));	
	String art031_opt004 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT004", "OPT004", "OPT"));	
	String art032_opt005 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT005", "OPT005", "OPT"));	
	String art033_opt006 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT006", "OPT006", "OPT"));	
	String art034_opt007 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT007", "OPT007", "OPT"));	
	String art035_opt008 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT008", "OPT008", "OPT"));	
	String art040_opt009 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT009", "OPT009", "OPT"));	
	String art041_opt010 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT010", "OPT010", "OPT"));	
	String art042_opt011 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT011", "OPT011", "OPT"));	
	String art043_opt012 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT012", "OPT012", "OPT"));	
	String art044_opt013 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT013", "OPT013", "OPT"));	
	String art045_opt021 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT021", "OPT021", "OPT"));	
	String art046_opt022 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT022", "OPT022", "OPT"));	
	String art047_opt023 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT023", "OPT023", "OPT"));	
	String art050_opt014 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT014", "OPT014", "OPT"));	
	String art051_opt015 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT015", "OPT015", "OPT"));	
	String art052_opt016 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT016", "OPT016", "OPT"));	
	String art053_opt017 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT017", "OPT017", "OPT"));	
	String art054_opt018 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT018", "OPT018", "OPT"));	
	String art060_opt019 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT019", "OPT019", "OPT"));	
	String art070_opt020 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT020", "OPT020", "OPT"));		
	
	String adddeptBtn = messageSource.getMessage("approval.button.applines.adddept", null, langType);//부서추가
	String addlineBtn = messageSource.getMessage("approval.button.applines.addline", null, langType);//추가
	String modlineBtn = messageSource.getMessage("approval.button.applines.modline", null, langType);//변경
	String dellineBtn = messageSource.getMessage("approval.button.applines.delline", null, langType);//삭제
	String returnBtn = messageSource.getMessage("approval.enforce.button.return", null, langType);

	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); // 확인
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기

	List<DepartmentVO> results = (List<DepartmentVO>) request.getAttribute("results");	
	pageContext.setAttribute("userProfile", session.getAttribute("userProfile"));
	
	pageContext.setAttribute("DEPT_ID", session.getAttribute("DEPT_ID"));
	String PART_ID = (String) session.getAttribute("PART_ID");	
	PART_ID = (PART_ID == null? "" : PART_ID );
	pageContext.setAttribute("PART_ID", PART_ID);
	String tbColor = "#E3E3E3";
	
	//결재경로 설정 중일 때 는 Y 결재중일때는 N을 넘긴다.
	String groupYn = request.getParameter("groupYn");
	groupYn = (groupYn == null) ? "N" : groupYn;
	pageContext.setAttribute("groupYn", groupYn);
	
	String currentDate = DateUtil.getCurrentDate();
	String docId = (String) request.getParameter("docId");
	String receiverOrder = (String) request.getParameter("receiverOrder");
	String returnComment = (String) request.getAttribute("comment");
	
	String adminFlag = CommonUtil.nullTrim((String) request.getParameter("adminFlag"));
	List<EmptyInfoVO> emptyInfoList = new ArrayList<EmptyInfoVO>();
	if ("Y".equals(adminFlag)) {
		emptyInfoList = (List<EmptyInfoVO>) request.getAttribute("emptyInfos");
	}
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code="approval.title.applines" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript"><!--
//결제요청코드   
var ART060 = '<%=appCode.getProperty("ART060","ART060","APP") %>'; //선람       
var ART070 = '<%=appCode.getProperty("ART070","ART070","APP") %>'; //담당 

var APT001 = '<%=appCode.getProperty("APT001","APT001","APT") %>'; //승인
var APT003 = '<%=appCode.getProperty("APT003","APT003","APT") %>'; //대기

var g_Arts = new Array();

var isCtrl = false, isShift = false; //keyChecked

var gSaveObject = g_selector();
//결재라인 tr 객체를 담는 Valuable
var gSaveLineObject = g_selector();
var sColor = "#F2F2F4";
var tbColor = "<%=tbColor%>";
var docId = "<%=docId%>";
var currentDate = new Date(<%=currentDate.substring(0,4) %>,<%=currentDate.substring(5,7) %>-1,<%=currentDate.substring(8,10) %>,<%=currentDate.substring(11,13) %>,<%=currentDate.substring(14,16) %>,<%=currentDate.substring(17,19) %>);

//2015.07.28_lsk_사용자 정보 조회 팝업창 명칭
var Org_ViewUserInfo = null;

$(document).ready(function() { 
	makeArtNames();
	$("#org_tree").tree({
		rules : {
		valid_children : [ "root" ]
		},
		types : {
			// all node types inherit the "default" node type
			"default" : {
				deletable : false,
				renameable : false,
				draggable : false
			},
			"root" : {				
				icon: {
					valid_children : [ "comp" ],
					image : "<%=webUri%>/app/ref/js/jsTree/demo/root.gif"	
				}
			},
	
			"comp" : {
				icon: {
				valid_children : [ "dept" ],
				image : "<%=webUri%>/app/ref/js/jsTree/demo/comp.gif"
	
				}
			},
	
			"dept" : {
				icon: {
				valid_children : [ "part" ],
				image : "<%=webUri%>/app/ref/js/jsTree/demo/dept.gif"	
				}
			},
			"part" : {
				icon: {
				valid_children : [ "none" ],
				image : "<%=webUri%>/app/ref/js/jsTree/demo/part.gif"	
				}
			}			
		},
		callback : {
			//노드가 OPEN 될때
			onopen: function(node, tree_obj) {
				openNode(node, tree_obj);
			},
			//노드를 선택했을 때
			onselect : function(node, tree_obj){
				selectNode(node, tree_obj);
			},
			//노드 생성시 호출됨
			oncreate : function(node, parent, type, tree_obj, rollback) {
				//alert("aaaaaaa");
// 			},
// 			ondblclk : function(node, tree_obj){
// 				dbclickNode(node, tree_obj);
			}
		}		
	});
	
    //모든부서 선택할 수 없도록 disabled
	$('li a').each(function() {
		var orgName = $(this).text();
		var disabledHtml = "<ins style='vertical-align:top'></ins><font color=#BDBCBC>" + orgName + "</font>";
		
		$(this).addClass('disabled');
		$(this).attr('style', 'cursor:default;float:left;');
		$(this).html(disabledHtml);
	});
	
    //문서 열람가능한 부서 Setting 
	getDeptAuth('init');
	
	var errOrg = $('li[orgType="2"][parentId="'+$('li[orgType=0]').attr('orgId')+'"]');
	errOrg.remove(); //부서이면서 그룹을 부모로 하는 것 삭제
	
	$.tree.focused().select_branch('#${userProfile.deptId}');

    // added by jkkim 2015.06.18 본인의 상위부서 정보를 
    // 가져와 상위부서 오픈하여 본인부서 포커싱 처리.. start
	var parentId = '#${userProfile.orgParentId}';
	var parentIdType = '${userProfile.orgParentIdType}';
	
	<c:choose>
		<c:when test='${PART_ID != ""}'>
			$.tree.focused().select_branch('#${DEPT_ID}');
			$.tree.focused().open_branch('#${DEPT_ID}');
			if(parentId != null && parentIdType == 3){
				$.tree.focused().select_branch(parentId);
				$.tree.focused().open_branch(parentId);
			}
			$.tree.focused().select_branch('#${PART_ID}');
		</c:when>
		<c:otherwise>
			$.tree.focused().select_branch('#${DEPT_ID}');
		</c:otherwise>	
	</c:choose>


	//결재경로를 호출한다.	
	lineInit(setAppLine());
});
//$(document).ready 종료
//----------------Shift Key  Ctrl Key--------------------------------
$(document).bind('keyup', function(event){
	var keyCode = event.which;
	if(keyCode === 17) isCtrl=false;  
	if(keyCode === 16) isShift=false; 
	
});

$(document).bind('keydown', function(event){
	var keyCode = event.which;
	if(keyCode === 17) isCtrl=true;  
	if(keyCode === 16) isShift=true; 
	
});

//동명이인검색 추가
$('#userName').live('keydown', function(event){
	var keyCode = event.which;
	if(keyCode === 13){
		goSearch();
	}
});
//----------------Tree 이벤트--------------------------------

	//열람권한 부서 Array
	var deptArray = new Array();
	
	// 열람권한 부서 Setting
	function getDeptAuth(type) {
		
		var ownDeptId		= "<%= deptId %>";
		var ownDeptName 	= "<%= deptName %>";
		var deptData		= "";
		
		if(ownDeptName == "") {
			ownDeptName ="<%= compName %>";
		}
		
		//본인부서 선택가능하도록 Setting
		if(type == 'init') {
			deptData = {
				    "orgID"		: ownDeptId,
				    "orgName"	: ownDeptName,
				    "authType"  : "own"
			};
			
			deptArray.push(deptData);
	
			//하위부서 선택가능하도록 Setting
			var url = "<%=webUri%>/app/common/AllDepthOrgTreeAjax.do";
			url += ("?orgID=" + ownDeptId + "&treeType=1");
			
			var subDeptResults = "";
			
			$.ajaxSetup({async:false});
			$.getJSON(url,function(data){
				subDeptResults = data;
			});
			
			if(subDeptResults != null && subDeptResults != "") {
				for(var i = 0; i < subDeptResults.length ; i++) {
					
					deptData = {
						    "orgID"		: subDeptResults[i].orgID,
						    "orgName"	: subDeptResults[i].orgName,
						    "authType"  : "sub"
					};
					
					deptArray.push(deptData);
				}
			} 

			//문서등록대장 공유하도록 지정한 부서 선택가능하도록 Setting
			var url = "<%=webUri%>/app/env/ajaxSelectShareDept.do";
			url += "?targetDeptId=" + ownDeptId;
			
			var ShareDeptResults = "";
			
			$.ajaxSetup({async:false});
			$.getJSON(url,function(data){
				ShareDeptResults = data;
			});

			if(ShareDeptResults != null && ShareDeptResults != "") {
				for(var j = 0; j < ShareDeptResults.length ; j++) {
					
						deptData = {
							    "orgID"		: ShareDeptResults[j].shareDeptId,
							    "orgName"	: ShareDeptResults[j].shareDeptName,
							    "authType"  : "share"
						};
						
						deptArray.push(deptData);
				}
			}
		}

		if(deptArray != null && deptArray.length > 0) {
			for(var k = 0; k < deptArray.length; k++) {
				
				if(deptArray[k].authType == "own") {
	
					//문서를 열람할 수 있는 부서의 경우 선택가능하도록 enable
					$('#' + deptArray[k].orgID + '> a').each(function(i) {
						if($(this).attr('class') == 'disabled') {
							$(this).removeClass('disabled');
							$(this).attr('style', '');
							$(this).html("<ins style='vertical-align:top'></ins>" + deptArray[k].orgName);
						}
					});	
				}
			}
		}
	}

//노트가 열렸을때 발생하는 이벤트를 처리하는 함수
var openNodeId = "";
function openNode(node, tree_obj){
	var nSize = tree_obj.children(node).length; //하위노드의 수 
	var url = "<%=webUri%>/app/common/OrgTreeAjax.do";
	url += ("?orgID="+node.id+"&treeType=1");
	
	if(nSize < 1 && openNodeId !== node.id){
		var results = null;
		openNodeId = node.id;
		
		$.ajaxSetup({async:false});
		$.getJSON(url,function(data){
			results = data;
		});	
		drawSubTree(node, results);
		$('#'+node.id+' li').removeClass('leaf');
		$('#'+node.id+' li').addClass('closed');		
	}
	//$.tree.focused().select_branch("#"+node.id);
}

//하위 노드를 그리는 함수
function drawSubTree(node, nodeObj){
	var t = $.tree.focused();

	for(var i=0; i < nodeObj.length; i++) {
		try {
			var rel = "";
			
			if(nodeObj[i].orgType === 0){
				rel="root";
			}else if(nodeObj[i].orgType === 1){
				rel="comp";
			}else if(nodeObj[i].orgType === 2){
				rel="dept";
			}else{
				rel="part";
			}			
		t.create({attributes:{'id':nodeObj[i].orgID, 'parentId':nodeObj[i].orgParentID, 'depth':nodeObj[i].hasChild, 'orgType':nodeObj[i].orgType, 'orgName':nodeObj[i].orgName, 'rel':rel},
			data:{title:nodeObj[i].orgName}}, '#'+nodeObj[i].orgParentID);
		
			//	파트인 경우 선택 안됨	20150515_csh
<%-- 			if ( nodeObj[i].orgParentID != "<%= deptId %>" ) {
				$('#'+ nodeObj[i].orgID +' a').addClass('disabled');
				$('#'+ nodeObj[i].orgID +' a').attr('style', 'cursor:default;float:left;');
				$('#'+ nodeObj[i].orgID +' a').html("<ins style='vertical-align:top'></ins><font color=#BDBCBC>" + nodeObj[i].orgName + "</font>");
			} --%>
		} catch(e) {}
	}
}

//2015.07.28_lsk_사용자 정보 팝업 호출
function onFindUserInfo(strUserID){
	if (strUserID == "" || strUserID == null) {
		alert("<spring:message code='list.list.msg.noSearchUser'/>");
		return;
	}

	var strUrl = "";
	var height = "";
	strUrl = "<%=webUri%>/app/common/userInfo.do?userId="+strUserID+"&compid=<%=compId%>";
	height = "470";

	var top = (screen.availHeight - 560) / 2;
	var left = (screen.availWidth - 800) / 2;
	var option = "top="+top+",left="+left+",toolbar=no,resizable=no, status=no, width=600,height=470";

	if(Org_ViewUserInfo != null && Org_ViewUserInfo.closed == false) {
		Org_ViewUserInfo.close();
	}
	
	Org_ViewUserInfo = openWindow("Org_ViewUserInfoWin", strUrl , "500", height, "no", "post", "no");
}

//선택된 부서의 사용자 정보를 사용자 목록 에 저장한다. 
//리스트 표현 순서 등을 추후 추가 해야 함
function insertUser(results){
	var tbl = $('#tbUsers tbody');
	var strUsers = "";
	//tbl.html("");
	tbl.empty();
	if(results.length > 0){
		for(var i = 0; i < results.length; i++){
			var user = results[i];
			var bEmpty = bIsEmpty(user.isEmpty, user.emptyStartDate, user.emptyEndDate);

			var strEmpty = "N";

			if(bEmpty){
				strEmpty = "Y";
			}else{
				strEmpty = "N";
			}
			
			var row = "<tr id='"+user.userUID+"' deptID='"+ user.deptID+"' deptName='"+ user.deptName +"' userName='"+ user.userName;
			row += "' gradeCode='"+ user.gradeCode + "' gradeName='"+ user.gradeName +"' compID='"+ user.compID +"' compName='"+ user.compName;
			row += "' userID='"+ user.userID + "' positionCode='"+ user.positionCode +"' positionName='"+ user.displayPosition +"' titleName='"+ user.titleName;
			row += "' rowOrd='"+ i + "' empty='" + strEmpty;
			row += "' ondblclick='onListDblClick($(\"#"+user.userUID+"\"));' onmousemove='onListMouseMove();' onkeydown='onListKeyDown($(\"#"+user.userUID+"\"));' >";
			var userPosition = (user.displayPosition == ""?"&nbsp;":user.displayPosition); 
			// 2015.07.28_lsk_직위 클릭시 사용자 정보 팝업창 호출
			row += "<td class='ltb_center' style='cursor: pointer; border-bottom: "+tbColor+" 1px solid;border-left:1pt solid  "+tbColor+";' width='145' onclick=\"javascript:onFindUserInfo('"+user.userUID+"');return(false);\" >"+userPosition+"</td>";
			// 2015.07.28_lsk_이름 클릭시 결재자 입력
			row += "<td class='ltb_center' style='cursor: pointer; border-bottom: "+tbColor+" 1px solid;border-left:1pt solid  "+tbColor+";border-right:1pt solid  "+tbColor+";' title='"+user.emptyReason+"' onclick='onListClick($(\"#"+user.userUID+"\"));'>"+user.userName
			if(bEmpty){
				row += "&nbsp;<img src='<%=webUri%>/app/ref/image/user_absence.gif' align=absmiddle alt=''>";
			}
			row += "</td></tr>";
			tbl.append(row);
		}
	}else{
		var row = "<tr>";
		row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;'colsapn='2'><spring:message code='app.alert.msg.19' /></td>";
		row += "</tr>";
		tbl.append(row);
	}		
}

//부재여부를 가져온다.
function bIsEmpty(isEmpty, emptyStartDate, emptyEndDate){
	if(isEmpty){
		if(emptyStartDate == "") return false;
		if(emptyEndDate == "") return false;
		
		var sYy = Number(emptyStartDate.substring(0,4));
		var sMm = Number(emptyStartDate.substring(5,7));
		var sDd = Number(emptyStartDate.substring(8,10));
		var sHs = Number(emptyStartDate.substring(11,13));
		var sMi = Number(emptyStartDate.substring(14,16));
		var sSs = Number(emptyStartDate.substring(17,19));
		var sDate = new Date(sYy, sMm-1, sDd, sHs,sMi,sSs);
		var eYy = Number(emptyEndDate.substring(0,4));
		var eMm = Number(emptyEndDate.substring(5,7));
		var eDd = Number(emptyEndDate.substring(8,10));
		var eHs = Number(emptyEndDate.substring(11,13));
		var eMi = Number(emptyEndDate.substring(14,16));
		var eSs = Number(emptyEndDate.substring(17,19));
		var eDate = new Date(eYy, eMm-1, eDd, eHs,eMi,eSs);
		
		var sCnt = (currentDate - sDate);
		var eCnt = (eDate - currentDate);

		if(sCnt > 0 && eCnt > 0){
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}	
}

//부서 정보에 해당 조직정보를 추가 한다.
function addAttrOrg(result, node){
	if(result!==null){
		node.orgCode = result.orgCode; 	    //조직 코드
		node.isDeleted = result.isDeleted; //삭제여부
		node.isInspection = result.isInspection; //감사과 여부
		node.isODCD = result.isODCD;	//문서과 여부
		node.isProxyDocHandleDept = result.isProxyDocHandleDept //대리 문서 처리과 여부
		node.orgAbbrName = result.orgAbbrName; //조직 약어명
		node.isProcess = result.isProcess; //처리과 여부
	}
}
	
function makeArtNames(){
	g_Arts[ 0] = new Object();
	g_Arts[ 1] = new Object();


	g_Arts[ 0].Val = ART060;
	g_Arts[ 1].Val = ART070;


	g_Arts[ 0].Nm = '${options["OPT019"].optionValue}';
	g_Arts[ 1].Nm = '${options["OPT020"].optionValue}';	
}



//호출 페이지에서 넘져준 경로를 받아온다.
var g_lines = ""; 
function setAppLine(){
	
	if (opener != null && opener.getEnfLine) {
		g_lines = opener.getEnfLine();

		var arrLines = getEnfList(g_lines);

		if(arrLines.length == 0){

			arrLines = new Array();
			var enf = new Object();
			<c:if test='${options["OPT362"].useYn == "Y"}'>
			<c:choose>
			<c:when test='${options["OPT019"].useYn == "Y"}'>
			<c:if test='${DEPTHEAD != null}'>
			
			//선람사용여부 Y
				enf.processorId             = '${DEPTHEAD.userUID}';
				enf.processorName           = '${DEPTHEAD.userName}';
				enf.processorPos            = '${DEPTHEAD.displayPosition}';
				enf.processorDeptId         = '${DEPTHEAD.deptID}';
				enf.processorDeptName       = '${DEPTHEAD.deptName}';
				enf.representativeId       	= '';
				enf.representativeName     	= '';
				enf.representativePos      	= '';
				enf.representativeDeptId   	= '';
				enf.representativeDeptName 	= '';
				enf.askType                 = ART060;
				enf.procType                = '';
				enf.processDate             = '';
				enf.readDate                = '';
				enf.editLineYn              = '';
				enf.mobileYn                = '';
				enf.procOpinion             = '';
				enf.signFileName            = '';
				enf.lineHisId            	= '';
				enf.fileHisId            	= '';
				enf.absentReason            = '';
				enf.lineOrder               = '1';
				arrLines[0] = enf;
			</c:if>
			</c:when>
			<c:otherwise>
			<c:if test='${DEPTHEAD != null}'>
			//선람사용여부 Y
				enf.processorId             = '${DEPTHEAD.userUID}';
				enf.processorName           = '${DEPTHEAD.userName}';
				enf.processorPos            = '${DEPTHEAD.displayPosition}';
				enf.processorDeptId         = '${DEPTHEAD.deptID}';
				enf.processorDeptName       = '${DEPTHEAD.deptName}';
				enf.representativeId       	= '';
				enf.representativeName     	= '';
				enf.representativePos      	= '';
				enf.representativeDeptId   	= '';
				enf.representativeDeptName 	= '';
				enf.askType                 = ART070;
				enf.procType                = '';
				enf.processDate             = '';
				enf.readDate                = '';
				enf.editLineYn              = '';
				enf.mobileYn                = '';
				enf.procOpinion             = '';
				enf.signFileName            = '';
				enf.lineHisId            	= '';
				enf.fileHisId            	= '';
				enf.absentReason            = '';
				enf.lineOrder               = '1';
				arrLines[0] = enf;
			</c:if>			
			</c:otherwise>
			</c:choose>
			</c:if>
			
		}


		if(arrLines.length == 0){
			return false;
		}
		
		for(var i = 0; i < arrLines.length; i++){
			var lines = $('#tbApprovalLines tbody'); //결재경로
			var appLines = lines.children();
			
			var enfObj = arrLines[i];
			
			for(var idx = 0; idx < g_Arts.length; idx++){
				if(g_Arts[idx].Val === enfObj.askType){
					enfObj.opt_nm = g_Arts[idx].Nm;
					break;
				}
			}
			
		
			var row = approveMakeRow(enfObj);
			lines.append(row);
		}

		return true;
	}
	
	return false;
}
//----------------Tree 이벤트 끝--------------------------------

//로그인 사용자 정보 초기화 시킨다.
function lineInit(bUse){
	var usrRow = $('#${userProfile.userUid}');
	selectOneElement(usrRow);
	var radio = $('input:radio[name=options1]');
	if(radio.length > 0){
		radio[0].setAttribute("checked",true);
	}
	
}
//사원 선택시 발생하는 이벤트 
// parameter obj : 선택된 tr 의 jquery 객체이다.
function onListClick(obj){
		selectOneElement(obj);
		// 2015.07.28_lsk_원 클릭시 결재자 지정
		addApprovalLine();
}

//하나의 셀을 선택했을 수행하는 이벤트
//parameter obj : 선택된 tr 의 jquery 객체이다.
function selectOneElement(obj){
		
	if((!isCtrl && !isShift )|| (isCtrl && isShift)){
		$('document').empty();
		gSaveObject.restore();
		gSaveObject.add(obj, sColor);
	}else{
		if(isCtrl){
			$('document').empty();
			gSaveObject.add(obj, sColor);
		}

		if(isShift){
			
			var num1=0, num2=0, sNum=0, eNum = 0, ktype=0;

			num1 = new Number(gSaveObject.first().attr("rowOrd"));			
			num2 = new Number(obj.attr("rowOrd"));
			
			if(num1 >= num2){
				ktype = 1;
				num1 = new Number(gSaveObject.last().attr("rowOrd"));
			}

			sNum = (num2 > num1? num1 : num2);
			eNum = (num2 > num1? num2 : num1);
			
			var objs = $('#tbUsers tbody').children();
			gSaveObject.restore();
			for(var i = sNum; i<= eNum; i++){
				var nextObj = $("#"+objs[i].id);
				gSaveObject.add(nextObj, sColor);
			}	

			gSaveObject.setType(ktype);	

			if(ktype == 0){	
				gSaveObject.last().children()[0].focus();
			}else{
				gSaveObject.first().children()[0].focus();
			}
		}
	}
}
//마우스 이동시 발생하는 이벤트
function onListMouseMove(){
	document.selection.empty();
}

//키를 선택했을 때 발생하는 이벤트
function onListKeyDown(obj){
	var objTmp = null;
	var checkEvn = false;
		
	switch(event.keyCode){
		case 37:
		case 38:
		{
			objTmp = obj.prev();
			checkEvn = true;
			break;
		}
		case 39:
		case 40:
		{
			objTmp = obj.next();
			checkEvn = true;
			break;
		}
		case 13:
		{
//			obj.trigger('dblclick');
//			obj.children()[0].focus();
			break;
		}		
	}
	if(checkEvn){
		if(objTmp.children().length > 0){
			selectOneElement(objTmp);
			objTmp.children()[0].focus();
		}
	}
}

//사원 더블클릭시 발생하는 이벤트
function onListDblClick(obj){
	document.selection.empty();
}


function addApprovalLine(){

	var lines = $('#tbApprovalLines tbody'); //결재경로
	var appLines = lines.children();
	var appColl = gSaveObject.collection();

	if(appColl.length === 0){
		if(!bPop)
			alert('<spring:message code="approval.msg.applines.cknochoice" />');
		else
			popMsg = '<spring:message code="approval.msg.applines.cknochoice" />';
		return;
	}
	
	for(var i = 0; i < appColl.length; i++){
		var appItem = appColl[i];
		var enfObj = new Object();
		
		var ck_app = true;
		for(var j = 0; j < appLines.length; j++){
			var appLine = appLines[j];
			if(appLine.id == ("tbApprovalLines_" + appItem.attr("id"))){
				ck_app = false;
				if(!bPop)
					alert('<spring:message code="approval.msg.applines.ckchoice" />');
				else
					popMsg = '<spring:message code="approval.msg.applines.ckchoice" />';
				break;
			}
		}

		if(ck_app){
			if(appItem.attr('empty') === "Y"){
				var emptyMsg = '<spring:message code="approval.msg.applines.empty" />'; 
				emptyMsg = emptyMsg.replace(/%S/g,appItem.attr("userName"));
				if(!confirm(emptyMsg)){
					ck_app = false;
				}
			}
		}
		
		if(ck_app){
			var opt_code = "";
			var opt_nm = "";

			opt_code = "ART080";
			opt_nm = '<spring:message code="list.list.title.headerRetuenApprove" />'+"자";
			
			enfObj.lineOrder = "";             
			enfObj.processorId = "";            
			enfObj.processorName = "";          
			enfObj.processorPos  = "";          
			enfObj.processorDeptId = "";        
			enfObj.processorDeptName = "";      
			enfObj.representativeId = "";      
			enfObj.representativeName = "";    
			enfObj.representativePos = "";     
			enfObj.representativeDeptId = "";  
			enfObj.representativeDeptName = "";
			enfObj.askType = "";                
			enfObj.procType = "";               
			enfObj.processDate = "";            
			enfObj.readDate = "";               
			enfObj.editLineYn = "";             
			enfObj.mobileYn = "";               
			enfObj.procOpinion = "";            
			enfObj.signFileName = ""; 
			enfObj.lineHisId = ""; 
			enfObj.fileHisId = ""; 
			enfObj.absentReason = "";           
					
			enfObj.processorId = appItem.attr("id");
			enfObj.processorName = appItem.attr("userName");
   		 	enfObj.processorPos = appItem.attr("positionName"); //appItem.attr("positionCode");
   		 	enfObj.processorDeptId = appItem.attr("deptID");
   			enfObj.processorDeptName = appItem.attr("deptName");
   			enfObj.askType = opt_code;
   			enfObj.procType = "APT003";
   			enfObj.opt_nm = opt_nm;
   			//alert(opt_nm); 		
           	var row = approveMakeRow(enfObj);
           	
           lines.append(row);
		}
	}

	gSaveLineObject.restore();
	
	returnDoc();
}

//의견 및 결재암호 팝업
function returnDoc() {
	//의견 및 암호입력
	popOpinion("returnEnfOk","<%=returnBtn%>","Y"); 
}

//의견 및 결재암호 팝업
function popOpinion(returnFunction, btnName, opinionYn) {

	var width = 500;
	<% if ("1".equals(opt301)) { %> // 암호입력이면
	var height = 270;
<% } else { %>  
	var height = 230;
<% } %>  
    
	if(opinionYn=="N") {
<% if ("1".equals(opt301)) { %> // 암호입력이면
		height = 170;
<% } else { %>  
		height = 200;
<% } %>  
	}
	
	var top = (screen.availHeight - height) / 2;
	var left = (screen.availWidth - width) / 2;
	
	popupWin = window.open("", "popupWin", "width=" + width + ",height=" + height + ",top=" + top + ",left=" + left + ",scrollbars=no,status=yes,resizable=no"); 

	$("#returnFunction").val(returnFunction);
	$("#btnName").val(btnName);
	$("#opinionYn").val(opinionYn);
	$("#comment").val($("#comment", "#approvalitem").val());
	$("#frmDocInfo").attr("target", "popupWin");
	$("#frmDocInfo").attr("action", "<%=webUri%>/app/enforce/popupOpinion.do");
	$("#frmDocInfo").submit();
	setTimeout("popupfocus()",100);
}

function popupfocus() {
    popupWin.focus();
}

function approveMakeRow(enfObj){
	
	var tbId = "tbApprovalLines";

	var tbgcoler = "#FFFFFF";

	if(enfObj.procType !== ""){
		tbgcoler = "#EAEAEA"; 
	}

	var row = "<tr ";
	row += "id='"+ tbId + "_" + enfObj.processorId + "' lineOrder='" + enfObj.lineOrder + "' "; 
	row += "processorId='" + enfObj.processorId + "' processorName='" + enfObj.processorName + "' processorPos='" + enfObj.processorPos + "' ";
	row += "processorDeptId='" + enfObj.processorDeptId + "' processorDeptName='" +  enfObj.processorDeptName + "' representativeId='" + enfObj.representativeId + "' "; 
	row += "representativeName='" + enfObj.representativeName + "' representativePos='" + enfObj.representativePos + "' representativeDeptId='" + enfObj.representativeDeptId + "' "; 
	row += "representativeDeptName='" + enfObj.representativeDeptName + "' askType='" + enfObj.askType + "' procType='" + enfObj.procType + "' ";  
	row += "readDate='"+ enfObj.signFileName +"' processDate='"+ enfObj.processDate +"' ";
	row += "editLineYn='" + enfObj.editLineYn + "' mobileYn='" + enfObj.mobileYn + "' procOpinion='" + enfObj.procOpinion + "' signFileName='" + enfObj.signFileName + "' " ;
	row += "lineHisId='" + enfObj.lineHisId + "' fileHisId='" + enfObj.fileHisId + "' absentReason='" + enfObj.absentReason +"' " ;
	row += " onclick='onLineClick($(\"#"+ tbId + "_" + enfObj.processorId+"\"));' onmousemove='onLineMouseMove();' onkeydown='onLineKeyDown($(\"#"+ tbId + "_" + enfObj.processorId +"\"));' ";
	row += " style='background-color:"+tbgcoler+";' >";
	var processorDeptName = "&nbsp;"
	if(enfObj.processorDeptName !== "") processorDeptName = enfObj.processorDeptName;
	row += "<td width='123' class='ltb_center' style='border-bottom: "+tbColor+" 1px solid'>" + processorDeptName + "</td>";
	var processorPos = "&nbsp;"
	if(enfObj.processorPos !== "") processorPos = enfObj.processorPos;
	row += "<td width='123' class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-left:1pt solid "+tbColor+";border-right:1pt solid "+tbColor+"'>" + processorPos + "</td>";
	var processorName = "&nbsp;"
	if(enfObj.processorName !== "") processorName = enfObj.processorName;
	row += "<td width='164' class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-right:1pt solid "+tbColor+"'>" +processorName + "</td>"
	var opt_nm = "&nbsp;"
	if(enfObj.opt_nm !== "") opt_nm = enfObj.opt_nm;

	<%
	int emptysize = emptyInfoList.size();
	if ("Y".equals(adminFlag) && emptysize > 0) {
	    for (int loop = 0; loop < emptysize; loop++) {
			EmptyInfoVO emptyInfo = emptyInfoList.get(loop);
%>
	if (enfObj.processorId == "<%=emptyInfo.getUserId()%>") {
		row += "<td class='ltb_center' style='border-bottom:  "+tbColor+" 1px solid;border-right:1pt solid  "+tbColor+"; '>" + (opt_nm === "" ? "&nbsp;" : opt_nm);
		if (enfObj.representativeId == "") {
			row += "&nbsp;<span id='representative_" + tbId + "_" + enfObj.processorId + "' isset='0' onClick='onSetRepresentative(\"" + tbId + "_" + enfObj.processorId + "\", \"<%=emptyInfo.getSubstituteId()%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteName())%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteDisplayPosition())%>\", \"<%=emptyInfo.getSubstituteDeptId()%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteDeptName())%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getEmptyReason())%>\");return(false);' "
			+ " style='cursor:pointer' title='<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteDisplayPosition())%> <%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteName())%>'><b>[<spring:message code="approval.button.applines.on.representative"/>]</b></span>";
		} else {
			row += "&nbsp;<span id='representative_" + tbId + "_" + enfObj.processorId + "' isset='1' onClick='onSetRepresentative(\"" + tbId + "_" + enfObj.processorId + "\", \"<%=emptyInfo.getSubstituteId()%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteName())%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteDisplayPosition())%>\", \"<%=emptyInfo.getSubstituteDeptId()%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteDeptName())%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getEmptyReason())%>\");return(false);' "
			+ " style='cursor:pointer'><b>[" + enfObj.representativePos + " " + enfObj.representativeName + "]</b></span>";
		}
		row += "</td>";
	}else{
		row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-right:1pt solid "+tbColor+"'>" + opt_nm + "</td>";
	}
<%
	    }
	} else {
%>
	row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-right:1pt solid "+tbColor+"'>" + opt_nm + "</td>";
<% } %>
	row += "</tr>";
	
	return row;
}

//--------------------------결재선 처리------------------------------------------------------//
function onLineClick(obj){
	selectOneLineElement(obj);
	click_Appline(obj);
	// onDeleteLine();
}
function click_Appline(obj){
	$('input:radio[value="'+obj.attr('askType')+'"]').attr('checked', true);
}

//키를 선택했을 때 발생하는 이벤트
function onLineKeyDown(obj){
	var objTmp = null;
	var checkEvn = false;
		
	switch(event.keyCode){
		case 37:
		case 38:
		{
			objTmp = obj.prev();
			checkEvn = true;
			break;
		}
		case 39:
		case 40:
		{
			objTmp = obj.next();
			checkEvn = true;
			break;
		}		
	}
	if(checkEvn){
		if(objTmp.children().length > 0){
			selectOneLineElement(objTmp);
			objTmp.children()[0].focus();
		}
	}
}

//결재라인 선택시 처리하는 함수
function selectOneLineElement(obj){
//gSaveLineObject
	if((!isCtrl && !isShift )|| (isCtrl && isShift)){
		$('document').empty();
		gSaveLineObject.restore();
		gSaveLineObject.add(obj, sColor);
	}else{
		if(isCtrl){
			$('document').empty();
			gSaveLineObject.add(obj, sColor);
		}

		if(isShift){
			
			var num1=0, num2=0, sNum=0, eNum = 0, ktype=0;
			var objs = $('#tbApprovalLines tbody').children();
			
			for(var i = 0; i < objs.length; i++){
				if(objs[i].id === obj.attr("id")){
					num2 = i;
					break;
				}
			}

			for(var i = 0; i < objs.length; i++){
				if(objs[i].id === gSaveLineObject.first().attr("id")){
					num1 = i;
					break;
				}
			}
			
			if(num1 >= num2){
				ktype = 1;
				for(var i = 0; i < objs.length; i++){
					if(objs[i].id === gSaveLineObject.last().attr("id")){
						num1 = i;
						break;
					}
				}
			}

			sNum = (num2 > num1? num1 : num2);
			eNum = (num2 > num1? num2 : num1);
			
			gSaveLineObject.restore();
			
			for(var i = sNum; i<= eNum; i++){
				var nextObj = $("#"+objs[i].id);
				gSaveLineObject.add(nextObj, sColor);
			}
		}
	}
}

//마우스 이동시 발생하는 이벤트
function onLineMouseMove(){
	document.selection.empty();
}

//추가(+) 버튼 클릭시
function onAddLine(){
	addApprovalLine();
}

//삭제버튼(-)  클릭시
function onDeleteLine(){
	var lines = gSaveLineObject.collection();
	
	for(var i = 0; i < lines.length; i++){
		if(lines[i].attr("procType") !== ""){
			alert('<spring:message code="approval.msg.applines.after.nodelitem" />');
			return;
		}
		lines[i].remove();
	}
	
}


//위로올리기 버튼
function onMoveUp(){
	if(gSaveLineObject.collection().length === 1){
		var obj = gSaveLineObject.first();
		var prev = obj.prev();

		if(obj.attr('procType') !== "" || prev.attr('procType') !== "" ){
			return;
		}
		
		prev.before($('#'+obj.attr('id')));
	}
}

//아래로 내리기 버튼
function onMoveDown(){
	if(gSaveLineObject.collection().length === 1){
		var obj = gSaveLineObject.first();
		var prev = obj.next();

		if(obj.attr('procType') !== ""){
			return;
		}
		
		prev.after($('#'+obj.attr('id')));		
	}
}

function setOpinion(opinion){
	$("#comment", "#approvalitem").val(opinion);
}

//확인버튼 클릭
function sendOk(comment){
	var appline = "";
		
	appline = makeApprovalLine();
	
	$("#enfLines", "#approvalitem").val(appline);
	$("#comment", "#approvalitem").val(comment);
	
	$.post("<%=webUri%>/app/enforce/enfline/insertReturnEnfLine.do", $("#appDocForm").serialize(), function(data){
        if (data.result =='OK') {
			
        	alert("<spring:message code='approval.result.msg.approverfixok'/>");
        	
        	opener.location.reload();
        	window.close();
        }
        else{
        	alert("<spring:message code='env.form.msg.fail'/>");
        }
    },'json').error(function(data) {
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='env.form.msg.fail'/>");
		}
	});
		
}

function closePopup(){
	window.close();
}

//결재라인 생성하기
function makeApprovalLine(){
	var lines = $('#tbApprovalLines tbody').children();
	return getApprovalLines(lines);
}

//결재라인 작성하기
function getApprovalLines(lines){
	
	var max = lines.length;
	var strRtn = "";		
	for( var i = 0; i< max ; i++){
		var line = lines[i];
		line.setAttribute('lineOrder',i+1);

		strRtn += (line.getAttribute('processorId') 			+ String.fromCharCode(2));
		strRtn += (line.getAttribute('processorName') 			+ String.fromCharCode(2));
		strRtn += (line.getAttribute('processorPos') 			+ String.fromCharCode(2));
		strRtn += (line.getAttribute('processorDeptId') 		+ String.fromCharCode(2));
		strRtn += (line.getAttribute('processorDeptName') 		+ String.fromCharCode(2));
		strRtn += (line.getAttribute('representativeId') 		+ String.fromCharCode(2));
		strRtn += (line.getAttribute('representativeName') 		+ String.fromCharCode(2));
		strRtn += (line.getAttribute('representativePos') 		+ String.fromCharCode(2));
		strRtn += (line.getAttribute('representativeDeptId') 	+ String.fromCharCode(2));
		strRtn += (line.getAttribute('representativeDeptName') 	+ String.fromCharCode(2));
		strRtn += (line.getAttribute('askType') 				+ String.fromCharCode(2));
		strRtn += (line.getAttribute('procType') 				+ String.fromCharCode(2));
		strRtn += (line.getAttribute('processDate') 			+ String.fromCharCode(2));
		strRtn += (line.getAttribute('readDate') 				+ String.fromCharCode(2));
		strRtn += (line.getAttribute('editLineYn') 				+ String.fromCharCode(2));
		strRtn += (line.getAttribute('mobileYn') 				+ String.fromCharCode(2));
		strRtn += (line.getAttribute('procOpinion') 			+ String.fromCharCode(2));
		strRtn += (line.getAttribute('signFileName') 			+ String.fromCharCode(2));
		strRtn += (line.getAttribute('lineHisId') 				+ String.fromCharCode(2));
		strRtn += (line.getAttribute('fileHisId') 				+ String.fromCharCode(2));
		strRtn += (line.getAttribute('absentReason') 			+ String.fromCharCode(2));
		strRtn += (line.getAttribute('lineOrder') 				+ String.fromCharCode(4));
		
	}

	return strRtn;
}

function optClick(obj){
	var lines = gSaveLineObject.collection();

	for(var i = 0; i < lines.length; i++){
		var line = lines[i];
		var procType = line.attr('procType')
		if(APT001 === procType || APT003 === procType ){
			return;
		}
		
		line.attr('askType',obj.getAttribute('value'));
		line.children().last().text(obj.getAttribute('codeNm'));		
	}
	
}

//------------- 결재경로그룹 시작 ------------- 2011.06.02 신경훈 //
<%= com.sds.acube.app.design.AcubeTab.getScriptFunction(2) %>

var appLineList = null;

function checkProc() {
	var lines = $('#tbApprovalLines tbody').children();
	var groupYn = "Y";
	linesLength = lines.length;
	for (i=0; i<linesLength; i++) {
		var procType = $('#'+lines[i].id).attr('procType');
		if (procType != "") {
			groupYn = "N";
		}
	}
	return groupYn;
}

function callMethod(flag) {	
	if (flag == "divAppLineGroup") {
		var appLineGroup = $('#tblAppLineGroup tbody').children();
		if(appLineGroup.length > 0){
			onAppLineGroupClick($('#'+appLineGroup[0].id));
		}	
		/*
		if (checkProc() == "Y") {
			var appLineGroup = $('#tblAppLineGroup tbody').children();
			if(appLineGroup.length > 0){
				onAppLineGroupClick($('#'+appLineGroup[0].id));
			}		
		} else {
			alert("<spring:message code='env.group.msg.notice.proc'/>");
			flag= "divAppLine";
			selectTab(1);			
		}*/
		$('#div_dept_search').hide();
		$('#div_dept_search_no').show();
	}else{
		$('#div_dept_search').show();
		$('#div_dept_search_no').hide();
	}
	$('div[group="appLine"]').hide();
	$('#'+flag).show();
}

function codeToName(cd) {
	var ART010 = "<%=art010_opt001%>";
	var ART020 = "<%=art020_opt002%>";
	var ART021 = "<%=art020_opt002%>";
	var ART030 = "<%=art030_opt003%>";
	var ART031 = "<%=art031_opt004%>";
	var ART032 = "<%=art032_opt005%>";
	var ART033 = "<%=art033_opt006%>";
	var ART034 = "<%=art034_opt007%>";
	var ART035 = "<%=art035_opt008%>";
	var ART040 = "<%=art040_opt009%>";
	var ART041 = "<%=art041_opt010%>";
	var ART042 = "<%=art042_opt011%>";
	var ART043 = "<%=art043_opt012%>";
	var ART044 = "<%=art044_opt013%>";
	var ART050 = "<%=art050_opt014%>";
	var ART051 = "<%=art051_opt015%>";
	var ART052 = "<%=art052_opt016%>";
	var ART053 = "<%=art053_opt017%>";
	var ART054 = "<%=art054_opt018%>";
	var ART060 = "<%=art060_opt019%>";
	var ART070 = "<%=art070_opt020%>";	
	return eval(cd);
}

function onAppLineGroupClick(obj){
	selectOneElementGp(obj);//2011.09.03 ctrl, atl, shift 키관련
	getAppLine(obj);
}

function selectOneElementGp(obj){
	$('document').empty();
	gSaveObject.restore();
	gSaveObject.add(obj, sColor);
}

function onAppLineGroupDblClick() {
	appLineGroupAdd();
}

function appLineGroupAdd() {

	var tbAppLines = $('#tbApprovalLines tbody');
	linesLength = tbAppLines.children().length;
	appLineLength = appLineList.length-1;

	if (linesLength < 1) {
		for (var i=appLineLength; i>=0; i--) {	
			appLineObj = appLineList[i];
			appLineObj.processorId = appLineObj.approverId;
			appLineObj.processorName = appLineObj.approverName;
			appLineObj.processorPos = appLineObj.approverPos;
			appLineObj.processorDeptId = appLineObj.approverDeptId;
			appLineObj.processorDeptName = appLineObj.approverDeptName;
			appLineObj.askType = appLineObj.askType;
			appLineObj.opt_nm = codeToName(appLineObj.askType);
			appLineObj.representativeId = "";      
			appLineObj.representativeName = "";    
			appLineObj.representativePos = "";     
			appLineObj.representativeDeptId = "";  
			appLineObj.representativeDeptName = "";
			appLineObj.procType = "";
			appLineObj.processDate = "";
			appLineObj.readDate = "";               
			appLineObj.editLineYn = "";             
			appLineObj.mobileYn = "";               
			appLineObj.procOpinion = "";            
			appLineObj.signFileName = ""; 
			appLineObj.lineHisId = ""; 
			appLineObj.fileHisId = ""; 
			appLineObj.absentReason = "";
			
			var tmpTr = $('#tbApprovalLines tbody tr[id="'+appLineObj.approverId+'"]');

			/*var row = approveMakeRow("tbApprovalLines", appLineObj.approverId, "1", appLineObj.lineOrder
					, appLineObj.lineSubOrder, appLineObj.approverId, appLineObj.approverName, appLineObj.approverPos
					, appLineObj.approverDeptId, appLineObj.approverDeptName, "", "", "", "", "", appLineObj.askType
					, "", "", "", "", "", "", "", "", "", "", "", "", "", "", appLineObj.approverPos
					, appLineObj.approverDeptName, appLineObj.approverName, codeToName(appLineObj.askType));*/
			if(tmpTr.length == 0 && appLineObj.changeYn == "N"){	
				var row = approveMakeRow(appLineObj);
				tbAppLines.append(row);
			}
			if(appLineObj.changeYn == "Y"){
				alert("<spring:message code='env.group.msg.notice.recvline' />");
			}
		}
	} else {
		alert("<spring:message code='env.group.msg.notice.groupadd'/>");
	}
}

function getAppLine(obj){		
	var lineGroupId = obj.attr('id');
	var procUrl = "<%=webUri%>/app/env/listEnvAppLine.do?lineGroupId="+lineGroupId;
	var results = null;

	$.ajaxSetup({async:false});
	$.getJSON(procUrl, function(data){
		results = data;
	});
	
	appLineList = results;
	drawAppLine(results);			
}

function drawAppLine(appLineList) {
	var tbl = $('#tblAppLine');
	var txtAppLines = "";
	var applineLength = appLineList.length-1;		
	//var notice = "N";
	var bgcolor = "";
	tbl.empty();

	for (var i=applineLength; i>=0; i--) {				
		var appLine = appLineList[i];
		var askName = codeToName(appLine.askType);
		bgcolor = "ffffff";
		if (appLine.changeYn == "Y") {
			bgcolor = "ffefef";
			//notice = "Y";
		}
		var row = "<tr bgcolor='#"+bgcolor+"'>";
		row += "<td width='73' class='ltb_center'>"+appLine.approverDeptName+"</td>";
		row += "<td width='73' class='ltb_center'>"+appLine.approverPos+"</td>";
		row += "<td width='72' class='ltb_center'>"+appLine.approverName+"</td>";
		row += "<td width='*' class='ltb_center'>"+askName+"</td>";
		row += "</tr>";
		tbl.append(row);
	}

	/*
	if (notice == "Y") {
		alert("<spring:message code='env.group.msg.notice.appline' />");
	}
	*/
}

//------------- 결재경로그룹 끝 ------------- 2011.06.02 신경훈 //
//동명이인 검색
var sameUsers = "";
function goSameNameUser(){
	var userName = $('#userName');

	if(userName.val() === ""){
		alert("<spring:message code='list.list.msg.noSearchUser'/>");
		userName.focus();
		return;
	}
	
	var results = "";
	var url = "<%=webUri%>/app/common/sameNameUsers.do";
	$.ajaxSetup({async:false});
	$.getJSON(url,userName.serialize() ,function(data){
		results = data;
	});

	if(results !== ""){
		sameUsers = results
		if(results.length == 0) {
			alert("<spring:message code='app.alert.msg.56'/>");
			$('#userName').val("");
		}else if(results.length == 1){
			makeSameUser(results[0]);
		}else{
			var width = 400;
			var height = 350;
			var url = "<%=webUri%>/app/common/sameNameUsers.do?method=2";
			var appDoc = null;
			appDoc = openWindow("sameUserWin", url, width, height); 
		}
	}		
}

//동명이인 검색
function getSameUsers(){
	return sameUsers;
}
//동명이인 검색
var bPop = false;
var popMsg = "";
function setSameUsers(user){
	bPop = true;
	makeSameUser(user);
	bPop = false;
	return popMsg;
}

function makeSameUser(user){
	var tbl = $('#sameUser tbody');
	tbl.empty();
	
	var row = "<tr id='"+user.userUID+"' deptID='"+ user.deptID+"' deptName='"+ user.deptName +"' userName='"+ user.userName;
	row += "' gradeCode='"+ user.gradeCode + "' gradeName='"+ user.gradeName +"' compID='"+ user.compID +"' compName='"+ user.compName;
	row += "' userID='"+ user.userID + "' positionCode='"+ user.positionCode +"' positionName='"+ user.displayPosition +"' titleName='"+ user.titleName;
	row += "' >";
	var userPosition = (user.displayPosition == ""?"&nbsp;":user.displayPosition);
	row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;' width='88'>"+userPosition+"</td>";
	row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-left:1pt solid "+tbColor+";border-right:1pt solid "+tbColor+";'>"+user.userName+"</td></tr>";

	tbl.append(row);

	var tRow = $('#sameUser tbody tr[id='+user.userUID+']');

	gSaveObject.restore();
	gSaveObject.add(tRow, sColor);

	addApprovalLine();

	$('#userName').val("");
}

var searchDepts = "";

function goSearchDept(){
	var objDept = $('#searchDept');	
	objDept.val($('#userName').val());

	if(objDept.val() === ""){
		alert("<spring:message code='common.msg.deptnm.no' />"); //common.msg.deptnm.no
		return;
	}

	var url = "<%=webUri%>/app/common/searchDept.do?method=1"
	url += "&searchType=1";
	
	var results = "";
	$.ajaxSetup({async:false});
	$.getJSON(url,objDept.serialize() ,function(data){
		results = data;
	});

	if(results != ""){
		if(results.length === 1){
			selectOwnDept(results[0]);
			return;
		}else{
			searchDepts = results;
			goSearchList();
			return;
		}
	}else{
		alert("<spring:message code='app.alert.msg.56'/>");
		objDept.val("");
		$('#userName').val("")
		return;
	}
}

function getSearchDept(){
	return searchDepts;
}

var ownDeptId = "";
function selectOwnDept(objDept){
	var url = "<%=webUri%>/app/common/searchDept.do?method=2";
	url += "&compId=" + objDept.companyID;
	url += "&deptId=" + objDept.orgID;

	var results = "";
	$.ajaxSetup({async:false});
	$.getJSON(url,"",function(data){
		results = data;
	});

	if(results != ""){
		var rsCnt = results.length;
		for(var i = 0; i < rsCnt; i++){
		
			var node = $('#'+results[i].orgID);
			openNode(node[0], $.tree.focused());
			
			if(i === (rsCnt -1)){
				ownDeptId = '#' + results[i].orgID;
				$.tree.focused().scroll_into_view(ownDeptId);
				$.tree.focused().select_branch(ownDeptId);			
			}
		}
	}

	setTimeout(function(){
		if(ownDeptId != null){
			jQuery.tree.reference("#org_tree").scroll_into_view(ownDeptId);
		}
		
	},10);
}

function goSearchList(){
	var width = 400;
	var height = 350;
	var url = "<%=webUri%>/app/common/searchDept.do?method=3";
	url += "&searchType=1";
	
	var appDoc = null;
	appDoc = openWindow("searchDeptName", url, width, height); 
}

function goSearch(){
	var searchType = $('#searchType');
	if(searchType.val() === "1"){
		goSameNameUser();
	}else{
		goSearchDept();
	}
}

//대결지정
function onSetRepresentative(tblId, representativeId, representativeName, representativePos, representativeDeptId, representativeDeptName, absentReason) {
	var representative = $("#representative_" + tblId);
	var approver = $("#" + tblId);
	var isset = representative.attr("isset");
	if (isset == "0") {
		representative.attr("isset", "1");
		representative.html("<b>[" + representativePos + " " + representativeName + "]</b>");
		approver.attr("representativeId", representativeId);
		approver.attr("representativeName", representativeName);
		approver.attr("representativePos", representativePos);
		approver.attr("representativeDeptId", representativeDeptId);
		approver.attr("representativeDeptName", representativeDeptName);
		approver.attr("absentReason", absentReason);
	} else {
		representative.attr("isset", "0");
		representative.html("<b>[<spring:message code="approval.button.applines.on.representative"/>]</b>");
		approver.attr("representativeId", "");
		approver.attr("representativeName", "");
		approver.attr("representativePos", "");
		approver.attr("representativeDeptId", "");
		approver.attr("representativeDeptName", "");
		approver.attr("absentReason", "");
	}
}

//노드가 선택되었때 발생하는 이벤트 처리 함수
function selectNode(node, tree_obj){
	var nSize = tree_obj.children(node).length; //하위노드의 수 
	var url = "<%=webUri%>/app/common/UsersByOrgAjax.do";
	url += ("?orgID="+node.id+"&orgType="+node.getAttribute('orgType'));

	var bRet = $('#'+ node.id +' a').hasClass('disabled');
	
	// disabled 된 경우 선택 못하도록
	if(bRet) {
		$('#'+ node.id +' a').removeClass('clicked');
		return false;
	}
	
	var results = null;
	$.ajaxSetup({async:false});
	$.getJSON(url,function(data){
		results = data;
	});	
	insertUser(results);

	url = "<%=webUri%>/app/common/OrgbyDeptAjax.do";
	url += "?deptID="+node.id;
	$.getJSON(url,function(data){
		results = data;
	});
	addAttrOrg(results, node);
	gSaveObject.restore();
}

--></script>
<style>
	.tdSelect{
		background-color : #F9E5DF;
	}
</style>
</head> 
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
	<acube:outerFrame>
		<acube:titleBar><spring:message code="approval.title.applines" /></acube:titleBar>
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		
		<!-- 탭 시작 -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
	            <td>
	               <acube:tabGroup>
	                  <acube:tab index="1" onClick="JavaScript:selectTab(1);callMethod('divAppLine');" selected="true"><spring:message code="approval.title.applines" /></acube:tab>
	               </acube:tabGroup>
	            </td>
	         </tr>
         </table>
         <!-- 탭 끝 -->
	    <div id="div_dept_search">     
		<!-- 검색  -->	
		<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
		<tr>
			<td class="tb_tit" width="70">
				<select id="searchType" name="searchType">
					<option value="1"><spring:message code="env.search.user.title" /></option>
					<option value="2"><spring:message code="bind.obj.dept.name" /></option>
				</select>
			</td>				
			<td class="tb_tit" width="25%"><input id="userName" name="userName" type="text" style="width: 100%;ime-mode:active;FONT-SIZE:9pt; FONT-FAMILY:Gulim,Dotum,Arial; color:#777777; background-color:#FFFFFF; height:16px; padding-left:3px; padding-right:3px; border:1px #C3C2C2 solid;" />
			<input id="searchDept" name="searchDept" type="hidden" />
			</td>
			<td class="tb_tit" width="10%"><a href="javascript:goSearch();"><img id="imgRelDoc" src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
			<td class="tb_tit">&nbsp;</td>
		</tr>
		</acube:tableFrame>
		</div>
		<div id="div_dept_search_no" style="display:none">
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td class="tb_tit">&nbsp;</td>
			</tr>
			</acube:tableFrame>
		</div>
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		
		<!-- 결재경로 시작 -->
		<div id="divAppLine" group="appLine" style="display:block;">
			
		<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
		<tr>
			<td width="48%">
			 <!-- 트리용 테이블  -->
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td height="150" class="basic_box">
				<div id="org_tree" style="height:155px; overflow:auto; background-color : #FFFFFF;">
			    <ul>
	<!-- 		    	<li id="ROOT" class="open"><a href='#'><ins></ins>ROOT</a>  -->
	<!--		    	<ul> -->
			    	<%
			    	int nSize = results.size();
			    	for(int i = 0; i < nSize; i++){
			    		DepartmentVO result = results.get(i);
			    		String _class = "class='closed'";
			    		String rel = "";
			    		boolean hasChild = false;
			    		
			    		if(i < (nSize-1)){
			    		    //자식존재여부
				    		hasChild = result.getOrgID().equals(results.get(i+1).getOrgParentID());	    		
						}
			    		
			    		if(result.getOrgType()== 0 ) {
							_class = "class='open'";
							rel = " rel='root' ";
						}else if(result.getOrgType() == 1){
						    rel = " rel='comp' ";
						}else if(result.getOrgType() == 2){
						    rel = " rel='dept' ";
						}else{
						    rel = " rel='part' ";
						}
			    		
			    		out.println("<li id='"+result.getOrgID()+"' parentId='"+result.getOrgParentID()+"' orgName='"+result.getOrgName()+"' orgType='"+result.getOrgType()+"' depth='"+ result.getHasChild() +"' "+_class+rel+"><a href='#'><ins style='vertical-align:top'></ins>"+result.getOrgName()+"</a>");
			    		
			    		if(hasChild){//자식이있으면
			    		    out.println("<ul>");
			    		}
			    		
			    		
			    		if(i <= (nSize -2)){
			    		   
			    		   if(!hasChild && !result.getOrgParentID().equals(results.get(i+1).getOrgParentID())){
			    		   		//out.println("</li>\n</ul>\n</li>");
							   int deptSize = results.get(i+1).getDepth() - result.getDepth();
			    			   out.println("</li>");
			    			   for(int nLi = 0; nLi < deptSize; nLi++){
			    				   out.println("\n</ul>\n</li>");
			    			   }
			    		   }
			    		   
			    		   if(!hasChild && result.getOrgParentID().equals(results.get(i+1).getOrgParentID())){
			    		   		out.println("</li>");
			    		   }
			    		}
			    		
			    		if(i == (nSize-1)){
				    		if(!results.get(0).getOrgParentID().equals(results.get(i).getOrgParentID())){
				    			out.println("</li>\n</ul>\n</li>");
				    		}else{
				    			out.println("</li>");
				    		}
			    		}
			    	}
			    	%>
	<!--		    	</ul> -->
	<!--				</li> -->
			    </ul>
				</div>				
				</td>
			</tr>
			</acube:tableFrame><!-- 트리용 테이블 -->
			</td>
			<td width="3%" style="background-color : #FFFFFF;"></td>
			<td>
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td height="177" align="center" class="basic_box" style="padding:0px; margin:0px;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
					<thead>
						<TR>
							<TD width="152" class="ltb_head" style="border-top:none;border-bottom: <%=tbColor %> 1px solid;"><spring:message code="approval.table.title.gikwei" /></TD>
							<TD class="ltb_head"  style='border-left:1pt solid <%=tbColor %>;border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.name" /></TD>
						</TR>
					</thead>
				</table>
				<div style="height:150px; overflow-x:hidden; overflow-y:scroll; background-color : #FFFFFF;">
				<table id="tbUsers" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tbody />
				</table>
				</div>
			</td>
			</tr>
			</acube:tableFrame>
			</td>
		</tr>
		</acube:tableFrame>
		
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
	 
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		
		</div>
		<!-- 결재경로 끝 -->
		
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
		<!-- 결재 경로 -->
		<table  width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<acube:tableFrame width="100%" border="0" cellpadding="1" cellspacing="0">
						<tr>
							<td width="100%" height="100%">
								<div>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<thead>
										<TR><TD width="130" class="ltb_head" style="border-top:none;border-bottom: <%=tbColor %> 1px solid;"><spring:message code="approval.table.title.sosok" /></TD>
											<TD width="130"  class="ltb_head"  style='border-left:1pt solid <%=tbColor %>;border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.gikwei" /></TD>
											<TD width="170"  class="ltb_head"  style='border-left:1pt solid <%=tbColor %>;border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.name" /></TD>
											<TD class="ltb_head"  style='border-left:1pt solid <%=tbColor %>;border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.gubun" /></TD>
										</TR>
									</thead>
								</table>
								</div>
								<div style="height:25px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF;">
								<table id="tbApprovalLines" width="100%" border="0" cellpadding="0" cellspacing="0">
									<tbody />
								</table>
								</div>
							</td>
						</tr>
					</acube:tableFrame>
				</td>
				<td width="5"></td>
			</tr>
			<!-- 2015.07.28_lsk_닫기 버튼 추가 -->
			<tr>
				<acube:space between="title_button" />
			</tr>
			<tr>
				<td>
					<acube:buttonGroup>
						<acube:button onclick="javascript:closePopup();" value="<%=closeBtn%>" type="2" class="gr"/>
					</acube:buttonGroup>
				</td>
			</tr>
		</table>
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		<!-- 여백 끝 -->
	</acube:outerFrame>
	<div id="divPop" style="display: none;position: absolute;">
	<table id="sameUser">
		<tbody></tbody>
	</table>
	</div>
	<form id="frmDocInfo" name="frmDocInfo" method="POST"  target="popupWin" >
		<input type="hidden" id="returnFunction" name="returnFunction" value="" />
	    <input type="hidden" id="btnName" name="btnName" value="" />
	    <input type="hidden" id="opinionYn" name="opinionYn" value="" />
	    <input type="hidden" id="comment" name="comment" value=""/>
	</form>
	<form id="appDocForm" name="appDocForm" method="post">
		<div id="approvalitem" name="approvalitem">
			<input id="docId" name="docId" type="hidden" value="<%=docId%>"></input><!-- 문서ID --> 
			<input id="enfLines" name="enfLines" type="hidden" value=""></input><!-- 보고경로 -->
			<input id="receiverOrder" name="receiverOrder" type="hidden" value="<%=receiverOrder%>"></input><!-- 접수부서순서 -->
			<input type="hidden" id="comment" name="comment" value="<%=returnComment%>"></input><!-- 의견 -->
		</div> 
	</form>
</body>	
</html>