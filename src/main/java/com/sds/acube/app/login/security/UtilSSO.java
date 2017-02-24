package com.sds.acube.app.login.security;

import java.io.IOException;import javax.servlet.ServletException;import javax.servlet.http.HttpServletRequest;import javax.servlet.http.HttpSession;import com.sds.acube.esso.security.EnDecoder;import com.sds.acube.esso.security.SecurityFactory;import com.sds.acube.app.common.util.AppConfig;import com.sds.acube.app.login.vo.UserProfileVO;

public class UtilSSO {		static String algorithm = AppConfig.getProperty("encode_algorithm", "DES", "login");
	public static String encodeSession(HttpServletRequest request) {
		String result = "";
		try {
			SecurityFactory fac = SecurityFactory.getInstance();
			EnDecoder enDecoder = fac.getEnDecoder(algorithm);
			result = enDecoder.encode(getSSOData(request));
		} catch (Exception e) {	}
		return result;	}		public static String encodeData(String plainText) {		
		String result = "";		
		try {			
			SecurityFactory fac = SecurityFactory.getInstance();			
			EnDecoder enDecoder = fac.getEnDecoder(algorithm);			
			result = enDecoder.encode(plainText);			
		} catch (Exception e) {	}		
		return result;	}
	public static String decodeData(String encodeText) {				String result = "";				try {						SecurityFactory fac = SecurityFactory.getInstance();						EnDecoder enDecoder = fac.getEnDecoder(algorithm);						result = enDecoder.decode(encodeText);					} catch (Exception e) {	}				return result;	}
	public static String getSSOData(HttpServletRequest request) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		UserProfileVO userProfile = (UserProfileVO) session.getAttribute("userProfile");
		if (userProfile == null) {
			userProfile = new UserProfileVO();
		}		
		String loginID = userProfile.getLoginId();
		String strName = userProfile.getUserName();
		String strCompID = userProfile.getCompId();
		String strCompName = userProfile.getCompName();
		String strCompTel = "";
		String strDeptID = userProfile.getDeptId();
		String strDeptName = userProfile.getDeptName();
		String strGrdID = userProfile.getGradeCode();
		String strGrdName = userProfile.getGradeName();
		String strSecID = "";
		String strSGLSvr = (String) session.getAttribute("SGLSVR");
		String passwd = userProfile.getPassword();
		String strSGLOMAddr = (String) session.getAttribute("SYSMAIL");
		String strSabun = (String) session.getAttribute("SABUN");
		String strLang = (String) session.getAttribute("LANG");
		String strOmWebIP = (String) session.getAttribute("OMWEBIP");
		String strMailServer = "";
		String strUserID = userProfile.getUserUid();
		String strSocialID = (String) session.getAttribute("SOCIALID");
		String strPrivLst = (String) session.getAttribute("PRIVLST");
		String strEmail = (String) session.getAttribute("USEREMAIL");
		String strPortalWebLoc = "";
		String strPortalSvr = "";
		String strStartPage = "";
		String strGrpLst = (String) session.getAttribute("GRPLST");
		String strSTimeZone = "";
		String strCTimeZone = (String) session.getAttribute("USERTIMEZONE");
		String strRoleCodes = (String) session.getAttribute("ROLECODES");
		String strDSTFlag = "";
		String strDateFormat = "";
		String strTimeFormat = "";
		String GmtFlag = "";
		String DayligntFlag = "";
		String strDN = "";
		String strJikCheck = "";
		String strAddJobCode = "";
		String strAddTel = "";
		String strAddDeptCode = (String) session.getAttribute("ADDITIONAL_DEPTCODE");
		String strAddDeptName = (String) session.getAttribute("ADDITIONAL_DEPTNAME");
		String strAddSecLevel = (String) session.getAttribute("ADDITIONAL_SECLEVEL");
		String strAddLogin = (String) session.getAttribute("ADDITIONAL_LOGIN");
		String strPisGrp = (String) session.getAttribute("PISGRP");
		String strPositionName = (String) session.getAttribute("POSITIONNAME");
		String strPositionID = (String) session.getAttribute("POSITIONID");
		String strIntranet = (String) session.getAttribute("INTRAFLAG");
		String selLang = "ko";
		String strDutyCode = (String) session.getAttribute("DUTYCODE");
		String strReserved1 = (String) session.getAttribute("RESERVED1");
		String strReserved2 = (String) session.getAttribute("RESERVED2");
		String strReserved3 = (String) session.getAttribute("RESERVED3");
		String vtime = "";
		StringBuffer buff = new StringBuffer();
		buff.append("F1=" + loginID + ";");
		buff.append("F2=" + strName + ";");
		buff.append("F3=" + strCompID + ";");
		buff.append("F4=" + strCompName + ";");
		buff.append("F5=" + strCompTel + ";");
		buff.append("F6=" + strDeptID + ";");
		buff.append("F7=" + strDeptName + ";");
		buff.append("F8=" + strGrdID + ";");
		buff.append("F9=" + strGrdName + ";");
		buff.append("F10=" + strSecID + ";");
		buff.append("F11=" + strSGLSvr + ";");
		buff.append("F12=" + passwd + ";");
		buff.append("F13=" + strSGLOMAddr + ";");
		buff.append("F14=" + strSabun + ";");
		buff.append("F15=" + strLang + ";");
		buff.append("F16=" + strOmWebIP + ";");
		buff.append("F17=" + strMailServer + ";");
		buff.append("F18=" + strUserID + ";");
		buff.append("F19=" + strSocialID + ";");
		buff.append("F20=" + strPrivLst + ";");
		buff.append("F21=" + strEmail + ";");
		buff.append("F22=" + strPortalSvr + ";");
		buff.append("F23=" + strPortalWebLoc + ";");
		buff.append("F24=" + strStartPage + ";");
		buff.append("F25=" + strGrpLst + ";");
		buff.append("F26=" + strSTimeZone + ";");
		buff.append("F27=" + strCTimeZone + ";");
		buff.append("F28=" + strDateFormat + ";");
		buff.append("F29=" + strTimeFormat + ";");
		buff.append("F30=" + GmtFlag + ";");
		buff.append("F31=" + DayligntFlag + ";");
		buff.append("F32=" + strDN + ";");
		buff.append("F33=" + strDSTFlag + ";");
		buff.append("F34=" + strRoleCodes + ";");
		buff.append("F35=" + "" + ";");
		buff.append("F36=" + (String) session.getAttribute("SEL_EDITOR") + ";");
		buff.append("F37=" + strJikCheck + ";");
		buff.append("F38=" + strAddJobCode + ";");
		buff.append("F39=" + strAddTel + ";");
		buff.append("F40=" + strAddDeptCode + ";");
		buff.append("F41=" + strAddDeptName + ";");
		buff.append("F42=" + strAddSecLevel + ";");
		buff.append("F43=" + strAddLogin + ";");
		buff.append("F44=" + strPisGrp + ";");
		buff.append("F45=" + strPositionName + ";");
		buff.append("F46=" + strPositionID + ";");
		buff.append("F47=" + strIntranet + ";");
		buff.append("F48=" + selLang + ";");
		buff.append("F49=" + ";");
		buff.append("F50=" + strDutyCode + ";");
		buff.append("F51=" + strReserved1 + ";");
		buff.append("F52=" + strReserved2 + ";");
		buff.append("F53=" + strReserved3 + ";");
		buff.append("F54=" + vtime + ";"); // Valid Time
		buff.append("F55=" + ""); // Valid Check Second
		return buff.toString();
	}		public static String getData(String source, String key, String delimiter) {		if ("".equals(source) || "".equals(key+"=")) {// D1에 F1등의 키값이 포함되어있을 수도 있어서 수정함.			return "";		}		if ("".equals(delimiter)) {				delimiter = ";";		}				int nKeyPos = source.indexOf(key+"=");		if (nKeyPos == -1) {			return "";		}		int nDelimiterPos = source.indexOf(delimiter, nKeyPos + key.length());		if (nDelimiterPos > -1) {			return source.substring(nKeyPos + key.length() + 1, nDelimiterPos);		}		else {			return source.substring(nKeyPos + key.length() + 1);		}				}}
