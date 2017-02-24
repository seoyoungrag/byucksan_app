package com.sds.acube.app.relay.service;

import java.io.File;

import com.sds.acube.app.relay.vo.PackInfoVO;

/** 
 *  Class Name  : IRelayRecvService.java <br>
 *  Description : 문서유통(수신) Interface  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2012. 4. 19. <br>
 *  수 정  자 : 김상태  <br>
 *  수정내용 :  <br>
 * 
 *  @author  김상태 
 *  @since 2012. 4. 19.
 *  @version 1.0 
 *  @see  com.sds.acube.app.relay.service.IRelayRecvService.java
 */

public interface IRelayRecvService {	
	// 유통문서 (수신) 처리
	public void isRecvProcess(PackInfoVO packInfoVO, File recvFile) throws Exception;
}
