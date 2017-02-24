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
package com.sds.acube.app.notice.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.anyframe.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.notice.service.INoticeService;
import com.sds.acube.app.notice.vo.NoticeSendOrgVO;
import com.sds.acube.app.notice.vo.NoticeVO;

 
 
/**
 * Class Name  : SendProcService.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.approval.service.impl.SendProcService.java
 */
@SuppressWarnings("serial")
@Service("noticeService")

@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class NoticeService extends BaseService  implements INoticeService { 
	
		/**
		 */
		@Autowired 
    	private ICommonDAO icommonDao;

    	// 공지사항 리스트 조회
        @SuppressWarnings("unchecked")
        public Page getList(NoticeVO noticeVO, int page) throws Exception {
	  	   	return (Page) this.icommonDao.getPagingList("notice.SelectNotice", noticeVO, page);
	    }
        
        public NoticeVO getNotice(NoticeVO noticeVO) throws Exception {
	  	   	return (NoticeVO) this.icommonDao.get("notice.getNotice", noticeVO);
	    }
        
        public int insert(NoticeVO noticeVO) throws Exception {
        	return this.icommonDao.insert("notice.insert", noticeVO);
        }
        public int insertso(NoticeSendOrgVO noticesoVO) throws Exception {
        	return this.icommonDao.insert("noticeso.insert", noticesoVO);
        }
        
        public int update(NoticeVO noticeVO) throws Exception {
        	return this.icommonDao.modify("notice.update", noticeVO);
        }
        
        public int delete(String reportNos) throws Exception {
        	Map<String, String> param = new HashMap<String, String>();
        	param.put("reportNos", reportNos);
        	return this.icommonDao.delete("notice.delete", param);
        }
        
        public int deleteso(String reportNos) throws Exception {
        	Map<String, String> param = new HashMap<String, String>();
        	param.put("reportNos", reportNos);
        	return this.icommonDao.delete("noticeso.delete", param);
        }
}
