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
            <form id="data_search_form" class="form-inline" role="form" action="${ctx}/business/onestop.html" method="post">
                <div class="form-group">
                    <label for="inputName">驾校名称:</label>
                    <input type="text" class="form-control" id="inputName" name="name" value="${dto.name}" placeholder="Jane Doe">
                </div>
                <div class="form-group">
                    <label for="inputLinkTel">联系电话:</label>
                    <input type="text" class="form-control" id="inputLinkTel" name="linkTel" value="${dto.linkTel}" placeholder="18888888888">
                </div>

                <div class="form-group">
                    <label for="inputStatus">状态: </label>
                    <select id="inputStatus" class="form-control" name="status">
                        <option value="">全部</option>
                        <option value="1" <c:if test="${dto.status eq 1}">selected="selected"</c:if>>审核</option>
                        <option value="2" <c:if test="${dto.status eq 2}">selected="selected"</c:if>>屏蔽</option>
                        <option value="3" <c:if test="${dto.status eq 3}">selected="selected"</c:if>>正常</option>
                    </select>
                </div>

                <div class="form-group" style="float: right">
                    <div class="btn-group" role="group">
                        <button type="submit" class="btn btn-primary">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 搜索
                        </button>
                        <button type="button" class="btn btn-primary" disabled="disabled">
                            <span class="glyphicon glyphicon-refresh" aria-hidden="true"></span> 重置
                        </button>
                        <button type="button" class="btn btn-primary" disabled="disabled">
                            <span class="glyphicon glyphicon-save-file" aria-hidden="true"></span> 导出
                        </button>
                    </div>
                    <div class="btn-group" role="group">
                        <a <mytag:show value="detailUser">id="data_add_opts" class="btn btn-primary" url="${ctx}/business/add_onestop.html" dialogwidth="50%"</mytag:show> class="btn btn-primary disabled">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 新增
                        </a>
                        <a <mytag:show value="detailUser">id="data_batdel_opts" class="btn btn-danger" url="${ctx}/business/delete_onestop.html" dialogwidth="40%"</mytag:show> class="btn btn-danger disabled">
                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span> 批量删除
                        </a>
                    </div>
                </div>

                <%-- 分页需要的隐藏数据 --%>
                <%@include file="/WEB-INF/pages/common/pagehidden.jsp" %>
            </form>
            <br>

            <div class="table-responsive">
                <table class="table table-hover table-bordered">
                    <thead>
                        <tr>
                            <th width="2%"><input type="checkbox" id="all_choose_checkbox"></th>
                            <th width="3%">序号</th>
                            <th width="13%">驾校名称 <mytag:order col="name"/></th>
                            <th width="12%">联系人电话 <mytag:order col="link_tel"/></th>
                            <th width="10%">城市</th>
                            <th width="7%">好评率 <mytag:order col="star_num"/></th>
                            <th width="7%">有无考场</th>
                            <th width="5%">状态</th>
                            <th width="30%">地址</th>
                            <th width="8%">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${page.content.size() gt 0}">
                                <c:forEach items="${page.content}" var="content" varStatus="i">
                                    <tr <c:if test="${content.id eq dto.updateId}">class="warning"</c:if>">
                                        <td><input type="checkbox" class="checkbox" value="${content.id}"/></td>
                                        <td>${(page.pageNow - 1) * page.pageSize + i.index + 1}</td>
                                        <td>
                                            <span <mytag:show value="detailUser">id="data_detail_opts_${i.index + 1}" url="${ctx}/business/detail_onestop.html" urldata="{id: ${content.id}}" dialogwidth="70%" style="cursor: pointer;color: #337ab7;text-decoration: underline"</mytag:show>>
                                                ${content.name}
                                            </span>
                                        </td>
                                        <td>${content.link_tel}</td>
                                        <td>${content.city_name}</td>
                                        <td>${content.star_num}</td>
                                        <td style="vertical-align: middle">
                                            <c:if test="${content.status eq 1}"><span class="label label-warning">审核</span></c:if>
                                            <c:if test="${content.status eq 2}"><span class="label label-danger">屏蔽</span></c:if>
                                            <c:if test="${content.status eq 3}"><span class="label label-success">正常</span></c:if>
                                        </td>
                                        <td>${content.is_examroom}</td>
                                        <td><mytag:moreFmt value="${content.address}" subLength="20" fmtType="abbr"/></td>
                                        <td>
                                            <a <mytag:show value="detailUser">class="btn btn-primary btn-xs" url="${ctx}/business/edit_onestop.html" urldata="{id: ${content.id}}" dialogwidth="50%" id="data_edit_opts_${i.index + 1}"</mytag:show> class="btn btn-primary btn-xs disabled">
                                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑
                                            </a>
                                            <a <mytag:show value="detailUser">class="btn btn-danger btn-xs" url="${ctx}/business/delete_onestop.html" urldata="${content.id}" dialogwidth="40%" id="data_del_opts_${i.index + 1}"</mytag:show> class="btn btn-danger btn-xs disabled">
                                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span> 删除
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr class="danger">
                                    <td colspan="10" style="text-align: center;">没有查询到数据</td>
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