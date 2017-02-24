<%@ page import="com.sds.acube.app.etc.vo.PubPostVO" %>
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
<%@ include file="/app/jsp/common/headerListAuth.jsp" %>
<%
/**  
 *  Class Name  : ListPubPost.jsp 
 *  Description : 공람게시판 리스트 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 05. 18 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");

	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");

	//==============================================================================
	String listTitle = (String) request.getAttribute("listTitle");
	
	// 검색 결과 값
	List<PubPostVO> results = (List<PubPostVO>) request.getAttribute("ListVo");
	SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());	
		
	int nSize = results.size();
	
	String resultSearchType 	= CommonUtil.nullTrim(resultSearchVO.getSearchType());
	String resultSearchWord 	= CommonUtil.nullTrim(resultSearchVO.getSearchWord());
	String resultStartDate		= CommonUtil.nullTrim(resultSearchVO.getStartDate());
	String resultEndDate		= CommonUtil.nullTrim(resultSearchVO.getEndDate());
	String resultLobCode		= CommonUtil.nullTrim(resultSearchVO.getLobCode());
	String resultDeptAdminYn	= CommonUtil.nullTrim(resultSearchVO.getDeptAdminYn());
	
	String deptId = CommonUtil.nullTrim((String) session.getAttribute("DEPT_ID")); // 사용자 소속 부서 아이디
	String userId = CommonUtil.nullTrim((String) session.getAttribute("USER_ID")); // 사용자 아이디
	
	resultStartDate = DateUtil.getFormattedDate(resultStartDate, dateFormat);
	resultEndDate = DateUtil.getFormattedDate(resultEndDate, dateFormat);
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
	
	String msgHeaderTitle 				= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderHeaderPublisher 	= messageSource.getMessage("list.list.title.headerPublisher" , null, langType);
	String msgHeaderHeaderPublisherDept = messageSource.getMessage("list.list.title.headerPublisherDept" , null, langType);
	String msgHeaderHeaderPublisherDate = messageSource.getMessage("list.list.title.headerPublisherDate" , null, langType);
	String msgHeaderHeaderReadPerson 	= messageSource.getMessage("list.list.title.headerReadPerson" , null, langType);
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
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" flush="true" />

<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><%=listTitle%></acube:titleBar></td>
		</tr>
		<jsp:include page="/app/jsp/list/common/ListButtonGroup.jsp" flush="true" />
		<jsp:include page="/app/jsp/list/common/ListDateSearch.jsp" flush="true" />

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
							acubeList = new AcubeList(sLine, 6);
							acubeList.setColumnWidth("20,*,100,100,80,44");
							acubeList.setColumnAlign("center,left,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
		
							titleRow.setData(rowIndex,"<img src=\""+webUri+"/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
							titleRow.setAttribute(rowIndex,"onclick","javascript:check_All();");
							titleRow.setAttribute(rowIndex,"style","padding-left:2px");
		
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderHeaderPublisher);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderHeaderPublisherDept);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderHeaderPublisherDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderHeaderReadPerson);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							AcubeListRow row = null;
							
						
			 				String tempAttachYn = "Y";
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    PubPostVO result = (PubPostVO) results.get(i);
								
							    String rsPubId				= CommonUtil.nullTrim(result.getPublishId());
								String rsDocId 				= CommonUtil.nullTrim(result.getDocId());
					            String rsTitle				= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsPublisherId		= CommonUtil.nullTrim(result.getPublisherId());
					            String rsPublisherName		= EscapeUtil.escapeHtmlDisp(result.getPublisherName());
					            String rsPublishDeptId		= CommonUtil.nullTrim(result.getPublishDeptId());
					            String rsPublishDeptName	= EscapeUtil.escapeHtmlDisp(result.getPublishDeptName());
					            String rsDate 				= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getPublishDate()));
					            String titleDate			= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getPublishDate()));
					            int readCount				= result.getReadCount();	
					            String docGubun				= CommonUtil.nullTrim(rsDocId.substring(0,3));
					            String rsElecYn				= CommonUtil.nullTrim(result.getElectronDocYn());
					            String endYn				= "N";
					            String titleReadPerson		= msgHeaderHeaderReadPerson;
					          //jkkim added security 관련 추가 start
					            String rsSecurityYn			= CommonUtil.nullTrim(result.getSecurityYn());
					            String rsSecurityPass		= CommonUtil.nullTrim(result.getSecurityPass());
					            String rsSecurityStartDate	= CommonUtil.nullTrim(result.getSecurityStartDate());
					            String rsSecurityEndDate	= CommonUtil.nullTrim(result.getSecurityEndDate());
					            String rsReadDate	= CommonUtil.nullTrim(result.getReadDate());
					            //end
					            String linkReadPerson		= "";
					            String scriptLinkName		= "";
					            //by 서영락, 2016-01-14
					            if(!rsReadDate.equals("")){
									if("9999".equals(rsReadDate.substring(0,4))){
						        		rsTitle = "<b>"+rsTitle+"</b>";
					            	}						
								}
					            if(userId.equals(rsPublisherId) ) {
					        		endYn = "Y";
					            }else if( deptId.equals(rsPublishDeptId) && "Y".equals(resultDeptAdminYn) ){
					        		endYn = "Y";
					            }
					            
					            if(readCount > 0 ){
					        	    linkReadPerson = "<a href='#' onclick=\"javascript:displayNoticeReadPersonPop('"+rsPubId+"');\"><img src='"+webUri+"/app/ref/image/ico_buseo_ex.gif' border='0'></a>";
					            }else{
					        		linkReadPerson = "";
					            }
					          
					            boolean isDuration = false;//jkkim added 보안문서 아이콘 표시 start 
					            if("APP".equals(docGubun)){
					        		if("Y".equals(rsElecYn)){
					        			scriptLinkName = "selectPubPost";
					        			
					        			//jkkim added 보안문서 아이콘 표시 start 
					        			if(!rsSecurityStartDate.equals("")&&!rsSecurityEndDate.equals(""))
					        			{
					        			    int nStartDate = Integer.parseInt(rsSecurityStartDate);
					        			    int nEndDate = Integer.parseInt(rsSecurityEndDate);
					        			    int nCurDate = Integer.parseInt(DateUtil.getCurrentDate("yyyyMMdd"));
					        				if((nCurDate > nStartDate ||  nCurDate == nStartDate) && (nCurDate < nEndDate ||  nCurDate == nEndDate))
					        				    isDuration = true;
					        			}
					        			//end
					        		}else{
					        		    scriptLinkName = "selectNonAppPubPost";
					        		}
					        		
					            }else{
					        	
						        	if("Y".equals(rsElecYn)){
						        	    scriptLinkName = "selectEnfPubPost";
						        	}else{
						        	    scriptLinkName = "selectNonEnfPubPost";
						        	}
					        							        	
					            }


								StringBuffer buff;
								
								row = acubeList.createRow();
								row.setAttribute("id", rsDocId);
					
								rowIndex = 0;
								buff = new StringBuffer();
								buff.append("<input type=\"checkbox\"  name=\"publishId\" id=\"publishId\" value=\""+rsPubId+"\"  listFormChk=\""+endYn+"\" >");
								
								row.setData(rowIndex, buff.toString());
								row.setAttribute(rowIndex, "class", "ltb_check");
								row.setAttribute(rowIndex,"style","vertical-align:top;");	
								
								String rsTitleSec = "";
								if("Y".equals(rsSecurityYn)&&(isDuration==true))
			        			{
								    rsTitleSec = "<img src=\"" + webUri + "/app/ref/image/secret.gif\" border='0'/>" + rsTitle;
			        			    scriptLinkName = "selectPubPostSec";
			        			}
								else
								    rsTitleSec = rsTitle;
								
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:"+scriptLinkName+"('"+rsPubId+"','"+rsDocId+"','"+resultLobCode+"','N','"+rsSecurityYn+"','"+rsSecurityPass+"','"+isDuration+"');\">"+rsTitleSec+"</A>");	
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", rsTitle);
								
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+rsPublisherId+"');return(false);\">"+rsPublisherName+"</A>");
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",rsPublisherName);
								
								row.setData(++rowIndex, rsPublishDeptName);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",rsPublishDeptName);
								
								row.setData(++rowIndex, rsDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\"  onclick=\"javascript:"+scriptLinkName+"('"+rsPubId+"','"+rsDocId+"','"+resultLobCode+"','Y');\"> </A>");
								row.setAttribute(rowIndex, "title",titleDate);
								
								row.setData(++rowIndex, linkReadPerson);
								row.setAttribute(rowIndex, "title",titleReadPerson);
					
						    } // for(~)
			
					        if(totalCount == 0){
					
					            row = acubeList.createDataNotFoundRow();
								row.setData(0, msgNoData);
					
					        }
					
					        acubeList.setNavigationType("normal");
							acubeList.generatePageNavigator(true); 
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
	 <!-- 페이징관련 form -->
     <jsp:include page="/app/jsp/list/common/ListPagingDateForm.jsp" flush="true" /> 
     <!-- 페이징관련 form  끝-->
     
</acube:outerFrame>

</Body>
</Html>