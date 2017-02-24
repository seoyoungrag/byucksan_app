/**
 * 
 */
package com.sds.acube.app.common.service;

import java.util.Locale;

import com.sds.acube.app.common.vo.SendMessageVO;
import com.sds.acube.app.ws.vo.CmnResVO;

/** 
 *  Class Name  : ISendMessage.java <br>
 *  Description : 알림 서비스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 7. 6. <br>
 *  수 정  자 : 신경훈  <br>
 *  수정내용 :  <br>
 * 
 *  @author  skh0204 
 *  @since 2011. 7. 6.
 *  @version 1.0 
 *  @see  com.sds.acube.app.common.service.ISendMessageService.java
 */

public interface ISendMessageService {
    
    /**
     * 
     * <pre> 
     *  알림 메시지를 발송한다.
     * </pre>
     * @param vo
     * @param langType
     * @throws Exception
     * @see  
     *
     */
    boolean sendMessage(SendMessageVO vo, Locale langType);
}
