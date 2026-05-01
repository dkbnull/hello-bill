/**
 * import.html js - 账单导入页面逻辑模块
 *
 * @author null
 * @date 2025-01-27
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
let $;
let currentPageNum = 1;
let currentPageSize = 10;

layui.use(['layer', 'table', 'upload', 'element'], function () {
    if (!validate()) {
        return;
    }

    $ = layui.jquery;

    initMethod();
    doPostQuery(1, 10);
});

function initMethod() {
    const layer = layui.layer;

    $('.reload-info .layui-btn').on('click', function () {
        const method = $(this).data('method');
        active[method] ? active[method].call(this) : '';
    });

    const active = {
        reloadInfo: function () {
            doPostQuery(1, currentPageSize);
        }
    };

    doUpload('#upload-file', 'csv', '/import/billFile', null, function (res) {
        if (res !== null) {
            layer.msg(res.msg);

            doPostQuery(1, currentPageSize);
        } else {
            const reUploadFile = $('#re-upload-file');
            reUploadFile.removeClass('layui-hide');
            reUploadFile.on('click', function () {
                uploadFile.upload();
            });
        }
    });
}

function doPostQuery(pageNum, pageSize) {
    currentPageNum = pageNum;
    currentPageSize = pageSize;
    doPost('import/list', {pageNum: pageNum, pageSize: pageSize}, callback);
}

function callback(result) {
    const table = layui.table;
    const pageData = result.data;

    table.render({
        elem: '#info-table',
        data: pageData.records,
        totalRow: true,
        cols: [[
            {field: 'billTypeName', title: '类型'},
            {field: 'billTime', title: '时间', width: 180},
            {field: 'topClass', title: '顶级分类'},
            {field: 'secondClass', title: '二级分类'},
            {field: 'detailConvert', title: '明细'},
            {field: 'amount', title: '金额'},
            {field: 'payMode', title: '支付方式'},
            {field: 'remark', title: '备注'},
            {fixed: 'right', title: '操作', toolbar: '#info-table-bar', width: 160}
        ]]
    });

    renderPage(pageData, function (curr, limit) {
        doPostQuery(curr, limit);
    });

    table.on('tool(infoTable)', function (obj) {
        if (obj.event === 'edit') {
            layer.open({
                type: 2,
                title: '修改',
                content: 'import-edit.html?id=' + obj.data.id,
                area: ['350px', '500px'],
                maxmin: true,
                shade: 0,
                btn: ['确认', '取消'],
                yes: function (index, layero) {
                    const childWindow = layero.find('iframe')[0].contentWindow;
                    childWindow.updateInfo();
                },
                btn2: function (index) {
                    layer.close(index);
                }
            });
        } else if (obj.event === 'confirm') {
            layer.confirm('是否确认当前账单明细？', {icon: 1}, function (index) {
                const data = {
                    id: obj.data.id
                };

                doPost('import/confirm', data, callbackQuery);
                layer.close(index);
            });
        } else if (obj.event === 'del') {
            layer.confirm('是否删除当前账单明细？', {icon: 2}, function (index) {
                const data = {
                    id: obj.data.id
                };

                doPost('import/delete', data, callbackQuery);
                layer.close(index);
            });
        }
    });
}

function callbackQuery(result) {
    doPostQuery(currentPageNum, currentPageSize);
}

let editLayerIndex = null;

function closeAll(message) {
    if (editLayerIndex !== null) {
        layer.close(editLayerIndex);
        editLayerIndex = null;
    }
    layer.msg(message);

    doPostQuery(currentPageNum, currentPageSize);
}
