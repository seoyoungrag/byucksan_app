<%@page import="java.util.HashMap"%>
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
	response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
	String deptProCd = (String)request.getAttribute("deptProCd");
	String deptProNm = (String)request.getAttribute("deptProNm");
	String clientCd = (String)request.getAttribute("clientCd");
	String clientname = (String)request.getAttribute("clientname");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/common.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script>

function select(code, name){
	window.close();
	opener.setPlant(code, name);
}
$(function() {

//	$('.table').css("top","50px");
//	$('.table_body').css("top","80px");
	$('.table_body tr:odd').css("backgroundColor","#ffffff"); 
	$(".table_body tr:odd").mouseenter(function() {
	    $(this).css("background", "#F9F9F9").css("border-radius", "3px");
	}).mouseleave(function() {
	     $(this).css("background", "#ffffff").css("border-radius", "0px");
	});
	$('.table_body tr:even').css("backgroundColor","#F0F0F0"); 
	$(".table_body tr:even").mouseenter(function() {
	    $(this).css("background", "#F9F9F9").css("border-radius", "3px");
	}).mouseleave(function() {
	     $(this).css("background", "#F0F0F0").css("border-radius", "0px");
	});
	$("#checkBtn").click(function() {
		
		$('input[name=check]:checked').each(function(idx, row) {
			var record = $(row).parents("tr");
			var approve_num = record.find("td:eq(13)")[0] == null ? "" : record.find("td:eq(13)")[0].innerText; //승인번호
			var account_num = record.find("td:eq(14)")[0] == null ? "" : record.find("td:eq(14)")[0].innerText; //사업자번호
				if(account_num!="") account_num = account_num.substr(0,3)+"-"+account_num.substr(3,2)+"-"+account_num.substr(5,5);
			var app_day = record.find("td:eq(4)")[0] == null ? "" : record.find("td:eq(4)")[0].innerText; //승인일자
				if(app_day!="") app_day = app_day.substr(0,4)+"-"+app_day.substr(4,2)+"-"+app_day.substr(6,2);

			var td2 = "";
			var approvalTotal = parseInt(record.find("td:eq(9)")[0] == null ? "0" : record.find("td:eq(9)")[0].innerText); //승인합계
			var tax = parseInt(record.find("td:eq(8)")[0] == null ? "0" : parseInt(record.find("td:eq(8)")[0].innerText)); //세금
			var accountName = record.find("td:eq(10)")[0] == null ? "" : record.find("td:eq(10)")[0].innerText; //가맹점명
			var pa = window.opener;
			pa.addMaster();
			pa.document.getElementById("deptPro_"+pa.totalCount).value = "<%=deptProCd%>";
			pa.document.getElementById("deptSub_"+pa.totalCount).value = "<%=deptProNm%>";
			pa.document.getElementById("accountId_"+pa.totalCount).value = "<%=clientCd%>";
			pa.document.getElementById("accountName_"+pa.totalCount).value = "<%=clientname%>";
			pa.document.getElementById("ammount_"+pa.totalCount).value = parseInt(approvalTotal).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			pa.document.getElementById("approveNum_"+pa.totalCount).value = approve_num;
			pa.selectDetail(pa.totalCount);
			$('input[name=vat]:checked').each(function(idx, row) {
				var innerRecord = $(row).parents("tr");
				if(record[0].rowIndex==innerRecord[0].rowIndex){ //각 로우별로 vat가 체크된 인덱스와 전체 테이블 인덱스가 일치할 때 수행된다는 의미
					//pa.document.getElementById("ammount_"+pa.totalCount).value = parseInt(approvalTotal-tax).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
					//세금이 0일때 로직이 추가되었다. by 160309 승인합계를 1.1로 나눈값이 출장비가되고, 승인합계에서 출장비를 뺀 값이 카드부가가치금???
					var taxZeroCase = false;
					if(parseInt(tax)==0){
						taxZeroCase = true;
					}
					if(taxZeroCase){
						tax = parseInt(approvalTotal/1.1);
						pa.document.getElementById("ammount_"+pa.totalCount).value = parseInt(tax).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
					}else{
						pa.document.getElementById("ammount_"+pa.totalCount).value = parseInt(approvalTotal-tax).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
					}
					pa.addMaster();
					pa.document.getElementById("deptPro_"+pa.totalCount).value = '111200300';
					pa.document.getElementById("deptSub_"+pa.totalCount).value = '카드부가가치세선급금';
					pa.document.getElementById("sum_"+pa.totalCount).value = accountName + ' VAT'; // 적요에 추가해달라 요청 by 0324
					pa.document.getElementById("accountId_"+pa.totalCount).value = 'K50260';
					pa.document.getElementById("accountName_"+pa.totalCount).value = 'B.C카드';
					if(taxZeroCase){
						pa.document.getElementById("ammount_"+pa.totalCount).value = parseInt(approvalTotal-tax).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
					}else{
						pa.document.getElementById("ammount_"+pa.totalCount).value = parseInt(tax).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
					}
						//카드부가가치선급금의 디테일 //95 + A1 + 10 + 22 + 31 + 08 + 97
						td2 = (record.find("td:eq(16)")[0] == null ? "" : record.find("td:eq(16)")[0].innerText)//세무구분  
						+ "!^" + account_num //사업자번호
						+ "!^" + (record.find("td:eq(10)")[0] == null ? "" : record.find("td:eq(10)")[0].innerText) //가맹점명
						+ "!^" + (record.find("td:eq(11)")[0] == null ? "" : record.find("td:eq(11)")[0].innerText) //가맹점 대표자명
						+ "!^" + app_day //승인일자
						+ "!^" + (record.find("td:eq(6)")[0] == null ? "" : record.find("td:eq(6)")[0].innerText)//카드번호
						//+ "!^" + (record.find("td:eq(9)")[0] == null ? "" : record.find("td:eq(9)")[0].innerText); //승인합계
						+ "!^" + (record.find("td:eq(9)")[0] == null ? "" : parseInt(approvalTotal-tax)); //승인합계
					pa.document.getElementById("tempDetail_"+pa.totalCount).value = td2;
					pa.checkCost();
					pa.selectDetail(pa.totalCount);
				};
			}); 
			pa.addMaster();
			pa.document.getElementById("chadae_"+pa.totalCount).options[1].selected = true;
			pa.document.getElementById("deptPro_"+pa.totalCount).value = '210040800';
			pa.document.getElementById("deptSub_"+pa.totalCount).value = '카드대금';
			pa.document.getElementById("sum_"+pa.totalCount).value = accountName + ' 카드대금'; // 적요에 추가해달라 요청 by 0324
			//pa.auto_no_l++;
			//pa.document.getElementById("lot_"+pa.totalCount).value = "L"+pa.dTime+""+pa.leadingZeros(pa.auto_no_l, 4);
			pa.getLotNoJson(pa.totalCount);
			pa.document.getElementById("accountId_"+pa.totalCount).value = 'K50260';
			pa.document.getElementById("accountName_"+pa.totalCount).value = 'B.C카드';
			//pa.document.getElementById("ammount_"+pa.totalCount).value = parseInt(approvalTotal).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			pa.document.getElementById("ammount_"+pa.totalCount).value = parseInt(approvalTotal).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");;
				//카드대금의 디테일 //08 + 32
				td2 = (record.find("td:eq(6)")[0] == null ? "" : record.find("td:eq(6)")[0].innerText) //카드번호
				+ "!^" + (record.find("td:eq(15)")[0] == null ? "" : record.find("td:eq(15)")[0].innerText) //발행일
				+ "!^" + (record.find("td:eq(5)")[0] == null ? "" : record.find("td:eq(5)")[0].innerText) //승인시간
				+ "!^" + (record.find("td:eq(12)")[0] == null ? "" : record.find("td:eq(12)")[0].innerText) //가맹점주소
				+ "!^" + (record.find("td:eq(13)")[0] == null ? "" : record.find("td:eq(13)")[0].innerText); //승인번호
			pa.document.getElementById("tempDetail_"+pa.totalCount).value = td2;
			pa.checkCost();
			pa.selectDetail(pa.totalCount);
		});
		window.close();
	});
});

</script>
</head>
<body topmargin=0 leftmargin=0 rightmargin=0 bottommargin=0 >

<div class="btdiv_popright">
	<span class="bt_poptop"><a href="#" id="checkBtn">확인</a></span>&nbsp;&nbsp;&nbsp;
</div>
<form name="frmForm">

            <!------ 리스트 Table S --------->
    
            <%   
            try {
			    //==============================================================================
			    // Page Navigation variables
			    String cPageStr = request.getParameter("pageNo");
			    String sLineStr = request.getParameter("sline");
			    int CPAGE = 1;
			    IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
				String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
				String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
				OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
				int sLine = Integer.parseInt(OPT424);
			    int trSu = 1;
			    int RecordSu = 0;
			    if (cPageStr != null && !cPageStr.equals(""))
				CPAGE = Integer.parseInt(cPageStr);
			    if (sLineStr != null && !sLineStr.equals(""))
				sLine = Integer.parseInt(sLineStr);

			    //==============================================================================

			    int totalCount = 0; //총글수
			    int curPage = CPAGE; //현재페이지

			    AcubeList acubeList = null;
				acubeList = new AcubeList(sLine, 17);
				acubeList.setColumnWidth("3%,3%,3%,5%,5%,5%,9%,5%,3%,5%,*,5%,20%,5%,0%,0%,0%");
				acubeList.setColumnAlign("center, center,center,center, center,center,center, center,center,center, center,center,center, center,center,center,center");	 
				acubeList.setListWithScroll(450);
				
			    AcubeListRow titleRow = acubeList.createTitleRow();
			    int rowIndex = 0;
			    
			    titleRow.setData(rowIndex,"선택");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"CK");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"VAT");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"전산번호");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"승인일자");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"승인시간");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"카드번호");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"승인금액");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"세금");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"승인합계");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"가맹점명");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"대표자명");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"가맹점 주소");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"승인번호");
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,"사업자번호");
				titleRow.setAttribute(rowIndex,"style","display  : none;");

				titleRow.setData(++rowIndex,"발행일");
				titleRow.setAttribute(rowIndex,"style","display  : none;");
				
				titleRow.setData(++rowIndex,"회계구분");
				titleRow.setAttribute(rowIndex,"style","display  : none;");		
			    
			    AcubeListRow row = null;
			    List<HashMap<String, String>> list = (List<HashMap<String, String>>)request.getAttribute("list");
			    
			    if (list.size() == 0) {
					row = acubeList.createDataNotFoundRow();
					row.setData(0, m.getMessage("bind.msg.unit.no.data", null, langType));
			    } else {
					for (int i = 0; i < list.size(); i++) {
						HashMap<String, String> map = (HashMap<String, String>)list.get(i);
						String useKbn = map.get("use_kbn").trim().equals("")?"&nbsp;":map.get("use_kbn");
						row = acubeList.createRow();
						rowIndex = 0;
						
						if(useKbn.equals("Y")){
							row.setData(rowIndex, "<input type=\"checkbox\"  name=\"check\" id=\"check_"+i+"\" value=\""+map.get("approve_num")  +"\" disabled>");
						}else{
							row.setData(rowIndex, "<input type=\"checkbox\"  name=\"check\" id=\"check_"+i+"\" value=\""+map.get("approve_num")  +"\"    >");
						}
						row.setAttribute(rowIndex, "class", "ltb_check");
						row.setAttribute(rowIndex,"style","vertical-align:top;");
						
						row.setData(++rowIndex, map.get("use_kbn").trim().equals("")?"&nbsp;":map.get("use_kbn"));
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",map.get("use_kbn"));		

						if(useKbn.equals("Y")){
							row.setData(++rowIndex, "<input type=\"checkbox\"  name=\"vat\" id=\"vat_"+i+"\" value=\"vat\" disabled>");
						}else{
							row.setData(++rowIndex, "<input type=\"checkbox\"  name=\"vat\" id=\"vat_"+i+"\" value=\"vat\"    >");
						}
						row.setAttribute(rowIndex, "class", "ltb_check");
						row.setAttribute(rowIndex,"style","vertical-align:top;");
						
						if(map.get("acc_no")!=null && !map.get("acc_no").trim().equals("")){
							row.setData(++rowIndex, map.get("acc_no"));
						}else{
							row.setData(++rowIndex, "&nbsp;");
						}
						
						
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",map.get("acc_no"));		
						
						row.setData(++rowIndex, map.get("approve_date"));
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",map.get("approve_date"));		
						
						row.setData(++rowIndex, map.get("approve_time"));
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",map.get("approve_time"));	
						
						row.setData(++rowIndex, map.get("card_num"));
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",map.get("card_num"));	
						
						row.setData(++rowIndex, map.get("approve_amt"));
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",map.get("approve_amt"));	
						
						row.setData(++rowIndex, map.get("tax"));
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",map.get("tax"));	
						
						row.setData(++rowIndex, map.get("approve_total"));
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",map.get("approve_total"));	
						
						row.setData(++rowIndex, map.get("vendor_name"));
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",map.get("vendor_name"));
						
						row.setData(++rowIndex, map.get("vendor_person"));
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",map.get("vendor_person"));
						
						row.setData(++rowIndex, map.get("vendor_address1"));
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",map.get("vendor_address1"));
						
						row.setData(++rowIndex, map.get("approve_num"));
						row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
						row.setAttribute(rowIndex, "title",map.get("approve_num"));

						row.setData(++rowIndex, map.get("vendor_tax_num"));
						row.setAttribute(rowIndex,"style","display  : none;");
						row.setAttribute(rowIndex, "title",map.get("vendor_tax_num"));
						
						row.setData(++rowIndex, map.get("publ_ymd"));
						row.setAttribute(rowIndex,"style","display  : none;");
						row.setAttribute(rowIndex, "title",map.get("publ_ymd"));
						
						row.setData(++rowIndex, map.get("ac_gubun"));
						row.setAttribute(rowIndex,"style","display  : none;");
						row.setAttribute(rowIndex, "title",map.get("ac_gubun"));
					}
			    }
			    
				acubeList.setNavigationType("normal");
				acubeList.generatePageNavigator(false); 
				acubeList.setPageDisplay(true);
				acubeList.setTotalCount(totalCount);
				acubeList.setCurrentPage(curPage);
				acubeList.generate(out);
				
			} catch (Exception e) {
			    logger.error(e.getMessage());
			}
        %>
</form>
</body>
</html>