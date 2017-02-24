package com.sds.acube.app.appcom.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.sds.acube.app.login.vo.UserProfileVO;


/**
 * Class Name : IPubReaderProcService.java <br>
 * Description : 공람처리 인터페이스 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 3. 30. <br>
 * 수 정 자 : 윤동원 <br>
 * 수정내용 : <br>
 * 
 * @author 윤동원
 * @since 2011. 3. 30.
 * @version 1.0
 * @see com.sds.acube.app.appcom.service.IPubReaderProcService.java
 */

public interface IPubReaderProcService {

    /**
     * <pre> 
     *  공람자 등록
     * </pre>
     * 
     * @param pubReaderVO
     * @throws Exception
     * @see
     */
    public void insertPubReader(Map<String, String> map) throws Exception;

    /**
     * <pre> 
     *  생산/접수문서의 공람자 공람문서 읽기(TGW_PUB_READER)
     *  -읽은날짜 수정
     * </pre>
     * 
     * @param compId 회사ID
     * @param docId 문서ID
     * @param userId 사용자ID
     * @param currentDate 현재날짜
     * @throws Exception
     * @see
     */
    public int updatePubReader(String compId, String[] docIds, String userId, String currentDate) throws Exception;
    public int updatePubReader(String compId, String docId, String userId, String currentDate) throws Exception;


    /**
     * <pre> 
     *  생산/접수문서의 공람자 공람(TGW_PUB_READER)
     *  -공람일자 수정
     * </pre>
     * 
     * @param compId 회사ID
     * @param docId 문서ID
     * @param userId 사용자ID
     * @param currentDate 현재날짜
     * @throws Exception
     * @see
     */
    public int processPubReader(String compId, String[] docIds, String userId, String currentDate) throws Exception;
    public int processPubReader(String compId, String docId, String userId, String currentDate) throws Exception;


    /**
     * <pre> 
     *  공람게시판의 게시읽음(TGW_POST_READER)
     *  -공람일자 수정
     * </pre>
     * 
     * @param compId 회사ID
     * @param docId 문서ID
     * @param userProfileVO 사용자정보
     * @param currentDate 현재날짜
     * @throws Exception
     * @see
     */
    public int processPostReader(String compId, String docId, UserProfileVO userProfileVO, String currentDate) throws Exception;

}