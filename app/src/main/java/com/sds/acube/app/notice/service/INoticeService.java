/**
 * 
 */
package com.sds.acube.app.notice.service;

import org.anyframe.pagination.Page;

import com.sds.acube.app.notice.vo.NoticeSendOrgVO;
import com.sds.acube.app.notice.vo.NoticeVO;


/** 
 *  Class Name  : INoticeService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2015. 9. 11. <br>
 *  수 정  자 : Administrator  <br>
 *  수정내용 :  <br>
 * 
 *  @author  Administrator 
 *  @since 2015. 9. 11.
 *  @version 1.0 
 *  @see  com.sds.acube.app.notice.service.INoticeService.java
 */

public interface INoticeService { 
	public Page getList(NoticeVO noticeVO, int page) throws Exception ;
	public NoticeVO getNotice(NoticeVO noticeVO) throws Exception ;
	public int insert(NoticeVO noticeVO) throws Exception;
	public int insertso(NoticeSendOrgVO noticesoVO) throws Exception;
    public int update(NoticeVO noticeVO) throws Exception;
    public int delete(String reportNos) throws Exception;
    public int deleteso(String reportNos) throws Exception;
}
