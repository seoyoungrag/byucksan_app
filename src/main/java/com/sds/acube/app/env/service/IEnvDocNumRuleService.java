package com.sds.acube.app.env.service;

/** 
 *  Class Name  : IEnvDocNumRuleService.java <br>
 *  Description : 문서번호 생성하기  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2012. 5. 2. <br>
 *  수 정  자 : jd.park  <br>
 *  수정내용 :  <br>
 * 
 *  @author  jd.park 
 *  @since 2012. 5. 2.
 *  @version 1.0 
 *  @see  com.sds.acube.app.env.service.IEnvDocNumRuleService.java
 */

public interface IEnvDocNumRuleService {
	
	/**
	 * 
	 * <pre> 
	 *  문서번호 생성규칙에 따라 문서번호 생성
	 * </pre>
	 * @param deptId
	 * @param compId
	 * @param classNumber
	 * @return
	 * @throws Exception
	 * @see  
	 *
	 */
	public String getDocNum(String deptId, String compId, String classNumber) throws Exception;
}
