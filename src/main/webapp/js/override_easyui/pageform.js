$.extend($.fn.form.defaults, {
    novalidate: true,
    onSubmit: function(){
        var isValid = $(this).form("validate");
        if(!isValid) {
            return false;
        }
        $.messager.progress({text: '正在处理中...', interval: 500});
    },
    success : function(data) {
        var data1 = $.parseJSON(data);
        if(checkPermission(data1)) {
            $.messager.progress({text: '操作成功, 窗口在2s后消失...', interval: 500});
            setTimeout(function() {
                    colseTab(true);
                },
                2000);
        }else {
            $.messager.progress('close');
        }
    }
});