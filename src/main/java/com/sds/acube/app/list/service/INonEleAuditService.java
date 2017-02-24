/**
 * 
 */
package com.sds.acube.app.list.service;

import java.util.Map;

import com.sds.acube.app.common.vo.AuditListVO;
import com.sds.acube.app.common.vo.ResultVO;

/** 
 *  Class Name  : INonEleAuditService.java <br>
 *  Description : 일상감사일지 등록  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 5. 20. <br>
 *  수 정  자 : 장진홍  <br>
 *  수정내용 :  <br>
 * 
 *  @author  jumbohero 
 *  @since 2011. 5. 20.
 *  @version 1.0 
 *  @see  com.sds.acube.app.list.service.INonEleAuditService.java
 */

public interface INonEleAuditService {
    /**
     * 
     * <pre> 
     *  일상감사일지 등록 
     * </pre>
     * @param auditListVO
     * @param currentDate
     * @return
     * @throws Exception
     * @see  
     *
     */
    public ResultVO insertNonEleAudit(AuditListVO auditListVO,  String currentDate) throws Exception;
    

    /**
     * 
     * <pre> 
     *  일상감사일지 정보
     * </pre>
     * @param map
     * @return
     * @throws Exception
     * @see  
     *
     */
    public AuditListVO selectAuditList(Map<String, String> map) throws Exception;
    
    /**
     * 
     * <pre> 
     *  일상감사일지를 수정한다.
     * </pre>
     * @param auditListVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public ResultVO updateNonEleAudit(AuditListVO auditListVO) throws Exception;
}
