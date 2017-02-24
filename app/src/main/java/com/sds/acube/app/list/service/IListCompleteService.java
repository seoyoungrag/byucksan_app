package com.sds.acube.app.list.service;

import java.util.List;

import org.anyframe.pagination.Page;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.env.vo.AuditDeptVO;
import com.sds.acube.app.list.vo.SearchVO;

/** 
 *  Class Name  : IListCompleteService.java <br>
 *  Description : (완료폴더)기안문서함, 결재완료함, 접수완료함, 공람문서함, 후열문서함, 검사부열람함, 임원문서함, 임원결재함, 주관부서문서함, 본부문서함, 회사문서함, 여신문서함 , 거래처완료문서 에 관한  리스트 인터페이스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : <br>
 *  수 정  자 : <br>
 *  수정내용 : <br>
 * 
 *  @author  김경훈 
 *  @since 2011. 4. 14.
 *  @version 1.0 
 *  @see  com.sds.acube.app.list.service.IListCompleteService.java
 */

public interface IListCompleteService {
    
   
    
    /**
     * 
     * <pre> 
     *  기안문서함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDraft(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  기안문서함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDraft(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재완료함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listApprovalComplete(SearchVO searchVO, int pageIndex) throws Exception;    
    
    
    /**
     * 
     * <pre> 
     *  결재완료함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listApprovalComplete(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재완료함 목록을 조회하는 서비스(메인)
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<AppDocVO> listApprovalComplete(SearchVO searchVO) throws Exception; 
    
    /**
     * 
     * <pre> 
     *  접수완료함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listReceiveComplete(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  접수완료함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listReceiveComplete(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  접수완료함 목록을 조회하는 서비스(목록만)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AppDocVO> listReceiveComplete(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  공람문서함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDisplay(SearchVO searchVO, int pageIndex) throws Exception;
       
    
    /**
     * 
     * <pre> 
     *  공람문서함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listDisplay(SearchVO searchVO, int pageIndex, int pageSize) throws Exception; 
    
    /**
     * 
     * <pre> 
     *  공람문서함 목록을 조회하는 서비스(목록만)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AppDocVO> listDisplay(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  공람문서함 목록을 조회하는 서비스(건수)
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public int listDisplayCount(SearchVO searchVO) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  후열문서함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listRear(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  후열문서함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listRear(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  후열문서함 목록을 조회하는 서비스(목록만)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AppDocVO> listRear(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  후열문서함 목록을 조회하는 서비스(count)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int listRearCount(SearchVO searchVO) throws Exception;
    
    
    
    /**
     * 
     * <pre> 
     *  검사부열람함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listExamReading(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  검사부열람함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listExamReading(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  감사담당부서  조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AuditDeptVO> getListAuditDept(AuditDeptVO auditDeptVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  감사담당부서  갯수를 조회하는 서비스
     * </pre>
     * @param auditDeptVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int getAuditDeptCnt(AuditDeptVO auditDeptVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  임원문서함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listOfficer(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  임원문서함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listOfficer(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  임원문서함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public List<AppDocVO> listOfficer(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  임원결재함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listOfficerApproval(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  임원결재함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listOfficerApproval(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  신청서완료함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listConductTeam(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  신청서완료함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listConductTeam(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  본부공문함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listHQ(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  본부공문함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listHQ(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  기관공문함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listInstitution(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  기관공문함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listInstitution(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  기관공문함 목록을 조회하는 서비스(목록만)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AppDocVO> listInstitution(SearchVO searchVO) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  기관공문함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listCompany(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  회사공문함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page listCompany(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  회사공문함 목록을 조회하는 서비스(목록만)
     * </pre>
     * @param searchVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<AppDocVO> listCompany(SearchVO searchVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  여신문서함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page LoanBusiness(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  여신문서함 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page LoanBusiness(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    /**
     * 
     * <pre> 
     *  거래처완료문서 목록을 조회하는 서비스
     * </pre>
     * @param 
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    public Page ListCustomerComplete(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  부서협조문서함 목록을 조회하는 서비스
     * </pre>
     * @param searchVO
     * @param pageIndex
     * @return
     * @throws Exception
     * @see  
     *
     */
    public Page listDeptAssistant(SearchVO searchVO, int pageIndex) throws Exception;
    
    /**
     * 
     * <pre> 
     *  부서협조문서함 목록을 조회하는 서비스
     * </pre>
     * @param searchVO
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     * @see  
     *
     */
    public Page listDeptAssistant(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
    
    public Page listRepresentativeComplete(SearchVO searchVO, int pageIndex, int pageSize) throws Exception;
}
