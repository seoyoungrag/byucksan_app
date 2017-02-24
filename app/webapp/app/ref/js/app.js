/**
 * JSON 형식으로 AJAX Request
 * jsonCall이랑 callJson이랑 너무 헤깔림 --;;
 *
 * @param mvcBeanId 컨트롤러 ID
 * @param methodName 컨트롤러 메소드명
 * @param params  호출 파라미터 (QueryString 형식 or JSON 형식)
 * @param callback  콜백 함수명 (default: jsonCallback) [optional]
 * @return
 */
function jsonCall(mvcBeanId, methodName, params, callback) { callJson(mvcBeanId, methodName, params, callback); }
function callJson(mvcBeanId, methodName, params, callback) {
	if(mvcBeanId == null || methodName == null) {
		alert("Invalid Call");
		return;
	}

	$('#loading_red').show();
	
	params = params == null ? "" : "&" + params; 
	var uri = "/ep/app/json/request.do?mvcBeanId=" + mvcBeanId + "&methodName=" + methodName;
	$.getJSON(uri+params, function(data) {
		if(callback) {
			callback(data);
		}
		$('#loading_red').hide();
	});
}

function postJson(mvcBeanId, methodName, params, callback) {
	if(mvcBeanId == null || methodName == null) {
		alert("Invalid Call");
		return;
	}

	$('#loading_red').show();
	
	var uri = "/ep/app/json/request.do";
	params = params == null ? "" : "&" + params;
	params = "mvcBeanId=" + mvcBeanId + "&methodName=" + methodName + params;
	$.post(uri, params, function(data) {
		if(callback) {
			callback(data);
		}
		$('#loading_red').hide();
	}, 'json');
}

/*
Object.prototype.clone = function() {
	var newObj = (this instanceof Array) ? [] : {};
	for (i in this) {
		if (i == 'clone') continue;
		if (this[i] && typeof this[i] == "object") {
			newObj[i] = this[i].clone();
		} else newObj[i] = this[i]
	} 
	return newObj;
};
*/
/**
 * Javascript Array에는 remove 기능 추가
 */
Array.prototype.remove = function(object) {
	var from = this.indexOf(object);
	var to = from;
	var rest = this.slice((to || from) + 1 || this.length);
  	this.length = from < 0 ? this.length + from : from;
  	return this.push.apply(this, rest);
};

/**
* n번째 인덱스의 배열요소를 삭제
* @param index
* @return
 */
Array.prototype.removeAt = function(index) {
	var arr = this.slice(0, index).concat(this.slice(index + 1));
	return arr;
};

/**
 * Javascript Array에 indexOf 기능 추가
 * String의 indexOf와 같음
 */
Array.prototype.indexOf = function(object) {
	for (var i = 0, length = this.length; i < length; i++)
		if (this[i] == object) return i;
	return -1;
};

String.prototype.escapeHTML = function() {
	return this.replace(/</g,'&lt;').replace(/>/g, '&gt;');
};

/**
 * function same as Java's HashMap
 * 
 * ex) var cache = new HashMap();
 *     cache.put('test','good');
 *     alert(cache.get('test'));
 */
var HashMap = function() {
	this.initialize();
}

HashMap.prototype = {
	initialize: function() {
		this.entrySize = 0;
        this.keyEntry = new Array();
        this.valueEntry = new Array();
	},

	indexOf: function(arr, obj) {
		for(i=0; i < arr.length; i++) {
			if(arr[i]==obj) {
				return i;
			}
		}
		return -1;
	},
	
    clear: function() {
    	var stack = new Array();
    	for(var i=0; i < this.keyEntry.length; i++) {
    		stack.push(this.keyEntry[i]);
    	}
    	while(stack.length > 0) {
    		this.remove(stack.pop());
    	}
        this.entrySize = 0;
    },

    put: function(key, value) {
    	var idx = this.indexOf(this.keyEntry,key);
    	if(idx > -1) {
	        this.valueEntry[idx] = value;
    	}
    	else {
	        this.keyEntry[this.keyEntry.length] = key;
	        this.valueEntry[this.valueEntry.length] = value;
	        this.entrySize++;
    	}
    },

    get: function(key) {
        var idx = this.indexOf(this.keyEntry,key);
        if(idx > -1 && this.valueEntry[idx]!=null) {
            return this.valueEntry[idx];
        } else {
            return null;
        }
    },

    remove: function(key) {
        var idx = this.indexOf(this.keyEntry,key);
        if(idx==-1) {
            return null;
        }
        var retValue = this.valueEntry[idx];
        
		this.keyEntry.splice(idx,1);
		this.valueEntry.splice(idx,1);		        
        this.entrySize--;
        return retValue;
    },

    size: function() {
        return this.keyEntry.length;
    },

    containsKey: function(key) {
        var idx = this.indexOf(this.keyEntry,key);
        return idx > -1;
    },

    containsValue: function(value) {
        var idx = this.indexOf(this.valueEntry,key);        
        return idx > -1;
    },
    
    toString: function() {
    	var str = "[";
    	for(var i=0; i < this.keyEntry.length; i++) {
    		var comma = ",";
    		if(i==this.keyEntry.length-1) {
    			comma = "";
    		}
    		str += this.keyEntry[i] + ":" + this.valueEntry[i] + comma; 
    	}
    	str += "]";
    	return str;
    }
}

  
/**
 * 포탈에서 사용하는 공통 함수 정의
 * 호출방법 : app.xxx();
 */
var app = {
	isLoggedIn: false,
	usePersonalMenu: false,
	popupWindow: new Array(),

	/**
	 * jQuery의 $('#id') 와 같은 역할을 함
	 * ActiveX같은 오브젝트에 접근할때 jQuery의 $('#id')를 사용할 수 없어
	 * document.getElementById를 재정의 함
	 * 
	 *  사용법은 jQuery와 같음
	 *  앞에 app만 붙여주면 됨
	 *  
	 *  예) app.$('#objId')
	 */
	$: function(id) {
		if(id.indexOf('#')!=0) {
			return null;
		}
		return document.getElementById(id.substring(1));
	},
		
	/**
	 * Unique ID 생성
	 * 서버의 CommonUtil.generateId를 호출해서 값을 받아옴
	 */
	generateId: function(prefix) {
		if(this.isNullOrEmpty(prefix)) {
			prefix = "";
		}
		
		var id = "";
		$.ajax({
			url: "/ep/common/jsProxy/generateId.jsp?prefix="+prefix, 
			async: false, 
			success: function(data) {	
				id = app.trim(data);
			}
		});
		return id;
	},

	/**
	 * 엔터 체크
	 * 이벤트 넘겨주고 엔터일때 처리할 로직 넘겨줌
	 */
	checkEnter: function(e, action) {
		var keycode = e.keyCode || e.which;
		if(keycode==13) {
			eval(action);
		}
	},
	
	showTooltip: function(e,msg) {
		var pos = this.getMousePos(e);		
		this.tooltip.innerHTML = msg;
		this.tooltip.style.display = 'block';
		this.tooltip.style.top = pos.y + 20;
		this.tooltip.style.left = pos.x - this.tooltip.offsetWidth + 20;		
	},
	
	hideTooltip: function() {
		this.tooltip.style.display = 'none';
	},

	stripDash: function(str) {
		return str.replace(/-/,"");
	},
	
	trim: function(str) {
	    return str.replace( /^\s*/, "" ).replace( /\s*$/, "" );
	},
	
	/**
	 * Reference : http://www.bram.us/2008/02/01/javascript-isarray-check-if-an-elementobject-is-an-array/
	 */
	isArray: function(obj) {
	    return obj.constructor == Array;
	},
	
	setOpacity: function(obj,value) {
		obj.style.filter = "alpha(opacity=" + value + ")";
		obj.style.opacity = (value / 100);
		obj.style.MozOpacity = (value / 100);
		obj.style.KhtmlOpacity = (value / 100);
		if(obj.style.display=='none') {
			obj.style.display="block";
		}
	},
	
	isNullOrEmpty: function(str) {
		if(typeof(str) == 'undefined' || !str || str==null || str=='null') {
			return true;
		} else {
			if(typeof(str) == 'string' && this.trim(str)=='') {
				return true;
			}
			return false;
		}
	},
	
	isBlank: function( str ) {
	    var regular = /^ *$/;
	    return str.match( regular );
	},
	
	hasBlank: function( str ) {
		for(var i=1; i < str.length-1; i++) {
			if(str.charCodeAt(i)==32) {
				return true;
			}
		}
		return false;
	},
	
	isEmail: function(str) {
		var rex = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;
		return rex.test(str);
	},
	
	/**
	 * 특수문자가 있는지 체크 (한글허용안됨)
	 * 알파벳, 숫자, _(언더바)만 허용됨
	 */
	hasSpecialChar: function(str) {
		var rex = /[^_a-zA-Z0-9]/;
		return rex.test(str);
	},

	/**
	 * 특수문자가 있는지 체크 (한글허용)
	 */
	hasSpecialChar2: function(str) {
		var rex = /[\`\~\!\@\#\$\%\^\&\*\+\=\\\\|\'\;\:\"\?\>\<]/;
		return rex.test(str);
	},

	/**
	 * 문자열중에 특수문자 제거
	 * type이 1이면 알파벳, 숫자, _(언더바)만 남기고 모두 제거 (hasSpecialChar로 체크)
	 * type이 2이면 hasSpecialChar2로 체크해서 특수문자 제거
	 */
	removeSpecialChar: function(str, type) {
		if(this.isNullOrEmpty(str)) {
			return str;
		}
		if(this.isNullOrEmpty(type)) {
			type = "1";
		}
		
		var newStr = "";
		for(var i=0; i < str.length; i++) {
			var ch = str.charAt(i);
			if(type=="1") {
				if(!this.hasSpecialChar(ch)) {
					newStr += ch;
				}
			}
			if(type=="2") {
				if(!this.hasSpecialChar2(ch)) {
					newStr += ch;
				}
			}
		}
		
		return newStr;
	},

	/**
	 * 마우스 커서의 현재 위치 정보 구함
	 * jQuery 사용시에는 e.pageX, e.pageY 사용할 것
	 */ 
	getMousePos: function(e) {	
		mouseX = (e.pageX) ? e.pageX : (e.clientX + document.body.scrollLeft - document.body.clientLeft);
		mouseY = (e.pageY) ? e.pageY : (e.clientY + document.body.scrollTop - document.body.clientTop);
		return { x:mouseX, y:mouseY };
	},
	
	/**
	 * 엘리먼트의 위치정보
	 * jQuery 사용시에는 position() 사용할 것
	 */
    getAbsPos: function(element) {
        var left = 0;
        var top  = 0;

        while (element.offsetParent) {
            // add border width
            left += element.offsetLeft;
            top += element.offsetTop;
            element = element.offsetParent;
        }

        left += element.offsetLeft;
        top  += element.offsetTop;

        return {x:left, y:top};
    },

    dialog: function(obj, options, e) {
    	if(typeof(options)=='undefined') { options = { top:50, modal:true }; }
   		if(typeof(options.top)=='undefined') { options.top = 50; }
   		if(typeof(options.modal)=='undefined') { options.modal = true; }
   		
		this.setCenter(obj, options.top);
		
    	if(options.modal) {
    		obj.attr('caller',app.generateId('C'));
    		$('#alphabg').attr('caller',obj.attr('caller'));
    		$('#alphabg').height($(document).height());
    		$('#alphabg').show();
    	}
    	
    	obj.show();
    },
    
    dialogClose: function(obj, finalize) {
    	try { 
    		if(obj.attr('caller')==$('#alphabg').attr('caller')) {
    			$('#alphabg').hide();
    		}
    		obj.hide();
    		if(finalize) finalize();
    	} catch(e) {}
    },

    loading: function(obj, options) {
    	if(typeof(options)=='undefined') { options = { type:'circle', size:'s', caption:'', append:true, modal:false }; }
    	if(typeof(options.type)=='undefined') { options.type = 'circle'; }
   		if(typeof(options.size)=='undefined') { options.size = 's'; }
   		if(typeof(options.caption)=='undefined') { options.caption = ''; }
   		if(typeof(options.append)=='undefined') { options.append = true; }
   		if(typeof(options.modal)=='undefined') { options.modal = false; }
   		obj.html("<div class='loading'><img src='/ep/ref/image/common/loading_"+options.type+"_"+options.size+".gif' /><div>"+options.caption+"</div></div>");
   		if(!options.append) {
   			if(options.modal) {
   	    		obj.attr('caller',app.generateId('C'));
   	    		$('#alphabg').attr('caller',obj.attr('caller'));
   				$('#alphabg').height($(document).height());
   				$('#alphabg').show();
   			}
   	   		var pos = obj.offset();
   	   		var objW = obj.width();
   	   		var objH = obj.height();
   	   		var loadingW = obj.find('.loading').width();
   	   		var loadingH = obj.find('.loading').height();
   	   		var axisX = pos.left + objW/2 - loadingW/2;
   	   		var axisY = pos.top + objH/2 + loadingH/2;
   	   		if(axisY > 300) axisY -= 100;
   	   		obj.find('.loading').css('position','absolute');
   	   		obj.find('.loading').offset({top:axisY, left:axisX});
   		}
   		obj.find('.loading').show();
    },

    loadingClose: function(obj, finalize) {
    	try {
    		if(obj.attr('caller')==$('#alphabg').attr('caller')) {
    			$('#alphabg').hide();
    		}
    		obj.find('.loading').remove();
    		if(finalize) finalize();
    	} catch(e) {}
    },

	setCenter: function(obj,moveAmout) {
	    var _xc = parseInt($(document).width()) / 2;
	    var _yc = parseInt($(window).height()) / 2;
	    var box_w_c = parseInt(obj.width()) / 2;
	    var box_h_c = parseInt(obj.height()) / 2;
	    if(!moveAmout) {
	    	moveAmout = 0;
	    }
	    obj.css("left",(_xc - box_w_c + "px"));
	    obj.css("top",($(window).scrollTop() + _yc - box_h_c - moveAmout + "px"));
	},

	setCenterX: function(obj) {
	    var _xc = parseInt($(document).width()) / 2;
	    var box_w_c = parseInt(obj.width()) / 2;
	    obj.css("left",(_xc - box_w_c + "px"));
	},

	setCenterY: function(obj) {
	    var _yc = parseInt($(document).height()) / 2;
	    var box_h_c = parseInt(obj.height()) / 2;
	    obj.css("top",(_yc - box_h_c + "px"));
	},
	
	setAtMousePos: function(e, obj, direction) {		
		if(e.type!='click') {
			this.setCenter(obj);
			return;
		}
		var pos = this.getMousePos(e);
		obj.style.top = pos.y + 20;	
		if(!direction) {
			obj.style.left = pos.x - 20;
		}
		if(direction=='left') {
			obj.style.left = pos.x - obj.offsetWidth + 20;
		}
	},
	
	/**
	 * Validate form's input(text,password type) elements
	 */
	validateForm: function(formElement) {
		var form = $(formElement);
		var inputs = form.getInputs();
		for(var i=0; i < inputs.length; i++) {
			if(inputs[i].type=='text' || inputs[i].type=='password') {
				if(!this.trim(inputs[i].value)) {
					return inputs[i].id;
				}
			}
		}
	},
	
	escapeSpecialChar: function(str) {
		str = str.replace("#","%23");
		str = str.replace("&","%26");
		str = str.replace("\"","%22");
		str = str.replace("<","%3C");
		str = str.replace(">","%3E");
		str = str.replace("+","%2B");
		return str;
	},

	nl2br: function(str) {
		str = str.replace("\n","<br />");
		str = str.replace("\r","<br />");
		str = str.replace("\r\n","<br />");
		return str;
	},

	setCookie: function(key,value) {
		var exdate=new Date();
		exdate.setDate(exdate.getDate()+7);
		document.cookie = key+"="+escape(value)+";expires="+exdate.toGMTString();
	},
	
	getCookie: function(key) {
		if(document.cookie.length > 0) {
  			c_start=document.cookie.indexOf(key+"=");
  			if(c_start!=-1) {
			    c_start = c_start + key.length+1; 
			    c_end = document.cookie.indexOf(";",c_start);
    			if(c_end==-1) {
    				c_end = document.cookie.length;
    			}
    			return unescape(document.cookie.substring(c_start,c_end));
			}
		}
		return ""
  	},
  	
  	popup: function(url, option) {
  		var option = option;
  		var screenW = screen.availWidth;
  		var screenH = screen.availHeight;
  		var windowW = option.width;
  		var windowH = option.height;
  		var left = (screenW - windowW)/2;
  		var top = (screenH - windowH)/2;
  		
  		if(typeof(option.resizable)=='undefined') option.resizable = "no";
  		if(typeof(option.toolbar)=='undefined') option.toolbar = "no";
  		if(typeof(option.status)=='undefined') option.status = "no";
  		if(typeof(option.scrollbars)=='undefined') option.scrollbars = "no";
  		
  		var opt = "toolbar="+option.toolbar+",scrollbars="+option.scrollbars+",resizable="+option.resizable+",status="+option.status+",location=no,menubar=no,width="+windowW+",height="+windowH+",left="+left+",top="+top;
  		var openedWinObj = window.open(url,"",opt);
  		openedWinObj.focus();
  		this.popupWindow.push(openedWinObj);
  	},
  	
  	setOpacity: function(obj,value) {
	    obj.style.filter = "alpha(opacity=" + value + ")";
	    obj.style.opacity = (value / 100);
	    obj.style.MozOpacity = (value / 100);
	    obj.style.KhtmlOpacity = (value / 100);
	    obj.style.display="block";
	},
	
	replaceHtml: function(el, html) {
		var oldEl = typeof el === "string" ? document.getElementById(el) : el;
		var newEl = oldEl.cloneNode(false);
		newEl.innerHTML = html;
		oldEl.parentNode.replaceChild(newEl, oldEl);
		return newEl;
	}, 
	
	_resizeAlphaBG: function() {
		var docHeight = $(document).height();
		var scroll = $(window).height() + $(window).scrollTop();
		if(docHeight == scroll) {
			// 스크롤이 가장 밑으로 내려왔을때
		}
		$('#alphabg').height($(document).height());
	},
	
	setLoadingRed: function() {
		if(!$('#loading_red').is('span')) {
			$('body').append("<span id='loading_red' class='loading-red'>Loading..</span>");
			$('#loading_red').css('top',($(window).height()+$(window).scrollTop()-22));						$('#loading_red').hide();
		}
	}
}

$(window).scroll(function() { 
	app._resizeAlphaBG();
	app.setLoadingRed();
});

$(window).resize(function() { 
	app._resizeAlphaBG();
	app.setLoadingRed();
});

/**
 * 열려진 팝업창 모두 닫음
 */
$(window).unload(function() {
	try {
		while(app.popupWindow.length > 0) {
			app.popupWindow.pop().close();
		}
	} catch(e) {}
});


$(document).ready(function() {
	app.setLoadingRed();
	
	// 포탈에서 사용되는 모든 목록에는 hover 적용 (.except-hover)가 들어있는 경우에는 예외
	$('table.table-body:not(.except-hover) > tbody > tr').live('mouseover mouseout', function(e) {
		if(e.type=='mouseover') {
			$(this).addClass('hover');
		} else {
			$(this).removeClass('hover');
		}
	});
	
	// input박스에 ID와 같이 알파벳+숫자+언더바 조합만 허용할 경우 아래와 같이 only-alphabet-number-underbar 클래스를 부여
	// ex) <input type='text' class='only-alphabet-number-underbar' />
	$(':text.only-alphabet-number-underbar').live('keyup', function(e) {
		if(app.hasSpecialChar($(this).val())) {
			alert(portal_alert_msg_41);
			$(this).val(app.removeSpecialChar($(this).val(), "1"));
			return false;
		}
	});

	// input박스에 특수문자 금지할 경우 아래와 같이 no-special-char 클래스를 부여
	// ex) <input type='text' class='no-special-char' />
	$(':text.no-special-char').live('keyup', function(e) {
		if(app.hasSpecialChar2($(this).val())) {
			alert(portal_alert_msg_40);
			$(this).val(app.removeSpecialChar($(this).val(), "2"));
			return false;
		}
	});

	// readonly 사용한 input 필드 readonly 처리
	$('.input-readonly').attr('readonly','readonly');
	
});

