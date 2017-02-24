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
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListDeptDocRegist.jsp 
 *  Description : �����μ�������ȸ 
 *  Modification Information 
 * 
 *   �� �� �� :  
 *   �� �� �� : 
 *   �������� : 
 * 
 *  @author  �����
 *  @since 2011. 04. 25 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");

	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String compId 	= (String) session.getAttribute("COMP_ID");	// ȸ�� ID

	//==============================================================================	
// 	String listTitle = (String) request.getAttribute("listTitle");
	// �˻� ��� ��
	List<AppDocVO> results = (List<AppDocVO>) request.getAttribute("ListVo");
	SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());
	
	int nSize = results.size();
	
	String resultSearchType 	= CommonUtil.nullTrim(resultSearchVO.getSearchType());
	String resultSearchWord 	= CommonUtil.nullTrim(resultSearchVO.getSearchWord());
	String resultStartDate		= CommonUtil.nullTrim(resultSearchVO.getStartDate());
	String resultEndDate		= CommonUtil.nullTrim(resultSearchVO.getEndDate());
	String resultLobCode		= CommonUtil.nullTrim(resultSearchVO.getLobCode());
	String resultAuthDeptName	= CommonUtil.nullTrim(resultSearchVO.getSearchAuthDeptName());
	
// 	if(resultAuthDeptName != null && !"".equals(resultAuthDeptName)) {
// 		listTitle += " ( " + resultAuthDeptName + " )";
// 	}
	
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
	
	String msgHeaderType			= messageSource.getMessage("approval.from.regdiv" , null, langType);
	String msgHeaderTitle 			= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderDocNumber 		= messageSource.getMessage("list.list.title.headerAppEnfDocNumber" , null, langType);
	String msgHeaderRegistDate 		= messageSource.getMessage("list.list.title.headerAppEnfRegiNumber" , null, langType);
	String msgHeaderDraftReceive	= messageSource.getMessage("list.list.title.headerRegiReceiver" , null, langType);
	String msgHeaderRecieveSend		= messageSource.getMessage("list.list.title.headerSenderDeptName" , null, langType);
	String msgHeaderElecType		= messageSource.getMessage("list.list.title.headerElecType" , null, langType);
	String msgHeaderAttach			= messageSource.getMessage("list.list.title.headerAttach" , null, langType);
	String msgHeaderEnfType			= messageSource.getMessage("list.list.title.headerEnfType" , null, langType);
	String msgTheRest 				= messageSource.getMessage("list.list.msg.theRest" , null, langType);
	String msgCnt 					= messageSource.getMessage("list.list.msg.cnt" , null, langType);
	String headerProcStateDocmgr	= messageSource.getMessage("list.list.title.headerProcStateDocmgr", null, langType); //ó������
	String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
	String listTitle 				= messageSource.getMessage("list.listBox.title.menuDeptSerch" , null, langType);
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
							acubeList = new AcubeList(sLine, 7);
							
							acubeList.setColumnWidth("20,80,80,*,100,60,80");
							acubeList.setColumnAlign("center,center,center,left,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							//	üũ�ڽ�
							titleRow.setData(rowIndex,"<img src=\"" + webUri + "/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
							titleRow.setAttribute(rowIndex,"onclick","javascript:check_All();");
							titleRow.setAttribute(rowIndex,"style","padding-left:2px");

							//	����(����)��Ϲ�ȣ
							titleRow.setData(++rowIndex,msgHeaderDocNumber);
							titleRow.setSingleLine(rowIndex,false);		//	<nobr>	����	20150527_csh
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							//	����(����)�������
							titleRow.setData(++rowIndex,msgHeaderRegistDate);
							titleRow.setSingleLine(rowIndex,false);		//	<nobr>	����	20150527_csh
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

							//	����
							titleRow.setData(++rowIndex,msgHeaderTitle);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

							//	�߽źμ�
							titleRow.setData(++rowIndex,msgHeaderRecieveSend);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							//	���� -> ��ϱ���
							titleRow.setData(++rowIndex,msgHeaderType);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							//	�����(���������)
							titleRow.setData(++rowIndex,msgHeaderDraftReceive);
							titleRow.setSingleLine(rowIndex,false);		//	<nobr>	����	20150527_csh
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							

		
							AcubeListRow row = null;
						
							String tempAttachYn = "N";
							String det001 = appCode.getProperty("DET001","DET001","DET");
							String det002 = appCode.getProperty("DET002","DET002","DET");
							
							// ����Ÿ ������ŭ ����...(�����)
							for(int i = 0; i < nSize; i++) {
							    
							    AppDocVO result = (AppDocVO) results.get(i);
								
								String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
								String rsCompId 		= CommonUtil.nullTrim(result.getCompId());
					            String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
					            String rsDeptCategory	= EscapeUtil.escapeHtmlDisp(result.getDeptCategory());
					            int rsSerialNumber		= result.getSerialNumber();
					            int rsSubSerialNumber	= result.getSubserialNumber();
					            String rsDate 			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getApprovalDate()));
					            int rsAttach			= result.getAttachCount();
					            String rsRecvDeptNames	= EscapeUtil.escapeHtmlDisp(result.getRecvDeptNames());
					            int rsRecvDeptCnt		= result.getRecvDeptCnt();
					            String senderCompName	= EscapeUtil.escapeHtmlDisp(result.getSenderCompName());
					            String senderName		= EscapeUtil.escapeHtmlDisp(result.getSenderName());					            
					            String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
					            String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
					            String docGubun			= CommonUtil.nullTrim(rsDocId.substring(0,3));
					            String rsEnfType		= CommonUtil.nullTrim(result.getEnfType());
					            String rsTransferYn		= CommonUtil.nullTrim(result.getTransferYn());
					            String rsUnRegistYn		= CommonUtil.nullTrim(result.getUnregistYn());
					            String rsDeleteYn		= CommonUtil.nullTrim(result.getDeleteYn());
					            String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
					            String rsDrafterId		= CommonUtil.nullTrim(result.getDrafterId());
					          //jkkim added security ���� �߰� start
					            String rsSecurityYn			= CommonUtil.nullTrim(result.getSecurityYn());
					            String rsSecurityPass		= CommonUtil.nullTrim(result.getSecurityPass());
					            String rsSecurityStartDate	= CommonUtil.nullTrim(result.getSecurityStartDate());
					            String rsSecurityEndDate	= CommonUtil.nullTrim(result.getSecurityEndDate());
					            //end
					            String docTypeMsg		= "";
					            String electronDocMsg 	= "";
					            String titleMsg			= "";
					            String linkScriptName	= "";
					            String DocNumber		= "";
					            String enfTypeMsg		= "";
					            String titleDate		= "";
					            String unRegistAuthYn 	= "N";
					            String docState = CommonUtil.nullTrim(result.getDocState());//���������� �̼۱����� ���� �߰��� added by jkkim 
					            
					            if("APP".equals(rsDocId.substring(0,3))) {
					        		docTypeMsg = messageSource.getMessage("list.list.msg.docTypeProduct" , null, langType);
					        		
					        		if(rsRecvDeptCnt > 0){
						        		rsRecvDeptNames = rsRecvDeptNames+" "+msgTheRest+" "+rsRecvDeptCnt+msgCnt;
						            }
					        		
					            }else{
									//�������°� ENF610 �Ǵ� ENF620�� ���, Į������ ����(�̼�)���� ������..added by jkkim
					        		String enf610 = appCode.getProperty("ENF610","ENF610","ENF");
									String enf620 = appCode.getProperty("ENF620","ENF620","ENF");
							     	String enf630 = appCode.getProperty("ENF630","ENF630","ENF");
									String enf640 = appCode.getProperty("ENF640","ENF640","ENF");
									if(enf610.equals(docState)||enf620.equals(docState))
									    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeTransAfterReceive" , null, langType);
									else if(enf630.equals(docState)||enf640.equals(docState))
									    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeViaAfterReceive" , null, langType);
									else
					        			docTypeMsg = messageSource.getMessage("list.list.msg.docTypeReceive" , null, langType);
									//end
					        		
					        		if(!det001.equals(rsEnfType) && !det002.equals(rsEnfType) ) {
					        		    if(!"".equals(senderCompName)){
					        				rsRecvDeptNames = senderCompName;
					        		    }
					        		}else{
					        		    if(!"".equals(rsRecvDeptNames)){
				        					rsRecvDeptNames = rsRecvDeptNames;
				        		    	}
					        		}
					        		
					            }
					            
					            DocNumber = rsDeptCategory;
					            if(rsSerialNumber > 0){
					        		DocNumber = rsSerialNumber+"";
					            }
					            if(rsSubSerialNumber > 0){
					        		DocNumber = rsSubSerialNumber+"";
					            }
					            
					            titleMsg = rsTitle;
					            if("Y".equals(urgencyYn)) {
					        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
					            }

					            if("Y".equals(electronDocYn)) {
						        	if("APP".equals(docGubun)){
						        	    linkScriptName = "selectSenderDoc"; 
					        		}else{
					        		    linkScriptName = "selectEnfDoc";
					        		}
						        	
						        	electronDocMsg = messageSource.getMessage("list.list.msg.docElec" , null, langType);
						        	titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
						        	
					            }else{
					        		if("APP".equals(docGubun)){
					        		    linkScriptName = "selectNonAppDoc";  
					        		}else{
					        		    linkScriptName = "selectNonEnfDoc";
					        		}
					        		
					        		electronDocMsg = messageSource.getMessage("list.list.msg.docNoElec" , null, langType);
					        		titleDate = rsDate;
					            }
					            
					            if(det001.equals(rsEnfType) && "Y".equals(electronDocYn)) {
					        		linkScriptName = "selectAppDoc"; 
					            }
					            
					            if(!"".equals(rsEnfType)) {
					        		enfTypeMsg = messageSource.getMessage("list.code.msg."+rsEnfType.toLowerCase() , null, langType); 
					            }
					            
					            if(("".equals(rsUnRegistYn) || "N".equals(rsUnRegistYn)) && "N".equals(electronDocYn)){
					        		unRegistAuthYn = "Y";
					            }
					            
					            
					            StringBuffer buff;
					            
								row = acubeList.createRow();
								row.setAttribute("id", rsDocId);
								row.setAttribute("elecYn", electronDocYn);
					
								rowIndex = 0;
								
								//	üũ�ڽ�
								buff = new StringBuffer();
								buff.append("<input type=\"checkbox\"  name=\"docId\" id=\"docId\" value=\""+rsDocId+"\"  listFormChk=\""+unRegistAuthYn+"\"  >");
								//���������߰� 20150326_dykim							
								buff.append("<input type=\"hidden\" name=\"lobCode\" id=\"lobCode\" value=\""+resultLobCode+"\">");
								buff.append("<input type=\"hidden\" name=\"transferYn\" id=\"transferYn\" value=\""+rsTransferYn+"\">");
								buff.append("<input type=\"hidden\" name=\"electronDocYn\" id=\"electronDocYn\" value=\""+electronDocYn+"\">");
								
								row.setData(rowIndex, buff.toString());
								row.setAttribute(rowIndex, "class", "ltb_check");
								row.setAttribute(rowIndex,"style","vertical-align:center;"); //üũ�ڽ� ��ġ ���� jskim_20150526

								//	����(����)��Ϲ�ȣ
								row.setData(++rowIndex, DocNumber);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if(("Y".equals(rsUnRegistYn) && !"".equals(DocNumber))  || "Y".equals(rsDeleteYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",DocNumber);
								
								//	����(����)�������
								row.setData(++rowIndex, rsDate+"<a nohref=\"#\" id=\"a_"+rsDocId+"\" elecYn=\""+electronDocYn+"\" onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+resultLobCode+"', '"+rsTransferYn+"', '"+electronDocYn+"','Y');\"> </A>");
								if("Y".equals(rsUnRegistYn)  || "Y".equals(rsDeleteYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",titleDate);

								//	����								
								//jkkim added ���ȹ��� ������ ǥ�� start 
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
								//end
								
								if("Y".equals(rsUnRegistYn)  || "Y".equals(rsDeleteYn)) {
									row.setData(++rowIndex, rsTitle);
								}else{
								    row.setData(++rowIndex, "<a href=\"#\" onclick=\"javascript:"+linkScriptName+"('"+rsDocId+"','"+resultLobCode+"','"+rsTransferYn+"', '"+electronDocYn+"','N','"+rsSecurityYn+"','"+rsSecurityPass+"','"+isDuration+"');\">"+rsTitle+"</A>");
								}
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;  height: 30px; ");//row ���� ����  jskim_20150526
								if("Y".equals(rsUnRegistYn)  || "Y".equals(rsDeleteYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title", titleMsg);

								//	���깮���� ��� �߽źμ� ǥ�� ����		20150810_csh
								if("APP".equals(docGubun)){
									rsRecvDeptNames = ""; 
				        		}
								
								//	�߽źμ�
								row.setData(++rowIndex, rsRecvDeptNames);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; ");
								if(("Y".equals(rsUnRegistYn) && !"".equals(rsRecvDeptNames)) || "Y".equals(rsDeleteYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsRecvDeptNames);
								
								//	��ϱ���
								row.setData(++rowIndex, docTypeMsg);								
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								//rsDeleteYn
								if("Y".equals(rsUnRegistYn) || "Y".equals(rsDeleteYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title", docTypeMsg);
								
								//	�����(���������)
								row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+rsDrafterId+"');return(false);\">"+rsDrafterName+"</a>");
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								if("Y".equals(rsUnRegistYn)  || "Y".equals(rsDeleteYn)) {
							        	row.setAttribute(rowIndex,"style","text-decoration:line-through;color:red;");
							    }
								row.setAttribute(rowIndex, "title",rsDrafterName);
								
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
<form id="appDocForm" method="post" name="appDocForm" style="margin:0px">
<!-- �ǰ��˾� -->
<input type="hidden" id="returnDocId" name="returnDocId" value="" />
<input type="hidden" id="returnFunction" name="returnFunction" value="" />
<input type="hidden" id="btnName" name="btnName" value="" />
<input type="hidden" id="opinionYn" name="opinionYn" value="" />
<input type="hidden" id="comment" name="comment" value=""/>
</form>
</Body>
</Html>