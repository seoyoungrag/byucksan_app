package com.sds.acube.app.list.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.anyframe.pagination.Page;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.list.vo.SearchVO;

/** 
 *  Class Name  : IListEtcService.java <br>
 *  Description : 공람게시판 리스트, 기타 인터페이스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : <br>
 *  수 정  자 : <br>
 *  수정내용 : <br>
 * 
 *  @author  김경훈 
 *  @since 2011. 5. 18.
 *  @version 1.0 
 *  @see  com.sds.acube.app.list.service.IListApprovalService.java
 */

public interface IListEtcService {
    
    /**
     * 
     * <pre> 
     *  공람게시판 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDisplayNotice(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  공람게시판 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDisplayNotice(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  공람게시판 목록을 조회하는 서비스(목록만)
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AppDocVO> listDisplayNotice(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  공람게시판 전체 목록을 조회하는 서비스
     * </pre>
     * @param searchVO
     * @param pageIndex
     * @return
     * @throws Exception
     * @see  
     *
     */
    public Page listAllDisplayNotice(SearchVO searchVO, int pageIndex) throws Exception;
        
   
    /**
     * 
     * <pre> 
     *  공람게시판 전체 목록을 조회하는 서비스
     * </pre>
     * @param searchVO
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     * @see  
     *
     */
    public Page listAllDisplayNotice(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  공람게시판 전체 목록을 조회하는 서비스
     * </pre>
     * @param searchVO
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AppDocVO> listAllDisplayNotice(SearchVO searchVO) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  조건에 따른 하위부서 반환
     * </pre>
     * @param compId
     * @param deptId
     * @param orgType
     * @return
     * @throws Exception
     * @see  
     *
     */
    public String getRowDeptIds(String compId, String deptId, String orgType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  조건에 따른 하위부서 반환
     * </pre>
     * @param deptId
     * @param orgType
     * @return
     * @throws Exception
     * @see  
     *
     */
    public String getRowDeptIds(String deptId, String orgType) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  조건에 따른 부서 반환
     * </pre>
     * @param compId
     * @param deptId
     * @param orgType
     * @return
     * @throws Exception
     * @see  
     *
     */
    public String getDeptId(String compId, String deptId, String orgType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  조건에 따른 부서 반환
     * </pre>
     * @param compId
     * @param deptId
     * @param orgType
     * @return
     * @throws Exception
     * @see  
     *
     */
    public String getDeptName(String compId, String deptId, String orgType) throws Exception;
    
    /**
     * 
     * <pre> 
     *  조건에 따른 부서 정보 반환
     * </pre>
     * @param compId
     * @param deptId
     * @param orgType
     * @return
     * @throws Exception
     * @see  
     *
     */
    public OrganizationVO getDeptInfo(String compId, String deptId, String orgType) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  편철에 따른 결재진행중인 건수
     * </pre>
     * @param compId
     * @param bindId(편철ID)
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int getAppIngBindCount(String compId, String bindingId) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  편철ID에 따른 결재진행중인 목록
     * </pre>
     * @param searchVO
     * @param pageIndex
     * @return
     * @throws Exception
     * @see  
     *
     */
    public Page listAppIngBind(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재자 역할에 들어갈 내용 반환
     * </pre>
     * @param compId
     * @return
     * @throws Exception
     * @see  
     *
     */
    public Map<String,String> returnDetailSearchApprRole(String compId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  요청한 역할의 사용 여부를  반환
     * </pre>
     * @param compId
     * @param arrList
     * @return
     * @throws Exception
     * @see  
     *
     */
    public Map<String,String> returnDetailSearchApprRole(String compId, ArrayList<String> arrList) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  업무결재 연계이력 삭제
     * </pre>
     * @param compId
     * @param docId
     * @return
     * @throws Exception
     * @see  
     *
     */
    public ResultVO deleteBizProc(String compId, String docId) throws Exception;
    /**
     * 
     * <pre> 
     *  문서관리 연계이력 삭제
     * </pre>
     * @param compId
     * @param docId
     * @return
     * @throws Exception
     * @see  
     *
     */
    public ResultVO deleteDocToMgr(String compId, String docId) throws Exception;
    /**
     * 
     * <pre> 
     *  접속이력 삭제
     * </pre>
     * @param compId
     * @param hisId
     * @return
     * @throws Exception
     * @see  
     *
     */
    public ResultVO deleteAccHis(String compId, String hisId) throws Exception;
    
}
