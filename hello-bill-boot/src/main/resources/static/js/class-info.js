/**
 * class-info.html js
 *
 * @author null
 * @date 2022-01-04
 * @link <a href="https://github.com/dkbnull/hello-bill">GitHub</a>
 */
let $, form;

layui.use(['layer', 'table', 'form'], function () {
    if (!validate()) {
        return;
    }

    $ = layui.jquery;
    form = layui.form;

    initMethod();
    doPostQuery();
});

function initMethod() {
    form.on('radio(type)', function (obj) {
        doPostQuery();
    });
}

function doPostQuery() {
    const data = {
        type: $('input[name="type"]:checked').val()
    };

    doPost("class/query", data, callbackQuery)
}

function callbackQuery(result) {
    const table = layui.table;
    table.render({
        elem: '#info-table',
        data: result.data,
        cellMinWidth: 100,
        cols: [[
            {field: 'topClass', title: '顶级分类'},
            {field: 'secondClass', title: '二级分类'},
            {field: 'typeName', title: '类别'},
            {
                field: 'serialNo', title: '序号', edit: 'number',
                style: 'cursor: pointer; font-weight:bold; background-color: #07C180; color: #fff;'
            },
            {
                field: 'status', title: '状态',
                templet: function (d) {
                    let status = '';
                    if (d.status === '1') {
                        status = 'checked';
                    }

                    return '<input type="checkbox" name="status" lay-skin="switch" lay-text="启用|禁用" ' +
                        ' data-uuid="' + d.uuid + '"lay-filter="status"' + status + ' />';
                }
            }
        ]],
        page: true,
        limit: 10
    });

    table.on('edit(infoTable)', function (obj) {
        const value = obj.value;
        if (!isNumber(value)) {
            layer.msg('请输入数字');
            return;
        }

        doPostUpdate(obj.data.uuid, 'serialNo', value);
    });

    form.on('switch(status)', function (obj) {
        const uuid = obj.elem.attributes['data-uuid'].value;
        doPostUpdate(uuid, 'status', obj.elem.checked ? '1' : '0');
    });
}

function doPostUpdate(uuid, key, value) {
    const data = {
        uuid: uuid,
        key: key,
        value: value
    };

    doPost("class/update", data, function (result) {
        layer.msg(result.message);
    })
}