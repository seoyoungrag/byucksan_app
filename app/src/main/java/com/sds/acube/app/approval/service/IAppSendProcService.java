/**
 * 
 */
package com.sds.acube.app.approval.service;

import java.util.HashMap;
import java.util.List;


import java.util.Map;

import com.sds.acube.app.idir.org.orginfo.OrgImage;
import com.sds.acube.app.appcom.vo.EnfProcVO;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.SendProcVO;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.StampListVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;
 
/** 
 *  Class Name  : IAppSendProcService.java <br>
 *  Description : 발송일괄처리  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 23. <br>
 *  수 정  자 : 정태환  <br>
 *  수정내용 :  <br>
 * 
 *  @author  정태환 
 *  @since 2011. 3. 23.
 *  @version 1.0 
 *  @see  com.sds.acube.app.approval.service.IAppSendProcService.java
 */

public interface IAppSendProcService {

    int appSendProc(String method, SendProcVO sendProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, StampListVO stampListVO, FileVO fileVO, AppDocVO appDocVO) throws Exception;

    int appReSendProc(SendProcVO sendProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO, AppDocVO appDocVO) throws Exception;
       
    int appPubReSendProc(SendProcVO sendProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO, AppDocVO appDocVO) throws Exception;

    int saveToHistory(DocHisVO docHisVO, FileHisVO fileHisVO)throws Exception;
    
    int appAppendSendProc(List<AppRecvVO> appAppendRecvVO, SendProcVO sendProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO, AppDocVO appDocVO) throws Exception;
    
    int appPubAppendSendProc(List<AppRecvVO> appAppendRecvVO, SendProcVO sendProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO, AppDocVO appDocVO, boolean inAppendExist) throws Exception;
    
    int appSendCancel(SendProcVO sendProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO, AppDocVO appDocVO) throws Exception;
    
    List<AppRecvVO> getAppSendInfo(AppRecvVO appRecvVO) throws Exception;
    
    int stampToDoc(StampListVO stampListVO,  FileVO fileVO ) throws Exception;
    
    int updateSendInfoSealType(StampListVO stampListVO) throws Exception;  
 
    int stampToDocChk(StampListVO stampListVO ) throws Exception;
    
    int sendEnforceChk(AppDocVO appDocVO ) throws Exception;
    
    int transferCall(SendProcVO sendProcVO, AppDocVO appDocVO)throws Exception;

    int rejectStamp(SendProcVO sendProcVO, AppDocVO appDocVO, DocHisVO docHisVO)throws Exception;

    int noSend(SendProcVO sendProcVO, AppDocVO appDocVO)throws Exception;

    int stopSend(SendProcVO sendProcVO, AppDocVO appDocVO, AppRecvVO appRecvVO)throws Exception;

    int enableSend(SendProcVO sendProcVO, AppDocVO appDocVO)throws Exception;
    
    List<OrgImage> selectOrgSealList(String deptId, int sealType) throws Exception;
	
    FileVO selectOrgSeal(String compId, String sealId) throws Exception;

    List<EnfProcVO> getProcInfo(EnfProcVO enfProcVO) throws Exception;
    
    List<EnfProcVO> getProcInfoForDist(EnfProcVO enfProcVO) throws Exception;

    SendProcVO getLastProcInfo(SendProcVO sendProcVO) throws Exception;
    
    SendProcVO getLastrejectStampInfo(SendProcVO sendProcVO) throws Exception;
    
    /**
     * 
     * <pre> 
     *  반려 여부 체크
     * </pre>
     * @param compId
     * @param docId
     * @return
     * @throws Exception
     * @see  
     *
     */
    int rejectStampChk(String compId, String docId) throws Exception;

	/**
	 * <pre> 
	 *  설명
	 * </pre>
	 * @param map
	 * @return
	 * @throws Exception 
	 * @see  
	 * */ 
	
	EnfDocVO selectEnfDocForApproval(Map<String, String> map) throws Exception;
   
}

