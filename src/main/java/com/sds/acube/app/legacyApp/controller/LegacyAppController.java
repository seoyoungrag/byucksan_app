package com.sds.acube.app.legacyApp.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.List;

import javax.activation.DataHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.anyframe.pagination.Page;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.impl.OrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.legacyApp.service.ILegacyAppService;
import com.sds.acube.app.legacyApp.vo.LegacyAppVO;
import com.sds.acube.app.relay.util.RelayUtil;
import com.sds.acube.app.ws.client.LegacyClientWs;
import com.sds.acube.app.ws.service.ILegacyService;
import com.sds.acube.app.ws.vo.AppFileVO;
import com.sds.acube.app.ws.vo.ApproverVO;
import com.sds.acube.app.ws.vo.HeaderVO;
import com.sds.acube.app.ws.vo.LegacyVO;
import com.sds.acube.app.ws.vo.ReceiverVO;
import com.sds.acube.app.ws.vo.ReserveVO;
import com.sds.acube.app.ws.vo.ResultVO;
import com.sds.acube.app.ws.vo.SenderVO;

/**
 * Class Name  : LegacyAppController.java<br>
 * Description : 결재연계 테스트용<br>
 * Modification Information<br><br>
 * 수 정 일 : <br>
 * 수 정 자 : <br>
 * 수정내용 : <br>
 * @author  jth8172 
 * @since   2012. 3. 23.
 * @version 1.0 
 * @see  com.sds.acube.app.LegacyApp.controller.LegacyAppController.java
 */
@SuppressWarnings("serial")
@Controller("LegacyAppController")
@RequestMapping("/app/LegacyApp/*.do")
public class LegacyAppController extends BaseController {


    /**
	 */
    @Autowired
    private ILegacyAppService LegacyAppService;
 
    /**
	 */
    @Autowired
    private OrgService orgservice;     
    // 연계목록
    
 // 연계작성저장  
    // 등록과 수정을 함께 처리(삭제후 insert)
    @RequestMapping("/app/LegacyApp/LegacyApiReg.do")
    public ModelAndView regLegacyApi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("LegacyAppController.processOK");
		System.out.println("TEST >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		//System.in.read();
		SenderVO senderVO = new SenderVO();
		senderVO.setOrgCode("C000");
		senderVO.setOrgName("한국주택공사");
		senderVO.setDeptCode("Z800");
		senderVO.setDeptName("발송부서");
		senderVO.setUserId("test02");
		senderVO.setUserName("발송기안");
		senderVO.setPosition("기안직위");

		ReceiverVO receiverVO = new ReceiverVO();
		receiverVO.setOrgCode("C000");
		receiverVO.setOrgName("한국주택공사");
		receiverVO.setDeptCode("Z800");
		receiverVO.setDeptName("발송부서");
		receiverVO.setUserId("test02");
		receiverVO.setUserName("발송기안");

		
		HeaderVO headerVO = new HeaderVO();
		headerVO.setBusinessCode("CLM");
	    headerVO.setOriginDocId(""); // 프로그램 ID를 입력함, 입력을하지 않으면 32bit 키값이 입력됨.
	    headerVO.setTitle("출장신청서_0402_local_jkkim22");
	    //headerVO.setWebUri("http://127.0.0.1:8090/ep");
	    //headerVO.setUploadTemp("D:/_localDev_lh/temp/legacyClient");
	    //String retUrl = "http://172.30.127.112:8001/ep/app/jsp/login/test.jsp";// 업무쪽에서 호출하는 URL을 처리함.
	    String retUrl = "http://172.30.127.144/COM/lhStlmListener.lh?CLASS_NAME=lh.sample.stlm.cl.StlmSampleSC&METHOD_NAME=sampleTest";
	    //String retUrl = "http://172.30.127.112:8001/ep/app/jsp/login/test.jsp?CLASS_NAME=lh.online.gna.test.TestClass&METHOD_NAME=process";// 업무쪽에서 호출하는 URL을 처리함.
	    String uploadTemp = "/Files/ea/MSP";// 엄무쪽 파일 쌓이는 곳
	    String actionType = "1";// 연계서비스 호출
	    
	    //################# Body 부분 처리 Sample #####################
		String sfilePath = "/Files/ea/HtmlTemp/MSP/CLM0001.html";
		File sfile = new File(sfilePath);

	    String guid = "";
	    guid = GuidUtil.getGUID(); 

		// 연계 상위폴더 생성 start
		String filePath = uploadTemp + "/" + receiverVO.getOrgCode() + "/" + guid + "/body.html";
		File tfile = new File(filePath);
		if(!tfile.exists()) {
			tfile.mkdirs();
		}
		
		copyFile(sfile,tfile);// 템플릿 본문파일을 이용하여 본문을 만들수 있도록 타겟 위치에 복사
		///////////////////////////////////
		// 본문경로에 있는 파일에 Data 넣어주는 로직이
		// 추가되어야함.
		///////////////////////////////////
		headerVO.setBodyFilePath(sfilePath);
	    //##########################################################
	    String[][] attaches = null;
	    LegacyClientWs legacyClient = new LegacyClientWs(senderVO,receiverVO,headerVO,retUrl,uploadTemp, attaches);
	    LegacyVO legacyResVO = legacyClient.getLegacyResVO();
	    System.out.println("RESULT : "+ legacyResVO.getResult().getMessageCode());
	    System.out.println("DOCID : "+ legacyResVO.getResult().getDocId());
	    String appDocId = legacyResVO.getResult().getDocId();
		mav.addObject("result", appDocId );
	
		//HttpUtil.goAppUrl(response, "test02", appDocId);
		
		
		return mav;
    }
    
    /**
  	 * 
  	 * <pre> 
  	 *  source 파일을 target으로 파일 복사
  	 * </pre>
  	 * @param source
  	 * @param target
  	 * @throws Exception
  	 */
  	public static void copyFile(File source, File target) throws Exception {
  		//스트림, 채널 선언
  		FileInputStream inputStream = null;
  		FileOutputStream outputStream = null;
  		FileChannel fcin = null;
  		FileChannel fcout = null;
  		try {
  			if(!target.getParentFile().exists()) {
  				target.getParentFile().mkdirs();
  			}
  			//스트림 생성
  			inputStream = new FileInputStream(source);
  			outputStream = new FileOutputStream(target.getPath() + ".tmp");
  			//채널 생성
  			fcin = inputStream.getChannel();
  			fcout = outputStream.getChannel();
  			//채널을 통한 스트림 전송
  			long size = fcin.size();
  			fcin.transferTo(0, size, fcout);
  		} catch (Exception e) {
  		} finally {
  			//자원 해제
  			if(fcout != null) {
  				fcout.close();
  			}
  			if(fcin != null) {
  				fcin.close();
  			}
  			if(outputStream != null) {
  				outputStream.close();
  			}
  			if(inputStream != null) {
  				inputStream.close();
  			}
  			
  			for(File file : target.getParentFile().listFiles()) {
  				if(file.getName().equals(target.getName() + ".tmp")) {
  					if(!file.renameTo(target)){
  						target.delete();
  						file.renameTo(target);
  					}
  				}
  			}
  		}
  	}

    // 연계작성저장  
    // 등록과 수정을 함께 처리(삭제후 insert)
    @RequestMapping("/app/LegacyApp/LegacyAppReg.do")
    public ModelAndView regLegacyApp(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	HttpSession session = request.getSession();
 
		String currentDate = DateUtil.getCurrentDate();

		LegacyAppVO legacyAppVO = new LegacyAppVO();
		
		ModelAndView mav = new ModelAndView("LegacyAppController.processOK");

		int result = 0;
		String TripCd = CommonUtil.nullTrim(request.getParameter("tripCd"));
		if(!"".equals(TripCd)) {
			legacyAppVO.setTripCd(TripCd);
			result = LegacyAppService.deleteLegacyApp(legacyAppVO);
		}	
		legacyAppVO.setTripCd(GuidUtil.getGUID());
		legacyAppVO.setRegDate(currentDate);
		legacyAppVO.setFlag("0");
		if(!"".equals(TripCd)) {
			legacyAppVO.setDeptId(CommonUtil.nullTrim(request.getParameter("deptId")) );
		} else {
			legacyAppVO.setDeptId((String)session.getAttribute("DEPT_ID"));
		}
		if(!"".equals(TripCd)) {
			legacyAppVO.setUserId(CommonUtil.nullTrim(request.getParameter("userId")) );
		} else {
			legacyAppVO.setUserId((String)session.getAttribute("LOGIN_ID"));
		}
		legacyAppVO.setFromDate(CommonUtil.nullTrim(request.getParameter("fromDate")) + " 00:00:00");
		legacyAppVO.setToDate(CommonUtil.nullTrim(request.getParameter("toDate")) + " 00:00:00");
		legacyAppVO.setTitle(CommonUtil.nullTrim(request.getParameter("title")));
		legacyAppVO.setObjective(CommonUtil.nullTrim(request.getParameter("objective")));
		legacyAppVO.setAmount(Integer.parseInt(CommonUtil.nullTrim(request.getParameter("amount"))));
		//legacyAppVO.setBusinessCode(CommonUtil.nullTrim(request.getParameter("businessCode")));
		legacyAppVO.setBusinessCode("CLM");
		result = LegacyAppService.insertLegacyApp(legacyAppVO);
		
		LegacyVO legacyResVO = new LegacyVO();
		String appDocId = "";
		if(result>0) {
			//1.연계를 위한 파일 생성, 결재연계서비스 호출
			String[] approvers = new String[10];
			String[] arts = new String[10];

			for (int nLoop = 0; nLoop < 10; ++nLoop) {
				approvers[nLoop] = CommonUtil.nullTrim(request.getParameter("approver" + (nLoop+1)));
				arts[nLoop] = CommonUtil.nullTrim(request.getParameter("art" + (nLoop+1)));
			}
			legacyResVO = insertDoc(legacyAppVO, approvers, arts);
			result = Integer.parseInt(legacyResVO.getResult().getMessageCode());
			appDocId = legacyResVO.getResult().getDocId();

		}
		mav.addObject("result", appDocId );
	
		return mav;
    }


    //결재연계 송신호출
    public LegacyVO insertDoc( LegacyAppVO legacyAppVO, String[] approvers, String[] arts ) throws Exception {
		
		// 기간시스템으로 테스트 하기위하며 만든것.
		// 실제로는 기간시스템의 변수들로 정보를 채워야 하지만
		// 테스트를 위하여 임의 생성하였음
		String currentDate = DateUtil.getCurrentDate();
		LegacyVO legacyResVO = new LegacyVO();
		String businessCode = legacyAppVO.getBusinessCode();
		try
		{
			
		    //기간시스템 연계기안을 위한 환경
			String uploadBizTemp = AppConfig.getProperty("path", "", "legacy/"+businessCode+"/receive");
			String compList = AppConfig.getProperty("compid", "", "companyinfo");

			LegacyVO legacyVO = new LegacyVO();
			AppFileVO fileVO = null;
			
			File file = null;
			URL fileURL =  null;
			DataHandler handler = null;
			Holder<DataHandler> holder = null;
			JAXBContext  jContext = null;
			Marshaller m = null;
			StringBuffer strBuf = new StringBuffer();
			String strReturn = "1";  //리턴 코드 : 성공:1, 실패 0

			// --------------------- 데이타 생성 ------------------ START ------------//	
			//1. 헤더정보
			UserVO drafter = orgservice.selectUserByLoginId(legacyAppVO.getUserId());

			SenderVO senderVO = new SenderVO();
			senderVO.setOrgCode(drafter.getCompID());
			senderVO.setOrgName(drafter.getCompName());
			senderVO.setDeptCode(drafter.getDeptID());
			senderVO.setDeptName(drafter.getDeptOtherName());
			senderVO.setPosition(drafter.getPositionName());
			senderVO.setUserId(drafter.getUserID());
			senderVO.setUserName(drafter.getUserName());
			legacyVO.setSender(senderVO);
			
			ReceiverVO receiverVO = new ReceiverVO();
			receiverVO.setOrgCode(drafter.getCompID());
			receiverVO.setOrgName(drafter.getCompName());
			receiverVO.setDeptCode(drafter.getDeptID());
			receiverVO.setDeptName(drafter.getDeptOtherName());
			receiverVO.setPosition(drafter.getPositionName());
			receiverVO.setUserId(drafter.getUserID());
			receiverVO.setUserName(drafter.getUserName());
			legacyVO.setReceiver(receiverVO);
			
			ResultVO resultVO = new ResultVO();  // ESB 연계용 메세지 설정(테스트 에서는 제외)
			resultVO.setMessageCode("TEST0101");  // 임의지정
			legacyVO.setResult(resultVO);

			// 연계 상위폴더 생성 start
			if(!CommonUtil.isNullOrEmpty(compList)) {
				for(String compId : compList.split(",")) {
					file = new File(uploadBizTemp + "/" + compId);
					if(!file.exists()) {
						file.mkdirs();
					}
				}
			}
			
			String filePath = uploadBizTemp + "/" + receiverVO.getOrgCode() + "/" + legacyAppVO.getTripCd();
			file = new File(filePath);
			if(!file.exists()) {
				file.mkdirs();
			}
			// 연계 상위폴더 생성 end
	
			//2. 결재자 정보
			List<UserVO> userVOs = orgservice.selectUserListByOrgId(drafter.getCompID(), drafter.getDeptID(), 9);

			ApproverVO approverVO = null;
			int cnt = userVOs.size();
			int approverCount = 0;
			for (int nLoop = 0; nLoop < approvers.length; nLoop++) {
				if ("approver".equals(approvers[nLoop])) {
					break;
				} else {
					approverCount++;
				}	
			}	
			 
			for (int nLoop = 0; nLoop < cnt; ++nLoop) {
				if ("approver".equals(approvers[nLoop])) {
					break;
				}
				UserVO approver = null;
				for (int nLoop2 = 0; nLoop2 < cnt; ++nLoop2) {
					if (userVOs.get(nLoop2).getUserID().equals(approvers[nLoop])) {
						approver = userVOs.get(nLoop2);
						break;
					}
				}
				approverVO = new ApproverVO();
				approverVO.setSerialOrder((nLoop+1)+"");
				approverVO.setParallelOrder("1");
				approverVO.setUserName( approver.getUserName());
				approverVO.setUserId(approver.getUserID());
				approverVO.setPosition(approver.getPositionName());
				approverVO.setDeptName(approver.getDeptName());
				approverVO.setDeptCode(approver.getDeptID());
				approverVO.setAskType(arts[nLoop]);
				approverVO.setActType("");
				approverVO.setActDate("");
				approverVO.setOpinion("");
				if (nLoop == 0 && approverCount >1) {  //기안자외 결재자 존재시 기안자가 기안처리를 한것으로 간주하여 검토자 대기함에 바로나타남
					approverVO.setActType("APT001");
					approverVO.setActDate(currentDate);
				} else {
					if(nLoop == 1 && approverCount > 1){ //기안자외 결재자 존재시 기안자가 연계기안 대기인 것으로 간주하여 설정 -연계기안함
						approverVO.setActType("APT003");
					}
				}
				legacyVO.getApprovers().add(approverVO);
			}

			
			//3.본문파일
			file = new File(filePath + "/body.html");// 본문html
			fileURL = file.toURL();
			
			strBuf = new StringBuffer();
			strBuf.append("<html>\r\n");
			strBuf.append("<head>\r\n");
			strBuf.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=EUC-KR\">\r\n");
			strBuf.append("<style type=\"text/css\">\r\n");
			strBuf.append("body {font-family:굴림,verdana,arial,sans-serif;font-size:12pt;}\r\n");
			strBuf.append("td   {font-family:굴림,verdana,arial,sans-serif;font-size:12pt; vertical-align:middle}\r\n");
			strBuf.append("</style>\r\n");
			strBuf.append("</head>\r\n");
			strBuf.append("<body>\r\n");
			strBuf.append("&nbsp;&nbsp;&nbsp;&nbsp;다음과 같이 출장을 신청 하오니 재가 바랍니다.<br><br>\r\n");
			strBuf.append("<center>- 다 음 -</center><br>\r\n");
			strBuf.append("  <center>\r\n");
			strBuf.append("    <table border=\"1\" width=\"90%\">\r\n");
			strBuf.append("        <tr>\r\n");
			strBuf.append("          <td align=\"center\">목 적</td>\r\n");
			strBuf.append("          <td>" + legacyAppVO.getObjective() + "</td>\r\n");
			strBuf.append("        </tr>\r\n");
			strBuf.append("        <tr>\r\n");
			strBuf.append("          <td align=\"center\">기 간</td>\r\n");
			strBuf.append("          <td align=\"center\">" + legacyAppVO.getFromDate() + " ~ " + legacyAppVO.getToDate() + "</td>\r\n");
			strBuf.append("        </tr>\r\n");
			strBuf.append("        <tr>\r\n");
			strBuf.append("          <td align=\"center\">비 용</td>\r\n");
			strBuf.append("          <td align=\"right\">" + legacyAppVO.getAmount() + "원</td>\r\n");
			strBuf.append("        </tr>\r\n");
			strBuf.append("    </table>\r\n");
			strBuf.append("  </center>\r\n");
			strBuf.append("  <br>\r\n");
			strBuf.append("  * 본 문서는 전자결재 시스템 연동 테스트를 위한 문서입니다.<br>\r\n");
			strBuf.append("  끝.\r\n");
			strBuf.append(" </body>\r\n");
			strBuf.append("</html>\r\n");		
			
			try {
				FileWriter fw = new FileWriter(file);
				BufferedWriter bw  = new BufferedWriter(fw);
				bw.write(strBuf.toString());
				bw.flush();
				bw.close();
				fw.close();
			}catch(Exception e){
			    e.printStackTrace();
			    legacyResVO.getResult().setMessageCode("0");
			    return legacyResVO;
		    }			
			
			handler = new DataHandler(fileURL);
		
			holder = new Holder<DataHandler>();
			holder.value = handler;
			
			fileVO = new AppFileVO();
			fileVO.setFileName("body.html");
			fileVO.setContent(handler);
			fileVO.setFileType("body");
		
			legacyVO.getFiles().add(fileVO);
			
			
			
			//4. 첨부파일
			
			//4-1. 파일을 미리 인터페이스용 서버의 폴더에 업로드한다.(ftp 등 이용)
			
			//4-2. 파일 정보 생성
			file = new File(filePath + "/attach.txt");// 첨부파일 - 테스트를 위해 한개파일 정도만 사용함 
			try {  // 테스트를 위해 전송대신 임의 생성함
				FileWriter fw = new FileWriter(file);
				fw.write("결재연계 첨부 테스트용 파일");
				fw.close();
			}catch(Exception e){
			    e.printStackTrace();
			    legacyResVO.getResult().setMessageCode("0");;
			    return legacyResVO;
		    }		

			fileURL = file.toURL();
		
			handler = new DataHandler(fileURL);
		
			holder = new Holder<DataHandler>();
			holder.value = handler;
			
			fileVO = new AppFileVO();
			fileVO.setFileName("attach.txt");
			fileVO.setContent(handler);
			fileVO.setFileType("attach");
			legacyVO.getFiles().add(fileVO);


			
			//5. 최종 결재연계정보
			HeaderVO headerVO = new HeaderVO();
			headerVO.setSystemCode("APP"); //임의지정
			headerVO.setBusinessCode(legacyAppVO.getBusinessCode()); //임의 지정,업무코드를 받아서 처리
			headerVO.setDocType("submit"); //기안요청/검토요청 구분하여 필수 입력
			headerVO.setSendServerId("CLM"); // 발신 서버 임의로 지정함
			headerVO.setReceiveServerId("CLM"); // 수신 서버 임의로 지정함
		    headerVO.setOriginDocId(legacyAppVO.getTripCd()); // 출장신청 ID를 DOCID 로 사용
		    headerVO.setTitle(legacyAppVO.getTitle());
		    headerVO.setDocNum("");  //문서분류 있을경우 명기
		    headerVO.setPublication("1");
		    headerVO.setDraftDate(currentDate);
			legacyVO.setHeader(headerVO);
			
			ReserveVO reserveVO = new ReserveVO();
			legacyVO.setReserve(reserveVO);		
			//결재연계정보파일 - 스케줄러 테스트용
			file = new File(filePath + "/" + legacyAppVO.getTripCd() + ".xml"); 
			fileURL = file.toURL();
			jContext = JAXBContext.newInstance(LegacyVO.class);
			m = jContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(legacyVO, file);
			
			//6. EOF 파일(정보 전송 종료를 명시함)
			file = new File(filePath + "/" + legacyAppVO.getTripCd() + ".eof");// EOF 파일
			try { // 테스트를 위해 전송대신 임의 생성함
				FileWriter fw = new FileWriter(file);
				fw.close();
			}catch(Exception e){
			    e.printStackTrace();
			    legacyResVO.getResult().setMessageCode("0");;
			    return legacyResVO;
		    }		
			
				
			// --------------------- 데이타 생성 ------------------ END ------------//	
			
			
			
			// --------------------- 결재연계 정의 ---------------------------------//	
			// 결재연계시 파일을 통한 스케줄러 연동은 웹서비스를 호출하지 않으면 됨. 
			
			//String legacyAppMethod ="FILE";
			String legacyAppMethod ="WEBSERVICE";
			
  			if("WEBSERVICE".equals(legacyAppMethod) ) {
			// --------------------- 웹서비스 연계방식 ------------------ start ------------//	
			
				// 결재연계 웹서비스
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(ILegacyService.class);
				factory.setAddress("http://172.30.127.112:8001/ep/app/legacy/service/LegacyService");
				factory.setBindingId(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_MTOM_BINDING);
			
			    // 기안요청 서비스호출  - 웹서비스 테스트용
			    ILegacyService legacyService = (ILegacyService) factory.create();	
				legacyResVO = legacyService.insertAppDoc(legacyVO);
				strReturn = legacyResVO.getResult().getMessageCode();
 				logger.printVO(legacyResVO);

			// --------------------- 웹서비스 연계방식 ------------------ end ------------//	
			}

			return legacyResVO;//Integer.parseInt(strReturn);   // 성공시 1, 실패면 0 
			
		}catch(Exception e){
		    e.printStackTrace();
		    legacyResVO.getResult().setMessageCode("0");
		    return legacyResVO;
		}
 
   }
    
    
    
    
    
 
    //결재연계 수신호출
    @RequestMapping("/app/LegacyApp/LegacyUpdateAckApp.do")
    public ModelAndView getBizAck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 기간시스템으로 테스트 하기위하며 만든것.
		// 실제로는 기간시스템의 변수들로 정보를 채워야 하지만
		// 테스트를 위하여 임의 생성하였음
    	// 스케줄러 등에 의해 수신폴더의 수신파일 정보를 체크하여 파일이 존재하면 진행함
    	// 테스트를 위해 스케줄러를 사용하지 않고 호출시 수동으로 정보를 체크함

    	//기간시스템 연계기안을 위한 환경
		String uploadBizTemp = AppConfig.getProperty("path", "", "CLM.SEND");
		String compList = AppConfig.getProperty("compid", "", "companyinfo");
		File file = null;
		String recvCompId  =  CommonUtil.nullTrim(request.getParameter("compId"));  // 회사코드를 받아옴
		
		// 연계 상위폴더 생성 start
		if(!CommonUtil.isNullOrEmpty(compList)) {
			for(String compId : compList.split(",")) {
				file = new File(uploadBizTemp + "/" + compId);
				if(!file.exists()) {
					file.mkdirs();
				}
			}
		}
		
		String filePath = uploadBizTemp + "/" + recvCompId + "/";
		file = new File(filePath);
		if(!file.exists()) {
			file.mkdirs();
		}

		File inputDir = new File(filePath + "/inputDir");  // 수신폴더
		if(!inputDir.exists()) {
			inputDir.mkdirs();
		}
		File processDir = new File(filePath + "/processDir");  // 처리폴더
		if(!processDir.exists()) {
			processDir.mkdirs();
		}
		File successDir = new File(filePath + "/successDir");  // 완료폴더
		if(!successDir.exists()) {
			successDir.mkdirs();
		}
		File errorDir = new File(filePath + "/errorDir");  // 오류폴더
		if(!errorDir.exists()) {
			errorDir.mkdirs();
		}
		
		// 연계 상위폴더 생성 end

    	String strReturn = "1";  //리턴 코드 : 성공:1, 실패 0
    	int docCount = inputDir.listFiles().length; //처리건수
    	RelayUtil relayUtil = new RelayUtil();  //기타기능을 사용하기위해 잠간 빌려씀. 기간시스템에서는 자체구현 필요
		try {
			// 수신임시 폴더 위치
			for(File recvDocDir : inputDir.listFiles()) {  // 수신폴더에 수신 DOCID 폴더가 있는지 검사
				for(File recv : recvDocDir.listFiles()) {  //수신 DOC 하위 파일 목록 가져옴
					if( recv.getName().indexOf("eof") != -1) {   // eof 파일이 존재하면 해당건을 수신완료로 간주하고
						
						File tmpAckFile = new File(recvDocDir.getPath() + "/" + recv.getName().substring(0, recv.getName().lastIndexOf(".")) + ".xml");
						File procAckFile = new File(processDir.getPath() + "/" + recv.getName().substring(0, recv.getName().lastIndexOf(".")) + ".xml");
						
						// xml 파일을 process 로 복사하여 처리준비함.
						relayUtil.copyFile( tmpAckFile, procAckFile );
						
						//1. 파일을 읽어서 VO 에 넣음
			 			JAXBContext jContext = JAXBContext.newInstance(LegacyVO.class);
						Unmarshaller m = jContext.createUnmarshaller();
						LegacyVO legacyVO = (LegacyVO) m.unmarshal(procAckFile);

						//2. Ack 정보검사(Legacy 출장신청 규칙에 따름)
						String strFlg = "0";  
 						for (ApproverVO approver :  legacyVO.getApprovers()){
							if(!"".equals(approver.getActDate()) ) {  //결재 처리일자가 존재시
								if("1".equals(approver.getSerialOrder()) ) {  // 기안완료
									strFlg = "1";
								} else if(("ART050,ART051,ART052,ART053,ART054".indexOf(approver.getAskType()) ) > -1 ) { // 마지막 결재자 
									strFlg = "5";
								} else if(1 < Integer.parseInt(approver.getSerialOrder()) ) { // 진행중 
									strFlg = "2";
								}
								if("APT002".equals(approver.getActType() ) ) { // 반려 
									strFlg = "3";
								}	
							}	

						}// for approver
 						//3. Ack 정보를 업데이트
 						int rtn = updateAck(legacyVO.getHeader().getSystemCode(), legacyVO.getHeader().getBusinessCode(), legacyVO.getHeader().getOriginDocId(), strFlg);
 						if (rtn <=0) {
							// 오류시 오류폴더로 복사
 							File errorAckFile = new File(errorDir.getPath() + "/" + recv.getName().substring(0, recv.getName().lastIndexOf(".")) + ".xml");
							relayUtil.copyFile( procAckFile, errorAckFile );
							procAckFile.delete(); //진행파일 삭제
 						} else {
	 						//4.처리완료후 파일 삭제, 완료폴더로 이동 
							File successAckFile = new File(successDir.getPath() + "/" + recv.getName().substring(0, recv.getName().lastIndexOf(".")) + ".xml");
							relayUtil.copyFile( procAckFile, successAckFile );
							procAckFile.delete(); //진행파일 삭제
 						}
					} //if eof
					
				} // for file
				CommonUtil.deleteDirectory(recvDocDir,false);
 			} // for dir
		}catch(Exception e){
		    e.printStackTrace();
		    strReturn = "0";
		}

		ModelAndView mav = new ModelAndView("LegacyAppController.processOK");
		mav.addObject("result", strReturn );
		mav.addObject("count", docCount+"" );
		return mav;   // 성공시 1, 실패면 0 
}
    
   // 연계 Ack 적용
    public int updateAck(String systemCode, String businessCode, String businessDocId, String strFlg) throws Exception {
    	//systemCode = "HR", businessCode = "TR01" 일 경우를 가정하여 테스트 업데이트를 진행함
		LegacyAppVO legacyAppVO = new LegacyAppVO();
		
		int result = 0;
		String TripCd = CommonUtil.nullTrim(businessDocId);
		if(!"".equals(TripCd)) {
			legacyAppVO.setTripCd(TripCd);
			legacyAppVO.setFlag(strFlg);
			result = LegacyAppService.updateLegacyAckApp(legacyAppVO);
		}	

		return result;
    }
    
    
    
    // 코티스 파일 연동 후처리 by jkkim
    @RequestMapping("/app/LegacyApp/LegacyFileMng.do")
    public void movLegacyApi(HttpServletRequest request, 
    						HttpServletResponse response) throws Exception {
      	
    	String sFName = request.getParameter("fname");
    	String eaiCnntNo = request.getParameter("EAI_CNNT_NO");
    	String maEaiCnntNo = request.getParameter("MA_EAI_CNNT_NO");
    	if(sFName == null){ 
    		sFName = "";
    	}
    	String bcode = request.getParameter("bcode");
    	String sFilePath = "/Files/CEA/legacy/lhExtraBiz/"+bcode+"/receive/";
    	String sFullPath = sFilePath + sFName;
    	//String strToday = CommonUtil.getToday();
    	String strToday2 = "";
    	if(sFName.length() > 6){
    		strToday2 = sFName.substring(0, 6);
    	}
    	
    	String strFullPathByDay = sFilePath+eaiCnntNo;// sFilePath+strToday2; : 날짜로 처리함..
    	if(bcode != null && !bcode.equals("")){
    		if("SUP".equals(bcode)){
    			strFullPathByDay = sFilePath+maEaiCnntNo;
    		}
    	}
    
    	try{
    		File file = new File(sFullPath);
    		File file2 = new File(strFullPathByDay);
    		if(file.exists()){
    			file2.mkdirs();
    			if(file2.exists()){
    				File file3 = new File(strFullPathByDay+"/"+sFName);
    				//copyFile(file,file3);
    				file.renameTo(file3);
    			}else{
    				logger.debug(strFullPathByDay + " is Not Existed");
    			}
    		}else{
    			logger.debug(sFullPath + " is Not Existed");
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    
}