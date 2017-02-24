<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale,java.util.Map"
%>
<%@ page import="com.sds.acube.app.design.AcubeList,
                 com.sds.acube.app.design.AcubeListRow,
                 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : ListEnvFormMain.jsp 
 *  Description : 서식 관리
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.25 
 *   수 정 자 : 윤동원 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  윤동원
 *  @since 2011. 4. 25 
 *  @version 1.0 
 */ 
%>

<%
    String rolecode = (String) session.getAttribute("ROLE_CODES");//role 코드
    String admrole = AppConfig.getProperty("role_appadmin", "", "role");
    
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
    
    //서식함등록
    String categoryRegister = messageSource.getMessage("env.category.button.register" , null, langType);
    //서식함이름수정
    String categoryModify = messageSource.getMessage("env.category.button.namemodify" , null, langType);
    //서식함삭제
    String categoryDelete = messageSource.getMessage("env.category.button.delete" , null, langType);
    //서식등록
    String buttonRegister = messageSource.getMessage("env.form.button.register" , null, langType);
    //서식수정
    String buttonModify = messageSource.getMessage("env.form.button.modify" , null, langType);
    //서식삭제
    String buttonDelete = messageSource.getMessage("env.form.button.delete" , null, langType);
    //양식상세정보
    String btnDetailInfo = messageSource.getMessage("env.category.button.formDetailInfo" , null, langType);
    //양식다운로드
    String btnDownload = messageSource.getMessage("env.category.button.formDownload" , null, langType);
    
    // 권한map
    Map roleType = (Map)request.getAttribute("type");
    
    // 문서편집기 선택 정보 조회
    boolean isSelectHwpDoc = false;
    String opt428 = appCode.getProperty("OPT428", "OPT428", "OPT");
    IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
    opt428 = envOptionAPIService.selectOptionText(compId, opt428);
    
    if (opt428.indexOf("I1:Y") >= 0) {
    	isSelectHwpDoc = true;	
    }
    
    if (opt428.indexOf("I2:Y") >= 0) {
    	isSelectHwpDoc = true;	
    }    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.form.title.formmng"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<!-- 다국어 때문에 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 때문에 추가 종료 -->

<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">

var roleType = "<%=(String)roleType.get("roleType")%>";

$(document).ready(function() { initialize(); });

    function initialize() {  
        // 파일 ActiveX 초기화
    	initializeFileManager();
   }

   //함등록
    function fnc_categoryRegister(){
        
        //함등록 팝업
    	var popupWin = openWindow("popupWin", "<%=webUri%>/app/env/admin/InsertEnvCategory.do", 400, 163);
 
    }

    //양식함 조회
    function fnc_searchList(){

        //alert(roleType);                 
        if(roleType =="ADMIN"){
            frmformCategory.location.href ="<%=webUri%>/app/env/category/admin/listEnvCategory.do?categoryId="+frmForm.categoryId.value;
        }else if(roleType =="CHARGE"){
            frmformCategory.location.href ="<%=webUri%>/app/env/category/charge/listEnvCategory.do?categoryId="+frmForm.categoryId.value;
        }
    }

    //양식 조회
    function fnc_searchFromList(){
        if(roleType =="ADMIN"){
            //alert('ad');
            frmformList.location.href ="<%=webUri%>/app/env/form/admin/listEnvForm.do?categoryId="+frmForm.categoryId.value;
        }else if(roleType =="CHARGE"){ 
            //alert('ch');
            frmformList.location.href ="<%=webUri%>/app/env/form/charge/listEnvForm.do?categoryId="+frmForm.categoryId.value;
        }
    }
    
    //함수정
    function fnc_categoryModify(){

        var categoryId = frmformCategory.g_selCategory;        
        var categoryName = frmformCategory.g_selCategoryNm;
        var deleteYn = frmformCategory.g_selDeleteYn;
        var order = frmformCategory.g_selOrder;
        var resourceId = frmformCategory.g_resourceId;
        
        if(categoryId ==""){
            alert("<spring:message code='env.form.msg.categoryselect'/>");
            return;
        }
        
        //함수정 팝업
        var popupWin = openWindow("popupWin", "<%=webUri%>/app/env/admin/UpdateEnvCategory.do?categoryName="+encodeURIComponent(categoryName)+"&categoryId="+categoryId+"&deleteYn="+deleteYn+"&categoryOrder="+order+"&resourceId="+resourceId, 400, 220);
    }
   
    //함삭제
    function fnc_categoryDelete(){

        var categoryId = frmformCategory.g_selCategory;    
		var resourceId = frmformCategory.g_resourceId;

        if(confirm("<spring:message code='env.form.msg.confirm.categorydel'/>")){    
            frmForm.categoryId.value = categoryId;
            frmForm.resourceId.value = resourceId;
            if(categoryId ==""){
                alert("<spring:message code='env.form.msg.categoryselect'/>");
                return;
            }
            if(frmformList.gintCnt !=0){
                alert("<spring:message code='env.form.msg.confirm.categorydelclear'/>");
                return;
            }
            //submit
            $.post("<%=webUri%>/app/env/admin/deleteCategory.do", $("#frmForm").serialize(), function(data){
            	if("OK" == data.result ) {
            		// 다국어 삭제
            		deleteResource(resourceId); 
                	
    				alert("<spring:message code='env.form.msg.confirm.categorydelok'/>");
                    fnc_searchList();
    			} else {
    				alert(data.result);
    			}	
            }, 'json');
        }
    }
    
    // 양식등록 버튼
    function fnc_buttonRegister() {

        if ("false" == "<%= isSelectHwpDoc %>") {
			// alert("문서 편집기 선택에서 한글 또는 워드를 선택하세요.");
			alert("<spring:message code='env.option.msg.selectHwpDoc'/>");
			return;
        }

        var categoryId = frmformCategory.g_selCategory;        
        var categoryName = frmformCategory.g_selCategoryNm;
 
        if(categoryId == ""){
            alert("<spring:message code='env.form.msg.categoryselect'/>");
            return;
        }
        
        var url="";
        if(roleType =="ADMIN"){
        	url="<%=webUri%>/app/env/form/admin/insertEnvForm.do?categoryName="+categoryName+"&categoryId="+categoryId;
        }else if(roleType =="CHARGE"){
        	url="<%=webUri%>/app/env/form/charge/insertEnvForm.do?categoryName="+categoryName+"&categoryId="+categoryId;
        } 
        //팝업
        var popupWin = openWindow("popupWin1",url, 650, 590);
    }

    //양식수정
    function fnc_buttonModify(){

        var categoryId = frmformCategory.g_selCategory;        
        var categoryName = frmformCategory.g_selCategoryNm;        
        var formId = frmformList.gstrFormId;        

        if(formId == "") {
            alert("<spring:message code='env.form.msg.formselect'/>");
            return;
        }

       	if (typeof(formId) == "undefined") {
          	alert("<spring:message code='env.form.msg.formselect'/>");
          	return;
       	} 
 
        if(categoryId ==""){
            alert("<spring:message code='env.form.msg.categoryselect'/>");
            return;
        }
        
        //팝업
        var url = "";

        if(roleType =="ADMIN"){
            url="<%=webUri%>/app/env/form/admin/selectEnvForm.do?categoryName="+categoryName+"&categoryId="+categoryId+"&formId="+formId;
        }else if(roleType =="CHARGE"){
            url="<%=webUri%>/app/env/form/charge/selectEnvForm.do?categoryName="+categoryName+"&categoryId="+categoryId+"&formId="+formId;
        } 

        var popupWin = openWindow("popupWin", url, 650, 470);
        
    }

    //양식삭제
    function fnc_buttonDelete(){

        var categoryId = frmformCategory.g_selCategory;
        var formId = frmformList.gstrFormId;

        if(formId == "") {
            alert("<spring:message code='env.form.msg.formselect'/>");
            return;
        }

       	if (typeof(formId) == "undefined") {
          	alert("<spring:message code='env.form.msg.formselect'/>");
          	return;
       	} 
  
        frmForm.categoryId.value = categoryId;
        frmForm.formId.value = formId;
        
        if(categoryId =="") {
            alert("<spring:message code='env.form.msg.categoryselect'/>");
            return;
        }

        if(confirm("<spring:message code='env.form.msg.confirm.formdel'/>")){
            //submit
            $.post("<%=webUri%>/app/env/admincharge/deleteEnvForm.do", $("#frmForm").serialize(), function(data){
                if("OK" == data.result ) {  
                    alert("<spring:message code='env.form.msg.confirm.formdelok'/>");
                    frmForm.formId.value='';
                    fnc_searchFromList();
                } else {
                    alert("<spring:message code='env.form.msg.fail'/>");
                }   
            }, 'json');
        }
    }
/*
    //양식상세정보
    function fnc_detailInfo(){

		var width = 1200;
		if (screen.availWidth < 1200) {
			width = screen.availWidth;
		}
		var height = 768;
		if (screen.availHeight > 768) {
			height = screen.availHeight;
		}

		var top = (screen.availHeight - height) / 2;
		var left = (screen.availWidth - width) / 2;		
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
		var doc = window.open("/env/form/openEnvForm.do?formId="+frmForm.formId.value+"&categoryId="+frmForm.categoryId.value, "appDoc", option);
    }
   */

   
    // 양식상세정보 버튼 
    function fnc_detailInfo(){
       	var formId = frmformList.gstrFormId;
       
        if(formId == ""){
          	alert("<spring:message code='env.form.msg.formselect'/>");
          	return;
       	}

       	if (typeof(formId) == "undefined") {
          	alert("<spring:message code='env.form.msg.formselect'/>");
          	return;
       	} 
       	
        var categoryId = frmformCategory.g_selCategory;    //양식함 아이디    
        if(categoryId == ""){
            alert("<spring:message code='env.form.msg.categoryselect'/>");
            return;
         }

        var formType = frmformList.gstrFormType;	
        var categoryName = frmformCategory.g_selCategoryNm;        //양식함명
        var formId = frmformList.gstrFormId;   //양식아이디
        
        var url = "<%=webUri%>/app/env/form/selectEnvForm.do?type=SEL&categoryName="+categoryName+"&categoryId="+categoryId+"&formId="+formId+"&formType="+formType;    
        var doc = openWindow("popupWin", url, 550, 460);
        
    }
   
    // 양식내려받기 버튼(양식다운로드)
    function fnc_downloadForm() {
        if(frmformList.gstrFormFileId == "") {
            alert("<spring:message code='env.form.msg.formselect'/>");
            return;
        }

       	if (typeof(frmformList.gstrFormFileId) == "undefined") {
          	alert("<spring:message code='env.form.msg.formselect'/>");
          	return;
       	} 
        
       	var formType = frmformList.gstrFormType;
    	var ext = ".hwp";

    	if ("2" == formType) {
        	ext = ".doc";
    	} else if ("3" == formType) {
        	ext = ".html";
    	}
        
        var attach = new Object();
        attach.fileid = frmformList.gstrFormFileId;
        attach.filename = frmformList.gstrFormfileName;
        attach.displayname = frmformList.gstrFormName + ext;
        attach.type = "save";
        attach.gubun = "";
        attach.docid="none";

        FileManager.download(attach);
    }
</script>
</head>
<body>
<form name="frmForm" id="frmForm">
<input type="hidden" name="categoryId" id="categoryId" value=""></input>
<input type="hidden" name="formId" id="formId" value=""></input>
<input type="hidden" name="resourceId" id="resourceId" value=""></input>
</form>
<acube:outerFrame>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td colspan="2">
            	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td width="50%" align="left">
							<acube:titleBar popup="true"><spring:message code='env.form.title.formmng'/></acube:titleBar>
						</td>
						<td width="50%" align="right">
						<acube:buttonGroup>
			                <%
			                
			                if(!isExtWeb) { 
			                    //권한이 관리자인 경우
			                    if(rolecode.indexOf(admrole) >= 0 && "ADMIN".equals((String)roleType.get("roleType"))){
			                %>
			                        <acube:menuButton onclick="javascript:fnc_categoryRegister();" value="<%=categoryRegister%>" 
			                             />
			                        <acube:space between="button" />
			                        <acube:menuButton onclick="javascript:fnc_categoryModify();" value="<%=categoryModify%>" 
			                             />
			                        <acube:space between="button" />
			                        <acube:menuButton onclick="javascript:fnc_categoryDelete();" value="<%=categoryDelete%>" 
			                             />
			                        <acube:space between="button" />
			                    <%
			                    }
			                    %>                
			                <acube:menuButton onclick="javascript:fnc_buttonRegister();" value="<%=buttonRegister%>" 
			                     />
			                <acube:space between="button" />
			                <acube:menuButton onclick="javascript:fnc_buttonModify();" value="<%=buttonModify%>" 
			                    />
			                <acube:space between="button" />
			                <acube:menuButton onclick="javascript:fnc_buttonDelete();" value="<%=buttonDelete%>" 
			                     />
			               <%}%>
			            </acube:buttonGroup>
						</td>
					</tr>
				</table>
            </td>
        </tr>
        
        <tr>
            <acube:space between="button_search" />
        </tr>
        <tr>
            <td height="100%" class="communi_text">
            <table height="300" width="100%"  cellspacing='0' cellpadding='0' class="tree_table">
                <tr>
                    <td colspan="5" height="10" class="communi_text"></td>
                </tr>
                <tr>
                    <td width="1%" class="communi_text"></td>
                    <td colspan="2" height="20" class="communi_text">
                         <table border="0" cellspacing="0" cellpadding="0">
                             <tr>
                                 <td class="communi_text" width="150">
                                 <acube:titleBar type="sub"><spring:message code='env.form.category'/></acube:titleBar>
                                 </td>
                            </tr>
                         </table>
                     </td>
                     <td colspan="2" class="communi_text">
                         <table border="0" cellspacing="0" cellpadding="0">
                             <tr>
                                 <td class="communi_text">
                                 <acube:titleBar type="sub"><spring:message code='env.form.categorylist'/></acube:titleBar>
                                 </td>
                             </tr>
                         </table>
                    </td>
                </tr>
                <tr>
                    <td width="1%" class="communi_text"></td>
                    <td width="44%" height="100%">
                    <!------------------------------------양식함--------------------------------------------->
                    <% if("ADMIN".equals((String)roleType.get("roleType"))){ %>
        	        <iframe name='frmformCategory' src="<%=webUri%>/app/env/category/admin/listEnvCategory.do" scrolling='no' frameborder='2' width="100%" height="100%" class="iframe_table"></iframe>
                    <%}else if("CHARGE".equals((String)roleType.get("roleType"))){   %>
        	        <iframe name='frmformCategory' src="<%=webUri%>/app/env/category/charge/listEnvCategory.do" scrolling='no' frameborder='2' width="100%" height="100%" class="iframe_table"></iframe>
                    <%} %>
                    </td>
                    <td width="4%" class="communi_text"></td>
                   
                    <!-- --------------------------------서식관리------------------------------------------ -->
                    <td width="50%" height="100%">
                    <% if("ADMIN".equals((String)roleType.get("roleType"))){ %>
                        <iframe name='frmformList' src="<%=webUri%>/app/env/form/admin/listEnvForm.do" scrolling='no' frameborder='no' width="100%" height="100%" class="iframe_table"></iframe>
                    <%}else if("CHARGE".equals((String)roleType.get("roleType"))){   %>
                        <iframe name='frmformList' src="<%=webUri%>/app/env/form/charge/listEnvForm.do" scrolling='no' frameborder='no' width="100%" height="100%" class="iframe_table"></iframe>
                    <%} %>
                    </td>
                    <td width= "1%" class="communi_text"></td>
                  </tr>
                <tr>
                    <td colspan="5" height="10" class="communi_text"></td>
                </tr>
                <tr>
                    <td colspan="5" align="right" class="communi_text">
                        <table border='0' cellspacing='0' cellpadding='0'>
                            <tr>
                                <td>
                                <acube:buttonGroup>
                                    <acube:button onclick="javascript:fnc_detailInfo();" value="<%=btnDetailInfo%>" />
                                    <acube:space between="button" />
                                    <% if(!isExtWeb) { %>
                                    <acube:button onclick="javascript:fnc_downloadForm();" value="<%=btnDownload%>" />
                                    <%}%>
                                </acube:buttonGroup>
                                </td>
                            </tr>
                        </table>
                    </td> 
                </tr>
                <tr>
                    <td colspan="5" height="10" class="communi_text"></td>
                </tr>
            </table>
            </td>
        </tr>
    </table>
</acube:outerFrame>
</body>
</html>