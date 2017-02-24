package com.sds.acube.app.exchange.service;

import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.exchange.vo.ConvertVO;


/**
 * Class Name : DocumentService.java <br>
 * Description : 설명 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 6. 1. <br>
 * 수 정 자 : yucea <br>
 * 수정내용 : <br>
 * 
 * @author yucea
 * @since 2011. 6. 1.
 * @version 1.0
 * @see com.sds.acube.app.ws.service.IDocumentService.java
 */

public interface IDocumentService {

    /**
     * <pre> 
     *  큐에 싸인 전송 목록을 문서관리서비스에  전송하는 scheduler
     * </pre>
     * 
     * @throws Exception
     * @see
     */
    void create() throws Exception;
    
    /**
     * 
     * <pre> 
     *  큐에 싸인 전송 목록을 문서관리서비스에  전송하는 서비스(웹에서 실행)
     * </pre>
     * @throws Exception
     * @see  
     *
     */
    void createImmediately() throws Exception;


    /**
     * <pre> 
     *  규에 쌓인 데이터를 삭제한다.
     * </pre>
     * 
     * @throws Exception
     * @see
     */
    void removeQueue() throws Exception;


    /**
     * <pre> 
     *  문서관리 웹서비스 연동 : 문서이동
     * </pre>
     * 
     * @throws Exception
     * @see
     */
    boolean move(BindVO before, BindVO after, String[] docIds) throws Exception;


    boolean convert(ConvertVO vo, boolean ignoreError) throws Exception;

}
