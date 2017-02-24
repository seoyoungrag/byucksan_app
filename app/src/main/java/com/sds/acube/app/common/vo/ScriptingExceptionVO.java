/**
 * 
 */
package com.sds.acube.app.common.vo;

/**
 * Class Name  : ScriptingExceptionVO.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 3. 22. <br> 수 정  자 : Timothy  <br> 수정내용 :  <br>
 * @author   Timothy 
 * @since  2011. 3. 22.
 * @version  1.0 
 * @see  com.sds.acube.app.common.vo.ScriptingExceptionVO.java
 */

public class ScriptingExceptionVO {
	/**
	 * module
	 */
	private String module;
	/**
	 * command
	 */
	private String command;
	/**
	 * field
	 */
	private String field;
	
	public ScriptingExceptionVO(String module, String command, String field) {
		this.module = module;
		this.command = command;
		this.field = field;
	}
	
	/**
	 * module 정보를 설정한다.
	 * @param module  정보를 설정한다.
	 */
	public void setModule(String module) {
		this.module = module;
	}
	
	/**
	 * module 정보를 조회한다.
	 * @return  module 목록
	 */
	public String getModule() {
		return (this.module == null) ? "" : this.module;
	}
	
	/**
	 * command 정보를 설정한다.
	 * @param command  정보를 설정한다.
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	
	/**
	 * command 정보를 조회한다.
	 * @return  command 목록
	 */
	public String getCommand() {
		return (this.command == null) ? "" : this.command;
	}
	
	/**
	 * field 정보를 설정한다.
	 * @param field  정보를 설정한다.
	 */
	public void setField(String field) {
		this.field = field;
	}
	
	/**
	 * field 정보를 조회한다.
	 * @return  field 목록
	 */
	public String getField() {
		return (this.field == null) ? "" : this.field;
	}

}
