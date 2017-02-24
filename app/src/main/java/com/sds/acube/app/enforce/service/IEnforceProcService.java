package com.sds.acube.app.enforce.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.appcom.vo.EnfProcVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.SendProcVO;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;



/**
 * Class Name : IEnfLineService.java <br>
 * Description : 접수, 반송, 이송 등의 프로세스 일괄 처리 <br>
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

public interface IEnforceProcService {
    
	EnfDocVO selectEnfDoc(Map<String, String> searchMap) throws Exception;

	List<EnfRecvVO> selectEnfRecv(Map<String, String> searchMap) throws Exception;
	
	List<EnfRecvVO> selectEnfRecvUser(Map<String, String> searchMap) throws Exception;
	
	List<EnfRecvVO> selectEnfRecvDeptRecv(Map<String, String> searchMap) throws Exception;

	List<EnfRecvVO> selectEnfRecvDeptRef(Map<String, String> searchMap) throws Exception;
	
	List<EnfRecvVO> selectEnfRecvOrder(Map<String, String> searchMap) throws Exception;
	
	EnfRecvVO selectEnfRecvMinReceiverOrder(EnfRecvVO enfRecvVO) throws Exception;

	EnfRecvVO selectEnfRecvMaxReceiverOrder(EnfRecvVO enfRecvVO) throws Exception;
		
	int setEnfProc(EnfProcVO enfProcVO) throws Exception;
	
	int modiEnfProc(EnfDocVO enfDocVO) throws Exception; 
	
	int setEnfDocDup(EnfDocVO enfDocVO) throws Exception;
	
	int setEnfDocDist(EnfDocVO enfDocVO) throws Exception;
	
    	int updateAppRecvReader(AppRecvVO appRecvVO) throws Exception;
    	
    	int updateEnfDocReader(Map<String, String> modifyMap) throws Exception;

    	int updateEnfRecvReader(EnfRecvVO enfRecvVO) throws Exception;
    	
    	int setAppRecvAccept(AppRecvVO appRecvVO) throws Exception;

    	int setAppRecvDist(AppRecvVO appRecvVO) throws Exception;
    	
    	int setEnfRecvAddAccept(EnfRecvVO enfRecvVO) throws Exception;

    	int setEnfRecvAddDist(EnfRecvVO enfRecvVO) throws Exception;

    	int setEnfRecvDelAccept(EnfRecvVO enfRecvVO) throws Exception;
    	
    	int setEnfDelDistributeAccept(EnfDocVO enfDocVO) throws Exception;
    	
    	int setEnfDelDistributeFileInfo(EnfDocVO enfDocVO) throws Exception;
    	
    	int setFileInfoDupAccept(EnfDocVO enfDocVO) throws Exception;	

    	int setFileInfoDupDist(EnfDocVO enfDocVO) throws Exception;	
    	
    	int setFileInfoDistToSub(EnfDocVO enfDocVO) throws Exception;
    	
    	int setEnfDocAfterAccept(EnfDocVO enfDocVO) throws Exception;
    	
    	int setEnfDocDistToSub(EnfDocVO enfDocVO) throws Exception;

    	int setEnfRecvAddDistToSub(EnfRecvVO enfRecvVO) throws Exception;

    	int setEnfDocDupDistOnSub(EnfDocVO enfDocVO) throws Exception;

    	int updateEnfRecvDistOnSub(EnfRecvVO enfRecvVO) throws Exception;

    	int updateEnfDocDistOnSub(EnfDocVO enfDocVO) throws Exception;

    	int setFileInfoDelDistOnSub(EnfDocVO enfDocVO) throws Exception;

    	int setFileInfoAfterAccept(EnfDocVO enfDocVO) throws Exception;
    	
    	int setOwnDept(OwnDeptVO ownDeptVO) throws Exception;
    	
    	int setAppRecvMove(AppRecvVO appRecvVO) throws Exception;

    	int setEnfDocMove(EnfDocVO enfDocVO) throws Exception;

    	int setAppRecvReturn(AppRecvVO appRecvVO) throws Exception;
    	
    	int setEnfRecvMove(EnfRecvVO enfRecvVO) throws Exception;

    	int setEnfRecvDel(EnfRecvVO enfRecvVO) throws Exception;
    	
    	int setEnfDocReturn(EnfDocVO enfDocVO) throws Exception;

    	int setFileInfoReturn(EnfDocVO enfDocVO) throws Exception;
    	
    	int updateAppDocSendReturn(AppDocVO appDocVO) throws Exception;
	
    	int updateBodyFileInfo( FileVO fileVO) throws Exception;
    	
    	SendProcVO selectLastSendOpinion(SendProcVO sendProcVO) throws Exception;
    	
    	EnfProcVO selectLastEnfOpinion(EnfProcVO enfProcVO) throws Exception;
    	
    	int setAppRecvReSendRequest(AppRecvVO appRecvVO) throws Exception;   	

    	int setEnfDocState(EnfDocVO enfDocVO) throws Exception;
    	
    	int setEnfRecvState(EnfRecvVO enfRecvVO) throws Exception;

    	int setEnfProcDel(EnfProcVO enfProcVO) throws Exception;

    	int setEnfDocDelete(EnfDocVO enfDocVO) throws Exception;
    	
    	int setEnfRecvAddReDist(EnfRecvVO enfRecvVO) throws Exception;

    	int initEnfDocAttachCount(Map<String, String> modifyMap) throws Exception;
	
    	List<EnfRecvVO> getRecvList(Map<String, String> searchMap) throws Exception;

    	List<EnfRecvVO> selectEnableDistributeWithdraw(EnfRecvVO enfRecvVO) throws Exception;

    	List<EnfDocVO> selectEnfDocDistToSub(EnfDocVO enfDocVO) throws Exception;
    	
    	int updateEnfDocDistToSub(EnfDocVO enfDocVO) throws Exception;
    	
    	List<EnfRecvVO> selectEnableReturnOnDist(EnfRecvVO enfRecvVO) throws Exception;

}