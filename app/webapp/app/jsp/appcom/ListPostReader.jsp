<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.etc.vo.PubPostVO" %>
<%@ page import="com.sds.acube.app.common.util.DateUtil" %>
<%@ page import="com.sds.acube.app.etc.vo.PostReaderVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,				 
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%
/** 
 *  Class Name  : ListPostReader.jsp 
 *  Description : 공람게시 조회자 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.11 
 *   수 정 자 : 허 주
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  허주
 *  @since 2011. 03. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	//List<PostReaderVO> postReaderVOs = (List) request.getAttribute("postReaderVOs");
	List<PostReaderVO> results = (List<PostReaderVO>) request.getAttribute("postReaderVOs");
	PubPostVO resultSearchVO = (PubPostVO) request.getAttribute("searchVO");
	
	String publishId = CommonUtil.nullTrim(resultSearchVO.getPublishId());
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());	
	int nSize = results.size();

	// 버튼명
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
	
	//==============================================================================
	// Page Navigation variables
	String cPageStr = request.getParameter("cPage");
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
	
	String headerDept 		= messageSource.getMessage("approval.form.dept" , null, langType);
	String headerPosition 	= messageSource.getMessage("approval.form.position" , null, langType);
	String headerName		= messageSource.getMessage("approval.form.name" , null, langType);
	String headerReadDate	= messageSource.getMessage("approval.form.readdate" , null, langType);
	
	String msgNoData 		= messageSource.getMessage("approval.msg.notexist.postreader" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.postreader'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
}

function closePostReader() {
	window.close();
}

function changePage(p) {
	$("#cPage").val(p);
	$("#pagingList").submit();
}
</script>
</head>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td><span class="pop_title77"><spring:message code='approval.title.postreader'/></span></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<%
				AcubeList acubeList = null;
				acubeList = new AcubeList(sLine, 4);
				acubeList.setColumnWidth("30%,20%,25%,25%,");
				acubeList.setColumnAlign("center,center,center,center");	 

				AcubeListRow titleRow = acubeList.createTitleRow();
				int rowIndex=0;
				
				titleRow.setData(rowIndex,headerDept);
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,headerPosition);
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,headerName);
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,headerReadDate);
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				

				AcubeListRow row = null;

				// 데이타 개수만큼 돈다...(행출력)
				for(int i = 0; i < nSize; i++) {
				    
				    PostReaderVO result = (PostReaderVO) results.get(i);
				    
				String readerId = result.getReaderId();
				String readerName = result.getReaderName();
				String readerPos = result.getReaderPos();
				String readerDeptName = result.getReaderDeptName();
				String readDate = result.getReadDate();
				
				row = acubeList.createRow();
				
				rowIndex = 0;
				
				row.setData(rowIndex, EscapeUtil.escapeHtmlTag(readerDeptName));	
				row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				row.setAttribute(rowIndex, "title", EscapeUtil.escapeHtmlTag(readerDeptName));
				
				row.setData(++rowIndex, EscapeUtil.escapeHtmlTag(readerPos));
				row.setAttribute(rowIndex, "title",EscapeUtil.escapeHtmlTag(readerPos));	
				
				row.setData(++rowIndex, EscapeUtil.escapeHtmlTag(readerName));
				row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				row.setAttribute(rowIndex, "title",EscapeUtil.escapeHtmlTag(readerName));
				
				row.setData(++rowIndex, DateUtil.getFormattedShortDate(readDate));
				row.setAttribute(rowIndex, "title",DateUtil.getFormattedDate(readDate));
				
	
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
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="closePostReader();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
<form name="pagingList" id="pagingList" method="post" style="margin:0px">
 <input name="cPage" id="cPage" type="hidden">
 <input name="publishId" id="publishId" type="hidden" value="<%=publishId%>">
</form>
</body>
</html>