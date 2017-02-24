<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale, java.util.List,java.util.Map" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
                 com.sds.acube.app.design.AcubeListRow,
                 com.sds.acube.app.common.util.DateUtil,
                 com.sds.acube.app.common.util.EscapeUtil"
%>
<%@ page import="com.sds.acube.app.env.vo.CategoryVO" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
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
    String category = messageSource.getMessage("env.form.category" , null, langType);
    String categoryid = messageSource.getMessage("env.form.categoryid" , null, langType);
    String deleteyn = messageSource.getMessage("env.bizsystem.useyn" , null, langType);
    String nodata =  messageSource.getMessage("common.msg.nodata" , null, langType);
    String choice = messageSource.getMessage("env.form.choice" , null, langType);//
    String rolecode = (String) session.getAttribute("ROLE_CODES");//권한
    String admrole = AppConfig.getProperty("role_appadmin", "", "role");

    String use = messageSource.getMessage("env.form.use" , null, langType);
	String viewonly = messageSource.getMessage("env.form.viewonly" , null, langType);
	String unuse = messageSource.getMessage("env.form.unuse" , null, langType);
    
    List list = (List)request.getAttribute("list");//조회결과
    String categoryID = ""  ;              
    String categoryName = "";
    String deleteYn = "";
    int categoryOrder=0;
    String resourceId = "";
    
    if(list !=null && list.size() > 0){
 
         categoryID = ((CategoryVO)list.get(0)).getCategoryId();                    
         categoryName = ((CategoryVO)list.get(0)).getCategoryName();
         deleteYn = ((CategoryVO)list.get(0)).getDeleteYn();
         categoryOrder = ((CategoryVO)list.get(0)).getCategoryOrder();
         resourceId = ((CategoryVO)list.get(0)).getResourceId();
    }
    
    //권한map
    Map roleType = (Map)request.getAttribute("type");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript">


    var g_selCategory="";
    var g_selCategoryNm="";
    var g_selDeleteYn="";
    var g_order="";
    var g_resourceId="";

    var roleType = "<%=(String)roleType.get("roleType")%>";

    
    //alert(roleType);    
    //체크박스 선택시 이벤트
    function setUnique(p,categoryId,categoryName,deleteYn,order,resourceId) {
        // alert(p.name);
        if (p.checked){
            for (var i=0; i < document.frmForm.CK_name.length; i++){
                if (document.frmForm.CK_name[i]!=p && document.frmForm.CK_name[i].checked)
                    document.frmForm.CK_name[i].checked = false;
            }
            fnc_searchFormList(categoryId,categoryName,deleteYn,order,resourceId);
        }else{
            g_selCategory="";
            g_selCategoryNm="";
            g_selDeleteYn="";
            g_resourceId="";
        }
    }

    //체크박스 클릭시
    function clickUnique(categoryId) {
        document.getElementById(categoryId).click();
    }


    //초기 로딩될때 이벤트
    function onLoad(){
        //alert("category");
        if(<%=list.size()%>  > 0 ){
            document.frmForm.CK_name[0].checked = true;
            setUnique(document.frmForm.CK_name[0],"<%=categoryID%>","<%=categoryName%>","<%=deleteYn%>","<%=categoryOrder%>","<%=resourceId%>");
        }
    }
   
    //양식조회
    function fnc_searchFormList(categoryId,categoryName,deleteYn,order,resourceId){
        g_selCategory = categoryId;
        g_selCategoryNm = categoryName;
        g_selDeleteYn= deleteYn;
        g_selOrder = order;
        g_resourceId = resourceId;

        parent.frmForm.categoryId.value = g_selCategory;
        parent.frmForm.resourceId.value = g_resourceId;

        if(roleType =='CHARGE'){
            parent.frmformList.location.href="<%=webUri%>/app/env/form/charge/listEnvForm.do?categoryId="+categoryId;
        }else if(roleType =='ADMIN'){
            parent.frmformList.location.href="<%=webUri%>/app/env/form/admin/listEnvForm.do?categoryId="+categoryId;
        }else{
            //alert('els');
            parent.frmformList.location.href="<%=webUri%>/app/env/form/listEnvForm.do?categoryId="+categoryId;
        }
    }
    
</script>
</head>
<body topmargin=0 leftmargin=0 rightmargin=0 bottommargin=0 onLoad="javascript:onLoad();">
<form name="frmForm">

            <!------ 리스트 Table S --------->
    
            <%   
            
            //권한
            String mngrole =  "N";//
            if(rolecode.indexOf(admrole) >= 0 && "ADMIN".equals((String)roleType.get("roleType"))){
        	   mngrole="Y";//관리자권한
            } 
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
			if("Y".equals(mngrole)){            
	            acubeList = new AcubeList(sLine, 4);
	            acubeList.setColumnWidth("12%,30%,40%,*");
	            acubeList.setColumnAlign("left,left,left,left"); 
	            acubeList.setAttribute("style","table-layout:fixed");
	            acubeList.setListWithScroll(198);
			}else{
	            acubeList = new AcubeList(sLine, 2);
	            acubeList.setColumnWidth("12%,*");
	            acubeList.setColumnAlign("left,left");   
	            acubeList.setListWithScroll(198);
			}

            
            AcubeListRow titleRow = acubeList.createTitleRow();
            int rowIndex=0;
            
            titleRow.setData(rowIndex,choice);//체크박스
            //titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
			if("Y".equals(mngrole)){            
            	titleRow.setData(++rowIndex,categoryid );//양식함ID
            	//titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
			}
            titleRow.setData(++rowIndex,category);//양식함명
            //titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;word-break:break-all;");
			if("Y".equals(mngrole)){     
            	titleRow.setData(++rowIndex,deleteyn);//삭제여부
            	//titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; word-break:break-all;");
			}
            AcubeListRow row = null;
            
            //==============================================================================
            // 데이타 가져오는 부분
            //==============================================================================
        
            int iCnt = list.size();
            
            // 데이타 개수만큼 돈다...(행출력)
            for(int i = 0; i < iCnt; i++) {
    
                 categoryID = ((CategoryVO)list.get(i)).getCategoryId();                    
                 categoryName = ((CategoryVO)list.get(i)).getCategoryName();
                 deleteYn = ((CategoryVO)list.get(i)).getDeleteYn();
                 categoryOrder = ((CategoryVO)list.get(i)).getCategoryOrder();
                 resourceId = ((CategoryVO)list.get(i)).getResourceId();
                 
                 StringBuffer buff;
                
                row = acubeList.createRow();
                
                rowIndex = 0;
                buff = new StringBuffer();
                buff.append("<input type=\"checkbox\" id=\""+categoryID+"\" name=\"CK_name\" value=\""+categoryID+"\"  onclick=\"javascript:setUnique(this,'"+categoryID+"','"+EscapeUtil.escapeJavaScript(categoryName)+"','"+deleteYn+"','"+categoryOrder+"','"+resourceId+"');\">");
                
                row.setData(rowIndex, buff.toString());  
                row.setAttribute(rowIndex, "class", "ltb_check");

				if("Y".equals(mngrole)){            
                	// 양식함ID
                	row.setData(++rowIndex, "<a href=\"javascript:clickUnique('"+categoryID+"');\">" + EscapeUtil.escapeHtmlDisp(categoryID)+"</a>");  
                	//row.setAttribute(rowIndex,"style","text-overflow : ellipsis;  overflow : hidden;");
				}             
                // 양식함
                row.setData(++rowIndex, "<a href=\"javascript:clickUnique('"+categoryID+"');\">" + EscapeUtil.escapeHtmlDisp(categoryName)+"</a>");  
                //row.setAttribute(rowIndex,"style","text-overflow : ellipsis;  overflow : hidden;");
				if("Y".equals(mngrole)){               
                	// 삭제여부
                	if ("N".equals(deleteYn)) {
                    	row.setData(++rowIndex, "<a href=\"javascript:clickUnique('"+categoryID+"');\">" + use + "</a>");  
                		//ow.setAttribute(rowIndex,"style","text-overflow : ellipsis;  overflow : hidden;");
                	} else if ("T".equals(deleteYn)) {
                    	row.setData(++rowIndex, "<a href=\"javascript:clickUnique('"+categoryID+"');\">" + viewonly + "</a>");  
                		//ow.setAttribute(rowIndex,"style","text-overflow : ellipsis;  overflow : hidden;");
                    
                	} else {
                    	row.setData(++rowIndex, "<a href=\"javascript:clickUnique('"+categoryID+"');\">" + unuse + "</a>");  
                		//ow.setAttribute(rowIndex,"style","text-overflow : ellipsis;  overflow : hidden;");                	    
                	}
				}
            } // for(~)
    
            if(iCnt == 0){
    
                row = acubeList.createDataNotFoundRow();
                row.setData(0, nodata);
    
            }
            acubeList.setNavigationType("normal");
            acubeList.generatePageNavigator(false); 
            acubeList.setTotalCount(totalCount);
            acubeList.setCurrentPage(curPage);
            acubeList.generate(out);
        %>
<input type ="hidden" id="CK_name" value=""></input>
</form>
</body>
</html>