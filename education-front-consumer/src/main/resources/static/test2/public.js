function filterStr(haystack,needle){
	if(needle!=''){
		if(haystack.lastIndexOf(needle)=='-1'){
			return haystack;
		}else{
			if((haystack.lastIndexOf(needle)+1)==haystack.length){
				return haystack.substring(0,haystack.lastIndexOf(needle));
			}else{
				return haystack;
			}
		}
	}
}
function splitStr(str){
	var arr=str.split(',');
	var n=[];
	if(arr.length>0){
		for(var p in arr){
			if(arr[p].length=='')continue;
			else n.push(arr[p]);
		}
		return n.join(',');
	}
}
 /**
  *@desc		判断是否是数组
  *@param		要操作的数据
  *@return		bool
  *@Author		Qian Hong Lee
  *@time		2012-4-1
  */
function isArray(m){
    return typeof m==='object'&&
		   typeof m.length==='number'&&
		   typeof m.slice==='function'&&
		   !(m.propertyIsEnumerable('length'))? (m.length==0? 'empty Array' : true) : false ;
}
 /**
  *@desc		过滤数组中的空元素
  *@param		要操作的数据
  *@return		bool
  *@Author		Qian Hong Lee
  *@time		2012-4-1
  */
function trimArray(arr){
	var n=[];
	if(isArray(arr)&&typeof arr!='string'){
		for (var i=0;i<arr.length;i++)
		{
		    if(arr[i].length!=0){
				n.push(arr[i]);
			}
		}
	  return n;
	}
}
 /**
  *@desc		过滤数组中指定元素
  *@param		要操作的数据
  *@return		bool
  *@Author		Qian Hong Lee
  *@time		2012-4-1
  */
function spliceArray(arr,element){
	var n=isArray(arr)&&typeof arr!='string'?arr:arr.split(',');
	if(n.length>0){
		var len=n.length;
		for (var i=0;i<len;i++)
		{
		    if(n[i]==element){
				n.splice(i,1);
			}
		}
	}
	return n;
}
 /**
  *@desc		过滤数组中重复的元素
  *@param		要操作的数据
  *@return		bool
  *@Author		Qian Hong Lee
  *@time		2012-4-1
  */
function array_unique(arr){
	//var temp=arr.slice(0);
	var temp=arr.concat();
	for (var i=0;i<temp.length ;i++ )
	{
		for (var j=i+1;j<temp.length ;)
		{
			if(temp[j]==temp[i]){//后面的元素若和待比较的相同，则删除并计数；   
								 //删除后，后面的元素会自动提前，所以指针j不移动  
				temp.splice(j,1);
			}else{//不同，则指针移动  
				j++;
			}
		}	
	}
	return temp;
}
 /**
  *@desc		判断数组中是否包含某个元素
  *@param		要操作的数据
  *@return		bool
  *@Author		Qian Hong Lee
  *@time		2012-4-1
  */
function in_array(arr,obj) {
    var i = arr.length;
    while (i--) {
        if (arr[i] === obj) {
            return true;break;
        }
    }
    return false;
}
function replace(str) 
{ 
	//re=new RegExp("l","g")中的第一个参数是你要替换的字符串，第二个参数指替换所有的，其中，第二参数也可以设置为("i"),表示只替换第一个字符串。 
    //  str.replace(re,"t")中第二个参数你要修改的字符串
   var str=str;
   re  = new RegExp(",","g"); 
   re2 = new RegExp("&","g");
   var newstr=str.replace(re,"/[()]/"); 
	   newstr=newstr.replace(re2,"/[)(]/");
   return newstr; 
} 
function backreplace(str) 
{ 
   var str=str;
   //第1种表达式
   ree=/\/\[\(\)\]\//g; 
   ree2=/\/\[\)\(\]\//g;
   ree3=new RegExp("(\n|\r)","g");
   var  newstr=str.replace(ree,","); 
		newstr=newstr.replace(ree2,"&"); 
		newstr=newstr.replace(ree3,"<br>");
   return newstr; 
}
function isWebSite(str){
	///http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/
	var reg=/^(https?:\/\/)?([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
	var re = new RegExp( reg );
	if( re.test( str )==true){
		return true;
	}else{
		return false;
	}
}
/**
* 检查是否为网址
*/
function isURL(str_url) { // 验证url
	var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
	+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
	+ "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
	+ "|" // 允许IP和DOMAIN（域名）
	+ "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
	+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
	+ "[a-z]{2,6})" // first level domain- .com or .museum
	+ "(:[0-9]{1,4})?" // 端口- :80
	+ "((/?)|" // a slash isn't required if there is no file name
	+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
	var re=new RegExp(strRegex);
	//re.test()
	if (re.test(str_url)){
		return true;
	}else{
		return false;
	}
} 
 /**
  *@desc        匹配QQ号
  *@param		要操作的数据（一般是str）
  *@return		返回一个编码的 URI
  *@Author		Qian Hong Lee
  *@time		2012-4-1
  */
function isQQ(str){
	if(!(/^[1-9]\d{4,11}$/.test(str))){
		return false;
	}else{
		return true;
	}
} 
 /**
  *@joinParams  匹配手机号
  *@param		要操作的数据（一般是str）
  *@return		返回一个编码的 URI
  *@Author		Qian Hong Lee
  *@time		2012-4-1
  */
function isMobile(str){
	//var regu = "^(1[3,5,8,7]{1}[\d]{9})|(((400)-(\d{3})-(\d{4}))|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{3,7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$";
	//var regu = "/^1[3|5|8|7][\d]{9}$/";
	//var re = new RegExp( regu );
	if(!(/^(1[3|5|8|7][\d]{9}$)|(((400)-(\d{3})-(\d{4}))|((\d{3,4})[-]?(\d{7,8}))$)$/i.test(str))){
		return false;
	}else{
		return true;
	}
} 
 /**
  *@joinParams  固定的电话
  *@param		要操作的数据（一般是str）
  *@return		返回一个编码的 URI
  *@Author		Qian Hong Lee
  *@time		2012-4-1
  */
function isPhone(str){
	//var regu = "\d{3}-\d{8}|\d{4}-\d{7}";
	//var re = new RegExp( regu );
	if(!(/^((\d{3}-\d{8})|(\d{4}-\d{7}))$/.test(str))){
		return false;
	}else{
		return true;
	}
}
  
 /**
  *@joinParams  判断Email
  *@param		要操作的数据（一般是str）
  *@return		返回一个编码的 URI
  *@Author		Qian Hong Lee
  *@time		2012-4-1
  */
function isEmail(str){
	var regu = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|com|gov|mil|org|edu|int|name|asia|cn)$";
	var re = new RegExp( regu );
	if( str.search( re ) == -1 ){
		return false;
	}else{
		return true;
	}
}
 /**
  *@joinParams  用于在数据传输时将文本字符串编码为一个有效的统一资源标识符 (URI),防止在IE下出现乱码
  *@param		要操作的数据（一般是str）
  *@return		返回一个编码的 URI
  *@Author		Qian Hong Lee
  *@time		2012-4-1
  */
function joinParams(params){
	return encodeURI(params.join('&'));
}
 /**
  *@trim        去除首尾空格
  *@param		要操作的数据（一般是string）
  *@return		去除空格后的字符串
  *@Author		Qian Hong Lee
  *@time		2012-4-1
  */
function trim(str){
	if (str != ''){
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}else{
		return '';
	}
}
 /**
  *@trim        去除全部空格
  *@param		要操作的数据（一般是string）
  *@return		去除空格后的字符串
  *@Author		Qian Hong Lee
  *@time		2012-4-1
  */
function allTrim(str){
	var new_str='';
	if(str != ''){
		var len = str.length;
		for (var i=0;i<len ;i++ ){
			if (str.charAt(i) != ' ')
			{
				new_str+=str.charAt(i);
			}
		}
		return new_str;
	}else{
		return '您传入的字符串是空字符串!请核查！';
	}
}
 /**
  *@trim        进行当前页面的跳转
  *@param		none
  *@return		none
  *@Author		Qian Hong Lee
  *@time		2012-5-25
  */
function jump(url){
	if(url!=''){window.location.href=url;}
	else window.location.href=window.location.href;
}
//解决IE下不支持select的option的disabled属性
window.onload=function(){
	var selects = document.getElementsByTagName('select');
	for(var i = 0; i < selects.length; i++)
	{
		for(var j = 0; j < selects[i].options.length; j++)
		{
			var option = selects[i].options[j];
			if ( option.disabled )
			{
				var optgroup = document.createElement('optgroup');
				optgroup.label = option.innerHTML;
				optgroup.style.color = '#999';
				option.parentNode.insertBefore(optgroup,option);
				option.parentNode.removeChild(option);
			}
		}
	}
}
 /**
  *@trim        验证是否是有效的身份证
  *@param		id
  *@return		no:无效，yes：有效
  *@Author		Qian Hong Lee
  *@time		2012-5-25
  */
function check_id(id){
	var iSum=0;
	var info="";
	var sId=id;
	var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"***",71:"台湾",81:"香港",82:"澳门",91:"国外"};
		if(!(/(^\d{17}(\d|x|X)$)|(^\d{15}$)/i.test(sId))){
		  return false;
		}
		sId=sId.replace(/x$/i,"a");
		//非法地区
		if(aCity[parseInt(sId.substr(0,2))]==null)
		{
			return false;
		}
		var sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));
		var d=new Date(sBirthday.replace(/-/g,"/"))
		//非法生日
		if(sBirthday!=(d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()))
		{
			return false;
		}
		/*
		//if(/(^\d{17}(\d|x|X)$)/i.test(sId)){
			for(var i=17;i>=0;i--){
				
				iSum+=(Math.pow(2,i)%11)*parseInt(sId.charAt(17-i),11);
				//alert('i='+i+'***='+iSum);
			}
			
		}else if(/^\d{15}$/i.test(sId)){
			for(var i=14;i>=0;i--){
				
				iSum+=(Math.pow(2,i)%11)*parseInt(sId.charAt(14-i),11);
				//alert('ii='+i+'-***-='+iSum);
			}
		}
		
		alert('iSum='+iSum);
		alert('iSum%11='+iSum%11);
		if(iSum%11!=1){
			return 'no4';
		}*/
		return true;
}
/**
 *---------------------------------------------------
 *@desc          动态创建form,改变传统的GET提交
 *---------------------------------------------------
 *@funcNmae		 createFormAndSubmit
 *---------------------------------------------------
 *@formName		 表单名称 unrequire
 *@formMethod    提交方式 unrequire
 *@formAction    提交地址 require
 *@elementArr    提交元素 unrequire
 *---------------------------------------------------
 *@Auth    Qian Hong Lee
 *---------------------------------------------------
 *@time    2012-6-20
 *---------------------------------------------------
 */
 /*----------------start--------------------------------*/
function getNewSubmitForm(formName,formMethod){
	var submitForm = document.createElement("FORM");
	document.body.appendChild(submitForm);
	if(formMethod==''){
		submitForm.method = 'post';
	}else{
		submitForm.method = formMethod;
	}
	if(formName!=''){
		submitForm.id = formName;
		submitForm.name = formName;
	}
	return submitForm;
}
function createNewFormElement(inputForm, elementArr){
	if(elementArr.length>0){
		for (var k in elementArr){
			//alert(k+'=>'+elementArr[k]);
			var newElement = document.createElement("input");
			newElement.name= k;
			newElement.type= "hidden";
			newElement.value =elementArr[k];
			inputForm.appendChild(newElement);
		}
		return newElement;
	}else return '';
}
function createFormAndSubmit(formName,formMethod,formAction,elementArr,TargetMode){
	var submitForm = getNewSubmitForm(formName,formMethod);
	if(elementArr!=''){
		createNewFormElement(submitForm, elementArr);
	}
	submitForm.action= formAction;
	if(typeof TargetMode !='undefined'&&TargetMode!=''){
		submitForm.target=TargetMode;
	}
	submitForm.submit();
}
/*----------------end--------------------------------*/

//该方法仅供在个人中心首页的系统通知、人事通知、申请职位、收藏职位的链接用
function SELECT_INFO(type){
  if(type!=''){
	  var POST_ARR=	new Array('USERID');
	  if(type=='email'){
		createFormAndSubmit(type+'form','post','index.php?/user/index/conf',new Array());
	  }else if(type=='index'){
		createFormAndSubmit(type+'form','post','index.php?/user/index',new Array());
	  }else{
		createFormAndSubmit(type+'form','post','index.php?/user/user_info_manage/user_'+type+'_manage',POST_ARR);
	  }
  }else window.location.href=window.location.href;
}
//js对cookie的操作
function getCookie(objName){//获取指定名称的cookie的值
	if(objName!=''){
		var arrStr = document.cookie.split("; ");
		for(var i = 0;i < arrStr.length;i ++){
			var temp = arrStr[i].split("=");
			if(temp[0] != objName){
				continue;
			}else{
				return unescape(temp[1]);
			}
		}
	}
}
function addCookie(domain,objName,objValue,objHours){      //添加cookie
	var str = objName + "=" + escape(objValue); 
	if(objHours > 0){                               //设定过期时间，浏览器关闭时cookie自动消失 
		var date = new Date();
		date.setTime(date.getTime() + objHours*3600*1000); 
		str += "; expires=" + date.toGMTString();
   } 
   str += "; path=/; domain="+domain; //根下访问
   document.cookie = str;
}
function delCookie(name){//删除cookie 
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval=getCookie(name);
	if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}
// If Push and pop is not implemented by the browser
if (!Array.prototype.push) {
	Array.prototype.push = function array_push() {
		for(var i=0;i<arguments.length;i++)
			this[this.length]=arguments[i];
		return this.length;
	}
};
if (!Array.prototype.pop) {
	Array.prototype.pop = function array_pop() {
		lastElement = this[this.length-1];
		this.length = Math.max(this.length-1,0);
		return lastElement;
	}
};
//消息中心
function delMsg(umid, obj){
	var msg = "";
	if(!umid){
		$.dialog.alert('<font color="grey">信息错误，不能删除</font>');
	}else{
		msg = (obj == 'all') ? '确认删除所有消息' : '确认删除此条信息';
		var data = (obj == 'all') ? {cate:umid} : {umid:umid};
		$.dialog.confirm(msg, function(){
			$.ajax({
				url: base_url + "user/del_msg",
				data: data,
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
						if(obj == "all"){
							$(".list_content").remove();
							$("#pagenav").remove();
							$(".userdata").append('<div class="list_content" style="text-align:center">您暂时还没有收到消息提醒</div>');
						}else{
							$(obj).parents(".list_content").remove()
						}
					}
				}
			});					
		})
	}
}