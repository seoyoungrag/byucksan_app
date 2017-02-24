<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.sds.acube.app.enforce.vo.EnfDocVO"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO"%>
<%@ page import="com.sds.acube.app.approval.vo.AppOptionVO"%>
<%@ page import="com.sds.acube.app.approval.vo.AppLineVO"%>
<%@ page import="com.sds.acube.app.approval.vo.RelatedDocVO"%>
<%@ page import="com.sds.acube.app.approval.vo.RelatedRuleVO"%>
<%@ page import="com.sds.acube.app.approval.vo.CustomerVO"%>
<%@ page import="com.sds.acube.app.appcom.vo.SendInfoVO"%>
<%@ page import="com.sds.acube.app.appcom.vo.OwnDeptVO"%>
<%@ page import="com.sds.acube.app.enforce.vo.EnfRecvVO"%>
<%@ page import="com.sds.acube.app.appcom.vo.PubReaderVO"%>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO"%>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="org.anyframe.util.StringUtil"%>
<%@ page import="com.sds.acube.app.common.util.UtilRequest"%>
<%@ page import="com.sds.acube.app.common.vo.DocHisVO"%>
<%@ page import="com.sds.acube.app.appcom.vo.EnfProcVO"%>
<%@ page import="com.sds.acube.app.env.vo.FormVO"%>
<%@ page import="com.sds.acube.app.env.vo.FormEnvVO"%>
<%@ page import="com.sds.acube.app.relay.vo.PackInfoVO"%>
<%@ page import="com.sds.acube.app.relay.vo.LineInfoVO"%>

<%
/** 
 *  Class Name  : EnforceDocumentDetail.jsp 
 *  Description : �������� ������
 * 
 *   �� �� �� : 2015.03.12
 *   �� �� �� : csh
 *   �������� : �������� ������ ��� �߰� 
 * 
 *  @author  csh
 *  @since 2015. 03. 12
 *  @version 1.0 
 *  @see
 */ 
%>

<%
	String compId = (String) session.getAttribute("COMP_ID"); // ����� �Ҽ� ȸ�� ���̵�
	String compName = (String) session.getAttribute("COMP_NAME"); // ����� �Ҽ� ȸ���
	
    String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // ����(HWP)
    String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT");
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // ÷��
	String aft005 = appCode.getProperty("AFT005", "AFT005", "AFT"); // ����
	String aft006 = appCode.getProperty("AFT006", "AFT006", "AFT"); // ������
	String aft011 = appCode.getProperty("AFT011", "AFT011", "AFT"); // ������빮�� ����(XML)
    
	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // ����
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // �ݷ�
    String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // ���
    String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // ����
    String apt005 = appCode.getProperty("APT005", "APT005", "APT"); // ����
    String apt006 = appCode.getProperty("APT006", "APT006", "APT"); // �̼�
    String apt007 = appCode.getProperty("APT007", "APT007", "APT"); // �ݼ�
    String apt012 = appCode.getProperty("APT012", "APT012", "APT"); // ���
    String apt013 = appCode.getProperty("APT013", "APT013", "APT"); // ���ο�û
    String apt014 = appCode.getProperty("APT014", "APT014", "APT"); //����
    String apt018 = appCode.getProperty("APT018", "APT018", "APT"); // ��ξ���
    String apt019 = appCode.getProperty("APT019", "APT019", "APT"); // ���ȸ��
    String apt020 = appCode.getProperty("APT020", "APT020", "APT"); // ��߼ۿ�û
    
    String art010 = appCode.getProperty("ART010", "ART010", "ART"); // ���
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // ����
	String art050 = appCode.getProperty("ART050", "ART050", "ART"); // ���� 
	String art051 = appCode.getProperty("ART051", "ART051", "ART"); // ����
	String art052 = appCode.getProperty("ART052", "ART052", "ART"); // ���
	String art053 = appCode.getProperty("ART053", "ART053", "ART"); // 1������
	String art080 = appCode.getProperty("ART080", "ART080", "ART"); // 1������  

    String dct497 = AppConfig.getProperty("form497", "DCT497", "formcode");
	String dct498 = AppConfig.getProperty("form498", "DCT498", "formcode");
	String dct499 = AppConfig.getProperty("form499", "DCT499", "formcode");
   
    String det002 = appCode.getProperty("DET002", "DET002", "DET"); // �볻
    String det003 = appCode.getProperty("DET003", "DET003", "DET"); // ���
    String det007 = appCode.getProperty("DET007", "DET007", "DET"); // �ο���
    String det011 = appCode.getProperty("DET011", "DET011", "DET"); // �������
    
    String dhu002 = appCode.getProperty("DHU002", "DHU002", "DHU"); // ��������
	String dhu009 = appCode.getProperty("DHU009", "DHU009", "DHU"); // ÷�μ���
	String dhu010 = appCode.getProperty("DHU010", "DHU010", "DHU"); // ������������(����Ϸ���)
	String dhu013 = appCode.getProperty("DHU013", "DHU013", "DHU"); // �����Ϸ�
	String dhu015 = appCode.getProperty("DHU015", "DHU015", "DHU"); // �߰��߼�
	String dhu017 = appCode.getProperty("DHU017", "DHU017", "DHU"); // ������������(����Ϸ���)
	String dhu025 = appCode.getProperty("DHU025", "DHU025", "DHU"); // ������ ����    

    String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // ����
    String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // ����
    
    String dru001 = appCode.getProperty("DRU001", "DRU001", "DRU"); // �μ�
    String dru002 = appCode.getProperty("DRU002", "DRU002", "DRU"); // ���

	String enf100 = appCode.getProperty("ENF100", "ENF100", "ENF"); // ��� ���
	String enf110 = appCode.getProperty("ENF110", "ENF110", "ENF"); // ���ο�û
	String enf200 = appCode.getProperty("ENF200", "ENF200", "ENF"); // ���� ���(�μ�)
	String enf250 = appCode.getProperty("ENF250", "ENF250", "ENF"); // ��� ���(���)
	String enf300 = appCode.getProperty("ENF300", "ENF300", "ENF"); // ���� �� ��� ���� ���
    String enf310 = appCode.getProperty("ENF310", "ENF310", "ENF"); // ���� �� ��� ���� ���(�ݷ�)
    String enf400 = appCode.getProperty("ENF400", "ENF400", "ENF"); // �������
    String enf500 = appCode.getProperty("ENF500", "ENF500", "ENF"); // �����
    String enf600 = appCode.getProperty("ENF600", "ENF600", "ENF"); // �ϷṮ��
    
	String usingType = appCode.getProperty("DPI002", "DPI002", "DPI");//usingType 
    
	// ������
	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB");	// ��������  �߿� ���๮��
	String lob004 = appCode.getProperty("LOB004", "LOB004", "LOB");	// ���๮����  �߿� ���๮��
	String lob007 = appCode.getProperty("LOB007", "LOB007", "LOB");	// ��δ����
	String lob008 = appCode.getProperty("LOB008", "LOB008", "LOB");	// ���������
	String lob010 = appCode.getProperty("LOB010", "LOB010", "LOB");	// ����Ϸ��� �߿� ���๮��
	String lob011 = appCode.getProperty("LOB011", "LOB011", "LOB");	// �����Ϸ���
	String lob012 = appCode.getProperty("LOB012", "LOB012", "LOB");	// ����������
	String lob015 = appCode.getProperty("LOB015", "LOB015", "LOB");	// �ӿ������� �߿� ���๮��
	String lob019 = appCode.getProperty("LOB019", "LOB019", "LOB");	// ���ο�û��
    String lob031 = appCode.getProperty("LOB031", "LOB031", "LOB"); // �����Խ� 
    String lob091 = appCode.getProperty("LOB091", "LOB091", "LOB"); // ���������(������)
	String lob092 = appCode.getProperty("LOB092", "LOB092", "LOB"); // ��δ����(������)
	String lob093 = appCode.getProperty("LOB093", "LOB093", "LOB"); // ���ù������
	String lob099 = appCode.getProperty("LOB099", "LOB099", "LOB");	// ��������ü���  
	
	/*�����ܰ迡���� ���ù��� ������, jd.park, 20120612
	String lob093 = appCode.getProperty("LOB093", "LOB093", "LOB"); // ���ù������
	*/

	String lol001 = appCode.getProperty("LOL001", "LOL001", "LOL");	// ��ϴ���
	String lol002 = appCode.getProperty("LOL002", "LOL002", "LOL");	// ������δ���
	String lol003 = appCode.getProperty("LOL003", "LOL003", "LOL");	// �̵�ϴ���

	String docId = UtilRequest.getString(request, "docId"); // ���ȭ�鿡�� �Ѿ�� docId
	String lobCode = UtilRequest.getString(request, "lobCode"); // ���ȭ�鿡�� �Ѿ�� docId
	String apprLine = CommonUtil.nullTrim((String)request.getAttribute("apprLine")); //������Ȳ ������ ��� ���� 20150317_dykim
	
	String userId = (String) session.getAttribute("USER_ID"); // ����� ���̵�
	String userName = (String) session.getAttribute("USER_NAME"); // ����ڸ�
	String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // ����� ����

	String deptId = (String) session.getAttribute("DEPT_ID"); // ����� �μ� ���̵�
	String deptName = (String) session.getAttribute("DEPT_NAME"); // ����� �μ���

	String result = UtilRequest.getString(request, "result");
	String message = UtilRequest.getString(request, "message");

    //�����������
	String enfLines = (String)request.getAttribute("enfLines");//�������
    
    //����������
    PubReaderVO pubReaderVO = (PubReaderVO) request.getAttribute("pubReaderVO");
    
    boolean isWithdraw = (Boolean)request.getAttribute("isWithdraw");
    boolean isEnfLineChange = (Boolean)request.getAttribute("isEnfLineChange");
    
    // ��ι��� ȸ�� ���ɿ���
    boolean isEnableDistributeWithdraw = (Boolean)request.getAttribute("isEnableDistributeWithdraw");

    // ��δ��忡�� �ݼ��̳� ���ο�û ���ɿ���
    boolean isEnableReturnOnDist = (Boolean)request.getAttribute("isEnableReturnOnDist");

    // ��������
	EnfDocVO enfDocVO = (EnfDocVO) request.getAttribute("enfDocInfo");
	if (enfDocVO == null ) {
	    enfDocVO = new EnfDocVO();  
	}
	// �����μ������� �����ͼ� ���� �����μ��� ��������  jth8172 20110821
	String ownDeptId = "";
	List<OwnDeptVO> ownDeptVOs = enfDocVO.getOwnDepts();
	int deptSize =  ownDeptVOs.size();
	OwnDeptVO ownDeptVO = null;
	for (int deptCnt=0; deptCnt<deptSize; deptCnt++) {
	    ownDeptVO = ownDeptVOs.get(deptCnt);
	    if("Y".equals(ownDeptVO.getOwnYn()) ) {
			ownDeptId = ownDeptVO.getOwnDeptId(); // ���������μ�
	    }
	}
	
	EnfRecvVO enfRecvVO = (EnfRecvVO) request.getAttribute("enfRecvInfo");
	if(enfRecvVO == null ) {
	    enfRecvVO = new EnfRecvVO();  
	}
	List<EnfRecvVO> enfRecvVOs = new ArrayList<EnfRecvVO>();
	if (!("").equals(enfRecvVO.getDocId())) {
		enfRecvVOs.add(enfRecvVO);
	}
	
	// �����깮������
	AppDocVO appDocVO = (AppDocVO) request.getAttribute("appDocVO");
	if ( appDocVO == null ) {
		appDocVO = new AppDocVO();
	}
	String docType = appDocVO.getDocType();
	SendInfoVO sendInfoVO = appDocVO.getSendInfoVO();
	if ( sendInfoVO == null ) {
		sendInfoVO = new SendInfoVO();
	}	
	List<AppLineVO> appLineVOs = appDocVO.getAppLine();  // �������

	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // �������� - 0 : ��������, 1 : �����н�����, 2 : ������
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	String opt303 = appCode.getProperty("OPT303", "OPT303", "OPT"); // �μ����� - 1 : ����������, 2 : ���������
	opt303 = envOptionAPIService.selectOptionValue(compId, opt303);
	String opt304 = appCode.getProperty("OPT304", "OPT304", "OPT"); // ����ǥ�� - 1 : �������, 2 : ��������, 3 : �������	
	opt304 = envOptionAPIService.selectOptionValue(compId, opt304);
	String opt322 = appCode.getProperty("OPT322", "OPT322", "OPT"); // PDF���� ������� - 1 : ������ ��������å����, 2 : �������
	opt322 = envOptionAPIService.selectOptionValue(compId, opt322);
    String opt335 = appCode.getProperty("OPT335", "OPT335", "OPT"); // �������� ������뿩��
	opt335 = envOptionAPIService.selectOptionValue(compId, opt335); 
	String opt343 = appCode.getProperty("OPT343", "OPT343", "OPT"); // html
	opt343 = envOptionAPIService.selectOptionValue(compId, opt343);
	String opt351 = appCode.getProperty("OPT351", "OPT351", "OPT"); // ������ ��߼� ��뿩��
	opt351 = envOptionAPIService.selectOptionValue(compId, opt351);
	String opt355 = appCode.getProperty("OPT355", "OPT355", "OPT"); //  ä������ ������� ���Ұ�  20110808
	opt355 = envOptionAPIService.selectOptionValue(compId, opt355); 
	String opt356 = appCode.getProperty("OPT356", "OPT356", "OPT"); //  ��ä������ ������� ���Ұ�  20110808
	opt356 = envOptionAPIService.selectOptionValue(compId, opt356); 
	String opt357 = appCode.getProperty("OPT357", "OPT357", "OPT"); //  ���� ó�� �� ���� �ڵ��ݱ�  20110808
	opt357 = envOptionAPIService.selectOptionValue(compId, opt357); 
	String opt371 = appCode.getProperty("OPT371", "OPT371", "OPT"); //  �������� ����ȭ ��� ����
	opt371 = envOptionAPIService.selectOptionValue(compId, opt371); 
	String opt401 = appCode.getProperty("OPT401", "OPT401", "OPT"); // ������ �̼۱�� ��� ���� 2012.03.20 by jkkim
	opt401 = envOptionAPIService.selectOptionValue(compId, opt401);
	String opt402 = appCode.getProperty("OPT402", "OPT402", "OPT"); // ������ �̼۱�� ��� ���� 2012.03.20 by jkkim
	opt402 = envOptionAPIService.selectOptionValue(compId, opt402);
	String opt403 = appCode.getProperty("OPT403", "OPT403", "OPT"); // ������ ������� ��� ���� 2012.03.20 by jkkim
	opt403 = envOptionAPIService.selectOptionValue(compId, opt403);
	String opt427 = appCode.getProperty("OPT427", "OPT427", "OPT"); // ���߹�� ��� ���� 2012.06.25 by redcomet
	opt427 = envOptionAPIService.selectOptionValue(compId, opt427);
	
	// �����ܰ迡���� ���ù��� ������, jd.park, 20120612
	String opt321 = appCode.getProperty("OPT321", "OPT321", "OPT"); // ���ù��� �������, jd.park, 20120504
	//���ù��� ��Ȱ��ȭ(�����ϴܿ� ǥ�� ��) - ���� ����� ���� ó��
	//opt321 = envOptionAPIService.selectOptionValue(compId, opt321);
	opt321 = "N";
	
	/*�ɼ� �߰�, �����з�ü�� �� ��ö ��� ���� , jd.park, 20120509 S*/
	String opt422 = appCode.getProperty("OPT422", "OPT422", "OPT"); //�����з�ü�� ������� 
	opt422 = envOptionAPIService.selectOptionValue(compId, opt422);
	
	String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT"); //��ö �������
	opt423 = envOptionAPIService.selectOptionValue(compId, opt423);
	/*�ɼ� �߰�, �����з�ü�� �� ��ö ��� ���� , jd.park, 20120509 E*/
	
	String roleCode = (String) session.getAttribute("ROLE_CODES"); // �����ڵ�
	String roleId12 = AppConfig.getProperty("role_cordoccharger","","role"); //������ ���� �����
    String roleId11 = AppConfig.getProperty("role_doccharger","","role"); //�μ� ����å����
    boolean  deptDocManager = (roleCode.indexOf(roleId11) == -1) ? false : true; 
    
	boolean docManagerFlag = (roleCode.indexOf(roleId12) == -1) ? false : true; 
   
	String sendOpinion = (String) request.getAttribute("sendOpinion"); //�߼��ǰ�
	String reDistOpinion = (String) request.getAttribute("reDistOpinion");  //���ο�û�ǰ�
	String moveOpinion = (String) request.getAttribute("moveOpinion");  //�̼��ǰ�
	 
	boolean isMainDistribute = (Boolean) request.getAttribute("isMainDistribute");  //�� ��ι��� ����

	FileVO bodyVO = (FileVO) request.getAttribute("bodyfile");
	if(bodyVO == null ) {
	    bodyVO = new FileVO();  
	}	
	
	// �������� Ÿ�� ����
	String strBodyType = "hwp";
	strBodyType = CommonUtil.getFileExtentsion(bodyVO.getFileName());
	
	List<FileVO> fileVOs = (List<FileVO>) request.getAttribute("enfFileInfo");
	if(fileVOs == null ) {
	    fileVOs = new ArrayList<FileVO>();
	}
	int idxNum = enfDocVO.getDocNumber().indexOf("-");  // -1 �̸� �߹���󹮼�
	
	String isNum = "Y";  // �߹���󿩺�
	if(!(idxNum >0)) {
	    isNum = "N";
	}
	
	String transferYn = enfDocVO.getTransferYn(); // �������� �̰��� ��������
	String docState = enfDocVO.getDocState(); // ��������
	String title = enfDocVO.getTitle();
	String originCompId = StringUtil.null2str(enfDocVO.getOriginCompId());
	
	String recvState = enfRecvVO.getRecvState(); // ���Ż���
	
	String msgOpinion =  messageSource.getMessage("approval.enforce.opinion", null, langType); //�ǰ�
	String docinfoBtn = messageSource.getMessage("approval.button.docinfo", null, langType); //��������
	String distributeBtn = messageSource.getMessage("approval.enforce.button.distribute", null, langType); //���
	String reDistributeBtn = messageSource.getMessage("approval.enforce.button.redistribute", null, langType); //����
	String reDistRequestBtn = messageSource.getMessage("approval.enforce.button.redistrequest", null, langType); //���ο�û
	String noDistributeBtn = messageSource.getMessage("approval.enforce.button.nodistribute", null, langType); //��ξ���
	String appendDistributeBtn = messageSource.getMessage("approval.enforce.button.appenddistribute", null, langType); //�߰����
	String distributeWithdrawBtn = messageSource.getMessage("approval.enforce.button.distributewithdraw", null, langType); //���ȸ��
	String acceptBtn = messageSource.getMessage("approval.enforce.button.accept", null, langType); 
	String accapprovalBtn = messageSource.getMessage("approval.enforce.button.acceptapproval", null, langType); 
	String sendOpinionBtn = messageSource.getMessage("approval.enforce.button.sendopinion", null, langType);
	String reDistOpinionBtn = messageSource.getMessage("approval.enforce.button.redistopinion", null, langType); 
	String moveBtn = messageSource.getMessage("approval.enforce.button.move", null, langType); 
	String viaBtn = messageSource.getMessage("approval.enforce.button.via", null, langType); 
	String returnBtn = messageSource.getMessage("approval.enforce.button.return", null, langType); 
	String reSendBtn = messageSource.getMessage("approval.enforce.button.resendrequest", null, langType); //��߼ۿ�û
	String processorfixBtn = messageSource.getMessage("enforce.button.processorfix", null, langType); // �������
	// �������ݰ����ڰ����ȭ 20141107 ������� ��ư �߰� HJ
// 	String processorpubBtn = messageSource.getMessage("enforce.button.processorpub", null, langType); // ������� ��ư �߰� HJ 
    String processorRefixBtn = messageSource.getMessage("enforce.button.processorRefix", null, langType); 
	String preopenBtn = messageSource.getMessage("enforce.button.preopen", null, langType); //����
	String pubreadBtn = messageSource.getMessage("enforce.button.pubreader", null, langType); //����
	String retrievedocBtn = messageSource.getMessage("enforce.button.retrievedoc", null, langType); //ȸ��
	String saveBtn = messageSource.getMessage("approval.button.save", null, langType); //����
	String saveHwpBtn = messageSource.getMessage("approval.button.savehwp", null, langType); // ��������	
	String savePdfBtn = messageSource.getMessage("approval.button.savepdf", null, langType);//pdf����
	String saveAllBtn = messageSource.getMessage("approval.button.saveall", null, langType); // ����/÷�� ����
	String sendMailBtn = messageSource.getMessage("approval.button.sendmail", null, langType); // �系����
	String printBtn = messageSource.getMessage("approval.button.print", null, langType); //�μ�
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); //�ݱ�
	String sendInfoBtn = messageSource.getMessage("approval.enforce.button.sendinfo", null, langType);
	String procInfoBtn = messageSource.getMessage("approval.enforce.button.procinfo", null, langType);
    String pubreaderBtn = messageSource.getMessage("approval.button.pubreader", null, langType);//������
	
	String appendBtn = messageSource.getMessage("approval.button.append", null, langType); 
	String removeBtn = messageSource.getMessage("approval.button.remove", null, langType); 
	String upBtn = messageSource.getMessage("approval.button.up", null, langType); 
	String downBtn = messageSource.getMessage("approval.button.down", null, langType); 
 //   String procBtn = messageSource.getMessage("approval.enforce.button.procOpinion", null, langType);
 
	String bindBtn = messageSource.getMessage("approval.form.bind", null, langType); 
	String modBtn = messageSource.getMessage("approval.button.modifydocinfo", null, langType); 
	String dateFormat = AppConfig.getProperty("date_format", "YYYY-MM-DD", "date");
	String format = AppConfig.getProperty("format", "yyyy/MM/dd HH:mm:ss", "date");

	String transMsg = messageSource.getMessage("enforce.tranfer.error.process", null, langType); //�������̰�
	
	String approaldocBtn = messageSource.getMessage("enforce.button.approaldoc", null, langType); //���Ȯ��
	String rejectDocBtn = messageSource.getMessage("enforce.button.rejectdoc", null, langType); //�ݷ�(�������������û)
	String approaldocMsg =  messageSource.getMessage("approval.result.msg.approvalok", null, langType); //���Ȯ�� �޼���
	String rejectDocMsg =  messageSource.getMessage("enforce.msg.rejectdocok", null, langType); //�������������û �޼���
	String approalprocessdocBtn = messageSource.getMessage("enforce.button.approalprocessdoc", null, langType); //���ó��
	String previousBtn = messageSource.getMessage("approval.button.previous.doc", null, langType); // ��������
	String nextBtn = messageSource.getMessage("approval.button.next.doc", null, langType); // ��������
	String applineBtn = messageSource.getMessage("approval.button.appline", null, langType); // ������
	
	// �̼��ǰ�(�߼��ǰ��� ������ �Բ� �����ش�.)
	if (!"".equals(moveOpinion) && !"".equals(sendOpinion)) {
	    moveOpinion = moveOpinion + "\r\n\r\n-- " + sendOpinionBtn + " --\r\n" + sendOpinion; 
	}

    //�����ڸ���Ʈ
    List pubReaderVOs = (List)request.getAttribute("pubReaderVOs");
    
    String publishId = StringUtil.null2str(request.getParameter("publishId")); // �Խ�ID
    String procOpinion = StringUtil.null2str((String)request.getAttribute("procOpinion"));  // ����ó���ǰ�
    String procAskType = StringUtil.null2str((String)request.getAttribute("procAskType"));  // ����ó������
    String strProcAskType =  messageSource.getMessage("approval.title.enforce." + procAskType, null, langType); //����,���
    
    FormVO formVO = (FormVO) request.getAttribute("formVO");
    FileVO logoEnvVO = (FileVO) request.getAttribute("logo");
	FileVO symbolEnvVO = (FileVO) request.getAttribute("symbol");
	List<FileVO> signFileVOs = (List<FileVO>) request.getAttribute("sign");
	PackInfoVO packInfoVO = (PackInfoVO) request.getAttribute("packInfoVO");
	FileVO sealVO = (FileVO) request.getAttribute("seal");

	String autoNextDocYn  = CommonUtil.nullTrim(request.getParameter("autoNextDocYn")); // �������� �ڵ� ����
	
	//��ܱ�����빮���� ��� �ѱ۹����� strBodyType ���� (T:��δ��, N:�����ʿ��� ���� ������� ó��, lob008 : ������-���������, det011 : ��������-�������))
    if((("T".equals(enfDocVO.getDistributeYn()) ||("N".equals(enfDocVO.getDistributeYn()) &&
			 lob008.equals(lobCode))))&& det011.equals(enfRecvVO.getEnfType())) {
    	strBodyType = "hwp";
    }
    
    List<String> readrange = (ArrayList) request.getAttribute("readrange");
	String publicPost = (String) request.getAttribute("publicpost");
	List<DocHisVO> docHisVOs = (ArrayList) request.getAttribute("docHisVOs");
	List<EnfProcVO> ProcVOs = (List<EnfProcVO>) request.getAttribute("ProcVOs");
	
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // �ý��۰�����
	boolean adminstratorFlag = (roleCode.indexOf(roleId10) == -1) ? false : true;
	
	String disabled;

	//ó���� ���������, ��������
// 	if(docManagerFlag && !(enf200.equals(docState) || enf250.equals(docState))){
// 		disabled ="";
// 	}else{
		disabled="disabled";   
// 	}
	
	String tabDisplay = ""; // �� ǥ�� ����
	String initTab = "docinfo"; // �ʱ� ǥ�� ��
	
	if(lobCode.equals(lol002)){
		disabled="disabled";
		tabDisplay = "none";
		initTab = "divProcInfo";
	}
	//������Ȳ ������ ��� �Ķ���� ����� �ʱ� ��� �� ���� 20150317_dykim
	if(apprLine != ""){
		initTab = "enflineinfo";
	}
	int rangesize = readrange.size();
    
%> 



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code='approval.title.select.docinfo'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/uuid.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery-ui-1.8.4.custom.min.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/blockUI.js"></script>

<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />


<!-- ��ũ��ƮSSSSSSSSSSSS -->
<script type="text/javascript">


/////////////////////////////SSS
//����������ȸâ�� ��ũ��Ʈ
function getDocInfo() {

	var docInfo = new Object();
	docInfo.compId = $("#compId", "#frmDocInfo").val();
	docInfo.docId = $("#docId", "#frmDocInfo").val();
	docInfo.title = $("#title", "#frmDocInfo").val();
	docInfo.bindingId = $("#bindingId", "#frmDocInfo").val();
	docInfo.bindingName = $("#bindingName", "#frmDocInfo").val();
	docInfo.conserveType = $("#conserveType", "#frmDocInfo").val();
	docInfo.readRange = $("#readRange", "#frmDocInfo").val();
	//docInfo.auditYn = $("#auditYn", "#frmDocInfo").val();
	docInfo.deptCategory = $("#deptCategory", "#frmDocInfo").val();
	docInfo.serialNumber = $("#serialNumber", "#frmDocInfo").val();
	docInfo.docNumber = $("#docNumber", "#frmDocInfo").val();  //�߼ۺμ� ������ȣ
	docInfo.subserialNumber = $("#subserialNumber", "#frmDocInfo").val();
	docInfo.enfType = $("#enfType", "#frmDocInfo").val();
	docInfo.publicPost = $("#publicPost", "#frmDocInfo").val();
	docInfo.urgencyYn = $("#urgencyYn", "#frmDocInfo").val();
	docInfo.docState = $("#docState", "#frmDocInfo").val();
	docInfo.ownDeptId = "<%=ownDeptId%>";   //�����μ� �߰� jth8172 20110821

	docInfo.classNumber = $("#classNumber", "#frmDocInfo").val();
 	docInfo.docnumName = $("#docnumName", "#frmDocInfo").val();
 		 	
	docInfo.docType = $("#docType", "#approvalitem").val();		

			
	docInfo.transferYn = $("#transferYn", "#approvalitem").val();

	return docInfo;
}

//��������-�������
function getAppLine() {
	return $("#enfLines", "#frmDocInfo").val();
}

//�������ݰ����ڰ����ȭ_15.01.14_���ð��� ����Ʈ_MG.Lee
function getPubList() {
	return $("#pubReader", "#frmDocInfo").val();
}

//�������ݰ����ڰ����ȭ �������� ��ȸ �� ����ó �� �߰� 2014.12.17.JJE
// �������� - ���깮�� ����������
function getAppRecv() {
	var recv = new Object();
	recv.appRecv = $("#app_appRecv", "#frmDocInfo").val();
	recv.displayNameYn = $("#app_displayNameYn", "#frmDocInfo").val();
	recv.receivers = $("#app_receivers", "#frmDocInfo").val();

	return recv;
}

function getRelatedDoc() {
	return $("#relatedDoc", "#approvalitem").val();
}
/////////////////////////////EEE








var bindWin =null;

//2015.07.28_lsk_����� ���� ��ȸ �˾�â ��Ī
var Org_ViewUserInfo = null;

$(document).ready(function() { initialize(); });

var enflineinfo = null;
var bodyinfo = null;
var attachinfo = null;
var popOpinionWin;
var relatedDocWin =  null;
var docLinkedWin = null;
var apprLine = "<%=apprLine%>"; //������Ȳ ������ ��� ���� 20150317_dykim

function initialize() {
	
	var docType = "";
	
	// ��������
	if (getDocInfo != null) {
		var docInfo = getDocInfo();
		docType = docInfo.docType;
		$("#docId").val(docInfo.docId);
		$("#txtitle").text(docInfo.title);
        $("#title").val(docInfo.title);
		if (docInfo.serialNumber > 0) {
			if (docInfo.subserialNumber > 0) {
				$("#divDeptCategory").text(docInfo.deptCategory + "-" + docInfo.serialNumber + "-" + docInfo.subserialNumber);
			} else {
				$("#divDeptCategory").text(docInfo.deptCategory + "-" + docInfo.serialNumber);
			}
		}
        
		if (docInfo.urgencyYn == "Y") {
			$("#urgencyYn").attr("checked", true);
		} else {
			$("#urgencyYn").attr("checked", false);
		}

		//��ö ������ ���� �Ҷ� ���� �����ֱ�, jd.park, 20120509
		if (docInfo.bindingName != "") {
			$('#bindTR').show();
			$("#bindingName").text(docInfo.bindingName);
		}else{
			$('#bindTR').hide();
		}

		$("#readRange").text(typeOfReadRange(docInfo.readRange));
		$("#senderTitle").text(docInfo.senderTitle);

<% if ("1".equals(publicPost) || "3".equals(publicPost)) { %>
        if (docInfo.publicPost != "") {
            $("#publicPostYn").attr("checked", true);
            $("#publicPostBound").show();
            $("#publicPost").val(docInfo.publicPost);
        }
<% } %> 

		$("#classNumber").val(docInfo.classNumber);
		$("#docnumName").val(docInfo.docnumName);
		
		//���� �з� ü�� ������ ���� �Ҷ� ���� �����ֱ�, jd.park, 20120509
		if (docInfo.classNumber != "") {	
			$('#classNumberTR').show();
			$("#divDocKind").html($("#classNumber").val() + " [" + $("#docnumName").val() + "]");
		}else{
			$('#classNumberTR').hide();
		}

		changeTab('<%= initTab %>');

	}
    
	// ����������
	if (getAppLine != null) {
		var tbEnfLines = $('#tbEnfLines tbody');
		var enfline = getAppLine();

		if (enfline == "") {
			var row = makeNonApprover();
			tbEnfLines.append(row);
		} else {
			var approver = getEnfList(enfline);
			var approversize = approver.length;
			for (var loop = 0; loop < approversize; loop++) {
				var dept = approver[loop].processorDeptName;
				var pos = approver[loop].processorPos;
				var name = approver[loop].processorName;
				var bodyHisId = "";
				var fileHisId = "";
				var lineHisId = approver[loop].lineHisId; 
				var asktype = typeOfApp(approver[loop].askType);
				var proctype = approver[loop].procType;
	            var procOpinion = approver[loop].procOpinion;
	            var absentReason = approver[loop].absentReason;
				var reppos = approver[loop].representativePos;//����������			
				var repname = approver[loop].representativeName;//�������̸�
				var date = approver[loop].processDate;
				var readdate = approver[loop].readDate;
				if (date.indexOf("9999")>=0) {
					date = "";
				}
				if (procOpinion.indexOf("null") >= 0) {
					procOpinion = "";
				}
				
				// 2015.07.29_LSK_userUid �߰�
				var userUid = approver[loop].processorId;
				
				var row = makeApprover(dept, pos, name, asktype, proctype, date, bodyHisId, fileHisId, lineHisId,procOpinion,absentReason,reppos,repname,readdate,userUid);
				tbEnfLines.append(row);
			}
		}
	}
	
	//�������ݰ����ڰ����ȭ �������� ���������� ����ó���� �� �߰� SSS 2014.12.17.JJE
	// ����������
	if (getAppRecv != null) {
		var recv = getAppRecv();
		var receiver = getReceiverList(recv.appRecv);
		var recvsize = receiver.length;
		var tbRecvLines = $('#tbRecvLines tbody');
		if (recvsize > 0) {
			for (var loop = 0; loop < recvsize; loop++) {
				var sName = receiver[loop].recvDeptName;
				var sRef = typeOfStr(receiver[loop].refDeptName);
				var sSymbol = typeOfStr(receiver[loop].recvSymbol);
				if (receiver[loop].recvUserName != "") {
					sRef = receiver[loop].recvUserName;
				}
				if (receiver[loop].enfType == "DET006") {
					sSymbol = typeOfStr(receiver[loop].fax);
				}
				else if (receiver[loop].enfType == "DET007") {
					var brTag = "";
					if(receiver[loop].postNumber != null && receiver[loop].postNumber != ""
						&& receiver[loop].address != null && receiver[loop].address != "") {
						brTag = "<br>";
					} else {
						brTag = "";
					}
					sRef = typeOfStr(receiver[loop].postNumber+brTag+receiver[loop].address);
					if(receiver[loop].telephone != null && receiver[loop].telephone != ""
						&& receiver[loop].fax != null && receiver[loop].fax != "") {
						brTag = "<br>";
					} else {
						brTag = "";
					}
					sSymbol = typeOfStr(receiver[loop].telephone+brTag+receiver[loop].fax);
				}
				var row = makeReciver(sName, sRef, sSymbol);
				tbRecvLines.append(row);
			}
		} else {
			var row = makeNoReciver();
			tbRecvLines.append(row);
		}
		if (recv.displayNameYn == "Y") {
			$('#chkDisplayAs').attr("checked", true);
			$('#displayAs').text(recv.receivers);
		} else {
			$('#chkDisplayAs').attr("checked", false);
		}
	}	
	//�������ݰ����ڰ����ȭ �������� ���������� ����ó���� �� �߰� EEE 2014.12.17.JJE

	<% if ("Y".equals(opt321)) { %>
	// ���ù���
	if (getRelatedDoc != null) {
		var tbRelatedDocs = $('#tbRelatedDocs tbody');
		var relateddoc = getRelatedDoc();

		if (relateddoc == "") {
			var row = makeNonRelatedDoc();
			tbRelatedDocs.append(row);
		} else {
			var related = getRelatedDocList(relateddoc);
			var relateddocsize = related.length;

			for (var loop = 0; loop < relateddocsize; loop++) {
				var docId = related[loop].docId;
				var title = related[loop].title;
				var usingType = related[loop].usingType;
				var electronDocYn = related[loop].electronDocYn;
				var row = makeRelatedDoc(docId, title, usingType, electronDocYn);
				tbRelatedDocs.append(row);
			}
		}
	}
	<% } %>	

<%
	// �����̷�
	if (docHisVOs != null) { 
%>
	    var tbDocHis = $('#tbDocHis tbody');
<%
	    int hisCount = docHisVOs.size();
	    if (hisCount == 0) {
%>	
	var row = makeNonDocHis();
	tbDocHis.append(row);
<%			
	    } else {
	    	for (int loop = 0; loop < hisCount; loop++) {
	    	    DocHisVO docHisVO = docHisVOs.get(loop);
	    		String hisType = messageSource.getMessage("approval.dhutype."+docHisVO.getUsedType().toLowerCase(), null, langType);
%>	
	var row<%=loop%> = makeDocHis("<%=docHisVO.getDocId()%>", "<%=docHisVO.getHisId()%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getUserName())%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getPos())%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getDeptName())%>", "<%=docHisVO.getUsedType()%>", "<%=hisType%>", "<%=docHisVO.getUseDate()%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getRemark())%>", "<%=docHisVO.getUserIp()%>", "<%=docHisVO.getUserId()%>");
	tbDocHis.append(row<%=loop%>);
<%		
	    	}
	    }
	}
%>
 
<%
// ���߽��̷�
	if (ProcVOs != null && ProcVOs.size() > 0) { 
%>
    	var tbProc = $('#tbProc tbody');
<%
	    int procCount = ProcVOs.size();
	    if (procCount == 0) {
%>	
			var row = makeNonProc();
			tbProc.append(row);
<%			
	    } else {
	    	int receiverOrder = -1;
	    	for (int loop = 0; loop < procCount; loop++) {
	    		EnfProcVO enfProcVO = ProcVOs.get(loop);
	    		String procType = messageSource.getMessage("approval.procinfo.form.proctype."+enfProcVO.getProcType().toLowerCase(), null, langType);
	    		if(enfProcVO.getReceiverOrder() != receiverOrder) {
	    			if(receiverOrder != -1) {
%>
						var rowDept = makeBlankLine(); 
						tbProc.append(rowDept);
<%
	    			}
	    			if(enfProcVO.getRefDeptName() != null && !"".equals(enfProcVO.getRefDeptName())) {
%>
						var rowDept = makeProcRefDept("<%=enfProcVO.getRefDeptName()%>"); 
						tbProc.append(rowDept);
<%
	    			}					
					receiverOrder = enfProcVO.getReceiverOrder();
	    		}
%>	

				var row<%=loop%> = makeProc("<%=enfProcVO.getProcOrder()%>", "<%=procType%>", 
						"<%=EscapeUtil.escapeJavaScript(enfProcVO.getProcessorName())%>", 
						"<%=EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(enfProcVO.getProcessDate() ) ) %>", 
						"<%=EscapeUtil.escapeDate(DateUtil.getFormattedDate(enfProcVO.getProcessDate()))%>", 
						"<%=EscapeUtil.escapeJavaScript(CommonUtil.nullTrim(enfProcVO.getProcOpinion()))%>",
						"<%=EscapeUtil.escapeJavaScript(enfProcVO.getProcessorId())%>");
				tbProc.append(row<%=loop%>);
<%		
	    	} //for
	    }
	}
%>
}

function makeProcRefDept(deptName) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td class='ltb_head' colspan='4'>" + deptName + "</td>";
	row += "</tr>";

	return row;
}

function makeNonRelatedDoc() {
	return "<tr bgcolor='#ffffff'><td class='tb_center' colspan='3'><nobr><spring:message code='list.list.msg.noData'/></nobr></td></tr>";
}

//������ �̷�
function selectEnfLineHis(hisId) {
    var url = "<%=webUri%>/app/enforce/enflineHis/getLineHisList.do?docId=" + $("#docId").val() + "&lineHisId="+ hisId;
    enflineinfo = openWindow("enflineinfowin", url , 500, 450);
}

//2015.07.28_lsk_����� ���� �˾� ȣ��
function onFindUserInfo(strUserID){
	if (strUserID == "" || strUserID == null) {
		alert("<spring:message code='list.list.msg.noSearchUser'/>");
		return;
	}

	var strUrl = "";
	var height = "";
	strUrl = "<%=webUri%>/app/common/userInfo.do?userId="+strUserID+"&compid=<%=compId%>";
	height = "470";

	var top = (screen.availHeight - 560) / 2;
	var left = (screen.availWidth - 800) / 2;
	var option = "top="+top+",left="+left+",toolbar=no,resizable=no, status=no, width=600,height=470";

	if(Org_ViewUserInfo != null && Org_ViewUserInfo.closed == false) {
		Org_ViewUserInfo.close();
	}
	
	Org_ViewUserInfo = openWindow("Org_ViewUserInfoWin", strUrl , "500", height, "no", "post", "no");
}

//������
function makeApprover(dept, pos, name, asktype, proctype, date, bodyHisId, fileHisId, lineHisId,procOpinion,absentReason,reppos,repname,readdate,userUid) {

	var row = "<tr bgcolor='#ffffff'>";
	
	if (asktype == "<%=art080%>") {
		row = "<tr bgcolor='#ffffff' style='display: none;'>";
	}
	
	if (procOpinion != "") {
		if (repname == "") {
			row += "<td class='tb_center' width='17%' rowspan='2'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='tb_center' width='18%' rowspan='2'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='17%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='18%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>"; 
		} 
	} else {
		if (repname == "") {
			row += "<td class='tb_center' width='17%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>";
			// 2015.07.29_LSK_�̸� ���ý� ������ȸ �˾� ȣ��
			row += "<td class='tb_center' width='18%' style='cursor: pointer;' onclick=\"javascript:onFindUserInfo('"+escapeJavaScript(userUid)+"');return(false);\">" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='17%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='18%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>";
		}
	}
	row += "<td width='20%' class='ltb_center' >" + escapeJavaScript(dept) + "</td>"; 
	
	//	�ݷ� �߰�		20150105_csh
	if (proctype == "<%=apt002%>") {
		row += "<td width='12%' class='ltb_center' >" + asktype + "<br><font color='red'><b>(<spring:message code="approval.proctype.apt002" />)</b></font>" + "</td>";
	} else {
		row += "<td width='12%' class='ltb_center' >" + asktype + "</td>";
	}
    
	//����ǥ�� 
    if(proctype ==null || proctype =="<%=apt014%>"){
    	row += "<td width='16%' class='ltb_center'  title='" + typeOfAppDate(date, "<%=format%>") + "'> <font color='blue'>" + absentReason + "</font></td>"; 
    }else{
		row += "<td width='16%' class='ltb_center' title='" + typeOfAppDate(date, "<%=format%>") + "'>" + typeOfAppDate(date, "<%=format%>") + "</td>";
    }
	row += "<td class='ltb_center' >";
	if (proctype == "<%=apt001%>" && (lineHisId != "" || bodyHisId != "" || fileHisId != "")) {
		if (lineHisId != "") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/path_history.gif' style='cursor:hand;' onclick='selectEnfLineHis(\"" + lineHisId + "\");return(false);'/>&nbsp;";
		} else {
//			row += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
		}
		if (fileHisId != "") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_etc.gif' style='cursor:hand;' onclick='selectAttachHis(\"" + fileHisId + "\");return(false);'/>&nbsp;";
		} else {
//			row += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
		}
	} else {
		row += "&nbsp;";
	}
	row += "</td>"; 
    row += "</tr>";
    if (procOpinion !="") {
       // alert(procOpinion);
        row += "<tr bgcolor='#ffffff'>";
        row += "<td width='100%' class='ltb_left' colspan='4' >" + unescapeCarriageReturn(unescapeJavaScript(procOpinion)) + "</td>";
    } 

	return row;
}

// �������ݰ����ڰ����ȭ_15.01.15_���ð����� ���߰�_MG.Lee
function makePubReader(pubReaderPos, pubReaderName, pubReaderDeptName, pubReaderAskType, pubDate, pubReaderDate, pubReaderHis) {
	var row = "<tr bgcolor='#ffffff'>";

	row += "<td class='tb_center' width='17%' >" + ((pubReaderPos == "") ? "&nbsp;" : pubReaderPos) + "</td>"; 
	row += "<td class='tb_center' width='18%' >" + escapeJavaScript(pubReaderName) + "</td>";
	row += "<td class='ltb_center' width='20%' >" + escapeJavaScript(pubReaderDeptName) + "</td>";
	row += "<td class='ltb_center' width='12%' >" + pubReaderAskType + "</td>";
	row += "<td width='16%' class='ltb_center' title='" + typeOfAppDate(pubReaderDate, "<%=format%>") + "'>" + typeOfAppDate(pubReaderDate, "<%=format%>") + "</td>";
	row += "<td width='17%' class='ltb_center'>"+pubReaderHis+"</td>";
	row += "</tr>";
	
	return row;	
}

//	�����ڰ� �������� ���� ���			20150105_csh
function makeNonApprover() {
	return "<tr bgcolor='#ffffff'><td class='ltb_center' colspan='6'><nobr><spring:message code='approval.msg.appline.nodata'/></nobr></td></tr>";	
}

function makeBlankLine() {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td colspan='4'>&nbsp;</td>";
	row += "</tr>";

	return row;
}

function selectAttachHis(hisId) {
	attachinfo = openWindow("attachinfo", "<%=webUri%>/app/appcom/attach/listAttachHis.do?docId=" + $("#docId").val() + "&hisId=" + hisId, 350, 450);
}

function selectBodyHis(hisId) {
	var width = 1200;
	if (screen.availWidth < 1200) {
		width = screen.availWidth;
	}
	var height = 768;
	if (screen.availHeight > 768) {
		height = screen.availHeight;
	}

	bodyinfo = openWindow("bodyinfo", "<%=webUri%>/app/appcom/attach/selectBodyHis.do?docId=" + $("#docId").val() + "&hisId=" + hisId, width, height);
}

<% if (docHisVOs != null) { %>
//�����̷�
function makeDocHis(docId, hisId, userName, pos, deptName, usedType, hisType, useDate, remark, userIp, userUid) {
	var row = "<tr bgcolor='#ffffff'>";
	// 2015.07.29_LSK_�����̷� ������ ������ȸ �˾� ȣ��
	row += "<td width='27%' class='ltb_center' title='" + deptName + " " + pos + " " + userName + "' style='cursor: pointer;' onclick=\"javascript:onFindUserInfo('"+escapeJavaScript(userUid)+"');return(false);\">" + userName + "</td>";
	row += "<td width='25%' class='ltb_center'>" + useDate + "</td>";
	row += "<td width='18%' class='ltb_center'>" + hisType + "</td>";
	row += "<td width='16%' class='ltb_center'>" + userIp + "</td>";
	row += "<td width='14%' class='ltb_center'>";
	if (usedType == "<%=dhu002%>" || usedType == "<%=dhu010%>" || usedType == "<%=dhu013%>"|| usedType == "<%=dhu015%>") {
		row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_hwp.gif' style='cursor:hand;' onclick='selectBodyHis(\"" + hisId + "\");return(false);'/>&nbsp;";
	} else 	if (usedType == "<%=dhu009%>") {
		row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_etc.gif' style='cursor:hand;' onclick='selectAttachHis(\"" + hisId + "\");return(false);'/>&nbsp;";
	} else {
		row += "&nbsp;";
	}
	row += "</td>";
	row += "</tr>";
	row += "<tr bgcolor='#ffffff'>";
	row += "<td width='27%' class='ltb_head'><spring:message code="approval.title.modifyreason" /></td>";
	row += "<td width='73%' class='ltb_left' colspan='4'>" + escapeHtmlCarriageReturn(remark) + "</td>";
	row += "</tr>";

	return row;
}

function makeNonDocHis() {
	return "<tr bgcolor='#ffffff'><td class='ltb_center' colspan='4'><nobr><spring:message code='approval.procinfo.msg.nodata'/></nobr></td></tr>";	
}
<% } %>

<% if (ProcVOs != null && ProcVOs.size() > 0) { %>
//���߽��̷�

function makeProc(no, procType, processorName, processShortDate, processDate, procOpinion, processorId) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td width='20%' class='ltb_center' >" + no + "</td>";
	row += "<td width='20%' class='ltb_center'>" + procType + "</td>";
	// 2015.07.29_LSK_�����̷�_ó���� ���ý� ����� ���� ��ȸ �˾� ȣ��
	row += "<td width='30%' class='ltb_center' style='cursor: pointer;' onclick=\"javascript:onFindUserInfo('"+processorId+"');return(false);\">" + processorName + "</td>";
	row += "<td width='30%' class='ltb_center' title='" + processDate + "'>" + processShortDate + "</td>";
	row += "</tr>";
	if(procOpinion!=null && procOpinion!="") {
		row += "<tr bgcolor='#ffffff'>";
		row += "<td width='20%' class='ltb_head'><spring:message code="approval.procinfo.form.procopinion" /></td>";
		row += "<td width='80%' class='ltb_left' colspan='3'>" + escapeHtmlCarriageReturn(procOpinion) + "</td>";
		row += "</tr>";
	}

	return row;
}

function makeNonProc() {
	return "<tr bgcolor='#ffffff'><td class='ltb_center' colspan='4'><nobr><spring:message code='approval.procinfo.msg.nodata'/></nobr></td></tr>";	
}
<% } %>

function isPopOpen(){
	// ����â�� ���� ������ Ȯ�� �� �ݴ´�.
	if (relatedDocWin != null && relatedDocWin.closed == false) {
		if (confirm("<spring:message code='list.list.msg.closewindow'/>")){
			relatedDoc.close();			
			return true;			
		} else {
			return false;
		}
	} else {
		return true;
	}
}

function closeDocInfo(isall) {
	if (enflineinfo != null && !enflineinfo.closed)
		enflineinfo.close();
	if (bodyinfo != null && !bodyinfo.closed)
		bodyinfo.close();
	if (attachinfo != null && !attachinfo.closed)
		attachinfo.close();
	if (isall == "Y" && docLinkedWin != null && !docLinkedWin.closed)
		docLinkedWin.close();
	
	window.close();
}

function hideAllDiv() {
	$("#docinfo").hide();
	$("#enflineinfo").hide();
	//�������� ���������� ����ó���� �� �߰� 20141229_csh
	$("#receiverinfo").hide();

<% if ("Y".equals(opt321)) { %>
	$("#relateddocinfo").hide();
<% } %>		
<% if (docHisVOs != null) { %>
	$("#dochisinfo").hide();
<% } %>
<% if (ProcVOs != null && ProcVOs.size() > 0) { %>
	$("#divProcInfo").hide();
<% } %>
}

function changeTab(divid) {
	hideAllDiv();
	$("#" + divid).show();
}


function changeChoice() {
    if ($("#publicPostYn").attr("checked")) {
        $("#publicPostBound").show();
    } else {
        $("#publicPostBound").hide();
    }
}


function close_win(docInfo) {
    if (setChargeeDocInfo) {

        //������������ �� ����
    	setChargeeDocInfo(docInfo);

    	if (relatedDocWin != null && relatedDocWin.closed == false) {
    			relatedDoc.close();		
    	}		
        if (popOpinionWin != null) {
            popOpinionWin.close();
        }
        window.close();
    }
}

//�ǰ� �� �����ȣ �˾�
function popOpinion(returnFunction, btnName, opinionYn) {

    var top = (screen.availHeight - 250) / 2;
    var left = (screen.availWidth - 500) / 2;
    var height = "height=230,";
    <% if (!"1".equals(opt301)) { %> // ��ȣ�Է¾ƴϸ�
        height = "height=280,";
    <% } %>  
    if(opinionYn=="N") {
        height = "height=120,";
        <% if (!"1".equals(opt301)) { %> // ��ȣ�Է¾ƴϸ�
        height = "height=150,";
        <% } %>  
    }   
    popOpinionWin = window.open("", "popOpinionWin",
            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=500," + height +
            "scrollbars=no,resizable=no"); 
    $("#returnFunction").val(returnFunction);
    $("#btnName").val(btnName);
    $("#opinionYn").val(opinionYn);
    $("#frmDocInfo").attr("target", "popOpinionWin");
    $("#frmDocInfo").attr("action", "<%=webUri%>/app/enforce/popupOpinion.do");
    $("#frmDocInfo").submit();
}


<%
int index = 2;
if ("Y".equals(opt321)) {
    index++;
}
if (docHisVOs != null) {
	index++;
}
if (ProcVOs != null && ProcVOs.size() > 0) {
	index++;
}


String strIndex = index + "";
int tabIndex = 1;
%>
<%= com.sds.acube.app.design.AcubeTab.getScriptFunction(index) %>

//�������ݰ����ڰ����ȭ �������� ���������� ����ó���� �� �߰� SSS 2014.12.17.JJE
//������
function makeReciver(sName, sRef, sSymbol) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td width='40%' class='tb_center'>" +sName + "</td>"; 
	row += "<td width='40%' class='tb_center'>" +sRef + "</td>"; 
	row += "<td width='20%' class='tb_center'>" +sSymbol + "</td>"; 
	row += "</tr>";

	return row;
}

function makeNoReciver() {
	return "<tr bgcolor='#ffffff'><td class='tb_center' colspan='3'><spring:message code='approval.msg.notexist.receiver' /></td></tr>";
}
//�������ݰ����ڰ����ȭ �������� ���������� ����ó���� �� �߰� EEE 2014.12.17.JJE

</script>

<!-- ��ũ��ƮEEEEEEEEEEEE -->
</head>

<body topmargin="0" leftmargin="0">
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='approval.title.select.docinfo'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr style="display:<%= tabDisplay %>"> 
			<td>
				<acube:tabGroup>
				<!-- ������Ȳ �����Ǹ� ��� 20150317_dykim -->
				<%if(apprLine !=""){%>
						<acube:tab index="1" onclick="selectTab(1);changeTab('applineinfo');"selected="true">
							<spring:message code='approval.title.approverinfo' />
						</acube:tab>
				<%}else{%>
<% String selectTab = "selectTab(" + tabIndex + ");changeTab('docinfo');"; %>
<% 
	String tagIndex = "" + tabIndex;
	tabIndex++;
%>
					<acube:tab index="<%= tagIndex %>" onClick="<%=selectTab%>" selected="true"><spring:message code='approval.title.docinfo'/></acube:tab>
						<acube:space between="tab"/>
						
						
<% selectTab = "selectTab(" + tabIndex + ");changeTab('enflineinfo');"; %>
<% 
	tagIndex = "" + tabIndex;
	tabIndex++;
%>
					<acube:tab index="<%= tagIndex %>" onClick="<%=selectTab%>"><spring:message code='approval.title.approverinfo'/></acube:tab>

<!-- �������ݰ����ڰ����ȭ �������� ������������ ����ó���� �� �߰� SSS 2014.12.18.JJE -->
					<acube:space between="tab"/>
<% selectTab = "selectTab(" + tabIndex + ");changeTab('receiverinfo');"; %>
<% 
	tagIndex = "" + tabIndex;
	tabIndex++;
%>
					<acube:tab index="<%= tagIndex %>" onClick="<%=selectTab%>"><spring:message code='approval.title.receiverinfo'/></acube:tab>
					<acube:space between="tab"/>
					
<!-- �������ݰ����ڰ����ȭ �������� ������������ ����ó���� �� �߰� EEE 2014.12.18.JJE -->
<% if ("Y".equals(opt321)) { %>
<% 	selectTab = "selectTab(" + tabIndex + ");changeTab('relateddocinfo');"; %>
<% 
	tagIndex = "" + tabIndex;
	tabIndex++;
%>
					<acube:space between="tab"/>
					<acube:tab index="<%= tagIndex %>" onclick="<%=selectTab%>"><spring:message code='approval.title.relateddoc'/></acube:tab>
<% } %>	
<% if (docHisVOs != null) { %>
<% 	selectTab = "selectTab(" + tabIndex + ");changeTab('dochisinfo');"; %>
<% 
	tagIndex = "" + tabIndex;
	tabIndex++;
%>
						<acube:space between="tab"/>
					<acube:tab index="<%= tagIndex %>" onClick="<%=selectTab%>"><spring:message code='approval.title.dochis'/></acube:tab>
<% } %>
<% if (ProcVOs != null && ProcVOs.size() > 0)  { %>
<% 	selectTab = "selectTab(" + tabIndex + ");changeTab('divProcInfo');"; %>
<% 
	tagIndex = "" + tabIndex;
	tabIndex++;
%>
						<acube:space between="tab"/>
					<acube:tab index="<%= tagIndex %>" onClick="<%=selectTab%>"><spring:message code='approval.enforce.button.procinfo'/></acube:tab>
<% } %>
<% } %>
               </acube:tabGroup>
            </td>
         </tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
				<!------- �������� Table S--------->
				<div id="docinfo" style="display:none;">
                
				<acube:tableFrame>
					<tr bgcolor="#ffffff"><!-- ���� -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.title'/></td>
						<td width="80%" class="tb_left_bg" id="txtitle" name="txtitle">
                        
                        </td><input type="hidden" id="title" name="title" />
					</tr>
					<tr bgcolor="#ffffff"><!-- ������ȣ -->
						<td width="20%" class="tb_tit" id="docNumber"><spring:message code='approval.form.docnumber'/></td>
						<td class="tb_left_bg" id="divDeptCategory" >
                        <input type="hidden" id="deptCategory" name="deptCategory" value=""/></td>
					</tr>
					
					<tr bgcolor="#ffffff" id="bindTR" Style="display: none"><!-- ��ö -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.bind'/></td>
						<td class="tb_left_bg" id="bindingName">
						</td>
					</tr>					
					
					<tr bgcolor="#ffffff" id="classNumberTR" Style="display: none"><!-- �����з� -->
						<td class="tb_tit" width="20%" style="height: 28px;"><spring:message code="approval.form.docKind" /></td>
						<td class="tb_left_bg" colspan="2">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="96%"><div id="divDocKind" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:3pt; vertical-align:bottom;"></div>
									<input type="hidden" name="classNumber" id="classNumber" value=""/>
									<input type="hidden" name="docnumName" id="docnumName" value="" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr bgcolor="#ffffff"><!-- �������� -->
                        <td width="20%" class="tb_tit" ><spring:message code='approval.form.readrange'/></td>
	                    <td class="tb_left_bg" width="30%" id="readRange"></td> 

					
                    <tr bgcolor="#ffffff"><!-- ��޿��� -->
                        <td width="20%" class="tb_tit"><spring:message code='approval.form.urgencyyn'/></td>
                        <td class="tb_left_bg" ><input type="checkbox" id="urgencyYn" name="urgencyYn" <%=disabled%>>		<%--	��޿��� üũ����		20141229_csh	--%>
                    </tr>   

<% if ("1".equals(publicPost) || "3".equals(publicPost)) { %>
					<tr bgcolor="#ffffff"><!-- �����Խ� -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.publicpost'/></td>				
<% 	if (rangesize > 1) { %>	    
						<td class="tb_left_bg" colspan="3">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="30">
										<input type="checkbox" id="publicPostYn" name="publicPostYn" <%=disabled%>>		<%--	�����Խ� üũ����		20141229_csh	--%>
									</td>
									<td>
<%	if (rangesize > 2) { %>		
										<div id="publicPostBound" style="display:none;">								
											<select id="publicPost" name="publicPost" class="select_9pt" style="width:115;">
<%		for (int loop = 1; loop < rangesize; loop++) {
		    	String range = (String)readrange.get(loop); %>	
		    							<% if("KFCC002".equals(compId)) { %>						
												<option value="<%=range%>"><%=messageSource.getMessage("approval.form.readrange.kfcc002." + range.toLowerCase() , null, langType)%></option>
										<% } else { %>
												<option value="<%=range%>"><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></option>
										<% } %>
<%		} %>
											</select>
										</div>
<% } else {
				String range = (String)readrange.get(1); %>
										<% if("KFCC002".equals(compId)) { %>
										<div id="publicPostBound" style="display:none;font-size:9pt;font-family:Gulim,Dotum,Arial;"><%=messageSource.getMessage("approval.form.readrange.kfcc002." + range.toLowerCase() , null, langType)%></div>
										<% } else { %>
										<div id="publicPostBound" style="display:none;font-size:9pt;font-family:Gulim,Dotum,Arial;"><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></div>
										<% } %>
										<input type="hidden" id="publicPost" name="publicPost" value="<%=range%>"/>
<% } %>											
									</td>
								</tr>
							</table>	
						</td>
<% 	} else if (rangesize == 1) {
	    	String range = (String)readrange.get(0); %>							
						<td class="tb_left_bg" colspan="3">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="30">
										<input type="checkbox" id="publicPostYn" name="publicPostYn"  <%=disabled%>>		<%--	�����Խ� üũ����		20141229_csh	--%>
										<input type="hidden" id="publicPost" name="publicPost" value="<%=range%>"/>
									</td>
									<td>
									<% if("KFCC002".equals(compId)) { %>
										<div id="publicPostBound" style="display:none;font-size:9pt;font-family:Gulim,Dotum,Arial;"><%=messageSource.getMessage("approval.form.readrange.kfcc002." + range.toLowerCase() , null, langType)%></div>
									<% } else { %>
										<div id="publicPostBound" style="display:none;font-size:9pt;font-family:Gulim,Dotum,Arial;"><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></div>
									<% } %>
									</td>
								</tr>
							</table>	
						</td>
<% 	} %>					
                        </tr>
<% } %>
				</acube:tableFrame>
               
				</div>
				<!-------�������� Table E --------->
				<!------- �������� Table S--------->	
					<div id="enflineinfo" style="display:none;">
						<div style="height:162px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">
						<table class="table_grow" width="100%" border="0" cellpadding="0" cellspacing="1" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr bgcolor="#ffffff">
                                <td width="17%" class="ltb_head"><spring:message code="approval.form.position" /></td>
                                <td width="18%" class="ltb_head"><spring:message code="approval.form.name" /></td>
                                <td width="20%" class="ltb_head"><spring:message code="approval.form.dept" /></td>
                                <td width="12%" class="ltb_head"><spring:message code="approval.form.apptype" /></td>
                                <td width="16%" class="ltb_head"><spring:message code="approval.form.processdate" /></td>
                                <td width="17%" class="ltb_head"><spring:message code="approval.form.editinfo" /></td>
							</tr>
						</table>
						<table id="tbEnfLines" bgcolor="#adbed7" width="100%" border="0" cellpadding="0" cellspacing="1" style="position:absolute;left:0px;top:30px;z-index:1;">
							<tbody/>
						</table>
						</div>
 				</div>
				<!-------�������� Table E --------->
				<!-- �������ݰ����ڰ����ȭ �������� ������������ ����ó �� �߰� SSS 2014.12.18.JJE -->
				<!------- ������ Table S--------->
				<div id="receiverinfo" style="display:none;">
					<div style="height:269px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
						<table cellpadding="2" cellspacing="1" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr>
								<td width="40%" class="ltb_head"><spring:message code="approval.table.title.recieve" /></td>
								<td width="40%" class="ltb_head"><spring:message code="approval.table.title.ref" /></td>
								<td width="20%" class="ltb_head"><spring:message code="approval.table.title.recvsymbol" /></td>
							</tr>
						</table>
						<table id="tbRecvLines" cellpadding="2" cellspacing="1" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
							<tbody/>
						</table>
					</div>
					<!-- ���� ���� -->
					<acube:space between="button_content" table="y"/>
					<!-- ���� �� -->
					<table width='100%' height="18" cellpadding="0" cellspacing="1" border="0" class="td_table">
						<tr>
							<td width="20" class="tb_left_bg" bgcolor="#dce9f6"><input type="checkbox" name="chkDisplayAs" id="chkDisplayAs"" disabled/></td>
							<td width="20%" class="tb_tit"><spring:message code="approval.form.markreciver" /></td>
							<td width="*" class="tb_left_bg" id="displayAs"></td>
						</tr>
					</table>
					<!-- ���� ���� -->
					<acube:space between="button_content" table="y"/>
					<!-- ���� �� -->
				</div>
				<!------- ������ Table E --------->
				<!-- �������ݰ����ڰ����ȭ �������� ������������ ����ó �� �߰� EEE 2014.12.18.JJE -->
<% if ("Y".equals(opt321)) { %>
				<!------- ���ù��� Table S--------->
				<div id="relateddocinfo" style="display:none;">
					<div style="height:148px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
						<table cellpadding="2" cellspacing="1" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr><!-- ���� -->
								<td width="10%" class="ltb_head"><spring:message code="list.list.title.headerType" /></td>
								<td width="90%" class="ltb_head"><spring:message code="list.list.title.headerTitle" /></td>
							</tr>
						</table>
						<table id="tbRelatedDocs" cellpadding="2" cellspacing="1" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
							<tbody/>
						</table>
					</div>
				</div>
				<!------- ���ù��� Table E --------->
<% } %>				
<% if (docHisVOs != null) { %>
				<!------- �����̷� Table S--------->  
				<div id="dochisinfo" style="display:none;">
					<div style="height:148px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
						<table cellpadding="2" cellspacing="1" width="100%" class="table"" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr><!-- ���� -->
								<td width="27%" class="ltb_head"><spring:message code="approval.title.modifyuser" /></td>
 								<td width="25%" class="ltb_head"><spring:message code="approval.title.modifydate" /></td>
								<td width="18%" class="ltb_head"><spring:message code="approval.title.modifytype" /></td>
								<td width="16%" class="ltb_head"><spring:message code="approval.title.modifyip" /></td>
								<td width="14%" class="ltb_head"><spring:message code="approval.form.editinfo" /></td>
							</tr>
						</table>
						<table id="tbDocHis" cellpadding="2" cellspacing="1" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
						<tbody/>
						</table>
					</div>	
				</div>
				<!------- �����̷� Table E --------->
<% } %>
<% if (ProcVOs != null && ProcVOs.size() > 0) { %>
				<!------- ���߽��̷� Table S--------->  
				<div id="divProcInfo" style="display:none;">
					<div style="height:148px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
						<table cellpadding="2" cellspacing="1" width="100%" class="table"" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr><!-- ���� -->
								<td width="20%" class="ltb_head"><spring:message code="approval.procinfo.form.no" /></td>
 								<td width="20%" class="ltb_head"><spring:message code="approval.procinfo.form.proctype" /></td>
								<td width="30%" class="ltb_head"><spring:message code="approval.procinfo.form.processorname" /></td>
								<td width="30%" class="ltb_head"><spring:message code="approval.procinfo.form.processdate" /></td>
							</tr>
						</table>
						<table id="tbProc" cellpadding="2" cellspacing="1" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
						<tbody/>
						</table>
					</div>	
				</div>
				<!------- �����̷� Table E --------->
<% } %>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="closeDocInfo('Y');return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table> 
</acube:outerFrame>

<form id="frmDocInfo" name="frmDocInfo" method="POST"  target="popupWin" >
	<input id="autoNextDocYn" name="autoNextDocYn" type="hidden" value="<%=autoNextDocYn%>"/>
<div id="approvalitem" name="approvalitem" >
    <input type="hidden" id="compId" name="compId" value="<%=compId%>" />
    <input type="hidden" id="title" name="title" value="<%=EscapeUtil.escapeHtmlTag(title)%>"/><!-- �������� --> 
	<input type="hidden" id="docType" name="docType" value="<%=appDocVO.getDocType()%>"></input><!-- �������� --> 
    <input type="hidden" id="lobCode" name="lobCode" value="<%=lobCode%>" />
    <input type="hidden" id="docId" name="docId" value="<%=docId%>" />
    <input type="hidden" id="newDocId" name="newDocId" value="" />
    <input type="hidden" id="originCompId" name="originCompId" value="<%=enfDocVO.getOriginCompId()%>" />
    <input type="hidden" id="originDocId" name="originDocId" value="<%=enfDocVO.getOriginDocId()%>" />

    <input type="hidden" id="electronDocYn" name="electronDocYn" value="Y" />
    <input type="hidden" id="docState" name="docState" value="<%=docState%>" />
    <input type="hidden" id="recvState" name="recvState" value="<%=recvState%>" />
    <input type="hidden" id="procType" name="procType" value="APT005" size="50" />
    <input type="hidden" id="distributeYn" name="distributeYn" value="<%=enfDocVO.getDistributeYn()%>" /><!-- ��ο��� -->
    <input type="hidden" id="urgencyYn" name="urgencyYn" value="<%=enfDocVO.getUrgencyYn()%>" />
    <input type="hidden" id="senderDeptName" name="senderDeptName" value="<%=enfDocVO.getSenderDeptName()%>" /><!-- �߼ۺμ� -->
    <input type="hidden" id="senderDeptId" name="senderDeptId" value="<%=enfDocVO.getSenderDeptId()%>" /><!-- �߼ۺμ�ID -->
    <input type="hidden" id="senderCompName" name="senderCompName" value="<%=enfDocVO.getSenderCompName()%>" /><!-- �߼�ȸ�� -->
    
	<input id="bindingId" name="bindingId" type="hidden" value="<%=enfDocVO.getBindingId()%>"/><!-- ��öID --> 
	<input id="bindingName" name="bindingName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(enfDocVO.getBindingName())%>"/><!-- ��ö�� --> 
	<input id="bindingResourceId" name="bindingResourceId" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(enfDocVO.getBindingResourceId())%>"></input><!-- ��ö �ٱ��� �߰� -->
	<input id="conserveType" name="conserveType" type="hidden" value="<%=enfDocVO.getConserveType()%>"/><!-- �������� --> 
	<input id="readRange" name="readRange" type="hidden" value="<%=enfDocVO.getReadRange()%>"/><!-- �������� --> 
	<input id="docNumber" name="docNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(enfDocVO.getDocNumber())%>"/><!-- ���깮����ȣ --> 
	<input id="deptCategory" name="deptCategory" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(enfDocVO.getDeptCategory())%>"/><!-- �����μ��з� --> 
	<input id="serialNumber" name="serialNumber" type="hidden" value="<%=enfDocVO.getSerialNumber()%>"/><!-- �����Ϸù�ȣ --> 
	<input id="subserialNumber" name="subserialNumber" type="hidden" value="<%=enfDocVO.getSubserialNumber()%>"/><!-- ����������ȣ --> 
	<input id="enfType" name="enfType" type="hidden" value="<%=enfDocVO.getEnfType()%>"/><!-- �������� --> 
	<input id="publicPost" name="publicPost" type="hidden" value="<%=enfDocVO.getPublicPost()%>"/><!-- �����Խ� --> 
    <input id="pubReader" name="pubReader" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferPubReader(pubReaderVOs))%>" /><!-- ������ --> 
    <input id="transferYn" name="transferYn" type="hidden" value="<%=transferYn%>" />
  	<input id="assistantLineType" name="assistantLineType" type="hidden" value="<%=StringUtil.null2str(enfDocVO.getAssistantLineType(), opt303)%>"></input><!-- ������������ --> 
	<input id="auditLineType" name="auditLineType" type="hidden" value="<%=StringUtil.null2str(enfDocVO.getAuditLineType(), opt304)%>"></input><!-- ����������� --> 
    	
	<!-- �߼�, �̼�, ���ο�û �ǰ� -->
    <input type="hidden" id="sendOpinion" name="sendOpinion" value="<%=EscapeUtil.escapeHtmlTag(sendOpinion)%>"/>
    <input type="hidden" id="moveOpinion" name="moveOpinion" value="<%=EscapeUtil.escapeHtmlTag(moveOpinion)%>"/>
    <input type="hidden" id="reDistOpinion" name="reDistOpinion" value="<%=EscapeUtil.escapeHtmlTag(reDistOpinion)%>"/>
    
	<!-- �ǰ���ȸ�˾� -->
    <input type="hidden" id="popupTitle" name="popupTitle" value=""/>
    <input type="hidden" id="popupOpinion" name="popupOpinion" value=""/>

	<!-- �ǰ��˾� -->
    <input type="hidden" id="returnFunction" name="returnFunction" value="" />
    <input type="hidden" id="btnName" name="btnName" value="" />
    <input type="hidden" id="opinionYn" name="opinionYn" value="" />
    <input type="hidden" id="comment" name="comment" value=""/>
    <input type="hidden" id="opinion" name="opinion" value=""/>
    <input type="hidden" id="procOpinion" name="procOpinion" value="<%=EscapeUtil.escapeHtmlTag(procOpinion)%>"/>
 

    <input type="hidden" id="bodyFileId" name="bodyFileId" value="<%=bodyVO.getFileId()%>" />
    <input type="hidden" id="bodyFileName" name="bodyFileName" value="<%=bodyVO.getFileName()%>" />
    <input type="hidden" id="bodyFileDisplayName" name="bodyFileDisplayName" value="<%=bodyVO.getDisplayName()%>" />
    <input type="hidden" id="bodyFileSize" name="bodyFileSize" value="<%=bodyVO.getFileSize()%>" />
	<!-- ���� --> 
	<input id="bodyFile" name="bodyFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft001))%>"></input>
	<!-- ÷�� --> 
	<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAttach(fileVOs))%>"></input>
   
    <input type="hidden" id="bindId" name="bindId" value="" />
    <input type="hidden" id="bindName" name="bindName" value="" />
    
    <input type="hidden" id="userId" name="userId" value="<%=userId%>" size="50" /> 
    <input type="hidden" id="userName" name="userName" value="<%=userName%>" size="50" />
    <input type="hidden" id="userPos" name="userPos" value="<%=userPos%>" size="50" />
    <input type="hidden" id="userDeptId" name="userDeptId" value="<%=deptId%>" size="50" />
    <input type="hidden" id="userDeptName" name="userDeptName" value="<%=deptName%>" size="50" />

    <input type="hidden" id ="enfLines" name="enfLines" value="<%=EscapeUtil.escapeHtmlTag(enfLines)%>"/><!-- ������ -->
    <input type="hidden" id ="publishId" name="publishId" value="<%=publishId%>"/><!-- �����Խ�id -->
    
	<input id="recvDeptId" name="recvDeptId" type="hidden" value="<%=enfRecvVO.getRecvDeptId()%>"/><!-- ���źμ�ID -->
	<input id="recvEnfType" name="recvEnfType" type="hidden" value="<%=enfRecvVO.getEnfType()%>"/><!-- �����ڽ������� --> 
	<input id="recvDeptName" name="recvDeptName" type="hidden" value="<%=enfRecvVO.getRecvDeptName()%>"/><!-- ���źμ��� -->
	<input id="refDeptId" name="refDeptId" type="hidden" value="<%=enfRecvVO.getRefDeptId()%>"/><!-- ����(��δ��)�μ�ID -->
	<input id="refDeptName" name="refDeptName" type="hidden" value="<%=enfRecvVO.getRecvDeptName()%>"/><!-- ����(��δ��)�μ��� -->
	<input id="recvUserId" name="recvUserId" type="hidden" value="<%=enfRecvVO.getRecvUserId()%>"/><!-- ������ID -->
	<input id="recvUserName" name="recvUserName" type="hidden" value="<%=enfRecvVO.getRecvUserName()%>"/><!-- �����ڸ� -->
	<input id="receiverOrder" name="receiverOrder" type="hidden" value="<%=enfRecvVO.getReceiverOrder()%>"/><!-- �����ڼ��� -->
	
	<!-- ��μ����� --> 
	<input id="appRecv" name="appRecv" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferEnfRecv(enfRecvVOs))%>"/>
	<input id="distDeptId" name="distDeptId" type="hidden" value=""/>
	<input id="distDeptName" name="distDeptName" type="hidden" value=""/>
	
	<input id="distributeFlag" name="distributeFlag" type="hidden" value=""/><!-- ���(D),����(R),�߰����(A),�̼�(M),���ο�û(Q),��ξ���(N) -->

	<input id="classNumber" name="classNumber" type="hidden" value="<%=CommonUtil.nullTrim(enfDocVO.getClassNumber()) %>"></input><!-- �з���ȣ --> 
	<input id="docnumName" name="docnumName" type="hidden" value="<%=CommonUtil.nullTrim(enfDocVO.getDocnumName()) %>"></input><!-- �з���ȣ�� -->
<%
	AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
	if(appOptionVO == null ) {
	    appOptionVO = new AppOptionVO();  
	}

	List<RelatedDocVO> relatedDocVOs = appDocVO.getRelatedDoc();
	List<RelatedRuleVO> relatedRuleVOs = appDocVO.getRelatedRule();
	List<CustomerVO> customerVOs = appDocVO.getCustomer();
%>	
<!-- ����ȹ�������  start-->
		<input id="app_appLine" name="app_appLine" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppLine(appLineVOs, compId))%>"/>
		<!-- ���ù��� --> 
		<input id="relatedDoc" name="relatedDoc" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedDoc(relatedDocVOs))%>"></input>
		<!-- ���ñ��� --> 
		<input id="app_relatedRule" name="app_relatedRule" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedRule(relatedRuleVOs))%>"></input>
		<!-- �ŷ�ó --> 
		<input id="app_customer" name="app_customer" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferCustomer(customerVOs))%>"></input>
		<input id="app_conserveType" name="app_conserveType" type="hidden" value="<%=appDocVO.getConserveType()%>"/><!-- �������� --> 
		<input id="app_readRange" name="app_readRange" type="hidden" value="<%=appDocVO.getReadRange()%>"/><!-- �������� --> 
		<!-- �߼����� -->
		<input id="app_sendOrgName" name="app_sendOrgName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getSendOrgName() == null) ? "" : sendInfoVO.getSendOrgName())%>"></input><!-- �߽ű���� -->
		<input id="app_senderTitle" name="app_senderTitle" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getSenderTitle() == null) ? "" : sendInfoVO.getSenderTitle())%>"></input><!-- �߽Ÿ��� -->
		<input id="app_headerCamp" name="app_headerCamp" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getHeaderCamp() == null) ? "" : sendInfoVO.getHeaderCamp())%>"></input><!-- ���ķ���� -->
		<input id="app_footerCamp" name="app_footerCamp" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getFooterCamp() == null) ? "" : sendInfoVO.getFooterCamp())%>"></input><!-- �Ϻ�ķ���� -->
		<input id="app_postNumber" name="app_postNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getPostNumber() == null) ? "" : sendInfoVO.getPostNumber())%>"></input><!-- �����ȣ -->
		<input id="app_address" name="app_address" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getAddress() == null) ? "" : sendInfoVO.getAddress())%>"></input><!-- �ּ� -->
		<input id="app_telephone" name="app_telephone" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getTelephone() == null) ? "" : sendInfoVO.getTelephone())%>"></input><!-- ��ȭ -->
		<input id="app_fax" name="app_fax" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getFax() == null) ? "" : sendInfoVO.getFax())%>"></input><!-- FAX -->
		<input id="app_via" name="app_via" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getVia() == null) ? "" : sendInfoVO.getVia())%>"></input><!-- ���� -->
		<input id="app_sealType" name="app_sealType" type="hidden" value="<%=(sendInfoVO.getSealType() == null) ? "" : sendInfoVO.getSealType()%>"></input><!-- �������� -->
		<input id="app_homepage" name="app_homepage" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getHomepage() == null) ? "" : sendInfoVO.getHomepage())%>"></input><!-- Ȩ������ -->
		<input id="app_email" name="app_email" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getEmail() == null) ? "" : sendInfoVO.getEmail())%>"></input><!-- �̸��� -->
		<input id="app_receivers" name="app_receivers" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getReceivers() == null) ? "" : sendInfoVO.getReceivers())%>"></input><!-- ���� -->
		<input id="app_displayNameYn" name="app_displayNameYn" type="hidden" value="<%=(sendInfoVO.getDisplayNameYn() == null) ? "" : sendInfoVO.getDisplayNameYn()%>"></input><!-- ���� -->
		<!--�������ݰ����ڰ����ȭ �������� ��ȸ �� ����ó �� �߰� 2014.12.17.JJE-->
		<input id="app_appRecv" name="app_appRecv" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppRecv(appDocVO.getReceiverInfo()))%>"/>
		
<!-- ����ȹ������� end -->
</div>	
</form>

<% if(lobCode.equals(lob091) || lobCode.equals(lob092)){ %>
<!-- �߼ۻ�Ȳ��ȸ -->
<form id="frmSendInfo" name="frmSendInfo" method="POST" action="<%=webUri%>/app/approval/sendInfo.do" target="popupWin" style="display:none">
	<input type="hidden" id="sendInfoCompId" name="sendInfoCompId" value="" />
	<input type="hidden" id="sendInfoEditFlag" name="sendInfoEditFlag" value="" />
	<input type="hidden" id="sendInfoDocId" name="sendInfoDocId" value="" />
	<input type="hidden" id="sendInfoDocState" name="sendInfoDocState" value="<%=appDocVO.getDocState()%>" />
	<input type="hidden" id="sendInfoComment" name="sendInfoComment" value=""/>
	<input type="hidden" id="sendInfoLobCode" name="sendInfoLobCode" value="<%=lobCode %>"/>
</form> 
<% } %>

</body>
</html>