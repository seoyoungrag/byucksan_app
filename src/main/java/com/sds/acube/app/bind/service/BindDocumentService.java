/**
 * 
 */
package com.sds.acube.app.bind.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.bind.vo.BindDocVO;


/**
 * Class Name : BindDocumentService <br>
 * Description : 편철에 등록된 문서정보 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 3. 29. <br>
 * 수 정 자 : I-ON <br>
 * 수정내용 : <br>
 * 
 * @author I-ON
 * @since 2011. 3. 29.
 * @version 1.0
 * @see com.sds.acube.app.bind.service.BindDocumentService.java
 */

public interface BindDocumentService {

    /**
     * <pre> 
     *  문서목록 가져오는 함수
     * </pre>
     * 
     * @param param
     * @return
     * @throws Exception
     * @see
     */
    List<BindDocVO> getDocumentList(Map<String, String> param) throws Exception;


    /**
     * <pre> 
     *  문서 정보 가져오는 함수
     * </pre>
     * 
     * @param compId
     * @param docId
     * @return
     * @throws Exception
     * @see
     */
    BindDocVO getDocument(String compId, String docId) throws Exception;

    /**
     * <pre> 
     *  미편철함 목록 가져오는 함수
     * </pre>
     * @param param
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<BindDocVO> getNonBindList(Map<String, String> param) throws Exception;


    /**
     * <pre> 
     *  인계 문서함 목록 가져오는 함수
     * </pre>
     * @param param
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<BindDocVO> getTransferList(Map<String, String> param) throws Exception;

    /**
     * <pre> 
     *  문서 이동
     * </pre>
     * @param bindDocVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    int move(BindDocVO bindDocVO) throws Exception;

}
