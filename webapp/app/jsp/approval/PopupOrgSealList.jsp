<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : PopupOrgSealList.jsp 
 *  Description : 관인,서명인 목록 조회 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.05.25 
 *   수 정 자 : jth8172 
 *   수정내용 : 요건반영
 *  
 *  @author  jth8172
 *  @since 2011. 5. 25 
 *  @version 1.0 
 */ 
%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.sds.acube.app.approval.vo.AppRecvVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 com.sds.acube.app.common.util.UtilRequest,
				 com.sds.acube.app.idir.org.orginfo.OrgImage,
				 com.sds.acube.app.common.vo.OrganizationVO,
				 org.anyframe.pagination.Page,
				 java.util.Locale,
				 java.net.URLEncoder,
				 java.net.URLDecoder,
				 java.util.List"
%>
<% 
	response.setHeader("pragma","no-cache");	

    List<OrgImage> OrgImageList = (List<OrgImage>) request.getAttribute("OrgImageList");
	if(OrgImageList == null) {
	    OrgImageList = (List) new OrgImage();
	}
	int nSize = OrgImageList.size();
	
	// 소속부서 상위 부서목록을 가져옴  // jth8172 2012 신결재 TF
    List<OrganizationVO> upperOrgs = (List<OrganizationVO>) request.getAttribute("upperOrgs");
	if(upperOrgs == null) {
		upperOrgs = (List) new OrganizationVO();
	}
	int nOrgSize = upperOrgs.size();
	
	
	String compId = UtilRequest.getString(request, "compId");
	String docId = UtilRequest.getString(request, "docId");
	String sealType = UtilRequest.getString(request, "sealType");
	String deptId = UtilRequest.getString(request, "DEPT_ID");

	if("".equals(deptId)) {
		deptId = (String) session.getAttribute("DEPT_ID");
	}
	
 	String proxyDeptId =(String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
		deptId = proxyDeptId;  
	}
 	
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
   
	String stamptitle = messageSource.getMessage("approval.enforce.title.orgstamp", null, langType);
	String sealtitle = messageSource.getMessage("approval.enforce.title.orgseal", null, langType);
	String omitstamptitle = messageSource.getMessage("approval.enforce.hwp.omitstamp", null, langType);
	String omitsealtitle = messageSource.getMessage("approval.enforce.hwp.omitseal", null, langType);
	String list = messageSource.getMessage("approval.enforce.title.list", null, langType);
	String preview = messageSource.getMessage("approval.enforce.title.preview", null, langType);
	String deptlist = messageSource.getMessage("approval.form.readrange.drs002", null, langType);  // jth8172 2012 신결재 TF
	
	String omitStamp = messageSource.getMessage("hwpconst.form.omitstamp", null, langType);
	String omitSignature = messageSource.getMessage("hwpconst.form.omitsignature", null, langType);
	String noStamp = messageSource.getMessage("approval.result.msg.nostamp", null, langType);
	
	
	String title ="";
	// 서명인(0)/관인(1)
	String spt001 = appCode.getProperty("SPT001", "SPT001", "SPT"); // 직인
	String spt002 = appCode.getProperty("SPT002", "SPT002", "SPT"); // 서명인
	String spt003 = appCode.getProperty("SPT003", "SPT003", "SPT"); // 직인생략
	String spt004 = appCode.getProperty("SPT004", "SPT004", "SPT"); // 서명인생략
	String nodata =  messageSource.getMessage("common.msg.nodata" , null, langType);
	
	if (spt001.equals(sealType)) {
	    title = stamptitle;
	} else if (spt002.equals(sealType)) {
	    title = sealtitle;
	} else if (spt003.equals(sealType)) {
	    title = omitstamptitle;
	    nSize = 1;
	} else if (spt004.equals(sealType)) {
	    title = omitsealtitle;
	    nSize = 1;
	} 
	
	String okBtn = messageSource.getMessage("approval.enforceinfo.button.ok" , null, langType); 
	String closeBtn = messageSource.getMessage("approval.enforce.button.close", null, langType);
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=title%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<% if ("2".equals(opt301)) { %>     
<jsp:include page="/app/jsp/common/certification.jsp" />
<% } %>
<script type="text/javascript"> 

$(document).ready(function(){
	init();
});

//초기화
function init() {
	// 파일 ActiveX 초기화
	initializeFileManager();
	$("#selectItem:eq(0)").click(); // 자신의 부서 첫번재 날인을 선택되게 함 
	$("#selDept").closest("td").css("background-color","#FFFFFF");
	$("a[deptId=<%=deptId%>]").closest("td").css("background-color","#F2F2F4");  //CSS 변경시 일괄적용해야하는 부분.
	
}
 
//목록클릭
function selectList(sealId, FileName, FileType, imgWidth, imgHeight, rowIndex) {
	var url ="<%=webUri%>/app/approval/selectOrgSealFile.do?sealId=" + sealId;
	if(sealId=="" || sealId ==null) {
		return;
	}	
	var filename = "";
	$("#selectItem").closest("td").css("background-color","#FFFFFF");
	$("#selectItem:eq(" + rowIndex + ")").closest("td").css("background-color","#F2F2F4");  //CSS 변경시 일괄적용해야하는 부분.
	 
	document.all.imageUrl.border="1";
	document.all.imageUrl.src=url;
	document.all.imageUrl.alt=FileName;
	document.all.imageUrl.width=imgWidth*4;
	document.all.imageUrl.height=imgHeight*4;
	// 이미지 헤더에 저장한 파일명을 가져온다.
    try { 
        var httpRequest = new XMLHttpRequest(); 
        httpRequest.open('HEAD', url, false); 
        httpRequest.send(null); 
        var contentDisposition = httpRequest.getResponseHeader("Content-Disposition");
        var spos = contentDisposition.indexOf("=");
        var epos = contentDisposition.lastIndexOf(";");
        if (spos > 0 && epos > 0 && spos < epos) {
            filename = contentDisposition.substring(spos+1, epos);
        }
    } catch (e) { 
        alert("Image File not found."); 
    }

    $("#imageFileName").val(filename);
	$("#imageId").val(sealId);
	$("#imageName").val(FileName);
	$("#imageWidth").val(imgWidth);
	$("#imageHeight").val(imgHeight);
	$("#imageFileType").val(FileType);
	$("#imageTitle").text(FileName);
	
	  
}  


//이미지 선택 
function selectDept(deptId) {
 
	var url = document.location.pathname + "?sealType=<%=sealType%>&compId=<%=compId%>&docId=<%=docId%>&DEPT_ID=" + deptId;
	
	top.location.href = url;
}

//이미지 선택 
function selectImage(url) {
 
	document.all.imageUrl.border="3";
}

//확인하면 로컬 temp 로 다운로드
function returnOk () {
	//1. 로컬로 다운로드
    var fileYn = $("#imageFileName").val();
    if (fileYn =="" && ("<%=spt001%>" == "<%=sealType%>" || "<%=spt002%>" == "<%=sealType%>") ) {
    	alert("<%=noStamp%>");
        return;
    }
        
	var file =  new Object();
	file.fileid = $("#imageId").val();
	file.filename = $("#imageFileName").val();
	file.title = $("#imageName").val();
	file.displayname = $("#imageId").val() + $("#imageFileType").val();
	file.filetype = $("#imageFileType").val();
	file.filewidth = $("#imageWidth").val();
	file.fileheight = $("#imageHeight").val();
	file.type="savebody";

	var stampFilePath = FileManager.savebodyfile(file);
	
	file.stampfilepath=stampFilePath;

	//2. 부모창 함수 호출
	closeWin();
	opener.stampOk(file);
}

function closeWin(){
	self.close();
}



//결재암호 체크
function chkPassword() {
	var fileYn = $("#imageFileName").val();
    if (fileYn =="" && ("<%=spt001%>" == "<%=sealType%>" || "<%=spt002%>" == "<%=sealType%>") ) {
    	alert("<%=noStamp%>");
        return;
    }			
	
	<% if ("1".equals(opt301)) { %>
		var password = $.trim($("#password").val());
		if (password == $.trim("")) {
			alert("<spring:message code='approval.msg.need.approvalpassword'/>");
			$("#password").focus();
			return;
		} else {
			prepareSeed();
			$("#encryptedpassword").val(document.getElementById("seedOCX").SeedEncryptData($("#password").val()));
			
			$.post("<%=webUri%>/app/appcom/compareAppPwd.do", $("#opinionForm").serialize(), function(data){
				if (data.result != "success") {
					alert(data.message);
					$("#password").focus();
					return;
				} else {
					returnOk();	
				}	
			}, 'json');
		}
	<% } else if ("2".equals(opt301)) {%>
		$.post("<%=webUri%>/app/appcom/validateSignDate.do", "", function(data) {
			if (data.validation == "Y") {
				returnOk();
				$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
			} else {
			    if (certificate()) {
			    	returnOk();	
					$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
			    } else {
			        return false;
			    }
			}
		}, 'json').error(function(data) {
		    if (certificate()) {
		    	returnOk();	
				$.post("<%=webUri%>/app/appcom/updateValidation.do", "", function(data) {});
		    } else {
		        return false;
		    }
		});
	<% } else { %>
		returnOk();
	<% } %>	
}

//암호에서 엔터키 치면 버튼을 누른것으로 처리함
function chkKeyPress() {
	var keyCode = window.event.keyCode;
	switch (keyCode)
	{
		case 13:
		{
			chkPassword();
		}
		default:
			break;
	}
}


function prepareSeed() {
 var currRoundKey = document.getElementById("seedOCX").GetCurrentRoundKey();
 if (currRoundKey == "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0") {
     var userkey = document.getElementById("seedOCX").GetUserKey();
     currRoundKey = document.getElementById("seedOCX").GetRoundKey(userkey);
 }
 var roundkey_c = document.getElementById("seedOCX").SeedEncryptRoundKey(currRoundKey);
 $('#roundkey').val(roundkey_c);
}

</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td>
			<span class="pop_title77"><%=title%></span>
</td>
<td></td>
</tr>
<!-- 여백 시작 -->
<tr>
<acube:space between="title_button" />
</tr> 
<!-- 여백 끝 -->
<tr><td>

	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="position:relative;"> 
 	<tr>

	<td height="100%" class="communi_text">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="tree_table">
          <tr><td colspan="5" height="10" class="communi_text"></td></tr><!-- 여백 -->
          <tr>
              <td width="1%" class="communi_text" bgcolor="#ffffff"></td><!-- 여백 -->
	 		  <td width="44%"  valign="top" bgcolor="#ffffff">   

	 		    <table width="100%" height="100%"  class="tree_table"  border="0" bgcolor="#ffffff"><tr>
	 		  	<td width="1%" ></td>
	 		  	<td>
					<%
						AcubeList acubeListDept = null;
						acubeListDept = new AcubeList(nSize, 1);
						acubeListDept.setColumnWidth("*");
						acubeListDept.setColumnAlign("center");   
						acubeListDept.setListWithScroll(240);
						AcubeListRow titleRowDept = acubeListDept.createTitleRow();
						
						titleRowDept.setData(0,deptlist + " " + list);
						titleRowDept.setAttribute(0,"style","border-top:none; border-left:none;");
						
						AcubeListRow rowDept = null;
					
						OrganizationVO upperOrg = null;
						for(int i = 0; i < nOrgSize; i++) {
							upperOrg = (OrganizationVO) upperOrgs.get(i);
							rowDept = acubeListDept.createRow();
							rowDept.setData(0, "<a href='#' id='selDept' deptId='"+ upperOrg.getOrgID() + "' onclick=\"selectDept('"+ upperOrg.getOrgID() +"'); \">"+upperOrg.getOrgName()+"</a>");  
							
						}
						if (nOrgSize==0){
							rowDept = acubeListDept.createDataNotFoundRow();
							rowDept.setData(0, nodata);
			    
			            }
						acubeListDept.setNavigationType("normal");
						acubeListDept.generatePageNavigator(false); 
						acubeListDept.setTotalCount(nSize);
						acubeListDept.setCurrentPage(1);
						acubeListDept.generate(out);
					%>
				</td>
				 <td width="1%"></td><!-- 여백 -->
				</tr>
				</table>
			</td>
       	 	<td width="4%" class="communi_text"></td><!-- 여백 -->
			<td width="44%"  bgcolor="#ffffff" valign="top" align="center">
				<table width="100%"  class="tree_table"  border="0" bgcolor="#ffffff" style="height:150px"> 

				<tr><td width=1%"></td>
				<td>

		 		  
						<% 
						AcubeList acubeList = null;
						acubeList = new AcubeList(nSize, 1);
						acubeList.setColumnWidth("*");
						acubeList.setColumnAlign("center");   
						acubeList.setListWithScroll(90);
						AcubeListRow titleRow = acubeList.createTitleRow();
						
						titleRow.setData(0,title + " " + list);
						titleRow.setAttribute(0,"style","border-top:none; border-left:none;");
						
						AcubeListRow row = null;
			
							for(int i = 0; i < nSize; i++) {
							    if (spt003.equals(sealType)) { //직인생략
									row = acubeList.createRow();
							        row.setData(0, omitStamp);  
							    } else if (spt004.equals(sealType)) { //서명인생략
									row = acubeList.createRow();
							        row.setData(0, omitSignature);  
							    } else {
								    OrgImage result = (OrgImage) OrgImageList.get(i);
				  				 	//폐기돤 이미지는 skip
				  				 	if ("1".equals(result.getDisuseYN())) {
				  				 	    continue;
				  				 	}
				  				 	
				 					String imageId = result.getImageID();
									String imageName = result.getImageName();
									String imageFileType = result.getImageFileType();
									String imageWidth = result.getSizeWidth()+"";
									String imageHeight = result.getSizeHeight()+"";
				
									row = acubeList.createRow();
							        row.setData(0, "<a href='#' id='selectItem' onclick=\"selectList('"+ imageId +"','"+imageName+"','"+imageFileType+"','"+imageWidth+"','"+imageHeight+"','"+ i +"'); \">"+imageName+"</a>");  
							    }
			
							} // for(~)
							    
						if (nSize==0){
			                row = acubeList.createDataNotFoundRow();
			                row.setData(0, nodata);
			    
			            }
			            acubeList.setNavigationType("normal");
			            acubeList.generatePageNavigator(false); 
			            acubeList.setTotalCount(nSize);
			            acubeList.setCurrentPage(1);
			            acubeList.generate(out);
					 
						%>
					
		
				</td>
				</tr>
				<tr>
				<td width="1%" ></td>
				<td>  
					<table width="100%"  class="table" cellspacing="1" border="0" > 	
					<tr>
					<td width="50%" class="ltb_head" style="border-top:none;" ><div id="imageTitle" name="imageTitle">
					<% 
					    if (spt003.equals(sealType)) { //직인생략
					%> 
					<%=omitStamp%>
				    <%   
					    } else if (spt004.equals(sealType)) { //서명인생략
					%>
					<%=omitSignature%>
					<%
						} else {
					%>&nbsp;
					<%		
						}
					%> 
					</div>
						<input type="hidden" id="imageName" name="imageName" />
						<input type="hidden" id="imageId" name="imageId"/>
						<input type="hidden" id="imageFileName" name="imageFileName"/>
						<input type="hidden" id="imageFileType" name="imageFileType"/>
						<input type="hidden" id="imageWidth" name="imageWidth"/>
						<input type="hidden" id="imageHeight" name="imageHeight"/>
					</td> 
					</tr> 
		 			<tr style="height:120px" >
					<td width="100%"  style="border-top:none;" valign="top" bgcolor="#ffffff" align="center"> <br/>
					<% 
					    if (spt003.equals(sealType)) { //직인생략
					%>
					
				       <table border="0" cellspacing="4" bgcolor="#000000" >
				       <tr>
				       <td bgcolor="#ffffff"  style="color:#000000; font-size:14pt; font-weight:bold; height:40px; width:120px;" align="center">
				       <%=omitStamp%>
				       </td>
				       </tr>
				       </table>  
				    <%   
					    } else if (spt004.equals(sealType)) { //서명인생략
					%>
				       <table border="0" cellspacing="4" bgcolor="#000000" >
				       <tr>
				       <td bgcolor="#ffffff" style="color:#000000; font-size:14pt; font-weight:bold; height:40px; width:120px;" align="center">
				       <%=omitSignature%>
				       </td>
				       </tr>
				       </table>  

				    <%   
					    } else  {  
					%>
						<image id="imageUrl" name="imageUrl" alt="" border="1" onclick="selectImage(this.src);" src="<%=webUri%>/app/ref/image/common/n_no_img.gif"/>
					<%
					    }
					%>	
					</td>
			 		</tr>
					</table>
				</td>
				<td width="1%" ></td>
				</tr> 
				</table>
			</td>	
        	<td width="1%" class="communi_text"></td><!-- 여백 -->
		</tr>
		<tr><td colspan="5" height="10" class="communi_text"></td></tr><!-- 여백 -->
 	</table>
</td> 
</tr>
<% if ("1".equals(opt301)) { %>
		<tr bgcolor="#ffffff"><!-- 암호 -->
			<acube:space between="button_content" />   
		</tr>   
		<tr style="height:10px" bgcolor="#ffffff">
			<td width="100%" style="height:10px">
				<table width="100%" style="height:10px" class="table_grow"  border="0" bgcolor="#ffffff" cellpadding="0" cellspacing="1" marginheight="0">
					<tr bgcolor="#ffffff">
						<td width="100" class="tb_tit" style="height:10px" ><spring:message code='approval.title.approval.password'/><font color="red">*</font></td>
						<td width="*" class="tb_left_bg" style="height:10px" >
							<input type="password" id="password" name="password" style="width:100%" class="input" onkeydown="chkKeyPress();return(true);"/>
							<form id="opinionForm" name="opinionForm" method="post" onsubmit="return(false);" style="margin:0;">
								<input type="hidden" id="roundkey" name="roundkey"/>
								<input type="hidden" id="encryptedpassword" name="encryptedpassword"/>
							</form>
						</td>
					</tr></table></td> 
		</tr>
 <% } %>		
 <!-- 여백 시작 -->
<tr>
	<acube:space between="title_button" />
</tr>
<!-- 여백 끝 -->  
<tr> 	
<td colspan="2">
<acube:buttonGroup align="right">
	<acube:button id="btnConfirm" disabledid="" onclick="chkPassword();" value="<%=okBtn%>" />
	<acube:space between="button" />
	<acube:button id="btnClose" onclick="javascript:closeWin();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
</acube:buttonGroup>
</td>
</tr>
</table>
</td>
</tr>
</table>
</acube:outerFrame> 
<input type="hidden" id="SEC_IS_FOREIGN_USER" name="SEC_IS_FOREIGN_USER" value="false"/>
<jsp:include page="/app/jsp/common/seed.jsp" />

</body>
</html>