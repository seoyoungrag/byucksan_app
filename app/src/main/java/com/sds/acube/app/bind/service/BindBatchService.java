/**
 * 
 */
package com.sds.acube.app.bind.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.bind.vo.BatchVO;


/**
 * Class Name : BindBatchService.java <br>
 * Description : 편철 일괄생성 관련 서비스 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 3. 29. <br>
 * 수 정 자 : I-ON <br>
 * 수정내용 : <br>
 * 
 * @author I-ON
 * @since 2011. 3. 29.
 * @version 1.0
 * @see com.sds.acube.app.bind.service.BindBatchService.java
 */

public interface BindBatchService {

    /**
     * <pre> 
     *  편철일괄생성 목록 가져오는 함수
     * </pre>
     * 
     * @param param
     * @return
     * @throws Exception
     * @see
     */
    List<BatchVO> getList(Map<String, String> param) throws Exception;


    /**
     * <pre> 
     *  편철일괄생성 실행
     * </pre>
     * 
     * @param param
     * @param batchType
     * @return
     * @throws Exception
     * @see
     */
    int execute(Map<String, String> param, String batchType) throws Exception;


    /**
     * <pre> 
     *  편철일괄 생성 로그 등록
     * </pre>
     * 
     * @param batchVO
     * @return
     * @throws Exception
     * @see
     */
    int insert(BatchVO batchVO) throws Exception;


    /**
     * <pre> 
     *  해당 년도/회기 로그 가져오는 함수
     * </pre>
     * 
     * @param compId
     * @param year
     * @return
     * @throws Exception
     * @see
     */
    BatchVO get(String compId, String year) throws Exception;


    /**
     * <pre> 
     *  편철 일괄생성 - 편철 삭제
     * </pre>
     * 
     * @param params
     * @return
     * @throws Exception
     * @see
     */
    int remove(Map<String, String> params) throws Exception;


    /**
     * <pre> 
     *  편철 일괄생성 - 배치로그 삭제
     * </pre>
     * 
     * @param params
     * @return
     * @throws Exception
     * @see
     */
    int removeBatch(Map<String, String> params) throws Exception;

}
