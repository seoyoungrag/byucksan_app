<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="com.sds.acube.app.design.AcubeList,com.sds.acube.app.design.AcubeListRow,com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.env.vo.FormVO"%>
<%@ page import="com.sds.acube.app.login.vo.UserProfileVO"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ page import="com.sds.acube.app.common.vo.AuditListVO" %>
<%@ page import="com.sds.acube.app.common.util.DateUtil" %>
<%@ page import="java.net.InetAddress" %>
<%@ page import="java.net.NetworkInterface" %>
<%@ page import="java.net.URLDecoder"%>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    /** 
     *  Class Name  : InsertEnvForm.jsp
     *  Description : ���� ���� ���
     *  Modification Information 
     * 
     *   �� �� �� : 2011.04.25 
     *   �� �� �� : ������ 
     *   �������� :  
     * 
     *  @author  ������
     *  @since 2011. 4. 25 
     *  @version 1.0 
     */
%>

<%
    String formname = messageSource.getMessage("env.form.formname", null, langType);//��ĸ�
    String registdate = messageSource.getMessage("env.form.registdate", null, langType);//�������
    
	String sendBtn = messageSource.getMessage("approval.enforce.button.send", null, langType); 		// �߼�
	String saveBtn = messageSource.getMessage("appcom.button.save", null, langType); 		// ����	
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 	// �ݱ�

    String compId = (String) session.getAttribute("COMP_ID");
/*     String rolecode = (String) session.getAttribute("ROLE_CODES");
	// ����ڵ�   // jth8172 2012 �Ű��� TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	if ("".equals(institutionId)) {
		institutionId = compId;
	}	
    String role11 = AppConfig.getProperty("role_doccharger", "", "role"); 		// ó����
    String role12 = AppConfig.getProperty("role_cordoccharger", "", "role"); 	// ������
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); 		// �ý��۰�����
	String relay_use = AppConfig.getProperty("relay_use", "", "relay"); 		// �������� ������� 
	
	String chargeType="";  //����� ����
	if(rolecode.contains(role12) ){
	    chargeType = "D";  //�����������
	    if(rolecode.contains(role11)){
		      chargeType = "W";  //������ �� ó���� �����
	    }
	} else if(rolecode.contains(role11)){
	    chargeType = "T";  // ó���� �����
    }
    
    String checked="";	//������ üũ
    String numberingYn="";
    
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String opt325 = appCode.getProperty("OPT325", "OPT325", "OPT"); // ���߰����뿩��
    String opt358 = appCode.getProperty("OPT358", "OPT358", "OPT"); // ä����뿩��
	opt325 = envOptionAPIService.selectOptionValue(compId, opt325);
    opt358 = envOptionAPIService.selectOptionValue(compId, opt358);
    String opt380 = appCode.getProperty("OPT380", "OPT380", "OPT"); // �����󹮼�,���繮�� ���� ��뿩��, jkkim, 20120718
	opt380 = envOptionAPIService.selectOptionValue(compId, opt380);
    
	// ����map
	Map roleType = (Map)request.getAttribute("type"); 
    
    // ���������� ��� �� ��ȸ
    String opt428 = appCode.getProperty("OPT428", "OPT428", "OPT");
    
    String editorTypeValues = envOptionAPIService.selectOptionText(compId, opt428);
    String[] editorType = editorTypeValues.split("\\^");
    int editorTypeCount = editorType.length; */
    
    HashMap<String, String> results = (HashMap<String,String>) request.getAttribute("result");
    
	//	���� IP
	InetAddress ip = InetAddress.getLocalHost();
	
	//	��Ʈ��ũ �������̽�
	NetworkInterface netIp = NetworkInterface.getByInetAddress(ip);
	
	String localIp = ""; 
	String macAdd = "";
	
	if (netIp != null) {
		localIp = ip.toString().substring(ip.toString().lastIndexOf('/')+1, ip.toString().length());
			
		byte[] mac = netIp.getHardwareAddress();
		
		for (int i=0; i<mac.length; i++) {
			macAdd = macAdd + String.format("%02X%s", mac[i], "");
		}
	}
    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code="env.option.title.senderTitle"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
</head>

<% if(results.get("auditReason").length()>0){ %>
<body leftmargin="10" topmargin="10" marginwidth="0" marginheight="0" onload="javascript:commonType('<%=results.get("auditReason") %>', '<%=results.get("elecType") %>', '<%=results.get("construction") %>', '<%=results.get("auditType") %>');">
<% } else { %>
<body leftmargin="10" topmargin="10" marginwidth="0" marginheight="0" onload="javascript:rngType();">
<%} %>
<center>
    <form:form modelAttribute="" method="post" name="frmForm" id="frmForm">
		<acube:outerFrame>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			    <tr>
			        <td><acube:titleBar><spring:message code='list.list.title.ezisRegister'/></acube:titleBar></td>
			    </tr>
			    <tr>
			    	<td>
			    		<acube:buttonGroup align="right">
				    		<acube:button id="sendBtn" onclick="saveEzis('S');" value="<%=sendBtn %>" type="2" class="gr" />
							<acube:space between="button" />
							<acube:button id="saveBtn" onclick="saveEzis('I');" value="<%=saveBtn %>" type="2" class="gr" />
							<acube:space between="button" />
							<acube:button id="closeBtn" onclick="closePopup();" value="<%=closeBtn %>" type="2" class="gr" />
							<acube:space between="button" />
						</acube:buttonGroup>
			    	</td>
			    </tr>
			    <tr>
					<td><acube:space between="title_button" /></td>
				</tr>
			</table>
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
				<tr>
					<td>
						<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="1" class="td_table">
							<tr bgcolor="#ffffff">
								<td width="10%" class="tb_tit_center"><spring:message code='list.list.title.headerOpinionTitle'/></td>
								<td class="tb_left_bg" colspan="7">
									<input type="text" id="auditTitle" name="auditTitle" class="input_read" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" readOnly value="<%=results.get("auditTitle")%>">
								</td>
							</tr>							
							<tr bgcolor="#ffffff">
								<!-- �������� -->
								<td width="10%" class="tb_tit_center" ><spring:message code='list.list.title.auditOpinionDate'/></td>
								<td class="tb_left_bg" colspan="7">
									<table border="0" width="100%" cellpadding="0" cellspacing="0">
										<tr>
											<td width="15%" class="tb_left_bg">
												<input type="text" id="opinionDate" name="opinionDate" class="input_read" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" readOnly value="<%=DateUtil.getFormattedShortDate(results.get("opinionDate"))%>">
											</td>
											<!-- ������ -->
											<td width="10%" class="tb_tit_center" ><spring:message code='list.list.title.receiveDate'/></td>
											<td width="15%" class="tb_left_bg">
												<input type="text" id="receiveDate" name="receiveDate" class="input_read" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" readOnly value="<%=DateUtil.getFormattedShortDate(results.get("receiveDate"))%>">
											</td>
											<!-- �Ƿںμ� -->
											<td width="10%" class="tb_tit_center" ><spring:message code='list.list.title.requestDept'/></td>
											<td width="20%" class="tb_left_bg" colspan="3">
												<input type="text" id="requestDept" name="requestDept" class="input_read" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" readOnly value="<%=results.get("requestDept")%>">
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr bgcolor="#ffffff">
								<!-- ������ -->
								<td width="10%" class="tb_tit_center" ><spring:message code='list.list.title.auditOpinionPeople'/></td>
								<td class="tb_left_bg" colspan="7">
									<table border="0" width="100%" cellpadding="0" cellspacing="0">
										<tr>
											<td width="15%" class="tb_left_bg">
												<input type="text" id="auditPeople" name="auditPeople" class="input_read" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" readOnly value="<%=results.get("auditPeople")%>">
												<input type="hidden" id="auditUid" name="auditUid" value="<%=results.get("auditUid")%>">
											</td>
											<!-- ���� -->
											<td width="10%" class="tb_tit_center" ><spring:message code='env.position.priority.grade'/></td>
											<td width="15%" class="tb_left_bg">
												<input type="text" id="auditPos" name="auditPos" class="input_read" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" readOnly value="<%=results.get("auditPos")%>">
											</td>
											<!-- �μ� -->
											<td width="10%" class="tb_tit_center" ><spring:message code='list.list.title.headerDrafterDept'/></td>
											<td width="15%" class="tb_left_bg">
												<input type="text" id="auditDept" name="auditDept" class="input_read" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" readOnly value="<%=results.get("auditDept")%>">
												<input type="hidden" id="auditDeptCode" name="auditDeptCode" value="<%=results.get("auditDeptCode")%>">
											</td>
											<!-- ��������� -->
											<td width="15%" class="tb_tit_center" ><spring:message code='list.list.title.closedCheck'/></td>
											<td width="15%" class="tb_left_bg">
												<input type="checkbox" id="closedCheck" name="closedCheck" value="N" onclick="javascript:setChecked(this,frmForm.closedCheck)" <%="Y".equals(results.get("closedCheck")) ? " checked " : ""%>>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr bgcolor="#ffffff">
								<td width="10%" class="tb_tit_center" ><spring:message code='list.list.title.auditOpinion'/></td>
								<td class="tb_left_bg" colspan="7">
									<textarea id="auditOpinion" name="auditOpinion" rows="10" cols="10" style="width: 100%;word-break:break-all;" onkeyup="checkInputMaxLength(this,'',2000)"><%=results.get("auditOpinion")%></textarea>
								</td>
							</tr>
						</acube:tableFrame>
					</td>
				</tr>
				<tr>
					<td><acube:space between="title_button" /></td>
				</tr>
			</acube:tableFrame>
			<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
				<tr>
					<td><acube:space between="title_button" /></td>
				</tr>
				<tr>
					<td><acube:space between="title_button" /></td>
				</tr>
				<tr>
					<td>
						<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="1" class="td_table">
							<tr bgcolor="#ffffff">
								<!-- �ϻ󰨻�ٰ� -->
								<td width="14%" class="tb_tit_center" ><spring:message code='list.list.title.auditReason'/></td>
								<!-- ���� -->
								<td width="14%" class="tb_tit_center" ><spring:message code='list.list.title.headerElecType'/></td>
								<!-- ���� -->
								<td width="14%" class="tb_tit_center" ><spring:message code='list.list.title.construction'/></td>
								<!-- ���� -->
								<td width="14%" class="tb_tit_center" ><spring:message code='list.list.title.headerType'/></td>
								<!-- ��� �� ���ֱݾ� -->
								<td width="14%" class="tb_tit_center" ><spring:message code='list.list.title.valueOrdered'/></td>
								<!-- ���躯�������� -->
								<td width="14%" class="tb_tit_center" ><spring:message code='list.list.title.designChange'/></td>
								<!-- ������������ -->
								<td width="15%" class="tb_tit_center" ><spring:message code='list.list.title.reduceBudget'/></td>
							</tr>
							<tr bgcolor="#ffffff">
								<!-- �ϻ󰨻�ٰ� -->
								<td width="14%" class="tb_left_bg" >
									<select id="auditReason" name="auditReason" onchange="redirect(this.selectedIndex);" class="select_9pt" style="width: 100%;" >
									<%
										for(int i=1; i<16; i++){
											if ( i >= 10 ) {
									%>
										<option value="<%= i%>"><%= i%></option>
									<%
											} else {
									%>
										<option value="0<%= i%>"><%= i%></option>
									<%
											}
										}
									%>
									</select>
								</td>
								<!-- ���� -->
								<td width="14%" class="tb_left_bg" >
									<select id="elecType" name="elecType" onchange="redirect1(this.selectedIndex);" class="select_9pt" style="width: 100%;" >
									</select>
								</td>
								<!-- ���� -->
								<td width="14%" class="tb_left_bg" >
									<select id="construction" name="construction" onchange="redirect2(this.selectedIndex);" class="select_9pt" style="width: 100%;" >
									</select>
								</td>
								<!-- ���� -->
								<td width="14%" class="tb_left_bg" >
									<select id="auditType" name="auditType" class="select_9pt" style="width: 100%;">
									</select>
								</td>
								<!-- ��� �� ���ֱݾ� -->
								<td width="14%" class="tb_left_bg" >
									<input type="text" id="valueOrdered" name="valueOrdered" style="width: 100%;" value="<%=results.get("valueOrdered")%>" onKeyDown="javascript:checkNumber(this);"/>
								</td>
								<!-- ���躯�������� -->
								<td width="14%" class="tb_left_bg" >
									<input type="text" id="designChange" name="designChange" style="width: 100%;" value="<%=results.get("designChange")%>" onKeyDown="javascript:checkNumber(this);"/>
								</td>
								<!-- ������������ -->
								<td width="14%" class="tb_left_bg" >
									<input type="text" id="reduceBudget" name="reduceBudget" style="width: 100%;" value="<%=results.get("reduceBudget")%>" onKeyDown="javascript:checkNumber(this);"/>
								</td>
							</tr>
						</acube:tableFrame>
					</td>		
				</tr>
			</acube:tableFrame>
		</acube:outerFrame>
		
<script type="text/javascript">

	function closePopup(){
		window.close();
// 		opener.listRefreshFromList();
	}
	
   function setChecked(obj,setObj){
        if(obj.checked==true){
        	setObj.value="Y";
        }
        else{
        	setObj.value="N";
        }
    }	
	
	function saveEzis(mode){
		$("#mode").val(mode);
		
		$.ajaxSetup({async:false});
		$.post("<%=webUri%>/app/audit/send/SendEzisSave.do", $("#frmForm").serialize(), function(data){
			if (data.result == "success") {
				alert(data.message);
				opener.listRefreshFromList();
			} else {
				alert(data.message);
			}
		}, 'json');
	}
	
	/*�ι�°����Ʈ�ڽ�(����)*/
	var groups = document.forms['frmForm'].auditReason.options.length;
	var group = new Array(groups);
	
	for (i=0; i<groups; i++){
		group[i] = new Array();
	}
	
	group[0][0] = new Option("-", "0");
	group[1][0] = new Option("-", "0");
	group[2][0] = new Option("-", "0");
	group[3][0] = new Option("-", "0");
	group[4][0] = new Option("-", "0");
	group[5][0] = new Option("-", "0");
	
	group[6][0] = new Option("����", "0701");
	group[6][1] = new Option("���躯��", "0702");
	group[6][2] = new Option("��Ÿ", "0703");
	
	group[7][0] = new Option("-", "0");
	group[8][0] = new Option("-", "0");
	group[9][0] = new Option("-", "0");
	group[10][0] = new Option("-", "0");
	group[11][0] = new Option("-", "0");
	group[12][0] = new Option("-", "0");
	group[13][0] = new Option("-", "0");
	group[14][0] = new Option("-", "0");
	
	function redirect(x){
		
		var temp = document.forms['frmForm'].elecType;
		
		for(i=0;i<group[x].length;i++)
		{
			temp.options[i] = new Option(group[x][i].text,group[x][i].value);
		}
		
		temp.options[0].selected = true;
		
		redirect1(0);
	}
	
	/*����°����Ʈ�ڽ�(����)*/
	var secondGroups = document.forms['frmForm'].elecType.options.length;
	var secondGroup = new Array();
	
	for (i=0; i<groups; i++)  {
		secondGroup[i]=new Array();
		
		for (j=0; j<group[i].length; j++)  {
			secondGroup[i][j]=new Array();  
		}
	}
	
	secondGroup[0][0][0] = new Option("-", "0");
	secondGroup[1][0][0] = new Option("-", "0");
	secondGroup[2][0][0] = new Option("-", "0");
	secondGroup[3][0][0] = new Option("-", "0");
	secondGroup[4][0][0] = new Option("-", "0");
	secondGroup[5][0][0] = new Option("-", "0");
	
	secondGroup[6][0][0] = new Option("���ðǼ�", "070101");
	secondGroup[6][0][1] = new Option("�����Ǽ�", "070102");
	
	secondGroup[6][1][0] = new Option("���ðǼ�", "070201");
	secondGroup[6][1][1] = new Option("�����Ǽ�", "070202");
	
	secondGroup[6][2][0] = new Option("-", "0");
	
	secondGroup[7][0][0] = new Option("-", "0");
	secondGroup[8][0][0] = new Option("-", "0");
	secondGroup[9][0][0] = new Option("-", "0");
	secondGroup[10][0][0] = new Option("-", "0");
	secondGroup[11][0][0] = new Option("-", "0");
	secondGroup[12][0][0] = new Option("-", "0");
	secondGroup[13][0][0] = new Option("-", "0");
	secondGroup[14][0][0] = new Option("-", "0");
	
	function redirect1(z){
		var temp1=document.forms['frmForm'].construction;
		
		for (m=temp1.options.length-1;m>0;m--){
			temp1.options[m] = null;
		}
		
		for (i=0;i<secondGroup[document.forms[0].auditReason.options.selectedIndex][z].length;i++){
			temp1.options[i] = 
				new Option(secondGroup[document.forms['frmForm'].auditReason.options.selectedIndex][z][i].text,
						secondGroup[document.forms['frmForm'].auditReason.options.selectedIndex][z][i].value);
		}
		
		temp1.options[0].selected = true;
		redirect2(0);
	}
	
	/*�׹�° ����Ʈ�ڽ�(����) */
	var thirdGroups = document.forms['frmForm'].construction.options.length;
	var thirdGroup = new Array();
	
	for (i=0; i<groups; i++){
		thirdGroup[i] = new Array();
		
		for (j=0; j<group[i].length; j++){
			thirdGroup[i][j] = new Array();
			
			for (k=0; k<secondGroup[i][j].length; k++){
				thirdGroup[i][j][k] = new Array();
			}
		}
	}
	
	thirdGroup[0][0][0][0] = new Option("-", "0");
	thirdGroup[1][0][0][0] = new Option("-", "0");
	thirdGroup[2][0][0][0] = new Option("-", "0");
	thirdGroup[3][0][0][0] = new Option("-", "0");
	thirdGroup[4][0][0][0] = new Option("-", "0");
	thirdGroup[5][0][0][0] = new Option("-", "0");
	
	thirdGroup[6][0][0][0] = new Option("����", "07010101");
	thirdGroup[6][0][0][1] = new Option("�뿪", "07010102");
	
	thirdGroup[6][0][1][0] = new Option("����", "07010201");
	thirdGroup[6][0][1][1] = new Option("�뿪", "07010202");
	
	thirdGroup[6][1][0][0] = new Option("����", "07020101");
	thirdGroup[6][1][0][1] = new Option("�뿪", "07020102");
	
	thirdGroup[6][1][1][0] = new Option("����", "07020201");
	thirdGroup[6][1][1][1] = new Option("�뿪", "07020202");
	
	thirdGroup[6][2][0][0] = new Option("-", "0");
	
	thirdGroup[7][0][0][0] = new Option("-", "0");
	thirdGroup[8][0][0][0] = new Option("-", "0");
	thirdGroup[9][0][0][0] = new Option("-", "0");
	thirdGroup[10][0][0][0] = new Option("-", "0");
	thirdGroup[11][0][0][0] = new Option("-", "0");
	thirdGroup[12][0][0][0] = new Option("-", "0");
	thirdGroup[13][0][0][0] = new Option("-", "0");
	thirdGroup[14][0][0][0] = new Option("-", "0");
	
	
	function redirect2(a){
		var temp2=document.forms['frmForm'].auditType;
		for (m=temp2.options.length-1;m>0;m--){
			temp2.options[m] = null;
		}

		for (i=0;i<thirdGroup[document.forms['frmForm'].auditReason.options.selectedIndex][document.forms['frmForm'].elecType.options.selectedIndex][a].length;i++){
			temp2.options[i] = 
				new Option(thirdGroup[document.forms['frmForm'].auditReason.options.selectedIndex][document.forms['frmForm'].elecType.options.selectedIndex][a][i].text,
						thirdGroup[document.forms['frmForm'].auditReason.options.selectedIndex][document.forms['frmForm'].elecType.options.selectedIndex][a][i].value);
		}
		
		temp2.options[0].selected = true;
	}
	
	redirect(0);
	
	function rngType(){
		var thisForm = document.forms['frmForm'];
		thisForm.auditReason.options[6].selected=true;						
		redirect(6);			
		
	}           
	function commonType(flag,flag1,flag2,flag3){
		
		var thisForm = document.forms['frmForm'];			
		
		var flag = flag - 1;
		var flag1 = flag1.toString().substring(3, 4) - 1;
		var flag2 = flag2.toString().substring(5, 6) - 1;
		var flag3 = flag3.toString().substring(7, 8) - 1;
		
		thisForm.auditReason.options[flag].selected=true;						
		redirect(flag);			
		thisForm.elecType.options[flag1].selected=true;									
		redirect1(flag1);			
		thisForm.construction.options[flag2].selected=true;									
		redirect2(flag3);			
		thisForm.auditType.options[flag3].selected=true;									
					
	}   
	
	function checkNumber(obj){		
		var kc = event.keyCode;
	         if((kc < 48 || kc > 57) && (kc < 96 || kc > 105) && (kc != 8 && kc != 9) && (kc  != 189) && (kc  != 13) )
	         {                  
	                alert("<spring:message code='list.list.msg.insertOnlyNumber'/>");             
	                obj.focus();
	                window.event.returnValue = false;                
	         }
	}
</script>
		<input type="hidden" id="macAdd" name="macAdd" value="<%=macAdd%>">
		<input type="hidden" id="localIp" name="localIp" value="<%=localIp%>">
		<input type="hidden" id="docNo" name="docNo" value="<%=results.get("docNo")%>">
		<input type="hidden" id="docNumber" name="docNumber" value="<%=results.get("docNumber")%>">
		<input type="hidden" id="mode" name="mode">
	</form:form>
</center>

</body>
</html>