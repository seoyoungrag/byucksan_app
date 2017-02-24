<%@ page import="com.sds.acube.app.enforce.vo.EnfDocVO" %>
<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/headerListPopAuth.jsp" %>
<%
/** 
 *  Class Name  : PrintDistributionRemindBox.jsp 
 *  Description : 재배부대기함 리스트 인쇄 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 06. 03 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");	

	//==============================================================================
	String listTitle = (String) request.getAttribute("listTitle");
	// 검색 결과 값
	List<EnfDocVO> results = (List<EnfDocVO>) request.getAttribute("ListVo");
	SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());
	
	int nSize = results.size();
	
	String resultSearchType = CommonUtil.nullTrim(resultSearchVO.getSearchType());
	String resultSearchWord = CommonUtil.nullTrim(resultSearchVO.getSearchWord());
	String resultLobCode	= CommonUtil.nullTrim(resultSearchVO.getLobCode());
	//==============================================================================
	
	//==============================================================================
	// Page Navigation variables
	String curPageStr = request.getAttribute("curPage").toString();
	
	String cPageStr = request.getParameter("cPage");
	if(cPageStr != null && !cPageStr.equals(curPageStr)){
	    cPageStr = curPageStr;
	}	
	String sLineStr = request.getParameter("sline");
	int CPAGE = 1;
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int trSu = 1;
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) sLine = Integer.parseInt(sLineStr);
	
	String msgHeaderTitle = messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderDocNumber = messageSource.getMessage("list.list.title.headerDocNumber" , null, langType);
	String msgHeaderSendDeptName = messageSource.getMessage("list.list.title.headerSendDeptName" , null, langType);
	String msgNoData = messageSource.getMessage("list.list.msg.noData" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><%=listTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListPrintCommon.jsp" flush="true" />

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<jsp:include page="/app/jsp/list/common/ListPrintTop.jsp" flush="true" />
<div id="printDiv">
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><%=listTitle%></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
			<table height="100%" width="100%" style='' border='0' cellspacing='0'
				cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<form name="formList"  style="margin:0px">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->							
							<%		
							
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 3);
							acubeList.setColumnWidth("*,100,150");
							acubeList.setColumnAlign("left,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
		
							titleRow.setData(rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderDocNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderSendDeptName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");							
		
							AcubeListRow row = null;
							
						
			 				String tempAttachYn = "N";
			 				
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    EnfDocVO result = (EnfDocVO) results.get(i);
								
								String rsCompId			= CommonUtil.nullTrim(result.getCompId());
							    String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
					            String rsTitle			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsDocNumber		= EscapeUtil.escapeHtmlDisp(result.getDocNumber());
					            String rsSenderCompName	= EscapeUtil.escapeHtmlDisp(result.getSenderCompName());
					            String rsSenderDeptName	= EscapeUtil.escapeHtmlDisp(result.getSenderDeptName());
					            String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
					            String titleMsg			= "";
					            String senderInfo 		= "";
					            
					            if(!"".equals(rsSenderCompName) || !"".equals(rsSenderDeptName)){
					        		if(!"".equals(rsSenderCompName) && "".equals(rsSenderDeptName)){
					        		    senderInfo = rsSenderCompName;
					        		}else if("".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
					        		    senderInfo = rsSenderDeptName;
					        		}else if(!"".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
					        		    senderInfo = rsSenderCompName + "/" + rsSenderDeptName; 
					        		}
					            }
					            
					            titleMsg = rsTitle;
					            if("Y".equals(urgencyYn)) {
					        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
					            }
					           
								
								row = acubeList.createRow(false);
					
								rowIndex = 0;
																							
								
								row.setData(rowIndex, rsTitle);	
								row.setAttribute(rowIndex, "title", titleMsg);

								row.setData(++rowIndex, rsDocNumber);
								row.setAttribute(rowIndex, "title",rsDocNumber);
								
								row.setData(++rowIndex, senderInfo);
								row.setAttribute(rowIndex, "title",senderInfo);
					
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
		<tr>
			<acube:space between="title_button" />
		</tr>
	</table>
</acube:outerFrame>
</div>
</Body>
</Html>