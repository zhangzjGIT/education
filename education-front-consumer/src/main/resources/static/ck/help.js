// JavaScript Document
function laycodeChange(){
	var hm=$('.content');
	var len_html=hm.has('.brush_html').length,
	len_js=hm.has('.brush_js').length,
	len_as3=hm.has('.brush_as3').length,
	len_php=hm.has('.brush_php').length,
	len_xml=hm.has('.brush_xml').length;
	if(len_html>0){
		$('.brush_html').laycode({
			title: 'HTML',
			by: false,
			skin:6
		});
	}
	if(len_js>0){
		$('.brush_js').laycode({
			title: 'javascript',
			by: false,
			skin:2
		});
	}
	if(len_as3>0){
		$('.brush_as3').laycode({
			title: 'actionscript3.0',
			by: false,
			skin:4
		});
	}
	if(len_php>0){
		$('.brush_php').laycode({
			title: 'PHP',
			by: false,
			skin:1
		});
	}
	if(len_xml>0){
		$('.brush_xml').laycode({
			title: 'XML',
			by: false,
			skin:1
		});
	}
}
$(document).ready(function($) {
	laycodeChange();
	$(".tli").click(function(){
			$(this).next(".ishidden").slideToggle(500).siblings(".ishidden").slideUp(500);
	})
});