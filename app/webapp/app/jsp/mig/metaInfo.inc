<%-----------------------------------------------------------------------------
 *	
 * 작   성  자 : 조 재 혁
 *
 * 프로그램 명 : metaInfo.inc
 *
 * 설       명 : 모든 프로그램에서 <head></head>사이에 include 해서 쓰는 공통 부분.
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
 * Copyright : Copyright (C) 2001 by SAMSUNG SDS co.,Ltd. All right reserved.
 *				  
-----------------------------------------------------------------------------%>
<META NAME="Version" CONTENT="<%=envProp.getProperty("version")%>">
<META NAME="Build Date" CONTENT="<%=envProp.getProperty("build-date")%>">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="<%=strContentType%>">
<META NAME="Author" CONTENT="SAMSUNG SDS CO., LTD.">
<META NAME="Application" CONTENT="<%=envProp.getProperty("application")%>">
<SCRIPT LANGUAGE="JAVASCRIPT">
   var JSP	 = '<%=JSP%>';
   var ENV = '<%=ENV%>';
   var IMAGE = '<%=IMAGE%>';
   var HTML	= '<%=HTML%>';
   var SERVLET	= '<%=SERVLET%>';
</SCRIPT>	
					
