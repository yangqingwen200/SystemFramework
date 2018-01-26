<%@ page language="java" contentType="text/html; charset=utf-8" %>

<%-- 提示框/浏览详情公共的模态框, 仅仅是前台展示/提示, 没有与后台进行交互 --%>
<div class="modal fade" id="data_common_dialog" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" id="data_common_dialog_modal">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">
                    <span class="glyphicon glyphicon-flash" aria-hidden="true"></span> <span id="data_common_dialog_title">提示</span>
                </h4>
            </div>

            <div class="modal-body">
                <div id="data_common_dialog_load_romte">
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-info" data-dismiss="modal">
                    <span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 知道了
                </button>
            </div>
        </div>
    </div>
</div>

<%-- 删除信息确认模态框, 可以与不需要填写任何信息业务公用, 如: 禁用/下架等  --%>
<div class="modal fade" id="data_delete_dialog" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" id="data_delete_dialog_modal">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">
                    <span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span> 确认框
                </h4>
            </div>

            <div class="modal-body">
                <div id="data_delete_dialog_load_remote"></div>
            </div>

            <div class="modal-footer">
                <%-- 用来保存删除URL临时变量 --%>
                <input type="hidden" id="delete_url"/>
                <%--这个id, 主要删除数据用的主键id, 值随着选中不同行而变化. --%>
                <input type='hidden' id="primary_id"/>
                <button class="btn btn-warning" disabled="disabled" style="display: none" id="data_delete_error_message"></button>
                <button type="button" id="data_delete_dialog_ok_button" class="btn btn-danger" pagenow="${page.pageNow}" pagesize="${page.pageSize}" totalnum="${page.totalNum}" lastpage="${page.lastPage}">
                    <span class="glyphicon glyphicon-check" aria-hidden="true"></span> 确认
                </button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 取消
                </button>
            </div>
        </div>
    </div>
</div>

<%-- 新增/编辑模态框, 可以与需要填写任何信息的业务公用 --%>
<div class="modal fade" id="data_add_or_edit_dialog" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" id="data_add_or_edit_dialog_modal">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">
                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span> <span id="data_add_or_edit_dialog_title"></span>
                </h4>
            </div>

            <div class="modal-body">
                <div id="data_add_or_edit_dialog_load_remote">
                </div>
            </div>

            <div class="modal-footer">
                <button class="btn btn-danger" disabled="disabled" style="display: none" id="data_add_or_edit_dialog_error_message"></button>
                <button type="button" class="btn btn-success" id="data_add_or_edit_dialog_ok_button" pagenow="${page.pageNow}">
                    <span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 保存
                </button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 取消
                </button>
            </div>
        </div>
    </div>
</div>
