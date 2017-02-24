package com.sds.acube.app.ws.service;

import javax.jws.WebService;

import com.sds.acube.app.mobile.vo.MobileAppActionVO;
import com.sds.acube.app.mobile.vo.MobileAppResultVO;
import com.sds.acube.app.ws.vo.AppActionVO;
import com.sds.acube.app.ws.vo.AppAttachVO;
import com.sds.acube.app.ws.vo.AppDetailVO;
import com.sds.acube.app.ws.vo.AppFileVOs;
import com.sds.acube.app.ws.vo.AppItemCountVO;
import com.sds.acube.app.ws.vo.AppListVOs;
import com.sds.acube.app.ws.vo.AppMenuVOs;
import com.sds.acube.app.ws.vo.AppReqVO;
import com.sds.acube.app.ws.vo.AppResultVO;
import com.sds.acube.app.ws.vo.HeaderVO;

/**
 * 
 *  Class Name  : IEsbAppService.java <br>
 *  Description : 전자결재 ESB 연계서비스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 5. 23. <br>
 *  수 정  자 : 윤동원  <br>
 *  수정내용 :  <br>
 * 
 *  @author  윤동원 
 *  @since 2011. 5. 23.
 *  @version 1.0 
 *  @see  com.sds.acube.app.ws.service.IEsbAppService.java
 */

@WebService
public interface IEsbAppService {

    /**
     * 
     * <pre> 
     *  결재함 리스트 조회
     * </pre>
     * @param appReqVO
     * @return AppMenuVO
     * @see  
     *
     */
    public AppMenuVOs  listBox(AppReqVO appReqVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재함 리스트 조회(모바일)
     * </pre>
     * @param appReqVO
     * @return AppMenuVO
     * @see  
     *
     */
    public AppMenuVOs  listMobileBox(AppReqVO appReqVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재리스트 조회
     * </pre>
     * @param appReqVO
     * @return
     * @see  
     *
     */
    public AppListVOs listDoc(AppReqVO appReqVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재리스트 조회(모바일 처리용)
     * </pre>
     * @param appReqVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public AppListVOs listAppDoc(AppReqVO appReqVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재문서 갯수 조회
     * </pre>
     * @param appReqVO
     * @return
     * @see  
     *
     */
    public AppItemCountVO countDoc(AppReqVO appReqVO) throws Exception ;   
    
    /**
     * 
     * <pre> 
     *  결재문서 갯수 조회(포탈)
     * </pre>
     * @param appReqVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public AppItemCountVO countPortalDoc(AppReqVO appReqVO) throws Exception ;
    
    /**
     * 
     * <pre> 
     *  결재상세내용 조회
     * </pre>
     * @param appReqVO
     * @return AppDetailVO
     * @see  
     *
     */
    public AppDetailVO selectDocInfo(AppReqVO appReqVO) throws Exception;   
    
    /**
     * 
     * <pre> 
     *  결재상세내용 조회(모바일)
     * </pre>
     * @param appReqVO
     * @return AppDetailVO
     * @see  
     *
     */
    public AppDetailVO selectDocInfoMobile(AppReqVO appReqVO) throws Exception;   
    
    /**
     * 
     * <pre> 
     *  결재상세내용 조회
     * </pre>
     * @param appReqVO
     * @return AppDetailVO
     * @see  
     *
     */
    public AppAttachVO getAttachFile(AppReqVO appReqVO) throws Exception;   

    /**
     * 
     * <pre> 
     *  결재처리 요청(전자결재 ack)
     * </pre>
     * @param appReqVO
     * @return AppDetailVO
     * @see  
     *
     */
    public AppResultVO processAppDoc(HeaderVO headerVO, AppFileVOs fileVOs) throws Exception ;   
    
    /**
     * 
     * <pre> 
     *  결재처리 요청(모바일)
     * </pre>
     * @param appReqVO
     * @return AppDetailVO
     * @see  
     *
     */
    public AppResultVO processDoc(AppActionVO appActionVO) throws Exception ;   

    /**
     * 
     * <pre> 
     *  모바일 결재 처리 웹서비스
     * </pre>
     * @param MobileAppActionVO
     * @return MobileAppResultVO
     * @see  
     *
     */
    public MobileAppResultVO processMobileApproval(MobileAppActionVO mobileAppActionVO) throws Exception ;   
}
