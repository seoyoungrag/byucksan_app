/* INISAFE Web V6 - install.js */
/* 맨마지막 라인의 InitechGroupID는 지정된 값으로 반드시 변경하세요 */

/********************************************************************
1. update 2004/09/01 wakano@initech.com
 - INISAFEWeb.html에서 프레임없이 사용하는 구조
	: function CheckPlugin() 및 var InstallPluginURL, CheckVersion 추가

2. update 2005/11/01 wakano@initech.com
 - IDC을 이용하여 다운로드 받는 구조로 변경
	: var InitechGroupID 추가(맨마지막 라인참고)

3. update 2005/11/01 young@initech.com
 - componentName, mimeType 오류 수정 (Netscape 및 파이어폭스)
********************************************************************/

var baseURL				= webUrl + webUri + "/app/ref/initech/plugin/";

var InstallModuleURL	= baseURL + "dll/INIS60.vcs"; 
var iePackageURL		= baseURL + "down/INIS60.cab"; 
var nsPackageURL		= baseURL + "down/INIS60.jar"; 
var ieManualPackageURL	= baseURL + "down/INIS60.exe";
var nsManualPackageURL	= ieManualPackageURL;
var manualInstallURL	= baseURL + "down/download.html";

//var ieVersion			= "6,1,0,5";
var ieVersion			= "6,4,0,33";
var nsVersion			= ieVersion;

// add 2004/09/01 wakano@initech.com INISAFEWeb.html 사용시 사용)
var InstallPluginURL	 = webUrl + webUri + "/app/ref/initech/plugin/site/install.html";
var CheckVersion		= ieVersion;  // 업그레이드 확인버전 ieVersion과 동일하게 사용을 권장

var componentName		= "plugins/initech/INISAFE60/npINISAFEWeb60.dll";
var mimeType			= "application/x-INISAFEWebv60";
//6.3.0.8
//var CLSID				= "286A75C3-11FB-4FB4-AC4A-4DD1B0750050";
//6.4.0.33
var CLSID				= "DD8C54E8-9028-4a54-96B9-30761B1F80DF";

/****************************************************/
/*********** 아래의 내용은 수정하지 마세요 **********/
/****************************************************/

function getIntVersion(versionStr)
{
	var version = new Array(4);
	versionArray = versionStr.split(",");	
	for(i=0;i<4;i++)
		version[i] = parseInt(versionArray[i], 10);

	return version;
}

function myVersionCompare()
{
	var myMimetype = navigator.mimeTypes[mimeType];
	var desc = myMimetype.enabledPlugin.description;
    var index = desc.indexOf('v.', 0);
    if (index < 0)
        return -5;
    desc += ' ';

    versionString = desc.substring(index+2, desc.length);
    arrayOfStrings = versionString.split('.');

	var existing = new Array(4);
	for(i=0; i<4; i++)
    	existing[i] = parseInt(arrayOfStrings[i], 10);

	var version = getIntVersion(nsVersion);

	for(i=0; i<4; i++)
	{
		if(existing[i]>version[i])
			return (4-i);
		else if(existing[i]<version[i])
			return -(4-i);
	}

    return 0;
}

//add  brson 2002/4/16
function getUserAgentVersion()
{	
	var s = navigator.userAgent.indexOf("/");
	var	e = navigator.userAgent.indexOf(" ");
	var	ver = navigator.userAgent.substring(s+1, s+4);
	return ver;
}

//change brson 2002/4/16
function startDownload() 
{
	var trigger;
	var version;
	var newVI;
	var existingVI;
	var myMimetype = navigator.mimeTypes[mimeType];
	// If some version is already installed on this machine...
	if ( myMimetype ) {
		if(getUserAgentVersion()>=5.0){
			if(myVersionCompare()<0){
				top.location = manualInstallURL;
				return true;
			} else{
				return true;
			}
		}
		trigger = netscape.softupdate.Trigger;
		version = getIntVersion(nsVersion);
		newVI = new netscape.softupdate.VersionInfo(version[0], version[1], version[2], version[3]);
		existingVI = netscape.softupdate.Trigger.GetVersionInfo(componentName);
		if ( existingVI==null)
		{
			if(myVersionCompare()<0)
			{
				alert("INIplugin을 설치합니다.");
				//return trigger.StartSoftwareUpdate(nsPackageURL, trigger.DEFAULT_MODE|trigger.SILENT_MODE);
				return trigger.StartSoftwareUpdate(nsPackageURL, trigger.DEFAULT_MODE);
			}
			else
			{
				return true;
			}
		}
		else if ( existingVI.compareTo(newVI)<0)
		{
			alert("INIplugin을 설치합니다.");
			//return trigger.ConditionalSoftwareUpdate(nsPackageURL, componentName, newVI, trigger.DEFAULT_MODE|trigger.SILENT_MODE);
			return trigger.ConditionalSoftwareUpdate(nsPackageURL, componentName, newVI, trigger.DEFAULT_MODE);
		}
		else
		{
			return true;
		}
	}
	else
	{
		if(getUserAgentVersion()>=5.0){
			top.location = manualInstallURL;
			return true;
		}
		else{
			trigger = netscape.softupdate.Trigger;
			alert("INIplugin을 설치합니다.");
			//return trigger.StartSoftwareUpdate(nsPackageURL, trigger.DEFAULT_MODE|trigger.SILENT_MODE);
			return trigger.StartSoftwareUpdate(nsPackageURL, trigger.DEFAULT_MODE);
		}
	}

	return false;
}



//change brson 2002/4/16
function isInstalled()
{
	var myMimetype = navigator.mimeTypes[mimeType];
	if ( myMimetype ){
		if(getUserAgentVersion()>=5.0){
			if(myVersionCompare()>=0){
				return true;
			}else{
				return false;
			}
		} else{
			var version = getIntVersion(nsVersion);
			var newVI = new netscape.softupdate.VersionInfo(version[0], version[1], version[2], version[3]);
			var existingVI = netscape.softupdate.Trigger.GetVersionInfo(componentName);
			if(existingVI==null) {
				if(myVersionCompare()>=0){
					return true;
				}else{
					return false;
				}
			}
			else if ( existingVI.compareTo(newVI)>=0){
				return true;
			}
		}
	}
	return false;
}

var loadOK;
function LoadPlugin()
{
	loadOK=true;	
	if (navigator.appName == 'Netscape') 
	{
		if(isInstalled())
		{
			document.writeln('<EMBED type=' + mimeType + ' name="INIplugin" width=2 height=2>INIplugin Load OK</EMBED>');
		}
		else
		{
			loadOK = startDownload();
			NS_Init();
		}
	}
	else
	{
		document.writeln('<OBJECT ID="INIplugin" CLASSID="CLSID:' + CLSID + '" width=1 height=1 ');
		document.writeln('CODEBASE='+ iePackageURL + '#Version=' + ieVersion +'>');
		document.writeln('</OBJECT>');	
	}
}

var loopCount=0;
function NS_Init()
{
	
	if(!loadOK)
	{
		alert("설치 실패");
	}
	else if(isLoaded())
	{
		return;
	}
	else if(isInstalled())
	{
		location.reload();
		//location.replace("./INIplugin.html");	
	}
	else
	{
		loopCount++;
		if(loopCount>60*10)
		{
			alert("설치 실패");
		}
		else
		{
			setTimeout("NS_Init()", 1000);
		}
	}
}

function isLoaded()
{
	if(navigator.appName == "Netscape")
	{
		if(document.INIplugin==null)
			return false;
		else
			return true;
	}
	else
	{
		if(frame.INIplugin==null || typeof(frame.INIplugin) == "undefined" || frame.INIplugin.object==null) return false;
		else 
			return true;
	}
}

// add 2004/09/01 wakano@initech.com
function CheckPlugin()
{
	var installOK = false;
	loadOK=true;

//alert(installOK + "=[start]");

	if (navigator.appName == 'Netscape') 
	{
		if(isInstalled()) {
			document.writeln('<EMBED type=' + mimeType + ' name="INIplugin" width=2 height=2>INIplugin Load OK</EMBED>');
			installOK = true;
		}
	} 
	else
	{
		//document.writeln('<OBJECT ID="INIplugin" CLASSID="CLSID:' + CLSID + '" width=1 height=1 ');
		//document.writeln('CODEBASE='+ iePackageURL + '#Version=' + ieVersion +'>');
		//document.writeln('</OBJECT>');	
		document.writeln('<OBJECT ID="INIplugin" CLASSID="CLSID:' + CLSID + '" width=1 height=1 ></OBJECT>');

		//alert("check 1 = " + typeof(this.document.INIplugin));
		//alert("check 2 = " + this.document.INIplugin);
		//alert("check 3 = " + this.document.INIplugin);
		//alert("check 4 = " + this.document.INIplugin.object);

		if( !((typeof(this.document.INIplugin) == "undefined") || (this.document.INIplugin == "undefined") ||
				(this.document.INIplugin == null) || (this.document.INIplugin.object == null) ))
		{
			var thisArray = String(this.document.INIplugin.GetVersion()).split(',');
			var inputArray = CheckVersion.split(',');
			for (i=0; i<4; i++)
			{
//alert(thisArray[i] + "|" + inputArray[i]);
				if (parseInt(thisArray[i], 10) > parseInt(inputArray[i], 10)) {
					installOK = true;
					break;
				} else if (parseInt(thisArray[i], 10) < parseInt(inputArray[i], 10)) {
					break;
				} else {
					if (i==3) installOK = true;
				}
			}
//alert(installOK + "=[version check end]");

			if (installOK == true)
			{
				var inputArray2 = ieVersion.split(',');
				for (i=0; i<4; i++)
				{
//alert(thisArray[i] + "|" + inputArray2[i]);
					if (parseInt(thisArray[i], 10) > parseInt(inputArray2[i], 10)) {
						break;
					} else if (parseInt(thisArray[i], 10) < parseInt(inputArray2[i], 10)) {
						if (confirm("암호화모듈(INISAFE Web)이 업그레이드 되었습니다. 업그레이드 하시겠습니까")) {
							installOK = false;
							break;
						}
						break;
					}
				}
//alert(installOK + "=[upgrade check end]");
			}
		}
	}
//alert(installOK + "=[end]");
	if (installOK == false) top.location = InstallPluginURL;
}

