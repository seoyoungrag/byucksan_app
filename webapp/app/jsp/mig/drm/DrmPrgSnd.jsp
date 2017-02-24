<%------------------------------------------------------------------------------------
 *
 * ��   ��  �� : �� �� �� 
 *
 * ���α׷� �� : DrmPrgSnd.jsp
 *
 * ��       �� :FSD�� ������ ���� �޾� �ش� ������� ���� ������ �����Ѵ�.
 *
 *
 * ���� ������ : 2002/3/27
 *
 * ���� ������ : 0000/00/00
 *
 * ��   ��  �� : 
 *				- ��������
 * ��       �� : ver 1.0
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
//DB���� �������� ���� �۾�    
String dmHome=System.getProperty("dm.home");
if(dmHome == null)
	dmHome = ".";
File dmh=new File(dmHome);	// dmHome�� ����Ű�� File
try {
	DbInfoParser dip= new DbInfoParser(dmHome+(dmHome.charAt(dmHome.length() -1) == File.separatorChar ? "" : File.separator)+dmDataSourcesXML);
	dbInfo = dip.getDbInfo(new InitialContext());			
}catch(Exception e) {
	System.out.println("Get DB name Exception");	
}


//�ļ����� ������ �̺�Ʈ�� 
String P_VIEW  = "2";
String P_SAVE  = "3";
String P_PRINT = "6";
String P_ENCSAVE  = "12";

String varPrgInfo[][];	//'��������
int i;

// ������ �߰� ������ �Ѱ��� �������� �޾ƿ�
String rcsURL=request.getParameter("rcsURL"); 
// ȫ�¹� �߰� ��ȣȭ�Ǿ� �Ѿ�� ID�� ��ȣȭ
String S_EncUserId = request.getParameter("LOGONID");
String S_userid = DecodeBySType(S_EncUserId);
//String S_userid = request.getParameter("LOGONID");
//������� ����
String S_contentid = request.getParameter("CONTENTID");
// ȫ�¹� �߰� ��ȣȭ�Ǿ� �Ѿ�� purpose���� ��ȣȭ
String S_EncPurpose = request.getParameter("PURPOSE");
String S_purpose = DecodeBySType(S_EncPurpose);
//String S_purpose = request.getParameter("PURPOSE"); //'View = 1, Save = 2, Print= 6 
//������� ����
String S_transactid = request.getParameter("TRANSACTID");
String S_docid;
int    S_Prg;
String S_Right_Result = "0";
//System.out.println("S_contentid:"+S_contentid);
//System.out.println("S_userid:"+S_userid);


/*
'--------------------------------
'   S_Prg ���Ѽ���
'	10: �����ȸ
'	13: �����⺻���� ��ȸ
'	15: ������ȸ
'	20: ������ȸ+�μ�
'	25: ������ȸ+�ٿ�ε�
'	27: �⺻���� ����
'	28: �⺻���� + ������� ����
'	30: ��������
'	35: ��������
'	40: �������Ѽ���
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
varPrgInfo[i][0] = S_docid; //�������̺��� docid�� ���Ѵ�. 
	varPrgInfo[i][1] = "25"; //�������� 25�� �⺻���� �����Ѵ�.
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
for (int u=0; u < varPrgInfo.length; u++)  //u�� 0�̻��� ���̽���?
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
       System.out.println("***Unidentified error *****"); //error �˼����� ���� 
      }

} //end of if(S_docid != null) 
else 
{
     S_Right_Result = "0"; //Fasoo �� �����Ͽ� �߰��ڵ� ���� �� �ִ�.
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

