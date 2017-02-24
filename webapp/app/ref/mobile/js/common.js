jQuery(function($) {
	$(".navbtn").click(function(){
		if ( $(".main_nav").is('.hidden')) {
			$(".main_nav").removeClass("hidden");
			$(".main_nav").slideDown("fast");
		}else{
			$(".main_nav").addClass("hidden");
			$(".main_nav").slideUp("fast");
		}
	});
	$(".nav_btn").click(function(){
		if ( $(".main_nav").is('.hidden')) {
			$(".main_nav").removeClass("hidden");
			$(".main_nav").slideDown("fast");
		}else{
			$(".main_nav").addClass("hidden");
			$(".main_nav").slideUp("fast");
		}
	});
	$(".main_nav .closebtn").click(function(){
		$(".main_nav").addClass("hidden");
		$(".main_nav").slideUp("fast");
	});
	
	$(".top_btn").click(function(){
		$('html, body').animate({scrollTop:0}, 200, "linear");
	})
	
	$(".main_nav .sub > a").click(function(){
		var parent = $(this).parent("li");
		if ( $(parent).is('.active')) {
			$(parent).removeClass("active").find("ul:first").slideUp("fast");
		}else{
			$(parent).addClass("active").find("ul:first").slideDown("fast");
		}
	});
	
	$(".sub_nav .sub > a").click(function(){

		var parent = $(this).parent("li");
		if ( $(parent).is('.active')) {
			//$(parent).removeClass("active").find("ul:first").slideUp("easeInQuart");
		}else{
			(parent).parent().find("li.sub").removeClass("active");
			$(parent).parent().find(".sub > a").css("color","#808080");
			$(parent).parent().find(".arrow").css("display","none");
			$(parent).parent().find(".depth2").css("display","none");
			
			$(parent).addClass("active");
			$(parent).find(" > a").css("color","#5478b4");
			$(parent).find(".arrow").css("display","block");
			$(parent).find(".depth2").css("display","block");
		}
	});
	//alert(window.innerHeight);
	var checkGap = 93;
	var gapsize = 10;
	
	$(".box_slideUp_btn").click(function(){
		var parent = $(this).parent().find("ul.content");
		if($(parent).is(".open")){
			$(parent).removeClass("open").slideUp("fast");
			$(".box_slideUp_btn").text("답글펼치기 ▼");
		}else{
			$(parent).addClass("open").slideDown("fast");
			$(".box_slideUp_btn").text("답글접기 ▲")
		}
	});
	$(".option_slideUp_btn").click(function(){
		var parent = $(this).parent().find("ul");
		if($(parent).is(".open")){
			$(parent).removeClass("open").slideUp("fast");
			$(".option_slideUp_btn span").text("▼");
		}else{
			$(parent).addClass("open").slideDown("fast");
			$(".option_slideUp_btn span").text("▲")
		}
	});
	
	// Style Radios and checkboxes style changes
	$('input:radio, input:checkbox').change( function(event)
	{
		var element = $(this),
			replacement = element.data('replacement'),
			checked = this.checked;
		// Update visual styles
		if (replacement)
		{
			// Update style
			replacement[checked ? 'addClass' : 'removeClass']('checked');
		}
		// Button labels
		else if (element.parent().is('label.button') || element.parent().is('label.radio') || element.parent().is('label.checkbox'))
		{
			element.parent()[checked ? 'addClass' : 'removeClass']('active');
		}
		
		// If radio, refresh others without triggering 'change'
		if (this.type === 'radio')
		{
			$('input[name="'+this.name+'"]:radio').not(this).each(function(i)
			{
				var input = $(this),
					replacement = input.data('replacement');

				// Switch
				if (replacement)
				{
					replacement[checked ? 'removeClass' : 'addClass']('checked');
				}
				// Button labels
				else if (input.parent().is('label.button') || input.parent().is('label.radio'))
				{
					input.parent()[checked ? 'removeClass' : 'addClass']('active');
				}
			});
		}
	});
	
});


!function ($) {
  "use strict"; // jshint ;_;
 /* DROPDOWN CLASS DEFINITION
  * ========================= */
  var toggle = '[data-toggle=dropdown]'
    , Dropdown = function (element) {
        var $el = $(element).on('click.dropdown.data-api', this.toggle)
        $('html').on('click.dropdown.data-api', function () {
          $el.parent().removeClass('open')
        })
      }
  Dropdown.prototype = {
    constructor: Dropdown
  , toggle: function (e) {
      var $this = $(this)
        , $parent
        , isActive
      if ($this.is('.disabled, :disabled')) return
      $parent = getParent($this)
      isActive = $parent.hasClass('open')
      clearMenus()
      if (!isActive) {
        $parent.toggleClass('open')
        $this.focus()
      }
      return false
    }

  , keydown: function (e) {
      var $this
        , $items
        , $active
        , $parent
        , isActive
        , index

      if (!/(38|40|27)/.test(e.keyCode)) return
      $this = $(this)
      e.preventDefault()
      e.stopPropagation()
      if ($this.is('.disabled, :disabled')) return
      $parent = getParent($this)
      isActive = $parent.hasClass('open')
      if (!isActive || (isActive && e.keyCode == 27)) return $this.click()
      $items = $('[role=menu] li:not(.divider) a', $parent)
      if (!$items.length) return
      index = $items.index($items.filter(':focus'))
      if (e.keyCode == 38 && index > 0) index--                                        // up
      if (e.keyCode == 40 && index < $items.length - 1) index++                        // down
      if (!~index) index = 0
      $items
        .eq(index)
        .focus()
    }
  }

  function clearMenus() {
    $(toggle).each(function () {
      getParent($(this)).removeClass('open')
    })
  }

  function getParent($this) {
    var selector = $this.attr('data-target')
      , $parent
    if (!selector) {
      selector = $this.attr('href')
      selector = selector && /#/.test(selector) && selector.replace(/.*(?=#[^\s]*$)/, '') //strip for ie7
    }
    $parent = $(selector)
    $parent.length || ($parent = $this.parent())
    return $parent
  }

  /* DROPDOWN PLUGIN DEFINITION
   * ========================== */
  $.fn.dropdown = function (option) {
    return this.each(function () {
      var $this = $(this)
        , data = $this.data('dropdown')
      if (!data) $this.data('dropdown', (data = new Dropdown(this)))
      if (typeof option == 'string') data[option].call($this)
    })
  }
  $.fn.dropdown.Constructor = Dropdown

  /* APPLY TO STANDARD DROPDOWN ELEMENTS
   * =================================== */

  $(document)
    .on('click.dropdown.data-api touchstart.dropdown.data-api', clearMenus)
    .on('click.dropdown touchstart.dropdown.data-api', '.dropdown form', function (e) { e.stopPropagation() })
    .on('click.dropdown.data-api touchstart.dropdown.data-api'  , toggle, Dropdown.prototype.toggle)
    .on('keydown.dropdown.data-api touchstart.dropdown.data-api', toggle + ', [role=menu]' , Dropdown.prototype.keydown)

}(window.jQuery);


/* SCROLL
 * =================================== */

  var myScroll;
  function loaded() {
      myScroll = new iScroll('wrapper', {
          useTransform: false,
          onBeforeScrollStart: function (e) {
          var target = e.target;
          while (target.nodeType != 1) target = target.parentNode;
          if (target.tagName != 'SELECT' && target.tagName != 'INPUT' && target.tagName != 'TEXTAREA')
              e.preventDefault();
          }
      });
  }
  
  document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
  
  document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 200); }, false);


