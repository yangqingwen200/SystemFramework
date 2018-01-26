/**
 * Array 原型扩展方法: removeValue
 * @param val 要删除的值
 */
Array.prototype.removeValue = function(val) {
    for(var i=0; i<this.length; i++) {
        if(this[i] == val) {
            this.splice(i, 1);
            break;
        }
    }
};

//用来保存选中的id
var idsArray = new Array();

/**
 * 点击分页栏指定页码调用的方法
 */
$("#page_forward").on("click", "a[id^='page_forward_']", function () {
    $("#page_now").val($(this).attr("value"));
    $("#data_search_form").submit();
});

/**
 * 改变每页的大小
 */
$("#page_forward_setpagesize").on("change", function () {
    $("#page_size").val($(this).val());
    $("#data_search_form").submit();
});

/**
 * 输入指定页码调用的方法
 */
$("#page_forward_go").on("click", function () {
    var gopage = $("#page_forward_go_page").val();
    if(isNaN(gopage) || gopage <= 0) {
        gopage = 1;
    }
    var topage = $(this).attr("value");
    $("#page_now").val(Number(gopage) > Number(topage) ? topage : gopage);
    $("#data_search_form").submit();
});

/**
 * JSP页面点击表头, 分页排序用, 与自定义标签OrderTag配合使用.
 */
$("th").on('click', "span[id^='span_order_']", function () {
    $("#page_order_attr").val($(this).attr("column"));
    $("#page_order_type").val($(this).attr("ordertype"));
    $("#data_search_form").submit();
});

/**
 * 勾选表头的复选框
 */
$("#all_choose_checkbox").on('click', function () {
    //清空装有id的数组, 避免用户先勾选全部选中, 然后再取消勾选每行的复选框, 最后又点击勾选全部选中.
    idsArray.splice(0, idsArray.length);

    //attr获取checked是undefined, attr是获取自定义属性的值
    //prop修改和读取dom原生属性的值
    var status = $(this).prop("checked");
    $(".checkbox").prop("checked", status);
    if(status) {
        $.each($(".checkbox"), function (i, n) {
            idsArray.push($(this).val());
        });
    }
});

/**
 * 单击行, 改变行颜色
 */
$("tbody").on('click', "tr", function () {
    $(this).siblings().removeClass("warning");
    $(this).addClass("warning");
});

/**
 * 单个勾选复选框
 */
$("td").on('click', "input[type='checkbox']",function () {
    //判断选中的元素 和 .checkbox总数是否相等
    var status = $(".checkbox:checked").length == $(".checkbox").length;
    $("#all_choose_checkbox").prop("checked", status);

    var isChecked = $(this).prop("checked");
    if(isChecked) {
        idsArray.push($(this).val());
    } else {
        idsArray.removeValue($(this).val());
    }
});

/**
 * 同步提交请求信息
 *
 * @param url 请求地址
 * @param data 请求提交的数据
 * @returns {*}
 */
function ajaxRequestPost(url, data) {
	var result;
    $.ajax({
        url: url,
		data: data,
        dataType: "json",
		type: "post",
        async: false,
        cache: false,
        success: function(data) {
            result = data;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            result = {message: false, errorMsg: '请求出错.'};
        }
    });
    return result;
}

/**
 * 退出系统
 * @param href 退出之后需要跳转的地址
 */
function logoutSystem(href) {
    window.location.href = href;
}

/**
 * 隐藏确认删除模态框时, 去掉上次操作错误信息
 */
$('#data_delete_dialog').on('hidden.bs.modal', function (e) {
    $("#data_delete_error_message").css('display', 'none');
    //设置成上次操作出现错误, 用户再也无法点击确认按钮(除非刷新页面), 加了禁用的样式.
    //$("#data_delete_dialog_ok_button").removeAttr("disabled");
});

/**
 * 隐藏新增/编辑模态框时, 还原初始化信息
 */
$('#data_add_or_edit_dialog').on('hidden.bs.modal', function (e) {
    $("#data_add_or_edit_dialog_error_message").css('display', 'none');
    $("#data_add_or_edit_dialog_load_remote").html("");
    $("#data_add_or_edit_dialog_title").html("");
    $("#data_add_or_edit_dialog_modal").css("width", "50%");
    //设置成上次操作出现错误, 用户再也无法点击确认按钮(除非刷新页面), 加了禁用的样式.
    //$("#data_add_dialog_ok_button").removeAttr("disabled");
});

/**
 * 隐藏模态框时, 还原初始化信息
 */
$('#data_common_dialog').on('hidden.bs.modal', function (e) {
    $("#data_common_dialog_modal").css("width", "32%");
    $("#data_common_dialog_load_romte").html("");
});

/**
 * 点击删除按钮
 */
$("td a[id^='data_del_opts_']").on("click", function () {
    var delUrl = $(this).attr("url");
    clearCheckboxAndIds();
    var width = $(this).attr("dialogwidth"); //改变弹出编辑框的大小: 60px or 60%
    if(width) {
        $("#data_delete_dialog_modal").css("width", width);
    }
    var urldata = $(this).attr("urldata"); //要删除数据的主键id
    $("#primary_id").val(urldata);
    $("#delete_url").val(delUrl);
    $("#data_delete_dialog_load_remote").html("您确认要删除选中的信息吗? <mark>且删除后无法恢复, 请谨慎操作.</mark>");
    $("#data_delete_dialog").modal(); //显示对话框
});

/**
 * 点击批量删除按钮
 */
$("#data_batdel_opts").on("click", function () {
    var delUrl = $(this).attr("url");
    var width = $(this).attr("dialogwidth"); //改变弹出编辑框的大小: 60px or 60%
    if(idsArray.length <= 0) {
        if(width) {
            $("#data_common_dialog_modal").css("width", width);
        }
        $("#data_common_dialog_title").html("提示");
        $("#data_common_dialog_load_romte").html("<h5>您还没勾选数据, 请<mark>至少勾选一行</mark>数据.</h5>");
        //弹出未选择任何数据的modal
        $("#data_common_dialog").modal();
        return;
    }
    if(width) {
        $("#data_delete_dialog_modal").css("width", width);
    }
    $("#primary_id").val(idsArray.concat(","));
    $("#delete_url").val(delUrl);
    $("#data_delete_dialog_load_remote").html("您确认要批量删除选中的信息吗? <mark>且删除后无法恢复, 请谨慎操作.</mark>");

    $("#data_delete_dialog").modal(); //显示对话框
});

/**
 * 点击确认删除按钮
 */
$("#data_delete_dialog_ok_button").on("click", function () {
    var pageNow = $(this).attr("pagenow");
    var pageSize = $(this).attr("pagesize");
    var totalNum = $(this).attr("totalnum");
    var lastPage = $(this).attr("lastpage");

    $("#data_delete_dialog_ok_button").attr("disabled", "disabled"); //添加禁用样式,无法点击
    var result = ajaxRequestPost($("#delete_url").val(), {id: $("#primary_id").val()});
    if(result.message) {
        $("#data_delete_dialog").modal('hide'); //隐藏当前dialog(也可以不用隐藏, form提交后自动刷新页面)
        var arrLen = idsArray.length; //得到数据长度
        if((pageNow == lastPage && totalNum % pageSize == 1) || (arrLen != 0 && arrLen == pageSize)
            || (arrLen != 0 && totalNum % pageSize == arrLen)) {
            pageNow = pageNow - 1; //当前位置在最后一页 && 当前页只有一条数据, 删除这条数据应该跳到上一页
            if(pageNow <= 0) {
                pageNow = 1;
            }
        }
        $("#page_now").val(pageNow);
        $("#data_search_form").submit();  //重新刷新当前页面
    } else {
        $("#data_delete_error_message").css("display", "");
        $("#data_delete_error_message").html(result.errorMsg);  //显示错误信息
    }
});

/**
 * 新增/编辑对话框
 */
$("#data_add_opts,td a[id^='data_edit_opts_']").on("click", function () {
    var addUrl = $(this).attr("url");
    var data = $(this).attr("urldata");  //url传递参数, 格式: {id:123}
    var width = $(this).attr("dialogwidth"); //改变弹出编辑框的大小: 60px or 60%
    try {
        if(data) {
            data = eval("("+data+")");
        }
    } catch (e) {
        alert("JS eval解析: " + data + "出现错误了...");
        return;
    }
    clearCheckboxAndIds();
    $("#data_add_or_edit_dialog_title").html("填写信息");
    if(width) {
        $("#data_add_or_edit_dialog_modal").css("width", width);
    }
    //根据id, 远程加载数据
    //load方法有三个参数, 如果不传第二个参数(data), 就为get请求, 否则为post请求
    $("#data_add_or_edit_dialog_load_remote").load(createURL(addUrl, data), function(response, status, xhr) {
        if(status == 'success') {
            $("#data_add_or_edit_dialog").modal(); //显示对话框
        } else {
            alert("请求数据发生异常...");
            return;
        }
    });
});

/**
 * 点击新增/编辑对话框 上的保存按钮
 */
$("#data_add_or_edit_dialog_ok_button").on("click", function () {
    var pageNow = $(this).attr("pagenow"); //当前页
    if($("#data_add_or_edit_form").valid()) { //校验通过后才提交表单
        //如果防止用户重复提交, 最好是后台用token来校验, 每次请求生成一个随机token, 放在redis或者session中, 在拦截器中校验token. ~_~
        $("#data_add_or_edit_dialog_ok_button").attr("disabled", "disabled"); //添加禁用样式,无法点击
        var updateId = $("#data_add_or_edit_form input[type=hidden][name=id]").val();  //得到更改数据主键id
        $("#data_add_or_edit_form").ajaxSubmit({
            type: "post",
            success: function (responseText, statusText) {
                var result = $.parseJSON(responseText);
                if(statusText == "success" && result.message) {
                    $("#data_add_or_edit_dialog").modal('hide'); //服务器返回成功信息, 隐藏当前dialog(也可以不用隐藏, form提交后自动刷新页面)
                    $("#page_now").val(pageNow);
                    $("#update_id").val(updateId); //设置更改数据的id, bootstrap增加样式, 友好显示那条数据被更改
                    $("#data_search_form").submit(); //重新刷新当前页面
                } else {
                    $("#data_add_or_edit_dialog_error_message").css("display", "");
                    $("#data_add_or_edit_dialog_error_message").html(result.errorMsg);  //显示错误信息
                }
            }
        });
    }
});

/**
 * 弹出详情对话框
 */
$("td").on("click", "span[id^='data_detail_opts_']", function () {
    var detailUrl = $(this).attr("url");
    var data = $(this).attr("urldata");  //url传递参数, 格式: {id:123}
    var width = $(this).attr("dialogwidth"); //改变弹出编辑框的大小: 60px or 60%
    try {
        if(data) {
            data = eval("("+data+")");
        }
    } catch (e) {
        alert("JS eval解析: " + data + "出现错误了...");
        return;
    }
    clearCheckboxAndIds();
    if(width) {
        $("#data_common_dialog_modal").css("width", width);
    }
    $("#data_common_dialog_title").html("浏览详情");
    //根据id, 远程加载数据
    //load方法有三个参数, 如果不传第二个参数(data), 就为get请求, 否则为post请求
    $("#data_common_dialog_load_romte").load(createURL(detailUrl, data), function(response, status, xhr) {
        if(status == 'success') {
            $("#data_common_dialog").modal(); //显示对话框
        } else {
            alert("请求数据发生异常...");
            return;
        }
    });
});


/**
 * 清除选中的复选框和装有id的数组
 * 这种情况是勾选了复选框, 但是点击的是行内编辑 or 删除按钮
 */
function clearCheckboxAndIds() {
    $("#all_choose_checkbox").prop("checked", false); //去掉全选复选框的勾
    $("td .checkbox").prop("checked", false); //去掉单个复选框的勾
    idsArray.splice(0, idsArray.length); //清空装有id的数组
}

/**
 * 拼接URL
 * @param url
 * @param param
 * @returns 拼接之后URL
 */
function createURL(url, param){
    url = url += "?";
    for(var name in param){
        url += (name + "=" + param[name] + "&");
    }
    return url.substr(0, url.length - 1);
}

/**
 * 弹出编辑对话框
 */
/*$("td").on("click", "a[id^='data_edit_opts_']", function () {
 var editUrl = $(this).attr("url");
 var data = $(this).attr("urldata");  //url传递参数, 格式: {id:123}
 var width = $(this).attr("dialogwidth"); //改变弹出编辑框的大小: 60px or 60%
 try {
 if(data) {
 data = eval("("+data+")");
 }
 } catch (e) {
 alert("JS eval解析: " + data + "出现错误了...");
 return;
 }
 clearCheckboxAndIds();
 if(width) {
 $("#data_edit_dialog_modal").css("width", width);
 }
 //根据id, 远程加载数据
 //load方法有三个参数, 如果不传第二个参数(data), 就为get请求, 否则为post请求
 $("#data_edit_load_romte").load(createURL(editUrl, data), function(response, status, xhr) {
 if(status == 'success') {
 $("#data_edit_dialog").modal(); //显示对话框
 } else {
 alert("请求数据发生异常...");
 return;
 }
 });
 });*/

/**
 * 编辑界面 点击确认按钮
 */
/*$("#data_edit_dialog_ok_button").on("click", function () {
 var pageNow = $(this).attr("pagenow");
 if($("#data_update_form").valid()) { //校验通过后才提交表单
 //如果防止用户重复提交, 最好是后台用token来校验, 每次请求生成一个随机token, 放在redis缓存中, 在拦截器中校验token. ~_~
 $("#data_edit_dialog_ok_button").attr("disabled", "disabled"); //添加禁用样式,无法点击
 var updateId = $("#data_update_form input[type=hidden][name=id]").val();  //得到更改数据主键id
 $("#data_update_form").ajaxSubmit({
 type: "post",
 success: function (responseText, statusText) {
 var result = $.parseJSON(responseText);
 if(statusText == "success" && result.message) {
 $("#data_edit_dialog").modal('hide'); //服务器返回成功信息, 隐藏当前dialog(也可以不用隐藏, form提交后自动刷新页面)
 $("#page_now").val(pageNow);
 $("#update_id").val(updateId); //设置更改数据的id, bootstrap增加样式, 友好显示那条数据被更改
 $("#data_search_form").submit(); //重新刷新当前页面
 } else {
 $("#data_edit_error_message").css("display", "");
 $("#data_edit_error_message").html(result.errorMsg);  //显示错误信息
 }
 }
 });
 }
 });*/