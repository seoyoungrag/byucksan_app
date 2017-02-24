<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/app/jsp/common/header.jsp" %>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>          
<!DOCTYPE html>
<% 
String D1 = (String) request.getParameter("D1");

AppDocVO appDocVO = (AppDocVO) request.getAttribute("appDocVO");


String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
String userName = (String) session.getAttribute("USER_NAME"); // 사용자 이름
String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
String userDeptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서 이름
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
<meta http-equiv="expires" content="-1"/>
<meta http-equiv="Cache-Control" content="No-Cache"/>
<meta http-equiv="Pragma" content="No-Cache"/>
<title>벽산 전자결재</title>
<link rel="shortcut icon" href="favicon.ico">
<link rel="apple-touch-icon-precomposed" href="icon57.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="icon114.png">

<script type="text/javascript" charset="utf-8" src="<%=webUri%>/app_mobile/js/libs/jquery-2.0.2.min.js"></script><!-- jQuery -->
<script type="text/javascript" src="<%=webUri%>/app_mobile/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=webUri%>/app_mobile/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app_mobile/js/jquery.js"></script>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app_mobile/css/common.css">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app_mobile/css/button.css">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app_mobile/css/font_icon.css">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app_mobile/css/community/style.css">

<script>
var d1 = "<%=D1%>";
$(document).ready(function() {
//	getRejectList();
	$("#searcharea").hide();
});

function getRejectList() {

 	 $.ajax({
			type:'post',
			timeout: 5000,
			async:false,
			dataType:'json',
			url:'/ep/app/list/webservice/approval/ListApprovalreject.do?D1='+D1,
			success:function(data) {
				if(data.webServiceStatus == 200){
					setRejectList(data);
					myScroll.refresh();
				}
			},
			error:function(data,sataus,err) {						
					alert("리스트 가져오기 실패");
			}
		});  
		
		
}

function setRejectList(data){
	var addHtml = "";
	$("#approvalRejectList").html("");
 	if(data.ListVos.length == 0){
		addHtml += "<li class='group'><a href=''>";
		addHtml += "<div class='rf'><p class='title'>반려문서 목록이 없습니다.</p></div></a>";		
	}else{
				
		for(var i = 0; i < data.ListVos.length; i++){
			addHtml += "<li class='group'>";
			addHtml += "<a href=''>";
			addHtml += "<img src='../img/proimg_sample.png' alt='이미지' class='circleImg'/>";
			addHtml += "<div class='rf'>";
			addHtml += "<p class='title' style='cursor:pointer;' onclick=\"viewAppDoc('"+data.ListVos[i].docid+"')\">"+ data.ListVos[i].title +"</p>";
			addHtml += "<p class='status'><span>"+data.ListVos[i].draftername+"</span><span>|</span><span>"+data.ListVos[i].draftdate+"</span></p>" ;
			addHtml +=	"</div></a><div class='signWrap'><button class='signBtn'>반려</button></div></li>";		
		} 
	}		 
	$("#approvalRejectList").html(addHtml);	
	$("#listCount").html(data.pageTotalCount);
	$("#listTotalCount").html(data.totalCount);
	if(data.SearchType == "searchTitle"){
		$("#selectType").val("searchTitle").attr("selected", "selected");	
	}else if(data.SearchType =="searchDrafter"){
		$("#selectType").val("searchDrafter").attr("selected", "selected");
	}
	if(data.SearchWord != null || data.SearchWord != ""){
		$("#searchKeyWord").val(data.SearchWord);	
	}
}

function viewAppDoc(docid){

	document.location.href = '/app_mobile/app_mobile/approval/approval_selectProcessAppDoc.jsp?docId='+docid+'&D1='+d1+'&lobCode=LOB003';				
	
}

function listSearch(){

	 $.ajax({
			type:'post',
			timeout: 5000,
			async:false,
			dataType:'json',
			data : {
				searchType : $("#selectType").val(),
				searchWord : $("#searchKeyWord").val()
			},
			url:'/ep/app/list/webservice/approval/ListApprovalreject.do',
			success:function(data) {
				if(data.webServiceStatus == 200){
					setRejectList(data);
				}
			},
			error:function(data,sataus,err) {						
					alert("로그인 실패 했습니다.");
			}
		}); 
	
}

function goApprovalBox(index){
	
	switch(index){
	case 0:
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_receiveBox.jsp?D1='+d1;				//수신함
		break;
	case 1:
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_processBox.jsp?D1='+d1;				//진행함
		break;
	case 2:
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_completeBox.jsp?D1='+d1;				//완료함
		break;
	case 3:
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_draftBox.jsp?D1='+d1;				//기안함
		break;
	case 4:
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_rearBox.jsp?D1='+d1;					//후열함
		break;
	case 5:
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_rejectBox.jsp?D1='+d1;				//반려함
		break;
	case 6:
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_displayNotice.jsp?readRange=DRS002&D1='+d1;				//공람게시 (부서)
		break;
	case 7:
		document.location.href = '<%=webUri%>/app_mobile/approval/approval_displayNotice.jsp?readRange=DRS005D1='+d1;				//공람게시 (회사)
		break;
	}
}

function insertopinion(){
	
	$("#opinion").val($("#opinionText").val());
		
	$("#opinionform")
	.attr("action","<%=webUri%>/app/list/webservice/approval/selectAppDoc.do")
	.attr('method', 'post')
	.submit();
	
}



</script>
</head>
<body>
<form id="opinionform" name="opinion">
	<input type="hidden" id="docId" name="docId" value=<%=request.getAttribute("docId")%>>
	<input type="hidden" id="opinion" name="opinion" value=""/>
	<input type="hidden" id="lobCode" name="lobCode" value="LOB003">
	<input type="hidden" id="isreceiveBox" name="isreceiveBox" value="Y" />
	<input type="hidden" id="D1" name="D1" value=<%=request.getAttribute("D1")%> />
</form>

	<header>
		<%@ include file="/app_mobile/approval/toggleMenu.jsp" %> 
	</header>
	
	<div class="public_sub_top">
		<div class="nav_btn"><a href="#none" ><img src="../img/community/nav_btn.png" alt="메뉴"></a></div>
		<div class="title">전자결재</div>
		<div class="search"></div>
	</div>
	<div id="wrapper" style="overflow:hidden;">
		<div id="scroller">
		     <div class="titleBar">
		     <p class="subTitle">결재의견입력</p>
		     </div>
		    <div class="sub_content">
				<ul class="list V_Limg_list Rdiv group">
					<li class="group">
						<a href="community.html"><img src="<%=AppConfig.getProperty("iam_url", "http://smart.bsco.co.kr", "organization")%>/acube/iam/identity/userinfoimage/Select.go?requestImage=picture&userId="<%=appDocVO.getDrafterId()%> alt='이미지' class='circleImg'/>
							<div class="rf">
								<p class="title"><%=appDocVO.getTitle()%></p>
								<p class="status">
		                        <span><span><%=appDocVO.getDrafterName()%></span>&nbsp;<span><%=appDocVO.getDrafterPos()%></span><span>(<%=appDocVO.getDrafterDeptName()%>)</span></span>
		                        <span><%=appDocVO.getDraftDate()%></span>
		                        </p>
							</div>
							<div class="rsf"></div>
						</a>
					</li>
				</ul>
		       </div>
		         
		        <div class="list_write">
		        <textarea placeholder="의견을 작성해 주십시오." class="input_content" id="opinionText"></textarea>
					
		             <div class="btnset_r">
						<a href="#none" class="cancel_btn">취소</a>&nbsp;
						<a href="javascript:insertopinion()" class="confirm_btn" >입력</a>
					</div>
		            </div>
		            
		            </div>
		            
		            </div>
		
		<!-- <div class="moreNtop">
				<a href="#none" class="more_btn"><span class="txt">20개 더보기</span>&nbsp;&nbsp;<span class="info">20 / 55</span></a>
				<a href="#none" class="top_btn"><img src="../img/community/top_btn.png"></a>
			</div> -->
		</div>
	</div>
<%@ include file="/app_mobile/approval/footer.jsp" %>
</body>
</html>