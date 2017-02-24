<!--******************     For FASOO DRM Inserted by Selphina 2002.4    *****************************-->
<%
/*   int itemp;
   InputStream is = null;
   String temp=null;
*/	String DrmSvrName=null;
/*	String DrmIniFileName=null;
*/   String DrmURL;
   String DrmPort;
   String DrmTemp;

   try
   {
		String sInfo[] = uiBean.getOriginArrInfo();
		DrmURL=(sInfo[32] == null) ? "" : sInfo[32];
		DrmPort=(sInfo[33] == null) ? "" : sInfo[33];
		DrmTemp=(sInfo[34] == null) ? "" : sInfo[34];
   }
   catch ( Exception e )
   {
	   DrmURL = "";
	   DrmPort = "";
	   DrmTemp = "";
   }
//   String DrmProduct=uiBean.arrInfo[34];
//   String DrmTarget=uiBean.arrInfo[35];
   String DrmCabSource="http://"+DrmURL+DrmPort;
/*
   if(DrmURL!=null && !DrmURL.trim().equals(""))
   {
      //Fasoo DRM Server에 접속해서 다운받아야 할 파일들의 이름을 가지고 옴

      //..............................................
      // 파수에서 파일을 가지고 온다.
      try {
         is = (new URL("http://"+DrmURL+"/fsdinit/FSFolder.ini")).openStream();
         BufferedReader br = new BufferedReader(new InputStreamReader(is));

         temp=br.readLine();
         while(temp!=null)
         {
            itemp = temp.indexOf("CPID=");

            if(itemp>-1)
            {
               DrmIniFileName = (temp.substring(itemp+5)).trim();
               break;
            }
            temp=br.readLine();
         }
         is = new URL("http://"+DrmURL+"/fsdinit/"+DrmIniFileName+".ini").openStream();
         br = new BufferedReader(new InputStreamReader(is));
         temp=br.readLine();
         while(temp!=null )
         {
         	itemp = temp.indexOf("RS Server=");
         	if(itemp>-1)
         	{
         		DrmSvrName= (temp.substring(itemp+10)).trim();
					break;
         	}
				temp=br.readLine();
         }
      } catch (IOException ioe) {
         ioe.printStackTrace();
      } finally {
         try { is.close(); } catch (IOException ioe2) { ioe2.printStackTrace(); }
      }
   }
*/
	if (DrmTemp!=null && DrmTemp.equals("1") )
	{
%>
<%--FASOO DRM Viewer Download--%>
	<OBJECT CLASSID="clsid:934CEFDC-E880-446F-880F-6560F613D8AA"  style='WIDTH: 0px; HEIGHT: 0px' CODEBASE="http://<%=DrmURL+DrmPort%>/activex/fclient.cab#version=1,0,1,2" ID=fversion>
	</OBJECT><!--f_ver.dll-->
	<OBJECT CLASSID="clsid:67BD3C0F-603A-4C10-B507-5002BFFCC5C4"  style='WIDTH: 0px; HEIGHT: 0px' CODEBASE="http://<%=DrmURL+DrmPort%>/activex/AcroPlugin.cab#version=1,0,0,4" ID=fapiv>
	</OBJECT><!--f_apiver.dll-->
<%
}
%>

<!--******************************************************************************************-->
