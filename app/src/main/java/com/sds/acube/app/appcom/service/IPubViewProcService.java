
package com.sds.acube.app.appcom.service;

import com.sds.acube.app.appcom.vo.PubReaderVO;


/**
 * 
 *  Class Name  : IPubViewProcService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 30. <br>
 *  수 정  자 : 윤동원  <br>
 *  수정내용 :  <br>
 * 
 *  @author  윤동원 
 *  @since 2011. 3. 30.
 *  @version 1.0 
 *  @see  com.sds.acube.app.appcom.service.IPubViewProcService.java
 */
public interface IPubViewProcService {

    /**
     * <pre> 
     *  생산/접수문서의 공람자 공람처리(TGW_PUB_VIEW)
     *  -공람처리일자 수정
     * </pre>
     * 
     * @param enfDocVO
     * @throws Exception
     * @see
     */
    public void processPubView(PubReaderVO pubReaderVO) throws Exception;



}