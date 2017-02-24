package com.sds.acube.app.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sds.acube.app.common.vo.ScriptingExceptionVO;
import com.sds.acube.app.common.vo.ScriptingVO;

/**
 * Class Name  : XssConfig.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 3. 22. <br> 수 정  자 : Timothy  <br> 수정내용 :  <br>
 * @author   Timothy 
 * @since  2011. 3. 22.
 * @version  1.0 
 * @see  com.sds.acube.app.common.util.XssConfig.java
 */

public class XssConfig {
    static Logger logger = Logger.getLogger(XssConfig.class.getName());
    // by ddalgiabba
    /**
	 */
    private static ScriptingVO scriptings = new ScriptingVO();

    public ScriptingVO getScriptingInstance() {
	return scriptings;
    }

    /**
     * Corss Site Scripting 을 초기화 한다.
     * 
     */
    public static void loadScripting() {
	/** 첫 로그인 사용자인 경우 Cross Site Scripting 데이터 로딩 **/
	if (!scriptings.getIsload()) {			
	    try {
		if (logger.isDebugEnabled()) {
		    logger.debug("# start : Cross Site Scripting ############################################ #");
		    logger.debug("# read cross site scripting data.");
		}

		InputStream f = XssConfig.class.getResourceAsStream("/app/config/scripting.xml");
		DocumentBuilder parser;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		factory.setValidating(false);
		factory.setNamespaceAware(false);
		factory.setIgnoringElementContentWhitespace(true);
		factory.setIgnoringComments(true);
		parser = factory.newDocumentBuilder();

		Document document = parser.parse(f);

		if (document != null) {
		    Element root = document.getDocumentElement();
		    // cross site scripting data
		    NodeList list = root.getElementsByTagName("scripting");
		    if (list != null) {
			for (int loop = 0; loop < list.getLength(); loop++) {
			    Element scripting = (Element) list.item(loop);
			    String script = "";
			    Node child = scripting.getFirstChild();
			    while (child != null) {
				if (child.getNodeType() == 3 && "#text".equals(child.getNodeName())) {
				    script = child.getNodeValue();
				    break;
				}

				child = child.getNextSibling();
			    }

			    if (logger.isDebugEnabled()) {
				logger.debug("# scripting : " + script);
			    }

			    if (script.length() > 0) {
				synchronized (scriptings) {
				    scriptings.setScripting(script);
				}
			    }
			}
		    }

		    // exception - cross site scripting - data
		    list = root.getElementsByTagName("exception");
		    if (list != null) {
			for (int loop = 0; loop < list.getLength(); loop++) {
			    Element exception = (Element) list.item(loop);
			    String module = exception.getAttribute("module");
			    String command = exception.getAttribute("command");
			    String field = exception.getAttribute("field");

			    if (logger.isDebugEnabled()) {
				logger.debug("# exception : module - " + module + ", command - " + command + ", field - " + field);
			    }	

			    synchronized (scriptings) {
				scriptings.setException(module, command, field);
			    }
			}
		    }
		}

		synchronized (scriptings) {
		    scriptings.setIsload(true);
		}

		if (logger.isDebugEnabled()) {
		    logger.debug("# end : Cross Site Scripting ############################################ #");
		}
	    } catch(IOException e) {
		logger.error(e.getMessage());
	    } catch (Exception e) {
		logger.error(e.getMessage());
	    }
	}
    }


    /**
     * Corss Site Scripting 을 점검 한다.
     * 
     * @param request
     * @param modulename
     * @param commandname
     * @throws Exception 
     */
    public static boolean existScripting(HttpServletRequest request, String modulename, String commandname) {
	boolean result = false;

	if (logger.isDebugEnabled()) {
	    logger.debug("# checkScripting ############################################ #");
	    logger.debug("# modulename : " + modulename);
	    logger.debug("# commandname : " + commandname);
	}

	/** Cross Site Scripting 데이터 체크 **/
	if (scriptings.getScriptingSize() > 0) {
	    try {		
		int scriptingsize = scriptings.getScriptingSize();
		int exceptiongsize = scriptings.getExceptionSize();

		if (scriptingsize > 0) {
		    Enumeration<String> e = request.getParameterNames();
		    while(e.hasMoreElements() && !result)
		    {
			String name = (String)e.nextElement();
			String param[] = request.getParameterValues(name);
			int paramlength = param.length;

			for (int i = 0; i < paramlength; i++) {
			    String value = "";
			    if (param[i] != null)
				value = param[i].toLowerCase();

			    // cross site scripting 예외가 있는지 조사
			    boolean isexception = false;
			    for (int pos = 0; pos < exceptiongsize; pos++) {
				ScriptingExceptionVO exception = scriptings.getException(pos);
				String module = exception.getModule();
				String command = exception.getCommand();
				String field = exception.getField();
				if (module.equals(modulename) && command.equals(commandname)) {
				    if ("".equals(field) || field.equals(name)) {
					isexception = true;
					break;
				    }
				}
			    }
			    // cross site scripting 예외가 있으면 다음 파라메터 검사
			    if (isexception)
				continue;

			    for (int loop = 0; loop < scriptingsize; loop++) {
				String script = scriptings.getScripting(loop).toLowerCase();
				// cross site scripting 이 발견되었을 때...
				if (value.indexOf(script) != -1 || value.indexOf(script) != -1) {
				    if (logger.isDebugEnabled()) {
					logger.debug("# script : " + script);
					logger.debug("# name : " + name);
					logger.debug("# param : " + value);
				    }
				    result = true;
				    break;
				}
			    }

			    if (result)
				break;
			}
		    }
		}
	    } catch (Exception ex) {
		logger.warn("Ignore!!! ScriptingConfig.existScripting(HttpServletRequest request, String modulename, String commandname)");
	    }
	} else {
	    reloadScripting();
	}

	return result;
    }


    /**
     * Corss Site Scripting 설정값을 리턴한다.
     * 
     */
    public static ArrayList<String> getScripting() {
	ArrayList<String> scriptinglist = new ArrayList<String>();

	try {
	    int scriptingsize = scriptings.getScriptingSize();

	    for (int pos = 0; pos < scriptingsize; pos++) {
		String script = scriptings.getScripting(pos).toLowerCase();

		scriptinglist.add(script);
	    }
	} catch (Exception e) {
	}

	return scriptinglist;
    }


    /**
     * Corss Site Scripting Exception 설정값을 리턴한다.
     * 
     */
    public static ArrayList<String> getException() {
	ArrayList<String> exceptionlist = new ArrayList<String>();

	try {
	    int exceptiongsize = scriptings.getExceptionSize();

	    for (int pos = 0; pos < exceptiongsize; pos++) {
		ScriptingExceptionVO exception = scriptings.getException(pos);
		String module = exception.getModule();
		String command = exception.getCommand();
		String field = exception.getField();

		exceptionlist.add(module + "\\u0002" + command + "\\u0002" + field);
	    }
	} catch (Exception e) {
	}

	return exceptionlist;
    }


    /**
     * Corss Site Scripting 을 초기화한다.
     * 
     */
    public static void reloadScripting() {
	try {
	    // initialize object
	    scriptings.initialize();
	    // load object
	    loadScripting();
	} catch (Exception e) {
	}
    }
}
