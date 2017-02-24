var SC_Browser_Version = navigator.userAgent;
if (new RegExp(/MSIE/).test(SC_Browser_Version)) {
	document.write("<object id='seedOCX' classid='clsid:A69C034C-75AB-49A0-A3F9-D0F8DCB76E43'");
	document.write("      codebase='/ep/app/ref/cabfile/SeedClientX.cab#version=1.0.0.4'");
	document.write("      width='0' height='0'>");
	document.write("</object>");
} else if (new RegExp(/Firefox/).test(SC_Browser_Version)) {
	document.write("<embed id='seedOCX' type='Application/SeedClientX' pluginspage='/ep/app/ref/cabfile/npSeedClientX.xpi' width=0 height=0 />");
} else {
	document.write("<embed id='seedOCX' type='Application/SeedClientX' pluginspage='/ep/app/ref/cabfile/npSeedClientX.msi' width=0 height=0 />");
}