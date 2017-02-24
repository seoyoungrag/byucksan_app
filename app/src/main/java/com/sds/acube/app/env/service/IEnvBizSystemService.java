/**
 * 
 */
package com.sds.acube.app.env.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.sds.acube.app.env.vo.BizSystemVO;

/** 
 * 
 *  Class Name  : IEnvBizSystemService.java <br>
 *  Description : 업무시스템관리 서비스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 5. 13. <br>
 *  수 정  자 : chamchi  <br>
 *  수정내용 :  <br>
 * 
 *  @author  chamchi 
 *  @since 2011. 5. 13.
 *  @version 1.0 
 *  @see  com.sds.acube.app.env.service.IEnvBizSystemService.java
 */

@WebService
public interface IEnvBizSystemService {
   
    /**
     * 
     * <pre> 
     *  연계시스템 목록조회
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List listEvnBizSystem(BizSystemVO bizSystemVO) throws Exception;

    
    /**
     * 
     * <pre> 
     *  연계시스템 조회
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public BizSystemVO selectEvnBizSystem(Map inputMap) throws Exception;
    
    
    
    /**
     * 
     * <pre> 
     *  연계시스템 조회
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public BizSystemVO selectEvnBizSystem(BizSystemVO bizSystemVO) throws Exception;

    /**
     * 
     * <pre> 
     * 연계시스템 등록
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public void insertEvnBizSystem(Map inputMap) throws Exception;
    
    /**
     * 
     * <pre> 
     *  연계시스템 수정
     * </pre>
     * @return
     * @throws Exception
     * @see  
     *
     */
    public void updateEvnBizSystem(Map inputMap) throws Exception;
    

    /**
     * 
     * <pre> 
     *  연계시스템 삭제처리
     * </pre>
     * @param inputMap
     * @throws Exception
     * @see  
     *
     */
    public void deleteEvnBizSystem(Map inputMap) throws Exception;
}
