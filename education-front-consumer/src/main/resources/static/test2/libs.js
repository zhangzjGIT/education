$(document).ready(function(){
    //全选、取消全选的事件
    $("[name='checkbox_name[]']:checkbox").click(function(){
        var flag = true;
        // 判断选中的框个数 
        $("[name='checkbox_name[]']:checkbox").each(function(){
            if (!this.checked) {
            	flag = false;
            }
        });
        $("#checkedall").prop("checked", flag);
        $(this).parent().parent().removeClass("clickclass");
        var num = $("input.checkbox:checked").length; // 统计已选择复选框个数
        var foo = $(this).parent().next().find('span a.Del_libs');
        if($(this).is(":checked")){
            if ( num > 1 ) {
                $(".tr_selected span").text( "已选中本页"+num+"个文件" );
                $(".tr_selected").show(); // 已选中文件显示提示操作
                $(".videofile_wraper tbody tr").find('span a.Del_libs').html("&#160;");
            } else if ( num == 1 ) {
                var inputId = $(this).attr('id');
                Del_flag    = inputId;
                $(".tr_selected span").text( "已选中本页"+num+"个文件" );
                $(".tr_selected").show(); // 已选中文件显示提示操作
            }
        } else {
            $(".tr_selected span").text( "已选中本页"+num+"个文件" );
            if ( num == 1 ) {
                // $("input.checkbox:checked").parent().next().find('span a.Del_libs').html('删除');
                $(".videofile_wraper tbody tr").find('span a.Del_libs').html("删除");
            } else if ( num < 1 ) {
                $(".tr_selected").hide();  // 已选中文件隐藏提示操作
                $(".videofile_wraper tbody tr").find('span a.Del_libs').html("删除");
            }
        }
    });

    // 全选操作
    $("#checkedall").click(function(){
        $("[name='checkbox_name[]']:checkbox").prop("checked", this.checked);
        $("[name='checkedall']:checkbox").prop("checked", this.checked);
        var chekname = $("[name='checkbox_name[]']:checkbox");
        var num      = $("input.checkbox:checked").length;
        var selchek  = $(".videofile_wraper tbody tr").find("[name='checkbox_name[]']:checkbox").prop("checked");
        if ( selchek ) {
            $(".videofile_wraper tbody tr").find('span a.Del_libs').html("&#160;");
        	chekname.parent().parent().addClass("clickclass");
            $(".tr_selected span").text( "已选中本页"+num+"个文件" );
            $(".tr_selected").show(); // 已选中文件显示提示操作
        } else {
            $(".videofile_wraper tbody tr").find('span a.Del_libs').html("删除");
        	chekname.parent().parent().removeClass("clickclass");
            $(".tr_selected").hide(); // 未选中文件隐藏提示操作
        } 
    });


    //选中复选框以后换背景色
    $(".videofile_wraper tbody").find("tr").click(function(){  //查找tr
        var has=$(this).hasClass("clickclass");//查找是否含用clickclass样式
        if(has){
            $(this).removeClass("clickclass");
        }
        var chek=$(this).find(":input").prop("checked");
        if(chek){
            $(this).addClass("clickclass");
        }
    });

    //重命名文件名

    // 找到 tbody中所有的td操作 
    var $tds = $('tbody td.txtleft');  
    // 给$tds注册点击事件  
    $tds.find("span.handle_show > a.Rename").live("click",function() {  
        // alert('11');
        // 获取被点击的td  
        var $td = $(this);  
        // 检测此td是否已经被替换了，如果被替换直接返回  
        if ($td.children("input").length > 0) {  
            return false;  
        }
        // 获取$td中的文本内容
        var isflag = '';
        var foo    = $td.parent().parent().find("span.vedio_filename");
        // 标记是否选中
        var chk    = $td.parent().parent().prev().find("input[name='checkbox_name[]']");
        if ( chk.length > 0 ) {
            if ( chk.is(":checked") ) {
                isflag = 'ischeck';
            } else {
                isflag = 'nocheck';
            } 
        } else {
            isflag = '';
        }
        var text   = foo.text();  // 获取$td中的未修改的值
        var onStatus = foo.attr("on");
        var delStr = foo.parent().find("span a.Del_libs").text();
        var lib_id = foo.attr("data-id"); // 获取$td中的data-id
        // 创建替换的input 对象  
        var $input = $("<input type='text' data-on="+isflag+">").css({"width":"60%","font-size":"12px","background-color":"#ffffff"});  
        $input.val(text); // 设置value值 
        // 清除td中的文本内容 
        foo.html("");  
        $td.parent().parent().find("span.handle_show").remove();
        foo.append($input);
        var pass = "<span class='pass' style='padding-left:20px;'><a href='javascript:void(0);' class='ok' style='padding-right:3px;' data-on="+isflag+">确定</a><a href='javascript:void(0);' class='off' style='padding-left:3px;' data-on="+isflag+">取消</a></span>";
        foo.after(pass);
        $input.focus();


        $(".ok").click(function(){
            // 获取当前文本框中的内容 text为文本框中修改前的值,value为当前输入的值 
            var num = $("input.checkbox:checked").length; // 统计已选择复选框个数 
            var value = $input.val();
            if ( value != '' && value != null ) {
                // 回调获取AJAX返回值
                renametitle( value, lib_id, function( datas ){
                    if ( datas == '100' ) {
                        // 将td中的内容修改成获取的value值
                        var strVal = '<a href="javascript:void(0);"><label for="'+lib_id+'">'+value+'</label></a>';
                        foo.html(strVal); 
                    } else if ( datas == '102' ) {
                        alert( "名称不可为空!" );
                        // 恢复原本输入框中的值
                        var oldVal = '<a href="javascript:void(0);"><label for="'+lib_id+'">'+text+'</label></a>';
                        foo.html(oldVal);
                    } else if ( datas == '101' ) {
                        $.dialog.alert( "修改失败!" );
                        var oldVal = '<a href="javascript:void(0);"><label for="'+lib_id+'">'+text+'</label></a>';
                        // 恢复原本输入框中的值
                        foo.html(oldVal);
                    }
                } );
                $(".pass").remove();
                var str = '';
                var ischeck = $(this).attr('data-on');  
                if ( ischeck ) {
                    if ( ischeck == 'ischeck' ) {
                        if ( num > 1 ) {
                            str = "&#160;";
                        } else {
                            str = "删除";
                        }
                    } else if ( ischeck == 'nocheck' ) {
                        str = "删除";    
                    }
                } else {
                    str = "&#160;";
                }
                
                $('span[data-id="'+lib_id+'"]').after($('<span class="handle_show" style="float:right;margin-top: 2px;"><a class="marl10 Rename" href="javascript:void(0);">重命名</a><a class="marl10 Del_libs" href="javascript:void(0);">'+str+'</a></span>'));
            } else {
                alert("名称不可为空!");
                var str = '';
                var num = $("input.checkbox:checked").length; // 统计已选择复选框个数
                var oldVal = '<a href="javascript:void(0);"><label for="'+lib_id+'">'+text+'</label></a>';
                foo.html(oldVal);
                $(".pass").remove();
                var ischeck = $(this).attr('data-on');  
                if ( ischeck ) {
                    if ( ischeck == 'ischeck' ) {
                        if ( num > 1 ) {
                            str = "&#160;";
                        } else {
                            str = "删除";
                        }
                    } else if ( ischeck == 'nocheck' ) {
                        str = "删除";    
                    }
                } else {
                    str = "&#160;";
                }   
                
                $('span[data-id="'+lib_id+'"]').after($('<span class="handle_show" style="float:right;margin-top: 2px;"><a class="marl10 Rename" href="javascript:void(0);">重命名</a><a class="marl10 Del_libs" href="javascript:void(0);">'+str+'</a></span>'));
            }
        });  

        $(".off").click(function(){
            var str = '';
            var num = $("input.checkbox:checked").length; // 统计已选择复选框个数
            var oldVal = '<a href="javascript:void(0);"><label for="'+lib_id+'">'+text+'</label></a>';
            foo.html(oldVal);
            $(".pass").remove();
            var ischeck = $(this).attr('data-on');  
            if ( ischeck ) {
                if ( ischeck == 'ischeck' ) {
                    if ( num > 1 ) {
                        str = "&#160;";
                    } else {
                        str = "删除";
                    }
                } else if ( ischeck == 'nocheck' ) {
                    str = "删除";    
                }
            } else {
                str = "&#160;";
            }   
            
            $('span[data-id="'+lib_id+'"]').after($('<span class="handle_show" style="float:right;margin-top: 2px;"><a class="marl10 Rename" href="javascript:void(0);">重命名</a><a class="marl10 Del_libs" href="javascript:void(0);">'+str+'</a></span>'));
        });

        // 处理enter事件和esc事件  
        $input.keyup(function(event) {  
            // 获取当前按下键盘的键值  
            var key = event.which;
            var num = $("input.checkbox:checked").length; // 统计已选择复选框个数 
            // 处理回车的情况  
            if (key == 13) {  
                // 获取当前文本框中的内容 text为文本框中修改前的值,value为当前输入的值  
                var value = $input.val();
                if ( value != '' && value != null ) {
                    // 回调获取AJAX返回值
                    renametitle( value, lib_id, function( datas ){
                        if ( datas == '100' ) {
                            // 将td中的内容修改成获取的value值
                            var strVal = '<a href="javascript:void(0);"><label for="'+lib_id+'">'+value+'</label></a>';
                            foo.html(strVal); 
                        } else if ( datas == '102' ) {
                            alert( "名称不可为空!" );
                            // 恢复原本输入框中的值
                            var oldVal = '<a href="javascript:void(0);"><label for="'+lib_id+'">'+text+'</label></a>';
                            foo.html(oldVal);
                        } else if ( datas == '101' ) {
                            alert( "修改失败!" );
                            var oldVal = '<a href="javascript:void(0);"><label for="'+lib_id+'">'+text+'</label></a>';
                            // 恢复原本输入框中的值
                            foo.html(oldVal);
                        }
                    } );
                    // 追加操作选项
                    $(".pass").remove();
                    var str = '';
                    var ischeck = $(this).attr('data-on');  
                    if ( ischeck ) {
                        if ( ischeck == 'ischeck' ) {
                            if ( num > 1 ) {
                                str = "&#160;";
                            } else {
                                str = "删除";
                            }
                        } else if ( ischeck == 'nocheck' ) {
                            str = "删除";    
                        }
                    } else {
                        str = "&#160;";
                    }
                    
                    $('span[data-id="'+lib_id+'"]').after($('<span class="handle_show" style="float:right;margin-top: 2px;"><a class="marl10 Rename" href="javascript:void(0);">重命名</a><a class="marl10 Del_libs" href="javascript:void(0);">'+str+'</a></span>'));
                } else {
                    var str = '';
                    var num = $("input.checkbox:checked").length; // 统计已选择复选框个数
                    var oldVal = '<a href="javascript:void(0);"><label for="'+lib_id+'">'+text+'</label></a>';
                    foo.html(oldVal);
                    $(".pass").remove();
                    var ischeck = $(this).attr('data-on');     
                    if ( ischeck ) {
                        if ( ischeck == 'ischeck' ) {
                            if ( num > 1 ) {
                                str = "&#160;";
                            } else {
                                str = "删除";
                            }
                        } else if ( ischeck == 'nocheck' ) {
                            str = "删除";    
                        }
                    } else {
                        str = "&#160;";
                    }
                    
                    $('span[data-id="'+lib_id+'"]').after($('<span class="handle_show" style="float:right;margin-top: 2px;"><a class="marl10 Rename" href="javascript:void(0);">重命名</a><a class="marl10 Del_libs" href="javascript:void(0);">'+str+'</a></span>'));
                    return false;
                }
            } else if (key == 27) {  
                var str = '';
                var num = $("input.checkbox:checked").length; // 统计已选择复选框个数
                var oldVal = '<a href="javascript:void(0);"><label for="'+lib_id+'">'+text+'</label></a>';
                foo.html(oldVal);
                $(".pass").remove();
                var ischeck = $(this).attr('data-on');   
                if ( ischeck ) {
                    if ( ischeck == 'ischeck' ) {
                        if ( num > 1 ) {
                            str = "&#160;";
                        } else {
                            str = "删除";
                        }
                    } else if ( ischeck == 'nocheck' ) {
                        str = "删除";    
                    }
                } else {
                    str = "&#160;";
                }  
                
                $('span[data-id="'+lib_id+'"]').after($('<span class="handle_show" style="float:right;margin-top: 2px;"><a class="marl10 Rename" href="javascript:void(0);">重命名</a><a class="marl10 Del_libs" href="javascript:void(0);">'+str+'</a></span>'));
            }
        });
    }); 

    // 请求接口数据，并回调返回值 tomey(2014-12-11)
    function renametitle ( name, id, callback ) {
        $.ajax({
            url: base_url + "my_lib/update_filename",
            data: {'filename':name,'lib_id':id},
            dataType:'json',
            type:'post',
            success: function( datas ){
                if ( datas ) {
                    callback( datas );
                }
            }
        });
    }

    //鼠标指针经过绑定的元素时触发显示翻页
    $(".list").hover(function(){  
        $(".layer_menu_list").fadeIn(100);  
    },function(){  
        $(".layer_menu_list").hide(); 
    });

    $(".videofile_wraper tbody tr").delegate('td.txtleft','mouseenter', function(){            
        $(this).find(".handle_show").fadeIn(200);
        return false;
    });
    $(".videofile_wraper tbody tr").delegate('td.txtleft','mouseleave', function(){
        $(this).find(".handle_show").hide();
         return false;
    });

    var Del_flag = '';
    // 单个删除操作 tomey(2014-12-10) 
    $(".Del_libs").live("click",function(e){
        Del_flag = $("input.checkbox:checked").attr("id");
        $("#"+Del_flag).removeAttr('checked');
        $("#"+Del_flag).parent().parent().removeClass("clickclass");
        var currentTarget = $(e.currentTarget);
        var $tr = currentTarget.closest('tr');
        $tr.find("[name='checkbox_name[]']").attr("checked",true);
        var cval = $tr.find("[name='checkbox_name[]']").val();
        Del_flag = cval;
        $("#delete_libs").show(); // 弹出确认提示层
    });

    $(".btn-cancel").click(function(){
        hidden_colsebtn();
    });

    function hidden_colsebtn () {
        $("#delete_libs").hide();//取消资源库文件按钮关闭弹出提示层
        $("#leave_page").hide();//选择留在本页按钮关闭弹出提示层
        $("#cancel_upload").hide();//取消上传按钮关闭弹出提示层
        // $("#"+Del_flag).removeAttr('checked');
        // $("#"+Del_flag).parent().parent().removeClass("clickclass");
        // $('[data-id="'+Del_flag+'"]').parent().find('span a.Del_libs').html("&#160;");
        // $(".tr_selected").hide();  // 已选中文件隐藏提示操作
    }

    // 全部删除 tomey (2014-12-10)
    $(".delall_libs").click(function(e){
        $("#delete_libs").show(); // 弹出确认提示层
    });

    // 点击确定执行AJAX tomey (2014-12-11)
    $("#libs_btnRemoveConfirm").click(function(e){
        var arr = [];
        $('input:checked').each(function(){
            if ( this.value != 'on' ){
                arr.push(this.value);
            }
        })
        if ( arr ) {
            $.ajax({
                url: base_url + "my_lib/del_lib",
                data: {'rid':arr},
                dataType:'json',
                type:'post',
                success: function( datas ){
                    if ( datas == 100 ) {
                        var url = base_url+"my_lib/index";
                        // window.location.reload();
                        window.location.href=url;
                    } else if ( datas == 101 ) {
                        alert("删除失败");
                        // $(".tr_selected").text( "删除失败" );
                    } else if ( datas == 102 ) {
                        alert("数据异常");
                        // $(".tr_selected").text( "数据异常" );
                    } else if ( datas == '103' ) {
                        alert("存在不可删除文件,请重新选择!");
                        // $(".tr_selected").text( "存在不可删除文件,请重新选择!" );
                    }
                }
            });
            // AJAX情况会用到
            // if ( result == 100 ) {
            //     // $("tr[id="+arr+"]").remove();
            //     $("#delete_libs").hide(); // 弹出确认提示层
            // }
        }
    });
   

    //离开页面操作
    $(".close_page_btn").click(function(){
    	//如果全部上传完成直接关闭
    		window.opener=null;
    		window.top.opener=null;
    		window.parent.close();

    	
       
    });
    // $(window).bind('beforeunload',function(){
    //     return '离开本页面，未完成上传文件将不被保存。';
    // });
    // $("input:button").click(function(){
    //     $(window).unbind('beforeunload');
    // });


    //取消正在上传的文件
    $(".tab_bd_complete_load i").click(function(){
        $("#cancel_upload").show();//弹出确认提示层
    });
});