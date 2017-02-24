/** 
 *  Class Name  : SendProcService.java <br>
 *  Description : 공지사항 관련 클래스 <br>
 *  Modification Information <br>
 *  <br>
 *  수 정 일 : 2015. 09.11 <br>
 *  수 정 자 : jyd <br>
 *  수정내용 : 최초작성 <br>
 * 
 *  @author jyd 
 *  @since 2015. 09.11 
 *  @version 1.0 
 *  @see  com.sds.acube.app.notice.service.impl.NoticeService
 */ 
package com.sds.acube.app.mig.service.impl;

import java.util.List;
import java.util.Map;

import org.anyframe.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.mig.service.IMigService;
import com.sds.acube.app.mig.vo.ApprInfoVO;
import com.sds.acube.app.mig.vo.ApproverInfoVO;
import com.sds.acube.app.mig.vo.AttachInfoVO;
import com.sds.acube.app.mig.vo.DeliveresInfoVO;
import com.sds.acube.app.mig.vo.DocInfoVO;
import com.sds.acube.app.mig.vo.DraftInfoVO;
import com.sds.acube.app.mig.vo.MigFileVO;
import com.sds.acube.app.mig.vo.RecipientsInfoVO;
import com.sds.acube.app.mig.vo.RegDocVO;

 
 
/**
 * Class Name  : SendProcService.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.approval.service.impl.SendProcService.java
 */
@SuppressWarnings("serial")
@Service("migService")

@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class MigService extends BaseService  implements IMigService { 
	
		/**
		 */
		@Autowired 
    	private ICommonDAO icommonDao;

    	// 등록대장, 접수대장 마이그레이션 문서정보(DOC_ID, TITLE) 리스트 조회
        public Page getList(RegDocVO regDocVO, int page, int pageSize) throws Exception {
	  	   	return (Page) this.icommonDao.getPagingList("mig.selectRegDoc", regDocVO, page, pageSize);
	    }
		@Override
		public Page getListRecv(RegDocVO regDocVO, int page, int pageSize) throws Exception {
	  	   	return (Page) this.icommonDao.getPagingList("mig.selectRecvDoc", regDocVO, page, pageSize);
		}
        public Page getFileList(MigFileVO migFileVO, int page) throws Exception {
        	return (Page) this.icommonDao.getPagingList("mig.selectMigFiles", migFileVO, page, 6000);
        }
		@Override 
		public RegDocVO getDoc(String docId) {
	  	   	return null;
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<MigFileVO> getMigratedFileList(Map<String, String> map) throws Exception {
			return (List<MigFileVO>) icommonDao.getListMap("mig.selectMigratedFiles", map);
		}
		@Override
		public DocInfoVO getDocInfo(Map<String, String> map) throws Exception {
			return  (DocInfoVO) icommonDao.getMap("mig.selectDocInfo", map);
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<ApprInfoVO> getApprList(Map<String, String> map) throws Exception {
			return  (List<ApprInfoVO>) icommonDao.getListMap("mig.selectApprovalLineInfo", map);
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<ApproverInfoVO> getApproverList(Map<String, String> map) throws Exception {
			return  (List<ApproverInfoVO>) icommonDao.getListMap("mig.selectApproverInfo", map);
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<AttachInfoVO> getAttachList(Map<String, String> map) throws Exception {
			return  (List<AttachInfoVO>) icommonDao.getListMap("mig.selectAttachInfo", map);
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<DeliveresInfoVO> getDeliveresList(Map<String, String> map) throws Exception {
			return  (List<DeliveresInfoVO>) icommonDao.getListMap("mig.selectDeliverersInfo", map);
		}
		@Override
		public DraftInfoVO getDraftInfo(Map<String, String> map) throws Exception {
			return  (DraftInfoVO) icommonDao.getMap("mig.selectDraftInfo", map);
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<RecipientsInfoVO> getRecipientsInfo(Map<String, String> map) throws Exception {
			return  (List<RecipientsInfoVO>) icommonDao.getListMap("mig.selectRecipientsInfo", map);
		}
		@Override
		public DocInfoVO getDocInfoRecv(Map<String, String> map) throws Exception {
			return  (DocInfoVO) icommonDao.getMap("mig.selectDocInfoRecv", map);
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<ApprInfoVO> getApprListRecv(Map<String, String> map) throws Exception {
			return  (List<ApprInfoVO>) icommonDao.getListMap("mig.selectApprovalLineInfoRecv", map);
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<ApproverInfoVO> getApproverListRecv(Map<String, String> map) throws Exception {
			return  (List<ApproverInfoVO>) icommonDao.getListMap("mig.selectApproverInfoRecv", map);
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<AttachInfoVO> getAttachListRecv(Map<String, String> map) throws Exception {
			return  (List<AttachInfoVO>) icommonDao.getListMap("mig.selectAttachInfoRecv", map);
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<DeliveresInfoVO> getDeliveresListRecv(Map<String, String> map) throws Exception {
			return  (List<DeliveresInfoVO>) icommonDao.getListMap("mig.selectDeliverersInfoRecv", map);
		}
		@Override
		public DraftInfoVO getDraftInfoRecv(Map<String, String> map) throws Exception {
			return  (DraftInfoVO) icommonDao.getMap("mig.selectDraftInfoRecv", map);
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<RecipientsInfoVO> getRecipientsInfoRecv(Map<String, String> map) throws Exception {
			return  (List<RecipientsInfoVO>) icommonDao.getListMap("mig.selectRecipientsInfoRecv", map);
		}
		@Override
		public List<MigFileVO> getCabFileList(Map<String, String> map) throws Exception {
	  	   	return (List<MigFileVO>) this.icommonDao.getListMap("cab.selectMigFiles", map);
		}
		
		@Override
		public Page getCabList(RegDocVO regDocVO, int page, int pageSize) throws Exception {
	  	   	return (Page) this.icommonDao.getPagingList("cab.selectCabDoc", regDocVO, page, pageSize);
		}
		@Override
		public int updateBoardContent(Map<String, String> map) throws Exception {
			return icommonDao.modifyMap("cab.updateBoardContent", map);
		}
		@Override
		public List<MigFileVO> isDuplicate(Map<String, String> map) throws Exception {
			return icommonDao.getListMap("cab.selectMigFileOne", map);
		}
		
}
