<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.design.AcubeList,com.sds.acube.app.design.AcubeListRow,com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
    /** 
     *  Class Name  : ListEnvFormPopAdd.jsp 
     *  Description : ���� ��ȸ �˾�
     *  Modification Information 
     * 
     *   �� �� �� :  
     *   �� �� �� :  
     *   �������� :  
     * 
     *  @author  
     *  @since  
     *  @version 1.0 
     */
%>

<%

	String pcdocBtn = messageSource.getMessage("approval.button.open.pcdoc", null, langType); // PC����
	String btnOk = messageSource.getMessage("appcom.button.ok", null, langType);
    String btnCancel = messageSource.getMessage("appcom.button.cancel", null, langType);

    //��Ļ�����
    String btnDetailInfo = messageSource.getMessage("env.category.button.formDetailInfo", null, langType);
    //��Ĵٿ�ε�
    String btnDownload = messageSource.getMessage("env.category.button.formDownload", null, langType);
    
    String docId = CommonUtil.nullTrim(request.getParameter("docId"));//�̼۱�� ���� �߰�  by jkkim
    String title = CommonUtil.nullTrim(request.getParameter("title"));//�̼۱�� ���� �߰�  by jkkim
    String transtype = CommonUtil.nullTrim(request.getParameter("transtype"));//�̼۱�� ���� �߰�  by jkkim
    
    String itemCount = CommonUtil.nullTrim(request.getParameter("itemcount"));
    
 	// ���������� ���� ���� ��ȸ
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
    
    String type = (String) request.getParameter("type");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code="env.form.title.formmng" /></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/hwpmanager.jsp" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
    $(document).ready(function() { initialize(); });
    
    function initialize() {  
        // ���� ActiveX �ʱ�ȭ
    	initializeFileManager();
    	// �ѱ� ActiveX �ʱ�ȭ
    	initializeHwp("divhwp", "document");
    }

<% if("opinion".equals(type)) { %>
//Ȯ�� ��ư (�����ۼ� ����)
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
		
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
		
		var top = (screen.availHeight - height) / 2;	
		var left = (screen.availWidth - width) / 2; 
		
		// ���ο� ����â�� ����(������ ���â���� �ٸ� â����)
		// ���� -> ���ο� ����â ���� O, ��ư X
        var url = "<%=webUri%>/app/approval/createAuditOpinion.do?formId="+frmForm.formId.value+"&auditOpinion=Y";  
        var doc = openWindow("popupWin", url, width, height,"no","post","yes","yes");
	}
<% } else { %>
    // Ȯ�� ��ư (�����ۼ� ����)
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
	    
	    var formVo = new Object();
        
        $.ajaxSetup({async:false});
        
        $.getJSON("<%=webUri%>/app/approval/appDocAdd.do", { formId: frmForm.formId.value, itemCount: "<%=itemCount%>" }, function(data){
			
			if(data != null) {
				results = (data.formFileName);
				// �������ݰ����ڰ����ȭ ���๮ ����� ��� ����ó ���� ���� üũ formType �߰� 2014.12.24.JJE
				formType = (data.enfdocformYn);
				opener.setItem(results, formType);	
			} else {
				alert("<spring:message code='approval.msg.notexist.selectitem'/>");
			}
		});
        
        <% if(!"audit".equals(type)) { %>
      	opener.window.close();
      	<% } %>
        window.close();
    }
<% } %>
    
	// ��� ��ư 
	function fnc_cancel() {
		//opener.window.close();
		window.close();
	}

    // ��Ļ����� ��ư 
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
        
        var categoryId = frmformCategory.g_selCategory;    // ����� ���̵�    
        if(categoryId == "") {
            alert("<spring:message code='env.form.msg.categoryselect'/>");
            return;
        }

       
    	var formType = frmformList.gstrFormType;				 // ���������� Ÿ��
        var categoryName = frmformCategory.g_selCategoryNm;      // ����Ը�
        var formId = frmformList.gstrFormId;   					 // ��ľ��̵�

        var url = "<%=webUri%>/app/env/form/selectEnvForm.do?categoryName="+categoryName+"&categoryId="+categoryId+"&formId="+formId+"&formType="+formType;  
        var doc = openWindow("popupWindow", url, 550, 460);
    }

   
    // ��ĳ����ޱ� ��ư(��Ĵٿ�ε�)
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

    //�����/��ĸ�� ��ȸ
    function onLoad(){
        frmformCategory.location.href = '<%=webUri%>/app/env/category/listEnvCategory.do';
        frmformList.location.href = "<%=webUri%>/app/env/form/listEnvForm.do";
    }

    
</script>
</head>
<body onLoad = "onLoad();">
<acube:outerFrame>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td colspan="2"><acube:titleBar><spring:message code='env.form.categorylist'/></acube:titleBar></td>
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

                            <td class="communi_text"><img src="<%=webUri%>/app/ref/image/dot_search9.gif" /></td>
                            <td class="communi_text"><%=messageSource.getMessage("env.form.category", null, langType)%></td>
                        </tr>
                    </table>
                    </td>
                    <td colspan="2" class="communi_text">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td class="communi_text"><img src="<%=webUri%>/app/ref/image/dot_search9.gif" /></td>
                            <td class="communi_text"><%=messageSource.getMessage("env.form.categorylist", null, langType)%></td>
                        </tr>
                    </table>
                    </td>
                </tr>
                <tr>
                    <td width="1%" class="communi_text"></td>
                    <td width="44%" height="100%">
                    
                    <!-- ---------------------------------�����-------------------------------------------- -->
                     
                    <iframe name='frmformCategory' src='' scrolling='none' frameborder='2' width="100%" height="100%"
                        class="iframe_table"></iframe></td>
                    <td width="4%" class="communi_text"></td>

                    <!-- --------------------------------���İ���------------------------------------------ -->

                    <td width="50%" height="100%"><iframe name='frmformList' src="" scrolling='none' frameborder='no' width="100%" height="100%" class="iframe_table"></iframe>
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
                <%--	�������ݰ����ڰ���ý��۰�ȭ - PC������ư���� hr.yoon 2014-11-24
                <acube:button onclick ="javascript:fnc_openLocalDoc();" value="<%=pcdocBtn%>" />
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