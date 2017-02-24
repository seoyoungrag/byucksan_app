package com.sds.acube.app.appcom.vo;

/**
 * Class Name  : ProxyDeptVO.java <br> Description : 대리처리부서 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  ProxyDeptVO
 */ 
public class ProxyDeptVO {
	
	/**
	 * 대리처리부서ID
	 */
	private String proxyDeptId;
	/**
	 * 대리처리부서명
	 */
	private String proxyDeptName;
	
	/**
	 * @param proxyDeptId  the proxyDeptId to set
	 */
        public void setProxyDeptId(String proxyDeptId) {
	    this.proxyDeptId = proxyDeptId;
        }
	/**
	 * @return  the proxyDeptId
	 */
        public String getProxyDeptId() {
	    return proxyDeptId;
        }
	/**
	 * @param proxyDeptName  the proxyDeptName to set
	 */
        public void setProxyDeptName(String proxyDeptName) {
	    this.proxyDeptName = proxyDeptName;
        }
	/**
	 * @return  the proxyDeptName
	 */
        public String getProxyDeptName() {
	    return proxyDeptName;
        }

}
