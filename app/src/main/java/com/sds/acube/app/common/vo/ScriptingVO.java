/**
 * 
 */
package com.sds.acube.app.common.vo;

import java.util.ArrayList;

/**
 * Class Name  : ScriptingVO.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 3. 22. <br> 수 정  자 : Timothy  <br> 수정내용 :  <br>
 * @author   Timothy 
 * @since  2011. 3. 22.
 * @version  1.0 
 * @see  com.sds.acube.app.common.vo.ScriptingVO.java
 */

public class ScriptingVO {
	/**	scripting arraylist	*/
	private ArrayList<String> scriptings = new ArrayList<String>();
	/**	exceptions	*/
	private ArrayList<ScriptingExceptionVO> exceptions = new ArrayList<ScriptingExceptionVO>();
	/**
	 * isload
	 */
	private boolean isload = false;
			
	/**
	 * 기본 생성자
	 */
	public ScriptingVO() {
	}
	
	/**
	 * scripting 정보를 설정한다.
	 * @param scripting 정보를 설정한다.
	 */
	public void setScripting(String scripting) {
		if (scripting != null) {
			this.scriptings.add(scripting);
		}
	}
	
	/**
	 * scripting 정보를 조회한다.
	 * @return scripting 목록
	 */
	public String getScripting(int index) {
		return (String) this.scriptings.get(index);
	}

	/**
	 * scripting 카운트를 조회한다.
	 * @return scripting 카운트
	 */
	public int getScriptingSize() {
		return this.scriptings.size();
	}
	
	/**
	 * exception 정보를 설정한다.
	 * @param exception 정보를 설정한다.
	 */
	public void setException(String module, String command, String field) {
		if ((module != null && !"".equals(module)) && (command != null && !"".equals(command))) {
		    ScriptingExceptionVO exception = new ScriptingExceptionVO(module, command, field);
			this.exceptions.add(exception);
		}
	}
	
	/**
	 * exception 정보를 조회한다.
	 * @return exception 목록
	 */
	public ScriptingExceptionVO getException(int index) {
		return (ScriptingExceptionVO) this.exceptions.get(index);
	}
	
	/**
	 * exception 카운트를 조회한다.
	 * @return exception 카운트
	 */
	public int getExceptionSize() {
		return this.exceptions.size();
	}

	/**
	 * scripting + exception 카운트를 조회한다.
	 * @return scripting + exception 카운트
	 */
	public int getSize() {
		return this.scriptings.size() + this.exceptions.size();
	}

	/**
	 * isload 정보를 설정한다.
	 * @param isload  정보를 설정한다.
	 */
	public void setIsload(boolean isload) {
			this.isload = isload;
	}
	
	/**
	 * isload 정보를 조회한다.
	 * @return  isload 목록
	 */
	public boolean getIsload() {
		return this.isload;
	}

	/**
	 * BmsComScriptingVO 개체를 초기화한다.
	 */
	public void initialize() {
		scriptings.clear();
		exceptions.clear();
		this.isload = false;
	}

}
