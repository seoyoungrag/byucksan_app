/**
 * 
 */
package com.sds.acube.app.list.service;

import java.util.List;

import org.anyframe.pagination.Page;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.list.vo.SearchVO;

/** 
 *  Class Name  : IListMigService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2016. 1. 23. <br>
 *  수 정  자 : 서영락  <br>
 *  수정내용 :  <br>
 * 
 *  @author  서영락 
 *  @since 2016. 1. 23.
 *  @version 1.0 
 *  @see  com.sds.acube.app.list.service.IListMigService.java
 */

public interface IListMigService {

    
    /**
     * 
     * <pre> 
     *  구문서등록대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listMigDocRegist(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  구문서등록대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listMigDocRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  구문서등록대장 목록을 조회하는 서비스(목록만)
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AppDocVO> listMigDocRegist(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  구문서등록대장 목록을 조회하는 서비스(count)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int listMigDocRegistCount(SearchVO searchVO) throws Exception;
    
    /**
     * 
     *
     */

    /**
     * 
     * <pre> 
     *  구문서등록대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listMigDocDist(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  구문서등록대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listMigDocDist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  구문서등록대장 목록을 조회하는 서비스(목록만)
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AppDocVO> listMigDocDist(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  구문서등록대장 목록을 조회하는 서비스(count)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int listMigDocDistCount(SearchVO searchVO) throws Exception;

}
