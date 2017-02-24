/**
 * 
 */
package com.sds.acube.app.list.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.appcom.vo.DocNumVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.common.vo.StampListVO;

/** 
 *  Class Name  : iNonEleStampSealService.java <br>
 *  Description : 서명인/직인날인기록 등록   <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 5. 18. <br>
 *  수 정  자 : 장진홍  <br>
 *  수정내용 :  <br>
 * 
 *  @author  jumbohero 
 *  @since 2011. 5. 18.
 *  @version 1.0 
 *  @see  com.sds.acube.app.list.service.INonEleStampSealService.java
 */

public interface INonEleStampSealService {

    /**
     * 
     * <pre> 
     *  서명인/직인날인기록 등록 
     * </pre>
     * @param stempListVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public ResultVO insertNonEleStampSeal(StampListVO stempListVO,String deptId, String proxyDeptId, String currentDate) throws Exception;
    
    /**
     * 
     * <pre> 
     * 서명인/직인날인기록 정보
     * </pre>
     * @param map
     * @return
     * @throws Exception
     * @see  
     *
     */
    public StampListVO selectStampSeal(Map<String, String> map) throws Exception;
    
    /**
     * 
     * <pre> 
     *  서명인/날인 등록 대장 내역을 삭제한다.
     * </pre>
     * @param map
     * @return
     * @throws Exception
     * @see  
     *
     */
    public ResultVO deleteStampList(List<Object> params) throws Exception;
       
    /**
     * 
     * <pre> 
     *  서명인/날인 등록 대장 내역을 업데이트 한다.
     * </pre>
     * @param stempListVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public ResultVO updateStampList(StampListVO stempListVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  서명인날인 승인
     * </pre>
     * @param docNumVO
     * @param stampId
     * @return
     * @throws Exception
     * @see  
     *
     */
    public ResultVO confirmStampSealList(DocNumVO docNumVO, String stampId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  날인번호 확인
     * </pre>
     * @param stampId
     * @return sealNumber
     * @throws Exception
     * @see  
     *
     */
    public int selectStampSealNumber(String compId, String stampId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  서명인 날인 대장을 삭제한다
     * </pre>
     * @param stempListVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public ResultVO deleteStampSeal(StampListVO stempListVO) throws Exception;
}
