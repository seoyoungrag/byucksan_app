<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
  /** 
 *  Class Name  : Bullmodify.jsp 
 *  Description : 게시물 수정화면
 *  Modification Information 
 * 
 *   수 정 일 : 2012.05.23 
 *   수 정 자 : 곽경종 
 *   수정내용 :  
 * 
 *  @author  정태환
 *  @since 2012. 3. 23 
 *  @version 1.0 
 */
%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.sds.acube.app.board.vo.AppBoardVO"%>
<%@ page
	import="java.util.List,
				 com.sds.acube.app.appcom.vo.FileVO,
				 com.sds.acube.app.common.util.AppTransUtil,
				 com.sds.acube.app.common.util.EscapeUtil"
%>
<%

	String cPage = (String) request.getAttribute("cPage");

    AppBoardVO boardVO = (AppBoardVO) request.getAttribute("boardVO");
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
	List<FileVO> fileVOs = (List<FileVO>)request.getAttribute("attachList");
	List<AppBoardVO> results =(List<AppBoardVO>)request.getAttribute("boardList");
	
	int nSize = results.size();
	
	// textarea 처리
	String contents = boardVO.getContents();
	contents = contents.replaceAll("<br/>", "\r");
	boardVO.setContents(contents);

	//버튼명
	String list = messageSource.getMessage("board.btn.list", null, langType);
	String modify = messageSource.getMessage("board.btn.modi", null , langType);
	String deptId = (String) session.getAttribute("DEPT_ID");
	String change = messageSource.getMessage("env.empty.button.Change" , null, langType);

	String convertedTitle = boardVO.getBullTitle();
	convertedTitle = convertedTitle.replaceAll("\"","&quot;");
	boardVO.setBullTitle(convertedTitle);
/*  	String convertedContents = boardVO.getContents();
	convertedContents = convertedContents.replaceAll("\"","&quot;");
	boardVO.setContents(convertedContents);  */
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<%-- <link rel="stylesheet" href="<%=webUri%>/app/ref/css/common.css" type="text/css"> --%>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
var index = 0;
var callType = 1;
var bOrgAbbrName = false;
var selectBindId = null;

$(document).ready(
	function() {
		var version2 = <%=boardVO.getDocVersion()%>;
		var selectversion1 = parseFloat("0.1");
		var version1 = parseFloat(version2);
		var result1 = selectversion1 + version1;
		var docVersion = result1.toFixed(1);
		var selectversion2 = parseFloat("1.0");
		var version2 = parseInt(version2);
		var result2 = selectversion2 + version2;
		var tmp1 = version1.toFixed(1);
		var str = "";
		str = "<option value='선택'>선택</option>";
		if (tmp1.indexOf(",") != -1) {
			str+= "<option id='v0' value=" + tmp1 + ">"+ " 현재버전 : " + tmp1 + ".0" + "</option>";
		} else {
			str+= "<option id='v0' value=" + tmp1 + ">"+ " 현재버전 : " + tmp1 +"</option>";
		}
		if (docVersion != result2) {
			str+= "<option id='v1' value='0.1'>"+ docVersion +"</option>";
		}
		str+= ("<option id='v2' value='1.0'>" + result2 + ".0" + "</option>");
		$('#version').append(str);
		var tmpYn = $('#tmpYn').val(tmp1);
		loadAtt();
		printRetentionPeriod('<%=boardVO.getRetentionPeriod()%>');
		selectBindId  = '${selectBindId}';
	}
);
//현재 안건번호
/* function getCurrentItem() {
	return 1;
}*/

function loadAtt(){

	initializeFileManager();
	loadAttach($("#attachFile").val(),false); //첨부파일을 불러온다.
}

function regForm(){
	/* 소유자를 수정에서 변경시 체인지 */
	var ownerName = $('#ownerName').val();
	var ownerId = $('#ownerId').val();
	var docOwnerName = $('#docOwnerName').val(ownerName);
	var docOwnerId = $('#docOwnerId').val(ownerId);
	/* 보안체계를 변경 */
	var docSecurity = $('#docSecurity').val();
	var securityType = $('#securityType').val(docSecurity);
	/* 키워드를 수정에서 변경시 체인지 */
	var kiword = $('#kiword').val();
	var docKiword = $('#docKiword').val(kiword);
	/* 버전을 수정에서 변경시 체인지 */
	var version = $("#version option:selected").val();
	var docVersion = $('#docVersion').val();
	if (version == '선택') {
		document.myform.version.focus();
		alert("버전을 선택하시기 바랍니다.");
		//alert("버전을 선택하시기 바랍니다.\n(현재버전을 선택하면 현재버전의 문서가 수정되며, \n현재버전보다 상위버전을 선택하면 새버전의 문서가 생성됩니다.)");
		return false;
	}
	if (version == docVersion){
		if (!confirm("현재버전을 선택하면 현재버전의 문서가 수정됩니다. 수정하시겠습니까?")){
				return false;
			}
	}
	$("#securityType").val(document.myform.docSecurity.value); //보안 체계
	$("#contents").val(replaceAll($("#txtContents").html(), '\n', '<br/>'));
	document.getElementById("myform").submit(); // 수정된 사항을 등록한다.
}

function listForm(regId){
	var param = 'boardId = <%=boardVO.getBoardId()%>' + '&selectBindId=' + selectBindId + '&cPage=<%=cPage%>';
	location.href = "<%=webUri%>/app/board/BullShare.do?"+param;//목록화면으로 이동
	<%-- location.href = "<%=webUri%>/app/board/BullList.do?boardId=<%=boardVO.getBoardId()%>"; --%> //목록화면으로 이동
}
	
//버전변경하기
function dcVersion(){
	var version1 = $("#version option:selected").val();
	var versionId = $("#version option:selected").attr('id');
	var version2 = <%=boardVO.getDocVersion()%>;
	if (versionId == 'v0') {
		var version = parseFloat(version2);
		var docVersion = version2.toFixed(1);
		var totalVersion = $('#docVersion').val(docVersion);
	} else if (versionId == 'v1') {
		var selectversion = parseFloat(version1);
		var version = parseFloat(version2);
		var result = selectversion + version;
		var docVersion = result.toFixed(1);
		var totalVersion = $('#docVersion').val(docVersion);
	} else {
		var selectversion = parseInt(version1);
		var version = parseInt(version2);
		var docVersion = version + selectversion
		var totalVersion = $('#docVersion').val(docVersion+".0");
	}
}

function bullAppendAttach() {
	var version = $("#version option:selected").val();
	var versionId = $("#version option:selected").attr('id');
	if (version == '선택') {
		document.myform.version.focus();
		alert("버전을 먼저 선택해주시기 바랍니다.");
		return false;
	} else {
		appendAttach();
		/* if (versionId == 'v0') {
			alert("현재버전은 첨부파일을 등록 하실수 없습니다.");
			return false;
		} else {
			appendAttach();
		} */
	}
}

function bullRemoveAttach() {
	var version = $("#version option:selected").val();
	var versionId = $("#version option:selected").attr('id');
	if (version == '선택') {
		document.myform.version.focus();
		alert("버전을 먼저 선택해주시기 바랍니다.");
		return false;
	} else {
		removeAttach();
		/* if (versionId == 'v0') {
			alert("현재버전은 첨부파일을 삭제 하실수 없습니다.");
			return false;
		} else {
			removeAttach();
		} */
	}
}

function selectBind() {
	var bindWin = openWindow("bindWin", "<%=webUri%>/app/bind/selectShare.do?bindType=DOC_BOARD", 430, 450);
}

function setBind(bind) {
	
	if (typeof(bind) == "object") {
		$("#bindingId").val(bind.bindingId);
		$("#bindingName").val(bind.bindingName);
		$("#bindingDisp").val(bind.bindingName);
		$("#retentionPeriod").val(bind.retentionPeriod);
		printRetentionPeriod(bind.retentionPeriod);
	}
}

function printRetentionPeriod(retentionPeriod) {
	if(retentionPeriod!='null'&&retentionPeriod!=null&&retentionPeriod!="") {
		var url = "<%=webUri%>/app/board/getRetentionPeriodAjax.do?retentionPeriod="+retentionPeriod;
		$.ajaxSetup({async:false});
		$.getJSON(url,function(data){
			$("#retentionPeriodValue").val(data);
		});
	}
}

function getCallType() {
	return callType;
}

function setUserInfo(obj) {
	if (getCallType() == "e") {	
		setEmptyInfo(obj);
	} else if (getCallType() == "p") {
		setProxyInfo(obj);
	}
}
function setUserInfo(obj) {
	$('#ownerName').val(obj.userName);
	$('#ownerId').val(obj.userId);
}

function setEmptyInfo(obj) {
	$('#ownerName').val(obj.userName);
	$('#ownerId').val(obj.userId);
}

function setCallType(type) {
	callType = type;
}

function popOrgTree(type) {
	setCallType(type);
	if (type == "p" && $('#emptyUserName').val() == "") {
		alert("<spring:message code='env.option.msg.validate.noempty'/>");
		return;
	}
	var url = "<%=webUri%>/app/common/OrgTree.do?type=1&treetype=3";
	if (type == "p") {
		url = "<%=webUri%>/app/common/OrgTree.do?type=1&treetype=3&deptId="+$('#emptyDeptId').val();
	}
	if (type == "p" && opt313 == "2") {
		alert("<spring:message code='env.empty.msg.validate.setEmpty'/>");
	} else {
		var winRecvLine = openWindow("winRecvLine", url, 600, 310);
	}
}

</script>
<title><spring:message code="board.list.modi" /></title>
</head>
<body leftmargin='0' topmargin='0' marginwidth='0' marginheight='0'>
	<acube:outerFrame>
		<form name="myform" id="myform"
			action="<%=webUri%>/app/board/BullModifyResult.do" method="post">
			<input type="hidden" name="cPage" value="<%=cPage%>" />
			<input type="hidden" name="contents" id="contents" value="" />
			<input type="hidden" name="boardId" value="<%=boardVO.getBoardId()%>" />
			<input type="hidden" name="compId" value="<%=boardVO.getCompId()%>" />
			<input type="hidden" name="bullId" value="<%=boardVO.getBullId()%>" />
			<input type="hidden" name="regId" value="<%=boardVO.getRegId()%>" />
			<input type="hidden" name="regName" value="<%=boardVO.getRegName()%>" />
			<input type="hidden" name="regDate" value="<%=boardVO.getRegDate()%>" />
			<input type="hidden" name="regDeptId" value="<%=boardVO.getRegDeptId()%>" />
			<input type="hidden" name="regDeptName" value="<%=boardVO.getRegDeptName()%>" />
			<input type="hidden" name="docOwnerId" id="docOwnerId" value="<%=boardVO.getDocOwnerId()%>" />
			<input type="hidden" name="docOwnerName" id="docOwnerName" value="<%=boardVO.getDocOwnerName()%>" />
			<input type="hidden" name="docVersion" id="docVersion" value="<%=boardVO.getDocVersion()%>" />
			<input type="hidden" name="tmpYn" id="tmpYn" value="<%=boardVO.gettmpYn()%>" />
			<input type="hidden" name="docKiword" id="docKiword" value="<%=boardVO.getDocKeyword()%>" />
			<input type="hidden" name="regNo" value="<%=boardVO.getRegNo()%>" />
			<input type="hidden" name="securityType" id="securityType" value="<%=boardVO.getSecurityType()%>" />
			<div id="approvalitem1" name="approvalitem" style="dispaly: none">
				<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAttach(fileVOs))%>"></input>
			</div>
			 <div class="title_warp">
					<div class="title_sub">
						<h3>문서내용 수정</h3>
					</div>
					<!-- 버튼양식 -->
					<div class="title_right btn">
					   <span class="main_btn" onclick="regForm()">수정</span>
					   <span class="main_btn" onclick="listForm()">목록</span>
					</div>
					<!-- 버튼양식 //-->
				</div>
				
				<!-- 검색 -->
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="tablegray">
					<tr>
						<th width="115">작성자</th>
						<td width="193"><%=boardVO.getRegName()%></td>
						<th width="117">작성부서</th>
						<td width="239"><%=boardVO.getRegDeptName()%></td>
						<th width="114">소유자</th>
						<td width="306">
						<input type="text" name="ownerName" id="ownerName" style="width: 150px" value="<%=boardVO.getDocOwnerName()%>" disabled/>
						<span><a onclick="popOrgTree('e')" style="cursor: pointer;"><%=change%></a></span>
						<input type="hidden" name="ownerId" id="ownerId" value="<%=boardVO.getDocOwnerId()%>"></input>
						</td>
					</tr>
					<tr>
						<th>등록일</th>
						<td><%=boardVO.getRegDate()%></td>
						<!-- <th>완료예정일</th> -->
						<th>보존기간</th>
						<td>
							<input type="hidden" name="retentionPeriod" id="retentionPeriod" value="<%=boardVO.getRetentionPeriod()%>"/>
							<input type="text" name="retentionPeriodValue" id="retentionPeriodValue" style="width: 99%" disabled/>
						</td>
						<th>보안체계</th>
						<td>
							<select name="docSecurity" id="docSecurity">				
							<c:forEach items="${docSecurityList}" var="docSecurity">
								<option value="${docSecurity.codeValue}" <c:if test="${docSecurity.codeValue == boardVO.securityType}">selected</c:if>>${docSecurity.codeName}</option>
							</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>케비닛/폴더</th>
						<td colspan="5">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">	
								<tr>
									<td width="75%">
										<input type="text" name="bindingDisp" id="bindingDisp" 
											value="<%=boardVO.getBindingName()%>" style="width: 99%" disabled/>
										<input type="hidden" name="bindingName" id="bindingName" value="<%=boardVO.getBindingName()%>"/>
										<input type="hidden" name="bindingId" id="bindingId" value="<%=boardVO.getBindingId()%>"/>
									</td>
									<td width="25%" align="right">
										<div id="bindDiv">
											<acube:button onclick="selectBind();return(false);" value="찾기" type="4" class="gr" />
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<th>제목</th>
						<td colspan="5">
						<input id="bullTitle" name="bullTitle" type="text" class="input" value="<%=boardVO.getBullTitle()%>" maxlength="256" style="width: 98%; ime-mode: active;" onkeyup="checkInputMaxLength(this,'',512)" /></td>
					</tr>
					<tr>
						<th>키워드</th>
							<td colspan="5"><input type="text" name="kiword" id="kiword" style="width: 99%" value="<%=boardVO.getDocKeyword()%>" /></td>
					</tr>
					<tr>
						<th>본문</th>
						<td colspan="5">
						<textarea id="txtContents" name="contents" rows="30" cols="10" style="width: 99%; ime-mode: active;"><%=boardVO.getContents().trim()%></textarea></td>
					</tr>
					<tr>
						<th>현재버전</th>
						<td colspan="2"><%=boardVO.getDocVersion()%></td>
						<th>버전옵션</th>
						<td colspan="2"><select name="version" id="version" onchange="dcVersion()"></select></td>
					</tr>
					<tr bgcolor="#ffffff">
						<th class="tb_tit"><spring:message code="approval.form.attach" /></th>
						<!-- 첨부파일 -->
						<td class="tb_left_bg" colspan="10" height="60">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="80%">
										<div id="divattach" style="background-color: #ffffff; border: 0px solid; height: 90px; width: 100%; overflow: auto;"></div>
									</td>
									<td>
										<table cellpadding="0" cellspacing="0" border="0" width="2%">
										<tr>
											<td style="background: #ffffff" align="center" valign="top">
												<img src="<%=webUri%>/app/ref/img/bu_up.gif" style="cursor: pointer" onclick="javascript:moveUpAttach();return(false);">
											</td>
											<td width="5">&nbsp;</td>
											<td style="background: #ffffff" align="center" valign="top">
												<img src="<%=webUri%>/app/ref/img/bu_pp.gif" style="cursor: pointer" id="appendAttach" onclick="javascript:bullAppendAttach();return(false);">
											</td>
										</tr>
										<tr>
											<td colspan="3" style="height: 5px;"></td>
										</tr>
										<tr>
											<td style="background: #ffffff" align="center" valign="bottom">
												<img src="<%=webUri%>/app/ref/img/bu_down.gif" style="cursor: pointer" onclick="javascript:moveDownAttach();return(false);">
											</td>
											<td></td>
											<td style="background: #ffffff" align="center" valign="bottom">
												<img src="<%=webUri%>/app/ref/img/bu_mm.gif" style="cursor: pointer" onclick="javascript:bullRemoveAttach();return(false);">
											</td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<% 
					if(!boardVO.getRegDate().equals("") || boardVO.getRegDate() != null){
						int boardRegYear = Integer.parseInt(boardVO.getRegDate().substring(0,4));
						if(boardRegYear < 2016){  %>
					<tr>
						<th>기존문서</th>
						<td colspan="5">
						<iframe width="100%" height="400px" src="http://bics.bsco.co.kr/dm/jsp/docview.jsp?ID=<%=boardVO.getBullId() %>&PRG=25&FLDID=1100009189&UBOOL=&D1=50a1c4e6e51dbfdb7453ac561acfc14860a9abad3a186ea361f8925c24d995e4d3bc70b954cda3819a2915e25dff3455e0a8477d06e9208bf5ae1f9b0881ed4ea8562d7737ba9e0286b1a8a8f69d47a7bd61f64855230a1224e0b9bb42c699658550fa1a865988c80129e977195a01e1f857b716ad7029f97d991b48ec6b7b21e0915888baa1fccd79b9c204b32548ec1dc0f3ba70102fdb1ccaacb7fb70cd9ceb1ace55692dc03b9817bf534fbc869133385898b183c4382cf27b64ea79f7ef4455f255790704cd7593d9e89a235734789d15f1eda75ecd8aa39d04deda75f82e118e428c2ac322baec1f419334449499b16779a9ef70e51174dff09f801cc52c05efc5cc39bc21c1c55ee6d7a303c20523b34a817119340f2251db6737341bc520f31c7498ec29d2fe3fccd19596f25be6df690e629b38c0459cf65cc095fd864c656731a30eb439b2129a52387dfd06324cc011ac690077bfabd673026e52106213829f6ce55b632949c7fe78760cc16b45fe346c3512104183d06f8c8822dc554cdc3b8853a9fa854501a4c6c550f54e24ebec2a2e5b2494a46824bcf57e964d6ca37ec11b4ace4ff1cf75ffdf943c7be254f00b111abe468728b0df0918ca15f5ef6fb5cc0a46c1707ed534ea0c3f065c0132434ad5bcc0153b9ffcdb079379a3c7ae82ce793121a3ac21db8aeb38f4baf9efa0989a7917f150af8c9bdb337d47811614a8fcdd29e7d2f76d7f20c2cd43d5c7e9e06a0c94a571124d7b9c76910f0df22808f4682dcd4bcee43cb017fb4ac637718df2a27337b804dfea8a729f9c41e2329171267661c0e0e19e98114e807fb8ef19dbe130ef2d0a4eaf9e5f6885390b1f56f4a5a57bedf0a779df8bf5b9dce82736f382578bcfce9f723ae80ab508ac98af057f44c463c2430df89e9e9d1014d7be6f5f66d35470ccc4bd0b3a08d267073e103b44192799eee067">
						</iframe>
						</td>
					</tr>
					<%	 	}
						}  %>
				</table>
				<!-- 검색 //-->
		</form>
	</acube:outerFrame>
</body>
</html>