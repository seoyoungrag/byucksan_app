/** 
 *  Class Name  : ISendProcService.java <br>
 *  Description : 발송처리개별서비스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 21. <br>
 *  수 정  자 : 정태환  <br>
 *  수정 내용 : 최초작성<br>
 * 
 *  @author  정태환 
 *  @since 2011. 3. 21.
 *  @version 1.0 
 *  @see  com.sds.acube.app.approval.service.ISendProcService.java
 */

package com.sds.acube.app.approval.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.SendInfoVO;
import com.sds.acube.app.appcom.vo.SendProcVO;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.StampListVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;

public interface ISendProcService {
    
	int setSendProc(SendProcVO sendProcVO) throws Exception;
	
    	int setAppRecv(AppRecvVO appRecvVO) throws Exception;
    	
    	int setEnfRecv(EnfRecvVO enfRecvVO) throws Exception;
    	
    	int setEnfDoc(EnfDocVO enfDocVO) throws Exception;

    	int setStampList(StampListVO stampListVO) throws Exception;
    	
        int setSendInfoSealType(StampListVO stampListVO) throws Exception;
        
        StampListVO getStampListInfo(StampListVO stampListVO) throws Exception;
        
        SendInfoVO getStampList(StampListVO stampListVO) throws Exception;

    	int setStampSendDate(StampListVO stampListVO) throws Exception;
    	
    	int setFileInfo(FileVO fileVO) throws Exception;

    	int setStampFileInfo(FileVO fileVO) throws Exception;

    	int updateAppDocSendComplete(AppDocVO appDocVO) throws Exception;

    	int setAppRecvStopSend(AppRecvVO appRecvVO) throws Exception;
    	
    	int setAppRecvReSend(AppRecvVO appRecvVO) throws Exception;
    	
    	int setEnfRecvReSend(EnfRecvVO enfRecvVO) throws Exception;
    	
    	int setEnfDocReSend(EnfDocVO enfDocVO) throws Exception;
    	
    	int deleteFileInfoReSend(FileVO fileVO) throws Exception;

    	int setFileInfoReSend(FileVO fileVO) throws Exception;
    	
    	int updateAppDocReSend(AppDocVO appDocVO) throws Exception;
    	
    	int setSendInfoSend(SendInfoVO sendInfoVO) throws Exception;

    	int setSendInfoAppendSend(SendInfoVO sendInfoVO) throws Exception;
    	
    	@SuppressWarnings("unchecked")
        public int insertAppRecv(List appRecvVOs) throws Exception;
    	
    	int setAppRecvCancel(AppRecvVO appRecvVO) throws Exception;
    	
    	int setEnfRecvCancel(EnfRecvVO enfRecvVO) throws Exception;
    	
    	int setEnfDocCancel(EnfDocVO enfDocVO) throws Exception;

    	int setFileInfoCancel(FileVO fileVO) throws Exception;
    	
    	int updateAppDocSendCancel(AppDocVO appDocVO) throws Exception;

    	List<AppRecvVO> getAppSendInfo(Map<String, String> searchMap) throws Exception;

    	int updateAppDocTransferCall(AppDocVO appDocVO) throws Exception;

    	int updateAppDocRejectStamp(AppDocVO appDocVO) throws Exception;
    	
    	AppDocVO getAppDocSendChk(AppDocVO appDocVO) throws Exception;
    	
    	SendProcVO getFirstSendProc(SendProcVO sendProcVO) throws Exception;
    	
    	SendProcVO getLastSendProc(SendProcVO sendProcVO) throws Exception;
    	
    	FileVO listStampAttach(FileVO fileVO) throws Exception;
    	
    	List<DocHisVO> selectDelFileInfo(DocHisVO docHisVO) throws Exception;
    	
    	int deleteFileHis(FileHisVO fileHisVO) throws Exception;
    	
    	int deleteDocHis(DocHisVO DocHisVO) throws Exception;
    	
    	int deleteFileInfo(FileHisVO fileHisVO) throws Exception;
    	
    	int selFileHisInFileInfo (FileHisVO fileHisVO) throws Exception;
    
}
 