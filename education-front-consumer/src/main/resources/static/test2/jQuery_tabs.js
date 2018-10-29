(function($){
	$.fn.extend({
		eol_Tabs:function(options){
			var defaults    = {tabs:'tabs',content:'content',currentClass:'current',hoverClass:'',start:1,event:'mouseover'}
				options     = $.extend(defaults,options);
			if(!options.tabs && !options.content){return}
			return this.each(function(){
				var hover   = options.hoverClass,
					current = options.currentClass,
					$this   = $(this),
					start   = options.start,
					tabs    = $('.'+options.tabs,$this),
					con     = $('.'+options.content,$this);
				if(tabs.size()!=con.size()){return;}
				tabs.css('cursor','pointer').eq(start-1).addClass(current);
				con.css('display','none').eq(start-1).css('display','block');
				if(options.event=='mouseover'){
					tabs.mouseover(function(index){
						var flag=tabs.index($(this)[0]);
						tabs.removeClass(current).eq(flag).addClass(current);
						con.css('display','none').eq(flag).css('display','block');})
				}else if(options.event=='click'){
					var flag=start-1;
					if(hover!=''){
						tabs.hover(function(index){
							if(tabs.index($(this)[0])!=flag){
								$(this).addClass(hover);
							}
						},function(){
							if($(this).hasClass(hover)){
							$(this).removeClass(hover)}
						})
					}
					tabs.click(function(index){
						flag=tabs.index($(this)[0]);
						if($(this).hasClass(hover)){$(this).removeClass(hover)}
						tabs.removeClass(current).eq(flag).addClass(current);
						con.css('display','none').eq(flag).css('display','block');
					})
				}
			})
		}
	})
})(jQuery)
	
