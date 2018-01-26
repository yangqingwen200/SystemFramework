<%@ page language="java" contentType="text/html; charset=utf-8" %>
<div style="text-align: center">
    <nav id="page_forward">
        <ul class="pagination" style="margin: 10px">
            <c:if test="${page.pageNow eq 1}">
                <li class="disabled"><a>首页</a></li>
                <li class="disabled">
                    <a aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </c:if>
            <c:if test="${page.pageNow ne 1}">
                <li><a id="page_forward_first" style="cursor: pointer" value="1">首页</a></li>
                <li>
                    <a id="page_forward_previous" style="cursor: pointer" aria-label="Previous" value="${page.pageNow - 1}">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </c:if>

            <c:forEach items="${page.continuePage}" var="continuePage" varStatus="i">
                <li <c:if test="${page.pageNow eq continuePage}">class="active"</c:if>>
                    <a id="page_forward_${i.index + 1}" style="cursor: pointer" value="${continuePage}">
                        ${continuePage}
                    </a>
                </li>
            </c:forEach>

            <c:if test="${page.pageNow eq page.pageCount}">
                <li class="disabled">
                    <a aria-label="Previous">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                <li class="disabled"><a>尾页</a></li>
            </c:if>
            <c:if test="${page.pageNow ne page.pageCount}">
                <li>
                    <a id="page_forward_next" style="cursor: pointer" aria-label="Next" value="${page.pageNow + 1}">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                <li><a id="page_forward_end" style="cursor: pointer" value="${page.pageCount}">尾页</a></li>
            </c:if>
            <li style="float: left">
                <select id="page_forward_setpagesize" class="form-control" style="padding-right: 2px;color: #337ab7;border-radius: 0px;">
                    <option <c:if test="${page.pageSize eq 10}">selected="selected"</c:if> value="10">10</option>
                    <option <c:if test="${page.pageSize eq 15}">selected="selected"</c:if> value="15">15</option>
                    <option <c:if test="${page.pageSize eq 20}">selected="selected"</c:if> value="20">20</option>
                    <option <c:if test="${page.pageSize eq 25}">selected="selected"</c:if> value="25">25</option>
                    <option <c:if test="${page.pageSize eq 30}">selected="selected"</c:if> value="30">30</option>
                </select>
            </li>
            <li>
                <div class="btn-group" role="group" style="display: inline;">
                    <input id="page_forward_go_page" type="number" class="btn btn-default" style="width: 50px;cursor: text;border-radius: 0px;color: #337ab7;padding: 6px 0px 6px 0px" value="${page.pageNow}" />
                    <button id="page_forward_go" type="button" class="btn btn-primary" value="${page.pageCount}">Go</button>
                </div>
            </li>

            <li>
                <label style="margin-top: 12px;margin-left: 20px;">当前${page.pageNow}/${page.pageCount}页, 显示第${(page.pageNow - 1) * page.pageSize + 1}到<c:if test="${page.pageNow * page.pageSize >= page.totalNum}">${page.totalNum}</c:if><c:if test="${page.pageNow * page.pageSize < page.totalNum}">${page.pageNow * page.pageSize}</c:if>条记录, 共${page.totalNum}条记录</label>
            </li>
        </ul>
    </nav>
</div>
