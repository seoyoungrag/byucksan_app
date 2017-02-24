<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale, java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList,
                 com.sds.acube.app.design.AcubeListRow,
                 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.env.vo.BizSystemVO" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : ListEnvBizSystem.jsp 
 *  Description : 시스템조회 
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
    List list = (List)request.getAttribute("list");

    String nodata = messageSource.getMessage("common.msg.nodata" , null, langType);//
    String choice = messageSource.getMessage("env.form.choice" , null, langType);//
    String bizsystemcode = messageSource.getMessage("env.bizsystem.code" , null, langType);//
    String bizsystemname = messageSource.getMessage("env.bizsystem.name" , null, langType);//
    String biztypecode = messageSource.getMessage("env.biztype.code" , null, langType);//
    String biztypename = messageSource.getMessage("env.biztype.name" , null, langType);//
    String bizcategory = messageSource.getMessage("env.bizsystem.doc.category" , null, langType);//
    String numberingyn = messageSource.getMessage("env.bizsystem.numberingyn" , null, langType);//
    String useyn = messageSource.getMessage("env.bizsystem.useyn" , null, langType);//
    String overlapyn = messageSource.getMessage("env.bizsystem.overlapyn" , null, langType);//
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">

    var g_systemCode="";
    var g_systemName="";
    var g_bizTypeCode="";
    var g_bizTypeName="";
    var g_useYn="";


    //tr클릭시 체크박스 선택
    function setcheck(p,systemCode,bizTypeCode){
        p.checked = true;
        setUnique(p,systemCode,bizTypeCode);
    }
    
    //체크박스
    function setUnique(p,systemCode,bizTypeCode) {

        //alert(p.checked);
        if (p.checked){
            for (var i=0; i < document.frmSystem.CK_name.length; i++){
                if (document.frmSystem.CK_name[i]!=p && document.frmSystem.CK_name[i].checked)
                    document.frmSystem.CK_name[i].checked = false;
            }
            setSystemName(systemCode,bizTypeCode);
        }
        else{
            //gstrFormId="";
            //gstrFormFileId="";
        }
    }

    //체크박스클릭
    function clickUnique(row) {
        $("input:checkbox")[row].click();
    }

    
    //선택 
    function setSystemName(systemCode,bizTypeCode){
        //alert('선택');
        g_systemCode = systemCode;
        g_bizTypeCode = bizTypeCode;

        document.frmSystem.bizSystemCode.value =g_systemCode;
        document.frmSystem.bizTypeCode.value =g_bizTypeCode;
        return;
    }


    //등록
    function fnc_insertBizSystem(){
        
        //팝업
        var popupWin = openWindow("popupWin", "<%=webUri%>/app/env/InsertEnvBizSystem.do", 550, 390);
    }

        
    //수정
    function fnc_updateBizSystem(){

    	if(g_systemCode ==""){
            alert("<spring:message code='env.bizsystem.msg.bizsystemselect'/>");
            return;
        }
        
        //팝업
        var popupWin = openWindow("popupWin", "<%=webUri%>/app/env/admincharge/selectEnvBizSystem.do?bizSystemCode="+g_systemCode+"&bizTypeCode="+g_bizTypeCode, 550, 400);
    }

    //조회
    function fnc_selectForm(){

        document.location.href="<%=webUri%>/app/env/admincharge/listEnvBizSystem.do";
    }

    //삭제
    function fnc_deleteBizSystem(){

       if( confirm("<spring:message code='env.bizsystem.msg.confirm.bizsystemdel'/>")){ 
            //submit
            $.post("<%=webUri%>/app/env/admincharge/deleteEnvBizSystem.do", $("#frmSystem").serialize(), function(data){
                if("OK" == data.result ) {  
                    
                    alert("<spring:message code='env.bizsystem.msg.bizsystemdelok'/>");
                    
                    fnc_selectForm();
                    
                } else {
                    alert("<spring:message code='env.form.msg.fail'/>"+"["+data.result+"]");
                }   
            }, 'json');
        }
    }
    
</script>
</head>
<body topmargin=0 leftmargin=0 rightmargin=0 bottommargin=0>
<acube:outerFrame>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb_table">
        <tr>
            <td colspan="2"><acube:titleBar><spring:message code='env.bizsystem.title.mng'/> </acube:titleBar></td>
        </tr>
        <tr>
            <acube:space between="title_button" />
        </tr>
        <tr>
            <td colspan="2">
            <acube:buttonGroup>
            <%
            if(!isExtWeb) { 
            %>
                <acube:menuButton onclick="javascript:fnc_insertBizSystem();" 
                value='<%= messageSource.getMessage("appcom.button.insert" , null, langType)%>' 
                     />
                <acube:space between="button" />
                <acube:menuButton onclick="javascript:fnc_updateBizSystem();" 
                value='<%= messageSource.getMessage("appcom.button.update" , null, langType)%>' 
                     />
                <acube:space between="button" />
                <acube:menuButton onclick="javascript:fnc_deleteBizSystem();" 
                value='<%= messageSource.getMessage("appcom.button.delete" , null, langType)%>' 
                     />
              <%}%>
            </acube:buttonGroup>
            </td>
        </tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
        <tr>
            <td>
                 <table width="100%" border="0" cellpadding="0" cellspacing="0" >
                    <tr>
                        <td valign="top" ><!------ 리스트 Table S --------->
                        <form name="frmSystem" id="frmSystem">
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
                        acubeList = new AcubeList(sLine, 9);
                        acubeList.setColumnWidth("3%,15%,15%,10%,*,10%,8%,8%,8%");
                        acubeList.setColumnAlign("center,center,center,center,left,center,center,center,center");   
                        acubeList.setAttribute("style","table-layout:fixed");
                        acubeList.setListWithScroll(400);
            
                        AcubeListRow titleRow = acubeList.createTitleRow();

                        int rowIndex=0;
            
                        titleRow.setData(rowIndex,"");
                        titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
                        
                        titleRow.setData(++rowIndex,bizsystemcode);
                        titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
            
                        titleRow.setData(++rowIndex,bizsystemname);
                        titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

                        titleRow.setData(++rowIndex,biztypecode);
                        titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

                        titleRow.setData(++rowIndex,biztypename);
                        titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

                        titleRow.setData(++rowIndex,bizcategory);
                        titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

                        titleRow.setData(++rowIndex,numberingyn);
                        titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

                        titleRow.setData(++rowIndex,useyn);
                        titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
                        
                        titleRow.setData(++rowIndex,overlapyn);
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
                            String bizStystemCode = ((BizSystemVO)list.get(j)).getBizSystemCode();
                            String bizTypeName = ((BizSystemVO)list.get(j)).getBizTypeName();
                            String bizCategory = ((BizSystemVO)list.get(j)).getUnitId();
                            String numberingYn = ((BizSystemVO)list.get(j)).getNumberingYn();
                            String useYn = ((BizSystemVO)list.get(j)).getUseYn();
                            String overlapYn = ((BizSystemVO)list.get(j)).getOverlapYn();
                                
                            StringBuffer buff;
                            // .. onMouseOver="this.style.backgroundColor='#F2F2F4'"
                            row = acubeList.createRow();

                         // row.setAttribute("onMouseOver","none");
                            row.setAttribute("style","cursor:hand;");
                            row.setAttribute("onClick","javascript:setcheck(document.frmSystem.CK_name["+j+"],'"+systemCode+"','"+bizTypeCode+"')");
                          
                            rowIndex = 0;
                            buff = new StringBuffer();
                            buff.append("<input type=\"checkbox\" id=\"CK_name\" name=\"CK_name\" value=\""+systemCode+"\" onclick=\"javascript:setUnique(this,'"+systemCode+"','"+EscapeUtil.escapeJavaScript(bizTypeCode)+"');\">");
                          
                            row.setData(rowIndex, buff.toString());
                            row.setAttribute(rowIndex, "class", "ltb_check");  
                            row.setData(++rowIndex,bizStystemCode);
                            
                            row.setData(++rowIndex,systemName);                  
			 			    row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
            
                            row.setData(++rowIndex,bizTypeCode);
                            row.setData(++rowIndex,bizTypeName);
			 			    row.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
                            row.setData(++rowIndex,bizCategory);
                            row.setData(++rowIndex, messageSource.getMessage(("Y".equals(numberingYn) ? "env.bizsystem.use" : "env.bizsystem.notuse"), null, langType));
                            row.setData(++rowIndex, messageSource.getMessage(("Y".equals(useYn) ? "env.bizsystem.use" : "env.bizsystem.notuse"), null, langType));
                            row.setData(++rowIndex, messageSource.getMessage(("Y".equals(overlapYn) ? "env.bizsystem.permit" : "env.bizsystem.notpermit"), null, langType));
                            //row.setData(++rowIndex, "");
                
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
                        
                        <input type="hidden" name="bizSystemCode" value=""/>
                        <input type="hidden" name="bizTypeCode" value=""/>
                        <input type="hidden" name="CK_name" value=""/>
                        
                        </form>
                        </td>
                    </tr>
                </table>
			</td>
		</tr>
	</table>         
</acube:outerFrame>
</body>
</html>