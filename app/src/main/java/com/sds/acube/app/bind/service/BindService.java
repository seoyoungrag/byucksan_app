/**
 * 
 */
package com.sds.acube.app.bind.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.bind.vo.BatchVO;
import com.sds.acube.app.bind.vo.BindBatchVO;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.bind.vo.BindUnitVO;


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
 * @see com.sds.acube.app.bind.service.BindService.java
 */

public interface BindService {

    /**
     * <pre> 
     *  편철 목록을 가져오는 함수 (관리자)
     * </pre>
     * 
     * @param param
     * @return
     * @throws Exception
     * @see
     */
    List<BindVO> getList(Map<String, String> param) throws Exception;
    List<BindVO> getListResource(Map<String, String> param) throws Exception;
    
    /**
     * <pre> 
     *  편철 목록을 가져오는 함수(공융문서함 문서 개수는 제외) (관리자)
     * </pre>
     * 
     * @param param
     * @return
     * @throws Exception
     * @see
     */
    List<BindVO> getList2(Map<String, String> param) throws Exception;
    /**
     * <pre> 
     *  단위업무 목록을 가져오는 함수
     * </pre>
     * 
     * @param param
     * @return
     * @throws Exception
     * @see
     */
    List<BindUnitVO> getBindUnitList(BindUnitVO bindUnitVO) throws Exception;
    List<BindUnitVO> getBindUnitListResource(BindUnitVO bindUnitVO) throws Exception;

    /**
     * <pre> 
     *  선택한 편철 정보를 가져오는 함수
     * </pre>
     * 
     * @param compId
     * @param deptId
     * @param bindId
     * @return
     * @throws Exception
     * @see
     */
    BindVO get(String compId, String deptId, String bindId, String unitType) throws Exception;
    BindVO getShare(String compId, String deptId, String bindId, String unitType) throws Exception;
    BindVO getResource(String compId, String deptId, String bindId, String unitType, String langType) throws Exception;
    
    /**
     * <pre> 
     *  관리자, 권한 정보 셋업
     * </pre>
     * 
     * @param resultBindVO
     * @param compId
     * @param deptId
     * @param bindId
     * @return
     * @throws Exception
     * @see
     */
    public void setInfoManagerAndAuth(BindVO resultBindVO, String bindId, String compId, String deptId) throws Exception;
    
    /**
     * <pre> 
     *  선택한 편철 정보를 가져오는 함수 (단위업무명 제외)
     * </pre>
     * 
     * @param compId
     * @param deptId
     * @param bindId
     * @return
     * @throws Exception
     * @see
     */
    BindVO getMinor(String compId, String deptId, String bindId) throws Exception;
    BindVO getMinorShare(String compId, String deptId, String bindId) throws Exception;
    BindVO getMinorResource(String compId, String deptId, String bindId, String langType) throws Exception;


    /**
     * <pre> 
     *  단위업무와 지정한 년도/회기에 등록된 편철 목록을 가져오는 함수
     * </pre>
     * 
     * @param compId
     * @param deptId
     * @param unitId
     * @param createYear
     * @return
     * @throws Exception
     * @see
     */
    List<BindVO> selectBindList(String compId, String deptId, String unitId, String createYear) throws Exception;


    /**
     * <pre> 
     *  편철 등록
     * </pre>
     * 
     * @param bindVO
     * @return
     * @throws Exception
     * @see
     */
    int insert(BindVO bindVO) throws Exception;
    int insertShare(BindVO bindVO) throws Exception;
    
    

    /**
     * <pre> 
     *  편철 수정
     * </pre>
     * 
     * @param bindVO
     * @return
     * @throws Exception
     * @see
     */
    int update(BindVO bindVO) throws Exception;
    int updateShare(BindVO bindVO) throws Exception;
    

    /**
     * <pre> 
     *  하위 캐비닛까지 일괄 수정
     * </pre>
     * 
     * @param bindVO
     * @return
     * @throws Exception
     * @see
     */
    int updateAll(BindVO bindVO) throws Exception;
    

    /**
     * <pre> 
     *  편철 삭제
     * </pre>
     * 
     * @param bindVO
     * @return
     * @throws Exception
     * @see
     */
    int remove(BindVO bindVO) throws Exception;

    /**
     * <pre> 
     *  편철 삭제
     * </pre>
     * 
     * @param bindVO
     * @return
     * @throws Exception
     * @see
     */
    String removeBind(BindVO bindVO) throws Exception;
    String removeBindShare(BindVO bindVO) throws Exception;
    

    /**
     * <pre> 
     *  편철 인계
     * </pre>
     * 
     * @param bindVO
     * @return
     * @throws Exception
     * @see
     */
    int transfer(BindVO bindVO) throws Exception;

    /**
     * <pre> 
     *  편철 인계
     * </pre>
     * 
     * @param bindVO
     * @return
     * @throws Exception
     * @see
     */
    int bindTransfer(BindVO bindVO) throws Exception;

    /**
     * <pre> 
     *  편철 목록을 가져오는 함수
     * </pre>
     * 
     * @param param
     * @return
     * @throws Exception
     * @see
     */
    List<BindVO> getSelectList(Map<String, String> param) throws Exception;
    List<BindVO> getSelectListResource(Map<String, String> param) throws Exception;
    
    BindVO[] getSelectTreeBind(BindVO bindVO) throws Exception;
    BindVO[] getSelectTreeShareBind(BindVO bindVO) throws Exception;
    BindVO[] getSelectTreeBindResource(BindVO bindVO) throws Exception;

    /**
     * <pre> 
     *  문서등록 시 편철 선택목록을 제공하는 함수
     * </pre>
     * 
     * @param param
     * @return
     * @throws Exception
     * @see
     */
    List<BindVO> getSelectTargetList(Map<String, String> param) throws Exception;
    List<BindVO> getSelectTargetListResource(Map<String, String> param) throws Exception;
    public BindVO[] getSelectTargetTreeList(BindVO bindVO) throws Exception;
    /**
     * <pre> 
     *  다음 생성할 편철 아이디를 가져오는 함수 
     * </pre>
     * 
     * @return
     * @throws Exception
     * @see
     */
    String getNextBindId() throws Exception;


    /**
     * <pre> 
     *  부서에 편철이 없을 경우 편철 자동생성 시키는 함수 
     * </pre>
     * 
     * @param param
     * @return
     * @throws Exception
     * @see
     */
    int autoCreate(Map<String, String> param) throws Exception;


    /**
     * <pre> 
     *  편철을 공유하는 함수
     * </pre>
     * 
     * @param bindVO
     * @return
     * @throws Exception
     * @see
     */
    int share(BindVO bindVO) throws Exception;

    /**
     * <pre> 
     *  편철을 공유하는 함수
     * </pre>
     * 
     * @param bindVO
     * @return
     * @throws Exception
     * @see
     */
    String shareBind(BindVO bindVO) throws Exception;
    
    /**
     * <pre> 
     *  공유편철 목록 가져오는 함수
     * </pre>
     * 
     * @param param
     * @return
     * @throws Exception
     * @see
     */
    List<BindVO> getSharedList(Map<String, String> param) throws Exception;

    /**
     * <pre> 
     *  공유편철 삭제
     * </pre>
     * 
     * @param vo
     * @return
     * @throws Exception
     * @see
     */
    int removeShare(BindVO vo) throws Exception;

    /**
     * <pre> 
     *  공유편철 삭제
     * </pre>
     * 
     * @param vo
     * @return
     * @throws Exception
     * @see
     */
    void removeShareBind(BindVO vo) throws Exception;
    
    /**
     * <pre> 
     *  가장 최근 편철일괄 생성로그를 가져오는 함수 
     * </pre>
     * 
     * @param compId
     * @return
     * @throws Exception
     * @see
     */
    BatchVO getBindSearchYear(String compId) throws Exception;


    /**
     * <pre> 
     *  편철 순서를 조정하는 함수
     * </pre>
     * 
     * @param compId
     * @param deptId
     * @param bindId
     * @param ordered
     * @return
     * @throws Exception
     * @see
     */
    int bindOrder(String compId, String deptId, String bindId, int ordered) throws Exception;


    /**
     * <pre> 
     *  단위업무로 편철가져오는 함수
     * </pre>
     * 
     * @param compId
     * @param deptId
     * @param unitId
     * @param period
     * @return
     * @throws Exception
     * @see
     */
    BindVO getBindByUnitId(String compId, String deptId, String unitId, String period) throws Exception;


    /**
     * <pre> 
     *  동일한 편철명이 존재하는지 확인하는 함수 bindId null 일경우 등록, bindId가 null 아닐경우 수정
     * </pre>
     * 
     * @param compId
     * @param deptId
     * @param bindName
     * @param createYear
     * @param bindId
     * @return
     * @throws Exception
     * @see
     */
    boolean exist(String compId, String deptId, String bindName, String createYear, String bindId) throws Exception;
    boolean existShare(String compId, String deptId, String bindName, String createYear, String bindId) throws Exception;
    
    
    boolean existResource(String compId, String deptId, String bindName, String createYear, String bindId, String langType) throws Exception;


    /**
     * <pre> 
     *  공유편철 목록을 가져오는 함수
     * </pre>
     * 
     * @param compId
     * @param bindId
     * @return
     * @throws Exception
     * @see
     */
    List<BindVO> getBindShareList(String compId, String bindId) throws Exception;


    /**
     * <pre> 
     *  편철일괄생성을 위한 대상 목록을 가져오는 함수
     * </pre>
     * 
     * @param params
     * @see
     */

    List<BindBatchVO> getBindBatchTargetList(Map<String, String> param) throws Exception;


    /**
     * <pre> 
     *  편철 일괄생성 등록
     * </pre>
     * 
     * @param params
     * @see
     */

    int bindBatchCreate(Map<String, String> param) throws Exception;


    /**
     * <pre> 
     *  편철 일괄생성 목록에 신규생성하려는 목록이 존재하는지 여부
     * </pre>
     * 
     * @param params
     * @return
     * @see
     */

    boolean bindBatchExist(Map<String, String> params) throws Exception;


    /**
     * <pre> 
     *  편철 일괄생성 목록에서 데이타 삭제
     * </pre>
     * 
     * @param params
     * @see
     */

    int bindBatchRemove(Map<String, String> params) throws Exception;


    /**
     * <pre> 
     *  편철 일괄생성 조회
     * </pre>
     * 
     * @param compId
     * @param unitId
     * @param bindName
     * @see
     */

    BindBatchVO getBindBatchTarget(String compId, String unitId, String displayName) throws Exception;

    /**
     * <pre> 
     *  연계코드로 편철 가져오는 함수
     * </pre>
     * @param compId
     * @param deptId
     * @param prefix
     * @param period
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<BindVO> getBindToPrefix(String compId, String deptId, String prefix, String period) throws Exception;
    
    /**
     * 
     * <pre> 
     *  연계코드로 편철 가져오는 함수, 편철함 상태 확인
     * </pre>
     * @param compId
     * @param deptId
     * @param prefix
     * @param period
     * @param sendType
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<BindVO> getBindToPrefix(String compId, String deptId, String prefix, String period, String sendType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  편철 조회
     * </pre>
     * @param bindVO(compId, bindId)
     * @return BindVO
     * @throws Exception
     * @see  
     *
     */
    BindVO selectBind(BindVO bindVO) throws Exception;
    
    /**
     * 
     * <pre> 
     * 인계할 캐비닛의 하위 캐비닛 리스트를 가져옴
     * </pre>
     * @param BindVO bindVO
     * @param List<BindVO> bindList
     * @return String 
     * @throws Exception
     * @see  
     *
     */
     String getTransposeTreeList(BindVO bindVO, List<BindVO> bindList) throws Exception;
	/**
	 * <pre> 
	 *  설명
	 * </pre>
	 * @param bindVO
	 * @return
	 * @see  
	 * */ 
	
	BindVO[] getSelectTreeBindShare(BindVO bindVO) throws Exception;
	
	
	BindVO checkroot(BindVO bindVO) throws Exception;
}
