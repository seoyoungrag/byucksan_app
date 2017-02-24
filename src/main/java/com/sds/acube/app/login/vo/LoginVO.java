/*
 * @(#) LoginVO.java 2010. 5. 25.
 * Copyright (c) 2010 Samsung SDS Co., Ltd. All Rights Reserved.
 */
package com.sds.acube.app.login.vo;


/** * @author   Alex, Eum * @version  $Revision: 1.1.4.1 $ $Date: 2010/08/31 23:44:24 $ */
public class LoginVO {
	/**	 */	private String loginId;
	/**	 */	private String password;
	/**	 */	private String language;
	/**	 */	private String loginIp;
	
	/**	 * @return  Returns the language.	 */
	public String getLanguage() {
		return language;
	}

	
	/**	 * @param language  The language to set.	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**	 * @return  Returns the loginId.	 */
	public String getLoginId() {
		return loginId;
	}
	
	/**	 * @param loginId  The loginId to set.	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	/**	 * @return  Returns the password.	 */
	public String getPassword() {
		return password;
	}
	
	/**	 * @param password  The password to set.	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**	 * <pre>  설명 </pre>	 * @return	 * @see   	 */	public String getLoginIp() {
		return loginIp;
	}

	/**	 * <pre>  설명 </pre>	 * @param loginIp	 * @see   	 */	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}	/* (non-Javadoc)	 * @see java.lang.Object#toString()	 */	@Override	public String toString() {		return "LoginVO [language=" + language + ", loginId=" + loginId				+ ", loginIp=" + loginIp + ", password=" + password + "]";	}
}
