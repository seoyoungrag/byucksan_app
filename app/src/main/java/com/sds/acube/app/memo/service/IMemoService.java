/**
 * 
 */
package com.sds.acube.app.memo.service;

import java.util.Map;

import org.anyframe.pagination.Page;

import com.sds.acube.app.memo.vo.MemoVO;


/** 
 *  Class Name  : IMemoService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2015. 9. 17. <br>
 *  수 정  자 : Administrator  <br>
 *  수정내용 :  <br>
 * 
 *  @author  Administrator 
 *  @since 2015. 9. 17.
 *  @version 1.0 
 *  @see  com.sds.acube.app.memo.service.IMemoService.java
 */

public interface IMemoService {
	public Page list(Map<String, String> param, int page) throws Exception ;
	public Page list(Map<String, String> param, int page, int pageSize) throws Exception ;
	public MemoVO get(Map<String, String> param) throws Exception ;
	public int insert(MemoVO memoVo) throws Exception;
    public int update(MemoVO memoVo) throws Exception;
    public int updateReadDate(MemoVO memoVo) throws Exception;
    public int delete(String ids) throws Exception;
}
