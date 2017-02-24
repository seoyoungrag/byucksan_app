// e : event,
// decimal : 소숫점 허용여부
// style="ime-mode:disabled;" onkeypress="return onlyNumber(event, false);"
function onlyNumber(e, decimal) {
	var key;
	var keychar;

	if (window.event) {
		key = window.event.keyCode;
	} else if (e) {
		key = e.which;
	} else {
		return true;
	}

	keychar = String.fromCharCode(key);

	if ((key == null) || (key == 0) || (key == 8) || (key == 9) || (key == 13)
			|| (key == 27)) {
		return true;
	} else if ((('0123456789').indexOf(keychar) > -1)) {
		return true;
	} else if (decimal && (keychar == '.')) {
		return true;
	} else {
		return false;
	}
}


//필수입력항목 체크
function validateMandatory(){

    // var frm = obj.all;
    var obj = $('*[mandatory="Y"]');
    if(obj.length <= 0) return true;
    
    for(var i=0; i<obj.length; i++){  
        //alert('obj'+obj[i].value);
        if(obj[i].tagName == 'INPUT'){
        	//라디오
        	if(obj[i].type =='radio'){
                var robj = $('INPUT:radio[name="'+obj[i].name+'"]').attr("checked");
                if(!robj){
            		alert(obj[i].msg+"을(를) 입력하세요");
                    obj[i].focus();
                    return false;
                }
            }
            //체크박스
            if(obj[i].type =='checkbox'){
                var cobj = $('INPUT:checkbox[cname="'+obj[i].name+'"]]').attr("checked");
                if(!cobj){
                    alert(obj[i].msg+"을(를) 입력하세요");
                    obj[i].focus();
                    return false;
                }
            }
            else{
                if(obj[i].value ==''){
  
                    alert(obj[i].msg+"을(를) 입력하세요");
                    obj[i].focus();
                    return false;
                }
            }                
        }
        else{
            if(obj[i].value ==''){
                alert(obj[i].msg+"을(를) 입력하세요");
                obj[i].focus();
                return false;
            }
        }                
    }
    return true;
}

/**
 * Trims whitespace from either end of a string, leaving spaces within the string intact.  Example:
 * <pre><code>
var s = '  foo bar  ';
alert('-' + s + '-');         //alerts "- foo bar -"
alert('-' + s.trim() + '-');  //alerts "-foo bar-"
</code></pre>
 * @return {String} The trimmed string
 */
String.prototype.trim = function(){
    var re = /^\s+|\s+$/g;
    return function(){ return this.replace(re, ""); };
}();


var printDoc = null;
// 인쇄

function printBox(linkUrl, param) {
	commonPrintBox(linkUrl, param);
}

function commonPrintBox(linkUrl, param) {
	if(param != null && param.length > 0) {
		linkUrl += '?' + param;
	}
	
	if(isPrintOpen()) {
		var width = 900;
		if (screen.availWidth < 900) {
		    width = screen.availWidth;
		}
		
		var height = 700;
		if (screen.availHeight > 700) {
		    height = screen.availHeight;
		}
		
		var top = (screen.availHeight - height) / 2;
		var left = (screen.availWidth - width) / 2;
		// var option =
		// "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=1,status=yes";
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=yes,scrollbars=1,status=yes";

		printDoc = window.open(linkUrl, "printDoc", option);
	}
}

// 문서창 확인
function isPrintOpen(){
	// 문서창이 열려 있으면 확인 후 닫는다.
	if(printDoc != null && printDoc.closed == false) {
		if(confirm(this.msg_print_open)){
			printDoc.close();
			return true;
		}else{
			return false;
		}
	}else{
		return true;
	}	
}