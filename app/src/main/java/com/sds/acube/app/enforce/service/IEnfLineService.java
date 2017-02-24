/**
 * 
 */
package com.sds.acube.app.enforce.service;

import java.util.List;

import com.sds.acube.app.enforce.vo.EnfLineVO;


/**
 * Class Name : IEnfLineService.java <br>
 * Description : 접수 결재라인을 처리하는 인터페이스 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : Mar 18, 2011 <br>
 * 수 정 자 : 윤동원 <br>
 * 수정내용 : <br>
 * 
 * @author 윤동원
 * @since Mar 18, 2011
 * @version 1.0
 * @see com.kdb.portal.enforce.impl.IEnfLineService.java
 */

public interface IEnfLineService {
    
    /**
     * 
     * <pre> 
     *  결재라인 등록처리
     * </pre>
     * @param list
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
	public void insert(List list) throws Exception;

    /**
     * 
     * <pre> 
     *  결재라인 삭제처리
     * </pre>
     * @param lineVO
     * @throws Exception
     * @see  
     *
     */
    public void delete(EnfLineVO lineVO) throws Exception;

    /**
     * 
     * <pre> 
     *  결재라인 정보조회처리
     * </pre>
     * @param lineVO
     * @param docState
     * @return
     * @throws Exception
     * @see  
     *
     */
    public String get(EnfLineVO lineVO, String docState) throws Exception;

    /**
     * 
     * <pre> 
     *  결재라인 정보조회처리
     * </pre>
     * @param lineVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public String get(EnfLineVO lineVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재라인 내역 조회처리
     * </pre>
     * @param lineVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<EnfLineVO> getList(EnfLineVO lineVO) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  결재처리 의견 조회
     * </pre>
     * @param lineVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public String[]  getCurOpinion(EnfLineVO lineVO) throws Exception;

}