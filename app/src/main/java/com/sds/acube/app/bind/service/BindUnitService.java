/**
 * 
 */
package com.sds.acube.app.bind.service;

import java.util.List;
import java.util.Map;

import org.anyframe.pagination.Page;

import com.sds.acube.app.bind.vo.BindUnitVO;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.list.vo.SearchVO;


/**
 * Class Name : BindService.java <br>
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
 * @see com.sds.acube.app.bind.service.BindUnitService.java
 */

public interface BindUnitService {

    /**
     * <pre> 
     *  단위업무 트리를 나타내기 위해 트리 목록을 가져오는 함수
     * </pre>
     * 
     * @param param
     * @return
     * @throws Exception
     * @see
     */
    List<BindUnitVO> getTree(Map<String, String> param) throws Exception;


    /**
     * <pre> 
     *  단위업무 선택을 위한 트리 목록을 가져오는 함수
     * </pre>
     * 
     * @param param
     * @return
     * @throws Exception
     * @see
     */
    List<BindUnitVO> getSelectTree(Map<String, String> param) throws Exception;
    
    
    Page listBindUnit(SearchVO searchVO, int pageIndex) throws Exception;
    Page listBindUnit(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    public BindUnitVO[] listTreeBindUnit(BindUnitVO bindUnitVO) throws Exception;
    public BindUnitVO[] listTreeShareBindUnit(BindUnitVO bindUnitVO) throws Exception;

    // 다국어 추가
    Page listBindUnitResource(SearchVO searchVO, int pageIndex) throws Exception;
    Page listBindUnitResource(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    public BindUnitVO[] listTreeBindUnitResource(BindUnitVO bindUnitVO) throws Exception;

    /**
     * <pre> 
     *  단위업무 등록
     * </pre>
     * 
     * @param bindUnitVO
     * @return
     * @throws Exception
     * @see
     */
    int insert(BindUnitVO bindUnitVO) throws Exception;
    int simpleInsert(BindUnitVO bindUnitVO) throws Exception;


    /**
     * <pre> 
     *  단위업무 삭제
     * </pre>
     * 
     * @param bindUnitVO
     * @return
     * @throws Exception
     * @see
     */
    int delete(BindUnitVO bindUnitVO) throws Exception;
    int simpleDelete(BindUnitVO bindUnitVO) throws Exception;


    /**
     * <pre> 
     *  단위업무 수정
     * </pre>
     * 
     * @param bindUnitVO
     * @return
     * @throws Exception
     * @see
     */
    int update(BindUnitVO bindUnitVO) throws Exception;
    int simpleUpdate(BindUnitVO bindUnitVO) throws Exception;


    /**
     * <pre> 
     *  분류체계명 수정
     * </pre>
     * 
     * @param bindUnitVO
     * @return
     * @throws Exception
     * @see
     */
    int rename(BindUnitVO bindUnitVO) throws Exception;
    int simpleRename(BindUnitVO bindUnitVO) throws Exception;


    /**
     * <pre> 
     *  단위업무 내용 가져오는 함수
     * </pre>
     * 
     * @param bindUnitId
     * @param bindUnitVO
     * @return
     * @throws Exception
     * @see
     */
    BindUnitVO get(String bindUnitId, BindUnitVO bindUnitVO) throws Exception;
    BindUnitVO getResource(String bindUnitId, BindUnitVO bindUnitVO) throws Exception;
    
    BindUnitVO simpleGet(BindUnitVO bindUnitVO) throws Exception;
    BindUnitVO simpleGetResource(BindUnitVO bindUnitVO) throws Exception;


    /**
     * <pre> 
     *  단위업무 목록 가져오는 함수
     * </pre>
     * 
     * @param vo
     * @return
     * @throws Exception
     * @see
     */
    List<BindVO> getBindUseList(BindUnitVO vo) throws Exception;

}
