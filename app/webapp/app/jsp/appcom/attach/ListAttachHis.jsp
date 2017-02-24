<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.appcom.vo.FileHisVO" %>
<%
/** 
 *  Class Name  : ListAttachHis.jsp 
 *  Description : 첨부이력 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.11 
 *   수 정 자 : 허 주
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  허주
 *  @since 2011. 03. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%!
	String getAttachIcon(String filename) {
    	String extension = "";
    	
		try {
			extension = filename.substring(filename.lastIndexOf(".") + 1);
			if (!("bc".equals(extension) || "bmp".equals(extension) || "dl".equals(extension) || "doc".equals(extension) || "docx".equals(extension) || "exe".equals(extension)
				|| "gif".equals(extension) || "gul".equals(extension) || "htm".equals(extension) || "html".equals(extension) || "hwp".equals(extension)
				|| "ini".equals(extension) || "jpg".equals(extension)	|| "mgr".equals(extension) || "mpg".equals(extension) || "pdf".equals(extension)
				|| "ppt".equals(extension) || "pptx".equals(extension) || "print".equals(extension) || "report".equals(extension) || "tif".equals(extension) || "txt".equals(extension)
				|| "wav".equals(extension) || "xls".equals(extension) || "xlsx".equals(extension) || "xls02".equals(extension) || "xml".equals(extension) || "gif".equals(extension))) {
				extension = "etc";
			}
		} catch (Exception e) {
			extension = "etc";
		}
		return "/image/attach/attach_" + extension + ".gif";
	}
%>
<%
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디

	List<FileHisVO> fileHisVOs = (List) request.getAttribute("fileHis");

	// 버튼명
	String openBtn = messageSource.getMessage("approval.button.open", null, langType); 
	String saveBtn = messageSource.getMessage("approval.button.save", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.filehisinfo'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
	// 파일 ActiveX 초기화
	initializeFileManager();
}

function openAttach(fileid, filename, displayname) {
	var attachfile = new Object();
	attachfile.fileid = fileid;
	attachfile.filename = filename;
	attachfile.displayname = displayname;
	attachfile.gubun = "";
	attachfile.docid = "";
	attachfile.type = "open";
	
	var bodyfilepath = AttachManager.saveAttach(attachfile);
}

function saveAttach(fileid, filename, displayname) {
	var attachfile = new Object();
	attachfile.fileid = fileid;
	attachfile.filename = filename;
	attachfile.displayname = displayname;
	attachfile.gubun = "";
	attachfile.docid = "";
	attachfile.type = "save";
	
	var bodyfilepath = AttachManager.saveAttach(attachfile);
}

function closeAttahHis() {
	window.close();
}
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='approval.title.filehisinfo'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tableFrame class="table_grow">
					<tr bgcolor="#ffffff">
						<td width="10%" class="tb_tit_center"><spring:message code="approval.form.order" /></td>
						<td width="*" class="tb_tit_center"><spring:message code="approval.form.attachfilename" /></td>
					</tr>
<%
	if (fileHisVOs != null) {
	    int fileHisCount = fileHisVOs.size();
	    if (fileHisCount > 0) {
		    for (int loop = 0; loop < fileHisCount; loop++) {
				FileHisVO fileHisVO = fileHisVOs.get(loop);
				String fileId = fileHisVO.getFileId();
				String fileName = fileHisVO.getFileName();
				String displayName = EscapeUtil.escapeJavaScript(fileHisVO.getDisplayName());
				String open = "openAttach('" + fileId + "', '" + fileName + "', '" + displayName + "');return(false);";
				String save = "saveAttach('" + fileId + "', '" + fileName + "', '" + displayName + "');return(false);";
%>			
					<tr bgcolor="#ffffff">
						<td class="tb_center"><%=fileHisVO.getFileOrder()%></td>									
						<td class="tb_center" valign="top">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="85%">
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td>&nbsp;</td>
												<td width="16"><img src="<%=webUri%>/app/ref<%=getAttachIcon(fileHisVO.getFileName())%>"/></td>
												<td>&nbsp;<a href="#" onclick="<%=open%>"><%=fileHisVO.getDisplayName()%>(<%=Math.round(Math.ceil(fileHisVO.getFileSize() / 1024))%>KB)</a></td>
											</tr>
										</table>
									</td>
									<td width="15%" class="ltb_bg"">
										<acube:button onclick="<%=save%>" value="<%=saveBtn%>" type="4" class="gr" />
									</td>
								</tr>
							</table>
						</td>
					</tr>		
<%
	    	}
	    } else {
%>
					<tr bgcolor="#ffffff">
						<td colspan="2" class="tb_center"><spring:message code='approval.msg.notexist.filehisinfo'/></td>
					</tr>		
<%		
	    }
	} else {
%>	    
					<tr bgcolor="#ffffff">
						<td colspan="2" class="tb_center"><spring:message code='approval.msg.notexist.filehisinfo'/></td>
					</tr>		
<%	    
	}
%>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="closeAttahHis();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>