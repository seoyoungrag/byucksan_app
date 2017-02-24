<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.env.vo.OptionVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
/** 
 *  Class Name  : filemanager.jsp 
 *  Description : 파일OCX 처리 
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
<%
	String sftpSvr = AppConfig.getProperty("sftp_svr", "", "attach");
	String sftpPort = AppConfig.getProperty("sftp_port", "7775", "attach");
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	String cabVersion = AppConfig.getProperty("cab_version", "2,1,9,30", "attach");
	String limitExt = AppConfig.getProperty("limit_ext", "exe,jsp,asp", "attach");

	String attMode = "0";
	if((Boolean) session.getAttribute("IS_EXTWEB")) {
		sftpSvr = request.getServerName();
		sftpPort = Integer.toString(request.getServerPort());
		attMode = "1";
	}
	
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String opt330 = appCode.getProperty("OPT330", "OPT330", "OPT"); // 첨부제한
	OptionVO envOption = envOptionAPIService.selectOption(compId, opt330);
	int maxSize = 20 * 1024 * 1024;
	if ("Y".equals(envOption.getUseYn()) && envOption.getOptionValue() != null && !"".equals(envOption.getOptionValue())) {
	    maxSize = Integer.parseInt(envOption.getOptionValue()) * 1024 * 1024;
	} else {
	    maxSize = 0;
	}
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT");
	String aft010 = appCode.getProperty("AFT010", "AFT010", "AFT");
%>
<script type="text/javascript">
// Define FileManager Variables
var FileConst = {
		Error : {
			TransException	: "<spring:message code='fileconst.error.transexception'/>",
			NeedWizard	: "<spring:message code='fileconst.error.needwizard'/>",
			NeedBroker	: "<spring:message code='fileconst.error.needbroker'/>",
			Method		: "<spring:message code='fileconst.error.method'/>",
			NoPlaceToMove	: "<spring:message code='fileconst.error.noplacetomove'/>",
			UpgradeWizard	: "<spring:message code='fileconst.error.upgradewizard'/>",
			ProhibitedFile	: "<spring:message code='fileconst.error.prohibitedfile'/>(<spring:message code='fileconst.error.prohibitedext'/>:<%=limitExt%>)",
			NoAttachFileSelected : 	"<spring:message code='fileconst.error.noattachfileselected'/>",
			SelectOnlyOneAttach : 	"<spring:message code='fileconst.error.selectonlyoneattachfile'/>",
			ExceedLimitedSize : 	"<spring:message code='fileconst.error.exceedlimitedsize'/>",
			CannotDeleteFromBiz : "<spring:message code='fileconst.error.cannot.delete.frombiz'/>",
			ConfirmDeleteFromBiz : "<spring:message code='fileconst.confirm.delete.frombiz'/>"
		},

		Rsc : {
			BizAttach : "<spring:message code='fileconst.title.frombiz'/>",
			ModifyFileName : "<spring:message code='fileconst.title.modify.filename'/>"
		},
		
		FileType : {
			Attach : "<%=aft004%>",
			BizAttach : "<%=aft010%>",
			MaxSize : <%=maxSize%>
		},

		Variable : {
			Distribute : "N"
		}
};

// FileWizard Object initialize
function initializeFileManager() {		
	try {
		FileManager.initialize("<%=webUri%>", "<%=cabVersion%>", "<%=sftpSvr%>", "<%=sftpPort%>", "<%=uploadTemp%>", "<%=compId%>", "<%=limitExt%>", "<%=attMode%>");
	} catch (error) {
		FileManager.errormessage("initializeFileManager");
	}
}
</script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/filemanager.js"></script>