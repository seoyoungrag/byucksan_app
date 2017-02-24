package com.sds.acube.app.ws.client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.xml.ws.Holder;



//import org.apache.axis.utils.tcpmon;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.sds.acube.app.ws.service.ILegacyService;
import com.sds.acube.app.ws.vo.AppFileVO;
import com.sds.acube.app.ws.vo.AppFileVOs;
import com.sds.acube.app.ws.vo.ApproverVO;
import com.sds.acube.app.ws.vo.HeaderVO;
import com.sds.acube.app.ws.vo.LegacyVO;
import com.sds.acube.app.ws.vo.ReceiverVO;
import com.sds.acube.app.ws.vo.ResultVO;
import com.sds.acube.app.ws.vo.SenderVO;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.LogWrapper;

import java.util.Properties;


/**
 * Class Name : LegacyClientWs <br>
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

public class LegacyClientWs {

    public ILegacyService legacyService;
    private String uploadTemp = "";//AppConfig.getProperty("upload_temp", "", "attach");
    private String uploadUrl = "";
    private LegacyVO legacyResVO;
    private Properties props;
    

    public LegacyClientWs(SenderVO senderVO,
            ReceiverVO receiverVO,
            HeaderVO headerVO,
            String retUrl,
            String uploadDir,
            String[][] attaches) {
    	setUploadPath();
		//String webUri = headerVO.getWebUri();
    	//String webUri = "http://172.30.127.112:8001/ep";//전자결재 url처리 개발
    	//String webUri = "http://127.0.0.1:8090/ep"; // 로컬 전자결재
    	String webUri = uploadUrl;//"http://172.18.1.14:8002/ep";//전자결재 url처리 운영
    	uploadTemp = uploadDir;
		
		JaxWsProxyFactoryBean factory = null;
		
		factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ILegacyService.class);
		factory.setAddress(webUri+"/app/esb/service/LegacyService");
		factory.setBindingId(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_MTOM_BINDING);
	
		// ---------------------
		// 서비스 인터페이스
		// ---------------------
		legacyService = (ILegacyService) factory.create();
		LegacyVO legacyVO = new LegacyVO();
		try {

		        insertDoc(senderVO,receiverVO,headerVO,retUrl,attaches);//retUrl 추가
		        
		} catch (Exception e) {
	
		}
    }
    
    public LegacyVO insertDoc(SenderVO senderVO,
    		              ReceiverVO receiverVO,
    		              HeaderVO headerVO,
    		              String retUrl,
    		              String[][] attaches) throws Exception {

    	LegacyVO legacyResVO = new LegacyVO();
		try
		{
			LegacyVO legacyVO = new LegacyVO();
			String bizKey = "";
	        String businessCode = headerVO.getBusinessCode();
			legacyVO.setSender(senderVO);
			legacyVO.setReceiver(receiverVO);
			
			headerVO.setSystemCode("APP");
			headerVO.setDocType("submit");//기안요청/검토요청 구분하여 필수 입력
			headerVO.setSendServerId(businessCode); // 발신 서버 임의로 지정함
			headerVO.setReceiveServerId(businessCode); // 수신 서버 임의로 지정함
		    headerVO.setDocNum("");  //문서분류 있을경우 명기
		    headerVO.setPublication("1");
		    headerVO.setDraftDate("2015-03-23");
			legacyVO.setHeader(headerVO);
			String origindocId = headerVO.getOriginDocId();
			if(origindocId.equals("") || origindocId == null){
				bizKey = GuidUtil.getGUID(); 
				headerVO.setOriginDocId(bizKey);
			}
			
			ResultVO resultVO = new ResultVO();
			resultVO.setMessageCode(businessCode+"0101");
			resultVO.setRetUrl(retUrl);// return url 추가
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
			/*file = new File(uploadTemp + "/appinfo.xml");// 테스트파일
			fileURL = file.toURL();
		
		
			handler = new DataHandler(fileURL);
		
			holder = new Holder<DataHandler>();
			holder.value = handler;
		
			fileVO.setFileName("appinfo.xml");
			fileVO.setContent(handler);
			fileVO.setFileType("appinfo");
		
			files.add(fileVO);*/
			//결재정보 XML Body 
			//file = new File(uploadTemp + "/test.xml");// 테스트파일
			//fileURL = file.toURL();
			//handler = new DataHandler(fileURL);
			//holder = new Holder<DataHandler>();
			//holder.value = handler;
			//fileVO.setFileName("test.xml");
			//fileVO.setContent(handler);
			//fileVO.setFileType("body");
			//legacyVO.getFiles().add(fileVO);
			
			ApproverVO approverVO = new ApproverVO();
			approverVO.setSerialOrder("1");
			approverVO.setParallelOrder("1");
			approverVO.setUserName(receiverVO.getUserName());
			approverVO.setUserId(receiverVO.getUserId());
			approverVO.setPosition("");
			approverVO.setDeptName(receiverVO.getDeptName());
			approverVO.setDeptCode(receiverVO.getOrgCode());
			approverVO.setAskType("ART010");
			approverVO.setActType("");
			approverVO.setActDate("");
			approverVO.setOpinion("");
		    legacyVO.getApprovers().add(approverVO);
		    
			// 연계 상위폴더 생성 start
			String filePath = "";//uploadTemp + "/" + receiverVO.getOrgCode() + "/" + bizKey;
			/*file = new File(filePath);
			if(!file.exists()) {
				file.mkdirs();
			}*/
			// 연계 상위폴더 생성 end
			
			//본문
			if(headerVO.getHashMapBodyFileInfo().get("body") != null){
				
				filePath = headerVO.getHashMapBodyFileInfo().get("body").toString();
				fileURL = saveBodyFile(filePath);
				handler = new DataHandler(fileURL);
				holder = new Holder<DataHandler>();
				holder.value = handler;
				fileVO = new AppFileVO();
				String fileName = "";
				String[] arrFilePath = filePath.split("/");
				for(int i = 0; i < arrFilePath.length ; i++){
					if(i == arrFilePath.length -1){
						fileName = arrFilePath[i];
					}
				}
				fileVO.setFileName(fileName);
				fileVO.setContent(handler);
				fileVO.setFileType("body");
				legacyVO.getFiles().add(fileVO);
				//files.add(fileVO);
			}
			
			// 참조본문자료
			if(headerVO.getHashMapBodyFileInfo().get("refBody") != null){
				
				filePath = headerVO.getHashMapBodyFileInfo().get("refBody").toString();
				fileURL = saveBodyFile(filePath);
				handler = new DataHandler(fileURL);
				holder = new Holder<DataHandler>();
				holder.value = handler;
				fileVO = new AppFileVO();
				String refFileName = "";
				String[] arrRefFilePath = filePath.split("/");
				for(int i = 0; i < arrRefFilePath.length ; i++){
					if(i == arrRefFilePath.length -1){
						refFileName = arrRefFilePath[i];
					}
				}
				fileVO.setFileName(refFileName);
				fileVO.setContent(handler);
				fileVO.setFileType("refBody");
				legacyVO.getFiles().add(fileVO);
				
			}
			
			// 첨부파일
			if(attaches != null){
			if(attaches.length > 0){
				for(int n = 0; n<attaches.length ; n++){
						String[] arrAttFileInfo = attaches[n];
						filePath = arrAttFileInfo[1];
						fileURL = saveBodyFile(filePath);// 파일 path
						handler = new DataHandler(fileURL);
						holder = new Holder<DataHandler>();
						holder.value = handler;
						fileVO = new AppFileVO();
						String attFileName = "";
						int nOrder = n;//파일순서 추가
						String[] arrAttFilePath = filePath.split("/");
						for(int m = 0; m < arrAttFilePath.length ; m++){
							if(m == arrAttFilePath.length -1){
								attFileName = arrAttFilePath[m];
							}
						}
						if(!arrAttFileInfo[0].equals("") || arrAttFileInfo[0] != null){
							attFileName = arrAttFileInfo[0];
						}
						if( arrAttFileInfo.length > 2 ){
							 nOrder = Integer.parseInt(arrAttFileInfo[2]);
						}
						fileVO.setFileName(attFileName);//파일명
						fileVO.setContent(handler);
						fileVO.setFileType("attach");
						fileVO.setFileOrder(nOrder);//파일 순서 추가 2015.09.01
						legacyVO.getFiles().add(fileVO);
					}
				}
			}
			// 기안요청 서비스호출
			legacyResVO = legacyService.insertAppDoc(legacyVO);
		
			setLegacyResVO(legacyResVO);
		
			
			}catch(Exception e){
			    e.printStackTrace();
			    legacyResVO.getResult().setMessageCode("0");
			    return legacyResVO;
			}
		
		   return legacyResVO;
    }
    
    
    /**
	 * @return the legacyResVO
	 */
	public LegacyVO getLegacyResVO() {
		return legacyResVO;
	}
	/**
	 * @param legacyResVO the legacyResVO to set
	 */
	public void setLegacyResVO(LegacyVO legacyResVO) {
		this.legacyResVO = legacyResVO;
	}

    
    public URL saveBodyFile(String filePath) throws Exception{
    	//3.본문파일
		File file = new File(filePath);// 본문html + "/body.html"
		URL fileURL = file.toURL();
		if(file.exists()){
			return fileURL;
		}
    	/*try{
    	
    		StringBuffer strBuf = new StringBuffer();
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
    		strBuf.append("          <td>해외 출장용입니다.</td>\r\n");
    		strBuf.append("        </tr>\r\n");
    		strBuf.append("        <tr>\r\n");
    		strBuf.append("          <td align=\"center\">기 간</td>\r\n");
    		strBuf.append("          <td align=\"center\">2015-03-24 ~ 2015-03-29</td>\r\n");
    		strBuf.append("        </tr>\r\n");
    		strBuf.append("        <tr>\r\n");
    		strBuf.append("          <td align=\"center\">비 용</td>\r\n");
    		strBuf.append("          <td align=\"right\">10000 원</td>\r\n");
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
    			}	
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	*/
    	return fileURL;
    }
    public URL saveAttachFile(String filePath) throws Exception{
    	   
		//4-2. 파일 정보 생성
		File file = new File(filePath + "/attach.txt");// 첨부파일 - 테스트를 위해 한개파일 정도만 사용함 
		try {  // 테스트를 위해 전송대신 임의 생성함
			FileWriter fw = new FileWriter(file);
			fw.write("결재연계 첨부 테스트용 파일");
			fw.close();
		}catch(Exception e){
		    e.printStackTrace();
	    }		
	
		URL fileURL = file.toURL();

		return fileURL;
    }
    
    private void setUploadPath(){
    	props = new Properties();
    	InputStream fis = null;

        try
        {
            //fis = Thread.currentThread().getContextClassLoader().getResourceAsStream("applegacy.properties");
            //		//getClass().getResourceAsStream("applegacy.properties");
        	//ClassLoader loader = Thread.currentThread().getContextClassLoader();
        	//fis = loader.getResourceAsStream("app/config/applegacy.properties");
        	fis = getClass().getResourceAsStream("/applegacy.properties");
            props.load(fis);
            this.uploadUrl = props.getProperty("legacy.AppUrl", "http://172.18.1.14:8002/ep");
        }
        catch (IOException e)
        {
            LogWrapper.getLogger("com.sds.acube.app").error(e.getMessage());
        }
        finally
        {
            try
            {
                if (fis != null)
                {
                    fis.close();
                }
            }
            catch (Exception e)
            {
            }
        }
    }
}
