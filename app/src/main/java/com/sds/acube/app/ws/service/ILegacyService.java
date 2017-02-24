package com.sds.acube.app.ws.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.jws.WebService;

import com.sds.acube.app.ws.vo.LegacyVO;

/** 
 *  Class Name  : ILegacyService.java <br>
 *  Description : 전자결재 연계서비스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2012. 5. 30. <br>
 *  수 정  자 : 김상태  <br>
 *  수정내용 :  <br>
 * 
 *  @author  김상태 
 *  @since 2012. 5. 30.
 *  @version 1.0 
 *  @see  com.sds.acube.app.ws.service.ILegacyService.java
 */

@WebService
public interface ILegacyService {
	/**
     * 
     * <pre> 
     *  결재 기안
     * </pre>
     * @param name
     * @param attchFile
     * @see  
     *
     */
    public LegacyVO insertAppDoc(LegacyVO legacyVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  파일생성
     * </pre>
     * @param name
     * @param attchFile
     * @see  
     *
     */
     public void writeFile(final InputStream input, String filePath, String fileName) throws IOException;
    
    /**
     * 
     * <pre> 
     *  source 파일을 target으로 파일 복사
     * </pre>
     * @param name
     * @param attchFile
     * @see  
     *
     */
    public void copyFile(File source, File target) throws Exception;

}
