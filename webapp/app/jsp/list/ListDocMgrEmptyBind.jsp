<%@ page import="com.sds.acube.app.bind.vo.BindVO" %>
<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil,
				 java.util.ArrayList"
%>
<%@ page import="java.util.Collections" %>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/adminheader.jsp" %>
<%
/** 
 *  Class Name  : ListDocMgrEmptyBind.jsp
 *  Description : 빈편철함 목록 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2012. 02. 23 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");	

	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID

	//==============================================================================
	String listTitle = "";
	
	// 검색 결과 값
	List<BindVO> results = new ArrayList<BindVO>();
	if(request.getAttribute("bindList") != null){
		results = (List<BindVO>) request.getAttribute("bindList");
	}
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());
	int nSize = results.size();
	//==============================================================================
	
	//==============================================================================
	// Page Navigation variables
	String curPageStr = "1";
	
	String cPageStr = request.getParameter("cPage");
	if(cPageStr != null && !cPageStr.equals(curPageStr)){
	    cPageStr = curPageStr;
	}	
	String sLineStr = request.getParameter("sline");
	int CPAGE = 1;
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int trSu = 1;
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) sLine = Integer.parseInt(sLineStr);
	
	String msgHeaderType 			= messageSource.getMessage("list.list.title.headerType" , null, langType);
	String msgHeaderTitle 			= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderDrafterDept 	= messageSource.getMessage("list.list.title.headerDrafterDept" , null, langType);
	String msgHeaderDraftReceive	= messageSource.getMessage("list.list.title.headerDraftReceive" , null, langType);
	String msgHeaderProgressor		= messageSource.getMessage("list.list.title.headerProgressor" , null, langType);
	String msgHeaderEnfDocNumber	= messageSource.getMessage("list.list.title.headerEnfDocNumber" , null, langType);
	String msgHeaderLastUpdateDate	= messageSource.getMessage("list.list.title.headerLastUpdateDate" , null, langType);
	String msgHeaderDocState		= messageSource.getMessage("list.list.title.headerDocState" , null, langType);
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><spring:message code="list.list.title.listAdminAll"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />


</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code="list.list.title.listAdminAll"/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
			<table height="100%" width="100%" style='' border='0' cellspacing='0' cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<form name="formList" style="margin:0px">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->
							
							<%
							
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 6);
							acubeList.setColumnWidth("40,100,130,150,40,*");
							acubeList.setColumnAlign("center,center,center,center,center,left");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							
						    titleRow.setData(rowIndex,msgHeaderType);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");							
							
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderEnfDocNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");	
		
							titleRow.setData(++rowIndex,msgHeaderDrafterDept);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDraftReceive);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderDraftReceive);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");	
		
							
		
							AcubeListRow row = null;
							
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    BindVO result = (BindVO) results.get(i);
								
								String rsCompId 	= CommonUtil.nullTrim(result.getCompId());
								String rsCreateYear	= CommonUtil.nullTrim(result.getCreateYear());
								String rsBindId		= CommonUtil.nullTrim(result.getBindId());
								String rsBindName	= CommonUtil.nullTrim(result.getBindName());
								String rsDeptId		= CommonUtil.nullTrim(result.getDeptId());
								String rsDeptName	= CommonUtil.nullTrim(result.getDeptName());
								String rsIsActive	= CommonUtil.nullTrim(result.getIsActive());
								String rsDocMgrUuid	= CommonUtil.nullTrim(result.getDocMgrUuid());
								String rsDocMgrPath	= CommonUtil.nullTrim(result.getDocMgrPath());
					           
					            StringBuffer buff;
								row = acubeList.createRow();
								rowIndex = 0;
								
								
							    row.setData(rowIndex, rsCreateYear);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");								
								row.setAttribute(rowIndex, "title", rsCreateYear);
								
								row.setData(++rowIndex, rsBindId);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");								
								row.setAttribute(rowIndex, "title", rsBindId);
								
								row.setData(++rowIndex, rsBindName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");								
								row.setAttribute(rowIndex, "title", rsBindName);
								
								row.setData(++rowIndex, rsDeptName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");								
								row.setAttribute(rowIndex, "title", rsDeptName);
								
								row.setData(++rowIndex, rsIsActive);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");								
								row.setAttribute(rowIndex, "title", rsIsActive);
								
								row.setData(++rowIndex, rsDocMgrUuid);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");								
								row.setAttribute(rowIndex, "title", rsDocMgrUuid);
								
								
								
								
								
					
						    } // for(~)
			
					        if(totalCount == 0){
					
					            row = acubeList.createDataNotFoundRow();
								row.setData(0, msgNoData);
					
					        }
					
					        acubeList.setNavigationType("normal");
							acubeList.generatePageNavigator(false); 
							acubeList.setTotalCount(totalCount);
							acubeList.setCurrentPage(curPage);
							acubeList.generate(out);	    
						    
			%>
					</td>
						</tr>					
					</table>
					</form>
					<!---------------------------------------------------------------------------------------------->
			</table>
			</td>
		</tr>
	</table>
</acube:outerFrame>
</Body>
</Html>