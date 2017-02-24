<%@page import="com.sds.acube.app.common.util.AppConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<script type="text/javascript">
/**
 * 메신져실행
 */
 function openApp(){
    var openAt = new Date,
	uagentLow = navigator.userAgent.toLocaleLowerCase(),
	chrome25,
	kitkatWebview;
	    
	     // 안드로이드는 iframe 에서 실행됨
	if($("#____sorilink____").length <= 0){
		$("body").append("<iframe id='____sorilink____'></iframe>");
	}
	$("#____sorilink____").hide();
	
	// 4초안에 액션이 일어나지 않으면 플랫폼별로 스토어로 연결
	/*setTimeout( function() {
	  if (new Date - openAt < 4000) {
	      if (uagentLow.search("android") > -1) {
	             // 구글 스토어 주소
	          $("#____sorilink____").attr("src","market://details?id=com.soribada.android&hl=ko");
	      } else if (uagentLow.search("iphone") > -1) {
	             // 아이폰 스토어 주소
	          location.replace("https://itunes.apple.com/kr/app/solibada-myujig-mujehan-eum/id346528006?mt=8");
	      }
	  }
	}, 1000);
	*/
	
	if(uagentLow.search("android") > -1 || uagentLow.search("iphone") > -1){
	    document.location.href = "messenger://AtSmart";
	    //document.location.href = "http://www.atmessenger.co.kr/mobile/index.html";
	}
 }
 
 function getMailCount(){
	 $.ajax({
			type:'post',
			timeout: 5000,
			//async:false,
			dataType:'json',
			url:'<%=AppConfig.getProperty("portal_url", "http://smart.bsco.co.kr", "portal")%>/luxor_collaboration/wrk/wrkWork.do?method=getMailCount&userProfile.loginId=<%=userId%>',  
			success:function(data) {
				if(data.returnCode == '0'){ // 메일 카운트 가져오기 성공
					$('#mailCount').text(data.result);
				}else{
					$('#mailCount').text("-1");
				}
			},
			error:function(data,sataus,err) {						
				//alert("메일 카운트를 가져 오는데 실패 했습니다.");\
			}
		});  	


}
 
 function getApprovalProcessCount(){
	 $.ajax({
			type:'post',
			timeout: 5000,
			//async:false,
			dataType:'json',
			url:'<%=AppConfig.getProperty("portal_url", "http://smart.bsco.co.kr", "portal")%>/luxor_collaboration/bbs/approval.do?method=processCount&userUid=<%=userId%>',
			success:function(data) {
				$('#approvalCount').text(data.count);
			},
			error:function(data,sataus,err) {						
					alert("결재 진행함 카운트를 가져 오는데 실패 했습니다.");
			}
		});  	
} 
</script>



<!-- Footer -->
<footer id="footer">
	<a href="<%=AppConfig.getProperty("portal_url", "http://smart.bsco.co.kr", "portal")%>/luxor_collaboration/mobile/main.do?method=main" class="footmenu_01"><p>홈</p></a>
	<a href="<%=AppConfig.getProperty("portal_url", "http://smart.bsco.co.kr", "portal")%>/luxor_collaboration/mobile/bbs/bbsMyBoardSet.do?method=latestpostlst" class="footmenu_07"><p>게시판</p></a>
	<a href="<%=AppConfig.getProperty("url", "http://deepmail.bsco.co.kr:8080", "mail")%>/mobile/mobileGate.ds?act=sso&noPw=1&id=<%=userId%>" class="footmenu_06"><p>메일</p><!-- <span id="mailCount"></span> --></a>
	<a href="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/list/webservice/approval/receive.do?D1=<%=D1%>" class="footmenu_08"><p>결재</p><!-- <span id="approvalCount"></span> --></a>
	<a href="javascript:openApp()" class="footmenu_04"><p>알림</p></a>
</footer>
		<!-- //Footer:END -->
		
	