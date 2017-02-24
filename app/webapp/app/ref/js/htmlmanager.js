
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

	var Document_HwpCtrl = "";
	var Enforce_HwpCtrl = ""; 

	function insertOpinionTbl(objCtrl, opinionFile, totalOpinion) { return false; }
	
	function resetApprover(objCtrl, approvers, linecount, doctype, assistantlinetype, auditlinetype) { return false; }
	
	function moveToPos(objCtrl, value) { return false; }
	
	function showToolbar(objCtrl, value) { return false; }
	
	function changeEditMode(objCtrl, value, flg) { return false; }
	
	function openHwpDocument(objCtrl, localpath) { return false; }
	
	function existField(objCtrl, title) { return false; }
	
	function getFieldText(objCtrl, via) { return ""; }
	
	function putFieldText(objCtrl, enforceNumber, serial) { return false; }
	
	function saveHwpDocument(objCtrl, filepath, flg) { return false; }
	
	function saveDocument(bodyfilepath, filepath, openfilename){ return false; }
	
	function insertSeal(objCtrl, stampfilepath, filewidth, fileheight) { return false; } 
	
	function showOmitSignature(objCtrl) { return false; } 
	
	function getTitle() { return ""; }
	
	// DisplayAppDoc.jsp 에서 본문복구에서 무조건 true로 리턴해서 skip 할 수 있게 한다.
	function isExistDocument(objCtrl) { return true; }
	
	function setSavePoint(objCtrl) { return false; }
	
	function isStandardForm(objCtrl) { return false; }
	
	function clearApprTable(objCtrl) { return false; }
	
	function initializeHwp(objCtrl, document) { return false; }
	
	function checkCurrentVersion(objCtrl) { return true; }

	function registerModule(objCtrl) { return false; }
	
	function initializeMenu(objCtrl, gulimche, value0, value1) { return false; }

	function insertTextDocument(objCtrl, bodyfilepath, content) { return false; }
	
	function insertHtmlDocument(objCtrl, bodyfilepath, content) { return false; }
	
	function insertImage(objCtrl, logo, logopath, x, y) { return false; }
	
	function clearRegiInfo(objCtrl) { return false; }
	
	function removeStamp(objCtrl) { return false; }
	
	function removeOmitStamp(objCtrl) { return false; }

	function getEditMode(objCtrl) { return 3; }
	
	function replaceApprTable(objCtrl, draftSignFile, draftDeptLine) { return false; }
	
	function deleteOpinionTbl(objCtrl) { return false; }
	
	function isChanged(objCtrl) { return false; }
	
	function removeApprTable(objCtrl, title) { return false; }
	
	function setExtraInfo(objCtrl, docinfo) { return false; }
		
	function getConsiderCount(objCtrl) { return 1; }
	
	function getAssistanceCount(objCtrl) { return 1; }
	
	function getAuditorCount(objCtrl) { return 1; }
	
	function exportImage(objCtrl, logo, logofilepath) { return false; }
	
	function savePubContent(objCtrl, pubdocfile) { return false; }
	
	function FieldExist(nOrder) { return false; }
	
	function showOmitStamp(objCtrl) { return false; }
	
	function showOmitSignature(objCtrl) { return false; }
	
	function initEditorForRecp() { return false; }
	
	function initEditorAfter() { return false; }
	
	function typeOfDate(type, date) { return ""; } 
	
	function loadToolbar(editMode, guideline){ return false; }
	