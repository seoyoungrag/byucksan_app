/**
 * 
 */
package com.sds.acube.app.etc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.approval.vo.AppOptionVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.etc.service.IEtcService;
import com.sds.acube.app.etc.vo.PostReaderVO;
import com.sds.acube.app.etc.vo.PubPostVO;

/**
 * Class Name  : EtcService.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 5. 18. <br> 수 정  자 : Timothy  <br> 수정내용 :  <br>
 * @author   Timothy 
 * @since  2011. 5. 18.
 * @version  1.0 
 * @see  com.sds.acube.app.etc.service.impl.EtcService.java
 */

@Service("etcService")
@SuppressWarnings("serial")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class EtcService extends BaseService implements IEtcService {

    /**
	 */
    @Inject
    @Named("orgService")
    private IOrgService orgService;
    
    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
    
    /**
     * <pre> 
     * 공람게시 추가 
     * </pre>
     * @param publicPostVO - 공람게시정보 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public int insertPublicPost(PubPostVO publicPostVO) throws Exception {
	return commonDAO.modify("etc.insertPublicPost", publicPostVO);
    }

    /**
     * <pre> 
     * 공람게시 삭제
     * </pre>
     * @param publicPostVO - 공람게시정보 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public int deletePublicPost(PubPostVO publicPostVO) throws Exception {
	return commonDAO.modify("etc.deletePublicPost", publicPostVO);
    }
    
    /**
     * <pre> 
     * 공람게시 열람자 삭제 
     * </pre>
     * @param publicPostVO - 공람게시정보 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public int deletePostReader(PubPostVO publicPostVO) throws Exception {
	return commonDAO.modify("etc.deletePostReader", publicPostVO);
    }

    
    /**
     * <pre> 
     * 공람게시 삭제 
     * </pre>
     * @param postIds - 공람게시ID정보 
     * @param compId - 회사ID 
     * @param currentDate - 현재날짜 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public int closePublicPost(String[] publishIds, String compId, String currentDate) throws Exception {
	
	int result = 0;
	int postCount = publishIds.length;
	for (int loop = 0; loop < postCount; loop++) {
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("publishId", publishIds[loop]);
	    map.put("compId", compId);
	    map.put("publishEndDate", currentDate);
	    result += commonDAO.deleteMap("etc.closePublicPost", map);
	}
	
	return result;
    }


    /**
     * <pre> 
     * 공람게시 조회자 추가 
     * </pre>
     * @param postReaderVO - 공람게시 조회자정보 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public int insertPostReader(PostReaderVO postReaderVO) throws Exception {

	int result = 0;
	PostReaderVO readerVO = (PostReaderVO) commonDAO.get("etc.selectPostReader", postReaderVO);
	if (readerVO == null) {
	    commonDAO.insert("etc.insertPostReader", postReaderVO);
	    result = updatePublicPost(postReaderVO.getPublishId(), postReaderVO.getCompId());
	}
	
	return result;
    }


    /**
     * <pre> 
     * 공람게시 조회자 추가 
     * </pre>
     * @param List - 공람게시 조회자정보 리스트 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    @SuppressWarnings("unchecked")
    public int insertPostReader(List postReaderVOs) throws Exception {

	int result = 0;
	int readerCount = postReaderVOs.size();
	for (int loop = 0; loop < readerCount; loop++) {
	    PostReaderVO postReaderVO = (PostReaderVO) postReaderVOs.get(loop);
	    String publishId = postReaderVO.getPublishId();
	    String compId = postReaderVO.getCompId();
	    PostReaderVO readerVO = (PostReaderVO) commonDAO.get("etc.selectPostReader", postReaderVO);
	    if (readerVO == null) {
		commonDAO.insert("etc.insertPostReader", postReaderVO);
		result += updatePublicPost(publishId, compId);
	    }
	}
	
	return result;
    } 
    
    
    /**
     * <pre> 
     * 공람게시 조회수 수정 
     * </pre>
     * @param publishId - 공람게시ID 
     * @param compId - 회사ID 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public PubPostVO selectPublicPost(String compId, String docId) throws Exception {

	Map<String, String> map = new HashMap<String, String>();
	map.put("docId", docId);
	map.put("compId", compId);
	return (PubPostVO) commonDAO.getMap("etc.selectPublicPost", map);
    }
    
    
    /**
     * <pre> 
     * 공람게시 조회
     * </pre>
     * @param publishId - 공람게시ID 
     * @param compId - 회사ID 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    private int updatePublicPost(String publishId, String compId) throws Exception {

	Map<String, String> map = new HashMap<String, String>();
	map.put("publishId", publishId);
	map.put("compId", compId);
	return commonDAO.modifyMap("etc.updatePublicPost", map);
    }
    
    
    /**
     * <pre> 
     * 산은캐피탈 FSP 연계 
     * </pre>
     * @param userId - 기안자ID 
     * @param appId - 결재자ID 
     * @param appOptionVO - 문서부가정보 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public boolean sendFSP(String userId, String appId, AppOptionVO appOptionVO, String currentDate) throws Exception {
	boolean result = false;
	try {
	    UserVO drafter = orgService.selectUserByUserId(userId);
	    UserVO approver = orgService.selectUserByUserId(appId);
	    String requestTime = appOptionVO.getRequestTime();
	    
	     String callURL = "";
	    //callURL = AppConfig.getProperty("fspUrl", "", ""); //FSP 연계 시 URL 설정
	
	    NameValuePair nameValuePairs[] = new NameValuePair[8];
	    nameValuePairs[0] = new NameValuePair("StartDate", DateUtil.getFormattedDate(currentDate, "yyyy-MM-dd"));
	    nameValuePairs[1] = new NameValuePair("StartTime", DateUtil.getFormattedDate(currentDate, "HH:mm:ss"));
	    String drafterId = drafter.getUserID();
	    if ("C".equals(drafterId.substring(0, 1).toUpperCase())) {
		drafterId = drafterId.substring(1);
	    }
	    nameValuePairs[2] = new NameValuePair("UserId", drafterId);
//	    nameValuePairs[2] = new NameValuePair("UserId", "2007089");
	    nameValuePairs[3] = new NameValuePair("EndDate", DateUtil.getPreNextDate(currentDate, 0, 0, 0, Integer.parseInt(requestTime.substring(3, 4)), Integer.parseInt(requestTime.substring(4, 6)), 0, "yyyy-MM-dd"));
	    nameValuePairs[4] = new NameValuePair("EndTime", DateUtil.getPreNextDate(currentDate, 0, 0, 0, Integer.parseInt(requestTime.substring(3, 4)), Integer.parseInt(requestTime.substring(4, 6)), 0, "HH:mm:ss"));
	    nameValuePairs[5] = new NameValuePair("ReqComment", appOptionVO.getRequestReason());
	    String approverId = approver.getUserID();
	    if ("C".equals(approverId.substring(0, 1).toUpperCase())) {
		approverId = approverId.substring(1);
	    }
	    nameValuePairs[6] = new NameValuePair("AppId", approverId);
//	    nameValuePairs[6] = new NameValuePair("AppId", "1990135");
	    nameValuePairs[7] = new NameValuePair("AppComment", " ");
	    
	    HttpClient httpClient = new HttpClient();
//	    httpClient.setConnectionTimeout(5000);
	    httpClient.getParams().setParameter("http.connection.timeout", 5000);
	    PostMethod postMethod = new PostMethod(callURL);
	    postMethod.setRequestBody(nameValuePairs);
	    int responseCode = httpClient.executeMethod(postMethod);
	    if (responseCode == 200) {
		String responseMsg = postMethod.getResponseBodyAsString();
		responseMsg = responseMsg.trim();
		if (responseMsg.startsWith("SUCCESS")) {
		    logger.debug("# Call FSP : " + responseMsg);
		    result = true;
		} else {
		    logger.error("# Call FSP : " + responseMsg);
		}
	    } else {
		logger.error("responseCode : " + responseCode);
	    }
	    postMethod.releaseConnection();
	    

	} catch(Exception e) {
	    logger.error(e.getMessage());
	}
	return result;  
   }        
}
