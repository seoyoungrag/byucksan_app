<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.approval.service.IApprovalService" %>
<%@ page import="com.sds.acube.app.approval.vo.AppRecvVO" %>
<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 org.anyframe.pagination.Page,
				 java.util.List,
				 java.util.HashMap,
				 java.util.Map,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListReturnSendWaitBox.jsp 
 *  Description : �ݼ۴���� ����Ʈ 
 *  Modification Information 
 * 
 *   �� �� �� :  
 *   �� �� �� : 
 *   �������� : 
 * 
 *  @author  �����
 *  @since 2011. 04. 12
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");	

	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String compId 	= (String) session.getAttribute("COMP_ID");	// ȸ�� ID

	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String opt415 = appCode.getProperty("OPT415", "OPT415", "OPT"); // ���/�߼۴���� �߼����  // jth8172 2012 �Ű��� TF
	opt415 = envOptionAPIService.selectOptionValue(compId, opt415);
	//==============================================================================
	String listTitle = (String) request.getAttribute("listTitle");
	// �˻� ��� ��
	List<AppDocVO> results = (List<AppDocVO>) request.getAttribute("ListVo");
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
	String curPageStr = request.getAttribute("curPage").toString();
	
	String cPageStr = request.getParameter("cPage");
	if(cPageStr != null && !cPageStr.equals(curPageStr)){
	    cPageStr = curPageStr;
	}	
	String sLineStr = request.getParameter("sline");
	int CPAGE = 1;
	String userId = (String) session.getAttribute("USER_ID"); // ����� ���̵�
	compId = (String) session.getAttribute("COMP_ID"); // ����� �Ҽ� ȸ�� ���̵�
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //���������� ��� �Ǽ�
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int trSu = 1;
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) sLine = Integer.parseInt(sLineStr);
	
	String msgHeaderTitle 			= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderDocNumber 		= messageSource.getMessage("list.list.title.headerDocNumber" , null, langType);
	String msgHeaderDrafterName 	= messageSource.getMessage("list.list.title.headerDrafterName" , null, langType);
	String msgHeaderReceiveDeptName = messageSource.getMessage("list.list.title.headerReceiveDeptName" , null, langType);
	String msgHeaderCompleteDate 	= messageSource.getMessage("list.list.title.headerCompleteDate" , null, langType);
	String msgHeaderDocSate	 		= messageSource.getMessage("list.list.title.headerDocState" , null, langType);
	String msgHeaderSendInfo 		= messageSource.getMessage("list.list.title.headerSendInfo" , null, langType);	//�߼���Ȳ ����Ʈ ��� 20150331_dykim
	
	String msgReturnDate 	        = messageSource.getMessage("list.list.title.returnDate" , null, langType);
	String msgReturnOrg 	        = messageSource.getMessage("list.list.title.returnOrg" , null, langType);
	String msgSend 		            = messageSource.getMessage("list.list.title.send" , null, langType);	//�߼���Ȳ ����Ʈ ��� 20150331_dykim

	String msgTheRest 				= messageSource.getMessage("list.list.msg.theRest" , null, langType);
	String msgCnt 					= messageSource.getMessage("list.list.msg.cnt" , null, langType);
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//����������
	
	//	�ݼ��ǰ� �� ȣ��		20150819_csh
	IApprovalService approvalService = (IApprovalService)ctx.getBean("approvalService");
	
	//	��������		20150915_csh
	String rolecode = (String) session.getAttribute("ROLE_CODES");
	String role11 = AppConfig.getProperty("role_doccharger", "", "role"); 		// ó����
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
		<jsp:include page="/app/jsp/list/common/ListSearch.jsp" flush="true" />
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
							<td height="100%" valign="top" class="communi_text"><!------ ����Ʈ Table S --------->							
							<%		
							
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 6);   //����Ʈ �� �÷� ���� 20150602_jskim
							acubeList.setColumnWidth("20,130,*,100,250,80");
							acubeList.setColumnAlign("center,center,left,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
		
							//S		üũ�ڽ� �߰�	20150320_dykim		S
							titleRow.setData(rowIndex,"<img src=\"" + webUri + "/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
							titleRow.setAttribute(rowIndex,"onclick","javascript:check_All();");
							titleRow.setAttribute(rowIndex,"style","padding-left:2px");
							//E		üũ�ڽ� �߰�	20150320_dykim		E
							
							//	�����Ϲ�ȣ
							titleRow.setData(++rowIndex,msgHeaderDocNumber);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 36px;");
	
							//	��������
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							//	�ݼ�����
							titleRow.setData(++rowIndex,msgReturnDate);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							//	�ݼ۱��(�μ�)
							titleRow.setData(++rowIndex,msgReturnOrg);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							//	����
							titleRow.setData(++rowIndex,msgSend);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							AcubeListRow row = null;
							
     						// ����Ÿ ������ŭ ����...(�����)
							for(int i = 0; i < nSize; i++) {
						    	AppDocVO result = (AppDocVO) results.get(i);
							    String rsCompId			= CommonUtil.nullTrim(result.getCompId());
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
					            String rsTitle			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsDrafterId		= CommonUtil.nullTrim(result.getDrafterId());
					            String rsDrafterName	= EscapeUtil.escapeHtmlDisp(result.getDrafterName());
					            String rsDate			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getApprovalDate()));
					            String rsDrafterDeptId	= CommonUtil.nullTrim(result.getDrafterDeptId());	//��Ⱥμ� 20150331_dykim
					            String titleDate		= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
					            String rsDocNumber		= EscapeUtil.escapeHtmlDisp(result.getDeptCategory());
					            int rsSerialNumber		= result.getSerialNumber();
					            int rsSubSerialNumber	= result.getSubserialNumber();
					            String rsRecvDeptNames	= EscapeUtil.escapeHtmlDisp(result.getRecvDeptNames());
					            int rsRecvDeptCnt		= result.getRecvDeptCnt();
					            String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
					            String rsDocState		= CommonUtil.nullTrim(result.getDocState());
					            String rsTransferYn		= CommonUtil.nullTrim(result.getTransferYn());
					            String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
					            String rsReturnDocYn	= "Y";//CommonUtil.nullTrim(result.getReturnDocYn());
					          //jkkim added security ���� �߰� start
					            String rsSecurityYn			= CommonUtil.nullTrim(result.getSecurityYn());
					            String rsSecurityPass		= CommonUtil.nullTrim(result.getSecurityPass());
					            String rsSecurityStartDate	= CommonUtil.nullTrim(result.getSecurityStartDate());
					            String rsSecurityEndDate	= CommonUtil.nullTrim(result.getSecurityEndDate());
					            //end
					            String titleMsg			= "";
					            String docStateMsg		= "";
					            
					            if(rsDocNumber.length() > 1 && rsSerialNumber > 0){
						            if(rsSerialNumber > 0 && rsSubSerialNumber > 0){
					        			rsDocNumber = rsDocNumber+"-"+rsSerialNumber+"-"+rsSubSerialNumber;
					            	}else if(rsSerialNumber > 0 && rsSubSerialNumber == 0){
					            		rsDocNumber = rsDocNumber+"-"+rsSerialNumber;					            	    
					            	}
					            }else{
					        		rsDocNumber = "";
					            }
					            
					            if(rsRecvDeptCnt > 0){
					        		rsRecvDeptNames = rsRecvDeptNames+" "+msgTheRest+" "+rsRecvDeptCnt+msgCnt;
					            }
					            
					            titleMsg = rsTitle;
					            if("Y".equals(urgencyYn)) {
					        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
					            }
					            
					            if(!"".equals(rsDocState)) {
					            	docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType);
					            	if("Y".equals(opt415)) { // �߼۴���Կ��� ���/�߼۴���ڰ� �߼۽� �ɻ�Ϸ���� ǥ�����ش�.  // jth8172 2012 �Ű��� TF
					            		if("APP620".equals(rsDocState) || "APP625".equals(rsDocState) ) {
						            		docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() + "Signed" , null, langType);
					            		}
					            	}
					            }
								
								row = acubeList.createRow();
								row.setAttribute("id", rsDocId);
								row.setAttribute("elecYn", electronDocYn);
					
								rowIndex = 0;
								StringBuffer buff;
								buff = new StringBuffer();
								buff.append("<input type=\"checkbox\"  name=\"docId\" id=\"docId\" value=\""+rsDocId+"\"  listFormChk=\""+rsReturnDocYn+"\"  >");
								
								//end
								//S		 ������ ��ư	20150320_dykim		S
								buff.append("<input type=\"hidden\" name=\"lobCode\" id=\"lobCode\" value=\""+resultLobCode+"\">");
								buff.append("<input type=\"hidden\" name=\"transferYn\" id=\"transferYn\" value=\""+rsTransferYn+"\">");
								buff.append("<input type=\"hidden\" name=\"electronDocYn\" id=\"electronDocYn\" value=\""+electronDocYn+"\">");
								//E		 ������ ��ư	20150320_dykim		E
								
								row.setData(rowIndex, buff.toString());
								row.setAttribute(rowIndex, "class", "ltb_check");
								row.setAttribute(rowIndex,"style","vertical-align:center;"); //üũ�ڽ� ��ġ ���� jskim_20150526
								
								//jkkim added ���ȹ��� ������ ǥ�� start 
								String linkScriptName = "selectAppDoc";
								boolean isDuration = false;
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
								    linkScriptName = "selectAppDocSec";
								}
								
								//	�����Ϲ�ȣ
								row.setData(++rowIndex, rsDocNumber);    //����Ʈ �� �÷� ���� 20150602_jskim
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; ");
								row.setAttribute(rowIndex, "title",rsDocNumber);
								
								//	�ݼ��ǰ�		20150819_csh
								String comment = "";
								Map<String, String> returnMap = new HashMap<String, String>();
								returnMap.put("docId", rsDocId);
							    returnMap.put("compId", compId);
							    
							    List<AppRecvVO> recvVOs = approvalService.listAppRecv(returnMap);
							    comment = CommonUtil.nullTrim(recvVOs.get(0).getSendOpinion());
								
								//	��������
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+resultLobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N','"+rsSecurityYn+"','"+rsSecurityPass+"','"+isDuration+"');\">"+rsTitle+"</A>");	
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 30px;");
								row.setAttribute(rowIndex, "title", comment);
								
								//	�ݼ�����
								row.setData(++rowIndex, rsDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:selectAppDoc('"+rsDocId+"','"+resultLobCode+"', '"+rsTransferYn+"', '"+electronDocYn+"','Y');\"> </A>");
								row.setAttribute(rowIndex, "title",titleDate);
								
								//	�ݼ۱��(�μ�)
								row.setData(++rowIndex, rsRecvDeptNames);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",rsRecvDeptNames);
								
								//	����
								if ( rolecode.contains(role11) ) {
									row.setData(++rowIndex, "<a href=\"#\" onclick=\"javascript:returnSend('"+rsDocId+"');\"><img src=\"" + webUri + "/app/ref/image/LH/button/btn_app_icon05.jpg\" border='0'></a>");
								} else {
									row.setData(++rowIndex, "");
								}
								row.setAttribute(rowIndex, "title","");
								
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
     <jsp:include page="/app/jsp/list/common/ListPagingDateForm.jsp" flush="true" /> 
     <!-- ����¡���� form  ��-->  
</acube:outerFrame>
<!-- �߼���Ȳ ��ȸ form--> 
	<form id="appDocForm" method="post" name="appDocForm">
		<input type="hidden" id="draftDeptId" name="draftDeptId" value="" />
	</form>
	<form id="frmSendInfo" name="frmSendInfo" method="POST" action="<%=webUri%>/app/approval/sendInfo.do" target="popupWin" style="display:none">
		<input type="hidden" id="sendInfoCompId" name="sendInfoCompId" value="" />
		<input type="hidden" id="sendInfoEditFlag" name="sendInfoEditFlag" value="" />
		<input type="hidden" id="sendInfoDocId" name="sendInfoDocId" value="" />
		<input type="hidden" id="sendInfoDocState" name="sendInfoDocState" value="" />
		<input type="hidden" id="sendInfoLobCode" name="sendInfoLobCode" value="" />
		<input type="hidden" id="sendInfoComment" name="sendInfoComment" value=""/><br/>
	</form>
<!-- �߼���Ȳ ��ȸ form  ��-->
</Body>
</Html>