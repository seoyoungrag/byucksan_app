package com.sds.acube.app.list.service;

import java.util.HashMap;
import java.util.List;

import org.anyframe.pagination.Page;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.CategoryVO;
import com.sds.acube.app.list.vo.SearchVO;

/** 
 *  Class Name  : IListRegistService.java <br>
 *  Description : 문서등록대장, 문서배부대장, 미등록문서대장, 서명인날인대장, 직인날인대장, 일일감사대장, 일상감사일지, 감사직인날인대장 리스트 인터페이스  <br>
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

public interface IListRegistService {
    
       
    /**
     * 
     * <pre> 
     * 카테고리 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<CategoryVO> listCategory(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서등록대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDocRegist(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서등록대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDocRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서등록대장 목록을 조회하는 서비스(목록만)
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AppDocVO> listDocRegist(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서등록대장 목록을 조회하는 서비스(count)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int listDocRegistCount(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서배부대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDistributionRegist(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서배부대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDistributionRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  하위번호채번을 위한  목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listRowRankDocRegist(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  관련문서 등록을 위한 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listRelationDocRegist(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  미등록대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listNoDocRegist(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  미등록대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listNoDocRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  서명인날인대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listStampSealRegist(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  서명인날인대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listStampSealRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  직인날인대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listSealRegist(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  직인날인대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listSealRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  일일감사대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDailyAuditRegist(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  일일감사대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDailyAuditRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  일상감사일지 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDailyAudit(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  일상감사일지 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDailyAudit(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  감사직인날인대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listAuditSealRegist(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  감사직인날인대장 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listAuditSealRegist(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  감사직인날인대장을 등록하기 위한 상세검색 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listAuditSealRegistDetailSearch(SearchVO searchVO, int pageIndex) throws Exception;
    
   /**
    * 
    * <pre> 
    *  년도/회기 정보를 가져오는 서비스
    * </pre>
    * @param compId
    * @param searchCurRange
    * @param startDate
    * @param endDate
    * @return
    * @throws Exception
    * @see  
    *
    */
    public HashMap<String, Object> returnRegistDate(String compId, String searchCurRange, String startDate, String endDate) throws Exception;
}
