/**
 * 
 */
package com.sds.acube.app.common.service;

/** 
 *  Class Name  : IDrmService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 25. <br>
 *  수 정  자 : Timothy  <br>
 *  수정내용 :  <br>
 * 
 *  @author  Timothy 
 *  @since 2011. 3. 25.
 *  @version 1.0 
 *  @see  com.sds.acube.app.common.service.impl.IDrmService.java
 */

public interface IDrmService {

	/**
	 * 첨부파일 업로드 처리를 한다. 
	 * @param farr	파일정보 배열
	 * @throws Exception 
	 */
	public void processUpload(String[][] farr) throws Exception;
	
	/**
	 * 첨부파일 다운로드 처리를 한다. 
	 * @param farr	파일정보 배열
	 * @throws Exception 
	 */
	public void processDownload(String[][] farr) throws Exception;
}
