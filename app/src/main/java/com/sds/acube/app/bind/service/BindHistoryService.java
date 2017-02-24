/**
 * 
 */
package com.sds.acube.app.bind.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.bind.vo.BindVO;


/**
 * Class Name : BindHistoryService.java <br>
 * Description : 설명 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 3. 29. <br>
 * 수 정 자 : I-ON <br>
 * 수정내용 : <br>
 * 
 * @author I-ON
 * @since 2011. 3. 29.
 * @version 1.0
 * @see com.sds.acube.app.bind.service.BindHistoryService.java
 */

public interface BindHistoryService {

    /**
     * <pre> 
     *  편철 이력 목록 가져오는 함수
     * </pre>
     * 
     * @param param
     * @return
     * @throws Exception
     * @see
     */
    List<BindVO> getList(Map<String, String> param) throws Exception;


    /**
     * <pre> 
     *  편철 정보 가져오는 함수
     * </pre>
     * 
     * @param bindVO
     * @return
     * @throws Exception
     * @see
     */
    BindVO get(BindVO bindVO) throws Exception;


    /**
     * <pre> 
     *  편철 수정 시 이력 등록하는 함수
     * </pre>
     * 
     * @param bindVO
     * @return
     * @throws Exception
     * @see
     */
    int insert(BindVO bindVO) throws Exception;
}
