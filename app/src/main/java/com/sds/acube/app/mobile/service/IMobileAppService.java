/**
 * 
 */
package com.sds.acube.app.mobile.service;

import java.util.List;

import javax.jws.WebService;

import com.sds.acube.app.mobile.vo.MobileAppActionVO;
import com.sds.acube.app.mobile.vo.MobileAppResultVO;
import com.sds.acube.app.mobile.vo.MobileQueueVO;
import com.sds.acube.app.mobile.ws.client.esbservice.AppActionVO;
import com.sds.acube.app.mobile.ws.client.esbservice.AppAttachVO;
import com.sds.acube.app.mobile.ws.client.esbservice.AppDetailVO;
import com.sds.acube.app.mobile.ws.client.esbservice.AppListVOs;
import com.sds.acube.app.mobile.ws.client.esbservice.AppMenuVOs;
import com.sds.acube.app.mobile.ws.client.esbservice.AppReqVO;
import com.sds.acube.app.mobile.ws.client.esbservice.AppResultVO;

/** 
 *  Class Name  : IMobileAppService.java <br>
 *  Description : 모바일 웹서비스 service 호출 테스트를 위한 Interface  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2012. 6. 18. <br>
 *  수 정  자 : jd.park  <br>
 *  수정내용 :  <br>
 * 
 *  @author  jd.park 
 *  @since 2012. 6. 18.
 *  @version 1.0 
 *  @see  com.sds.acube.app.mobile.service.IMobileAppService.java
 */

public interface IMobileAppService {
	/**
	 * 
	 * <pre> 
	 *  모바일 함별 건수
	 * </pre>
	 * @param regVo
	 * @return
	 * @see  
	 *
	 */
	AppMenuVOs getListMobileBox(AppReqVO reqVo) throws Exception;
	
	/**
	 * 모바일 함별 목록
	 * <pre> 
	 *  설명
	 * </pre>
	 * @param req
	 * @return
	 * @throws Exception
	 * @see  
	 *
	 */
	AppListVOs getListMobileAppDoc(AppReqVO req) throws Exception;

	/**
     * <pre> 
     *  모바일 결재상세내용 조회
     * </pre>
     * @param reqVo
     * @return
     * @see  
     * */ 
    
    AppDetailVO getSelectDocInfoMobile(AppReqVO req)throws Exception;
    
    /**
	 * 
	 * <pre> 
	 *  결재 처리 요청
	 * </pre>
	 * @param actionVo
	 * @return
	 * @throws Exception
	 * @see  
	 *
	 */
    AppResultVO processAppMobile(AppActionVO actionVo) throws Exception;

    /**
     * <pre> 
     *  모바일 첨부파일
     * </pre>
     * @param reqVo
     * @return
     * @see  
     * */ 
    
    AppAttachVO getAttachFile(AppReqVO reqVo)throws Exception;
	
    void setApproverInfo()throws Exception;
    
    /**
     * <pre> 
     *  모바일 큐 리스트
     * </pre>
     * @return
     * @see  
     * */ 
    List<MobileQueueVO> selectMobileQueue() throws Exception;

    // 모바일 결재 스케줄러에서 파일 타입 ('.doc', 'html', '.hwp')을 추가한 정보를 조회한다.
    List<MobileQueueVO> selectMobileQueueFileType() throws Exception;
    
    /**
     * <pre> 
     *  모바일 결재처리
     * </pre>
     * @param mobileQueueVO
     * @return
     * @see  
     * */ 
    void processMobileApp(MobileQueueVO mobileQueueVO)throws Exception;

    /**
     * <pre> 
	 * 모바일 결재처리(QUEUE 생성)
     * </pre>
     * @param mobileActionVo
     * @return
     * @see  
     * */ 
	MobileAppResultVO processMobileApproval(MobileAppActionVO mobileActionVo) throws Exception;
    
}
