<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale, java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList,
                 com.sds.acube.app.design.AcubeListRow,
                 com.sds.acube.app.common.util.DateUtil,
                 com.sds.acube.app.common.util.EscapeUtil"
%>
<%@ page import="com.sds.acube.app.env.vo.BizSystemVO" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : SelectEnvBizSystem.jsp 
 *  Description : 시스템조회 팝업
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.25 
 *   수 정 자 : 윤동원 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  윤동원
 *  @since 2011. 4. 25 
 *  @version 1.0 
 */ 
%>

<%
    
    //결과데이타
    List list = (List)request.getAttribute("systemList");
    String nodata = messageSource.getMessage("common.msg.nodata" , null, langType);//
    String bizsystemname = messageSource.getMessage("env.bizsystem.name" , null, langType);//
    String biztypename = messageSource.getMessage("env.biztype.name" , null, langType);//
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.bizsystem.title.src"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">

    var g_systemCode;
    var g_systemName;
    var g_bizTypeCode;
    var g_bizTypeName;

    //선택 서식 set
    function setSystemName(systemCode,systemName,bizTypeCode, bizTypeName){
        //alert('선택');
        g_systemCode = systemCode;
        g_systemName = systemName;
        g_bizTypeCode = bizTypeCode;
        g_bizTypeName =bizTypeName;
        return;
    }

    
    //선택시스템
    function fnc_setSystem(){
        //alert('opener');
        if (typeof(g_systemCode) == "undefined" || g_systemCode == "") {
        	alert('<spring:message code="env.bizsystem.msg.bizsystemselect"/>');
        	return;
   		}
        opener.setSystem(g_systemCode,g_systemName,g_bizTypeCode, g_bizTypeName);
        window.close();
    }

    //페이지 변경
    function changePage(p) {
        $("#cPage").val(p);
        $("#pagingList").submit();
    }

    function init(){
        parent.rightWin = 1;
    }

    
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>

<acube:outerFrame>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
       <tr>
			<td>
		<span class="pop_title77"><spring:message code='env.bizsystem.title.src' /></span></td>
        </tr>
        <tr>
            <acube:space between="title_button" />
        </tr>
        <tr>
            <td height="100%" class="communi_text">
                <table height="300" width="100%" border="0" cellpadding="0" cellspacing="0" class="tree_table" style="position:relative;">
                <tr>
                <td>
                    <table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0" >
                    <tr>
                        <td height="100%" valign="top"  ><!------ 리스트 Table S --------->
                        
                        <%
                                          
                        //==============================================================================
                        // Page Navigation variables
                        String cPageStr = request.getParameter("CPAGE");
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
                        //==============================================================================
                        
                        int totalCount=0;   //총글수
                        int curPage=CPAGE;  //현재페이지
                        
                        AcubeList acubeList = null;
                        acubeList = new AcubeList(sLine, 2);
                        acubeList.setColumnWidth("50%,50%");
                        acubeList.setColumnAlign("center,center");   
                        acubeList.setListWithScroll(300);
            
                        AcubeListRow titleRow = acubeList.createTitleRow();
                        titleRow.setAttribute("formId","none");
                        int rowIndex=0;
            
                        titleRow.setData(rowIndex,bizsystemname);
                        titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
            
                        titleRow.setData(++rowIndex,biztypename);
                        titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
            
                        AcubeListRow row = null;
                        
                        //==============================================================================
                        // 데이타 가져오는 부분
                        //==============================================================================
                        int jCnt =0;
                        if(list !=null){
                            jCnt = list.size();
                        }
                        
                        // 데이타 개수만큼 돈다...(행출력)
                        for(int j = 0; j < jCnt; j++) {
                
                            String systemCode = ((BizSystemVO)list.get(j)).getBizSystemCode();        
                            String systemName = ((BizSystemVO)list.get(j)).getBizSystemName();
                            String bizTypeCode = ((BizSystemVO)list.get(j)).getBizTypeCode();
                            String bizTypeName = ((BizSystemVO)list.get(j)).getBizTypeName();
                                
                            StringBuffer buff;
                            row = acubeList.createRow();
            
                            //row.setAttribute("onClick","javascript:clickOnForm();");
                            row.setAttribute("style","cursor:default;");
                            
                            rowIndex = 0;
                            buff = new StringBuffer();
                            buff.append("");
                            
                            row.setData(rowIndex, "<A href=\"#\"   onClick=\"javascript:setSystemName('"+EscapeUtil.escapeJavaScript(systemCode)+"','"+EscapeUtil.escapeJavaScript(systemName)+"','"+EscapeUtil.escapeJavaScript(bizTypeCode)+"','"+EscapeUtil.escapeJavaScript(bizTypeName)+"');\">"+EscapeUtil.escapeHtmlDisp(systemName)+"</A>");                  
                            row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
            
                            row.setData(++rowIndex, bizTypeName);
                
                        } // for(~)
                
                        if(jCnt == 0){
                
                            row = acubeList.createDataNotFoundRow();
                            row.setData(0, nodata);
                
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
                </td>
                </tr>
                </table>
             </td>
             </tr>
             <tr>
              <td>
               <table align="right" border="0" cellpadding="0" cellspacing="0">
                <tr>
                <tr>
                <tr>
                <td background="./image/2.0/bu2_bg.gif" class="text_left">
                    <acube:buttonGroup>
                    <acube:button  onclick="javascript:fnc_setSystem();return(false);" value='<%=messageSource.getMessage("env.form.ok",null,langType)%>' />
                    <acube:space between="button" />
                    <acube:button  onclick="javascript:window.close();return(false);" value='<%=messageSource.getMessage("env.form.cancel",null,langType)%>' />
                    </acube:buttonGroup>
                </td>
                </tr>
               </table>
           </td>
       </tr> 
    </table> 
</acube:outerFrame>
<form name="pagingList" id="pagingList" method="post" style="margin:0px">
        <input name="cPage" id="cPage" type="hidden">
</form>
</body>
</html>