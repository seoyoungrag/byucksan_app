package com.sds.acube.app.list.service;

import org.anyframe.pagination.Page;

import com.sds.acube.app.list.vo.SearchVO;

/** 
 *  Class Name  : IListDraftService.java <br>
 *  Description : 임시저장함, 연계기안함 리스트 인터페이스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : <br>
 *  수 정  자 : <br>
 *  수정내용 : <br>
 * 
 *  @author  김경훈 
 *  @since 2011. 3. 25.
 *  @version 1.0 
 *  @see  com.sds.acube.app.list.service.IListDraftService.java
 */

public interface IListDraftService {
    
    /**
     * 
     * <pre> 
     *  임시저장함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listTempApproval(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  임시저장함 목록을 조회하는 서비스
     * </pre>
     * @param searchVO
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     * @see  
     *
     */
    public Page listTempApproval(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  연계기안함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listBizDraft(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  연계기안함 목록을 조회하는 서비스
     * </pre>
     * @param searchVO
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     * @see  
     *
     */
    public Page listBizDraft(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
}
