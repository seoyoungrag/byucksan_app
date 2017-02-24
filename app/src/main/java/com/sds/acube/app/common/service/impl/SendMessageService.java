/**
 * 
 */
package com.sds.acube.app.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.ISendMessageService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AtMessengerCommunicator;
import com.sds.acube.app.common.util.UtilRequest;
import com.sds.acube.app.common.vo.SendMessageVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.ws.client.alarm.MessengerProvider4WS;


/**
 * Class Name  : SendMessageService.java <br> Description : 결재 알림 Service <br> Modification Information <br> <br> 수 정  일 : 2011. 7. 6. <br> 수 정  자 : 신경훈  <br> 수정내용 :  <br>
 * @author   skh0204 
 * @since  2011. 7. 6.
 * @version  1.0 
 * @see  com.sds.acube.app.common.service.impl.SendMessageService.java
 */

@SuppressWarnings("serial")
@Service("sendMessageService")
//@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class SendMessageService extends BaseService implements ISendMessageService {
    
    @Inject
    @Named("messageSource")
    MessageSource messageSource;
        
    /**
	 */
    @Autowired
    private IEnvOptionAPIService envOptionAPIService;
    
    /**
	 */
    @Autowired
    private IOrgService orgService;
    
    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
    
    
    /**
	 */
    @Resource(name = "wsClientAlarmMsgrProviderService")
    private MessengerProvider4WS messengerProvider4WS;   

    //private String requestCharSet = UTF-8";
    
    // PointCode
    /*
    I1 = 문서처리 시 다음 결재자에게 알림
    I2 = 최종결재완료 시 기안자에게 알림
    I3 = 배부/재배부 시 해당부서 문서관리책임자에게 알림
    I4 = 재배부 요청시 문서과 문서관리책임자에게 알림
    I5 = 재지정 요청시 문서관리책임자에게 알림
    I6 = 접수문서 처리시 업무담당자에게 알림
    I7 = 공람자 지정시 공람자에게 알림
    I8 = 연계기안함에 문서 도착시 기안자에게 알림
    I9 = 접수문서 도착시 해당부서 문서관리책임자에게 알림
    I10 = 심사 요청시 날인관리책임자에게 알림
    I11 = 심사 반려시 심사 요청자에게 알림
    */
    
    /**
     * 
     * <pre> 
     *  알림 메시지를 발송한다.
     * </pre>
     * @param vo
     * @param langType
     * @throws Exception
     * @see  
     *
     */
    public boolean sendMessage(SendMessageVO vo, Locale langType) {

    	String noticeMode = appCode.getProperty("OPT317","OPT317", "OPT");	// 알림설정
    	String noticeTimePoint = appCode.getProperty("OPT332","OPT332", "OPT");	// 알림시점설정
    	boolean result = true;	
    	HashMap<String, String> mapNoticeMode;

    	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
    	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수

    	String usingType = null;

    	try {
        	String compId = (String) UtilRequest.getSessionAttrString("COMP_ID");
        	
        	if(compId == null || "".equals(compId)) {
        		compId = vo.getCompId();
        	}
    		
		    mapNoticeMode = envOptionAPIService.selectOptionTextMap(compId, noticeMode);
		    HashMap<String, String> mapNoticeTimePoint = envOptionAPIService.selectOptionTextMap(compId, noticeTimePoint);
    		
		    if ("Y".equals(mapNoticeTimePoint.get(vo.getPointCode()))) {	// 알림시점체크
    		
		    	if (langType == null) {
		    		String defaultLangType = AppConfig.getProperty("default", "", "locale");
		    		langType = new Locale(defaultLangType);
		    	}

		    	String docId = vo.getDocId();
	
	    		List<UserVO> receiverVO = orgService.selectUserListByUserIds(vo.getReceiverId());
	    		
	    		UserVO senderVO = orgService.selectUserByUserId(vo.getSenderId());
	    		String senderCompId = senderVO.getCompID();
	
	    		Map inputMap = new HashMap();
	    		inputMap.put("docId", docId);
	    		inputMap.put("compId", senderCompId);
	    		Map rsltMap = new HashMap();
	
	    		if (docId.startsWith("APP")) {
	    			usingType = dpi001;
	    			rsltMap =  (Map)commonDAO.getMap("sendMessage.selectAppDocInfo", inputMap);
	    		} else {
	    			usingType = dpi002;
	    			rsltMap= (Map)commonDAO.getMap("sendMessage.selectEnfDocInfo", inputMap);
	    		}
	
	    		String title = (String)rsltMap.get("title");
	    		String electronicYn = (String)rsltMap.get("electronDocYn");
	    		String messageTitle = "제목: "+title +". "+ messageSource.getMessage("alarm.message.title."+vo.getPointCode(), null, langType);
	    		String messageContent = "제목: "+title +". "+ messageSource.getMessage("alarm.message.content."+vo.getPointCode(), null, langType);
	    		
	    		//20170209 의견있을 때 변경
	    		if(vo.getModifiedMsg()!=null && !vo.getModifiedMsg().equals("")){
		    		messageTitle = vo.getModifiedMsg();
		    		messageContent = vo.getModifiedMsg();
	    		}
	    		
	    		String returnURL = messageSource.getMessage("alarm.returnURL."+usingType+electronicYn, null, langType);
	
    			returnURL = this.getReturnURL(senderCompId, returnURL, vo.getDocId(), vo.getPointCode());
	
			    if ("Y".equals(mapNoticeMode.get("I1"))) {	// 메신져
	    			//					this.sendMSG(vo);
			    	AtMessengerCommunicator atmc = new AtMessengerCommunicator("211.168.82.26", 1234);			
			    	
			    	for(int i=0;i<receiverVO.size();i++){														
			    		UserVO receiver = (UserVO)receiverVO.get(i);											
			    		
			    		String webUrl = AppConfig.getConfigManager().getProperty("web_url", "", "path");		 
			        	String webUri = AppConfig.getConfigManager().getProperty("web_uri", "", "path");		
			        	
			    		String url = webUrl+webUri+"/app/login/loginOnlyId.do?loginId="+receiver.getUserUID()+"&returnUrl="+returnURL.replaceAll("\\?", "*");	
			    		
			    		
			    		//겸직자라면 원사용자에게 알림 메시지를 발생시켜야 한다.
			    		if(receiver!=null && !receiver.getUserRID().equals("")){
			    			UserVO receiverTemp = orgService.selectUserByUserId(receiver.getUserRID());
			    			url = returnURL + "&usrIdForMessanger="+receiver.getUserUID();
			    			atmc.addMessage(receiverTemp.getUserID(), senderVO.getUserName(), messageTitle, url, messageContent);		
			    		}else{
			    			atmc.addMessage(receiver.getUserID(), senderVO.getUserName(), messageTitle, url, messageContent);		
			    		}
			    		
			    		
				    	atmc.send();
			    	}
			    }
			    if ("Y".equals(mapNoticeMode.get("I2"))) {	// 메일
//					this.sendMail(vo);
			    }
			    if ("Y".equals(mapNoticeMode.get("I3"))) {	// SMS
//					this.sendSMS(vo);
			    }
		    }
    	} catch (Exception e) {
    		result = false;
    		logger.debug(e.getMessage());
    	}

    	return result;
    }
    
    
    /**
     * 
     * <pre> 
     *  return url 을 반환한다. 
     * </pre>
     * @param url
     * @throws Exception
     * @see  
     *
     */
    public String getReturnURL(String compId, String url, String docId, String point) {

    	String webUrl = AppConfig.getConfigManager().getProperty("web_url", "", "path");
    	//String webUrl = AppConfig.getConfigManager().getProperty("comp"+compId, "", "webUrl");
    	String webUri = AppConfig.getConfigManager().getProperty("web_uri", "", "path");
    	String lobCode = this.getLobCode(point);
    	String returnURL = webUrl + webUri + url + "?docId=" + docId + "&lobCode=" + lobCode + "&compId=" + compId;
    	return returnURL;
    }
    
    
    /**
     * 
     * <pre> 
     *  LOB 코드를 반환한다.
     * </pre>
     * @param url
     * @throws Exception
     * @see  
     *
     */
    public String getLobCode(String point) {

    	String lobCode = "";
    	if ("I1".equals(point)) {
    		lobCode = appCode.getProperty("LOB003", "", "LOB");
    	} else if ("I2".equals(point)) {
    		lobCode = appCode.getProperty("LOB010", "", "LOB");
    	} else if ("I3".equals(point)) {
    		lobCode = appCode.getProperty("LOB008", "", "LOB");
    	} else if ("I4".equals(point)) {
    		//재배부요청문서는 배부대기함에서 조회
    		//lobCode = appCode.getProperty("LOB019", "", "LOB");
    		lobCode = appCode.getProperty("LOB007", "", "LOB");
    	} else if ("I5".equals(point)) {
    		lobCode = appCode.getProperty("LOB003", "", "LOB");
    	} else if ("I7".equals(point)) {
    		lobCode = appCode.getProperty("LOB012", "", "LOB");
    	} else if ("I8".equals(point)) {
    		lobCode = appCode.getProperty("LOB002", "", "LOB");
    	} else if ("I9".equals(point)) {
    		lobCode = appCode.getProperty("LOB008", "", "LOB");
    	} else if ("I10".equals(point)) {
    		lobCode = appCode.getProperty("LOB006", "", "LOB");
    	} else if ("I11".equals(point)) {
    		lobCode = appCode.getProperty("LOB005", "", "LOB");
    	}

    	return lobCode;
    }    
    
}
