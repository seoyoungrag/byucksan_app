package com.sds.acube.app.appcom.service;

import java.util.Map;

import com.sds.acube.app.approval.vo.AppDocVO;


/**
 * 
 *  Class Name  : IDocMngInfoService.java <br>
 *  Description : 문서관러정보 서비스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 4. 28. <br>
 *  수 정  자 : chamchi  <br>
 *  수정내용 :  <br>
 * 
 *  @author  chamchi 
 *  @since 2011. 4. 28.
 *  @version 1.0 
 *  @see  com.sds.acube.app.appcom.service.IDocMngInfoService.java
 */

public interface IDocMngInfoService {


    /**
     * 
     * <pre> 
     *  문서관러정보 수정
     * </pre>
     * @param appDocVO
     * @throws Exception
     * @see  
     *
     */
    public void updateDocMngInfo(Map inputMap) throws Exception;


    /**
     * 
     * <pre> 
     *  문서관리 정보 조회
     * </pre>
     * @param inputMap
     * @return
     * @throws Exception
     * @see  
     *
     */
    public Map  selectDocMngInfo(Map inputMap) throws Exception;

}