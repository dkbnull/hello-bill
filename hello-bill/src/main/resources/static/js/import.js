/**
 * js
 *
 * @author null  2025-01-27
 * https://github.com/dkbnull/HelloBill
 */
let $;

layui.use(['layer', 'table', 'upload', 'element'], function () {
    if (!validate()) {
        return;
    }

    $ = layui.jquery;

    initMethod();
    doPostQuery();
});

function initMethod() {
    const layer = layui.layer;

    $('.reload-info .layui-btn').on('click', function () {
        const method = $(this).data('method');
        active[method] ? active[method].call(this) : '';
    });

    const active = {
        reloadInfo: function () {
            doPostQuery();
        }
    };

    doUpload('#upload-file', 'csv', '/import/billFile', null, function (res) {
        if (res != null) {
            layer.msg(res.message);

            doPostQuery();
        } else {
            const reUploadFile = $('#re-upload-file');
            reUploadFile.removeClass("layui-hide");
            reUploadFile.on('click', function () {
                uploadFile.upload();
            });
        }
    })
}

function doPostQuery() {
    doPost("import/queryList", null, callback)
}

function callback(result) {
    const table = layui.table;
    table.render({
        elem: '#info-table',
        data: result.data,
        totalRow: true,
        cols: [[
            {field: 'billTypeValue', title: '类型'},
            {field: 'billTime', title: '时间', width: 180},
            {field: 'topClass', title: '顶级分类', width: 110},
            {field: 'secondClass', title: '二级分类', width: 110},
            {field: 'detail', title: '明细'},
            {field: 'amount', title: '金额'},
            {field: 'remark', title: '备注'},
            {fixed: 'right', title: '操作', toolbar: '#info-table-bar', width: 160}
        ]],
        page: true,
        limit: 10
    });

    table.on('tool(infoTable)', function (obj) {
        if (obj.event === 'del') {
            layer.confirm('是否删除当前账单明细？', function (index) {
                const data = {
                    id: obj.data.id
                };

                doPost("import/delete", data, callbackQuery)
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
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
                btn2: function () {
                    layer.closeAll();
                }
            });
        } else if (obj.event === 'confirm') {
            layer.confirm('是否确认当前账单明细？', function (index) {
                const data = {
                    id: obj.data.id
                };

                doPost("import/confirm", data, callbackQuery)
                layer.close(index);
            });
        }
    });
}

function callbackQuery(result) {
    doPostQuery();
}

function closeAll(message) {
    layer.closeAll();
    layer.msg(message);

    doPostQuery();
}