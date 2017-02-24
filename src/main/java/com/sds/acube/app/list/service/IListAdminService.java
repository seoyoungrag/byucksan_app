package com.sds.acube.app.list.service;

import java.util.List;

import org.anyframe.pagination.Page;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.common.vo.BizProcVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.list.vo.SearchVO;
import com.sds.acube.app.relay.vo.RelayAckHisVO;
import com.sds.acube.app.relay.vo.RelayExceptionVO;

/** 
 *  Class Name  : IListAdminService.java <br>
 *  Description : (관리자)문서전체, 서명인날인목록, 직인날인목록, 일상감사일지, 연계결과, 문서관리연계 리스트 인터페이스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : <br>
 *  수 정  자 : <br>
 *  수정내용 : <br>
 * 
 *  @author  김경훈 
 *  @since 2011. 5. 25.
 *  @version 1.0 
 *  @see  com.sds.acube.app.list.service.IListAdminService.java
 */

public interface IListAdminService {
    
    /**
     * 
     * <pre> 
     *  문서전체 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listAdminAll(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서전체 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listAdminAll(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  서명인날인 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listAdminStampSeal(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  서명인날인 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listAdminStampSeal(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  직인날인 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listAdminSeal(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  직인날인 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listAdminSeal(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
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
    
    public Page listAdminAudit(SearchVO searchVO, int pageIndex) throws Exception;
    
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
    
    public Page listAdminAudit(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  연계결과 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listBizResult(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  연계결과 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listBizResult(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  연계결과 상세목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<BizProcVO> listBizResultDoc(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  연계처리 결과 xml 보기
     * </pre>
     * @param bizProcVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public BizProcVO bizResultXmlDoc(BizProcVO bizProcVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서관리연계 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listAdminDocmgrResult(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서관리연계 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listAdminDocmgrResult(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서관리연계 상세목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<QueueToDocmgrVO> listAdminDocmgrResultDoc(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  전자결재 접속 이력을 조회하는 서비스
     * </pre>
     * @param searchVO
     * @param pageIndex
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listAdminAccHis(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  전자결재 접속 이력을 조회하는 서비스
     * </pre>
     * @param searchVO
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     * @see  
     *
     */
    public Page listAdminAccHis(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
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
    
    public Page listAdminReceiveWait(SearchVO searchVO, int pageIndex) throws Exception;
    
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
    
    public Page listAdminReceiveWait(SearchVO searchVO, int pageIndex, int pageSize) throws Exception; 
    
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
    
    public Page listAdminDistributionWait(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;  
    
     /**
     * 
     * <pre> 
     *  문서유통 오류이력 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listAdminRelayResult(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서유통 오류이력 상세정보 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public RelayExceptionVO getAdminRelayResultDetail(RelayExceptionVO relayExceptionVO) throws Exception;
    
     /**
     * 
     * <pre> 
     *  문서유통 응답이력 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listAdminRelayAckResult(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서유통 응답이력 상세정보 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<RelayAckHisVO> getAdminRelayAckResultDetail(RelayAckHisVO relayAckHisVO) throws Exception;
    
     /**
     * 
     * <pre> 
     *  문서유통 오류이력 첨부파일 가져오기
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    public  List<FileVO> getRelayAttachFile(RelayExceptionVO relayExceptionVO) throws Exception;
    
     /**
     * 
     * <pre> 
     *  문서유통 Ack Xml 보기
     * </pre>
     * @param relayAckHisVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public RelayAckHisVO relayResultXmlDoc(RelayAckHisVO relayAckHisVO) throws Exception;
}
