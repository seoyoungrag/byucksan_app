package com.sds.acube.app.legacyTest.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
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

import com.sds.acube.app.legacyTest.service.ILegacyTestService;
import com.sds.acube.app.legacyTest.vo.LegacyTestVO;
import com.sds.acube.app.relay.util.RelayUtil;
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
 * Class Name  : LegacyTestController.java<br>
 * Description : 결재연계 테스트용<br>
 * Modification Information<br><br>
 * 수 정 일 : <br>
 * 수 정 자 : <br>
 * 수정내용 : <br>
 * @author  jth8172 
 * @since   2012. 3. 23.
 * @version 1.0 
 * @see  com.sds.acube.app.LegacyTest.controller.LegacyTestController.java
 */
@SuppressWarnings("serial")
@Controller("LegacyTestController")
@RequestMapping("/app/LegacyTest/*.do")
public class LegacyTestController extends BaseController {


    /**
	 */
    @Autowired
    private ILegacyTestService LegacyTestService;
 
    /**
	 */
    @Autowired
    private OrgService orgservice;     
    // 연계목록
    

    
    @RequestMapping("/app/LegacyTest/LegacyTestList.do")
    public ModelAndView listLegacyTest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String cpage = CommonUtil.nullTrim(request.getParameter("cPage"));

		
		if (cpage.equals("")) cpage="1";
		
		LegacyTestVO legacyTestVO = new LegacyTestVO();
		 
		Page page =  LegacyTestService.listLegacyTest(legacyTestVO, Integer.parseInt(cpage));
		ModelAndView mav = new ModelAndView("LegacyTestController.listLegacyTest");
  		mav.addObject("curPage", cpage);
		mav.addObject("LegacyTestVOpage", page.getList() );
		mav.addObject("totalCount" , page.getTotalCount() );
 		
		return mav; 
    }

    // 연계작성저장  
    // 등록과 수정을 함께 처리(삭제후 insert)
    @RequestMapping("/app/LegacyTest/LegacyTestReg.do")
    public ModelAndView regLegacyTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	HttpSession session = request.getSession();
 
		String currentDate = DateUtil.getCurrentDate();

		LegacyTestVO legacyTestVO = new LegacyTestVO();
		
		ModelAndView mav = new ModelAndView("LegacyTestController.processOK");

		int result = 0;
		String TripCd = CommonUtil.nullTrim(request.getParameter("tripCd"));
		if(!"".equals(TripCd)) {
			legacyTestVO.setTripCd(TripCd);
			result = LegacyTestService.deleteLegacyTest(legacyTestVO);
		}	
		legacyTestVO.setTripCd(GuidUtil.getGUID());
		legacyTestVO.setRegDate(currentDate);
		legacyTestVO.setFlag("0");
		if(!"".equals(TripCd)) {
			legacyTestVO.setDeptId(CommonUtil.nullTrim(request.getParameter("deptId")) );
		} else {
			legacyTestVO.setDeptId((String)session.getAttribute("DEPT_ID"));
		}
		if(!"".equals(TripCd)) {
			legacyTestVO.setUserId(CommonUtil.nullTrim(request.getParameter("userId")) );
		} else {
			legacyTestVO.setUserId((String)session.getAttribute("LOGIN_ID"));
		}
		legacyTestVO.setFromDate(CommonUtil.nullTrim(request.getParameter("fromDate")) + " 00:00:00");
		legacyTestVO.setToDate(CommonUtil.nullTrim(request.getParameter("toDate")) + " 00:00:00");
		legacyTestVO.setTitle(CommonUtil.nullTrim(request.getParameter("title")));
		legacyTestVO.setObjective(CommonUtil.nullTrim(request.getParameter("objective")));
		legacyTestVO.setAmount(Integer.parseInt(CommonUtil.nullTrim(request.getParameter("amount"))));
 	
		result = LegacyTestService.insertLegacyTest(legacyTestVO);
		
		
		if(result>0) {
			//1.연계를 위한 파일 생성, 결재연계서비스 호출
			String[] approvers = new String[10];
			String[] arts = new String[10];

			for (int nLoop = 0; nLoop < 10; ++nLoop) {
				approvers[nLoop] = CommonUtil.nullTrim(request.getParameter("approver" + (nLoop+1)));
				arts[nLoop] = CommonUtil.nullTrim(request.getParameter("art" + (nLoop+1)));
			}
			result = insertDoc(legacyTestVO, approvers, arts);

		}
		mav.addObject("result", result+"" );

		return mav;
    }

    
   // 연계삭제
    @RequestMapping("/app/LegacyTest/LegacyTestDel.do")
    public ModelAndView delLegacyTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
		LegacyTestVO legacyTestVO = new LegacyTestVO();
		
		ModelAndView mav = new ModelAndView("LegacyTestController.processOK");
		
		int result = 0;
		String TripCd = CommonUtil.nullTrim(request.getParameter("tripCd"));
		if(!"".equals(TripCd)) {
			legacyTestVO.setTripCd(TripCd);
			result = LegacyTestService.deleteLegacyTest(legacyTestVO);
		}	
		 
		mav.addObject("result", result+"" );

		return mav;
    }
    
        
    
 
	// 연계작성
    @RequestMapping("/app/LegacyTest/LegacyTestWrite.do")
    public ModelAndView reWrite(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("LegacyTestController.listWrite");
    	HttpSession session = request.getSession();
		List<UserVO> userVOs = orgservice.selectUserListByOrgId((String)session.getAttribute("COMP_ID"), (String)session.getAttribute("DEPT_ID"), 0);
		mav.addObject("userVOs", userVOs);
		return mav;
		
    }
    
     
    //연계 수정화면
    @RequestMapping("/app/LegacyTest/LegacyTestModify.do")
    public ModelAndView Modify(HttpServletRequest request, HttpServletResponse response) throws Exception {
     	
		LegacyTestVO legacyTestVO = new LegacyTestVO();
		String tripCd = request.getParameter("tripCd");

		legacyTestVO.setTripCd(tripCd);
		legacyTestVO =  LegacyTestService.viewLegacyTest(legacyTestVO);
 
		ModelAndView mav = new ModelAndView("LegacyTestController.viewModify");
 		mav.addObject("LegacyTestVO",legacyTestVO);
		
		return mav;
 
    }

    //결재연계 송신호출
    public int insertDoc( LegacyTestVO legacyTestVO, String[] approvers, String[] arts ) throws Exception {
		
		// 기간시스템으로 테스트 하기위하며 만든것.
		// 실제로는 기간시스템의 변수들로 정보를 채워야 하지만
		// 테스트를 위하여 임의 생성하였음
		String currentDate = DateUtil.getCurrentDate();
		try
		{
			
		    //기간시스템 연계기안을 위한 환경
			String uploadBizTemp = AppConfig.getProperty("path", "", "legacy/TR01/receive");
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
			UserVO drafter = orgservice.selectUserByLoginId(legacyTestVO.getUserId());

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
			
			String filePath = uploadBizTemp + "/" + receiverVO.getOrgCode() + "/" + legacyTestVO.getTripCd();
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
			strBuf.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
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
			strBuf.append("          <td>" + legacyTestVO.getObjective() + "</td>\r\n");
			strBuf.append("        </tr>\r\n");
			strBuf.append("        <tr>\r\n");
			strBuf.append("          <td align=\"center\">기 간</td>\r\n");
			strBuf.append("          <td align=\"center\">" + legacyTestVO.getFromDate() + " ~ " + legacyTestVO.getToDate() + "</td>\r\n");
			strBuf.append("        </tr>\r\n");
			strBuf.append("        <tr>\r\n");
			strBuf.append("          <td align=\"center\">비 용</td>\r\n");
			strBuf.append("          <td align=\"right\">" + legacyTestVO.getAmount() + "원</td>\r\n");
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
			    return 0;
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
			    return 0;
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
			headerVO.setSystemCode("HR"); //임의지정
			headerVO.setBusinessCode("TR01"); //임의 지정
			headerVO.setDocType("submit"); //기안요청/검토요청 구분하여 필수 입력
			headerVO.setSendServerId("STEST01"); // 발신 서버 임의로 지정함
			headerVO.setReceiveServerId("RTEST01"); // 수신 서버 임의로 지정함
		    headerVO.setOriginDocId(legacyTestVO.getTripCd()); // 출장신청 ID를 DOCID 로 사용
		    headerVO.setTitle(legacyTestVO.getTitle());
		    headerVO.setDocNum("");  //문서분류 있을경우 명기
		    headerVO.setPublication("1");
		    headerVO.setDraftDate(currentDate);
			legacyVO.setHeader(headerVO);
			
			ReserveVO reserveVO = new ReserveVO();
			legacyVO.setReserve(reserveVO);		
			//결재연계정보파일 - 스케줄러 테스트용
			file = new File(filePath + "/" + legacyTestVO.getTripCd() + ".xml"); 
			fileURL = file.toURL();
			jContext = JAXBContext.newInstance(LegacyVO.class);
			m = jContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(legacyVO, file);
			
			//6. EOF 파일(정보 전송 종료를 명시함)
			file = new File(filePath + "/" + legacyTestVO.getTripCd() + ".eof");// EOF 파일
			try { // 테스트를 위해 전송대신 임의 생성함
				FileWriter fw = new FileWriter(file);
				fw.close();
			}catch(Exception e){
			    e.printStackTrace();
			    return 0;
		    }		
			
				
			// --------------------- 데이타 생성 ------------------ END ------------//	
			
			
			
			// --------------------- 결재연계 정의 ---------------------------------//	
			// 결재연계시 파일을 통한 스케줄러 연동은 웹서비스를 호출하지 않으면 됨. 
			
			//String legacyTestMethod ="FILE";
			String legacyTestMethod ="WEBSERVICE";
			
  			if("WEBSERVICE".equals(legacyTestMethod) ) {
			// --------------------- 웹서비스 연계방식 ------------------ start ------------//	
			
				// 결재연계 웹서비스
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(ILegacyService.class);
				factory.setAddress("http://70.7.32.238:8080/ep/app/legacy/service/LegacyService");
				factory.setBindingId(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_MTOM_BINDING);
			
			    // 기안요청 서비스호출  - 웹서비스 테스트용
			    ILegacyService legacyService = (ILegacyService) factory.create();	
				LegacyVO legacyResVO = legacyService.insertAppDoc(legacyVO);
				strReturn = legacyResVO.getResult().getMessageCode();
 				logger.printVO(legacyResVO);

			// --------------------- 웹서비스 연계방식 ------------------ end ------------//	
			}

			return Integer.parseInt(strReturn);   // 성공시 1, 실패면 0 
			
		}catch(Exception e){
		    e.printStackTrace();
		    return 0;
		}
 
   }
    
    
    
    
    
 
    //결재연계 수신호출
    @RequestMapping("/app/LegacyTest/LegacyUpdateAckTest.do")
    public ModelAndView getBizAck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 기간시스템으로 테스트 하기위하며 만든것.
		// 실제로는 기간시스템의 변수들로 정보를 채워야 하지만
		// 테스트를 위하여 임의 생성하였음
    	// 스케줄러 등에 의해 수신폴더의 수신파일 정보를 체크하여 파일이 존재하면 진행함
    	// 테스트를 위해 스케줄러를 사용하지 않고 호출시 수동으로 정보를 체크함

    	//기간시스템 연계기안을 위한 환경
		String uploadBizTemp = AppConfig.getProperty("path", "", "TR01.SEND");
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

		ModelAndView mav = new ModelAndView("LegacyTestController.processOK");
		mav.addObject("result", strReturn );
		mav.addObject("count", docCount+"" );
		return mav;   // 성공시 1, 실패면 0 
}
    
   // 연계 Ack 적용
    public int updateAck(String systemCode, String businessCode, String businessDocId, String strFlg) throws Exception {
    	//systemCode = "HR", businessCode = "TR01" 일 경우를 가정하여 테스트 업데이트를 진행함
		LegacyTestVO legacyTestVO = new LegacyTestVO();
		
		int result = 0;
		String TripCd = CommonUtil.nullTrim(businessDocId);
		if(!"".equals(TripCd)) {
			legacyTestVO.setTripCd(TripCd);
			legacyTestVO.setFlag(strFlg);
			result = LegacyTestService.updateLegacyAckTest(legacyTestVO);
		}	

		return result;
    }
    
            
    
    
    
    
    
    
}