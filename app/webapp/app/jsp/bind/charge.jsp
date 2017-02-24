<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.common.vo.UserVO"%>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 아이디

MessageSource m = messageSource;

String tbColor = "#E3E3E3";

List<UserVO> results = (List<UserVO>)request.getAttribute("deptUsers");
%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code='bind.title.charge' /></title>

<link rel="stylesheet" type="text/css"
	href="<%=webUri%>/app/ref/css/main.css" />
<link rel="stylesheet" type="text/css"
	href="<%=webUri%>/app/ref/css/icons.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript"
	src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>

<!-- 다국어 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 추가 종료 -->

<jsp:include page="/app/jsp/common/common.jsp" />

<SCRIPT LANGUAGE="javascript">
			var isCtrl = false, isShift = false; //keyChecked			
			var arrRet = new Array();			
			
			var g_selector = function(){
								
				var selector = new Array();				
				var bgcolors = new Array();
				
				var idx = 0,  mtype = 0;
				
				var bfontColor = "#707070";
				var afontColor = "#3333ff";
				
				return {
					add : function(obj, color){						
						var ckobj = false;
									
						for(var i = 0; i < idx; i++){				
							if(selector[i].attr("id") == obj.attr("id")){
								ckobj = true;
								break;
							}
						}
						
						if(!ckobj){
							selector[idx] = obj;
							bgcolors[idx] = obj.css('background-color');
							obj.css('background-color',color);
							obj.children().css('color',afontColor);
							idx++;
						}
					}, 
					collection : function(){
						return selector;
					},
					count : function(){
						return selector.length;
					},
					restore : function(){
						
						for(var i =0; i< idx;i++){
							//alert(bgcolors[i]);
							selector[i].css('background-color',bgcolors[i]);
							selector[i].children().css('color',bfontColor);
						}
						this.removeAll();
					},
					restoreId : function(id){
						for(var i =0; i< idx;i++){
							if(selector[i].attr("id") !== id){
								selector[i].css('background-color',bgcolors[i]);
								break;
							}
						}
					},
					removeAll : function(){
						selector = null;
						selector = new Array();
						idx = 0;
						mtype = 0;
					},
					removeId : function(id){
						var tmpArr = new Array();
						var colorArr = new Array();
			
						var tempCnt = 0;
						for(var i =0; i< idx;i++){
							if(selector[i].attr("id") !== id){
								tmpArr[tempCnt] 	= selector[i];
								colorArr[tmpCnt] 	= bgcolors[i];
								tempCnt++;
							}
						}
						
						selector = tmpArr;
						bgcolors = colorArr;
						idx = tmpArr.length;
					},
					last: function(){
						if(idx > 0){
							return selector[idx-1];
						}
			
						return null;
					},
					first: function(){
						if(selector.length > 0){
							return selector[0];
						}
			
						return null;
					},
					setType:function(ntype){
						mtype = ntype;
					},
					getType:function(){
						return mtype;
					}
				};
			}
			
			var gSaveObject = g_selector();
			var gSaveLineObject = g_selector();
			var sColor = "#F2F2F4";
			var tbColor = "<%=tbColor%>";

	$(document).bind('keyup', function(event) {
		var keyCode = event.which;
		if (keyCode === 17)
			isCtrl = false;
		if (keyCode === 16)
			isShift = false;

	});

	$(document).bind('keydown', function(event) {
		var keyCode = event.which;
		if (keyCode === 17)
			isCtrl = true;
		if (keyCode === 16)
			isShift = true;

	});

	//사원 더블클릭시 발생하는 이벤트
	function onListDblClick(obj) {
		document.selection.empty();
		sendOk();
	}

	function sendOk() {

		var trUser = gSaveObject.first();
		var user = new Object();
		user.userUid = trUser.attr('userUid');
		user.userName = trUser.attr('userName');

		if (opener != null) {
			opener.addCharge(user);
			window.close();
		}
	}

	//사원 선택시 발생하는 이벤트 
	//parameter obj : 선택된 tr 의 jquery 객체이다.
	function onListClick(obj) {
		//alert($('deptUsers').length);
		selectOneElement(obj);
		gSaveLineObject.restore();
	}
	//마우스 이동시 발생하는 이벤트
	function onListMouseMove() {

		try {
			document.selection.empty();
		} catch (error) {
		}
	}

	//키를 선택했을 때 발생하는 이벤트
	function onListKeyDown(obj) {
		var objTmp = null;
		var checkEvn = false;

		switch (event.keyCode) {
		case 37:
		case 38: {
			objTmp = obj.prev();
			checkEvn = true;
			break;
		}
		case 39:
		case 40: {
			objTmp = obj.next();
			checkEvn = true;
			break;
		}
		case 13: {
			//			obj.trigger('dblclick');
			//			obj.children()[0].focus();
			break;
		}
		}
		if (checkEvn) {
			if (objTmp.children().length > 0) {
				selectOneElement(objTmp);
			}
		}
	}

	//하나의 셀을 선택했을 수행하는 이벤트
	//parameter obj : 선택된 tr 의 jquery 객체이다.
	function selectOneElement(obj) {

		if ((!isCtrl && !isShift) || (isCtrl && isShift)) {
			$('document').empty();
			gSaveObject.restore();
			gSaveObject.add(obj, sColor);
		} else {
			if (isCtrl) {
				$('document').empty();
				gSaveObject.add(obj, sColor);
			}

			if (isShift) {

				var num1 = 0, num2 = 0, sNum = 0, eNum = 0, ktype = 0;

				num1 = new Number(gSaveObject.first().attr("rowOrd"));
				num2 = new Number(obj.attr("rowOrd"));

				if (num1 >= num2) {
					ktype = 1;
					num1 = new Number(gSaveObject.last().attr("rowOrd"));
				}

				sNum = (num2 > num1 ? num1 : num2);
				eNum = (num2 > num1 ? num2 : num1);

				var objs = $('#tbUsers tbody').children();
				gSaveObject.restore();
				for (var i = sNum; i <= eNum; i++) {
					var nextObj = $("#" + objs[i].id);
					gSaveObject.add(nextObj, sColor);
				}

				gSaveObject.setType(ktype);

				if (ktype == 0) {
					gSaveObject.last().children()[0].focus();
				} else {
					gSaveObject.first().children()[0].focus();
				}
			}
		}
	}
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<acube:outerFrame>
		<acube:titleBar>
			<spring:message code="bind.title.charge" />
		</acube:titleBar>
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y" />
		<!-- 여백 끝 -->
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y" />
		<!-- 여백 끝 -->

		<acube:tableFrame width="100%" border="0" cellpadding="0"
			cellspacing="0" class="">
			<tr>
				<td><acube:tableFrame width="100%" border="0" cellpadding="0"
						cellspacing="0" class="table">
						<tr>
							<td height="190" class="basic_box"
								style="padding: 0px; margin: 0px;">
								<div>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<thead>
											<TR>
												<TD width="120" class="ltb_head" style="border-top: none; border-bottom: #ADBED7 1px solid;">
													<spring:message code="approval.table.title.gikwei" />
												</TD>
												<TD width="100" class="ltb_head" style="border-left: 1pt solid #ADBED7; border-bottom: #ADBED7 1px solid;">
													<spring:message code="approval.table.title.name" />
												</TD>
												<TD class="ltb_head" style='border-left: 1pt solid #ADBED7; border-bottom: #ADBED7 1px solid;'>
													<spring:message code="approval.table.title.dept" />
												</TD>
											</TR>
										</thead>
									</table>
								</div>
								<div
									style="height: 162px; overflow-x: hidden; overflow-y: auto; background-color: #FFFFFF;">
									<table id="tbUsers" width="100%" border="0" cellpadding="0"
										cellspacing="0">
										<c:forEach items="${deptUsers}" var="user">
											<tr id="${user.userUID}" userName="${user.userName}"
												userUid="${user.userUID }"
												onclick='onListClick($("#${user.userUID}"));'
												onmouseover='onListMouseMove();'
												onkeydown='onListKeyDown($("#${user.userUID}"));'
												ondblclick='onListDblClick($("#${user.userUID}"));'>
												<td class='ltb_center'
													style='border-bottom: #ADBED7 1px solid;' width='113'>${user.positionName}&nbsp;</td>
												<td class='ltb_center'
													style='border-bottom: #ADBED7 1px solid; border-left: 1pt solid #ADBED7;'
													width='93'>${user.userName}&nbsp;</td>
												<td class='ltb_center'
													style='border-bottom: #ADBED7 1px solid; border-left: 1pt solid #ADBED7; border-right: 1pt solid #ADBED7;'>${user.deptName}&nbsp;</td>

											</tr>
										</c:forEach>
									</table>
								</div>
							</td>
						</tr>
					</acube:tableFrame></td>
			</tr>
		</acube:tableFrame>
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y" />
		<!-- 여백 끝 -->
		<acube:buttonGroup align="right">
			<acube:button id="sendOk" disabledid="" onclick="sendOk();"
				value='<%=m.getMessage("bind.button.ok", null, langType)%>' />
			<acube:space between="button" />
			<acube:button id="sendCalcel" disabledid=""
				onclick="javascript:window.close();"
				value='<%=m
							.getMessage("bind.button.cancel", null, langType)%>' />
		</acube:buttonGroup>
	</acube:outerFrame>
</body>
</html>
