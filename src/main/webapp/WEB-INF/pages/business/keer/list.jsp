<%@ page language="java" contentType="text/html; charset=utf-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
    <link href="${ctx}/js/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
</head>
<body>
<%@include file="/WEB-INF/pages/common/navbar.jsp"%>
<div class="container-fluid">
    <div class="panel panel-default">
        <div class="panel-heading" style="font-weight: bold; font-size: 14px;padding: 0px">
            <%@include file="/WEB-INF/pages/business/bnsnavbar.jsp"%>
        </div>

        <div class="panel-body" style="padding-bottom: 5px;">
            <div id="#toolbar">
            </div>
            <table id="table"></table>
        </div>

    </div>
</div>

<%@include file="/WEB-INF/pages/common/script.jsp"%>
<script type="text/javascript" src="${ctx}/js/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript">
    $("#table").bootstrapTable({
        url: "${ctx}/business/list_keer.html",
        columns: [{
            field: 'id',
            title: 'id'

        }, {
            field: 'name',
            title: '名称'

        }, {
            field: 'link_tel',
            title: '联系电话'

        }, {
            field: 'address',
            title: '地址'

        }, {
            field: 'is_examroom',
            title: '考场'

        }, {
            field: 'status',
            title: '状态',
            formatter: function(value, row, index) {
                if(value == 1) {
                    return "审核";
                }
                if(value == 2) {
                    return "屏蔽";
                }
                if(value == 3) {
                    return "正常"
                }
            }
        }, {
            field: 'opt',
            title: '操作',
            formatter: function(value, row, index) {
                var edit = '<a class="btn btn-primary btn-xs" ><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑</a>&nbsp;';
                var del = '<a class="btn btn-danger btn-xs" ><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> 删除</a>';
                return edit + del;
            }
        }],
        method: 'post',
        contentType: 'application/x-www-form-urlencoded',
        sortName: "id",
        sortOrder: "desc",
        //striped: true,
        toolbar: "#toolbar",  //一个jQuery 选择器，指明自定义的toolbar（工具栏），将需要的功能放置在表格工具栏（默认）位置。
        sidePagination: "server",  //设置在哪里进行分页，可选值为 'client' 或者 'server'。设置 'server'时，必须设置 服务器数据地址（url）或者重写ajax方法
        pageNumber: 1,    //如果设置了分页，首页页码
        pageSize: 15,   //如果设置了分页，页面数据条数
        pageList: [
            10, 15, 20, 30, 40   //如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录。
        ],
        pagination: true,  //设置为 true 会在表格底部显示分页条
        //showRefresh: true, //显示 刷新按钮
        showColumns: true, //是否显示 内容列下拉框
       // searchOnEnterKey: true,  //设置为 true时，按回车触发搜索方法，否则自动触发搜索方法
       // search: true   //是否启用搜索框
    });
</script>
</body>
</html>
