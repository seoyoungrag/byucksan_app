/**
 * 
 */
package com.sds.acube.app.common.util;

import org.apache.commons.lang.StringUtils;


/**
 * Class Name : EscapeUtil.java <br>
 * Description : 설명 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 4. 4. <br>
 * 수 정 자 : Timothy <br>
 * 수정내용 : <br>
 * 
 * @author Timothy
 * @since 2011. 4. 4.
 * @version 1.0
 * @see com.sds.acube.app.common.util.EscapeUtil.java
 */

public class EscapeUtil {

    public static final String LT = "&lt;";
    public static final String GT = "&gt;";
    public static final String QUOT = "&quot;";
    public static final String NL = "\\n";
    public static final String ETC = "&#39;";
    public static final String USE_DRM = "1";
    public static final String NO_USE_DRM = "0";
    public static final String NO_DEFINE_EXT = "etc";
    public static final String[] FILE_EXT_ARR = { "XLS", "DOC", "PPT", "GUL", "HTM", "TXT", "HWP", "BMP", "EXE", "GIF", "HTM", "INI",
	    "JPG", "MGR", "MPG", "PDF", "TIF", "TXT", "XML", "WAV", "FSC", "ZIP", "BC", "DL" };

    private static final String AMP = "&amp;";
    private static final String BR = "<br>";

    /**
     * HTML 코드 표현으로 문자열을 바꿔주는 메서드(화면 출력용)
     * 
     * @param str
     *            문자열
     * @return String 변경된 문자열
     */
    public static String escapeHtmlDisp(String str) {
	
	String strText = StringUtils.defaultString(str);
	String strInput;
	StringBuffer strOutput = new StringBuffer("");
	String convert;
	char strTmp;
	char strTmp1;

	int nCount;
	char prevChar = '\u0000';
	if (strText == null) {
	    strText = "";
	}

	strInput = strText;
	nCount = strInput.length();

	for (int i = 0; i < nCount; i++) {

	    strTmp = strInput.charAt(i);

	    if (i + 1 == nCount) {
		strTmp1 = ' ';
	    } else {
		strTmp1 = strInput.charAt(i + 1);
	    }

	    if (strTmp == '<')
		strOutput.append(LT);
	    else if (strTmp == '>')
		strOutput.append(GT);
	    else if (strTmp == '&' && strTmp1 == '#')
		strOutput.append('&');
	    else if (strTmp == '&')
		strOutput.append(AMP);
	    else if (strTmp == (char) 37)
		strOutput.append("&#37;");
	    else if (strTmp == (char) 34)
		strOutput.append(QUOT);
	    else if (strTmp == (char) 39)
		strOutput.append(ETC);
	    // else if (strTmp == '#')
	    // strOutput.append("&#35;");
	    else if (strTmp == '\n') {
		if (prevChar != '\r') {
		    strOutput.append(BR);
		}
	    } else if (strTmp == '\r') {
		strOutput.append(BR);
	    }
	    // else if (strTmp == ' ')
	    // strOutput.append("&nbsp;");
	    else
		strOutput.append(strTmp);

	    prevChar = strTmp;
	}

	convert = strOutput.toString();
	return convert;

    }


    /**
     * HTML tag안에 문자열을 사용하고자 할때 문자열을 바꿔주는 메서드
     * 
     * @param str
     *            문자열
     * @return String 변경된 문자열
     */
    public static String escapeHtmlTag(String str) {
	String input = StringUtils.defaultString(str);
	if (input == null) {
	    return "";
	}

	StringBuffer filtered = new StringBuffer(input.length());
	char c;
	char c1;

	for (int i = 0; i < input.length(); i++) {
	    c = input.charAt(i);

	    if (i + 1 == input.length()) {
		c1 = ' ';
	    } else {
		c1 = input.charAt(i + 1);
	    }

	    if (c == '<') {
		filtered.append(LT);
	    } else if (c == '>') {
		filtered.append(GT);
	    } else if (c == '&' && c1 == '#') {
		filtered.append('&');
	    } else if (c == '&') {
		filtered.append(AMP);
	    }
	    // else if (c == (char)37) {
	    // filtered.append("&#37;");
	    // }
	    else if (c == (char) 34) {
		filtered.append(QUOT);
	    } else if (c == (char) 39) {
		filtered.append(ETC);
	    }
	    /*
	     * else if (c == '\\') { filtered.append("\\\\"); }
	     */
	    // Html Tag에 들어갈때 ; (char 59) 은 변환될 필요가 없음
	    /*
	     * else if (c == (char) 59) { filtered.append("&#59;"); }
	     */
	    else {
		filtered.append(c);
	    }

	}
	return filtered.toString();
    }


    /**
     * Javascript 안에 문자열을 사용하고자 할때 문자열을 바꿔주는 메서드
     * 
     * @param str
     *            문자열
     * @return String 변경된 문자열
     */
    public static String escapeJavaScript(String str) {
	String input = StringUtils.defaultString(str);
	if (input == null) {
	    return "";
	}

	StringBuffer filtered = new StringBuffer(input.length());
	char prevChar = '\u0000';
	char c;
	char c1;

	for (int i = 0; i < input.length(); i++) {
	    c = input.charAt(i);

	    if (i + 1 == input.length()) {
		c1 = ' ';
	    } else {
		c1 = input.charAt(i + 1);
	    }
/*
	    if (c == '<') {
		filtered.append(LT);
	    } else if (c == '>') {
		filtered.append(GT);
	    } else if (c == '&' && c1 == '#') {
		filtered.append('&');
	    } else if (c == '&') {
		filtered.append(AMP);
	    } else     
	    if (c == (char) 34) {
		filtered.append("\"");
	    } else 
*/			
	    if (c == (char) 39) {
		filtered.append("\\'");
	    } else if (c == '"') {
		filtered.append("\\\"");
	    } else if (c == '\'') {
		filtered.append("\\'");
	    } else if (c == '\\') {
		filtered.append("\\\\");
	    } else if (c == '\t') {
		filtered.append("\\t");
	    } else if (c == '\n') {
		if (prevChar != '\r') {
		    filtered.append(NL);
		}
	    } else if (c == '\r') {
		filtered.append(NL);
	    } else if (c == '\f') {
		filtered.append("\\f");
	    } else if (c == '/') {
		filtered.append("\\/");
	    } else {
		filtered.append(c);
	    }
	    prevChar = c;

	}

	return filtered.toString();
    }

    /**
     * 날짜문자열이 9999-12-31 23:59:59 빈문자열로 바꿔주는 메서드
     * 
     * @param str
     *            문자열
     * @return String 변경된 문자열
     */
    public static String escapeDate(String date) {
	if (date == null || "".equals(date)) {
	    return ""; 
	} else {
	    String compareDate = date;
	    if (compareDate.indexOf("/") != -1) {
		compareDate = compareDate.replaceAll("/", "-");
	    }
	    if (compareDate.indexOf("9999-12-31") != -1) {
		return "";
	    } else {
		return date;
	    }
	}
    }
}
