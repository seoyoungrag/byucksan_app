<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>         
    <%@ include file="/app/jsp/common/header.jsp" %>
<!DOCTYPE html>
<% 
String D1 = (String) request.getParameter("D1");
String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
String userName = (String) session.getAttribute("USER_NAME"); // 사용자 이름
String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
String userDeptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서 이름
String iam_url = AppConfig.getProperty("iam_url", "", "organization");
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

<script type="text/javascript" charset="utf-8" src="<%=webUri%>/app/ref/mobile/js/libs/jquery-2.0.2.min.js"></script><!-- jQuery -->
<script type="text/javascript" src="<%=webUri%>/app/ref/mobile/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=webUri%>/app/ref/mobile/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/mobile/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/mobile/js/iscroll.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/mobile/js/appCodeMessage.js"></script>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/mobile/css/common.css">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/mobile/css/button.css">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/mobile/css/font_icon.css">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/mobile/css/community/style.css">


<script>
var d1 = "<%=D1%>";
var pageSetting = 0;
var processCount = 0;
var addHtml = "";
var moreFlag = true;
$(document).ready(function() {
	getRejectList();
	$("#searcharea").hide();
});

function getRejectList() {
	if(moreFlag != false){
		pageSetting++;
 	 $.ajax({
			type:'post',
			timeout: 5000,
			async:true,
			dataType:'json',
			data : {
				searchType : $("#selectType").val(),
				searchWord : $("#searchKeyWord").val(),
				D1 : d1,
				cPage : pageSetting
			},
			url:'<%=webUri%>/app/list/webservice/approval/ListApprovalreject.do',
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
	}else{
		alert("더이상 목록이 없습니다.");
	}
		
}

function setRejectList(data){
	
	if(pageSetting == 1){
		$("#approvalRejectList").html("");
	}
	
 	if(data.ListVos.length == 0){
		addHtml += "<li class='group'><a href=''>";
		addHtml += "<div class='rf'><p class='title'>반려문서 목록이 없습니다.</p></div></a>";		
	}else{
				
		for(var i = 0; i < data.ListVos.length; i++){
			var docStatus = getAppCode(data.ListVos[i].docstate);
			addHtml += "<li class='group'>";
			addHtml += "<a>";
			addHtml += "<img src='<%=iam_url%>/acube/iam/identity/userinfoimage/Select.go?requestImage=picture&userId="+data.ListVos[i].drafterid+"' alt='이미지' class='circleImg'/>";
			addHtml += "<div class='rf'>";
			addHtml += "<p class='title' style='cursor:pointer;' onclick=\"viewAppDoc('"+data.ListVos[i].docid+"')\">"+ data.ListVos[i].title +"</p>";
			addHtml += "<p class='status'><span>"+data.ListVos[i].draftername+"</span><span>|</span><span>"+data.ListVos[i].draftdate+"</span></p>" ;
			addHtml +=	"</div></a><div class='signWrap'><button class='signBtn'>"+docStatus+"</button></div></li>";		
		} 
	}		 
 	
 	processCount += data.ListVos.length;
	$("#approvalRejectList").html(addHtml);	
	$("#listCount").html(processCount);
	/* $("#listCount").html(data.pageTotalCount); */
	$("#listTotalCount").html(data.totalCount);
	if(processCount >= data.totalCount){
		processCount = data.totalCount;
		moreFlag = false;
	}
	
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

	document.location.href = '<%=webUri%>/app/list/webservice/approval/selectAppDoc.do?docId='+docid+'&D1='+d1+'&lobCode=LOB004';				
	
}

function listSearch(){
	addHtml = "";
	processCount = 0;
	pageSetting = 0;
	moreFlag = true;
	getRejectList();
	
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
function searchArea(){
	
	 $( "#searcharea" ).animate({
		    height: "toggle"
	  }, 500, function() {
		  myScroll.refresh();
		  });	
}
</script>
</head>
<body>
	<header>
		<%@ include file="/app_mobile/approval/toggleMenu.jsp" %> 
	</header>
	
	<div class="sub_top msn_top">
		<a href="#none" class="navbtn"></a>
		<div class="title">전자결재</div>
		<div class="search"><a style="cursor:pointer;" onclick="searchArea()"><img src="<%=webUri%>/app/ref/mobile/img/community/top_search_white_btn.png" alt="검색"></a></div>
	</div>   
	<div id="wrapper" style="overflow:hidden;">
		<div id="scroller">     
		    <div class="titleBar">
		        <p class="subTitle">반려함</p>
		    </div>
		    <div class="searchBarWarp" id="searcharea">
		      <table class="grid_bar_search">
		        <colgroup>
		          <col style="width:35%;">
		          <col style="width:50%;">
		          <col style="width:15%;">
		        </colgroup>
		        <tbody>
		        <td class="bar_select">
		        	<select name="selectType" id="selectType">
		          		<option value="searchTitle">제목</option>
				        <option value="searchDrafter">기안자</option>
		        	</select>
		        	</select>
		        </td>
		          <td class="bar_select"><span class="bar_textbox">
		            <input type="text" id="searchKeyWord" />
		          </span></td>
		          <td><button onClick="listSearch()" class="tdSearchbtn">검색</button></td>
		          </tbody>
		      </table>
		    </div>
		
			<div class="sub_content app">
				<ul class="list V_Limg_list Rdiv group" id="approvalRejectList">
					<!-- <li class="group">
						<a href="#a">
		                    <img src="../img/proimg_sample.png" alt="이미지" class="circleImg"/>                	
							<div class="rf">
								<p class="title">해외출장 결과 품의서 </p>
								<p class="status"><span>현재수 과장</span><span>|</span><span>2015.09.20 13:20</span></p>
							</div>
						</a>
						<div class="signWrap"><button onclick="" class="signBtn">결재완료</button></div>
					</li>
					<li class="group">
						<a href="#a">
		                	<img src="../img/proimg_sample.png" alt="이미지" class="circleImg"/>
							<div class="rf">
								<p class="title">해외출장 결과 품의서 </p>
								<p class="status"><span>현재수 과장</span><span>|</span><span>2015.09.20 13:20</span></p>
							</div>
						</a>
						<div class="signWrap"><button onclick="" class="holdBtn">보류</button></div>
					</li> -->
				</ul>
			</div>
		
			<div class="moreNtop">
				<a href="#none" class="more_bottom" onclick="getRejectList();">더보기 <span id="listCount"></span><span>/</span><span id="listTotalCount"></span></a>
				<a href="javascript:myScroll.scrollToElement(document.querySelector('#scroller .sub_content .list > li:nth-child(1)'), null, null, true)" class="top_btn"><img src="<%=webUri%>/app/ref/mobile/img/community/top_btn.png"></a>
			</div>
		</div>
	</div>
<%@ include file="/app_mobile/approval/footer.jsp" %>	
</body>
</html>