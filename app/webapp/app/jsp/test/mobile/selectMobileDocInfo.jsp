<%@ page contentType="text/html; charset=UTF-8" %>
<%
/** 
 *  Class Name  : selectMobileDocInfo.jsp 
 *  Description : 모바일 결재상세내용 조회 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  kj.yang
 *  @since 2012. 06. 18
 *  @version 1.0 
 *  @see
 */  
%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="javax.activation.DataHandler" %>
<%@ page import="java.io.*" %>
<%@ page import="com.sds.acube.app.mobile.ws.client.esbservice.AppLineVO"%>
<%@ page import="com.sds.acube.app.mobile.ws.client.esbservice.AppDetailVO"%>
<%@ page import="com.sds.acube.app.mobile.ws.client.esbservice.AppAttachVO"%>
<%@ page import="com.sds.acube.app.mobile.ws.client.esbservice.EnfLineVO"%>
<%@ page import="com.sds.acube.app.mobile.ws.client.esbservice.Files"%>
<%@ page import="com.sds.acube.app.mobile.ws.client.esbservice.File"%>
<%
	String itemCode = CommonUtil.nullTrim(request.getParameter("itemCode"));
	String listType = CommonUtil.nullTrim(request.getParameter("listType"));

	AppDetailVO detailVO = (AppDetailVO)request.getAttribute("SelectDocInfo");
	//AppAttachVO attachVO = (AppAttachVO)request.getAttribute("AttachList");
	
	List<AppLineVO>  appline = detailVO.getAppline();	//결재경로	
	List<EnfLineVO>  enfline = detailVO.getEnfline();	//보고경로	
	Files filesVO = detailVO.getContent();				//파일정보	
	
	
	String docId = detailVO.getDocid();
	Files content = detailVO.getContent();
	
	File fileVO = new File();
	int fileSize = filesVO.getFile().size();
	StringBuffer sb = new StringBuffer();
	String attachFiles = "";
				
	for(int i=0; i<fileSize; i++){	   
	    fileVO = filesVO.getFile().get(i);
	    
	   	if(fileVO.getFileType() != null){
	  
		   	if(fileVO.getFileType().equals("body")) {
				if(fileVO.getContent() != null) {
				    DataHandler dh = fileVO.getContent();
				    InputStream is = dh.getInputStream();
				    BufferedReader bf = new BufferedReader(new InputStreamReader(is, "EUC-KR")); //UTF-8		   
				    
				    String lineTxt = "";
					
					while ((lineTxt = bf.readLine()) != null) {			
					    sb.append(lineTxt);
					}				
				}
			    
		    }else {				
		    	attachFiles += "<a href=\"javascript:attachFile('"+ fileVO.getFileId() +"');\">"+fileVO.getFileName() + "</a><br>";
		    }
	   	}
	    
	}
%>



<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title></title>
		<link rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css" type="text/css">
		<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
		<jsp:include page="/app/jsp/common/common.jsp" />
		<jsp:include page="/app/jsp/common/filemanager.jsp" />
		<script type="text/javascript">
			function processApp(code){
				var codeName ="승인";
				if(code =="A2"){
					codeName ="반려";
				}else if(code =="A3"){
					codeName ="찬성";
				}else if(code =="A4"){
					codeName ="반대";
				}
				if(confirm(codeName+"에 대하여 실행하시겠습니까?")){
					var form = document.forms[0];				
					form.actionCode.value = code;			
					form.action ="processAppMobile.do";
					form.submit();
				}else{
					return;
				}				
			}
			
			function attachFile(fileId) {
				var form = document.forms[0];
				form.action ="attachFileMobile.do?docId=<%=docId%>&fileId="+fileId;
				form.submit();		
			}


			function processApproval(code) {
				var codeName ="승인";
				if(code =="A2"){
					codeName ="반려";
				}else if(code =="A3"){
					codeName ="찬성";
				}else if(code =="A4"){
					codeName ="반대";
				}
				if(confirm(codeName+"에 대하여 실행하시겠습니까?")){
					var form = document.forms[0];				
					form.actionCode.value = code;			
					$.post("<%=webUri%>/app/mobile/processMobileApproval.do", $("#mobileform").serialize(), function(data){
						alert(data.result);
					}, 'json').error(function(data) {
						alert("실패!!!!");
					});
				}else{
					return;
				}				
			}

			
		</script>
	</head>
	<body  leftmargin="0" topmargin="20" marginwidth="0" marginheight="0">
		<form name="mobileform" id="mobileform" method="post">
			<input type="hidden" name="section" id="section" value="result"/>
			<input type="hidden" name="docId" id="docId" value="<%=docId%>"/>
			<input type="hidden" name="actionCode" id="actionCode"/>
			<input type="hidden" name="opinion" id="opinion" value="결재의견"/>
			<input type="hidden" name="password" id="password" value="xygate"/>
		</form>
		
		<p align="center" style="font-size:17px;">문서상세조회</p>		
		<p align="right" style="font-size:14px;">		
		<%
		if(listType.equals("OPT103")){
		%>
			결재 코드 - A1 : 승인, A2 : 반려, A3 : 찬성,  A4 : 반대 &nbsp; &nbsp; &nbsp; &nbsp;
		<%
			if(itemCode.equals("APP360") || itemCode.equals("APP362") || itemCode.equals("APP365")){
		%>
			<A href="javascript:processApproval('A3')">찬성(큐)</A>
			<A href="javascript:processApproval('A4')">반대(큐)</A>
			<A href="javascript:processApp('A3')">찬성</A>
			<A href="javascript:processApp('A4')">반대</A>
		<%		
			}else{
		%>
			<A href="javascript:processApproval('A1')">승인(큐)</A>
			<A href="javascript:processApproval('A2')">반려(큐)</A>
			<A href="javascript:processApp('A1')">승인</A>
			<A href="javascript:processApp('A2')">반려</A>
		<%	
			}
		}
		%>
			<A href="javascript:history.back()">목록</A>
		</p>		
		<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table" align="center">
		
			<tr bgcolor='#ffffff'>
				<td width="20%" class="ltb_center">제목</td>
				<td  class="ltb_left"><%=detailVO.getTitle() %></td>
			</tr>
			<tr bgcolor='#ffffff' height="100%">
				<td class="ltb_center">결재경로</td>
				<td class="ltb_left">
					<table width="95%" border="0" cellpadding="0" cellspacing="0">						
			<%			
				if (appline != null) {
					AppLineVO appLineVO = new AppLineVO();	    
					
	    			for (int i=0; i< appline.size(); i++) {
						appLineVO = appline.get(i);
						
						String appAskMess = "";
						String appDetail = appLineVO.getApproverName();
						if(!appLineVO.getAskType().equals("")) {
							appAskMess = messageSource.getMessage("mobile.asktype." + appLineVO.getAskType().toLowerCase(), null, langType);
						}
						
						if(!appLineVO.getProcType().equals("")){
							appDetail = appDetail + "&nbsp;" + messageSource.getMessage("mobile.proctype." +  appLineVO.getProcType().toLowerCase(), null, langType);
						}
						
						if(!(appLineVO.getProcessDate().equals("9999-12-31 23:59:59") || appLineVO.getProcessDate().equals(""))) {
							appDetail = appDetail + "(" + appLineVO.getProcessDate() + ")";
						}				
					
			%> 
						<tr height="40">
							<td class="ltb_left" width="15%" >
								<%=appAskMess %>								
							</td>
							<td class="ltb_left">
								<%=appDetail %> <%=appLineVO.getProcOpinion() %>
							</td>							
						</tr>
			<%
				    }
				}
			%>						
					</table>
				</td>
			</tr>
			<tr bgcolor='#ffffff' height="100%">
				<td class="ltb_center">보고경로</td>
				<td class="ltb_left">
					<table width="95%" border="0" cellpadding="0" cellspacing="0">
			<%
				if (enfline != null) {
				    EnfLineVO enfLineVO = new EnfLineVO();
				    
				    for (int i=0; i<enfline.size(); i++) {
						enfLineVO = enfline.get(i);
						
						String envAskMess = "";
						String envDetail = enfLineVO.getProcessorName();
						
						if(!enfLineVO.getAskType().equals("")) {
							envAskMess = messageSource.getMessage("mobile.asktype." + enfLineVO.getAskType().toLowerCase(), null, langType);
						}
						
						if(!enfLineVO.getProcType().equals("")){
							envDetail = envDetail + "&nbsp;" + messageSource.getMessage("mobile.proctype." +  enfLineVO.getProcType().toLowerCase(), null, langType);
						}
						
						if(!(enfLineVO.getProcessDate().equals("9999-12-31 23:59:59") || enfLineVO.getProcessDate().equals(""))) {
							envDetail = envDetail + "(" + enfLineVO.getProcessDate() + ")";
						}
						
						
			%>
						<tr>
							<td class="ltb_left" width="15%"><%=envAskMess %></td>
							<td class="ltb_left"><%=envDetail%></td>
						</tr>
			<%
				    }
				}
			
			%>			
					</table>	
				</td>
			</tr>
			<tr bgcolor='#ffffff' height="100%">
				<td class="ltb_center">첨부파일</td>
				<td class="ltb_left"><%=attachFiles %></td>
			</tr>
			<tr bgcolor='#ffffff'>
				<td colspan="2" align="center">
					<table>
						<tr><td><%=sb.toString() %></td></tr>
					</table>
				</td>
			</tr>			
		</table>
	</body>
</html>