<%-----------------------------------------------------------------------------
 *	
 * 작   성  자 : 조 재 혁
 *
 * 프로그램 명 : usr_include.inc
 *
 * 설       명 : 사용자 웹 프로그램에서 include해서 쓰는 공통 부분.
 *				 
 *
 * 개발 시작일 : 2001/11/06
 *
 * 개발 종료일 : 0000/00/00
 *
 * 수   정  일 : 0000/00/00(수정자 : ) 
 *				- 수정사유()
 * 버       젼 : ver 1.0
 *
 * Copyright notice : Copyright (C) 2001 by SAMSUNG SDS co.,Ltd. All right reserved.
 *				  
-----------------------------------------------------------------------------%>
<%--
	★ usr_include.inc에서 import해야 할 클래스
		java.util.*;
		javax.naming.InitialContext;
		javax.rmi.PortableRemoteObject;
		com.sds.acube.dm.jsp.*;
	
	1. 사용자 프로그램이나 관리자 프로그램은 반드시 usr_include.inc나 admCommon.inc를 포함해야 합니다.
	
	2. usr_include에서 사용하는 jspService()메소드(스크립트릿) 안에서의 변수

		String 			SCHTERM 		: 검색기간
		String 			strCookiePath 	: 쿠키의 패스
		LogManager 		lm				: 로그관리자
		String 			CLASS_NAME		: 로그를 남길 때 쓸 파일 이름
		CookieManager 	cMan			: 쿠키 관리자
	  *	Properties 		cookProp		: 쿠키중에서 DM2K의 이름에 들어가 있는 내용을 담은것.
		Properties		omProp			: xml로 된 설정파일의 om 섹션의 내용			(application scope의 빈)
		Properties		envProp			: xml로 된 설정파일의 env 섹션의 내용			(application scope의 빈)
		Properties		connectionProp	: xml로 된 설정파일의 connection 섹션의 내용	(application scope의 빈)
		UserInfo		uiBean			: 사용자의 정보를 담고 있는 세션객체			(session scope의 빈)
	
	3. 여기 있는 내용의 대부분은 처음 로긴하여 사용자의 세션이 생성될 때에 행하는 내용들이 대부분이다.
		따라서 세션의 내용이나 애플리케이션의 내용을 변경하고자 한다면 직접 접근을 해서 바꾼다.
	
	4. 쿠키를 세팅하고자 할 때 DM2K의 내용을 수정할 때는 반드시 cookProp를 이용하여 세팅한다.
		다른 Properties의 객체를 생성해서 쿠키를 쓴다면 기존의 DM2K에 들어있던 내용은 모두 사라지기 때문이다.
		
	5. 쿠키(DM2K)의 내용

		APROVEDOC 	:
		DELAYEDDOC 	:
		EDITBODY 	:
		ENVUPDATED 	:
		FLEENCOPT 	:
		HUNPLUGIN 	:
		INITSEARCH 	:
		LISTCNT 	:
		LSTCNT 		:
		LSTSORT 	:
		LSTSORTORD 	:
		OCX 		:
		SCHTERM		:
		SPASSCHTRM 	:
		SVRBNDSAV 	:
		SVRFLESIZE 	:
		TRASHBIN 	:
		V_DOC 		:
		V_GUL 		:
		V_HWP 		:
		V_IMG 		:
		V_PPT 		:
		V_XLS 		:
		ZIP2UPLOAD 	:
	
	6. 메소드 내용.	(여기에 계속 둘 지 의견 주시면 감사하겠습니다.)
	
		String 		getVal(String str, String defStr)				str이 null일 때 defStr을 반환하고 null이 아니면 str을 반환
		String 		getInfData(String strParam, String strCntDb)
		String[] 	getInfData(String[] arrParam, String strCntDb)


--%>
<%
	String strCookiePath = request.getContextPath();					// DM 에서 사용할 쿠키들의 디렉토리
	com.sds.acube.dm.LogManager lm = com.sds.acube.dm.LogManager.getInstance();		// Log를 기록할 변수 설정
	String CLASS_NAME = request.getRequestURI();	// LogManager에 사용할 클래스 이름
	com.sds.acube.dm.jsp.CookieManager cMan=new com.sds.acube.dm.jsp.CookieManager(request.getCookies());			// CookieManager 생성.
	Properties cookProp = null;
	String SCHTERM = null;
	
	WebLogger wl = new WebLogger(getServletConfig(), request);
	
//	String BASE = request.getContextPath();
	String JSP = application.getInitParameter("JSP");
	String ENV = application.getInitParameter("ENV");
	String IMAGE = application.getInitParameter("IMAGE");
	String HTML = application.getInitParameter("HTML");
	String SERVLET = application.getInitParameter("SERVLET");
	
	DmAbsPath.JSP = JSP;
	DmAbsPath.ENV = ENV;
	DmAbsPath.IMAGE = IMAGE;
	DmAbsPath.HTML = HTML;	
	DmAbsPath.SERVLET = SERVLET;	

		
	%>
<%@ page errorPage="/jsp/dmErrorPage.jsp"%>
<%-- 설정파일에서 설정을 읽어서 application scope의 bean으로 만든다. --%>
<jsp:useBean id="envProp" scope="application" class="java.util.Properties">
	<%
		Properties tmpEnvProp = com.sds.acube.dm.DmConfigParser.getProperties(com.sds.acube.dm.DmConfigParser.WEB_CONFIG, com.sds.acube.dm.DmConfigParser.SECTION_ENV);
		if(tmpEnvProp != null) envProp.putAll(tmpEnvProp); 
		lm.setLogMode(lm.MODE_DETAIL);
		lm.writeLog("envProp was initialized["+envProp.size()+" entries]",CLASS_NAME);
		// dm-web-config.xml에 SSO_ID_COL 항목이 없으면 기본값 1을 넣는다(2002.04.30 조재혁)
		if(envProp.getProperty("SSO_ID_COL") == null) {
			envProp.setProperty("SSO_ID_COL","1");
		}
	%>
</jsp:useBean>
<jsp:useBean id="omProp" scope="application" class="java.util.Properties">
	<%
		Properties tmpOmProp = com.sds.acube.dm.DmConfigParser.getProperties(com.sds.acube.dm.DmConfigParser.WEB_CONFIG, com.sds.acube.dm.DmConfigParser.SECTION_OM);
		if(tmpOmProp != null) omProp.putAll(tmpOmProp); 
		lm.setLogMode(lm.MODE_DETAIL);
		lm.writeLog("omProp was initialized["+omProp.size()+" entries]",CLASS_NAME);
	%>
</jsp:useBean>
<jsp:useBean id="connectionProp" scope="application" class="java.util.Properties">
	<%
		Properties tmpConnectionProp = com.sds.acube.dm.DmConfigParser.getProperties(com.sds.acube.dm.DmConfigParser.WEB_CONFIG, com.sds.acube.dm.DmConfigParser.SECTION_CONNECTION);
		if(tmpConnectionProp != null) connectionProp.putAll(tmpConnectionProp); 
		lm.setLogMode(lm.MODE_DETAIL);
		lm.writeLog("connectionProp was initialized["+connectionProp.size()+" entries]",CLASS_NAME);
	%>
</jsp:useBean>


<%	
	String strD1 = request.getParameter("D1");
	com.sds.acube.dm.jsp.DmUserInfo uiBean;
////////////////////////////////////////////////////////////////////////////////////////
// 			세션 synchronized 시작
////////////////////////////////////////////////////////////////////////////////////////
	synchronized(session)
	{
		uiBean = (com.sds.acube.dm.jsp.DmUserInfo)session.getAttribute("uiBean");
		if(uiBean == null || (strD1 != null && strD1.length() > 0 && uiBean instanceof com.sds.acube.dm.jsp.userinfo.DmUserInfoMySingleSend)) {
			lm.writeLog("uiBean = "+uiBean,CLASS_NAME);
			try {
				uiBean = (com.sds.acube.dm.jsp.DmUserInfo)Class.forName(envProp.getProperty("DmUserInfoClass")).newInstance();
				
			} catch(ClassNotFoundException e) {
				throw e;
			}
			uiBean.setUseParameterEncode(application.getServerInfo().startsWith("WebLogic"));
			session.setMaxInactiveInterval(720*5);
			session.setAttribute("uiBean", uiBean);
		}
		String sStrUid = (String)session.getAttribute("sStrUid");
//데모모드일 때 true 아니면 false;
// dm-web-config.xml에서 DEMO 라는 항목에 따라 설정하도록 변경함 2002.1.4 조재혁
//		uiBean.setIsDemo(false);

		String strEnvIsDemo = envProp.getProperty("DEMO");
		if("N".equals(strEnvIsDemo)) {
			uiBean.setIsDemo(false);
		}else {
			uiBean.setIsDemo(true);
		}
	// D1이 넘어오면 사용자 정보를 Update 한다.
		if(strD1 != null && strD1.length() >0) {
			uiBean.setUserInfo(strD1);
			String strLoginPage = envProp.getProperty("LOGIN_PAGE");
			if(strLoginPage != null) {//&& strLoginPage.startsWith("http://")) {
				uiBean.setPortalServer(strLoginPage);
			}
 	
			if(!uiBean.getIsDemo()) {
				StringBuffer sb = new StringBuffer("Log Info\n");
				if(uiBean.getUserInfoLength() == 0) {
					sb.append("\tuiBean.getUserInfoLength() is 0\n");
				} else {
					sb.append("\tarrInfo.length = "+uiBean.getUserInfoLength()+"\n");
				}
				sb.append("strD1="+strD1+'\n');
				lm.writeLog(sb.toString(), "usr_include.inc");
				
			}
			
/* 포탈의 LDAP 관련 부분을 사용하기 위해 세팅함
포탈은 E와 K 둘중 하나만 쓸것으로 예상되므로 DM의 언어매핑 전에 이 값을 세팅한다.
COMPID 회사 ID*
USERID 현재 사용자 ID*
DEPTID 현재 사용자 부서 ID ( 초기 조직도 출력시 필요 )
LANG 언어설정*/
	
			if("LDAP".equals(envProp.getProperty("OFFICETYPE"))) {
				session.setAttribute("COMPID",uiBean.getCompID());
				session.setAttribute("USERID",uiBean.getUserID());
				session.setAttribute("DEPTID",uiBean.getDptID());
				session.setAttribute("LANG",uiBean.getLanguage());
				session.setAttribute("CHARSET",DmWordManager.getEncoding(uiBean.getLanguage()));
			}			
						
	
		// 언어 설정 SSO정보가 F15 가 K이면 LANG1의 언어로 E이면 LANG2의 언어로 매핑한다.
			if(uiBean.getLanguage().equals("K") &&
				envProp.getProperty("LANG1") != null) {
				uiBean.setLanguage(envProp.getProperty("LANG1"));
			} else if(uiBean.getLanguage().equals("E") && envProp.getProperty("LANG2") != null) {
				uiBean.setLanguage(envProp.getProperty("LANG2"));
			}
	
		// 세션에 있는 사용자가 현재 uiBean의 사용자와 틀리면 로그인처리를 한다.
			if(!(uiBean.getUserID()+'|'+uiBean.getDptID()).equals(sStrUid)) {
				if(!uiBean.getIsDemo()) out.println("Welcome to ACUBE DM");
				try {
					/* 사용자 DB를 새로 초기화*/
					uiBean.setUserDb(null, null);
			// 데이터베이스에 로그를 남기면서 사용자 타입을 가져온다.		
					lm.writeLog("** login 호출",CLASS_NAME);
					com.sds.acube.dm.login.DmLoginHome logHome = (com.sds.acube.dm.login.DmLoginHome)com.sds.acube.dm.ServiceLocator.getEJBHome("DmLogin");
					com.sds.acube.dm.login.DmLogin log = logHome.create();
					uiBean.setUsrType( log.login( uiBean.getUserID(),uiBean.getUserName(),uiBean.getDptID(),uiBean.getDptName(),uiBean.getTitleName(),uiBean.getConfidentiality(),uiBean.getStrCurDb() ) );
					lm.writeLog("session["+uiBean.getUserID()+"] was initialized",CLASS_NAME);
			// uiBean의 dmsWebServer이라는 변수를 변경
			// dm-web-config.xml 에 DMURL 이라는 항목이 있으면 동적으로 적용하며 http://acube:8888 과 같이 "http://" 로 시작하여 포트까지 적는다. 물론 80포트를 사용할 때는 포트는 적을 필요 없이 http://acube 와 같이 적으면 된다. ( 2002.4.12 : 조재혁 )
					uiBean.setDmWebRoot(envProp.getProperty("DMURL"));
					if(uiBean.getDmWebRoot() == null || uiBean.getDmWebRoot().length() < 1) {
						String _sPort = 80 == request.getServerPort() ? "": ":"+request.getServerPort();
						uiBean.setDmWebRoot("http://"+request.getServerName()+ _sPort);
					}
			
				}catch(Exception e) {
					lm.writeLog(e, "세션에 로그인 정보 생성 실패", CLASS_NAME);
				}
				cookProp = new Properties();				// 쿠키 경보를 변경
				session.setAttribute("sStrUid", uiBean.getUserID()+'|'+uiBean.getDptID());	// 세션에 현재 사용자를 변경
			} else {
				cookProp = cMan.str2prop(cMan.getValue("DM2K"));	// cookie 이름이 DM2K 인 것에 들어있는 값들을 저장
				SCHTERM = cookProp.getProperty("SCHTERM");			// 검색기간
			}

		}else {
			cookProp = cMan.str2prop(cMan.getValue("DM2K"));	// cookie 이름이 DM2K 인 것에 들어있는 값들을 저장
			SCHTERM = cookProp.getProperty("SCHTERM");			// 검색기간
		}
		// 웹어플리케이션을 클러스터링하는 경우는 setAttribute를 해주어야 변경된 내용이 클러스터간 공유됨
		session.setAttribute("uiBean",uiBean);
//		System.out.println("####################### session sync end ############################");
	}
	wl.setUserInfo(uiBean);

////////////////////////////////////////////////////////////////////////////////////////
// 			세션 synchronized 끝
////////////////////////////////////////////////////////////////////////////////////////
	


// DmWordManager를 이용하여 strContentType 이라는 변수를 선언한다. (현재 jsp의 out의 charset에 영향을 주지 못함)
String strContentType = "text/html;charset="+DmWordManager.getEncoding(uiBean.getLanguage());
response.setContentType(strContentType);
response.addHeader("P3P","CP='CAO PSA CONi OTR OUR DEM ONL'");
%>

<%

	Properties infProp = com.sds.acube.dm.jsp.DmInfPropFactory.getInfProp(uiBean.getStrCurDb());
%>



<%
//*** 환경설정 정보를 쿠키에 저장 ***
//=======================================
//	서버 기본값 설정
//=======================================
	if(cookProp == null || cookProp.size() <= 3) {
		try {
			String[] arrUsrInfName = {"SCHTERM","LISTCNT","ORDER","SVRFLESIZE","FLEENCOPT","EDITBODY","LSTINCSUB"};
			String[] arrUsrInfVal = null;
			com.sds.acube.dm.code.DmCodeQueryHome cdqHome = (com.sds.acube.dm.code.DmCodeQueryHome)com.sds.acube.dm.ServiceLocator.getEJBHome("DmCodeQuery");
			com.sds.acube.dm.code.DmCodeQuery cdq = cdqHome.create();
	
			arrUsrInfVal = cdq.getInfVals(arrUsrInfName, uiBean.getStrCurDb());
	
			if(arrUsrInfVal != null && arrUsrInfVal.length == arrUsrInfName.length) {
				SCHTERM = getVal(arrUsrInfVal[0], "14d");										// 검색기간
				cookProp.setProperty("LISTCNT", getVal(arrUsrInfVal[1], "10"));	// 목록갯수
				int iLength = arrUsrInfName.length;
				for(int i=3; i < iLength; i++) {
					cookProp.setProperty(arrUsrInfName[i] , getVal(arrUsrInfVal[i],""));
				}
	
			}
			
	// ***정렬방향/항목을 INF01T에서 가져옴 - 쿠키에서는 LSTSORT와 LSTSORTORD를 분리하지 않음
			String strBuf = null;
			if(arrUsrInfVal != null && arrUsrInfVal[2] != null && arrUsrInfVal[2].length() > 0) {
				strBuf = arrUsrInfVal[2];
			} else {
				strBuf = "isrtdt desc";
			}
/*			if(strBuf.indexOf("ASC") >=0) {
				cookProp.setProperty("LSTSORT", strBuf.substring(0, strBuf.length()-4));
				cookProp.setProperty("LSTSORTORD", "ASC");
			} else {
				cookProp.setProperty("LSTSORT", strBuf.substring(0, strBuf.length()-5));
				cookProp.setProperty("LSTSORTORD", "DESC");
			}
*/

			cookProp.setProperty("LSTSORT", strBuf.toLowerCase());//			소문자로 함
			
				
	// 만약 DB의 UCONFIG에 해당 ID의 환경설정 정보가 없다면 DEFAULT 값을 채워 넣는다.
			cookProp.setProperty("LSTSELCOL1", "USRNAME");
			cookProp.setProperty("LSTSELCOL2", "ISRTDT");
			cookProp.setProperty("APROVEDOC", "7");
			cookProp.setProperty("DELAYEDDOC", "7");
//	EDITBODY 기본 값을 inf01t 에서 가져옴(2002.01.12)
//			cookProp.setProperty("EDITBODY", "NT");
			cookProp.setProperty("SPASSCHTRM", "14");
			cookProp.setProperty("TRASHBIN", "7");
			cookProp.setProperty("V_DOC", "A");
			cookProp.setProperty("V_GUL", "A");
			cookProp.setProperty("V_HWP", "A");
			cookProp.setProperty("V_IMG", "A");
			cookProp.setProperty("V_PPT", "A");
			cookProp.setProperty("V_XLS", "A");
			cookProp.setProperty("ZIP2UPLOAD", "N");
			cookProp.setProperty("OCX", "N");
			cookProp.setProperty("HUNPLUGIN", "N");
			cookProp.setProperty("ENVUPDATED", "N");		//사용자가 환경설정을 바꾸면 반영될 때까지 Y, 반영되면 N
//	out.write("158");		
			com.sds.acube.dm.user.DmUserQueryHome uqHome = (com.sds.acube.dm.user.DmUserQueryHome)com.sds.acube.dm.ServiceLocator.getEJBHome("DmUserQuery");
			com.sds.acube.dm.user.DmUserQuery uq = uqHome.create();
			String[][] arrUcfg= uq.listUcfg(uiBean.getUserID(), "", uiBean.getStrCurDb());
			
			if(arrUcfg != null) {
				String strListSort = null;
				String strListSortOrder = null;
				for(int i=0; i < arrUcfg.length; i++) {
//					System.out.println("[arrUcfg]"+arrUcfg[i][0]+"="+arrUcfg[i][1]);
					if( arrUcfg[i][1] != null && arrUcfg[i][1].length() > 0) {
						if("SCHTERM".equalsIgnoreCase(arrUcfg[i][0])) {
							SCHTERM = arrUcfg[i][1];
						} else if("APROVEDOC".equalsIgnoreCase(arrUcfg[i][0]) && arrUcfg[i][1] != null && arrUcfg[i][1].length() > 3) {		// 설정된 값이 적절하지 않으면 디폴트를 그냥 사용한다. 999를 넘으면 안됨
						} else if("DELAYEDDOC".equalsIgnoreCase(arrUcfg[i][0]) && arrUcfg[i][1] != null && arrUcfg[i][1].length() > 3) {
						} else if("TRASHBIN".equalsIgnoreCase(arrUcfg[i][0]) && arrUcfg[i][1] != null && arrUcfg[i][1].length() > 3) {
						} else if("SPASSCHTRM".equalsIgnoreCase(arrUcfg[i][0]) && arrUcfg[i][1] != null && arrUcfg[i][1].length() > 7) {
						} else if("LISTCNT".equalsIgnoreCase(arrUcfg[i][0]) && arrUcfg[i][1] != null && arrUcfg[i][1].length() > 2) {
						} else if("ZIP2UPLO0AD".equalsIgnoreCase(arrUcfg[i][0]) && arrUcfg[i][1] != null && !"Y".equals(arrUcfg[i][1])) {
						} else if("LSTSORT".equalsIgnoreCase(arrUcfg[i][0])) { strListSort = arrUcfg[i][1]; //System.out.println("아싸");
						} else if("LSTSORTORD".equalsIgnoreCase(arrUcfg[i][0])) { strListSortOrder = arrUcfg[i][1]; //System.out.println("아싸2");
						} else {
							cookProp.setProperty(arrUcfg[i][0], arrUcfg[i][1]);
						}
					}
				}
				if(strListSort != null && strListSort.length() > 0 && strListSortOrder != null && strListSortOrder.length() > 0) {
					cookProp.setProperty("LSTSORT",(strListSort+" "+strListSortOrder).toLowerCase());
				}
			}
			if (SCHTERM != null && SCHTERM.length() > 0) {
				try {
					int iSchterm = Integer.parseInt(SCHTERM);
					int iMonth = 0;
					if (iSchterm > 30) {
						iMonth = iSchterm / 30;
						SCHTERM = iMonth + "m";
					}
				} catch (Exception e) {
				}
			} else {
				SCHTERM = "14d";
			}
			cookProp.setProperty("SCHTERM", SCHTERM);
			printProp(cookProp);
			Cookie cookDM2K = new Cookie("DM2K",cMan.prop2str(cookProp));
			cookDM2K.setPath(strCookiePath);
			response.addCookie(cookDM2K);
			
			
			lm.writeLog("cookie["+uiBean.getUserID()+"] was added",CLASS_NAME);
		}catch(Exception e) {
			lm.writeLog(e, "클라이언트에 쿠키 생성 실패", CLASS_NAME);
		}
	}
// 생성된 사용자 정보를 확인한다.
//	for(int i =0 ;i<uiBean.getUserInfoLength();i++) {
//		System.out.println("uiBean.arrInfo["+i+"] = "+uiBean.arrInfo[i]+"<br>");
//	}

// 페이지 정보를 보기 위해 Exception을 발생시키기
	String tmpPageString = request.getParameter("PAGE");
	if("TEST".equals(tmpPageString)) {
		tmpPageString = null;
		int i = tmpPageString.length();
	}
		
	
%>


<%-- 공통으로 쓰는 메소드와 전역변수를 선언한다. 일반적으로 전역변수는 그다지 권장하지 않는다.--%>
<%!
	String getVal(String str, String defStr)
	{
		return str == null ? defStr : str;
	}
	
	
	String getInfData(String strParam, String strCntDb) {
		String strInfo=null;
		try {
			com.sds.acube.dm.code.DmCodeQueryHome cqHome = (com.sds.acube.dm.code.DmCodeQueryHome)com.sds.acube.dm.ServiceLocator.getEJBHome("DmCodeQuery");
			com.sds.acube.dm.code.DmCodeQuery cq = cqHome.create();
			strInfo = cq.getInfVal(strParam, strCntDb);
		} catch(Exception e) {
			strInfo = null;
		}
		return strInfo;
	}

	String[] getInfData(String[] arrParam, String strCntDb) {
		String[] arrInfo=null;
		try {
			com.sds.acube.dm.code.DmCodeQueryHome cqHome = (com.sds.acube.dm.code.DmCodeQueryHome)com.sds.acube.dm.ServiceLocator.getEJBHome("DmCodeQuery");
			com.sds.acube.dm.code.DmCodeQuery cq = cqHome.create();
			arrInfo = cq.getInfVals(arrParam, strCntDb);
		} catch(Exception e) {
			arrInfo = null;
		}
		return arrInfo;
	}
%>	 
		
<%!
	void printProp(Properties prop) {
		com.sds.acube.dm.LogManager lm = com.sds.acube.dm.LogManager.getInstance();		// Log를 기록할 변수 설정
		Enumeration enum = prop.propertyNames();
		String strName=null;
		StringBuffer sb = new StringBuffer("\nProperties \n");
		while(enum.hasMoreElements()) {
			strName = (String)enum.nextElement();
			try{
				sb.append('\t');
				sb.append(strName);
				sb.append(" = ");
				sb.append(prop.getProperty(strName));
				sb.append('\n');
			}catch(Exception e) {
			}
		}
		lm.writeLog(sb.toString(),this.getClass().getName());
	}

%> 
