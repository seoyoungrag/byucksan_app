package com.sds.acube.app.list.service;

import java.util.List;

import org.anyframe.pagination.Page;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.list.vo.SearchVO;

/** 
 *  Class Name  : IListSendService.java <br>
 *  Description : 발송대기함, 발송심사함 리스트 인터페이스  <br>
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

public interface IListSendService {
    
    /**
     * 
     * <pre> 
     *  발송대기함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listSendWait(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  발송대기함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listSendWait(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  발송대기함 목록을 조회하는 서비스(포틀릿).
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AppDocVO> listSendWait(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  발송대기함 건수를 조회하는 서비스
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int listSendWaitCount(SearchVO searchVO) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  발솜심사함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listSendJudge(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  발솜심사함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listSendJudge(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  발솜심사함 건수를 조회하는 서비스
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int listSendJudgeCount(SearchVO searchVO) throws Exception;
}
