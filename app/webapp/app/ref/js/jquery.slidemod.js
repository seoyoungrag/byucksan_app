
(function ( $ ) {
	jquery_slidemod_duration_divide = 8
	// 슬라이드 애니메이션이 완전히 끝나기 전에 강제 hide/show할 값을 정한다.
	// 너무 높으면 중간에 끊겨 버리고, 너무 낮으면 IE 브라우저에서 플리커 현상이 생긴다.
	// 7~8 권장값. 컴퓨터 사양 및 브라우저 버전에 따라 다르다.


	$.fn.slideUpMod = function( jquery_slidemod_duration ) {

        if (jquery_slidemod_duration == null){
            var jquery_slidemod_duration = 400;	// slide 기본값
        }
        var jquery_slidemod_duration_threshold = jquery_slidemod_duration/jquery_slidemod_duration_divide;

        $(this).slideUp(jquery_slidemod_duration);
        var jquery_slidemod_this_id = $(this);		// 먼저 this_id = $(this); 를 지정해주지 않으면 function(){ 괄호 내로 값을 가져가지 못한다.
        // MSIE 0-9 버전에서만 Mod 버전으로 슬라이드를 작동시키고 다른 버전에서는 기존 슬라이드를 작동시킨다.
        if(navigator.userAgent.match(/MSIE ([0-9])\./) ){
            var slideUpMod_timeout = setTimeout(function(){
            jquery_slidemod_this_id.hide();
            clearTimeout(slideUpMod_timeout);
            },jquery_slidemod_duration-jquery_slidemod_duration_threshold);
        }
	};

	$.fn.slideDownMod = function( jquery_slidemod_duration ) {
        if (jquery_slidemod_duration == null){
            var jquery_slidemod_duration = 400;	// slide 기본값
        }
        var jquery_slidemod_duration_threshold = jquery_slidemod_duration/jquery_slidemod_duration_divide;

        $(this).slideDown(jquery_slidemod_duration);
        var jquery_slidemod_this_id = $(this);		// 먼저 this_id = $(this); 를 지정해주지 않으면 function(){ 괄호 내로 값을 가져가지 못한다.
        // MSIE 0-9 버전에서만 Mod 버전으로 슬라이드를 작동시키고 다른 버전에서는 기존 슬라이드를 작동시킨다.
        if(navigator.userAgent.match(/MSIE ([0-9])\./) ){
            var slideDownMod_timeout = setTimeout(function(){
            jquery_slidemod_this_id.show();
            clearTimeout(slideDownMod_timeout);
            },jquery_slidemod_duration-jquery_slidemod_duration_threshold);
        }
	};

	$.fn.slideToggleMod = function( jquery_slidemod_duration ) {
        if (jquery_slidemod_duration == null)
            var jquery_slidemod_duration = 400;

        if ($(this).is(":hidden"))
            $(this).slideDownMod(jquery_slidemod_duration);
        else
            $(this).slideUpMod(jquery_slidemod_duration);
	}
}( jQuery ));