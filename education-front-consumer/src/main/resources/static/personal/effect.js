var inter;
var timeArr = new Array();
var numberToChinese = function(number) {
    var units = '个十百千万@#%亿^&~';
    var chars = '0123456789';
    var a = (number + '').split(''), s = [];
    if (a.length > 12) {
        throw new Error('too big');
    } else {
        for (var i = 0, j = a.length - 1; i <= j; i++) {
            if (j == 1 || j == 5 || j == 9) {// 两位数 处理特殊的 1*
                if (i == 0) {
                    if (a[i] != '1')
                        s.push(chars.charAt(a[i]));
                } else {
                    s.push(chars.charAt(a[i]));
                }
            } else {
                s.push(chars.charAt(a[i]));
            }
            if (i != j) {
                s.push(units.charAt(j - i));
            }
        }
    }
    return s.join('').replace(/零([十百千万亿@#%^&~])/g, function(m, d, b) {// 优先处理 零百 零千 等
        b = units.indexOf(d);
        if (b != -1) {
            if (d == '亿')
                return d;
            if (d == '万')
                return d;
            if (a[j - b] == '0')
                return '零'
        }
        return '';
    }).replace(/零+/g, '零').replace(/零([万亿])/g, function(m, b) {
        return b;
    }).replace(/亿[万千百]/g, '亿').replace(/[零]$/, '').replace(/[@#%^&~]/g, function(m) {
        return {
            '@': '十',
            '#': '百',
            '%': '千',
            '^': '十',
            '&': '百',
            '~': '千'
        }[m];
    }).replace(/([亿万])([一-九])/g, function(m, d, b, c) {
        c = units.indexOf(d);
        if (c != -1) {
            if (a[j - c] == '0')
                return d + '零' + b
        }
        return m;
    });
}
var json_info;//课程安排页面各种效果所需参数
jQuery(function($) {
    //var KContent=''; //课程安排页面添加文本定义的长度
    var body = $('body');
    if (body.attr('id') === 'index' || body.attr('id') == 'course_sort') {
        //遮罩层
        $('.courseimg').css('overflow', 'hidden').each(function() {
            $(this).height($(this).width() / 2);
        });
        function resizeTitleBg() {
            $('.course_title_bg').each(function() {
                $(this).height($(this).next().height() + 10 + 'px');
            })
        }
        resizeTitleBg();
        var courses = $('.courseimg', $('#courselist'));
        var courseMaskLayer = $('<div></div>').addClass('courseMaskLayer');
        var courseLink = $('<a href="#"></a>').addClass('courseLink');
        var moreText = $('<span>课程详情</span>').addClass('moreText');
        moreText.appendTo(courseLink);
        var maskLink = courseMaskLayer.add(courseLink);
        maskLink.hide().appendTo('body')
        courses.bind('mouseenter', function() {
            maskLink.css({'left': $(this).offset().left, 'top': $(this).offset().top, 'width': $(this).outerWidth(), 'height': $(this).outerHeight()});
            courseLink.attr('href', $(this).find('h4').find('a').attr('href'));
            courseLink.attr('target', '_blank');
            moreText.css({'margin-top': $(this).height() / 2 - moreText.height() / 2 - 16});
            maskLink.show();
        });
        courseLink.bind('mouseleave', function() {
            maskLink.hide();
        });
        $(window).resize(function() {
            maskLink.hide();
            $('.courseimg').css('overflow', 'hidden').each(function() {
                $(this).height($(this).width() / 2);
            })
        });
        //新home页好课精选遮照层
        $('div.course-img-box').bind('mouseenter', function() {
            $(this).find('.course-img-mask').show();
        }).bind('mouseleave', function() {
            $(this).find('.course-img-mask').hide();
        });

        //课程分类页标签遮照层
        $(".course_col").delegate("li .course-img-box", "hover", function() {
            $(this).toggleClass("hove");
            if ($(this).hasClass("hove")) {
                $(this).find('.course-img-mask').show();
            } else {
                $(this).find('.course-img-mask').hide();
            }
        });

        $('#home1_sidebox_right div').bind('mouseenter', function() {
            $(this).siblings().removeClass("goto-hover");
        }).bind('mouseleave', function() {
            $(this).siblings().addClass("goto-hover");
        }).bind('click', function() {
            var goto_url = $(this).attr('data');
            if (goto_url) {
                window.location.href = goto_url;
            }
        });
        $(window).resize(function() {
            resizeTitleBg();
        })

        $(document).click(function(event) {
        })
        // 焦点图
        if ($('.flexslider').length) {
            $('.flexslider').flexslider({
                animation: "slide",
                controlNav: false,
                slideshowSpeed: 4000,
                animationSpeed: 600
            })
        }
        //窗口大小改变
        $(window).resize(function() {
            maskLink.hide();
            // $('hgroup',allCourse).hide();
        });

        //IE8
        /*隐去课程列表控制宽度
         if($.browser.msie && $.browser.version<9){
         $('.course_wraper','#courselist').each(function(index){
         if(body.attr('id')=='course_sort'){
         $(this).css('width','286px')
         }
         if(index!=0 && (index+1)%4==0){
         $(this).css('margin-right','0px');
         }
         }).eq(15).hide();
         }*/
    };
    //发现课程
    $('#show_course_type,#show_course_type_nav').click(function() {
        if (!$('#show_course_type_layer').is(':visible')) {
            $('#show_course_type_layer').fadeIn(400);
            if ($('#show_course_type_layer').height() > $(window).height()) {
                $('#show_course_type_layer').css({'margin-top': '0', 'top': '10px', 'position': 'absolute'});
            } else {
                $('#show_course_type_layer').css({'top': '50%', 'margin-top': -$('#show_course_type_layer').outerHeight() / 2, 'margin-left' : -$('#show_course_type_layer').outerWidth() / 2, 'position': 'fixed'});
            }
        }
    });
    $('#show_course_type_close').click(function() {
        $('#show_course_type_layer').fadeOut(200);
    });
    //搜索
    var headsearch = $('.headsearch:first');
    var search_text = $('.search_text', headsearch);
    search_text.bind('focus', function() {
        if ($(this).val() == '查找课程') {
            $(this).val('');
        }
    }).bind('blur', function() {
        if ($(this).val() == '') {
            $(this).val('查找课程')
        }
    });
    var login = $('.login:first');
    var loginbox_show = 0;
    login.bind('mouseenter', function() {
        $('.userinfo-box-layer').fadeIn(100);
    }).bind('mouseleave', function() {
        $('.userinfo-box-layer').hide();
    });
    $('.userinfo-box-layer').bind('mouseenter', function() {
        $('.userinfo-box-layer').show();
    }).bind('mouseleave', function() {
        $('.userinfo-box-layer').hide();
    });

    var mailbox = $('#mailbox');
    mailbox.bind('mouseenter', function() {
        $('#mailcontent').fadeIn(100);
    }).bind('mouseleave', function() {
        $('#mailcontent').hide();
    });
    var msgbox = $('#msgbox .msg-tips');
    msgbox.bind('mouseenter', function() {
        $(this).addClass("userinfo-mailbox-preview");
    }).bind('mouseleave', function() {
        $(this).removeClass("userinfo-mailbox-preview");
    });

    //购物车
    $(".userinfo-cartbox").bind("click", function() {
        window.open(base_url + "order/cart");
    });


    //提示信息
    var help_message = $('.message_ico');
    help_message.hover(
            function(){
	        $(this).next().fadeIn(200)
	    },
            function(){
	        $(this).next().fadeOut(200)
	    }
    )
    $('#register_user input[type=text],#register_org input[type=text]').bind('focus', function() {
        if ($(this).parent().prev().children('.help_message').length) {
            $(this).parent().prev().children('.help_message').fadeIn(200);
        }
    }).bind('blur', function() {
        if ($(this).parent().prev().children('.help_message').length) {
            $(this).parent().prev().children('.help_message').fadeOut(200);
        }
    });

    //各种注册与登录之间的切换
    var popWindows = $('.popWindow');
    var forgetpsw = $('#forget_link'), register_user = $('.register_link'), register_org = $('#register_org_link'), header_sign = $('#header_sign'), header_register = $('#header_register'), other_sign = $('.other_sign a', popWindows);
    var opMaskLayer = $('.opMaskLayer');
    var popcolseBtn = $('.pop_colsebtn', popWindows);
    var return_sign = $('.return_sign');
    var showpopWindow = function(which) {
        if (popWindows.filter(':visible').length) {
            if (!opMaskLayer.is(':visible')) {
                opMaskLayer.show()
            }
            popWindows.filter(':visible').animate(
                {'margin-left': '-600px', 'opacity': '0'}, 300, 'swing', function() {
                    $(this).hide()
                });
            which.css({'display': 'block', 'opacity': '0', 'top': $(window).height() / 2 + 'px', 'margin-top': -which.outerHeight() / 2 + 'px', 'margin-left': '0'});
            which.animate({'margin-left': '-200px', 'opacity': 1}, 400, 'swing');
        } else {
            opMaskLayer.show();
            which.css({'top': $(window).height() / 2 + 'px', 'margin-top': -which.outerHeight() / 2 + 'px', opacity: 1, 'margin-left': '-200px'}).fadeIn(200);
        }
        //$('#email').val($('#email').attr('defaultvalue'));
    }

    var hidepopWindow = function() {
        popWindows.hide();
        popWindows.find('input').val('');
        popWindows.find('textarea').val('');
        popWindows.find("span[id^='confirm_']").hide();
        opMaskLayer.hide();
    }

    /*收藏课程开始*/
    var collect_course = $('.collect_course');
    collect_course.bind('click', function() {
        var login_user_id = $('#login_user_id').val();
        var course_id = $('#course_id').val();
        if (login_user_id == 0) {
            showpopWindow($('#sign'));
            return false;
        }
        else {
            $.ajax({
                type: "POST",
                url: base_url + "course/collect_course",
                data: {id: course_id, userid: login_user_id},
                dataType: 'json',
                error: function(errormsg) {
                    $.dialog({
                        title: '提示',
                        content: errormsg,
                        min: false,
                        max: false,
                        button: [{name: '关闭'}]
                    });
                },
                success: function(msg) {
                    $.dialog({
                        title: '提示',
                        content: msg,
                        min: false,
                        max: false,
                        button: [{name: '关闭'}]
                    });
                }
            });
        }
    });
    /*收藏课程结束*/
    /*点击开始学习开始*/
    var start_study_course = $('#start_study_course');
    start_study_course.bind('click', function() {
        var login_user_id = $('#login_user_id').val();
        var course_id = $('#course_id').val();
        var section_id = $("#defaultId").val();
        var sale_status = $("#saleStatus").val();
        if (sale_status == 1) {
            return false;
        }
        if (login_user_id == 0) {
            //登录后的进入地址
            $('#from').val(base_url + "course/course_detail2/?course_id=" + course_id);
            showpopWindow($('#sign'));
            return false;
        }
        else {
            //window.location.href=base_url+"course/flvdetail?courseid="+course_id+"&id="+section_id;
            window.location.href = base_url + "course/course_detail2/?course_id=" + course_id;
            return false;
        }
    });
    /*点击开始学习结束*/
    /*试听开始*/


    /*试听结束*/
    /*点击进入学习视频开始*/
    var start_study_course = $('.study_flv');
    start_study_course.bind('click', function() {
        var login_user_id = $('#login_user_id').val();
        var course_id = $(this).children('.flv_course_id').val();
        var section_id = $(this).children('.flv_section_id').val();
        var free_id = $(this).children('.flv_free_id').val();
        if (free_id == 0) {
            //登录后的进入地址
            if (login_user_id == 0) {
                $('#from').val(base_url + "course/flv_detail/?course_id=" + course_id + "&section_id=" + section_id);
                showpopWindow($('#sign'));
                return false;
            }
            else {
                window.location.href = base_url + "course/flv_detail/?course_id=" + course_id + "&section_id=" + section_id;
                return false;
            }
        } else if (free_id == 1) {
            //tryDetail(section_id);
            return false;
        }
    });
    /*点击进入学习视频结束*/

    //点击评论的登录
    var comment_login = $('.comment_login');
    comment_login.live('click', function() {
        showpopWindow($('#sign'));
        return false;
    });

    //首页导航，当用户未登陆时，点击上传课程提示登陆
    var up_course_login = $('.up_course_login_flag');
    up_course_login.live('click', function() {
        $("input[id='from']").val(base_url + 'my_course/course_basic?act=add');
        showpopWindow($('#sign'));
        return false;
    });

    /*点击评论课程*/
    var isbind = $('.comment_type', '.message_content').val();
    if (isbind == 1) {
        var radius5 = $('.radius5', '.message_content');//class="radius5"有好几处有
        radius5.live('click', function() {
            var login_user_id = $('#login_user_id').val();
            var course_id = $('#course_id').val();
            var pub_message = $(this).closest('.pub_message');
            var msgbox = $(this).closest('.msgbox');
            var mainid = $('.mainid', msgbox).val();
            var iscreateuser = $('.createuser', msgbox).val();
            if (pub_message.length > 0) {//最顶上的评论文本框
                var content = $('.comment_content', pub_message).val();//得到提交的内容
                var parentid = $('.parentid', pub_message).val();
            }
            else {//回复的文本框
                var reply = $(this).closest('.reply');
                var content = $('.comment_content', reply).val();//得到提交的内容
                var parentid = $('.parentid', reply).val();
            }
            if (login_user_id == 0) {
                //js评论内容创建cookie值
                if (content != '' && content != '文明上网，理性发言') {
                    document.cookie = "courseDetailComment=" + content + ";path=/";
                }
			$('#from').val(window.location.href);
                showpopWindow($('#sign'));
                return false;
            }
            else {
                content = content.replace(/\s/g, "");//这里是去掉空字符
                if (content == '' || content == '文明上网，理性发言') {
                    $.dialog.alert('很抱歉，请填写评论内容');
                    return false;
                }
                else {
                    $.ajax({
                        type: "POST",
                        url: base_url + "course/comment_course",
                        data: {id: course_id, userid: login_user_id, content: content, parentid: parentid, mainid: mainid, iscreateuser: iscreateuser},
                        dataType: 'json',
                        async: false,
                        success: function(data) {
                            if (data.msg == 'error') {
                                $.dialog.alert(data.data);
                                return false;
                            }
                            else {
                                //将html替换
                                var commentHtml = data.data;
                                $('.course_messageComm').html(commentHtml);
                                return false;
                            }
                        }
                    });
                }
            }
        });
    }
    /*评论课程结束*/
    return_sign.add(header_sign).bind('click', function() {
        popWindows.find("span[id^='confirm_']").hide();
        showpopWindow($('#sign'));
        return false;
    });
    /*忘记密码*/
    forgetpsw.bind('click', function() {
        popWindows.find('input').val('');
        popWindows.find("span[id^='confirm_']").hide();
        $("#RESET_FORM #img").attr('src', function() {
            return $(this).attr('srcdata');
        });
        showpopWindow($('#forget_password'));
        return false;
    });
    /*个人注册*/
    register_user.add(header_register).bind('click', function() {
        $('[name=vcode]', '#user').attr('src', function() {
            return $(this).attr('srcdata');
        });
        //document.user.vcode.click();
        showpopWindow($('#register_user'));
        return false;
    });
    /*机构注册*/
    register_org.bind('click', function() {
        $('[name=vcode]', '#org').attr('src', function() {
            return $(this).attr('srcdata');
        });
        //document.org.vcode.click();
        showpopWindow($('#register_org'));
        return false;
    });
    other_sign.bind('click', function() {
        hidepopWindow();
    });
    popcolseBtn.hover(function() {
        $(this).css('background-position', '-10px 0')
    }, function() {
        $(this).css('background-position', '0 0')
    });
    $('.pop_colsebtn', popWindows).live('click', function() {
        $('.popWindow').hide();
        // $('.popWindow').find('input').val('');
        // $('.popWindow').find('textarea').val('');
        //课程安排页面,点击关闭视频、音频、pdf文档的上传窗口时,需要清除div样式

        if ($('.popWindow').hasClass('up_video')) {
            $("#form_upload", '.up_video').children().each(function() {
                if (!$(this).is('p')) {
                    $(this).remove();
                }
            })
        }
        if ($('.popWindow').hasClass('up_sound')) {
            $("form", '.up_sound').children().each(function() {
                if (!$(this).is('p')) {
                    $(this).remove();
                }
            })
        }
        if ($('.popWindow').hasClass('up_document')) {
            $("form", '.up_document').children().each(function() {
                if (!$(this).is('p')) {
                    $(this).remove();
                }
            })
        }
        if ($(".popWindow").hasClass('up_data')) {
            $(".upload_data", $("#up_data")).children().add("#fileQueue_data", $("#up_data")).remove();
        }
        if($(".popWindow:visible").hasClass('addgocart')){
            $(".userinfo-cartbox").show();
        }
        opMaskLayer.hide();
    })
    //opMaskLayer.css({'height':$(document).height()});
    opMaskLayer.css({'height': $(document).height(), 'position': 'fixed'});
    popWindows.css({'top': $(window).height() / 2 + 'px', 'margin-top': -popWindows.filter(':visible').outerHeight() / 2 + 'px'});
    $(window).resize(function() {
        var current = popWindows.filter(':visible');
        if (current.length) {
            opMaskLayer.css({'width': $(document).width(), 'height': $(document).height()});
            current.css({'top': $(window).height() / 2 + 'px', 'margin-top': -current.outerHeight() / 2 + 'px'});
        }
    });
    //新增教师
    var add_teacher_html = $('#add_teacher').html();
    $('#add_teacher_btn').live('click', function() {
        $('#add_teacher').html(add_teacher_html);
        showpopWindow($('#add_teacher'));
        return false;
    });
    //编辑教师
    $('a.edit', '.teacher_info').live('click', function() {

        var imageName = $(this).attr('bid');
        var aid = $(this).attr('aid');
        var bid = $(this).attr('bid');
        var cid = $(this).attr('cid');
        $('#add_teacher').find('h3').text('编辑教师');

        $('#f').attr('src', bid);

        $.ajax({
            url: base_url + 'teacher/edit',
            type: 'POST',
            dataType: 'json',
            data: 'id=' + aid,
            success: function(msg) {
                //替换内容
                $(msg).each(function(i, item) {

                    $('#a').val(item.real_name);
                    $('#b').val(item.email);
                    $('#c').val(item.tel);
                    $('#d').val(item.intro);
                    $("#add_teacher_submit").attr('tid', item.teacher_id);
                    $('#e').attr('action', base_url + 'teacher/update/' + aid + '/' + cid);

                });
            }
        });
        showpopWindow($('#add_teacher'));
        return false;
    })
    //表格右对齐
    if ($('table.layout').length) {
        $("table.layout tr").each(function() {
	    $('td:first', this).css('text-align', 'right');
	})
    };
    //用户更换头像
    var user_face = $('#user_face');
    if (user_face.length) {
        var changebtn = $('#changebtn');
        user_face.hover(function() {
            changebtn.fadeIn(100)
        }, function() {
            changebtn.hide();
        })
    }
    //课程页效果
    if (body.attr('id') == 'course') {
        if ($(window).width() > 600) {
            $(".course_time,.teacher_infos,.students_notes,.course_rel,.course_ping").appendTo("#course_right");
            $(".description,.course_contents,.course_message").appendTo("#course_left");
        }
        var sectionbox = $('div.section:first');
        var section = sectionbox.find('li');
        section.hover(function() {
            $(this).addClass('current');
            $(this).parent().prev().addClass('current');
            if ($(this).children('.chapter_over').length && $(this).attr('isFree') != 1) {
                $(this).children('a').append($("<span class='enter'>复习一遍</span>"));
            } else if ($(this).attr('isFree') != 1) {
                $(this).children("a").append($("<span class='enter'>进入学习</span>"));
            }
        }, function() {
            $(this).removeClass('current');
	    if ($(this).attr('isFree') != 1) {
                $(this).find('.enter').remove();
            }
            $(this).parent().prev().removeClass('current');


        });

        var weight_section = 0;
        section = sectionbox.find("li:contains('回放')");

        section.each(function(index) {
            $(this).children('.chapter').html(++weight_section);
        });
        section = sectionbox.find("li:not(:contains('回放'))");

        section.each(function(index) {
            $(this).children('.chapter').html(++weight_section);
        });

        var sectionbox = $('div.section:eq(1)');
        var section = sectionbox.find('li');
        section.hover(function() {
            $(this).addClass('current');
            $(this).parent().prev().addClass('current');
            if ($(this).children('.chapter_over').length && $(this).attr('isFree') != 1) {
                $(this).children('a').append($("<span class='enter'>复习一遍</span>"));
            } else if ($(this).attr('isFree') != 1) {
                $(this).children("a").append($("<span class='enter'>进入学习</span>"));
            }
        }, function() {
            $(this).removeClass('current');
	    if ($(this).attr('isFree') != 1) {
	        $(this).find('.enter').remove();
	    }
                $(this).parent().prev().removeClass('current');
	    });
	    
	section.each(function(index) {
	    $(this).children('.chapter').html(index + 1);
	});
    };
    //预览页
    if (body.attr('id') == 'course_preview') {
        if ($(window).width() > 600) {
            $(".course_time,.teacher_infos,.students_notes,.course_rel,.course_ping").appendTo("#course_right");
            $(".description,.course_contents,.course_message").appendTo("#course_left");
        }
        var sectionbox = $('div.section:first');
        var section = sectionbox.find('li');
        section.hover(function() {
            $(this).addClass('current');
            $(this).parent().prev().addClass('current');
            if ($(this).attr('isFree') != 1 && $(this).find("a").attr("href") != "#") {
                $(this).children("a").append($("<span class='enter'>预览</span>"));
            }
        }, function() {
            $(this).removeClass('current');
            if ($(this).attr('isFree') != 1) {
                $(this).find('.enter').remove();
            }
            $(this).parent().prev().removeClass('current');
        });


        section.each(function(index) {
            $(this).children('.chapter').html(index + 1);
        });
    };
    //师资机构的两列显示
    if ($('.user_detail').length) {
        var teachers = $('.teacher', '.user_detail');
        var teachers_wraper = $('.teachers_wraper', '.user_detail');
        var teachersleft = $('<div class="tleft"></div>');
        var teachersright = $('<div class="tright"></div>');
        teachers.filter(':even').appendTo(teachersleft);
        teachers.filter(':odd').appendTo(teachersright);
        teachers_wraper.append(teachersleft).append(teachersright);
        var des = $('p', teachers);
        des.each(function() {
            $(this).data('long', $(this).text());
            $(this).data('short', $(this).text().substring(0, 60) + '...');
            $(this).html($(this).data("short"));
        })
        teachers.each(function(index) {
            $(this).children('div').append($('<a class="moredes" style="display:none" href="#">展开</a>'))
            $(this).hover(
                    function() {
                        $(this).children('div').addClass('bg');
                        $(this).find('.moredes').show();
                    },
                    function() {
                        $(this).children('div').removeClass('bg');
                        //if($(this).find('.moredes').text()=="收起"){
                        //	des.eq(index).text(des.eq(index).data('short'));
                        //	$(this).find('.moredes').text("展开");
                        //}
                        $(this).find('.moredes').hide();
                    }
            );
            $(this).find('.moredes').bind('click', function() {
                if ($(this).text() == "展开") {
                    des.eq(index).text(des.eq(index).data('long'));
                    $(this).text("收起");
                } else {
                    des.eq(index).text(des.eq(index).data('short'));
                    $(this).text("展开");
                };
                return false;
            })
        });
    }

    /*-- 课程安排页面，各种效果开始，请勿随意修改 --*/
    jQuery(document).ready(function($) {
        //课程安排
        if ($('#course_set').length) {
            /*增加 2013/10/10*/
            function course_drag() {
                var count = $(".section #chapter_id[value!='']").size();
                if (count == 0)
                    return;
                $("div.chapters").dragsort({//节拖拽
                    dragBetween: true,  // 设置多组列表之间拖动选定的列表
                    dragSelectorExclude: "a.ok,a.cancel,textarea,.chapter_title input,.chapter_title .edit_chapter_title,.chapter_title .delete_chapter,.select_box a,.fl,.fr,a.js_cancel_up_video,a.js_cancel_uping_video,a.js_cancel_up_sound,a.js_cancel_up_pdf,.ec_content .posr,.ec_content .ec_delete,div.video_v", 
                    dragSelector: '.chapter_content', // CSS选择器内的元素的列表项的拖动手柄。默认值是“li”
                    dragEnd: function() {
                        updata_chapter_num();
                        update_section_num();
                        $('.addchapter').each(function() {
                            var parent = $(this).closest('.chapters');
                            $(this).appendTo(parent);
                        })
                        var current_section = $(this).closest(".section");
                        var current_cid = $("#section_id", this).val();
                        var current_sid = $("#chapter_id", current_section).val();
                        var prev_chapter = $(this).prev();
                        var cid = $("#cid").val();
                        if ($(this).index() == 0) {
                            move_cid = 0;//某一章的第一节,第一节的位置索引是0，依次类推+1
                        } else {
                            move_cid = $("#section_id", prev_chapter).val();
                        }
                        $.post(
                            base_url + "assign_course/set_order", 
                            {current_sid: current_sid, move_cid: move_cid, current_cid: current_cid, cid: cid}, 
                            function(data) {}
                        );
                    }, 
                    placeHolderTemplate: "<div class='dragplaceHolder'>可放置区域</div>"
                });
                $('div#course_set').dragsort({//章拖拽
                    dragBetween: true, 
                    dragSelector: 'div.section',
                    dragEnd: function() {
                        $('#addsection').appendTo('#course_set');
                        update_section_num();
                        updata_chapter_num();
                        var current_sid = $("#chapter_id", this).val();
                        var move_sid = 0;
                        var prev_section = $(this).prev();
                        var cid = $("#cid").val()
                        if ($(this).index() == 0) {
                            move_sid = 0;
                        } else {
                            move_sid = $("#chapter_id", prev_section).val();
                        }
                        $.post(
                            base_url + "assign_course/set_order_chapter", 
                            {current_sid: current_sid, move_sid: move_sid, current_cid: 0, cid: cid}, 
                            function(data) {}
                        );
                    }, 
                    dragSelectorExclude: "a.ok,a.cancel,.titles_edit input,a.edit_section_title,a.delete_section,div.video_v", 
                    dragStart: true, 
                    placeHolderTemplate: "<div class='dragplaceHolder'>可放置区域</div>"
                })
            }
            course_drag();
            $('.section h2').live('mousedown', function() {
                $(this).next().hide();
            }).live('mouseup', function() {
                $(this).next().show();
            });
            var addsection_btn = $('#addsection');

            //添加章
            var addsection = function() {
                var sections = $('.section', '#course_set').size();
                //当前section下添加章id的标志位
                var section = $('<div class="section"><span id="chapter_span" class="section_span_class"><input type="hidden" name="chapter_id" id="chapter_id" value=""></span><h2><span class="pdr10 section_num">第' + numberToChinese(sections + 1) + '章</span><span class="titles_edit"><input type="text" value="请输入标题" maxlength="50" /></span><span class="confirm"><a href="#" class="ok gradient_btn_blue radius2">确定</a>或<a href="#" class="cancel">取消</a></span></h2></div>')
                section.insertBefore(addsection_btn);
                section.find('input').focus().select();
                $('div.chapters').dragsort('destroy');
                course_drag();
            }

            //章的标题鼠标划过
            $('h2', '#course_set').live('mouseenter', function() {
                $(this).addClass('current');
                $('.edit_section_title', this).css('visibility', 'visible');
                if (!$(this).children().hasClass('confirm')) {
                    $('.delete_section', this).show();
                }
            }).live('mouseleave', function() {
                $(this).removeClass('current');
                $('.edit_section_title', this).css('visibility', 'hidden');
                $('.delete_section', this).hide();
            });

            var confirm = $('<span class="confirm"><a class="ok gradient_btn_blue radius2" href="#">确定</a>或<a href="#" class="cancel">取消</a></span>'); //确认或取消按钮

            //章的标题(小笔头图标)编辑
            $('h2 a.edit_section_title', '#course_set').live('click', function() {
                var input_title = $('<input type="text" maxlength="50" value="请输入标题" >');
                input_title.attr('value', $(this).prev().html());
                $(this).parent().append(input_title);
                $(this).parent().parent().append(confirm);
                //此处调整为隐藏，不是删除
                //$(this).prev().remove();
                //$(this).remove();
                $(this).prev().css('display', 'none');//是b标签
                $(this).css('display', 'none');
                $(this).parent().find(".delete_section").css('display', 'none');
                input_title.focus().select();
                return false;
            })

            //添加章
            addsection_btn.bind('click', function() {
                addsection();
                return false;
            });
            //更新章序号
            var update_section_num = function() {
                $('.section_num').each(function(index) {
                    $(this).html('第' + (index + 1) + '章');
                })
            };
            //更新节序号
            var updata_chapter_num = function(wchapter) {
                $('.chapter_num').each(function(index) {
                    $(this).html('第' + (index + 1) + '节')
                })
            }
            //添加节
            var addchapter = function(section) {
                var chapters = $('<div class="chapters"></div>');
                //此处添加每节的标志位chapter_span-->chapter_id
                var chapter_content = $('<div class="chapter_content curr"><a name="s_" id="sec_id"></a><span id="section_span" class="chapter_span_class"><input type="hidden" name="section_id" id="section_id" value=""></span><h4><span class="pdr10 chapter_num">第节</span><span class="chapter_title"><input type="text" maxlength="50" value="输入标题" /></span><span class="confirm"><a href="#" class="ok gradient_btn_blue radius2">确定</a>或<a href="#" class="cancel">取消</a></span></h4></div>');
                var addchapter = $('<div class="addchapter"><span>添加新的一节</span></div>');
                var addchapter_btn = section.find('.addchapter');
                if (!addchapter_btn.size()) {
                    chapters.appendTo(section);
                    chapter_content.appendTo(chapters);
                    addchapter.appendTo(chapters);
                } else {
                    chapter_content.insertBefore(addchapter_btn)
                }
                updata_chapter_num(section);
                $('div.chapters').dragsort('destroy');
                course_drag();
            };
            //初始章节
            if (!$('.section', '#course_set').length) {
                addsection();
            } else {
                update_section_num();
                updata_chapter_num();
            }

            //取消章,改为状态切换,而非章的删除操作
            $('.cancel', '#course_set h2').live('click', function() {
                var section = $(this).closest('.section');
                var chapter_id = $('#chapter_span #chapter_id', section).val();//得到当前章的id,从服务器端返回
                var titles_edit_flag = $("span[class='titles_edit']", section);
                //若是已经存在的章信息
                if (typeof chapter_id != 'undefined' && chapter_id != null && chapter_id != '') {
                    $("input", titles_edit_flag).remove();
                    $("b", titles_edit_flag).css("display", "");
                    $("a", titles_edit_flag).css("display", "");
                    $("h2 span[class='confirm']", section).remove();
                } else {
                    $(section).remove();
                    update_section_num();
                }
                return false;//防止URL自动追加#号,导致页面自动定位到顶部
            })
            //整章删除
            $('a.delete_section', '#course_set h2 .titles_edit').live('click', function() {
                var chapter = $(this).closest('.section');
                var chapter_id = $('#chapter_span #chapter_id', chapter).val();//得到当前章的id
                var remove_flag = $(this);
                $.dialog.confirm('确认要删除该章的信息吗？', function() {
                    //若章、节在系统中已经存在需要从系统中删除
                    if (typeof chapter_id != 'undefined') {
                        $.post(base_url + "assign_course/del_process", {CID: CID, chapter_id: chapter_id, mode: 'whole_chapter'}, function(msg) {
                        }, 'json');
                    }
                    remove_flag.closest('.section').remove();
                    update_section_num();
                    updata_chapter_num();
                }, function() {

                });
            })

            //确认添加章的标题，回车确认
            $('input', '#course_set h2 .titles_edit').live('keydown', function(event) {
                var event = event || window.event;
                switch (event.keyCode) {
                    case 13:
                        var section = $(this).closest('.section');
                        if (section.index() != '0' && $('.chapter_content').length == 0) {
                            $.dialog.alert('请按照顺序先添加第一章的标题');
                            return false;
                        }
                        var chapter_id = $('#chapter_span #chapter_id', section).val();//得到当前章的id,从服务器端返回
                        var currentSectionRank = section.index(); //返回当前是第几章,从0开始
                        var titles_edit = $(this).parent();
                        var input_title_temp = $.trim($(this).val());
                        if (input_title_temp == "请输入标题" || input_title_temp == '') {
                            $.dialog.alert('请先添加对应的章的标题');
                            return false;
                        } else {
                            titles_edit.empty().append(input_title_temp == "请输入标题" ? '<b style="color:#ccc">标题未输入</b><a href="#" class="edit_section_title"></a><a href="#" class="delete_section"></a>' : '<b>' + input_title_temp + '</b><a href="#" class="edit_section_title"></a><a href="#" class="delete_section"></a>');
                            //因涉及到章标题的取消,是把原来b标签隐藏,所以要取input中的值
                            //var input_title_temp=section.find('.titles_edit b').html();
                            //$(this).remove();
                            $("h2 span[class='confirm']", section).remove();
                            if (!$('.chapters', section).length) {
                                addchapter(section);
                            }
                            $.post(base_url + "assign_course/process", {CID: CID, chapter_id: chapter_id, title: input_title_temp, isParent: 'yes'}, function(msg) {
                                if (msg && typeof msg.info != 'undefined') {
                                    switch (msg.info) {
                                        case 'success':
                                            if (typeof msg.id != 'undefined') {
                                                $('#chapter_span #chapter_id', section).val(msg.id);
                                            }
                                            break;
                                        case 'nologin':
                                            $.dialog.alert('系统检测到您当前处于离线状态,请重新登录');
                                            break;
                                        case 'noparam':
                                            $.dialog.alert('系统中暂无当前课程的信息!请确认');
                                            break;
                                        case 'nomode':
                                            $.dialog.alert('网络繁忙,请关闭浏览器后重试');
                                            break;
                                    }
                                }
                            }, 'json');
                            return false;
                        }
                        break;
                }
            })
            //确认添加章的标题
            $('.ok', '#course_set h2').live('click', function() {
                var section = $(this).closest('.section');
                if (section.index() != '0' && $('.chapter_content').length == 0) {
                    $.dialog.alert('请按照顺序先添加第一章的标题');
                    return false;
                }
                var chapter_id = $('#chapter_span #chapter_id', section).val();//得到当前章的id,从服务器端返回
                var currentSectionRank = section.index(); //返回当前是第几章,从0开始
                var titles_edit = $(this).parent().prev();
                var input_title = titles_edit.find('input:first');
                var input_title_temp = $.trim(input_title.val());
                if (input_title_temp == "请输入标题" || input_title_temp == '') {
                    $.dialog.alert('请先添加对应的章的标题');
                    return false;
                } else {
                    titles_edit.empty().append(input_title_temp == "请输入标题" ? '<b style="color:#ccc">标题未输入</b><a href="#" class="edit_section_title"></a><a href="#" class="delete_section"></a>' : '<b>' + input_title_temp + '</b><a href="#" class="edit_section_title"></a><a href="#" class="delete_section"></a>');
                    //因涉及到章标题的取消,是把原来b标签隐藏,所以要取input中的值
                    //var input_title_temp=section.find('.titles_edit b').html();
                    //input_title.remove();
                    $(this).parent().remove();
                    if (!$('.chapters', section).length) {
                        addchapter(section);
                    }
                    $.post(base_url + "assign_course/process", {CID: CID, chapter_id: chapter_id, title: input_title_temp, isParent: 'yes'}, function(msg) {
                        if (msg && typeof msg.info != 'undefined') {
                            switch (msg.info) {
                                case 'success':
                                    if (typeof msg.id != 'undefined') {
                                        $('#chapter_span #chapter_id', section).val(msg.id);
                                    }
                                    break;
                                case 'nologin':
                                    $.dialog.alert('系统检测到您当前处于离线状态,请重新登录');
                                    break;
                                case 'noparam':
                                    $.dialog.alert('系统中暂无当前课程的信息!请确认');
                                    break;
                                case 'nomode':
                                    $.dialog.alert('网络繁忙,请关闭浏览器后重试');
                                    break;
                            }
                        }
                    }, 'json');
                    return false;
                }
            })

            $('.addchapter').live('click', function() {
                var section = $(this).closest('.section');
                addchapter(section);
            });

            //确认添加节的标题，回车确认
            $('input', '#course_set h4 .chapter_title').live('keydown', function(event) {
                var event = event || window.event;
                switch (event.keyCode) {
                    case 13:
                        var chapter = $(this).closest('.section');
                        var chapter_id = $('#chapter_span #chapter_id', chapter).val();//得到当前章的id,从服务器端返回
                        if (typeof chapter_id == 'undefined' || Number(chapter_id) < '1') {
                            $.dialog.alert('请先添加对应的章');
                            return false;
                        }
                        var section = $(this).closest('.chapter_content');
                        var section_id = $('#section_span #section_id', section).val();//得到当前节的id,从服务器端返回	
                        var h4 = $(this).closest('h4');
                        var add_cc_content_flag = $('.edit_chapter', section).length;
                        var addcontent_btn = $('<span class="add_cc"><a href="javascript:void(0);"><span class="bottomdirection">' + (add_cc_content_flag > 0 ? '编辑' : '添加') + '内容</span></a></span>');
                        var titles_edit = $(this).parent();
                        var select_type = $('<div class="select_type" style="display:block"><div class="select_box"><a href="javascript:void(0)" class="video"></a><a href="javascript:void(0)" class="sound"></a><a href="javascript:void(0)" class="pdf"></a></div></div>');
                        var input_title_temp = $.trim(section.find('.chapter_title input').val());
                        if (input_title_temp == "输入标题" || input_title_temp == '') {
                            $.dialog.alert('请先添加对应的节的标题');
                            return false;
                        } else {
                            titles_edit.empty().append(input_title_temp == "输入标题" || input_title_temp == '' ? '<b style="color:#ccc">标题未输入</b><a href="#" class="edit_chapter_title"></a><a class="delete_chapter" href="#"></a>': '<b>' + input_title_temp + '</b><a href="#" class="edit_chapter_title"></a><a class="delete_chapter" href="#"></a>');
                            $("span[class='add_cc']", h4).show();
                            $("span[class='add_other_info']",h4).show();
                            $("h4 span[class='confirm']", section).remove();
                            $.post(base_url + "assign_course/process", {isFirstChapter: ($('.chapter_content').length == 1 ? 'yes' : 'no'), CID: CID, chapter_id: chapter_id, section_id: section_id, title: input_title_temp, isParent: 'no'}, function(msg) {
                                if (msg) {
                                    switch (msg.info) {
                                        case 'success':
                                            $("#del_id").val( msg.section_id );
                                            if (typeof msg.section_id != 'undefined') {
                                                $('#section_span #section_id', section).val(msg.section_id);
                                                if (add_cc_content_flag < 1 && $("div[class='select_type']", section).length < 1) {
                                                    $('.chapter_content div.select_type').hide();
						                            $('.chapter_content div.edit_chapter').hide();
                                                    $("#sec_id").attr("name","s_"+msg.section_id);
                                                    $('.chapter_content.curr').find("a[name^='s_']").parent().removeClass("curr");
                                                    h4.after(select_type);
                                                    h4.append(addcontent_btn);
                                                }
                                            }
                                            break;
                                        case 'nologin':
                                            $.dialog.alert('系统检测到您当前处于离线状态,请重新登录');
                                            break;
                                        case 'noparam':
                                            $.dialog.alert('系统中暂无当前课程的信息!请确认');
                                            break;
                                        case 'nomode':
                                            $.dialog.alert('网络繁忙,请关闭浏览器后重试');
                                            break;
                                        case 'nosection':
                                            $.dialog.alert('请先添加对应的章', function() {
                                                window.location.reload();
                                            });
                                            break;
                                    }
                                }

                            }, 'json');
                            return false;
                        }
                        break;
                }
            })
            //确认添加节的标题
            $('.ok', '#course_set h4').live('click', function() {
                var h4 = $(this).closest('h4');
                var chapter = $(this).closest('.section');
                var chapter_id = $('#chapter_span #chapter_id', chapter).val();//得到当前章的id,从服务器端返回
                if (typeof chapter_id == 'undefined' || Number(chapter_id) < '1') {
                    $.dialog.alert('请先添加对应的章');
                    return false;
                }
                var section = $(this).closest('.chapter_content');
                var section_id = $('#section_span #section_id', section).val();//得到当前节的id,从服务器端返回	
                var section_edit = $(this).parent().siblings("span:eq(1)");
                var input_title = section_edit.find('input:first');
                var add_cc_content_flag = $('.edit_chapter', section).length;
                var addcontent_btn = $('<span class="add_cc"><a href="javascript:void(0);"><span class="bottomdirection">' + (add_cc_content_flag > 0 ? '编辑' : '添加') + '内容</span></a></span>');
                var select_type = $('<div class="select_type" style="display:block"><div class="select_box"><a href="javascript:void(0)" class="video"></a><a href="javascript:void(0)" class="sound"></a><a href="javascript:void(0)" class="pdf"></a></div></div>');
                var input_title_temp = $.trim(section.find('.chapter_title input').val());
                if (input_title_temp == "输入标题" || input_title_temp == '') {
                    $.dialog.alert('请先添加对应的节的标题');
                    return false;
                } else {
                    section_edit.empty().append(input_title.val() == "输入标题" ? '<b style="color:#ccc">标题未输入</b><a class="edit_chapter_title" href="#"></a><a class="delete_chapter" href="#"></a>' : '<b>' + input_title.val() + '</b><a class="edit_chapter_title" href="#"></a><a class="delete_chapter" href="#"></a>');
                    $("span[class='add_cc']", h4).show();
                    $("span[class='add_other_info']",h4).show();
                    $("h4 span[class='confirm']", section).remove();
                    $.post(base_url + "assign_course/process", {isFirstChapter: ($('.chapter_content').length == 1 ? 'yes' : 'no'), CID: CID, chapter_id: chapter_id, section_id: section_id, title: input_title_temp, isParent: 'no'}, function(msg) {
                        if (msg) {
                            switch (msg.info) {
                                case 'success':
                                    $("#del_id").val( msg.section_id );
                                    if (typeof msg.section_id != 'undefined') {
                                        $('#section_span #section_id', section).val(msg.section_id);
                                        if (add_cc_content_flag < 1 && $("div[class='select_type']", section).length < 1) {
                                            $('.chapter_content div.select_type').hide();
					                        $('.chapter_content div.edit_chapter').hide();
                                            $("#sec_id").attr("name","s_"+msg.section_id);
                                            $('.chapter_content.curr').find("a[name^='s_']").parent().removeClass("curr");
                                            h4.after(select_type);
                                            h4.append(addcontent_btn);
                                        }
                                    }
                                    break;
                                case 'nologin':
                                    $.dialog.alert('系统检测到您当前处于离线状态,请重新登录');
                                    break;
                                case 'noparam':
                                    $.dialog.alert('系统中暂无当前课程的信息!请确认');
                                    break;
                                case 'nomode':
                                    $.dialog.alert('网络繁忙,请关闭浏览器后重试');
                                    break;
                                case 'nosection':
                                    $.dialog.alert('请先添加对应的章', function() {
                                        window.location.reload();
                                    });
                                    break;
                            }
                        }

                    }, 'json');
                    return false;
                }
            });
            //节的标题鼠标滑过
            $('h4','.chapter_content').live('mouseenter', function(){
                $(this).find('a.edit_chapter_title').css('visibility', 'visible');;
                if (!$(this).children().hasClass('confirm')){
                    $(this).find('a.delete_chapter').show();
                }
            }).live('mouseleave', function(){
                $(this).find('a.edit_chapter_title').css('visibility', 'hidden');
                $(this).find('a.delete_chapter').hide();
            })

            //节的标题(小笔头图标)编辑
            $('.chapter_title a.edit_chapter_title').live('click', function() {
                var h4 = $(this).closest('h4');
                var value = $(this).prev().text();
                var inputText=$('<input type="text" maxlength="50" value="' + value + '">');
                $("span[class='add_cc']", h4).hide();
                $("span[class='add_other_info']",h4).hide();
                h4.append(confirm);
                $(this).parent().append(inputText);
                $(this).prev().css('display', 'none');//是b标签
                $(this).next().css('display', 'none');//删除图标
                $(this).css('display', 'none');//笔头图标
                inputText.focus().select();
                return false;
            })

            //取消节,改为状态切换,而非节的删除操作
            $('.cancel', '#course_set h4').live('click', function() {
                var section = $(this).closest('.section');
                var chapter_id = $('#chapter_span #chapter_id', section).val();//得到当前章的id
                var chapter = $(this).closest('.chapter_content');
                var section_id = $('#section_span #section_id', chapter).val();//得到当前节的id,从服务器
                var add_cc_content_flag = $('.edit_chapter', chapter).length;
                var section_val = $("h4 span[class='chapter_title']", chapter).find("input").val();
                //若是已经存在的节
                if (typeof chapter_id != 'undefined' && section_id != null && section_id != '') {
                    // var val = $("h4 span[class='chapter_title']", chapter).find("input").val();
                    $("h4 span[class='chapter_title']", chapter).replaceWith('<span class="chapter_title"><b>' + section_val + '</b><a class="edit_chapter_title" href="#"></a><a class="delete_chapter" href="#"></a></span>');
                    $("h4 span[class='confirm']", chapter).remove();
                    $("h4 span[class='add_cc']", chapter).show();
                    $("h4 span[class='add_other_info']", chapter).show();
                    // $("h4", chapter).append('<span class="add_cc"><a href="javascript:void(0);"><span class="bottomdirection">' + (add_cc_content_flag > 0 ? '编辑' : '添加') + '内容</span></a></span>');
                } else {
                    $(chapter).remove();
                    updata_chapter_num();
                }
                return false;//防止URL自动追加#号,导致页面自动定位到顶部
            })
            //整节删除
            $('a.delete_chapter', '#course_set h4 .chapter_title').live('click', function() {
                var chapter = $(this).closest('.section');
                var chapter_id = $('#chapter_span #chapter_id', chapter).val();//得到当前章的id
                var section = $(this).closest('.chapter_content');
                var section_id = $('#section_span #section_id', section).val();//得到当前节的id,从服务器
                var remove_flag = $(this);
                $.dialog.confirm('确认要删除该节的信息吗？', function() {
                    //若章、节在系统中已经存在需要从系统中删除
                    if (typeof section_id != 'undefined' && typeof chapter_id != 'undefined') {

                        $.post(base_url + "assign_course/del_process", {CID: CID, chapter_id: chapter_id, section_id: section_id, mode: 'whole_section'}, function(msg) {

                        }, 'json');
                        remove_flag.closest('.chapter_content').remove();

                        updata_chapter_num();
                    }
                }, function() {

                });
                return false;
            })
            var up_submit = function(type, which) {

            }
            //章、节标题清空,统一修改章、节标题 
            var chapter_title = '输入标题', section_title = '请输入标题';
            $('.chapter_title input').live('click', function() {
                if ($(this).val() == chapter_title) {
                    $(this).select();
                }
            }).live('focus', function() {
                var current_val = $(this).val();
                if (current_val == chapter_title) {
                    $(this).val('');
                }
            }).live('blur', function() {
                var current_val = $.trim($(this).val());
                if (current_val == '') {
                    $(this).val(chapter_title);
                }
            })
            $(".section .titles_edit input").live('click', function() {
                if ($(this).val() == section_title) {
                    $(this).select();
                }
            }).live('focus', function() {
                var current_val = $(this).val();
                if (current_val == section_title) {
                    $(this).val('');
                }
            }).live('blur', function() {
                var current_val = $.trim($(this).val());
                if (current_val == '') {
                    $(this).val(section_title);
                }
            })
            /* --四种上传弹窗 start-- */

            //当4个不同功能弹窗时，需要获得当前对应的章、节ID信息,并且获得对应的弹窗类型1.视频、2.音频。3.pdf/4.文本
            function add_json_info(WINDOW_TYPE, CID, chapter_id, section_id) {
                json_info = {"window_type": WINDOW_TYPE, "CID": CID, "chapter_id": chapter_id, "section_id": section_id};
            }

            //节的内容的不同图标的绑定事件
            $('img', '#course_set .course_type_ico').live('click', function() {
                var judge_pic_flag = $(this).attr('flag');//1.视频、2.音频。3.pdf、4.文本
                switch (judge_pic_flag) {
                    case '1':
                        var chapter = $(this).closest('.section');
                        var chapter_id = $('#chapter_span #chapter_id', chapter).val();//得到当前章的id
                        var section = $(this).closest('.chapter_content');
                        var section_id = $('#section_span #section_id', section).val();//得到当前节的id
                        $("#sid").val(section_id);
                        add_json_info('addVideo', CID, chapter_id, section_id);

                        var chapter_content = $(this).closest(".chapter_content");
                        var edit_chapter = $(this).closest(".edit_chapter").hide();
			            chapter_content.append("<div class='select_type' style='display:block'></div>");
			            edit_chapter=$(this).closest(".edit_chapter");
                        var select_type = $(".select_type", chapter_content);
                        if ($("#course_set .select_video").size() > 0) {
                            $("#course_set .select_video").each(function() {
                                $(".js_cancel_up_video", this).click();
                            });
                        }
                        select_type.append($("#template_add .select_video"));
                        $(".select_video input[name='videofile']", select_type).attr("id", "videofile");
                        $(".select_video", select_type).show();
                        var swfobj = new SWFObject('http://union.bokecc.com/flash/api/uploader.swf', 'uploadswf', '80', '25', '8');
                        swfobj.addVariable("progress_interval", 1);	//	上传进度通知间隔时长（单位：s）
                        swfobj.addVariable("notify_url", base_url_cc + "cc/notify");	//	上传视频后回调接口
                        swfobj.addParam('allowFullscreen', 'true');
                        swfobj.addParam('allowScriptAccess', 'always');
                        swfobj.addParam('wmode', 'transparent');
                        swfobj.write('swfDiv');
                        break;
                    case '2':
                        var chapter = $(this).closest('.section');
                        var chapter_id = $('#chapter_span #chapter_id', chapter).val();//得到当前章的id
                        var section = $(this).closest('.chapter_content');
                        var section_id = $('#section_span #section_id', section).val();//得到当前节的id
                        $("#sid").val(section_id);
                        add_json_info('addSound', CID, chapter_id, section_id);

                        var chapter_content = $(this).closest(".chapter_content");
			            $(this).closest(".edit_chapter").hide();
			            chapter_content.append($("<div class='select_type' style='display:block'></div>"));
                        var select_type = $(".select_type", chapter_content);
                        $("#template_add .select_sound").clone().appendTo(select_type).show();
                        $(".select_sound", select_type).attr("id", "select_sound");
                        if (!$('.select_sound', select_type).hasClass('fileQueue_sound_class')){
                            $(".sound_file", select_type).before($('<div id="fileQueue_sound" class="fileQueue_sound_class"></div>'));
                        }
                        if (!$('.select_sound', select_type).hasClass('uploadify_sound_class')){
                            $(".sound_file", select_type).append($('<input type="file" name="uploadify_sound" id="uploadify_sound" class="uploadify_sound_class upload_file_input" />'));
                        }
                        get('uploadify_sound', 'select_sound', 'fileQueue_sound', 'addSound', json_info);
                        break;
                    case '3':
                        var chapter = $(this).closest('.section');
                        var chapter_id = $('#chapter_span #chapter_id', chapter).val();//得到当前章的id
                        var section = $(this).closest('.chapter_content');
                        var section_id = $('#section_span #section_id', section).val();//得到当前节的id
                        $("#sid").val(section_id);
                        add_json_info('addDocument', CID, chapter_id, section_id);

                        var chapter_content = $(this).closest(".chapter_content");
			            $(this).closest(".edit_chapter").hide();
			            chapter_content.append($("<div class='select_type' style='display:block'></div>"));
                        var select_type = $(".select_type", chapter_content);
                        $("#template_add .select_pdf").clone().appendTo(select_type).show();
                        $(".select_pdf", select_type).attr("id", "select_pdf");
                        if (!$('.select_pdf', select_type).hasClass('fileQueue_pdf_class')){
                            $(".doc_file", select_type).before($('<div id="fileQueue_pdf" class="fileQueue_pdf_class"></div>'));
                        }
                        if (!$('.select_pdf', select_type).hasClass('uploadify_pdf_class')){
                            $(".doc_file", select_type).append($('<input type="file" name="uploadify_pdf" id="uploadify_pdf" class="uploadify_pdf_class upload_file_input" hidefocus="true" />'));
                        }
                        get('uploadify_pdf', 'select_pdf', 'fileQueue_pdf', 'addDocument', json_info);
                        break;
                }
            })
            //添加cc视频
            $('.select_box .video').live('click', function() {
                var flag = 0;
                $(".chapter_content").children(".select_type").find("div").each(function() {
                    if ($(this).hasClass("up_videoing")) {
                        $.dialog.alert("已有上传的课程，请等待该课程上传结束后再执行此操作");
                        flag = 1;
                        return;
                    }
                });
                if (flag == 1)
                    return;
                var chapter = $(this).closest('.section');
                var chapter_id = $('#chapter_span #chapter_id', chapter).val();//得到当前章的id
                var section = $(this).closest('.chapter_content');
                var section_id = $('#section_span #section_id', section).val();//得到当前节的id
                $("#sid").val(section_id);
                add_json_info('addVideo', CID, chapter_id, section_id);
                var select_type = $(this).closest(".select_type");
                $(this).closest(".select_box").remove();
                if ($("#course_set .select_video").size() > 0) {
                    $("#course_set .select_video").each(function() {
                        $(".js_cancel_up_video", this).click();
                    });
                }
                select_type.append($("#template_add .select_video"));
                $(".select_video input[name='videofile']", select_type).attr("id", "videofile");
                $(".select_video", select_type).show();
                var swfobj = new SWFObject('http://union.bokecc.com/flash/api/uploader.swf', 'uploadswf', '150', '40', '8');
                swfobj.addVariable("progress_interval", 1);	//	上传进度通知间隔时长（单位：s）
                swfobj.addVariable("notify_url", base_url_cc + "cc/notify");	//	上传视频后回调接口
                swfobj.addParam('allowFullscreen', 'true');
                swfobj.addParam('allowScriptAccess', 'always');
                swfobj.addParam('wmode', 'transparent');
                swfobj.write('swfDiv');
            });
            //取消添加
            $(".select_type .js_cancel_up_video").live('click', function() {
                var select_type = $(this).closest(".select_type");
                if ($(this).closest(".chapter_content").find("div").hasClass("edit_chapter")) {
                    $(this).closest(".select_video").appendTo("#template_add");
                    select_type.closest(".chapter_content").find(".edit_chapter").show();
                    select_type.remove();
                } else {
                    $(this).closest(".select_video").appendTo("#template_add");
                    select_type.append($('#template_add .select_box').clone());
                }
            });
            //取消上传
            $(".select_type .js_cancel_uping_video").live("click", function() {
                var select_type = $(this).closest(".select_type");
                var select_videotmp = select_type.children(".select_video").clone();
                select_type.children(".select_video").remove();
                $(this).closest(".up_videoing").remove();
                select_videotmp.appendTo(select_type).css({overflow: "visible"});
                var swfobj = new SWFObject('http://union.bokecc.com/flash/api/uploader.swf', 'uploadswf', '150', '40', '8');
                swfobj.addVariable("progress_interval", 1);	//	上传进度通知间隔时长（单位：s）
                swfobj.addVariable("notify_url", base_url_cc + "cc/notify");	//	上传视频后回调接口
                swfobj.addParam('allowFullscreen', 'true');
                swfobj.addParam('allowScriptAccess', 'always');
                swfobj.addParam('wmode', 'transparent');
                swfobj.write('swfDiv');
            });

            //添加视频代码
            $(".select_type .js_up_vtext").live("click", function() {
                var chapter = $(this).closest('.section');
                var chapter_id = $('#chapter_span #chapter_id', chapter).val();//得到当前章的id
                var section = $(this).closest(".chapter_content");
                var section_id = $('#section_span #section_id', section).val();//得到当前节的id
                $("#sid").val(section_id);
                add_json_info('addVideo', CID, chapter_id, section_id);
                var select_type = $(this).closest(".select_type");
                // $(this).closest(".select_video").appendTo("#template_add");
                // $("#template_add .up_vtexting").clone().appendTo(select_type);
                // $('.up_vtexting textarea', select_type).val('');
                $.post(base_url + "assign_course/get_about_file_info", {CID: json_info.CID, chapter_id: json_info.chapter_id, section_id: json_info.section_id, mode: 'getPlayCode'}, function(msg) {
                    if (msg && msg.info) {
                        switch (msg.info) {
                            case 'yes':
                                $('.up_vtexting textarea', select_type).val(msg.data.playcode);
                                break;
                            case 'empty':
                                $('.up_vtexting textarea', select_type).val('');
                                break;
                        }
                    }
                }, 'json');
            });
            //取消添加视频代码
            $(".up_vtexting #cancel_up_video_submit").live("click", function() {
                var select_type = $(this).closest(".select_type");
                $(this).closest(".up_vtexting").remove();
                if ($("#course_set .select_video").size() > 0) {
                    $("#course_set .select_video").each(function() {
                        $(".js_cancel_up_video", this).click();
                    });
                }
                select_type.append($("#template_add .select_video"));
                $(".select_video input[name='videofile']", select_type).attr("id", "videofile");
                $(".select_video", select_type).show();
                var swfobj = new SWFObject('http://union.bokecc.com/flash/api/uploader.swf', 'uploadswf', '150', '40', '8');
                swfobj.addVariable("progress_interval", 1);	//	上传进度通知间隔时长（单位：s）
                swfobj.addVariable("notify_url", base_url_cc + "cc/notify");	//	上传视频后回调接口
                swfobj.addParam('allowFullscreen', 'true');
                swfobj.addParam('allowScriptAccess', 'always');
                swfobj.addParam('wmode', 'transparent');
                swfobj.write('swfDiv');
            });

            //点击音频按钮添加音频
            $('.select_box .sound').live('click', function() {
                var chapter = $(this).closest('.section');
                var chapter_id = $('#chapter_span #chapter_id', chapter).val();//得到当前章的id
                var section = $(this).closest('.chapter_content');
                var section_id = $('#section_span #section_id', section).val();//得到当前节的id
                $("#sid").val(section_id);
                add_json_info('addSound', CID, chapter_id, section_id);

                var select_type = $(this).closest(".select_type");
                $(this).closest(".select_box").remove();
                $("#template_add .select_sound").clone().appendTo(select_type).show();
                $(".select_sound", select_type).attr("id", "select_sound");
                if (!$('.select_sound', select_type).hasClass('fileQueue_sound_class')) {
                    $(".sound_file", select_type).before($('<div id="fileQueue_sound" class="fileQueue_sound_class"></div>'));
                }
                if (!$('.select_sound', select_type).hasClass('uploadify_sound_class')) {
                    $(".sound_file", select_type).append($('<input type="file" name="uploadify_sound" id="uploadify_sound" class="uploadify_sound_class upload_file_input" />'));
                }
                get('uploadify_sound', 'select_sound', 'fileQueue_sound', 'addSound', json_info);
            });

            //取消音频上传
            $(".select_type .js_cancel_up_sound").live("click", function() {
                var select_type = $(this).closest(".select_type");
                $(this).closest(".select_sound").remove();
                if (select_type.closest(".chapter_content").children().hasClass("edit_chapter")) {
                    select_type.closest(".chapter_content").find(".edit_chapter").show();
                    select_type.remove();
                } else {
                    $("#template_add .select_box").clone().appendTo(select_type).show();
                }
            });

            //点击pdf按钮上传pdf
            $('.select_box .pdf').live('click', function() {
                var chapter = $(this).closest('.section');
                var chapter_id = $('#chapter_span #chapter_id', chapter).val();//得到当前章的id
                var section = $(this).closest('.chapter_content');
                var section_id = $('#section_span #section_id', section).val();//得到当前节的id
                $("#sid").val(section_id);
                add_json_info('addDocument', CID, chapter_id, section_id);
                var select_type = $(this).closest(".select_type");
                $(this).closest(".select_box").remove();
                $("#template_add .select_pdf").clone().appendTo(select_type).show();
                $(".select_pdf", select_type).attr("id", "select_pdf");
                if (!$('.select_pdf', select_type).hasClass('fileQueue_pdf_class')) {
                    $(".doc_file", select_type).before($('<div id="fileQueue_pdf" class="fileQueue_pdf_class"></div>'));
                }
                if (!$('.select_pdf', select_type).hasClass('uploadify_pdf_class')) {
                    $(".doc_file", select_type).append($('<input type="file" name="uploadify_pdf" id="uploadify_pdf" class="uploadify_pdf_class upload_file_input" hidefocus="true" />'));
                }
                get('uploadify_pdf', 'select_pdf', 'fileQueue_pdf', 'addDocument', json_info);
            });
            //取消pdf上传
            $(".select_type .js_cancel_up_pdf").live("click", function() {
                var select_type = $(this).closest(".select_type");
                $(this).closest(".select_pdf").remove();
                if (select_type.closest(".chapter_content").children().hasClass("edit_chapter")) {
                    select_type.closest(".chapter_content").find(".edit_chapter").show();
                    select_type.remove();
                } else {
                    $("#template_add .select_box").clone().appendTo(select_type).show();
                }
            });

            $(".select_type form[name=modlive_course] .v_cancel_btn").live("click", function(){
                var select_type = $(this).closest(".select_type");
                $(this).closest(".tabs_wraper").remove();
                if (select_type.closest(".chapter_content").children().hasClass("edit_chapter")){
                    select_type.closest(".chapter_content").find(".edit_chapter").show();
                    select_type.remove();
                } else {
                    $("#template_add .tabs_wraper").clone().appendTo(select_type).show();
                }
            });
	    
	    
            /* --四种上传弹窗 end-- */
            $('#up_video_type').eol_Tabs({content: 'up_video_content', event: 'click'});
            //修改文字说明
            var target = null;
            $('.word_caption').live('click', function() {
                var section = $(this).closest('.section');
                var chapter_id = $('#chapter_span #chapter_id', section).val();//得到当前章的id	
                var chapter = $(this).closest('.chapter_content');
                var section_id = $('#section_span #section_id', chapter).val();//得到当前节的id
                
                //向文字说明的弹窗中动态增加章、节id
                if (!$('#text_note').hasClass('fun_text_sectionId_class')) {
                    $('#text_note').append("<input type='hidden' id='fun_text_sectionId' class='fun_text_sectionId_class' name='fun_text_sectionId' value='" + section_id + "' />");
                }
                if (!$('#text_note').hasClass('fun_text_chapterId_class')) {
                    $('#text_note').append("<input type='hidden' id='fun_text_chapterId' name='fun_text_chapterId' class='fun_text_chapterId_class' value='" + chapter_id + "' />");
                }
                showpopWindow($('#text_note'));
                target = $(this).closest('.ec_content').find('.ec_dec');
                $('textarea', '#text_note').val(target.text());
                return false;
            })

            //功能1=> 上传视频时的触发事件
            $('#up_video_submit').live('click', function() {
                var up_video_content_textarea = $.trim($(this).closest("form").find("textarea:eq(0)").val());
                if (up_video_content_textarea != '' && typeof up_video_content_textarea != 'undefined') {//代表是添加视频代码
                    if (/^\s*http/.test(up_video_content_textarea)) {
                        $.dialog.alert("请粘贴HTML代码");
                        return false;
                    }
                    $.post(base_url + "assign_course/process_introduce", {CID: CID, chapter_id: json_info.chapter_id, section_id: json_info.section_id, mode: json_info.window_type, playcode: up_video_content_textarea.substring(0, 65000)}, function(msg) {
                    	if (msg && msg.info == 'success') {
                            $.dialog.alert('恭喜您操作成功', function() {
                                window.location.reload();
                            });
                        } else {
                            $.dialog.alert('操作失败');
                        }
                    }, 'json');
                } else {
                    $.dialog.alert("请粘贴HTML代码");
                }
            })
            //功能2=> 上传音频时的触发事件
            $('#up_sound_submit').live('click', function() {
                $.dialog.confirm('确认要关闭当前窗口吗？', function() {
                    $("form", '.up_sound').children().each(function() {
                        if (!$(this).is('p')) {
                            $(this).remove();
                        }
                    })
                    hidepopWindow($('#up_sound_submit'));
                }, function() {

                });
                //console.log(json_info.chapter_id+'='+json_info.section_id+'='+json_info.window_type);
            })
            //功能3=> 上传pdf文档时的触发事件
            $('#up_document_submit').live('click', function() {
                $.dialog.confirm('确认要关闭当前窗口吗？', function() {
                    $("form", '.up_document').children().each(function() {
                        if (!$(this).is('p')) {
                            $(this).remove();
                        }
                    })
                    hidepopWindow($('#up_document_submit'));
                }, function() {

                });
                //hidepopWindow($('#up_document_submit'));
                //console.log(json_info.chapter_id+'='+json_info.section_id+'='+json_info.window_type);
            })
            //功能4=> 上传文本时的触发事件
            $('#up_text_submit_text').live('click', function() {
                if ($.trim(KContent.length) > 0) {//KContent是在课程安排页面定义的
                    $.post(base_url + "assign_course/process_introduce", {CID: CID, chapter_id: json_info.chapter_id, section_id: json_info.section_id, mode: json_info.window_type, playcode: KContent.substring(0, 65000)}, function(msg) {
                    	if (msg && msg.info == 'success') {
                            var myhref = window.location.href;
                            var myarr = myhref.split("?");
                            var canshu = /(course_id=[\d]+)/.exec(myarr[1]);
                            var myarr1 = new Array();
                            $("input[name='is_free']:checked").each(function() {
                                myarr1.push($(this).val());
                            });
                            var newhref = "";
                            if (myarr1.length >= 1)
                                newhref = myarr[0] + "?" + canshu[1] + "&showtips=1&checkid=" + myarr1.join(",");
                            else
                                newhref = myarr[0] + "?" + canshu[1] + "&showtips=1";
                            if (myhref.indexOf("addFlag") != -1)
                                newhref += "&addFlag=Add";
                            location.href = newhref + "&time=" + Date.parse(new Date()) + "#s_" + json_info.section_id;
                        }
                    }, 'json');

                } else {
                    $.dialog.confirm('确认要关闭当前窗口吗？', function() {
                        $("form", '.up_text').children().each(function() {
                            if (!$(this).is('p')) {
                                $(this).remove();
                            }
                        })
                        hidepopWindow($('#up_text_submit_text'));
                    }, function() {

                    });
                }
            })

            //文字说明和添加文本的提交按钮的名称重复都是=up_text_submit
            $('#up_text_submit').live('click', function() {
                //得到文本框的值
                var textarea = $(this).closest('#text_note').find('textarea').val();
                var section_id = $(this).closest('#text_note');//得到当前弹窗章的id
                var sectionid = $('#fun_text_sectionId', section_id).val();//得到当前弹窗章的id
                var chapter_id = $(this).closest('#text_note');//得到当前弹窗节的id
                var chapterid = $('#fun_text_chapterId', chapter_id).val();//得到当前弹窗节的id
                target.text(textarea);
                //清除文字说明的弹窗中动态增加章、节id
                $('#fun_text_sectionId', section_id).remove();
                $('#fun_text_chapterId', chapter_id).remove();
                hidepopWindow();
                //若章、节在系统中已经存在需要从系统中删除
                if (typeof section_id != 'undefined' && typeof chapter_id != 'undefined') {
                    $.post(base_url + "assign_course/process_introduce", {CID: CID, chapter_id: chapterid, section_id: sectionid, mode: 'addIntroduce', intro: textarea}, function(msg) {
                        if (msg && msg.info == 'success') {
                            target.text(msg.intro);
                        }
                    }, 'json');
                }
                return false;
            });

            //后加的
            var target = 0;
            $('.select_box a').live('click', function() {
                target = $('.chapters').index($(this).closest('.chapters'));
            });
            var up_submit = function(type, target) {

            }
            //分配教师的打开弹窗
            $('.edit_chapter .all_teachers,.tabs_wraper .all_teachers').live('click', function() {
                $("#select_teacher .teachers_List .col").empty();
                var m = '', a = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
                for (var p in a) {
                    m += "<li><a href=\"javascript:select('" + a[p] + "')\">" + a[p] + "</a></li>";
                }
                $("#select_teacher .a-z").empty().append(m);
                var form = document.forms['add_teacher_form'], e = form.elements;
                for (var p in e) {
                    if (typeof e[p] != 'undefined' && e[p] != null && (e[p].type == 'textarea' || e[p].type == 'text' || e[p].type == 'file')) {
                        e[p].value = '';
                    }
                }
                var chapter = $(this).closest('.section');
                var chapter_id = $('#chapter_span #chapter_id', chapter).val();//得到当前章的id
                var section = $(this).closest('.chapter_content');
                var section_id = $('#section_span #section_id', section).val();//得到当前节的id
                $("#del_id").val( section_id );
                $("#sid").val( section_id );
                add_json_info('addSound', CID, chapter_id, section_id);

                $('#add_teacher_chapter_id', '#add_teacher #add_teacher_form').val(json_info.chapter_id);
                //console.log(json_info.chapter_id+' '+$('#add_teacher_chapter_id','#add_teacher #add_teacher_form').val());
                $('#upload_type', '#add_teacher #add_teacher_form').val('add_teacher');
                $('#add_teacher_course_id', '#add_teacher #add_teacher_form').val(json_info.CID);
                if (typeof section_id != 'undefined' && typeof chapter_id != 'undefined') {
                    $.post(base_url + "assign_course/get_publish_teacher_list", {CID: CID, section_id: section_id, courseCreator: courseCreator, mode: 'getTeacher'}, function(msg) {
                        if (msg) {
                            switch (msg.info) {
                                case 'nologin':
                                    $.dialog.alert('系统检测到您当前处于离线状态,请重新登录');
                                    break;
                                case 'noparam':
                                    $.dialog.alert('网络错误,请关闭浏览器后重试');
                                    break;
                                case 'yes':
                                    var h = '', teacher_arr = '';
                                    if (typeof msg.section_teacher != 'undefined' && msg.section_teacher != null && msg.section_teacher != '') {
                                        teacher_arr = msg.section_teacher;
                                    }
                                    for (var p in msg.data) {
                                        if (msg.data[p].real_name == '' || msg.data[p].real_name == 'undefined' || msg.data[p].user_id == '' || msg.data[p].user_id == 'undefined' || msg.data[p].spell_sort == '' || msg.data[p].spell_sort == 'undefined') {
                                            continue;
                                        }
                                        h += '<li id="' + msg.data[p].spell_sort + '_' + msg.data[p].user_id + '" title="' + msg.data[p].real_name + '">';
                                        if (msg.data[p].head_img != '') {
                                            var head_img = UPLOAD_SITE + msg.data[p].head_img.replace(/(\/.*?[^\/])(\.[jpg|png|gif])/,"$1_48x48$2");
                                            h += '<label for="teacher_' + msg.data[p].teacher_id + '"><img onerror="this.onerror=null;this.src=\'' + base_url + 'public/images/m_userface.png\'" src="'+head_img + '">';
                                        } else {
                                            h += '<label for="teacher_' + msg.data[p].teacher_id + '"><img for="teacher_' + msg.data[p].teacher_id + '" src="' + base_url + 'public/images/m_userface.png">';
                                        }
                                        if (typeof teacher_arr != 'undefined' && $.trim(teacher_arr) != '') {
                                            h += '<span><input id="teacher_' + msg.data[p].teacher_id + '" type="checkbox"' + (in_array(teacher_arr, msg.data[p].teacher_id) || typeof msg.courseTeacherInfo == 'string' && in_array(teacher_arr, msg.courseTeacherInfo) ? " checked='checked'" : '');
                                            delete msg.courseTeacherInfo;
                                        } else {
                                            h += '<span><input id="teacher_' + msg.data[p].teacher_id + '" type="checkbox"' + (p == '0' && courseCreator == msg.data[p].user_id && userType === '1' ? " checked='checked'" : '');
                                        }
                                        h += ' name="teacher[]" value="' + msg.data[p].teacher_id + '"><label for="teacher_' + msg.data[p].teacher_id + '" ID="' + msg.data[p].teacher_id + '">' + msg.data[p].real_name + '</label></span></label></li>';
                                    }
                                    $("#select_teacher .teachers_List .col").append(h);
                                    break;
                            }
                        }
                    }, 'json');
                }
                showpopWindow($('#select_teacher'));
                return false;
            });
            //分配教师的关闭弹窗
            $('#select_teacher_submit').live('click', function() {
                var s = [], s_name = [];
                $("input[name='teacher[]']:checked", "#select_teacher .teachers_List .col").each(function() {
                    s.push($(this).val());
                    s_name.push($(this).parent().parent().attr('title'));
                });

                var chapter=$(this).closest('.section');
                var chapter_id=$('#chapter_span #chapter_id',chapter).val();//得到当前章的id
                var section=$(this).closest('.chapter_content');
                var section_id=$('#section_span #section_id',chapter).val();//得到当前节的id
                if (s.length > 0) {
                    $.post(base_url + "assign_course/process_introduce", {courseCreatorRealName: courseCreatorRealName, teacher: s, section_id: json_info.section_id, chapter_id: json_info.chapter_id, CID: CID, courseCreator: courseCreator, mode: 'addTeacher'}, function(msg) {
                        if (msg) {
                            switch (msg.info) {
                                case 'success':
                                    $.dialog.alert('恭喜您，分配教师成功', function() {
                                        var hef = location.href;
                                        hef = hef.replace(/time=\d{13}/, "time=" + Date.parse(new Date()));
                                        window.location.href = hef;
                                    });
                                    break;
                                case 'nologin':
                                    $.dialog.alert('系统检测到您当前处于离线状态,请重新登录');
                                    break;
                                case 'nocourse':
                                    $.dialog.alert('系统中暂无当前课程的信息!请确认');
                                    break;
                                case 'nomode':
                                    $.dialog.alert('网络繁忙,请关闭浏览器后重试');
                                    break;
                                case 'noparam':
                                    $.dialog.alert('网络错误,请关闭浏览器后重试');
                                    break;
                            }
                        }
                    }, 'json');
                } else {
                    $.dialog.alert('请至少分配一位教师');
                    hidepopWindow($('#up_data_submit'));
                }

            });

            //增加教师的信息提交和关闭弹窗
            $('#add_teacher_submit').live('click', function() {
                //当新增教师列表的信息全未填写时,直接返回到当前页面,否则提示填写信息,然后再跳转
                var form = document.forms['add_teacher_form'], e = form.elements, allExt = ['jpg', 'gif', 'png'];
                
                var f = trim($('#add_teacher_file', '#add_teacher #add_teacher_form').val());
                var info = {
                    real_name: trim($('#real_name', '#add_teacher #add_teacher_form').val()),
                    email: trim($('#email', '#add_teacher #add_teacher_form').val()),
                    tel: trim($('#tel', '#add_teacher #add_teacher_form').val()),
                    intro: trim($('#intro', '#add_teacher #add_teacher_form').val()),
                    path: f
                };
                if (info.real_name != '' || info.email != '' || info.tel != '' || info.intro != '' || info.path != '') {
                    if (info.real_name == '') {
                        $.dialog.alert('姓名不能为空');
                        return false;
                    }
                    if (info.email != '' && !isEmail(info.email)) {
                        $.dialog.alert('请填写正确的邮箱');
                        return false;
                    }
                    if (info.tel != '' && !isMobile(info.tel)) {
                        $.dialog.alert('请填写正确的手机');
                        return false;
                    }
                    if (info.intro == '') {
                        $.dialog.alert('简介不能为空');
                        return false;
                    }
                    if (trim(f) != '') {
                        var f = f.substring(f.lastIndexOf('.') + 1, f.length).toLowerCase();
                        if (!in_array(allExt, f)) {
                            $.dialg.alert('请上传' + allExt.join(',') + '格式的文档');
                            return false;
                        }
                    }
                    //console.log($('#add_teacher_chapter_id','#add_teacher #add_teacher_form').val());
		    $.post(base_url+'teacher/check_teacher',{name:info.real_name},function(msg){
		    if ( msg && msg.info ) {
		        switch ( msg.info ) {
		            case 'nologin':
		                $.dialog.alert('<font color="grey">系统检测到您已经处于离线状态,请重新登录</font>');
				    return false;
		            break;
		            case 'exists':
		                $.dialog.alert('<font color="grey">教师 [<span class="red">'+info.real_name+'</span>] 已经存在！请确认</font>');return false;
			 	   return false;
		            break;
		            case 'yes':
		                $('.tac', '#add_teacher #add_teacher_form').css('display', 'none');
                            $('#add_teacher_tips', '#add_teacher #add_teacher_form').css('display', '').html('处理中,请稍后...');
                            form.method = 'post';
                            form.target = "add_teacher_iframe";
                            form.action = base_url + "assign_course/img_handle";
                            form.submit();
                            break;
			    }
			}
		    },'json');
		} else {
		    showpopWindow($('#select_teacher'));
		    return false;
		}
	    });
            //分配教师处增加教师的弹窗
            $('#add_teacher_link').live('click', function() {
                $('.tac', '#add_teacher #add_teacher_form').css('display', '');
                $('#add_teacher_tips', '#add_teacher #add_teacher_form').css('display', 'none').html('处理中,请稍后...');
                var form = document.forms['add_teacher_form'], e = form.elements;
                for (var p in e) {
                    if (typeof e[p] != 'undefined' && e[p] != null && (e[p].name != 'upload_type' || e[p].name != 'add_teacher_chapter_id' || e[p].name != 'add_teacher_course_id') && (e[p].type == 'textarea' || e[p].type == 'text' || e[p].type == 'file')) {
                        e[p].value = '';
                    }
                }
                showpopWindow($('#add_teacher'));
                return false;
            });

            //辅导资料关闭弹窗
            $('#up_data_submit').live('click', function() {
                hidepopWindow($('#up_data_submit'));
                if ($(this).closest(".popWindow").hasClass('up_data')) {
                    $(".upload_data", $("#up_data")).children().add("#fileQueue_data", $("#up_data")).remove();
                }
                return false;
            });
            //章节辅导材料的弹窗
            $('.edit_chapter .help_data').live('click', function() {
                //$('.upload_data input:first','#up_data #up_data_form').replaceWith('<input id="up_data_file" type="file" name="up_data_file" style="height:22px;line-height:22px;" size="38">');
                //$('#up_data_upload','#up_data #up_data_form').val('上传');
                var chapter = $(this).closest('.section');
                var chapter_id = $('#chapter_span #chapter_id', chapter).val();//得到当前章的id
                var section = $(this).closest('.chapter_content');
                var section_id = $('#section_span #section_id', section).val();//得到当前节的id,从服务器
                $("#del_id").val( section_id ); // 赋值隐藏的文本框值
                $("#sid").val( section_id );    // 赋值隐藏的文本框值
                add_json_info('addData', CID, chapter_id, section_id);
                if (!$('.pop_content #up_data_form').hasClass('fileQueue_data_class')) {
                    $('.pop_content #up_data_form .upload_data').before($('<div id="fileQueue_data" class="fileQueue_data_class"></div>'));
                }
                if (!$('.pop_content #up_data_form').hasClass('uploadify_data_class')) {
                    $('.pop_content #up_data_form .upload_data').append($('<input type="file" name="uploadify_data" id="uploadify_data" class="uploadify_data_class"/>'));
                }
                get('uploadify_data', 'up_data_form', 'fileQueue_data', 'addData', json_info);
                showpopWindow($('#up_data'));

                if (typeof section_id != 'undefined') {
                    $.post(base_url + "assign_course/get_about_file_info", {CID: CID, section_id: section_id, mode: 'getAboutFileInfo'}, function(msg) {
                        if (msg && msg.info) {
                            switch (msg.info) {
                                case 'empty'://空信息
                                    break;
                                case 'yes':
                                    var h = '';
                                    for (var p in msg.data) {
                                        if (typeof msg.data[p].section_id == 'undefined' || msg.data[p].section_id == '' || typeof msg.data[p].doc_id == 'undefined' || msg.data[p].doc_id == '' || typeof msg.data[p].original_file == 'undefined' || msg.data[p].original_file == '') {
                                            continue;
                                        }
                                        h += '<p id="p_' + msg.data[p].doc_id + '" class="datas" title="' + msg.data[p].original_file + '"> <em>' + msg.data[p].original_file.substring(0, 25) + '</em><a href="javascript:delAboutFile(' + msg.data[p].doc_id + ',' + msg.data[p].section_id + ',' + CID + ')"></a></p>';
                                    }
                                    $('.updata_list', '#up_data #up_data_form').empty().append(h);
                                    break;
                            }
                        }
                    }, 'json');
                }
                add_json_info('', CID, section_id, chapter_id);
                return false;
            });

            //节的删除,删除(删除按钮应该是删除具体内容，而不是整节都删除,暂不重建索引)完后要重建节的索引顺序
            $('.ec_delete').live('click', function() {
                var chapter = $(this).closest('.section');
                var chapter_id = $('#chapter_span #chapter_id', chapter).val();//得到当前章的id
                var section = $(this).closest('.chapter_content');
                var section_id = $('#section_span #section_id', section).val();//得到当前节的id,从服务器
                var remove_flag = $(this);
                var select_type = $('<div class="select_type" style="display:block"><div class="select_box"><a href="#" class="video"></a><a href="#" class="sound"></a><a href="#" class="pdf"></a></div></div>');
                $.dialog.confirm('确认要删除该节的内容吗？', function() {
                    //若章、节在系统中已经存在需要从系统中删除
                    if (typeof section_id != 'undefined' && typeof chapter_id != 'undefined') {
                        $.post(base_url + "assign_course/del_process", {CID: CID, chapter_id: chapter_id, section_id: section_id, mode: 'chapter'}, function(msg) {
                            if (msg) {
                                switch (msg.info) {
                                    case 'success':
                                        var delId = $("#del_id").val();
                                        $('[data-id="'+delId+'"]').remove();
                                        remove_flag.closest('.edit_chapter').remove();
                                        section.append(select_type);
                                        // window.location.reload();
                                        //remove_flag.closest('.chapter_content').remove();
                                        //updata_chapter_num();							
                                        break;
                                    case 'nologin':
                                        $.dialog.alert('系统检测到您当前处于离线状态,请重新登录');
                                        break;
                                    case 'nocourse':
                                        $.dialog.alert('系统中暂无当前课程的信息!请确认');
                                        break;
                                    case 'nomode':
                                        $.dialog.alert('网络繁忙,请关闭浏览器后重试');
                                        break;
                                    case 'noparam':
                                        $.dialog.alert('网络错误,请关闭浏览器后重试');
                                        break;
                                }
                            }
                        }, 'json');
                    }
                }, function() {

                });
            })
        };
    })

    //直播
    var start_study_live = $('.live_flv');
    start_study_live.each(function(i) {
        timeArr[i] = $(this).children(".live_order_starttime").val();
    })
    var phone = $("#live_phone").val();
    var email = $("#live_email").val();
    start_study_live.bind('click', function() {
        var login_user_id = $('#login_user_id').val();
        var course_id = $("#course_id").val();
        var ispay = $("#ispay").val();
        var live_section_id = $(this).children('.live_section_id').val();
        var live_order_id = $(this).children('.live_order_id').val();
        var live_order_flag = $(this).children('.live_order_flag').val();
        var starttime = $(this).children('.live_order_starttime').val();
        var show = $(this).children('.live_href').val();
        var key = $(this).children('.key').val();
        if (login_user_id == 0) {
            $('#from').val(window.location.href);
            showpopWindow($('#sign'));
            return false;
        }
        if (live_order_id == 1) {
            //弹框
            $("#order_section_id").val(live_section_id);
            $("#order_phone").val(phone);
            $("#order_email").val(email);
            if (live_order_flag == 1){
	        $("#order_y").show();
	        $("#order_n").hide();
	    }
            else{
                $("#order_n").show();
                $("#order_y").hide();
                $.ajax({
                    type: "get",
                    url: base_url + "live/get_current_time",
                    async: false,
                    success: function(data) {
                        data = eval('(' + data + ')');
                        timeArr[key] = starttime - data;
                    }
                });
                if (timeArr[key] > 0) {
                    if (inter) {
                        clearInterval(inter);
			$("#live").parent().hide();
		    }
			inter = setInterval("timeShow(" + key + ")", 1000);
		    }
		    else {
			clearInterval(inter);
			$("#live").parent().hide();
		    }
            }
            $("#order_show").show();
            return false;
        } else if (show == 1) {
            window.open(base_url + "live/live_in?id=" + live_section_id);
            return false;
        } else {
            return false;
        }
    });
    //加入购物车
    $("#addcart_btn").bind("click", function() {
        var login_user_id = $('#login_user_id').val();
        var course_id = $("#course_id").val();
        if (login_user_id == '0') {
            $('#from').val(window.location.href);
            showpopWindow($('#sign'));
            return false;
        }
        if (course_id == 0) {
            $.dialog.alert("课程信息错误");
            return false;
        }
        $.ajax({
            type: "post",
            url: base_url + "order/ajax_add_cart",
            data: {cid: course_id},
            success: function(ret) {
                data = eval('(' + ret + ')');
                if (data.data.status == 1) {
                    $("#addtocart").show();
                    return false;
                } else {
                    $.dialog.alert(data.data.msg);
                    return false;
                }

            }
        });
    });
    $("#continue_add").bind("click", function() {
        popWindows.hide();
        //页头购物车图标样式变化
        $(".userinfo-cartbox").show();
    });
    /*-- 各种效果结束 --*/
});
function timeShow(i)
{
    var day = Math.floor(timeArr[i] / 86400);
    var hour = Math.floor((timeArr[i] % 86400) / 3600);
    var minite = Math.floor((timeArr[i] % 86400) % 3600 / 60);
    var second = timeArr[i] % 86400 % 3600 % 60;
    if (timeArr[i] <= 0) {
        clearInterval(inter);
        $("#live").parent().hide();
    }
    else {
        $("#live").text(day + "天" + hour + "小时" + minite + "分" + second + "秒");
        $("#live").parent().show();
    }
    timeArr[i]--;
}
//预约
function order() {
    var section_id = $("#order_section_id").val();
    var course_id = $("#course_id").val();
    var phone = $("#order_phone").val();
    var email = $("#order_email").val();
    if (!checkEmail(email))
    {
        $.dialog.alert("邮箱填写错误，请重新填写");
        return false;
    }
    if (phone != '请输入手机号' && phone !='' && !isMobile(phone)) {
        $.dialog.alert('请填写正确的手机');
        return false;
    }
    if (email != '' && !isEmail(email)) {
        $.dialog.alert('请填写正确的邮箱');
        return false;
    }
    $.ajax({
        type: "post",
        url: base_url + "live/order",
        data: {sectionid: section_id, courseid: course_id, phone: phone, email: email},
        success: function(data) {
            data = eval('(' + data + ')');
            if (data.msg == 'error') {
                $.dialog.alert(data.data);
                return false;
            }
            $.dialog.alert("预约成功");
            $("#" + section_id).siblings(".enter").html('已预约');
            $("#" + section_id).siblings(".live_order_flag").val(1);
            $("#live_email").val(email);
            $("#order_show").hide();
        }
    })
}