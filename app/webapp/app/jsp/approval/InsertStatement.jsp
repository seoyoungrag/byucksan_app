<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
String [][] codes = (String [][])request.getAttribute("codes");
String formName = CommonUtil.nullTrim((String)request.getAttribute("formName"));
String totalCost = CommonUtil.nullTrim((String)request.getAttribute("totalCost"));
String title = CommonUtil.nullTrim((String)request.getAttribute("title"));
String isSelect = CommonUtil.nullTrim((String)request.getAttribute("isSelect"));
int auto_no = Integer.parseInt((CommonUtil.nullTrim((String)request.getAttribute("auto_no")) =="" ? "0":(String)request.getAttribute("auto_no"))); //전산번호 따기위한 시퀀스 --> 필요없다.
int auto_no_l = Integer.parseInt((CommonUtil.nullTrim((String)request.getAttribute("auto_no_l")) =="" ? "0":(String)request.getAttribute("auto_no_l"))); //LOT번호 따기 위한 시퀀스 ->필요없다.
String dTime = (String)request.getAttribute("ddTime");  //현재월일 SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
String acc05tInfo[] = new String[10]; 
acc05tInfo = (String [])request.getAttribute("acc05tInfo"); //이미 저장되어 있을 때 = {in_psn}+"!^"+{dept_cd}+"^!"+{dept_nm}  //집행자사원번호(로그인아이디)와 부서코드, 부서명임
String ACC_GB = CommonUtil.nullTrim((String)request.getAttribute("ACC_GB"));
String clientCd = CommonUtil.nullTrim((String)request.getAttribute("clientCd"));
String clientname = CommonUtil.nullTrim((String)request.getAttribute("clientname"));
String erpId = CommonUtil.nullTrim((String)request.getAttribute("erpId"));
//String employeeId = CommonUtil.nullTrim((String)request.getAttribute("employeeId");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>전표 입력</title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/common.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<!-- 다국어 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 추가 종료 -->

<jsp:include page="/app/jsp/common/common.jsp" />
<script>
var dTime = <%=dTime%>;
var auto_no = parseInt(<%=auto_no%>); //전산번호 보여주는 기능은 따로 없음. //TODO
var auto_no_l = parseInt(<%=auto_no_l%>); //lot번호 기타미지급금, 카드대금은 lot번호를 보여줘야함... 현금이 무조건 입력되므로 1이 증가해서 입력되게 하자..
var curIndex = 0;
var subIndex = 0;
var totalCount = '<%=codes!=null?codes.length:0%>';
totalCount = parseInt(totalCount-1);
var detailTotalCount = 0;
//var sendDeptPro ="";

$(document).ready(function() { initialize(); });

function initialize(){
	// 기안기에서 넘어온 전체 금액을 출장비 필드에 넣어준다
	var rowCount = $('#masterTable tr').length;

	var par = window.opener;
	if('<%=erpId%>'!=''){
		$("#erpId").val('<%=erpId%>');
	}else{
		$("#erpId").val(par.document.getElementById("erpId").value);
	}
	if($("#erpId").val() == ""){
		var totalCost = '<%=totalCost%>';
		for(var i=0; i < rowCount-1; i++){
			//if($('#deptSub_'+i).val() == '출장비'){
				$('#ammount_'+i).val(parseInt(totalCost).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
				//break;
			//}
		}
		// 가장 첫 계정코드의 적요에 문서의 제목을 넣어준다.
		$('#sum_0').val('<%=title%>');
	}
	if($("#erpId").val() != ""){
		var erpId = $("#erpId").val();
		if(erpId.indexOf("!^")>-1){
			erpId = $("#erpId").val().split("!^").join("").trim();
		}
		$("#computeNum").val(erpId.substring(0,2)+"-"+erpId.substring(2,9)+"-"+erpId.substring(9,14));
	}
	
	
	checkCost();
}

function leadingZeros(n, digits) { //일련번호의 앞에 0을 붙여준다...
  var zero = '';
  n = n.toString();

  if (n.length < digits) {
    for (var i = 0; i < digits - n.length; i++)
      zero += '0';
  }
  return zero + n;
}		
	
function searchPlantByEnter() {
	if (window.event.keyCode == 13) {
		searchPlant();
	}
}
function searchPlant() {
	var plantCode = $('#plantCode').val();
	var plantName = $('#plantName').val();
	
	openWindow('search_plant_pop', '<%=webUri%>/app/approval/searchPlant.do?plantCode='+plantCode+'&plantName='+plantName, 520, 450, 'no');
}

function searchDeptByEnter() {
	if (window.event.keyCode == 13) {
		searchDept();
	}
}
function searchDept() {
	var plantCode = $('#plantCode').val();
	var deptCode = $('#deptCode').val();
	var deptName = $('#deptName').val();
	
	openWindow('search_dept_pop', '<%=webUri%>/app/approval/searchDept.do?plantCode='+plantCode+'&deptCode='+deptCode+'&deptName='+deptName, 520, 450, 'no');
}
function getLotNoJson(index){
	$.ajaxSetup( {cache:false} );
	curIndex = index;
	var plantCode = $('#plantCode').val();
	var dTime = $('#dTime').val();
	$.ajax({
		url : '<%=webUri%>/app/approval/getLotNoJson.do',
		dataType : 'json',
		data: { 
			plantCode : plantCode,
			dTime : dTime
		},
		success: function(data) {
			//alert(curIndex);
			//alert(dTime.substring(0,6));
			//alert(data.auto_no_l);
			document.getElementById("lot_"+curIndex).value = "L"+dTime.substring(0,6)+""+leadingZeros(data.auto_no_l, 4);	
		},
		error: function(data) {
			alert("해당 lot번호를 읽어오는데 실패했습니다.");
		}
	});	
}
function searchMasterByEnterJson(subCode, index) {
	if (window.event.keyCode == 13) {
		curIndex = index;
		$.ajax({
			url : '<%=webUri%>/app/approval/searchMasterJson.do',
			dataType : 'json',
			data: { 
				masterName : subCode
			},
			success: function(data) {
				if(data.detailList.length > 0){
					setMaster(data.detailList[0].accCd, data.detailList[0].accNm, data.detailList[0].drcr);
					var deptproval = $('#deptPro_'+curIndex).val();
					if(deptproval=="210040800" || deptproval=="210049000"){
						//auto_no_l++; //이부분을 json으로 받아서 처리해야된다...
						getLotNoJson(index);
					}
					$("#sum_"+curIndex).focus();
					$("#detailTable_"+curIndex).remove();
					selectDetail(curIndex);
				}else{
					alert("해당 계정 코드가 없습니다.");
				}
			},
			error: function(data) {
				alert("관리항목 조회 중 오류가 발생했습니다.");
			}
		});		
	}
}

//searchFunc01ByEnter 지출증빙검색
function searchFunc01ByEnter(func01, index) {
	if (window.event.keyCode == 13) {
		searchFunc01(func01, index);
	}
}

function searchFunc01(func01, index) {
	curIndex = index;
	openWindow('search_func01_pop', '<%=webUri%>/app/approval/searchFunc01.do?func01='+func01, 520, 450, 'no');
}


function setFunc01(id, name){
	$('#func_01_'+curIndex).val(id.trim());
}

function searchAccountByEnterJson(accountName, index){
	if (window.event.keyCode == 13) {
		curIndex = index;
		$.ajax({
			url : '<%=webUri%>/app/approval/searchAccountJson.do',
			dataType : 'json',
			data: { 
				accountName : accountName
			},
			success: function(data) {
				if(data.detailList.length > 0){
					setAccount(data.detailList[0].clientCd, data.detailList[0].clientName);
					$("#ammount_"+curIndex).focus();
				}else{
					alert("해당 거래처 코드가 없습니다.");
				}
			},
			error: function(data) {
				alert("관리항목 조회 중 오류가 발생했습니다.");
			}
		});		
	}
}
function searchAccountByEnter(accountName, index) {
	if (window.event.keyCode == 13) {
		searchAccount(accountName, index);
	}
}

function searchSubByEnter(index, subCode) {
	curIndex = index;
	var plantCode = $('#plantCode').val();
	var deptCode = $('#deptCode').val();
	var deptName = $('#deptName').val();
	var plantName = $('#plantName').val();
	if (window.event.keyCode == 13) {
		openWindow('search_sub_pop', '<%=webUri%>/app/approval/searchSub.do?subCode='+subCode+'&dptCd='+deptCode+'&plantCode='+plantCode, 520, 450, 'no');
	}
}

function searchMasterByEnter(subCode) {
	if (window.event.keyCode == 13) {
		searchSub(subCode);
	}
}

function searchAccount(accountName, index) {
	curIndex = index;
	openWindow('search_account_pop', '<%=webUri%>/app/approval/searchAccount.do?accountName='+accountName, 520, 450, 'no');
}

function searchMasterByEnter(masterName, index) {
	if (window.event.keyCode == 13) {
		searchMaster(masterName, index);
	}
}

function searchMaster(masterName, index) {
	curIndex = index;
	openWindow('search_master_pop', '<%=webUri%>/app/approval/searchMaster.do?masterName='+masterName, 520,250, 'no');
}
function searchBubCard() {
	openWindow('search_bubcard_pop', '<%=webUri%>/app/approval/selectBubCard.do?deptCode='+$("#deptCode").val()+'&plantCode='+$("#plantCode").val()+'&userId='+$("#userId").val(), 1300,800, 'yes');
}


function searchUserByEnter() {
	if (window.event.keyCode == 13) {
		searchUser();
	}
}
function searchUser() {
	var userId = $('#userId').val();
	var userName = $('#userName').val();
	var deptCode = $('#deptCode').val();
	var plantCode = $('#plantCode').val();
	
	openWindow('search_user_pop', '<%=webUri%>/app/approval/searchUser.do?userId='+userId+'&userName='+userName+'&deptId='+deptCode+'&plantCode='+plantCode, 520, 350, 'no');
}

function setUser(code, name){
	$('#userId').val(code);
	$('#userName').val(name);	
}

function setPlant(code, name){
	$('#plantCode').val(code);
	$('#plantName').val(name);	
}

function setDept(code, name){
	$('#deptCode').val(code);
	$('#deptName').val(name);	
}

function setSub(cd){
	$('#sub_'+curIndex).val(cd);
}

function setAccount(id, name){
	$('#accountId_'+curIndex).val(id.trim());
	$('#accountName_'+curIndex).val(name.trim());	
}


function setMaster(cd, name, drcr){
	$("#chadae_"+curIndex+" > option[value="+drcr+"]").attr("selected", "ture");
	$('#deptPro_'+curIndex).val(cd);
	$('#deptSub_'+curIndex).val(name);
	if(cd=="210040800" || cd=="210049000"){
		getLotNoJson(curIndex);
	}
}

function selectBubCard(){
	$.ajax({
		url : '<%=webUri%>/app/approval/selectBubCard.do',
		dataType : 'json',
		success: function(data) {
			var list = data.detailList;
		},
		error: function(data) {
			alert("법인카드 사용내역 조회 중 오류가 발생했습니다.");
		}
	});		
}

var selMaster;
function selectDetail(index){
	selMaster = index;
	var chadae = $('#chadae_'+index).val();   
	var accCd = $('#deptPro_'+index).val();   
	
	$.ajax({
		url : '<%=webUri%>/app/approval/searchDetail.do',
		dataType : 'json',
		data: { 
			accCd : accCd,
			chadae : chadae
		},
		success: function(data) {
			var list = data.detailList;
			$(".detailTable").hide();
			if($("#sub_"+index+"_0").html()==null){
				$("#detailTable_"+index).remove();
				detailTotalCount = 0 ;
				var table = '<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablegray detailTable" id="detailTable_'+index+'">'+
								'<tr>'+
									'<th width="105">&nbsp;</th>'+
									'<th width="173">관리항목</th>'+
									'<th width="865">관리항목 적요</th>'+
								'</tr>'+
							'</table>';
				$("#details").append(table);
				
				for(var i=0;i < list.length; i++){
					writeDetail(data.detailList[i].KW_CD, data.detailList[i].KW_NM, index);
				}
			}else{
				$("#detailTable_"+index).show();
			}
			
			//$("#detailTable_"+index).find("tr:gt(0)").remove();
			//$("#detailTable_"+index).show();
		},
		error: function(data) {
			alert("관리항목 조회 중 오류가 발생했습니다.");
		}
	});				
}

function writeDetail(cd,nm,index){
	var trContent = '<tr>'
					+'<td>'
					+'<input type="hidden" name="subCd_'+index+'_'+detailTotalCount+'" value="'+cd+'" />'+cd
					+'</td>'
					+'<td>'+nm+'</td>'
					+'<td class="tablegrayleft">'
					+'<input type="text" name="sub_'+index+'_'+detailTotalCount+'" id="sub_'+index+'_'+detailTotalCount+'" style="width:250px"  onkeyup="searchSubByEnter(\''+index+'_'+detailTotalCount+'\', \''+cd+'\');"/>'
					+'</td>'
					+'</tr>';
	$('#detailTable_'+index).append(trContent);
	var tempDetail =  $("#tempDetail_"+index).val().split("!^");
	if(tempDetail!=null && tempDetail.length > 0){
		$("#sub_"+index+"_"+detailTotalCount).val(tempDetail[detailTotalCount]);
	}
	if(cd=="D6"){
		$("#sub_"+index+"_"+detailTotalCount).val("A");
	}
	$("#detailCount_"+totalCount).val(detailTotalCount);
	detailTotalCount++;
	var isSelect = "";
	isSelect = '<%=isSelect%>';
	if(isSelect=="Y"){
		$("#masterTable input[type=text]").attr("readonly",true);
		$("#usrInfo input").attr("readonly",true);
		$("#details input[type=text]").attr("readonly",true);
		$("#details input[type=text]").attr("disable",true);
		$("#masterTable input[type=text]").attr("disable",true);
		$("#usrInfo input").attr("disable",true);
	}
}


function addMaster(){
	totalCount = parseInt(totalCount)+1;
	var content = '<tr id="master_'+totalCount+'" onclick="selectDetail(\''+totalCount+'\');" style="cursor:pointer;">' + 
		'<td>' + 
		'	<select name="chadae_'+totalCount+'" id="chadae_'+totalCount+'" class="searchinput">' + 
		'		<option value="D">차변</option>' + 
		'		<option value="C">대변</option>' + 
		'	</select>' + 
		'</td>' + 
		'<td><input type="text" name="deptPro_'+totalCount+'" id="deptPro_'+totalCount+'" style="width:60%;text-align:center;" value="" onkeyup="searchMasterByEnterJson(this.value, \''+totalCount+'\');"/> <a href="javascript:searchMaster(\'\', \''+totalCount+'\');">검색</a></td>' + 
		'<td><input type="text" name="deptSub_'+totalCount+'" id="deptSub_'+totalCount+'" style="width:90%" value="" onkeyup="searchMasterByEnter(this.value, \''+totalCount+'\');" /></td>' + 
		'<td><input type="text" name="sum_'+totalCount+'" id="sum_'+totalCount+'" style="width:95%" /></td>' + 
		'<td><input type="text" name="accountId_'+totalCount+'" id="accountId_'+totalCount+'" style="width:70%;text-align:center;" value="" onkeyup="searchAccountByEnterJson(this.value, \''+totalCount+'\');"/> <a href="javascript:searchAccount(\'\',\''+totalCount+'\');">검색</a></td>' + 
		'<td><input type="text" name="accountName_'+totalCount+'" id="accountName_'+totalCount+'" style="width:100px" value="" onkeyup="searchAccountByEnter(this.value, \''+totalCount+'\');" /></td>' +
		'<td><input type="text" name="func_01_'+totalCount+'" id="func_01_'+totalCount+'" style="width:100px;text-align:center;" value="" readonly="readonly" onkeyup="searchFunc01ByEnter(this.value, \''+totalCount+'\');"/></td>' +
		'<td><input type="text" name="lot_'+totalCount+'" id="lot_'+totalCount+'" style="width:100px;text-align:center;" readonly="readonly"/></td>' + 
		'<td><input type="text" name="ammount_'+totalCount+'" id="ammount_'+totalCount+'" style="width:100px;text-align:right;" value=""/></td>' + 
		'<td style="width:0%;display  : none;"><input type="hidden" name="tempDetail_'+totalCount+'" id="tempDetail_'+totalCount+'" value=""/></td>' +
		'<td style="width:0%;display  : none;"><input type="hidden" name="approveNum_'+totalCount+'" id="approveNum_'+totalCount+'" value=""/></td>' +
		'<td style="width:0%;display  : none;"><input type="hidden" name="detailCount_'+totalCount+'" id="detailCount_'+totalCount+'" value=""/></td>' +
	'</tr>';
	
	$('#masterTable').append(content);
}

function removeMaster(idx){
	var removeIdx;
	if(idx||idx!=undefined){
		removeIdx = idx;
	}else{
		removeIdx = selMaster;
	}
	if($("#lot_"+(removeIdx)).val()!=""){
		//auto_no_l--;	
	}
	//$("#masterTable").find("tr:eq("+(totalCount+1)+")").remove();
	$("#master_"+(removeIdx)).remove();
	$("#detailTable_"+(removeIdx)).remove();
	if(totalCount!=-1){
		totalCount = parseInt(totalCount)-1;
	}
	checkCost();
}


function checkCost(){
	var rowCount = $('#masterTable tr').length-1;
	var chaCost = 0, deaCost = 0;
	for(var i=0;i<=rowCount;i++){
		var ammount = $('#ammount_'+i).val();
		if(ammount != null && ammount!=""){
			ammount = parseInt(ammount.replace(/[^0-9\.]/g, ''));
		}
		if($("#chadae_"+i+" option:selected").val()=='D'){ //차변
			if(ammount){
				chaCost += ammount;
			}else{
				chaCost += 0;
			}
		
		}else if($("#chadae_"+i+" option:selected").val()=='C'){ //대변
			if(ammount){
				deaCost += ammount;
			}else{
				deaCost += 0;
			}
		}
	}
	var conChaCost = parseInt(chaCost).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	var conDeaCost = parseInt(deaCost).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	var conGapCost = parseInt(chaCost-deaCost).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	
	$('#chaCost').html(conChaCost);
	$('#deaCost').html(conDeaCost);
	$('#gapCost').html(conGapCost);
}

function regForm(){ // 전표를 ERP DB에  입력한다.
	$("#totalCount").val(totalCount);
	if(confirm("전표 입력을 완료하시겠습니까?") == true){
		$.post("<%=webUri%>/app/approval/insertStatementToDBTemp.do", $("#myform").serialize(), function(data) {
			if (data.result == "success") {
				var par = window.opener;
				par.document.getElementById("erpId").value = data.statementId;
				alert("전표입력이 완료되었습니다." );
				var erpId = data.statementId.split("!^").join("").trim();
				erpId = erpId.substr(0,2).trim()+"-"+erpId.substr(2,7).trim()+"-"+erpId.substr(9,12).trim();
				par.markingErpId(erpId);
				window.close();
			}else{
				if(data.alert){
					alert(data.alert);
				}
				alert("전표입력이 실패하었습니다.");
			}
		}, 'json').error(function(data) {
			alert("전표입력이 실패하였습니다. - ajax 에러");
		});	
	}
}

function leadingZeros(n, digits) { //일련번호의 앞에 0을 붙여준다...
  var zero = '';
  n = n.toString();

  if (n.length < digits) {
    for (var i = 0; i < digits - n.length; i++)
      zero += '0';
  }
  return zero + n;
}
function modiForm(){ // 전표를 수정상태로 변환한다.
	$("#myform input[type=text]").attr("readonly",false);
	$("#myform input[type=text]").attr("disable",false);
}
function initForm(){ // 전표를 모두 비운다.
	var rowCount = $('#masterTable tr').length-1;
	for(var i=0;i<=rowCount;i++){
		removeMaster(i);
	}
}
</script>
</head>

<body>
	<form name="myform" id="myform" method="post">
			<input type="hidden" name="erpId" id="erpId" value=""/>
			<input type="hidden" name="totalCount" id="totalCount" vale=""/>
		<div class="popuptitbg">
			<div class="popuptittext">전표입력</div>
			<div class="popuptitx"><img src="<%=webUri%>/app/ref/img/icon_x.png"/></div>
		</div>
		<div class="popupbody">
	 	<!-- 테이블 타이틀 양식 -->
		<div class="btdiv_popup">
		<%if(!isSelect.equals("Y")){ %>
			<p><%=formName%></p>
			<span class="bt_poptop"onclick="regForm()"><a href="#">입력완료</a></span>
			<!-- <span class="bt_poptop"onclick="modiForm()"><a href="#">수정</a></span> -->
			<!-- <span class="bt_poptop"onclick="initForm()"><a href="#">내역 초기화</a></span> -->
			<span class="bt_popup"><a href="javascript:searchBubCard();	">법인카드</a></span>
			<span class="bt_popup"><a href="javascript:addMaster();	">추가</a></span>
			<span class="bt_popup"><a href="javascript:removeMaster();">삭제</a></span>
		<%} %>
		</div>
		<!-- 테이블 타이틀 양식 //-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablegray" id="usrInfo">
		<tr>
			<th width="80" style="">회계단위</th>
			<td width="40">
				<div>
					<div style="float:left;"><input style="width:20px" id="plantCode" name="plantCode" value="<%=acc05tInfo[3]==null?"":acc05tInfo[3]%>"/></div>
					<div style="float:left;margin-left:3px;"><a href="javascript:searchPlant();"><img src="<%=webUri%>/app/ref/image/icon/zoom_in.png" / ></a></div>
				</div>
			</td>
			<td width="80"><input  style="width:95%" id="plantName" name="plantName"  onkeyup="searchPlantByEnter();" value="<%=acc05tInfo[4]==null?"":acc05tInfo[4]%>"/></td>
			<th width="80">부서</th>
			<td width="80">
				<div>
					<div style="float:left;"><input style="width:60px" id="deptCode" name="deptCode" value="<%=acc05tInfo[1]==null?"":acc05tInfo[1]%>"/></div>
					<div style="float:left;margin-left:3px;"><a href="javascript:searchDept();"><img src="<%=webUri%>/app/ref/image/icon/zoom_in.png" / ></a></div>
				</div>
			</td>
			<td width="120"><input  style="width:95%" id="deptName" name="deptName"  value="<%=acc05tInfo[5]==null?"":acc05tInfo[5]%>" onkeyup="searchDeptByEnter();"/></td>
			<th width="100">사용자</th>
			<td width="100">
				<div>
					<div style="float:left;"><input style="width:80px" id="userId" name="userId" value="<%=acc05tInfo[0]==null?"":acc05tInfo[0]%>"/></div>
					<div style="float:left;margin-left:3px;"><a href="javascript:searchUser();"><img src="<%=webUri%>/app/ref/image/icon/zoom_in.png" / ></a></div>
				</div>
			</td>
			<td width="120"><input  style="width:95%" id="userName" name="userName" value="<%=acc05tInfo[6]==null?"":acc05tInfo[6]%>"/></td>
			<th width="150">전표기준일</br>(형식: yyyymmdd)</th>
			<td width="65">
				<div>
					<div style="float:left;"><input style="width:60px" id="dTime" name="dTime" value="<%=dTime==null?"":dTime%>"/></div>
				</div>
			</td>
			<th width="100">전산번호</th>
			<td width="*">
			<input style="width:95%" id="computeNum" name="computeNum"  value="" onkeyup="searchDeptByEnter();"/>
			</td>
		</tr>
		</table>
	
		<!-- 테이블 -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablegray" id="masterTable">
		<tr>
			<th width="80">차대</th>
			<th width="120">계정코드</th>
			<th width="180">계정과목</th>
			<th width="*">적요</th>
			<th width="130">거래처</th>
			<th width="180">거래처명</th>
			<th width="130">지출증빙</th>
			<th width="138">LOT번호</th>
			<th width="145">금액(원)</th>
			<th width="0" style="display  : none; width:0%">관리항목값</th>
			<th width="0" style="display  : none; width:0%">관리항목갯수</th>
			<th width="0" style="display  : none; width:0%">현재법인카드승인번호(키)</th>
		</tr>
		<%
		if(codes!=null && codes.length > 0){
			for(int i=0;i < codes.length; i++){
				String deptPros = "";
				String deptPro = "";
				String deptProNm = "";
				String chadae = "";
				String accountId = "";
				String accountName = "";
				String sum = "";
				String lot = "";
				String ammountD = "";
				String tempDetail = "";
				String func_01 = "";
				deptPros = codes[i][0];
				try{
					deptPro = deptPros.split("\\!\\@")[0];
					chadae = deptPros.split("\\!\\@")[1];
					deptProNm = deptPros.split("\\!\\@")[2];
					if(deptPros.split("\\!\\@").length>3){
						accountId = deptPros.split("\\!\\@")[3];
						accountName = deptPros.split("\\!\\@")[4];
						sum = deptPros.split("\\!\\@")[5];
						ammountD = deptPros.split("\\!\\@")[6];
						  int inValues = Integer.parseInt(ammountD);
						   DecimalFormat Commas = new DecimalFormat("#,###");
						   ammountD = (String)Commas.format(inValues);
						lot = deptPros.split("\\!\\@")[7];
						tempDetail = deptPros.split("\\!\\@")[8];
						if(deptPros.split("\\!\\@").length>=10){
							func_01 = deptPros.split("\\!\\@")[9];
						}else{
							func_01 = "";
						}
					//rs.getString("acc_cd") + "-" + (rs.getString("drcr").equals("D") ? "대변" : "차변") + "-" + rs.getString("acc_nm") + "-" + 
					//rs.getString("trd_cd") + "-" + rs.getString("trd_nm") + "-" + rs.getString("jugyo") + "-" + rs.getString("amt") + "-" + rs.getString("lot_no");
					//codes[rs.getRow()-1][0] += rs.getString("kwan_0"+i)+"!^";
					}
				}catch(Exception e){
					out.print(e.toString());
				}
		%>
				<tr id="master_<%=i%>" onclick="selectDetail('<%=i%>');" style="cursor:pointer;">
					<td>
						<select name="chadae_<%=i%>" id="chadae_<%=i%>" class="searchinput">
							<option <%=chadae.equals("차변")?"selected='selected'":""%>  value="D">차변</option>
							<option <%=chadae.equals("대변")?"selected='selected'":""%>  value="C">대변</option>
						</select>
					</td>
					<td><input type="text" name="deptPro_<%=i%>" id="deptPro_<%=i%>" style="width:60%;text-align:center;" value="<%=deptPro%>" onkeyup="searchMasterByEnterJson(this.value, '<%=i%>');" /> <a href="javascript:searchMaster('','<%=i%>');">검색</a></td>
					<td><input type="text" name="deptSub_<%=i%>" id="deptSub_<%=i%>" style="width:90%" value="<%=deptProNm%>"  onkeyup="searchMasterByEnter(this.value, '<%=i%>');" /></td>
					<td><input type="text" name="sum_<%=i%>" id="sum_<%=i%>" style="width:95%" value="<%=sum%>" /></td>
					<td><input type="text" name="accountId_<%=i%>" id="accountId_<%=i%>" style="width:70%;text-align:center;" value="<%=accountId == ""?  clientCd: accountId%>" onkeyup="searchAccountByEnterJson(this.value, '<%=i%>');"/> <a href="javascript:searchAccount('','<%=i%>');">검색</a></td>
					<td><input type="text" name="accountName_<%=i%>" id="accountName_<%=i%>" style="width:100px" value="<%=accountName =="" ? clientname : accountName%>" onkeyup="searchAccountByEnter(this.value, '<%=i%>');" /></td>
					<td><input type="text" name="func_01_<%=i%>" id="func_01_<%=i%>" style="width:100px;text-align:center;" value="<%=func_01%>" readonly="readonly" onkeyup="searchFunc01ByEnter(this.value, '<%=i%>');"/></td>
					<td><input type="text" name="lot_<%=i%>" id="lot_<%=i%>" style="width:100px;text-align:center;" value="<%=lot%>" readonly="readonly"/></td>
					<td><input type="text" name="ammount_<%=i%>" id="ammount_<%=i%>" style="width:100px;text-align:right;" value="<%=ammountD%>" onblur="javascipt:checkCost();"/></td>
					<td style="width:0%;display  : none;"><input type="hidden" name="tempDetail_<%=i%>" id="tempDetail_<%=i%>" value="<%=tempDetail%>"/></td>
					<td style="width:0%;display  : none;"><input type="hidden" name="approveNum_<%=i%>" id="approveNum_<%=i%>" value=""/></td>
					<td style="width:0%;display  : none;"><input type="hidden" name="detailCount_<%=i%>" id="detailCount_<%=i%>" value=""/></td>
				</tr>
				<%if(deptProNm.equals("출장비")){ %>
					<script>
						//sendDeptPro = '<%=deptPro%>'; //출장비항목을 법인카드를 선택하는 자식 윈도우에서 사용하기 위해 할당
					</script>
				<%}else if(deptProNm.equals("기타미지급금") && codes.length<3){ //저장된 것을 불러오지 않고 전표입력을 시작할 때는 최초에 기타미지급금이 무조건 들어감으로 lot번호를 1 증가 시킨 후 lot번호를 할당한다.(lot번호는 디비에 입력될 때 다시한번 디비에서 체크한 후 입력된다.)
					//auto_no_l++;
				%>
					<script>
						getLotNoJson('<%=i%>');
						//$("#lot_<%=i%>").val("L"+<%=dTime%>+""+leadingZeros(<%=auto_no_l%>, 4));
					</script>
				<%} %>
			<%} %>
			<%if(isSelect.equals("Y")){ %>
			<script>
			$("#masterTable input[type=text]").attr("readonly",true);
			$("#usrInfo input").attr("readonly",true);
			$("#details input[type=text]").attr("readonly",true);
			$("#details input[type=text]").attr("disable",true);
			$("#masterTable input[type=text]").attr("disable",true);
			$("#usrInfo input").attr("disable",true);
			</script>
			<%} %>
			<%if(codes.length>3){ //최초 수정 후부터는 코드의 길이가 늘어나는데, 이때 readonly로 두었다가 수정모드로 전환하게 만들 수 있다. %>
			<script>
/* 				$("#masterTable input[type=text]").attr("readonly",true);
				$("#usrInfo input").attr("readonly",true);
				$("#details input[type=text]").attr("readonly",true);
				$("#details input[type=text]").attr("disable",true);
				$("#masterTable input[type=text]").attr("disable",true);
				$("#usrInfo input").attr("disable",true); */
			</script>
			<%} %>
		<%}else{ //저장된 관리항목이 없을 수 있음 서블릿단에서 초기화하거나 jsp 초기화.. .여기선 서블릿에서 초기화해서 넘어옴.%>
		<script>
		<%-- var rowCount = $('#masterTable tr').length;
		
		var par = window.opener;
			for(var i=0; i < rowCount-1; i++){
				//if($('#deptSub_'+i).val() == '출장비'){
					$('#ammount_'+i).val('<%=totalCost%>');
					//break;
				//}
			// 가장 첫 계정코드의 적요에 문서의 제목을 넣어준다.
			$('#sum_0').val('<%=title%>');
		}
		checkCost(); --%>
		</script>
			<!-- <tr>
				<td colspan="8">관리 항목 조회에 실패했습니다.</td>
			</tr> -->
		<%} %>
		</table>
		<!-- 테이블 //-->
	                            
		<!-- 테이블 -->
		<div class="btn_left_img">(차액 : <span id="gapCost">0</span>)원</div>
		<div class="btn_right_text">(차변 : <span id="chaCost">0</span>, 대변 : <span id="deaCost">0</span>)원</div>
		<div id="details">
			<!-- <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tablegray detailTable" id="detailTable_0">
				<tr>
					<th width="105">&nbsp;</th>
					<th width="173">관리항목</th>
					<th width="865">관리항목 적요</th>
				</tr>
			</table> -->
		</div>
		
		<!-- 테이블 //-->
		
		<!-- 팝업 하단버튼 -->
		<div class="btdiv_popright">
		</div>
		<!-- 팝업 하단버튼 //-->
		</div>
	</form>
</body>
</html>

<%for(int i=0;i < codes.length; i++){ %>
<script>
selectDetail('<%=i%>');
</script>
<%} %>