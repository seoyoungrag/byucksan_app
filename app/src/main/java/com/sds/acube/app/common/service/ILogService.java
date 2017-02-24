package com.sds.acube.app.common.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.common.vo.DocHisVO;

/** 
 *  Class Name  : ILogService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 4. 8. <br>
 *  수 정  자 : Timothy  <br>
 *  수정내용 :  <br>
 * 
 *  @author  Timothy 
 *  @since 2011. 4. 8.
 *  @version 1.0 
 *  @see  com.sds.acube.app.common.service.ILogService.java
 */

public interface ILogService {
	public int insertDocHis(DocHisVO docHisVO) throws Exception;
	public int updateDocHis(Map<String, String> map) throws Exception;
	public List<DocHisVO> listDocHis(Map<String, String> map) throws Exception;
	public List<DocHisVO> selectDocHis(DocHisVO docHisVO) throws Exception;
}
