package com.sds.acube.app.common.service;

import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.QueueVO;
/** 
 *  Class Name  : ICommonService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 25. <br>
 *  수 정  자 : Timothy  <br>
 *  수정내용 :  <br>
 * 
 *  @author  Timothy 
 *  @since 2011. 3. 25.
 *  @version 1.0 
 *  @see  com.sds.acube.app.common.service.impl.ICommonService.java
 */

public interface ICommonService {

    // 문서관리 연계큐 
    public int insertQueueToDocmgr(QueueToDocmgrVO queueToDocmgrVO) throws Exception;
    // 검색엔진 연계큐
    public int insertQueue(QueueVO queueVO) throws Exception;
}
