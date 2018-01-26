<%@ page language="java" contentType="text/html; charset=utf-8" %>

<ul class="nav nav-tabs">
    <li role="presentation" <c:if test="${business eq 'onestop'}">class="active"</c:if>>
        <a href="${ctx}/business/onestop.html">
            一站式拿证
        </a>
    </li>
    <li role="presentation" <c:if test="${business eq 'time'}">class="active"</c:if>>
        <a href="${ctx}/business/time.html">
            计时学车
        </a>
    </li>
    <li role="presentation" <c:if test="${business eq 'keer'}">class="active"</c:if>>
        <a href="${ctx}/business/keer.html">
            科目二套餐
         </a>
    </li>
    <li role="presentation" class="disabled">
        <a >
            科目三套餐
        </a>
    </li>
    <li role="presentation" class="disabled">
        <a >
            报名
        </a>
    </li>
    <li role="presentation" class="disabled">
        <a >
            有证练车
        </a>
    </li>
    <li role="presentation" class="disabled">
        <a href="#" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            更多菜单
            <span class="caret"></span>
        </a>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
            <li class="disabled"><a style="font-weight: bold">没有菜单</a></li>
            <li class="disabled"><a style="font-weight: bold">并没有菜单</a></li>
            <li role="separator" class="divider"></li>
            <li class="disabled"><a style="font-weight: bold">其实并没有菜单</a></li>
        </ul>
    </li>
</ul>