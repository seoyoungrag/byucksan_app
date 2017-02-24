<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@ page import="com.sds.acube.app.common.vo.DepartmentVO"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.env.vo.EmptyInfoVO"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil" %>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppLineVO" %>

<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : ApprovalLinePopList.jsp 
 *  Description : �������� �������
 *  Modification Information
 * 
 *   �� �� �� : 2011.03.23 
 *   �� �� �� : ����ȫ
 *   �������� : 2011.06.01 �Ű���  - �����α׷� �� �߰�
 * 
 *  @author  ����ȫ
 *  @since 2011. 03. 23 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService"); // jth8172 2012 �Ű��� TF
	String compId = (String) session.getAttribute("COMP_ID"); // ����� �Ҽ� ȸ�� ���̵�
	String compName		= (String) session.getAttribute("COMP_NAME");
	String deptId		= (String) session.getAttribute("DEPT_ID");
	String deptName		= (String) session.getAttribute("DEPT_NAME");

	// variable naming rule: {tgw_app_code.code_value(ART010~ART070)}_{tgw_app_option.option_id(OPT001~OPT023)}. 2012.03.23. edited by bonggon.choi.
	String art010_opt001 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT001", "OPT001", "OPT"));	
	String art020_opt002 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT002", "OPT002", "OPT"));	
	String art030_opt003 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT003", "OPT003", "OPT"));	
	String art031_opt004 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT004", "OPT004", "OPT"));	
	String art032_opt005 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT005", "OPT005", "OPT"));	
	String art033_opt006 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT006", "OPT006", "OPT"));	
	String art034_opt007 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT007", "OPT007", "OPT"));	
	String art035_opt008 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT008", "OPT008", "OPT"));	

	String art130_opt053 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT053", "OPT053", "OPT"));	// jth8172 2012 �Ű��� TF
	String art131_opt054 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT054", "OPT054", "OPT"));	// jth8172 2012 �Ű��� TF
	String art132_opt055 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT055", "OPT055", "OPT"));	// jth8172 2012 �Ű��� TF
	String art133_opt056 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT056", "OPT056", "OPT"));	// jth8172 2012 �Ű��� TF
	String art134_opt057 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT057", "OPT057", "OPT"));	// jth8172 2012 �Ű��� TF
	String art135_opt058 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT058", "OPT058", "OPT"));	// jth8172 2012 �Ű��� TF
	
	String art040_opt009 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT009", "OPT009", "OPT"));	
	String art041_opt010 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT010", "OPT010", "OPT"));	
	String art042_opt011 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT011", "OPT011", "OPT"));	
	String art043_opt012 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT012", "OPT012", "OPT"));	
	String art044_opt013 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT013", "OPT013", "OPT"));	
	String art045_opt021 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT021", "OPT021", "OPT"));	
	String art046_opt022 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT022", "OPT022", "OPT"));	
	String art047_opt023 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT023", "OPT023", "OPT"));	
	String art050_opt014 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT014", "OPT014", "OPT"));	
	String art051_opt015 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT015", "OPT015", "OPT"));	
	String art052_opt016 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT016", "OPT016", "OPT"));	
	String art053_opt017 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT017", "OPT017", "OPT"));	
	String art054_opt018 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT018", "OPT018", "OPT"));	
	String art055_opt059 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT059", "OPT059", "OPT"));	// jth8172 2012 �Ű��� TF
	String art060_opt019 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT019", "OPT019", "OPT"));	
	String art070_opt020 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT020", "OPT020", "OPT"));
	
	//	�������, ��ȹ���� �߰�		20150422_csh
	String art081_opt030 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT030", "OPT030", "OPT"));	//	�������	
	String art082_opt031 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT031", "OPT031", "OPT"));	//	�������(���)
	String art083_opt032 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT032", "OPT032", "OPT"));	//	�������(����)
	String art084_opt033 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT033", "OPT033", "OPT"));	//	�������(����)
	String art091_opt040 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT040", "OPT040", "OPT"));	//	��ȹ����
	String art092_opt041 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT041", "OPT041", "OPT"));	//	��ȹ����(���)
	String art093_opt042 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT042", "OPT042", "OPT"));	//	��ȹ����(����)
	String art094_opt043 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT043", "OPT043", "OPT"));	//	��ȹ����(����)
	
	String adddeptBtn = messageSource.getMessage("approval.button.applines.adddept", null, langType); // �μ��߰�
	String addlineBtn = messageSource.getMessage("approval.button.applines.addline", null, langType) ;// �߰�
	String modlineBtn = messageSource.getMessage("approval.button.applines.modline", null, langType); // ����
	String dellineBtn = messageSource.getMessage("approval.button.applines.delline", null, langType); // ����

	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); // Ȯ��
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // �ݱ�
	
	String adminFlag = CommonUtil.nullTrim((String) request.getParameter("adminFlag"));
	List<EmptyInfoVO> emptyInfoList = new ArrayList<EmptyInfoVO>();
	if ("Y".equals(adminFlag)) {
		emptyInfoList = (List<EmptyInfoVO>) request.getAttribute("emptyInfos");
	}

	List<DepartmentVO> results = (List<DepartmentVO>) request.getAttribute("results");	
	pageContext.setAttribute("userProfile", session.getAttribute("userProfile"));
	pageContext.setAttribute("DEPT_ID", session.getAttribute("DEPT_ID"));

	String PART_ID = (String) session.getAttribute("PART_ID");	
	PART_ID = (PART_ID == null? "" : PART_ID );
	pageContext.setAttribute("PART_ID", PART_ID);
	
	String ROLE_CODES = (String) session.getAttribute("ROLE_CODES");
	
	String linetype = request.getParameter("linetype");
	linetype = (linetype == null ? "1" : linetype);
	
	String tbColor = "#E3E3E3";
	
	String audittype = request.getParameter("audittype");
	audittype = (audittype == null? "U" : audittype);
	pageContext.setAttribute("audittype", audittype); // Y : ����Ÿ��
	
	//������ ���� ���� �� �� Y �������϶��� N�� �ѱ��.
	String groupYn = request.getParameter("groupYn");
	groupYn = (groupYn == null) ? "N" : groupYn;
	pageContext.setAttribute("groupYn", groupYn);
	
	String currentDate = DateUtil.getCurrentDate();
	envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String opt378 = appCode.getProperty("OPT378", "OPT378", "OPT"); // ó������ �������� ���� ��뿩��
	opt378 = envOptionAPIService.selectOptionValue(compId, opt378);
	String opt379 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT379", "OPT379", "OPT"));
	String opt501 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT501", "OPT501", "OPT"));
	String opt502 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT502", "OPT502", "OPT"));

	// �������� Ÿ�� 
	String formBodyType = (String) request.getParameter("formBodyType");
	
	List<AppDocVO> appDocVOs = (List<AppDocVO>) request.getAttribute("appDocVOs");
	int docCount = appDocVOs.size();
		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code="approval.title.applines" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<style type="text/css">
	.range {display:inline-block;width:90px;height:25px; !important;}
</style>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript"><!--

//�����û�ڵ�
var ART010 = '<%=appCode.getProperty("ART010","ART010","APP") %>'; //���       
var ART020 = '<%=appCode.getProperty("ART020","ART020","APP") %>'; //����
var ART021 = '<%=appCode.getProperty("ART021","ART021","APP") %>'; //����(�ְ��μ�)
var ART030 = '<%=appCode.getProperty("ART030","ART030","APP") %>'; //����       
var ART031 = '<%=appCode.getProperty("ART031","ART031","APP") %>'; //��������   
var ART032 = '<%=appCode.getProperty("ART032","ART032","APP") %>'; //�μ�����   
var ART033 = '<%=appCode.getProperty("ART033","ART033","APP") %>'; //����(���) 
var ART034 = '<%=appCode.getProperty("ART034","ART034","APP") %>'; //����(����) 
var ART035 = '<%=appCode.getProperty("ART035","ART035","APP") %>'; //����(����) 

var ART130 = '<%=appCode.getProperty("ART130","ART130","APP") %>'; //����       // jth8172 2012 �Ű��� TF
var ART131 = '<%=appCode.getProperty("ART131","ART131","APP") %>'; //��������   // jth8172 2012 �Ű��� TF
var ART132 = '<%=appCode.getProperty("ART132","ART132","APP") %>'; //�μ�����   // jth8172 2012 �Ű��� TF
var ART133 = '<%=appCode.getProperty("ART133","ART133","APP") %>'; //����(���) // jth8172 2012 �Ű��� TF
var ART134 = '<%=appCode.getProperty("ART134","ART134","APP") %>'; //����(����) // jth8172 2012 �Ű��� TF
var ART135 = '<%=appCode.getProperty("ART135","ART135","APP") %>'; //����(����) // jth8172 2012 �Ű��� TF

var OPT051 = '<%=appCode.getProperty("OPT051","OPT051","OPT") %>'; //���� 		// jth8172 2012 �Ű��� TF
var OPT052 = '<%=appCode.getProperty("OPT052","OPT052","OPT") %>'; //���� 		// jth8172 2012 �Ű��� TF
var OPT053 = '<%=appCode.getProperty("OPT053","OPT053","OPT") %>'; //���μ������� // jth8172 2012 �Ű��� TF



var ART040 = '<%=appCode.getProperty("ART040","ART040","APP") %>'; //����  
var ART041 = '<%=appCode.getProperty("ART041","ART041","APP") %>'; //�μ�����   
var ART042 = '<%=appCode.getProperty("ART042","ART042","APP") %>'; //����(���) 
var ART043 = '<%=appCode.getProperty("ART043","ART043","APP") %>'; //����(����) 
var ART044 = '<%=appCode.getProperty("ART044","ART044","APP") %>'; //����(����)

//	�������		20150422_csh
var ART081 = '<%=appCode.getProperty("ART081","ART081","APP") %>'; //�������   
var ART082 = '<%=appCode.getProperty("ART082","ART082","APP") %>'; //�������(���) 
var ART083 = '<%=appCode.getProperty("ART083","ART083","APP") %>'; //�������(����) 
var ART084 = '<%=appCode.getProperty("ART084","ART084","APP") %>'; //�������(����) 

//	��ȹ����		20150422_csh
var ART091 = '<%=appCode.getProperty("ART091","ART091","APP") %>'; //��ȹ����   
var ART092 = '<%=appCode.getProperty("ART092","ART092","APP") %>'; //��ȹ����(���) 
var ART093 = '<%=appCode.getProperty("ART093","ART093","APP") %>'; //��ȹ����(����) 
var ART094 = '<%=appCode.getProperty("ART044","ART094","APP") %>'; //��ȹ����(����) 

var ART045 = '<%=appCode.getProperty("ART045","ART045","APP") %>'; //�ϻ󰨻�
var ART046 = '<%=appCode.getProperty("ART046","ART046","APP") %>'; //�ع�����
var ART047 = '<%=appCode.getProperty("ART047","ART047","APP") %>'; //��������

var ART050 = '<%=appCode.getProperty("ART050","ART050","APP") %>'; //����       
var ART051 = '<%=appCode.getProperty("ART051","ART051","APP") %>'; //����       
var ART052 = '<%=appCode.getProperty("ART052","ART052","APP") %>'; //���       
var ART053 = '<%=appCode.getProperty("ART053","ART053","APP") %>'; //1������    
var ART054 = '<%=appCode.getProperty("ART054","ART054","APP") %>'; //�Ŀ�       
var ART055 = '<%=appCode.getProperty("ART055","ART055","APP") %>'; //�뺸  // jth8172 2012 �Ű��� TF
var ART060 = '<%=appCode.getProperty("ART060","ART060","APP") %>'; //����       
var ART070 = '<%=appCode.getProperty("ART070","ART070","APP") %>'; //��� 

var APT001 = '<%=appCode.getProperty("APT001","APT001","APT") %>'; //����
var APT003 = '<%=appCode.getProperty("APT003","APT003","APT") %>'; //���
var APT004 = '<%=appCode.getProperty("APT004","APT004","APT") %>'; //����

var role_dailyinpectreader = '<%=AppConfig.getProperty("role_dailyinpectreader","","role")%>'; //�ϻ󰨻����� ��ȸ�� 
var role_dailyinpecttarget = '<%=AppConfig.getProperty("role_dailyinpecttarget","","role")%>'; //�ϻ󰨻�����
var role_ceo = '<%=AppConfig.getProperty("role_ceo","","role")%>'; // CEO�ڵ�
var role_officer = '<%=AppConfig.getProperty("role_officer","","role")%>'; // �ӿ��ڵ�

var CURRART = ""; //�μ����� �� �μ����� ���� �� ����
var DOUBLEART = ""; //���߰��� ó�� ������ üũ
var bDouble = false; //���� �μ�ó�� ���θ� �����Ѵ�.

var g_Arts = new Array();
var isCtrl = false, isShift = false; //keyChecked

var gSaveObject = g_selector();
var sColor = "#F2F2F4";
var tbColor = "<%=tbColor%>";

//������� tr ��ü�� ��� Valuable
var gSaveLineObject = g_selector();

//ȣ�� ���������� ������ ��θ� �޾ƿ´�.
var g_lines = ""; 
var position = null;	//	��������	20150313_jskim

var currentDate = new Date(<%=currentDate.substring(0,4) %>,<%=currentDate.substring(5,7) %>-1,<%=currentDate.substring(8,10) %>,<%=currentDate.substring(11,13) %>,<%=currentDate.substring(14,16) %>,<%=currentDate.substring(17,19) %>);
//����μ� ����
var isInspection = false;
var isDiscuss = false;
var isControl = false;

//2015.07.28_lsk_����� ���� ��ȸ �˾�â ��Ī
var Org_ViewUserInfo = null;

$(document).ready(function() {
	makeArtNames();
	$("#org_tree").tree({
		rules : {
		valid_children : [ "root" ]
		},
		types : {
			// all node types inherit the "default" node type
			"default" : {
				deletable : false,
				renameable : false,
				draggable : false
			},
			"root" : {				
				icon: {
					valid_children : [ "comp" ],
					image : "<%=webUri%>/app/ref/js/jsTree/demo/root.gif"	
				}
			},
	
			"comp" : {
				icon: {
				valid_children : [ "dept" ],
				image : "<%=webUri%>/app/ref/js/jsTree/demo/comp.gif"
	
				}
			},
	
			"dept" : {
				icon: {
				valid_children : [ "part" ],
				image : "<%=webUri%>/app/ref/js/jsTree/demo/dept.gif"	
				}
			},
			"part" : {
				icon: {
				valid_children : [ "none" ],
				image : "<%=webUri%>/app/ref/js/jsTree/demo/part.gif"	
				}
			}			
		},
		callback : {
			//��尡 OPEN �ɶ�
			onopen: function(node, tree_obj) {
				openNode(node, tree_obj);
			},
			//��带 �������� ��
			onselect : function(node, tree_obj){
				selectNode(node, tree_obj);
			},
			//��� ������ ȣ���
			oncreate : function(node, parent, type, tree_obj, rollback) {
// 			},
// 			ondblclk : function(node, tree_obj){
// 				dbclickNode(node, tree_obj);
			}
		}
	});
	
    //���μ� ������ �� ������ disabled
	$('li a').each(function() {
		var orgName = $(this).text();
		var disabledHtml = "<ins style='vertical-align:top'></ins><font color=#BDBCBC>" + orgName + "</font>";
		
		$(this).addClass('disabled');
		$(this).attr('style', 'cursor:default;float:left;');
		$(this).html(disabledHtml);
	});
    
    
    //���� ���������� �μ� Setting 
	getDeptAuth('init');
    
	var errOrg = $('li[orgType="2"][parentId="'+$('li[orgType=0]').attr('orgId')+'"]');
	errOrg.remove(); //�μ��̸鼭 �׷��� �θ�� �ϴ� �� ����
	
	$.tree.focused().select_branch('#${userProfile.deptId}');
	
	<c:choose>
		<c:when test='${PART_ID != ""}'>
			$.tree.focused().select_branch('#${DEPT_ID}');
			$.tree.focused().open_branch('#${DEPT_ID}');
			$.tree.focused().select_branch('#${PART_ID}');
		</c:when>
		<c:otherwise>
			$.tree.focused().select_branch('#${DEPT_ID}');
		</c:otherwise>	
	</c:choose>
	
	
	
	//�����θ� ȣ���Ѵ�.	
	lineInit(setAppLine());
});

//$(document).ready ����
$(document).bind('keyup', function(event){
	var keyCode = event.which;
	if(keyCode === 17) isCtrl=false;  
	if(keyCode === 16) isShift=false; 
	
});

$(document).bind('keydown', function(event){
	var keyCode = event.which;
	if(keyCode === 17) isCtrl=true;  
	if(keyCode === 16) isShift=true; 

});

//�������ΰ˻� �߰�
$('#userName').live('keydown', function(event){
	var keyCode = event.which;
	if(keyCode === 13){
		goSearch();
	}
});
//------------------------Ʈ�� �̺�Ʈ ����--------------------------------------------------------

//�������� �μ� Array
var deptArray = new Array();

// �������� �μ� Setting
function getDeptAuth(type) {
	
	var ownDeptId		= "<%= deptId %>";
	var ownDeptName 	= "<%= deptName %>";
	var deptData		= "";
	
	if(ownDeptName == "") {
		ownDeptName ="<%= compName %>";
	}
	
	//���κμ� ���ð����ϵ��� Setting
	if(type == 'init') {
		deptData = {
			    "orgID"		: ownDeptId,
			    "orgName"	: ownDeptName,
			    "authType"  : "own"
		};
		
		deptArray.push(deptData);

		//�����μ� ���ð����ϵ��� Setting
		var url = "<%=webUri%>/app/common/AllDepthOrgTreeAjax.do";
		url += ("?orgID=" + ownDeptId + "&treeType=1");
		
		var subDeptResults = "";
		
		$.ajaxSetup({async:false});
		$.getJSON(url,function(data){
			subDeptResults = data;
		});
		
		if(subDeptResults != null && subDeptResults != "") {
			for(var i = 0; i < subDeptResults.length ; i++) {
				
				deptData = {
					    "orgID"		: subDeptResults[i].orgID,
					    "orgName"	: subDeptResults[i].orgName,
					    "authType"  : "sub"
				};
				
				deptArray.push(deptData);
			}
		} 

		//������ϴ��� �����ϵ��� ������ �μ� ���ð����ϵ��� Setting
		var url = "<%=webUri%>/app/env/ajaxSelectShareDept.do";
		url += "?targetDeptId=" + ownDeptId;
		
		var ShareDeptResults = "";
		
		$.ajaxSetup({async:false});
		$.getJSON(url,function(data){
			ShareDeptResults = data;
		});

		if(ShareDeptResults != null && ShareDeptResults != "") {
			for(var j = 0; j < ShareDeptResults.length ; j++) {
				
					deptData = {
						    "orgID"		: ShareDeptResults[j].shareDeptId,
						    "orgName"	: ShareDeptResults[j].shareDeptName,
						    "authType"  : "share"
					};
					
					deptArray.push(deptData);
			}
		}
	}

	if(deptArray != null && deptArray.length > 0) {
		for(var k = 0; k < deptArray.length; k++) {
			
			if(deptArray[k].authType == "own") {

				//������ ������ �� �ִ� �μ��� ��� ���ð����ϵ��� enable
				$('#' + deptArray[k].orgID + '> a').each(function(i) {
					if($(this).attr('class') == 'disabled') {
						$(this).removeClass('disabled');
						$(this).attr('style', '');
						$(this).html("<ins style='vertical-align:top'></ins>" + deptArray[k].orgName);
					}
				});	
			}
		}
	}
}


//��Ʈ�� �������� �߻��ϴ� �̺�Ʈ�� ó���ϴ� �Լ�
var openNodeId = "";
function openNode(node, tree_obj){
	var nSize = tree_obj.children(node).length; //��������� �� 
	var url = "<%=webUri%>/app/common/OrgTreeAjax.do";
	url += ("?orgID="+node.id+"&treeType=1");
	
	if(nSize < 1 && openNodeId !== node.id){
		var results = null;
		openNodeId = node.id;
		
		$.ajaxSetup({async:false});
		$.getJSON(url,function(data){
			results = data;
		});
		drawSubTree(node, results);
		$('#'+node.id+' li').removeClass('leaf');
		$('#'+node.id+' li').addClass('closed');	
	}
	//$.tree.focused().select_branch("#"+node.id);
}

//���� ��带 �׸��� �Լ�
function drawSubTree(node, nodeObj){
	var t = $.tree.focused();

	for(var i=0; i < nodeObj.length; i++) {
		try {
		var rel = "";
		
		if(nodeObj[i].orgType === 0){
			rel="root";
		}else if(nodeObj[i].orgType === 1){
			rel="comp";
		}else if(nodeObj[i].orgType === 2){
		<% if ("Y".equals(opt378)) { %>
			if(nodeObj[i].isProcess){
				rel="proc_dept";
			}else{
				rel="dept";
			}
		<% } else { %>	
			rel="dept";
		<% } %>	
		}else{
			rel="part";
		}
		
		t.create({attributes:{'id':nodeObj[i].orgID, 'parentId':nodeObj[i].orgParentID, 'depth':nodeObj[i].hasChild, 'orgType':nodeObj[i].orgType, 'orgName':nodeObj[i].orgName, 'rel': rel},
			data:{title:nodeObj[i].orgName}}, '#'+nodeObj[i].orgParentID);

			//	��Ʈ�� ��� ���� �ȵ�	20150515_csh
			if ( nodeObj[i].orgParentID != "<%= deptId %>" ) {
				$('#'+ nodeObj[i].orgID +' a').addClass('disabled');
				$('#'+ nodeObj[i].orgID +' a').attr('style', 'cursor:default;float:left;');
				$('#'+ nodeObj[i].orgID +' a').html("<ins style='vertical-align:top'></ins><font color=#BDBCBC>" + nodeObj[i].orgName + "</font>");
			}
		
		} catch(e) {}
	}
}


//��尡 ���õǾ��� �߻��ϴ� �̺�Ʈ ó�� �Լ�
function selectNode(node, tree_obj){
	var nSize = tree_obj.children(node).length; //��������� �� 
	var url = "<%=webUri%>/app/common/UsersByOrgAjax.do";
	
	url += ("?orgID="+node.id+"&orgType="+node.getAttribute("orgType"));
	
	var bRet = $('#'+ node.id +' a').hasClass('disabled');
	
	// disabled �� ��� ���� ���ϵ���
	if(bRet) {
		$('#'+ node.id +' a').removeClass('clicked');
		return false;
	}
	
	var results = null;
	$.ajaxSetup({async:false});
	$.getJSON(url,function(data){
		results = data;
	});	
	insertUser(results);
	url = "<%=webUri%>/app/common/OrgbyDeptAjax.do";
	url += "?deptID="+node.id;
	$.getJSON(url,function(data){
		results = data;
	});
	addAttrOrg(results, node);
	gSaveObject.restore();
}

//2015.07.28_lsk_����� ���� �˾� ȣ��
function onFindUserInfo(strUserID){
	if (strUserID == "" || strUserID == null) {
		alert("<spring:message code='list.list.msg.noSearchUser'/>");
		return;
	}

	var strUrl = "";
	var height = "";
	strUrl = "<%=webUri%>/app/common/userInfo.do?userId="+strUserID+"&compid=<%=compId%>";
	height = "470";

	var top = (screen.availHeight - 560) / 2;
	var left = (screen.availWidth - 800) / 2;
	var option = "top="+top+",left="+left+",toolbar=no,resizable=no, status=no, width=600,height=470";

	if(Org_ViewUserInfo != null && Org_ViewUserInfo.closed == false) {
		Org_ViewUserInfo.close();
	}
	
	Org_ViewUserInfo = openWindow("Org_ViewUserInfoWin", strUrl , "500", height, "no", "post", "no");
}

//���õ� �μ��� ����� ������ ����� ��� �� �����Ѵ�. 
function insertUser(results){
	var tbl = $('#tbUsers tbody');
	var strUsers = "";
	//tbl.html("");
	tbl.empty();
	if(results.length > 0){
		for(var i = 0; i < results.length; i++){
			var user = results[i];
			var bEmpty = bIsEmpty(user.isEmpty, user.emptyStartDate, user.emptyEndDate);
			var strEmpty = "N";

			if(bEmpty){
				strEmpty = "Y";
			}else{
				strEmpty = "N";
			}
			
			var row = "<tr id='"+user.userUID+"' deptID='"+ user.deptID+"' deptName='"+ user.deptName +"' userName='"+ user.userName;
			row += "' gradeCode='"+ user.gradeCode + "' gradeName='"+ user.gradeName +"' compID='"+ user.compID +"' compName='"+ user.compName;
			row += "' userID='"+ user.userID + "' positionCode='"+ user.positionCode +"' positionName='"+ user.displayPosition +"' titleName='"+ user.titleName;
			row += "' roleCodes='"+ user.roleCodes + "' rowOrd='"+ i + "' empty='" + strEmpty;
			row += "' ondblclick='onListDblClick($(\"#"+user.userUID+"\"));' onmousemove='onListMouseMove();' onkeydown='onListKeyDown($(\"#"+user.userUID+"\"));' >";		
			var userPosition = (user.displayPosition == ""?"&nbsp;":user.displayPosition);
			// 2015.07.28_lsk_���� Ŭ���� ����� ���� �˾�â ȣ��
			row += "<td class='ltb_center' style='cursor: pointer; border-bottom: "+tbColor+" 1px solid;border-left:1pt solid  "+tbColor+";' width='145' onclick=\"javascript:onFindUserInfo('"+user.userUID+"');return(false);\" >"+userPosition+"</td>";
			// 2015.07.28_lsk_�̸� Ŭ���� ������ �Է�
			row += "<td class='ltb_center' style='cursor: pointer; border-bottom: "+tbColor+" 1px solid;border-left:1pt solid  "+tbColor+";border-right:1pt solid  "+tbColor+";' title='"+user.emptyReason+"' onclick='onListClick($(\"#"+user.userUID+"\"));'>"+user.userName
			if(bEmpty){
				row += "&nbsp;<img src='<%=webUri%>/app/ref/image/user_absence.gif' align=absmiddle alt=''>";
			}
			row += "</td></tr>";
			tbl.append(row);
		}
	}else{
		var row = "<tr>";
		row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;'colsapn='2'><spring:message code='app.alert.msg.19' /></td>";
		row += "</tr>";
		tbl.append(row);
	}
}

//���翩�θ� �����´�.
function bIsEmpty(isEmpty, emptyStartDate, emptyEndDate){
	if(isEmpty){
		if(emptyStartDate == "") return false;
		if(emptyEndDate == "") return false;
		
		var sYy = Number(emptyStartDate.substring(0,4));
		var sMm = Number(emptyStartDate.substring(5,7));
		var sDd = Number(emptyStartDate.substring(8,10));
		var sHs = Number(emptyStartDate.substring(11,13));
		var sMi = Number(emptyStartDate.substring(14,16));
		var sSs = Number(emptyStartDate.substring(17,19));
		var sDate = new Date(sYy, sMm-1, sDd, sHs,sMi,sSs);
		var eYy = Number(emptyEndDate.substring(0,4));
		var eMm = Number(emptyEndDate.substring(5,7));
		var eDd = Number(emptyEndDate.substring(8,10));
		var eHs = Number(emptyEndDate.substring(11,13));
		var eMi = Number(emptyEndDate.substring(14,16));
		var eSs = Number(emptyEndDate.substring(17,19));
		var eDate = new Date(eYy, eMm-1, eDd, eHs,eMi,eSs);
		
		var sCnt = (currentDate - sDate);
		var eCnt = (eDate - currentDate);

		if(sCnt > 0 && eCnt > 0){
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}	
}
//�μ� ������ �ش� ���������� �߰� �Ѵ�.
function addAttrOrg(result, node){
	if(result!==null){
		node.orgCode = result.orgCode; 	    //���� �ڵ�
		node.isDeleted = result.isDeleted; //��������
		node.isInspection = result.isInspection; //����� ����
		isInspection =  result.isInspection;//����� ����
		node.isODCD = result.isODCD;	//������ ����
		node.isProxyDocHandleDept = result.isProxyDocHandleDept //�븮 ���� ó���� ����
		node.orgAbbrName = result.orgAbbrName; //���� ����
		node.isProcess = result.isProcess; //ó���� ����
	}
}
//------------------------Ʈ�� �̺�Ʈ ��--------------------------------------------------------
function makeArtNames(){
	g_Arts[ 0] = new Object();
	g_Arts[ 1] = new Object();
	g_Arts[ 2] = new Object();
	g_Arts[ 3] = new Object();
	g_Arts[ 4] = new Object();
	g_Arts[ 5] = new Object();
	g_Arts[ 6] = new Object();
	g_Arts[ 7] = new Object();
	g_Arts[ 8] = new Object();
	g_Arts[ 9] = new Object();
	g_Arts[10] = new Object();
	g_Arts[11] = new Object();
	g_Arts[12] = new Object();
	g_Arts[13] = new Object();
	g_Arts[14] = new Object();
	g_Arts[15] = new Object();  
	g_Arts[16] = new Object();  
	g_Arts[17] = new Object();  
	g_Arts[18] = new Object(); 
	
	g_Arts[19] = new Object();
	g_Arts[20] = new Object();
	g_Arts[21] = new Object();

	g_Arts[22] = new Object(); // jth8172 2012 �Ű��� TF
	g_Arts[23] = new Object(); // jth8172 2012 �Ű��� TF
	g_Arts[24] = new Object(); // jth8172 2012 �Ű��� TF
	g_Arts[25] = new Object(); // jth8172 2012 �Ű��� TF
	g_Arts[26] = new Object(); // jth8172 2012 �Ű��� TF
	g_Arts[27] = new Object(); // jth8172 2012 �Ű��� TF
	g_Arts[28] = new Object(); // jth8172 2012 �Ű��� TF
	g_Arts[29] = new Object(); // �������			20150427_csh
	g_Arts[30] = new Object(); // �������(���)	20150427_csh
	g_Arts[31] = new Object(); // �������(����)	20150427_csh
	g_Arts[32] = new Object(); // �������(����)	20150427_csh
	g_Arts[33] = new Object(); // ��ȹ����			20150427_csh
	g_Arts[34] = new Object(); // ��ȹ����(���)	20150427_csh
	g_Arts[35] = new Object(); // ��ȹ����(����)	20150427_csh
	g_Arts[36] = new Object(); // ��ȹ����(����)	20150427_csh

	g_Arts[ 0].Val = ART010;
	g_Arts[ 1].Val = ART020;
	g_Arts[ 2].Val = ART030;
	g_Arts[ 3].Val = ART031;
	g_Arts[ 4].Val = ART032;
	g_Arts[ 5].Val = ART033;
	g_Arts[ 6].Val = ART034;
	g_Arts[ 7].Val = ART035;
	g_Arts[ 8].Val = ART040;
	g_Arts[ 9].Val = ART041;
	g_Arts[10].Val = ART042;
	g_Arts[11].Val = ART043;
	g_Arts[12].Val = ART044;
	g_Arts[13].Val = ART050;
	g_Arts[14].Val = ART051;
	g_Arts[15].Val = ART052;
	g_Arts[16].Val = ART053;
	g_Arts[17].Val = ART054;
	g_Arts[18].Val = ART021; //ART020 ���߰��� �ڵ�

	g_Arts[19].Val = ART045;
	g_Arts[20].Val = ART046;
	g_Arts[21].Val = ART047;

	g_Arts[22].Val = ART130; //���μ������� // jth8172 2012 �Ű��� TF
	g_Arts[23].Val = ART131; //���κ������� // jth8172 2012 �Ű��� TF
	g_Arts[24].Val = ART132; //�μ�����// jth8172 2012 �Ű��� TF
	g_Arts[25].Val = ART133; //����(���)// jth8172 2012 �Ű��� TF
	g_Arts[26].Val = ART134; //����(����)// jth8172 2012 �Ű��� TF
	g_Arts[27].Val = ART135; //����(����)// jth8172 2012 �Ű��� TF
	g_Arts[28].Val = ART055;  //�뺸 // jth8172 2012 �Ű��� TF
	g_Arts[29].Val = ART081; // �������		20150427_csh
	g_Arts[30].Val = ART082; // �������(���)	20150427_csh
	g_Arts[31].Val = ART083; // �������(����)	20150427_csh
	g_Arts[32].Val = ART084; // �������(����)	20150427_csh
	g_Arts[33].Val = ART091; // ��ȹ����		20150427_csh
	g_Arts[34].Val = ART092; // ��ȹ����(���)	20150427_csh
	g_Arts[35].Val = ART093; // ��ȹ����(����)	20150427_csh
	g_Arts[36].Val = ART094; // ��ȹ����(����)	20150427_csh

	g_Arts[ 0].Nm = '${options["OPT001"].optionValue}';
	g_Arts[ 1].Nm = '${options["OPT002"].optionValue}';
	g_Arts[ 2].Nm = '${options["OPT003"].optionValue}';
	g_Arts[ 3].Nm = '${options["OPT004"].optionValue}';
	g_Arts[ 4].Nm = '${options["OPT005"].optionValue}';
	g_Arts[ 5].Nm = '${options["OPT006"].optionValue}';
	g_Arts[ 6].Nm = '${options["OPT007"].optionValue}';
	g_Arts[ 7].Nm = '${options["OPT008"].optionValue}';
	g_Arts[ 8].Nm = '${options["OPT009"].optionValue}';
	g_Arts[ 9].Nm = '${options["OPT010"].optionValue}';
	g_Arts[10].Nm = '${options["OPT011"].optionValue}';
	g_Arts[11].Nm = '${options["OPT012"].optionValue}';
	g_Arts[12].Nm = '${options["OPT013"].optionValue}';
	g_Arts[13].Nm = '${options["OPT014"].optionValue}';
	g_Arts[14].Nm = '${options["OPT015"].optionValue}';
	g_Arts[15].Nm = '${options["OPT016"].optionValue}';
	g_Arts[16].Nm = '${options["OPT017"].optionValue}';
	g_Arts[17].Nm = '${options["OPT018"].optionValue}';
	g_Arts[18].Nm = '${options["OPT002"].optionValue}';//ART020 ���߰��� �ڵ�

	g_Arts[19].Nm = '${options["OPT021"].optionValue}';
	g_Arts[20].Nm = '${options["OPT022"].optionValue}';
	g_Arts[21].Nm = '${options["OPT023"].optionValue}';

	g_Arts[22].Nm = '${options["OPT053"].optionValue}'; //���μ�������// jth8172 2012 �Ű��� TF
	g_Arts[23].Nm = '${options["OPT054"].optionValue}'; //���κ�������// jth8172 2012 �Ű��� TF
	g_Arts[24].Nm = '${options["OPT055"].optionValue}'; //�μ�����// jth8172 2012 �Ű��� TF
	g_Arts[25].Nm = '${options["OPT056"].optionValue}'; //����(���)// jth8172 2012 �Ű��� TF
	g_Arts[26].Nm = '${options["OPT057"].optionValue}'; //����(����)// jth8172 2012 �Ű��� TF
	g_Arts[27].Nm = '${options["OPT058"].optionValue}'; //����(����)// jth8172 2012 �Ű��� TF
	g_Arts[28].Nm = '${options["OPT059"].optionValue}'; //�뺸// jth8172 2012 �Ű��� TF
	g_Arts[29].Nm = '${options["OPT030"].optionValue}'; // �������		20150427_csh
	g_Arts[30].Nm = '${options["OPT031"].optionValue}'; // �������(���)	20150427_csh
	g_Arts[31].Nm = '${options["OPT032"].optionValue}'; // �������(����)	20150427_csh
	g_Arts[32].Nm = '${options["OPT033"].optionValue}'; // �������(����)	20150427_csh
	g_Arts[33].Nm = '${options["OPT040"].optionValue}'; // �������		20150427_csh
	g_Arts[34].Nm = '${options["OPT041"].optionValue}'; // �������(���)	20150427_csh
	g_Arts[35].Nm = '${options["OPT042"].optionValue}'; // �������(����)	20150427_csh
	g_Arts[36].Nm = '${options["OPT043"].optionValue}'; // �������(����)	20150427_csh
	 
	
}

function setAppLine(){

	if ($("#appLine", "#approvalitem").val() != null) {
		g_lines = $("#appLine", "#approvalitem").val();

		var arrLines = getApproverList(g_lines);

		if(arrLines.length == 0){
			return false;
		}

		for(var i = 0; i < arrLines.length; i++){
			var lines = $('#tbApprovalLines tbody'); //������
			var appLines = lines.children();
			
			var id = "", lineNum = "" , lineOrder = "" , lineSubOrder = "" ,  approverId = "" , approverName  = ""
				, approverPos = "" ,  approverDeptId = "" , approverDeptName = "" , representativeId = "" ,  representativeName  = ""
				, representativePos = "" , representativeDeptId = "" ,  representativeDeptName = "" , askType  = "", procType = "" ,  absentReason  = ""
				, editBodyYn = "" , editAttachYn = "" ,  editLineYn = "" , mobileYn  = "", procOpinion  = "",  signFileName = "", readDate = "", processDate = ""
				, lineHisId = "", fileHisId = "", bodyHisId = "", approverRole = "", positionName  = "", deptName = "", userName = "", opt_nm = "" ;
				
			line = arrLines[i];
			
			id = line.approverId;

			lineNum 				= line.lineNum;
			lineOrder 				= line.lineOrder;
			lineSubOrder 			= line.lineSubOrder;
			approverId 				= line.approverId;
			approverName 			= line.approverName;
			approverPos 			= line.approverPos;
			approverDeptId 			= line.approverDeptId;
			approverDeptName 		= line.approverDeptName;
			representativeId 		= line.representativeId;
			representativeName 		= line.representativeName;
			representativePos 		= line.representativePos;
			representativeDeptId 	= line.representativeDeptId;
			representativeDeptName 	= line.representativeDeptName;
			askType 				= line.askType;
			procType 				= line.procType;
			absentReason 			= line.absentReason;
			editBodyYn	 			= line.editBodyYn;
			editAttachYn 			= line.editAttachYn;
			editLineYn 				= line.editLineYn;
			mobileYn 				= line.mobileYn;
			procOpinion 			= line.procOpinion;
			signFileName 			= line.signFileName;
			readDate 				= line.readDate;
			processDate 			= line.processDate;
			lineHisId 				= line.lineHisId;
			fileHisId	 			= line.fileHisId;
			bodyHisId 				= line.bodyHisId;
			approverRole			= line.approverRole;
			positionName 			= line.approverPos;
			deptName 				= line.approverDeptName;
			userName 				= line.approverName;

			if(userName === ""){
				positionName = "";
			}
		
     		//�μ�����,�μ�����,�μ�����
			//���� ���� ��ΰ� �μ�����, �μ�����, �μ����� ���� ���� Ȯ��
			if((askType === ART032 ||askType === ART033 ||askType === ART034 ||askType === ART035 || 
				askType === ART132 ||askType === ART133 ||askType === ART134 ||askType === ART135 || // jth8172 2012 �Ű��� TF
				askType === ART041 ||askType === ART042 ||askType === ART043 ||askType === ART044 ||
				askType === ART081 ||askType === ART082 ||askType === ART083 ||askType === ART084 ||
				askType === ART091 ||askType === ART092 ||askType === ART093 ||askType === ART094) 
				&& '${userProfile.deptId}' === approverDeptId){

				CURRART = askType;
 
				//	�������, ��ȹ����	20150424_csh
				if(askType === ART032 ||askType === ART132 || askType === ART041 || askType === ART081 || askType === ART091){ // jth8172 2012 �Ű��� TF
					approverId 			= '${userProfile.userUid}';
					approverName 		= '${userProfile.userName}';
					approverPos 		= '${userProfile.positionName}';
					approverDeptId 		= '${userProfile.deptId}';
					approverDeptName 	= '${userProfile.deptName}';

					id 					= approverId;
					deptName 			= approverDeptName;
					positionName 		= approverPos;
					userName 			= approverName;

					if(askType === ART032){
						askType = ART033;
					}
					if(askType === ART132){ // jth8172 2012 �Ű��� TF
						askType = ART133;	// jth8172 2012 �Ű��� TF
					}
					if(askType === ART041){
						askType = ART042;
					}
					if(askType === ART081){	//	�������	20150424_csh
						askType = ART082;
					}
					if(askType === ART091){	//	��ȹ����	20150424_csh
						askType = ART092;
					}
				}

				if(askType === ART032 ||askType === ART033 ||askType === ART034 ||askType === ART035){
					$('#divART032').show();
					$('#divART132').hide();	// jth8172 2012 �Ű��� TF
					$('#divART041').hide();
					$('#divART081').hide();	//	�������
					$('#divART091').hide();	//	��ȹ����
					//askType = ART033;
				} else if(askType === ART132 ||askType === ART133 ||askType === ART134 ||askType === ART135){ // jth8172 2012 �Ű��� TF
						$('#divART032').hide(); // jth8172 2012 �Ű��� TF
						$('#divART132').show(); // jth8172 2012 �Ű��� TF
						$('#divART141').hide(); // jth8172 2012 �Ű��� TF
						//askType = ART133;
				} else if(askType === ART082 ||askType === ART083 ||askType === ART084){
					$('#divART032').hide();
					$('#divART132').hide();	// jth8172 2012 �Ű��� TF
					$('#divART041').hide();
					$('#divART081').show();	//	�������
					$('#divART091').hide();	//	��ȹ����
				} else if(askType === ART092 ||askType === ART093 ||askType === ART094){
					$('#divART032').hide();
					$('#divART132').hide();	// jth8172 2012 �Ű��� TF
					$('#divART041').hide();
					$('#divART081').hide();	//	�������
					$('#divART091').show();	//	��ȹ����
				}else{
					$('#divART032').hide();
					$('#divART132').hide();	// jth8172 2012 �Ű��� TF
					$('#divART041').show();	
					$('#divART081').hide();	//	�������
					$('#divART091').hide();	//	��ȹ����
				}
			}
			
			for(var idx = 0; idx < g_Arts.length; idx++){
				if(g_Arts[idx].Val === askType){
					opt_nm = g_Arts[idx].Nm;
					break;
				}
			}
			
			var row = approveMakeRow("tbApprovalLines", id, lineNum , lineOrder , lineSubOrder ,  approverId , approverName 
					, approverPos ,  approverDeptId , approverDeptName , representativeId ,  representativeName 
					, representativePos , representativeDeptId ,  representativeDeptName , askType , procType ,  absentReason 
					, editBodyYn , editAttachYn ,  editLineYn , mobileYn , procOpinion ,  signFileName, readDate, processDate
					, lineHisId , fileHisId , bodyHisId ,approverRole , positionName , deptName, userName, opt_nm);

// 			lines.append(row); //������� �ϴ��߰�	20150312_dykim	
		}

		return true;
	}
	
	return false;
}

//�α��� ����� ���� �ʱ�ȭ ��Ų��.
function lineInit(bUse){
	var usrRow = $('#${userProfile.userUid}');
	selectOneElement(usrRow);
}
//��� ���ý� �߻��ϴ� �̺�Ʈ 
// parameter obj : ���õ� tr �� jquery ��ü�̴�.
function onListClick(obj){ 
		selectOneElement(obj);
		gSaveLineObject.restore();
		// 2015.07.28_lsk_�� Ŭ���� ������ �߰�
		addApprovalLine();
}

//�ϳ��� ���� �������� �����ϴ� �̺�Ʈ
//parameter obj : ���õ� tr �� jquery ��ü�̴�.
function selectOneElement(obj){
		
	if((!isCtrl && !isShift )|| (isCtrl && isShift)){
		$('document').empty();
		gSaveObject.restore();
		gSaveObject.add(obj, sColor);
	}else{
		if(isCtrl){
			$('document').empty();
			gSaveObject.add(obj, sColor);
		}

		if(isShift){
			
			var num1=0, num2=0, sNum=0, eNum = 0, ktype=0;

			num1 = new Number(gSaveObject.first().attr("rowOrd"));			
			num2 = new Number(obj.attr("rowOrd"));
			
			if(num1 >= num2){
				ktype = 1;
				num1 = new Number(gSaveObject.last().attr("rowOrd"));
			}

			sNum = (num2 > num1? num1 : num2);
			eNum = (num2 > num1? num2 : num1);
			
			var objs = $('#tbUsers tbody').children();
			gSaveObject.restore();
			for(var i = sNum; i<= eNum; i++){
				var nextObj = $("#"+objs[i].id);
				gSaveObject.add(nextObj, sColor);
			}	

			gSaveObject.setType(ktype);	

			if(ktype == 0){	
				gSaveObject.last().children()[0].focus();
			}else{
				gSaveObject.first().children()[0].focus();
			}
		}
	}
	<%if("2".equals(linetype)){%>
	$('input:radio').attr('disabled', false);
	var lines2 = $('#tbApprovalLines tbody tr[procType='+APT003+']');
	//������ư �ʱ�ȭ
	if(lines2.length > 0){
		if(lines2.attr('lineNum') === "2"){
			$('#lineNum1').attr('disabled', true);
			$('#lineNum2').attr('checked', true);
		}
	}
	<%}%>
}
//���콺 �̵��� �߻��ϴ� �̺�Ʈ
function onListMouseMove(){
	try {
		document.selection.empty();
	} catch (error) {
	}
}

//Ű�� �������� �� �߻��ϴ� �̺�Ʈ
function onListKeyDown(obj){
	var objTmp = null;
	var checkEvn = false;
		
	switch(event.keyCode){
		case 37:
		case 38:
		{
			objTmp = obj.prev();
			checkEvn = true;
			break;
		}
		case 39:
		case 40:
		{
			objTmp = obj.next();
			checkEvn = true;
			break;
		}
		case 13:
		{
			break;
		}		
	}
	if(checkEvn){
		if(objTmp.children().length > 0){
			selectOneElement(objTmp);
			objTmp.children()[0].focus();
		}
	}
}

//��� ����Ŭ���� �߻��ϴ� �̺�Ʈ
function onListDblClick(obj){
	document.selection.empty();
	// 2015.07.28_lsk_���� Ŭ���� ������ �߰� �ּ�ó��
	//	click_Appline('');
}

//-----------------------------------------
function addApprovalLine(){
	popMsg = "";	//20141121 ������ȭ�鿡�� ����� �ߺ����� �޽��� ȣ������� �ʱ�ȭ��. kj.yang
	var lines = $('#tbApprovalLines tbody'); //������
	var appLines = lines.children();
	var appColl = gSaveObject.collection();

	//	�������, ��ȹ���� �߰�	20150423_csh
	var radio = $('input[name="options1"][id!="'+ART010+'"][id!="'+ART053+'"][id!="'+ART032+'"][id!="'+ART132+'"][id!="'+ART041+'"][id!="'+ART081+'"][id!="'+ART091+'"]:checked'); // jth8172 2012 �Ű��� TF
	
	//	�������, ��ȹ���� �߰�	20150423_csh
	if(radio.length == 0 ){
		radio = $('input#'+ART033+', input#'+ART042+', input#'+ART082+', input#'+ART092);  // jth8172 2012 �Ű��� TF
		if(radio.length > 0){
			radio.attr('checked',true);
		}
	}

	if(appColl.length === 0){
		if(!bPop)
			alert('<spring:message code="approval.msg.applines.cknochoice" />');
		else
			popMsg = '<spring:message code="approval.msg.applines.cknochoice" />';
		return;
	}

	for(var i = 0; i < appColl.length; i++) {
		var appItem = appColl[i];
		var ck_app = true;
		for(var j = 0; j < appLines.length; j++){
			var appLine = appLines[j];
			//	�߰��� ����� üũ
			if(appLine.id == ("tbApprovalLines_" + appItem.attr("id"))){
				ck_app = false;
				if(!bPop)
					alert('<spring:message code="approval.msg.applines.ckchoice" />');
				else
					popMsg = '<spring:message code="approval.msg.applines.ckchoice" />';
				break;
			}
		}

		//	���� üũ
		if(ck_app){
			if(appItem.attr('empty') === "Y"){
				var emptyMsg = '<spring:message code="approval.msg.applines.empty" />'; 
				emptyMsg = emptyMsg.replace(/%S/g,appItem.attr("userName"));
				if(!confirm(emptyMsg)){
					ck_app = false;
				}
			}
		}
		
		if(ck_app){
			var opt_code = "";
			var opt_nm = "";
			var radio = null;

			var id = "", lineNum = "1" , lineOrder = "" , lineSubOrder = "" ,  approverId = "" , approverName = "" ;
			var approverPos = "" ,  approverDeptId = "" , approverDeptName = "" , representativeId = "" ,  representativeName = "" ;
			var representativePos = "" , representativeDeptId = "" ,  representativeDeptName = "" , askType = "" , procType = "" ,  absentReason = "" ;
			var editBodyYn = "" , editAttachYn = "" ,  editLineYn = "" , mobileYn = "" , procOpinion = "" ,  signFileName = "", readDate ="" , processDate = "";
			var lineHisId = "", fileHisId = "", bodyHisId = "", approverRole = "",positionName = "" , deptName = "", userName = "";

			//���߰����
			var rdLineNum = $('input:radio[name="lineNum"]:checked');
			var lineNm = "";
			if(rdLineNum.length != 0){				
				lineNum = rdLineNum.val();
				lineNm = rdLineNum.attr('codeNm');
			}
			var curAskType = "";
			
			if(CURRART === ART032){
				curAskType = ART033;
			}
			if(CURRART === ART132){ // jth8172 2012 �Ű��� TF
				curAskType = ART133;	// jth8172 2012 �Ű��� TF
			}
			if(CURRART === ART041 || CURRART === ART042){
				curAskType = ART042;
			}
			if(CURRART === ART081 || CURRART === ART082){	//	�������	20150424_csh
				curAskType = ART082;
			}
			if(CURRART === ART091 || CURRART === ART092){	//	��ȹ����	20150424_csh
				curAskType = ART092;
			}
			
			radio = $("input:radio:checked");
			if(radio.length == 0){
				//	�������, ��ȹ���� �߰�		20150423_csh
				radio = $('input#'+ART033+', input#'+ART133+', input#'+ART042+', input#'+ART082+', input#'+ART092);
				if(radio.length > 0){
					radio.attr('checked',true);
					opt_code = radio.val();

					if(lineNm === ""){
						opt_nm = radio.attr('codeNm');
					}else{
						opt_nm = radio.attr('codeNm') + "/" + lineNm;
					}
				}					
			}else{
				radio = $('input#'+curAskType);
				radio.attr('checked',true);
				opt_code = radio.val();
				
				if(lineNm === ""){
					opt_nm = radio.attr('codeNm');
				}else{
					opt_nm = radio.attr('codeNm') + "/" + lineNm;
				}
			}
			
   		   id = appItem.attr("id");
           approverId = appItem.attr("id");
           approverName = appItem.attr("userName");
           approverPos = appItem.attr("positionName"); //appItem.attr("positionCode");
           approverDeptId = appItem.attr("deptID");
           approverDeptName = appItem.attr("deptName");
           positionName = appItem.attr("positionName");
           deptName = appItem.attr("deptName");
           userName = appItem.attr("userName");
           askType = opt_code;

           if(askType === ART031){   //	������ �ٸ��� ����, ������ ��������	 20150313_jskim
        	   if(position!== null && position!==positionName ){
        		   askType=ART030;
        	   }
           
        	   position=positionName;
            }
            
           if(appItem.attr("roleCodes").indexOf(role_ceo) !== -1){
 				if(approverRole != ""){
  					approverRole += "^";
  				}
        		approverRole += role_ceo; //CEO
           }

           if(appItem.attr("roleCodes").indexOf(role_officer) !== -1){
  				if(approverRole != ""){
  					approverRole += "^";
  				}
  				approverRole += role_officer; //�ӿ�
          	}

           if(appItem.attr("roleCodes").indexOf(role_dailyinpecttarget) !== -1){
  				if(approverRole != ""){
  					approverRole += "^";
  				}
  				approverRole += role_dailyinpecttarget; //�ϻ󰨻�����
          	}

           if(appItem.attr("roleCodes").indexOf(role_dailyinpectreader) !== -1){
  				if(approverRole != ""){
  					approverRole += "^";
  				}
  				approverRole += role_dailyinpectreader; //�ϻ󰨻����� ��ȸ��
          	}
           if(appItem.attr("roleCodes").indexOf(OPT051) !== -1){ // jth8172 2012 �Ű��� TF
 				if(approverRole != ""){
 					approverRole += "^";
 				}
 				approverRole += OPT051; // ������ // jth8172 2012 �Ű��� TF
          	}
           if(appItem.attr("roleCodes").indexOf(OPT052) !== -1){ // jth8172 2012 �Ű��� TF
				if(approverRole != ""){
					approverRole += "^";
				}
				approverRole += OPT052; // ������ // jth8172 2012 �Ű��� TF
         	}
           
			if (approverRole!="") {  //���� �� ���� üũ  // jth8172 2012 �Ű��� TF
			 	var strAddTxt ="";
			
				if( approverRole.indexOf("OPT051")> -1  ) {  //����
					strAddTxt = "${options["OPT051"].optionValue}" ;
					if( approverRole.indexOf("OPT052")> -1 ) strAddTxt += "/";
				}			 
				if( approverRole.indexOf("OPT052")> -1  ) {  //����
					strAddTxt += "${options["OPT052"].optionValue}" ;
				}  
				if(strAddTxt !="") {
					strAddTxt = "[" + strAddTxt +"]";
					opt_nm = opt_nm + strAddTxt;
				}	
			}	           

           if(! checkART031(askType)){ ////�������� ó���κ�
        	   if(!bPop)
               	alert('<spring:message code="approval.msg.applines.after.noparallel" />');
        	   else
        	   	popMsg = '<spring:message code="approval.msg.applines.after.noparallel" />';
               return;
           }
           
           if(! checkART131(askType)){ ////�������� ó���κ� // jth8172 2012 �Ű��� TF
        	   if(!bPop)
               	alert('<spring:message code="approval.msg.applines.after.noagreeparallel" />');
        	   else
        	   	popMsg = '<spring:message code="approval.msg.applines.after.noagreeparallel" />';
               return;
           }
			
           var row = approveMakeRow("tbApprovalLines", id, lineNum , lineOrder , lineSubOrder ,  approverId , approverName 
					, approverPos ,  approverDeptId , approverDeptName , representativeId ,  representativeName 
					, representativePos , representativeDeptId ,  representativeDeptName , askType , procType ,  absentReason 
					, editBodyYn , editAttachYn ,  editLineYn , mobileYn , procOpinion ,  signFileName, readDate, processDate
					, lineHisId , fileHisId , bodyHisId , approverRole, positionName , deptName, userName, opt_nm);

			if(appLines.length == 0){
				lines.append(row);
			}else{
				if(CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035){//�μ�����
					var test = $('#tbApprovalLines tbody tr[askType^=ART033],[ askType^=ART034], [askType^=ART035]');
					test.last().after(row);		//������� ���� ����(����->����) 20150312_dykim		
				} else if(CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135){//�μ����� // jth8172 2012 �Ű��� TF
						var test = $('#tbApprovalLines tbody tr[askType^=ART133],[ askType^=ART134], [askType^=ART135]'); // jth8172 2012 �Ű��� TF
						test.last().after(row);	//������� ���� ����(����->����) 20150312_dykim				
				} else if(CURRART === ART041 || CURRART === ART042 || CURRART === ART043 || CURRART === ART044){//�μ�����
					var test = $('#tbApprovalLines tbody tr[askType^=ART042],[ askType^=ART043], [askType^=ART044]');
					test.last().after(row);	//������� ���� ����(����->����) 20150312_dykim
				} else if(CURRART === ART081 || CURRART === ART082 || CURRART === ART083 || CURRART === ART084){//�������		20150423_csh
					var test = $('#tbApprovalLines tbody tr[askType^=ART082],[ askType^=ART083], [askType^=ART084]');
					test.last().after(row);	//������� ���� ����(����->����) 20150312_dykim
				} else if(CURRART === ART091 || CURRART === ART092 || CURRART === ART093 || CURRART === ART094){//��ȹ����		20150423_csh
					var test = $('#tbApprovalLines tbody tr[askType^=ART092],[ askType^=ART093], [askType^=ART094]');
					test.last().after(row);	//������� ���� ����(����->����) 20150312_dykim
				}else{
					<%if("2".equals(linetype)){ //���߰��� ������ ���%>						
						if(lineNum == 1){
							var lineNums2 = $('#tbApprovalLines tbody tr[lineNum=2]');
							if(lineNums2.length > 0){
								lineNums2.first().before(row); //������� ���� ����(����->����) 20150312_dykim
							}else{
								$('#'+appLines[0].id).after(row); //������� ���� ����(����->����) 20150312_dykim
							}
						}else{
							$('#'+appLines[0].id).after(row); //������� ���� ����(����->����) 20150312_dykim
						}
					<%}else{%>
					lines.append(row); //������� �ϴ��߰� 20150312_dykim
					<%}%>
				}
			}
		}
	}

	gSaveLineObject.restore();	
	bPop = false;
	
	sendOk();
}
//----------------------------------
// ���� ���� ���� �Լ�--------------------------------
function approveMakeRow(tbId, id, lineNum , lineOrder , lineSubOrder ,  approverId , approverName 
		, approverPos ,  approverDeptId , approverDeptName , representativeId ,  representativeName 
		, representativePos , representativeDeptId ,  representativeDeptName , askType , procType ,  absentReason 
		, editBodyYn , editAttachYn ,  editLineYn , mobileYn , procOpinion ,  signFileName, readDate, processDate
		, lineHisId, fileHisId, bodyHisId, approverRole , positionName , deptName, userName, opt_nm){
	var tbgcolor = "#FFFFFF";

	if(procType === ""){
		tbgcolor = "#FFFFFF"; 

		if( CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035 ||
			CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135){  //�μ����� //�μ����� // jth8172 2012 �Ű��� TF
			if( askType !== ART032 && askType !== ART033 && askType !== ART034 && askType !== ART035 &&
				askType !== ART132 && askType !== ART133 && askType !== ART134 && askType !== ART135 ){
				tbgcolor = "#EAEAEA";
			}else{
				if(approverDeptId !=='${DEPT_ID}'){
					if(representativeDeptId === '' ){
						tbgcolor = "#EAEAEA";
					}else if(representativeDeptId !=='${DEPT_ID}'){
						tbgcolor = "#EAEAEA";
					}					
				}
			}
		}
		
		if(CURRART === ART041 || CURRART === ART042 || CURRART === ART043 || CURRART === ART044){ //�μ�����
			if(askType !== ART041 && askType !== ART042 && askType !== ART043 && askType !== ART044){
				tbgcolor = "#EAEAEA";
			}else{
				if(approverDeptId !=='${DEPT_ID}'){
					if(representativeDeptId === '' ){
						tbgcolor = "#EAEAEA";
					}else if(representativeDeptId !=='${DEPT_ID}'){
						tbgcolor = "#EAEAEA";
					}					
				}
			}
		}
		
		if(CURRART === ART081 || CURRART === ART082 || CURRART === ART083 || CURRART === ART084){ //	�������	20150424_csh	
			if(askType !== ART081 && askType !== ART082 && askType !== ART083 && askType !== ART084){
				tbgcolor = "#EAEAEA";
			}else{
				if(approverDeptId !=='${DEPT_ID}'){
					if(representativeDeptId === '' ){
						tbgcolor = "#EAEAEA";
					}else if(representativeDeptId !=='${DEPT_ID}'){
						tbgcolor = "#EAEAEA";
					}					
				}
			}
		}
		
		if(CURRART === ART091 || CURRART === ART092 || CURRART === ART093 || CURRART === ART094){ //	��ȹ����	20150424_csh
			if(askType !== ART091 && askType !== ART092 && askType !== ART093 && askType !== ART094){
				tbgcolor = "#EAEAEA";
			}else{
				if(approverDeptId !=='${DEPT_ID}'){
					if(representativeDeptId === '' ){
						tbgcolor = "#EAEAEA";
					}else if(representativeDeptId !=='${DEPT_ID}'){
						tbgcolor = "#EAEAEA";
					}					
				}
			}
		}
				
	}else{
		if(DOUBLEART != "" && procType === APT003 && (DOUBLEART === ART010 || DOUBLEART === ART053)){
			tbgcolor = "#FFFFFF";
		}else{
			tbgcolor = "#EAEAEA";
		}
	}

	approverName = escapeJavaScript(approverName);
	approverDeptName = escapeJavaScript(approverDeptName);
	representativeName = escapeJavaScript(representativeName);
	representativeDeptName = escapeJavaScript(representativeDeptName);
	procOpinion = escapeJavaScript(procOpinion);
	deptName = escapeJavaScript(deptName);
	userName = escapeJavaScript(userName);	
	var row = "<tr id='"+ tbId + "_" + id + "' lineNum='" + lineNum + "' lineOrder='" + lineOrder + "' lineSubOrder='" + lineSubOrder + "' "; 
	row += "approverId='" + approverId + "' approverName='" + approverName + "' approverPos='" + approverPos + "' ";
	row += "approverDeptId='" + approverDeptId + "' approverDeptName='" +  approverDeptName + "' representativeId='" + representativeId + "' "; 
	row += "representativeName='" + representativeName + "' representativePos='" + representativePos + "' representativeDeptId='" + representativeDeptId + "' "; 
	row += "representativeDeptName='" + representativeDeptName + "' askType='" + askType + "' procType='" + procType + "' ";  
	row += "absentReason='" + absentReason + "' editBodyYn='"+ editBodyYn +"' editAttachYn='"+ editAttachYn + "' ";
	row += "editLineYn='" + editLineYn + "' mobileYn='" + mobileYn + "' procOpinion='" + procOpinion + "' ";
	row += "signFileName='" + signFileName +"' readDate='"+ readDate +"' processDate='"+processDate +"' ";
	row += "lineHisId='" + lineHisId +"' fileHisId='"+ fileHisId +"' bodyHisId='"+bodyHisId +"' approverRole='" + approverRole + "' ";
	row += " onclick='onLineClick($(\"#"+ tbId + "_" + id+"\"));' ondblclick='onLineDblClick($(\"#"+ tbId + "_" + id+"\"));' onmousemove='onLineMouseMove();' onkeydown='onLineKeyDown($(\"#"+ tbId + "_" + id +"\"));'";
	row += " style='background-color:"+tbgcolor+";' >";
	row += "<td width='123' class='ltb_center' style='border-bottom:  "+tbColor+" 1px solid; '>" + (deptName === "" ? "&nbsp;" : deptName) + "</td>";
	row += "<td width='123' class='ltb_center' style='border-bottom:  "+tbColor+" 1px solid;border-left:1pt solid  "+tbColor+";border-right:1pt solid  "+tbColor+"; '>" + (positionName === "" ? "&nbsp;" : positionName) + "</td>";
	row += "<td width='123' class='ltb_center' style='border-bottom:  "+tbColor+" 1px solid;border-right:1pt solid  "+tbColor+"; '>" + (userName === "" ? "&nbsp;" : userName)  + "</td>"
<%
	int emptysize = emptyInfoList.size();
	if ("Y".equals(adminFlag) && emptysize > 0) {
	    for (int loop = 0; loop < emptysize; loop++) {
			EmptyInfoVO emptyInfo = emptyInfoList.get(loop);
%>
	if (approverId == "<%=emptyInfo.getUserId()%>") {
		row += "<td class='ltb_center' style='border-bottom:  "+tbColor+" 1px solid;border-right:1pt solid  "+tbColor+"; '>" + (opt_nm === "" ? "&nbsp;" : opt_nm);
		if (representativeId == "") {
			row += "&nbsp;<span id='representative_" + tbId + "_" + id + "' isset='0' onClick='onSetRepresentative(\"" + tbId + "_" + id + "\", \"<%=emptyInfo.getSubstituteId()%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteName())%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteDisplayPosition())%>\", \"<%=emptyInfo.getSubstituteDeptId()%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteDeptName())%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getEmptyReason())%>\");return(false);' "
			+ " style='cursor:pointer' title='<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteDisplayPosition())%> <%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteName())%>'><b>[<spring:message code="approval.button.applines.on.representative"/>]</b></span>";
		} else {
			row += "&nbsp;<span id='representative_" + tbId + "_" + id + "' isset='1' onClick='onSetRepresentative(\"" + tbId + "_" + id + "\", \"<%=emptyInfo.getSubstituteId()%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteName())%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteDisplayPosition())%>\", \"<%=emptyInfo.getSubstituteDeptId()%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getSubstituteDeptName())%>\", \"<%=EscapeUtil.escapeJavaScript(emptyInfo.getEmptyReason())%>\");return(false);' "
			+ " style='cursor:pointer'><b>[" + representativePos + " " + representativeName + "]</b></span>";
		}
		row += "</td>";
	} else {
		row += "<td class='ltb_center' style='border-bottom:  "+tbColor+" 1px solid;border-right:1pt solid  "+tbColor+"; '>" + (opt_nm === "" ? "&nbsp;" : opt_nm) + "</td>";
	}
<%
	    }
	} else {
%>	    
	row += "<td class='ltb_center' style='border-bottom:  "+tbColor+" 1px solid;border-right:1pt solid  "+tbColor+"; '>" + (opt_nm === "" ? "&nbsp;" : opt_nm) + "</td>";
<%
	}
%>
	row += "</tr>";
	return row;
}

function escapeJavaScript(reqString) {
	return (reqString == null) ? "" : reqString.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\"/g,'&quot;').replace(/\'/g,'\\\'').replace(/#&/g,'&');
}

function onSetRepresentative(tblId, representativeId, representativeName, representativePos, representativeDeptId, representativeDeptName, absentReason) {
	var representative = $("#representative_" + tblId);
	var approver = $("#" + tblId);
	var isset = representative.attr("isset");
	if (isset == "0") {
		representative.attr("isset", "1");
		representative.html("<b>[" + representativePos + " " + representativeName + "]</b>");
		approver.attr("representativeId", representativeId);
		approver.attr("representativeName", representativeName);
		approver.attr("representativePos", representativePos);
		approver.attr("representativeDeptId", representativeDeptId);
		approver.attr("representativeDeptName", representativeDeptName);
		approver.attr("absentReason", absentReason);
	} else {
		representative.attr("isset", "0");
		representative.html("<b>[<spring:message code="approval.button.applines.on.representative"/>]</b>");
		approver.attr("representativeId", "");
		approver.attr("representativeName", "");
		approver.attr("representativePos", "");
		approver.attr("representativeDeptId", "");
		approver.attr("representativeDeptName", "");
		approver.attr("absentReason", "");
	}
}

//--------------------------���缱 ó��------------------------------------------------------//
function onLineClick(obj){
	selectOneLineElement(obj);
	click_Appline(obj);
}

function onLineDblClick(obj){
	try {
		document.selection.empty();
		onLineClick(obj);
		onDeleteLine();
	} catch (error) {
	}
}

//Option ��ư Ȱ��ȭ
function click_Appline(obj){
	var askType = "";
	var lineNum = "1";
	var appRole = "";// jth8172 2012 �Ű��� TF
	var absentReason = ""; //���� ���� 20150312_dykim
	//������� �μ�ID���� 20150327_dykim
	g_lines = getAppLine();
	var arrLines = getApproverList(g_lines);
	var deptId = arrLines[0].approverDeptId;
	
	
	if(obj !== ""){
		askType = obj.attr('askType');
		lineNum = obj.attr('lineNum');
		appRole = obj.attr('approverRole');// jth8172 2012 �Ű��� TF
		absentReason = obj.attr('absentReason'); //���� ���� 20150312_dykim
		approverDeptId = obj.attr('approverDeptId'); //�߰��� ����� �μ�ID���� 20150327_dykim
	}
	
	var rdType1 = $('input:radio[value ="'+ART010+'"], input:radio[value ="'+ART053+'"]'); //���, 1�� ����
	var rdType2 = $('input:radio[value !="'+ART010+'"][value !="'+ART053+'"]');
	var rdType3 = $('input:radio[value = "'+ART032+'"],input:radio[value = "'+ART132+'"],input:radio[value="'+ART041+'"],input:radio[value="'+ART081+'"],input:radio[value="'+ART091+'"]'); //	�������, ��ȹ���� �߰�	20150422_csh
	var rdType4 = $('input:radio[value !="'+ART032+'"][value !="'+ART132+'"][value !="'+ART041+'"][value !="'+ART081+'"][value !="'+ART091+'"]');	//	�������, ��ȹ���� �߰�	20150422_csh
	var chkType5 = null; // jth8172 2012 �Ű��� TF
	var selnode = $('input:radio[value = "'+ART031+'"]'); //�������� 20150327_dykim
	//	���� üũ�ڽ� ����	20150209_csh
	var rdType5 = $('input:checkbox[value ="ART071"]');
	
 	<c:if test='${options["OPT051"].useYn == "Y"}'>
		chkType5 = $('input:checkbox[value ="'+OPT051+'"]'); //����// jth8172 2012 �Ű��� TF
		<c:choose>
		<c:when test='${options["OPT052"].useYn == "Y"}'>
			chkType5 = $('input:checkbox[value ="'+OPT051+'"], input:checkbox[value ="'+OPT052+'"]'); //����,����// jth8172 2012 �Ű��� TF
		</c:when>
		</c:choose>					
	</c:if>
	<c:if test='${options["OPT052"].useYn == "Y"}'>
		<c:choose>
		<c:when test='${options["OPT051"].useYn == "Y"}'>
			chkType5 = $('input:checkbox[value ="'+OPT051+'"], input:checkbox[value ="'+OPT052+'"]'); //����,����// jth8172 2012 �Ű��� TF
		</c:when>
		<c:otherwise>
			chkType5 = $('input:checkbox[value ="'+OPT052+'"]'); //����
		</c:otherwise>		
		</c:choose>					
	</c:if>
 	
 	<c:if test='${options["OPT051"].useYn == "Y"}'>
	if(appRole.indexOf("OPT051")>-1) {  //����// jth8172 2012 �Ű��� TF
		$('input:checkbox[value ="'+OPT051+'"]').attr('checked',true);
	} else {
		$('input:checkbox[value ="'+OPT051+'"]').attr('checked',false);
	}	
	</c:if>
 	<c:if test='${options["OPT052"].useYn == "Y"}'>
	if(appRole.indexOf("OPT052")>-1) { //����// jth8172 2012 �Ű��� TF
		$('input:checkbox[value ="'+OPT052+'"]').attr('checked',true);
	} else {
		$('input:checkbox[value ="'+OPT052+'"]').attr('checked',false);
	}
	</c:if>
				
<%if("1".equals(linetype)){ //���ϰ����� ���%>
	
	if(askType === ART010 || askType === ART053){ //��� �� 1�� ����
			rdType1.attr('disabled', false); //enable
			rdType2.attr('disabled', true);  //disable
			rdType5.attr('disabled', true);	//	���, 1�ΰ����� ��� disable	20150209_csh
			<c:if test='${options["OPT051"].useYn == "Y"}'>			
			chkType5.attr('disabled', false); //enable
			</c:if>
			<c:if test='${options["OPT052"].useYn == "Y"}'>			
			chkType5.attr('disabled', false); //enable
			</c:if>
	}else{
		if ( askType === ART050 || askType === ART051 || askType === ART052 || askType === ART054 ) {
			rdType5.attr('disabled', true);	//	������ ��� disable	20150209_csh
		} else {
			rdType5.attr('disabled', false);
		}	
			rdType1.attr('disabled', true);
			rdType2.attr('disabled', false);
	//		chkType5.attr('disabled', true); //disable  ���ǹ׺����� ���� �ʵ带 ����ڿ��Ը� ������ ��� ���⸦ Ǭ��. ����� ���� ���밡��// jth8172 2012 �Ű��� TF
	}

	if(askType !== ''){
		$('input:radio[value="'+askType+'"]').attr('checked',true);
		
		//	�������, ��ȹ���� �߰�		20150422_csh
		if(askType === ART032 || askType === ART132 || askType === ART041 || askType === ART081 || askType === ART091){
			rdType3.attr('disabled', false);
			rdType4.attr('disabled', true);
			<c:if test='${options["OPT051"].useYn == "Y"}' >			
			chkType5.attr('disabled', true); //disable  ���ǹ׺����� // jth8172 2012 �Ű��� TF
			</c:if>
			<c:if test='${options["OPT052"].useYn == "Y"}'>			
			chkType5.attr('disabled', true); //disable  ���ǹ׺����� // jth8172 2012 �Ű��� TF
			</c:if>
		}else{
			
			rdType3.attr('disabled', true);
		}		
	}else{
		rdType3.attr('disabled', true);
	}
<%}else{%>
//���߰���üũ �κ�
	if((askType === ART010 || askType === ART053) && lineNum == "1"){ //��� �� 1�� ����
		rdType1.attr('disabled', false); //enable
		rdType2.attr('disabled', true);  //disable
		
	}else if((askType === ART010 || askType === ART053) && lineNum == "2"){
		rdType1.attr('disabled', false); //enable
		rdType2.attr('disabled', false);
		rdType3.attr('disabled', false); //enable
		rdType4.attr('disabled', false);
		
		$('#lineNum1').attr('disabled', false);
		$('#lineNum2').attr('disabled', false);
	}else{				
		rdType1.attr('disabled', true);
		rdType2.attr('disabled', false);

		
		if(obj !== ""){
			if(obj.attr('approverId')==="" && obj.attr('lineNum')==="2"){ //�μ��߰��̸� 
				$('#lineNum1').attr('disabled', true);
				$('#lineNum2').attr('disabled', false);
				$('input:radio[value ="'+ART050+'"]').attr('disabled', true);
				$('input:radio[value ="'+ART051+'"]').attr('disabled', true); // jth8172 2012 �Ű��� TF
				$('input:radio[value ="'+ART052+'"]').attr('disabled', true); // jth8172 2012 �Ű��� TF
			}else{
				
				if(obj.attr('lineNum')==="2"){
					$('input:radio').attr('disabled', false);
				}
				
				$('#lineNum1').attr('disabled', false);
				$('#lineNum2').attr('disabled', false);
			}
		}
	}	
	if(askType !== ''){
		if(askType === ART021){
			 askType = ART020;
			 $('input:radio[value="'+ART030+'"][value="'+ART130+'"]').attr('disabled', true); // jth8172 2012 �Ű��� TF
		}
		
		$('input:radio[value="'+askType+'"]').attr('checked',true);
		$('input:radio[value="'+lineNum+'"]').attr('checked',true);
	}

	if(DOUBLEART !== ""){//���߰���ó�� ��������
		//$('#lineNum1').attr('disabled', true);
		if(askType === ART010 || askType === ART053){
			rdType2.attr('disabled', true);
		}
	}

	if(bDouble){
		$('#lineNum1').attr('disabled', true);
	}
<%}%>
	
	//������ ������ ���ý� ���� ǥ��	20150312_dykim
		rdType5.attr('checked', false);
	if ( absentReason == "undefined" || absentReason == "" ) {
		$('#userStatus').attr("style", "display: none");		
	} else {
		rdType5.attr('checked', true);
		$('#userStatus').attr("style", "display: inline");
		$("#userStatus").val(absentReason).attr("selected", "selected");
	}
	//����ڿ� �߰��� ����� �μ��� ������ �������� ��ư ��Ȱ��ȭ �� üũ���� 20150327_dykim
	if(deptId == approverDeptId){
		selnode.attr('disabled', true);
		$('input:radio[value="'+ART031+'"]').attr('checked',false);
		return;
	}else{
		selnode.attr('disabled', false);
	}
}

//Ű�� �������� �� �߻��ϴ� �̺�Ʈ
function onLineKeyDown(obj){
	var objTmp = null;
	var checkEvn = false;
		
	switch(event.keyCode){
		case 37:
		case 38:
		{
			objTmp = obj.prev();
			checkEvn = true;
			break;
		}
		case 39:
		case 40:
		{
			objTmp = obj.next();
			checkEvn = true;
			break;
		}		
	}
	if(checkEvn){
		if(objTmp.children().length > 0){
			selectOneLineElement(objTmp);
			objTmp.children()[0].focus();
		}
	}
}

//������� ���ý� ó���ϴ� �Լ�
function selectOneLineElement(obj){
//gSaveLineObject
	if((!isCtrl && !isShift )|| (isCtrl && isShift)){
		$('document').empty();
		gSaveLineObject.restore();
		gSaveLineObject.add(obj, sColor);
	}else{
		if(isCtrl){
			$('document').empty();
			gSaveLineObject.add(obj, sColor);
		}

		if(isShift){
			
			var num1=0, num2=0, sNum=0, eNum = 0, ktype=0;
			var objs = $('#tbApprovalLines tbody').children();
			
			for(var i = 0; i < objs.length; i++){
				if(objs[i].id === obj.attr("id")){
					num2 = i;
					break;
				}
			}

			for(var i = 0; i < objs.length; i++){
				if(objs[i].id === gSaveLineObject.first().attr("id")){
					num1 = i;
					break;
				}
			}
			
			if(num1 >= num2){
				ktype = 1;
				for(var i = 0; i < objs.length; i++){
					if(objs[i].id === gSaveLineObject.last().attr("id")){
						num1 = i;
						break;
					}
				}
			}

			sNum = (num2 > num1) ? num1 : num2;
			eNum = (num2 > num1) ? num2 : num1;
			
			gSaveLineObject.restore();
			
			for(var i = sNum; i<= eNum; i++){
				var nextObj = $("#"+objs[i].id);
				gSaveLineObject.add(nextObj, sColor);
			}
		}
	}
}

//���콺 �̵��� �߻��ϴ� �̺�Ʈ
function onLineMouseMove(){
	try {
		document.selection.empty();
	} catch (error) {
	}
}

//�߰�(+) ��ư Ŭ����
function onAddLine(){	
	addApprovalLine();
}

//������ư(-)  Ŭ����
function onDeleteLine(){
	var lines = gSaveLineObject.collection();
	var appTr = $('#tbApprovalLines tbody tr[approverId="${userProfile.userUid}"]');
	var repTr = $('#tbApprovalLines tbody tr[representativeId="${userProfile.userUid}"]');

	for(var i = 0; i < lines.length; i++){
		if(lines[i].attr("askType") === ART010 && lines[i].attr("lineNum") === "1"){
			alert('<spring:message code="approval.msg.applines.cannotsubmiter" />');
			return;
		}else if(lines[i].attr("askType") === ART053 && lines[i].attr("lineNum") === "1"){
			alert('<spring:message code="approval.msg.applines.cannotonesubmiter" />');
			return;
		}else{
			
			if(lines[i].attr("procType") !== ""){
				alert('<spring:message code="approval.msg.applines.after.nodelline" />');
				return;
			}

			if(! checkART031(lines[i].attr("askType"))){
				alert('<spring:message code="approval.msg.applines.no.pub" />');
				return;
			}

			if(! checkART131(lines[i].attr("askType"))){  // jth8172 2012 �Ű��� TF
				alert('<spring:message code="approval.msg.applines.no.pub" />');
				return;
			}

			if(CURRART !== ""){//�μ� ����, �μ�����, �μ� �����϶� üũ�ϴ� �κ�
				var tAskType = lines[i].attr("askType");
				if( CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035 ||
					CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135 ){  // jth8172 2012 �Ű��� TF
					if( tAskType !== ART032 && tAskType !== ART033 && tAskType !== ART034 && tAskType !== ART035 &&
						tAskType !== ART132 && tAskType !== ART133 && tAskType !== ART134 && tAskType !== ART135 ){
						alert('<spring:message code="approval.msg.applines.after.nodelline" />');
						return;
					}else{
						if(lines[i].attr('approverDeptId') !=='${DEPT_ID}'){
							if(lines[i].attr('representativeDeptId') === '' ){
								alert('<spring:message code="approval.msg.applines.after.nodelline" />');
								return;
							}else if(lines[i].attr('representativeDeptId') !=='${DEPT_ID}'){
								alert('<spring:message code="approval.msg.applines.after.nodelline" />');
								return;
							}					
						}
					}
				}else{
					if(tAskType !== ART041 && tAskType !== ART042 && tAskType !== ART043 && tAskType !== ART044 ){
						alert('<spring:message code="approval.msg.applines.after.nodelline" />');
						return;
					}else{
						if(lines[i].attr('approverDeptId') !=='${DEPT_ID}'){
							if(lines[i].attr('representativeDeptId') === '' ){
								alert('<spring:message code="approval.msg.applines.after.nodelline" />');
								return;
							}else if(lines[i].attr('representativeDeptId') !=='${DEPT_ID}'){
								alert('<spring:message code="approval.msg.applines.after.nodelline" />');
								return;
							}					
						}
					}
				}
			}
			
			lines[i].remove();
		}
	}
	
}

///�������� üũ  -----------------------------------------------------------
function checkART031(askType){
	var appTr = $('#tbApprovalLines tbody tr[approverId="${userProfile.userUid}"]');
	var repTr = $('#tbApprovalLines tbody tr[representativeId="${userProfile.userUid}"]');
	var tmpTr = null;

	if(appTr.length === 1 && repTr.length === 0){
		tmpTr = appTr;
	}else if(appTr.length === 1 && repTr.length === 1){
		tmpTr = repTr;
	}else{
		return true;
	}

	if(tmpTr.attr("askType") === ART031 && askType === tmpTr.attr("askType")){
		return false;
	}else{
		return true;
	}
}


///�������� üũ // jth8172 2012 �Ű��� TF-----------------------------------------------------------
function checkART131(askType){
	var appTr = $('#tbApprovalLines tbody tr[approverId="${userProfile.userUid}"]');
	var repTr = $('#tbApprovalLines tbody tr[representativeId="${userProfile.userUid}"]');
	var tmpTr = null;

	if(appTr.length === 1 && repTr.length === 0){
		tmpTr = appTr;
	}else if(appTr.length === 1 && repTr.length === 1){
		tmpTr = repTr;
	}else{
		return true;
	}

	if(tmpTr.attr("askType") === ART131 && askType === tmpTr.attr("askType")){
		return false;
	}else{
		return true;
	}
}

//���οø��� ��ư
function onMoveUp(){
	if(gSaveLineObject.collection().length === 1){
		var obj = gSaveLineObject.first();
		var prev = obj.prev();

		if(obj.attr('lineNum')=== "1" && (obj.attr('askType') === ART010 ||obj.attr('askType') === ART053) ){
			return;
		}

		if(obj.attr('procType') !== ""){
			return;
		}

		if(obj.attr('procType') === APT001 || prev.attr('procType') !== ""){
			return;
		} //���� �����׸��� ��Ƚ� ���� �̵� ���� 20150312_dykim
		if( CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035 ||
			CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135 ){ //�μ�����,�μ�����// jth8172 2012 �Ű��� TF
			/*if(ART035 === obj.attr('askType')){//�μ������ ���̻� ���� �ö󰥼� ����.
				return;
			}
			*/
			if( ART033 !== prev.attr('askType') && ART034 !== prev.attr('askType') && ART035 !== prev.attr('askType') &&
				ART133 !== prev.attr('askType') && ART134 !== prev.attr('askType') && ART135 !== prev.attr('askType') ){ //����,���ǰ��縦 �Ѿ�� ����.
				return;
			}

			
			
		}
		if(CURRART === ART041 || CURRART === ART042 || CURRART === ART043 || CURRART === ART044){//�μ�����
/*
			if(ART044 === obj.attr('askType')){//�μ������ ���̻� ���� �ö󰥼� ����.
				return;
			}

			if(ART044 === prev.attr('askType')){ //�������縦 �Ѿ�� ����.
				return;
			}
*/
			if(ART042 !== prev.attr('askType') && ART043 !== prev.attr('askType') && ART044 !== prev.attr('askType')){ //�������縦 �Ѿ�� ����.
				return;
			}
		}

		if(prev.attr('askType') !== ART010 && prev.attr('askType') !== ART053 ){
			prev.before($('#'+obj.attr('id')));
		}else{
			if(prev.attr('lineNum') !== "1"){
				prev.before($('#'+obj.attr('id')));
			}
		}
	}
}

//�Ʒ��� ������ ��ư-----------
function onMoveDown(){
	if(gSaveLineObject.collection().length === 1){
		var obj = gSaveLineObject.first();
		var prev = obj.next();

		if(obj.attr('lineNum')=== "1" && (obj.attr('askType') === ART010 ||obj.attr('askType') === ART053) ){
			return;
		}

		if(obj.attr('procType') !== "" || prev.attr('procType') !== "" ){
			return;
		}

		if(obj.attr('procType') === APT001 || prev.attr('procType') !== ""){
			return;
		}

		if( CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035 ||
			CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135 ){ //�μ�����,�μ�����// jth8172 2012 �Ű��� TF
/*			if(ART035 === obj.attr('askType')){//��������� ���̻� ������ �� ����.
				return;
			}
*/
			if( ART033 !== prev.attr('askType') && ART034 !== prev.attr('askType') && ART035 !== prev.attr('askType') &&
				ART133 !== prev.attr('askType') && ART134 !== prev.attr('askType') && ART135 !== prev.attr('askType') ){ //����,�������縦 �Ѿ�� ����.// jth8172 2012 �Ű��� TF, obj->prev ���� 20150312_dykim
				return;
			}
		}
		
		if(CURRART === ART041 || CURRART === ART042 || CURRART === ART043 || CURRART === ART044){//�μ�����
			/*if(ART044 === obj.attr('askType')){//�������� ���̻� ������ �� ����.
				return;
			}*/

			if(ART042 !== obj.attr('askType') && ART043 !== obj.attr('askType') && ART044 !== obj.attr('askType')){ //�������縦 �Ѿ�� ����.
				return;
			}
		}
		
		
		if(prev.attr('askType') !== ART010 && prev.attr('askType') !== ART053){	
			prev.after($('#'+obj.attr('id')));
		}else{
			if(prev.attr('lineNum') !== "1"){
				prev.after($('#'+obj.attr('id')));
			}
		}
		
	}
}

//Ȯ�ι�ư Ŭ��
function sendOk(){
	var appline = "";
		
	appline = makeApprovalLine();
	
	$("#appLine", "#approvalitem").val(appline);
	
	$.post("<%=webUri%>/app/approval/insertListReceiveLine.do", $("#appDocForm").serialize(), function(data){
    	
        if (data.result =='OK') {
			
        	alert("<spring:message code='approval.result.msg.approverfixok'/>");
        	if ( opener.exitAppDoc == null ) {
        		opener.location.reload();	
        	} else {
        		opener.exitAppDoc();
        	}
        	window.close();
        }
        else{
        	alert("<spring:message code='env.form.msg.fail'/>");
        }
    },'json').error(function(data) {
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='env.form.msg.fail'/>");
		}
	});
}

function closePopup(){
	window.close();
}

//������� �����ϱ�
function makeApprovalLine(){
	var lines = $('#tbApprovalLines tbody');
	setLineOrder(lines);
	var strRt = getApprovalLines(lines);
	return strRt;
}

//���� ���� �� ���� ���� ���� �����Ѵ�.
function setLineOrder(lines){
	var subline = lines.children();
	var lineOrder = 1;
	var lineSubOrder = 1;
	var max = subline.length -1;
	for( var i = 0; i < subline.length ; i++){ //���� ���� �������� ���� 20150312_dykim
		var askType = subline[i].getAttribute('askType');
		if ((askType === ART031 || askType === ART131)  && i < (max+1)){	// jth8172 2012 �Ű��� TF		
			if(subline[i-1].getAttribute('askType') == ART031 ||subline[i-1].getAttribute('askType') == ART131){
				subline[i].setAttribute('lineOrder',subline[i-1].getAttribute('lineOrder'));
				var subNo = new Number(subline[i-1].getAttribute('lineSubOrder')) + 1
				subline[i].setAttribute('lineSubOrder',subNo);
			}else{
				subline[i].setAttribute('lineOrder' , lineOrder);
				subline[i].setAttribute('lineSubOrder', "1");
				lineOrder++;
			}
		}else{
			subline[i].setAttribute('lineOrder' , lineOrder);
			subline[i].setAttribute('lineSubOrder', "1");
			lineOrder++;
		}
	}
}

//������� �ۼ��ϱ�
function getApprovalLines(lines){
	var subline = lines.children();
	
	var max = subline.length -1;
	var strAppline = "";
	
	for( var i = 0; i<subline.length ; i++){ //������� �������� ���� 20150312_dykim
		var line = subline[i];
		strAppline += line.getAttribute('lineOrder') 			+ String.fromCharCode(2) + line.getAttribute('lineSubOrder') 			+ String.fromCharCode(2); 
		strAppline += line.getAttribute('approverId') 			+ String.fromCharCode(2) + line.getAttribute('approverName') 			+ String.fromCharCode(2); 
		strAppline += line.getAttribute('approverPos') 			+ String.fromCharCode(2) + line.getAttribute('approverDeptId') 			+ String.fromCharCode(2); 
		
		//������ ���� ���� ����� �� �˾� �ߴ� ���� ����  20150325_jskim
		if(i == subline.length -1 ){   //������ �������� �����ڰ� ������ ���� �� ���� ǥ��  20150323_jskim
			if(line.getAttribute('approverPos')!== "����" ){  //���� �̿� ����� ���� ǥ��	20150311_jskim
				if((line.getAttribute('askType') === ART050 || line.getAttribute('askType') === ART020)){
					line.setAttribute('askType' ,ART051); 
			}
		}else{
				if(line.getAttribute('askType') === ART020){
					line.setAttribute('askType' ,ART050); 
			}
		 }
		}

		strAppline += line.getAttribute('approverDeptName') 	+ String.fromCharCode(2) + line.getAttribute('representativeId') 		+ String.fromCharCode(2); 
		strAppline += line.getAttribute('representativeName') 	+ String.fromCharCode(2) + line.getAttribute('representativePos') 		+ String.fromCharCode(2); 
		strAppline += line.getAttribute('representativeDeptId') + String.fromCharCode(2) + line.getAttribute('representativeDeptName') 	+ String.fromCharCode(2); 
		strAppline += line.getAttribute('askType')	 			+ String.fromCharCode(2) + line.getAttribute('procType') 				+ String.fromCharCode(2);
		strAppline += line.getAttribute('absentReason') 		+ String.fromCharCode(2) + line.getAttribute('editBodyYn') 				+ String.fromCharCode(2);
		strAppline += line.getAttribute('editAttachYn') 		+ String.fromCharCode(2) + line.getAttribute('editLineYn') 				+ String.fromCharCode(2);
		strAppline += line.getAttribute('mobileYn') 			+ String.fromCharCode(2) + line.getAttribute('procOpinion') 			+ String.fromCharCode(2);
		strAppline += line.getAttribute('signFileName')			+ String.fromCharCode(2) + line.getAttribute('readDate')				+ String.fromCharCode(2); 
		strAppline += line.getAttribute('processDate') 			+ String.fromCharCode(2) + line.getAttribute('lineHisId')  				+ String.fromCharCode(2);
		strAppline += line.getAttribute('fileHisId') 			+ String.fromCharCode(2) + line.getAttribute('bodyHisId')  				+ String.fromCharCode(2);
		strAppline += line.getAttribute('approverRole') 		+ String.fromCharCode(2);
		strAppline += line.getAttribute('lineNum')  			+ String.fromCharCode(4);
	}
	return strAppline;	
}

// ������ο� ��������,���� �� ������ �����ڰ� �ִ��� ���� üũ (2011.10.20 �Ű���)
function procCheck(obj) { 
	var procYn = "N";
	var size = obj.length;	
	for (i=0; i<size; i++) {
		var line = obj[i];
		if ((line.askType == ART031 || line.askType == ART131) && line.procType == APT001) {// jth8172 2012 �Ű��� TF
			procYn = "Y";
		}
	}
	return procYn;
}

//üũ�ڽ� Ŭ������ �� �߻��ϴ� �̺�Ʈ(����/������)  // jth8172 2012 �Ű��� TF
function chkClick(obj){
 	
	if(gSaveLineObject.collection().length === 1) {

		var strTxt = gSaveLineObject.first().children().last().text();
		if(strTxt.indexOf("[")>0) strTxt = strTxt.substring(0,strTxt.indexOf("["));
		gSaveLineObject.first().attr('approverRole', "");
	 	var strAddOpt ="";
	 	var strAddTxt ="";
	 	<c:if test='${options["OPT051"].useYn == "Y"}'>
		if( $("#OPT051").attr("checked")  ) { //���� // jth8172 2012 �Ű��� TF
			strAddOpt =  OPT051;
			strAddTxt = "${options["OPT051"].optionValue}";
			<c:choose>
			<c:when test='${options["OPT052"].useYn == "Y"}'>
				if( $("#OPT052").attr("checked") ) {
					strAddTxt += "/";
					strAddOpt += "^";
				}	
			</c:when>
			</c:choose>					
		}
		</c:if>			 
	 	<c:if test='${options["OPT052"].useYn == "Y"}'>
		if( $("#OPT052").attr("checked") ) { // ���� // jth8172 2012 �Ű��� TF
			strAddOpt += OPT052;
			strAddTxt += "${options["OPT052"].optionValue}";
		}  
		</c:if>

		//		����/����� approverRole, ����� absentReason�� ������ ���� 20150312_dykim
		if( $("#ART071").attr("checked") ) {
			strAddOpt += $("#userStatus option:selected").val();	//	selectbox���� ������ �ڵ尪
			strAddTxt += $("#userStatus option:selected").val();
			gSaveLineObject.first().attr('absentReason',strAddOpt);//	selectbox���� ������ ��
		}else{
			gSaveLineObject.first().attr('approverRole',strAddOpt);
		}
		
		if(strAddTxt !="") strAddTxt = "[" + strAddTxt +"]";
		gSaveLineObject.first().children().last().text(strTxt + strAddTxt );
			
	}
 	
}

// �ɼ� Ŭ������ �� �߻��ϴ� �̺�Ʈ
function optClick(obj){
	var lines = $('#tbApprovalLines tbody tr');
	var rdType1 = $('input:radio[value ="'+ART010+'"], input:radio[value ="'+ART053+'"]'); //���, 1�� ����
	
	if(gSaveLineObject.collection().length === 1){
		
		var rdOption  = $('input:radio[name!="lineNum"]:checked');
		var rdLineNum = $('input:radio[name="lineNum"]:checked');
			
		var askType = rdOption.val();

		//�μ�����, �μ� ���翡 �ʿ��� ����
		var askTypeTmp	= gSaveLineObject.first().attr('askType');
		//���� ó���� ���� ����
		var procType 	= gSaveLineObject.first().attr('procType');		
		
		if(rdLineNum.length === 0){
			askName = rdOption.attr('codeNm');
			
			//		�������ý� �����ο� ǥ��		20150312_dykim 
			if ( $('input:checkbox[value ="ART071"]:checked').length == 1 ) {
				askName = lines.absentReason;
			}
		}else{
			gSaveLineObject.first().attr('lineNum', rdLineNum.val());
			askName = rdOption.attr('codeNm') + "/" + rdLineNum.attr('codeNm');
		}

		if(obj.name === 'lineNum' && obj.value === "2"){
			rdType1.attr('disabled', false);
		}

		if (DOUBLEART !== ""){
			//if(DOUBLEART !== ART010 && DOUBLEART !== ART053 && procType !== APT003){
			//	return;
			//}
		}else{
			//���� ó���ڳ� ����ڷ� �����Ǹ� ������ �� ����
			//if(procType !== "") ����
			if(procType === APT001) {
				return;
			}

			// ���������� ��� ������ �� �Ѹ��̶� ������ �� ��� ������ �� ����. (2011.10.20 �Ű���)
			if (askTypeTmp === ART031) { 
				if (procCheck(lines)=="Y") {
					return;
				}
			}
			if (askTypeTmp === ART131) {  //�������ǵ� üũ // jth8172 2012 �Ű��� TF
				if (procCheck(lines)=="Y") {
					return;
				}
			}
		}
		
		//	���� ���ý� ������ ���� ��Ÿ����		20150209_csh
		var empty = $('input:checkbox[value ="ART071"]:checked').length;
		var art050Check = $('input#ART050:checked, input#ART051:checked, input#ART052:checked, input#ART054:checked').length;

		if ( empty == 1) {
			$('#userStatus').attr("style", "display: inline");
			chkClick('ART071');
		} else {
			$('#userStatus').attr("style", "display: none");
			gSaveLineObject.first().attr('absentReason', "");	//	���� ��üũ�� �������� ����	20150312_dykim
		}
		
		if ( art050Check == 1 ){
			$('input:checkbox[value ="ART071"]').attr('disabled', true);
		} else {
			$('input:checkbox[value ="ART071"]').attr('disabled', false);
		}

<%if ( "1".equals(linetype)){%>
		//�μ�����,����
		if( CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035 ||
			CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135 ){ // jth8172 2012 �Ű��� TF
			
			if( askTypeTmp !==  ART032 && askTypeTmp !==  ART033 && askTypeTmp !==  ART034 && askTypeTmp !==  ART035 &&
				askTypeTmp !==  ART132 && askTypeTmp !==  ART133 && askTypeTmp !==  ART134 && askTypeTmp !==  ART135 ){ // jth8172 2012 �Ű��� TF
				return;
			}

			//����(���) �̸鼭 ���� ��� ���� ���� �ٲܼ� ����.
			if((askTypeTmp === ART033 || askTypeTmp === ART133) && procType === APT003){ // jth8172 2012 �Ű��� TF
				return;
			}
		}

		//�μ�����
		if(CURRART === ART041 || CURRART === ART042 || CURRART === ART043 || CURRART === ART044){
			if(askTypeTmp !==  ART041 && askTypeTmp !==  ART042 && askTypeTmp !==  ART043 && askTypeTmp !==  ART044){
				return;
			}
			
			//����(���) �̸鼭 ���� ��� ���� ���� �ٲܼ� ����.
			if(askTypeTmp === ART042 && procType === APT003){
				return;
			}
		}
<%}%>
<%if ( "2".equals(linetype)){//2011.05.27 ���߰��� �μ� �� ��� ART020�� �����ϸ� ART021�� �־���� �Ѵ�. %>
//���߰��� 
		if(obj.value === ART020 && gSaveLineObject.first().attr('askType') === ART021){
			askType = ART021;
		}
<%}%>
		gSaveLineObject.first().attr('askType', askType);
		gSaveLineObject.first().children().last().text(askName);
	}
}

//������� �� üũ�ϱ�
function checkLineRole(){
	var lines = $('#tbApprovalLines tbody tr');
	var lineCnt = lines.length;
	var oldItem = null, appItem = null, appItem2 = null; //�����û ���翩�� Ȯ��
	var role_ceoYn = false, role_auditorYn=false; //CEO ���� ����, ���翩��
	var audittypeYn = false; //����Ÿ��üũ
	var appRole1 = false; //����Ÿ��üũ // jth8172 2012 �Ű��� TF
	var appRole2 = false; //����Ÿ��üũ // jth8172 2012 �Ű��� TF
	 
	var rdLineNum = $('input:radio[name="lineNum"]');

	var doubleLines = $('#tbApprovalLines tbody tr[lineNum="2"]'); //���߰��� ���ο� �ִ� ��ҵ�
	var Art033Idx = (lineCnt-1);//�μ�������� - ����(���)
	var ART033_DeptId = "";
	var Art035Idx = 0;//�μ���������-����(����)
	var ART035_DeptId

	var Art133Idx = (lineCnt-1);//�μ����Ǳ�� - ����(���) // jth8172 2012 �Ű��� TF
	var ART133_DeptId = ""; // jth8172 2012 �Ű��� TF
	var Art135Idx = 0; //�μ����ǰ���-����(����) // jth8172 2012 �Ű��� TF
	var ART135_DeptId // jth8172 2012 �Ű��� TF

	var Art042Idx = (lineCnt-1);
	var ART042_DeptId = "";
	var Art044Idx = 0;
	var ART044_DeptId = "";
	var ART050Idx = 0; //������ġ
	var ART050X1 = ""; //1���� �����ڵ�

	var ARTSING = ""; //�������ĸ� üũ�ϱ� ����

	//���簡 2���ΰ��� �ʵ�
	var art50s = $('#tbApprovalLines tbody tr[askType="ART050"][lineNum="1"],[askType="ART051"][lineNum="1"],[askType="ART052"][lineNum="1"],[askType="ART053"][lineNum="1"]');

	if(art50s.length > 1){
		alert('<spring:message code="approval.msg.applines.after.noorder" />');
		return;
	}

	//�ְ��μ�
	var art502s = $('#tbApprovalLines tbody tr[askType="ART050"][lineNum="2"],[askType="ART051"][lineNum="2"],[askType="ART052"][lineNum="2"],[askType="ART053"][lineNum="2"]');
	if(art502s.length > 1){
		alert('<spring:message code="approval.form.recdept" /> <spring:message code="approval.msg.applines.after.noorder" />');
		return;
	}
	
	for(var i = 0; i < lineCnt; i++){ //��üũ �������� ���� 20150312_dykim
		tmpItem = lines[i];
		
		if(oldItem !== null){		
			
			if(oldItem.getAttribute('askType') == ART050 || oldItem.getAttribute('askType') == ART051 
					|| oldItem.getAttribute('askType') == ART052 || oldItem.getAttribute('askType') == ART053){
				ARTSING = oldItem.getAttribute('askType');
			}
			
			if(ARTSING === ART050 //���� ���Ŀ��� ����, �μ�����, �Ŀ�,�뺸��  �ü� �ִ�.
				<c:if test='${options["OPT336"].useYn == "Y"}'>
					&& tmpItem.getAttribute('askType') !== ART030 //����
				</c:if>
				<c:if test='${options["OPT337"].useYn == "Y"}'>
					&&  tmpItem.getAttribute('askType') !== ART032 //�μ�����
					&&  tmpItem.getAttribute('askType') !== ART033 //�������
					&&  tmpItem.getAttribute('askType') !== ART034 //��������
					&&  tmpItem.getAttribute('askType') !== ART035 //��������
				</c:if>
				<c:if test='${options["OPT360"].useYn == "Y"}'>	
					&&  tmpItem.getAttribute('askType') !== ART031 //�������� 
				</c:if>
					&&  tmpItem.getAttribute('askType') !== ART054
					&&  tmpItem.getAttribute('askType') !== ART055  //�뺸 // jth8172 2012 �Ű��� TF
					&&  oldItem.getAttribute('lineNum') === tmpItem.getAttribute('lineNum') && tmpItem.getAttribute('lineNum') === "1"){

				var msgArt = '<spring:message code="approval.msg.applines.after.art050a" />';
				
				<c:if test='${options["OPT336"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art030a" />';//����
				</c:if>
				<c:if test='${options["OPT337"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art032a" />'; //�μ�����
				</c:if>
				<c:if test='${options["OPT360"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art031a" />'; //��������
				</c:if>
				msgArt += '<spring:message code="approval.msg.applines.after.art054a" />';
 				
				alert(msgArt);
				
				return false;
			}

			if(ARTSING === ART051 //���� ���Ŀ��� ����, �μ�����, �Ŀ�,�뺸��  �ü� �ִ�.
					<c:if test='${options["OPT336"].useYn == "Y"}'>
						&& tmpItem.getAttribute('askType') !== ART030 //����
					</c:if>
					<c:if test='${options["OPT337"].useYn == "Y"}'>
						&&  tmpItem.getAttribute('askType') !== ART032 //�μ�����
						&&  tmpItem.getAttribute('askType') !== ART033 //�������
						&&  tmpItem.getAttribute('askType') !== ART034 //��������
						&&  tmpItem.getAttribute('askType') !== ART035 //��������
					</c:if>
					<c:if test='${options["OPT360"].useYn == "Y"}'>	
						&&  tmpItem.getAttribute('askType') !== ART031 //�������� 
					</c:if>
					&&  tmpItem.getAttribute('askType') !== ART054
					&&  tmpItem.getAttribute('askType') !== ART055  //�뺸 // jth8172 2012 �Ű��� TF
					&&  oldItem.getAttribute('lineNum') === tmpItem.getAttribute('lineNum') && tmpItem.getAttribute('lineNum') === "1"){

				var msgArt = '<spring:message code="approval.msg.applines.after.art051a" />';
				
				<c:if test='${options["OPT336"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art030a" />';//����
				</c:if>
				<c:if test='${options["OPT337"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art032a" />'; //�μ�����
				</c:if>
				<c:if test='${options["OPT360"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art031a" />'; //��������
				</c:if>
				msgArt += '<spring:message code="approval.msg.applines.after.art054a" />';

				alert(msgArt);
				
				return false;
			}

			if(ARTSING === ART052 //��� ���Ŀ��� ����, �μ�����, �Ŀ�, �뺸��  �ü� �ִ�.
					<c:if test='${options["OPT336"].useYn == "Y"}'>
						&& tmpItem.getAttribute('askType') !== ART030 //����
					</c:if>
					<c:if test='${options["OPT337"].useYn == "Y"}'>
						&&  tmpItem.getAttribute('askType') !== ART032 //�μ�����
						&&  tmpItem.getAttribute('askType') !== ART033 //�������
						&&  tmpItem.getAttribute('askType') !== ART034 //��������
						&&  tmpItem.getAttribute('askType') !== ART035 //��������
					</c:if>
					<c:if test='${options["OPT360"].useYn == "Y"}'>	
						&&  tmpItem.getAttribute('askType') !== ART031 //�������� 
					</c:if>
					&&  tmpItem.getAttribute('askType') !== ART055  //�뺸 // jth8172 2012 �Ű��� TF
					&&  tmpItem.getAttribute('askType') !== ART054
					&& oldItem.getAttribute('lineNum') === tmpItem.getAttribute('lineNum') && tmpItem.getAttribute('lineNum') === "1"){

				var msgArt = '<spring:message code="approval.msg.applines.after.art052a" />';
				
				<c:if test='${options["OPT336"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art030a" />';//����
				</c:if>
				<c:if test='${options["OPT337"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art032a" />'; //�μ�����
				</c:if>
				<c:if test='${options["OPT360"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art031a" />'; //��������
				</c:if>
				msgArt += '<spring:message code="approval.msg.applines.after.art054a" />';

				alert(msgArt);
				
				return false;
			}
			
			if(ARTSING === ART053 
					<c:if test='${options["OPT336"].useYn == "Y"}'>
						&& tmpItem.getAttribute('askType') !== ART030 //����
					</c:if>
					<c:if test='${options["OPT337"].useYn == "Y"}'>
						&&  tmpItem.getAttribute('askType') !== ART032 //�μ�����
						&&  tmpItem.getAttribute('askType') !== ART033 //�������
						&&  tmpItem.getAttribute('askType') !== ART034 //��������
						&&  tmpItem.getAttribute('askType') !== ART035 //��������
					</c:if>
					<c:if test='${options["OPT360"].useYn == "Y"}'>	
						&&  tmpItem.getAttribute('askType') !== ART031 //�������� 
					</c:if>
					&&  tmpItem.getAttribute('askType') !== ART055  //�뺸 // jth8172 2012 �Ű��� TF
					&& tmpItem.getAttribute('askType') !== ART054
					&& oldItem.getAttribute('lineNum') === tmpItem.getAttribute('lineNum') && tmpItem.getAttribute('lineNum') === "1"){//1������ ���� �Ŀ��� �ü� ����

				var msgArt = '<spring:message code="approval.msg.applines.after.art053a" />';
				
				<c:if test='${options["OPT336"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art030a" />';//����
				</c:if>
				<c:if test='${options["OPT337"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art032a" />'; //�μ�����
				</c:if>
				<c:if test='${options["OPT360"].useYn == "Y"}'>
				msgArt += '<spring:message code="approval.msg.applines.after.art031a" />'; //��������
				</c:if>
				msgArt += '<spring:message code="approval.msg.applines.after.art054a" />';

				alert(msgArt);
				
				return false;
			}

			if((oldItem.getAttribute('askType') === ART054  || oldItem.getAttribute('askType') === ART055 )  // jth8172 2012 �Ű��� TF		
					&& (tmpItem.getAttribute('askType') !== ART054 && tmpItem.getAttribute('askType') !== ART055) // jth8172 2012 �Ű��� TF		
					&& oldItem.getAttribute('lineNum') === tmpItem.getAttribute('lineNum') && tmpItem.getAttribute('lineNum') === "1"){ 
				//�Ŀ��ڿ��� �Ŀ�,�뺸�� �� �� ����
				alert('<spring:message code="approval.msg.applines.after.art054" />');
				return false;
			}

			// jth8172 2012 �Ű��� TF		
			if( oldItem.getAttribute('askType') === ART055 &&  tmpItem.getAttribute('askType') !== ART055
					&& oldItem.getAttribute('lineNum') === tmpItem.getAttribute('lineNum') && tmpItem.getAttribute('lineNum') === "1"){ 
				//�뺸�ڿ��� �뺸�� �� �� ����
				alert('<spring:message code="approval.msg.applines.after.art055" />');
				return false;
			}

			//�ְ��μ��� ��û�μ� �߰��� ���� �� �� ����.
			if(oldItem.getAttribute('lineNum') !="" && oldItem.getAttribute('lineNum') !== tmpItem.getAttribute('lineNum') && tmpItem.getAttribute('lineNum') === "1"){
				alert('<spring:message code="approval.msg.applines.after.noorder" />');
				return false;
			}

			//�μ�����,���� ���� ���� üũ
			if( CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035 || 
				CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135){	// jth8172 2012 �Ű��� TF				
				var ART033s = $('#tbApprovalLines tbody tr[askType="'+ART033+'"]');
				var ART133s = $('#tbApprovalLines tbody tr[askType="'+ART133+'"]'); // jth8172 2012 �Ű��� TF
								
				if(tmpItem.getAttribute('askType') === ART033){
					Art033Idx = i;
					ART033_DeptId = tmpItem.getAttribute('approverDeptId');
				}

				if(tmpItem.getAttribute('askType') === ART133){ // jth8172 2012 �Ű��� TF
					Art133Idx = i;
					ART133_DeptId = tmpItem.getAttribute('approverDeptId');
				}

				if(tmpItem.getAttribute('askType') === ART035){
					Art035Idx = i;
					ART035_DeptId = tmpItem.getAttribute('approverDeptId');
				}
				if(tmpItem.getAttribute('askType') === ART135){ // jth8172 2012 �Ű��� TF
					Art135Idx = i;
					ART135_DeptId = tmpItem.getAttribute('approverDeptId');
				}

				
				if(tmpItem.getAttribute('askType') === ART033 || tmpItem.getAttribute('askType') === ART034 || tmpItem.getAttribute('askType') === ART035){
					if(Art033Idx > ART050Idx){ //���� ������ ����
						if(Art035Idx < ART050Idx){
							alert('<spring:message code="approval.msg.applines.after.noorder" />');
							return;
						}
					}
				}

				if(tmpItem.getAttribute('askType') === ART133 || tmpItem.getAttribute('askType') === ART134 || tmpItem.getAttribute('askType') === ART135){ // jth8172 2012 �Ű��� TF
					if(Art133Idx > ART050Idx){ //���� ������ ����
						if(Art135Idx < ART050Idx){
							alert('<spring:message code="approval.msg.applines.after.noorder" />');
							return;
						}
					}
				}
				
				if(tmpItem.getAttribute('askType') === ART034 && tmpItem.getAttribute('approverDeptId') === ART033_DeptId){

					if(i <= Art033Idx){ //���� ���濡 ���� ���Ǻ��� 20150312_dykim
						alert('<spring:message code="approval.msg.applines.after.noorder" />');
						return;
					}

					if(i >= Art035Idx && tmpItem.getAttribute('approverDeptId') === ART035_DeptId){ //���� ���濡 ���� ���Ǻ��� 20150312_dykim
						alert('<spring:message code="approval.msg.applines.after.noorder" />');
						return;
					}					
				}									
				if(tmpItem.getAttribute('askType') === ART134 && tmpItem.getAttribute('approverDeptId') === ART133_DeptId){ // jth8172 2012 �Ű��� TF

					if(i <= Art133Idx){ //���� ���濡 ���� ���Ǻ��� 20150312_dykim
						alert('<spring:message code="approval.msg.applines.after.noorder" />');
						return;
					}

					if(i >= Art135Idx && tmpItem.getAttribute('approverDeptId') === ART135_DeptId){ //���� ���濡 ���� ���Ǻ��� 20150312_dykim
						alert('<spring:message code="approval.msg.applines.after.noorder" />');
						return;
					}					
				}									

				
			}

			//�������� ���� ���� üũ
			if(CURRART === ART041 || CURRART === ART042 || CURRART === ART043 || CURRART === ART044){					
				//var ART042s = $('#tbApprovalLines tbody tr[askType="'+ART042+'"]');
				
				if(tmpItem.getAttribute('askType') === ART042){
					Art042Idx = i;
					ART042_DeptId = tmpItem.getAttribute('approverDeptId');
				}

				if(tmpItem.getAttribute('askType') === ART044 && tmpItem.getAttribute('approverDeptId') === ART042_DeptId){
					Art044Idx = i;
					ART044_DeptId = tmpItem.getAttribute('approverDeptId');
				}

				
				
				if(tmpItem.getAttribute('askType') === ART043 && tmpItem.getAttribute('approverDeptId') === ART042_DeptId){

					if(i <= Art042Idx){ //���� ���濡 ���� ���Ǻ��� 20150312_dykim
						alert('<spring:message code="approval.msg.applines.after.noorder" />');
						return;
					}

					if(i >= Art044Idx && tmpItem.getAttribute('approverDeptId') === ART044_DeptId){ //���� ���濡 ���� ���Ǻ��� 20150312_dykim
						alert('<spring:message code="approval.msg.applines.after.noorder" />');
						return;
					}					
				}						
			}
<%if ( "2".equals(linetype)){ //���߰��� %>
			if(oldItem.getAttribute('lineNum') === "1" &&  tmpItem.getAttribute('lineNum') === "2"){

				//�ְ��μ� �μ��� �߱�Ǿ����� ����� �߰��� �� �� ����.
				if(ART021 === tmpItem.getAttribute('askType') ){//�μ�����
					if($('#tbApprovalLines tbody tr[lineNum="2"][approverId != ""]').length > 0){
						alert('<spring:message code="approval.msg.applines.afterdeptnouser" />');
						return;
					}
				}
										
				//�ְ��μ� ����ڰ� ���ڼ��� �߰��� ��� �μ��� �߰��� �� ����.
				if("" !== tmpItem.getAttribute('approverDeptId') && "" !== tmpItem.getAttribute('approverId')){
					if($('#tbApprovalLines tbody tr[lineNum="2"][approverDeptId=""]').length > 0){
						alert('<spring:message code="approval.msg.applines.afterusernodept" />');
						return;
					}
				}

				if(!dobleLineCheck()){
					return;
				}
			}
<%}%>


			if(oldItem.getAttribute('askType') === ART050 //��������  ���缱 ������ �� ����
					&& oldItem.getAttribute('lineNum') === "2"){
				alert('<spring:message code="approval.form.recdept" /> <spring:message code="approval.msg.applines.after2.art050" />');
				return false;
			}
				
			if(oldItem.getAttribute('askType') === ART051 //��������  ���缱 ������ �� ����
					&& oldItem.getAttribute('lineNum') === "2"){
				alert('<spring:message code="approval.form.recdept" /> <spring:message code="approval.msg.applines.after2.art051" />');
				return false;
			}

			if(oldItem.getAttribute('askType') === ART052 //������� ���缱 ������ �� ����
					&& oldItem.getAttribute('lineNum') === "2"){
				alert('<spring:message code="approval.form.recdept" /> <spring:message code="approval.msg.applines.after2.art052" />');
				return false;
			}
			
			if(oldItem.getAttribute('askType') === ART053 //1������ ����  ���缱 ������ �� ����
					&& oldItem.getAttribute('lineNum') === "2"){
				alert('<spring:message code="approval.form.recdept" /> <spring:message code="approval.msg.applines.after2.art053" />');
				return false;
			}
		}else{
			if(lineCnt < 2 && tmpItem.getAttribute('askType') !== ART053){ 
				// ���� ������ �ϳ��� �ִ� ���� 1���������� üũ
				alert('<spring:message code="approval.msg.applines.after.notexit" />');
				return false;
			}
		}
		
		//���� ���翩�� Ȯ��
		if((tmpItem.getAttribute('askType') === ART050 ||tmpItem.getAttribute('askType') === ART051 
				||tmpItem.getAttribute('askType') === ART052 ||tmpItem.getAttribute('askType') === ART053)
				&& tmpItem.getAttribute('lineNum') === "1"){
			ART050Idx = i;
			ART050X1 = tmpItem.getAttribute('askType');
			appItem = tmpItem;

			/*//CEO ���� ���� --> CEO(role_ceo), �ϻ󰨻����� ����(role_dailyinpecttarget) �� �����ο� ���ԵǾ� ������ ���� ����
			if(tmpItem.getAttribute("approverRole").indexOf(role_ceo) !== -1){
				role_ceoYn = true; //CEO ���� ����
			}
			*/
		}

		//CEO ���� ���� --> CEO(role_ceo), �ϻ󰨻����� ����(role_dailyinpecttarget) �� �����ο� ���ԵǾ� ������ ���� ����
		if(tmpItem.getAttribute("approverRole").indexOf(role_ceo) !== -1 || tmpItem.getAttribute("approverRole").indexOf(role_dailyinpecttarget) !== -1){
			role_ceoYn = true; //CEO ���� ���� ,�ϻ󰨻����� ����
		}
		

		//���߰��� ���� ���翩�� Ȯ��
		if((tmpItem.approverId === "" ||tmpItem.getAttribute('askType') === ART050 ||tmpItem.getAttribute('askType') === ART051 
				||tmpItem.getAttribute('askType') === ART052 ||tmpItem.getAttribute('askType') === ART053)
				&& tmpItem.getAttribute('lineNum') === "2"){
			appItem2 = tmpItem;
		}

		if(tmpItem.getAttribute('askType') === ART040 || tmpItem.getAttribute('askType') === ART041 || tmpItem.getAttribute('askType') === ART042
				|| tmpItem.getAttribute('askType') === ART043 || tmpItem.getAttribute('askType') === ART044
				|| tmpItem.getAttribute('askType') === ART045 || tmpItem.getAttribute('askType') === ART046 || tmpItem.getAttribute('askType') === ART047){ //���翩�� üũ
			audittypeYn = true;
		}

		if(tmpItem.getAttribute('approverRole').indexOf(OPT051)>-1 ) { //�������� // jth8172 2012 �Ű��� TF
			if(appRole1) {
				alert('<spring:message code="approval.msg.applines.after.noorder" />');
				return false;
			}	
			appRole1 = true;
		}	
		if(tmpItem.getAttribute('approverRole').indexOf(OPT052) >-1 ) { // �������� // jth8172 2012 �Ű��� TF
			if(appRole2) {
				alert('<spring:message code="approval.msg.applines.wrongrole" />');
				return false;
			}	
			appRole2 = true;
		}	

		
		oldItem = tmpItem;		
	}  //for
		
	/* if(appItem === null) ������ �������� �����ڰ� ������ ���� �� ���� ǥ��  20150323_jskim
	{
		alert('<spring:message code="approval.msg.applines.after.notexit" />');
		return false;
	} */

<%if ( "1".equals(linetype)){%>
//���߰��簡 �ƴѰ��
<c:if test='${options["OPT320"].useYn == "Y"}'>

//�����κ�
/*
	if(role_ceoYn && !role_auditorYn){//CEO ����� �����ڰ� �ʼ��� ���ԵǾ� �־�� �Ѵ�.
		if(!confirm('<spring:message code="approval.msg.applines.after.norole_auditor" />')){
			//alert('<spring:message code="approval.msg.applines.after.norole_auditor" />');//�����ڰ� ���缱�� �������� �ʾҽ��ϴ�.
			return false;
		}
	}
*/
	var iRoleCodes = "<%=ROLE_CODES%>";
	if((iRoleCodes.indexOf(role_dailyinpectreader) === -1 && iRoleCodes.indexOf(role_dailyinpecttarget) === -1 && iRoleCodes.indexOf(role_ceo) === -1  )
			&&role_ceoYn && !audittypeYn){
		if(!confirm('<spring:message code="approval.msg.applines.after.norole_auditor" />')){ //�����ڰ� ���缱�� �������� �ʾҽ��ϴ�.
			return false;
		}
	}
	
</c:if>

<c:if test='${audittype == "N"}'>
	if(audittypeYn){//audittype �� N �ΰ�� �������Ÿ���� �߰��� �� ����
		alert('<spring:message code="approval.msg.audittype.n" />');
		return false;
	}
</c:if>
<c:if test='${audittype == "Y"}'>
	if(!audittypeYn){//audittype �� Y �ΰ�� �������Ÿ���� �ʼ��� �߰��ؾ���
		alert('<spring:message code="approval.msg.audittype.y" />');
		return false;
	}
</c:if>
<%}%>

<%if ( "2".equals(linetype)){%>
	if(appItem2 === null)
	{
		if (doubleLines.length < 1){
			alert('<spring:message code="approval.msg.applines.after.notexit2" />');
			return false;
		}
		
		if(doubleLines[doubleLines.length-1].getAttribute('procType') !== ""){
			alert('<spring:message code="approval.msg.applines.after.notexit2" />');
			return false;
		}
	}
<%}%>

	//�μ����� �ߺ� üũ
	if(CURRART === ART032 || CURRART === ART033 || CURRART === ART034 || CURRART === ART035){
		var ART033s = $('#tbApprovalLines tbody tr[askType="'+ART033+'"]');

		for(var i = 0; i < ART033s.length; i++){		
			var approverDeptId = ART033s[i].getAttribute('approverDeptId');
			
			var ART033Cnt = $('#tbApprovalLines tbody tr[askType="'+ART033+'"][approverDeptId='+approverDeptId+']').length;
			var ART035Cnt = $('#tbApprovalLines tbody tr[askType="'+ART035+'"][approverDeptId='+approverDeptId+']').length;
		
			if(ART033Cnt > 1 || ART035Cnt > 1){ //1�� �̻��̸� ���� ������ �߸��� ����
				alert('<spring:message code="approval.msg.applines.after.errappline" />');
				return;
			}
		
			if(ART033Cnt === 1 && ART035Cnt === 0){
				alert('<spring:message code="approval.msg.applines.after.errappline" />');
				return;
			}
		}
	}

	//�μ����� �ߺ� üũ
	if(CURRART === ART132 || CURRART === ART133 || CURRART === ART134 || CURRART === ART135){ // jth8172 2012 �Ű��� TF
		var ART133s = $('#tbApprovalLines tbody tr[askType="'+ART133+'"]');

		for(var i = 0; i < ART133s.length; i++){		
			var approverDeptId = ART133s[i].getAttribute('approverDeptId');
			
			var ART133Cnt = $('#tbApprovalLines tbody tr[askType="'+ART133+'"][approverDeptId='+approverDeptId+']').length;
			var ART135Cnt = $('#tbApprovalLines tbody tr[askType="'+ART135+'"][approverDeptId='+approverDeptId+']').length;
		
			if(ART133Cnt > 1 || ART135Cnt > 1){ //1�� �̻��̸� ���� ������ �߸��� ����
				alert('<spring:message code="approval.msg.applines.after.errappline" />');
				return;
			}
		
			if(ART133Cnt === 1 && ART135Cnt === 0){
				alert('<spring:message code="approval.msg.applines.after.errappline" />');
				return;
			}
		}
	}
	
	//�μ�����  �ߺ� üũ
	if(CURRART === ART041 || CURRART === ART042 || CURRART === ART043 || CURRART === ART044){
		var ART042s = $('#tbApprovalLines tbody tr[askType="'+ART042+'"]');
		
		for(var i = 0; i < ART042s.length; i++){
			var approverDeptId = ART042s[i].getAttribute('approverDeptId');
			
			var ART042Cnt = $('#tbApprovalLines tbody tr[askType="'+ART042+'"]').length;
			var ART044Cnt = $('#tbApprovalLines tbody tr[askType="'+ART044+'"]').length;

			if(ART042Cnt > 1 || ART044Cnt > 1){ //1�� �̻��̸� ���� ������ �߸��� ����
				alert('<spring:message code="approval.msg.applines.after.errappline" />');
				return;
			}

			if(ART042Cnt === 1 && ART044Cnt === 0){
				alert('<spring:message code="approval.msg.applines.after.errappline" />');
				return;
			}
		}
	}
		
	return true;
}

/**
 * ���߰��� ������� üũ
 */
function dobleLineCheck(){
	var doubleLines = $('#tbApprovalLines tbody tr[lineNum="2"]'); //���߰��� ���ο� �ִ� ��ҵ�
	//doubleLines.first().getAttribute("askType");
	if(doubleLines.length === 1){
		if(doubleLines.first().attr("askType") === ART021 || doubleLines.first().attr("askType") === ART010 || doubleLines.first().attr("askType") === ART053){
			return true;
		}else{
			alert('<spring:message code="approval.msg.applines.nomaindeptandapprover" />');
			return false;
		}
	}

	if(doubleLines.length > 1){
		if(doubleLines.last().attr("askType") === ART010 && 
				(doubleLines.first().attr("askType") === ART050 
						|| doubleLines.first().attr("askType") === ART051 
						|| doubleLines.first().attr("askType") === ART051)){
			return true;
		}else{
			alert('<spring:message code="approval.msg.applines.after.noorder2" />');
			return false;
		}
	}
	return true;
}

//------------- �����α׷� ���� ------------- 2011.06.01 �Ű��� //
<%= com.sds.acube.app.design.AcubeTab.getScriptFunction(2) %>

var appLineList = null;

function checkProc() {
	var lines = $('#tbApprovalLines tbody').children();
	var groupYn = "Y";
	linesLength = lines.length;
	for (i=0; i<linesLength; i++) {
		var procType = $('#'+lines[i].id).attr('procType');
		if (procType != "") {
			groupYn = "N";
		}
	}
	return groupYn;
}

function callMethod(flag) {	
	if (flag == "divAppLineGroup") {
		var appLineGroup = $('#tblAppLineGroup tbody').children();
		if(appLineGroup.length > 0){
			onAppLineGroupClick($('#'+appLineGroup[0].id));
		}

		$('#div_dept_search').hide();
		$('#div_dept_search_no').show();
		
	}else{
		$('#div_dept_search').show();
		$('#div_dept_search_no').hide();
	}
	
	$('div[group="appLine"]').hide();
	$('#'+flag).show();
}

function codeToName(cd) {
	var ART010 = "<%=art010_opt001%>";
	var ART020 = "<%=art020_opt002%>";
	var ART021 = "<%=art020_opt002%>";
	var ART030 = "<%=art030_opt003%>";
	var ART031 = "<%=art031_opt004%>";
	var ART032 = "<%=art032_opt005%>";
	var ART033 = "<%=art033_opt006%>";
	var ART034 = "<%=art034_opt007%>";
	var ART035 = "<%=art035_opt008%>";

	var ART130 = "<%=art130_opt053%>"; // jth8172 2012 �Ű��� TF
	var ART131 = "<%=art131_opt054%>"; // jth8172 2012 �Ű��� TF
	var ART132 = "<%=art132_opt055%>"; // jth8172 2012 �Ű��� TF
	var ART133 = "<%=art133_opt056%>"; // jth8172 2012 �Ű��� TF
	var ART134 = "<%=art134_opt057%>"; // jth8172 2012 �Ű��� TF
	var ART135 = "<%=art135_opt058%>"; // jth8172 2012 �Ű��� TF

	var ART040 = "<%=art040_opt009%>";
	var ART041 = "<%=art041_opt010%>";
	var ART042 = "<%=art042_opt011%>";
	var ART043 = "<%=art043_opt012%>";
	var ART044 = "<%=art044_opt013%>";
	var ART045 = "<%=art045_opt021%>";
	var ART046 = "<%=art046_opt022%>";
	var ART047 = "<%=art047_opt023%>";
	var ART050 = "<%=art050_opt014%>";
	var ART051 = "<%=art051_opt015%>";
	var ART052 = "<%=art052_opt016%>";
	var ART053 = "<%=art053_opt017%>";
	var ART054 = "<%=art054_opt018%>";
	var ART055 = "<%=art055_opt059%>"; // jth8172 2012 �Ű��� TF
	var ART060 = "<%=art060_opt019%>";
	var ART070 = "<%=art070_opt020%>";
	
	//	�������, ��ȹ���� ���� ��û		20150422_csh
	var ART081 = "<%=art081_opt030%>";	//	�������
	var ART082 = "<%=art082_opt031%>";	//	�������(���)
	var ART083 = "<%=art083_opt032%>";	//	�������(����)
	var ART084 = "<%=art084_opt033%>";	//	�������(����)
	var ART091 = "<%=art091_opt040%>";	//	��ȹ����
	var ART092 = "<%=art092_opt041%>";	//	��ȹ����(���)
	var ART093 = "<%=art093_opt042%>";	//	��ȹ����(����)
	var ART094 = "<%=art094_opt043%>";	//	��ȹ����(����)
	return eval(cd);
}

function onAppLineGroupClick(obj){
	selectOneElementGp(obj);//2011.09.03 ctrl, atl, shift Ű����
	getAppLine(obj);
}

function selectOneElementGp(obj){
	$('document').empty();
	gSaveObject.restore();
	gSaveObject.add(obj, sColor);
}

function onAppLineGroupDblClick() {
	appLineGroupAdd();
}

function appLineGroupAdd() {

	var tbAppLines = $('#tbApprovalLines tbody').children();	
	linesLength = tbAppLines.length;
	appLineLength = appLineList.length-1;
	if (linesLength > 1) {
		for (var i=1; i<linesLength; i++) { //�׷��߰��� ���������� ���� 20150312_dykim
			$('#'+tbAppLines[i].id).remove();
		}
	}
	tbAppLines = $('#tbApprovalLines tbody').children();
	for (var i=appLineLength; i>=1; i--) { //�׷��߰� �������� ���� 20150312_dykim
		appLineObj = appLineList[i];
		if (appLineObj.changeYn != "Y") {
			var row = approveMakeRow("tbApprovalLines", appLineObj.approverId, "1", appLineObj.lineOrder
					, appLineObj.lineSubOrder, appLineObj.approverId, appLineObj.approverName, appLineObj.approverPos
					, appLineObj.approverDeptId, appLineObj.approverDeptName, "", "", "", "", "", appLineObj.askType
					, "", "", "", "", "", "", "", "", "", "", "", "", "", appLineObj.roleCodes, appLineObj.approverPos
					, appLineObj.approverDeptName, appLineObj.approverName, codeToName(appLineObj.askType));
			$('#'+tbAppLines[0].id).after(row); //���� �ϴ� ���� 20150312_dykim
		} else {
			alert(appLineObj.approverName+"<spring:message code='env.group.msg.notice.appline'/>");
		}
	}
}

function getAppLine(obj){		
	var lineGroupId = "";//obj.attr('id');
	var procUrl = "<%=webUri%>/app/env/listEnvAppLine.do?lineGroupId="+lineGroupId;
	var results = null;

	$.ajaxSetup({async:false});
	$.getJSON(procUrl, function(data){
		results = data;
	});
	
	appLineList = results;
	drawAppLine(results);			
}

function drawAppLine(appLineList) {
	var tbl = $('#tblAppLine');
	var txtAppLines = "";
	var applineLength = appLineList.length;		
	//var notice = "N";
	var bgcolor = "";
	tbl.empty();

	for (var i=0; i<applineLength; i++) {				
		var appLine = appLineList[i];
		var askName = codeToName(appLine.askType);
		bgcolor = "ffffff";
		if (appLine.changeYn == "Y") {
			bgcolor = "ffefef";
			//notice = "Y";
		}
		var row = "<tr bgcolor='#"+bgcolor+"'>";
		row += "<td width='73' class='ltb_center'>"+appLine.approverDeptName+"</td>";
		if (appLine.askType == "ART032" || appLine.askType == "ART132" || appLine.askType == "ART041"
				|| appLine.askType == "ART081" || appLine.askType == "ART091") {
			row += "<td width='73' class='ltb_center'></td>";
		} else {
			row += "<td width='73' class='ltb_center'>"+appLine.approverPos+"</td>";
		}
		row += "<td width='72' class='ltb_center'>"+appLine.approverName+"</td>";
		row += "<td width='*' class='ltb_center'>"+askName+"</td>";
		row += "</tr>";
		tbl.append(row);
	}

}

//------------- �����α׷� �� ------------- 2011.06.01 �Ű��� //

//�������� �˻�
var sameUsers = "";
function goSameNameUser(){
	var userName = $('#userName');

	if(userName.val() === ""){
		alert("<spring:message code='list.list.msg.noSearchUser'/>");
		userName.focus();
		return;
	}
	
	var results = "";
	var url = "<%=webUri%>/app/common/sameNameUsers.do";
	$.ajaxSetup({async:false});
	$.getJSON(url,userName.serialize() ,function(data){
		results = data;
	});

	if(results !== ""){
		sameUsers = results
		if(results.length == 0) {
			alert("<spring:message code='app.alert.msg.56'/>");
			$('#userName').val("");
		}else if(results.length == 1){
			makeSameUser(results[0]);
		}else{
			var width = 400;
			var height = 350;
			var url = "<%=webUri%>/app/common/sameNameUsers.do?method=2";
			var appDoc = null;
			appDoc = openWindow("sameUserWin", url, width, height); 
		}
	}		
}
//�������� �˻�
function getSameUsers(){
	return sameUsers;
}
//�������� �˻�
var bPop = false;
var popMsg = "";
function setSameUsers(user){
	bPop = true;
	makeSameUser(user);
	bPop = false;
	return popMsg;
}

function makeSameUser(user){
	var tbl = $('#sameUser tbody');
	tbl.empty();
	
	var row = "<tr id='"+user.userUID+"' deptID='"+ user.deptID+"' deptName='"+ user.deptName +"' userName='"+ user.userName;	
	row += "' gradeCode='"+ user.gradeCode + "' gradeName='"+ user.gradeName +"' compID='"+ user.compID +"' compName='"+ user.compName;	
	row += "' userID='"+ user.userID + "' positionCode='"+ user.positionCode +"' positionName='"+ user.displayPosition +"' titleName='"+ user.titleName;
	row += "' roleCodes='"+ user.roleCodes;
	row += "'>";
	var userPosition = (user.displayPosition == ""?"&nbsp;":user.displayPosition);
	row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;' width='88'>"+userPosition+"</td>";
	row += "<td class='ltb_center' style='border-bottom: "+tbColor+" 1px solid;border-left:1pt solid  "+tbColor+";border-right:1pt solid  "+tbColor+";'>"+user.userName+"</td></tr>";
	tbl.append(row);

	var tRow = $('#sameUser tbody tr[id='+user.userUID+']');

	gSaveObject.restore();
	gSaveObject.add(tRow, sColor);

	addApprovalLine();

	$('#userName').val("");
}

var ownDeptId = "";
function selectOwnDept(objDept){
	var url = "<%=webUri%>/app/common/searchDept.do?method=2";
	url += "&compId=" + objDept.companyID;
	url += "&deptId=" + objDept.orgID;

	var results = "";
	$.ajaxSetup({async:false});
	$.getJSON(url,"",function(data){
		results = data;
	});

	if(results != ""){
		var rsCnt = results.length;
		for(var i = 0; i < rsCnt; i++){
		
			var node = $('#'+results[i].orgID);
			openNode(node[0], $.tree.focused());
			
			if(i === (rsCnt -1)){
				ownDeptId = '#' + results[i].orgID;
				$.tree.focused().scroll_into_view(ownDeptId);
				$.tree.focused().select_branch(ownDeptId);			
			}
		}
	}

	setTimeout(function(){
		if(ownDeptId != null){
			jQuery.tree.reference("#org_tree").scroll_into_view(ownDeptId);
		}
		
	},10);
	
}

function goSearch(){
	var searchType = $('#searchType');
	if(searchType.val() === "1"){
		goSameNameUser();
	}else{
		goSearchDept();
	}
}

--></script>
<style>
	.tdSelect{
		background-color : #F9E5DF;
	}
</style>
</head> 
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
		<acube:outerFrame>
			<acube:titleBar><spring:message code="approval.title.applines" /></acube:titleBar>
			<!-- ���� ���� -->
			<acube:space between="button_content" table="y"/>
			<!-- ���� �� -->
			
			<!-- �� ���� -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
		            <td>
		               <acube:tabGroup>
		                  <acube:tab index="1" onClick="JavaScript:selectTab(1);callMethod('divAppLine');" selected="true"><spring:message code="approval.title.applines" /></acube:tab>
						<c:if test='${groupYn == "Y"}'>
						  <acube:space between="tab"/>
						  <acube:tab index="2" onClick="JavaScript:selectTab(2);callMethod('divAppLineGroup');"><spring:message code="env.tapmenu.linegroup" /></acube:tab>
		               </c:if>
		               </acube:tabGroup>
		            </td>
		         </tr>
	         </table>
	         <!-- �� �� -->
	         
	         <!-- ���� ���� -->
			<acube:space between="button_content" table="y"/>
			<!-- ���� �� -->
			<!-- �˻�  -->
			<div id="div_dept_search">
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td class="tb_tit" width="70">
					<input type="hidden" id="searchType" name="searchType" value="1"><spring:message code="env.search.user.title" />
				</td>				
				<td class="tb_tit" width="25%"><input id="userName" name="userName" type="text" style="width: 100%;ime-mode:active;FONT-SIZE:9pt; FONT-FAMILY:Gulim,Dotum,Arial; color:#777777; background-color:#FFFFFF; height:16px; padding-left:3px; padding-right:3px; border:1px #C3C2C2 solid;" />
				<input id="searchDept" name="searchDept" type="hidden" />
				</td>
				<td class="tb_tit" width="10%"><a href="javascript:goSearch();"><img id="imgRelDoc" src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
				<td class="tb_tit">&nbsp;</td>
			</tr>
			</acube:tableFrame>
			</div>
			<div id="div_dept_search_no" style="display:none">
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td class="tb_tit">&nbsp;</td>
			</tr>
			</acube:tableFrame>
			</div>
			<!-- ���� ���� -->
			<acube:space between="button_content" table="y"/>
			<!-- ���� �� -->
			
			<!-- ������ ���� -->
			<div id="divAppLine" group="appLine" style="display:block;">
			
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td width="48%">
				 <!-- Ʈ���� ���̺�  -->
				<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
				<tr>
					<td height="150" class="basic_box">
					<div id="org_tree" style="height:155px; overflow:auto; background-color : #FFFFFF;">
				    <ul>
		<!-- 		    	<li id="ROOT" class="open"><a href='#'><ins></ins>ROOT</a>  -->
		<!--		    	<ul> -->
				    	<%
				    	int nSize = results.size();
				    	for(int i = 0; i < nSize; i++){
				    		DepartmentVO result = results.get(i);
				    		String _class = "class='closed'";
				    		String rel = "";
				    		boolean hasChild = false;
				    		
				    		if(i < (nSize-1)){
				    		    //�ڽ����翩��
					    		hasChild = result.getOrgID().equals(results.get(i+1).getOrgParentID());	    		
							}
				    		
				    		if(result.getOrgType()== 0 ) {
								_class = "class='open'";
								rel = " rel='root' ";
							}else if(result.getOrgType() == 1){
							    rel = " rel='comp' ";
							}else if(result.getOrgType() == 2){
							    if ("Y".equals(opt378)) {
								   	if(result.getIsProcess()){
								   	 	rel = " rel='proc_dept' ";
								   	}else{
								   		rel = " rel='dept' ";
								   	}
								} else {	
							    	rel = " rel='dept' ";
								}	    
							}else{
							    rel = " rel='part' ";
							}
				    		
				    		out.println("<li id='"+result.getOrgID()+"' parentId='"+result.getOrgParentID()+"' orgName='"+result.getOrgName()+"' orgType='"+result.getOrgType()+"' depth='"+ result.getHasChild() +"' "+_class+rel+"><a href='#'><ins style='vertical-align:top'></ins>"+result.getOrgName()+"</a>");
				    		
				    		if(hasChild){//�ڽ���������
				    		    out.println("<ul>");
				    		}
				    		
				    		
				    		if(i <= (nSize -2)){
				    		   
				    		   if(!hasChild && !result.getOrgParentID().equals(results.get(i+1).getOrgParentID())){
				    		   		//out.println("</li>\n</ul>\n</li>");
				    			   int deptSize = results.get(i+1).getDepth() - result.getDepth();
				    			   out.println("</li>");
				    			   for(int nLi = 0; nLi < deptSize; nLi++){
				    				   out.println("\n</ul>\n</li>");
				    			   }
				    		   }
				    		   
				    		   if(!hasChild && result.getOrgParentID().equals(results.get(i+1).getOrgParentID())){
				    		   		out.println("</li>");
				    		   }
				    		}
				    		
				    		if(i == (nSize-1)){
					    		if(!results.get(0).getOrgParentID().equals(results.get(i).getOrgParentID())){
					    			out.println("</li>\n</ul>\n</li>");
					    		}else{
					    			out.println("</li>");
					    		}
				    		}
				    	}
				    	%>
		<!--		    	</ul> -->
		<!--				</li> -->
				    </ul>
					</div>				
					</td>
				</tr>
				</acube:tableFrame><!-- Ʈ���� ���̺� -->
				</td>
				<td width="3%" style="background-color : #FFFFFF;"></td>
				<td>
				<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
				<tr>
					<td height="177" class="basic_box" style="padding:0px; margin:0px;">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
						<thead>
							<TR>
								<TD width="152" class="ltb_head" style="border-left:1pt solid <%=tbColor %>;border-bottom: <%=tbColor %> 1px solid;"><spring:message code="approval.table.title.gikwei" /></TD>
								<TD class="ltb_head"  style='border-left:1pt solid <%=tbColor %>;border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.name" /></TD>
							</TR>
						</thead>
					</table>
					<div style="height:150px; overflow-x:hidden; overflow-y:scroll; background-color : #FFFFFF;">
					<table id="tbUsers" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tbody />
					</table>
					</div>
				</td>
				</tr>
				</acube:tableFrame>
				</td>
			</tr>
			</acube:tableFrame>
			
			<!-- ���� ���� -->
			<acube:space between="button_content" table="y"/>
			<!-- ���� �� -->
		 
		 <!-- ���� �ɼ� -->
			<acube:tableFrame width="100%" border="0" cellpadding="1" cellspacing="0" class="">
				<tr>
					<td width="100%" height="100%" class="g_box" style="padding:2px 2px 2px 2px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td height="5" colspan="3"></td>
							</tr>
							<tr>
								<td width="5" height="30"></td>
								<td style="font-size:9pt;">
									<div id='divART032' style="display:none; height:30px; "><!-- ==============�μ� ����============================== -->
										<c:if test='${options["OPT006"].useYn == "Y"}'>
										<span class="range">
										<input id='ART033' name="options1" type="radio"  value='<%=appCode.getProperty("ART033", "ART033", "APP") %>' codeNm='${options["OPT006"].optionValue}' onclick="optClick(this)" />${options["OPT006"].optionValue}
										</span>
										</c:if><!-- ����(���) -->
									</div>
									<div id='divART132' style="display:none; height:30px; "><!-- ==============�μ� ����============================== -->
										<c:if test='${options["OPT056"].useYn == "Y"}'>
										<span class="range">
										<input id='ART133' name="options1" type="radio"  value='<%=appCode.getProperty("ART133", "ART133", "APP") %>' codeNm='${options["OPT056"].optionValue}' onclick="optClick(this)" />${options["OPT056"].optionValue}
										</span>
										</c:if><!-- ����(���) // jth8172 2012 �Ű��� TF-->
									</div>
									<div id='divART041' height="30" style="display:none; height:30px; "><!-- ==============�μ� ����============================== -->
<%-- 										<c:if test='${options["OPT011"].useYn == "Y"}'> --%>
										<span class="range">
										<input id='ART042' name="options1" type="radio"  value='<%=appCode.getProperty("ART042", "ART042", "APP") %>' codeNm='${options["OPT011"].optionValue}' onclick="optClick(this)" />${options["OPT011"].optionValue}
										</span>
<%-- 										</c:if><!-- ����(���) --> --%>
									</div>
									<div id='divART081' height="30" style="display:none; height:30px; "><!-- ============== ������� ============================== -->
<%-- 										<c:if test='${options["OPT031"].useYn == "Y"}'> --%>
										<span class="range">
										<input id='ART082' name="options1" type="radio"  value='<%=appCode.getProperty("ART082", "ART082", "APP") %>' codeNm='${options["OPT031"].optionValue}' onclick="optClick(this)" />${options["OPT031"].optionValue}
										</span>
<%-- 										</c:if><!-- �������(���) --> --%>
									</div>
									<div id='divART091' height="30" style="display:none; height:30px; "><!-- ============== ��ȹ���� ============================== -->
<%-- 										<c:if test='${options["OPT041"].useYn == "Y"}'> --%>
										<span class="range">
										<input id='ART092' name="options1" type="radio"  value='<%=appCode.getProperty("ART092", "ART092", "APP") %>' codeNm='${options["OPT041"].optionValue}' onclick="optClick(this)" />${options["OPT041"].optionValue}
										</span>
<%-- 										</c:if><!-- ��ȹ����(���) --> --%>
									</div>
				<%
				if("1".equals(linetype)){ //���ϰ����� ���
				%>
				<%}else{ %>	
				<%} %>
								</td>
								<td width="5"></td>
							</tr>
							<tr>
								<td height="2" colspan="3"></td>
							</tr>
						</table>
					</td>
				</tr>
			</acube:tableFrame>
			
			<!-- ���� ���� -->
			<acube:space between="button_content" table="y"/>
			<!-- ���� �� -->
			<table border='0' cellpadding='0' cellspacing='0' width="100%">
				<tr>
				<td width="100%" height="23" align="right" valign="bottom">
					<acube:buttonGroup align="right">
					<acube:button id="addlineBtn" disabledid="" type="right" onclick="onAddLine();" value="<%=addlineBtn%>" />
					<acube:space between="button" />
					<acube:button id="dellineBtn" disabledid="" type="left" onclick="onDeleteLine();" value="<%=dellineBtn%>" />
					</acube:buttonGroup>
				</td>
				</tr>
			</table>
			
			</div>
			<!-- ������ �� -->
			
			<!-- ////////// �����α׷� ���� ////////// 2011.06.01 �Ű��� -->
			<div id="divAppLineGroup" group="appLine" style="display:none;">
			
				<div id="divAppLineGroupList" style="float:left; width:45%;">
					<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
						<tr> 
							<td height="235" class="basic_box" style="padding:2px;" valign="top">
									
								<!-- �����α׷� ��� -->								
								<table cellpadding="0" cellspacing="1" width="100%" class="table" style="table-layout:fixed;">
									<tr>
										<td width="187" class="ltb_head"><spring:message code="env.option.form.03"/></td>
										<td width="*" class="ltb_head"><spring:message code="env.option.form.06"/></td>
									</tr>
								</table>
								<div style="height:235px; overflow-x:hidden; overflow-y:scroll; background-color:#ffffff;">
								<table id="tblAppLineGroup" cellpadding="0" cellspacing="1" width="100%" class="table_body" style="table-layout:fixed;">
									<tbody>
									
									<c:forEach var="vo" items="${gList}">
										<tr id="${vo.lineGroupId}" name="${vo.lineGroupId}" onClick='onAppLineGroupClick($("#${vo.lineGroupId}"));' onDblClick='onAppLineGroupDblClick($("#${vo.lineGroupId}"));' style="background-color:#ffffff;">
											<td width="180" class="ltb_left" style="text-overflow:ellipsis;overflow:hidden;" title="${vo.lineGroupName}"><nobr>${vo.lineGroupName}</nobr></td>
											<td width="*" class="ltb_center"><spring:message code="env.grouptype.${vo.groupType}"/></td>											
										</tr>
									</c:forEach>
									
									</tbody>
								</table>
								</div>
								
							</td>
						</tr>
					</acube:tableFrame>
				</div>
				<div id="divAppLineList" style="float:right; width:53%;">
					<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
						<tr>
							<td height="240" class="basic_box" style="padding:2px;">
							
								<!-- ������ -->								
								<table cellpadding="0" cellspacing="1" width="100%" class="table" style="table-layout:fixed;">
									<tr>
										<td width="25%" class="ltb_head"><spring:message code="env.option.form.07"/></td>
										<td width="25%" class="ltb_head"><spring:message code="env.option.form.08"/></td>
										<td width="25%" class="ltb_head"><spring:message code="env.option.form.09"/></td>
										<td width="25%" class="ltb_head"><spring:message code="env.option.form.06"/></td>
									</tr>
								</table>
								<div style="height:228px; overflow-x:hidden; overflow-y:scroll; background-color:#ffffff;">
								<table id="tblAppLine" cellpadding="0" cellspacing="1" width="100%" class="table_body" style="table-layout:fixed;">
									<tbody />
								</table>
								</div>	
								
								
							</td>
						</tr>
					</acube:tableFrame>
				</div>
				
			<!-- ���� ���� -->
			<acube:space between="button_content" table="y"/>
			<!-- ���� �� -->
			<table border='0' cellpadding='0' cellspacing='0' width="100%">
				<tr>
				<td width="100%" height="23" align="right" valign="bottom">
					<acube:buttonGroup align="right">
					<acube:button id="addlineBtn" disabledid="" type="right" onclick="appLineGroupAdd();" value="<%=addlineBtn%>" />
					<acube:space between="button" />
					<acube:button id="dellineBtn" disabledid="" type="left" onclick="onDeleteLine();" value="<%=dellineBtn%>" />
					</acube:buttonGroup>
				</td>
				</tr>
			</table>
			
			</div>
			<!-- ////////// �����α׷� ���� ////////// 2011.06.01 �Ű��� -->
			
			<!-- ���� ���� -->
			<acube:space between="button_content" table="y"/>
			<!-- ���� �� -->
			<!-- ���� ��� -->
			<table  width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<acube:tableFrame width="100%" border="0" cellpadding="1" cellspacing="0" >
							<tr>
								<td width="100%" height="100%">
									<div>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<thead>
											<TR><TD width="130" class="ltb_head" style="border-top:none;border-bottom: <%=tbColor%> 1px solid;"><spring:message code="approval.table.title.sosok" /></TD>
												<TD width="130"  class="ltb_head"  style='border-left:1pt <%=tbColor%> solid; border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.gikwei" /></TD>
												<TD width="130"  class="ltb_head"  style='border-left:1pt <%=tbColor%> solid; border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.name" /></TD>
												<TD class="ltb_head"  style='border-left:1pt <%=tbColor%> solid; border-bottom: <%=tbColor %> 1px solid;'><spring:message code="approval.table.title.gubun" /></TD>
											</TR>
										</thead>
									</table>
									</div>
									<div style="height:120px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF;">
									<table id="tbApprovalLines" width="100%" border="0" cellpadding="0" cellspacing="0">
										<tbody />
									</table>
									</div>
								</td>
							</tr>
						</acube:tableFrame>
					</td>
					<td width="5"></td>
					<td width="2%">
						<TABLE cellpadding="0" cellspacing="0" width="2%">
							<TR>
								<TD style="background:#ffffff" align="center" valign="middle">
									<IMG src="<%=webUri%>/app/ref/image/bu_up.gif" onclick="javascript:onMoveUp();return(false);"><BR /><BR />
									<IMG src="<%=webUri%>/app/ref/image/bu_down.gif" onclick="javascript:onMoveDown();return(false);">
								</TD>
							</TR>
						</TABLE>
					</td>
				</tr>
			</table>
			<!-- ���� ���� -->
			<acube:space between="button_content" table="y"/>
			<!-- ���� �� -->
<%-- 			<acube:buttonGroup align="right"> --%>
<%-- 			<acube:button id="sendOk" disabledid="" onclick="sendOk();" value="<%=confirmBtn%>" /> --%>
<%-- 			<acube:space between="button" /> --%>
<%-- 			<acube:button id="sendCalcel" disabledid="" onclick="closePopup();" value="<%=closeBtn%>" /> --%>
<%-- 			</acube:buttonGroup> --%>
			</div>		
		</acube:outerFrame>
		<div id="divPop" style="display: none;position: absolute;">
		<table id="sameUser">
			<tbody></tbody>
		</table>
		</div>
		<form id="appDocForm" name="appDocForm" method="post">
		<%
			for (int loop = 0; loop < docCount; loop++) {
				AppDocVO appDocVO = appDocVOs.get(loop);
				List<AppLineVO> appLineVOs = appDocVO.getAppLine();
		%>
			<div id="approvalitem" name="approvalitem">
				<input id="docId" name="docId" type="hidden" value="<%=appDocVO.getDocId()%>"></input><!-- ����ID --> 
				<input id="compId" name="compId" type="hidden" value="<%=appDocVO.getCompId()%>"></input><!-- ȸ��ID -->
				<input id="appLine" name="appLine" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppLine(appLineVOs))%>"></input><!-- ������ -->
			</div> 
		<%
			}
		%>
		</form>
</body>	
</html>