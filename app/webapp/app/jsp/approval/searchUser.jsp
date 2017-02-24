<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale, java.util.List,java.util.Map" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
                 com.sds.acube.app.design.AcubeListRow,
                 com.sds.acube.app.common.util.DateUtil,
                 com.sds.acube.app.common.util.EscapeUtil"
%>
<%@ page import="com.sds.acube.app.env.vo.CategoryVO" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
	response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script>
function selectUser(id, name){
	window.close();
	opener.setUser(id, name);
}

</script>
</head>
<body topmargin=0 leftmargin=0 rightmargin=0 bottommargin=0 >
<form name="frmForm">

            <!------ 리스트 Table S --------->
    
            <%   
            try {
			    //==============================================================================
			    // Page Navigation variables
			    String cPageStr = request.getParameter("pageNo");
			    String sLineStr = request.getParameter("sline");
			    int CPAGE = 1;
			    IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
				String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
				String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
				OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
				int sLine = Integer.parseInt(OPT424);
			    int trSu = 1;
			    int RecordSu = 0;
			    if (cPageStr != null && !cPageStr.equals(""))
				CPAGE = Integer.parseInt(cPageStr);
			    if (sLineStr != null && !sLineStr.equals(""))
				sLine = Integer.parseInt(sLineStr);

			    //==============================================================================

			    int totalCount = 0; //총글수
			    int curPage = CPAGE; //현재페이지

			    AcubeList acubeList = null;
				acubeList = new AcubeList(sLine, 3);
				acubeList.setColumnWidth("30%,40%,*");
				acubeList.setColumnAlign("center, center,center");	 
				acubeList.setListWithScroll(450);
				
			    AcubeListRow titleRow = acubeList.createTitleRow();
			    int rowIndex = 0;
			    
			    titleRow.setData(rowIndex,"회사명");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"이름");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"사번");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
			    
			    AcubeListRow row = null;
			    List<HashMap<String, String>> userList = (List<HashMap<String, String>>)request.getAttribute("userList");
			    
			    if (userList.size() == 0) {
					row = acubeList.createDataNotFoundRow();
					row.setData(0, m.getMessage("bind.msg.unit.no.data", null, langType));
			    } else {
					for (int i = 0; i < userList.size(); i++) {
						HashMap<String, String> map = (HashMap<String, String>)userList.get(i);

						row = acubeList.createRow();
						rowIndex = 0;
						
						String userName = (String)(map.get("userName")==null?"&nbsp;":map.get("userName"));
						String compName = (String)(map.get("compName")==null?"&nbsp;":map.get("compName"));
						String employeeId = (String)(map.get("employeeId")==null?"&nbsp;":map.get("employeeId"));
						

						row.addAttribute("onclick", "javascript:selectUser('" + employeeId + "', '" + userName.trim() + "');");
						row.addAttribute("style", "cursor:hand;");
						
						
						row.setData(rowIndex, compName);
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",compName);		
						
						row.setData(++rowIndex, userName);
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",userName);	
						
						row.setData(++rowIndex, employeeId);
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",employeeId);
						
					}
			    }
			    
				acubeList.setNavigationType("normal");
				acubeList.generatePageNavigator(false); 
				acubeList.setPageDisplay(true);
				acubeList.setTotalCount(totalCount);
				acubeList.setCurrentPage(curPage);
				acubeList.generate(out);
				
			} catch (Exception e) {
			    logger.error(e.getMessage());
			}
        %>
</form>
</body>
</html>