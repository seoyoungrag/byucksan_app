package com.sds.acube.app.legacyTest.service;

import org.anyframe.pagination.Page;

import com.sds.acube.app.legacyTest.vo.LegacyTestVO;


 
/** 
 *  Class Name  : ILegacyTestServicee.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : <br>
 *  수 정  자 : <br>
 *  수정내용 :  <br>
 * 
 *  @author  jth8172 
 *  @since 2012. 5. 21.
 *  @version 1.0 
 *  @see  com.sds.acube.app.legacyTest.service.ILegacyTestService.java
 */

public interface ILegacyTestService {

    // 연계목록조회
    public Page listLegacyTest(LegacyTestVO legacyTestVO,int page) throws Exception;
    
    // 연계등록
    public int insertLegacyTest(LegacyTestVO legacyTestVO) throws Exception;

    // 연계삭제
    public int deleteLegacyTest(LegacyTestVO legacyTestVO) throws Exception;

    // 연계조회
    public LegacyTestVO viewLegacyTest(LegacyTestVO legacyTestVO) throws Exception;
    
    // 연계Ack반영
    public int updateLegacyAckTest(LegacyTestVO legacyTestVO) throws Exception;
 
 
 
    
}
