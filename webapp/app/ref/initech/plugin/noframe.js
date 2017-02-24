//document.writeln('<OBJECT ID="INIplugin" CLASSID="CLSID:286A75C3-11FB-4FB4-AC4A-4DD1B0750050"  CODEBASE=http://www.initech.com/initech/plugin/wn/INIS60.cab #Version=6,0,0,13>');
//document.writeln('</OBJECT>');

var checkFrame = false;
function StartIniPlugin()
{
	// INIplugin.js, install.js, cert.js 로딩여부 확인
	if ( (typeof(LoadPlugin) == 'undefined') || (typeof(SCert) == 'undefined') || (typeof(LoadCert) == 'undefined'))
	{
		if (checkFrame == false)
		{
			checkFrame = true;
			alert("reCheck");
			setTimeout("StartIniPlugin()", 2000);
			return;
		} else 
			alert("install.js/cert.js/INIplugin.js 파일이 include되지 않았습니다.")
			return;
	}

	//이중로드 방지
	if (typeof (ModuleInstallCheck) == "function") {
 		if (ModuleInstallCheck() != null) {
//			alert("find secureframe skip noframe...");
			return;
		}
	}

    CheckPlugin();
	InstallModule(InstallModuleURL);

		
	if (!LoadCert(SCert)) {
		alert("보안 재설정중입니다.");
		location.reload();
	}

	//update 

	LoadCACert(CACert);				//인증서 필터링(CA 인증서가 발급한 클라이언트 인증서만 보임)
	SetProperty("certmanui_GPKI", "all");			//모든 인증서를 보임
	//SetProperty("certmanui_GPKI", "GPKIonly");	//GPKI 인증서만 보임
	SetProperty("codepage", "949");

	SetLogoPath();				//이미지 URL 세팅							
	//SetCacheTime(100)				//인증서캐쉬타임	default (360)
	//EnableCheckCRL(true);			//공인인증서 CRL확인여부 default (false)
	//DisableInvalidCert(true);   	//로그인창에서 페기/만료된인증서 표시여부	 defaul

}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
if (typeof(StartIniPlugin) != 'undefined')
	StartIniPlugin();
else
	setTimeout("StartIniPlugin()", 1000);
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
