<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
/** 
 *  Class Name  : jsp 
 *  Description : 한글OCX 처리 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.11 
 *   수 정 자 : 허 주
 *   수정내용 : 신규개발 
 * 
 *  @author  허주
 *  @since 2011. 03. 11 
 *  @version 1.0 
 *  @see
 */ 
%>

<script type="text/javascript">
// Define HWP Constants
var HwpConst = {
        // Hwp 양식 필드명
        Form : {
			Title		: "<spring:message code='hwpconst.form.title'/>",
			HeaderCampaign	: "<spring:message code='hwpconst.form.headercampaign'/>",
			OrganName	: "<spring:message code='hwpconst.form.organname'/>",
			Receiver		: "<spring:message code='hwpconst.form.receiver'/>",
			Receivers		: "<spring:message code='hwpconst.form.receivers'/>",
			Via            	: "<spring:message code='hwpconst.form.via'/>",
			SenderName     	: "<spring:message code='hwpconst.form.sendername'/>",
			EnforceNumber  	: "<spring:message code='hwpconst.form.enforcenumber'/>",
			EnforceDate   	: "<spring:message code='hwpconst.form.enforcedate'/>",
			ReceiveNumber  	: "<spring:message code='hwpconst.form.receivenumber'/>",
			ReceiveDate    	: "<spring:message code='hwpconst.form.receivedate'/>",
			PublicBound    	: "<spring:message code='hwpconst.form.publicbound'/>",
			ReadRange	: "<spring:message code='hwpconst.form.readrange'/>",
			ConserveType	: "<spring:message code='hwpconst.form.conservetype'/>",
			FooterCampaign 	: "<spring:message code='hwpconst.form.footercampaign'/>",
			Content        	: "<spring:message code='hwpconst.form.content'/>",
			ReceiverRefTitle 	: "<spring:message code='hwpconst.form.receiverreftitle'/>",
			ReceiverRef    	: "<spring:message code='hwpconst.form.receiverref'/>",
			Zipcode        	: "<spring:message code='hwpconst.form.zipcode'/>",
			Address        	: "<spring:message code='hwpconst.form.address'/>",
			Homepage       	: "<spring:message code='hwpconst.form.homepage'/>",
			OrgHomepage    	: "<spring:message code='hwpconst.form.orghomepage'/>",
			Telephone      	: "<spring:message code='hwpconst.form.telephone'/>",
			Fax            	: "<spring:message code='hwpconst.form.fax'/>",
			DeptFax			: "<spring:message code='hwpconst.form.deptfax'/>",
			Email          	: "<spring:message code='hwpconst.form.email'/>",
			Assistance     	: "<spring:message code='hwpconst.form.assistance'/>",
			Agree     		: "<spring:message code='hwpconst.form.agree'/>",
			Submit         	: "<spring:message code='hwpconst.form.submit'/>",
			Consider       	: "<spring:message code='hwpconst.form.consider'/>",
			Auditor			: "<spring:message code='hwpconst.form.auditor'/>",
			ExistOpinion   	: "<spring:message code='hwpconst.form.existopinion'/>",
			// 아래 3개는 image용 field로서 setEnfDocument() 의 항목에 포함되지 않음 (setEnforceForm()을 이용하여 display)
			Seal           	: "<spring:message code='hwpconst.form.seal'/>",
			OfficialSeal   	: "<spring:message code='hwpconst.form.officialseal'/>",
			AttachSeal           	: "<spring:message code='hwpconst.form.attachseal'/>",
			Logo           	: "<spring:message code='hwpconst.form.logo'/>",
			Symbol         	: "<spring:message code='hwpconst.form.symbol'/>",
			Print		: "<spring:message code='hwpconst.form.print'/>",
			DocumentCard   	: "<spring:message code='hwpconst.form.documentcard'/>",
			Omit		: "<spring:message code='hwpconst.form.omit'/>",
			OmitStamp	: "<spring:message code='hwpconst.form.omitstamp'/>",
			OmitSignature  	: "<spring:message code='hwpconst.form.omitsignature'/>",
			RegiNumber	: "<spring:message code='hwpconst.form.reginumber'/>",
			DocNumber	: "<spring:message code='hwpconst.form.docnumber'/>",
			RegiDate       	: "<spring:message code='hwpconst.form.regidate'/>",
			ApprovalDate   	: "<spring:message code='hwpconst.form.approvaldate'/>",
			DecideFor   : "<spring:message code='hwpconst.form.decidefor'/>",
			Empty          	: "\u0002",
			HWPUNITs	: 283.465
        },

        // 산은캐피탈용
        Form004 : {
        	WorkType : "<spring:message code='hwpconst.form.004.worktype'/>",
        	AppFormId : "<spring:message code='hwpconst.form.004.appformid'/>",
        	BizName : "<spring:message code='hwpconst.form.004.bizname'/>",
        	ReqName : "<spring:message code='hwpconst.form.004.reqname'/>",
        	RequestType : "<spring:message code='hwpconst.form.004.requesttype'/>", 
        	RequestTime : "<spring:message code='hwpconst.form.004.requesttime'/>",
        	TakeoutTarget : "<spring:message code='hwpconst.form.004.takeouttarget'/>",
        	TakeoutCharger : "<spring:message code='hwpconst.form.004.takeoutcharger'/>",
        	TakeoutContent : "<spring:message code='hwpconst.form.004.takeoutcontent'/>",
        	RequestReason : "<spring:message code='hwpconst.form.004.requestreason'/>"
        },
        
        // 결재처리명
        Appr : {
			Submit             		: "<spring:message code='hwpconst.appr.submit'/>",
			Consider           		: "<spring:message code='hwpconst.appr.consider'/>",
			Assistance         		: "<spring:message code='hwpconst.appr.assistance'/>",
			ParellelAssistance 		: "<spring:message code='hwpconst.appr.parellelassistance'/>",
			Agree         			: "<spring:message code='hwpconst.appr.agree'/>",
			ParellelAgree 			: "<spring:message code='hwpconst.appr.parellelagree'/>",
			Approver           		: "<spring:message code='hwpconst.appr.approver'/>",
			Arbitrary          		: "<spring:message code='hwpconst.appr.arbitrary'/>",
			DecideFor          		: "<spring:message code='hwpconst.appr.decidefor'/>",
			DecideForArbitrary		: "<spring:message code='hwpconst.appr.decideforarbitrary'/>",
			ReadAfter				: "<spring:message code='hwpconst.appr.readafter'/>",
			Inform					: "<spring:message code='hwpconst.appr.inform'/>",
			Audit					: "<spring:message code='hwpconst.appr.audit'/>"
        },

        // 문서관리카드/메모 본문 필드명
        Field : {
			Title		: "<spring:message code='hwpconst.field.title'/>",
			TaskInfo		: "<spring:message code='hwpconst.field.taskinfo'/>",
			Summary		: "<spring:message code='hwpconst.field.summary'/>",
			Body		: "<spring:message code='hwpconst.field.body'/>",
			Attachment	: "<spring:message code='hwpconst.field.attachment'/>",
			ReportPath	: "<spring:message code='hwpconst.field.reportpath'/>",
			Classification	: "<spring:message code='hwpconst.field.classification'/>",
			DeptName	: "<spring:message code='hwpconst.field.deptname'/>",  // 접수문서에서 표시
			Path		: "<spring:message code='hwpconst.field.path'/>",
			Opinion		: "<spring:message code='hwpconst.field.opinion'/>",
			Result		: "<spring:message code='hwpconst.field.result'/>",
			History		: "<spring:message code='hwpconst.field.history'/>",
			SenderOrgan	: "<spring:message code='hwpconst.field.senderorgan'/>",
			SenderName	: "<spring:message code='hwpconst.field.sendername'/>",
			RegDocNo	: "<spring:message code='hwpconst.field.regdocno'/>",
			AcceptDocNo	: "<spring:message code='hwpconst.field.acceptdocno'/>",  // 접수문서에서 표시
			IsOpen		: "<spring:message code='hwpconst.field.isopen'/>",
			Receiver		: "<spring:message code='hwpconst.field.receiver'/>",
			ReceiverRef	: "<spring:message code='hwpconst.field.receiverref'/>",
			Via		: "<spring:message code='hwpconst.field.via'/>",
			ApprPath1	: "<spring:message code='hwpconst.field.apprpath1'/>",  // 접수문서에서 표시 (결재자)
			ApprPath2	: "<spring:message code='hwpconst.field.apprpath2'/>",  // 접수문서에서 표시 (협조자)
			ReadRange	: "<spring:message code='hwpconst.field.readrange'/>",
			ReadLimit	: "<spring:message code='hwpconst.field.readlimit'/>",
			ReceiveDate	: "<spring:message code='hwpconst.field.receivedate'/>",

			Draft	: "<spring:message code='hwpconst.field.draft'/>",
			DraftDept	: "<spring:message code='hwpconst.field.draftdept'/>",
			DraftDate	: "<spring:message code='hwpconst.field.draftdate'/>",
			Approval	: "<spring:message code='hwpconst.field.approval'/>",
			ApprovalDept : "<spring:message code='hwpconst.field.approvaldept'/>",
			ApprovalDate	: "<spring:message code='hwpconst.field.approvaldate'/>",
			
			StandardForm	: "<spring:message code='hwpconst.field.standardform'/>",
			SimpleForm	: "<spring:message code='hwpconst.field.simpleform'/>",
			ConsiderLine		:"<spring:message code='hwpconst.field.considerline'/>",
			AssistanceLine		:"<spring:message code='hwpconst.field.assistanceline'/>",
			AgreeLine		:"<spring:message code='hwpconst.field.agreeline'/>",
			DraftDeptLine		: "<spring:message code='hwpconst.field.draftdeptline'/>",
			ExecDeptLine		: "<spring:message code='hwpconst.field.execdeptline'/>",
			RelatedDoc	: "<spring:message code='hwpconst.field.relateddoc'/>",
			RelatedRule	: "<spring:message code='hwpconst.field.relatedrule'/>",
			Customer	: "<spring:message code='hwpconst.field.customer'/>",
			Proposer        	: "★",           // 발의자 mark
			Reporter        	: "⊙"            // 보고자 mark
        },

        // 내부 데이터
        Data : {
			InnerApproval	: "<spring:message code='hwpconst.data.innerapproval'/>",
			Receiver		: "<spring:message code='hwpconst.data.receiver'/>",
			ReceiverRef	: "<spring:message code='hwpconst.data.receiverref'/>",
			PubFilter		: "<spring:message code='hwpconst.data.pubfilter'/>",
			Open			: "<spring:message code='hwpconst.data.open'/>",
			PartialOpen	: "<spring:message code='hwpconst.data.partialopen'/>",
			Closed		: "<spring:message code='hwpconst.data.closed'/>"
        },

        // 필드 Font Size
        Font : {
			Gulimche		: "<spring:message code='hwpconst.font.gulimche'/>",
			Goongseoche	: "<spring:message code='hwpconst.font.goongseoche'/>",
			SenderName 	: 18
        },

        // 공석사유
        EmptyReason : {
			DAB01		: "<spring:message code='hwpconst.emptyreason.dab01'/>",
			DAB02		: "<spring:message code='hwpconst.emptyreason.dab02'/>",
			DAB03		: "<spring:message code='hwpconst.emptyreason.dab03'/>",
			DAB04		: "<spring:message code='hwpconst.emptyreason.dab04'/>",
			DAB05		: "<spring:message code='hwpconst.emptyreason.dab05'/>",
			DAB06		: "<spring:message code='hwpconst.emptyreason.dab06'/>",
			DAB07		: "<spring:message code='hwpconst.emptyreason.dab07'/>",
			DAB08		: "<spring:message code='hwpconst.emptyreason.dab08'/>",
			DAB09		: "<spring:message code='hwpconst.emptyreason.dab09'/>",
			DAB10		: "<spring:message code='hwpconst.emptyreason.dab10'/>",
			DAB11		: "<spring:message code='hwpconst.emptyreason.dab11'/>",
			DAB12		: "<spring:message code='hwpconst.emptyreason.dab12'/>",
			DAB13		: "<spring:message code='hwpconst.emptyreason.dab13'/>",
			DAB14		: "<spring:message code='hwpconst.emptyreason.dab14'/>"
        },

        // Message
        Msg : {
			TRANSFER 				: "<spring:message code='hwpconst.msg.transfer'/>",
			RETURN 					: "<spring:message code='hwpconst.msg.return'/>",
			VIA 					: "<spring:message code='hwpconst.msg.via'/>",
			FAILED_MAKE_DISTRIBUTE_DOCUMENT 	: "<spring:message code='hwpconst.msg.failed_make_distribute_document'/>",
			TOO_SHORT_PASSWORD              		: "<spring:message code='hwpconst.msg.too_short_password'/>",
			ALREADY_DISTRIBUTE_DOCUMENT  		: "<spring:message code='hwpconst.msg.already_distribute_document'/>",
			READONLY_DOCUMENT              		: "<spring:message code='hwpconst.msg.readonly_document'/>",
			INSERT_CONTEXT				: "<spring:message code='hwpconst.msg.insert_context'/>",
			NOT_INSTALLED_ACTIVEX			: "'<spring:message code='hwpconst.msg.not_installed_activex'/>",
			OCCURRED_ERROR_REQUEST_MANAGER	: "<spring:message code='hwpconst.msg.occurred_error_request_manager'/>",
			NOT_INSTALLED_PUBFILTER			: "<spring:message code='hwpconst.msg.not_installed_pubfilter'/>",
			INCORRECT_PUBFILTER			: "<spring:message code='hwpconst.msg.incorrect_pubfilter'/>",
			NOT_SETTING_DRAFTERINFO		: "<spring:message code='hwpconst.msg.not_setting_drafterinfo'/>",
			NOT_MATCH_FIELD_AND_DATA		: "<spring:message code='hwpconst.msg.not_match_field_and_data'/>",
			HANGUL_NEED_HIGHER_VERSION : "<spring:message code='hwpconst.msg.hangul_need_higher_version'/>",
			HANGUL_NEED_LASTEST_PATCH : "<spring:message code='hwpconst.msg.hangul_need_lastest_patch'/>",
			NOT_INSTALLED_HANGUL : "<spring:message code='hwpconst.msg.not_installed_hangul'/>"
        }
};


// Define Hwp Variable
var HwpVariable = {
		Document : "",
		Title	 : "",
		IsExist  : false,
		DateFormat : "<%=AppConfig.getProperty("date_format", "YYYY-MM-DD", "date")%>",

		Position : {
			List : "",
			Para	 : "",
			Pos  : ""
		}
};

function loadInsertDocument(editBodyYn,apprline,yn){
	// 한글 ActiveX 초기화
	initializeHwp("divhwp", "document");
	if (!checkCurrentVersion(Document_HwpCtrl)) { window.close(); }
	registerModule(Document_HwpCtrl);
	initializeMenu(Document_HwpCtrl, HwpConst.Font.Gulimche, 0, 1);
	// 본문 width 사이즈 
	//setDefaultView(Document_HwpCtrl, '002');
	
	// Hwp 양식파일
	openHwpDocument(Document_HwpCtrl, hwpFormFile);

	// 표준서식 - 안추가/안삭제
	if (isStandardForm(Document_HwpCtrl) && $("#standardform_beforeprocess") != null) {
		$("#standardform_beforeprocess").show();
		$("#standardform_waiting").show();
	}
	// 수신자
	putFieldText(Document_HwpCtrl, HwpConst.Form.Receiver, "<spring:message code='hwpconst.data.innerapproval'/>");

	// 로고, 심볼, 상하캠페인, 발신명의
	initDocumentEnv(Document_HwpCtrl, getCurrentItem());

	if(editBodyYn == "Y"){
		changeEditMode(Document_HwpCtrl, 2, true);
	} else {	
		changeEditMode(Document_HwpCtrl, 0, false);
	}
	setAppLine(apprline,yn);
}

function loadSelectDocument(bodyext, bodyfilepath){
	// 한글 ActiveX 초기화
	initializeHwp("divhwp", "document");
	if (!checkCurrentVersion(Document_HwpCtrl)) { window.close(); }
	registerModule(Document_HwpCtrl);
	initializeMenu(Document_HwpCtrl, HwpConst.Font.Gulimche, 0, 0);
	// 본문 width 사이즈 
	setDefaultView(Document_HwpCtrl, '002');
	
	// Hwp 양식파일
	// jkkim added ( "Y" == opt380 && app401 == docState ) : 부서감사 옵션 추가 20120718
	if (bodyext == "txt" || bodyext == "htm" || bodyext == "html" || bodyext == "" ||  ("auditorigindoc" != call && "Y" == opt380 &&  app401 == appDocState )) {
		openHwpDocument(Document_HwpCtrl, hwpFormFile);
		if (bodyext == "txt") {
			insertTextDocument(Document_HwpCtrl, bodyfilepath, HwpConst.Form.Content);
		} else if (bodyext == "htm" || bodyext == "html") {
			insertHtmlDocument(Document_HwpCtrl, bodyfilepath, HwpConst.Form.Content);
		}
		// 로고, 심볼, 상하캠페인, 발신명의
		initDocumentEnv(Document_HwpCtrl, getCurrentItem());		
		// 제목
		putFieldText(Document_HwpCtrl, HwpConst.Form.Title, $("#title", "#approvalitem"+itemnum).val());
		putFieldText(Document_HwpCtrl, HwpConst.Form.Via, $("#via", "#approvalitem"+itemnum).val());
	} else {
		openHwpDocument(Document_HwpCtrl, bodyfilepath);
	}
}

function loadSelectTemporary(bodyext, bodyfilepath){
	// 한글 ActiveX 초기화
	initializeHwp("divhwp", "document");
	if (!checkCurrentVersion(Document_HwpCtrl)) { window.close(); }
	registerModule(Document_HwpCtrl);
	initializeMenu(Document_HwpCtrl, HwpConst.Font.Gulimche, 0, 1);
	// 본문 width 사이즈 
	setDefaultView(Document_HwpCtrl, '002');
	
	// Hwp 양식파일
	if (bodyext == "txt" || bodyext == "htm" || bodyext == "html" || bodyext == "") {
		openHwpDocument(Document_HwpCtrl, hwpFormFile);
		if (bodyext == "txt") {
			insertTextDocument(Document_HwpCtrl, bodyfilepath, HwpConst.Form.Content);
		} else if (bodyext == "htm" || bodyext == "html") {
			insertHtmlDocument(Document_HwpCtrl, bodyfilepath, HwpConst.Form.Content);
		}
		// 로고, 심볼, 상하캠페인, 발신명의
		initDocumentEnv(Document_HwpCtrl, getCurrentItem());		

		// 제목
		putFieldText(Document_HwpCtrl, HwpConst.Form.Title, $("#title", "#approvalitem"+itemnum).val());
		putFieldText(Document_HwpCtrl, HwpConst.Form.Via, $("#via", "#approvalitem"+itemnum).val());
	} else {
		openHwpDocument(Document_HwpCtrl, bodyfilepath);
		putFieldText(Document_HwpCtrl, HwpConst.Form.OrganName, $("#sendOrgName", "#approvalitem"+itemnum).val());

		// 발신명의 - 내부문서는 발신명의 생략
		var recvList = getReceiverList($("#appRecv", "#approvalitem"+itemnum).val());
		var recvsize = recvList.length;
		if (recvsize == 0) {
			putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, "");
		} else {
			putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, $("#senderTitle", "#approvalitem"+itemnum).val());
			$("#sendDate", "#approvalitem"+itemnum).val( getCurrentDate());  //자동발송 시행일자
		}

		// 하단 주소정보
	//putFieldText(hwpCtrl, HwpConst.Form.PublicBound, HwpConst.Data.Open);  // 공개범위는 문서정보창에서 설정함  // jth8172 2012 신결재 TF
		putFieldText(Document_HwpCtrl, HwpConst.Form.Zipcode, $("#postNumber", "#approvalitem"+itemnum).val());
		putFieldText(Document_HwpCtrl, HwpConst.Form.Address, $("#address", "#approvalitem"+itemnum).val());
		putFieldText(Document_HwpCtrl, HwpConst.Form.Telephone, $("#telephone", "#approvalitem"+itemnum).val());
		putFieldText(Document_HwpCtrl, HwpConst.Form.Fax, $("#fax", "#approvalitem"+itemnum).val());
		putFieldText(Document_HwpCtrl, HwpConst.Form.Homepage, $("#homepage", "#approvalitem"+itemnum).val());
		putFieldText(Document_HwpCtrl, HwpConst.Form.Email, $("#email", "#approvalitem"+itemnum).val());
	}
	// 표준서식 - 안추가/안삭제
	if (isStandardForm(Document_HwpCtrl) && $("#standardform_beforeprocess") != null) {
		$("#standardform_beforeprocess").show();
		$("#standardform_waiting").show();
	}
}

function loadSendDocument(bodyfilepath){
	// 한글 ActiveX 초기화
	initializeHwp("divhwp", "document");
	if (!checkCurrentVersion(Document_HwpCtrl)) { window.close(); }
	registerModule(Document_HwpCtrl);
	initializeMenu(Document_HwpCtrl, HwpConst.Font.Gulimche, 0, 0);
	// 본문 width 사이즈 
	setDefaultView(Document_HwpCtrl, '002');
	
	// Hwp 양식파일
	openHwpDocument(Document_HwpCtrl, bodyfilepath);
}

function loadLocalDocument(bodyfilepath){
	openLocalDocument(bodyfilepath);
}

function saveDocument(bodyfilepath, filepath, openfilename){
	saveHwpDocument(Document_HwpCtrl, filepath);
}

function loadToolbar(editMode, guideline){
	g_DocWord = Document_HwpCtrl.WordDocument;
	g_AppWord = Document_HwpCtrl.WordApplication;
	g_ActiveWnd = Document_HwpCtrl.ActiveWindow;
	
	showToolbar(Document_HwpCtrl, editMode);
	changeEditMode(Document_HwpCtrl, editMode, guideline);
}

function initEditorForRecp(){
	// 한글 ActiveX 초기화
	initializeHwp("divhwp", "document");
	if (!checkCurrentVersion(Document_HwpCtrl)) { window.close(); }
	registerModule(Document_HwpCtrl);
	initializeMenu(Document_HwpCtrl, HwpConst.Font.Gulimche, 0, 0);
	// 본문 width 사이즈 
	setDefaultView(Document_HwpCtrl, '002');
	
	setLockCommand(Document_HwpCtrl,"Copy", true);//접수문서 복사금지 설정 by jkkim
}

function initEditorAfter(){
	
}

</script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/hwpmanager.js"></script>