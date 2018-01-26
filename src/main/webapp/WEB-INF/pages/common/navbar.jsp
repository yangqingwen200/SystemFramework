<%@ page language="java" contentType="text/html; charset=utf-8" %>

<nav class="navbar navbar-default navbar-static-top navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" style="cursor: default">Welcome</a>
        </div>

        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li <c:if test="${index eq 'index'}">class="active"</c:if>><a href="${ctx}">主页</a></li>
                <li <c:if test="${index eq 'business'}">class="active"</c:if>><a href="${ctx}/business/onestop.html">预约培训</a></li>
                <li class="disabled"><a href="#">预约培训</a></li>
                <li class="disabled"><a href="#">预约培训</a></li>
                <li class="disabled"><a href="#">预约培训</a></li>
                <li class="disabled"><a href="#">预约培训</a></li>
                <li class="disabled"><a href="#">预约培训</a></li>
                <li class="disabled"><a href="#">预约培训</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">更多 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li ><a href="#" data-toggle="modal" data-target="#logout">Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Modal -->
<div class="modal fade" id="logout" tabindex="-1" role="dialog" aria-labelledby="logoutModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="logoutModalLabel">
                    <span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span> 退出系统
                </h4>
            </div>
            <div class="modal-body">
                <label>您确定要退出系统吗?</label>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-warning" onclick="logoutSystem('${ctx}')">
                    <span class="glyphicon glyphicon-log-out"  aria-hidden="true"></span> Logout
                </button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Cancel
                </button>
            </div>
        </div>
    </div>
</div>
