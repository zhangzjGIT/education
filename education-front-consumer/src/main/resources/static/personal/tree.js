(function() {
    //动态增加弹窗的标签
    var script = document.createElement('script'), heads = document.getElementsByTagName("head");
    script.setAttribute('type', "text/javascript");
    script.setAttribute('src', base_url + "public/js/lhgcore.lhgdialog.min.js");
    if (heads.length) {
        heads[0].appendChild(script);
    } else {
        document.documentElement.appendChild(script);
    }
})();

//课程分类
function listType(obj){
    var pid = obj.value;
    if (pid != 0) {
        $("#type_id").show();
        $.ajax({
            url: base_url + 'ajax/course_type/lists_course_type',
            data: {parentId: pid},
            dataType: 'json',
            type: 'post',
            cache: false,
            async: false,
            success: function(data) {
                if (data.data){
                    var html = '';
                    html += '<option value="">选择分类</option>';
                    if (data.data.length < 1 || data.data == null || data.data == '') {
                        $('#type_id').empty().html(html);
                    } else {
                        $.each(data.data, function(k, v) {
                            html += '<option value="' + v.type_id + '">' + v.name + '</option>';
                        });
                        $('#type_id').html( html );
                    }
                } else {
                    var html = '';
                    html += '<option value="">选择分类</option>';
                    if (data.data.length < 1 || data.data == null || data.data == '') {
                        $('#type_id').empty().html(html);
                    }
                }
            }
        });
    } else
        $("#type_id").hide();
        $(".coursetype_selected").remove();
}
/**
 * 课程标签
 */
function list_tag(obj){
    $(".coursetype_selected").remove();
    var type_id = $(obj).val();
    if(type_id != 0){
        $.post(base_url+"ajax/course_type/list_tag", {type_id:type_id}, function(data){
            data = eval("("+data+")");
            if(data.msg == "success"){
                if(data.data.length > 0)
                    var tag_str = '<div class="coursetype_selected"><div class="tabs_attr"><div class="tabs_key">课程属性：</div><div class="tabs_values tabs_tip">选择正确的课程属性有助于课程被搜索与展现</div></div>';
                for(var i = 0; i < data.data.length; i++){
                    tag_str += '<div class="tabs_attr"><div class="tabs_key">'+data.data[i]['parent_name']+'：</div><div class="tabs_values"><ul class="tabs_name">';
                    for(var j = 0; j < data.data[i].son.length; j++){
                        tag_str += '<li><input id="tag_'+i+j+'" name="tag_id[]" class="checkbox" type="checkbox" value="'+data.data[i].son[j].tag_id+'" /><label for="tag_'+i+j+'">'+data.data[i].son[j].alias_name+'</label></li>';
                    }
                    tag_str += "</ul></div></div>";
                }
                tag_str += "</div>";
                $(obj).closest(".tabs_attr").after($(tag_str));
                $(".coursetype_selected").show();
            }
        })
    }
}
/*获取字符串实际长度*/
function getStrLen(str) {
    var realLength = 0, len = str.length, charCode = -1;
    for (var i = 0; i < len; i++) {
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128)
            realLength += 1;
        else
            realLength += 2;
    }
    return realLength;
}

function checkexp(o){
    var value = "";
    if (!isNaN($(o).val())) {
        if (parseFloat($(o).val()) > 0) {
            if ($(o).val().indexOf(".") == -1)
                value = "" + $(o).val() + ".00";
            else {
                var arr = ("" + $(o).val()).split(".");
                arr[0] = "" + arr[0];
                arr[1] = "" + arr[1];
                var len = arr[1].length;
                if (len == 0)
                    value = "" + arr[0] + ".00";
                else if (len == 1)
                    value = "" + arr[0] + "." + arr[1] + "0";
                else if (len >= 2)
                    value = "" + arr[0] + "." + arr[1].substr(0, 2);
            }
            if (value.length > 10) {
                $.dialog.alert('<span color="grey">超出金额限制请与客服联系</span>');
                $(o).val($(obj).val().substr(0,7));
                return;
            }
            $(o).val(value);
            $("#act_price2").hide();
        } else{
            $("#act_price2").show();
        }
    } else{
        $(o).val("");
        o.focus();
        $("#act_price2").show();
    }
}

function checknum(obj){
    if ($(obj).val().length > 10) {
        var value=$(obj).val();
        $(obj).val(value.substr(0,7));
        $.dialog.alert('<span color="grey">超出金额限制请与客服联系</span>');
    }
}