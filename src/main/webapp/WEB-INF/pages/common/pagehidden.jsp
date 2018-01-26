<%@ page language="java" contentType="text/html; charset=utf-8" %>

<%-- 更改数据时用到, 用于保存哪条数据被更改, bootstrap增加样式, 友好显示哪条数据被更改 --%>
<input type="hidden" id="update_id" name="updateId"/>
<%-- 当前页/排序信息 --%>
<input type='hidden' id="page_now" name='page.pageNow' value="1"/>
<input type='hidden' id="page_size" name='page.pageSize' value="${page.pageSize}"/>
<input type='hidden' id="page_order_attr" name='page.orderAttr' value="${page.orderAttr}"/>
<input type='hidden' id="page_order_type" name='page.orderType' value="${page.orderType}"/>
