<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.approval.vo.MinwonDocVO" %>
<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ include file="/app/jsp/common/header.jsp"%>

<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,				 
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%
/*
 *  Class Name  : SelectMinwonNum.jsp
 *  Description : ����ο� �ο���ȣ ����
 *  Modification Information 
 * 
 *   �� �� �� : 2015.07.22 
 *   �� �� �� : �ֽ���
 *   �������� : ������ ����ο� �ο���ȣ ����
 * 
 *  @author  �ֽ���
 *  @since 2015. 07. 22
 *  @version 1.0 
 *  @see
 */
%>
<%
	response.setHeader("pragma","no-cache");
	
	String compId 	= (String) session.getAttribute("COMP_ID");	// ȸ�� ID

	// �˻� ��� ��
	List<MinwonDocVO> results = (List<MinwonDocVO>) request.getAttribute("ListVo");
	SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());
	
	int nSize = results.size();
	
	// Page Navigation variables
	String cPageStr = request.getParameter("cPage");
	String sLineStr = request.getParameter("sline");
	int CPAGE = 1;
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	compId = (String) session.getAttribute("COMP_ID"); // ����� �Ҽ� ȸ�� ���̵�
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //���������� ��� �Ǽ�
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int trSu = 1;
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) sLine = Integer.parseInt(sLineStr);

	int curPage=CPAGE;	//����������
	
	String dateFormat = AppConfig.getProperty("format", "yyyy-MM-dd", "date");
	
	String msgHeaderMinwonGroup			= messageSource.getMessage("list.list.title.headerMinwonGroup" , null, langType);
	String msgHeaderMinwonNumber		= messageSource.getMessage("list.list.title.headerMinwonNumber" , null, langType);
	String msgHeaderRegistDate			= messageSource.getMessage("list.list.title.headerRegistDate" , null, langType);
	String msgNoData	 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code='approval.title.docinfo'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />

<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<script type="text/javascript">
	$(document).ready(function() { initialize(); });
	
	function initialize() {
	}
	
</script>
</head>
<body>
	<acube:outerFrame>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td><acube:titleBar><spring:message code='approval.title.search.minwonnumber'/></acube:titleBar></td>
			</tr>
			<tr>
				<acube:space between="title_button" />
			</tr>
			<tr>
				<td>
					<table height="100%" width="100%" style='' border='0' cellspacing='0' cellpadding='0'>
						<tr>
							<td width="100%" height="100%">
								<form name="formList" style="margin:0px">
								<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td height="100%" valign="top" class="communi_text"><!------ ����Ʈ Table S --------->
										<%
											AcubeList acubeList = null;
											acubeList = new AcubeList(sLine, 3);  //����Ʈ �� �÷� ���� 20150601_jskim
											acubeList.setColumnWidth("20%,*,30%");
											acubeList.setColumnAlign("center,left,left");	 
						
											AcubeListRow titleRow = acubeList.createTitleRow();
											int rowIndex=0;
										
											//	����
											titleRow.setData(rowIndex, msgHeaderMinwonGroup);
											titleRow.setAttribute(rowIndex, "style","text-overflow : ellipsis; overflow : hidden; height: 36px;");

											//	�ο���ȣ
											titleRow.setData(++rowIndex, msgHeaderMinwonNumber);
											titleRow.setAttribute(rowIndex, "style","text-overflow : ellipsis; overflow : hidden;");
											
											//	�������
											titleRow.setData(++rowIndex, msgHeaderRegistDate);
											titleRow.setAttribute(rowIndex, "style","text-overflow : ellipsis; overflow : hidden;");
											
											AcubeListRow row = null;

											// ����Ÿ ������ŭ ����...(�����)
											for(int i = 0; i < nSize; i++) {
												MinwonDocVO result = (MinwonDocVO) results.get(i);
												
												String rsMinGuBun = CommonUtil.nullTrim(result.getMinGubun());
												String rsMinNo = CommonUtil.nullTrim(result.getMinNo());
												String rsRegDate = CommonUtil.nullTrim(result.getRegDate());
												
												String strMinGubunName = "";
												
												if ( rsMinGuBun.equals("1") ) {
													strMinGubunName = "����";
												} else if ( rsMinGuBun.equals("2") ) {
													strMinGubunName = "����";
												}
												
												row = acubeList.createRow();
												rowIndex = 0;
												
												row.setData(rowIndex, strMinGubunName);
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 30px;");
												row.setAttribute(rowIndex, "title", strMinGubunName);
												
												row.setData(++rowIndex, rsMinNo);
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												row.setAttribute(rowIndex, "title", rsMinNo);
												
												row.setData(++rowIndex, rsRegDate);
												row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
												row.setAttribute(rowIndex, "title", rsRegDate);
											}
											
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
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		 <!-- ����¡���� form -->
	     <jsp:include page="/app/jsp/list/common/ListPagingDateForm.jsp" flush="true" /> 
	     <!-- ����¡���� form  ��-->
	     
	     <!-- ÷������ div -->
	     <jsp:include page="/app/jsp/list/common/ListFileDiv.jsp" flush="true" /> 
	     <!-- ÷������ div ��-->
	</acube:outerFrame>
</body>
</html>