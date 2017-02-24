function openWindow(winName, url, width, height, scroll, method, status) {
	var winObject = null;
	var data = url.split("?");
	var param = "";
	if (data.length > 1) {
		url = data[0];
		param = data[1];
	}
	
	var left = (screen.availWidth - width) / 2;
	var top = (screen.availHeight - height - 40) / 2;

	if (typeof(scroll) == "undefined") {
		scroll = "no";
	}
	if (typeof(method) == "undefined") {
		method = "post";
	}
	if (typeof(status) == "undefined") {
		status = "yes";
	}

	/*var option = "left="+left+",top="+top+",width="+width+",height="+height+",scrollbars="+scroll+",status="+status;*/
	var option = "left="+left+",top="+top+",width="+width+",height="+height+",scrollbars="+scroll+",status="+status +",resizable=yes";
	
	
	try {
		if (param == null || param == "") {
			winObject = window.open(url, winName, option);
		} else if (method == "get") {
			winObject = window.open(url + "?" + param, winName, option);
		} else {
			// 빈창 생성
			winObject = window.open("", winName, option);
		
			// submit 용 form 생성/셋팅
			var formwizard = document.getElementById(winName);
			if (formwizard == null) {
				var formwizardscript = "<form id='" + winName + "' style='width:0px;height:0px' method='post' target='" + winName + "' action='" + url + "'>";		
				if (param != "") {
					var params = param.split("&");
					var paramcount = params.length;
					for (var loop = 0; loop < paramcount; loop++) {
						if (params[loop] != "") {
							var paramdata = params[loop].split("=");
							if (paramdata.length == 2 && paramdata[0] != "") {
								formwizardscript += "<input type='hidden' id='" + paramdata[0] + "' name='" + paramdata[0] + "' value='" + escapeJavaScript(paramdata[1]) + "'>";
							} else if (paramdata.length == 1 && paramdata[0] != "") {
								formwizardscript += "<input type='hidden' id='" + paramdata[0] + "' name='" + paramdata[0] + "' value=''>";
							}
						}
					}			
				}
				formwizardscript += "</form>";
				document.body.insertAdjacentHTML("beforeEnd", formwizardscript);
				formwizard = document.getElementById(winName);
			} else {
				formwizard.action = url;
				if (param != "") {
					var formwizardscript = "";
					var params = param.split("&");
					var paramcount = params.length;
					for (var loop = 0; loop < paramcount; loop++) {
						if (params[loop] != "") {
							var paramdata = params[loop].split("=");
							if (paramdata.length == 2 && paramdata[0] != "") {
								formwizardscript += "<input type='hidden' id='" + paramdata[0] + "' name='" + paramdata[0] + "' value='" + escapeJavaScript(paramdata[1]) + "'>";
							} else if (paramdata.length == 1 && paramdata[0] != "") {
								formwizardscript += "<input type='hidden' id='" + paramdata[0] + "' name='" + paramdata[0] + "' value=''>";
							}
						}
					}			
					$("#" + winName).html(formwizardscript);
				}
			}
			// form 발송
			formwizard.submit();
		}
		winObject.focus();
		return winObject;
	} catch (error) {
		try {
			if (param == null || param == "") {
				winObject = window.open(url, "winName", option);
			} else {
				winObject = window.open(url + "?" + param, "winName", option);
			}
			winObject.focus();
			return winObject;
		} catch (error) {
			alert(reloadwindow);
		}
	}
}

function replaceString(str, from, to) {
	if( str==null || str == "" )
		return "";

	var i = 0;
	var rStr = "";
	while( str.indexOf(from) > -1 ) {
		i = str.indexOf(from);
		rStr += str.substring(0,i) + to;
		str = str.substring( i+from.length );
	}
	return rStr+str;
}

function CheckByte(str) {

	var IEYES = 0;
	var menufacture = navigator.appName;
	var version = navigator.appVersion;
	var brow = navigator.appName;

	if((0 < brow.indexOf('Explorer'))
			&& (version.indexOf('4') >= 0 || version.indexOf('5') > 0
					|| version.indexOf('6') > 0 || version.indexOf('7') > 0
					|| version.indexOf('8') > 0 || version.indexOf('9') > 0)) 	{
		IEYES = 1;
	}

	var i;
	var strLen;
	var strByte;
	strLen = str.length;

	if (IEYES == 1) 	{
		for (i=0, strByte=0;i<strLen;i++) 		{
			if(str.charAt(i) >= ' ' && str.charAt(i) <= '~' ) {
				strByte++;
			} else {
				strByte += db_korean_byte;
			}
		}
		return strByte;
	} else 	{
		return strLen;
	}
}

function ltrim(para) {
	while(para.substring(0,1) == ' ') {
		para = para.substring(1, para.length);
	}
	return para;
}

function mtrim(para) {
	for ( i = 0; i < para.length;) {
		if ( para.substring(i,i+1) == ' ' ) {
			para = para.substring(0, i) + para.substring(i+1, para.length);
		} else {
			i++;
		}
	}
	return para;
}

function rtrim(para) {
	while(para.substring(para.length-1, para.length) == ' ') {
		para = para.substring(0, para.length-1);
	}
	return para;
}

function UnComma(input) {
	var    inputString  = new String;
	var    outputString = new String;
	var    outputNumber = new Number;
	var    counter = 0;

	inputString  = input;
	outputString = '';

	for (counter = 0; counter < inputString.length; counter++) {
		outputString += (inputString.charAt(counter) != ',' ?
				inputString.charAt(counter) : '');
	}
	outputNumber = parseFloat(outputString);
	return (outputNumber);
}

function Comma(input) {
	var inputString = new String;
	var outputString = new String;
	var counter = 0;
	var decimalPoint = 0;
	var end =0;

	inputString = input.toString();

	outputString = '';

	decimalPoint = inputString.indexOf('.', 1);

	if(decimalPoint == -1) 	{
		end = inputString.length;
		for (counter = 1; counter <= inputString.length; counter++) {
			outputString = (counter % 3 == 0  && counter < end ? ',' : '') + 	inputString.charAt(inputString.length - counter) + outputString;
		}
	} else 	{
		end = decimalPoint - (inputString.charAt(0) == '-' ? 1 : 0);
		for (counter = 1; counter <= decimalPoint; counter++) 	{
			outputString = (counter % 3 == 0 && counter < end ? ',' : '') + inputString.charAt(decimalPoint - counter) + outputString;
		} 
		for (counter = decimalPoint; counter < decimalPoint + 3; counter++) {
			outputString += inputString.charAt(counter);
		}
	}
	return (outputString);
}

function del_slash(para) {
	for (i = 0; i < para.length;) {
		if (para.substring(i, i + 1) == '/' ) {
			para = para.substring(0, i) + para.substring(i + 1, para.length);
		} else {
			i++;
		}
	}
	return para;
}

function add_slash(para) {
	var str = '';
	if (para.length != 8)
		return para;
	str = para.substring(0,4) + '/';
	str = str + para.substring(4,6) + '/';
	str = str + para.substring(6,8);
	return str;
}

function add_period(para) {
	var str = '';
	if (para.length != 8)
		return para;
	str = para.substring(0,4) + '.';
	str = str + para.substring(4,6) + '.';
	str = str + para.substring(6,8);
	return str;
}

function lrtrim(src)
{
	var search = 0

	while ( src.charAt(search) == " ")
	{
		search = search + 1
	}

	src = src.substring(search, (src.length))

	search = src.length - 1

	while (src.charAt(search) ==" ")
	{
		search = search - 1
	}

	return src.substring(0, search + 1)
}

function del_period(para)
{
	for (i = 0; i < para.length;)
		if (para.substring(i, i + 1) == '.' )
			para = para.substring(0, i) + para.substring(i + 1, para.length);
		else
			i++;
	return para;
}

//데이터 입력 시  데이터의 최대 길이를 체크한다.
function checkInputMaxLength(obj,title,maxLength)
{
	if (CheckByte(obj.value) > maxLength)
	{

		// alert(title + " 한글 " + Math.floor(maxLength/2) + "자  영문,숫자 "+ maxLength +"자 이내로 작성하십시오.");
		alert(exceed);
		obj.value = obj.value.substring(0,countMaxLength(obj.value,maxLength));		
		return false;
	}
	else{
		return true;
	}
}

//데이터 submit 시 데이터의 최대 길이를 체크한다.
function checkSubmitMaxLength(str,title,maxLength) {
	if (CheckByte(str) > maxLength) {		
		// alert("입력가능한 최대글자수를 초과하였습니다.");
		alert(title + " " + korean + " " + Math.floor(maxLength/2) + character + " " + englishnumber + " "+ maxLength + writewithin);
		return false;
	} else {
		return true;
	}
}

function countMaxLength(str,maxlength)
{

	var IEYES = 0;
	var menufacture = navigator.appName;
	var version = navigator.appVersion;
	var brow = navigator.appName;

	if((0 < brow.indexOf('Explorer'))
			&& (version.indexOf('4') >= 0 || version.indexOf('5') > 0
					|| version.indexOf('6') > 0 || version.indexOf('7') > 0
					|| version.indexOf('8') > 0 || version.indexOf('9') > 0))
	{
		IEYES = 1;
	}

	var i;
	var strLen;
	var strLength=0;;

	strLen = str.length;

	if (IEYES == 1)
	{
		for (i=0;i<maxlength;i++)
		{
			(str.charAt(i) >= ' ' && str.charAt(i) <= '~' ? strLength++ : strLength += db_korean_byte);
			if (strLength == maxlength) 
				return i+1;
			if (strLength > maxlength)
				return i;                                    
		}

		return maxlength;
	} else {
		return maxlength;
	}
}

function escapeDm(s)
{
	if (s.length > 0) {

		s  =   s.replace(/</g,'&lt');
		s  =   s.replace(/>/g,'&gt');
		s  =   s.replace(/\\/g,'\\\\');
		s  =   s.replace(/'/g,'\'');
		s  =   s.replace(/"/g,'&quot');

		//s  =   s.replace(/&/g,'&amp');

		return s;
	} else {
		return "";
	}
}

//Html 문자 로 변환
function escapeHtml(reqString) {
	return (reqString == null) ? "" : reqString.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\"/g,'&quot;');
}

//자바 스크립트 문자 로 변환
function escapeJavaScript(reqString) {
	return (reqString == null) ? "" : reqString.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\"/g,'&quot;').replace(/\'/g,'\\\'').replace(/#&/g,'&');
}

//자바 스크립트 특수문자 원래 문자로 변환
function unescapeJavaScript(reqString) {
	return (reqString == null) ? "" : reqString.replace(/&amp;/g,'&').replace(/&lt;/g,'<').replace(/&gt;/g,'>').replace(/&quot;/g,'"').replace(/\\\'/g,'\'').replace(/&/g,'#&');
}

function escapeCarriageReturn(reqString) {
	return (reqString == null) ? "" : reqString.replace(/\r\n/g, "<br/>").replace(/\r/g, "<br/>").replace(/\n/g, "<br/>");
}

function escapeHtmlCarriageReturn(reqString) {
	return (reqString == null) ? "" : reqString.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\"/g,'&quot;').replace(/\r\n/g, "<br/>").replace(/\r/g, "<br/>").replace(/\n/g, "<br/>");
}

function unescapeCarriageReturn(reqString) {
	if (reqString == null) {
		return "";
	}

	while (reqString.indexOf("<br/>") != -1) {
		reqString = reqString.replace("<br/>", '\r\n');
	}

	return reqString;
}

function escapeFilename(reqString) {
	return (reqString == null) ? "" : reqString.split("\r").join("").split("\n").join("").split("\\").join("").split("/").join("").split(":").join("").split("*").join("").split("?").join("").split("\"").join("").split("<").join("").split(">").join("").split("|").join("");
}

function replaceAll(reqString, findString, replaceString) {
	return (reqString == null) ? "" : reqString.split(findString).join(replaceString);
}