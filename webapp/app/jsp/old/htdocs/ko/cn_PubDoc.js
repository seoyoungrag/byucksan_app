	var PUBDOC_PATH = "pubdoc\\";
	var BODY_XML_FILE = "BODYDOCUMENT.xml";
	var PUBDOC_XML_FILE = "extract.xml";
	var UNPACK_PUBDOC_FILE = "pubdoc.xml";

	var ACK_DTD_VERSION = "2.0";
	var ACK_XSL_VERSION = "2.0";

	var ACK_ACCEPT = 1;
	var ACK_RETURN = 2;
	var ACK_RESEND = 3;
	var ACK_REQ_RESEND = 4;
	
	var SERIAL_NUMBER_LENGTH = 6;

	var ACK_ACCEPT_STRING = "accept";
	var ACK_RETURN_STRING = "return";
	var ACK_RESEND_STRING = "resend";
	var ACK_REQ_RESEND_STRING = "req-resend";

	var ALERT_ERROR_OCCUR = "PubDoc 생성중에 오류가 발생하였습니다.";
	var ALERT_ERROR_OCCUR_PACK = "PubPack 생성중에 오류가 발생하였습니다.";
	var ALERT_ERROR_OCCUR_ACK = "ACK 메세지 생성중에 오류가 발생하였습니다.";
	var ALERT_ERROR_OCCUR_FAIL_SAVE_BODY_TO_XML = "본문을 공문서 본문구조의 XML 파일로 변환하지 못하였습니다.";
	var ALERT_ERROR_OCCUR_FAIL_SAVE_OPINION_TO_XML = "의견을 공문서 본문구조의 XML 파일을 변환하지 못하였습니다.";
	var ALERT_ERROR_OCCUR_CHECK_YOUR_HWPPUBDOCPATCH_TOSAVE = "HWP 공문서 본문저장용 패치를 확인하시기 바랍니다.";
	var ALERT_ERROR_OCCUR_NOT_EXIST_SYMBOL = "기관 심볼이미지가 존재하지 않습니다.";
	var ALERT_ERROR_OCCUR_FAIL_SET_ASSIST_INFO = "Assist 정보가 올바르지 않습니다.";
	var ALERT_ERROR_OCCUR_FAIL_SET_APPROVER_INFO = "결재자 정보가 올바르지 않습니다.";

	var FORM_NAME_ORGAN = "기관명";
	var FORM_NAME_TITLE = "제목";
	var FORM_NAME_RECIPIENT = "수신";
	var FORM_NAME_RECIPIENT_REF = "참조";
	var FORM_NAME_VIA = "경유";
	var FORM_NAME_RECIPIENT_REFER = "수신처참조#1";
	var FORM_NAME_SENDERNAME = "발신명의#1";
	var FORM_VALUE_RECIPIENT_REFER = "수신자 참조";
	var FORM_NAME_COMPANYSTAMP = "관인";
	var FORM_NAME_OMITCOMPANYSTAMP = "관인생략";
	var FORM_NAME_REGNUMBER = "시행번호";
	var FORM_NAME_ENFORCEDATE = "시행일자";
	var FORM_NAME_NUMBER = "접수번호";
	var FORM_NAME_DATE = "접수일자";
	var FORM_NAME_ZIPCODE = "우편번호";
	var FORM_NAME_ADDRESS = "주소";
	var FORM_NAME_HOMEURL = "홈페이지";
	var FORM_NAME_TELPREFIX = "전화지역";
	var FORM_NAME_TELEPHONE = "전화번호";
	var FORM_NAME_FAXPREFIX = "전송지역";
	var FORM_NAME_FAX = "전송";
	var FORM_NAME_EMAIL = "전자우편";
	var FORM_NAME_PUBLICATION = "공개여부";
	var FORM_NAME_SYMBOL = "심볼";
	var FORM_NAME_LOGO = "로고";
	var FORM_NAME_HEADCAMPAIGN = "머리말";
	var FORM_NAME_FOOTCAMPAIGN = "꼬리말";

	var PUBLICATION_1 = "공개";
	var PUBLICATION_2 = "부분공개";
	var PUBLICATION_3 = "비공개";
	
	var ALT_SYMBOL = "심볼";
	var ALT_LOGO = "로고";

	var APPROVER_TYPE_0 = "1인결재";
	var APPROVER_TYPE_1 = "기안";
	var APPROVER_TYPE_2 = "검토";
	var APPROVER_TYPE_3 = "협조";
	var APPROVER_TYPE_5 = "결재";
	var APPROVER_TYPE_6 = "전결";
	var APPROVER_TYPE_7 = "대결";

	var APPROVER_ROLE_0 = "0";
	var APPROVER_ROLE_1 = "1";
	var APPROVER_ROLE_2 = "2";
	var APPROVER_ROLE_3 = "3";
	var APPROVER_ROLE_5 = "5";
	var APPROVER_ROLE_6 = "6";
	var APPROVER_ROLE_7 = "7";
	
	var STAMP_COMPANY_OMITSTAMP = "관인생략";

	var HAVE_OPINION = "의견(첨부)";
