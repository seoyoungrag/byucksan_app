<!-- 2002.06.03 by HongSungmin Adding for Configuration Code -->
<%
	String sDRMUserId;
	try
	{
		sDRMUserId = uiBean.getUserID();
	}
	catch (Exception e)
	{
		sDRMUserId = "";
	}
   if(!"0".equals(DrmTarget))                      //DRM   
   {
      if(DrmProduct!=null && "2".equals(DrmProduct))
      {
%>
<OBJECT ID="IDSREG" style='WIDTH: 0px; HEIGHT: 0px'
	CLASSID="CLSID:50BE3A1C-AD11-42FB-80AE-C2919DE8131F"
	  codebase="<%=ENV%>/activex/markany/DSREG.cab#version=1,1,1,1">
</OBJECT>


<SCRIPT language="javascript">
function DRMKeyReg()
{
	IDSREG.SetServerIP = "<%=DrmUrl%>"; 
	IDSREG.SetPort  = "40000";
	IDSREG.SetID = "<%=sDRMUserId%>";	
	

	IDSREG.SetDLSIp    = "<%=DrmUrl%>";
	IDSREG.SetDLSPort  = "40002";
	IDSREG.SetDTSSIp   = "<%=DrmUrl%>";
	IDSREG.SetDTSSPort = "40003";

	IDSREG.SetType = "2";
	IDSREG.DoProcess = "TRUE";

	nRet = IDSREG.DoProcess;

/*	if (nRet == "0")
		alert("Key Registration Sucess");
	else if (nRet == "50002")
		alert("Already registered in another computer");	
*/
}
</SCRIPT>
<%		}
	}
%>
