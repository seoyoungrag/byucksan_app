package com.sds.acube.app.list.service;

import java.util.List;

import org.anyframe.pagination.Page;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.list.vo.SearchVO;

/** 
 *  Class Name  : IListSendService.java <br>
 *  Description : 배부대기함, 접수대기함 리스트 인터페이스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : <br>
 *  수 정  자 : <br>
 *  수정내용 : <br>
 * 
 *  @author  김경훈 
 *  @since 2011. 4. 12.
 *  @version 1.0 
 *  @see  com.sds.acube.app.list.service.IListSendService.java
 */

public interface IListReceiveService {
    
    /**
     * 
     * <pre> 
     *  배부대기함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDistributionWait(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  배부대기함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDistributionWait(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  배부대기함 목록을 조회하는 서비스(메인)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<EnfDocVO> listDistributionWait(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  배부대기함 목록을 조회하는 서비스(portlet)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AppDocVO> listDistributionWaitPortlet(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  배부대기함 목록을 조회하는 서비스(건수)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int listDistributionWaitCount(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  재배부요청함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDistributionRemind(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  재배부요청함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDistributionRemind(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  접수대기함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listReceiveWait(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  접수대기함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listReceiveWait(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;    
    
     /**
     * 
     * <pre> 
     *  접수대기함 목록을 조회하는 서비스(portlet)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AppDocVO> listReceiveWait(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  접수대기함 목록을 조회하는 서비스(건수)
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public int listReceiveWaitCount(SearchVO searchVO) throws Exception;    
}
