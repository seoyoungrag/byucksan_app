<%@ page import="com.sds.acube.app.common.vo.AuditListVO" %>

<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>

<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,				 
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ include file="/app/jsp/common/headerListAuth.jsp" %>
<%
/** 
 *  Class Name  : ListControl.jsp 
 *  Description : ��ȹ���� ����Ʈ 
 *  Modification Information 
 * 
 *   �� �� �� :  
 *   �� �� �� : 
 *   �������� : 
 * 
 *  @author  �����
 *  @since 2011. 05. 03 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");

	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String compId 	= (String) session.getAttribute("COMP_ID");	// ȸ�� ID

	//==============================================================================
	String listTitle = (String) request.getAttribute("listTitle");
	// �˻� ��� ��
	List<AuditListVO> results = (List<AuditListVO>) request.getAttribute("ListVo");
	SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());
	int nSize = results.size();
	
	String resultSearchType = CommonUtil.nullTrim(resultSearchVO.getSearchType());
	String resultSearchWord = CommonUtil.nullTrim(resultSearchVO.getSearchWord());
	String resultStartDate	= CommonUtil.nullTrim(resultSearchVO.getStartDate());
	String resultEndDate	= CommonUtil.nullTrim(resultSearchVO.getEndDate());
	String resultLobCode	= CommonUtil.nullTrim(resultSearchVO.getLobCode());

	resultStartDate = DateUtil.getFormattedDate(resultStartDate, dateFormat);
	resultEndDate = DateUtil.getFormattedDate(resultEndDate, dateFormat);
	//==============================================================================
	
	//==============================================================================
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
	
	String msgHeaderReceiveNumber	= messageSource.getMessage("list.list.title.headerReceiveNumber" , null, langType);
	String msgHeaderRegistDate		= messageSource.getMessage("list.list.title.headerRegistDate" , null, langType);
	String msgHeadeDraftDeptName	= messageSource.getMessage("list.list.title.headerDraftDeptName" , null, langType);
	String msgHeaderTitle 			= messageSource.getMessage("list.list.title.doctitle" , null, langType);
	String msgHeaderCustomer		= messageSource.getMessage("list.list.title.headerProcessor" , null, langType);
	String msgHeaderAttach			= messageSource.getMessage("list.list.title.headerAttach" , null, langType);	
	String msgHeaderOpinion			= messageSource.getMessage("list.list.title.headerOpinion" , null, langType);
	String msgHeaderApprovalName	= messageSource.getMessage("list.list.title.headerApprovalName" , null, langType);
	String msgHeaderProcStateDocmgr	= messageSource.getMessage("list.list.title.headerProcStateDocmgr" , null, langType);
	String msgHeaderAudityn			= messageSource.getMessage("approval.form.audityn" , null, langType);
	
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//����������
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
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
		<jsp:include page="/app/jsp/list/common/ListRegistSearch.jsp" flush="true" />
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
			<table height="100%" width="100%" style='' border='0' cellspacing='0'
				cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<form name="formList" style="margin:0px">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text"><!------ ����Ʈ Table S --------->							
							<%		
							
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 10);
							acubeList.setColumnWidth("20,60,80,100,*,70,70,70,70,80");
							acubeList.setColumnAlign("center,center,center,center,left,center,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;							
							
							titleRow.setData(rowIndex,"<img src=\""+webUri+"/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
							titleRow.setAttribute(rowIndex,"onclick","javascript:check_All();");
							titleRow.setAttribute(rowIndex,"style","padding-left:2px");
							
							titleRow.setData(++rowIndex,msgHeaderReceiveNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 36px;");
							
							titleRow.setData(++rowIndex,msgHeaderRegistDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeadeDraftDeptName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderCustomer);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderApprovalName);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderOpinion);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderProcStateDocmgr);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderAudityn);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							AcubeListRow row = null;
						
							String tempAttachYn = "N";
							
							// ����Ÿ ������ŭ ����...(�����)
							for(int i = 0; i < nSize; i++) {
							    
							    AuditListVO result = (AuditListVO) results.get(i);
							    
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
								String rsCompId 		= CommonUtil.nullTrim(result.getCompId());					            
					            int rsReceiveNumber		= result.getAuditNumber();
					            String rsDate			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getReceiveDate()));
					            String rsChargeDeptName	= EscapeUtil.escapeHtmlDisp(result.getChargeDeptName());
					            String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsApproverType	= CommonUtil.nullTrim(result.getRegisterName());
					            String rsReceiveType 	= CommonUtil.nullTrim(result.getElectronDocYn());
					            String rsApproverPos	= CommonUtil.nullTrim(result.getRegisterPos());
					            String rsAskType		= CommonUtil.nullTrim(result.getAskType());
					            String rsProcType		= CommonUtil.nullTrim(result.getProcType());
					            String rsDeleteYn		= CommonUtil.nullTrim(result.getDeleteYn());
					           	String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
					           	String rsDocState		= CommonUtil.nullTrim(result.getDocState());
					           	//jkkim added security ���� �߰� start
					           	String rsSecurityYn			= CommonUtil.nullTrim(result.getSecurityYn());
					           	String rsSecurityPass		= CommonUtil.nullTrim(result.getSecurityPass());
					           	String rsSecurityStartDate	= CommonUtil.nullTrim(result.getSecurityStartDate());
					           	String rsSecurityEndDate	= CommonUtil.nullTrim(result.getSecurityEndDate());
					           	int rsAttach		= result.getAttachCount();
					           	int rsOpinion		= result.getOpinionCount();
					           	int rsAuditYn		= result.getAuditYn();
					           	//end
					            String titleMsg			= "";
					            String docState			= "";
					            String electronDocMsg	= "";
					            String deleteAuthYn		= "N";
					            String titleDate		= "";
					            String modAuthYn		= "Y";
					            
					            
					            titleMsg = rsTitle;
						        if("Y".equals(rsDeleteYn)) {
					        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
					            }
					            
					            if("N".equals(electronDocYn)){
					        		docState = messageSource.getMessage("list.list.msg.daliyAuditDocCompleteState" , null, langType);
					            }else{
					        		if(!"".equals(rsDocState) && rsDocState.length() == 6){
					        		   if(Integer.parseInt(rsDocState.substring(3,6)) >= 600 ){
					        		       docState = messageSource.getMessage("list.list.msg.daliyAuditDocCompleteState" , null, langType);
					        		   }else if(Integer.parseInt(rsDocState.substring(3,6)) == 110 ){
					        		       docState = messageSource.getMessage("list.list.msg.daliyAuditDocReturnState" , null, langType);
					        		   }else{
					        		       docState = messageSource.getMessage("list.list.msg.daliyAuditDocIngState" , null, langType);
					        		   }
					        		}
					            }

					            if("Y".equals(rsReceiveType)) {
					        		electronDocMsg = messageSource.getMessage("list.list.msg.docElec" , null, langType);
					        		titleDate	   = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getReceiveDate()));
					            }else{
					        		electronDocMsg = messageSource.getMessage("list.list.msg.docNoElec" , null, langType);
					        		titleDate	   = rsDate;
					            }
					            
					            if("N".equals(rsReceiveType) &&  "N".equals(rsDeleteYn)) {
					        		deleteAuthYn = "Y";
					            }
					            
					            if("Y".equals(rsDeleteYn)) {
					        		modAuthYn = "N";
					            }

					            StringBuffer buff;
					            
								row = acubeList.createRow();
								row.setAttribute("id", rsDocId);
								row.setAttribute("elecYn", electronDocYn);
					
								rowIndex = 0;
								
								//	üũ�ڽ�
								buff = new StringBuffer();
								buff.append("<input type=\"checkbox\"  name=\"docId\" id=\"docId\" value=\""+rsDocId+"\"  listFormChk=\""+deleteAuthYn+"\" modifyChk=\""+modAuthYn+"\">");
								
								row.setData(rowIndex, buff.toString());
								row.setAttribute(rowIndex, "class", "ltb_check");
								row.setAttribute(rowIndex,"style","vertical-align:center;");   // üũ�ڽ� ����  jskim_20150526
								
								//	�����ȣ
								if("Y".equals(rsDeleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+rsReceiveNumber+"</font>");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, rsReceiveNumber);
								}
								row.setAttribute(rowIndex, "title",rsReceiveNumber);
								
								//	�������
								if("Y".equals(rsReceiveType)) {
									row.setData(++rowIndex, rsDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:selectOnlyAppDoc('"+rsDocId+"','"+resultLobCode+"', '"+electronDocYn+"','Y');\"> </A>");
								}else{
								    row.setData(++rowIndex, rsDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:selectDailyAuditDoc('"+rsDocId+"', '"+electronDocYn+"','Y');\"> </A>");
								}
								if("Y".equals(rsDeleteYn)) {									
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}
								row.setAttribute(rowIndex, "title",titleDate);
								
								//	��Ⱥμ�
								if("Y".equals(rsDeleteYn)) {
									row.setData(++rowIndex, "<font color='red'>"+rsChargeDeptName+"</font>");
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
									row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
								}else{
								    row.setData(++rowIndex, rsChargeDeptName);
								}
								row.setAttribute(rowIndex, "title",rsChargeDeptName);
								

								//jkkim added ���ȹ��� ������ ǥ�� start 
								boolean isDuration = false;
							    String linkScriptName = "selectOnlyAppDoc";
								if(!rsSecurityStartDate.equals("")&&!rsSecurityEndDate.equals(""))
								{
								    int nStartDate = Integer.parseInt(rsSecurityStartDate);
								    int nEndDate = Integer.parseInt(rsSecurityEndDate);
								    int nCurDate = Integer.parseInt(DateUtil.getCurrentDate("yyyyMMdd"));
									if((nCurDate > nStartDate ||  nCurDate == nStartDate) && (nCurDate < nEndDate ||  nCurDate == nEndDate))
									    isDuration = true;
								}
								if("Y".equals(rsSecurityYn)&&(isDuration==true))
								{
								    rsTitle = "<img src=\"" + webUri + "/app/ref/image/secret.gif\" border='0'>" + rsTitle;
								    linkScriptName = "selectOnlyAppDocSec";
								}
								//end
								
								//	��������
								if("Y".equals(rsDeleteYn)) {
								    row.setData(++rowIndex, rsTitle);
								}else{
									if("Y".equals(rsReceiveType)) {
										row.setData(++rowIndex, "<a href=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\"  onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+resultLobCode+"', '"+electronDocYn+"','N');\">"+rsTitle+"</A>");
									}else{
									    row.setData(++rowIndex, "<a href=\"#\"  id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:selectDailyAuditDoc('"+rsDocId+"', '"+electronDocYn+"','N');\">"+rsTitle+"</A>");
									}
								}
								if("Y".equals(rsDeleteYn)) {
									row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; text-decoration:line-through;color:red;");
								}else{
								    row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								}
								row.setAttribute(rowIndex, "title", titleMsg);
								
								//	�����
							    row.setData(++rowIndex, rsApproverType);
								row.setAttribute(rowIndex, "title",rsApproverType);

								//	������
								row.setData(++rowIndex, rsApproverPos);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 30px;"); //row ���� ���� jskim_20150526
								
								//	�ǰ߼�
								if(rsOpinion > 0){
								    row.setData(++rowIndex, messageSource.getMessage("list.list.msg.existing" , null, langType));
								}else{
									row.setData(++rowIndex, messageSource.getMessage("list.list.msg.nothing" , null, langType));
								}
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
								//	ó������
								docState = messageSource.getMessage("approval.procinfo.form.proctype."+rsProcType.toLowerCase() , null, langType); 
								row.setData(++rowIndex, docState);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
								//	�ϻ󰨻�
								if(rsAuditYn > 0){
								    row.setData(++rowIndex, messageSource.getMessage("list.list.msg.existing" , null, langType));
								}else{
									row.setData(++rowIndex, messageSource.getMessage("list.list.msg.nothing" , null, langType));
								}
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								
					
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
	 <!-- ����¡���� form -->
     <jsp:include page="/app/jsp/list/common/ListPagingRegistForm.jsp" flush="true" /> 
     <!-- ����¡���� form  ��-->
     
     <!-- ÷������ div -->
     <jsp:include page="/app/jsp/list/common/ListFileDiv.jsp" flush="true" /> 
     <!-- ÷������ div ��-->      
</acube:outerFrame>

</Body>
</Html>