/** 
 *  Class Name  : SendProcService.java <br>
 *  Description : 발송처리 클래스 <br>
 *  Modification Information <br>
 *  <br>
 *  수 정 일 : 2011. 03.21 <br>
 *  수 정 자 : 정태환 <br>
 *  수정내용 : 최초작성 <br>
 * 
 *  @author  정태환 
 *  @since 2011. 3. 21 
 *  @version 1.0 
 *  @see  com.sds.acube.app.approval.service.impl.SendProcService
 */ 
package com.sds.acube.app.approval.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.approval.service.ISendProcService;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.SendInfoVO;
import com.sds.acube.app.appcom.vo.SendProcVO;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.StampListVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;
 
 
/**
 * Class Name  : SendProcService.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.approval.service.impl.SendProcService.java
 */
@SuppressWarnings("serial")
@Service("SendProcService")

@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class SendProcService extends BaseService  implements ISendProcService { 
	
	/**
	 */
	@Autowired 
    	private ICommonDAO icommonDao;


    	//발송처리이력
    	public int setSendProc(SendProcVO sendProcVO) throws Exception {
    	    return this.icommonDao.insert("enforceSend.insertSendProc", sendProcVO);
    	}
    	//생산문서수신자정보업데이트
    	public int setAppRecv(AppRecvVO appRecvVO) throws Exception {
    	    return this.icommonDao.modify("enforceSend.updateAppRecv", appRecvVO);
    	}

    	//접수문서수신자정보
    	public int setEnfRecv(EnfRecvVO enfRecvVO) throws Exception {
    	    return this.icommonDao.insert("enforceSend.insertEnfRecv", enfRecvVO);
    	}

    	//접수문서정보
    	public int setEnfDoc(EnfDocVO enfDocVO) throws Exception {
    	    return this.icommonDao.insert("enforceSend.insertEnfDoc", enfDocVO);
    	}
    	
    	//직인날인대장 저장
    	public int setStampList(StampListVO stampListVO) throws Exception {
    	    return this.icommonDao.insert("enforceSend.insertStampList", stampListVO);
    	}

    	//발송정보 날인유형 저장
    	public int setSendInfoSealType(StampListVO stampListVO) throws Exception {
    	    return this.icommonDao.modify("enforceSend.updateSendInfoSealType", stampListVO);
    	}
    	
    	//직인날인대장정보 조회
    	public StampListVO getStampListInfo(StampListVO stampListVO) throws Exception {
    	    return (StampListVO) this.icommonDao.get("enforceSend.selectStampListInfo", stampListVO);
    	}
    	
    	//직인날인여부 조회
    	public SendInfoVO getStampList(StampListVO stampListVO) throws Exception {
    	    return (SendInfoVO) this.icommonDao.get("enforceSend.selectStampChk", stampListVO);
    	}

    	//직인날인대장 시행일자 업데이트
    	public int setStampSendDate(StampListVO stampListVO) throws Exception {
    	    return this.icommonDao.insert("enforceSend.updateStampSendDate", stampListVO);
    	}
    	
    	//관인파일정보저장
    	public int setStampFileInfo(FileVO fileVO) throws Exception {
    	    return this.icommonDao.insert("enforceSend.insertStampFileInfo", fileVO);
    	}

    	//접수파일정보저장
    	public int setFileInfo(FileVO fileVO) throws Exception {
    	    return this.icommonDao.insert("enforceSend.insertEnfFileInfo", fileVO);
    	}

    	//생산문서정보저장
    	public int updateAppDocSendComplete(AppDocVO appDocVO) throws Exception {
    	    return this.icommonDao.modify("enforceSend.updateAppDocState", appDocVO);
    	}

    	//생산문서수신자정보업데이트(발송종료)
    	public int setAppRecvStopSend(AppRecvVO appRecvVO) throws Exception {
    	    return this.icommonDao.modify("enforceSend.updateAppRecvStopSend", appRecvVO);
    	}
    	
    	//생산문서수신자정보업데이트(재발송)
    	public int setAppRecvReSend(AppRecvVO appRecvVO) throws Exception {
    	    return this.icommonDao.modify("enforceSend.updateAppRecvReSend", appRecvVO);
    	}

    	//접수문서수신자정보(재발송)
    	public int setEnfRecvReSend(EnfRecvVO enfRecvVO) throws Exception {
    	    return this.icommonDao.insert("enforceSend.insertEnfRecvReSend", enfRecvVO);
    	}

    	//접수문서정보(재발송)
    	public int setEnfDocReSend(EnfDocVO enfDocVO) throws Exception {
    	    return this.icommonDao.insert("enforceSend.insertEnfDocReSend", enfDocVO);
    	}

    	//접수파일정보삭제(재발송)
    	public int deleteFileInfoReSend(FileVO fileVO) throws Exception {
    	    return this.icommonDao.insert("enforceSend.deleteEnfFileInfoReSend", fileVO);
    	}

    	//접수파일정보저장(재발송)
    	public int setFileInfoReSend(FileVO fileVO) throws Exception {
    	    return this.icommonDao.insert("enforceSend.insertEnfFileInfoReSend", fileVO);
    	}

    	//생산문서정보저장(재발송)
    	public int updateAppDocReSend(AppDocVO appDocVO) throws Exception {
    	    return this.icommonDao.modify("enforceSend.updateAppDocReSend", appDocVO);
    	}
    	
    	//발송정보 변경(발송)
    	public int setSendInfoSend(SendInfoVO sendInfoVO) throws Exception {
    	    return this.icommonDao.modify("appcom.updateSendInfoSend", sendInfoVO);
    	}
    	
    	//발송정보 변경(추가발송)
    	public int setSendInfoAppendSend(SendInfoVO sendInfoVO) throws Exception {
    	    return this.icommonDao.modify("appcom.updateSendInfoAppendSend", sendInfoVO);
    	}
    	
        // 수신자 Insert
    	@SuppressWarnings("unchecked")
        public int insertAppRecv(List appRecvVOs) throws Exception {
    	return this.icommonDao.insertList("approval.insertAppRecv", appRecvVOs);
        }
        
    	
    	//생산문서수신자정보변경(발송회수)
    	public int setAppRecvCancel(AppRecvVO appRecvVO) throws Exception {
    	    return this.icommonDao.modify("enforceSend.updateAppRecvCancel", appRecvVO);
    	}

    	//접수문서수신자정보삭제(발송회수)
    	public int setEnfRecvCancel(EnfRecvVO enfRecvVO) throws Exception {
    	    return this.icommonDao.delete("enforceSend.deleteEnfRecvCancel", enfRecvVO);
    	}

    	//접수문서정보삭제(발송회수)
    	public int setEnfDocCancel(EnfDocVO enfDocVO) throws Exception {
    	    return this.icommonDao.delete("enforceSend.deleteEnfDocCancel", enfDocVO);
    	}
    	
    	//파일정보삭제(발송회수)
    	public int setFileInfoCancel(FileVO fileVO) throws Exception {
    	    return this.icommonDao.delete("enforceSend.deleteFileInfoCancel", fileVO);
    	}

    	//생산문서정보변경(발송회수)
    	public int updateAppDocSendCancel(AppDocVO appDocVO) throws Exception {
    	    return this.icommonDao.modify("enforceSend.updateAppDocSendCancel", appDocVO);
    	}
    	
    	
    	
    	//발송정보(접수확인)
        @SuppressWarnings("unchecked")
        public List<AppRecvVO> getAppSendInfo(Map<String, String> searchMap) throws Exception {
    	    return (List<AppRecvVO>) this.icommonDao.getListMap("enforceSend.selectAppRecv", searchMap);
    	    
    	}
    	
    

    	//생산문서정보변경(발송의뢰/심사의뢰)
    	public int updateAppDocTransferCall(AppDocVO appDocVO) throws Exception {
    	    return this.icommonDao.modify("enforceSend.updateAppDocState", appDocVO);
    	}    	

    	//생산문서정보변경(심사반려)
    	public int updateAppDocRejectStamp(AppDocVO appDocVO) throws Exception {
    	    return this.icommonDao.modify("enforceSend.updateAppDocState", appDocVO);
    	}    	
    	
    	
    	//발송여부체크
    	public AppDocVO getAppDocSendChk(AppDocVO appDocVO) throws Exception {
    	    return (AppDocVO) this.icommonDao.get("enforceSend.selectAppDocSendChk", appDocVO);
    	}    	
    	
    	//최초발송이력 조회
    	public SendProcVO getFirstSendProc(SendProcVO sendProcVO) throws Exception {
    	    return (SendProcVO) this.icommonDao.get("enforceSend.selectFirstSendProc", sendProcVO);
    	} 
    	
    	//최종발송이력 조회
    	public SendProcVO getLastSendProc(SendProcVO sendProcVO) throws Exception {
    	    return (SendProcVO) this.icommonDao.get("enforceSend.selectLastSendProc", sendProcVO);
    	}  
    	
    	// 날인파일정보 조회(직인 등)
        public FileVO listStampAttach(FileVO fileVO) throws Exception {
            return (FileVO) this.icommonDao.get("enforceSend.listStampFile", fileVO);
        }
        
        // 삭제할 첨부날인 정보 확인
        @SuppressWarnings("unchecked")
        public List<DocHisVO> selectDelFileInfo(DocHisVO docHisVO)throws Exception {
            List<DocHisVO> docHisVOs = new ArrayList<DocHisVO>();

            docHisVOs =  this.icommonDao.getList("enforceSend.selectDelFileInfo", docHisVO);

            return docHisVOs;
        }
    	
        // fileHis 삭제
        public int deleteFileHis(FileHisVO fileHisVO) throws Exception {
            return this.icommonDao.delete("enforceSend.deleteFileHis", fileHisVO);
        }
        
        //docHis 삭제
        public int deleteDocHis(DocHisVO docHisVO)throws Exception {
            return this.icommonDao.delete("enforceSend.deleteDocHis", docHisVO);
        }
        
        // fileInfo 삭제
        public int deleteFileInfo(FileHisVO fileHisVO) throws Exception {
            return this.icommonDao.delete("enforceSend.deleteFileInfo", fileHisVO);
        }
        
        // 최근 fileHis의 내역 select, fileInfo insert
        public int selFileHisInFileInfo (FileHisVO fileHisVO) throws Exception{
            return this.icommonDao.insert("enforceSend.selFileHisInFileInfo", fileHisVO);
        }
 
  
}
