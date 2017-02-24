package com.sds.acube.app.list.service;


import java.util.Map;


/** 
 *  Class Name  : IListBoxService.java <br>
 *  Description : 왼쪽 메뉴 함 리스트 인터페이스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : <br>
 *  수 정  자 : <br>
 *  수정내용 : <br>
 * 
 *  @author  김경훈 
 *  @since 2011. 3. 22.
 *  @version 1.0 
 *  @see  com.sds.acube.app.list.service.IListBoxService.java
 */

public interface IListBoxService {
    
    /**
     * 
     * <pre> 
     *  함 목록을 조회하는 서비스
     * </pre>
     * @param optionVO
     * @return
     * @throws Exception
     * @see  
     *
     */
	Map<String,String[]> list(String compId, String optBoxGroupId, String optRegistGroupId, String roleCodes, String deptId, String userId, String langType) throws Exception;
}
