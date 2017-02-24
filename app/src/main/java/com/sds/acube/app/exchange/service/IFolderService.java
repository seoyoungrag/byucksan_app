/**
 * 
 */
package com.sds.acube.app.exchange.service;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.sds.acube.app.bind.vo.BindVO;


/**
 * Class Name : IFolderService.java <br>
 * Description : 설명 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 6. 16. <br>
 * 수 정 자 : yucea <br>
 * 수정내용 : <br>
 * 
 * @author yucea
 * @since 2011. 6. 16.
 * @version 1.0
 * @see com.sds.acube.app.ws.service.IFolderService.java
 */

public interface IFolderService {

    boolean create(BindVO vo) throws Exception;


    boolean update(BindVO vo) throws Exception;


    boolean move(BindVO vo, BindVO target) throws Exception;


    boolean remove(String compId, String deptId, String bindId) throws Exception;


    boolean share(BindVO vo, String targetId) throws Exception;


    boolean removeShare(BindVO bindVO) throws Exception;


    boolean order(String compId, String deptId, BindVO[] vo) throws Exception;
    
    JSONArray testCleanup(String compId) throws Exception;
    
    JSONObject cleanup(String compId) throws Exception;
}
