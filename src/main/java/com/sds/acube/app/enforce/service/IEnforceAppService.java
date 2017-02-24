/**
 * 
 */
package com.sds.acube.app.enforce.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.etc.vo.PostReaderVO;


/**
 * Class Name : IEnforceAppService.java <br>
 * Description : 접수후 프로세스를 위한 인터페이스 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : Mar 18, 2011 <br>
 * 수 정 자 : 윤동원 <br>
 * 수정내용 : <br>
 * 
 * @author 윤동원
 * @since Mar 18, 2011
 * @version 1.0
 * @see com.kdb.portal.enforce.impl.IEnforceAppService.java
 */

public interface IEnforceAppService {

    /**
     * 
     * <pre> 
     *   접수된 문서의 선람자가 선람처리함
     * </pre>
     * @param inputMap
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
	public void processPreRead(Map inputMap) throws Exception;


    /**
     * 
     * <pre> 
     *  접수된 문서의 최종 담당자가 접수완료처리함
     * </pre>
     * @param inputMap
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
	public void processFinalApproval(Map inputMap) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *    접수후 담당처리
     * </pre>
     * @param enfLineVO
     * @throws Exception
     * @see  
     *
     */
    public void processEnfApproval(EnfLineVO enfLineVO) throws Exception;


    /**
     * 
     * <pre> 
     *  접수된 문서의 담당자의 반송처리
     * </pre>
     * @param intput
     * @throws Exception
     * @see  
     *
     */
    public void processEnfDocReject(Map<String, String> intput) throws Exception;


    /**
     * 
     * <pre> 
     *  결재전 문서를 회수한다.
     * </pre>
     * @param enfLineVO
     * @throws Exception
     * @see  
     *
     */
    public void processDocRetrieve(EnfLineVO enfLineVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  관라지의 문서회수
     * </pre>
     * @param enfLineVO
     * @throws Exception
     * @see  
     *
     */
    public void processAdminRetrieve(EnfLineVO enfLineVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  접수경로지정 등록
     * </pre>
     * @param inputMap
     * @throws Exception
     * @see  
     *
     */
    public void insertEnfLine(Map<String, String> inputMap) throws Exception;

    /**
     * 
     * <pre> 
     *  열람일자를 현재일자로 수정함
     * </pre>
     * @param enfLineVO
     * @throws Exception
     * @see  
     *
     */
    public void updateReadDate(EnfLineVO enfLineVO) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *   접수문서 조회
     * </pre>
     * @param enfDocVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public EnfDocVO selectEnfDoc(EnfDocVO enfDocVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  공람게시자등록
     * </pre>
     * @param postReaderVO
     * @throws Exception
     * @see  
     *
     */
    public void  procPubPost(PostReaderVO postReaderVO) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  회수여부체크
     * </pre>
     * @param enfDocVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public boolean  isWithdraw(EnfDocVO enfDocVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  접수후 경로재지정 여부 체크
     * </pre>
     * @param enfDocVO
     * @return 
     * @throws Exception
     * @see  
     *
     */
    public boolean  isEnfLineChange(EnfDocVO enfDocVO) throws Exception;

  
    /**
     * 
     * <pre> 
     *  최종 결재경로 반환
     * </pre>
     * @param map (docId, compId, lines)
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<EnfLineVO> getEnfLineList(Map<String, String> map) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재경로 수정여부 확인
     * </pre>
     * @param enfLineVOs
     * @param enfLineVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public boolean checkEnfLine(List<EnfLineVO> enfLineVOs, EnfLineVO enfLineVO) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  결재경로 삭제 및 재등록(이력 포함)
     * </pre>
     * @param enfLineVOs
     * @param userId
     * @param userName
     * @param setLineHis(hisId 셋팅여부)
     * @return hisId
     * @throws Exception
     * @see  
     *
     */
    public String insertEnfLineProc(List<EnfLineVO> enfLineVOs, String userId, String userName, boolean setLineHis) throws Exception;
    
    /**
     * 
     * <pre> 
     *  다음 결재자 조회
     * </pre>
     * @param enfLineVO
     * @param opt313(부재처리 옵션)
     * @return EnfLineVO
     * @throws Exception
     * @see  
     *
     */
    public EnfLineVO selectNextLineUser(EnfLineVO enfLineVO, String opt313) throws Exception;
    
    /**
     * 
     * <pre> 
     *  다음 결재처리자 수정
     * </pre>
     * @param enfLineVO
     * @throws Exception
     * @see  
     *
     */
    public void updateNextLineUser(EnfLineVO enfLineVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  결재자 리스트
     * </pre>
     * @param enfLineVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public List<EnfLineVO> selectEnfLineList(EnfLineVO enfLineVO) throws Exception;
    
    
}