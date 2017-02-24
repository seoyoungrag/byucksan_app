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
 *  @see  com.sds.acube.app.memo.service.impl.NoticeService
 */ 
package com.sds.acube.app.memo.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.anyframe.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.memo.service.IMemoService;
import com.sds.acube.app.memo.vo.MemoVO;

 
 
/**
 * Class Name  : SendProcService.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.approval.service.impl.SendProcService.java
 */
@SuppressWarnings("serial")
@Service("memoService")

@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class MemoService extends BaseService  implements IMemoService { 
	
		/**
		 */
		@Autowired 
    	private ICommonDAO icommonDao;

    	// 쪽지 리스트 조회
        @SuppressWarnings("unchecked")
        public Page list(Map<String, String> param, int page) throws Exception {
	  	   	return (Page) this.icommonDao.getPagingList("memo.list", param, page);
	    }
             
        // 쪽지 리스트 조회
        @SuppressWarnings("unchecked")
        public Page list(Map<String, String> param, int page, int pageSize) throws Exception {
	  	   	return (Page) this.icommonDao.getPagingList("memo.list", param, page, pageSize);
	    }
        
        public MemoVO get(Map<String, String> param) throws Exception {
	  	   	return (MemoVO) this.icommonDao.get("memo.get", param);
	    }
        
        public int insert(MemoVO memoVo) throws Exception {
        	return this.icommonDao.insert("memo.insert", memoVo);
        }
        
        public int update(MemoVO memoVo) throws Exception {
        	return this.icommonDao.modify("memo.update", memoVo);
        }
        
        public int updateReadDate(MemoVO memoVo) throws Exception {
        	return this.icommonDao.modify("memo.update.readDate", memoVo);
        }
        
        public int delete(String ids) throws Exception {
        	Map<String, String> param = new HashMap<String, String>();
        	param.put("ids", ids);
        	return this.icommonDao.delete("memo.delete", param);
        }
}
