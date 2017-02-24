<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale,java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList,com.sds.acube.app.design.AcubeListRow,com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.ws.vo.CmnResVO,com.sds.acube.app.ws.vo.*,com.sds.acube.app.approval.vo.*"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
    /** 
			 *  Class Name  : ListEnvForm.jsp 
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
    //결과데이타
    
    String type = (String)request.getAttribute("type");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.title.senderTitle" /></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript"><!--




    function listDoc(item){
    	//$("#Itemid").val(item);
    	document.frmForm.Itemid.value = item;
        document.frmForm.action="<%=webUri%>/app/ws/server/listDoc.do";
        document.frmForm.target="_self";
        document.frmForm.submit();
    }

     
    //문서정보조회
    function selectInfo(docId){
        //$("#Itemid").val(item);
        document.frmForm.docId.value = docId;
        document.frmForm.action="<%=webUri%>/app/ws/server/selectDocInfo.do";
        document.frmForm.target="_self";
        document.frmForm.submit();
    }

</script>
</head>
<body topmargin=0 leftmargin=0 rightmargin=0 bottommargin=0>
<form name="frmForm" id="frmForm">


<%
if(type.equals("approval")){
CmnResVO cmmResVO = (CmnResVO) request.getAttribute("resultVO");

if(cmmResVO !=null){ %>
<table>

    <tr>
        <td>결과코드</td>        <td><%= cmmResVO.getResponse_code()%></td>
    </tr>
        <td>에러메시지</td>        <td><%=cmmResVO.getError_message()%></td>
    </tr>
</table>
<%
}
else{
    %>
<table>

    <tr>
        <td>결과코드</td>        <td>없음</td>
    </tr>

</table>
    <%
}
}
%>

 
<%
if(type.equals("box")){
AppMenuVOs appMenuVOs = (AppMenuVOs) request.getAttribute("resultVO");

if(appMenuVOs !=null){ 

    List appMenuList = appMenuVOs.getAppMenuVOs();
    AppMenuVO appmenuVO;
%>

<table border="1">
    <tr><td colspan="3">함목록조회</td>
    </tr>
    <tr>
        <td>함코드</td>
        <td>함명</td>
        <td>문서건수</td>
    </tr>
    <% 
    
    for(int i=0; i < appMenuList.size(); i++){ 
	appmenuVO = new AppMenuVO();
	appmenuVO = (AppMenuVO)appMenuList.get(i);
    %>
        <tr>
        <td><%= appmenuVO.getMenuid() %></td>
        <td><a href="javascript:listDoc('<%=appmenuVO.getMenuid() %>');" ><%= appmenuVO.getMenuname()%></a></td>
        <td><%= appmenuVO.getDoc_count()%></td>
    <%} %>    
    
    
</table>
<%}} %>


<%
if(type.equals("doc")){
AppListVOs appListVOs = (AppListVOs) request.getAttribute("resultVO");
//결재목록
if (appListVOs != null) {

 			List appList = appListVOs.getAppListVOs();
 			AppListVO appListVO;
%>

<table border="1">
    <tr>
        <td colspan="2">결재목록 조회</td>
    </tr>
    <tr>
        <td>문서 id</td>
        <td>문서제목</td>
        <td>기안자</td>
        <td>기안자부서</td>
        <td>기안일자</td>
        <td>결재상태</td>
    </tr>
    <%
        for (int i = 0; i < appList.size(); i++) {
    			    appListVO = new AppListVO();
    			    appListVO = (AppListVO) appList.get(i);
    %>
    <tr>
        <td><%=appListVO.getDocid()%></td>
        <td><a href="javascript:selectInfo('<%=appListVO.getDocid() %>');"><%=appListVO.getTitle()%></a></td>
        <td><%=appListVO.getUsername()%></td>
        <td><%=appListVO.getOrgname()%></td>
        <td><%=appListVO.getAppdate()%></td>
        <td><%=appListVO.getAppstatus()%></td>
        <%
            }
        %>
    
</table>
<%
    }}
%> 




<%
if(type.equals("info")){
AppDetailVO appDetailVO = (AppDetailVO) request.getAttribute("resultVO");
     //문서상세정보
if (appDetailVO != null) {

 			List appList = appDetailVO.getAppline();
 			AppLineVO appLineVO;
 %>

<table border="1">
    <tr>
        <td colspan="3">문서상세정보 조회</td>
    </tr>
    <tr>
        <td>문서 id</td>
        <td>문서제목</td>
        <td>결재상태</td>
    </tr>
    <tr>
        <td><%=appDetailVO.getDocid()%></td>
        <td><%=appDetailVO.getTitle()%></td>
        <td><%=appDetailVO.getAppstatus()%></td>
    </tr>

    <tr>
        <td colspan="3" height="10"></td>
        <tr>
        <td colspan="3">결재경로</td>
        <tr>
            <td>처리자</td>
            <td>처리상태</td>
            <td></td>

            <%
                for (int i = 0; i < appList.size(); i++) {
            			    appLineVO = new AppLineVO();
            			    appLineVO = (AppLineVO) appList.get(i);
            %>
            <tr>
                <td><%=appLineVO.getApproverId()%></td>
                <td><%=appLineVO.getProcType()%></td>
                <td></td>
                <%
                    }
                %>
            
</table>

<%
    }}
%>
<input type ="text" name="Itemid" id="Itemid" value="" />
<input type ="text" name="docId" id="docId" value="" />
</form>
</body>
</html>