package com.sds.acube.app.idir.common.constants;

public class ApprovalConstants {

	public static final int DOC_STATUS_DRAFTING = 0;
	public static final int DOC_STATUS_SUBMITER_SET = 1;
	public static final int DOC_STATUS_DRAFT_RESERVED = 2;
	public static final int DOC_STATUS_IN_PROCESS = 3;
	public static final int DOC_STATUS_DRAFT_WITHDRAWN = 4;
	public static final int DOC_STATUS_APPROVAL_RESERVED = 5;
	public static final int DOC_STATUS_REJECTED = 6;
	public static final int DOC_STATUS_OPPOSED = 7;
	public static final int DOC_STATUS_INPECT_RETURNED = 8;
	public static final int DOC_STATUS_APPROVAL_CANCELED = 9;
	public static final int DOC_STATUS_COMPLETED = 10;
	public static final int DOC_STATUS_CHARGER_ASSIGNED = 11;
	public static final int DOC_STATUS_PRIOR_ASSIGNED = 12;
	public static final int DOC_STATUS_SENT = 20;
	public static final int DOC_STATUS_SEND_REQUESTED = 21;
	public static final int DOC_STATUS_TEMPORARILY_REMOVED = 30;
	public static final int DOC_STATUS_LAST_INDEX = 30;

	public static final int LINE_TYPE_GENERAL = 0;
	public static final int LINE_TYPE_DEPT_SERIAL = 1;
	public static final int LINE_TYPE_DEPT_PARALLEL = 2;
	public static final int LINE_TYPE_DOUBLE = 3;
	public static final int LINE_TYPE_DEPT_REPORTED = 4;
	public static final int LINE_TYPE_DEPT_INSPECT = 5;
	public static final int LINE_TYPE_LAST_INDEX = 5;

	public static final int APPROVER_ROLE_SELF = 0;
	public static final int APPROVER_ROLE_DRAFT = 1;
	public static final int APPROVER_ROLE_CONSIDER = 2;
	public static final int APPROVER_ROLE_COOPERATE = 3;
	public static final int APPROVER_ROLE_READ = 4;
	public static final int APPROVER_ROLE_APPROVE = 5;
	public static final int APPROVER_ROLE_ARBITRARY = 6;
	public static final int APPROVER_ROLE_PROXY = 7;
	public static final int APPROVER_ROLE_SKIP = 8;
	public static final int APPROVER_ROLE_POST = 9;
	public static final int APPROVER_ROLE_REPORTED = 10;
	public static final int APPROVER_ROLE_PERSON_INSPECT = 11;
	public static final int APPROVER_ROLE_PERSON_SERIAL = 12;
	public static final int APPROVER_ROLE_PERSON_PARALLEL = 13;
	public static final int APPROVER_ROLE_PERSON_PARALLEL_APPROVAL = 14;
	public static final int APPROVER_ROLE_EXAMINE = 15;
	public static final int APPROVER_ROLE_CHARGE = 20;
	public static final int APPROVER_ROLE_PRIOR = 21;
	public static final int APPROVER_ROLE_PUBLIC = 22;
	public static final int APPROVER_ROLE_CONCURRENT_PUBLIC = 23;
	public static final int APPROVER_ROLE_PUBLIC_READ = 24;
	public static final int APPROVER_ROLE_DEPT_REPORTED = 30;
	public static final int APPROVER_ROLE_DEPT_INSPECT = 31;
	public static final int APPROVER_ROLE_DEPT_SERIAL = 32;
	public static final int APPROVER_ROLE_DEPT_PARALLEL = 33;
	public static final int APPROVER_ROLE_DEPT_MANAGE = 34;
	public static final int APPROVER_ROLE_LAST_INDEX = 34;

	public static final int APPROVER_ACTION_NO_DECIDE = 0;
	public static final int APPROVER_ACTION_RESERVE_SUBMIT = 1;
	public static final int APPROVER_ACTION_SUBMIT = 2;
	public static final int APPROVER_ACTION_RESUBMIT = 3;
	public static final int APPROVER_ACTION_CANCEL_SUBMIT = 4;
	public static final int APPROVER_ACTION_RESERVE_APPROVAL = 5;
	public static final int APPROVER_ACTION_APPROVAL = 6;
	public static final int APPROVER_ACTION_AGREE = 7;
	public static final int APPROVER_ACTION_OBJECT = 8;
	public static final int APPROVER_ACTION_REJECT = 9;
	public static final int APPROVER_ACTION_INSPECT_RETURN = 10;
	public static final int APPROVER_ACTION_CANCEL_APPROVAL = 11;
	public static final int APPROVER_ACTION_READ = 12;

	public static final int APPROVER_TYPE_GENERAL = 0;
	public static final int APPROVER_TYPE_REQUEST_DEPT = 1;
	public static final int APPROVER_TYPE_MANAGE_DEPT = 2;
	public static final int APPROVER_TYPE_PERSON_SERIAL = 3;
	public static final int APPROVER_TYPE_PERSON_PARALLEL = 4;
	public static final int APPROVER_TYPE_DEPT_SERIAL = 5;
	public static final int APPROVER_TYPE_DEPT_PARALLEL = 6;
	public static final int APPROVER_TYPE_DEPT_REPORT = 7;
	public static final int APPROVER_TYPE_DEPT_INSPECT = 8;

	public static final int ADDITIONAL_ROLE_GENERAL = 0;
	public static final int ADDITIONAL_ROLE_PROPOSAL = 1;
	public static final int ADDITIONAL_ROLE_REPORT = 2;
	public static final int ADDITIONAL_ROLE_CONFIRM_INSPECTION = 4;

	public static final int KEEP_STATUS_NORMAL = 0;
	public static final int KEEP_STATUS_DISCARDED = 1;
	public static final int KEEP_STATUS_REMOVED = 2;
	public static final int KEEP_STATUS_DISTRIBUTED_PROCESSING = 3;
	public static final int KEEP_STATUS_TEMPORARILY_REMOVED = 4;

	public static final int DELIVERER_TYPE_EXAMINE = 0;					// 심사자
	public static final int DELIVERER_TYPE_SEND = 1;					// 발신자
	public static final int DELIVERER_TYPE_DOC_DEPT_RECEIVE = 2;		// 문서과 접수자
	public static final int DELIVERER_TYPE_PROC_DEPT_RECEIVE = 3;		// 처리과 접수자
	public static final int DELIVERER_TYPE_UNDERTAKE = 4;				// 인수자
	public static final int DELIVERER_TYPE_POST_PUBLIC = 5;				// 게시공람자
	public static final int DELIVERER_TYPE_TRANSFER = 6;				// 이송자

	public static final int RECIP_GROUP_TYPE_SEND = 0;
	public static final int RECIP_GROUP_TYPE_RECV = 1;
	public static final int RECIP_GROUP_TYPE_PASS_THROUGH = 2;

	public static final String BODY_TYPE_GUL = "gul";
	public static final String BODY_TYPE_HWP97 = "hwp";
	public static final String BODY_TYPE_HWP2002 = "han";
	public static final String BODY_TYPE_HANGUL_2004 = "hwp6";
	public static final String BODY_TYPE_WORD = "doc";
	public static final String BODY_TYPE_HTML = "html";
	public static final String BODY_TYPE_TEXT = "txt";

	public static final String REGISTRY_TYPE_NO = "N";
	public static final String REGISTRY_TYPE_COMPLETE = "Y";
	public static final String REGISTRY_TYPE_PRESERVE = "P";
	public static final String REGISTRY_TYPE_SEND = "T";
	public static final String REGISTRY_TYPE_IMPLICIT_SEND = "I";
	public static final String REGISTRY_TYPE_REJECTION = "R";
	public static final String REGISTRY_TYPE_CIRCULATION = "C";
	public static final String REGISTRY_TYPE_TRANSFER = "S";

	public static final String DOC_CATEGORY_INTERNAL = "I";
	public static final String DOC_CATEGORY_ENFORCE = "E";
	public static final String DOC_CATEGORY_CONTACT = "W";
	public static final String DOC_CATEGORY_POST = "M";
	public static final String DOC_CATEGORY_CIVIL = "C";
	public static final String DOC_CATEGORY_EXTERNAL = "O";
	public static final String DOC_CATEGORY_EX_INTERNAL = "OI";
	public static final String DOC_CATEGORY_EX_ENFORCE = "OE";
	public static final String DOC_CATEGORY_PRE_INSPECT = "PR";
	public static final String DOC_CATEGORY_POST_INSPECT = "PO";

	public static final String ENFORCE_BOUND_INBOUND = "I";
	public static final String ENFORCE_BOUND_OUTBOUND = "O";
	public static final String ENFORCE_BOUND_COMPOSITE = "C";
	public static final String ENFORCE_BOUND_INTERNAL = "N";

	public static final int RECEIPT_STATUS_FAIL_SEND = 0;
	public static final int RECEIPT_STATUS_NO_RECEIVE = 1;
	public static final int RECEIPT_STATUS_RECEIVE = 2;
	public static final int RECEIPT_STATUS_ACCEPT = 3;
	public static final int RECEIPT_STATUS_RETURN = 4;
	public static final int RECEIPT_STATUS_WITHDRAW = 5;
	public static final int RECEIPT_STATUS_FAIL_WITHDRAW = 6;
	public static final int RECEIPT_STATUS_SHIFT = 7;
	public static final int RECEIPT_STATUS_ARRIVE = 8;
	public static final int RECEIPT_STATUS_RESEND = 9;
	public static final int RECEIPT_STATUS_REQ_RESEND = 10;
	public static final int RECEIPT_STATUS_NORMAL_RESEND = 11;
	public static final int RECEIPT_STATUS_PASS_THROUGH = 12;

	public static final int RECIPIENT_SENDING_TYPE_PLAIN = 0;
	public static final int RECIPIENT_SENDING_TYPE_SIGNED = 1;
	public static final int RECIPIENT_SENDING_TYPE_SIGNED_AND_ENVELOPED = 2;

	public static final String RECIPIENT_DEPT_CODE_OUT = "OUT";

	public static final int ATTACH_TYPE_FILE_BODY = 0;
	public static final int ATTACH_TYPE_RELATED_ATTACH = 1;
	public static final int ATTACH_TYPE_GENERAL_ATTACH = 2;
	public static final int ATTACH_TYPE_EXTENDED_ATTACH = 3;

	public static final int FLOW_STATUS_BEFORE_COMPLETED = 0;
	public static final int FLOW_STATUS_APPROVAL_COMPLETED = 1;
	public static final int FLOW_STATUS_WAITING_ENFORCE = 2;
	public static final int FLOW_STATUS_WAITING_SEND = 3;
	public static final int FLOW_STATUS_WAITING_EXAMINE = 4;
	public static final int FLOW_STATUS_INSPECT_RETURNED = 5;
	public static final int FLOW_STATUS_SENT = 6;
	public static final int FLOW_STATUS_RESENT = 7;
	public static final int FLOW_STATUS_WAITING_RECEIVE = 8;
	public static final int FLOW_STATUS_WAITING_DISTRIBUTE = 9;
	public static final int FLOW_STATUS_RECEIVE_RETURNED = 10;
	public static final int FLOW_STATUS_DISTRIBUTE_RETURNED = 11;
	public static final int FLOW_STATUS_RECEIVED = 12;
	public static final int FLOW_STATUS_DISTRIBUTED = 13;
	public static final int FLOW_STATUS_CIRCULAR = 14;
	public static final int FLOW_STATUS_FLOW_COMPLETED = 15;
	public static final int FLOW_STATUS_WAITING_SHIFT = 16;
	public static final int FLOW_STATUS_SHIFTED = 17;
	public static final int FLOW_STATUS_CIRCULATION_REJECTED = 18;
	public static final int FLOW_STATUS_REGISTERED_REJECTION = 19;
	public static final int FLOW_STATUS_PASS_THROUGH = 20;
	public static final int FLOW_STATUS_ENFORCE_CANCELED = 21;
	public static final int FLOW_STATUS_REJECTED = 22;
	public static final int FLOW_STATUS_LAST_INDEX = 22;

	public static final String CLASSIFY_INSP_STAMP = "InspStamp";
	public static final String CLASSIFY_DOC_STAMP = "DocStamp";
	public static final String CLASSIFY_SYNOPSIS = "Synopsis";
	public static final String CLASSIFY_MODIFIED_DOC = "ModifiedDoc";
	public static final String CLASSIFY_MODIFIED_ATTACH = "ModifiedAttach";
	public static final String CLASSIFY_MODIFIED_FLOW = "ModifiedFlow";
	public static final String CLASSIFY_REJECTED_DOC = "RejectedDoc";
	public static final String CLASSIFY_COMPLETED_DOC = "CompletedDoc";
	public static final String CLASSIFY_ENFORCE_DOC = "EnforceDoc";
	public static final String CLASSIFY_MESSAGE_HEADER = "MessageHeader";
	public static final String CLASSIFY_ENFORCE_RELATED = "EnforceRelated";
	public static final String CLASSIFY_ORIGIN_DOC = "OriginDoc";
	public static final String CLASSIFY_INSP_OPINION = "InspOpinion";
	public static final String CLASSIFY_ATTACH_BODY = "AttachBody";
	public static final String CLASSIFY_ORGAN_IMAGE = "OrganImage";
	public static final String CLASSIFY_PACK = "Pack";
	public static final String CLASSIFY_INSP_BODY = "InspBody";
	public static final String CLASSIFY_VOICE_OPINION = "VoiceOpinion";
	public static final String CLASSIFY_INTEGRATED_DOC = "IntegratedDoc";

	public static final String SUBDIV_DEPT_STAMP = "DeptStamp";
	public static final String SUBDIV_DEPT_OMIT_STAMP = "DeptOmitStamp";
	public static final String SUBDIV_COMPANY_STAMP = "CompanyStamp";
	public static final String SUBDIV_COMPANY_OMIT_STAMP = "CompanyOmitStamp";
	public static final String SUBDIV_ENFORCE_OMIT_STAMP = "EnforceOmitStamp";
	public static final String SUBDIV_PRE_STAMP = "PreStamp";
	public static final String SUBDIV_REP_STAMP = "RepStamp";
	public static final String SUBDIV_GOV_DOCUMENT = "GovDocument";
	public static final String SUBDIV_MESSAGE_HEADER = "MessageHeader";
	public static final String SUBDIV_BODY_XML = "BodyXML";
	public static final String SUBDIV_BODY_XSL = "BodyXSL";
	public static final String SUBDIV_SYMBOL = "Symbol";
	public static final String SUBDIV_LOGO = "Logo";
	public static final String SUBDIV_PUB_PACK = "PubPack";
	public static final String SUBDIV_EXCH_PACK = "ExchPack";

	public static final int RECIP_LEVEL_GENERAL = 0;
	public static final int RECIP_LEVEL_DISTRIBUTE = 1;
	public static final int RECIP_LEVEL_BOTH = 2;

	public static final String FILE_METHOD_ADD = "add";
	public static final String FILE_METHOD_DEL = "del";
	public static final String FILE_METHOD_REPLACE = "replace";
	public static final String FILE_METHOD_RENAME = "rename";

	public static final String RECIP_METHOD_DEL = "del";
	public static final String RECIP_METHOD_KEEP = "keep";
	public static final String RECIP_METHOD_REPLACE = "replace";
	public static final String RECIP_METHOD_WITHDRAW = "withdraw";

	public static final String IS_POST_NO = "N";
	public static final String IS_POST_YES = "Y";
	public static final String IS_POST_DELETE = "D";

	public static final int ANNOUNCEMENT_STATUS_NO = 0;
	public static final int ANNOUNCEMENT_STATUS_WAITING = 1;
	public static final int ANNOUNCEMENT_STATUS_DONE = 2;

	public static final String ROOT_APPROVAL_LINE_NAME = "0";

	public static final String ROOT_DRAFT_APPROVAL_LINE_NAME = "0";
	public static final String ROOT_ENFORCE_APPROVAL_LINE_NAME = "1";

	public static final int FIRST_APPROVER_ORDER = 0;

	public static final int FIRST_CASE_NUMBER = 1;

	public static final String PUB_DOC_IDENTIFIER = "KORGOVERN";

	public static final long APPROVAL_EVENT_SUBMIT = 0x001L;
	public static final long APPROVAL_EVENT_WITHDRAW = 0x002L;
	public static final long APPROVAL_EVENT_INTERMEDIATE = 0x004L;
	public static final long APPROVAL_EVENT_COMPLETE = 0x008L;
	public static final long APPROVAL_EVENT_REJECT = 0x010L;
	public static final long APPROVAL_EVENT_REQUEST_COMPLETE = 0x020L;
	public static final long APPROVAL_EVENT_MANAGE_SUBMIT = 0x040L;
	public static final long APPROVAL_EVENT_MANAGE_COMPLETE = 0x080L;
	public static final long APPROVAL_EVENT_RESERVE_SUBMIT = 0x100L;
	public static final long APPROVAL_EVENT_RESERVE_APPROVAL = 0x200L;

	public static final int LEGACY_INPUT_TYPE_EDITOR = 0;
	public static final int LEGACY_INPUT_TYPE_UILESS = 1;

	public static final int LEGACY_INPUT_METHOD_HTTP_POST_FORM = 0;
	public static final int LEGACY_INPUT_METHOD_REMOTE_FILE = 1;
	public static final int LEGACY_INPUT_METHOD_LOCAL_FILE = 2;

	public static final int LEGACY_INPUT_FORMAT_XML = 0;
	public static final int LEGACY_INPUT_FORMAT_XML_KEY = 1;
	public static final int LEGACY_INPUT_FORMAT_SOAP = 2;
	public static final int LEGACY_INPUT_FORMAT_SOAP_KEY = 3;
	public static final int LEGACY_INPUT_FORMAT_TEXT = 4;
	public static final int LEGACY_INPUT_FORMAT_TEXT_PARSE = 5;

	public static final String LEGACY_IS_SSO_YES = "Y";
	public static final String LEGACY_IS_SSO_NO = "N";

	public static final int LEGACY_OUTPUT_TYPE_SERVER_PAGE = 0;
	public static final int LEGACY_OUTPUT_TYPE_EJB = 1;
	public static final int LEGACY_OUTPUT_TYPE_CLIENT = 2;

	public static final int LEGACY_OUTPUT_METHOD_HTTP_POST_FORM = 0;
	public static final int LEGACY_OUTPUT_METHOD_HTTP_POST_SMTP_BODY = 1;
	public static final int LEGACY_OUTPUT_METHOD_HTTP_POST_REMOTE_FILE = 2;
	public static final int LEGACY_OUTPUT_METHOD_HTTP_POST_LOCAL_FILE = 3;
	public static final int LEGACY_OUTPUT_METHOD_HTTP_POST_PARAMETER_STRING = 4;

	public static final int LEGACY_OUTPUT_FORMAT_XML = 0;
	public static final int LEGACY_OUTPUT_FORMAT_XML_KEY = 1;
	public static final int LEGACY_OUTPUT_FORMAT_SOAP = 2;
	public static final int LEGACY_OUTPUT_FORMAT_SOAP_KEY = 3;
	public static final int LEGACY_OUTPUT_FORMAT_TEXT = 4;
	public static final int LEGACY_OUTPUT_FORMAT_TEXT_PARSE = 5;

	public static final String URGENCY_LEVEL_HIGH = "긴급";
	public static final String URGENCY_LEVEL_LOW = "보통";

	public static final String BOOLEAN_VALUE_YES = "Y";
	public static final String BOOLEAN_VALUE_NO = "N";

	public static final String TRANSFER_SEED_PREFIX = "TRANS:";
	
	public static final int APPROVE_CABINET_TYPE_WAIT = 0;				// 대기함
	public static final int APPROVE_CABINET_TYPE_PROCESS = 1;			// 진행함
	public static final int APPROVE_CABINET_TYPE_COMPLETE = 2;			// 완료함
	public static final int APPROVE_CABINET_TYPE_SUBMIT = 3;			// 기안함
	public static final int APPROVE_CABINET_TYPE_REJECT = 4;			// 반려함
	public static final int APPROVE_CABINET_TYPE_PRIVATE = 5;			// 개인함
	public static final int APPROVE_CABINET_TYPE_AFTERAPPROVE = 6;		// 후열함
	public static final int APPROVE_CABINET_TYPE_DISCARD = 7;			// 폐기함
	public static final int APPROVE_CABINET_TYPE_DEPTWAIT = 8;			// 부서대기함
}
