package com.sds.acube.app.list.service;

import java.util.List;

import org.anyframe.pagination.Page;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.list.vo.SearchVO;

/** 
 *  Class Name  : IListApprovalService.java <br>
 *  Description : 결재대기함, 진행문서함 리스트 인터페이스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : <br>
 *  수 정  자 : <br>
 *  수정내용 : <br>
 * 
 *  @author  김경훈 
 *  @since 2011. 3. 31.
 *  @version 1.0 
 *  @see  com.sds.acube.app.list.service.IListApprovalService.java
 */

public interface IListApprovalService {
    
    /**
     * 
     * <pre> 
     *  결재대기함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listApprovalWait(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재대기함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listApprovalWait(SearchVO searchVO, int pageIndex, int PageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  반려함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listApprovalReject(SearchVO searchVO, int pageIndex, int PageSize) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  폐기함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listApprovalDelete(SearchVO searchVO, int pageIndex, int PageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  진행문서함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listProgressDoc(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  진행문서함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listProgressDoc(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    // 결재 경로상의 사용자 중 결재 전 사용자가 결재진행함에서 결재문서를 조회할 수 없도록 수정
    public Page listProgressDocOption383(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재대기함 목록을 조회하는 서비스(건수)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int listApprovalWaitCount(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  진행문서함 목록을 조회하는 서비스(건수)
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public int listProgressDocCount(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재대기함 목록(목록만)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AppDocVO> listApprovalWait(SearchVO searchVO) throws Exception; 
    
    
    /**
     * 
     * <pre> 
     *  진행문서함 목록(목록만)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AppDocVO> listProgressDoc(SearchVO searchVO) throws Exception; 
    
    /**
     * 
     * <pre> 
     *  협조문서함 목록을 조회하는 서비스
     * </pre>
     * @param searchVO
     * @param pageIndex
     * @return
     * @throws Exception
     * @see  
     *
     */
    public Page listAssistantDoc(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  협조문서함 목록을 조회하는 서비스
     * </pre>
     * @param searchVO
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     * @see  
     *
     */
    public Page listAssistantDoc(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    
}
