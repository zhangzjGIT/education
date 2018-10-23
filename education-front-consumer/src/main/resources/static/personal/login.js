/*检测登录*/
var emailT = false;
var pwdT = false;
var loginScriptNum = 0;
var intervalId;
var flg = '';
var emailReg = false;
var nickReg = false;
var loginData = "";
function checkLogin(){
	var email = $("#email").val();
	var pass = $("#pass").val();
	var save = 0;
	$.ajax({
		type:"post",
		url:base_url+"ajax/user/login_check/",
		dataType:'json',
		async: true,
		cache:false,
		data:{email:email, pass:pass, save:save, openno:1},
		success: function(data){
			if(data.msg == 'error'){
				$.dialog.alert('<font color="grey">'+data.data+'</font>');
			}
			else{
				$("#sign_submit").hide();
				$("#sign_submit_gray").show();
				if (data.loginScript)
				{
					var patt = /src="([^"]+)"/gi;
					var r, i=0, ret;
					loginData = data.data;
					while(r = patt.exec(data.loginScript))
					{
						flg = 'login';
						includeJs(r[1], flg);
						i++;
						if (i > 4)
						{
							break;
						}
					}
					
				} else {
					window.location.reload();
				}
			}
		}
	});
}

function includeJs(file, flg) {
    var html_doc = document.getElementsByTagName('head')[0];
    js = document.createElement('script');
    js.setAttribute('type', 'text/javascript');
    js.setAttribute('src', file);
    html_doc.appendChild(js);
	if(document.all){
		js.onreadystatechange = function () {addScriptNum(flg)};
	}else{
		js.onload = function () {addScriptNum(flg)};
	}
	return false;
}
function addScriptNum(flg)
{
	loginScriptNum++;
	if (loginScriptNum >= 4)
	{
		if (flg == "logout")
		{
			logoutOK();
		} else {
			window.setTimeout(function(){loginOK(flg);}, 1000);
		}
	}
}
function logoutOK()
{
	window.location.href=base_url+"user/logout/";
}

function loginOK(flg)
{
	var from = $("#from").val();
	if (from.length == 0)
	{
		if(flg == 'login'){
		}
		else if(flg == 'register'){
			$.dialog.alert('<font color="grey">注册成功</font>');
		}
		if((location.href == base_url+'index/stu')||(location.href == base_url+'index/main')||(location.href == base_url+'index/tech')){
			var arr = loginData.split("|");
			if(arr[3] == 1){
				if(arr[1] > 0)
					location.href = base_url+"my_course/lists";
				else if(arr[2] > 0)
					location.href = base_url+"my_course/upload_lists";
				else
					window.location.reload();
			}else if(arr[3] == 2){
				if(arr[2] > 0)
					location.href = base_url+"my_course/upload_lists";
				else if(arr[1] > 0)
					location.href = base_url+"my_course/lists";
				else
					window.location.reload();
			}else{
				window.location.reload();
			}
		}else
			window.location.reload();
			//setTimeout(function(){location.href = base_url+'index/index?backUrl='+encodeURIComponent(location.href);}, 1000);
	} else {
		window.location.href = from;
	}
}

/*验证邮箱格式*/
function checkEmail(value){
	//var email = /^[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)*@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	var email = /^[a-zA-Z0-9-]+[a-zA-Z0-9-_.]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
	if(email.test(value)){
		return true;
	}
	else {
		return false;
	}
}
/*验证邮箱*/
function emailVerify(){
	var email = $("#email").val();
	if (email == '') {
        verifyOk($("#email"), 0, "请填写邮箱");
		return false;
	}	
	if(email.indexOf("@") == -1 || !checkEmail(email)) {
        verifyOk($("#email"), 0, "邮箱格式不正确");
		return false;
	}
    verifyOk($("#email"), 1);
}
/*验证密码*/
function passVerify(){
	var pass = $("#pass").val();
	if (pass == '') {
        verifyOk($("#pass"), 0, "请填写密码");
		return false;
	}
    verifyOk($("#pass"), 1);
}
function hideMsg(obj){
	var confirm = $(obj).parents("p").find("span[id^='confirm_']");
	confirm.hide();
	return true;
}

function verifyOk(obj, flag, msg){
    var src = base_url+'public/images/pw.jpg';
	var nObj = obj.next();
    nObj.parent().find("img").remove();
    flag == "undefined" ? 0 : 1;
    var regLogin = $("#reg_login").val();
    if(flag && (regLogin== 1)){
        nObj.after('<img class="pw" src="'+src+'">');
    }else{
        if(nObj.is("span")){
			
            nObj.addClass("check_form").html(msg).show();
        }
    }
}
/*注册*/
function register(str,formname){
	var formbox=$('#'+formname);
	var postData={
			captcha:$('[name=captcha]',formbox).first().val(),
			username:$('#username',formbox).val(),
			email:$('#email1',formbox).val(),
			password:$('#password',formbox).val(),
			password1:$('#password1',formbox).val(),
			usertype:(formname=='user'?1:2),
			agree:$('#agree',formbox).is(':checked')
		};
	var closebtn=$(str).closest('.popWindow').find('.pop_colsebtn');
	$.ajax({
		url:base_url+"ajax/user/check_register",
		data: postData,
		dataType:'json',
		type:'post',
		async: true,
		cache:false,
		success: function(data){
			if (data.msg == 'error')
			{
				$.dialog.alert('<font color="grey">'+data.data+'</font>');
				return false;
			} else {
				
				if (data.loginScript)
				{
					
					/*var patt = /src="([^"]+)"/gi;
					var r, i=0, ret, flg;
					while(r = patt.exec(data.loginScript))
					{
						flg = 'register';
						includeJs(r[1], flg);
						i++;
						if (i > 4)
						{
							break;
						}
					}
					*/
					//2015年12月16日12:36:58
					//$.dialog.alert('注册成功');
					window.location.href = base_url+'index/main';
					
				} else {
						
						window.location.href = from;
				}
				  
			}
				
//				console.log(closebtn)
				closebtn.trigger('click');
		}
	})
}

/*开放平台登录验证*/
var emailOpenT = 0;
var nickOpenT = 0;
var pwdOpenT = 0;
/*验证邮箱*/
function openEmailVerify(){
	var email = $("#email").val();
	var msg = "";
	if (email == '') {
		msg = "请填写邮箱";
		$("#confirm_email_open").addClass("check_form").html(msg).show();
		return false;
	}	
	if(email.indexOf("@") == -1 || !checkEmail(email)) {
		msg = "邮箱格式不正确";
		$("#confirm_email_open").addClass("check_form").html(msg).show();
		return false;
	}
	$.ajax({
		url:base_url + "ajax/user/check_email",
		data: {email:email},
		dataType:'json',
		type:'post',
		cache:false,
		success: function(data){
			if(data.data == -4){
				msg = "邮箱格式不正确";
			}else if(data.data == -5){
				msg = "该邮箱不允许注册";
			}else if(data.data == -6){
				msg = "该邮箱已经被注册过，请输入EOL通行证密码";
				emailOpenT = 2;//已经注册过,使用EOL通行证密码
			}
			if(msg != "") {
				$("#confirm_email_open").addClass("check_form").html(msg).show();
				return false;				
			}
			else {
				emailOpenT = 1;//未注册过，可使用
			}
		}		
	})
}
String.prototype.utf8length = function() { 
	var len = 0;
	for (var i=0; i<this.length; i++){
	if (this.charCodeAt(i)>127 || this.charCodeAt(i)==94){
	len += 2;
	}else { 
	len ++; 
	}
	} 
	return len; 
}
/*验证昵称*/
function openNickVerify(){
	var nick = $("#nick").val();
	var msg = "";
	if (nick == '') {
		msg = "请填写昵称";
		$("#confirm_nick_open").addClass("check_form").html(msg).show();
		return false;
	}	
	$.ajax({
		url:base_url + "ajax/user/check_nick",
		data: {nick:nick},
		dataType:'json',
		type:'post',
		cache:false,
		success: function(data){
			if(data.data == -1){
				msg = "昵称不合法";
			}else if(data.data == -2){
				msg = "该昵称包含要允许注册的词语";
			}else if(data.data == -3){
				nickOpenT = 2;//该昵称已经存在,检测邮箱，根据邮箱结果分别提示
			}
			if(msg != "") {
				$("#confirm_nick_open").addClass("check_form").html(msg).show();
				return false;				
			}
			else {
				nickOpenT = 1;//昵称不存在，可用
			}
		}		
	})
}
/*验证密码*/
function openPassVerify(){
	var pass = $("#pass").val();
	if (pass == '') {
		$("#confirm_pass_open").addClass("check_form").html("请填写密码").show();
		return false;
	}
	pwdOpenT = 1;
}
/*表单提交验证*/
function openLoginCheck(){
	openNickVerify();
	openEmailVerify();
	openPassVerify();

	if (pwdOpenT == 0 || emailOpenT == 0)
	{
		alert("请将信息填写完整");
		return false;
	}
	
	var pass = $("#pass").val();
	var nick = $("#nick").val();
	var email = $("#email").val();
	var headimage = $("#headimage").val();
	var from = $("#from").val();
	var type = "reg";
	if(pwdOpenT == 1 && nickOpenT == 2 && emailOpenT == 1){
		// 注册
		$("#confirm_nick_open").addClass("check_form").html("该昵称已存在").show();
		nickOpenT = 0;
		return false;
	}
	else if(pwdOpenT == 1 && emailOpenT == 2){
		// 与老帐号绑定标志
		type = "log";
	}
	$.ajax({
		url: base_url + "ajax/user/check_open_login",
		data: {email:email,pass:pass,nick:nick,type:type,headimage:headimage},
		dataType:'json',
		type:'post',
		async: true,
		cache:false,
		success: function(data){
			if(data.msg == "error"){
				$.dialog.alert('<font color="grey">'+data.data+'</font>');
				return false;
			}
			else {
				$("#open_sign_submit").hide();
				$("#open_sign_submit_ok").show();
				if (data.loginScript)
				{
					var patt = /src="([^"]+)"/gi;
					var r, i=0, ret, flg;
					while(r = patt.exec(data.loginScript))
					{
						flg = 'login';
						includeJs(r[1], flg);
						i++;
						if (i > 4)
						{
							break;
						}
					}
				} else {
					window.location.href = from;
				}
			}
		}
	})
}
/*回车实现登录注册提交*/
$(document).ready(function(){
	$("#login_form").keydown(function(e){
		 var e = e || event,
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
		  $("#sign_submit").trigger("click");
		 }
	});
	
	$("#register_user").keydown(function(e){
		 var e = e || event,
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
		  $("#register_user_submit").trigger("click");
		 }
	});
	
	$("#register_org").keydown(function(e){
		 var e = e || event,
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
		  $("#register_org_submit").trigger("click");
		 }
	});
});

/*注册用户昵称验证*/
function nickVerifyReg(formname){
	var formbox=$('#'+formname);
	var nick = $('#username',formbox).val();
	var msg = "";
	if (nick == '') {
		msg = "请填写昵称";
		$("#confirm_nick",formbox).html(msg);
		$("#confirm_nick",formbox).show();
		return false;
	}
	var nickLength = nick.utf8length();
	if(nickLength>15 || nickLength<3){
		msg = (nickLength>15)?"抱歉，您的昵称超长":"抱歉，您的昵称过短";
		$("#confirm_nick",formbox).html(msg).show();
		return false;
	}
	$.ajax({
		url:base_url + "ajax/user/check_nick",
		data: {nick:nick},
		dataType:'json',
		type:'post',
		cache:false,
		success: function(data){
			if(data.data == -1){
				msg = "昵称不合法";
			}else if(data.data == -2){
				msg = "该昵称包含要允许注册的词语";
			}else if(data.data == -3){
				msg = "该昵称已经存在"
			}
			if(msg != "") {
				$("#confirm_nick",formbox).html(msg).show();
				return false;				
			}
			else{
//				nickReg = true;
                verifyOk($("#username", formbox), 1);
				return true;
			}
		}		
	})
}

/*注册用户邮箱验证*/
function emailVerifyReg(formname){
	if($(".match_email",$("#"+formname)).css("display")!="none") return;
	var formbox=$('#'+formname);
	var email = $('#email1',formbox).val();
	var msg = "";
    var regLogin = $("#reg_login", formbox).val();
	if (email == '' || email == '电子邮箱') {
		$("#confirm_email_reg",formbox).html("请填写邮箱").show();
		return false;
	}
	if(email.indexOf("@") == -1 || !checkEmail(email)) {
		$("#confirm_email_reg",formbox).html("邮箱格式不正确").show();
		return false;
	}
	$.ajax({
		url:base_url + "ajax/user/check_email?"+Date.parse(new Date()),
		data: {email:email},
		dataType:'json',
		type:'post',
		cache:false,
		success: function(data){
			if(data.data == -4){
				msg = "邮箱格式不正确";
			}else if(data.data == -5){
				msg = "该邮箱不允许注册";
			}else if(data.data == -6){
				msg = "该邮箱已经被注册过,请直接<a href=\"javascript:void(0)\" id=\"return_sign_link\" onclick=\"loginRe('"+formname+"')\" >登录</a>或<a href=\"javascript:void(0)\" id=\"return_passwd_link\" onclick=\"passwdRe('"+formname+"')\">找回密码</a>";
			}
			if(msg != "") {
				$("#confirm_email_reg",formbox).html(msg).show();
				return false;			
			}
			else{
//				emailReg = true;
				var a=/\.(com|net|edu|org|gov|com\.cn|net\.cn|edu\.cn|org\.cn|gov\.cn|cn|hk|tw)$/i;
				if(!a.test(email))
					$("#confirm_email_reg",formbox).html("您确定使用"+$("#email1",$("#"+formname)).val()+"？"+"<a href='javascript:void(0)' class='js_upd'>修改</a>&nbsp;<a href='javascript:void(0)' class='js_sub'>确定</a>").show();
				else{
					$("#confirm_email_reg",formbox).html("").hide();
	                if(regLogin == 1){
	                    verifyOk($("#email1", formbox), 1);
	                }
                }
				return true;
			}
		}		
	})
}
/*注册提示找回密码*/
function passwdRe(formname){
	var formbox=$('#'+formname);
	var email = $('#email1',formbox).val();
	$("#forget_link").trigger("click");
	$("#reset_email").val(email);
	return false;
}
/*注册提示直接登录*/
function loginRe(formname){
	var formbox=$('#'+formname);
	var email = $('#email1',formbox).val();
	$("#header_sign").trigger("click");
	$("#email").val(email);
	return false;
}
/*注册验证密码*/
function passVerifyReg(formname){
    var formbox=$('#'+formname);
	var pass = $("#password", formbox).val();
	if (pass == '') {
        verifyOk($("#password", formbox), 0, "请填写密码");
		return false;
	}
	var $reg = /^[a-zA-Z0-9_]{6,15}$/;
	if($reg.test(pass)) {
        verifyOk($("#password", formbox), 1);
		return true;
	}
	else{
        verifyOk($("#password", formbox), 0, "密码必须是6-15位数字与字母组合");
		return false;
	}
}
/*再次输入密码*/
function passVerifyRegRe(formname){
    var formbox=$('#'+formname);
	var pass = $("#password").val();
	var pass1 = $("#password1").val();
	if(pass1 == '' || pass != pass1) {
        verifyOk($("#password1", formbox), 0, "两次密码输入不一致");
		return false;		
	}
	verifyOk($("#password1", formbox), 1);
}
/*验证码检测*/
function codeVerifyReg(formname){
     var formbox=$('#'+formname);
	 var code = $("#captcha", formbox).val();
	 if(code == ''){
         verifyOk($("#captcha", formbox), 0, "验证码不能为空");
		 return false;			 
	 }
 	 $.ajax({
		url:base_url + "ajax/user/check_code",
		data: {vcode:code},
		dataType:'json',
		type:'post',
		cache:false,
		success: function(data){
			if(data.msg == 'success') {
                verifyOk($("#captcha", formbox), 1);
				return true;			
			}
			else{
				return false;                
			}
		}		
	})        
}
/*自动检测各种邮箱*/
var end_email=['qq.com','163.com','126.com','sina.com','vip.sina.com','sina.cn','sohu.com','hotmail.com','gmail.com','yahoo.com','139.com','189.com'];
$(function(){
	$("#email1").live("keyup",function(event){
		var form=$(this).closest("form");
		if(!event) event=window.event;
		if(event.keyCode==13||event.keyCode==38||event.keyCode==40) return;
		var index=$(this).val().indexOf("@");
		if(index!=-1){
			var b_email=$(this).val().substr(0,index);
			var a_email=$(this).val().substr(index+1);
			var len=end_email.length;
			$("ul",$(this).parent()).html("").append("<li>请选择邮箱类型</li>");
			for(var i=0;i<len;i++){
				if(end_email[i].indexOf(a_email)!=-1){
					$("ul",$(this).parent()).append("<li>"+b_email+"@"+end_email[i]+"</li>");
				}
			}
			if($("ul li",$(this).parent()).size()>1)
				$(".match_email",form).show();
			else
				$(".match_email",form).hide();
		}else
			$(".match_email").hide();
	});
	$(".match_email_c li").live("click",function(){
		var form=$(this).closest("form");
		$("#email1",form).val($(this).html());
		$(".match_email",form).hide();
		$("#email1",form).trigger("blur");
	});
	$("#user").add("#org").delegate("#email1","keydown",function(event){
		var form=$(this).closest("form");
		$("#confirm_email_reg", form).html("").hide();
		$("#confirm_email_reg", form).parent().find("img").remove();
		if(!event) event=window.event;
		var email=$(this).val();
		switch(event.keyCode){
			case 38:
				var i=0;
				$(".match_email ul li",form).css({"color":"#999","background-color":"#fff"});
				$(".match_email ul li",form).each(function(){
					if($(this).html()==email){
						if($(this).index()==1){
							$(".match_email ul li:last",form).css("background-color","#00f").focus();
							$("#email1",form).val($(".match_email ul li:last",form).html());
						}else{
							$(this).prev().css("background-color","#00f").focus();
							$("#email1",form).val($(this).prev().html());
						}
					}else
						i++;					
				});
				if(i==$(".match_email ul li",form).size()){
					$(".match_email ul li:last",form).css("background-color","#00f").focus();
					$("#email1",form).val($(".match_email ul li:last",form).html());
				}
				break;
			case 40:
				var i=0;
				$(".match_email ul li",form).css({"color":"#999","background-color":"#fff"});
				$(".match_email ul li",form).each(function(){
					if($(this).html()==email){
						if($(this).index()==end_email.length){
							$(".match_email ul li:eq(1)",form).css("background-color","#00f").focus();
							$("#email1",form).val($(".match_email ul li:eq(1)",form).html());
						}else{
							$(this).next().css("background-color","#00f").focus();
							$("#email1",form).val($(this).next().html());
						}
					}else
						i++;					
				});
				if(i==$(".match_email ul li",form).size()){
					$(".match_email ul li:eq(1)",form).css("background-color","#00f").focus();
					$("#email1",form).val($(".match_email ul li:eq(1)",form).html());
				}
				break;
			case 13:
				if($(".match_email",form).css("display")=="none"){
					if(form.attr('id')=="user")
						$("#register_user_submit").trigger("click");
					else if(form.attr('id')=='org')
						$("#register_org_submit").trigger("click");
				}else{
					$(".match_email",form).hide();
					event.stopPropagation();
				}
				break;
		}
	});
	$(".js_upd").live("click",function(){
		$(this).closest("form").find("#email1").focus();
		$(this).closest("##confirm_email_reg").hide();
	});
	$(".js_sub").live("click",function(){
		$(this).closest("#confirm_email_reg").hide();
		$(this).closest("form").find("#username").val("").focus();
		
	});
	$(document).live("click",function(event){
		var form;
		if($("#register_org").css("display")=="block")
			form=$("#register_org");
		else if($("#register_user").css("display")=="block")
			form=$("#register_user");
		if($(".match_email", form).css("display")=="block"){
			$(".match_email",form).hide();
			$("#email1" ,form).trigger("blur");
		}
	})
});
