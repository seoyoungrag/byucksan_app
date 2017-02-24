<%------------------------------------------------------------------------------------
 *
 * 작   성  자 : 이 상 기 
 *
 * 프로그램 명 : DrmPrgSnd.jsp
 *
 * 설       명 :FSD로 부터의 값을 받아 해당 사용자의 문서 권한을 점검한다.
 *
 *
 * 개발 시작일 : 2002/3/27
 *
 * 개발 종료일 : 0000/00/00
 *
 * 수   정  일 : 
 *				- 수정사유
 * 버       젼 : ver 1.0
 *
 * Copyright notice : Copyright (C) 2001 by SAMSUNG SDS co.,Ltd. All right reserved.
 *
------------------------------------------------------------------------------------%>
<%@ page import = "javax.naming.InitialContext, javax.rmi.PortableRemoteObject, java.lang.*, java.util.*, com.sds.acube.dm.*, com.sds.acube.dm.code.*, com.sds.acube.dm.jsp.*, com.sds.acube.dm.cnt.*, com.sds.acube.dm.user.*, com.sds.acube.dm.jsp.WebUtil.*, com.sds.acube.dm.prg.*, com.sds.acube.dm.log.*, com.sds.acube.dm.doc.*,com.sds.acube.dm.drm.*,com.sds.acube.dm.DbInfo.*"%>
<%@ include file="/jsp/drm/CM_Enc.jsp"%>
<HTML>
<HEAD>

<%!
protected static DbInfo[] dbInfo;
private static final String dmDataSourcesXML = "dm-data-sources.xml";
%>
<%
//DB명을 가져오기 위한 작업    
String dmHome=System.getProperty("dm.home");
if(dmHome == null)
	dmHome = ".";
File dmh=new File(dmHome);	// dmHome을 가리키는 File
try {
	DbInfoParser dip= new DbInfoParser(dmHome+(dmHome.charAt(dmHome.length() -1) == File.separatorChar ? "" : File.separator)+dmDataSourcesXML);
	dbInfo = dip.getDbInfo(new InitialContext());			
}catch(Exception e) {
	System.out.println("Get DB name Exception");	
}


//파수에서 정의한 이벤트값 
String P_VIEW  = "2";
String P_SAVE  = "3";
String P_PRINT = "6";
String P_ENCSAVE  = "12";

String varPrgInfo[][];	//'권한정보
int i;

// 김지은 추가 정보를 넘겨줄 페이지를 받아옴
String rcsURL=request.getParameter("rcsURL"); 
// 홍승민 추가 암호화되어 넘어온 ID를 복호화
String S_EncUserId = request.getParameter("LOGONID");
String S_userid = DecodeBySType(S_EncUserId);
//String S_userid = request.getParameter("LOGONID");
//여기까지 수정
String S_contentid = request.getParameter("CONTENTID");
// 홍승민 추가 암호화되어 넘어온 purpose값을 복호화
String S_EncPurpose = request.getParameter("PURPOSE");
String S_purpose = DecodeBySType(S_EncPurpose);
//String S_purpose = request.getParameter("PURPOSE"); //'View = 1, Save = 2, Print= 6 
//여기까지 수정
String S_transactid = request.getParameter("TRANSACTID");
String S_docid;
int    S_Prg;
String S_Right_Result = "0";
//System.out.println("S_contentid:"+S_contentid);
//System.out.println("S_userid:"+S_userid);


/*
'--------------------------------
'   S_Prg 권한설정
'	10: 목록조회
'	13: 문서기본정보 조회
'	15: 문서조회
'	20: 문서조회+인쇄
'	25: 문서조회+다운로드
'	27: 기본정보 수정
'	28: 기본정보 + 요약정보 수정
'	30: 문서수정
'	35: 문서삭제
'	40: 문서권한수정
'--------------------------------
*/
InitialContext jndiContext1;
jndiContext1 = new InitialContext();

Object ref1 = jndiContext1.lookup("java:comp/env/ejb/DrmMapQuery");
//Object ref1 = jndiContext1.lookup("DrmMapQuery");
DrmMapQueryHome drmHome= (DrmMapQueryHome)PortableRemoteObject.narrow(ref1, DrmMapQueryHome.class);
DrmMapQuery drm= drmHome.create();
//String iRes=drm.qryMap(S_contentid,"longhorn");
String iRes=drm.qryMap(S_contentid,dbInfo[0].getDb());
S_docid = iRes;

if(S_docid != null) {

i=0;
varPrgInfo = new String[1][5];    
varPrgInfo[i][0] = S_docid; //매핑테이블에서 docid를 구한다. 
	varPrgInfo[i][1] = "25"; //문서권한 25를 기본으로 세팅한다.
	varPrgInfo[i][2] = "";
	varPrgInfo[i][3] = "0";
	varPrgInfo[i][4] = "";


InitialContext jndiContext = new InitialContext();
Object ref = jndiContext.lookup("java:comp/env/ejb/DmPrgQuery");
DmPrgQueryHome cqHome = (DmPrgQueryHome)PortableRemoteObject.narrow(ref, DmPrgQueryHome.class);
DmPrgQuery cq = cqHome.create();

//varPrgInfo = cq.getPrgInfo(S_userid, varPrgInfo,"longhorn");
varPrgInfo = cq.getPrgInfo(S_userid, varPrgInfo,dbInfo[0].getDb());


/*System.out.println("***varPrgInfo *****");
System.out.println("transactid: "+ S_transactid);
System.out.println("S_docid: "+ S_docid);
System.out.println("purpose: "+ S_purpose);
for (int u=0; u < varPrgInfo.length; u++)  //u가 0이상이 케이스는?
	System.out.println("178 " + Integer.toString(u) + " : 0 " + varPrgInfo[u][0] + ", 1 " + varPrgInfo[u][1] + ", 2 " + varPrgInfo[u][2] + ", 3 " + varPrgInfo[u][3] + ", 4 " + varPrgInfo[u][4] + ";");
*/	
	
	
S_Prg = Integer.parseInt(varPrgInfo[0][3]);


if(S_purpose.equals(P_VIEW))
    if(S_Prg >= 13)
       S_Right_Result = "1";
    else 
       S_Right_Result = "0";
else if(S_purpose.equals(P_PRINT))
    if(S_Prg >= 20)
       S_Right_Result = "1";
    else 
       S_Right_Result = "0";
else if(S_purpose.equals(P_SAVE))
    if(S_Prg >= 27)
       S_Right_Result = "1";
    else 
       S_Right_Result = "0";
else if(S_purpose.equals(P_ENCSAVE))
    if(S_Prg >= 27)
       S_Right_Result = "1";
    else 
       S_Right_Result = "0";       
else {S_Right_Result = "0";
       System.out.println("***Unidentified error *****"); //error 알수없는 권한 
      }

} //end of if(S_docid != null) 
else 
{
     S_Right_Result = "0"; //Fasoo 와 협의하에 추가코드 넣을 수 있다.
     System.out.println("*****S_Docid == null ****** ");
}

//System.out.println("S_Right_Result:"+S_Right_Result);
%>

</HEAD>
<SCRIPT language="vbscript">
function PrgSnd()
        drmform1.RIGHTS_RESULT.value="<%=S_Right_Result%>"
        drmform1.TRANSACTID.value= "<%=S_transactid%>" 
        drmform1.submit()
end function
</SCRIPT>

<body onload="PrgSnd()">
<FORM name="drmform1" action="<%=rcsURL%>" method="post">
<input type="hidden" name="RIGHTS_RESULT" value="">
<input type="hidden" name="TRANSACTID" value="">
</form>

</BODY>
</HTML>

