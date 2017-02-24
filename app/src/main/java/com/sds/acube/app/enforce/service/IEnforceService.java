package com.sds.acube.app.enforce.service;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.EnfProcVO;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;


 
/**
 * Class Name : IEnfLineService.java <br>
 * Description : 접수, 반송, 이송 등의 프로세스 처리 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011 4. 18 <br>
 * 수 정 자 : jth8172 <br>
 * 수정내용 : <br>
 * 
 * @author jth8172
 * @since 2011 4. 18
 * @version 1.0
 * @see com.kdb.portal.enforce.impl.IEnfLineService.java
 */

public interface IEnforceService {

    int appDistributeProc(EnfProcVO enfProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO) throws Exception;
   
    int appAcceptProc(EnfProcVO enfProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO, OwnDeptVO ownDeptVO) throws Exception;

    int enfApprovalProc(EnfProcVO enfProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO, OwnDeptVO ownDeptVO, EnfLineVO enfLineVO) throws Exception;
    
    int enfApprovalProcCallApprWait(EnfProcVO enfProcVO, EnfDocVO enfDocVO, EnfLineVO enfLineVO) throws Exception;
    
    int updateBodyFileInfo( FileVO fileVO) throws Exception;
    
    int moveSendDoc(EnfDocVO enfDocVO, EnfProcVO enfProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO ) throws Exception;

    int appendDistribute(EnfProcVO enfProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO ) throws Exception;

    int returnSendDoc(EnfProcVO enfProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO, AppDocVO appDocVO) throws Exception;

    int reDistRequest(EnfProcVO enfProcVO, EnfDocVO enfDocVO, EnfRecvVO enfRecvVO) throws Exception;

    int noDistribute(EnfProcVO enfProcVO, EnfRecvVO enfRecvVO) throws Exception;
    	
    int distributeWithdraw(EnfProcVO enfProcVO, EnfRecvVO enfRecvVO) throws Exception;
    
    int reSendRequest(EnfProcVO enfProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO, AppDocVO appDocVO) throws Exception;
    
    int saveEnfDocInfo(EnfDocVO enfDocVO, DocHisVO docHisVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  문서책임자 완료문서 수정
     * </pre>
     * @param enfDocVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    int saveChargeEnfDocInfo(EnfDocVO enfDocVO, DocHisVO docHisVO) throws Exception;
    
    /**
     * 접수문서 조회
     */
    public EnfDocVO selectEnfDoc(EnfDocVO enfDocVO) throws Exception;
    
    boolean checkDupAccept(EnfRecvVO enfRecvVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  해당 docId의 수신자 갯수 반환
     * </pre>
     * @param compId
     * @param docId
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int totEnfRecvCnt(String compId, String docId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  해당 수신자 한건 삭제 및 
     * </pre>
     * @param enfRecvVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    public ResultVO deleteEnfRecv(EnfRecvVO enfRecvVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  수신자 삭제에 따른 문서 및 파일정보 삭제
     * </pre>
     * @param docId
     * @param originCompId
     * @return
     * @throws Exception
     * @see  
     *
     */
    public int deleteEnfRecvAll(String docId, String originCompId) throws Exception;
    
   
}