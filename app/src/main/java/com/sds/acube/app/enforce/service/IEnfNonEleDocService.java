/**
 * 
 */
package com.sds.acube.app.enforce.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;


/**
 * Class Name : IEnfNonEleDocService.java <br>
 * Description : 접수용 비전자 문서 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 5. 3. <br>
 * 수 정 자 : 장진홍 <br>
 * 수정내용 : <br>
 * 
 * @author jumbohero
 * @since 2011. 5. 3.
 * @version 1.0
 * @see com.sds.acube.app.approval.service.IEnfNonEleDocService.java
 */
public interface IEnfNonEleDocService {
    /**
     * 
     * <pre> 
     *  문서 배부
     * </pre>
     * @param enfProcVO
     * @param enfRecvVO
     * @param enfDocVO
     * @param fileVO
     * @param ownDeptVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    ResultVO insertNonEleDoc(EnfDocVO enfDocVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  접수대기함에서 비전자 문서 접수처리
     *  DocId 가 있는 접수처리
     * </pre>
     * @param enfDocVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    ResultVO insertNonEleDocAcc01(EnfDocVO enfDocVO, String currentDate, String proxyDeptId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재대기함에서의 담당접수처리
     * </pre>
     * @param enfDocVO
     * @param currentDate
     * @param proxyDeptId
     * @return
     * @throws Exception
     * @see  
     *
     */
    ResultVO insertNonEleDocAcc003(EnfDocVO enfDocVO, String currentDate, String proxyDeptId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  접수대기함에서의 담당접수처리
     * </pre>
     * @param enfDocVO
     * @param currentDate
     * @return
     * @throws Exception
     * @see  
     *
     */
    ResultVO insertNonEleDocAcc011(EnfDocVO enfDocVO, String currentDate, String proxyDeptId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재라인 등록
     * </pre>
     * @param enfDocVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    ResultVO insertNonEleDocEnfLine(EnfDocVO enfDocVO, String UserId, String UserName) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서대기함에서의 비전자 문서 접수처리
     *  DocId 가 없는 접수처리
     * </pre>
     * @param enfDocVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    ResultVO insertNonEleDocAcc02(EnfDocVO enfDocVO, String currentDate, String proxyDeptId) throws Exception;
    
    /**
     * 문서대기함에서 비전자 문서 담당접수처리
     * <pre> 
     *  설명
     * </pre>
     * @param enfDocVO
     * @param currentDate
     * @param proxyDeptId
     * @return
     * @throws Exception
     * @see  
     *
     */
    ResultVO insertNonEleDocAcc022(EnfDocVO enfDocVO, String currentDate, String proxyDeptId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  배부한 문저정보 가져오기
     * </pre>
     * @param searchMap
     * @return
     * @throws Exception
     * @see  
     *
     */
    EnfDocVO selectNonEleDoc(Map<String, String> searchMap) throws Exception;
    
    /**
     * 
     * <pre> 
     *  접수문서를 변경한다.
     * </pre>
     * @param enfDocVO
     * @param docHisVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    ResultVO updateNonEleDoc(EnfDocVO enfDocVO, DocHisVO docHisVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  선람처리
     * </pre>
     * @param inputMap
     * @param pubReaderVOs
     * @return
     * @throws Exception
     * @see  
     *
     */
    ResultVO processPreRead(Map<String, String> inputMap, List<PubReaderVO> pubReaderVOs) throws Exception;
    
    /**
     * 
     * <pre> 
     *  담당처리
     * </pre>
     * @param inputMap
     * @param pubReaderVOs
     * @return
     * @throws Exception
     * @see  
     *
     */
    ResultVO processFinalApproval(Map<String, String> inputMap, List<PubReaderVO> pubReaderVOs) throws Exception;
}
