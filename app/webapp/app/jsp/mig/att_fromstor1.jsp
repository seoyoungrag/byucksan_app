<%------------------------------------------------------------------------------------
 *	
 * 작   성  자 : 허 준 혁
 *
 * 프로그램 명 : att_fromstor.jsp
 *
 * 설       명 : 첨부파일을 STOR엔진에서 가져와서 웹서버의 TMP에 저장을 시킨 후 그것을 클라이언트로 가져온다.
 *				 
 *
 * 개발 시작일 : 2001/11/24
 *
 * 개발 종료일 : 0000/00/00
 *
 * 수   정  일 : 0000/00/00(수정자 : ) 
 *				- 수정사유()
 * 버       젼 : ver 1.0
 *
 * Copyright notice : Copyright (C) 2001 by SAMSUNG SDS co.,Ltd. All right reserved.
 *				  
------------------------------------------------------------------------------------%>
<%@ page import = "javax.naming.InitialContext, javax.rmi.PortableRemoteObject, java.util.*, 
                              com.sds.acube.dm.*, com.sds.acube.dm.code.*, com.sds.acube.dm.jsp.*, 
                              com.sds.acube.dm.cnt.*, com.sds.acube.dm.user.*, com.sds.acube.dm.list.*, 
                              com.sds.acube.dm.doc.*, java.io.*, java.io.BufferedWriter.*, java.io.BufferedReader.*, 
                              com.sds.acube.pdf.*, com.sds.acube.dm.drm.*"%>
<%
/*
'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
' it download from store server to web server
'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
*/
%>
<%@ include file="/jsp/usr_include.inc"%>
<%
	Properties rsc = DmWordManager.getProperties(uiBean.getLanguage(), "com.sds.acube.dm.rsc.att_list");
	String sLang = null;
	sLang = uiBean.getLanguage();
	boolean	bdemo = uiBean.getIsDemo();		// demo flag
   String TMP = ( envProp.getProperty("TmpDir-Http") == null ) ? "" : envProp.getProperty("TmpDir-Http");

   Character ch29 = new Character((char)29);
   String GS = ch29.toString();
%>
<HTML>
<HEAD>
<%@ include file="/jsp/metaInfo.inc"%>

<SCRIPT SRC='<%=ENV%>/script/dms.js'></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src='<%=ENV%>/rsc/attach.<%=sLang%>.js'></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src='<%=ENV%>/script/attach.js'></SCRIPT>

<SCRIPT>
var diwin = null ;  
var mgrwin = null ; 
function chkchildwin(){
	if (diwin != null) 
		diwin.close();
	if (mgrwin != null) 
		mgrwin.close();
}
</SCRIPT>
</HEAD>

<BODY>
<%
String sDocId = null;
String sFolderId = null;
String sFileCnt = null;
String sOpType = null;
String sDel = null;

// add by junhyuk 2002-07-05 for DRM : MarkAny
String sDrmExpire = null;
String sMaxPrg = null;


String sTempPath = null;

String varFleInfo[][] = null;
String varFile[][] = null;
String varNewFile[][] = null;

String varFleId[] = null;
String varFleName[] = null;
String varFleTtl[] = null; 

int lRet = -1;

int lOpenMode = 0;		/*이값이 1인 경우만 자동으로 연다. 그외는 다운로드해서 보여주는 것이 아닌 다른 과정이다. : 에러처리 과정 */

String sOut = null;				// 서버 경로상의 결재용 XML저장 위치
String sXmlFilePath = null;		// 웹상의 결재용 XML저장 위치


String varFleType, varFleGubun;	// 2002-03-08 KTF 결재 정보처리를 위한 첨부파일 정보
String varDocTypeNM = null;		// 2002-03-08 KTF 결재 정보처리를 위한 첨부파일 정보


// DmList Initialize
InitialContext jndiContext = new InitialContext();
Object ref = jndiContext.lookup("java:comp/env/ejb/DmListQuery");
DmListQueryHome lqHome = (DmListQueryHome)PortableRemoteObject.narrow(ref, DmListQueryHome.class);
DmListQuery lq = lqHome.create();
/////////////////////////////////////

// Temp Path의 Path표시가 \이면 마지막에 \을 붙이고 /이면 /을 붙임
// 현실적으로 /만 사용해도 되나 일단 변환
// TmpDir경로를 XML이 아닌 DmList의 getTempDirPath를 이용하는 것으로 바꾸었음.
try 
{
	sTempPath = lq.getTempDirPath();
}
catch ( Exception e )
{
	sTempPath = "";
}

if (sTempPath.indexOf("/") == -1)		/*고육지책으로 마련한 방법 : 마지막에 /,\를 붙이는 과정*/
{
	if (sTempPath.endsWith("\\") == false)
		sTempPath = sTempPath + "\\\\";
}
else 
{	 
	if (sTempPath.endsWith("/") == false)
		sTempPath = sTempPath + "/";
}
//////////////////////////////////////////////////////////

InitialContext jndiContext3 = new InitialContext();
Object ref3 = jndiContext3.lookup("java:comp/env/ejb/DmDocDelete");
DmDocDeleteHome ddHome = (DmDocDeleteHome)PortableRemoteObject.narrow(ref3, DmDocDeleteHome.class);
DmDocDelete dd = ddHome.create();

sDel = (uiBean.encodeByLocale(request.getParameter("DEL")) == null) ? "" : uiBean.encodeByLocale(request.getParameter("DEL"));
sOpType = (uiBean.encodeByLocale(request.getParameter("TYPE")) == null) ? "" : uiBean.encodeByLocale(request.getParameter("TYPE"));
sFileCnt = (uiBean.encodeByLocale(request.getParameter("FILECNT")) == null) ? "1" : uiBean.encodeByLocale(request.getParameter("FILECNT"));
sFolderId = (uiBean.encodeByLocale(request.getParameter("FOLDERID")) == null) ? "" : uiBean.encodeByLocale(request.getParameter("FOLDERID"));
sDocId = (uiBean.encodeByLocale(request.getParameter("DOCID")) == null) ? "" : uiBean.encodeByLocale(request.getParameter("DOCID"));

// 2002-07-05 add & modify by junhyuk for DRM
sMaxPrg = (uiBean.encodeByLocale(request.getParameter("MAXPRG")) == null) ? "0" : uiBean.encodeByLocale(request.getParameter("MAXPRG"));	// For MarkAny DRM Inserted By Sammy 2002.5.29				
sDrmExpire = (uiBean.encodeByLocale(request.getParameter("DRMEXPIRE")) == null) ? "999" : uiBean.encodeByLocale(request.getParameter("DRMEXPIRE"));	// For MarkAny DRM Inserted By Sammy 2002.5.29	
if(sDrmExpire == null || sDrmExpire.equals("") || sDrmExpire.equals("null"))
{
   sDrmExpire = "999";
}
else
{
   sDrmExpire = uiBean.encodeByLocale(request.getParameter("DRMEXPIRE"));
}
///////////////////////////////////////////////////////


if (!bdemo)
{
	 
}

varFleId = WebUtil.split( (uiBean.encodeByLocale(request.getParameter("FILEID")) == null) ? "" : uiBean.encodeByLocale(request.getParameter("FILEID")), GS);
varFleName = WebUtil.split( (uiBean.encodeByLocale(request.getParameter("FILENAME")) == null) ? "" : uiBean.encodeByLocale(request.getParameter("FILENAME")), GS);
varFleTtl = WebUtil.split( (uiBean.encodeByLocale(request.getParameter("FILETTL")) == null) ? "" : uiBean.encodeByLocale(request.getParameter("FILETTL")), GS);
//varDocTypeNM = (uiBean.encodeByLocale(request.getParameter("DOCTYPENM")) == null) ? "" : uiBean.encodeByLocale(request.getParameter("DOCTYPENM"));

// 2003-03-12 add by junhyuk
sFileCnt = Integer.toString( WebUtil.split(request.getParameter("FILEID"), GS, false).length );
////////////////////////////////

varFleInfo = new String[Integer.parseInt(sFileCnt)][2];	// 배열의 차례가 바뀜
varFile = new String[Integer.parseInt(sFileCnt)][4];	      // 배열의 차례가 바뀜



if (!bdemo)
{
	/* Test로 찍는 메시지로 판단
	
}

//varFleName[0] = "C0C40756488741A69D9BDF6D9CFE11B7";
//FILENAME : !@#$%';=+-_)(&^.~`''%$''.gul<BR>
//FILETTL : C0C40756488741A69D9BDF6D9CFE11B7.gul<BR>
// 2002-04-10 add by junhyuk
// FILENAME에는 확장자가 있으나 FILETTL에서는 없는 경우가 많다..
// 따라서 FILETTL의 확장자가 없을 경우 FILENAME의 확장자를 때어서 붙인다. 
// FILETTL과 FILENAME의 개념이 DB의 개념과 반대이다...
for (int i = 0; i < Integer.parseInt(sFileCnt) ; i++)
{
	int iPos 		=	varFleName[i].lastIndexOf(".");
	String strExt	=	null;

	if (iPos > 0) {
		strExt	=	varFleName[i].substring(iPos+1, varFleName[i].length());
	} else {
		strExt	=	"";
	}
	if ( varFleName[i].indexOf(".") == -1 || WebUtil.getByteLength(strExt,uiBean.getLanguage()) > 4)
	{
		out.println("No Extention in FileTtl[" + i + "]" + "<BR>");
		int nlindex = varFleTtl[i].lastIndexOf(".");
		if ( nlindex != -1 )
		{
			varFleName[i] = varFleName[i] + varFleTtl[i].substring(nlindex);
			out.println("FileTtl[" + i + "] is changed to " + varFleName[i] + "<BR>");
		}
	}
}
/*반대인 경우
for (int i = 0; i < Integer.parseInt(sFileCnt) ; i++)
{
	if ( varFleName[i].indexOf(".") == -1 )
	{
		out.println("No Extention in varFleName[" + i + "]" + "<BR>");
		int nlindex = varFleTtl[i].lastIndexOf(".");
		if ( nlindex != -1 )
		{
			varFleName[i] = varFleName[i] + varFleTtl[i].substring(nlindex);
			out.println("varFleName[" + i + "] is changed to " + varFleName[i] + "<BR>");
		}
	}
}
*/
////////////////////////////////////////////////////////////////////////////
// 가장 핵심인 부분..getUUIDName
for (int i = 0; i < Integer.parseInt(sFileCnt) ; i++)  /* to FileCnt -1*/
{
   // 2003-03-07 add by junhyuk : 서버상의 파일이름을 UUID로 생성해서 처리해본다.
	//String sUUIDName = varFleTtl[i];    //getUUIDName(varFleTtl[i]);
   String strExtension = null;
   String tmpStr1[] = WebUtil.split(varFleTtl[i], ".");
   if (tmpStr1.length > 1)
   {
      strExtension = tmpStr1[tmpStr1.length-1];
   }
   String sUUIDName = DmGlobal.getUUID().toLowerCase() + (( strExtension == null ) ? "" : "." + strExtension );
	varFile[i][0] = varFleId[i];					//'File ID 배열의 차례가 바뀜
	//varFile[i][1] = varFleTtl[i];
	varFile[i][1] = sUUIDName;
	varFile[i][2] = sTempPath + sUUIDName;//varFleTtl[i];
	varFile[i][3] = varFleName[i];

	varFleInfo[i][0] = varFleId[i];					//'File ID 배열의 차례가 바뀜
	varFleInfo[i][1] = sTempPath + sUUIDName;      //varFleTtl[i];	'File Path
}


// 2002-02-26 허준혁 List의 자세히 보기에서 클릭으로 파일View를 시도하는 경우 실제 이름이 아닌 코드 네임이 온다.
// 따라서 DB에서 온전한 이름을 가져와 대치하는 작업이 필요하다.
// 2002-02-27 허준혁 : 목록에서는 인자값을 넘길 때 '나 기타 특수 문자를 다른 형태로 바꾸어서 보냄..
// 이 문제는 확장자에서 그러한데 이러한 문제를 보강한다.
if ( varFleName[0].equals(varFleTtl[0]) )
{
	// 2002-03-08 허준혁 : Deprecated 
	// 새로 변수를 추가 : String varFleType, varFleGubun;
	//varFleName[0] = lq.getAttachFileName( varFleId[0], uiBean.getStrCurDb());
	String varTmpFleInfo[] = new String[3];
	try
	{
		varTmpFleInfo = lq.getAttachFileInfo( varFleId[0], uiBean.getStrCurDb());
		varFleName[0] = varTmpFleInfo[0];
	}
	catch ( Exception e )
	{
		lm.writeLog("att_fromstor.jsp - Fail in getAttachFileInfo : " + varFleId[0], "DmListQueryEJB");

	}
	////////////////////////////////////////////////////////////////////////

	varFile[0][3] = varFleName[0];
	if ( varFile[0][1].lastIndexOf('.') != -1 )
	{
		varFile[0][1] = varFile[0][1].substring(0, varFile[0][1].lastIndexOf('.')) + varFleName[0].substring(varFleName[0].lastIndexOf('.'), varFleName[0].length()); 
	}
	if ( varFile[0][2].lastIndexOf('.') != -1 )
	{
		varFile[0][2] = varFile[0][2].substring(0, varFile[0][2].lastIndexOf('.')) + varFleName[0].substring(varFleName[0].lastIndexOf('.'), varFleName[0].length()); 
	}

	if (!bdemo)
	{
      if ( varFile != null && varFile[0] != null && varFile[0].length > 4 )
      {
		   out.println("Case List : varFile[0][0] - " + varFile[0][0] + "<BR>");
		   out.println("Case List : varFile[0][1] - " + varFile[0][1] + "<BR>");
		   out.println("Case List : varFile[0][2] - " + varFile[0][2] + "<BR>");
		   out.println("Case List : varFile[0][3] - " + varFile[0][3] + "<BR>");
      }
	}

	varFleInfo[0][1] = varFile[0][2];
}
//////////////////////////////////////////////////////////////////////////////////////////////////////


if ( varFleInfo != null )
{
   for (int qq=0; qq < varFleInfo.length; qq++)
   {
      for (int ww=0; ww < varFleInfo[0].length ; ww++)
      {
         
      }
   }
}

if ( varFile != null )
{
   for (int qq=0; qq < varFile.length; qq++)
   {
      for (int ww=0; ww < varFile[0].length ; ww++)
      {
         
      }
   }
}


// 저장 서버에서 TmpDir로 파일을 가져옴
System.out.println("Start of getFiles");
System.out.println("DocId : " + sDocId);
System.out.println("FldId : " + sFolderId);

//이은주가 추가함 20030225 for drm
// 2003-03-14 add by junhyuk
// 특성 상태시에 파일을 받지 않고 에러메시지를 출력하는 방식으로 전환
int nTrueFileCnt = 0;
boolean bDrm = false ; 

int iSecID = 9;
try 
{
	iSecID = Integer.parseInt(uiBean.getConfidentiality());
} 
catch(Exception e) 
{
	lm.writeLog(e, "Invalid uiBean.getConfidentiality()",CLASS_NAME);
	throw e;
}


DrmManager dMan = DrmManagerFactory.getDrmManager(uiBean.getStrCurDb());
bDrm = dMan.useDrm(sDocId, uiBean.getUserID(), iSecID) ;
if ( bDrm==true )   // DRM 문서인 경우
{
   try
   {
      
      
      varFleInfo = lq.getFilesDrmEx(sDocId, sDocId, varFleInfo, uiBean.getStrCurDb());

      if ( varFleInfo != null )
      {         
         
         

         for (int l=0; l< varFleInfo.length ; l++)
         {
            out.println("varFileInfo[" + l + "] = " + varFleInfo[l][0] +" :: "+varFleInfo[l][1] +" :: "+varFleInfo[l][2] );
            System.out.println("varFileInfo[" + l + "] = " + varFleInfo[l][0] +" :: "+varFleInfo[l][1] +" :: "+varFleInfo[l][2] );


            if (Integer.parseInt(varFleInfo[l][0]) == 0)     // DRM Packaging 완료
            {
               nTrueFileCnt++;
               if ( varFleInfo[l][1].endsWith(".fsf") )   // 확장자가 fsf : pdf파일인 경우
               {
                  varFile[l][3] = varFleName[l] +".fsf";    //pdf파일임
               }
               else
               {
                  varFile[l][3] = varFleName[l] +".fsc";    //일반파일임
               }
               varFile[l][2] = varFleInfo[l][1] ;
            }
            else if  (Integer.parseInt(varFleInfo[l][0]) == -26) //이미 fsc로 되어 있는 파일임
            { 
               nTrueFileCnt++;
               varFile[l][3] = varFleName[l] ;
            }
            else if  (Integer.parseInt(varFleInfo[l][0]) == -11)  // 변환을 지원하지 않는 파일 포맷 ==> 모직의 경우 다운로드를 받지 않아야 한다.
            {
               //out.println("<SCRIPT Language=javascript>");
               //out.println("alert(\"" + varFile[l][3] + " 파일은 DRM을 지원하지 않는 파일 포맷을 가지고 있습니다\");");
               //out.println("</SCRIPT>");
               varFile[l][3] = varFleName[l];
               //varFile[l][2] = varFleInfo[l][1] ;
            }
            else                                                                       // 변환 실패 ==> 파일을 다운로드 받지 않아야 한다.
            {
               // varFile[l][2] = null이면 파일 다운을 못받는 것으로 할까?
               // 하나의 경우는 간단하나 여러개의 파일의 경우는 어떻게 할까?
               varFile[l][3] = varFleName[l];
               //varFile[l][2] = varFleInfo[l][1] ;
            }
         }
      }
        
        
   }
   catch ( Exception e )
   {
	   lm.writeLog("att_fromstor.jsp - Fail in getFilesDrmEx", "DmListQueryEJB");
   }
} 
else   // DRM 문서가 아닌 경우
{
   try
   {
	   
      varFleInfo = lq.getFiles(sDocId, varFleInfo, uiBean.getStrCurDb());
      
      nTrueFileCnt = varFile.length;
   }
   catch ( Exception e )
   {
	   lm.writeLog("att_fromstor.jsp - Fail in getFiles", "DmListQueryEJB");
   }
}


// nTrueFileCnt과 sFileCnt가 일치하지 않으면 ==> DRM Packaging에서 문제가 있는 파일이 포함
// 문제1 : 11인지 실패인지 어떻게 구분을 하지? ==> 이것도 달고 다녀야 함..-_-;
//out.println("A(0, " + Integer.toString(i) + ") = \"" + varFile[i][0] + "\"");	//배열의 차례가 바뀜
//out.println("A(1, " + Integer.toString(i) + ") = \"" + varFile[i][3] + "\"");
//out.println("A(2, " + Integer.toString(i) + ") = \"" + varFile[i][2] + "\"");
//////////////////////////////////////////////////

if ( varFleInfo != null )
{
   for (int kk = 0; kk < varFleInfo.length; kk++)
   {
      for (int ll = 0; ll < varFleInfo[0].length; ll++)
      {
         out.println("varFleInfo[" + kk + "][" + ll + "] = " + varFleInfo[kk][ll]);
      }
   }
}

/*
   try
   {
	   //varFleInfo = lq.getFiles(sFolderId, varFleInfo, uiBean.getStrCurDb());
      varFleInfo = lq.getFiles(sDocId, varFleInfo, uiBean.getStrCurDb());
   }
   catch ( Exception e )
   {
	   lm.writeLog("att_fromstor.jsp - Fail in getFiles", "DmListQueryEJB");
   }

**/



///////////////////////////////////////
%>

<!-- DRM Section ACUBE/Security 2002-07-05 add by junhyuk : MaryAny DRM-->
<%@ include file="/jsp/drm/SC_DRMDocEnc.jsp"%>
<!----------------------------------------------------------------------->

<%
lOpenMode = 1;		/*' if dl or mgr file, then didn't open in ocx*/

// 저장 서버에서 다운로드 실패시
if ( varFleInfo == null )	/* varFleInfo에 null을 넣어보고 테스트해보자...*/
{
	/* out.println("<SCRIPT language=javascript>");
	out.print("	alert('" + rsc.getProperty("att11") );
	if ( varFleInfo != null && varFleInfo[0][0] != null )
		out.println(varFleInfo[0][0] + "');");
	else out.println("');");
	out.println("</SCRIPT>"); */
}

//'''''''''''''''''''''''''''''''''''''''''''''''''''''''
//' if user want to open only one file and file extension is mgr or dl,
//' call COMMUNICATION cgi to show information 
//'''''''''''''''''''''''''''''''''''''''''''''''''''''''
// sOpType is "F" Case : 정보보기 문서로 해당 파일을 다운받는 것이 아니라 특정 뷰어나 PopUp으로 보여주는 것이 목적
//                      OM의 결재정보(MGR)이나 메일수신처정보(DL) 및 mySingle의 결재정보(MGR.XML)와 메일수신처정보(RECEIVEINFO.XML)까지 처리
//                      varDocTypeNM is "A", "E", "G" Case : 새로 만들어진 OM의 정보보기 문서 - KTF 및 영산대 적용
// sOpType is "PDFVIEW" Case : 2002-10-25 추가, PDF 문서 보기용으로 독자 처리 요망.
if ( sOpType.equalsIgnoreCase("F") && varFleInfo != null && sFileCnt.equalsIgnoreCase("1") )
{
	String varInfoPath[][] = new String[1][2];	/*배열의 차례가 바뀜*/
	String sTmpCase = null;
	String sDefaultMode = (infProp.getProperty("LINKSYSTEM") == null) ? "" : infProp.getProperty("LINKSYSTEM");
	int nConnID = 0;
	int lResult = 0;

	sTmpCase = varFile[0][3].toUpperCase() ;		// Is It Right?3
	
     
	/* .MGR파일과 .DL파일의 경우 */
	if (sDefaultMode.equals("")&& sTmpCase.endsWith(".MGR") )	
	{
		// 그룹 결재 문서 보기의 경우 : 우리쪽의 Data를 OM으로 올린 후 OM의 URL에 경로를 알리고 보기 페이지를 부른다.
		
		//varInfoPath[0][0] = varFile[0][2];			// change 2 to 3

		//varInfoPath[0][1] = omProp.getProperty("APRDOWN") + "/" + varFile[0][1];	// 차후에 SFTP에서 지원하는 OM쪽 SFTP경로 받아오기 이용.

		/*iq.unCompress(varInfoPath[0][0]);*/
		/*unCompress 관련은 ActiveX에서 처리하도록 한다. --> 서버단에서 처리하므로 막는다.*/
		// 2002-05-03 허준혁 : 압축이 되어 있을 경우 자동으로 푼다.
		varInfoPath[0][0] = unCompressFile2UUID(varFile[0][2], "mgr");

		//GetBaseDir 받아오기
		String strBaseDir = "";
		try
		{
         // STOR, SFTP시리즈를 가져오는 방식은 다음과 같다.
         jsftpnative.JSFTPNative jSFTP = new jsftpnative.JSFTPNative();

			nConnID = jSFTP.JSFTP_Connect( uiBean.getOMIP(), 7775 );
			if (nConnID < 0) { //연결실패
				
			}
			else 
			{
				int iRet = jSFTP.JSFTP_GetBaseDir(nConnID, "OFFICE_BASE_DIR");
				if (iRet < 0) 
				{
					//getBaseDir받아오기 실패
					
				} 
				else 
				{
					strBaseDir = jSFTP.JSFTP_getBaseDirValue();	
					
					strBaseDir = eraseNullChar(strBaseDir);
				}
			}

			if ( strBaseDir.endsWith("/") )
				varInfoPath[0][1] = strBaseDir + varFile[0][1];
			else varInfoPath[0][1] = strBaseDir + "/" + varFile[0][1];
			//////////////////////////////////////////////////////////////////////////

			lResult = jSFTP.JSFTP_UploadEx(nConnID, 1, varInfoPath, 0, 0);
			jSFTP.JSFTP_Disconnect(nConnID);
		}
		catch (Exception e)
		{
			
		}		
		%>
			<SCRIPT language=javascript>
				mgrwin = window.open('<%=uiBean.getOMURL()%>'+'/htdocs/korean/mgrviewframe.html?MGRFILE=' + '<%=varInfoPath[0][1]%>', 'MgrWindow', 'width=510, height=500, status=yes, scrollbars=yes, screenX=1,left=1,screenY=1,top=1');
			</SCRIPT>
		<%
		lOpenMode = 0;
	}
	else if  (sDefaultMode.equals("")&& sTmpCase.endsWith(".DL") )	
	{
		// 결제나 메일시 관계자 보기 : 우리쪽의 Data를 OM으로 올린 후 OM의 URL에 경로를 알리고 보기 페이지를 부른다.
		
		//varInfoPath[0][0] = varFile[0][2];		// change 2 to 3
		
		//varInfoPath[0][1] = omProp.getProperty("APRDOWN") + "/" + varFile[0][1];	// 차후에 SFTP에서 지원하는 OM쪽 SFTP경로 받아오기 이용.

		/*iq.unCompress(varInfoPath[0][0]);*/
		/*unCompress 관련은 ActiveX에서 처리하도록 한다. --> 서버단에서 처리하므로 막는다.*/
		// 2002-05-03 허준혁 : 압축이 되어 있을 경우 자동으로 푼다.
		varInfoPath[0][0] = unCompressFile2UUID(varFile[0][2], "dl");

		//GetBaseDir 받아오기
		String strBaseDir = "";

		try
		{
         // STOR, SFTP시리즈를 가져오는 방식은 다음과 같다.
         jsftpnative.JSFTPNative jSFTP = new jsftpnative.JSFTPNative();

			nConnID = jSFTP.JSFTP_Connect( uiBean.getOMIP(), 7775 );
			if (nConnID < 0) { //연결실패
				
			}
			else 
			{
				int iRet = jSFTP.JSFTP_GetBaseDir(nConnID, "OFFICE_BASE_DIR");
				if (iRet < 0) 
				{
					//getBaseDir받아오기 실패
					
				} 
				else 
				{
					strBaseDir = jSFTP.JSFTP_getBaseDirValue();	
					
					strBaseDir = eraseNullChar(strBaseDir);
				}
			}

			if ( strBaseDir.endsWith("/") )
				varInfoPath[0][1] = strBaseDir + varFile[0][1];
			else varInfoPath[0][1] = strBaseDir + "/" + varFile[0][1];
			//////////////////////////////////////////////////////////////////////////

			lResult = jSFTP.JSFTP_UploadEx(nConnID, 1, varInfoPath, 0, 0);
			jSFTP.JSFTP_Disconnect(nConnID);
		}
		catch (Exception e)
		{
			
		}		
		%>
			<SCRIPT language=javascript>
				diwin = window.open('<%=uiBean.getOMURL()%>'+'/htdocs/korean/dlviewframe.html?ADDR_HIDFILE='+'<%=varFile[0][1]%>', 'DIWindow', 'width=570, height=320, status=1, scrollbars=yes, screenX=1,left=1,screenY=1,top=1');
			</SCRIPT>
		<%
		lOpenMode = 0;
	}
	// 2002-02-26 허준혁 : 결재 XML 문서일 경우 in 그룹EP
	// 2002-06-03 허준혁 : 메일 XML 문서일 경우 in 그룹EP
   // 2002-12-16 add by junhyuk : 결재 XML 문서일 경우 in mySingle V3
	else if ( sTmpCase.endsWith(".MGR.XML") || sTmpCase.equalsIgnoreCase("RECEIVEINFO.XML") || sTmpCase.equalsIgnoreCase("TRACEINFO.XML") )
	{
		// 결재 XML 문서일 경우 in 그룹EP : 정보를 담은 XML에 XSL의 경로를 알려주는 한 줄의 데이터를 추가한 후 XML을 열면 자동으로 XSL과 연결된다.
		// 따라서 XML은 웹에서 접근을 위해 웹접근이 가능한 디렉토리로 BufferedWriter하면서 수정
		// 메일 XML 문서일 경우 기본적으로 동일 : 단 EPXSL이 아닌 EPXSLMAIL을 사용한다.
		
			
		GUID tGUID = new GUID();
		////sOut = System.getProperty("dm.home") + "/web/html/tmp/" + tGUID.toString() + ".xml";	//varFile[0][1];
		//sOut = "/data1/dmsjava" + "/dmstest/web/html/tmp/" + tGUID.toString() + ".xml";
		////sXmlFilePath = HTML + "/tmp/" + tGUID.toString() + ".xml";		// sOut의 웹접근 경로
      // 2002-11-19 Get New Property of TMP
      sOut = sTempPath + tGUID.toString() + ".xml";
      //sXmlFilePath = TMP + "/" + tGUID.toString() + ".xml";       // sOut의 웹접근 경로 ==> 과거 방식 : 서블릿 방식으로 변경
      DmViewUtil dmViewUtil = new DmViewUtil();
      sXmlFilePath = dmViewUtil.getHostURI(request) + SERVLET + "/tmp/" + tGUID.toString() + ".xml" + "?filepath=" + sOut;

		
		

		try
		{
			//BufferedReader rFile = new BufferedReader(new FileReader(varFile[0][2]));	// change 2 to 3
			// 2002-05-03 허준혁 : 압축이 되어 있을 경우 자동으로 푼다.

         // 2002-09-17 허준혁 : charSet을 이용하여 InputStreamReader의 Encoding을 처리하여 1Byte로 설정한 서버에서 2Byte처리를 가능하도록 한다.
         // 기존의 File I/O 방식을 변경
         String charSet = DmWordManager.getEncoding(sLang);
         int BUFFER_SIZE = 1024*50;
         

         //BufferedReader rFile = new BufferedReader(new FileReader(unCompressFile2UUID(varFile[0][2], "mgr.xml")));
         File ifile = new File(unCompressFile2UUID(varFile[0][2], "mgr.xml"));
         
         // 임시 추가
         
         try
         {
            TextFile.encode(varFile[0][2], "utf-8", System.getProperty("file.encoding"));
         }
         catch (Exception e)
         {
            
         }
         ///////////////////////////////////////////////

         FileInputStream fin = new FileInputStream(ifile);
			BufferedReader rFile = new BufferedReader(new InputStreamReader(fin, charSet), BUFFER_SIZE);  		
           
         //BufferedWriter wFile = new BufferedWriter(new FileWriter(sOut)); 
         File ofile = new File(sOut);
         FileOutputStream fout = new FileOutputStream(ofile);
         BufferedWriter wFile = new BufferedWriter(new OutputStreamWriter(fout, charSet));
			
				
			String sBuf = null;
			String sXslMode = null;
			if ( sTmpCase.endsWith(".MGR.XML") )	// 결재일 경우
			{
				sXslMode = getInfData("EPXSL", uiBean.getStrCurDb());
				if ( sXslMode == null )
				{
					sXslMode = "/dm/html/ApprovalRoute.mgr.xsl";
				}
			}
			if ( sTmpCase.equalsIgnoreCase("RECEIVEINFO.XML") )		// 메일일 경우
			{
				sXslMode = getInfData("EPXSLMAIL", uiBean.getStrCurDb());
				if ( sXslMode == null )
				{
					sXslMode = "/dm/html/DMSMailReceiveInfo.xsl";
				}
			}
         // 2002-12-16 add by junhyuk
         if ( sTmpCase.equalsIgnoreCase("TRACEINFO.XML") )		   // mySingle V3일 경우
			{
				sXslMode = getInfData("EPXSL", uiBean.getStrCurDb());
				if ( sXslMode == null )
				{
					sXslMode = "../ApprovalRoute.mgr.xsl";
				}
			}
			////////////////////////////////////////////////////
			String sAppend = "<?xml-stylesheet type=\"text/xsl\" href=\"" + sXslMode + "\"?>";

			int lLine = 0;
			boolean bFlag = false;
			while ( (sBuf = rFile.readLine()) != null )
			{
				if ( sBuf.startsWith("<?xml") || bFlag == true )
				{
					// 그냥 보낸다.
				}
				else 
				{
					
					wFile.write(sAppend, 0, sAppend.length());
					wFile.newLine();
					bFlag = true;
				}
				
				wFile.write(sBuf, 0, sBuf.length());
				wFile.newLine();
				lLine ++;
			}

			

			wFile.close();
			rFile.close();		
         
         // 2002-09-17 add by junhyuk
         fout.close();
         fin.close();
         ifile.delete();
         ////////////////////////////
	
			if ( sOpType.equalsIgnoreCase("F") )
			{			
				if ( sTmpCase.endsWith(".MGR.XML") )	// 결재일 경우
				{
					%>
						<SCRIPT language=javascript>
							mgrwin = window.open('<%=sXmlFilePath%>', 'mgrwin', 'width=670, height=300, status=yes, scrollbars=yes, screenX=1,left=1,screenY=1,top=1');
						</SCRIPT>
					<%
				}
				if ( sTmpCase.equalsIgnoreCase("RECEIVEINFO.XML") )		// 메일일 경우
				{
					%>
						<SCRIPT language=javascript>
							mgrwin = window.open('<%=sXmlFilePath%>', 'mgrwin', 'width=670, height=300, status=yes, scrollbars=yes, screenX=1,left=1,screenY=1,top=1');
						</SCRIPT>
					<%
				}
            if ( sTmpCase.equalsIgnoreCase("TRACEINFO.XML") )		   // 결재일 경우
				{
					%>
						<SCRIPT language=javascript>
							mgrwin = window.open('<%=sXmlFilePath%>', 'mgrwin', 'width=670, height=300, status=yes, scrollbars=yes, screenX=1,left=1,screenY=1,top=1');
						</SCRIPT>
					<%
				}
			}
		}	
		catch (Exception e)
		{
			// 파일을 생성하고 닫는 도중에 에러
/* 			
			out.print("	alert('" + rsc.getProperty("att12") );
			if ( varFleInfo != null && varFleInfo[0][0] != null )
				
			else out.println("');");
			
 */         e.printStackTrace();
		}

		lOpenMode = 0;
	}
	// 2002-03-08 허준혁 : 결재 XML 문서일 경우 in KTF 결재정보 연동
	// 조건 : XML 파일, DocType이 A, E, G중 하나, XML파일의 Gubun이 FK
	else 
	{
		String varTmpDocInfo[] = new String[1];
		// 문서정보를 얻어온다.
		try
		{
			varTmpDocInfo = lq.getDocumentInfo(sDocId, uiBean.getStrCurDb());
			varDocTypeNM = varTmpDocInfo[0];
		}
		catch ( Exception e )
		{
			lm.writeLog("att_fromstor.jsp - Fail in getDocumentInfo : " + sDocId, "DmListQueryEJB");
			varDocTypeNM = "";
		}

		if ( sTmpCase.endsWith(".XML") && ( varDocTypeNM.equalsIgnoreCase("A") || varDocTypeNM.equalsIgnoreCase("E") || varDocTypeNM.equalsIgnoreCase("G") ) )
		{
			// KTF 결재의 경우 : 앞에서 처리한 문서정보로 보아 Gubun만 FK이면 KTF 결재 XML파일임
			// Gubun이 FK임을 확인하는 작업
			String varTmpFleInfo[] = new String[3];
			try
			{
				varTmpFleInfo = lq.getAttachFileInfo( varFleId[0], uiBean.getStrCurDb());
				 
			}
			catch ( Exception e )
			{
				lm.writeLog("att_fromstor.jsp - Fail in getAttachFileInfo : " + varFleId[0], "DmListQueryEJB");
				varTmpFleInfo[2] = "NULL";
			}

			 

			if ( varTmpFleInfo[2].equalsIgnoreCase("FK") )	// 이 조건이 옳다면 KTF 결재정보용 XML파일
			{
				// KTF결재 문서는 GroupEP처럼 XML이나 XSL을 사용하지 않고 JavaDOM으로 Parsing하여 mgrview.jsp에서 보여준다.
				
					
				// 2002-05-03 허준혁 : 압축이 되어 있을 경우 자동으로 푼다.
				//sOut = varFile[0][2];
				sOut = unCompressFile2UUID(varFile[0][2], "xml");

				

				try
				{
					if ( sOpType.equalsIgnoreCase("F") )
					{
						%>
							<SCRIPT language=javascript>
								mgrwin = window.open('<%=JSP%>/mgrview.jsp?xml=<%=sOut%>&mode=<%=varDocTypeNM.charAt(0)%>&docid=<%=sDocId%>&folderid=<%=sFolderId%>', 'mgrwin', 'width=560, height=400, status=yes, scrollbars=yes, screenX=1,left=1,screenY=1,top=1');
								//mgrwin = window.open('mgrview.jsp?xml=<%=sOut%>&mode=G&docid=<%=sDocId%>', 'MgrWindow', 'width=560, height=400, status=yes, scrollbars=yes, screenX=1,left=1,screenY=1,top=1');
							</SCRIPT>
						<%
					}
				}	
				catch (Exception e)
				{
					// 파일을 생성하고 닫는 도중에 에러
					
					
					if ( varFleInfo != null && varFleInfo[0][0] != null )
						out.println(varFleInfo[0][0] + "');");
					else out.println("');");
					
               e.printStackTrace();
				}
				lOpenMode = 0;            
			}
		}
	}
	////////////////////////////////////////////////////////////////////////////////////
}
// 2002-10-25
// PDF 문서 보기 Case
else if ( sOpType.equalsIgnoreCase("PDFVIEW") && varFleInfo != null && sFileCnt.equalsIgnoreCase("1") )
{
   /*
	varFile[i][0] = varFleId[i];					
	varFile[i][1] = sUUIDName;
	varFile[i][2] = sTempPath + sUUIDName;
	varFile[i][3] = varFleName[i];
   */
   
   String sReturnPath = "";
   Object refPdf = jndiContext.lookup("java:comp/env/ejb/PdfConversion");
   PdfConversionHome dpHome = (PdfConversionHome)PortableRemoteObject.narrow(refPdf, PdfConversionHome.class);
   PdfConversion dp = dpHome.create();
   
   // Queue에 남아있는 Count세기
   /*
   try
   {
      int nPDFCount = 0;
      nPDFCount = dp.getBePdfQueue();
      System.out.println("Queue Count : " + nPDFCount);
      out.println("<SCRIPT>");
      out.println("  alert('Queue Count : " + nPDFCount + "');");
      out.println("</SCRIPT>");
   }
   catch (Exception e)
   {
   }
   */

   // Direct2PDF
   	//static int NOT_SUPPORT_ERR = 101;   // PDF 변환이 불가능한 확장자
   	//static int GENERATE_PDF_ERR = 201;   // PDF 변환시 에러
	   //static int PDF_OPTION_ERR = 301;   // PDF 변환시 지원하지 않는 옵션
	   //static int GENERAL_EXCEPTION_ERR = 999;   // 일반적인 익셉션
	   //public int getErrorCode () throws RemoteException;
	   //public String getErrorMsg () throws RemoteException;
   try
   {
      sReturnPath = dp.convertRealTimePdf(varFile[0][2]);
   }
   catch (Exception e)
   {
      // PDF 생성도중 Exception 발생
      // dp.getErrorCode()에 따라서 분류처리함
      String sErrorMsg = "";
      switch ( dp.getErrorCode() )
      {
         case 101 :     // PDF 변환 불가능
            sErrorMsg = rsc.getProperty("att15");
         break;
         case 301 :     // 지원하지 않는 옵션
            sErrorMsg = rsc.getProperty("att16");
         break;
         case 201 :     // PDF 변환시 에러
            sErrorMsg = rsc.getProperty("att13");
         break;
         default:
            sErrorMsg = rsc.getProperty("att13");
      }
		
      lOpenMode = 0;    // Do not Use ActiveX Control
      
      sReturnPath = "ERROR";
   }

   
   // Error Treat
   if ( sReturnPath.equals("") )
   {
      // Error
      // PDF 생성도중 에러 발생 or PDF 변환 대상이 아님
		
      lOpenMode = 0;    // Do not Use ActiveX Control
   }
   else if ( !sReturnPath.equals("ERROR") )
   {
      // Success
      if ( TMP.equals("") )   // FileMng를 이용한 열기 구현
      {
         varFile[0][3] = varFile[0][3] + ".PDF";
         varFile[0][2] = sReturnPath;
         sOpType = "F";
         lOpenMode = 1;    // Use ActiveX Control
      }
      else
      {
         String varReturnPath[] = com.sds.acube.dm.jsp.WebUtil.split(sReturnPath, File.separator);  // "/"
         %>
               <FORM name='PDFVIEWER' method='POST' target="_new">
               <Input type=hidden name='URL'>
               </FORM>
               <SCRIPT language=javascript>
                     PDFVIEWER.URL.value = "<%=TMP%>/<%=varReturnPath[varReturnPath.length-1]%>";
                     PDFVIEWER.action = JSP + "/DM_WebViewer.jsp";
                     PDFVIEWER.submit();
                     //mgrwin = window.open(JSP + '/DM_WebViewer.jsp?URL=http://aegis:8888/dm/html/tmp/<%=varReturnPath[varReturnPath.length-1]%>', 'mgrwin', '');
               </SCRIPT>
         <%
         lOpenMode = 0;    // Do Not Use ActiveX Control
      }
      ///////////////////////////////////////////////////////////
   }   
}
// Example
//A(0, 0) = "96b9291edf5111d6ed3e080020b1f855"
//A(1, 0) = "test.doc"
//A(2, 0) = "/data1/dmsjava/tmp/D6D1F397E0354DC9AFE181F2B4888F6E.doc"
//lRet = parent.document.FR_ATTWIZ.ViewOneAttach("N", 1,"F", A) ==> View Case
//lRet = parent.document.FR_ATTWIZ.ViewOneAttach("N", 1,"W", A) ==> Download Case
//////////////////////////////////////////////////////////////////
		

/*방식을 수정*/
if ( varFleInfo != null )
{
%>
<SCRIPT language = vbScript>
	if <%=Integer.toString(lOpenMode)%> = 1 then '"<%=sOpType%>" = "W" and 
		Dim A
		Dim i
		Dim lRet 

		Dim xmlfilepath
		Dim sFileInfo 

		ReDim A( 2, <%=Integer.parseInt(sFileCnt) - 1%> )
<%
		for (int i = 0; i < Integer.parseInt(sFileCnt); i++ )	      // FileCnt -1
		{
         // test
         //if ( "separi.gul".equals(varFile[i][3]) )
         //{
         //   varFile[i][3] = "test_separi.gul";
         //}
			out.println("A(0, " + Integer.toString(i) + ") = \"" + varFile[i][0] + "\"");	//배열의 차례가 바뀜
			out.println("A(1, " + Integer.toString(i) + ") = \"" + varFile[i][3] + "\"");
			out.println("A(2, " + Integer.toString(i) + ") = \"" + varFile[i][2] + "\"");
         System.out.println("A(0, " + Integer.toString(i) + ") = \"" + varFile[i][0] + "\"");	//배열의 차례가 바뀜
			System.out.println("A(1, " + Integer.toString(i) + ") = \"" + varFile[i][3] + "\"");
			System.out.println("A(2, " + Integer.toString(i) + ") = \"" + varFile[i][2] + "\"");
		}
%>
		'if user succeed to open or download file, then leave log to uopr DB
		lRet = parent.document.FR_ATTWIZ.ViewOneAttach("<%=sDel%>", <%=sFileCnt%>,"<%=sOpType%>", A)
	else
		lRet = 1 
	end if 

	if lRet = 1 then 
<%
		String varUOprParams[] = new String[21];
		for (int i=0; i < Integer.parseInt(sFileCnt) ; i++)
		{
			varUOprParams[0] = (uiBean.encodeByLocale(request.getParameter("TYPE")) == null) ? "" : uiBean.encodeByLocale(request.getParameter("TYPE"));
         /////////////////////////////////////////////////////
         // 2002-11-04 add by junhyuk for PDF Conversion
         if ( varUOprParams[0].equalsIgnoreCase("PDFVIEW") )
         {
            varUOprParams[0] = "F";
         }
         ///////////////////////////////////////////////
			/*'open file - F, download file -W*/ 
			varUOprParams[1] = (uiBean.encodeByLocale(request.getParameter("DOCID")) == null) ? "" : uiBean.encodeByLocale(request.getParameter("DOCID"));
			/*'docid : sDocId*/
			varUOprParams[2] = uiBean.getUserID();					/*'user id*/
			varUOprParams[3] = uiBean.getUserName();					/*'name*/
			varUOprParams[15] = varFleId[i];						/*'file id*/
			varUOprParams[16] = varFleName[i];						/*'filename*/
			varUOprParams[17] = uiBean.getTitleID();					/*'deptID*/
			varUOprParams[18] = uiBean.getDptName();					/*'dept name*/
			varUOprParams[20] = uiBean.getTitleName();					/*'grade*/
		}
		/*' dd.saveUOprWork(varUOprParams, uiBean.getStrCurDb()); - File조회시 권한 체크 : 이곳에 위치해야 한번 다운로드 받을 때 여러번 등록되는 일이 없다.*/
		/*' 2002-02-07 Exception을 해주어야 만약의 에러를 감당한다. */
		try
		{
			dd.saveUsrWork(varUOprParams, uiBean.getStrCurDb());
		}
		catch (Exception e)
		{
			lm.writeLog("att_fromstor.jsp - Fail in saveUsrWork", "DmDocDeleteEJB");
		}
%>	
      document.close    'need to close load
	else
		document.close    'need to close load
	end	if

   ' 2002-12-03 add by junhyuk
   'if "<%=sOpType%>" = "F" then
   '   dim sTmpStr
   '   sTmpStr = "FILEID=<%=varFile[0][0]%>&FILEPATH=<%=varFile[0][2]%>&FILENAME=<%=varFile[0][3]%>&FILEWIZ=parent.document.FR_ATTWIZ&LANG=K&HTTPTMP=/dm/html/tmp/"
   '   document.location.replace("DM_ViewFile.jsp?" + sTmpStr)
   'end if
</SCRIPT>
<%
}
%>
<SCRIPT language=javascript>
	if (parent.tooltip) parent.tooltip.style.display="none";
	<% 
	if (bdemo) // 역으로 실제 출하버전에서만 replace하여 백버튼으로 인한 오류를 막는다.
	{
		out.println("document.location.replace('" + HTML + "/empty.html');");
	}
	%>
</SCRIPT>

<!--
<FORM name=VIEWFILE method=POST action="DM_ViewFile.jsp">
<INPUT TYPE=hidden name='FILEID'>
<INPUT TYPE=hidden name='FILEPATH'>
<INPUT TYPE=hidden name='FILENAME'>
<INPUT TYPE=hidden name='FILEWIZ'>
<INPUT TYPE=hidden name='LANG'>
<INPUT TYPE=hidden name='HTTPTMP'>
</FORM>
-->
</BODY>
</HTML>



<%!
	String eraseNullChar(String str) 
	{
		String strRet = "";
		String strBuf = "";
		String strTmp = "";
		
		strBuf = str.trim();
		
		if (str != null) 
		{
			for(int i=0; i<str.length() ; i++) 
			{
				strTmp = str.substring(i, i+1);
				if (" ".equals(strTmp)) 
				{
					strRet = strRet + strTmp;
				} 
				else 
				{	
					strRet = strRet + strTmp.trim();
				}
				
			}
		} 
		else 
		{
			strRet = null;
		}
		
		return strRet;
	}
%>

<%!
	// 2002-05-07 허준혁 : UUID Name 얻기.
	String getUUIDName(String sOriName)
	{
		
		String sName = null;
		sName = com.sds.acube.dm.DmGlobal.getUUID().toLowerCase() + "." + com.sds.acube.dm.jsp.WebUtil.getFileExt(sOriName);
		return sName;
	}
%>
<%! 
	// 2002-05-03 허준혁 : 압축이 되어 있다면 압축을 풀고 풀어낸 파일 이름을 리턴.
	// 나중에 조국영씨의 모듈로 바꾸고 EJB나 Bean화가 필수라 생각.
	// com.sds.acube.dm.DmGZIPInputStream
	String unCompressFile2UUID(String sPath, String sExt)
	{
		
		String sTarget = null;
		sTarget = sPath.substring(0, sPath.lastIndexOf(File.separator)) + File.separator + com.sds.acube.dm.DmGlobal.getUUID().toLowerCase() + "." + sExt;
		
		boolean bCompBool = false;

		try
		{
			FileOutputStream	fOut = null;
			com.sds.acube.dm.DmGZIPInputStream		fIn  = null;
			fOut = new FileOutputStream(sTarget); 
			fIn = new com.sds.acube.dm.DmGZIPInputStream(new FileInputStream(sPath)); 
			byte[] buf = new byte[4096]; 
			
			int iRead; 
			
			while((iRead = fIn.read(buf)) != -1) {
				fOut.write(buf, 0, iRead); 
			}
			fIn.close(); 
			fOut.close(); 
			bCompBool = true;
		} catch(FileNotFoundException e) { 
			 
			bCompBool = false;
		} catch(IOException e) { 
			System.out.println("error : IOException" + e); 	
			bCompBool = false;
		} catch(Exception e)  {
			System.out.println("error : " + e);	
			bCompBool = false;
		}

		if ( bCompBool == true )
		{
			System.out.println("Return : " + sTarget);
			return sTarget;
		}
		else
		{
			System.out.println("Return : " + sPath);
			return sPath;
		}
	}
%>
