// kindeditor编辑器调用
$(function(){
    KindEditor.ready(function(K) {
        var intro = K.create('textarea[name="intro"]', {
            items:['bold', 'italic', 'underline', 'strikethrough', '|', 'justifyleft', 'justifycenter', 'justifyright', , 'insertorderedlist', 'insertunorderedlist', '|', 'undo', 'redo' , '|' , 'plainpaste'],
            afterBlur: function(){this.sync();},
            pasteType : 0
        });
    });
});