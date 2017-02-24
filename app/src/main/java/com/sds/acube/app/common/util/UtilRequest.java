package com.sds.acube.app.common.util;

import java.io.UnsupportedEncodingException;import java.security.MessageDigest;import java.security.NoSuchAlgorithmException;import java.util.Enumeration;import java.util.HashMap;import java.util.Iterator;import java.util.List;import java.util.Map;import javax.servlet.http.HttpServletRequest;import javax.servlet.http.HttpSession;import javax.servlet.jsp.JspWriter;import org.springframework.web.context.request.RequestAttributes;import org.springframework.web.context.request.RequestContextHolder;

/**
 * Web Application의 request 처리 유틸리티
 */
public class UtilRequest {
	/**
	 * request에서 파라미터 값을 추출한다. 파라미터가 존재하지 않을 경우에는 빈 문자열을 리턴한다.
	 * 
	 * @param name 파라미터 이름
	 * @return 파라미터의 값 (파라미터가 존재하지 않을 경우 "")
	 */
	public static String getString(HttpServletRequest request, String name) {
		return getString(request, name, "");
	}


	/**
	 * request에서 파라미터 값을 추출한다.
	 * 
	 * @param name 파라미터 이름
	 * @param name 파라미터가 존재하지 않을 경우 디폴트 값
	 * @return 파라미터의 값
	 */
	public static String getString(HttpServletRequest request, String name, String defaultValue) {
		String value = request.getParameter(name);
		if(value == null || value.length() == 0) {
			return defaultValue;
		}
		return escapeTag(name, UtilEncoding.convertEncoding(value));
	}


	/**
	 * request에서 파라미터 값 배열을 추출한다.
	 * 
	 * @param name 파라미터 이름
	 * @return 파라미터의 값 배열
	 */
	public static String[] getStringArray(HttpServletRequest request, String name) {
		String[] values = request.getParameterValues(name);
		int count = (values == null) ? 0 : values.length;
		for(int i = 0; i < count; i++) {
			values[i] = escapeTag(name, UtilEncoding.convertEncoding(values[i]));
		}

		return values;
	}
	/**	 * 	 * <pre> 	 *  request를 map으로 담기	 * </pre>	 * @param request	 * @return	 * @see  	 *	 */	public static Map getParamMap(HttpServletRequest request) {		Map paramMap = new HashMap();				Map map = request.getParameterMap();		Iterator it = map.entrySet().iterator();		while(it.hasNext()) {			Map.Entry entry = (Map.Entry) it.next();			String key = (String) entry.getKey();			String[] values = (String[]) entry.getValue();			if(values.length ==1){			    			    String value = values[0];    			    paramMap.put(key, value);			}			else{   			    paramMap.put(key, values);			}		}		return paramMap;	}

	/**
	 * request에서 파라미터 값을 추출한다.
	 * 
	 * @param name 파라미터 이름
	 * @return 파라미터의 값 (파라미터가 존재하지 않을 경우 0)
	 */
	public static int getInt(HttpServletRequest request, String name) {
		return getInt(request, name, 0);
	}


	/**
	 * request에서 파라미터 값을 추출한다.
	 * 
	 * @param name 파라미터 이름
	 * @param name 파라미터가 존재하지 않을 경우 디폴트 값
	 * @return 파라미터의 값
	 */
	public static int getInt(HttpServletRequest request, String name, int defaultValue) {
		String value = getString(request, name, null);
		if(value == null) {
			return defaultValue;
		}

		int nRet = defaultValue;
		try {
			nRet = Integer.parseInt(value);
		} catch(Exception e) {}
		return nRet;
	}


	/**
	 * 넘어오는 파라미터가 많을 경우 request에 담겨져있는 모든 파라미터를 다 보여준다. 
	 * 디버깅할때 파라미터를 하나하나 찍어볼 필요없이 이 메소드를 호출하면 모든 파라미터를 보여준다
	 * 
	 * @param request
	 * @return 모든
	 */
	public static String printAllParameters(HttpServletRequest request) {
		Map map = request.getParameterMap();
		Iterator it = map.entrySet().iterator();
		StringBuffer sb = new StringBuffer();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			String[] value = (String[]) entry.getValue();
			sb.append(key).append(":").append(value[0]).append(" [value length : "+value.length+"] ").append("\n");
		}
		return sb.toString();
	}


	/**
	 * request에서 파라미터 값을 추출한다.
	 * 
	 * @param name 파라미터 이름
	 * @return 파라미터의 값이 "true" 또는 "y" 인 경우는 true. 그 외에는 false. (대소문자 구별 안함)
	 */
	public static boolean getBoolean(HttpServletRequest request, String name) {
		String value = getString(request, name, null);
		if(value == null) {
			return false;
		}

		return (value.equalsIgnoreCase("y") || value.equalsIgnoreCase("true"));
	}


	public static void repeatParameters(HttpServletRequest request, JspWriter out, String[] parameters) {
		try {
			int paramCount = parameters == null ? 0 : parameters.length;

			for(int i = 0; i < paramCount; i++) {
				printInputTag(request, out, parameters[i]);
			}
		} catch(Exception e) {
			LogWrapper.getLogger("com.sds.acube.app").error(e.getMessage());
		}
	}


	public static void repeatParameters(HttpServletRequest request, JspWriter out, List parameterList) {
		try {
			for(Iterator i = parameterList.iterator(); i.hasNext();) {
				String n = (String) i.next();
				printInputTag(request, out, n);
			}
		} catch(Exception e) {
			LogWrapper.getLogger("com.sds.acube.app").error(e.getMessage());
		}
	}


	public static void repeatParameters(HttpServletRequest request, JspWriter out) {
		try {
			for(Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
				String n = (String) e.nextElement();
				printInputTag(request, out, n);
			}
		} catch(Exception e) {
			LogWrapper.getLogger("com.sds.acube.app").error(e.getMessage());
		}
	}


	private static void printInputTag(HttpServletRequest request, JspWriter out, String name) throws Exception {
		out.print("<input type=\"hidden\"");
		out.print(" name=\"" + name + "\"");
		out.println(" value=\"" + getString(request, name) + "\">");
	}

	
	private static String escapeTag(String key, String value) {
		String[] forbidden = new String[]{""};
		
		for(int i=0; i < forbidden.length; i++) {
			if(key.equalsIgnoreCase(forbidden[i])) {
				value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			}
		}
		
		return value;
	}
	

	/**
	 * 클라이언트에서 넘어온 값이 조작된 값인지 아닌지를 검증한다.
	 *  
	 * @param request
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean validateTicket(HttpServletRequest request) {
		boolean result = true;
		HttpSession session = request.getSession();
		HashMap requestMap = (HashMap)session.getAttribute("requestMap");
		
		if(requestMap==null) {
			return result;
		}

		Map map = request.getParameterMap();
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
		    Map.Entry entry = (Map.Entry)it.next();
		    String key = (String)entry.getKey();
		    String[] entryValue = (String[])entry.getValue();
		    
			String originalSHA1 = (String)requestMap.get(key);
			String passedSHA1 = hash(entryValue[0]);
			
			if(originalSHA1!=null && !originalSHA1.equals(passedSHA1)) {
				result = false;
				//break;
			}		    
		}

		session.removeAttribute("requestMap");		
		return result;
	}


	/**
	 * 검증이 필요한 페이지는 이 메소를 호출하여 request에 담겨있는 값들을 세션에 담아두고
	 * 다음 페이지에서 검증하게 된다.
	 * 
	 * ex)
	 * Reg.jsp 페이지에서 UtilRequest.injectTicket(request); 코드를 한줄 입력후
	 * RegDB.jsp 페이지에서 UtilRequest.validateTicket(request); 코드를 입력해서 검증한다.
	 * 
	 * @param request
	 */
	public static void injectTicket(HttpServletRequest request) {
		HttpSession session = request.getSession();
		HashMap requestMap = new HashMap();
		Map map = request.getParameterMap();
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
		    Map.Entry entry = (Map.Entry)it.next();
		    String key = (String)entry.getKey();
		    String[] value = (String[])entry.getValue();	    
		    requestMap.put(key, hash(value[0]));
		}
		session.setAttribute("requestMap", requestMap);
	}
	
	
	public static String replacestr(String text, String repl, String with) {
		if(text == null || repl == null || with == null) {
			return text;
		}

		StringBuffer buf = new StringBuffer(text.length());
		int start = 0, end = 0;
		while((end = text.indexOf(repl, start)) != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + repl.length();
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	private static String convertToHex(byte[] data) {		StringBuffer buf = new StringBuffer();		for (int i = 0; i < data.length; i++) {	/*		    int halfbyte = (data[i] >>> 4) & 0x0F;		    int two_halfs = 0;		    do {			if((0 <= halfbyte) && (halfbyte <= 9))			    buf.append((char) ('0' + halfbyte));			else			    buf.append((char) ('a' + (halfbyte - 10)));			halfbyte = data[i] & 0x0F;		    } while(two_halfs++ < 1);	*/			buf.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));		}		return buf.toString();	}


	public static String hash(String text) {
		if(text==null) {
			return "";
		}
		
		byte[] sha1hash = new byte[40];
        try {
	        MessageDigest md;
	        md = MessageDigest.getInstance("SHA-1");
	        md.update(text.getBytes("iso-8859-1"), 0, text.length());
	        sha1hash = md.digest();
        } catch(NoSuchAlgorithmException e) {
		LogWrapper.getLogger("com.sds.acube.app").error(e.getMessage());
        } catch(UnsupportedEncodingException e) {
		LogWrapper.getLogger("com.sds.acube.app").error(e.getMessage());
        }
		return convertToHex(sha1hash);
	}
	private static Object getSessionAttribute(String name) {	    try {	    	return RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);	    } catch (Exception e) {	    	return null;	    }	}	public static String getSessionAttrString(String name) {		Object obj = getSessionAttribute(name);		if (obj != null) {			return (String) obj;		} else {			return "";		}	}}
