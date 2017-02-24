<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil" %>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil" %>
<%@page import="com.sds.acube.app.appcom.vo.FileVO"%>
<%@page import="com.sds.acube.app.mig.vo.MigVO"%>
<%@page import="com.sds.acube.app.mig.vo.MigFileVO"%>
<%@page import="com.sds.acube.app.mig.vo.ApprInfoVO"%>
<%@page import="com.sds.acube.app.mig.vo.ApproverInfoVO"%>
<%@page import="com.sds.acube.app.mig.vo.AttachInfoVO"%>
<%@page import="com.sds.acube.app.mig.vo.DeliveresInfoVO"%>
<%@page import="com.sds.acube.app.mig.vo.DocInfoVO"%>
<%@page import="com.sds.acube.app.mig.vo.DraftInfoVO"%>
<%@page import="com.sds.acube.app.mig.vo.RecipientsInfoVO"%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil" %>
<%@ page import="com.sds.acube.app.common.util.AppConfig" %>
<%@ page import="com.sds.acube.app.common.util.AppCode" %>
<%@ page import="com.sds.acube.app.common.util.MemoryUtil" %>
<%@page import="java.util.List"%>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/js/jquery.js"/></script>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/js/uuid.js"/></script>
<%
String webUri = AppConfig.getProperty("web_uri", "/ep", "path"); 
List<MigFileVO> fileList = (List<MigFileVO>)request.getAttribute("filelist"); //클라이언트에게 넘겨주기위한 파일리스트
String dataurl = (String) request.getAttribute("dataurl"); //3.0에서 사용하던 파라메터
String cabinet = (String) request.getAttribute("cabinet"); //3.0에서 사용하던 파라메터
String docstatus = (String) request.getAttribute("docstatus"); //3.0에서 사용하던 파라메터
String linename = (String) request.getAttribute("linename"); //3.0에서 사용하던 파라메터
String serialorder = (String) request.getAttribute("serialorder"); //3.0에서 사용하던 파라메터
String bodytype = (String) request.getAttribute("bodytype"); //3.0에서 사용하던 파라메터
List<ApprInfoVO> apprList = (List<ApprInfoVO>)request.getAttribute("apprList");
List<ApproverInfoVO> approverList = (List<ApproverInfoVO>)request.getAttribute("approverList");
List<AttachInfoVO> attachList = (List<AttachInfoVO>)request.getAttribute("attachList");
List<DeliveresInfoVO> delivereList = (List<DeliveresInfoVO>)request.getAttribute("deliverList");
DocInfoVO docInfo = (DocInfoVO)request.getAttribute("docInfo");
DraftInfoVO draftInfo = (DraftInfoVO)request.getAttribute("draftInfo");
List<RecipientsInfoVO> recipientList = (List<RecipientsInfoVO>)request.getAttribute("recipeintList");
List<FileVO> fileVOs = (List<FileVO>)request.getAttribute("fileVOs");
AppCode appCode = MemoryUtil.getCodeInstance();
String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
%>
<HTML>


<XML id="InputConfig">
	<INPUT_CONFIG>

		<INPUT_METHOD>0</INPUT_METHOD>
		<INPUT_FORMAT>0</INPUT_FORMAT>

	</INPUT_CONFIG>
</XML>



<XML id="ApprovalDoc">

<?xml version="1.0"?>
<APPROVAL_DOC>
<%if(docInfo != null){ %>
	<DOC_INFO>
		<DOC_ID><%out.print(docInfo.getDocId());%></DOC_ID>
		<IS_FORM><%out.print(docInfo.getIsForm());%></IS_FORM>
		<IS_BATCH><%out.print(docInfo.getIsBatch());%></IS_BATCH>
		<IS_OPEN>Y</IS_OPEN>
		<KEEP_STATUS><%out.print(docInfo.getKeepStatus());%></KEEP_STATUS>
		<KEEP_DATE><%out.print(docInfo.getKeepDate());%></KEEP_DATE>
		<BODY_TYPE><%out.print(docInfo.getBodyType());%></BODY_TYPE>
		<ORG_SYMBOL><![CDATA[<%out.print(docInfo.getOrgSymbol());%>]]></ORG_SYMBOL>
		<CLASS_NUMBER><%out.print(docInfo.getClassNumber());%></CLASS_NUMBER>
		<CLASS_NUMBER_ID><%out.print(docInfo.getClassNumberId());%></CLASS_NUMBER_ID>
		<SERIAL_SEED><%out.print(docInfo.getSerialSeed());%></SERIAL_SEED>
		<SERIAL_NUMBER><%out.print(docInfo.getSerialNumber());%></SERIAL_NUMBER>
		<REGISTRY_TYPE><%out.print(docInfo.getRegistryType());%></REGISTRY_TYPE>
		<SECURITY_PASS><%out.print(docInfo.getSecurityPass());%></SECURITY_PASS>
		<SECURITY_LEVEL><%out.print(docInfo.getSecurityLevel());%></SECURITY_LEVEL>
		<ACCESS_LEVEL_CODE><%out.print(docInfo.getAccessLevelCode());%></ACCESS_LEVEL_CODE>
		<ACCESS_LEVEL><%out.print(docInfo.getAccessLevel());%></ACCESS_LEVEL>
		<URGENCY><%out.print(docInfo.getUrgency());%></URGENCY>
		<PUBLIC_LEVEL><![CDATA[<%out.print(docInfo.getPublicLevel());%>]]></PUBLIC_LEVEL>
		<RESTRICTED_PAGE><%out.print(docInfo.getRestrictedPage());%></RESTRICTED_PAGE>
		<IS_DIRECT><%out.print(docInfo.getIsDirect());%></IS_DIRECT>
		<IS_ATTACHED><%out.print(docInfo.getIsAttached());%></IS_ATTACHED>
		<DRAFT_CONSERVE_CODE><%out.print(docInfo.getDraftConserveCode());%></DRAFT_CONSERVE_CODE>
		<DRAFT_CONSERVE><%out.print(docInfo.getDraftConserve());%></DRAFT_CONSERVE>
		<ENFORCE_CONSERVE_CODE><%out.print(docInfo.getEnforceConserveCode());%></ENFORCE_CONSERVE_CODE>
		<ENFORCE_CONSERVE><%out.print(docInfo.getEnforceConserve());%></ENFORCE_CONSERVE>
		<ENFORCE_METHOD><%out.print(docInfo.getEnforceMethod());%></ENFORCE_METHOD>
		<FLOW_STATUS><%out.print(docInfo.getFlowStatus());%></FLOW_STATUS>
		<IS_CONVERTED><%out.print(docInfo.getIsConverted());%></IS_CONVERTED>
		<IS_POST><%out.print(docInfo.getIsPost());%></IS_POST>
		<ENFORCE_FORM_ID><%out.print(docInfo.getEnforceFormId());%></ENFORCE_FORM_ID>
		<SENDER_DOC_ID><%out.print(docInfo.getSenderDocId());%></SENDER_DOC_ID>
		<DIST_DOC_ID><%out.print(docInfo.getDistDocId());%></DIST_DOC_ID>
		<DISTRIBUTE_SEED><%out.print(docInfo.getDistributeSeed());%></DISTRIBUTE_SEED>
		<DISTRIBUTE_NUMBER></DISTRIBUTE_NUMBER>
		<RECEIVE_SEED><%out.print(docInfo.getReceiveSeed());%></RECEIVE_SEED>
		<RECEIVE_NUMBER><%out.print(docInfo.getReceiveNumber());%></RECEIVE_NUMBER>
		<ANNOUNCEMENT_STATUS>0</ANNOUNCEMENT_STATUS>
		<SCHEMA_VERSION><%out.print(docInfo.getSchemaVersion());%></SCHEMA_VERSION>
		<DRAFT_ORG_CODE><![CDATA[<%out.print(docInfo.getDraftOrgCode());%>]]></DRAFT_ORG_CODE>
		<DRAFT_ORG_NAME><![CDATA[<%out.print(docInfo.getDraftOrgName());%>]]></DRAFT_ORG_NAME>
		<ENFORCE_ORG_CODE><![CDATA[<%out.print(docInfo.getEnforceOrgCode());%>]]></ENFORCE_ORG_CODE>
		<ENFORCE_ORG_NAME><![CDATA[<%out.print(docInfo.getEnforceOrgName());%>]]></ENFORCE_ORG_NAME>
		<SENDER><![CDATA[<%out.print(docInfo.getSender());%>]]></SENDER>
		<RECEIVER><![CDATA[<%out.print(docInfo.getReceiver());%>]]></RECEIVER>
		<SEND_DATE><%out.print(docInfo.getSendDate());%></SEND_DATE>
		<RECV_DATE><%out.print(docInfo.getRecvDate());%></RECV_DATE>
		<ACCEPTOR_ID><%out.print(docInfo.getAcceptorId());%></ACCEPTOR_ID>
		<ACCEPTOR_NAME><![CDATA[<%out.print(docInfo.getAcceptorName());%>]]></ACCEPTOR_NAME>
		<CHARGER_ID><%out.print(docInfo.getChargerId());%></CHARGER_ID>
		<CHARGER_NAME><![CDATA[<%out.print(docInfo.getChargerName());%>]]></CHARGER_NAME>
		<IS_PUBDOC><%out.print(docInfo.getIsPubdoc());%></IS_PUBDOC>
		<IS_ADMIN_MIS><%out.print(docInfo.getIsAdminMis());%></IS_ADMIN_MIS>
		<DRAFT_PROC_DEPT_CODE><![CDATA[<%out.print(docInfo.getDraftProcDeptCode());%>]]></DRAFT_PROC_DEPT_CODE>
		<DRAFT_PROC_DEPT_NAME><![CDATA[<%out.print(docInfo.getDraftProcDeptName());%>]]></DRAFT_PROC_DEPT_NAME>
		<ENFORCE_PROC_DEPT_CODE><![CDATA[<%out.print(docInfo.getEnforceProcDeptCode());%>]]></ENFORCE_PROC_DEPT_CODE>
		<ENFORCE_PROC_DEPT_NAME><![CDATA[<%out.print(docInfo.getEnforceProcDeptName());%>]]></ENFORCE_PROC_DEPT_NAME>
		<DRAFT_ARCHIVE_ID><%out.print(docInfo.getDraftArchiveId());%></DRAFT_ARCHIVE_ID>
		<DRAFT_ARCHIVE_NAME><![CDATA[<%out.print(docInfo.getDraftArchiveName());%>]]></DRAFT_ARCHIVE_NAME>
		<ENFORCE_ARCHIVE_ID><%out.print(docInfo.getEnforceArchiveId());%></ENFORCE_ARCHIVE_ID>
		<ENFORCE_ARCHIVE_NAME><![CDATA[<%out.print(docInfo.getEnforceArchiveName());%>]]></ENFORCE_ARCHIVE_NAME>
		<FORM_USAGE><%out.print(docInfo.getFormUsage());%></FORM_USAGE>
		<APPR_BIZ_ID><%out.print(docInfo.getApprBizId());%></APPR_BIZ_ID>
		<APPR_BIZ_NAME><![CDATA[<%out.print(docInfo.getApprBizName());%>]]></APPR_BIZ_NAME>
		<IS_MODIFIABLE><%out.print(docInfo.getIsModifiable());%></IS_MODIFIABLE>
		<MODIFY_DATE><%out.print(docInfo.getModifyDate());%></MODIFY_DATE>
	</DOC_INFO>
<%}else{ %>
	<DOC_INFO/>
<%} %>
<%if(draftInfo != null){ %>
	<DRAFT_INFOS>
		<DRAFT_INFO>
			<CASE_NUMBER><%out.print(draftInfo.getCaseNumber()); %></CASE_NUMBER>
			<TITLE><![CDATA[<%out.print(draftInfo.getTitle()); %>]]></TITLE>
			<DOC_CATEGORY><%out.print(draftInfo.getDocCategory()); %></DOC_CATEGORY>
			<ENFORCE_BOUND><%out.print(draftInfo.getEnforceBound()); %></ENFORCE_BOUND>
			<SENDER_AS_CODE><%out.print(draftInfo.getSenderAsCode()); %></SENDER_AS_CODE>
			<SENDER_AS><%out.print(draftInfo.getSenderAs()); %></SENDER_AS>
			<TREATMENT><![CDATA[<%out.print(draftInfo.getTreatment()); %>]]></TREATMENT>
		</DRAFT_INFO>
	</DRAFT_INFOS>
<%}else{ %>
	<DRAFT_INFOS/>
<%} %>
<%if(attachList != null){ %>
	<FILE_INFO>
		<FILE_BODIES>
		<%for(int pos = 0 ; pos < attachList.size(); pos ++){ 
			if(attachList.get(pos).getAttachType().equals("0")){%>
			<FILE_BODY METHOD="" MODIFY="">
				<DISPLAY_NAME><![CDATA[<%out.print(attachList.get(pos).getDisplayName());%>]]></DISPLAY_NAME>
				<FILE_NAME><%out.print(attachList.get(pos).getFileName());%></FILE_NAME>
				<FILE_SIZE><%out.print(attachList.get(pos).getFileSize());%></FILE_SIZE>
				<PAGE_COUNT><%out.print(attachList.get(pos).getPageCount());%></PAGE_COUNT>
				<IMAGE_WIDTH><%out.print(attachList.get(pos).getImageWidth());%></IMAGE_WIDTH>
				<IMAGE_HEIGHT><%out.print(attachList.get(pos).getImageHeight());%></IMAGE_HEIGHT>
				<LOCATION><%out.print(attachList.get(pos).getLocation());%></LOCATION>
			</FILE_BODY>
		<%	}
		} %>
		</FILE_BODIES>
		<RELATED_ATTACHES>
		<%for(int pos = 0 ; pos < attachList.size(); pos ++){ 
			if(attachList.get(pos).getAttachType().equals("1")){%> 
			<RELATED_ATTACH METHOD="" MODIFY="">
				<DISPLAY_NAME><![CDATA[<%out.print(attachList.get(pos).getDisplayName());%>]]></DISPLAY_NAME>
				<FILE_NAME><%out.print(attachList.get(pos).getFileName());%></FILE_NAME>
				<FILE_SIZE><%out.print(attachList.get(pos).getFileSize());%></FILE_SIZE>
				<PAGE_COUNT><%out.print(attachList.get(pos).getPageCount());%></PAGE_COUNT>
				<IMAGE_WIDTH><%out.print(attachList.get(pos).getImageWidth());%></IMAGE_WIDTH>
				<IMAGE_HEIGHT><%out.print(attachList.get(pos).getImageHeight());%></IMAGE_HEIGHT>
				<LOCATION><%out.print(attachList.get(pos).getLocation());%></LOCATION>
				<CLASSIFY><%out.print(attachList.get(pos).getClassify());%></CLASSIFY>
				<SUBDIV><%out.print(attachList.get(pos).getSubdiv());%></SUBDIV>
				<ENFORCE_BOUND><%out.print(attachList.get(pos).getEnforceBound());%></ENFORCE_BOUND>
			</RELATED_ATTACH>
		<%	}
		} %>
		</RELATED_ATTACHES>
		<%for(int pos = 0 ; pos < attachList.size(); pos ++){ 
			if(attachList.get(pos).getAttachType().equals("2")){%>
		<GENERAL_ATTACHES>
		<%	break;}
		} %>
		<%for(int pos = 0 ; pos < attachList.size(); pos ++){ 
			if(attachList.get(pos).getAttachType().equals("2")){%>
			<GENERAL_ATTACH METHOD="" MODIFY="">
				<CASE_NUMBER><%out.print(attachList.get(pos).getCaseNumber());%></CASE_NUMBER>
				<DISPLAY_NAME><![CDATA[<%out.print(attachList.get(pos).getDisplayName());%>]]></DISPLAY_NAME>
				<FILE_NAME><%out.print(attachList.get(pos).getFileName());%></FILE_NAME>
				<FILE_SIZE><%out.print(attachList.get(pos).getFileSize());%></FILE_SIZE>
				<PAGE_COUNT><%out.print(attachList.get(pos).getPageCount());%></PAGE_COUNT>
				<IMAGE_WIDTH><%out.print(attachList.get(pos).getImageWidth());%></IMAGE_WIDTH>
				<IMAGE_HEIGHT><%out.print(attachList.get(pos).getImageHeight());%></IMAGE_HEIGHT>
				<LOCATION><%out.print(attachList.get(pos).getLocation());%></LOCATION>
			</GENERAL_ATTACH>
		<%	}
		} %>
		<%for(int pos = 0 ; pos < attachList.size(); pos ++){ 
			if(attachList.get(pos).getAttachType().equals("2")){%>
		</GENERAL_ATTACHES>
		<%	break;}
		} %>
	</FILE_INFO>
<%}else{ %>
	<FILE_INFO/>
<%} %>
<%if(apprList != null){ %>
	<APPROVAL_LINES>
		<%for(int pos = 0 ; pos < apprList.size(); pos ++){ 
		if(apprList.get(pos).getLineName().equals(linename)){%>
		<APPROVAL_LINE IS_ACTIVE="Y">
		<%}else{ %>
		<APPROVAL_LINE IS_ACTIVE="N">
		<%} %>
			<LINE_NAME><%out.print(apprList.get(pos).getLineName());%></LINE_NAME>
			<DOC_STATUS><%out.print(apprList.get(pos).getDocStatus());%></DOC_STATUS>
			<LINE_TYPE><%out.print(apprList.get(pos).getLineType());%></LINE_TYPE>
			<CURRENT_SERIAL><%out.print(apprList.get(pos).getCurrentSerial());%></CURRENT_SERIAL>
			<CURRENT_PARALLEL><%out.print(apprList.get(pos).getCurrentParallel());%></CURRENT_PARALLEL>
			<CURRENT_ID><%out.print(apprList.get(pos).getCurrentId());%></CURRENT_ID>
			<CURRENT_NAME><![CDATA[<%out.print(apprList.get(pos).getCurrentName());%>]]></CURRENT_NAME>
			<CURRENT_ROLE><%out.print(apprList.get(pos).getCurrentRole());%></CURRENT_ROLE>
			<DRAFTER_ID><%out.print(apprList.get(pos).getDrafterId());%></DRAFTER_ID>
			<DRAFTER_NAME><![CDATA[<%out.print(apprList.get(pos).getDrafterName());%>]]></DRAFTER_NAME>
			<DRAFTER_POSITION><![CDATA[<%out.print(apprList.get(pos).getDrafterPosition());%>]]></DRAFTER_POSITION>
			<DRAFTER_POSITION_ABBR><![CDATA[<%out.print(apprList.get(pos).getDrafterPositionAbbr());%>]]></DRAFTER_POSITION_ABBR>
			<DRAFTER_LEVEL><![CDATA[<%out.print(apprList.get(pos).getDrafterLevel());%>]]></DRAFTER_LEVEL>
			<DRAFTER_LEVEL_ABBR><![CDATA[<%out.print(apprList.get(pos).getDrafterLevelAbbr());%>]]></DRAFTER_LEVEL_ABBR>
			<DRAFTER_DUTY><![CDATA[<%out.print(apprList.get(pos).getDrafterDuty());%>]]></DRAFTER_DUTY>
			<DRAFTER_TITLE><![CDATA[<%out.print(apprList.get(pos).getDrafterTitle());%>]]></DRAFTER_TITLE>
			<DRAFT_DEPT_NAME><![CDATA[<%out.print(apprList.get(pos).getDraftDeptName());%>]]></DRAFT_DEPT_NAME>
			<DRAFT_DEPT_CODE><![CDATA[<%out.print(apprList.get(pos).getDraftDeptCode());%>]]></DRAFT_DEPT_CODE>
			<DRAFT_DATE><%out.print(apprList.get(pos).getDraftDate());%></DRAFT_DATE>
			<CHIEF_ID><%out.print(apprList.get(pos).getChiefId());%></CHIEF_ID>
			<CHIEF_NAME><![CDATA[<%out.print(apprList.get(pos).getChiefName());%>]]></CHIEF_NAME>
			<CHIEF_ROLE><%out.print(apprList.get(pos).getChiefRole());%></CHIEF_ROLE>
			<COMP_DATE><%out.print(apprList.get(pos).getCompDate());%></COMP_DATE>
			<%for(int innerPos = 0 ; innerPos < approverList.size(); innerPos++){ 
				if(approverList.get(innerPos).getLineName().equals(linename)){%>
			<APPROVER IS_ACTIVE="N">
				<SERIAL_ORDER><%out.print(approverList.get(innerPos).getSerialOrder());%></SERIAL_ORDER>
				<PARALLEL_ORDER><%out.print(approverList.get(innerPos).getParallelOrder());%></PARALLEL_ORDER>
				<USER_ID><%out.print(approverList.get(innerPos).getUserId());%></USER_ID>
				<USER_NAME><![CDATA[<%out.print(approverList.get(innerPos).getUserName());%>]]></USER_NAME>
				<USER_POSITION><![CDATA[<%out.print(approverList.get(innerPos).getUserPosition());%>]]></USER_POSITION>
				<USER_POSITION_ABBR><![CDATA[<%out.print(approverList.get(innerPos).getUserPositionAbbr());%>]]></USER_POSITION_ABBR>
				<USER_LEVEL><![CDATA[<%out.print(approverList.get(innerPos).getUserLevel());%>]]></USER_LEVEL>
				<USER_LEVEL_ABBR><![CDATA[<%out.print(approverList.get(innerPos).getUserLevelAbbr());%>]]></USER_LEVEL_ABBR>
				<USER_DUTY><![CDATA[<%out.print(approverList.get(innerPos).getUserDuty());%>]]></USER_DUTY>
				<USER_TITLE><![CDATA[<%out.print(approverList.get(innerPos).getUserTitle());%>]]></USER_TITLE>
				<COMPANY><![CDATA[<%out.print(approverList.get(innerPos).getCompany());%>]]></COMPANY>
				<DEPT_NAME><![CDATA[<%out.print(approverList.get(innerPos).getDeptName());%>]]></DEPT_NAME>
				<DEPT_CODE><![CDATA[<%out.print(approverList.get(innerPos).getDeptCode());%>]]></DEPT_CODE>
				<IS_SIGNED><%out.print(approverList.get(innerPos).getIsSigned());%></IS_SIGNED>
				<SIGN_DATE><%out.print(approverList.get(innerPos).getSignDate());%></SIGN_DATE>
				<SIGN_FILE_NAME><%out.print(approverList.get(innerPos).getSignFileName());%></SIGN_FILE_NAME>
				<APPROVER_ROLE><%out.print(approverList.get(innerPos).getApproverRole());%></APPROVER_ROLE>
				<IS_OPEN><%out.print(approverList.get(innerPos).getIsOpen());%></IS_OPEN>
				<APPROVER_ACTION><%out.print(approverList.get(innerPos).getApproverAction());%></APPROVER_ACTION>
				<APPROVER_TYPE><%out.print(approverList.get(innerPos).getAdditionalRole());%></APPROVER_TYPE>
				<ADDITIONAL_ROLE><%out.print(approverList.get(innerPos).getKeepStatus());%></ADDITIONAL_ROLE>
				<KEEP_STATUS><%out.print(approverList.get(innerPos).getKeepStatus());%></KEEP_STATUS>
				<KEEP_DATE><%out.print(approverList.get(innerPos).getKeepDate());%></KEEP_DATE>
				<EMPTY_REASON><![CDATA[<%out.print(approverList.get(innerPos).getEmptyReason());%>]]></EMPTY_REASON>
				<IS_DOC_MODIFIED><%out.print(approverList.get(innerPos).getIsDocModified());%></IS_DOC_MODIFIED>
				<IS_LINE_MODIFIED><%out.print(approverList.get(innerPos).getIsLineModified());%></IS_LINE_MODIFIED>
				<IS_ATTACH_MODIFIED><%out.print(approverList.get(innerPos).getIsAttachModified());%></IS_ATTACH_MODIFIED>
				<OPINION><![CDATA[<%out.print(CommonUtil.nullTrim(approverList.get(innerPos).getOpinion()));%>]]></OPINION>
				<REP_ID><%out.print(approverList.get(innerPos).getRepId());%></REP_ID>
				<REP_NAME><![CDATA[<%out.print(approverList.get(innerPos).getRepName());%>]]></REP_NAME>
				<REP_POSITION><![CDATA[<%out.print(approverList.get(innerPos).getRepPosition());%>]]></REP_POSITION>
				<REP_POSITION_ABBR><![CDATA[<%out.print(approverList.get(innerPos).getRepPositionAbbr());%>]]></REP_POSITION_ABBR>
				<REP_LEVEL><![CDATA[<%out.print(approverList.get(innerPos).getRepLevel());%>]]></REP_LEVEL>
				<REP_LEVEL_ABBR><![CDATA[<%out.print(approverList.get(innerPos).getRepLevelAbbr());%>]]></REP_LEVEL_ABBR>
				<REP_DUTY><![CDATA[<%out.print(approverList.get(innerPos).getRepDuty());%>]]></REP_DUTY>
				<REP_TITLE><![CDATA[<%out.print(approverList.get(innerPos).getRepTitle());%>]]></REP_TITLE>
				<REP_COMPANY><![CDATA[<%out.print(approverList.get(innerPos).getRepCompany());%>]]></REP_COMPANY>
				<REP_DEPT_NAME><![CDATA[<%out.print(approverList.get(innerPos).getRepDeptName());%>]]></REP_DEPT_NAME>
				<REP_DEPT_CODE><![CDATA[<%out.print(approverList.get(innerPos).getRepDeptCode());%>]]></REP_DEPT_CODE>
			</APPROVER>
				
			<%		}//inner if
				}//inner for
				%>
		</APPROVAL_LINE>
				<%
			}//outer for
			%>
	</APPROVAL_LINES>
<%}else{ %>
	<APPROVAL_LINES/>
<%} %>
	<%if(delivereList!= null && delivereList.size()!=0){%>
	<DELIVERERS>
		<%for(int pos = 0 ; pos < delivereList.size(); pos ++){
			%>
		<DELIVERER>
			<DELIVERER_TYPE><%out.print(delivereList.get(pos).getDelivererType()); %></DELIVERER_TYPE>
			<USER_ID><%out.print(delivereList.get(pos).getUserId()); %></USER_ID>
			<USER_NAME><![CDATA[<%out.print(delivereList.get(pos).getUserName()); %>]]></USER_NAME>
			<USER_POSITION><![CDATA[<%out.print(delivereList.get(pos).getUserPosition()); %>]]></USER_POSITION>
			<USER_POSITION_ABBR><![CDATA[<%out.print(delivereList.get(pos).getUserPositionAbbr()); %>]]></USER_POSITION_ABBR>
			<USER_LEVEL><![CDATA[<%out.print(delivereList.get(pos).getUserLevel()); %>]]></USER_LEVEL>
			<USER_LEVEL_ABBR><![CDATA[<%out.print(delivereList.get(pos).getUserLevelAbbr()); %>]]></USER_LEVEL_ABBR>
			<USER_DUTY><![CDATA[<%out.print(delivereList.get(pos).getUserDuty()); %>]]></USER_DUTY>
			<USER_TITLE><![CDATA[<%out.print(delivereList.get(pos).getUserTitle()); %>]]></USER_TITLE>
			<COMPANY><![CDATA[<%out.print(delivereList.get(pos).getCompany()); %>]]></COMPANY>
			<DEPT_NAME><![CDATA[<%out.print(delivereList.get(pos).getDeptName()); %>]]></DEPT_NAME>
			<DEPT_CODE><![CDATA[<%out.print(delivereList.get(pos).getDeptCode()); %>]]></DEPT_CODE>
			<IS_OPEN><%out.print(delivereList.get(pos).getIsOpen()); %></IS_OPEN>
			<SIGN_DATE><%out.print(delivereList.get(pos).getSignDate()); %></SIGN_DATE>
			<SIGN_FILE_NAME><%out.print(delivereList.get(pos).getSignFileName()); %></SIGN_FILE_NAME>
			<REP_ID><%out.print(delivereList.get(pos).getRepId()); %></REP_ID>
			<REP_NAME><![CDATA[<%out.print(delivereList.get(pos).getRepName()); %>]]></REP_NAME>
			<REP_POSITION><![CDATA[<%out.print(delivereList.get(pos).getRepPosition()); %>]]></REP_POSITION>
			<REP_POSITION_ABBR><![CDATA[<%out.print(delivereList.get(pos).getRepPositionAbbr()); %>]]></REP_POSITION_ABBR>
			<REP_LEVEL><![CDATA[<%out.print(delivereList.get(pos).getRepLevel()); %>]]></REP_LEVEL>
			<REP_LEVEL_ABBR><![CDATA[<%out.print(delivereList.get(pos).getRepLevelAbbr()); %>]]></REP_LEVEL_ABBR>
			<REP_DUTY><![CDATA[<%out.print(delivereList.get(pos).getRepDuty()); %>]]></REP_DUTY>
			<REP_TITLE><![CDATA[<%out.print(delivereList.get(pos).getRepTitle()); %>]]></REP_TITLE>
			<REP_COMPANY><![CDATA[<%out.print(delivereList.get(pos).getRepCompany()); %>]]></REP_COMPANY>
			<REP_DEPT_NAME><![CDATA[<%out.print(delivereList.get(pos).getRepDeptName()); %>]]></REP_DEPT_NAME>
			<REP_DEPT_CODE><![CDATA[<%out.print(delivereList.get(pos).getRepDeptCode()); %>]]></REP_DEPT_CODE>
			<OPINION><![CDATA[<%out.print(delivereList.get(pos).getOpinion()); %>]]></OPINION>
		</DELIVERER>
		<%
		} %>
	</DELIVERERS>
	<%}else{ //딜리버가 널인지 체크하는 if종료%>
	<DELIVERERS/>
	<%}%>
	<%if(recipientList!= null && recipientList.size()!=0){%>
	<RECIPIENTS>
		<RECIP_GROUP>
			<RECIP_GROUP_TYPE>1</RECIP_GROUP_TYPE>
			<CASE_NUMBER>1</CASE_NUMBER>
			<DISPLAY_AS><![CDATA[]]></DISPLAY_AS>
		<%for(int pos = 0 ; pos < recipientList.size(); pos ++){%>
			<RECIPIENT METHOD="">
				<ENFORCE_BOUND><%out.print(recipientList.get(pos).getEnforceBound());%></ENFORCE_BOUND>
				<DEPT_NAME><![CDATA[<%out.print(recipientList.get(pos).getDeptName());%>]]></DEPT_NAME>
				<DEPT_CODE><![CDATA[<%out.print(recipientList.get(pos).getDeptCode());%>]]></DEPT_CODE>
				<DEPT_SYMBOL><![CDATA[<%out.print(recipientList.get(pos).getDeptSymbol());%>]]></DEPT_SYMBOL>
				<DEPT_CHIEF><![CDATA[<%out.print(recipientList.get(pos).getDeptChief());%>]]></DEPT_CHIEF>
				<REF_DEPT_NAME><![CDATA[<%out.print(recipientList.get(pos).getRefDeptName());%>]]></REF_DEPT_NAME>
				<REF_DEPT_CODE><![CDATA[<%out.print(recipientList.get(pos).getRefDeptCode());%>]]></REF_DEPT_CODE>
				<REF_DEPT_SYMBOL><![CDATA[<%out.print(recipientList.get(pos).getRefDeptSymbol());%>]]></REF_DEPT_SYMBOL>
				<REF_DEPT_CHIEF><![CDATA[<%out.print(recipientList.get(pos).getRefDeptChief());%>]]></REF_DEPT_CHIEF>
				<ACTUAL_DEPT_CODE><![CDATA[<%out.print(recipientList.get(pos).getActualDeptCode());%>]]></ACTUAL_DEPT_CODE>
				<ACCEPTOR><![CDATA[<%out.print(recipientList.get(pos).getAcceptor());%>]]></ACCEPTOR>
				<ACCEPT_DATE><%out.print(recipientList.get(pos).getAcceptDate());%></ACCEPT_DATE>
				<RECEIPT_STATUS><%out.print(recipientList.get(pos).getReceiptStatus());%></RECEIPT_STATUS>
				<DESCRIPTION><![CDATA[<%out.print(recipientList.get(pos).getDescription());%>]]></DESCRIPTION>
				<IS_PUBDOC_RECIP><%out.print(recipientList.get(pos).getIsPubdocRecip());%></IS_PUBDOC_RECIP>
				<IS_CERT_EXIST><%out.print(recipientList.get(pos).getIsCertExist());%></IS_CERT_EXIST>
				<SENDING_TYPE><%out.print(recipientList.get(pos).getSendingType());%></SENDING_TYPE>
			</RECIPIENT>
		<%
		} %>
		</RECIP_GROUP>
	</RECIPIENTS>
	<%}else{ //딜리버가 널인지 체크하는 if종료%>
	<RECIPIENTS/>
	<%}%>
	<RELATED_SYSTEMS/>
	<LEGACY_SYSTEMS/>
</APPROVAL_DOC>


</XML>




<HEAD>
	<TITLE>
문서 보기
	</TITLE>

<!-- Style Begin -->
<META http-equiv="Content-Type" content="text/html; charset=euc-kr">
<BASE href="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>">
<LINK rel="stylesheet" type="text/css"
	href="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/css/2.0/main.css" />

<!-- Style End -->

<!-- icaha, document switching -->
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApproveEditor.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/common/cn_WindowTitle.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/common/cn_WindowMenu.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApproveMenu.js"></SCRIPT>

<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApproveMenuImpl.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApprovePopupCtrl.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApproveDataCtrl.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApproveInfoCtrl.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApproveAction.js"></SCRIPT>


		<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/legacy/cn_LegacyData.js"></SCRIPT>

	<DIV id="editorAccess">

		<!--SCRIPT id="commonAccess" language="javascript" src=""></SCRIPT-->
		<SCRIPT id="bodyAccess" language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApproveHunDocument.js"></SCRIPT>

		<SCRIPT id="enforceAccess" language="javascript" src=""></SCRIPT>

			<!--SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApproveHunEnforce.js"></SCRIPT-->

			<!--SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/docflow/document/cn_ApproveHunDirectEnforce.js"></SCRIPT-->

	</DIV>

<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/ko/cn_ApproveDocument.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/ko/cn_ApproveSubDocument.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/ko/cn_ApproveRole.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/ko/cn_ApproveStatus.js"></SCRIPT>

<!--
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApproveMenuImpl.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApprovePopupCtrl.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApproveDataCtrl.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApproveInfoCtrl.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApproveAction.js"></SCRIPT>
-->
<!--
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApproveSubDocument.js"></SCRIPT>
-->

<!--
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/common/cn_ApproveRelateCtrl.js"></SCRIPT>
-->
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/common/cn_FileTransfer.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/approve/document/cn_ApproveFileCtrl.js"></SCRIPT>
<SCRIPT langauge="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/common/cn_DateNTimeCtrl.js"></SCRIPT>

<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/pubdoc/cn_PubDoc.js"></SCRIPT>
<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/ko/cn_PubDoc.js"></SCRIPT>





<SCRIPT language="javascript">
	var g_bHwpEnforceOpen = false;
	var g_strBaseUrl = "<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>";
	var g_strDataUrl = "<%=dataurl%>";
	var g_strFormUrl = "";
	var g_strFormName = "";
	var g_strFormUsage = "0";
	var g_strCabinet = "<%=cabinet%>";
	var g_strDocStatus = "0";
	var g_strSignUrl = "F081FB1C9A28A1526187E693FFFFE880.bmp";
	var g_strSignType = "0";
	var g_strUsePassword = "N";
	var g_strUseFP = "N";

	var g_strLogoName = "";
	var g_strSymbolName = "";

	var g_strUserID = "U2826020019228208120";
	var g_strRoleCode = "1001^1008^2001^2002^1001^1008^2001^2002^";
	// 20031010, icaha
	var g_strUserName = "한인경";
	var g_strUserDeptName = "경영정보팀";
	var g_strUserDeptCode = "DRJ000168522003125130781";
	var g_strApproverID = "U2826020019228208120";
	var g_strApproverDeptName = "경영정보팀";
	var g_strApproverDeptCode = "DRJ000168522003125130781";
	var g_strApproverCompName = "벽산";
	var g_strApproverCompCode = "AA1000";
	var g_strApproverType = "0";
	var g_strMandators = "";
	var g_strLineName = "<%=linename%>";
	var g_strSerialOrder = "-1";

	var g_strEmail = "daehan700@empal.com";
	var g_strHomePage = "";
	var g_strTelephone = "(주)벽산";
	var g_strFax = "02-2260-6043";
	var g_strZipCode = "100-710";
	var g_strAddress = "서울 중구 광희동1가 광희빌딩  14층 경영정보팀";

	var g_strDeptHomepage = "";
	var g_strDeptEmail = "";
	var g_strDeptAddress = "";
	var g_strDeptAddressDetail = "";
	var g_strDeptZipCode = "";
	var g_strDeptTelephone = "";
	var g_strDeptFax = "";

	var g_strUserOrgCode = "";
	var g_strUserOrgName = "";
	var g_strUserOrgDisplayName = "";

	var g_strRepUserInfo = "U2826020019228208120한인경S1벽산경영정보팀DRJ000168522003125130781";

	var g_strUploadUrl = "";
	var g_strDownloadPath = "";

	var g_strHttpUploadUrl = "";

	var g_strPubDocUploadUrl = "ftp://:/sendtemp/";
	var g_strMisDocUploadUrl = "ftp://:/sendtemp/";

	var g_strEditType = "24";
	var g_strIsDirect = "N";
	var g_strIsAdminMis = "N";

	var g_strCaller = "";
	var g_strInsertFile = "";
	var g_strTransYear = "";

	var g_strBodyType = "gul";

	var g_nFrameScale = 0;

	var g_strSenderAs = "";
	var g_objOriginRecipients = null;
	var g_strRecipients = "";

/// icaha, for document switching
	var g_strVersion = "2.0";

	var g_strSubject = "";
	var g_strIsAttached = "";

	<%if(docInfo != null){ %>
	g_strSubject = "<%=draftInfo.getTitle() %>";
	g_strIsAttached = "<%=docInfo.getIsAttached() %>";
	<%}%>
	var g_strIsDirect = "N";
	var g_strIsBatch = "N";
	var g_strIsOpinion = "N";
	var g_strIsPost = "N";
	var g_strDocCategory = "I";
	var g_strEnforceBound = "N";
	var g_strRegistryType = "Y";

	var g_bIsDocSwitching = false;

	var g_strOption4 = "1";
	var g_strOption5 = "0";
	var g_strOption8 = "34";
	var g_strOption15 = "0";
	var g_strOption16 = "0";
	var g_strOption36 = "YYYY.MM.DD";
	var g_strOption37 = "GRD^";
	var g_strOption42 = "0";
	var g_strOption44 = "0";
	var g_strOption45 = "1";
	var g_strOption46 = "1";
	var g_strOption55 = "0:1인결재^1:기안^2:검토^3:협조^4:열람^5:결재^6:전결^7:대결^8:결재안함(위임)^9:사후보고(후열)^10:개인통보^11:개인감사^12:개인 순차합의^13:개인 병렬합의^20:담당^21:선람^22:공람^23:동시공람^24:공람열람^30:부서통보^31:부서감사^32:부서순차합의^33:부서병렬합의^34:주관부서^35:신청부서^";
	var g_strOption77 = "0";
	var g_strOption78 = "0";
	var g_strOption81 = "0";
	var g_strOption83 = "0";
	var g_strOption87 = "1";
	var g_strOption88 = "1";
	var g_strOption90 = "1";
	var g_strOption96 = "1";
	var g_strOption148 = "0";
	var g_strOption161 = "1";
	var g_strOption170 = "0";
	var g_strOption171 = "0";
	var g_strOption180 = "0";

	function onLoad()
	{
		// 파일 ActiveX 초기화
		initializeFileManager();
		var fileCount = 0;
		var filelist = new Array();
		<% if 	(fileList != null) { %>
		<%		for (int loop = 0; loop < fileList.size(); loop++) {
		    MigFileVO fileVO = (MigFileVO) fileList.get(loop);		%>
			var file = new Object();
			file.filename = "<%=fileVO.getFileName()%>";
			file.displayname = "<%=fileVO.getFileName()%>";
			filelist[fileCount++] = file;
		<%		} %>
		<% } %>	
		if (filelist.length > 0) {
			var resultlist = FileManager.savefilelist(filelist);
			if(resultlist != null)	var result = resultlist.split(String.fromCharCode(31));
			//var resultcount = 1;
		}
		g_strDownloadPath = FileManager.getlocaltempdir();
		
		loadAttach($("#attachFile", "#approvalitem").val());
/*
		// 기상청
		if (ApprovalDoc.xml == "")
		{
			alert("이미 처리된 문서입니다.");
			return;
		}
*/
		// 대결지정값과 현재결재자 정보를 비교
		if (g_strMandators != "")
		{
			var strCurrentUserID = "";
			var objApprover = getCurrentApprover();
			if (objApprover != null)
				strCurrentUserID = getApproverUserID(objApprover);

			if (strCurrentUserID != "" && g_strMandators.indexOf(strCurrentUserID) != -1)
				g_strApproverType = 4;
		}

//개인함 재기안 문서 정보 유효성 검사
		if (g_strCabinet == "PRIVATE")
		{
			validateDocumentInfo();
		}

//		if (getSenderAs(1) != "")
//			g_strSenderAs = getSenderAs(1);

// check password for DM
		if (g_strOption148 == "1" && g_strCaller == "DM")
		{
			var strSecurityPass = "";
			strSecurityPass = getSecurityPass();
			if (strSecurityPass != "")
			{
				var bReturn = false;
				bReturn = checkRelatedPass(strSecurityPass);
				if (bReturn == false)
				{
					window.close();
					return;
				}
			}
		}

		/* if (g_strBodyType != "hwp")
		{
			window.moveTo(0, 0);
			window.resizeTo(800, 596);
		} */
		//g_strDownloadPath = getDownloadPath();
		//createDownloadPath(g_strDownloadPath);

		setBodyType(g_strBodyType);

		if (g_strBodyType == "hwp")
		{
			document.getElementById("hwpMenuSet").value = document.getElementById("defaultMenu").value;
		}

		if ("" == "L")
		{
			g_bLegacyFormSelected = true;
		}
		if (g_strCaller == "legacy" && getLegacySystem() != null)
		{
			if (getInputConfigInputMethod() == "3")		// process local legacy data file
			{
				loadLocalLegacyDataFile();
			}

			onOpenLegacyForm();
		}
		// 20030410, roadshow, legacy document prepared waiting for submission
		else if (getLegacySystem() != null && getFlowStatus() == "0")
		{
			var objApprovalLine = getActiveApprovalLine();
			if (objApprovalLine != null)
			{
				var strStatus = getDocStatus(objApprovalLine);

				if (strStatus == "1")		// 기안자 지정됨
				{
					onOpenLegacyForm();
				}
				else
				{
					onLoadDocument();
				}
			}
			else
				onLoadDocument();
		}
		else if (g_strDataUrl != "" && getIsPubDoc() == "Y")
		{
			var bReturn = unPack();

			if (bReturn == false)
			{
				if (g_strBodyType == "hwp")
					g_bHwpNeedClose = true;
				else
					window.close();
			}
		}
		else if (g_strDataUrl != "" && getIsAdminMis() == "Y")
		{
			var objApprovalLine = getActiveApprovalLine();
			if (objApprovalLine != null)
			{
				var strDocStatus = getDocStatus(objApprovalLine);
				if (strDocStatus == "1")
				{
					downloadAndUnpackAdminMisExchPackFile();
					onConvertAdminXmlToDocXml();
				}
				else
					onLoadDocument();
			}
			else
				onLoadDocument();
		}
		else
		{
			onLoadDocument();
		}

		var strFlowStatus = getFlowStatus();
		if (strFlowStatus == "6" || strFlowStatus == "7")
		{
			var objOriginRecipients = getRecipients();
			if (objOriginRecipients != null)
			{
				g_objOriginRecipients = objOriginRecipients.cloneNode(true);
				for (var nLoop = 0; nLoop < getBodyCount(); nLoop++)
				{
					var objRecipGroup = getRecipGroupFromOrigin(g_objOriginRecipients, nLoop + 1);
					if (objRecipGroup != null)
						g_strRecipients += "N\u0002" + getRecipientCount(objRecipGroup) + "\u0002";
					else
						g_strRecipients += "\u0002\u0002";
				}
			}
		}
	}

	function onDocumentOpen()
	{
		if (g_strCaller == "legacy")
		{
			fillLegacyDataField();
		}
		else if (getLegacySystem() != null && getFlowStatus() == "0")
		{
			var objApprovalLine = getActiveApprovalLine();
			if (objApprovalLine != null)
			{
				var strStatus = getDocStatus(objApprovalLine);
				if (strStatus == "1")		// 기안자 지정됨
				{
					fillLegacyDataField();
				}
			}
		}

		if (g_strIsDirect == "N" && (g_strEditType == "13" || g_strEditType == "14" || g_strEditType == "15" || g_strEditType == "16" || g_strEditType == "17" || g_strEditType == "51"))
		{
			var nProposal = 1;
			var nBodyCount = getBodyCount();
			while (nProposal <= nBodyCount)
			{
				var strDocCategory = getDocCategory(nProposal);
				if (strDocCategory == "E" || strDocCategory == "W")
					break;

				nProposal++;
			}

			if (nProposal <= nBodyCount && getEnforceFileObj(nProposal) != null)
			{
				createEnforceSide();

				setFrameScale(2);
				onLoadEnforceDocument();
			}
			else
				setFrameScale(0);
		}
		else
			setFrameScale(0);

		if (g_strBodyType != "hwp")
			onResize();

		if (g_strEditType == "0")		// 작성시 문서정보창 자동 Open
		{
			onSetDocInfo();
		}
		else if (g_strEditType == "1" && (g_strBodyType == "hwp" || g_strBodyType == "han" || g_strBodyType == "gul"))
		{
			var objExtendAttach = getFirstExtendAttach();
			while (objExtendAttach != null)
			{
				var strClassify = getAttachClassify(objExtendAttach);
				if (strClassify == "Synopsis")
				{
					strFileName = getAttachFileName(objExtendAttach);
					if (strFileName != "")
					{
						if (getLocalFileSize(g_strDownloadPath + strFileName) == 0)
						{
							if (downloadFile(g_strUploadUrl + strFileName, g_strDownloadPath + strFileName) != 0)
								openLocalFile(g_strDownloadPath + strFileName);
						}
						else
							openLocalFile(g_strDownloadPath + strFileName);
					}

					break;
				}

				objExtendAttach = getNextExtendAttach(objExtendAttach);
			}

			if (g_strIsOpinion == "Y")
				onViewOpinion();
		}
		else if (g_strEditType == "11")		// 벽산용 (시행변환시 의견창 보이기)
		{
			if (g_strIsOpinion == "Y")
				onViewOpinion();
		}
		else if (g_strEditType == "14")
		{
			if (checkAutoEnforceStamp() == true)
				getAutoEnforceStamp();
		}

		if (g_strOption180 == "1" && g_strEditType == "0")
			setInfoChange(true);

		var strFlowStatus = getFlowStatus();
		if (strFlowStatus == "5" || strFlowStatus == "9" || strFlowStatus == "15")
		{
			var strDelivererType = "";
			if (strFlowStatus == "5")
				strDelivererType = "0";
			else if (strFlowStatus == "9")
				strDelivererType = "3";
			else if (strFlowStatus == "15")
				strDelivererType = "5";

			var objDeliverer = getDelivererByDelivererType(strDelivererType);
			if (objDeliverer != null)
			{
				var strOpinion = getDelivererOpinion(objDeliverer);
				if (strOpinion != "")
				{
					var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveOpinion.jsp?strType=R&returnFunction=&opinion=" + strOpinion + "&bodytype=" + g_strBodyType;
					window.open(strUrl,"Approve_SetOpinion","toolbar=no,resizable=no, status=yes, width=550,height=320");
				}
			}
		}
		else if (strFlowStatus == "18")
		{
			var objApprovalLine = getApprovalLineByLineName("0");
			if (objApprovalLine != null)
			{
				var objApprover = getApproverByAction(objApprovalLine, "9");
				if (objApprover != null)
				{
					var strOpinion = getApproverOpinion(objApprover);
					if (strOpinion != "")
					{
						var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveOpinion.jsp?strType=R&returnFunction=&opinion=" + strOpinion + "&bodytype=" + g_strBodyType;
						window.open(strUrl,"Approve_SetOpinion","toolbar=no,resizable=no, status=yes, width=550,height=320");
					}
				}
			}
		}
	}

	function onClose()
	{
		if (checkAutoSuspend() == false)
			window.close();
	}

	function onUnload()
	{
		onUnloadDocument();

		var eleEnforce = document.getElementById("enforceSide");
		if (eleEnforce.innerHTML != "")
			onUnloadEnforceDocument();

		//deleteServerDirectory(g_strUploadUrl);
		//deleteDownloadPath(g_strDownloadPath);

		if (g_strCaller == "")
		{
			if (g_strBodyType == "hwp" && !g_bHideParent)
			{
//				parent.document.location.reload();
				//parent.document.location.href = parent.document.location.href;
			}
			else
			{
//				opener.document.location.reload();
				//opener.document.location.href = opener.document.location.href;
			}
		} 
	}

	function onResize()
	{
		var eleDocument = document.getElementById("documentSide");
		var eleEnforce = document.getElementById("enforceSide");
		var eleTextBody = document.getElementById("TextBody");

		if (eleDocument != null)
		{
			var nHeight = document.body.clientHeight - eleDocument.getBoundingClientRect().top + 2;
			if (nHeight > 0)
				eleDocument.style.height = nHeight;// - 50;	// for debug
		}
		if (eleDocument != null && eleEnforce != null)
		{
			var nHeight = document.body.clientHeight - eleDocument.getBoundingClientRect().top + 2;
			if (nHeight > 0)
				eleEnforce.style.height = nHeight;// - 50;		// for debug
		}

		if (eleTextBody != null)
		{
			var nHeight = document.body.clientHeight - eleTextBody.getBoundingClientRect().top + 2;
			if(nHeight > 0)
				eleTextBody.style.height = nHeight;	// - 50;	// -50 for debug;
		}
	}

	function createEnforceSide()
	{
		var eleEnforce = document.getElementById("enforceSide");
		if (eleEnforce.innerHTML == "")
		{
			var strInnerHTML = getEnforceObjectHTML();
			eleEnforce.innerHTML = strInnerHTML;
		}
	}

	function setFrameScale(nType)
	{
		g_nFrameScale = nType;

		var eleDocument = document.getElementById("documentSide");
		var eleEnforce = document.getElementById("enforceSide");

		if (nType == 0)
		{
			eleDocument.style.display = "block";
			eleEnforce.style.display = "none";
		}
		else if (nType == 1)
		{
			if (g_strIsDirect == "N")
			{
				eleDocument.style.display = "none";
				eleEnforce.style.display = "block";
			}
		}
		else if (nType == 2)
		{
			eleDocument.style.display = "block";
			eleEnforce.style.display = "block";
		}

		if (g_strBodyType == "hwp" && g_bHwpEnforceOpen == true)
		{
			setHwpWindowScale(nType);
		}
	}

	function checkNewRecipient(strProposal, strDeptcode, strRefdeptcode, strActualcode)
	{
		var bIsNew = true;

		if (g_objOriginRecipients != null)
		{
			var objRecipGroup = getRecipGroupFromOrigin(g_objOriginRecipients, strProposal);

			if (objRecipGroup != null)
			{
				var objRecipient = getFirstRecipient(objRecipGroup)
				while (objRecipient != null)
				{
					if (getRecipientDeptCode(objRecipient) == strDeptcode &&
						getRecipientRefDeptCode(objRecipient) == strRefdeptcode &&
						getRecipientActualDeptCode(objRecipient) == strActualcode)
					{
						bIsNew = false;
						break;
					}
					else
						objRecipient = getNextRecipient(objRecipient);
				}
			}
		}

		return bIsNew;
	}
	//문서정보조회
	function docDetail()
	{	
		window.open("<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/docDetail.jsp","Approve_ViewDocDetail","toolbar=no,resizable=no, status=yes, width=580,height=400");
	}
	function saveMigAppDoc() {
		var count = 0;
		var attachid = "";
		var checkname = "attach_cname";
		var checkboxes = document.getElementsByName(checkname);
		var filelist = new Array();
		if (checkboxes != null && checkboxes.length != 0) {
			for (var loop = checkboxes.length - 1; loop >= 0; loop--) {
				attachid = checkboxes[loop].id;
				attachid = attachid.replace("attach_cid_", "attach_");
				var attach = $("#" + attachid);
				var file = new Object();
				file.fileid = attach.attr("fileid");
				file.filename = attach.attr("filename");
				file.displayname = attach.attr("displayname");
				file.gubun = "";
				file.docid = "";
				file.type = "save";
				filelist[count++] = file;
			}
		}
		if (count == 0) {
			var filename = escapeFilename($("#title").val() + ".hwp");
				saveAppDoc();
		} else {
			FileConst.Variable.Distribute = "Y";
			FileManager.download(filelist);
		}
	}
</SCRIPT>

</HEAD>

<BODY onload="onLoad();" onunload="onUnload();" onresize="onResize();" ondragstart="return(false);">
<div id="approvalitem" name="approvalitem">
	<!-- 첨부 -->
	<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft004))%>" />
	<div id="divattach" type="hidden" style="display:none;background-color:#ffffff;border:0px solid;height:0px;width=100%;overflow:auto;"></div>
</div>

<!-- Title begin -->
<DIV id="title">
		<TABLE width="100%" height="33" border="0" cellpadding="0"
			cellspacing="0"
			background="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/pop_topbg.gif">
			<TR>
				<TD width="10"></TD>
				<TD width="9"><IMG src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/tit_icon_pop.gif" width="9"
					height="9"></TD>
				<TD width="5"></TD>
				<%if(draftInfo != null){ %>
				<TD class="title_wh" nowrap>문서 보기 (<%=draftInfo.getTitle()%>)
				<%}else{ %>
				<TD class="title_wh" nowrap>문서 보기
				<%} %>
				</TD>
				<TD width="19"><IMG src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/bu5_close.gif" width="19"
					height="19" onclick="javascript:onClose();return(false);"></TD>
				<TD width="10"></TD>
			</TR>
		</TABLE>

	</DIV>
<!-- Title end -->

<!-- Menu begin -->
<DIV id="menu">
<TABLE width='100%' border='0' cellpadding='0' cellspacing='0'
	id='defaultMenu' style='display: block'>
	<TR>
		<TD height='13'></TD>
	</TR>
	<TR>
		<TD><TABLE align='right' border='0' cellpadding='0'
				cellspacing='0'>
				<TR>
					<TD><IMG src='<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/bu2_left.gif' width='8'
						height='20'></TD>
					<TD background='<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/bu2_bg.gif' class='text_left' nowrap><A
						href='#' onclick="javascript:docDetail();return(false);">상세정보</A></TD>
					<TD><IMG src='<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/bu2_right.gif' width='8'
						height='20'></TD>
					<TD width='3'></TD>
					<TD><IMG src='<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/bu2_left.gif' width='8'
						height='20'></TD>
					<TD background='<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/bu2_bg.gif' class='text_left' nowrap><A
						href='#' onclick="javascript:onSaveFile();return(false);">내려받기</A></TD>
					<TD><IMG src='<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/bu2_right.gif' width='8'
						height='20'></TD>
					<%if("Y".equals(docInfo.getIsAttached())) {%>
					<TD><IMG src='<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/bu2_left.gif' width='8'
						height='20'></TD>
					<TD background='<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/bu2_bg.gif' class='text_left' nowrap><A
						href='#' onclick="javascript:saveMigAppDoc();return(false);">첨부받기</A></TD>
					<TD><IMG src='<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/bu2_right.gif' width='8'
						height='20'></TD>
					<TD width='3'></TD>
					<%} %>
					<TD width='7'></TD>
				</TR>
			</TABLE></TD>
	</TR>
	<TR>
		<TD height='5'></TD>
	</TR>
</TABLE>

</DIV>
<!-- Menu end -->

<!-- SubMenu begin -->

<%-- 
<TABLE border="0" cellspacing="0" cellpadding="0" valign="bottom" width="100%">
	<TR>
		<TD width="10" height="24"><BR/></TD>
		<TD align="left">

			<TABLE border="0" cellspacing="0" cellpadding="0">
				<TR>
					<TD width="30">
						<A href="#" onclick="javascript:onPrevApprove();return(false);" onmouseover="" onmouseout="" class="bt1"><IMG src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/prev_arrow.gif"/></A>
					</TD>
					<TD width="30">
						<A href="#" onclick="javascript:onNextApprove();return(false);" onmouseover="" onmouseout="" class="bt1"><IMG src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/next_arrow.gif"/></A>
					</TD>
				</TR>
			</TABLE>

		</TD>
		<TD align="right">

<!--
			<TABLE align='right' border='0' cellpadding='0' cellspacing='0'>
				<TR>
					<TD>
						<IMG src='<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/bu2_left.gif' width='8' height='20'>
					</TD>
					<TD background='<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/bu2_bg.gif' class='text_left' nowrap>
						<A href='#' onclick="javascript:onSendACUBEMsg('APPR', '');return(false);">보내기</A>
					</TD>
					<TD>
						<IMG src='<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/ref/image/bu2_right.gif' width='8' height='20'>
					</TD>
				</TR>
			</TABLE>
-->

		</TD>
		<TD width="7" height="5"></TD>
	</TR>
</TABLE>
 --%>

<!-- SubMenu end -->

<!-- Body begin -->
<DIV id="editor">
	
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
			<TR>
				<TD id="documentSide" style="display:block" height="1">
					<!--<OBJECT id="Document_Hun2KLa" width="100%" height="100%" classid="CLSID:3CCEE269-A6D2-11D2-BF35-00A0C98C39D8">
						<param name="_Version" value="65536"/>
						<param name="_ExtentX" value="2646"/>
						<param name="_ExtentY" value="1323"/>
						<param name="_StockProps" value="0"/>
					</OBJECT>-->
					<SCRIPT language="javascript" src="<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/app/jsp/old/htdocs/js/activex/Document_Hun2KLa.js"></SCRIPT>
				</TD>
				<TD id="enforceSide" style="display:none" height="1">
				</TD>
			</TR>
		</TABLE>


</DIV>
<!-- Body end -->

<DIV style="width:0;height:0;">
	<OBJECT id="FileTransfer" width="0" height="0" classid="CLSID:FF295F4D-39E3-474E-A5EA-EF8702B4D46C">
		<PARAM name="_Version" value="65536">
		<PARAM name="_ExtentX" value="2646">
		<PARAM name="_ExtentY" value="1323">
		<PARAM name="_StockProps" value="0">
	</OBJECT>
</DIV>

<DIV style="width:0;height:0;display:none;">
	<FORM name="dataForm" method="post" action="" target="dataTransform">
		<INPUT name="xmlData" type="hidden"></INPUT>
		<INPUT name="extendData" type="hidden"></INPUT>
		<INPUT name="returnFunction" type="hidden"></INPUT>
		<INPUT name="uploadpath" type="hidden" value="D:\acube\cn\upload\D75DF61C9A28A1526187E693FFFFE30F\">D:\acube\cn\upload\D75DF61C9A28A1526187E693FFFFE30F\</INPUT>
	</FORM>
</DIV>

<DIV style="width:0;height:0;display:none;">
	<FORM name="legacyForm" method="post" action="" target="legacyTransform">
		<INPUT name="xmlData" type="hidden"></INPUT>
		<INPUT name="extendData" type="hidden"></INPUT>
		<INPUT name="returnFunction" type="hidden"></INPUT>
	</FORM>
</DIV>

<IFRAME name="dataTransform" src="" width="100%" height="0" scrolling="yes" frameborder="yes" border="1"></IFRAME>
<IFRAME name="legacyTransform" src="" width="100%" height="0" scrolling="yes" frameborder="yes" border="1"></IFRAME>

</BODY>
</HTML>

