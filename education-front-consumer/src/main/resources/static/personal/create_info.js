$(document).ready(function(){
    

    //显示与隐藏选填信息
    $("#tog_click").toggle(function(){
        $(this).find("i").addClass("arrow_asc");
        $(".options_info").show();
    },function(){
        $(this).find("i").removeClass("arrow_asc");
        $(".options_info").hide();
    })

});

