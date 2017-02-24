/**
 * 
 */
package com.sds.acube.app.env.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class Name  : DocNumRuleManager.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 4. 26. <br> 수 정  자 : Administrator  <br> 수정내용 :  <br>
 * @author   Administrator 
 * @since  2012. 4. 26.
 * @version  1.0 
 * @see  com.sds.acube.app.env.util.DocNumRuleManager.java
 */

public class DocNumRuleManager {
	/** 부서명 */
	private String deptName = "DEPTNAME";
	/** 부서약어 */
	private String deptAcronym = "DEPTACRO";
	/** 연도/회기 */
	private String session = "SESSION";
	/** 분류번호 */
	private String classNumber = "CLASSNUM";
	/** 하이픈 */
	private String hyphen = "-";
	/** 구분자 */
	private String separator = ":";
	
	/**
	 */
	List<String> DocNumRule = new ArrayList<String>();
	
	public String getLineFromList(List<String> docNumRuleList) 
	{
		StringBuilder sb = new StringBuilder(128);
		Iterator it = docNumRuleList.iterator();
		
		while ( it.hasNext() )
		{
			String item = (String)it.next();
			sb.append(item);
		}
		
		return sb.toString();
	}
	
	public List<String> getListFromLine(String docNumRuleLine) 
	{
		List<String> list = new ArrayList<String>();
		String[] box = docNumRuleLine.split(separator);
		
		for ( String item : box )
		{
			list.add(item);
		}
		
		return list;
	}

	/**
	 * @return  the docNumRule
	 */
	public List<String> getDocNumRule() {
		return DocNumRule;
	}

	/**
	 * @param docNumRule  the docNumRule to set
	 */
	public void setDocNumRule(List<String> docNumRule) {
		DocNumRule = docNumRule;
	}

}
