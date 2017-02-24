package com.sds.acube.app.exchange.service;

import java.util.List;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.vo.BizProcVO;
import com.sds.acube.app.ws.vo.AcknowledgeVO;
import com.sds.acube.app.ws.vo.EsbAppDocVO;

/**
 * Class Name : IExchangeService.java <br>
 * Description : 전자결재 웹서비스 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 3. 25. <br>
 * 수 정 자 : 윤동원 <br>
 * 수정내용 : <br>
 * 
 * @author 윤동원
 * @since 2011. 3. 25.
 * @version 1.0
 * @see com.sds.acube.app.ws.server.service.IEsbAppService.java
 */

public interface IExchangeService {

    /**
     * 
     * <pre> 
     *  연계기안 요청에 대한 등록을 처리하는 서비스 메소드
     * </pre>
     * @param esbappDocVO
     * @throws Exception
     * @see  
     *
     */
    public void insertAppDoc(EsbAppDocVO esbappDocVO) throws Exception;


    /**
     * 
     * <pre> 
     *  연계기안 처리에 대한 결과전송 서비스 메소드
     * </pre>
     * @param acknowledgeVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public EsbAppDocVO processAppDoc(AcknowledgeVO acknowledgeVO) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  연계기안 처리에 대한 결과전송 서비스 메소드
     * </pre>
     * @param acknowledgeVO
     * @param docType
     * @return
     * @throws Exception
     * @see  
     *
     */
    public EsbAppDocVO processAppDoc(AcknowledgeVO acknowledgeVO, String docType) throws Exception;
    

    /**
     * 
     * <pre> 
     *  연계기안 처리에 대한 반송 서비스 메소드
     * </pre>
     * @param docList
     * @return
     * @throws Exception
     * @see  
     *
     */
    public EsbAppDocVO processDocReject(List<AppDocVO> docList) throws Exception;
    
    /**
     * 
     * <pre> 
     *  업무연계이력등록
     * </pre>
     * @param bizProcVO
     * @throws Exception
     * @see  
     *
     */
    public void insertBizProc(BizProcVO bizProcVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  업무연계이력삭제
     * </pre>
     * @throws Exception
     * @see  
     *
     */
    public int removeExchangeHistory() throws Exception;
    
    /**
     * 
     * <pre> 
     *  업무연계이력 Ack 발송리스트
     * </pre>
     * @throws Exception
     * @see  
     *
     */
    public List<BizProcVO> selectLegacyBizProc(BizProcVO bizProcVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  업무연계이력 상태수정
     * </pre>
     * @throws Exception
     * @see  
     *
     */
    public void updateLegacyBizProc(BizProcVO bizProcVO) throws Exception;
    
     /**
     * 
     * <pre> 
     *  업무처리 연계횟수
     * </pre>
     * @throws Exception
     * @see  
     *
     */
    public BizProcVO selectMaxProcCount(BizProcVO bizProcVO) throws Exception;
}
