package com.sds.acube.app.env.service;

import java.util.List;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.env.vo.AuditDeptVO;
import com.sds.acube.app.env.vo.EmptyInfoVO;
import com.sds.acube.app.env.vo.EmptyReasonVO;

/** 
 *  Class Name  : IUserEnvService.java <br>
 *  Description : 사용자설정정보를 가져온다 <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 4. 18. <br>
 *  수 정  자 : redcomet  <br>
 *  수정내용 :  <br>
 * 
 *  @author  redcomet 
 *  @since 2011. 4. 18.
 *  @version 1.0 
 *  @see  com.sds.acube.app.env.service.IEnvUserService.java
 */

public interface IEnvUserService {

    List<EmptyReasonVO> selectEmptyReasonList(String compId) throws Exception;

    EmptyInfoVO selectEmptyInfo(String userId) throws Exception;
    
    EmptyInfoVO selectEmptyInfoForAdmin(String userId) throws Exception;

    boolean insertEmptyInfo(EmptyInfoVO emptyInfoVO) throws Exception;
	
    boolean isAuditor(String compId, String auditorId, String auditorType) throws Exception;

    List<UserVO> selectAuditorList(String compId) throws Exception;

    List<UserVO> selectAuditorList(String compId, String auditorType) throws Exception;
    
    List<AuditDeptVO> selectAuditDeptList(AuditDeptVO auditDeptVO) throws Exception;
    
    void insertAuditor(AuditDeptVO auditDeptVO) throws Exception;

    void deleteAuditor(AuditDeptVO auditDeptVO) throws Exception;

    void insertAuditDept(AuditDeptVO auditDeptVO) throws Exception;

    void deleteAuditDept(AuditDeptVO auditDeptVO) throws Exception;

    FileVO selectUserImage(String compId, String userId, int nType) throws Exception;
    
    boolean updateUserImage(FileVO fileVO, int nType) throws Exception;

    boolean deleteUserImage(String userId, int nType) throws Exception;
    
}
