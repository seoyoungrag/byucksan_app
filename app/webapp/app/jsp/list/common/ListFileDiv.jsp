<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow"
%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListFileDiv.jsp 
 *  Description : 첨부파일관련 div 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 04. 08 
 *  @version 1.0 
 *  @see
 */ 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
</head>
<body>
<div id="attachDiv" style="display:none">     
     
<input name="attachDivUse" id="attachDivUse" value="Y" type="hidden"/>
<table width="250" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="file_table"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="16" height="18" valign="top"><img src="<%=webUri%>/app/ref/image/title3.gif" width="16" height="16"></td>
            <td class="file_title"><spring:message code="list.list.title.titleAttach"/></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="5"></td>
      </tr>
      <tr>
        <td class="file_table_w">
	        <table width="100%" border="0" cellspacing="1" cellpadding="1" id="attachTb">
	          <tr>
	            <td width="18%" class="file_head"><spring:message code="list.list.title.headerType"/></td>
	            <td class="file_head"><spring:message code="list.list.title.headerAttachName"/></td>
	          </tr>	          
	        </table>
        </td>
      </tr>

    </table></td>
  </tr>
</table>
</div> 
</body>
</html>