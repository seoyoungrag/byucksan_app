package com.sds.acube.app.common.service;


/** 
 *  Class Name  : IMemoryService.java <br>
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
 *  @see  com.sds.acube.app.common.service.impl.IMemoryService.java
 */

public interface IMemoryService {

    public void initializeCode() throws Exception;
    public void reloadCode() throws Exception;
    
    public void initializeOption() throws Exception;
    public void reloadOption() throws Exception;
    public void reloadOption(String compId) throws Exception;
}
