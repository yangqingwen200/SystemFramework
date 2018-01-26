<%@ page language="java" contentType="text/html; charset=utf-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
</head>
<body>
<%@include file="/WEB-INF/pages/common/navbar.jsp"%>
<div class="container-fluid">
    <div class="panel panel-default">
        <div class="panel-heading" style="font-weight: bold; font-size: 14px;padding: 0px">
            <%@include file="/WEB-INF/pages/business/bnsnavbar.jsp"%>
        </div>

        <div class="panel-body" style="padding-bottom: 5px;">
            <form id="data_search_form" class="form-inline" role="form" action="${ctx}/business/time.html" method="post">
                <div class="form-group">
                    <label for="exampleInputName2">联系人:</label>
                    <input type="text" class="form-control" id="exampleInputName2" name="name" value="${dto.name}" placeholder="Jane Doe">
                </div>

                <div class="form-group">
                    <label for="exampleInputEmail2">Email:</label>
                    <input type="email" class="form-control" id="exampleInputEmail2" placeholder="jane.doe@example.com">
                </div>

                <div class="form-group">
                    <label for="exampleInputSex2">驾校名称: </label>
                    <select id="exampleInputSex2" class="form-control" name="schoolId">
                        <option value="">全部</option>
                        <c:forEach items="${schoolList}" var="sc">
                            <option value="${sc.id}"
                                    <c:if test="${sc.id eq dto.schoolId}">selected="selected"</c:if>>${sc.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group" style="float: right">
                    <div class="btn-group" role="group" >
                        <button type="submit" class="btn btn-primary">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 搜索
                        </button>
                        <button type="button" class="btn btn-primary">
                            <span class="glyphicon glyphicon-refresh" aria-hidden="true"></span> 重置
                        </button>
                        <button type="button" class="btn btn-primary">
                            <span class="glyphicon glyphicon-save-file" aria-hidden="true"></span> 导出

                        </button>
                    </div>
                </div>

                <%-- 分页需要的隐藏数据 --%>
                <%@include file="/WEB-INF/pages/common/pagehidden.jsp" %>
            </form>
            <br>

            <div class="table-responsive">
                <table class="table table-hover table-bordered table-responsive">
                    <thead>
                        <tr>
                            <th width="3%">序号</th>
                            <th width="10%">驾校名称</th>
                            <th width="10%">联系人</th>
                            <th width="10%">联系人电话</th>
                            <th width="10%">经度</th>
                            <th width="10%">纬度</th>
                            <th width="10%">注册时间</th>
                            <th width="20%">地址</th>
                            <th width="8%">操作</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:choose>
                            <c:when test="${page.content.size() gt 0}">
                                <c:forEach items="${page.content}" var="content" varStatus="i">
                                    <tr>
                                        <td>${i.index + 1}</td>
                                        <td><mytag:moreFmt value="${content.name}" subLength="6" fmtType="abbr"/></td>
                                        <td>${content.link_man}</td>
                                        <td>${content.link_tel}</td>
                                        <td>${content.lng}</td>
                                        <td>${content.lat}</td>
                                        <td>${content.create_date}</td>
                                        <td><mytag:moreFmt value="${content.address}" subLength="20" fmtType="abbr"/></td>
                                        <td>
                                            <a type="button" class="btn btn-primary btn-xs">
                                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑
                                            </a>
                                            <a class="btn btn-danger btn-xs">
                                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span> 删除
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr class="danger">
                                    <td colspan="9" style="text-align: center; font-weight: bold">没有查询到数据...</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

        </div>

        <div class="panel-footer" style="padding: 0px;">
            <%--分页栏--%>
            <%@include file="/WEB-INF/pages/common/pagenav.jsp"%>
        </div>
    </div>
</div>

<%-- 编辑和删除对话框 --%>
<%@include file="/WEB-INF/pages/common/addeditdel.jsp" %>
<%@include file="/WEB-INF/pages/common/script.jsp"%>
</body>
</html>
