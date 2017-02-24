<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.common.vo.DepartmentVO"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.env.vo.EmptyInfoVO"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : ApprovalLinePop.jsp 
 *  Description : 결재경로팝업
 *  Modification Information
 * 
 *   수 정 일 : 2011.03.23 
 *   수 정 자 : 장진홍
 *   수정내용 : 2011.06.01 신경훈  - 결재경로그룹 탭 추가
 * 
 *  @author  장진홍
 *  @since 2011. 03. 23 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String formName = CommonUtil.nullTrim((String) request.getParameter("formName"));
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService"); // jth8172 2012 신결재 TF
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디

	// variable naming rule: {tgw_app_code.code_value(ART010~ART070)}_{tgw_app_option.option_id(OPT001~OPT023)}. 2012.03.23. edited by bonggon.choi.
	String art010_opt001 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT001", "OPT001", "OPT"));	
	String art020_opt002 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT002", "OPT002", "OPT"));	
	String art030_opt003 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT003", "OPT003", "OPT"));	
	String art031_opt004 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT004", "OPT004", "OPT"));	
	String art032_opt005 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT005", "OPT005", "OPT"));	
	String art033_opt006 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT006", "OPT006", "OPT"));	
	String art034_opt007 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT007", "OPT007", "OPT"));	
	String art035_opt008 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT008", "OPT008", "OPT"));	

	String art130_opt053 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT053", "OPT053", "OPT"));	// jth8172 2012 신결재 TF
	String art131_opt054 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT054", "OPT054", "OPT"));	// jth8172 2012 신결재 TF
	String art132_opt055 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT055", "OPT055", "OPT"));	// jth8172 2012 신결재 TF
	String art133_opt056 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT056", "OPT056", "OPT"));	// jth8172 2012 신결재 TF
	String art134_opt057 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT057", "OPT057", "OPT"));	// jth8172 2012 신결재 TF
	String art135_opt058 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT058", "OPT058", "OPT"));	// jth8172 2012 신결재 TF
	
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
	String art055_opt059 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT059", "OPT059", "OPT"));	// jth8172 2012 신결재 TF
	String art060_opt019 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT019", "OPT019", "OPT"));	
	String art070_opt020 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT020", "OPT020", "OPT"));
	
	String adddeptBtn = messageSource.getMessage("approval.button.applines.adddept", null, langType); // 부서추가
	String addlineBtn = messageSource.getMessage("approval.button.applines.addline", null, langType) ;// 추가
	String modlineBtn = messageSource.getMessage("approval.button.applines.modline", null, langType); // 변경
	String dellineBtn = messageSource.getMessage("approval.button.applines.delline", null, langType); // 삭제

	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); // 확인
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기
	
	String adminFlag = CommonUtil.nullTrim((String) request.getParameter("adminFlag"));
	List<EmptyInfoVO> emptyInfoList = new ArrayList<EmptyInfoVO>();
	if ("Y".equals(adminFlag)) {
		emptyInfoList = (List<EmptyInfoVO>) request.getAttribute("emptyInfos");
	}

	List<DepartmentVO> results = (List<DepartmentVO>) request.getAttribute("results");	
	pageContext.setAttribute("userProfile", session.getAttribute("userProfile"));
	pageContext.setAttribute("DEPT_ID", session.getAttribute("DEPT_ID"));

	String PART_ID = (String) session.getAttribute("PART_ID");	
	PART_ID = (PART_ID == null? "" : PART_ID );
	pageContext.setAttribute("PART_ID", PART_ID);
	
	String ROLE_CODES = (String) session.getAttribute("ROLE_CODES");
	
	String linetype = request.getParameter("linetype");
	linetype = (linetype == null ? "1" : linetype);
	
	String tbColor = "#E3E3E3";
	//String tbColor = "#000000";
	String audittype = request.getParameter("audittype");
	audittype = (audittype == null? "U" : audittype);
	pageContext.setAttribute("audittype", audittype); // Y : 감사타입
	
	//결재경로 설정 중일 때 는 Y 결재중일때는 N을 넘긴다.
	String groupYn = request.getParameter("groupYn");
	groupYn = (groupYn == null) ? "N" : groupYn;
	pageContext.setAttribute("groupYn", groupYn);
	
	String currentDate = DateUtil.getCurrentDate();
	envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String opt378 = appCode.getProperty("OPT378", "OPT378", "OPT"); // 처리과만 결재유형 선택 사용여부
	opt378 = envOptionAPIService.selectOptionValue(compId, opt378);
	String opt379 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT379", "OPT379", "OPT"));

	// 본문문서 타입 
	String formBodyType = (String) request.getParameter("formBodyType");	
	// out.println("테스트중 - 지우질 마세요(안규용) ApprovalLinePop.jsp (formBodyType) --> " + formBodyType + "<br>");
		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="approval.title.applines" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<style type="text/css">
	.range {display:inline-block;width:90px;height:25px; !important;}
</style>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript"><!--
var lineType = '<%=linetype%>';
var formName = '<%=formName%>';
//결재요청코드
var ART010 = '<%=appCode.getProperty("ART010","ART010","APP") %>'; //기안       
var ART020 = '<%=appCode.getProperty("ART020","ART020","APP") %>'; //검토
var ART021 = '<%=appCode.getProperty("ART021","ART021","APP") %>'; //검토(주관부서)
var ART030 = '<%=appCode.getProperty("ART030","ART030","APP") %>'; //협조       
var ART031 = '<%=appCode.getProperty("ART031","ART031","APP") %>'; //병렬협조   
var ART032 = '<%=appCode.getProperty("ART032","ART032","APP") %>'; //부서협조   
var ART033 = '<%=appCode.getProperty("ART033","ART033","APP") %>'; //협조(기안) 
var ART034 = '<%=appCode.getProperty("ART034","ART034","APP") %>'; //협조(검토) 
var ART035 = '<%=appCode.getProperty("ART035","ART035","APP") %>'; //협조(결재) 

var ART130 = '<%=appCode.getProperty("ART130","ART130","APP") %>'; //합의       // jth8172 2012 신결재 TF
var ART131 = '<%=appCode.getProperty("ART131","ART131","APP") %>'; //병렬합의   // jth8172 2012 신결재 TF
var ART132 = '<%=appCode.getProperty("ART132","ART132","APP") %>'; //부서합의   // jth8172 2012 신결재 TF
var ART133 = '<%=appCode.getProperty("ART133","ART133","APP") %>'; //합의(기안) // jth8172 2012 신결재 TF
var ART134 = '<%=appCode.getProperty("ART134","ART134","APP") %>'; //합의(검토) // jth8172 2012 신결재 TF
var ART135 = '<%=appCode.getProperty("ART135","ART135","APP") %>'; //합의(결재) // jth8172 2012 신결재 TF

var OPT051 = '<%=appCode.getProperty("OPT051","OPT051","OPT") %>'; //발의 		// jth8172 2012 신결재 TF
var OPT052 = '<%=appCode.getProperty("OPT052","OPT052","OPT") %>'; //보고 		// jth8172 2012 신결재 TF
var OPT053 = '<%=appCode.getProperty("OPT053","OPT053","OPT") %>'; //개인순차합의 // jth8172 2012 신결재 TF



var ART040 = '<%=appCode.getProperty("ART040","ART040","APP") %>'; //감사  
var ART041 = '<%=appCode.getProperty("ART041","ART041","APP") %>'; //부서감사   
var ART042 = '<%=appCode.getProperty("ART042","ART042","APP") %>'; //감사(기안) 
var ART043 = '<%=appCode.getProperty("ART043","ART043","APP") %>'; //감사(검토) 
var ART044 = '<%=appCode.getProperty("ART044","ART044","APP") %>'; //감사(결재) 

var ART045 = '<%=appCode.getProperty("ART045","ART045","APP") %>'; //일상감사
var ART046 = '<%=appCode.getProperty("ART046","ART046","APP") %>'; //준법감시
var ART047 = '<%=appCode.getProperty("ART047","ART047","APP") %>'; //감사위원

var ART050 = '<%=appCode.getProperty("ART050","ART050","APP") %>'; //결재       
var ART051 = '<%=appCode.getProperty("ART051","ART051","APP") %>'; //전결       
var ART052 = '<%=appCode.getProperty("ART052","ART052","APP") %>'; //대결       
var ART053 = '<%=appCode.getProperty("ART053","ART053","APP") %>'; //1인전결    
var ART054 = '<%=appCode.getProperty("ART054","ART054","APP") %>'; //후열       
var ART055 = '<%=appCode.getProperty("ART055","ART055","APP") %>'; //통보  // jth8172 2012 신결재 TF
var ART060 = '<%=appCode.getProperty("ART060","ART060","APP") %>'; //선람       
var ART070 = '<%=appCode.getProperty("ART070","ART070","APP") %>'; //담당 

var APT001 = '<%=appCode.getProperty("APT001","APT001","APT") %>'; //승인
var APT003 = '<%=appCode.getProperty("APT003","APT003","APT") %>'; //대기
var APT004 = '<%=appCode.getProperty("APT004","APT004","APT") %>'; //보류

var role_dailyinpectreader = '<%=AppConfig.getProperty("role_dailyinpectreader","","role")%>'; //일상감사일지 조회자 
var role_dailyinpecttarget = '<%=AppConfig.getProperty("role_dailyinpecttarget","","role")%>'; //일상감사대상자
var role_ceo = '<%=AppConfig.getProperty("role_ceo","","role")%>'; // CEO코드
var role_officer = '<%=AppConfig.getProperty("role_officer","","role")%>'; // 임원코드

var CURRART = ""; //부서협조 및 부서감사 여부 때 설정
var DOUBLEART = ""; //이중결재 처리 중인지 체크
var bDouble = false; //현재 부서처리 여부를 설정한다.

var g_Arts = new Array();
var isCtrl = false, isShift = false; //keyChecked

var gSaveObject = g_selector();
var sColor = "#F2F2F4";
var tbColor = "<%=tbColor%>";

//결재라인 tr 객체를 담는 Valuable
var gSaveLineObject = g_selector();

//호출 페이지에서 넘져준 경로를 받아온다.
var g_lines = ""; 

var currentDate = new Date(<%=currentDate.substring(0,4) %>,<%=currentDate.substring(5,7) %>-1,<%=currentDate.substring(8,10) %>,<%=currentDate.substring(11,13) %>,<%=currentDate.substring(14,16) %>,<%=currentDate.substring(17,19) %>);
//감사부서 여부
var isInspection = false;

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
		<% if ("Y".equals(opt378)) { %>
			"proc_dept" : {
				icon: {
				valid_children : [ "part" ],
				image : "<%=webUri%>/app/ref/js/jsTree/demo/proc_dept.gif"	
				}
			},	
		<% } %>	
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
			}
		}
	});
	<c:choose>
		<c:when test='${PART_ID != ""}'>
			$.tree.focused().select_branch('#${DEPT_ID}');
			$.tree.focused().open_branch('#${DEPT_ID}');
			$.tree.focused().select_branch('#${PART_ID}');
		</c:when>
		<c:otherwise>
			$.tree.focused().select_branch('#${DEPT_ID}');
		</c:otherwise>	
	</c:choose>
	

	//결재경로를 호출한다.	
	lineInit(setAppLine());

//$('#spanApprLineButtons').show();
//$('#spanApprLineButtons2').hide();
//$('#spanApprGroupButtons').hide();
});
//$(document).ready 종료
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
//------------------------트리 이벤트 시작--------------------------------------------------------
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
		<% if ("Y".equals(opt378)) { %>
			if(nodeObj[i].isProcess){
				rel="proc_dept";
			}else{
				rel="dept";
			}
		<% } else { %>	
			rel="dept";
		<% } %>	
		}else{
			rel="part";
		}
		
		t.create({attributes:{'id':nodeObj[i].orgID, 'parentId':nodeObj[i].orgParentID, 'depth':nodeObj[i].hasChild, 'orgType':nodeObj[i].orgType, 'orgName':nodeObj[i].orgName, 'rel': rel},
			data:{title:nodeObj[i].orgName}}, '#'+nodeObj[i].orgParentID);	
		} catch(e) {}
	}
}


//노드가 선택되었때 발생하는 이벤트 처리 함수
function selectNode(node, tree_obj){
	var nSize = tree_obj.children(node).length; //하위노드의 수 
	var url = "<%=webUri%>/app/common/UsersByOrgAjax.do";
	
	url += ("?orgID="+node.id+"&orgType="+node.getAttribute("orgType"));
	
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

//선택된 부서의 사용자 정보를 사용자 목록 에 저장한다. 

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
			
			var row = "<tr style='cursor:pointer;' id='"+user.userUID+"' deptID='"+ user.deptID+"' deptName='"+ user.deptName +"' userName='"+ user.userName;
			row += "' gradeCode='"+ user.gradeCode + "' gradeName='"+ user.gradeName +"' compID='"+ user.compID +"' compName='"+ user.compName;
			row += "' userID='"+ user.userID + "' positionCode='"+ user.positionCode +"' positionName='"+ user.displayPosition +"' titleName='"+ user.titleName;
			row += "' roleCodes='"+ user.roleCodes + "' rowOrd='"+ i + "' empty='" + strEmpty;
			row += "' onclick='onListClick($(\"#"+user.userUID+"\"));' ondblclick='onListDblClick($(\"#"+user.userUID+"\"));' onmousemove='onListMouseMove();' onkeydown='onListKeyDown($(\"#"+user.userUID+"\"));' >";		
			var userPosition = (user.displayPosition == ""?"&nbsp;":user.displayPosition);
						
			row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-left:1pt solid  "+tbColor+";' width='152'>"+userPosition+"</td>";
			row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-left:1pt solid  "+tbColor+";border-right:1pt solid  "+tbColor+";' title='"+user.emptyReason+"'>"+user.userName
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
		isInspection =  result.isInspection;//감사과 여부
		node.isODCD = result.isODCD;	//문서과 여부
		node.isProxyDocHandleDept = result.isProxyDocHandleDept //대리 문서 처리과 여부
		node.orgAbbrName = result.orgAbbrName; //조직 약어명
		node.isProcess = result.isProcess; //처리과 여부
	}
}
//------------------------트리 이벤트 끝--------------------------------------------------------
function makeArtNames(){
	g_Arts[ 0] = new Object();
	g_Arts[ 1] = new Object();
	g_Arts[ 2] = new Object();
	g_Arts[ 3] = new Object();
	g_Arts[ 4] = new Object();
	g_Arts[ 5] = new Object();
	g_Arts[ 6] = new Object();
	g_Arts[ 7] = new Object();
	g_Arts[ 8] = new Object();
	g_Arts[ 9] = new Object();
	g_Arts[10] = new Object();
	g_Arts[11] = new Object();
	g_Arts[12] = new Object();
	g_Arts[13] = new Object();
	g_Arts[14] = new Object();
	g_Arts[15] = new Object();  
	g_Arts[16] = new Object();  
	g_Arts[17] = new Object();  
	g_Arts[18] = new Object(); 
	
	g_Arts[19] = new Object();
	g_Arts[20] = new Object();
	g_Arts[21] = new Object();

	g_Arts[22] = new Object(); // jth8172 2012 신결재 TF
	g_Arts[23] = new Object(); // jth8172 2012 신결재 TF
	g_Arts[24] = new Object(); // jth8172 2012 신결재 TF
	g_Arts[25] = new Object(); // jth8172 2012 신결재 TF
	g_Arts[26] = new Object(); // jth8172 2012 신결재 TF
	g_Arts[27] = new Object(); // jth8172 2012 신결재 TF
	g_Arts[28] = new Object(); // jth8172 2012 신결재 TF

	g_Arts[ 0].Val = ART010;
	g_Arts[ 1].Val = ART020;
	g_Arts[ 2].Val = ART030;
	g_Arts[ 3].Val = ART031;
	g_Arts[ 4].Val = ART032;
	g_Arts[ 5].Val = ART033;
	g_Arts[ 6].Val = ART034;
	g_Arts[ 7].Val = ART035;
	g_Arts[ 8].Val = ART040;
	g_Arts[ 9].Val = ART041;
	g_Arts[10].Val = ART042;
	g_Arts[11].Val = ART043;
	g_Arts[12].Val = ART044;
	g_Arts[13].Val = ART050;
	g_Arts[14].Val = ART051;
	g_Arts[15].Val = ART052;
	g_Arts[16].Val = ART053;
	g_Arts[17].Val = ART054;
	g_Arts[18].Val = ART021; //ART020 이중결재 코드

	g_Arts[19].Val = ART045;
	g_Arts[20].Val = ART046;
	g_Arts[21].Val = ART047;

	g_Arts[22].Val = ART130; //개인순차합의 // jth8172 2012 신결재 TF
	g_Arts[23].Val = ART131; //개인병렬합의 // jth8172 2012 신결재 TF
	g_Arts[24].Val = ART132; //부서합의// jth8172 2012 신결재 TF
	g_Arts[25].Val = ART133; //합의(기안)// jth8172 2012 신결재 TF
	g_Arts[26].Val = ART134; //합의(검토)// jth8172 2012 신결재 TF
	g_Arts[27].Val = ART135; //합의(결재)// jth8172 2012 신결재 TF
	g_Arts[28].Val = ART055;  //통보 // jth8172 2012 신결재 TF

	g_Arts[ 0].Nm = '${options["OPT001"].optionValue}';
	g_Arts[ 1].Nm = '${options["OPT002"].optionValue}';
	g_Arts[ 2].Nm = '${options["OPT003"].optionValue}';
	g_Arts[ 3].Nm = '${options["OPT004"].optionValue}';
	g_Arts[ 4].Nm = '${options["OPT005"].optionValue}';
	g_Arts[ 5].Nm = '${options["OPT006"].optionValue}';
	g_Arts[ 6].Nm = '${options["OPT007"].optionValue}';
	g_Arts[ 7].Nm = '${options["OPT008"].optionValue}';
	g_Arts[ 8].Nm = '${options["OPT009"].optionValue}';
	g_Arts[ 9].Nm = '${options["OPT010"].optionValue}';
	g_Arts[10].Nm = '${options["OPT011"].optionValue}';
	g_Arts[11].Nm = '${options["OPT012"].optionValue}';
	g_Arts[12].Nm = '${options["OPT013"].optionValue}';
	g_Arts[13].Nm = '${options["OPT014"].optionValue}';
	g_Arts[14].Nm = '${options["OPT015"].optionValue}';
	g_Arts[15].Nm = '${options["OPT016"].optionValue}';
	g_Arts[16].Nm = '${options["OPT017"].optionValue}';
	g_Arts[17].Nm = '${options["OPT018"].optionValue}';
	g_Arts[18].Nm = '${options["OPT002"].optionValue}';//ART020 이중결재 코드

	g_Arts[19].Nm = '${options["OPT021"].optionValue}';
	g_Arts[20].Nm = '${options["OPT022"].optionValue}';
	g_Arts[21].Nm = '${options["OPT023"].optionValue}';

	g_Arts[22].Nm = '${options["OPT053"].optionValue}'; //개인순차합의// jth8172 2012 신결재 TF
	g_Arts[23].Nm = '${options["OPT054"].optionValue}'; //개인병렬합의// jth8172 2012 신결재 TF
	g_Arts[24].Nm = '${options["OPT055"].optionValue}'; //부서합의// jth8172 2012 신결재 TF
	g_Arts[25].Nm = '${options["OPT056"].optionValue}'; //합의(기안)// jth8172 2012 신결재 TF
	g_Arts[26].Nm = '${options["OPT057"].optionValue}'; //합의(검토)// jth8172 2012 신결재 TF
	g_Arts[27].Nm = '${options["OPT058"].optionValue}'; //합의(결재)// jth8172 2012 신결재 TF
	g_Arts[28].Nm = '${options["OPT059"].optionValue}'; //통보// jth8172 2012 신결재 TF
	 
	
}

function setAppLine(){
	
	if (opener != null && opener.getAppLine) {
		g_lines = opener.getAppLine();

		var arrLines = getApproverList(g_lines);

		if(arrLines.length == 0){
			return false;
		}

		for(var i = 0; i < arrLines.length; i++){
			var lines = $('#tbApprovalLines tbody'); //결재경로
			var appLines = lines.children();
			
			var id = "", lineNum = "" , lineOrder = "" , lineSubOrder = "" ,  approverId = "" , approverName  = ""
				, approverPos = "" ,  approverDeptId = "" , approverDeptName = "" , representativeId = "" ,  representativeName  = ""
				, representativePos = "" , representativeDeptId = "" ,  representativeDeptName = "" , askType  = "", procType = "" ,  absentReason  = ""
				, editBodyYn = "" , editAttachYn = "" ,  editLineYn = "" , mobileYn  = "", procOpinion  = "",  signFileName = "", readDate = "", processDate = ""
				, lineHisId = "", fileHisId = "", bodyHisId = "", approverRole = "", positionName  = "", deptName = "", userName = "", opt_nm = "" ;
				
			line = arrLines[i];
			//alert(g_Arts[12].Nm);
			id = line.approverId;

			lineNum 				= line.lineNum;
			lineOrder 				= line.lineOrder;
			lineSubOrder 			= line.lineSubOrder;
			approverId 				= line.approverId;
			approverName 			= line.approverName;
			approverPos 			= line.approverPos;
			approverDeptId 			= line.approverDeptId;
			approverDeptName 		= line.approverDeptName;
			representativeId 		= line.representativeId;
			representativeName 		= line.representativeName;
			representativePos 		= line.representativePos;
			representativeDeptId 	= line.representativeDeptId;
			representativeDeptName 	= line.representativeDeptName;
			askType 				= line.askType;
			procType 				= line.procType;
			absentReason 			= line.absentReason;
			editBodyYn	 			= line.editBodyYn;
			editAttachYn 			= line.editAttachYn;
			editLineYn 				= line.editLineYn;
			mobileYn 				= line.mobileYn;
			procOpinion 			= line.procOpinion;
			signFileName 			= line.signFileName;
			readDate 				= line.readDate;
			processDate 			= line.processDate;
			lineHisId 				= line.lineHisId;
			fileHisId	 			= line.fileHisId;
			bodyHisId 				= line.bodyHisId;
			approverRole			= line.approverRole;
			
			positionName 			= line.approverPos;
			
			deptName 				= line.approverDeptName;
			userName 				= line.approverName;

			if(userName === ""){
				positionName = "";
			}
						
			//부서협조,부서감사,부서합의
			//현재 결재 경로가 부서협조, 부서감사, 부서합의 결재 인지 확인
			if((askType === ART032 ||askType === ART033 ||askType === ART034 ||askType === ART035 || 
				askType === ART132 ||askType === ART133 ||askType === ART134 ||askType === ART135 || // jth8172 2012 신결재 TF
				askType === ART041 ||askType === ART042 ||askType === ART043 ||askType === ART044) 
				&& '${userProfile.deptId}' === approverDeptId){

				CURRART = askType;

				if(askType === ART032 ||askType === ART132 || askType === ART041){ // jth8172 2012 신결재 TF
					approverId 			= '${userProfile.userUid}';
					approverName 		= '${userProfile.userName}';
					approverPos 		= '${userProfile.positionName}';
					approverDeptId 		= '${userProfile.deptId}';
					approverDeptName 	= '${userProfile.deptName}';

					id 					= approverId;
					deptName 			= approverDeptName;
					positionName 		= approverPos;
					userName 			= approverName;

					if(askType === ART032){
						askType = ART033;
					}
					if(askType === ART132){ // jth8172 2012 신결재 TF
						askType = ART133;	// jth8172 2012 신결재 TF
					}
					if(askType === ART041){
						askType = ART042;
					}
				}

				$('#divART010').hide();
				if(askType === ART032 ||askType === ART033 ||askType === ART034 ||askType === ART035){
					$('#divART032').show();
					$('#divART132').hide();	// jth8172 2012 신결재 TF
					$('#divART041').hide();
					//askType = ART033;
				} else if(askType === ART132 ||askType === ART133 ||askType === ART134 ||askType === ART135){ // jth8172 2012 신결재 TF
						$('#divART032').hide(); // jth8172 2012 신결재 TF
						$('#divART132').show(); // jth8172 2012 신결재 TF
						$('#divART141').hide(); // jth8172 2012 신결재 TF
						//askType = ART133;
				}else{
					$('#divART032').hide();
					$('#divART132').hide();	// jth8172 2012 신결재 TF
					$('#divART041').show();	
					//askType = ART042;					
				}
								
				$('#divART050').hide();	
				
				
				if(askType === ART032 ||askType === ART033 ||askType === ART034 ||askType === ART035){
					$('input:radio[value="'+ART034+'"]').attr('checked', true);
				} else if (askType === ART132 ||askType === ART133 ||askType === ART134 ||askType === ART135){ // jth8172 2012 신결재 TF
					$('input:radio[value="'+ART134+'"]').attr('checked', true); // jth8172 2012 신결재 TF
				}else{
					$('input:radio[value="'+ART043+'"]').attr('checked', true);
				}
			}


<%if("2".equals(linetype)){	%>
//이중결재시
			if(lineNum === "2" && approverId === "" && (procType === APT003 || procType === APT004)){//이중부서이면서 결재자가 비어있으면 부서만 선택된 경우 임(결재라인 생성해야함)
				approverId 			= '${userProfile.userUid}';
				approverName 		= '${userProfile.userName}';
				approverPos 		= '${userProfile.positionName}';
				approverDeptId 		= '${userProfile.deptId}';
				approverDeptName 	= '${userProfile.deptName}';

				id 					= approverId;
				deptName 			= approverDeptName;
				positionName 		= approverPos;
				userName 			= approverName;
				askType 			= ART010;
			}

			//$('#lineNum1').attr('disabled', true);
			//$('#lineNum2').attr('checked', true);

			$('input:radio[value="'+ART020+'"]').attr('checked', true);

			if(lineNum === "2"){
				DOUBLEART = askType
			}

			if(lineNum === "2" && (procType == APT003 || procType == APT004)){
				bDouble = true;

			}
<%}%>
			//alert(askType);
			
			for(var idx = 0; idx < g_Arts.length; idx++){
				if(g_Arts[idx].Val === askType){
	<%if("1".equals(linetype)){ //단일결재시  %>
					//alert(askType + "::::::::"  + g_Arts[idx].Nm);					
					opt_nm = g_Arts[idx].Nm;
	<%}else{ //이중결재시 %>
					if(lineNum === "1"){
						opt_nm = g_Arts[idx].Nm + '/<spring:message code="approval.form.reqdept" />';//신청부서
					}else{
						opt_nm = g_Arts[idx].Nm + '/<spring:message code="approval.form.recdept" />';//주간부서
					}
	<%}%>
					break;
				}
			}
			
			if (approverRole!="") {  //발의 및 보고 체크 // jth8172 2012 신결재 TF
			 	var strAddTxt ="";
			
			 	<c:if test='${options["OPT051"].useYn == "Y"}'>
				if( approverRole.indexOf("OPT051")> -1  ) {  //발의 // jth8172 2012 신결재 TF
					strAddTxt = "${options["OPT051"].optionValue}" ;
					<c:choose>
					<c:when test='${options["OPT052"].useYn == "Y"}'>
						if( approverRole.indexOf("OPT052")> -1 ) {
							 strAddTxt += "/";
						}
					</c:when>
					</c:choose>					
				}			
				</c:if>
			 	<c:if test='${options["OPT052"].useYn == "Y"}'>
				if( approverRole.indexOf("OPT052")> -1  ) {  //보고 // jth8172 2012 신결재 TF
					strAddTxt += "${options["OPT052"].optionValue}" ;
				}  
				</c:if>
				if(strAddTxt !="") {
					strAddTxt = "[" + strAddTxt +"]"; // jth8172 2012 신결재 TF
					opt_nm = opt_nm + strAddTxt; // jth8172 2012 신결재 TF
				}	
			}	
			
			var row = approveMakeRow("tbApprovalLines", id, lineNum , lineOrder , lineSubOrder ,  approverId , approverName 
					, approverPos ,  approverDeptId , approverDeptName , representativeId ,  representativeName 
					, representativePos , representativeDeptId ,  representativeDeptName , askType , procType ,  absentReason 
					, editBodyYn , editAttachYn ,  editLineYn , mobileYn , procOpinion ,  signFileName, readDate, processDate
					, lineHisId , fileHisId , bodyHisId ,approverRole , positionName , deptName, userName, opt_nm);
			
			if(appLines.length == 0){
				lines.append(row);
			}else{
				$('#'+appLines[0].id).before(row);
			}
		}

		return true;
	}
	
	return false;
}

//로그인 사용자 정보 초기화 시킨다.
function lineInit(bUse){
	var usrRow = $('#${userProfile.userUid}');
	selectOneElement(usrRow);
	if(! bUse){
		usrRow.trigger('dblclick');
		usrRow.children()[0].focus();
	}
}
//사원 선택시 발생하는 이벤트 
// parameter obj : 선택된 tr 의 jquery 객체이다.
function onListClick(obj){
		selectOneElement(obj)
		gSaveLineObject.restore();
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
	<%if("2".equals(linetype)){%>
	$('input:radio').attr('disabled', false);
	var lines2 = $('#tbApprovalLines tbody tr[procType='+APT003+']');
	//라디오버튼 초기화
	if(lines2.length > 0){
		if(lines2.attr('lineNum') === "2"){
			$('#lineNum1').attr('disabled', true);
			$('#lineNum2').attr('checked', true);
		}
	}
	<%}%>
}
//마우스 이동시 발생하는 이벤트
function onListMouseMove(){
	try {
		document.selection.empty();
	} catch (error) {
	}
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
	if(document.selection){
		document.selection.empty();
	}
	addApprovalLine();
	click_Appline('');
}

//-----------------------------------------
function addApprovalLine(){
	var lines = $('#tbApprovalLines tbody'); //결재경로
	var appLines = lines.children();
	var appColl = gSaveObject.collection();

	var radio = $('input[name="options1"][id!="'+ART010+'"][id!="'+ART053+'"][id!="'+ART032+'"][id!="'+ART132+'"][id!="'+ART041+'"]:checked'); // jth8172 2012 신결재 TF
	
	if(radio.length == 0 ){
		radio = $('input#'+ART020+', input#'+ART034+', input#'+ART134+', input#'+ART043);  // jth8172 2012 신결재 TF
		if(radio.length > 0){
			radio.attr('checked',true);
		}
	}

	//부서협조 및 부서감사
	/*
	if(CURRART !== ""){
		if(CURRART === ART032 ||CURRART === ART033 ||CURRART === ART034 ||CURRART === ART035){
			$('input:radio[value="'+ART034+'"]').attr('checked', true);
		}else{
			$('input:radio[value="'+ART043+'"]').attr('checked', true);
		}
	}*/

	if(appColl.length === 0){
		if(!bPop)
			alert('<spring:message code="approval.msg.applines.cknochoice" />');
		else
			popMsg = '<spring:message code="approval.msg.applines.cknochoice" />';
		return;
	}

	for(var i = 0; i < appColl.length; i++) {
		var appItem = appColl[i];
		var ck_app = true;
		for(var j = 0; j < appLines.length; j++){
			var appLine = appLines[j];
			//alert(appLine.id +"=="+ ("tbApprovalLines_" + appItem.attr("id")));
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
			var radio = null;

			var id = "", lineNum = "1" , lineOrder = "" , lineSubOrder = "" ,  approverId = "" , approverName = "" ;
			var approverPos = "" ,  approverDeptId = "" , approverDeptName = "" , representativeId = "" ,  representativeName = "" ;
			var representativePos = "" , representativeDeptId = "" ,  representativeDeptName = "" , askType = "" , procType = "" ,  absentReason = "" ;
			var editBodyYn = "" , editAttachYn = "" ,  editLineYn = "" , mobileYn = "" , procOpinion = "" ,  signFileName = "", readDate ="" , processDate = "";
			var lineHisId = "", fileHisId = "", bodyHisId = "", approverRole = "",positionName = "" , deptName = "", userName = "";

			//이중결재시
			var rdLineNum = $('input:radio[name="lineNum"]:checked');
			var lineNm = "";
			if(rdLineNum.length != 0){				
				lineNum = rdLineNum.val();
				lineNm = rdLineNum.attr('codeNm');
			}
			
			if(appLines.length === 0){//처음입력이면(즉 신규 기안서이면)
				opt_code = ART010;
				opt_nm = '${options["OPT001"].optionValue}';
				//roleCodes
				if(lineNm !== ""){
					opt_nm += ("/" + lineNm)
				}
			}else{
				radio = $("input:radio:checked");
				if(radio.length == 0){
					radio = $('input#'+ART020+', input#'+ART034+', input#'+ART134+', input#'+ART043);
					if(radio.length > 0){
						radio.attr('checked',true);
						opt_code = radio.val();

						if(lineNm === ""){
							opt_nm = radio.attr('codeNm');
						}else{
							opt_nm = radio.attr('codeNm') + "/" + lineNm;
						}
					}					
				}else{
					opt_code = radio.val();
					if(lineNm === ""){
						opt_nm = radio.attr('codeNm');
					}else{
						opt_nm = radio.attr('codeNm') + "/" + lineNm;
					}
				}
			}

   		   id = appItem.attr("id");
           approverId = appItem.attr("id");
           approverName = appItem.attr("userName");
           approverPos = appItem.attr("positionName"); //appItem.attr("positionCode");
           approverDeptId = appItem.attr("deptID");
           approverDeptName = appItem.attr("deptName");
           positionName = appItem.attr("positionName");
           deptName = appItem.attr("deptName");
           userName = appItem.attr("userName");
           askType = opt_code;

           if(appItem.attr("roleCodes").indexOf(role_ceo) !== -1){
 				if(approverRole != ""){
  					approverRole += "^";
  				}
        		approverRole += role_ceo; //CEO
           }

           if(appItem.attr("roleCodes").indexOf(role_officer) !== -1){
  				if(approverRole != ""){
  					approverRole += "^";
  				}
  				approverRole += role_officer; //임원
          	}

           if(appItem.attr("roleCodes").indexOf(role_dailyinpecttarget) !== -1){
  				if(approverRole != ""){
  					approverRole += "^";
  				}
  				approverRole += role_dailyinpecttarget; //일상감사대상자
          	}

           if(appItem.attr("roleCodes").indexOf(role_dailyinpectreader) !== -1){
  				if(approverRole != ""){
  					approverRole += "^";
  				}
  				approverRole += role_dailyinpectreader; //일상감사일지 조회자
          	}
           if(appItem.attr("roleCodes").indexOf(OPT051) !== -1){ // jth8172 2012 신결재 TF
 				if(approverRole != ""){
 					approverRole += "^";
 				}
 				approverRole += OPT051; // 발의자 // jth8172 2012 신결재 TF
          	}
           if(appItem.attr("roleCodes").indexOf(OPT052) !== -1){ // jth8172 2012 신결재 TF
				if(approverRole != ""){
					approverRole += "^";
				}
				approverRole += OPT052; // 보고자 // jth8172 2012 신결재 TF
         	}
           
			if (approverRole!="") {  //발의 및 보고 체크  // jth8172 2012 신결재 TF
			 	var strAddTxt ="";
			
				if( approverRole.indexOf("OPT051")> -1  ) {  //발의
					strAddTxt = "${options["OPT051"].optionValue}" ;
					if( approverRole.indexOf("OPT052")> -1 ) strAddTxt += "/";
				}			 
				if( approverRole.indexOf("OPT052")> -1  ) {  //보고
					strAddTxt += "${options["OPT052"].optionValue}" ;
				}  
				if(strAddTxt !="") {
					strAddTxt = "[" + strAddTxt +"]";
					opt_nm = opt_nm + strAddTxt;
				}	
			}	           

           if(! checkART031(askType)){ ////병렬협조 처리부분
        	   if(!bPop)
               	alert('<spring:message code="approval.msg.applines.after.noparallel" />');
        	   else
        	   	popMsg = '<spring:message code="approval.msg.applines.after.noparallel" />';
               return;
           }
           
           if(! checkART131(askType)){ ////병렬합의 처리부분 // jth8172 2012 신결재 TF
        	   if(!bPop)
               	alert('<spring:message code="approval.msg.applines.after.noagreeparallel" />');
        	   else
        	   	popMsg = '<spring:message code="approval.msg.applines.after.noagreeparallel" />';
               return;
           }
			//jkkim added 2012.07.17 국민은행 감사기능 옵션화 작업 - 감사부서만 감사가능토록 기능
			var opt379 = "<%=opt379%>";
			if("Y" == opt379  && (askType == ART040 || askType == ART041 || askType == ART042 || askType == ART043 || askType == ART044 ) && isInspection == false)
			{
				alert('<spring:message code="env.group.msg.notice.audit" />');return;
			}
			//end
           var row = approveMakeRow("tbApprovalLines", id, lineNum , lineOrder , lineSubOrder ,  approverId , approverName 
					, approverPos ,  approverDeptId , approverDeptName , representativeId ,  representativeName 
					, representativePos , representativeDeptId ,  representativeDeptName , askType , procType ,  absentReason 
					, editBodyYn , editAttachYn ,  editLineYn , mobileYn , procOpinion ,  signFileName, readDate, processDate
					, lineHisId , fileHisId , bodyHisId , approverRole, positionName , deptName, userName, opt_nm);

			if(appLines.length == 0){
				lines.append(row);
			}else{
				if(CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035){//부서협조
					var test = $('#tbApprovalLines tbody tr[askType^=ART033],[ askType^=ART034], [askType^=ART035]');
					test.first().before(row);					
				} else if(CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135){//부서합의 // jth8172 2012 신결재 TF
						var test = $('#tbApprovalLines tbody tr[askType^=ART133],[ askType^=ART134], [askType^=ART135]'); // jth8172 2012 신결재 TF
						test.first().before(row);					
				} else if(CURRART === ART041 || CURRART === ART042 || CURRART === ART043 || CURRART === ART044){//부서감사
					var test = $('#tbApprovalLines tbody tr[askType^=ART042],[ askType^=ART043], [askType^=ART044]');
					test.first().before(row);					
				}else{
					<%if("2".equals(linetype)){ //이중결재 결재일 경우%>						
						if(lineNum == 1){
							var lineNums2 = $('#tbApprovalLines tbody tr[lineNum=2]');
							if(lineNums2.length > 0){
								lineNums2.last().after(row);
							}else{
								$('#'+appLines[0].id).before(row);
							}
						}else{
							$('#'+appLines[0].id).before(row);
						}
					<%}else{%>
					$('#'+appLines[0].id).before(row);
					<%}%>
				}
			}
		}
	}

	gSaveLineObject.restore();	
	bPop = false;
}
//----------------------------------
// 결재 라인 생성 함수--------------------------------
function approveMakeRow(tbId, id, lineNum , lineOrder , lineSubOrder ,  approverId , approverName 
		, approverPos ,  approverDeptId , approverDeptName , representativeId ,  representativeName 
		, representativePos , representativeDeptId ,  representativeDeptName , askType , procType ,  absentReason 
		, editBodyYn , editAttachYn ,  editLineYn , mobileYn , procOpinion ,  signFileName, readDate, processDate
		, lineHisId, fileHisId, bodyHisId, approverRole , positionName , deptName, userName, opt_nm){
	var tbgcolor = "#FFFFFF";

	if(procType === ""){
		tbgcolor = "#FFFFFF"; 

		if( CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035 ||
			CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135){  //부서협조 //부서합의 // jth8172 2012 신결재 TF
			if( askType !== ART032 && askType !== ART033 && askType !== ART034 && askType !== ART035 &&
				askType !== ART132 && askType !== ART133 && askType !== ART134 && askType !== ART135 ){
				tbgcolor = "#EAEAEA";
			}else{
				if(approverDeptId !=='${DEPT_ID}'){
					if(representativeDeptId === '' ){
						tbgcolor = "#EAEAEA";
					}else if(representativeDeptId !=='${DEPT_ID}'){
						tbgcolor = "#EAEAEA";
					}					
				}
			}
		}
		
		if(CURRART === ART041 || CURRART === ART042 || CURRART === ART043 || CURRART === ART044){ //부서감사
			if(askType !== ART041 && askType !== ART042 && askType !== ART043 && askType !== ART044){
				tbgcolor = "#EAEAEA";
			}else{
				if(approverDeptId !=='${DEPT_ID}'){
					if(representativeDeptId === '' ){
						tbgcolor = "#EAEAEA";
					}else if(representativeDeptId !=='${DEPT_ID}'){
						tbgcolor = "#EAEAEA";
					}					
				}
			}
		}
				
	}else{
		if(DOUBLEART != "" && procType === APT003 && (DOUBLEART === ART010 || DOUBLEART === ART053)){
			tbgcolor = "#FFFFFF";
		}else{
			tbgcolor = "#EAEAEA";
		}
	}

	approverName = escapeJavaScript(approverName);
	approverDeptName = escapeJavaScript(approverDeptName);
	representativeName = escapeJavaScript(representativeName);
	representativeDeptName = escapeJavaScript(representativeDeptName);
	procOpinion = escapeJavaScript(procOpinion);
	deptName = escapeJavaScript(deptName);
	userName = escapeJavaScript(userName);	
	var row = "<tr id='"+ tbId + "_" + id + "' lineNum='" + lineNum + "' lineOrder='" + lineOrder + "' lineSubOrder='" + lineSubOrder + "' "; 
	row += "approverId='" + approverId + "' approverName='" + approverName + "' approverPos='" + approverPos + "' ";
	row += "approverDeptId='" + approverDeptId + "' approverDeptName='" +  approverDeptName + "' representativeId='" + representativeId + "' "; 
	row += "representativeName='" + representativeName + "' representativePos='" + representativePos + "' representativeDeptId='" + representativeDeptId + "' "; 
	row += "representativeDeptName='" + representativeDeptName + "' askType='" + askType + "' procType='" + procType + "' ";  
	row += "absentReason='" + absentReason + "' editBodyYn='"+ editBodyYn +"' editAttachYn='"+ editAttachYn + "' ";
	row += "editLineYn='" + editLineYn + "' mobileYn='" + mobileYn + "' procOpinion='" + procOpinion + "' ";
	row += "signFileName='" + signFileName +"' readDate='"+ readDate +"' processDate='"+processDate +"' ";
	row += "lineHisId='" + lineHisId +"' fileHisId='"+ fileHisId +"' bodyHisId='"+bodyHisId +"' approverRole='" + approverRole + "' ";
	row += " onclick='onLineClick($(\"#"+ tbId + "_" + id+"\"));' ondblclick='onLineDblClick($(\"#"+ tbId + "_" + id+"\"));' onmousemove='onLineMouseMove();' onkeydown='onLineKeyDown($(\"#"+ tbId + "_" + id +"\"));'";
	row += " style='background-color:"+tbgcolor+"; cursor:pointer;' >";
	row += "<td width='123' class='ltb_center' style='border-bottom:  "+tbColor+" 1px solid; '>" + (deptName === "" ? "&nbsp;" : deptName) + "</td>";
	row += "<td width='123' class='ltb_center' style='border-bottom:  "+tbColor+" 1px solid;border-left:1pt solid  "+tbColor+";border-right:1pt solid  "+tbColor+"; '>" + (positionName === "" ? "&nbsp;" : positionName) + "</td>";
	row += "<td width='123' class='ltb_center' style='border-bottom:  "+tbColor+" 1px solid;border-right:1pt solid  "+tbColor+"; '>" + (userName === "" ? "&nbsp;" : userName)  + "</td>"
<%
	int emptysize = emptyInfoList.size();
	if ("Y".equals(adminFlag) && emptysize > 0) {
	    for (int loop = 0; loop < emptysize; loop++) {
			EmptyInfoVO emptyInfo = emptyInfoList.get(loop);
%>
	if (approverId == "<%=emptyInfo.getUserId()%>") {
		row += "<td class='ltb_center' style='border-bottom:  "+tbColor+" 1px solid;border-right:1pt solid  "+tbColor+"; '>" + (opt_nm === "" ? "&nbsp;" : opt_nm);
		if (representativeId == "") {
			row += "&nbsp;<span id='representative_" + tbId + "_" + id + "' isset='0' onClick='onSetRepresentative(\"" + tbId + "_" + id + "\", \"<%=emptyInfo.getSubstituteId()%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteName())%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteDisplayPosition())%>\", \"<%=emptyInfo.getSubstituteDeptId()%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteDeptName())%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getEmptyReason())%>\");return(false);' "
			+ " style='cursor:pointer' title='<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteDisplayPosition())%> <%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteName())%>'><b>[<spring:message code="approval.button.applines.on.representative"/>]</b></span>";
		} else {
			row += "&nbsp;<span id='representative_" + tbId + "_" + id + "' isset='1' onClick='onSetRepresentative(\"" + tbId + "_" + id + "\", \"<%=emptyInfo.getSubstituteId()%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteName())%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteDisplayPosition())%>\", \"<%=emptyInfo.getSubstituteDeptId()%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteDeptName())%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getEmptyReason())%>\");return(false);' "
			+ " style='cursor:pointer'><b>[" + representativePos + " " + representativeName + "]</b></span>";
		}
		row += "</td>";
	} else {
		row += "<td class='ltb_center' style='border-bottom:  "+tbColor+" 1px solid;border-right:1pt solid  "+tbColor+"; '>" + (opt_nm === "" ? "&nbsp;" : opt_nm) + "</td>";
	}
<%
	    }
	} else {
%>	    
	row += "<td class='ltb_center' style='border-bottom:  "+tbColor+" 1px solid;border-right:1pt solid  "+tbColor+"; '>" + (opt_nm === "" ? "&nbsp;" : opt_nm) + "</td>";
<%
	}
%>
	row += "</tr>";
	return row;
}

function escapeJavaScript(reqString) {
	return (reqString == null) ? "" : reqString.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\"/g,'&quot;').replace(/\'/g,'\\\'').replace(/#&/g,'&');
}

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

//--------------------------결재선 처리------------------------------------------------------//
function onLineClick(obj){
	selectOneLineElement(obj);
	click_Appline(obj);
}

function onLineDblClick(obj){
	try {
		document.selection.empty();
		onLineClick(obj);
		onDeleteLine();
	} catch (error) {
	}
}

//Option 버튼 활성화
function click_Appline(obj){
	var askType = "";
	var lineNum = "1";
	var appRole = "";// jth8172 2012 신결재 TF

	if(obj !== ""){
		askType = obj.attr('askType');
		lineNum = obj.attr('lineNum');
		appRole = obj.attr('approverRole');// jth8172 2012 신결재 TF
	}
	
	var rdType1 = $('input:radio[value ="'+ART010+'"], input:radio[value ="'+ART053+'"]'); //기안, 1인 전결
	var rdType2 = $('input:radio[value !="'+ART010+'"][value !="'+ART053+'"]');
	var rdType3 = $('input:radio[value = "'+ART032+'"],input:radio[value = "'+ART132+'"],input:radio[value="'+ART041+'"]'); //부서협조, 부서감사// jth8172 2012 신결재 TF
	var rdType4 = $('input:radio[value !="'+ART032+'"][value !="'+ART132+'"][value !="'+ART041+'"]');// jth8172 2012 신결재 TF
	var chkType5 = null; // jth8172 2012 신결재 TF
 	<c:if test='${options["OPT051"].useYn == "Y"}'>
		chkType5 = $('input:checkbox[value ="'+OPT051+'"]'); //발의// jth8172 2012 신결재 TF
		<c:choose>
		<c:when test='${options["OPT052"].useYn == "Y"}'>
			chkType5 = $('input:checkbox[value ="'+OPT051+'"], input:checkbox[value ="'+OPT052+'"]'); //발의,보고// jth8172 2012 신결재 TF
		</c:when>
		</c:choose>					
	</c:if>
	<c:if test='${options["OPT052"].useYn == "Y"}'>
		<c:choose>
		<c:when test='${options["OPT051"].useYn == "Y"}'>
			chkType5 = $('input:checkbox[value ="'+OPT051+'"], input:checkbox[value ="'+OPT052+'"]'); //발의,보고// jth8172 2012 신결재 TF
		</c:when>
		<c:otherwise>
			chkType5 = $('input:checkbox[value ="'+OPT052+'"]'); //보고
		</c:otherwise>		
		</c:choose>					
	</c:if>
 	
 	<c:if test='${options["OPT051"].useYn == "Y"}'>
	if(appRole.indexOf("OPT051")>-1) {  //발의// jth8172 2012 신결재 TF
		$('input:checkbox[value ="'+OPT051+'"]').attr('checked',true);
	} else {
		$('input:checkbox[value ="'+OPT051+'"]').attr('checked',false);
	}	
	</c:if>
 	<c:if test='${options["OPT052"].useYn == "Y"}'>
	if(appRole.indexOf("OPT052")>-1) { //보고// jth8172 2012 신결재 TF
		$('input:checkbox[value ="'+OPT052+'"]').attr('checked',true);
	} else {
		$('input:checkbox[value ="'+OPT052+'"]').attr('checked',false);
	}
	</c:if>
				
<%if("1".equals(linetype)){ //단일결재일 경우%>
	
	if(askType === ART010 || askType === ART053){ //기안 및 1인 결재
			rdType1.attr('disabled', false); //enable
			rdType2.attr('disabled', true);  //disable
			<c:if test='${options["OPT051"].useYn == "Y"}'>			
			chkType5.attr('disabled', false); //enable
			</c:if>
			<c:if test='${options["OPT052"].useYn == "Y"}'>			
			chkType5.attr('disabled', false); //enable
			</c:if>
	}else{
			rdType1.attr('disabled', true);
			rdType2.attr('disabled', false);
	//		chkType5.attr('disabled', true); //disable  발의및보고자 선택 필드를 기안자에게만 적용할 경우 여기를 푼다. 현재는 전부 적용가능// jth8172 2012 신결재 TF
	}

	if(askType !== ''){
		$('input:radio[value="'+askType+'"]').attr('checked',true);
		
		if(askType === ART032 || askType === ART132 || askType === ART041){
			rdType3.attr('disabled', false);
			rdType4.attr('disabled', true);
			<c:if test='${options["OPT051"].useYn == "Y"}' >			
			chkType5.attr('disabled', true); //disable  발의및보고자 // jth8172 2012 신결재 TF
			</c:if>
			<c:if test='${options["OPT052"].useYn == "Y"}'>			
			chkType5.attr('disabled', true); //disable  발의및보고자 // jth8172 2012 신결재 TF
			</c:if>
		}else{
			
			rdType3.attr('disabled', true);
		}		
	}else{
		rdType3.attr('disabled', true);
	}
<%}else{%>
//이중결재체크 부분
	if((askType === ART010 || askType === ART053) && lineNum == "1"){ //기안 및 1인 결재
		rdType1.attr('disabled', false); //enable
		rdType2.attr('disabled', true);  //disable
		
	}else if((askType === ART010 || askType === ART053) && lineNum == "2"){
		rdType1.attr('disabled', false); //enable
		rdType2.attr('disabled', false);
		rdType3.attr('disabled', false); //enable
		rdType4.attr('disabled', false);
		
		$('#lineNum1').attr('disabled', false);
		$('#lineNum2').attr('disabled', false);
	}else{				
		rdType1.attr('disabled', true);
		rdType2.attr('disabled', false);

		
		if(obj !== ""){
			if(obj.attr('approverId')==="" && obj.attr('lineNum')==="2"){ //부서추가이면 
				$('#lineNum1').attr('disabled', true);
				$('#lineNum2').attr('disabled', false);
				$('input:radio[value ="'+ART050+'"]').attr('disabled', true);
				$('input:radio[value ="'+ART051+'"]').attr('disabled', true); // jth8172 2012 신결재 TF
				$('input:radio[value ="'+ART052+'"]').attr('disabled', true); // jth8172 2012 신결재 TF
			}else{
				
				if(obj.attr('lineNum')==="2"){
					$('input:radio').attr('disabled', false);
				}
				
				$('#lineNum1').attr('disabled', false);
				$('#lineNum2').attr('disabled', false);
			}
		}
	}	
	if(askType !== ''){
		if(askType === ART021){
			 askType = ART020;
			 $('input:radio[value="'+ART030+'"][value="'+ART130+'"]').attr('disabled', true); // jth8172 2012 신결재 TF
		}
		
		$('input:radio[value="'+askType+'"]').attr('checked',true);
		$('input:radio[value="'+lineNum+'"]').attr('checked',true);
	}

	if(DOUBLEART !== ""){//이중결재처리 시점에서
		//$('#lineNum1').attr('disabled', true);
		if(askType === ART010 || askType === ART053){
			rdType2.attr('disabled', true);
		}
	}

	if(bDouble){
		$('#lineNum1').attr('disabled', true);
	}
<%}%>
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

			sNum = (num2 > num1) ? num1 : num2;
			eNum = (num2 > num1) ? num2 : num1;
			
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
	try {
		document.selection.empty();
	} catch (error) {
	}
}

//부서추가 버튼 클릭시
function onAddDept(){
	//부서협조,부서합의, 부서감사가 체크되어 있으면
	var radios1 = $('input#'+ART032+':checked, input#'+ART132+':checked, input#'+ART041+':checked'); // jth8172 2012 신결재 TF
	var radios2 = $('input#'+ART032+', input#'+ART132+', input#'+ART041); // jth8172 2012 신결재 TF
	var rdLineNum = $('input:radio[name="lineNum"]'); 
	var deptId = '${userProfile.deptId}';
	
	var id = "", lineNum = "1" , lineOrder = "" , lineSubOrder = "" ,  approverId = "" , approverName = "" 
		, approverPos = "" ,  approverDeptId = "" , approverDeptName = "" , representativeId = "" ,  representativeName = "" 
		, representativePos = "" , representativeDeptId = "" ,  representativeDeptName = "" , askType = "" , procType = "" ,  absentReason = "" 
		, editBodyYn = "" , editAttachYn = "" ,  editLineYn = "" , mobileYn = "" , procOpinion = "" ,  signFileName = "", readDate = "", processDate = ""
		, lineHisId = "", fileHisId = "", bodyHisId = "", approverRole="", positionName = "" , deptName = "", userName = "", opt_nm = "";

	if(CURRART !== ""){//부서 협조,부서 합의 및 부서 감사 시에는 부서 추가를 할 수 없다.
		return;
	}
		
	//선택된 조직
	var sdept = $.tree.focused().selected;

	//사용자와 같은 부서면 부서 추가 할 수 없음
	if(deptId === sdept.attr('id')){		
		alert('<spring:message code="approval.msg.applines.draft.dept" />');
		return;
	}
		
	if(radios2.length > 0 && rdLineNum.length < 1){
		var askRadio = null;
		//부서 협조, 부서 합의 및 감사협조 둘중하나를 자동선택하게 한다.
		if(radios1.length === 0){
			askRadio = radios2[0];
			askRadio.checked = true;
		}else{
			askRadio = radios1[0];
		}

		if(sdept.attr("orgType") !== "2"){
			//결재선으로 지정될 수 없습니다.
			alert('<spring:message code="approval.msg.applines.cannotline" />');
			return;
		}
		
		<% if ("Y".equals(opt378)) { %>
		//부서협조/부서합의/부서감사가 처리과인지 체크한다.
		if(!sdept.attr("isProcess")){
			alert('<spring:message code="approval.msg.applines.no.dept" />');
			return;
		}
		<% } %>

		id = sdept.attr("id");
		approverDeptId = sdept.attr("id");
		approverDeptName = sdept.attr("orgName");
		//approverPos = approverDeptName;
		askType = askRadio.getAttribute('value');
		deptName = approverDeptName;
		opt_nm = askRadio.getAttribute('codeNm');

		var ART042s = $('#tbApprovalLines tbody tr[askType^=ART042]');

		var ART033s = $('#tbApprovalLines tbody tr[askType^=ART033]');

		var ART133s = $('#tbApprovalLines tbody tr[askType^=ART133]');// jth8172 2012 신결재 TF

		for(var i = 0; i < ART042s.length; i++){
			var ckDept = ART042s[i].getAttribute('approverDeptId');
			if(ckDept == approverDeptId){
				alert('<spring:message code="approval.msg.applines.noadddept2" />');
				return;
			}	
		}

		for(var i = 0; i < ART033s.length; i++){
			var ckDept = ART033s[i].getAttribute('approverDeptId');
			if(ckDept == approverDeptId){
				alert('<spring:message code="approval.msg.applines.noadddept2" />');
				return;
			}	
		}

		for(var i = 0; i < ART133s.length; i++){ // jth8172 2012 신결재 TF
			var ckDept = ART133s[i].getAttribute('approverDeptId');
			if(ckDept == approverDeptId){
				alert('<spring:message code="approval.msg.applines.noadddept2" />');
				return;
			}	
		}
		if($('#tbApprovalLines_'+id).length > 0){
			alert('<spring:message code="approval.msg.applines.addeddept" />');
			return;
		}
		//jkkim added 2012.07.17 국민은행 감사기능 옵션화 작업 - 감사부서만 감사가능토록 기능
		var opt379 = "<%=opt379%>";
		if("Y" == opt379  && (askType == ART040 || askType == ART041 || askType == ART042 || askType == ART043 || askType == ART044 ) && isInspection == false)
		{
			alert('<spring:message code="env.group.msg.notice.audit" />');return;
		}
		//end	
		var row = approveMakeRow("tbApprovalLines", id, lineNum , lineOrder , lineSubOrder ,  approverId , approverName 
				, approverPos ,  approverDeptId , approverDeptName , representativeId ,  representativeName 
				, representativePos , representativeDeptId ,  representativeDeptName , askType , procType ,  absentReason 
				, editBodyYn , editAttachYn ,  editLineYn , mobileYn , procOpinion ,  signFileName, readDate, processDate
				, lineHisId , fileHisId , bodyHisId ,approverRole, positionName , deptName, userName, opt_nm);
		var children = $('#tbApprovalLines tbody').children();
		$('#'+children[0].id).before(row);
		gSaveLineObject.restore();
	}else if(radios2.length < 1 && rdLineNum.length > 0){ //이중결재 시 부서추가----------------------------
	
		var rdART020 = $('#'+ ART020);
		var askRadio = null;

		var lineNum1 = $('#lineNum1');
		var lineNum2 = $('#lineNum2');

		//이중결재 부서추가는 1개만 가능함 또한 이중결재 결재자가 추가되면 부서추가는 할 수 없음
		if($('#tbApprovalLines tbody tr:[lineNum="2"]').length > 0){
			alert('<spring:message code="approval.msg.applines.noadddept" />');
			return;
		}
		
		lineNum2.attr('checked', true);
		lineNum1.attr('disabled', true);
						
		if(rdART020.length > 0){
			askRadio = rdART020;
			askRadio.attr('checked', true);
		}else{ //검토가 없으면 부서추가를 할 수 없습니다. 2011.5.27
			alert('<spring:message code="approval.msg.applines.noadddept" />');
			return;
		}
		
		id = sdept.attr("id");
		lineNum = lineNum2.val();
		approverDeptId = sdept.attr("id");
		approverDeptName = sdept.attr("orgName");
		approverPos = approverDeptName;
		askType = ART021;//askRadio.val(); 2011.5.27 : 이중결재 부서 추가는 ART020 대신 ART021을 사용한다.
		deptName = sdept.attr("orgName");
		opt_nm = askRadio.attr('codeNm') +'/'+ lineNum2.attr('codeNm');

		if($('#tbApprovalLines_'+id).length > 0){
			
			alert('<spring:message code="approval.msg.applines.addeddept" />');
			return;
		}
					
		var row = approveMakeRow("tbApprovalLines", id, lineNum , lineOrder , lineSubOrder ,  approverId , approverName 
				, approverPos ,  approverDeptId , approverDeptName , representativeId ,  representativeName 
				, representativePos , representativeDeptId ,  representativeDeptName , askType , procType ,  absentReason 
				, editBodyYn , editAttachYn ,  editLineYn , mobileYn , procOpinion ,  signFileName, readDate, processDate
				, lineHisId , fileHisId , bodyHisId , approverRole, positionName , deptName, userName, opt_nm);
		var children = $('#tbApprovalLines tbody').children();
		$('#'+children[0].id).before(row);
		gSaveLineObject.restore();		
	}else{
		alert('<spring:message code="approval.msg.applines.noadddept" />');
		return;
	}
}

//추가(+) 버튼 클릭시
function onAddLine(){	
	addApprovalLine();
}

//삭제버튼(-)  클릭시
function onDeleteLine(){
	var lines = gSaveLineObject.collection();
	var appTr = $('#tbApprovalLines tbody tr[approverId="${userProfile.userUid}"]');
	var repTr = $('#tbApprovalLines tbody tr[representativeId="${userProfile.userUid}"]');

	for(var i = 0; i < lines.length; i++){
		if(lines[i].attr("askType") === ART010 && lines[i].attr("lineNum") === "1"){
			alert('<spring:message code="approval.msg.applines.cannotsubmiter" />');
			return;
		}else if(lines[i].attr("askType") === ART053 && lines[i].attr("lineNum") === "1"){
			alert('<spring:message code="approval.msg.applines.cannotonesubmiter" />');
			return;
		}else{
			
			if(lines[i].attr("procType") !== ""){
				alert('<spring:message code="approval.msg.applines.after.nodelline" />');
				return;
			}

			if(! checkART031(lines[i].attr("askType"))){
				alert('<spring:message code="approval.msg.applines.no.pub" />');
				return;
			}

			if(! checkART131(lines[i].attr("askType"))){  // jth8172 2012 신결재 TF
				alert('<spring:message code="approval.msg.applines.no.pub" />');
				return;
			}

			if(CURRART !== ""){//부서 협조, 부서합의, 부서 감사일때 체크하는 부분
				var tAskType = lines[i].attr("askType");
				if( CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035 ||
					CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135 ){  // jth8172 2012 신결재 TF
					if( tAskType !== ART032 && tAskType !== ART033 && tAskType !== ART034 && tAskType !== ART035 &&
						tAskType !== ART132 && tAskType !== ART133 && tAskType !== ART134 && tAskType !== ART135 ){
						alert('<spring:message code="approval.msg.applines.after.nodelline" />');
						return;
					}else{
						if(lines[i].attr('approverDeptId') !=='${DEPT_ID}'){
							if(lines[i].attr('representativeDeptId') === '' ){
								alert('<spring:message code="approval.msg.applines.after.nodelline" />');
								return;
							}else if(lines[i].attr('representativeDeptId') !=='${DEPT_ID}'){
								alert('<spring:message code="approval.msg.applines.after.nodelline" />');
								return;
							}					
						}
					}
				}else{
					if(tAskType !== ART041 && tAskType !== ART042 && tAskType !== ART043 && tAskType !== ART044 ){
						alert('<spring:message code="approval.msg.applines.after.nodelline" />');
						return;
					}else{
						if(lines[i].attr('approverDeptId') !=='${DEPT_ID}'){
							if(lines[i].attr('representativeDeptId') === '' ){
								alert('<spring:message code="approval.msg.applines.after.nodelline" />');
								return;
							}else if(lines[i].attr('representativeDeptId') !=='${DEPT_ID}'){
								alert('<spring:message code="approval.msg.applines.after.nodelline" />');
								return;
							}					
						}
					}
				}
			}
			
			lines[i].remove();
		}
	}
	
}

///병렬협조 체크  -----------------------------------------------------------
function checkART031(askType){
	var appTr = $('#tbApprovalLines tbody tr[approverId="${userProfile.userUid}"]');
	var repTr = $('#tbApprovalLines tbody tr[representativeId="${userProfile.userUid}"]');
	var tmpTr = null;

	if(appTr.length === 1 && repTr.length === 0){
		tmpTr = appTr;
	}else if(appTr.length === 1 && repTr.length === 1){
		tmpTr = repTr;
	}else{
		return true;
	}

	if(tmpTr.attr("askType") === ART031 && askType === tmpTr.attr("askType")){
		return false;
	}else{
		return true;
	}
}


///병렬합의 체크 // jth8172 2012 신결재 TF-----------------------------------------------------------
function checkART131(askType){
	var appTr = $('#tbApprovalLines tbody tr[approverId="${userProfile.userUid}"]');
	var repTr = $('#tbApprovalLines tbody tr[representativeId="${userProfile.userUid}"]');
	var tmpTr = null;

	if(appTr.length === 1 && repTr.length === 0){
		tmpTr = appTr;
	}else if(appTr.length === 1 && repTr.length === 1){
		tmpTr = repTr;
	}else{
		return true;
	}

	if(tmpTr.attr("askType") === ART131 && askType === tmpTr.attr("askType")){
		return false;
	}else{
		return true;
	}
}

//위로올리기 버튼
function onMoveUp(){
	if(gSaveLineObject.collection().length === 1){
		var obj = gSaveLineObject.first();
		var prev = obj.prev();

		if(obj.attr('lineNum')=== "1" && (obj.attr('askType') === ART010 ||obj.attr('askType') === ART053) ){
			return;
		}

		if(obj.attr('procType') !== ""){
			return;
		}

		if( CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035 ||
			CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135 ){ //부서협조,부서합의// jth8172 2012 신결재 TF
			/*if(ART035 === obj.attr('askType')){//부서결재는 더이상 위로 올라갈수 없다.
				return;
			}
			*/
			if( ART033 !== prev.attr('askType') && ART034 !== prev.attr('askType') && ART035 !== prev.attr('askType') &&
				ART133 !== prev.attr('askType') && ART134 !== prev.attr('askType') && ART135 !== prev.attr('askType') ){ //협조,합의결재를 넘어설수 없다.
				return;
			}

			
			
		}
		if(CURRART === ART041 || CURRART === ART042 || CURRART === ART043 || CURRART === ART044){//부서감사
/*
			if(ART044 === obj.attr('askType')){//부서결재는 더이상 위로 올라갈수 없다.
				return;
			}

			if(ART044 === prev.attr('askType')){ //협조결재를 넘어설수 없다.
				return;
			}
*/
			if(ART042 !== prev.attr('askType') && ART043 !== prev.attr('askType') && ART044 !== prev.attr('askType')){ //협조결재를 넘어설수 없다.
				return;
			}
		}

		if(prev.attr('askType') !== ART010 && prev.attr('askType') !== ART053 ){
			prev.before($('#'+obj.attr('id')));
		}else{
			if(prev.attr('lineNum') !== "1"){
				prev.before($('#'+obj.attr('id')));
			}
		}
	}
}

//아래로 내리기 버튼-----------
function onMoveDown(){
	if(gSaveLineObject.collection().length === 1){
		var obj = gSaveLineObject.first();
		var prev = obj.next();

		if(obj.attr('lineNum')=== "1" && (obj.attr('askType') === ART010 ||obj.attr('askType') === ART053) ){
			return;
		}
/*
		if(obj.attr('procType') !== "" || prev.attr('procType') !== "" ){
			return;
		}
*/
		if(obj.attr('procType') === APT001 || prev.attr('procType') !== ""){
			return;
		}

		if( CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035 ||
			CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135 ){ //부서협조,부서합의// jth8172 2012 신결재 TF
/*			if(ART035 === obj.attr('askType')){//협조결재는 더이상 내려갈 수 없다.
				return;
			}
*/
			if( ART033 !== obj.attr('askType') && ART034 !== obj.attr('askType') && ART035 !== obj.attr('askType') &&
				ART133 !== obj.attr('askType') && ART134 !== obj.attr('askType') && ART135 !== obj.attr('askType') ){ //합의,협조결재를 넘어설수 없다.// jth8172 2012 신결재 TF
				return;
			}
		}
		
		if(CURRART === ART041 || CURRART === ART042 || CURRART === ART043 || CURRART === ART044){//부서감사
			/*if(ART044 === obj.attr('askType')){//감사결재는 더이상 내려갈 수 없다.
				return;
			}*/

			if(ART042 !== obj.attr('askType') && ART043 !== obj.attr('askType') && ART044 !== obj.attr('askType')){ //협조결재를 넘어설수 없다.
				return;
			}
		}
		
		
		if(prev.attr('askType') !== ART010 && prev.attr('askType') !== ART053){	
			prev.after($('#'+obj.attr('id')));
		}else{
			if(prev.attr('lineNum') !== "1"){
				prev.after($('#'+obj.attr('id')));
			}
		}
		
	}
}

//확인버튼 클릭
function sendOk(){
	var appline = "";
	
	//결재라인 롤을 체크한다.
	if(! checkLineRole()){
		return;
	}
 	appline = makeApprovalLine();
<% if ("Y".equals(adminFlag)) { %>	
	if (opener != null && opener.setAppLineByAdmin) {
		var msg = opener.setAppLineByAdmin(appline, false);
		
		if(typeof(msg) !== "undefined"){
			if(msg !== ""){
				alert(msg);
			}else{
				window.close();
			}
		}else{
			window.close();
		}
	}
<% } else { %>
	if (opener != null && opener.setAppLine) {
		var msg = "";
		if ("hwp" == "<%= formBodyType %>" || "doc" == "<%= formBodyType %>") {
			msg = opener.setAppLine(appline, false);
		} else if ("html" == "<%= formBodyType %>") {
			msg = opener.setApprLineForHTML(appline, false);
		}

		if(typeof(msg) !== "undefined"){
			if(msg !== ""){
				alert(msg);
			}else{
				window.close();
			}
		}else{
			window.close();
		}
	}
<% } %>
}

function closePopup(){
	window.close();
}

//결재라인 생성하기
function makeApprovalLine(){
	var lines = $('#tbApprovalLines tbody');
	setLineOrder(lines);
	var strRt = getApprovalLines(lines);
	//alert(strRt);
	return strRt;
}

//결재 순서 및 하위 순서 등을 세팅한다.
function setLineOrder(lines){
	var subline = lines.children();
	var lineOrder = 1;
	var aLinerOrder = 1;
	var art130LineOrder = 5;
	var isArt130 = false;
	var lineSubOrder = 1;
	var max = subline.length -1;
	/* alert("max(subline.length -1)="+max); */
	for( var i = max; i>=0 ; i--){
		var askType = subline[i].getAttribute('askType');
		 /* alert(i+"의 askType은="+askType); 
		 alert("lineOrder는="+lineOrder); */
		if(askType == ART130){ // 결재라인의 하나라도 순차합의라면... 절대 결재보다 나중에 위치할 순 없으니까 
			isArt130 = true;
		}else{					//결재라인에서 하나라도 순차 합의라면 true가 되나 그렇지 않을경우 false로 만들어 주는 부분이 없었다. 160109동구 추가
			isArt130 = false;
		} 
		 //20160419
		 if(formName != "QBA-0000-01 회사표준갑지" && formName != "QBB-0000-01 회사표준갑지" && formName != "QBA-0000-01 회사표준 갑지" && formName != "QBB-0000-01 회사표준 갑지"){
			 if(isArt130){
					subline[i].setAttribute('lineOrder' , lineOrder);
					subline[i].setAttribute('lineSubOrder', "1");
					lineOrder++;
				}else if (((askType === ART031 || askType === ART131)  && i < (max-1))){	// 아윽 로직이 꼬였다. 병렬합의도 결재보다 라인 오더가 작아야 한다고 한다. 아이 참.
					if(subline[i+1].getAttribute('askType') == ART031 ||subline[i+1].getAttribute('askType') == ART131){
						subline[i].setAttribute('lineOrder',subline[i+1].getAttribute('lineOrder'));
						var subNo = new Number(subline[i+1].getAttribute('lineSubOrder')) + 1
						subline[i].setAttribute('lineSubOrder',subNo);
					}else{
						subline[i].setAttribute('lineOrder' , lineOrder);
						subline[i].setAttribute('lineSubOrder', "1");
						lineOrder++;
					}
				}else if (  !((askType === ART031 || askType === ART131)  && i < (max-1))){	// 병렬 협조, 병렬합의가 아닌경우를 먼저 처리한다.
					// 결재 라인이 기안외에 한명이라도 더 있을 때 마지막 결재권자는 가장 마지막 칸에 표시하려고 한다.
					if(subline.length > 1){
						var baseConsider = 10;
						var asisConsider = 10;
										
						if(lineType==1){ //단일 결재인 경우는 
							if(i==0){ // 역순으로 loop하므로 가장 마지막 결재권자를 뜻함
								asisConsider = opener.getConsiderCount(opener.Document_HwpCtrl);
								if (!opener.existField(opener.Document_HwpCtrl, opener.HwpConst.Field.ConsiderLine)) {
									baseConsider = asisConsider;
								}
								if(baseConsider != subline.length && lineOrder < baseConsider){ // 병렬합의에서 합의자의 라인오더가 4를 사용할 수도 있으므로...아 이거 누가 그냥 처음부터 다시짜라
									lineOrder = baseConsider;
								}
									
								/* alert("i가 0인 것의 lineorder="+lineOrder); */
							}
							
							subline[i].setAttribute('lineOrder' , lineOrder);		 
							subline[i].setAttribute('lineSubOrder', "1");
							lineOrder++;
							
							
							/* 
							if(askType == ART130){ //순차합의의경우는 결재라인의 lineOrder를 그대로 물고 가는데 그러면 디비에 등록이 안된다.
								subline[i].setAttribute('lineOrder' , art130LineOrder);
								subline[i].setAttribute('lineSubOrder', "1");
								art130LineOrder++;
							}else{
								subline[i].setAttribute('lineOrder' , lineOrder);
								subline[i].setAttribute('lineSubOrder', "1");
								lineOrder++;
							} */
							
						}else{ // 이중 결재인 경우는
							asisConsider = opener.getLineApproverCount(opener.Document_HwpCtrl, 1);
							//alert("lineType1이 아닌경우 asisConsider="+asisConsider);
						
							//1번째 라인의 마지막 결재권자인지 확인해야 한다. 현재 로우 정보에선 알 수 없으므로 다음 결재 권자의 결재 라인이 2라면 1번째 라인의 가장 마지막 결재권자로 지정한다.
							if(subline[i-1]){ 
								if(subline[i-1].getAttribute('lineNum')){
									if(subline[i].getAttribute('lineNum')=='1' && subline[i-1].getAttribute('lineNum')=='2'){
										if(asisConsider != subline[i].getAttribute('lineOrder')){ // 1번째 라인의 마지막 결재칸 인덱스와 현재 결재권자의 lineOrder가 다르면 
											lineOrder = asisConsider;
										}else{
											lineOrder = subline[i].getAttribute('lineOrder');
										}
									}
								}
							}
							
							if(i==0){
								asisConsider = opener.getLineApproverCount(opener.Document_HwpCtrl, 2);
								lineOrder = asisConsider;
							}
							
						    //alert(lineOrder - asisConsider);
							subline[i].setAttribute('lineOrder' , lineOrder > asisConsider ? lineOrder - asisConsider : lineOrder);
							//subline[i].setAttribute('lineOrder' , lineOrder );
							subline[i].setAttribute('lineSubOrder', "1");
							lineOrder++;
						}
					}
				}else{
					
				}
		 }else{//이중결재 합의시
			 if(askType == ART130 || askType == ART131){
				 if(lineOrder<3){
						lineOrder = 3;
					}
			 }
			subline[i].setAttribute('lineOrder' , lineOrder);
			subline[i].setAttribute('lineSubOrder', "1");
			lineOrder++;
		 }
		
	}
	//20160419 결재경로를 처음으로 지정하는 경우에는 lineorder를 지정해주며, 회사표준갑지는 lineorder로 결재순서를 식별해야하기 때문에 lineorder 셋팅이 끝나면 아래에서 한번 로직을 더돌린다.
	if(formName == "QBA-0000-01 회사표준갑지" || formName == "QBB-0000-01 회사표준갑지" || formName == "QBA-0000-01 회사표준 갑지" || formName == "QBB-0000-01 회사표준 갑지"){
		for( var i = max; i>=0 ; i--){
			if(subline[i-1]){
				if(subline[i-1].getAttribute('lineNum')){
					//회사표준갑지의 경우에는 라인넘 1일때 다음 라인오더가 4이면 마지막 결재권자로 지정한다. -> 순차협의를 다 진행한 이후에 최종결재자가 결재한다.
					var lineN  = subline[i].getAttribute('lineNum');
					if( 	subline[i].getAttribute('lineNum')=='1' 
						&&  subline[i-1].getAttribute('lineNum')=='2')
					   {
						//회사표준갑지의 경우에는 3이 마지막번호 -> 무조건 마지막번호
						if(4 != subline[i].getAttribute('lineOrder')){ // 1번째 라인의 마지막 결재칸 인덱스와 현재 결재권자의 lineOrder가 다르면
							subline[i].setAttribute('lineOrder' , 4 );
						}
					} else{
						lineOrder = subline[i].getAttribute('lineOrder');
					}
				}
			}
			if(subline[i+1]){
				if(subline[i+1].getAttribute('lineNum')){
					if(subline[i].getAttribute('lineNum')=='2' ){
						//if(subline[i+1].getAttribute('askType')=='ART130' || subline[i+1].getAttribute('lineOrder')=='3')
						if(subline[i+1].getAttribute('lineNum')=='1')
						{
							aLinerOrder = 1;
						}else{
							aLinerOrder++;
						}
						if(i==0){
							asisConsider = opener.getLineApproverCount(opener.Document_HwpCtrl, 2);
							aLinerOrder = asisConsider;
						}
						subline[i].setAttribute('lineOrder' , aLinerOrder );
						subline[i].setAttribute('lineSubOrder', "1");
					}
				}
			}
		}
		aLinerOrder = 1;
		for( var i = max; i>=0 ; i--){
			if(subline[i].getAttribute('askType')=='ART130' || subline[i].getAttribute('askType')=='ART131')  {
				subline[i].setAttribute('lineOrder' , 3 );
				subline[i].setAttribute('lineSubOrder' , aLinerOrder );
				aLinerOrder++;
			}
		}
		
		
	}
	//20170220
	if(formName.indexOf("교육신청서")>0){
		var arrLine = [];
		var lineorder = 5;
		for( var i = max; i>=0 ; i--){
			var line = subline[i];
			//art030이 아니며 linenum이 1이면 신청부서에 해당한다. lineorder는 기안자부터 결재자까지 1씩 증가한다. 4보다 큰 결재자부터 4로 박는다.
			if(line.getAttribute('asktype')!='ART030'&&line.getAttribute('lineNum')=='1'){
				arrLine.push(i);
			}else if(line.getAttribute('asktype')=='ART030'){
				line.setAttribute('lineOrder',lineorder++);
			}
		}
		if(arrLine.length<4){
			subline[arrLine[arrLine.length-1]].setAttribute('lineOrder',4);
		}else{
			/* lineorder, linenumber 기본키 중복에러남 */
			/* for( i = 0; i < arrLine.length; i++){
				if(subline[arrLine[i]].getAttribute('lineOrder')>3){
					subline[arrLine[i]].setAttribute('lineOrder',4);
				}
			} */
		}
	}
	
	
	/* for( var i = max; i>=0 ; i--){
		var askType = subline[i].getAttribute('askType');
		if ((askType === ART031 || askType === ART131)  && i < (max-1)){	// 병렬 협조, 병렬합의
			if(subline[i+1].getAttribute('askType') == ART031 ||subline[i+1].getAttribute('askType') == ART131){
				subline[i].setAttribute('lineOrder',subline[i+1].getAttribute('lineOrder'));
				var subNo = new Number(subline[i+1].getAttribute('lineSubOrder')) + 1
				subline[i].setAttribute('lineSubOrder',subNo);
			}else{
				subline[i].setAttribute('lineOrder' , lineOrder);
				subline[i].setAttribute('lineSubOrder', "1");
				lineOrder++;
			}
			subline[i].setAttribute('lineOrder' , lineOrder);
			subline[i].setAttribute('lineSubOrder', "1");
			lineOrder++;
		}
	} */
}

//결재라인 작성하기
function getApprovalLines(lines){
	var subline = lines.children();
	
	var max = subline.length -1;
	var strAppline = "";
	
	for( var i = max; i>=0 ; i--){
		var line = subline[i];
		strAppline += line.getAttribute('lineOrder') 			+ String.fromCharCode(2) + line.getAttribute('lineSubOrder') 			+ String.fromCharCode(2); 
		strAppline += line.getAttribute('approverId') 			+ String.fromCharCode(2) + line.getAttribute('approverName') 			+ String.fromCharCode(2); 
		strAppline += line.getAttribute('approverPos') 			+ String.fromCharCode(2) + line.getAttribute('approverDeptId') 			+ String.fromCharCode(2); 
		strAppline += line.getAttribute('approverDeptName') 	+ String.fromCharCode(2) + line.getAttribute('representativeId') 		+ String.fromCharCode(2); 
		strAppline += line.getAttribute('representativeName') 	+ String.fromCharCode(2) + line.getAttribute('representativePos') 		+ String.fromCharCode(2); 
		strAppline += line.getAttribute('representativeDeptId') + String.fromCharCode(2) + line.getAttribute('representativeDeptName') 	+ String.fromCharCode(2); 
		strAppline += line.getAttribute('askType')	 			+ String.fromCharCode(2) + line.getAttribute('procType') 				+ String.fromCharCode(2);
		strAppline += line.getAttribute('absentReason') 		+ String.fromCharCode(2) + line.getAttribute('editBodyYn') 				+ String.fromCharCode(2);
		strAppline += line.getAttribute('editAttachYn') 		+ String.fromCharCode(2) + line.getAttribute('editLineYn') 				+ String.fromCharCode(2);
		strAppline += line.getAttribute('mobileYn') 			+ String.fromCharCode(2) + line.getAttribute('procOpinion') 			+ String.fromCharCode(2);
		strAppline += line.getAttribute('signFileName')			+ String.fromCharCode(2) + line.getAttribute('readDate')				+ String.fromCharCode(2); 
		strAppline += line.getAttribute('processDate') 			+ String.fromCharCode(2) + line.getAttribute('lineHisId')  				+ String.fromCharCode(2);
		strAppline += line.getAttribute('fileHisId') 			+ String.fromCharCode(2) + line.getAttribute('bodyHisId')  				+ String.fromCharCode(2);
		strAppline += line.getAttribute('approverRole') 		+ String.fromCharCode(2);
		strAppline += line.getAttribute('lineNum')  			+ String.fromCharCode(4);
	}
	return strAppline;	
}

// 결재라인에 병렬협조,합의 중 승인한 결재자가 있는지 여부 체크 (2011.10.20 신경훈)
function procCheck(obj) { 
	var procYn = "N";
	var size = obj.length;	
	for (i=0; i<size; i++) {
		var line = obj[i];
		if ((line.askType == ART031 || line.askType == ART131) && line.procType == APT001) {// jth8172 2012 신결재 TF
			procYn = "Y";
		}
	}
	return procYn;
}

//체크박스 클릭했을 때 발생하는 이벤트(발의/보고자)  // jth8172 2012 신결재 TF
function chkClick(obj){
 	
	if(gSaveLineObject.collection().length === 1) {

		var strTxt = gSaveLineObject.first().children().last().text();
		if(strTxt.indexOf("[")>0) strTxt = strTxt.substring(0,strTxt.indexOf("["));
		gSaveLineObject.first().attr('approverRole', "");
	 	var strAddOpt ="";
	 	var strAddTxt ="";
	 	<c:if test='${options["OPT051"].useYn == "Y"}'>
		if( $("#OPT051").attr("checked")  ) { //발의 // jth8172 2012 신결재 TF
			strAddOpt =  OPT051;
			strAddTxt = "${options["OPT051"].optionValue}";
			<c:choose>
			<c:when test='${options["OPT052"].useYn == "Y"}'>
				if( $("#OPT052").attr("checked") ) {
					strAddTxt += "/";
					strAddOpt += "^";
				}	
			</c:when>
			</c:choose>					
		}
		</c:if>			 
	 	<c:if test='${options["OPT052"].useYn == "Y"}'>
		if( $("#OPT052").attr("checked") ) { // 보고 // jth8172 2012 신결재 TF
			strAddOpt += OPT052;
			strAddTxt += "${options["OPT052"].optionValue}";
		}  
		</c:if>			 
		if(strAddTxt !="") strAddTxt = "[" + strAddTxt +"]";
		gSaveLineObject.first().attr('approverRole',strAddOpt);
		gSaveLineObject.first().children().last().text(strTxt + strAddTxt );
			
	}
 	
}

// 옵션 클릭했을 때 발생하는 이벤트
function optClick(obj){
	var lines = $('#tbApprovalLines tbody tr');
	var rdType1 = $('input:radio[value ="'+ART010+'"], input:radio[value ="'+ART053+'"]'); //기안, 1인 전결
	
	if(gSaveLineObject.collection().length === 1){
		
		var rdOption  = $('input:radio[name!="lineNum"]:checked');
		var rdLineNum = $('input:radio[name="lineNum"]:checked');
			
		var askType = rdOption.val();

		//부서협조, 부서 감사에 필요한 변수
		var askTypeTmp	= gSaveLineObject.first().attr('askType');
		//결재 처리자 지정 여부
		var procType 	= gSaveLineObject.first().attr('procType');		
		
		if(rdLineNum.length === 0){
			askName = rdOption.attr('codeNm');
		}else{
			gSaveLineObject.first().attr('lineNum', rdLineNum.val());
			askName = rdOption.attr('codeNm') + "/" + rdLineNum.attr('codeNm');
		}

		if(obj.name === 'lineNum' && obj.value === "2"){
			rdType1.attr('disabled', false);
		}

		if (DOUBLEART !== ""){
			//if(DOUBLEART !== ART010 && DOUBLEART !== ART053 && procType !== APT003){
			//	return;
			//}
		}else{
			//결재 처리자나 대기자로 결정되면 수정할 수 없음
			//if(procType !== "") 예전
			if(procType === APT001) {
				return;
			}

			// 병렬협조인 경우 협조자 중 한명이라도 승인을 한 경우 수정할 수 없음. (2011.10.20 신경훈)
			if (askTypeTmp === ART031) { 
				if (procCheck(lines)=="Y") {
					return;
				}
			}
			if (askTypeTmp === ART131) {  //병렬합의도 체크 // jth8172 2012 신결재 TF
				if (procCheck(lines)=="Y") {
					return;
				}
			}
		}

<%if ( "1".equals(linetype)){%>
		//부서협조,합의
		if( CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035 ||
			CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135 ){ // jth8172 2012 신결재 TF
			
			if( askTypeTmp !==  ART032 && askTypeTmp !==  ART033 && askTypeTmp !==  ART034 && askTypeTmp !==  ART035 &&
				askTypeTmp !==  ART132 && askTypeTmp !==  ART133 && askTypeTmp !==  ART134 && askTypeTmp !==  ART135 ){ // jth8172 2012 신결재 TF
				return;
			}

			//협조(배부) 이면서 결재 대기 중인 것은 바꿀수 없다.
			if((askTypeTmp === ART033 || askTypeTmp === ART133) && procType === APT003){ // jth8172 2012 신결재 TF
				return;
			}
		}

		//부서감사
		if(CURRART === ART041 || CURRART === ART042 || CURRART === ART043 || CURRART === ART044){
			if(askTypeTmp !==  ART041 && askTypeTmp !==  ART042 && askTypeTmp !==  ART043 && askTypeTmp !==  ART044){
				return;
			}
			
			//감사(배부) 이면서 결재 대기 중인 것은 바꿀수 없다.
			if(askTypeTmp === ART042 && procType === APT003){
				return;
			}
		}
<%}%>
<%if ( "2".equals(linetype)){//2011.05.27 이중결재 부서 일 경우 ART020을 선택하면 ART021을 넣어줘야 한다. %>
//이중결재 
		if(obj.value === ART020 && gSaveLineObject.first().attr('askType') === ART021){
			askType = ART021;
		}
<%}%>
		gSaveLineObject.first().attr('askType', askType);
		gSaveLineObject.first().children().last().text(askName);
	}
}

//결재라인 롤 체크하기
function checkLineRole(){
	var lines = $('#tbApprovalLines tbody tr');
	var lineCnt = lines.length;
	var oldItem = null, appItem = null, appItem2 = null; //결재요청 존재여부 확인
	var role_ceoYn = false, role_auditorYn=false; //CEO 결재 여부, 감사여부
	var audittypeYn = false; //감사타입체크
	var appRole1 = false; //발의타입체크 // jth8172 2012 신결재 TF
	var appRole2 = false; //보고타입체크 // jth8172 2012 신결재 TF
	 
	var rdLineNum = $('input:radio[name="lineNum"]');

	var doubleLines = $('#tbApprovalLines tbody tr[lineNum="2"]'); //이중결재 라인에 있는 요소들
	var Art033Idx = (lineCnt-1);//부서협조기안 - 협조(기안)
	var ART033_DeptId = "";
	var Art035Idx = 0;//부서협조결재-협조(결재)
	var ART035_DeptId

	var Art133Idx = (lineCnt-1);//부서합의기안 - 합의(기안) // jth8172 2012 신결재 TF
	var ART133_DeptId = ""; // jth8172 2012 신결재 TF
	var Art135Idx = 0; //부서합의결재-합의(결재) // jth8172 2012 신결재 TF
	var ART135_DeptId // jth8172 2012 신결재 TF

	var Art042Idx = (lineCnt-1);
	var ART042_DeptId = "";
	var Art044Idx = 0;
	var ART044_DeptId = "";
	var ART050Idx = 0; //결재위치
	var ART050X1 = ""; //1라인 결재코드

	var ARTSING = ""; //결재이후를 체크하기 위함

	//결재가 2개인경우는 않됨
	var art50s = $('#tbApprovalLines tbody tr[askType="ART050"][lineNum="1"],[askType="ART051"][lineNum="1"],[askType="ART052"][lineNum="1"],[askType="ART053"][lineNum="1"]');

	if(art50s.length > 1){
		alert('<spring:message code="approval.msg.applines.after.noorder" />');
		return;
	}

	//주관부서
	var art502s = $('#tbApprovalLines tbody tr[askType="ART050"][lineNum="2"],[askType="ART051"][lineNum="2"],[askType="ART052"][lineNum="2"],[askType="ART053"][lineNum="2"]');
	if(art502s.length > 1){
		alert('<spring:message code="approval.form.recdept" /> <spring:message code="approval.msg.applines.after.noorder" />');
		return;
	}
	
	
	var isExist = false;
	for(var i = (lineCnt-1); i >= 0; i--){
		var itemCheck = lines[i];									//검토 이전에 합의 또는 협조가 있는경우만 체크 할 목적이므로
		if(itemCheck.getAttribute("askType") == ART030 || itemCheck.getAttribute("askType") == ART031 
				|| itemCheck.getAttribute("askType") == ART032 || itemCheck.getAttribute("askType") == ART130
				|| itemCheck.getAttribute("askType") == ART131 || itemCheck.getAttribute("askType") == ART132){
			isExist = true;
		}
		//20160419
		if(formName != "QBA-0000-01 회사표준갑지" && formName != "QBB-0000-01 회사표준갑지" && formName != "QBA-0000-01 회사표준 갑지" && formName != "QBB-0000-01 회사표준 갑지" && isExist && itemCheck.getAttribute("askType") == ART020
				&&formName.indexOf("교육신청서")<0){
			alert('<spring:message code="approval.msg.applines.after.noorder3" />');
			return false;
		}
	}
	
	
	for(var i = (lineCnt-1); i >= 0; i--){
		tmpItem = lines[i];
		
		if(oldItem !== null){		
			if(oldItem.getAttribute('askType') == ART050 || oldItem.getAttribute('askType') == ART051 
					|| oldItem.getAttribute('askType') == ART052 || oldItem.getAttribute('askType') == ART053){
				ARTSING = oldItem.getAttribute('askType');
			}
			//20160419
			if(formName != "QBA-0000-01 회사표준갑지" && formName != "QBB-0000-01 회사표준갑지" && formName != "QBA-0000-01 회사표준 갑지" && formName != "QBB-0000-01 회사표준 갑지" && ARTSING === ART050 //결재 이후에는 협조, 부서협조, 후열,통보만  올수 있다.
					&&formName.indexOf("교육신청서")<0 
				<c:if test='${options["OPT336"].useYn == "Y"}'>
					&& tmpItem.getAttribute('askType') !== ART030 //협조
				</c:if>
				<c:if test='${options["OPT337"].useYn == "Y"}'>
					&&  tmpItem.getAttribute('askType') !== ART032 //부서협조
					&&  tmpItem.getAttribute('askType') !== ART033 //협조기안
					&&  tmpItem.getAttribute('askType') !== ART034 //협조검토
					&&  tmpItem.getAttribute('askType') !== ART035 //협조결재
				</c:if>
				<c:if test='${options["OPT360"].useYn == "Y"}'>	
					&&  tmpItem.getAttribute('askType') !== ART031 //병렬협조 
				</c:if>
					&&  tmpItem.getAttribute('askType') !== ART054
					&&  tmpItem.getAttribute('askType') !== ART055  //통보 // jth8172 2012 신결재 TF
					&&  oldItem.getAttribute('lineNum') === tmpItem.getAttribute('lineNum') && tmpItem.getAttribute('lineNum') === "1"){

				var msgArt = '<spring:message code="approval.msg.applines.after.art050a" />';
				
				<c:if test='${options["OPT336"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art030a" />';//협조
				</c:if>
				<c:if test='${options["OPT337"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art032a" />'; //부서협조
				</c:if>
				<c:if test='${options["OPT360"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art031a" />'; //병렬협조
				</c:if>
				msgArt += '<spring:message code="approval.msg.applines.after.art054a" />';
 				
				alert(msgArt);
				
				return false;
			}

			if(ARTSING === ART051 //전결 이후에는 협조, 부서협조, 후열,통보만  올수 있다.
					<c:if test='${options["OPT336"].useYn == "Y"}'>
						&& tmpItem.getAttribute('askType') !== ART030 //협조
					</c:if>
					<c:if test='${options["OPT337"].useYn == "Y"}'>
						&&  tmpItem.getAttribute('askType') !== ART032 //부서협조
						&&  tmpItem.getAttribute('askType') !== ART033 //협조기안
						&&  tmpItem.getAttribute('askType') !== ART034 //협조검토
						&&  tmpItem.getAttribute('askType') !== ART035 //협조결재
					</c:if>
					<c:if test='${options["OPT360"].useYn == "Y"}'>	
						&&  tmpItem.getAttribute('askType') !== ART031 //병렬협조 
					</c:if>
					&&  tmpItem.getAttribute('askType') !== ART054
					&&  tmpItem.getAttribute('askType') !== ART055  //통보 // jth8172 2012 신결재 TF
					&&  oldItem.getAttribute('lineNum') === tmpItem.getAttribute('lineNum') && tmpItem.getAttribute('lineNum') === "1"){

				var msgArt = '<spring:message code="approval.msg.applines.after.art051a" />';
				
				<c:if test='${options["OPT336"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art030a" />';//협조
				</c:if>
				<c:if test='${options["OPT337"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art032a" />'; //부서협조
				</c:if>
				<c:if test='${options["OPT360"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art031a" />'; //병렬협조
				</c:if>
				msgArt += '<spring:message code="approval.msg.applines.after.art054a" />';

				alert(msgArt);
				
				return false;
			}

			if(ARTSING === ART052 //대결 이후에는 협조, 부서협조, 후열, 통보만  올수 있다.
					<c:if test='${options["OPT336"].useYn == "Y"}'>
						&& tmpItem.getAttribute('askType') !== ART030 //협조
					</c:if>
					<c:if test='${options["OPT337"].useYn == "Y"}'>
						&&  tmpItem.getAttribute('askType') !== ART032 //부서협조
						&&  tmpItem.getAttribute('askType') !== ART033 //협조기안
						&&  tmpItem.getAttribute('askType') !== ART034 //협조검토
						&&  tmpItem.getAttribute('askType') !== ART035 //협조결재
					</c:if>
					<c:if test='${options["OPT360"].useYn == "Y"}'>	
						&&  tmpItem.getAttribute('askType') !== ART031 //병렬협조 
					</c:if>
					&&  tmpItem.getAttribute('askType') !== ART055  //통보 // jth8172 2012 신결재 TF
					&&  tmpItem.getAttribute('askType') !== ART054
					&& oldItem.getAttribute('lineNum') === tmpItem.getAttribute('lineNum') && tmpItem.getAttribute('lineNum') === "1"){

				var msgArt = '<spring:message code="approval.msg.applines.after.art052a" />';
				
				<c:if test='${options["OPT336"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art030a" />';//협조
				</c:if>
				<c:if test='${options["OPT337"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art032a" />'; //부서협조
				</c:if>
				<c:if test='${options["OPT360"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art031a" />'; //병렬협조
				</c:if>
				msgArt += '<spring:message code="approval.msg.applines.after.art054a" />';

				alert(msgArt);
				
				return false;
			}
			
			if(ARTSING === ART053 
					<c:if test='${options["OPT336"].useYn == "Y"}'>
						&& tmpItem.getAttribute('askType') !== ART030 //협조
					</c:if>
					<c:if test='${options["OPT337"].useYn == "Y"}'>
						&&  tmpItem.getAttribute('askType') !== ART032 //부서협조
						&&  tmpItem.getAttribute('askType') !== ART033 //협조기안
						&&  tmpItem.getAttribute('askType') !== ART034 //협조검토
						&&  tmpItem.getAttribute('askType') !== ART035 //협조결재
					</c:if>
					<c:if test='${options["OPT360"].useYn == "Y"}'>	
						&&  tmpItem.getAttribute('askType') !== ART031 //병렬협조 
					</c:if>
					&&  tmpItem.getAttribute('askType') !== ART055  //통보 // jth8172 2012 신결재 TF
					&& tmpItem.getAttribute('askType') !== ART054
					&& oldItem.getAttribute('lineNum') === tmpItem.getAttribute('lineNum') && tmpItem.getAttribute('lineNum') === "1"){//1인전결 이후 후열만 올수 있음

				var msgArt = '<spring:message code="approval.msg.applines.after.art053a" />';
				
				<c:if test='${options["OPT336"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art030a" />';//협조
				</c:if>
				<c:if test='${options["OPT337"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art032a" />'; //부서협조
				</c:if>
				<c:if test='${options["OPT360"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art031a" />'; //병렬협조
				</c:if>
				msgArt += '<spring:message code="approval.msg.applines.after.art054a" />';

				alert(msgArt);
				
				return false;
			}

			if((oldItem.getAttribute('askType') === ART054  || oldItem.getAttribute('askType') === ART055 )  // jth8172 2012 신결재 TF		
					&& (tmpItem.getAttribute('askType') !== ART054 && tmpItem.getAttribute('askType') !== ART055) // jth8172 2012 신결재 TF		
					&& oldItem.getAttribute('lineNum') === tmpItem.getAttribute('lineNum') && tmpItem.getAttribute('lineNum') === "1"){ 
				//후열뒤에는 후열,통보만 올 수 있음
				alert('<spring:message code="approval.msg.applines.after.art054" />');
				return false;
			}

			// jth8172 2012 신결재 TF		
			if( oldItem.getAttribute('askType') === ART055 &&  tmpItem.getAttribute('askType') !== ART055
					&& oldItem.getAttribute('lineNum') === tmpItem.getAttribute('lineNum') && tmpItem.getAttribute('lineNum') === "1"){ 
				//통보뒤에는 통보만 올 수 있음
				alert('<spring:message code="approval.msg.applines.after.art055" />');
				return false;
			}

			//주관부서는 신청부서 중간에 끼어 들어갈 수 없다.
			if(oldItem.getAttribute('lineNum') !="" && oldItem.getAttribute('lineNum') !== tmpItem.getAttribute('lineNum') && tmpItem.getAttribute('lineNum') === "1"){
				alert('<spring:message code="approval.msg.applines.after.noorder" />');
				return false;
			}

			//부서협조,합의 결재 순서 체크
			if( CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035 || 
				CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135){	// jth8172 2012 신결재 TF				
				var ART033s = $('#tbApprovalLines tbody tr[askType="'+ART033+'"]');
				var ART133s = $('#tbApprovalLines tbody tr[askType="'+ART133+'"]'); // jth8172 2012 신결재 TF
								
				if(tmpItem.getAttribute('askType') === ART033){
					Art033Idx = i;
					ART033_DeptId = tmpItem.getAttribute('approverDeptId');
				}

				if(tmpItem.getAttribute('askType') === ART133){ // jth8172 2012 신결재 TF
					Art133Idx = i;
					ART133_DeptId = tmpItem.getAttribute('approverDeptId');
				}

				if(tmpItem.getAttribute('askType') === ART035){
					Art035Idx = i;
					ART035_DeptId = tmpItem.getAttribute('approverDeptId');
				}
				if(tmpItem.getAttribute('askType') === ART135){ // jth8172 2012 신결재 TF
					Art135Idx = i;
					ART135_DeptId = tmpItem.getAttribute('approverDeptId');
				}

				
				if(tmpItem.getAttribute('askType') === ART033 || tmpItem.getAttribute('askType') === ART034 || tmpItem.getAttribute('askType') === ART035){
					if(Art033Idx > ART050Idx){ //결재 이전에 존재
						if(Art035Idx < ART050Idx){
							alert('<spring:message code="approval.msg.applines.after.noorder" />');
							return;
						}
					}
				}

				if(tmpItem.getAttribute('askType') === ART133 || tmpItem.getAttribute('askType') === ART134 || tmpItem.getAttribute('askType') === ART135){ // jth8172 2012 신결재 TF
					if(Art133Idx > ART050Idx){ //결재 이전에 존재
						if(Art135Idx < ART050Idx){
							alert('<spring:message code="approval.msg.applines.after.noorder" />');
							return;
						}
					}
				}
				
				if(tmpItem.getAttribute('askType') === ART034 && tmpItem.getAttribute('approverDeptId') === ART033_DeptId){

					if(i >= Art033Idx){
						alert('<spring:message code="approval.msg.applines.after.noorder" />');
						return;
					}

					if(i <= Art035Idx && tmpItem.getAttribute('approverDeptId') === ART035_DeptId){
						alert('<spring:message code="approval.msg.applines.after.noorder" />');
						return;
					}					
				}									
				if(tmpItem.getAttribute('askType') === ART134 && tmpItem.getAttribute('approverDeptId') === ART133_DeptId){ // jth8172 2012 신결재 TF

					if(i >= Art133Idx){
						alert('<spring:message code="approval.msg.applines.after.noorder" />');
						return;
					}

					if(i <= Art135Idx && tmpItem.getAttribute('approverDeptId') === ART135_DeptId){
						alert('<spring:message code="approval.msg.applines.after.noorder" />');
						return;
					}					
				}									

				
			}

			//감사협조 결재 순서 체크
			if(CURRART === ART041 || CURRART === ART042 || CURRART === ART043 || CURRART === ART044){					
				//var ART042s = $('#tbApprovalLines tbody tr[askType="'+ART042+'"]');
				
				if(tmpItem.getAttribute('askType') === ART042){
					Art042Idx = i;
					ART042_DeptId = tmpItem.getAttribute('approverDeptId');
				}

				if(tmpItem.getAttribute('askType') === ART044 && tmpItem.getAttribute('approverDeptId') === ART042_DeptId){
					Art044Idx = i;
					ART044_DeptId = tmpItem.getAttribute('approverDeptId');
				}

				
				
				if(tmpItem.getAttribute('askType') === ART043 && tmpItem.getAttribute('approverDeptId') === ART042_DeptId){

					if(i >= Art042Idx){
						alert('<spring:message code="approval.msg.applines.after.noorder" />');
						return;
					}

					if(i <= Art044Idx && tmpItem.getAttribute('approverDeptId') === ART044_DeptId){
						alert('<spring:message code="approval.msg.applines.after.noorder" />');
						return;
					}					
				}						
			}
<%if ( "2".equals(linetype)){ //이중결재 %>
			if(oldItem.getAttribute('lineNum') === "1" &&  tmpItem.getAttribute('lineNum') === "2"){

				//주관부서 부서가 추기되었으면 사용자 추가를 할 수 없다.
				if(ART021 === tmpItem.getAttribute('askType') ){//부서검토
					if($('#tbApprovalLines tbody tr[lineNum="2"][approverId != ""]').length > 0){
						alert('<spring:message code="approval.msg.applines.afterdeptnouser" />');
						return;
					}
				}
										
				//주관부서 사용자가 결자선에 추가될 경우 부서를 추가할 수 없다.
				if("" !== tmpItem.getAttribute('approverDeptId') && "" !== tmpItem.getAttribute('approverId')){
					if($('#tbApprovalLines tbody tr[lineNum="2"][approverDeptId=""]').length > 0){
						alert('<spring:message code="approval.msg.applines.afterusernodept" />');
						return;
					}
				}

				if(!dobleLineCheck()){
					return;
				}
			}
<%}%>


			if(oldItem.getAttribute('askType') === ART050 //결재이후  결재선 지정할 수 없음
					&& oldItem.getAttribute('lineNum') === "2"){
				alert('<spring:message code="approval.form.recdept" /> <spring:message code="approval.msg.applines.after2.art050" />');
				return false;
			}
				
			if(oldItem.getAttribute('askType') === ART051 //전결이후  결재선 지정할 수 없음
					&& oldItem.getAttribute('lineNum') === "2"){
				alert('<spring:message code="approval.form.recdept" /> <spring:message code="approval.msg.applines.after2.art051" />');
				return false;
			}

			if(oldItem.getAttribute('askType') === ART052 //대결이후 결재선 지정할 수 없음
					&& oldItem.getAttribute('lineNum') === "2"){
				alert('<spring:message code="approval.form.recdept" /> <spring:message code="approval.msg.applines.after2.art052" />');
				return false;
			}
			
			if(oldItem.getAttribute('askType') === ART053 //1인전결 이후  결재선 지정할 수 없음
					&& oldItem.getAttribute('lineNum') === "2"){
				alert('<spring:message code="approval.form.recdept" /> <spring:message code="approval.msg.applines.after2.art053" />');
				return false;
			}
		}else{
			if(lineCnt < 2 && tmpItem.getAttribute('askType') !== ART053){ 
				// 결재 라인인 하나만 있는 경우는 1인전결인지 체크
				alert('<spring:message code="approval.msg.applines.after.notexit" />');
				return false;
			}
		}
		
		//결재 존재여부 확인
		if((tmpItem.getAttribute('askType') === ART050 ||tmpItem.getAttribute('askType') === ART051 
				||tmpItem.getAttribute('askType') === ART052 ||tmpItem.getAttribute('askType') === ART053)
				&& tmpItem.getAttribute('lineNum') === "1"){
			ART050Idx = i;
			ART050X1 = tmpItem.getAttribute('askType');
			appItem = tmpItem;

			/*//CEO 결재 여부 --> CEO(role_ceo), 일상감사대상자 대상사(role_dailyinpecttarget) 가 결재경로에 포함되어 있으면 으로 변경
			if(tmpItem.getAttribute("approverRole").indexOf(role_ceo) !== -1){
				role_ceoYn = true; //CEO 결재 여부
			}
			*/
		}

		//CEO 결재 여부 --> CEO(role_ceo), 일상감사대상자 대상사(role_dailyinpecttarget) 가 결재경로에 포함되어 있으면 으로 변경
		if(tmpItem.getAttribute("approverRole").indexOf(role_ceo) !== -1 || tmpItem.getAttribute("approverRole").indexOf(role_dailyinpecttarget) !== -1){
			role_ceoYn = true; //CEO 결재 여부 ,일상감사대상자 대상사
		}
		

		//이중결재 결재 존재여부 확인
		if((tmpItem.approverId === "" ||tmpItem.getAttribute('askType') === ART050 ||tmpItem.getAttribute('askType') === ART051 
				||tmpItem.getAttribute('askType') === ART052 ||tmpItem.getAttribute('askType') === ART053)
				&& tmpItem.getAttribute('lineNum') === "2"){
			appItem2 = tmpItem;
		}

		if(tmpItem.getAttribute('askType') === ART040 || tmpItem.getAttribute('askType') === ART041 || tmpItem.getAttribute('askType') === ART042
				|| tmpItem.getAttribute('askType') === ART043 || tmpItem.getAttribute('askType') === ART044
				|| tmpItem.getAttribute('askType') === ART045 || tmpItem.getAttribute('askType') === ART046 || tmpItem.getAttribute('askType') === ART047){ //감사여부 체크
			audittypeYn = true;
		}

		if(tmpItem.getAttribute('approverRole').indexOf(OPT051)>-1 ) { //발의유무 // jth8172 2012 신결재 TF
			if(appRole1) {
				alert('<spring:message code="approval.msg.applines.after.noorder" />');
				return false;
			}	
			appRole1 = true;
		}	
		if(tmpItem.getAttribute('approverRole').indexOf(OPT052) >-1 ) { // 보고유무 // jth8172 2012 신결재 TF
			if(appRole2) {
				alert('<spring:message code="approval.msg.applines.wrongrole" />');
				return false;
			}	
			appRole2 = true;
		}	

		
		oldItem = tmpItem;		
	}  //for
		
	if(appItem === null)
	{
		alert('<spring:message code="approval.msg.applines.after.notexit" />');
		return false;
	}

<%if ( "1".equals(linetype)){%>
//이중결재가 아닌경우
<c:if test='${options["OPT320"].useYn == "Y"}'>

//수정부분
/*
	if(role_ceoYn && !role_auditorYn){//CEO 결재시 감사자가 필수로 포함되어 있어야 한다.
		if(!confirm('<spring:message code="approval.msg.applines.after.norole_auditor" />')){
			//alert('<spring:message code="approval.msg.applines.after.norole_auditor" />');//감사자가 결재선에 지정되지 않았습니다.
			return false;
		}
	}
*/
	var iRoleCodes = "<%=ROLE_CODES%>";
	if((iRoleCodes.indexOf(role_dailyinpectreader) === -1 && iRoleCodes.indexOf(role_dailyinpecttarget) === -1 && iRoleCodes.indexOf(role_ceo) === -1  )
			&&role_ceoYn && !audittypeYn){
		if(!confirm('<spring:message code="approval.msg.applines.after.norole_auditor" />')){ //감사자가 결재선에 지정되지 않았습니다.
			return false;
		}
	}
	
</c:if>

<c:if test='${audittype == "N"}'>
	if(audittypeYn){//audittype 가 N 인경우 감사결재타입을 추가할 수 없음
		alert('<spring:message code="approval.msg.audittype.n" />');
		return false;
	}
</c:if>
<c:if test='${audittype == "Y"}'>
	if(!audittypeYn){//audittype 가 Y 인경우 감사결재타입을 필수로 추가해야함
		alert('<spring:message code="approval.msg.audittype.y" />');
		return false;
	}
</c:if>
<%}%>

<%if ( "2".equals(linetype)){%>
	if(appItem2 === null)
	{
		if (doubleLines.length < 1){
			alert('<spring:message code="approval.msg.applines.after.notexit2" />');
			return false;
		}
		
		if(doubleLines[doubleLines.length-1].getAttribute('procType') !== ""){
			alert('<spring:message code="approval.msg.applines.after.notexit2" />');
			return false;
		}
	}
<%}%>

	//부서협조 중복 체크
	if(CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035){
		var ART033s = $('#tbApprovalLines tbody tr[askType="'+ART033+'"]');

		for(var i = 0; i < ART033s.length; i++){		
			var approverDeptId = ART033s[i].getAttribute('approverDeptId');
			
			var ART033Cnt = $('#tbApprovalLines tbody tr[askType="'+ART033+'"][approverDeptId='+approverDeptId+']').length;
			var ART035Cnt = $('#tbApprovalLines tbody tr[askType="'+ART035+'"][approverDeptId='+approverDeptId+']').length;
		
			if(ART033Cnt > 1 || ART035Cnt > 1){ //1개 이상이면 결재 순서가 잘못된 것임
				alert('<spring:message code="approval.msg.applines.after.errappline" />');
				return;
			}
		
			if(ART033Cnt === 1 && ART035Cnt === 0){
				alert('<spring:message code="approval.msg.applines.after.errappline" />');
				return;
			}
		}
	}

	//부서합의 중복 체크
	if(CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135){ // jth8172 2012 신결재 TF
		var ART133s = $('#tbApprovalLines tbody tr[askType="'+ART133+'"]');

		for(var i = 0; i < ART133s.length; i++){		
			var approverDeptId = ART133s[i].getAttribute('approverDeptId');
			
			var ART133Cnt = $('#tbApprovalLines tbody tr[askType="'+ART133+'"][approverDeptId='+approverDeptId+']').length;
			var ART135Cnt = $('#tbApprovalLines tbody tr[askType="'+ART135+'"][approverDeptId='+approverDeptId+']').length;
		
			if(ART133Cnt > 1 || ART135Cnt > 1){ //1개 이상이면 결재 순서가 잘못된 것임
				alert('<spring:message code="approval.msg.applines.after.errappline" />');
				return;
			}
		
			if(ART133Cnt === 1 && ART135Cnt === 0){
				alert('<spring:message code="approval.msg.applines.after.errappline" />');
				return;
			}
		}
	}
	
	//부서감사  중복 체크
	if(CURRART === ART041 || CURRART === ART042 || CURRART === ART043 || CURRART === ART044){
		var ART042s = $('#tbApprovalLines tbody tr[askType="'+ART042+'"]');
		
		for(var i = 0; i < ART042s.length; i++){
			var approverDeptId = ART042s[i].getAttribute('approverDeptId');
			
			var ART042Cnt = $('#tbApprovalLines tbody tr[askType="'+ART042+'"]').length;
			var ART044Cnt = $('#tbApprovalLines tbody tr[askType="'+ART044+'"]').length;

			if(ART042Cnt > 1 || ART044Cnt > 1){ //1개 이상이면 결재 순서가 잘못된 것임
				alert('<spring:message code="approval.msg.applines.after.errappline" />');
				return;
			}

			if(ART042Cnt === 1 && ART044Cnt === 0){
				alert('<spring:message code="approval.msg.applines.after.errappline" />');
				return;
			}
		}
	}
		
	return true;
}

/**
 * 이중결재 결재라인 체크
 */
function dobleLineCheck(){
	var doubleLines = $('#tbApprovalLines tbody tr[lineNum="2"]'); //이중결재 라인에 있는 요소들
	//doubleLines.first().getAttribute("askType");
	if(doubleLines.length === 1){
		/* if(doubleLines.first().attr("askType") === ART021 || doubleLines.first().attr("askType") === ART010 || doubleLines.first().attr("askType") === ART053){ */  //160109동구 수정
			if(doubleLines.first().attr("askType") === ART053){	//이중결재시 기안자만 있어도 결재라인이 지정되었으나 결재 라인이 1명일 경우 1인 전결일 때에만(결재자는 반드시 포함) 결재 라인이 지정되도록 수정 
			return true;
		}else{
			/* alert('<spring:message code="approval.msg.applines.nomaindeptandapprover" />'); */
			alert('<spring:message code="approval.msg.applines.after.noorder2" />');			//주관부서 결재경로가 올바르지 않습니다. alert 띄우도록 수정
			return false;
		}
	}

	if(doubleLines.length > 1){
		/* if(doubleLines.last().attr("askType") === ART010 && 
				(doubleLines.first().attr("askType") === ART050 
						|| doubleLines.first().attr("askType") === ART051 
						|| doubleLines.first().attr("askType") === ART051)){ */
			if((doubleLines.last().attr("askType") === ART010 || doubleLines.last().attr("askType") === ART021 || doubleLines.last().attr("askType") === ART020)  &&	//160109동구 수정 결재 라인이 1명 이상 일 경우에는 결재 라인의 처음이 기안 또는 주관부서 검토일 경우에 결재 라인이 지정 되도록 수정 
				(doubleLines.first().attr("askType") === ART050 || doubleLines.first().attr("askType") === ART051)){
			return true;
		}else{
			alert('<spring:message code="approval.msg.applines.after.noorder2" />');
			return false;
		}
	}
	return true;
}

//------------- 결재경로그룹 시작 ------------- 2011.06.01 신경훈 //
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

	var ART130 = "<%=art130_opt053%>"; // jth8172 2012 신결재 TF
	var ART131 = "<%=art131_opt054%>"; // jth8172 2012 신결재 TF
	var ART132 = "<%=art132_opt055%>"; // jth8172 2012 신결재 TF
	var ART133 = "<%=art133_opt056%>"; // jth8172 2012 신결재 TF
	var ART134 = "<%=art134_opt057%>"; // jth8172 2012 신결재 TF
	var ART135 = "<%=art135_opt058%>"; // jth8172 2012 신결재 TF

	var ART040 = "<%=art040_opt009%>";
	var ART041 = "<%=art041_opt010%>";
	var ART042 = "<%=art042_opt011%>";
	var ART043 = "<%=art043_opt012%>";
	var ART044 = "<%=art044_opt013%>";
	var ART045 = "<%=art045_opt021%>";
	var ART046 = "<%=art046_opt022%>";
	var ART047 = "<%=art047_opt023%>";
	var ART050 = "<%=art050_opt014%>";
	var ART051 = "<%=art051_opt015%>";
	var ART052 = "<%=art052_opt016%>";
	var ART053 = "<%=art053_opt017%>";
	var ART054 = "<%=art054_opt018%>";
	var ART055 = "<%=art055_opt059%>"; // jth8172 2012 신결재 TF
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

	var tbAppLines = $('#tbApprovalLines tbody').children();	
	linesLength = tbAppLines.length;
	appLineLength = appLineList.length-1;
	if (linesLength > 1) {
		for (var i=0; i<linesLength-1; i++) {
			$('#'+tbAppLines[i].id).remove();
		}
	}
	tbAppLines = $('#tbApprovalLines tbody').children();
	//if (linesLength <= 1) {
		for (var i=0; i<appLineLength; i++) {
			appLineObj = appLineList[i];
			if (appLineObj.changeYn != "Y") {
				var row = approveMakeRow("tbApprovalLines", appLineObj.approverId, "1", appLineObj.lineOrder
						, appLineObj.lineSubOrder, appLineObj.approverId, appLineObj.approverName, appLineObj.approverPos
						, appLineObj.approverDeptId, appLineObj.approverDeptName, "", "", "", "", "", appLineObj.askType
						, "", "", "", "", "", "", "", "", "", "", "", "", "", appLineObj.roleCodes, appLineObj.approverPos
						, appLineObj.approverDeptName, appLineObj.approverName, codeToName(appLineObj.askType));
				$('#'+tbAppLines[0].id).before(row);
			} else {
				alert(appLineObj.approverName+"<spring:message code='env.group.msg.notice.appline'/>");
			}
		}
	//} else {
		//alert("<spring:message code='env.group.msg.notice.groupadd'/>");
	//}
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
	var applineLength = appLineList.length;		
	//var notice = "N";
	var bgcolor = "";
	tbl.empty();

	for (var i=0; i<applineLength; i++) {				
		var appLine = appLineList[i];
		var askName = codeToName(appLine.askType);
		bgcolor = "ffffff";
		if (appLine.changeYn == "Y") {
			bgcolor = "ffefef";
			//notice = "Y";
		}
		var row = "<tr bgcolor='#"+bgcolor+"'>";
		row += "<td width='73' class='ltb_center'>"+appLine.approverDeptName+"</td>";
		if (appLine.askType == "ART032" || appLine.askType == "ART132" || appLine.askType == "ART041") {
			row += "<td width='73' class='ltb_center'></td>";
		} else {
			row += "<td width='73' class='ltb_center'>"+appLine.approverPos+"</td>";
		}
		row += "<td width='72' class='ltb_center'>"+appLine.approverName+"</td>";
		row += "<td width='*' class='ltb_center'>"+askName+"</td>";
		row += "</tr>";
		tbl.append(row);
	}

	//if (notice == "Y") {
	//	alert("<spring:message code='env.group.msg.notice.appline' />");
	//}
}

//------------- 결재경로그룹 끝 ------------- 2011.06.01 신경훈 //

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
			var height = 330;
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
	row += "' roleCodes='"+ user.roleCodes;
	row += "'>";
	var userPosition = (user.displayPosition == ""?"&nbsp;":user.displayPosition);
	row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;' width='88'>"+userPosition+"</td>";
	row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-left:1pt solid  "+tbColor+";border-right:1pt solid  "+tbColor+";'>"+user.userName+"</td></tr>";
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
	var height = 330;
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
--></script>
<style>
	.tdSelect{
		background-color : #F9E5DF;
	}
</style>
</head> 
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
		<acube:outerFrame>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td>
			<span class="pop_title77"><spring:message code="approval.title.applines" /></span>
			<!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<!-- 여백 끝 -->
			
			<!-- 탭 시작 -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
		            <td>
		               <acube:tabGroup>
		                  <acube:tab index="1" onClick="JavaScript:selectTab(1);callMethod('divAppLine');" selected="true"><spring:message code="approval.title.applines" /></acube:tab>
						<c:if test='${groupYn == "Y"}'>
						  <acube:space between="tab"/>
						  <acube:tab index="2" onClick="JavaScript:selectTab(2);callMethod('divAppLineGroup');"><spring:message code="env.tapmenu.linegroup" /></acube:tab>
		               </c:if>
		               </acube:tabGroup>
		            </td>
		         </tr>
	         </table>
	         <!-- 탭 끝 -->
	         
	         <!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<!-- 여백 끝 -->
			<!-- 검색  -->
			<div id="div_dept_search">
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
							    if ("Y".equals(opt378)) {
								   	if(result.getIsProcess()){
								   	 	rel = " rel='proc_dept' ";
								   	}else{
								   		rel = " rel='dept' ";
								   	}
								} else {	
							    	rel = " rel='dept' ";
								}	    
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
					<td height="177" class="basic_box" style="padding:0px; margin:0px;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
						<thead>
							<TR>
								<TD width="152" class="ltb_head" style="border-left:1pt solid <%=tbColor %>;border-bottom: <%=tbColor %> 1px solid;"><spring:message code="approval.table.title.gikwei" /></TD>
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
		 
		 <!-- 결재 옵션 -->
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
				<tr>
					<td width="100%" height="100%" class="g_box" style="padding:2px 2px 2px 2px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td height="5" colspan="3"></td>
							</tr>
							<tr>
								<td width="5" height="30"></td>
								<td style="font-size:9pt;">
									<div id='divART032' style="display:none; height:30px; "><!-- ==============부서 협조============================== -->
										<c:if test='${options["OPT006"].useYn == "Y"}'>
										<span class="range">
										<input id='ART033' name="options1" type="radio"  value='<%=appCode.getProperty("ART033", "ART033", "APP") %>' codeNm='${options["OPT006"].optionValue}' onclick="optClick(this)" />${options["OPT006"].optionValue}
										</span>
										</c:if><!-- 협조(기안) -->
										<c:if test='${options["OPT007"].useYn == "Y"}'> 
										<span class="range">
										<input id='ART034' name="options1" type="radio"  value='<%=appCode.getProperty("ART034", "ART034", "APP") %>' codeNm='${options["OPT007"].optionValue}' onclick="optClick(this)" />${options["OPT007"].optionValue}
										</span>
										</c:if><!-- 협조(검토) -->
										<c:if test='${options["OPT008"].useYn == "Y"}'> 
										<span class="range">
										<input id='ART035' name="options1" type="radio"  value='<%=appCode.getProperty("ART035", "ART035", "APP") %>' codeNm='${options["OPT008"].optionValue}' onclick="optClick(this)" />${options["OPT008"].optionValue}
										</span>
										</c:if><!-- 협조(결재) -->
									</div>
									<div id='divART132' style="display:none; height:30px; "><!-- ==============부서 합의============================== -->
										<c:if test='${options["OPT056"].useYn == "Y"}'>
										<span class="range">
										<input id='ART133' name="options1" type="radio"  value='<%=appCode.getProperty("ART133", "ART133", "APP") %>' codeNm='${options["OPT056"].optionValue}' onclick="optClick(this)" />${options["OPT056"].optionValue}
										</span>
										</c:if><!-- 합의(기안) // jth8172 2012 신결재 TF-->
										<c:if test='${options["OPT057"].useYn == "Y"}'> 
										<span class="range">
										<input id='ART134' name="options1" type="radio"  value='<%=appCode.getProperty("ART134", "ART134", "APP") %>' codeNm='${options["OPT057"].optionValue}' onclick="optClick(this)" />${options["OPT057"].optionValue}
										</span>
										</c:if><!-- 합의(검토) // jth8172 2012 신결재 TF-->
										<c:if test='${options["OPT058"].useYn == "Y"}'> 
										<span class="range">
										<input id='ART135' name="options1" type="radio"  value='<%=appCode.getProperty("ART135", "ART135", "APP") %>' codeNm='${options["OPT058"].optionValue}' onclick="optClick(this)" />${options["OPT058"].optionValue}
										</span>
										</c:if><!-- 합의(결재) // jth8172 2012 신결재 TF-->
									</div>
									<div id='divART041' height="30" style="display:none; height:30px; "><!-- ==============부서 감사============================== -->
										<c:if test='${options["OPT011"].useYn == "Y"}'>
										<span class="range">
										<input id='ART042' name="options1" type="radio"  value='<%=appCode.getProperty("ART042", "ART042", "APP") %>' codeNm='${options["OPT011"].optionValue}' onclick="optClick(this)" />${options["OPT011"].optionValue}
										</span>
										</c:if><!-- 감사(기안) -->
										<c:if test='${options["OPT012"].useYn == "Y"}'> 
										<span class="range">
										<input id='ART043' name="options1" type="radio"  value='<%=appCode.getProperty("ART043", "ART043", "APP") %>' codeNm='${options["OPT012"].optionValue}' onclick="optClick(this)" />${options["OPT012"].optionValue}
										</span>
										</c:if><!-- 감사(검토) -->
										<c:if test='${options["OPT013"].useYn == "Y"}'> 
										<span class="range">
										<input id='ART044' name="options1" type="radio"  value='<%=appCode.getProperty("ART044", "ART044", "APP") %>' codeNm='${options["OPT013"].optionValue}' onclick="optClick(this)" />${options["OPT013"].optionValue}
										</span>
										</c:if>
									</div>
				<%
				if("1".equals(linetype)){ //단일결재일 경우
				%>
									<div id='divART010' style="height:50px;width:100%"><!-- ==============기안 , 1인 전결 , 검토, 협조, 병렬협조, 부서협조============================== -->
										<c:if test='${options["OPT001"].useYn == "Y"}'>
										<span class="range" >
										<input id='ART010' name="options1" type="radio"  value='<%=appCode.getProperty("ART010", "ART010", "APP") %>' codeNm='${options["OPT001"].optionValue}' onclick="optClick(this)" />${options["OPT001"].optionValue}
										</span>
										</c:if><!-- 기안 -->
										<c:if test='${options["OPT017"].useYn == "Y"}'> 
										<span class="range"  >
										<input id='ART053' name="options1" type="radio"  value='<%=appCode.getProperty("ART053", "ART053", "APP") %>' codeNm='${options["OPT017"].optionValue}' onclick="optClick(this)" />${options["OPT017"].optionValue}
										</span>
										</c:if><!-- 1인 전결 -->
										<c:if test='${options["OPT002"].useYn == "Y"}'>
										<span class="range"  >
										<input id='ART020' name="options1" type="radio"  value='<%=appCode.getProperty("ART020", "ART020", "APP") %>' codeNm='${options["OPT002"].optionValue}' onclick="optClick(this)" />${options["OPT002"].optionValue}
										</span>
										</c:if><!-- 검토-->
										
										<c:if test='${options["OPT051"].useYn == "Y"}'>
										<span class="range" style="width:45px"  >
										<input id='OPT051' name="OPT051" type="checkbox"  value='<%=appCode.getProperty("OPT051", "OPT051", "OPT") %>' codeNm='${options["OPT051"].optionValue}' onclick="chkClick('OPT051')" />${options["OPT051"].optionValue}
										</span>
										</c:if><!-- 발의// jth8172 2012 신결재 TF-->
										<c:if test='${options["OPT052"].useYn == "Y"}'>
										<span class="range" style="width:45px"   >
										<input id='OPT052' name="OPT052" type="checkbox"  value='<%=appCode.getProperty("OPT052", "OPT052", "OPT") %>' codeNm='${options["OPT052"].optionValue}' onclick="chkClick('OPT052')" />${options["OPT052"].optionValue}
										</span>
										</c:if><!-- 보고// jth8172 2012 신결재 TF-->
										<c:if test='${options["OPT059"].useYn == "Y"}'>
										<span class="range"  >
										<input id='ART055' name="options1" type="radio"  value='<%=appCode.getProperty("ART055", "ART055", "APP") %>' codeNm='${options["OPT059"].optionValue}' onclick="optClick(this)" />${options["OPT059"].optionValue}
										</span>
										</c:if><!-- 통보-->

										<br/>
										 
										
										<c:if test='${options["OPT003"].useYn == "Y"}'>
										<span class="range"  >
										<input id='ART030' name="options1" type="radio"  value='<%=appCode.getProperty("ART030", "ART030", "APP") %>' codeNm='${options["OPT003"].optionValue}' onclick="optClick(this)" />${options["OPT003"].optionValue}
										</span>
										</c:if><!-- 협조-->
										<c:if test='${options["OPT004"].useYn == "Y"}'>
										<span class="range" >
										<input id='ART031' name="options1" type="radio"  value='<%=appCode.getProperty("ART031", "ART031", "APP") %>' codeNm='${options["OPT004"].optionValue}' onclick="optClick(this)" />${options["OPT004"].optionValue}
										</span>
										</c:if><!-- 병렬협조-->
										<c:if test='${options["OPT005"].useYn == "Y"}'>
										<span class="range">
										<input id='ART032' name="options1" type="radio"  value='<%=appCode.getProperty("ART032", "ART032", "APP") %>' codeNm='${options["OPT005"].optionValue}' onclick="optClick(this)" />${options["OPT005"].optionValue}
										</span>
										</c:if><!-- 부서협조-->
										<c:if test='${options["OPT053"].useYn == "Y"}'>
										<span class="range">
										<input id='ART130' name="options1" type="radio"  value='<%=appCode.getProperty("ART130", "ART130", "APP") %>' codeNm='${options["OPT053"].optionValue}' onclick="optClick(this)" />${options["OPT053"].optionValue}
										</span>
										</c:if><!-- 개인순차함의// jth8172 2012 신결재 TF-->
										<c:if test='${options["OPT054"].useYn == "Y"}'>
										<span class="range">
										<input id='ART131' name="options1" type="radio"  value='<%=appCode.getProperty("ART131", "ART131", "APP") %>' codeNm='${options["OPT054"].optionValue}' onclick="optClick(this)" />${options["OPT054"].optionValue}
										</span>
										</c:if><!-- 개인병렬합의// jth8172 2012 신결재 TF-->
										<c:if test='${options["OPT055"].useYn == "Y"}'>
										<span class="range" >
										<input id='ART132' name="options1" type="radio"  value='<%=appCode.getProperty("ART132", "ART132", "APP") %>' codeNm='${options["OPT055"].optionValue}' onclick="optClick(this)" />${options["OPT055"].optionValue}
										</span>
										</c:if><!-- 부서합의// jth8172 2012 신결재 TF-->

										
									</div>
									<div id='divART050' style="height:25px;"><!-- ==============결재, 전결, 대결, 감사, 부서감사, 후열 ============================== -->
										<c:if test='${options["OPT014"].useYn == "Y"}'> 
										<span class="range"  style="width:45px"   >
										<input id='ART050' name="options1" type="radio"  value='<%=appCode.getProperty("ART050", "ART050", "APP") %>' codeNm='${options["OPT014"].optionValue}' onclick="optClick(this)" />${options["OPT014"].optionValue}
										</span>
										</c:if><!-- 결재 -->
										<c:if test='${options["OPT015"].useYn == "Y"}'>
										<span class="range" style="width:45px"   >
										<input id='ART051' name="options1" type="radio"  value='<%=appCode.getProperty("ART051", "ART051", "APP") %>' codeNm='${options["OPT015"].optionValue}' onclick="optClick(this)" />${options["OPT015"].optionValue}
										</span>
										</c:if><!-- 전결 -->
										<c:if test='${options["OPT016"].useYn == "Y"}'>
										<span class="range"  style="width:45px"  >
										<input id='ART052' name="options1" type="radio"  value='<%=appCode.getProperty("ART052", "ART052", "APP") %>' codeNm='${options["OPT016"].optionValue}' onclick="optClick(this)" />${options["OPT016"].optionValue}
										</span> 
										</c:if><!-- 대결-->
										<c:if test='${options["OPT018"].useYn == "Y"}'>
										<span class="range"  style="width:45px"  >
										<input id='ART054' name="options1" type="radio"  value='<%=appCode.getProperty("ART054", "ART054", "APP") %>' codeNm='${options["OPT018"].optionValue}' onclick="optClick(this)" />${options["OPT018"].optionValue}
										</span>
										</c:if><!-- 후열-->
										<!-- audittype 관련 부분  -->
										<c:if test='${options["OPT009"].useYn == "Y"}'>
										<span class="range"  >
										<input id='ART040' name="options1" type="radio"  value='<%=appCode.getProperty("ART040", "ART040", "APP") %>' codeNm='${options["OPT009"].optionValue}' onclick="optClick(this)" />${options["OPT009"].optionValue}
										</span>
										</c:if><!-- 감사-->
										<c:if test='${options["OPT021"].useYn == "Y"}'>
										<span class="range"  >
										<input id='ART045' name="options1" type="radio"  value='<%=appCode.getProperty("ART045", "ART045", "APP") %>' codeNm='${options["OPT021"].optionValue}' onclick="optClick(this)" />${options["OPT021"].optionValue}
										</span>
										</c:if><!-- 일상감사-->
										<c:if test='${options["OPT022"].useYn == "Y"}'>
										<span class="range">
										<input id='ART046' name="options1" type="radio"  value='<%=appCode.getProperty("ART046", "ART046", "APP") %>' codeNm='${options["OPT022"].optionValue}' onclick="optClick(this)" />${options["OPT022"].optionValue}
										</span>
										</c:if><!-- 준법감시-->
										<c:if test='${options["OPT023"].useYn == "Y"}'>
										<span class="range">
										<input id='ART047' name="options1" type="radio"  value='<%=appCode.getProperty("ART047", "ART047", "APP") %>' codeNm='${options["OPT023"].optionValue}' onclick="optClick(this)" />${options["OPT023"].optionValue}
										</span>
										</c:if><!-- 감사위원-->
										<c:if test='${options["OPT010"].useYn == "Y"}'>
										<span class="range">
										<input id='ART041' name="options1" type="radio"  value='<%=appCode.getProperty("ART041", "ART041", "APP") %>' codeNm='${options["OPT010"].optionValue}' onclick="optClick(this)" />${options["OPT010"].optionValue}
										</span>
										</c:if><!-- 부서감사-->
										<!--<c:if test='${audittype == "Y" || audittype == "U"}'>--><!-- audittype = Y 일때만 필요 -->
										<!--</c:if>-->

									</div>
				<%}else{ %>	
									<div id='divART010' style="height:30px;"><!-- ==============이중결재 : 기안 , 1인전결, 검토, 협조============================== -->
										<c:if test='${options["OPT001"].useYn == "Y"}'>
										<span class="range" >
										<input id='ART010' name="options1" type="radio"  value='<%=appCode.getProperty("ART010", "ART010", "APP") %>' codeNm='${options["OPT001"].optionValue}' onclick="optClick(this)" />${options["OPT001"].optionValue}
										</span>
										</c:if><!-- 기안 -->
										<c:if test='${options["OPT017"].useYn == "Y"}'> 
										<span class="range">
										<input id='ART053' name="options1" type="radio"  value='<%=appCode.getProperty("ART053", "ART053", "APP") %>' codeNm='${options["OPT017"].optionValue}' onclick="optClick(this)" />${options["OPT017"].optionValue}
										</span>
										</c:if><!-- 1인 전결 -->
										<c:if test='${options["OPT002"].useYn == "Y"}'>
										<span class="range"  >
										<input id='ART020' name="options1" type="radio"  value='<%=appCode.getProperty("ART020", "ART020", "APP") %>' codeNm='${options["OPT002"].optionValue}' onclick="optClick(this)" />${options["OPT002"].optionValue}
										</span>
										</c:if><!-- 검토-->
										
										<!-- 20160419 -->
										<c:choose>
										<c:when test='${options["OPT003"].useYn == "Y" && formName != "QBA-0000-01 회사표준갑지" && formName != "QBB-0000-01 회사표준갑지" && formName != "QBA-0000-01 회사표준 갑지" && formName != "QBB-0000-01 회사표준 갑지"}'>
										<span class="range"  >
										<input id='ART030' name="options1" type="radio"  value='<%=appCode.getProperty("ART030", "ART030", "APP") %>' codeNm='${options["OPT003"].optionValue}' onclick="optClick(this)" />${options["OPT003"].optionValue}
										</span>
										</c:when>
										<c:otherwise>
										<span class="range">
										<input id='ART131' name="options1" type="radio"  value='<%=appCode.getProperty("ART131", "ART131", "APP") %>' codeNm='${options["OPT054"].optionValue}' onclick="optClick(this)" />${options["OPT054"].optionValue}
										</span>
										<span class="range">
										<input id='ART130' name="options1" type="radio"  value='<%=appCode.getProperty("ART130", "ART130", "APP") %>' codeNm='${options["OPT053"].optionValue}' onclick="optClick(this)" />${options["OPT053"].optionValue}
										</span>
										</c:otherwise>
										</c:choose><!-- 협조-->
										
									</div>
									<div id='divART050' style="height:30px;"><!-- =============이중결재 : 기안 , 1인전결, 검토, 협조 ============================== -->
										<c:if test='${options["OPT014"].useYn == "Y"}'> 
										<span class="range"  >
										<input id='ART050' name="options1" type="radio"  value='<%=appCode.getProperty("ART050", "ART050", "APP") %>' codeNm='${options["OPT014"].optionValue}' onclick="optClick(this)" />${options["OPT014"].optionValue}
										</span>
										</c:if><!-- 결재 -->
										<c:if test='${options["OPT015"].useYn == "Y"}'>
										<span class="range"  >
										<input id='ART051' name="options1" type="radio"  value='<%=appCode.getProperty("ART051", "ART051", "APP") %>' codeNm='${options["OPT015"].optionValue}' onclick="optClick(this)" />${options["OPT015"].optionValue}
										</span>
										</c:if><!-- 전결 -->
										<c:if test='${options["OPT016"].useYn == "Y"}'>
										<span class="range">
										<input id='ART052' name="options1" type="radio"  value='<%=appCode.getProperty("ART052", "ART052", "APP") %>' codeNm='${options["OPT016"].optionValue}' onclick="optClick(this)" />${options["OPT016"].optionValue}
										</span>
										</c:if><!-- 대결-->
										<span class="range">
										<input id="lineNum1" name="lineNum" type="radio"  value="1" onclick="optClick(this)" codeNm='<spring:message code="approval.form.reqdept" />' checked="checked" /><spring:message code="approval.form.reqdept" />
										</span>
										<span class="range">
										<input id="lineNum2" name="lineNum" type="radio"  value="2" onclick="optClick(this)" codeNm='<spring:message code="approval.form.recdept" />' /><spring:message code="approval.form.recdept" />
										</span>
									</div>
				<%} %>
								</td>
								<td width="5"></td>
							</tr>
							<tr>
								<td height="2" colspan="3"></td>
							</tr>
						</table>
					</td>
				</tr>
			</acube:tableFrame>
			
			<!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<!-- 여백 끝 -->
			<table border='0' cellpadding='0' cellspacing='0' width="100%">
				<tr>
				<td width="100%" height="23" align="right" valign="bottom">
					<acube:buttonGroup align="right">
					<acube:button id="adddeptBtn" disabledid="" type="right" onclick="onAddDept()" value="<%=adddeptBtn%>" />
					<acube:space between="button" />
					<acube:button id="addlineBtn" disabledid="" type="right" onclick="onAddLine();" value="<%=addlineBtn%>" />
					<acube:space between="button" />
					<acube:button id="dellineBtn" disabledid="" type="left" onclick="onDeleteLine();" value="<%=dellineBtn%>" />
					</acube:buttonGroup>
				</td>
				</tr>
			</table>
			
			</div>
			<!-- 결재경로 끝 -->
			
			<!-- ////////// 결재경로그룹 시작 ////////// 2011.06.01 신경훈 -->
			<div id="divAppLineGroup" group="appLine" style="display:none;">
			
				<div id="divAppLineGroupList" style="float:left; width:45%;">
					<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
						<tr> 
							<td height="235" class="basic_box" style="padding:2px;" valign="top">
									
								<!-- 결재경로그룹 목록 -->								
								<table cellpadding="0" cellspacing="0" width="100%" class="table" style="table-layout:fixed;">
									<tr>
										<td width="180" class="ltb_head"><spring:message code="env.option.form.03"/></td>
										<td width="*" class="ltb_head"><spring:message code="env.option.form.06"/></td>
									</tr>
								</table>
								<div style="height:228px; overflow-x:hidden; overflow-y:scroll; background-color:#ffffff;">
								<table id="tblAppLineGroup" cellpadding="0" cellspacing="0" width="100%" class="table_body table_under" style="table-layout:fixed;">
									<tbody>
									
									<c:forEach var="vo" items="${gList}">
										<tr id="${vo.lineGroupId}" name="${vo.lineGroupId}" onClick='onAppLineGroupClick($("#${vo.lineGroupId}"));' onDblClick='onAppLineGroupDblClick($("#${vo.lineGroupId}"));' style="background-color:#ffffff;">
											<td width="180" class="ltb_left" style="text-overflow:ellipsis;overflow:hidden;" title="${vo.lineGroupName}"><nobr>${vo.lineGroupName}</nobr></td>
											<td width="*" class="ltb_center"><spring:message code="env.grouptype.${vo.groupType}"/></td>											
										</tr>
									</c:forEach>									
									</tbody>
								</table>
								</div>
								
							</td>
						</tr>
					</acube:tableFrame>
				</div>
				<div id="divAppLineList" style="float:right; width:53%;">
					<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
						<tr>
							<td height="240" class="basic_box" style="padding:2px;">
							
								<!-- 결재경로 -->								
								<table cellpadding="0" cellspacing="0" width="100%" class="table" style="table-layout:fixed;">
									<tr>
										<td width="73" class="ltb_head"><spring:message code="env.option.form.07"/></td>
										<td width="73" class="ltb_head"><spring:message code="env.option.form.08"/></td>
										<td width="73" class="ltb_head"><spring:message code="env.option.form.09"/></td>
										<td width="*" class="ltb_head"><spring:message code="env.option.form.06"/></td>
									</tr>
								</table>
								<div style="height:228px; overflow-x:hidden; overflow-y:scroll; background-color:#ffffff;">
								<table id="tblAppLine" cellpadding="0" cellspacing="0" width="100%" class="table_body table_under" style="table-layout:fixed;">
									<tbody />
								</table>
								</div>	
								
								
							</td>
						</tr>
					</acube:tableFrame>
				</div>
				
			<!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<!-- 여백 끝 -->
			<table border='0' cellpadding='0' cellspacing='0' width="100%">
				<tr>
				<td width="100%" height="23" align="right" valign="bottom">
					<acube:buttonGroup align="right">
					<acube:button id="addlineBtn" disabledid="" type="right" onclick="appLineGroupAdd();" value="<%=addlineBtn%>" />
					<acube:space between="button" />
					<acube:button id="dellineBtn" disabledid="" type="left" onclick="onDeleteLine();" value="<%=dellineBtn%>" />
					</acube:buttonGroup>
				</td>
				</tr>
			</table>
			
			</div>
			<!-- ////////// 결재경로그룹 시작 ////////// 2011.06.01 신경훈 -->
			
			<!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<!-- 여백 끝 -->
			<!-- 결재 경로 -->
			<table  width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="td_table table_border">
							<tr>
								<td width="100%" height="100%">
									<div>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<thead>
											<TR><TD width="123" class="ltb_head" style="border-top:none;border-bottom: <%=tbColor%> 1px solid;"><spring:message code="approval.table.title.sosok" /></TD>
												<TD width="123"  class="ltb_head"  style='border-left:1pt <%=tbColor%> solid; border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.gikwei" /></TD>
												<TD width="123"  class="ltb_head"  style='border-left:1pt <%=tbColor%> solid; border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.name" /></TD>
												<TD class="ltb_head"  style='border-left:1pt <%=tbColor%> solid; border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.gubun" /></TD>
											</TR>
										</thead>
									</table>
									</div>
									<div style="height:120px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF;">
									<table id="tbApprovalLines" width="100%" border="0" cellpadding="0" cellspacing="0">
										<tbody />
									</table>
									</div>
								</td>
							</tr>
						</acube:tableFrame>
					</td>
					<td width="5"></td>
					<td width="2%">
						<TABLE cellpadding="0" cellspacing="0" width="2%">
							<TR>
								<TD style="background:#ffffff" align="center" valign="middle">
									<IMG src="<%=webUri%>/app/ref/image/bu_up.gif" onclick="javascript:onMoveUp();return(false);"><BR /><BR />
									<IMG src="<%=webUri%>/app/ref/image/bu_down.gif" onclick="javascript:onMoveDown();return(false);">
								</TD>
							</TR>
						</TABLE>
					</td>
				</tr>
			</table>
			<!-- 여백 시작 -->
			<acube:space between="button_content" table="y"/>
			<!-- 여백 끝 -->
			
			<acube:buttonGroup align="right">
			<acube:button id="sendOk" disabledid="" onclick="sendOk();" value="<%=confirmBtn%>" />
			<acube:space between="button" />
			<acube:button id="sendCalcel" disabledid="" onclick="closePopup();" value="<%=closeBtn%>" />
			</acube:buttonGroup>		
		</acube:outerFrame>
		</td>
		
		</tr>
		</table>
			
		<div id="divPop" style="display: none;position: absolute;">
		<table id="sameUser">
			<tbody></tbody>
		</table>
		</div>
</body>	
</html>