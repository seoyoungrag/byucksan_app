<%@ page import="com.sds.acube.app.approval.vo.GwIfcuvVO" %>
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
 *  Class Name  : ListGwPibsv.jsp 
 *  Description : 거래처목록 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 07. 14 
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
	List<GwIfcuvVO> results = (List<GwIfcuvVO>) request.getAttribute("ListVo");
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());	
	
		
	int nSize = results.size();
	String resultCusnam = (String)request.getAttribute("cusnam");
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
	
	String msgHeaderCustom 				= messageSource.getMessage("list.list.title.headerCustom" , null, langType);
	String msgHeaderCustomNum 			= messageSource.getMessage("list.list.title.headerCustomNum" , null, langType);
	String msgHeaderCustomCeo 			= messageSource.getMessage("list.list.title.headerCustomerCeo" , null, langType);
	String msgHeaderCustomBusinessNum	= messageSource.getMessage("list.list.title.headerCustomerBusinessNum" , null, langType);
	String msgHeaderCustomCorNum 		= messageSource.getMessage("list.list.title.headerCustomerCorNum" , null, langType);
	String msgHeaderCustomStore 		= messageSource.getMessage("list.list.title.headerCustomerStore" , null, langType);
	String msgHeaderCustomer 			= messageSource.getMessage("list.list.title.headerCustomer" , null, langType);
	
	String msgNoData 					= messageSource.getMessage("list.list.msg.customListNoData" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
	String buttonSearch = messageSource.getMessage("list.list.button.search" , null, langType);
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><spring:message code='list.list.title.customList'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script language="javascript">
<!--
var chkAll = 0;

function check_All() {
    if(!document.formList.rsCusCde) return;
    if(chkAll==0){
        chkAll = 1;
        if(document.formList.rsCusCde.length > 1){
	        for (var i=0; i < document.formList.rsCusCde.length; i++) document.formList.rsCusCde[i].checked = true;
		}else{
			document.formList.rsCusCde.checked = true;
		}
    }else{
        chkAll = 0;
        if(document.formList.rsCusCde.length > 1){
	        for (var i=0; i < document.formList.rsCusCde.length; i++) document.formList.rsCusCde[i].checked = false;      
		}else{
			document.formList.rsCusCde.checked = false;
		}
    }
}

function changePage(p) {
	$("#cPage").val(p);
	$("#pagingList").submit();
}

function goSearch(){
	var sForm = document.listSearch;
	if(sForm.cusnam.value == ""){
		alert("<spring:message code='list.list.msg.customListNoSearchData'/>");
		sForm.cusnam.focus();
		return false;

	}

	
	$("#ListFormcPage").val("1");
	$("#listSearch").attr("target", "");
	$("#listSearch").attr("action", "");
	$("#listSearch").submit();
	
}

//거래처 등록을 위한 이벤트
function selGwPibsv(){

	if($("input#rsCusCde").size() == 0){
		alert("<spring:message code='list.list.msg.customListNoData'/>");
		return;
	}

	if($("[listFormChk]:checked").length == 0){
		alert("<spring:message code='list.list.msg.selCustomListNoData'/>");
	}else{
		var cusCde = [];
		var cusnam = [];
		
		$("[listFormChk]:checked").each(function(){
			
			cusCde.push(this.value);
			cusnam.push(this.getAttribute("cusnam"));
			
		});

		var len = cusCde.length;
		var returnString = "";
		
		for(i=0; i < len; i++){
			
			returnString += cusCde[i] + String.fromCharCode(2) + cusnam[i]+ String.fromCharCode(4);
		}
		
		if (parent != null && parent.setGwPibsv != null) {
			parent.setGwPibsv(returnString);
		}
		
	}
	
}

//-->
</script>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		
		<tr>
			<td><acube:line /></td>
		</tr>
		<tr>
			<td>
				<form name="listSearch" id="listSearch" method="post" style="margin:0px">
				<acube:tableFrame type="search">
			  <tr class="search">
			    <td width="10"></td>
			    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td width="100%" height="24"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="300"><input name="cusnam" type="text" class="input" style="width:100%" value="<%=resultCusnam%>" ></td>
			                <td width="5">&nbsp;</td>
			                <td width="58" align="right"><acube:button onClick="javascript:goSearch();" value='<%=buttonSearch%>' type="search" class="" align="left" disable="" /></td>
			                <td class="search_text">&nbsp;&nbsp;<spring:message code='list.list.msg.customListMsg'/></td>
			              </tr>
			            </table></td>
			                
			              
			            
			          </tr>
			          		          
			          		          
			          
			        </table></td>
			      </tr>     
			            
			    </table>    
			    </td>
			    <td width="10"></td>
			  </tr>
  			</acube:tableFrame>
  			<input type="hidden" name="cPage" id="ListFormcPage" value="<%=CPAGE%>">
			</form>
			</td>
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
							acubeList = new AcubeList(sLine, 8);
							acubeList.setColumnWidth("20,*,70,80,80,100,100,50");
							acubeList.setColumnAlign("center,left,center,center,center,center,center,center");	 
		
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							titleRow.setData(rowIndex,"<img src=\""+webUri+"/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
							titleRow.setAttribute(rowIndex,"onclick","javascript:check_All();");
							titleRow.setAttribute(rowIndex,"style","padding-left:2px");
		
							titleRow.setData(++rowIndex,msgHeaderCustom);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderCustomNum);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							titleRow.setData(++rowIndex,msgHeaderCustomCeo);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderCustomBusinessNum);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderCustomCorNum);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderCustomStore);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex,msgHeaderCustomer);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		
							AcubeListRow row = null;
							
						
			 				String tempAttachYn = "Y";
							// 데이타 개수만큼 돈다...(행출력)
							for(int i = 0; i < nSize; i++) {
							    
							    GwIfcuvVO result = (GwIfcuvVO) results.get(i);
								
								String rsCusCde			= CommonUtil.nullTrim(result.getCuscde()); //거래처code
							    String rsCusnam 		= CommonUtil.nullTrim(result.getCusnam()); //거래처명
							    String rsBosnam			= CommonUtil.nullTrim(result.getBosnam()); //대표자명
							    String rsBrgcde			= CommonUtil.nullTrim(result.getBrgcde()); //사업자번호
							    String rsCrgcde			= CommonUtil.nullTrim(result.getCrgcde()); //법인번호
							    String rsMgtdpt			= CommonUtil.nullTrim(result.getMgtdpt()); //부점
							    String rsMgtusr			= CommonUtil.nullTrim(result.getMgtusr()); //담당자

							    StringBuffer buff;
								row = acubeList.createRow();
					
								rowIndex = 0;
								
								buff = new StringBuffer();
								buff.append("<input type=\"checkbox\"  name=\"rsCusCde\" id=\"rsCusCde\" value=\""+rsCusCde+"\"  listFormChk=listFormChk cusnam=\""+rsCusnam+"\"  >");
								
								row.setData(rowIndex, buff.toString());
								row.setAttribute(rowIndex, "class", "ltb_check");
								row.setAttribute(rowIndex,"style","vertical-align:top;");	
								
								row.setData(++rowIndex, rsCusnam);	
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title", rsCusnam);
								
								row.setData(++rowIndex, rsCusCde);
								row.setAttribute(rowIndex, "title",rsCusCde);
								
								row.setData(++rowIndex, rsBosnam);
								row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
								row.setAttribute(rowIndex, "title",rsBosnam);
								
								row.setData(++rowIndex, rsBrgcde);
								row.setAttribute(rowIndex, "title",rsBrgcde);
								
								row.setData(++rowIndex, rsCrgcde);
								row.setAttribute(rowIndex, "title",rsCrgcde);
								
								row.setData(++rowIndex, rsMgtdpt);
								row.setAttribute(rowIndex, "title",rsMgtdpt);
								
								row.setData(++rowIndex, rsMgtusr);
								row.setAttribute(rowIndex, "title",rsMgtusr);

								
					
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
</acube:outerFrame>
<form name="pagingList" id="pagingList" method="post" style="margin:0px">
 <input name="cPage" id="cPage" type="hidden">
 <input type="hidden" name="cusnam" id="cusnam" value="<%=resultCusnam%>">
</form>
</Body>
</Html>