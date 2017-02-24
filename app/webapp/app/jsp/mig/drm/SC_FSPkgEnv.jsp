<!--******************     For FASOO DRM Inserted by Selphina 2002.4    *****************************-->
<%
/*   int itemp;
   InputStream is = null;
   String temp=null;
   String DrmIniFileName=null;
   String DrmPCertId=null;
   String DrmCCertId=null;
   String DrmPrivKeyId=null;
*/ 
   String DrmURL;   
   String DrmPort;   
   String DrmProduct;   
   String DrmTarget;

	try
	{
		String sInfo[] = uiBean.getOriginArrInfo();
		//DrmURL=uiBean.arrInfo[32];
		//DrmPort=uiBean.arrInfo[33];
		//DrmProduct=uiBean.arrInfo[34];
		//DrmTarget=uiBean.arrInfo[35];
		DrmURL=sInfo[32];
		DrmPort=sInfo[33];
		DrmProduct=sInfo[34];
		DrmTarget=sInfo[35];
	}
	catch (Exception e)
	{
		DrmURL="";
		DrmPort="";
		DrmProduct="";
		DrmTarget="";
	}


   String DrmCabSource="http://"+DrmURL+DrmPort;
   String DrmFsdInit= com.sds.acube.dm.jsp.WebUtil.getInfData("DRMFSDINIT", uiBean.getStrCurDb());
   String DrmFsdFsk1= com.sds.acube.dm.jsp.WebUtil.getInfData("DRMFSDFSK1", uiBean.getStrCurDb());
   String DrmFsdFsk2= com.sds.acube.dm.jsp.WebUtil.getInfData("DRMFSDFSK2", uiBean.getStrCurDb());
   String DrmFsdFsk3= com.sds.acube.dm.jsp.WebUtil.getInfData("DRMFSDFSK3", uiBean.getStrCurDb());
   
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
         int selphina=0;
         while(temp!=null)
         {
            if(DrmPCertId==null)
            {
               itemp = temp.indexOf("Current Parent Cert ID=");
               if(itemp>-1)
               {
                  DrmPCertId= (temp.substring(itemp+23)).trim();
               }
            }
            if(DrmPrivKeyId==null)
            {
               itemp = temp.indexOf("Current Child PrivKey ID=");
               if(itemp>-1)
               {
                  DrmPrivKeyId=(temp.substring(itemp+25)).trim();
               }
            }
            if(DrmCCertId==null)
            {
               itemp = temp.indexOf("Current Child Cert ID=");
               if(itemp>-1)
               {
                  DrmCCertId = (temp.substring(itemp+22)).trim();
               }
            }
            temp=br.readLine();
            System.out.println(selphina++);
         }
      } catch (IOException ioe) {
         System.out.println("ERROR");
         ioe.printStackTrace();
      } finally {
         try { is.close(); } catch (IOException ioe2) { ioe2.printStackTrace(); }
      }
   }
*/
   if(!DrmTarget.equals("0"))                      //DRM 이 적용되는 경우
   {
      if(DrmProduct!=null && DrmProduct.equals("1"))
      {
%>

         <OBJECT CLASSID="clsid:FB20E400-6232-48AF-8FA9-36FE3C019FFD" style='WIDTH: 0px; HEIGHT: 0px' CODEBASE="<%=ENV%>/activex/fasoo/drmpkg.cab#version=1,0,0,1" ID=drmpkg>
         </OBJECT><!--drmpkg.dll-->
         <OBJECT CLASSID="clsid:337DDEDD-9290-45D9-AE17-9AAD525BAB5A" style='WIDTH: 0px; HEIGHT: 0px' CODEBASE="<%=DrmCabSource%>/activex/CW-Pack.cab#version=1,0,0,2" ID=dtree>
         </OBJECT><!--DTree.dll-->
         <OBJECT CLASSID="clsid:8D4FE222-2921-4A86-9389-428136E8D5E5" style='WIDTH: 0px; HEIGHT: 0px' CODEBASE="<%=DrmCabSource%>/activex/CW-Pack.cab#version=1,1,0,10" ID=IxPackager>
         </OBJECT><!--f_pkmgr.dll-->
         <OBJECT CLASSID="clsid:6A177F96-36D0-419E-8CCC-9208E2160778" style='WIDTH: 0px; HEIGHT: 0px' CODEBASE="<%=DrmCabSource%>/activex/CW-Pack.cab#version=1,1,0,5" ID=PackagerCtrl>
         </OBJECT><!--fsd_pk.dll-->
         <OBJECT CLASSID="clsid:2F6DC8D9-21A0-4772-BDF3-C290D1AA4431" style='WIDTH: 0px; HEIGHT: 0px' CODEBASE="<%=DrmCabSource%>/activex/CW-Pack.cab#version=1,1,0,1" ID=PrePack>
         </OBJECT><!--fsd_prepkg.dll-->
         <OBJECT CLASSID="clsid:DA3144F1-FCE0-4012-A289-E4CEADA25EE6" style='WIDTH: 0px; HEIGHT: 0px' CODEBASE="<%=DrmCabSource%>/activex/fclient.cab#version=2,2,2,2" ID=downloadmgr>
         </OBJECT><!--f_dn.dll-->
         <OBJECT CLASSID="CLSID:AD98CBEE-44E1-48AB-90F2-116976BAA567" style="WIDTH: 0px; HEIGHT: 0px' CODEBASE="<%=DrmCabSource%>/activex/fclient.cab#version=1,0,0,2" ID="PkgProgress" >
         </OBJECT><!--f_pkst.dll-->         
         
         
         <SCRIPT language = 'JavaScript'>
	 <!-- Packager 초기화에 필요한 정보를 Download하는 함수**-->
         downloadmgr.StoreToInfoFile("http://"+"<%=DrmURL%>"+"<%=DrmPort%>"+"/fsdinit/"+"<%=DrmFsdFsk1%>" , 1) 
   	 downloadmgr.StoreToInfoFile("http://"+"<%=DrmURL%>"+"<%=DrmPort%>"+"/fsdinit/"+"<%=DrmFsdFsk2%>", 1) 
   	 downloadmgr.StoreToInfoFile("http://"+"<%=DrmURL%>"+"<%=DrmPort%>"+"/fsdinit/"+"<%=DrmFsdFsk3%>", 1) 
         downloadmgr.StoreToInfoFile("http://"+"<%=DrmURL%>"+"<%=DrmPort%>"+"/fsdinit/"+"<%=DrmFsdInit%>", 3)                  
	 </SCRIPT>
	 
	 <SCRIPT LANGUAGE='vbscript'>
	 'ActiveX Do not Attach File like this extension
	 Dim sExt        
	 sExt = "fsc"         
         Document.all.FileWizard.SetAvoidExtension sExt
         
         'Select Normal or DRM mode
	 ' 0 : Normal, 1 : Fasoo DRM 
  	 ' 0 is Default 
	 'SetDRMMode 1
         Dim nFlag
         nFlag = "1"         
	 Document.all.FileWizard.SetDRMMode nFlag	 

	 'for case of DRM Mode, Configuration Information for DRM
	 'SetDRMConfig 'A0000001.ini', '001'
	 Dim sPkgIni
	 sPkgIni = "<%=DrmFsdInit%>"+".ini"
	 Dim sSysCode
	 sSysCode = "001"
	 Document.all.FileWizard.SetDRMConfig sPkgIni, sSysCode
         </SCRIPT>   

         
<%
      }
   }
%>




