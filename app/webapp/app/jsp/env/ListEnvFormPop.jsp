<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.design.AcubeList,com.sds.acube.app.design.AcubeListRow,com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
    /** 
     *  Class Name  : ListEnvFormPop.jsp 
     *  Description : 서식 조회 팝업
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
	String pcdocBtn = messageSource.getMessage("approval.button.open.pcdoc", null, langType); // PC문서
	String btnOk = messageSource.getMessage("appcom.button.ok", null, langType);
    String btnCancel = messageSource.getMessage("appcom.button.cancel", null, langType);

    //양식상세정보
    String btnDetailInfo = messageSource.getMessage("env.category.button.formDetailInfo", null, langType);
    //양식다운로드
    String btnDownload = messageSource.getMessage("env.category.button.formDownload", null, langType);
    
    String docId = CommonUtil.nullTrim(request.getParameter("docId"));//이송기능 관련 추가  by jkkim
    String title = CommonUtil.nullTrim(request.getParameter("title"));//이송기능 관련 추가  by jkkim
    String transtype = CommonUtil.nullTrim(request.getParameter("transtype"));//이송기능 관련 추가  by jkkim
    
 	// 문서편집기 선택 정보 조회
    boolean isSelectHwpDoc = false;
    IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
    String opt428 = appCode.getProperty("OPT428", "OPT428", "OPT");
    String editorTypeValues = envOptionAPIService.selectOptionText((String) session.getAttribute("COMP_ID"), opt428);
    
    if (editorTypeValues.indexOf("I1:Y") >= 0) {
    	isSelectHwpDoc = true;	
    }
    
    if (editorTypeValues.indexOf("I2:Y") >= 0) {
    	isSelectHwpDoc = true;	
    }      
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.form.title.formmng" /></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/hwpmanager.jsp" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">

    $(document).ready(function() { initialize(); });
    
    function initialize() {  
        // 파일 ActiveX 초기화
    	initializeFileManager();
    	// 한글 ActiveX 초기화
    	initializeHwp("divhwp", "document");
    }

	// PC 문서 버튼 (내컴퓨터 파일 열기 기능 추가)
    function fnc_openLocalDoc() {
        if ("false" == "<%= isSelectHwpDoc %>") {
			// alert("문서 편집기 선택에서 한글 또는 워드를 선택하세요.");
			alert("<spring:message code='env.option.msg.selectHwpDoc'/>");
			return;
        }
        
		var editorTypeValues = "<%= editorTypeValues %>";
		var fileTypePattern = "";
		
		if (editorTypeValues.indexOf("I1:Y") >= 0) {
			fileTypePattern += "hwp" + String.fromCharCode(29);
		}

		if (editorTypeValues.indexOf("I2:Y") >= 0) {
			fileTypePattern += "doc" + String.fromCharCode(29);
		}

		// HTML일경우 양식기안에서는 사용하지 못하도록 수정.
        /*
		if (editorTypeValues.indexOf("I3:Y") >= 0) {
			fileTypePattern += "html" + String.fromCharCode(29);
		}
		*/
        var formId = "LOCALFILE";  // PC 파일을 직접 여는 경우 formId는 LOCALFILE 로 지정

		// 형식 : 'show:파일확장자 + String.fromCharCode(29) + 파일확장자 + String.fromCharCode(29)...
		var filelist = FileManager.uploadfile("show:" + fileTypePattern);
        
        //파일선택시....취소할경우 사이즈 0
        if(filelist.length > 0){
    		var uplodedFileName = filelist[0].filename;
        } else {
            return;    
        }           
        
 		var width = 830;
		if (screen.availWidth < 830) {
			width = screen.availWidth;
		}
		var height = 768;
		if (screen.availHeight > 768) {
			height = screen.availHeight;
		}
		height = height - 120;
		

        var url = "<%=webUri%>/app/approval/createAppDoc.do?formId=" + formId + "&formFileName=" + uplodedFileName;  
        var doc = openWindow("popupWin", url, width, height);
        
        window.close();
    }
	
    // 확인 버튼 (문서작성 오픈)
    function fnc_openDoc() {
        var formId =    frmformList.gstrFormId;
        
        if(formId == ""){
          	alert("<spring:message code='env.form.msg.formselect'/>");
          	return;
       	}

      	if (typeof(formId) == "undefined") {
          	alert("<spring:message code='env.form.msg.formselect'/>");
          	return;
       	} 
       	        
		var width = 830;
		if (screen.availWidth < 830) {
			width = screen.availWidth;
		}
		var height = 768;
		if (screen.availHeight > 768) {
			height = screen.availHeight;
		}
		height = height - 80;

	    opener.window.close();
        var url = "<%=webUri%>/app/approval/createAppDoc.do?formId="+formId+"&docId=<%=docId%>"+"&title=<%=title%>"+"&transtype=<%=transtype%>";  
        var doc = openWindow("popupWin", url, width, height); 
        
        window.close(); 
    }

    
	// 취소 버튼 
	function fnc_cancel() {
		window.close();
	}

    // 양식상세정보 버튼 
    function fnc_detailInfo(){
        var formId = frmformList.gstrFormId;
        if(formId == "") {
          	alert("<spring:message code='env.form.msg.formselect'/>");
          	return;
        }

      	if (typeof(formId) == "undefined") {
          	alert("<spring:message code='env.form.msg.formselect'/>");
          	return;
       	}         
        
        var categoryId = frmformCategory.g_selCategory;    // 양식함 아이디    
        if(categoryId == "") {
            alert("<spring:message code='env.form.msg.categoryselect'/>");
            return;
        }

       
    	var formType = frmformList.gstrFormType;				 // 문서편집기 타입
        var categoryName = frmformCategory.g_selCategoryNm;      // 양식함명
        var formId = frmformList.gstrFormId;   					 // 양식아이디

        var url = "<%=webUri%>/app/env/form/selectEnvForm.do?categoryName="+categoryName+"&categoryId="+categoryId+"&formId="+formId+"&formType="+formType;  
        var doc = openWindow("popupWin", url, 550, 460);
    }

   
    // 양식내려받기 버튼(양식다운로드)
    function fnc_downloadForm() {

        if(frmformList.gstrFormFileId =="") {
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
        attach.docid = "none";

        FileManager.download(attach);
    }


    //양식함/양식목록 조회
    function onLoad(){
        frmformCategory.location.href = '<%=webUri%>/app/env/category/listEnvCategory.do';
        //frmformList.location.href = "<%=webUri%>/app/env/form/listEnvForm.do";
    }

    
</script>
</head>
<body onLoad = "onLoad();">
<body style="margin: 0">

<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
    <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="pop_table05">
        <tr>
            <td colspan="2"><span class="pop_title77"><spring:message code='env.form.categorylist'/></span></td>
        </tr>
        <tr>
            <acube:space between="title_button" />
        </tr>
        <tr>
            <acube:space between="button_search" />
        </tr>
        <tr>
            <td height="100%" class="communi_text">
            <table height="300" width="100%" style='' border='0' cellspacing='0' cellpadding='0' class="tree_table">
                <tr>
                    <td colspan="5" height="10" class="communi_text"></td>
                </tr>
                <tr>
                    <td width="1%" class="communi_text"></td>
                    <td colspan="2" height="20" class="communi_text">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>

                            <td class="communi_text" style="padding-bottom:10px;"><img src="<%=webUri%>/app/ref/image/dot_search9.gif" /></td>
                            <td class="communi_text title_mini" style="padding-bottom:10px;"><%=messageSource.getMessage("env.form.category", null, langType)%></td>
                        </tr>
                    </table>
                    </td>
                    <td colspan="2" class="communi_text">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td class="communi_text" style="padding-bottom:10px;"><img src="<%=webUri%>/app/ref/image/dot_search9.gif" /></td>
                            <td class="communi_text title_mini" style="padding-bottom:10px;"><%=messageSource.getMessage("env.form.categorylist", null, langType)%></td>
                        </tr>
                    </table>
                    </td>
                </tr>
                <tr>
                    <td width="1%" class="communi_text"></td>
                    <td width="20%" height="100%">
                    
                    <!-- ---------------------------------양식함-------------------------------------------- -->
                     
                    <iframe name='frmformCategory' src='' scrolling='none' frameborder='1' width="100%" height="100%"
                        class="iframe_table"></iframe></td>
                    <td width="2%" class="communi_text"></td>

                    <!-- --------------------------------서식관리------------------------------------------ -->

                    <td width="40%" height="100%"><iframe name='frmformList' src="" scrolling='none' frameborder='no' width="95%" height="100%" class="iframe_table"></iframe>
                    </td>
                    <td width="1%" class="communi_text"></td>
                </tr>
                <tr>
                    <td colspan="5" height="10" class="communi_text"></td>
                </tr>
                <tr>
                    <td colspan="5" align="right" class="communi_text">
                    <table border='0' cellspacing='0' cellpadding='0'>
                        <tr>
                            <td><acube:buttonGroup>
                                <acube:button onclick="javascript:fnc_detailInfo();" value="<%=btnDetailInfo%>" />
                                <acube:space between="button" />
                                <% if(!isExtWeb) { %>
                                <acube:button onclick="javascript:fnc_downloadForm();" value="<%=btnDownload%>" />
                                <acube:space between="button" />
                                <%} %>
                            </acube:buttonGroup></td>
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
        <tr>
            <acube:space between="title_button" />
        </tr>
        <tr>
            <td><acube:buttonGroup>
               <%--  <acube:button onclick ="javascript:fnc_openLocalDoc();" value="<%=pcdocBtn%>" />
                <acube:space between="button" /> --%>

                <acube:button onclick ="javascript:fnc_openDoc();" value="<%=btnOk%>" />
                <acube:space between="button" />
                
                <acube:button onclick ="javascript:fnc_cancel();" value="<%=btnCancel%>" />
                <acube:space between="button" />
            </acube:buttonGroup></td>
        </tr>
    </table>
</acube:outerFrame>
<form name="frmForm" id="frmForm">
<input type="hidden" name="categoryId" id="categoryId" value=""></input> 
<input type="hidden" name="formId" id="formId" value=""></input>
<input type="hidden" name="resourceId" id="resourceId" value=""></input>
</form>
<div id="divhwp" width="100%" height="1" style="display:none;"></div>
</html>