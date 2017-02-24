<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.util.Locale, java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList,
                 com.sds.acube.app.design.AcubeListRow,
                 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.env.vo.FormVO" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.login.vo.UserProfileVO"%><!-- 사용자 정보		20150616_csh -->

<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : ListEnvForm.jsp 
 *  Description : 서식 관리
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
    String formname = messageSource.getMessage("env.form.formname" , null, langType);//양식명
    String registdate = messageSource.getMessage("env.form.registdate" , null, langType);//등록일자
    String nodata = messageSource.getMessage("common.msg.nodata" , null, langType);//
    String choice = messageSource.getMessage("env.form.choice" , null, langType);//
    String form = messageSource.getMessage("env.form.form" , null, langType);//
    
    String formId = "";
    String formName = "";
    String formFileId = "";
    String formFileName = "";
    String chargerId = "";
    String chargerName = "";
    String chargerPos = "";
    String chargerDeptId = "";
    String chargerDeptName = "";
    String firstFormType = "";
    
    //결과데이타
    List list = (List)request.getAttribute("list");
    
    //로딩시 조회결과의 첫 row
    if(list !=null && list.size() >0){
        formId        = ((FormVO)list.get(0)).getFormId();        
        formName      = ((FormVO)list.get(0)).getFormName();
        formFileId    = ((FormVO)list.get(0)).getFormFileId();
        formFileName  = ((FormVO)list.get(0)).getFormFileName();
        firstFormType = ((FormVO)list.get(0)).getFormType();
    }
    
	//	사용자 정보		20150616_csh
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	
	String[] deptList = userProfileVO.getDepartmentList();
	String roleCode = userProfileVO.getRoleCodes();
	boolean check = false;
	
	if ( roleCode.indexOf("7103") > -1 ){
		check = true;
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code="env.option.title.senderTitle"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">

    var gstrFormId = "";
    var gstrFormName = "";
    var gstrFormFileId = "";
    var gstrFormFileName = "";
    var gstrFormType = "";
    var gintCnt = <%=list !=null && list.size() > 0 ? list.size() : 0%> ;//양식갯수

    var formInfo = new Object();
    
    //체크박스
    function setUnique(p,formId,formName,formFileId, formFileName, idx, formType) {
        if (p.checked){
            for (var i=0; i < document.frmForm.CK_name.length; i++){
                if (document.frmForm.CK_name[i]!=p && document.frmForm.CK_name[i].checked)
                    document.frmForm.CK_name[i].checked = false;
            }
            setFormId(formId, formName, formFileId, formFileName, idx, formType);
        }
        else{
            gstrFormId="";
            gstrFormFileId="";
        }
    }

    //체크박스클릭
	function clickUnique(row) {
		$("input:checkbox")[row].click();
	}
    
    //선택 서식 set
    function setFormId(formId, formName, formFileId, formFileName, idx, formType){
        parent.frmForm.formId.value = formId;
    	gstrFormId = formId;
        gstrFormFileId = formFileId;
        gstrFormfileName = formFileName;
        gstrFormName = formName;
        gstrFormType = formType;

        formInfo.chargerId = $("#chargerId"+idx).val();
        formInfo.chargerName = $("#chargerName"+idx).val();
        formInfo.chargerPos = $("#chargerPos"+idx).val();
        formInfo.chargerDeptId = $("#chargerDeptId"+idx).val();
        formInfo.chargerDeptName = $("#chargerDeptName"+idx).val();
        formInfo.formType = $("#formType"+idx).val();
    }

    //초기 로딩될때 이벤트
    function onLoad(){
        
        if(<%=list !=null && list.size() > 0%>){
        	$("input:checkbox")[0].checked = true;
            setUnique($("input:checkbox")[0],"<%=formId%>","<%=formName%>","<%=formFileId%>","<%=formFileName%>",0, "<%= firstFormType %>");
         
        }
    }
    
</script>
</head>
<body topmargin=0 leftmargin=0 rightmargin=0 bottommargin=0 onLoad="javascript:onLoad();">
<form name="frmForm" >

            <!------ 리스트 Table  --------->

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
            acubeList = new AcubeList(sLine, 4);
            acubeList.setColumnWidth("12%,12%,*,25%");
            acubeList.setColumnAlign("center,center,left,center");   
            acubeList.setListWithScroll(200);
            acubeList.setAttribute("style","table-layout:fixed");
            AcubeListRow titleRow = acubeList.createTitleRow();
            titleRow.setAttribute("formId","none");
            int rowIndex=0;

            titleRow.setData(rowIndex,choice);
            titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");// display: none;");

            titleRow.setData(++rowIndex,form);
            titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

            titleRow.setData(++rowIndex,formname);
            titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");

            titleRow.setData(++rowIndex,registdate);
            titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");


            AcubeListRow row = null;
            
            //==============================================================================
            // 데이타 가져오는 부분
            //==============================================================================
            int jCnt =0;
            if(list !=null && list.size()>0){
                jCnt = list.size();
            }
            
            // 데이타 개수만큼 돈다...(행출력)
            for(int j = 0; j < jCnt; j++) {
    
                formId = ((FormVO)list.get(j)).getFormId();        
                formName = ((FormVO)list.get(j)).getFormName();
                String registDate = ((FormVO)list.get(j)).getRegistDate();
                String groupType = ((FormVO)list.get(j)).getGroupType();
                String categoryId = ((FormVO)list.get(j)).getCategoryId();
                formFileId = ((FormVO)list.get(j)).getFormFileId();
                formFileName = ((FormVO)list.get(j)).getFormFileName();
                chargerId =     ((FormVO)list.get(j)).getChargerId();
                chargerName =     ((FormVO)list.get(j)).getChargerName();
                chargerPos =     ((FormVO)list.get(j)).getChargerPos();
                chargerDeptId =     ((FormVO)list.get(j)).getChargerDeptId();
                chargerDeptName =     ((FormVO)list.get(j)).getChargerDeptName();
                String formType  = ((FormVO)list.get(j)).getFormType();
                String auditformYn  = ((FormVO)list.get(j)).getAuditformYn();
                
                
                StringBuffer buff;
                row = acubeList.createRow();

                row.setAttribute("style","cursor:default;");
                
                rowIndex = 0;
                buff = new StringBuffer();
                buff.append("<input type=\"checkbox\" id=\"CK_name\" name=\"CK_name\" value=\""+formId+"\" onclick=\"javascript:setUnique(this,'"+formId+"','"+EscapeUtil.escapeJavaScript(formName)+"','"+EscapeUtil.escapeJavaScript(formFileId)+"','"+EscapeUtil.escapeJavaScript(formFileName)+"','"+j+"','" + formType + "');\">")
                     .append("<input type=\"hidden\" id=\"chargerId"+j+"\" value=\""+chargerId+"\"/>")
                     .append("<input type=\"hidden\" id=\"chargerName"+j+"\" value=\""+chargerName+"\"/>")
                     .append("<input type=\"hidden\" id=\"chargerPos"+j+ "\" value=\""+chargerPos +"\"/>")
                     .append("<input type=\"hidden\" id=\"chargerDeptId"+j+"\" value=\""+chargerDeptId+"\"/>")
                     .append("<input type=\"hidden\" id=\"chargerDeptName"+j+"\" value=\""+chargerDeptName+"\"/>")
                     .append("<input type=\"hidden\" id=\"formType"+j+"\" value=\""+formType+"\"/>");
                
                row.setData(rowIndex, buff.toString());
                row.setAttribute(rowIndex, "class", "ltb_check");
//                 row.setAttribute(rowIndex, "style", "display: none;");
                
                rowIndex += 1;
                if ("1".equals(formType)) {
                	row.setData(rowIndex, "<img src=\""+webUri+"/app/ref/image/attach/attach_hwp.gif\" />");
                } else if ("2".equals(formType)) {   
                	row.setData(rowIndex, "<img src=\""+webUri+"/app/ref/image/attach/attach_doc.gif\" />");		
                } else if ("3".equals(formType)) {  
                	row.setData(rowIndex, "<img src=\""+webUri+"/app/ref/image/attach/attach_html.gif\" />");	
                }
                
                rowIndex += 1;
                row.setData(rowIndex,"<a href=\"javascript:clickUnique('"+j+"');\">"+EscapeUtil.escapeHtmlDisp((formName.length() > 25 ? formName.substring(0,25)+"..." :formName))+"</a>");                  
                row.setAttribute(rowIndex, "title", EscapeUtil.escapeHtmlDisp(formName));

                rowIndex += 1;
                row.setData(rowIndex, registDate);
    
            } // for(~)
    
            if(jCnt == 0){
    
                row = acubeList.createDataNotFoundRow();
                row.setData(0, nodata);
                row.setAttribute(0,"style","text-overflow : ellipsis; overflow : hidden;");
            }
            acubeList.setNavigationType("normal");
            acubeList.generatePageNavigator(false); 
            acubeList.setTotalCount(totalCount);
            acubeList.setCurrentPage(curPage);
            acubeList.generate(out);
            %>
</form>
</body>
</html>