<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.approval.util.ApprovalUtil" %>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppLineVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.PubReaderVO" %>
<%@ page import="com.sds.acube.app.env.vo.FormVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@ page import="java.util.List" %>
<%
/** 
 *  Class Name  : button.jsp 
 *  Description : 버튼공통처리 
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
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디

	String opt321 = appCode.getProperty("OPT321", "OPT321", "OPT"); // 관련문서사용여부 - Y : 사용, N : 사용안함	
	//관련문서 button 비활성화 - 추후 사용을 위해 처리
	//opt321 = envOptionAPIService.selectOptionValue(compId, opt321);
	opt321 = "N";
	String opt322 = appCode.getProperty("OPT322", "OPT322", "OPT"); // PDF파일 저장권한 - 1 : 문서과 문서관리책임자, 2 : 모든사용자
	opt322 = envOptionAPIService.selectOptionValue(compId, opt322);
	String opt334 = appCode.getProperty("OPT334", "OPT334", "OPT"); // 공람사용여부 - Y : 사용, N : 사용안함
	opt334 = envOptionAPIService.selectOptionValue(compId, opt334);
	String opt344 = appCode.getProperty("OPT344", "OPT344", "OPT"); // 관련규정사용여부 - Y : 사용, N : 사용안함
	opt344 = envOptionAPIService.selectOptionValue(compId, opt344);
	String opt348 = appCode.getProperty("OPT348", "OPT348", "OPT"); // 거래처사용여부 - Y : 사용, N : 사용안함
	opt348 = envOptionAPIService.selectOptionValue(compId, opt348);
	String opt350 = appCode.getProperty("OPT350", "OPT350", "OPT"); // 일괄기안 사용여부 - Y : 사용, N : 사용안함
	opt350 = envOptionAPIService.selectOptionValue(compId, opt350);
	String opt412 = appCode.getProperty("OPT412", "OPT412", "OPT"); // 반려문서등록대장등록  // jth8172 2012 신결재 TF
	opt412 = envOptionAPIService.selectOptionValue(compId, opt412);
	String opt380 = appCode.getProperty("OPT380", "OPT380", "OPT"); // 감사대상문서,감사문서 별도 사용여부, jkkim, 20120718
	opt380 = envOptionAPIService.selectOptionValue(compId, opt380);
	
	String opt421 = appCode.getProperty("OPT421", "OPT421", "OPT"); // 결재진행문서 회수기능 설정 - 1: 다음결재자 조회전 회수, 2 : 다음 결재자 처리전 회수, 0 : 사용안함
	opt421 = envOptionAPIService.selectOptionValue(compId, opt421);
	
	// 버튼명
	String submitBtn = messageSource.getMessage("approval.button.submit", null, langType); // 상신
	String redraftBtn = messageSource.getMessage("approval.button.redraft", null, langType); // 재기안
	String deleteBtn = messageSource.getMessage("approval.button.delete", null, langType); // 삭제
	String referdraftBtn = messageSource.getMessage("approval.button.referdraft", null, langType); // 참조기안
	String approvalBtn = messageSource.getMessage("approval.button.approval", null, langType); // 결재
	String returnBtn = messageSource.getMessage("approval.button.return", null, langType); // 반려

	String agreeBtn = messageSource.getMessage("approval.button.agree", null, langType); // 찬성
	String disagreeBtn = messageSource.getMessage("approval.button.disagree", null, langType); // 반대

	String withdrawBtn = messageSource.getMessage("approval.button.withdraw", null, langType); // 회수
	String savetempBtn = messageSource.getMessage("approval.button.savetemp", null, langType); // 임시저장
	String holdoffBtn = messageSource.getMessage("approval.button.holdoff", null, langType); // 보류
	String pcdocBtn = messageSource.getMessage("approval.button.open.pcdoc", null, langType); // PC문서
	String pubreaderBtn = messageSource.getMessage("approval.button.pubreader", null, langType); // 공람자
	String pubreadBtn = messageSource.getMessage("approval.button.pubread", null, langType); // 공람
	String readafterBtn = messageSource.getMessage("approval.button.readafter", null, langType); // 후열	
	String informBtn = messageSource.getMessage("approval.button.inform", null, langType); // 통보  // jth8172 2012 신결재 TF	
	String docinfoBtn = messageSource.getMessage("approval.button.docinfo", null, langType); // 문서정보
	String relateddocBtn = messageSource.getMessage("approval.button.relateddoc", null, langType); // 관련문서
	String relatedruleBtn = messageSource.getMessage("approval.button.relatedrule", null, langType); // 관련규정
	String customerBtn = messageSource.getMessage("approval.button.customer", null, langType); // 거래처
	String summaryBtn = messageSource.getMessage("approval.button.summary", null, langType); // 요약전
	String applineBtn = messageSource.getMessage("approval.button.appline", null, langType); // 결재경로
	String receiverBtn = messageSource.getMessage("approval.button.receiver", null, langType); // 수신자
	String readerBtn = messageSource.getMessage("approval.button.postreader", null, langType); // 열람자
	String saveBtn = messageSource.getMessage("approval.button.save", null, langType); // 저장
	String saveHwpBtn = messageSource.getMessage("approval.button.savehwp", null, langType); // 본문저장
	String savePdfBtn = messageSource.getMessage("approval.button.savepdf", null, langType); // PDF저장
	String sendMailBtn = messageSource.getMessage("approval.button.sendmail", null, langType); // 사내우편
	String saveAllBtn = messageSource.getMessage("approval.button.saveall", null, langType); // 본문/첨부 저장
	String printBtn = messageSource.getMessage("approval.button.print", null, langType); // 인쇄
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기
	String appendItemBtn = messageSource.getMessage("approval.button.append.item", null, langType); // 안추가
	String removeItemBtn = messageSource.getMessage("approval.button.remove.item", null, langType); // 안삭제
	String adminEdityBodyBtn = messageSource.getMessage("approval.button.modifybody", null, langType); // 본문수정
	String adminConfirmEdityBodyBtn = messageSource.getMessage("approval.button.confirm.modifybody", null, langType); // 본문수정확인
	String adminCancelEdityBodyBtn = messageSource.getMessage("approval.button.cancel.modifybody", null, langType); // 본문수정취소
	String adminChangeBodyBtn = messageSource.getMessage("approval.button.changebody", null, langType); // 본문수정(파일변경)
	String adminEdityAttachBtn = messageSource.getMessage("approval.button.modifyattach", null, langType); // 첨부수정
	String adminEditDocInfoBtn = messageSource.getMessage("approval.button.modifydocinfo", null, langType); // 문서정보수정
	String adminEditApplineBtn = messageSource.getMessage("approval.button.modifyappline", null, langType); // 결재경로수정
	String adminSendToDocBtn = messageSource.getMessage("approval.button.sendtodoc", null, langType); // 문서관리로 보내기
	String regRejectBtn = messageSource.getMessage("approval.button.regreject", null, langType); // 반려문서 대장등록   // jth8172 2012 신결재 TF
	String auditOriginDocBtn = messageSource.getMessage("approval.button.auditorigidocview", null, langType); // 감사문서 원문보기   // jkkim 20120720

	String roleCode = (String) session.getAttribute("ROLE_CODES"); // 역할코드
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
	String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role"); //문서과 문서 담당자
	boolean adminstratorFlag = (roleCode.indexOf(roleId10) == -1) ? false : true;
	boolean docManagerFlag = (roleCode.indexOf(roleId12) == -1) ? false : true; 
	boolean procManagerFlag = (roleCode.indexOf(roleId11) == -1) ? false : true; 

	String dct498 = AppConfig.getProperty("form498", "DCT498", "formcode");

	// 함종류
	String lob000 = appCode.getProperty("LOB000", "LOB000", "LOB");	// 새기안 - InsertAppDoc
	String lob001 = appCode.getProperty("LOB001", "LOB001", "LOB");	// 임시저장함 - SelectTemporary
	String lob002 = appCode.getProperty("LOB002", "LOB002", "LOB");	// 연계기안함 - SelectTemporary
	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB");	// 결재대기함 - SelectAppDoc
	String lob004 = appCode.getProperty("LOB004", "LOB004", "LOB");	// 진행문서함 - SelectAppDoc
	String lob009 = appCode.getProperty("LOB009", "LOB009", "LOB");	// 기안문서함 - SelectAppDoc
	String lob010 = appCode.getProperty("LOB010", "LOB010", "LOB");	// 결재완료함 - SelectAppDoc
	String lob012 = appCode.getProperty("LOB012", "LOB012", "LOB");	// 공람문서함 - SelectAppDoc
	String lob013 = appCode.getProperty("LOB013", "LOB013", "LOB");	// 후열문서함 - SelectAppDoc
	String lob014 = appCode.getProperty("LOB014", "LOB014", "LOB");	// 검사부열람함 - SelectAppDoc
	String lob015 = appCode.getProperty("LOB015", "LOB015", "LOB");	// 임원문서함 - SelectAppDoc
	String lob024 = appCode.getProperty("LOB024", "LOB024", "LOB");	// 통보문서함 - SelectAppDoc
	String lob031 = appCode.getProperty("LOB031", "LOB031", "LOB");	// 공람게시 - DisplayAppDoc
	String lob099 = appCode.getProperty("LOB099", "LOB099", "LOB");	// 관리자전체목록 - DisplayAppDoc
	String lol001 = appCode.getProperty("LOL001", "LOL001", "LOL");	// 문서등록대장 - SelectAppDoc
	String lol003 = appCode.getProperty("LOL003", "LOL003", "LOL");	// 문서등록대장 - SelectAppDoc
	String lol005 = appCode.getProperty("LOL005", "LOL005", "LOL");	// 직인날인대장 - DisplayAppDoc

	String app100 = appCode.getProperty("APP100", "APP100", "APP"); // 기안대기(회수)
	String app110 = appCode.getProperty("APP110", "APP110", "APP"); // 기안대기(반려문서)	
	String app200 = appCode.getProperty("APP200", "APP200", "APP"); // 검토대기
	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서

	// jth8172 2012 신결재 TF
	String app360 = appCode.getProperty("APP360", "APP360", "APP"); // 합의대기
	String app361 = appCode.getProperty("APP361", "APP361", "APP"); // 부서합의대기
	String app362 = appCode.getProperty("APP362", "APP362", "APP"); // 부서합의대기(검토)
	String app365 = appCode.getProperty("APP365", "APP365", "APP"); // 부서합의대기(결재)
	String app370 = appCode.getProperty("APP370", "APP370", "APP"); // 부서합의대기(TEXT본문)-연계
	String app401 = appCode.getProperty("APP401", "APP401", "APP"); // 부서감사대기
	String app402 = appCode.getProperty("APP402", "APP402", "APP"); // 부서감사(검토)
	String app405 = appCode.getProperty("APP405", "APP405", "APP"); // 부서감사(결재)

	
	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	String apt014 = appCode.getProperty("APT014", "APT014", "APT"); // 부재미처리
	
	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성 // jth8172 2012 신결재 TF
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대 // jth8172 2012 신결재 TF
	
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art030 = appCode.getProperty("ART030", "ART030", "ART"); // 협조
	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art032 = appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
	String art033 = appCode.getProperty("ART033", "ART033", "ART"); // 협조(기안)
	String art034 = appCode.getProperty("ART034", "ART034", "ART"); // 협조(검토)
	String art035 = appCode.getProperty("ART035", "ART035", "ART"); // 협조(결재)

	// jth8172 2012 신결재 TF
	String art130 = appCode.getProperty("ART130", "ART130", "ART"); // 합의
	String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의
	String art132 = appCode.getProperty("ART132", "ART132", "ART"); // 부서합의
	String art133 = appCode.getProperty("ART133", "ART133", "ART"); // 합의(기안)
	String art134 = appCode.getProperty("ART134", "ART134", "ART"); // 합의(검토)
	String art135 = appCode.getProperty("ART135", "ART135", "ART"); // 합의(결재)	

	
	
	String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
	String itemnum = (String) request.getAttribute("itemnum");
	String lobCode = CommonUtil.nullTrim((String) request.getAttribute("lobCode")); // 문서함코드

	List<AppDocVO> appDocVOs = (List<AppDocVO>) request.getAttribute("appDocVOs");
	AppDocVO appDocVO = new AppDocVO();

	int docCount = 0;
	String docState = "APP000";
	String deleteYn = "N";
	String editbodyYn = "Y";
	String transferYn = "N";
	String docType = "";
	String bizSystemCode = "";
	if (appDocVOs == null || appDocVOs.size() == 0) {
		FormVO formVO = (FormVO) request.getAttribute("formVO");
		if (formVO != null) {
		    editbodyYn = formVO.getEditbodyYn();
		    docType = formVO.getCategoryId();
		}
	} else {
	    docCount = appDocVOs.size();
	    appDocVO = appDocVOs.get(0);
		docState = appDocVO.getDocState();
		deleteYn = appDocVO.getDeleteYn();
		if (Integer.parseInt(itemnum) <= docCount) {
			editbodyYn = appDocVOs.get(Integer.parseInt(itemnum) - 1).getEditbodyYn();
			docType = appDocVOs.get(Integer.parseInt(itemnum) - 1).getDocType();
		} else {
			editbodyYn = appDocVO.getEditbodyYn();
			docType = appDocVO.getDocType();
		}
		transferYn = appDocVO.getTransferYn();
		bizSystemCode = appDocVO.getBizSystemCode();
	}
	
	boolean receiverFlag = true;	//수신자 지정 flag

	boolean currentUserFlag = false;
	boolean withdrawFlag = false;
	if (lob003.equals(lobCode)) {
		if (docCount > 0) {
		    List<AppLineVO> appLineVOs = appDocVO.getAppLine();
		    AppLineVO currentUser = ApprovalUtil.getCurrentUser(appLineVOs, userId, apt003);
		    if (currentUser == null) {
			    currentUser = ApprovalUtil.getCurrentUser(appLineVOs, userId, apt004);
			    if (currentUser == null) {
					String userDeptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
					currentUser = ApprovalUtil.getCurrentApprover(appLineVOs);
					if (currentUser != null && userDeptId.equals(currentUser.getApproverDeptId()) && "".equals(CommonUtil.nullTrim(currentUser.getApproverId()))) {
						String pdocManager = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
						if (roleCode.indexOf(pdocManager) != -1) {
							currentUserFlag = true;
						}
					}
			    } else {
					currentUserFlag = true;
			    }
		    } else {
				currentUserFlag = true;
		    }
		    if (currentUser != null) {
				String askType = currentUser.getAskType();
				if (art020.equals(askType)) {
				    /* approvalBtn = messageSource.getMessage("approval.button.consider", null, langType);	// 검토 */
				    approvalBtn = messageSource.getMessage("approval.button.approval", null, langType);	// 15.10.28 검토자도 결재로 변경
				} else if (art030.equals(askType) || art031.equals(askType) || art032.equals(askType) || art033.equals(askType) 
					|| art034.equals(askType) || art035.equals(askType)) {
				    approvalBtn = messageSource.getMessage("approval.button.assistant", null, langType);	// 협조
				}
		    }
		}
	} else if (lob004.equals(lobCode)) {
		if (docCount > 0) {
		    List<AppLineVO> appLineVOs = appDocVO.getAppLine();
		    AppLineVO currentUser = ApprovalUtil.getCurrentUser(appLineVOs, userId, apt001);
		    if (currentUser != null) {
			    boolean absentFlag = true;
			    while (absentFlag) {
				    List<AppLineVO> nextUsers = ApprovalUtil.getNextApprovers(appLineVOs, currentUser);
				    if (nextUsers == null || nextUsers.size() == 0) {
						break;
			    	}
				    int nextCount = nextUsers.size();
				    for (int loop = 0; loop < nextCount; loop++) {
						AppLineVO nextUser = nextUsers.get(loop);
						/*옵션화, 회수 옵션에 따른 회수 시점 지정, jd.park, 20120503 S*/
						//다음 결재자 처리전 회수(OPT421 : 2)
						if (opt421.equals("2")) {
						    if (apt003.equals(nextUser.getProcType()) && "9999-12-31 23:59:59".equals(nextUser.getProcessDate())) {
							    withdrawFlag = true;
							    absentFlag = false;
							} else {
							    if (apt014.equals(nextUser.getProcType())) {
									currentUser = nextUser;
							    } else {
									absentFlag = false;
									withdrawFlag = false;
									break;				    
							    }
							}
						//다음 결재자 조회전 회수(OPT421 : 1)
						} else if (opt421.equals("1")) {
							if (apt003.equals(nextUser.getProcType()) && "9999-12-31 23:59:59".equals(nextUser.getReadDate())) {
							    withdrawFlag = true;
							    absentFlag = false;
							} else {
							    if (apt014.equals(nextUser.getProcType())) {
									currentUser = nextUser;
							    } else {
									absentFlag = false;
									withdrawFlag = false;
									break;				    
							    }
							}
						//회수 사용안함(OPT421 : 0)
						}else{
							if (apt014.equals(nextUser.getProcType())) {
								currentUser = nextUser;
							} else {
								absentFlag = false;
								withdrawFlag = false;
								break;				    
							}
						}
						/*옵션화, 회수 옵션에 따른 회수 시점 지정, jd.park, 20120503 E*/
				    }
			    }
		    }
		    if (userId.equals(appDocVO.getDrafterId()) && !"".equals(bizSystemCode)) {
				withdrawFlag = false;
		    }
		}
	}

%>
