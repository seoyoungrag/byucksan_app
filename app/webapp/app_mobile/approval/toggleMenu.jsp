<%@page import="java.util.List"%>
<%@page import="com.sds.acube.app.common.util.CommonUtil"%>
<%@page import="com.sds.acube.app.common.vo.UserVO"%>
<%@page import="com.sds.acube.app.common.service.IOrgService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String loginId = (String) session.getAttribute("LOGIN_ID");
IOrgService orgService = (IOrgService)ctx.getBean("orgService");
List<UserVO> concurrentList = orgService.selectConcurrentUserListByLoginId(loginId);

%>
<script>
//겸직 변경
function changeConcurrent(val){
	//location.href = "<%=webUri%>/app/login/loginProcessByConcurrentForMobile.do?concurrentUserId=" + val + "&D1=<%=D1%>";
	location.href = "<%=webUri%>/app/login/loginProcessByConcurrentForMobile.do?concurrentUserId=" + val;
}
</script>
<div class="main_nav sidebar-nav hidden">
	<div class="sub_top">
		<a href="#none" class="closebtn"></a>
		<p class="title">전자결재</p>
	</div>
	<ul class="nav nav-list">     
		<% if(concurrentList.size()>1) { %>
			<li class="sub active open">
				<a href="#none"><span><img src="<%=webUri%>/app/ref/mobile/img/icon_msn.png"> 겸직</span></a>
				<ul class="sub active open">  
					<%
							for(int i=0; i<concurrentList.size(); i++) {
							    UserVO userVO = concurrentList.get(i);
							    String displayPosition = CommonUtil.nullTrim(userVO.getDisplayPosition());
					%>
								<li <%=userId.equals(userVO.getUserUID())?"style='background-color:rgb(170, 220, 255)'":""%>><a style="cursor:pointer;" onclick ="changeConcurrent('<%= userVO.getUserUID() %>');"><span><%=userVO.getCompName()%> - <%=userVO.getDeptName()%>[<%=userVO.getDisplayPosition()%>]</span></a></li>
					<%
							 }
					%>
				</ul>
			</li>  
       	<% } %>
		<li class="sub active open">
			<a href="#none"><span><img src="<%=webUri%>/app/ref/mobile/img/icon_msn.png"> 전자결재 <%-- <img src="<%=webUri%>/app_mobile/img/icon_new.png" style="width:10px;height:auto;"> --%></span></a>
			<ul class="sub active open">
				<li><a style="cursor:pointer;" onclick ="goApprovalBox(0)"><span>- 수신함</span></a></li>
				<li><a style="cursor:pointer;" onclick ="goApprovalBox(1)"><span>- 진행함</span></a></li>
				<li><a style="cursor:pointer;" onclick ="goApprovalBox(2)"><span>- 완료함</span></a></li>
				<li><a style="cursor:pointer;" onclick ="goApprovalBox(3)"><span>- 기안함</span></a></li>
				<li><a style="cursor:pointer;" onclick ="goApprovalBox(4)"><span>- 후열함</span></a></li>
				<li><a style="cursor:pointer;" onclick ="goApprovalBox(5)"><span>- 반려함</span></a></li>
			</ul>
		</li>                          
		<li class="sub active open">
			<a href="#none"><span><img src="<%=webUri%>/app/ref/mobile/img/icon_msn.png"> 공람게시 </span></a>
			<ul class="sub active open">
				<li><a style="cursor:pointer;" onclick ="goApprovalBox(6)"><span>- 부서</span></a></li>
				<li><a style="cursor:pointer;" onclick ="goApprovalBox(7)"><span>- 회사</span></a></li>
			</ul>
		</li>
	</ul>
<div class="nav_bottom"><img src='<%=iam_url%>/acube/iam/identity/userinfoimage/Select.go?requestImage=picture&userId=<%=userId%>' alt='이미지' class='circleImg'/> <%=userName%> <%=userPos%>(<%=userDeptName%>)</div>
</div>	
	