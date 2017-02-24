/**  
 *  Description : 결재경로처리를 위한 공통 스크립트
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.06 
 *   수 정 자 : 장진홍
 *   수정내용 : 신규개발 
 * 
 *  @author  장진홍
 *  @since 2011. 04. 06 
 *  @version 1.0 
 *  @see
 */
/**
 * <tr> 객체를 답는 함수
 */
var g_selector = function(){
	var selector = new Array();
	//var bgcolor = "#FFFFFF";
	var bgcolors = new Array();
	
	var idx = 0,  mtype = 0;
	
	var bfontColor = "#707070";
	var afontColor = "#3333ff";
	
	return {
		add : function(obj, color){
			//if(idx == 0) bgcolor = obj.css('background-color');
			var ckobj = false;
			
			for(var i = 0; i < idx; i++){				
				if(selector[i].attr("id") == obj.attr("id")){
					ckobj = true;
					break;
				}
			}
			
			if(!ckobj){
				selector[idx] = obj;
				bgcolors[idx] = obj.css('background-color');
				obj.css('background-color',color);
				obj.children().css('color',afontColor);
				idx++;
			}
		}, 
		collection : function(){
			return selector;
		},
		count : function(){
			return selector.length;
		},
		restore : function(){
			
			for(var i =0; i< idx;i++){
				//alert(bgcolors[i]);
				selector[i].css('background-color',bgcolors[i]);
				selector[i].children().css('color',bfontColor);
			}
			this.removeAll();
		},
		restoreId : function(id){
			for(var i =0; i< idx;i++){
				if(selector[i].attr("id") !== id){
					selector[i].css('background-color',bgcolors[i]);
					break;
				}
			}
		},
		removeAll : function(){
			selector = null;
			selector = new Array();
			idx = 0,  mtype = 0;
		},
		removeId : function(id){
			var tmpArr = new Array();
			var colorArr = new Array();

			var tempCnt = 0;
			for(var i =0; i< idx;i++){
				if(selector[i].attr("id") !== id){
					tmpArr[tempCnt] 	= selector[i];
					colorArr[tmpCnt] 	= bgcolors[i];
					tempCnt++;
				}
			}
			
			selector = tmpArr;
			bgcolors = colorArr;
			idx = tmpArr.length;
		},
		last: function(){
			if(idx > 0){
				return selector[idx-1];
			}

			return null;
		},
		first: function(){
			if(selector.length > 0){
				return selector[0];
			}

			return null;
		},
		setType:function(ntype){
			mtype = ntype
		},
		getType:function(){
			return mtype;
		}
	};
}