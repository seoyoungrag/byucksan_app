/**
 * 
 */
package com.sds.acube.app.ws.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.imageio.ImageIO;
import javax.xml.ws.Holder;

//import org.apache.axis.utils.tcpmon;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.LogWrapper;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.ws.service.IEsbAppService;
import com.sds.acube.app.ws.service.ILegacyService;
import com.sds.acube.app.ws.vo.AppActionVO;
import com.sds.acube.app.ws.vo.AppAttachVO;
import com.sds.acube.app.ws.vo.AppDetailVO;
import com.sds.acube.app.ws.vo.AppFileVO;
import com.sds.acube.app.ws.vo.AppFileVOs;
import com.sds.acube.app.ws.vo.AppItemCountVO;
import com.sds.acube.app.ws.vo.AppListVOs;
import com.sds.acube.app.ws.vo.AppMenuVOs;
import com.sds.acube.app.ws.vo.AppReqVO;
import com.sds.acube.app.ws.vo.AppResultVO;
import com.sds.acube.app.ws.vo.CmnResVO;
import com.sds.acube.app.ws.vo.CmnVO;
import com.sds.acube.app.ws.vo.HeaderVO;
import com.sds.acube.app.ws.vo.LegacyVO;
import com.sds.acube.app.ws.vo.ReceiverVO;
import com.sds.acube.app.ws.vo.ResultVO;
import com.sds.acube.app.ws.vo.SenderVO;


/**
 * Class Name : TestWs.java <br>
 * Description : 설명 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2012. 5. 23. <br>
 * 수 정 자 : jth8172 <br>
 * 수정내용 : PC 개발환경에서 테스트 가능하도록 변경 //신전자결재TF<br>
 *           was temp 폴더에 appinfo.xml, body.html, attach.hwp 필요<br>
 *           appinfo.xml 파일내에 doc_id 는 unique 해야 함.(테스트시마아다 변경필요)
 *           jetty 를 Start 한 상황에서 <br>
 *           TestWs.java 파일을 Run As ... Java Application 으로 실행 <br>
 *           java 실행환경에서 argument 에 1 을 셋팅하면 연게기안 처리됨. <br>
 * 
 * @author chamchi
 * @since 2011. 5. 17.
 * @version 1.0
 * @see com.sds.acube.app.ws.client.TestWs.java
 */

public class TestWs {

    public static LogWrapper logger = LogWrapper.getLogger("com.sds.acube.app");

    public IEsbAppService esbAppService;
    
    public ILegacyService legacyService;

    private String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
    
    

    public static void main(String args[]) throws Exception {

	System.out.println("TEST >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	//System.in.read();
	new TestWs(args);

    }
    
    
    public TestWs(String[] args) {


	String was_server = AppConfig.getProperty("was_servers", "http://localhost:8080", "path");
	String webUri = was_server + AppConfig.getProperty("web_uri", "/ep", "path");
	
	JaxWsProxyFactoryBean factory = null;
	
	factory = new JaxWsProxyFactoryBean();
	factory.setServiceClass(IEsbAppService.class);
	factory.setAddress(webUri+"/app/esb/service/EsbAppService");
	factory.setBindingId(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_MTOM_BINDING);

	// ---------------------
	// 서비스 인터페이스
	// ---------------------
	esbAppService = (IEsbAppService) factory.create();
	
	factory = new JaxWsProxyFactoryBean();
	factory.setServiceClass(ILegacyService.class);
	factory.setAddress(webUri+"/app/esb/service/LegacyService");
	factory.setBindingId(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_MTOM_BINDING);

	// ---------------------
	// 서비스 인터페이스
	// ---------------------
	legacyService = (ILegacyService) factory.create();

	try {

	    if (args[0].equals("1") || null == args[0] ) { // 기안요청 서비스 테스트

		insertDoc();
	    }

	    if (args[0].equals("2")) { // 함조회테스트

		listBox();
	    }
	    
	    if (args[0].equals("3")) {  //이미지변환

		transImage();
	    }

	    if (args[0].equals("5")) { // 문서목록

		listDoc();
	    }	    
	    if (args[0].equals("6")) { // 문서내용

		selectDoc();
	    }	    
	    if (args[0].equals("7")) { // 문서갯수테스트

		countDoc();
	    }	  
	    if (args[0].equals("8")) { // 결재상세내용 조회(모바일)

		selectDocInfoMobile();
	    }	    
	    if (args[0].equals("9")) { // 결재 상세조회시 첨부파일요청

		getAttachFile();
	    }
	    if (args[0].equals("10")) { // 함별 문서갯수(포탈호출)

		countPortalDoc();
	    }
	    if (args[0].equals("11")) { // 결재처리요청(모바일)

		processAppMobile();
	    }
	    
	} catch (Exception e) {

	}
    }


    
    


    public void insertDoc() throws Exception {

	
	try
	{
	    
	  // StringTokenizer st;
	    
	   // st.n
	  //  Calendar cl;
	LegacyVO legacyVO = new LegacyVO();
	
	

	SenderVO senderVO = new SenderVO();
	senderVO.setOrgCode("1000001");
	senderVO.setOrgName("산은지주");
	senderVO.setDeptCode("1000015");
	senderVO.setDeptName("발송부서");
	senderVO.setUserId("send01");
	senderVO.setUserName("발송기안");
	senderVO.setPosition("기안직위");
	legacyVO.setSender(senderVO);
	
	ReceiverVO receiverVO = new ReceiverVO();
	receiverVO.setOrgCode("1000001");
	receiverVO.setOrgName("산은지주");
	receiverVO.setDeptCode("1000015");
	receiverVO.setDeptName("발송부서");
	receiverVO.setUserId("send01");
	receiverVO.setUserName("발송기안");
	legacyVO.setReceiver(receiverVO);
	
	HeaderVO headerVO = new HeaderVO();
	headerVO.setSystemCode("HR");
	headerVO.setBusinessCode("TR01");
	headerVO.setDocType("submit");//기안요청/검토요청 구분하여 필수 입력
	legacyVO.setHeader(headerVO);
	
	ResultVO resultVO = new ResultVO();
	resultVO.setMessageCode("TEST0101");
	legacyVO.setResult(resultVO);
	
	// -----------------------------------------------------------------------------
	

	
	// ----------------------------
	// ---------------------------
	// 1. 기안요청 서비스 테스트
	// ----------------------------
	// ----------------------------
	
	AppFileVOs fileVOs = new AppFileVOs();
	List<AppFileVO> files = new ArrayList<AppFileVO>();
	AppFileVO fileVO = new AppFileVO();

	File file = null;
	URL fileURL =  null;
	DataHandler handler = null;
	Holder<DataHandler> holder = null;
	
	//결재정보
	file = new File(uploadTemp + "/appinfo.xml");// 테스트파일
	fileURL = file.toURL();


	handler = new DataHandler(fileURL);

	holder = new Holder<DataHandler>();
	holder.value = handler;

	fileVO.setFileName("appinfo.xml");
	fileVO.setContent(handler);
	fileVO.setFileType("appinfo");

	files.add(fileVO);

	
	//본문
	file = new File(uploadTemp + "/body.html");// 본문html
	fileURL = file.toURL();


	handler = new DataHandler(fileURL);

	holder = new Holder<DataHandler>();
	holder.value = handler;
	fileVO = new AppFileVO();

	fileVO.setFileName("body.html");
	fileVO.setContent(handler);
	fileVO.setFileType("body");

	files.add(fileVO);
	
	
	
	//첨부
	file = new File(uploadTemp + "/attach.hwp");// 본문html
	fileURL = file.toURL();

	// DataHandler handler = new DataHandler();

	handler = new DataHandler(fileURL);

	holder = new Holder<DataHandler>();
	holder.value = handler;
	fileVO = new AppFileVO();

	fileVO.setFileName("attach15.hwp");
	fileVO.setContent(handler);
	fileVO.setFileType("attach");

	files.add(fileVO);

	
//	//첨부
//	file = new File(uploadTemp + "/attach1.hwp");// 
//	fileURL = file.toURL();
//
//	// DataHandler handler = new DataHandler();
//
//	handler = new DataHandler(fileURL);
//
//	holder = new Holder<DataHandler>();
//	holder.value = handler;
//	fileVO = new AppFileVO();
//
//	fileVO.setFilename("attach1.hwp");
//	fileVO.setContent(handler);
//	fileVO.setFile_type("attach");
//
//	files.add(fileVO);	
//	
//	//첨부
//	file = new File(uploadTemp + "/attach2.hwp");// 
//	fileURL = file.toURL();
//
//	// DataHandler handler = new DataHandler();
//
//	handler = new DataHandler(fileURL);
//
//	holder = new Holder<DataHandler>();
//	holder.value = handler;
//	fileVO = new AppFileVO();
//
//	fileVO.setFilename("attach2.hwp");
//	fileVO.setContent(handler);
//	fileVO.setFile_type("attach");
//
//	files.add(fileVO);	
	
	
	fileVOs.setFileVOs(files);

	
	// 기안요청 서비스호출
	LegacyVO legacyResVO = legacyService.insertAppDoc(legacyVO);

	
	logger.printVO(legacyResVO);

	
	}catch(Exception e){
	    e.printStackTrace();
	}
    }
    
    
    public void transImage() throws Exception{
	
	File file = new File(uploadTemp + "/카드.bmp");
	
	
	BufferedImage bi =  ImageIO.read(file);

	//bi.setRGB(10, 10, 255);
	
	File file1 = new File(uploadTemp,"test.jpg"); //
	
	ImageIO.write(bi, "jpg", file1);
	
	//URLEncoder.encode("","EUC-KR");
		
    }
    
    
  
    
    /**
     */
    public void listBox() throws Exception {

	// ---------------------------
	// --------------------------
	// 2. 함조회테스트
	// --------------------------
	// ---------------------------
	AppReqVO appReqVO = new AppReqVO();

	//appReqVO.setItemid("LOB003");
	appReqVO.setUserid("3064");
	appReqVO.setOrgcode("003");
	appReqVO.setDeptid("D003003");
	// -------------
	// 서비스호출
	// -------------
	AppMenuVOs appMenuVOs = esbAppService.listBox(appReqVO);

	logger.printVO(appReqVO);
	for(int i=0; i<appMenuVOs.getAppMenuVOs().size(); i++){
	    logger.printVO(appMenuVOs.getAppMenuVOs().get(i));
	}
    }
    /**
     */
    public void listDoc() throws Exception {

	// ---------------------------
	// --------------------------
	// 5. 문서목록테스트
	// --------------------------
	// ---------------------------
	AppReqVO appReqVO = new AppReqVO();

	appReqVO.setItemid("OPT104");
	appReqVO.setUserid("D3216289"); // D3146191
	appReqVO.setOrgcode("003");
	//appReqVO.setDeptid("003_A25");
	appReqVO.setReqcount(999);
	// -------------
	// 서비스호출
	// -------------
	//AppListVOs appListVOs = esbAppService.listDoc(appReqVO); // 포탈(생산/접수문서)
	AppListVOs appListVOs = esbAppService.listAppDoc(appReqVO);  // 모바일(생산문서)

	logger.printVO(appReqVO);
	logger.printVO(appListVOs);
	for(int i=0; i<appListVOs.getAppListVOs().size(); i++){
	logger.printVO(appListVOs.getAppListVOs().get(i));
	}
    }
    
 
    /**
     */
    public void countDoc() throws Exception {

	// ---------------------------
	// --------------------------
	// 7. 문서갯수테스트
	// --------------------------
	// ---------------------------
	AppReqVO appReqVO = new AppReqVO();

	appReqVO.setItemid("OPT104"); // OPT206
	appReqVO.setUserid("D3216289"); // D3146191
	appReqVO.setOrgcode("003");
	//appReqVO.setDeptid("003_A77");
	appReqVO.setReqcount(999);
	// -------------
	// 서비스호출
	// -------------
	AppItemCountVO appItemcounttVO = esbAppService.countDoc(appReqVO);

	logger.printVO(appReqVO);
	logger.printVO(appItemcounttVO);
	//for(int i=0; i<appListVOs.getAppListVOs().size(); i++){
	//logger.printVO(appListVOs.getAppListVOs().get(i));
	//}
    }
    
    /**
     */
    public void selectDoc() throws Exception {

	// ---------------------------
	// --------------------------
	// 6. 문서내용테스트
	// --------------------------
	// ---------------------------
	AppReqVO appReqVO = new AppReqVO();

	appReqVO.setItemid("APP12CF66F7FF26E9D131B72538C9FFFF80");
	//appReqVO.setUserid("D3146191");
	appReqVO.setOrgcode("003");
	//appReqVO.setDeptid("D002001");
	//appReqVO.setReqcount(999);
	// -------------
	// 서비스호출
	// -------------
	AppDetailVO detailVo = esbAppService.selectDocInfo(appReqVO);

	logger.printVO(appReqVO);
	logger.printVO(detailVo);
	logger.printVO(detailVo.getAppline());
	//for(int i=0; i<detailVo.getAppListVOs().size(); i++){
	//logger.printVO(detailVo.getAppListVOs().get(i));
	//}
    }
    
    public void selectDocInfoMobile() throws Exception {

	// ---------------------------
	// --------------------------
	// 8. 결재상세내용 조회(모바일) 테스트
	// --------------------------
	// ---------------------------
	AppReqVO appReqVO = new AppReqVO();
	appReqVO.setItemid("ENF1435C4C1E36EC61333D8803DCFFFF800");//APP4634607272F5381F133195EED9CFFFF8
	//appReqVO.setUserid("D3146191");			//ENF1435C4C1E36EC61333D8803DCFFFF800
	appReqVO.setOrgcode("003");
	//appReqVO.setDeptid("D002001");
	//appReqVO.setReqcount(999);
	// -------------
	// 서비스호출
	// -------------
	AppDetailVO detailVo = esbAppService.selectDocInfoMobile(appReqVO);

	//logger.printVO(appReqVO);
	logger.printVO(detailVo);
	
	List<AppLineVO>  appline = detailVo.getAppline();
	if (appline != null) {
	    AppLineVO lineVO = new AppLineVO();
	    int lineSize = appline.size();
	    for (int i=0; i<lineSize; i++) {
		lineVO = appline.get(i);
		logger.debug(">>>>>>>>>>>> approverName : "+lineVO.getApproverName());
		logger.debug(">>>>>>>>>>>> askType : "+lineVO.getAskType());
	    }
	}
	//logger.printVO(">>>>>>>>>>>> enfLine() : "+detailVo.getEnfline());
	List<EnfLineVO>  enfline = detailVo.getEnfline();
	if (enfline != null) {
	    EnfLineVO lineVO2 = new EnfLineVO();
	    int lineSize2 = enfline.size();
	    for (int i=0; i<lineSize2; i++) {
		lineVO2 = enfline.get(i);
		logger.debug(">>>>>>>>>>>> approverName : "+lineVO2.getProcessorName());
		logger.debug(">>>>>>>>>>>> askType : "+lineVO2.getAskType());
	    }
	}
	
	AppFileVOs appFileVOs = detailVo.getContent();
	logger.debug(">>>>>>>>>>>> appFileVOs : "+appFileVOs);
	logger.debug("============================================");
	logger.printVO(appFileVOs);
	logger.debug("============================================");
	AppFileVO appFileVO = new AppFileVO();
	int fileSize = appFileVOs.getFileVOs().size();
	logger.debug(">>>>>>>>>>>> File size : "+fileSize);
	
	for(int i=0; i<fileSize; i++){	   
	    appFileVO = appFileVOs.getFileVOs().get(i);
	    logger.debug("#File["+i+"]");
	    logger.printVO(appFileVO);
	}
    }
    
    /**
     * 결재 처리 요청
     */
    public void processAppMobile() throws Exception {
	logger.debug(">>>>>>>>>>>> 결재처리요청 <<<<<<<<<<<<<");
	AppActionVO appActionVO = new AppActionVO();
	appActionVO.setReqtype("updateApproval");
	appActionVO.setDocid("APP6D2383DC19BF37E3133499825C9FFFF8");
	appActionVO.setOrgcode("003");
	appActionVO.setUserid("D3216289"); // D3130235
	appActionVO.setActioncode("A1"); // A1:승인, A2:반려, A3:대기, A4:보류, A5:합의, A6:검토
	appActionVO.setUserpassword("qwe123");
	
	AppResultVO appResultVo = esbAppService.processDoc(appActionVO);
	logger.printVO(appResultVo);
    }
    
    /**
     * 결재 상세조회시 첨부파일요청
     */
    public void getAttachFile() throws Exception {

	// ---------------------------
	// --------------------------
	// 9. 결재첨부파일 조회 테스트
	// --------------------------
	// ---------------------------
	AppReqVO appReqVO = new AppReqVO();

	//APP64BE7A8C3FCC9A3D132680A1E17FFFF8  //운영테스트데이타
	//APPAB7AF2157816A13293D5F92DFFFF8002   //개발
	appReqVO.setItemid("ENF1435C4C1E36EC61333D8803DCFFFF800"); //ENF1435C4C1E36EC61333D8803DCFFFF800
	
	//d2942b042968cb8657de4b8ae6a6ec74
	appReqVO.setFileId("2cf569eb023065b0848a9b8008a7bc43"); //a4599b0d63db952acabbeb1b28cf6445 2cf569eb023065b0848a9b8008a7bc43
	
	//appReqVO.setUserid("D3146191");
	appReqVO.setOrgcode("003");
	//appReqVO.setDeptid("D002001");
	//appReqVO.setReqcount(999);
	// -------------
	// 서비스호출
	// -------------
	AppAttachVO attachVO = esbAppService.getAttachFile(appReqVO);

	logger.printVO(appReqVO);
	logger.printVO(attachVO.getFile());
	//logger.printVO(attachVO.getAppline());
//	for(int i=0; i<detailVo.getContent().getFileVOs().size(); i++){
//	    logger.printVO(detailVo.getContent().getFileVOs().get(i));
//	}
    }
    
    public void countPortalDoc() throws Exception {

	// ---------------------------
	// --------------------------
	// 10. 문서갯수테스트
	// --------------------------
	// ---------------------------
	AppReqVO appReqVO = new AppReqVO();

	appReqVO.setItemid("OPT104"); // OPT206
	appReqVO.setUserid("2044"); // D3146191
	appReqVO.setOrgcode("002");
	appReqVO.setDeptid("D002001");
	appReqVO.setReqcount(999);
	// -------------
	// 서비스호출
	// -------------
	AppItemCountVO appItemcounttVO = esbAppService.countPortalDoc(appReqVO);

	logger.printVO(appReqVO);
	logger.printVO(appItemcounttVO);
	//for(int i=0; i<appListVOs.getAppListVOs().size(); i++){
	//logger.printVO(appListVOs.getAppListVOs().get(i));
	//}
    }
}
