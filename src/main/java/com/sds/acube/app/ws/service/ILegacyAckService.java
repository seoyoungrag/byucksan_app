package com.sds.acube.app.ws.service;

import com.sds.acube.app.ws.vo.EsbAppDocVO;
import com.sds.acube.app.ws.vo.LegacyVO;


/**
 * 
 *  Class Name  : ILegacyAckService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정 일 : 2012. 6. 26. <br>
 *  수 정 자 : 김상태  <br>
 *  수정내용 :  <br>
 * 
 *  @author  chamchi 
 *  @since   2011. 5. 30.
 *  @version 1.0 
 *  @see  com.sds.acube.app.ws.service.ILegacyAckService.java
 */
public interface ILegacyAckService {

    // Legacy Web Service연계
    public LegacyVO  processAppDoc(EsbAppDocVO esbAppDocVO) throws Exception;
    
    // Legacy Local File연계
    public LegacyVO legacyAckFile(EsbAppDocVO esbAppDocVO) throws Exception;
    
    // Legacy FTP연계
    public LegacyVO legacyAckFTP(EsbAppDocVO esbAppDocVO) throws Exception;
    
    // Legacy SFTP연계
    public LegacyVO legacyAckSFTP(EsbAppDocVO esbAppDocVO) throws Exception;

}
