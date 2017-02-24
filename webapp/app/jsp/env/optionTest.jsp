<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.ibm.as400.access.AS400"%>
<%@page import="com.ibm.as400.access.AS400Text"%>
<%@page import="com.ibm.as400.access.ProgramCall"%>
<%@page import="com.ibm.as400.access.ProgramParameter"%>

<%@page import="com.sds.acube.app.common.vo.UserVO"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>

<%@ include file="/app/jsp/common/header.jsp"%>


<%
	// SMS 전송 테스트용 페이지
	String AS400_URL = "192.168.31.44";
    String AS400_USERNAME = "KDBC";
    String AS400_PASSWORD = "system";
    AS400 sys = new AS400(AS400_URL,AS400_USERNAME,AS400_PASSWORD);
    
    String charSet = request.getParameter("charSet");
    String msg = messageSource.getMessage("alarm.message.title.I1" , null, langType);
    if (charSet != null && !"".equals(charSet)) {
    	msg = new String(msg.getBytes("UTF-8"), charSet);
    } else {
		msg = "APP Send SMS Test..";
    }
    String errCode = "";
    boolean result = false;
    
    try {
    
	    AS400Text txt7 = new AS400Text(7);
	    AS400Text txt12 = new AS400Text(12);
	    AS400Text txt10 = new AS400Text(10);
	    AS400Text txt8 = new AS400Text(8);
	    AS400Text txt2 = new AS400Text(2);
	    AS400Text txt200 = new AS400Text(200);
	    
	    ProgramParameter[] parmList = new ProgramParameter[7];
	    
	    parmList[0] = new ProgramParameter( txt7.toBytes("2007089"),7);	// 사번
	    parmList[1] = new ProgramParameter( txt12.toBytes("01028116527"),12); // 수신 전화번호
	    parmList[2] = new ProgramParameter( txt12.toBytes("01028116527"),12);	// 송신전화번호
	    parmList[3] = new ProgramParameter( txt10.toBytes("2011-08-24"),10);	// 전송일 yyyy-mm-dd
	    parmList[4] = new ProgramParameter( txt8.toBytes("11.10.00"),8);	// 전송시간 : hh.mm.ss	    
	    parmList[5] = new ProgramParameter( txt2.toBytes(errCode),2);	// 전송시에는 공백문자
	    parmList[6] = new ProgramParameter( txt200.toBytes(msg),200);	// 전송 메시지
	
	    // AS400 호출
	    ProgramCall pgm = new ProgramCall(sys,"/QSYS.LIB/@KDBC.LIB/KCSM220R.PGM",parmList);
	    
	    if (pgm.run() != true) {			
	    } else{	
			errCode = (String)txt2.toObject( parmList[5].getOutputData()) ;	          
	
			// 성공시 : "Y " 실패시 "E1"  
			if("E1".equals(errCode)){
			} else {
			    result = true;
			}	
	    }
	    
	    // 접속종료
	    sys.disconnectService(AS400.COMMAND);
	    
	} catch(Exception e){
	}
	


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">

<script type="text/javascript">

</script>
</head>
<body>
test success .....
</body>
</html>