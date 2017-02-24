package com.sds.acube.app.statistics.service;

import java.util.List;

import org.anyframe.pagination.Page;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.statistics.vo.SearchVO;
import com.sds.acube.app.statistics.vo.StatisticsVO;

/** 
 *  Class Name  : IStatisticsAdminService.java <br>
 *  Description : (관리자)통계(부서별/개인별) 인터페이스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : <br>
 *  수 정  자 : <br>
 *  수정내용 : <br>
 * 
 *  @author  김경훈 
 *  @since 2011. 5. 25.
 *  @version 1.0 
 *  @see  com.sds.acube.app.list.service.IStatisticsAdminService.java
 */

public interface IStatisticsAdminService {
    
    /**
     * 
     * <pre> 
     *  역할별 통계(부서별/개인별)를 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<StatisticsVO> approvalRoleStatistics(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  역할별 통계(부서별/개인별)를 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<StatisticsVO> docKindStatistics(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  전자결재 건수를 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<StatisticsVO> approvalCntStatistics(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  발송문서 건수를 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<StatisticsVO> sendCntStatistics(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  접수문서 건수를 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<StatisticsVO> receiveCntStatistics(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  대장문서 건수를 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<StatisticsVO> registCntStatistics(SearchVO searchVO) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  부서별 접수대기문서 현황을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<StatisticsVO> receiptStatusStatistics(SearchVO searchVO) throws Exception;
    
    public Page receiptStatusStatistics(SearchVO searchVO, int cPage) throws Exception;
    
    public Page receiptStatusStatistics(SearchVO searchVO, int cPage, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  부서별 접수대기문서 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<EnfDocVO> listReceiptStatus(SearchVO searchVO) throws Exception;
    
    public Page listReceiptStatus(SearchVO searchVO, int cPage) throws Exception;
    
    public Page listReceiptStatus(SearchVO searchVO, int cPage, int pageSize) throws Exception;

    /**
     * 
     * <pre> 
     *  개인별 문서미처리 현황을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<StatisticsVO> processStatusStatistics(SearchVO searchVO) throws Exception;     

    public Page processStatusStatistics(SearchVO searchVO, int cPage) throws Exception;  

    public Page processStatusStatistics(SearchVO searchVO, int cPage, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  개인별 문서미처리 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<AppDocVO> listProcessStatus(SearchVO searchVO) throws Exception;
    
    public Page listProcessStatus(SearchVO searchVO, int cPage) throws Exception;
    
    public Page listProcessStatus(SearchVO searchVO, int cPage, int pageSize) throws Exception;

    /**
     * 
     * <pre> 
     *  부서별 발송대기문서 현황을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<StatisticsVO> sendStatusStatistics(SearchVO searchVO) throws Exception;
    
    public Page sendStatusStatistics(SearchVO searchVO, int cPage) throws Exception;
    
    public Page sendStatusStatistics(SearchVO searchVO, int cPage, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  부서별 발송대기문서 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<EnfDocVO> listSendStatus(SearchVO searchVO) throws Exception;
    
    public Page listSendStatus(SearchVO searchVO, int cPage) throws Exception;
    
    public Page listSendStatus(SearchVO searchVO, int cPage, int pageSize) throws Exception;
}