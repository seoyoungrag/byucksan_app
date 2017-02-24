var oBrowser = navigator.userAgent;
if (new RegExp(/MSIE/).test(oBrowser)) {
	document.write("<object id='SSOClient' name='SSOClient' classid='" + SSOClient_ClassId + "' width=0 height=0	codeBase='" + webUri + "/app/ref/cabfile/ACUBEClient.cab#version=" + SSOClient_Version + "'></OBJECT>");
}
else if (new RegExp(/Firefox/).test(oBrowser)) {
	document.write("<embed id='SSOClient' type='Application/ACUBEClient' width=0 height=0></embed>");
}
else if (new RegExp(/Chrome/).test(oBrowser)) {
	document.write("<embed id='SSOClient' type='Application/ACUBEClient' width=0 height=0></embed>");
}
else if (new RegExp(/Safari/).test(oBrowser)) {	
	document.write("<embed id='SSOClient' type='application/ACUBEClient' width=0 height=0></embed>");
}
else if (new RegExp(/Opera/).test(oBrowser)) {
	document.write("<embed id='SSOClient' type='Application/ACUBEClient' width=0 height=0></embed>");
}
