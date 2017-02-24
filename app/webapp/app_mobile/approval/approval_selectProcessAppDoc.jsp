<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>       
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.common.util.DateUtil" %>
<%@ page import="com.sds.acube.app.approval.util.ApprovalUtil" %>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppOptionVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppLineVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppRecvVO" %>
<%@ page import="com.sds.acube.app.approval.vo.RelatedDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.RelatedRuleVO" %>
<%@ page import="com.sds.acube.app.approval.vo.CustomerVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.StorFileVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.SendInfoVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.PubReaderVO" %>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil" %>
<%@ page import="com.sds.acube.app.env.vo.FormEnvVO" %>
<%@ page import="com.sds.acube.app.env.vo.FormVO" %>
<%@ page import="org.anyframe.util.StringUtil"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>  
<%
	// 본문문서 타입 설정
	String strBodyType = "hwp";
	String D1 = (String) request.getParameter("D1");
	
	String userUid = (String)session.getAttribute("USER_ID");
	String iam_url = AppConfig.getProperty("iam_url", "", "organization");
	//2016-01-13
	String opinion = CommonUtil.nullTrim((String)request.getAttribute("opinion"));
	
	String roleCode = (String) session.getAttribute("ROLE_CODES"); // 역할코드
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	
	String uploadPath = AppConfig.getProperty("upload_temp", "", "attach");
	
	boolean adminstratorFlag = (roleCode.indexOf(roleId10) == -1) ? false : true;

	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디

	String OPT404 = appCode.getProperty("OPT404", "OPT404", "OPT"); // 비공개사유입력 // jth8172 2012 신결재 TF
	String ReasonUseYN = envOptionAPIService.selectOptionValue(compId, OPT404);

	String OPT406 = appCode.getProperty("OPT406", "OPT406", "OPT"); // 본문에 수신자기호표시여부 // jth8172 2012 신결재 TF
	String RecvSymbolUseYN = envOptionAPIService.selectOptionValue(compId, OPT406);
	String OPT407 = appCode.getProperty("OPT407", "OPT407", "OPT"); // 본문에 수신자부서장직위표시여부 // jth8172 2012 신결재 TF
	String RecvChiefUseYN = envOptionAPIService.selectOptionValue(compId, OPT407);
	
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	String opt303 = appCode.getProperty("OPT303", "OPT303", "OPT"); // 부서협조 - 1 : 최종협조자, 2 : 모든협조자
	opt303 = envOptionAPIService.selectOptionValue(compId, opt303);
	String opt304 = appCode.getProperty("OPT304", "OPT304", "OPT"); // 감사표시 - 1 : 결재라인, 2 : 협조라인, 3 : 감사라인	
	opt304 = envOptionAPIService.selectOptionValue(compId, opt304);
	String opt314 = appCode.getProperty("OPT314", "OPT314", "OPT");
	opt314 = envOptionAPIService.selectOptionValue(compId, opt314);
	String opt320 = appCode.getProperty("OPT320", "OPT320", "OPT"); // CEO 결재문서 감사
	opt320 = envOptionAPIService.selectOptionValue(compId, opt320);
	String opt343 = appCode.getProperty("OPT343", "OPT343", "OPT"); // 모바일사용여부 - Y : 사용, N : 사용안함
	opt343 = envOptionAPIService.selectOptionValue(compId, opt343);
	String opt346 = appCode.getProperty("OPT346", "OPT346", "OPT"); // 감사사용여부 - Y : 사용, N : 사용안함
	opt346 = envOptionAPIService.selectOptionValue(compId, opt346);
	String opt357 = appCode.getProperty("OPT357", "OPT357", "OPT"); // 결재 처리 후 문서 자동닫기 - Y : 사용, N : 사용안함
	opt357 = envOptionAPIService.selectOptionValue(compId, opt357);

	String opt412 = appCode.getProperty("OPT412", "OPT412", "OPT"); // 반려문서등록대장등록  // jth8172 2012 신결재 TF
	opt412 = envOptionAPIService.selectOptionValue(compId, opt412);
	
	String opt411 = appCode.getProperty("OPT411", "OPT411", "OPT"); //보안 - 1 : 로그인패스워드, 2 : 비밀번호
	opt411 = envOptionAPIService.selectOptionValue(compId, opt411);
	
	String opt321 = appCode.getProperty("OPT321", "OPT321", "OPT"); // 관련문서 사용유무, jd.park, 20120504
	opt321 = envOptionAPIService.selectOptionValue(compId, opt321);
	
	String opt380 = appCode.getProperty("OPT380", "OPT380", "OPT"); // 감사대상문서,감사문서 별도 사용여부, jkkim, 20120718
	opt380 = envOptionAPIService.selectOptionValue(compId, opt380);
	
	//대내문서자동발송여부  // jth8172 2012 신결재 TF
	String opt413 = appCode.getProperty("OPT413", "OPT413", "OPT");	
	String autoInnerSendYn = envOptionAPIService.selectOptionValue(compId, opt413);
 
	//자동발송시날인방법 (1:부서서명인, 2:생략인 3:최종결재자 서명)	  // jth8172 2012 신결재 TF
	String opt414 = appCode.getProperty("OPT414", "OPT414", "OPT");	
	String autoSealType = envOptionAPIService.selectOptionValue(compId, opt414);
	
	/*옵션 추가, 문서분류체계 및 편철 사용 유무 , jd.park, 20120509 S*/
	String opt422 = appCode.getProperty("OPT422", "OPT422", "OPT"); //문서분류체계 사용유무 
	opt422 = envOptionAPIService.selectOptionValue(compId, opt422);
	
	String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT"); //편철 사용유무
	opt423 = envOptionAPIService.selectOptionValue(compId, opt423);
	/*옵션 추가, 문서분류체계 및 편철 사용 유무 , jd.park, 20120509 E*/

	String det002 = appCode.getProperty("DET002", "DET002", "DET"); // 대내
	String det003 = appCode.getProperty("DET003", "DET003", "DET"); // 대외
	String det004 = appCode.getProperty("DET004", "DET004", "DET"); // 대내외
    String det007 = appCode.getProperty("DET007", "DET007", "DET"); // 민원인
	String dru002 = appCode.getProperty("DRU002", "DRU002", "DRU");
    
    String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT");
    String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT");
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
	String aft014 = appCode.getProperty("AFT014", "AFT014", "AFT"); // 감사기안 본문 added by jkkim 

	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류

	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성 // jth8172 2012 신결재 TF
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대 // jth8172 2012 신결재 TF

	String app100 = appCode.getProperty("APP100", "APP100", "APP"); // 기안대기
	String app110 = appCode.getProperty("APP110", "APP110", "APP"); // 기안대기(반려문서)
	String app200 = appCode.getProperty("APP200", "APP200", "APP"); // 검토대기
	String app250 = appCode.getProperty("APP250", "APP250", "APP"); // 검토대기(TEXT본문)-연계
	String app300 = appCode.getProperty("APP300", "APP300", "APP"); // 협조대기
	String app301 = appCode.getProperty("APP301", "APP301", "APP"); // 부서협조대기
	String app350 = appCode.getProperty("APP350", "APP350", "APP"); // 협조대기(TEXT본문)-연계
	String app351 = appCode.getProperty("APP351", "APP351", "APP"); // 부서협조대기(TEXT본문)-연계
	String app401 = appCode.getProperty("APP401", "APP401", "APP"); // 부서감사대기 added by jkkim : 감사옵션추가
	String app402 = appCode.getProperty("APP402", "APP402", "APP"); // 부서감사(검토) added by jkkim : 감사옵션추가
	String app405 = appCode.getProperty("APP405", "APP405", "APP"); // 부서감사(결재) added by jkkim : 감사옵션추가
	String app500 = appCode.getProperty("APP500", "APP500", "APP"); // 결재대기
	String app550 = appCode.getProperty("APP550", "APP550", "APP"); // 결재대기(TEXT본문)-연계
	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	String app610 = appCode.getProperty("APP610", "APP610", "APP"); // 발송대기 
	
	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art053 = appCode.getProperty("ART053", "ART053", "ART"); // 1인전결
	
	String dts002 = appCode.getProperty("DTS002", "DTS002", "DTS"); // 참조기안
	String dts003 = appCode.getProperty("DTS003", "DTS003", "DTS"); // 반려문서 재기안
	String dts004 = appCode.getProperty("DTS004", "DTS004", "DTS"); // 이송문서 기안 added by jkkim
	
	String dct497 = AppConfig.getProperty("form497", "DCT497", "formcode");
	String dct498 = AppConfig.getProperty("form498", "DCT498", "formcode");
	String dct499 = AppConfig.getProperty("form499", "DCT499", "formcode");

	String obt001 = appCode.getProperty("OBT001", "OBT001", "OBT"); // 그룹웨어
	String wkt001 = appCode.getProperty("WKT001", "WKT001", "WKT"); // 여신

	// 함종류
	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB");	// 결재대기함 - SelectAppDoc
	String lob004 = appCode.getProperty("LOB004", "LOB004", "LOB");	// 진행문서함 - SelectAppDoc
	String lol007 = appCode.getProperty("LOL007", "LOL007", "LOL");	// 일상감사대장 - SelectAppDoc
	
	String enf610 = appCode.getProperty("ENF610", "ENF610", "ENF"); // 이송중인 문서
	String enf620 = appCode.getProperty("ENF620", "ENF620", "ENF"); // 이송완료 문서
	String enf630 = appCode.getProperty("ENF630", "ENF630", "ENF"); // 이송중인 문서
	String enf640 = appCode.getProperty("ENF640", "ENF640", "ENF"); // 이송완료 문서

	//자동발송시 날인 유형  // jth8172 2012 신결재 TF
	String spt002 = appCode.getProperty("SPT002", "SPT002", "SPT"); // 서명인
	String spt004 = appCode.getProperty("SPT004", "SPT004", "SPT"); // 서명인생략

	String lobCode = CommonUtil.nullTrim((String) request.getAttribute("lobCode")); // 문서함코드
	String result = CommonUtil.nullTrim((String) request.getAttribute("result"));
	String enfDocState = CommonUtil.nullTrim((String)request.getAttribute("enfDocState"));//이송기안-임시저장-접수문서 상태체크 by jkkim
	String appDocState = CommonUtil.nullTrim((String)request.getAttribute("appDocState"));//부서감사기능 옵션 추가 by jkkim
	String call = CommonUtil.nullTrim((String)request.getAttribute("call"));//부서감사기능 옵션 추가 by jkkim
	String isreceiveBox = CommonUtil.nullTrim((String)request.getAttribute("isreceiveBox"));

	if ("success".equals(result)) {

		int appLineNum = 4;
	    String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
		String userName = (String) session.getAttribute("USER_NAME"); // 사용자 이름
		String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
		String userDeptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서 이름
		String userDeptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
		String compName = (String) session.getAttribute("COMP_NAME"); // 사용자 소속 회사
		String ownDeptId = CommonUtil.nullTrim((String) request.getAttribute("ownDeptId")); // 소유부서
		String ownDeptName = CommonUtil.nullTrim((String) request.getAttribute("ownDeptName")); // 소유부서
		ownDeptId = "".equals(ownDeptId) ? userDeptId : ownDeptId;

		List<AppDocVO> appDocVOs = (List<AppDocVO>) request.getAttribute("appDocVOs");
		int docCount = appDocVOs.size();
	
		FormVO formVO = (FormVO) request.getAttribute("formVO");
		FormEnvVO logoEnvVO = (FormEnvVO) request.getAttribute("logo");
		FormEnvVO symbolEnvVO = (FormEnvVO) request.getAttribute("symbol");
		FileVO signFileVO = (FileVO) request.getAttribute("sign");
		ArrayList<FileVO> signList = (ArrayList<FileVO>) request.getAttribute("signList");
	
		FileVO bodyVO = (FileVO) request.getAttribute("bodyfile");
		String itemnum = (String) request.getAttribute("itemnum");
		String docType = appDocVOs.get(Integer.parseInt(itemnum) - 1).getDocType();
		int serialNumber = appDocVOs.get(Integer.parseInt(itemnum) - 1).getSerialNumber();
		String editbodyYn = appDocVOs.get(Integer.parseInt(itemnum) - 1).getEditbodyYn();
		String doubleYn = appDocVOs.get(Integer.parseInt(itemnum) - 1).getDoubleYn();
		String docState = appDocVOs.get(Integer.parseInt(itemnum) - 1).getDocState();
		String assistantLineType = StringUtil.null2str(appDocVOs.get(Integer.parseInt(itemnum) - 1).getAssistantLineType(), opt303);
		String auditLineType = StringUtil.null2str(appDocVOs.get(Integer.parseInt(itemnum) - 1).getAuditLineType(), opt304);
		String docTitle = appDocVOs.get(Integer.parseInt(itemnum) - 1).getTitle();
		//added by jkkim 기안일시 추가
		String draftDate = appDocVOs.get(Integer.parseInt(itemnum) - 1).getDraftDate();
		String drafterName = appDocVOs.get(Integer.parseInt(itemnum) - 1).getDrafterName();
		String drafterPos = appDocVOs.get(Integer.parseInt(itemnum) - 1).getDrafterPos();
		String drafterUid = appDocVOs.get(Integer.parseInt(itemnum) - 1).getDrafterId();
		
		boolean currentUserFlag = false;
		String currentAskType = "";
		String drafterDeptId = userDeptId;
		String drafterDeptName = userDeptName;
		
		
		if (lob003.equals(lobCode)) {
			if (docCount > 0) {
				
				//doubleYn 이중결재여부 Y,N
				//ART130: 합의
				//ART131: 병렬합의
				//ART132: 부서합의
				//ART030: 협조
				//ART031: 병렬협조
				//ART032: 부서협조
				//이중결재이면 이중결재로 lineOrder 확인하고, 이중결재가 아니면 협조/합의확인인지 확인한다
				//협조/합의이면 lineSubOrder가 있는지 확인하고, 있으면 사용
				//없으면 lineOrder를 사용
				//협조/합의가 아니면 lineOrder를 반환
				
			    List<AppLineVO> appLineVOs = appDocVOs.get(0).getAppLine();
				int listsize = appLineVOs.size();
				//AppLineVO userInfo = null;
				
				int cooperCount = 1 ;
				int notCooperCount = 1 ;
				String appStatus = "";
				
				//결재라인수만큼 루프
				for(int loop = 0; loop<listsize; loop ++){
				    AppLineVO appLineVO = (AppLineVO) appLineVOs.get(loop);
					//결재라인 정보와 로그인 사용자 정보가 같다면
				    if ((userId.equals(appLineVO.getApproverId()) || userId.equals(appLineVO.getRepresentativeId())) ){
						//현재 문서가 이중결재 문서인지 확인
						if(doubleYn.equals("Y")){
							appStatus = "이중결재";
							//이중 결재 문서라면 
							//linenum, lineorder 반환
							appLineNum = appLineVO.getLineOrder();
							//이중 결재 문서가 아니라면
						}else{
							//협조문서 라면 
					    	if(appLineVO.getAskType().equals("ART130") || appLineVO.getAskType().equals("ART030")){
					    		appStatus = "합의협조";
								appLineNum = cooperCount;
							//병렬 협조라면
					    	}else if(appLineVO.getAskType().equals("ART131") || appLineVO.getAskType().equals("ART031")){
								appStatus = "병렬";
			    				//서브오더 리턴
			    				appLineNum = appLineVO.getLineSubOrder();
							//결재 문서 라면 
							}else{
								//로그인 사용자가 최종 결재자라면
					    		if(appLineVO.getAskType().equals("ART050")){
					    			appStatus = "최종결재";
									//가장 마지막 검토 인덱스 반환 -> 검토인덱스 대신 라인오더를 반환.
									appLineNum = appLineVO.getLineOrder();
								//아니라면
					    		}else{
					    			appStatus = "결재진행";
									//결재 인덱스 반환
									appLineNum = notCooperCount;
					    		}
							}
						}
					//결재라인 정보와 로그인 사용자 정보가 다르다면
				    }else{
				    	//현재 문서가 이중결재 문서인지 확인
						if(doubleYn.equals("Y")){
						//이중 결재 문서가 아니라면
						}else{
							//협조문서 라면 
					    	if(appLineVO.getAskType().equals("ART130") || appLineVO.getAskType().equals("ART030")){
								cooperCount++;
							//결재 문서 라면 
					    	}else{
					    		notCooperCount++;
					    	}
						}
				    }//결재정보비교
				}//loop
				/* out.print("내appLineNum: "+appLineNum);
				out.print("   내 결재단계: "+appStatus); */
				
				/* 
				for (int loop = 0; loop<listsize; loop++){
				    AppLineVO appLineVO = (AppLineVO) appLineVOs.get(loop);
					out.print("   ");
					out.print(loop+1+"----");
					out.print(appLineVO.getApproverId());
					out.print(" : ");
					out.print(appLineVO.getAskType());
				    if ((userId.equals(appLineVO.getApproverId()) || userId.equals(appLineVO.getRepresentativeId())) ){
				    	userInfo = appLineVO;
				    	out.print("  AA");
				    	out.print(userInfo.getApproverId());
				    	out.print(userInfo.getAskType());
				    	out.print("  BB"); 
				    	appLineNum = appLineVO.getLineOrder();
						out.print("  라 인오더: "+appLineVO.getLineOrder());
				    	if(appLineVO.getAskType().equals("ART131") || appLineVO.getAskType().equals("ART031")){
							if(appLineVO.getLineSubOrder()!=0){
								appLineNum = appLineVO.getLineSubOrder();
								out.print("       서브라인오더있음: "+appLineVO.getLineSubOrder());
							}
				    	}
				    	break;
				    }
				} */
				/* if(doubleYn.equals("Y")){
					out.print("이중결재");
					 int innerLoop = 0; 
					for (int loop = 0 ; loop<listsize; loop++){
					    AppLineVO appLineVO = (AppLineVO) appLineVOs.get(loop);
					    if ((userId.equals(appLineVO.getApproverId()) || userId.equals(appLineVO.getRepresentativeId())) ){
							appLineNum = userInfo.getLineOrder();
					    }
						 if(appLineVO.getLineNum()== userInfo.getLineNum()){
							innerLoop++;
						} 
					}
					
				}else if(userInfo.getAskType().equals("ART130") || userInfo.getAskType().equals("ART030")){
					out.print("합의협조");
					if(userInfo.getLineSubOrder()!=0){
						out.print("병렬");
						appLineNum = userInfo.getLineSubOrder();
						out.print("병렬번호:"+appLineNum);
						out.print("병렬번호끝");
					}else{
						 int innerLoop = 0; 
						for (int loop = 0 ; loop<listsize; loop++)
						{
						    AppLineVO appLineVO = (AppLineVO) appLineVOs.get(loop);
						    if ((userId.equals(appLineVO.getApproverId()) || userId.equals(appLineVO.getRepresentativeId()))&&appLineVO.getAskType().equals(userInfo.getAskType()) )
						    {
								appLineNum = userInfo.getLineOrder();
								appLineNum = innerLoop;
						    }
							if(appLineVO.getAskType().equals("ART130") || appLineVO.getAskType().equals("ART030"))
							{
								innerLoop++;
							} 
						}
					}
				}else{
					out.print("일반결재");
					for (int loop = 0 ; loop<listsize; loop++){
					    AppLineVO appLineVO = (AppLineVO) appLineVOs.get(loop);
					    out.print("userid:"+userId);   
					    out.print(" appId:)"+appLineVO.getApproverId()+", "+appLineVO.getRepresentativeId()+"   ");
					    if ((userId.equals(appLineVO.getApproverId()) || userId.equals(appLineVO.getRepresentativeId())) ){
							appLineNum = appLineVO.getLineOrder();
						}
					}
				} */
				//out.print("내 appLineNum:"+appLineNum);
			   
			    drafterDeptId = ApprovalUtil.getDrafter(appLineVOs).getApproverDeptId();
			    drafterDeptName = ApprovalUtil.getDrafter(appLineVOs).getApproverDeptName();
			    AppLineVO currentUser = ApprovalUtil.getCurrentUser(appLineVOs, userId, apt003);
			    if (currentUser == null) {
				    currentUser = ApprovalUtil.getCurrentUser(appLineVOs, userId, apt004);
				    if (currentUser == null) {
						currentUser = ApprovalUtil.getCurrentApprover(appLineVOs);
						if (currentUser != null && userDeptId.equals(currentUser.getApproverDeptId()) && "".equals(CommonUtil.nullTrim(currentUser.getApproverId()))) {
							String pdocManager = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
							if (roleCode.indexOf(pdocManager) != -1) {
								currentUserFlag = true;				    
							}
							currentAskType = currentUser.getAskType();
						}
				    } else {
						currentUserFlag = true;
						currentAskType = currentUser.getAskType();
				    }
			    } else {
					currentUserFlag = true;
					currentAskType = currentUser.getAskType();	
			    }
			}
		}
		
		
		Map hwpTransData = (Map)request.getAttribute("hwpTransData");
		List hwpImageList = (List)hwpTransData.get("imagePath");
		
		/* if(hwpTransData!=null){
			
		} */
		
%>
<!DOCTYPE HTML>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
<meta http-equiv="expires" content="-1"/>
<meta http-equiv="Cache-Control" content="No-Cache"/>
<meta http-equiv="Pragma" content="No-Cache"/>
<title>벽산 커뮤니티</title>
<link rel="shortcut icon" href="favicon.ico">
<link rel="apple-touch-icon-precomposed" href="icon57.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="icon114.png">
<script type="text/javascript" charset="utf-8" src="<%=webUri%>/app/ref/mobile/js/libs/jquery-2.0.2.min.js"></script><!-- jQuery -->
<script type="text/javascript" src="<%=webUri%>/app/ref/mobile/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=webUri%>/app/ref/mobile/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/mobile/js/iscroll.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/mobile/js/jquery.panzoom.js"></script>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/mobile/css/common.css">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/mobile/css/button.css">
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/mobile/css/community/style.css">
<script>
	$(document).ready(function() { initialize();});
	/* $(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });
	$.ajaxSetup({async:false}); */
	var docId = '<%=appDocVOs.get(Integer.parseInt(itemnum) - 1).getDocId()%>';
	var d1 = "<%=D1%>";
	<%-- var opinion = "<%=D1%>"; --%>
	
	$(function() {
		(function() {
	        var $section = $('section').first();
	          $section.find('.panzoom').panzoom({
	          $reset: $section.find(".reset"),
	          startTransform: 'scale(1.0)',
	          minScale: 1,
	          contain: 'invert'
	        })
	      })();
	});
	
	function initialize() {
		//requestDocImage();
		$("#opinionArea").hide();
	}
	
	var reRequestCnt = 0;
	
	function goApprovalBox(index){
		switch(index){
		case 0:
			document.location.href = '<%=webUri%>/app_mobile/approval/approval_receiveBox.jsp?D1='+d1;				//수신함
			break;
		case 1:
			document.location.href = '<%=webUri%>/app_mobile/approval/approval_processBox.jsp?D1='+d1;				//진행함
			break;
		case 2:
			document.location.href = '<%=webUri%>/app_mobile/approval/approval_completeBox.jsp?D1='+d1;				//완료함
			break;
		case 3:
			document.location.href = '<%=webUri%>/app_mobile/approval/approval_draftBox.jsp?D1='+d1;				//기안함
			break;
		case 4:
			document.location.href = '<%=webUri%>/app_mobile/approval/approval_rearBox.jsp?D1='+d1;					//후열함
			break;
		case 5:
			document.location.href = '<%=webUri%>/app_mobile/approval/approval_rejectBox.jsp?D1='+d1;				//반려함
			break;
		case 6:
			document.location.href = '<%=webUri%>/app_mobile/approval/approval_displayNotice.jsp?readRange=DRS002&D1='+d1;				//공람게시 (부서)
			break;
		case 7:
			document.location.href = '<%=webUri%>/app_mobile/approval/approval_displayNotice.jsp?readRange=DRS005D1='+d1;				//공람게시 (회사)
			break;
		}
	}

	function makeHwpForProcessDoc(proc){
		$("#opinion").val($("#opinionText").val());		//의견 입력 창에서 입력한 정보를 input에 담는다.
		var processInfo = "";
		if(proc == 'process'){
			processInfo = "결재";
		}else{
			processInfo = "보류";
		}
		if(confirm("문서를 "+ processInfo +"처리 하시겠습니까?")){
			$.post("<%=webUri%>/app/list/webservice/approval/makeHwpForProcessDoc.do?bodyFileName=<%=bodyVO.getFileName()%>&appLineNum=<%=appLineNum%>&appLine="+$("#appLine").val()+"&doubleYn="+$("#doubleYn").val(), function(data) {
				if(data.status == '0'){
					var bodyinfo = "";
					
					for(var i=0; i < parseInt(data.totalCount); i++){
						bodyinfo = "<%=bodyVO.getFileId()%>" + String.fromCharCode(2) + data.fileName + String.fromCharCode(2) + "<%=bodyVO.getDisplayName()%>" + String.fromCharCode(2) + 
					    "AFT001" + String.fromCharCode(2) + "<%=bodyVO.getFileSize()%>" + String.fromCharCode(2) +"0" + String.fromCharCode(2) + "0" + String.fromCharCode(2) + 
					    "1" + String.fromCharCode(2) + "<%=bodyVO.getRegisterId()%>" + String.fromCharCode(2) + "<%=bodyVO.getRegisterName()%>" + String.fromCharCode(2) + "<%=bodyVO.getRegistDate()%>" + String.fromCharCode(4);
					}
					$("#bodyFile", "#approvalitem1").val(bodyinfo);
					if(proc == 'process'){
						processAppDoc();						
					}else{
						holdOffProcessDoc();
					}
				}else{
					alert('한글문서 변환 시 오류가 발생했습니다. (error code:'+data.code+')');
					/* alert('한글문서 변환 시 오류가 발생했습니다. (code:'+data.code+', description:'+data.description+')'); */
				}
			}, 'json').error(function(data) {
			});
		}
	}
	
	function processAppDoc(){
		
		/* alert($("#opinion").val()); */
		$.post("<%=webUri%>/app/list/webservice/approval/processAppDoc.do", $("#appDocForm").serialize(), function(data) {
			if (data.result == "success") {
				alert("문서를 결재 하였습니다.");
				document.location.href = '<%=webUri%>/app_mobile/approval/approval_receiveBox.jsp?D1='+d1;				//수신함
			} else {
				alert(data.message);
			}
		}, 'json').error(function(data) {
			$("#waiting").hide();
			$("#beforeprocess").show();
			screenUnblock();
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.msg.fail.processdocument'/>");
			}
		}); 
	}
	
function rejectProcessDoc(){
	$("#opinion").val($("#opinionText").val());		//의견 입력 창에서 입력한 정보를 input에 담는다.
	if(confirm("문서를 반려 처리 하시겠습니까?")){
		$.post("<%=webUri%>/app/list/webservice/approval/returnAppDoc.do", $("#appDocForm").serialize(), function(data) {
			if (data.result == "success") {
				alert("문서를 반려처리 하였습니다.");
				document.location.href = '<%=webUri%>/app_mobile/approval/approval_receiveBox.jsp?D1='+d1;				//수신함
			} else {
				alert(data.message);
			}
		}, 'json').error(function(data) {
			$("#waiting").hide();
			$("#beforeprocess").show();
			screenUnblock();
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.msg.fail.processdocument'/>");
			}
		}); 	
	}
	}	
	
	function holdOffProcessDoc(){
		
		$.post("<%=webUri%>/app/list/webservice/approval/holdoffAppDoc.do", $("#appDocForm").serialize(), function(data) {
			if (data.result == "success") {
				alert("문서를 보류처리 하였습니다.");
				document.location.href = '<%=webUri%>/app_mobile/approval/approval_receiveBox.jsp?D1='+d1;		
			} else {
				alert(data.message);
			}
		}, 'json').error(function(data) {
			$("#waiting").hide();
			$("#beforeprocess").show();
			screenUnblock();
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.msg.fail.processdocument'/>");
			}
		}); 		
	}
	
	function selectdocinfo(){
		
		$("#appDocForm")
		.attr("action","<%=webUri%>/app/list/webservice/approval/selectDocInfo.do")
		.attr('method', 'post')
		.submit();
		
	}
	
	function insertOpinion(){
	
		 $( "#opinionArea" ).animate({
			    height: "toggle"
		  }, 500, function() {
			    // Animation complete.
			  });
		
	}
	
	 function fileDownload(fileid,filename,displayname,type){

	 	 $.ajax({
				type:'post',
				timeout: 5000,
				data : {
					fileid : fileid,
					filename : filename,
					displayname : displayname,
					type : type
				},
				url:'<%=webUri%>/app/appcom/attach/downloadAttachMobile.do',
				success:function(data) {
					location.href='<%=AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")%>/ep/servlet/upload/file/serverPath/'+filename+'/'+displayname +"?compId=<%=compId%>";
				},
				error:function(data,sataus,err) {						
						alert("파일을 가져 오는데 실패 했습니다.");
				}
			});  
		
	} 
	
	 function fileUrl(filename){
		
	 }
	 
	window.onload = function() { myScroll.refresh(); };
	
	
</script>
</head>

<body>
	<header>
		<%@ include file="/app_mobile/approval/toggleMenu.jsp" %> 
	</header>

	<div class="public_sub_top">
		<div class="nav_btn"><a href="#none" ><img src="<%=webUri%>/app/ref/mobile/img/community/nav_btn.png" alt="메뉴"></a></div>
		<div class="title">전자결재</div>
		<div class="search"></div>
	</div>
	<div id="wrapper" style="overflow:hidden;">
		<div id="scroller">
			<div class="titleBar">
				<p class="subTitle">결재문서 조회</p>
			</div>
		
			<div class="btn_top">
				<span class="sl">
				<%if(isreceiveBox.equals("Y") && !appDocState.equals("APP110")){ %>
					<a href="javascript:makeHwpForProcessDoc('process');" class="btn_top_l">결재</a>
					<a href="javascript:rejectProcessDoc();" class="btn_top_l">반려</a>
					<a href="javascript:makeHwpForProcessDoc('holdOff');" class="btn_top_l">보류</a>
					<!-- <a href="#none" class="btn_top_l">문서정보</a> -->
				<% }else{%>
					<!-- <a href="#none" class="btn_top_l">문서정보</a> -->
				<%} %>
				</span>
				<span class="sr">	
					<a href="javascript:selectdocinfo();" class="btn_top_r">문서정보</a>
						<%if(isreceiveBox.equals("Y")){ %>
							<a href="javascript:insertOpinion();" class="btn_top_r">결재의견</a>
						<%} %>
					<a href="javascript:history.back(-1);" class="btn_top_r">목록</a>
		
				</span>
			</div>
		   
			<div class="sub_content">
				<ul class="list V_Limg_list Rdiv group">
					<li class="group">
						<a href="#none"><img src="<%=iam_url%>/acube/iam/identity/userinfoimage/Select.go?requestImage=picture&userId=<%=drafterUid%>" alt="이미지"/>
							<div class="rf">
								<p class="title"><%=docTitle%></p>
								<p class="status">
			 					<span><%=drafterName%> <%=drafterPos%> (<%=drafterDeptName%>)</span><span>l</span> <span><%=draftDate%></span>
								</p>
							</div>
							<div class="rsf"></div>
						</a>
					</li>
				</ul>
			</div>
			<div class="list_write" id="opinionArea">
		        <textarea placeholder="의견을 작성해 주십시오." class="input_content" id="opinionText"></textarea>
					
		             <!-- <div class="btnset_r">
						<a href="#none" class="cancel_btn">취소</a>&nbsp;
						<a href="javascript:insertopinion()" class="confirm_btn" >입력</a>
					</div> -->
		    </div>  
			<div class="list_write">
				<div class="input_option ">
					<section>
						<button style="margin-top:3px;" class="reset">원래크기</button>
						<div class="parent">
							<div class="panzoom">
								<!-- <textarea placeholder="결재원문" class="input_content"></textarea> -->
								<%
								if(hwpImageList != null){
								for(int i=0;i<hwpImageList.size();i++){
									String imgUrl = (String)hwpImageList.get(i);
								%>
									<img id="bodyImg" src="<%=AppConfig.getProperty("hwpurl", "http://211.168.82.26:8088", "mail")%>/hermes/resource/store/<%=imgUrl%>" style="width:100%; height:auto;"/>
								<%}} %>
							</div>
						</div>
					</section>
				</div>
		        <%
		        List<FileVO> fileVOs = appDocVOs.get(0).getFileInfo();
		        if(fileVOs != null && fileVOs.size() > 0){
		        %>
				
					<%
					for (int loop = 0; loop < fileVOs.size(); loop++) {
					    FileVO fileVO = fileVOs.get(loop);
					    String fileType = fileVO.getFileType();
					    if ("AFT004".equals(fileType) || "AFT010".equals(fileType)) {
					    	//int extsize = fileVO.getFileName().split("\\.").length;
					    	String[] extend = fileVO.getFileName().split("\\.");
					    	int extsize = extend.length;
					%>	
						<div class="file_attach">
							<p>첨부파일</p>
							<p class="file_attach_bg">
								<a class="input_attach"  href="#" onclick="fileDownload('<%=fileVO.getFileId()%>','<%=fileVO.getFileName()%>','<%=fileVO.getDisplayName().replaceAll(",", " ")%>','<%=fileVO.getFileType()%>'); return false;">
								<%if(extend[extsize-1].equals("xlsx")){ %>
									<img src="<%=webUri%>/app/ref/mobile/img/icon_excel.png">
									<%}else if(extend[extsize-1].equals("hwp")){%>
										<img src="<%=webUri%>/app/ref/mobile/img/icon_hwp.png">
									<%}else{ %>
										<img style="width:21px; height:30px;" src="<%=webUri%>/app/ref/mobile/img/icon_clip.png">
									<%} %>
									 <strong><%=fileVO.getDisplayName()%> (<%=fileVO.getFileSize()%>kb)</strong> <span style="float:right"><img src="<%=webUri%>/app/ref/mobile/img/icon_down.png"></span>
								</a>
								
								<%-- <a class="input_attach" name="input_attach<%=loop%>" onclick="fileDownload(this,'<%=fileVO.getFileId()%>','<%=fileVO.getFileName()%>','<%=fileVO.getDisplayName()%>','<%=fileVO.getFileType()%>');" download style="cursor:pointer;" >
				  					<img src="<%=webUri%>/app_mobile/img/icon_excel.png"> <strong><%=fileVO.getDisplayName()%> (<%=fileVO.getFileSize()%>kb)</strong> <span style="float:right"><img src="<%=webUri%>/app_mobile/img/icon_down.png"></span>
								</a> --%>
							</p>
						</div>
					<%
					    }
					}
					%>
				<%} %>
			</div>
		
			<form id="appDocForm" name="appDocForm" method="post">
				<input id="draftType" name="draftType" type="hidden" value=""/>
				<input id="sourceAppLine" name="sourceAppLine" type="hidden" value=""/>
				<input id="lobCode" name="lobCode" type="hidden" value="<%=lobCode%>"/>
			<% if ("1".equals(opt301)) { %>		
				<input id="password" name="password" type="hidden" value=""/>
				<input id="roundkey" name="roundkey" type="hidden" value=""/>
			<% } %>	
					
				<!-- 반려문서대장등록여부  // jth8172 2012 신결재 TF-->
				<input id="redraftRegYn" name="redraftRegYn" type="hidden" value="N"></input><!-- 반려문서대장등록여부 -->
			
			
				<!-- 생산문서 -->
			<%
				for (int loop = 0; loop < docCount; loop++) {
				    AppDocVO appDocVO = appDocVOs.get(loop);
					List<AppLineVO> appLineVOs = appDocVO.getAppLine();
					fileVOs = appDocVO.getFileInfo();
					List<AppRecvVO> appRecvVOs = appDocVO.getReceiverInfo();
					SendInfoVO sendInfoVO = appDocVO.getSendInfoVO();
					if (sendInfoVO == null)
					    sendInfoVO = new SendInfoVO();
					List<RelatedDocVO> relatedDocVOs = appDocVO.getRelatedDoc();
					List<RelatedRuleVO> relatedRuleVOs = appDocVO.getRelatedRule();
					List<CustomerVO> customerVOs = appDocVO.getCustomer();
					List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();
			
					String securityStartDate = (CommonUtil.nullTrim(appDocVO.getSecurityStartDate())).replaceAll("/", "");
					String securityEndDate = (CommonUtil.nullTrim(appDocVO.getSecurityEndDate())).replaceAll("/", "");
					boolean isDuration = false;		
					if(!"".equals(securityStartDate)&&!"".equals(securityEndDate))
					{
					    int nStartDate = Integer.parseInt(securityStartDate);
					    int nEndDate = Integer.parseInt(securityEndDate);
					    int nCurDate = Integer.parseInt(DateUtil.getCurrentDate("yyyyMMdd"));
						if((nCurDate >= nStartDate) && (nCurDate <= nEndDate))
						    isDuration = true;
					}
			%>		    
				<div id="approvalitem<%=(loop+1)%>" name="approvalitem">
					<input id="docId" name="docId" type="hidden" value="<%=appDocVO.getDocId()%>"></input><!-- 문서ID --> 
					<input id="compId" name="compId" type="hidden" value="<%=appDocVO.getCompId()%>"></input><!-- 회사ID --> 
					<input id="title" name="title" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getTitle())%>"></input><!-- 문서제목 -->
					<input id="tempTitle" name="tempTitle" type="hidden" value=""></input><!-- 임시본문제목 (for 본문수정이력) -->
					<input id="docType" name="docType" type="hidden" value="<%=appDocVO.getDocType()%>"></input><!-- 문서유형 --> 
					<input id="securityYn" name="securityYn" type="hidden" value="<%=appDocVO.getSecurityYn()%>"></input><!--보안문서여부 -->
					<input id="securityPass" name="securityPass" type="hidden" value="<%=appDocVO.getSecurityPass()%>"></input><!-- 문서보안 비밀번호 -->
					<input id="securityStartDate" name="securityStartDate" type="hidden" value="<%=appDocVO.getSecurityStartDate()%>"></input><!-- 문서보안 시작일 -->
					<input id="securityEndDate" name="securityEndDate" type="hidden" value="<%=appDocVO.getSecurityEndDate()%>"></input><!-- 문서보안 종료일 -->
					<input id="isDuration" name="isDuration" type="hidden" value="<%=isDuration%>"></input><!-- 문서보안 유지여부 -->
					<input id="enfType" name="enfType" type="hidden" value=<%=appDocVO.getEnfType()%>></input><!-- 시행유형 --> 
					<input id="docState" name="docState" type="hidden" value="<%=appDocVO.getDocState()%>"></input><!-- 문서상태 --> 
					<input id="deptCategory" name="deptCategory" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getDeptCategory())%>"></input><!-- 문서부서분류 --> 
					<input id="serialNumber" name="serialNumber" type="hidden" value="<%=appDocVO.getSerialNumber()%>"></input><!-- 문서일련번호 --> 
					<input id="subserialNumber" name="subserialNumber" type="hidden" value="<%=appDocVO.getSubserialNumber()%>"></input><!-- 문서하위번호 --> 
					<input id="readRange" name="readRange" type="hidden" value="<%=appDocVO.getReadRange()%>"></input><!-- 열람범위 --> 
					<input id="openLevel" name="openLevel" type="hidden" value="<%=appDocVO.getOpenLevel()%>"></input><!-- 정보공개 --> 
					<input id="openReason" name="openReason" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getOpenReason())%>"></input><!-- 정보공개사유 --> 
					<input id="conserveType" name="conserveType" type="hidden" value="<%=appDocVO.getConserveType()%>"></input><!-- 보존년한 --> 
					<input id="deleteYn" name="deleteYn" type="hidden" value="<%=appDocVO.getDeleteYn()%>"></input><!-- 삭제여부 --> 
					<input id="tempYn" name="tempYn" type="hidden" value="<%=appDocVO.getTempYn()%>"></input><!-- 임시여부 --> 
					<input id="docSource" name="docSource" type="hidden" value="<%=appDocVO.getDocSource()%>"></input><!-- 문서출처 --> 
					<input id="originDocId" name="originDocId" type="hidden" value="<%=appDocVO.getOriginDocId()%>"></input><!-- 원문서ID --> 
					<input id="originDocNumber" name="originDocNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getOriginDocNumber())%>"></input><!-- 원문서번호 --> 
					<input id="batchDraftYn" name="batchDraftYn" type="hidden" value="<%=appDocVO.getBatchDraftYn()%>"></input><!-- 일괄기안여부 --> 
					<input id="batchDraftNumber" name="batchDraftNumber" type="hidden" value="<%=appDocVO.getBatchDraftNumber()%>"></input><!-- 일괄기안일련번호 -->
					<input id="electronDocYn" name="electronDocYn" type="hidden" value="<%=appDocVO.getElectronDocYn()%>"></input><!-- 전자문서여부 --> 
					<input id="attachCount" name="attachCount" type="hidden" value="<%=appDocVO.getAttachCount()%>"></input><!-- 첨부개수 --> 
					<input id="urgencyYn" name="urgencyYn" type="hidden" value="<%=appDocVO.getUrgencyYn()%>"></input><!-- 긴급여부 --> 
					<input id="publicPost" name="publicPost" type="hidden" value="<%=appDocVO.getPublicPost()%>"></input><!-- 공람게시 --> 
					<input id="auditReadYn" name="auditReadYn" type="hidden" value="<%=appDocVO.getAuditReadYn()%>"></input><!-- 감사열람여부 --> 
					<input id="auditReadReason" name="auditReadReason" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getAuditReadReason())%>"></input><!-- 감사열람사유 -->
					<input id="auditYn" name="auditYn" type="hidden" value="<%=("Y".equals(opt346)) ? ("U".equals(appDocVO.getAuditYn()) ? "N" : appDocVO.getAuditYn()) : "U"%>"></input><!-- 감사여부 --> 
					<input id="bindingId" name="bindingId" type="hidden" value="<%=appDocVO.getBindingId()%>"></input><!-- 편철ID --> 
					<input id="bindingName" name="bindingName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getBindingName())%>"></input><!-- 편철명 --> 
					<input id="bindingResourceId" name="bindingResourceId" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getBindingResourceId())%>"></input><!-- 편철 다국어 추가 -->
					<input id="handoverYn" name="handoverYn" type="hidden" value="<%=appDocVO.getHandoverYn()%>"></input><!-- 인계여부 -->
					<input id="autoSendYn" name="autoSendYn" type="hidden" value="<%=appDocVO.getAutoSendYn()%>"></input><!-- 자동발송여부 --> 
					<input id="bizSystemCode" name="bizSystemCode" type="hidden" value="<%=appDocVO.getBizSystemCode()%>"></input><!-- 업무시스템코드 -->
					<input id="bizTypeCode" name="bizTypeCode" type="hidden" value="<%=appDocVO.getBizTypeCode()%>"></input><!-- 업무유형코드 --> 
					<input id="mobileYn" name="mobileYn" type="hidden" value="<%=appDocVO.getMobileYn()%>"></input><!-- 모바일결재여부 --> 
					<input id="transferYn" name="transferYn" type="hidden" value="<%=appDocVO.getTransferYn()%>"></input><!-- 문서이관여부 --> 
					<input id="doubleYn" name="doubleYn" type="hidden" value="<%=appDocVO.getDoubleYn()%>"></input><!-- 이중결재여부 --> 
					<input id="editbodyYn" name="editbodyYn" type="hidden" value="<%=appDocVO.getEditbodyYn()%>"></input><!-- 본문수정가능여부 --> 
					<input id="editfileYn" name="editfileYn" type="hidden" value="<%=appDocVO.getEditfileYn()%>"></input><!-- 첨부수정가능여부 --> 
					<input id="execDeptId" name="execDeptId" type="hidden" value="<%=appDocVO.getExecDeptId()%>"></input><!-- 주관부서ID --> 
					<input id="execDeptName" name="execDeptName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getExecDeptName())%>"></input><!-- 주관부서명 --> 
					<input id="summary" name="summary" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getSummary())%>"></input><!-- 요약 --> 
					<input id="classNumber" name="classNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getClassNumber())%>"></input><!-- 분류번호 --> 
					<input id="docnumName" name="docnumName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getDocnumName())%>"></input><!-- 분류번호명 --> 
					<input id="assistantLineType" name="assistantLineType" type="hidden" value="<%=StringUtil.null2str(appDocVO.getAssistantLineType(), opt303)%>"></input><!-- 협조라인유형 --> 
					<input id="auditLineType" name="auditLineType" type="hidden" value="<%=StringUtil.null2str(appDocVO.getAuditLineType(), opt304)%>"></input><!-- 감사라인유형 -->
					<input id="drafterOpinion" name="drafterOpinion" type="hidden" value=""></input><!-- 기안자 의견 -->  
					<!-- 보고경로 --> 
					<input id="appLine" name="appLine" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppLine(appLineVOs))%>"></input>
					<!-- 수신자 --> 
					<input id="appRecv" name="appRecv" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppRecv(appRecvVOs))%>"></input>
					<!-- 결재의견 -->
					<input type="hidden" id="opinion" name="opinion" value="<%=opinion%>"/>
					<!-- 본문 --> 
			<% 	
					if ("Y".equals(opt343)) { 
						if (strBodyType.equals("html")) { 
			%>	   
						<input id="bodyFile" name="bodyFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft001))%>"></input>
						<input id="mobileFile" name="mobileFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft002))%>"></input>
			<%      
						} else { 
			%> 			
						<input id="bodyFile" name="bodyFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs))%>"></input>
			<%	
						}
					} else { 
			%>		
						<input id="bodyFile" name="bodyFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft001))%>"></input>
			<%		
					} 
			%>
					<!-- 첨부 --> 
					<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAttach(fileVOs))%>"></input>
					<!-- 발송정보 -->
					<input id="sendOrgName" name="sendOrgName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getSendOrgName() == null) ? "" : sendInfoVO.getSendOrgName())%>"></input><!-- 발신기관명 -->
					<input id="logoPath" name="logoPath" type="hidden" value=""></input><!-- 로고   // jth8172 2012 신결재 TF -->
					<input id="symbolPath" name="symbolPath" type="hidden" value=""></input><!-- 심볼   // jth8172 2012 신결재 TF -->
					<input id="senderTitle" name="senderTitle" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getSenderTitle() == null) ? "" : sendInfoVO.getSenderTitle())%>"></input><!-- 발신명의 -->
					<input id="headerCamp" name="headerCamp" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getHeaderCamp() == null) ? "" : sendInfoVO.getHeaderCamp())%>"></input><!-- 상부캠페인 -->
					<input id="footerCamp" name="footerCamp" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getFooterCamp() == null) ? "" : sendInfoVO.getFooterCamp())%>"></input><!-- 하부캠페인 -->
					<input id="postNumber" name="postNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getPostNumber() == null) ? "" : sendInfoVO.getPostNumber())%>"></input><!-- 우편번호 -->
					<input id="address" name="address" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getAddress() == null) ? "" : sendInfoVO.getAddress())%>"></input><!-- 주소 -->
					<input id="telephone" name="telephone" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getTelephone() == null) ? "" : sendInfoVO.getTelephone())%>"></input><!-- 전화 -->
					<input id="fax" name="fax" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getFax() == null) ? "" : sendInfoVO.getFax())%>"></input><!-- FAX -->
					<input id="via" name="via" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getVia() == null) ? "" : sendInfoVO.getVia())%>"></input><!-- 경유 -->
					<input id="sealType" name="sealType" type="hidden" value="<%=(sendInfoVO.getSealType() == null) ? "" : sendInfoVO.getSealType()%>"></input><!-- 날인유형 -->
					<input id="homepage" name="homepage" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getHomepage() == null) ? "" : sendInfoVO.getHomepage())%>"></input><!-- 홈페이지 -->
					<input id="email" name="email" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getEmail() == null) ? "" : sendInfoVO.getEmail())%>"></input><!-- 이메일 -->
					<input id="receivers" name="receivers" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getReceivers() == null) ? "" : sendInfoVO.getReceivers())%>"></input><!-- 수신 -->
					<input id="displayNameYn" name="displayNameYn" type="hidden" value="<%=(sendInfoVO.getDisplayNameYn() == null) ? "" : sendInfoVO.getDisplayNameYn()%>"></input><!-- 수신 -->
					<input id="enforceDate" name="enforceDate" type="hidden" value=""/><!-- 자동발송시 시행일자 -->
					<!-- 관련문서 --> 
					<input id="relatedDoc" name="relatedDoc" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedDoc(relatedDocVOs))%>"></input>
					<!-- 관련규정 --> 
					<input id="relatedRule" name="relatedRule" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedRule(relatedRuleVOs))%>"></input>
					<!-- 거래처 --> 
					<input id="customer" name="customer" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferCustomer(customerVOs))%>"></input>
					<!-- 공람자 --> 
					<input id="pubReader" name="pubReader" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferPubReader(pubReaderVOs))%>"></input>
			
					<!-- 본문변경여부 -->
					<input id="bodyEdited" name="bodyEdited" type="hidden" value="N"></input><!-- 본문변경여부 -->
			
					<!-- 합의 찬성/반대 -->
					<input id="procType" name="procType" type="hidden" value=""></input><!-- 합의찬성 반대여부 -->
			
			
					
				</div>
					<!-- 관인 --> 
					<input type="hidden" id="stampId" name="stampId" value="" />
					<input type="hidden" id="stampName" name="stampName" value="" />
					<input type="hidden" id="stampExt" name="stampExt" value="" />
					<input type="hidden" id="stampFileId" name="stampFileId" value="" />
					<input type="hidden" id="stampFilePath" name="stampFilePath" value="" />
					<input type="hidden" id="stampFileName" name="stampFileName" value="" />
					<input type="hidden" id="stampDisplayName" name="stampDisplayName" value="" />
					<input type="hidden" id="stampFileSize" name="stampFileSize" value="" />
					<input type="hidden" id="stampFileType" name="stampFileType" value="" />
					<input type="hidden" id="stampFileOrder" name="stampFileOrder" value="5" />
					<input type="hidden" id="stampImageWidth" name="stampImageWidth" value="30" />
					<input type="hidden" id="stampImageHeight" name="stampImageHeight" value="30" />	
			<%	}  %>		
			
			</form>
			<jsp:include page="/app/jsp/common/adminform.jsp" />
			</div>
		</div>
	<%@ include file="/app_mobile/approval/footer.jsp" %>
</body>
</html>
<% } else { %>
<%
	String message = CommonUtil.nullTrim((String)request.getAttribute("message"));
	message = messageSource.getMessage(message, null, langType);
%>	

<script>alert("<%=message%>");</script>
<% } %> 